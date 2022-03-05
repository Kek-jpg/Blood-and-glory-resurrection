/*
* file name: mmbridge.js
* version: Android SDK 2.2
* Rev: 2
*/

/* utilities  */
// get element by id
if (typeof $ === 'undefined') {
	$ = function(id) {
		return document.getElementById(id);
	};
}

// prevent console.log, console.error failure
var DEBUG = true;
if (!window.console || !DEBUG) {
	console = {
		log: function(){},
		error: function(){}
	};
}

/* local variables & functions */
var gMMBridge_SendEventBatchTimer, 
	gMMBridge_QueuedTrackingEvents, 
	gMMBridge_SavedUnloadHandler, 
	gMMBridge_sendTrackingEventsTimer, //ms
	gSendTimestamps = false, // acceleration handling

	// copy or extend objects
	deepCopy = function(source, target) {
		var i, src;
	
		for (i in source) {
			if (source.hasOwnProperty(i)) {
				src = source[i];
				
				if (src && typeof src === 'object' && !src.node) {
					switch (toString.call(src)) {
						case '[object Object]':
							target[i] = deepCopy(src, {});
							break;
						case '[object Array]':
							target[i] = deepCopy(src, []);
							break;
						default:
							target[i] = src;
							break;
					}
				}
				else {
					target[i] = src;
				}
			} 
		}
		
		return target;
	},
	
	// on call to sdk failure
	_unsupported = function(){
		alert('MMBridge/PhoneGap is not supported!');
	},
	
	// make call to sdk
	call_MMBridgeFunc = function(command){
		//DEBUGGING ONLY
		//alert('mmbridge:' + command);
	
		try {
			document.location = ('mmbridge:' + command);
		}
		catch (e) {
			console.log('Command \'' + command + '\' has not been executed, because of exception: ' + e);
			alert('Error executing command \'' + command + '\'.');
			_unsupported();
		}	
	},
	
	// creates a standard callback object  
	build_MMBridgeCallback = function(MMBridgeFunc, execOnInit){
		return {
			callbackQueue: [],
			callbackQueueLength: 0,
			callback: function() {
				for (var i in this.callbackQueue) {
					if (this.callbackQueue.hasOwnProperty(i) && this.callbackQueue[i]) {
						this.callbackQueue[i].apply(null,arguments);	
					}
				}
			},
			init: function(fn) {
				this.callbackQueueLength = this.callbackQueue.push(fn); 
				
				if (execOnInit) {
					this.get();
				}
				
				return this.callbackQueueLength - 1;
			},
			disable: function(fnId) {
				this.callbackQueue[fnId] = null;
			},	
			set: function() {
				if (this.callbackQueueLength > 0 && Device.available) {
					this.callback(arguments);
				}
			},
			get: function() {
				if (typeof MMBridgeFunc !== 'undefined') {
					Device.exec(MMBridgeFunc);
				}
			}
		};
	};	

/* global variables & functions */
// will return all queued events, serialized in the order they were received
// each event will look like: type=<TYPE>&key=<KEY>&val=<DATA>
function serializeTrackingEvents(){
	var retStr;
	for (var i in gMMBridge_QueuedTrackingEvents) {
		if (gMMBridge_QueuedTrackingEvents.hasOwnProperty(i)) {
			var str = escape(gMMBridge_QueuedTrackingEvents[i]);
			retStr = retStr ? (retStr + '&' + str) : (str);
		}
	}
	return retStr;
}

function sendQueuedTrackingEvents(){
	var events = serializeTrackingEvents();
	if (events) {
		call_MMBridgeFunc('batchedEvents?' + events);
	}
	gMMBridge_QueuedTrackingEvents = null;
	clearTimeout(gMMBridge_sendTrackingEventsTimer);
	gMMBridge_sendTrackingEventsTimer = null;
}

function queueTrackingEvent(eventType, eventKey, eventVal){
	if (eventType) {
		var ev = escape(eventType) +
		(eventKey || eventVal ? '?' : '') +
		(eventKey ? 'key=' + escape(eventKey) : '') +
		(eventVal ? ((eventKey ? '&' : '') + 'val=' + escape(eventVal)) : '');
		
		if (!gMMBridge_QueuedTrackingEvents) {
			gMMBridge_QueuedTrackingEvents = [];
		}
		gMMBridge_QueuedTrackingEvents[gMMBridge_QueuedTrackingEvents.length] = ev;
		
		if (!gMMBridge_sendTrackingEventsTimer) {
			gMMBridge_sendTrackingEventsTimer = setTimeout(sendQueuedTrackingEvents, 100); // every 100 ms, flush events
		}
	}
}

function MMBridge_unload(){
	// flush any unqueued events
	if (gMMBridge_SavedUnloadHandler) {
		gMMBridge_SavedUnloadHandler(); //important to call this first, it might send some events!
	}
	if (gMMBridge_sendTrackingEventsTimer) {
		clearTimeout(gMMBridge_sendTrackingEventsTimer);
		sendQueuedTrackingEvents();
	}
	
	return true;
}

function MMBridge_saveunload(){
	gMMBridge_SavedUnloadHandler = window.onunload;
	window.onunload = MMBridge_unload;
}

function _mm_timeStampString(){
	if (gSendTimestamps) {
		return '&_MM_Timestamp=' + (Math.floor(new Date().getTime() / 1000)).toString(); //Fixme - convert to a ISO-8601, or some other standard?
	}
	else {
		return '';
	}
}

// public mmbridge object
var Device = {
	available: false,
	bridgeVersion: '',
	model: '',
	version: '',
	uuid: '',
	initCallback: null,
	initQueue : [],
	init: function(bridgeVersion, model, version, uuid){
		try {
			//save the document's onunload handler, so we can call it after our own
			MMBridge_saveunload();
			Device.available = true;
			Device.bridgeVersion = bridgeVersion;
			Device.model = model;
			Device.version = version;
			Device.uuid = uuid;
			if (Device.initCallback !== null) {
				Device.initCallback();
				Device.initCallback = null;
			}
			if (Device.initQueue.length > 0) {
				while (Device.initQueue.length > 0) { 
					Device.exec(Device.initQueue.shift()); 
				}
			}
		} 
		catch (e) {
			// alert('MMBridge is not supported!');
		}
	},
	exec: function(command){
		if (Device.available) {
			call_MMBridgeFunc(command);
		}
		else {
			Device.initQueue.push(command);
		}
	},	
	launchYoutube: function(urlStr){
		Device.exec('launchYoutube?url=' + escape(urlStr));
	},
	log: function(message){
		Device.exec('log?message=' + escape(message));
	},
	DeviceID: build_MMBridgeCallback('getDeviceID'),
	SDKVersion: build_MMBridgeCallback('getSDKVersion'),
	BatteryLevel: build_MMBridgeCallback('getBatteryLevel'),
	AdRequest: build_MMBridgeCallback('getAdRequest'),
	Browser : {
		onRequest: build_MMBridgeCallback('initBrowserRequest'),
		onComplete: build_MMBridgeCallback('initBrowserComplete')		
	},
	AdData: {
		onExpandStart: build_MMBridgeCallback(),
		onExpandComplete: build_MMBridgeCallback(),
		onCollapseStart: build_MMBridgeCallback(),
		onCollapseComplete: build_MMBridgeCallback(),
		adID: build_MMBridgeCallback('getAdID')
	},
	Location: build_MMBridgeCallback('getloc'),
	vibrate: function(){
		Device.exec('vibrate');
	},
	sendTimestamps: function(bSend){
		if (bSend && typeof bSend == 'boolean') {
			gSendTimestamps = bSend;
		}
		else {
			gSendTimestamps = true;
		}
	},
	Orientation: deepCopy({ angle: 0 },build_MMBridgeCallback('initOrientation', true)),  
	/* Acceleration: Treated differently from other callbacks. */
	Acceleration: {
		accelX: 0,
		accelY: 0,
		accelZ: 0,
		rollingX: 0,
		rollingY: 0,
		rollingZ: 0,
		updateFreq: 1.0 / 40.0,
		shakeThreshold: 2.0,
		filteringFactor: 0.1,
		callback: null,
		shakeCallback: null,
		_callback: function(x, y, z, rx, ry, rz){
			if (Device.Acceleration.callback && Device.available) {
				Device.Acceleration.callback(x, y, z, rx, ry, rz);
			}
		},
		observe: function(bStart, optFreq){
			if (typeof bStart !== 'boolean') {
				bStart = true;
			}
			if (optFreq && !isNaN(parseFloat(optFreq))) {
				Device.Acceleration.updateFreq = (optFreq === 0.0) ? 1.0 / 40.0 : optFreq;
			}
			Device.exec('getaccel?start=' + bStart + '&freq=' + Device.Acceleration.updateFreq + '&filteringFactor=' + Device.Acceleration.filteringFactor + '&shakeThreshold=' + Device.Acceleration.shakeThreshold);
		}
	},
	/* END Acceleration */
	/* LEGACY : Can go away in favor of Document.Acceleration.observe(bStart, freq) */
	observeAccel: function(bStart, freq){ 
		Document.Acceleration.observe(bStart, freq);
	},
	/* END LEGACY */
	playsound: function(soundname){
		if (!Device || !Device.available) {
			setTimeout('Device.playsound(' + soundname + ')', 500);
		}
		else {
			Device.exec('playsound?file=' + escape(soundname));
		}
	},
	trackingEvent: {
		setString: function(strKey, strValue){
			try {
				queueTrackingEvent('setTrackingEventString', strKey, strValue);
			} 
			catch (e) {
				_unsupported();
			}
		},
		setDurationForKey: function(strKey, duration){
			try {
				queueTrackingEvent('setDurationForKey', strKey, parseFloat(duration));
			} 
			catch (e) {
				_unsupported();
			}
		},
		startTimerForKey: function(strKey){
			try {
				queueTrackingEvent('startTimerForKey', strKey);
			} 
			catch (e) {
				_unsupported();
			}
		},
		endTimerForKey: function(strKey){
			try {
				queueTrackingEvent('endTimerForKey', strKey);
			} 
			catch (e) {
				_unsupported();
			}
		},
		incrementKey: function(strKey, optDurationMs){
			try {
				queueTrackingEvent('incrementKey', escape(strKey), parseFloat(_mm_timeStampString()));
			} 
			catch (e) {
				_unsupported();
			}
		},
		setNumber: function(strKey, numValue){
			try {
				queueTrackingEvent('setTrackingEventNumber', escape(strKey), parseFloat(numValue));
			} 
			catch (e) {
				_unsupported();
			}
		},
		setValue: function(strKey, inval){
			try {
				if (typeof inval == 'string') {
					queueTrackingEvent('setTrackingEventString', escape(strKey), inval);
				}
				else 
					if (typeof inval == 'number') {
						queueTrackingEvent('setTrackingEventNumber', escape(strKey), parseFloat(inval));
					}
			} 
			catch (e) {
				_unsupported();
			}
		},
		setValues: function(dataobj){
			try {
				for (var i in dataobj) {
					if (i && typeof i == 'string') {
						if (dataobj[i] && typeof(dataobj[i]) == 'string') {
							queueTrackingEvent('setTrackingEventString', i, dataobj[i]);
						}
						else if (dataobj[i] && typeof(dataobj[i]) == 'number') {
							queueTrackingEvent('setTrackingEventNumber', i, parseFloat(dataobj[i]));
						}
					}
				}
			} 
			catch (e) {
				_unsupported();
			}
		}
	},
	sendData: function(dataobj, optDuration){
		var i, val, serialdata = '';
		for (i in dataobj) {
			if (i && typeof i === 'string') {
				if (dataobj[i]) {
					val = escape(dataobj[i]);
				}
				if (serialdata === '') {
					serialdata += '?' + escape(i) + '=' + val;
				}
				else {
					serialdata += '&' + escape(i) + '=' + val;
				}
			}
		}
		if (optDuration && optDuration !== null && !isNaN(optDuration)) {
			serialdata += '&MMUserDuration=' + optDuration.toString();
		}
		
		Device.exec('sendData' + serialdata + _mm_timeStampString());
	},
	dismissAd: function(){
		sendQueuedTrackingEvents();
		Device.exec('dismissAd');
	},
	dismissBrowser: function(){
		Device.exec('dismissBrowser');
	},
	audioPlayBackDidStart: function(){
		Device.exec('audioPlayBackDidStart');
	},
	audioPlayBackDidFinish: function(){
		Device.exec('audioPlayBackDidFinish');
	},
	screenCapture: function(filename){
		Device.exec('screenCapture?filename=' + escape(filename));
	},
	launchApp: function(appURL, alternateID){
		Device.exec('launchApp?appURL=' + escape(appURL) + '&alternateID=' + escape(alternateID));
	},
	passThruClickEvent: function(x, y){
		Device.exec('passThruClickEvent?x=' + escape(x) + '&y=' + escape(y));
	},
	playVideo: function(videoURL, videoKey){
		var videoRequest = 'playVideo?videoURL=' + escape(videoURL);
		if (videoKey && videoKey !== null) {
			videoRequest += '&videoKey=' + escape(videoKey);
		}
			
		Device.exec(videoRequest);
	},
	expandAd: function(){
		Device.exec('expandAd');
	},
	deactivateAppLevelSwipe: function() {
		Device.exec('deactivateAppLevelSwipe');
	},
	activateAppLevelSwipe: function() {
		Device.exec('activateAppLevelSwipe');
	},
	forceExpand: function() {
		Device.exec('forceExpand');
	}
};

//wrappers for user convenience
var MMBridge = Device;

_accel = {}
_accel.x = 0;
_accel.y = 0;
_accel.z = 0;

function gotAccel(x, y, z){	
	_accel.x = x;	
	_accel.y = y;	
	_accel.z = z;
}

var AdDevice = new Object();

AdDevice.getCurrentAcceleration = function (successCallback, errorCallback, options) {
	if (typeof successCallback == "function") {
		successCallback(_accel);	
	}
}

AdDevice.watchAcceleration = function (successCallback, errorCallback, options) {  
	var frequency = (options != undefined)? options.frequency : 10000;
	Accel.start(frequency);
	return setInterval(function() {
		AdDevice.getCurrentAcceleration(successCallback, errorCallback, options);
		}, frequency); 
}

AdDevice.clearWatch =  function (watchId) {	
	Accel.stop();	
	clearInterval(watchId);
}
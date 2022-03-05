/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.text.TextUtils
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 */
package com.kontagent.session;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.kontagent.Kontagent;
import com.kontagent.KontagentLog;
import com.kontagent.KontagentPrefs;
import com.kontagent.connectivity.ConnectivityTracker;
import com.kontagent.queue.IKTQueue;
import com.kontagent.queue.IMessageStackMonitorListener;
import com.kontagent.queue.ITransferQueueListener;
import com.kontagent.queue.Message;
import com.kontagent.queue.MessageStackMonitor;
import com.kontagent.queue.TransferQueue;
import com.kontagent.session.ISession;
import com.kontagent.session.ISessionListener;
import com.kontagent.util.GUIDUtil;
import com.kontagent.util.LibUtils;
import com.kontagent.util.ListUtil;
import com.kontagent.util.MapUtil;
import com.kontagent.util.NetworkUtil;
import com.kontagent.util.Waiter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session
implements ISession,
ITransferQueueListener,
IMessageStackMonitorListener {
    protected static final String ACTION_EVENT = "act";
    protected static final String APPLICATION_ADDED = "apa";
    protected static final String CUSTOM_EVENT = "evt";
    private static final long DEFAULT_HEARTBEAT_TIMEOUT = 30000L;
    private static final String FB_LIB_MSG = "pbk";
    public static final String FEEDPUB = "feedpub";
    private static final String GOAL_COUNT = "gci";
    private static final String INVITE_RESPONSE = "inr";
    private static final String INVITE_SENT = "ins";
    private static final String MESSAGE_RECEIVED = "mer";
    private static final String MESSAGE_SENT = "mes";
    private static final String NOTIFICATION_EMAIL_RESPONSE = "nei";
    private static final String NOTIFICATION_EMAIL_SENT = "nes";
    private static final String PAGE_REQUEST = "pgr";
    private static final String REVENUE_TRACKING = "mtu";
    private static final String STREAM_POST = "pst";
    private static final String STREAM_POST_RESPONSE = "psr";
    public static final String TAG = Session.class.getSimpleName();
    public static final String TEST_URL_PREFIX = "http://test-server.kontagent.com/api/v1/";
    private static final String UNDIRECTED_COMMUNICATION_CLICK = "ucc";
    public static final String URL_PREFIX = "http://api.geo.kontagent.net/api/v1/";
    private static final String USER_INFORMATION = "cpu";
    private String apiKey;
    private String apiKeyForTimezone;
    private Integer apiKeyTimezoneOffset;
    private String apiUrlPrefix;
    private Context context;
    private String experimentalServerBaseUrl;
    private String fbAppID;
    private final Handler heartbeatHandler;
    private final Runnable heartbeatRunnable;
    private boolean isSessionStarted;
    private MessageStackMonitor monitor;
    private final KontagentPrefs prefs;
    private String sdkMode;
    private String senderId;
    private ISessionListener sessionListener;
    private boolean shouldSendApplicationAdded;
    private TransferQueue transferQueue;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Session(Context context, HashMap<String, Object> hashMap) {
        Object object;
        Object object2;
        this.heartbeatRunnable = new Runnable(){

            public void run() {
                Session.this.heartbeat();
                Session.this.heartbeatHandler.postDelayed((Runnable)this, 30000L);
            }
        };
        this.context = context;
        this.apiKey = (String)hashMap.get((Object)"keySessionApiKey");
        this.sdkMode = (String)hashMap.get((Object)"keySessionMode");
        Object object3 = hashMap.get((Object)"keySessionApiURL");
        if (object3 != null) {
            this.apiUrlPrefix = (String)object3;
        }
        if (TextUtils.isEmpty((CharSequence)this.apiUrlPrefix)) {
            this.apiUrlPrefix = this.urlPrefix();
        }
        if ((object = hashMap.get((Object)"keySessionSenderId")) != null) {
            this.senderId = (String)object;
        }
        if ((object2 = hashMap.get((Object)"keySessionAPA")) != null) {
            this.shouldSendApplicationAdded = (Boolean)object2;
        }
        this.apiKeyForTimezone = (String)hashMap.get((Object)"keySessionApiKeyForTimezone");
        Object object4 = hashMap.get((Object)"keySessionApiKeyTimezoneOffset");
        this.apiKeyTimezoneOffset = 0;
        if (object4 != null) {
            try {
                this.apiKeyTimezoneOffset = Integer.valueOf((String)((String)object4));
            }
            catch (NumberFormatException numberFormatException) {
                KontagentLog.e(TAG, "Unable to convert apiKeyTimezoneOffset from String to Integer", numberFormatException);
            }
        }
        this.fbAppID = (String)hashMap.get((Object)"keySessionFBAppId");
        String string2 = (String)hashMap.get((Object)"keySessionServUrl");
        this.experimentalServerBaseUrl = string2 != null ? string2 : "http://src1.mobile.kontagent.com/api/v1/";
        this.prefs = new KontagentPrefs(this.context);
        this.heartbeatHandler = new Handler(Looper.getMainLooper());
        this.isSessionStarted = false;
    }

    private void applicationAdded() {
        this.applicationAdded((Map<String, String>)new HashMap());
    }

    private boolean assertStarted(String string2) {
        if (!this.isStarted()) {
            KontagentLog.e("Kontagent not started. Ignoring request: " + string2, null);
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private String buildUrl(String string2, Map<String, String> map, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        String string3 = string2.equals((Object)FB_LIB_MSG) ? this.experimentalServerBaseUrl : this.getApiUrlPrefix();
        StringBuilder stringBuilder2 = stringBuilder.append(string3);
        String string4 = bl ? this.apiKeyForTimezone : this.apiKey;
        stringBuilder2.append(string4).append("/").append(string2).append("/?").append(MapUtil.mapToString(map));
        return stringBuilder.toString();
    }

    private String fbLibGenerateDeviceIDFromCookie(Class class_) {
        if (class_ != null) {
            Class[] arrclass = new Class[]{Context.class};
            Object[] arrobject = new Object[]{this.context};
            return (String)LibUtils.callPublicMethod(class_, "generateDeviceIDFromCookie", arrclass, arrobject);
        }
        return null;
    }

    private String fbLibGenerateDeviceIDFromCookieHashed(Class class_) {
        if (class_ != null) {
            Class[] arrclass = new Class[]{Context.class, String.class};
            Object[] arrobject = new Object[]{this.context, this.apiKey};
            return (String)LibUtils.callPublicMethod(class_, "generateDeviceIDFromCookieHashed", arrclass, arrobject);
        }
        return null;
    }

    private void onFirstRun() {
        this.prefs.setFirstRun(false);
        KontagentLog.i("First sdk start detected.");
        String string2 = this.prefs.getReferrerEventType();
        String string3 = this.prefs.getReferrerEventSubtype1();
        String string4 = this.prefs.getReferrerEventSubtype2();
        String string5 = this.prefs.getReferrerEventSubtype3();
        KontagentLog.d("Kontagent referrer arguments: ");
        KontagentLog.d("\ttu=" + string2);
        KontagentLog.d("\tst1=" + string3);
        KontagentLog.d("\tst2=" + string4);
        KontagentLog.d("\tst3==" + string5);
        if (string2 == null || string3 == null) {
            KontagentLog.i("No referrer arguments specified, sending \"apa\" event only.");
            this.applicationAdded();
            return;
        }
        KontagentLog.i("Referrer arguments were specified, sending \"apa\" and \"ucc\" events.");
        String string6 = this.generateShortUniqueTrackingTag();
        HashMap hashMap = new HashMap();
        hashMap.put((Object)"su", (Object)string6);
        if (string3 != null) {
            hashMap.put((Object)"st1", (Object)string3);
        }
        if (string3 != null && string4 != null) {
            hashMap.put((Object)"st2", (Object)string4);
        }
        if (string3 != null && string4 != null && string5 != null) {
            hashMap.put((Object)"st3", (Object)string5);
        }
        this.undirectedCommunicationClick(true, string2, (Map<String, String>)hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put((Object)"su", (Object)string6);
        this.applicationAdded((Map<String, String>)hashMap2);
    }

    private Map<String, String> processOptionalParams(Map<String, String> map) {
        if (map != null) {
            return new HashMap(map);
        }
        return new HashMap();
    }

    private void sendMessage(String string2, Map<String, String> map) {
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        long l = System.currentTimeMillis() / 1000L;
        String string3 = Long.toString((long)l);
        map2.put((Object)"ts", (Object)string3);
        Message message = new Message(Session.super.buildUrl(string2, map2, false), string2, this.senderId, string3, GUIDUtil.generateSenderId());
        this.transferQueue.enqueue(message);
        if (this.apiKeyForTimezone != null && this.apiKeyTimezoneOffset != 0) {
            String string4 = Long.toString((long)(l + (long)this.apiKeyTimezoneOffset.intValue()));
            map2.put((Object)"ts", (Object)string4);
            Message message2 = new Message(Session.super.buildUrl(string2, map2, true), string2, this.senderId, string3, GUIDUtil.generateSenderId());
            Object[] arrobject = new Object[]{this.apiKeyTimezoneOffset, this.apiKeyTimezoneOffset / 3600};
            String string5 = String.format((String)"%ss=%sh", (Object[])arrobject);
            String string6 = TAG;
            Object[] arrobject2 = new Object[]{string2, this.apiKeyForTimezone, string5, string4, this.apiKey, string3};
            KontagentLog.i(string6, String.format((String)"MIRRORING HTTP call=%s (API_KEY_FOR_TIMEZONE=%s, timezone offset = %s, timestamp=%s) for (API_KEY=%s, timestamp=%s)", (Object[])arrobject2));
            this.transferQueue.enqueue(message2);
        }
    }

    private void setupQueue() {
        KontagentLog.d("Setting up processing queue...");
        this.transferQueue = new TransferQueue(this.context, this.senderId).setTransferQueueListener(this);
        String string2 = TAG;
        Object[] arrobject = new Object[]{this.transferQueue, this};
        KontagentLog.d(string2, String.format((String)"New transferQueue = %s with listener=%s has been created", (Object[])arrobject));
        this.monitor = new MessageStackMonitor(this.senderId).setMonitorListener(this);
        Waiter.getInstance().waitForOperationToComplete(new Runnable(){

            public void run() {
                Session.this.transferQueue.start();
            }
        }, 30000L);
        this.monitor.syncMessagesWithInternalDatabaseOnStartup(this.transferQueue.peekAll());
    }

    private void startHeartbeatTimer() {
        this.heartbeatHandler.postDelayed(this.heartbeatRunnable, 0L);
    }

    private void stopHeartbeatTimer() {
        this.heartbeatHandler.removeCallbacks(this.heartbeatRunnable);
    }

    @Override
    public void applicationAdded(Map<String, String> map) {
        if (!Session.super.assertStarted("applicationAdded")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        map2.put((Object)"s", (Object)this.senderId);
        map2.put((Object)"kt_v", (Object)Kontagent.libraryVersion());
        Session.super.sendMessage(APPLICATION_ADDED, map2);
        Class class_ = LibUtils.getRuntimeLibClass("KontagentFBLib");
        if (class_ != null) {
            String string2 = Session.super.fbLibGenerateDeviceIDFromCookie(class_);
            if (!TextUtils.isEmpty((CharSequence)string2)) {
                String string3 = Session.super.fbLibGenerateDeviceIDFromCookieHashed(class_);
                HashMap hashMap = new HashMap();
                hashMap.put((Object)"c", (Object)string2);
                hashMap.put((Object)"su", (Object)string3);
                hashMap.put((Object)"kt_v", (Object)Kontagent.libraryVersion());
                hashMap.put((Object)"appid", (Object)this.fbAppID);
                Object[] arrobject = new Object[]{this.fbAppID, string2};
                KontagentLog.d(String.format((String)"Sending FB App Id = %s and cookie = %s to experimental server", (Object[])arrobject));
                Session.super.sendMessage(FB_LIB_MSG, (Map<String, String>)hashMap);
                return;
            }
            KontagentLog.d("Nothing to send to experimental server: FB lib is present, but FB cookie is missing.");
            return;
        }
        KontagentLog.d("KontagentFBLib is missing");
    }

    @Override
    public void changeMaxQueueSize(int n) {
        if (!Session.super.assertStarted("changeMaxQueueSize")) {
            return;
        }
        this.transferQueue.setMaxQueueSize(n);
    }

    @Override
    public int currentMaxQueueSize() {
        if (!this.assertStarted("currentMaxQueueSize")) {
            return 500;
        }
        return this.transferQueue.getMaxQueueSize();
    }

    @Override
    public void customEvent(String string2, Map<String, String> map) {
        if (!Session.super.assertStarted("customEvent")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        map2.put((Object)"s", (Object)this.senderId);
        map2.put((Object)"n", (Object)string2);
        map2.put((Object)"kt_v", (Object)Kontagent.libraryVersion());
        Session.super.sendMessage(CUSTOM_EVENT, map2);
    }

    @Override
    public String generateShortUniqueTrackingTag() {
        return GUIDUtil.generateShortTrackingId(this.context, this.apiKey);
    }

    @Override
    public String generateUniqueTrackingTag() {
        return GUIDUtil.generateTrackingId();
    }

    public String getApiUrlPrefix() {
        return this.apiUrlPrefix;
    }

    public ConnectivityTracker getConnectivityTracker() {
        return this.transferQueue.getConnectivityTracker();
    }

    @Override
    public String getSenderId() {
        if (!this.assertStarted("getSenderId")) {
            return null;
        }
        return this.senderId;
    }

    protected void goalCount(Integer n, Integer n2) {
        if (!Session.super.assertStarted("goalCount")) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put((Object)"s", (Object)this.senderId);
        hashMap.put((Object)("gc" + n.toString()), (Object)n2.toString());
        Session.super.sendMessage(GOAL_COUNT, (Map<String, String>)hashMap);
    }

    @Override
    public void heartbeat() {
        if (!this.assertStarted("heartbeat")) {
            return;
        }
        KontagentLog.d("Heartbeat!");
        this.pageRequest(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void inviteResponse(boolean bl, String string2, Map<String, String> map) {
        if (!Session.super.assertStarted("inviteResponse")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        String string3 = bl ? "1" : "0";
        map2.put((Object)"i", (Object)string3);
        map2.put((Object)"u", (Object)string2);
        Session.super.sendMessage(INVITE_RESPONSE, map2);
    }

    protected void inviteSent(Integer n, String string2, Map<String, String> map) {
        ArrayList arrayList = new ArrayList();
        arrayList.add((Object)n);
        this.inviteSent((List<Integer>)arrayList, string2, map);
    }

    protected void inviteSent(List<Integer> list, String string2, Map<String, String> map) {
        if (!Session.super.assertStarted("inviteSent")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        map2.put((Object)"r", (Object)ListUtil.listToString(list));
        map2.put((Object)"u", (Object)string2);
        map2.put((Object)"s", (Object)this.senderId);
        Session.super.sendMessage(INVITE_SENT, map2);
    }

    @Override
    public boolean isStarted() {
        return this.isSessionStarted;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void messageResponse(boolean bl, String string2, Map<String, String> map) {
        if (!Session.super.assertStarted("messageResponse")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        String string3 = bl ? "1" : "0";
        map2.put((Object)"i", (Object)string3);
        map2.put((Object)"tu", (Object)"dashboard");
        map2.put((Object)"u", (Object)string2);
        map2.put((Object)"s", (Object)this.senderId);
        Session.super.sendMessage(MESSAGE_RECEIVED, map2);
    }

    protected void messageSent(final Integer n, String string2, Map<String, String> map) {
        this.messageSent((List<Integer>)new ArrayList<Integer>(){
            {
                this.add((Object)n);
            }
        }, string2, map);
    }

    protected void messageSent(List<Integer> list, String string2, Map<String, String> map) {
        if (!Session.super.assertStarted("messageSent")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        map2.put((Object)"r", (Object)ListUtil.listToString(list));
        map2.put((Object)"tu", (Object)"dashboard");
        map2.put((Object)"u", (Object)string2);
        map2.put((Object)"s", (Object)this.senderId);
        Session.super.sendMessage(MESSAGE_SENT, map2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void notificationEmailResponse(boolean bl, String string2, Map<String, String> map) {
        if (!Session.super.assertStarted("notificationEmailResponse")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        String string3 = bl ? "1" : "0";
        map2.put((Object)"i", (Object)string3);
        map2.put((Object)"u", (Object)string2);
        map2.put((Object)"s", (Object)this.senderId);
        Session.super.sendMessage(NOTIFICATION_EMAIL_RESPONSE, map2);
    }

    protected void notificationEmailSent(final Integer n, String string2, Map<String, String> map) {
        if (!Session.super.assertStarted("notificationEmailSent")) {
            return;
        }
        this.notificationEmailSent((List<Integer>)new ArrayList<Integer>(){
            {
                this.add((Object)n);
            }
        }, string2, map);
    }

    protected void notificationEmailSent(List<Integer> list, String string2, Map<String, String> map) {
        if (!Session.super.assertStarted("notificationEmailSent")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        map2.put((Object)"r", (Object)ListUtil.listToString(list));
        map2.put((Object)"u", (Object)string2);
        map2.put((Object)"s", (Object)this.senderId);
        Session.super.sendMessage(NOTIFICATION_EMAIL_SENT, map2);
    }

    @Override
    public void onMessageAdded(MessageStackMonitor messageStackMonitor, Message message) {
        if (this.sessionListener != null) {
            this.sessionListener.sessionQueueMessageAdded((ISession)this, message);
        }
    }

    @Override
    public void onMessageRemoved(MessageStackMonitor messageStackMonitor, Message message) {
        if (this.sessionListener != null) {
            this.sessionListener.sessionQueueMessageRemoved((ISession)this, message);
        }
    }

    @Override
    public void onMessageStatusChanged(MessageStackMonitor messageStackMonitor, Message message) {
        if (this.sessionListener != null) {
            this.sessionListener.sessionQueueMessageStatusChanged((ISession)this, message);
        }
    }

    protected void pageRequest(Map<String, String> map) {
        if (!Session.super.assertStarted("pageRequest")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        map2.put((Object)"s", (Object)this.senderId);
        map2.put((Object)"kt_v", (Object)Kontagent.libraryVersion());
        Session.super.sendMessage(PAGE_REQUEST, map2);
    }

    @Override
    public boolean pause() {
        this.transferQueue.pause();
        this.stopHeartbeatTimer();
        return true;
    }

    public String preferenceKey(String string2) {
        Object[] arrobject = new Object[]{string2, this.apiKey};
        return String.format((String)"%s.%s", (Object[])arrobject);
    }

    @Override
    public void queueDidAddMessage(IKTQueue iKTQueue, Message message) {
        this.monitor.addMessage(message);
    }

    @Override
    public void queueDidFinishedProcessing(IKTQueue iKTQueue) {
    }

    @Override
    public void queueDidReachabilityChanged(boolean bl) {
        if (this.sessionListener != null) {
            this.sessionListener.sessionQueueDidReachabilityChanged((ISession)this, bl);
        }
    }

    @Override
    public void queueDidRemoveMessage(IKTQueue iKTQueue, Long l) {
        this.monitor.removeMessage(l);
    }

    @Override
    public void queueDidResume(IKTQueue iKTQueue) {
        if (this.sessionListener != null) {
            this.sessionListener.sessionQueueDidResume((ISession)this, this.transferQueue);
        }
    }

    @Override
    public void queueDidStart(IKTQueue iKTQueue) {
        if (this.sessionListener != null) {
            this.sessionListener.sessionQueueDidStart((ISession)this, this.transferQueue);
        }
    }

    @Override
    public void queueDidStop(IKTQueue iKTQueue) {
        if (this.sessionListener != null) {
            this.sessionListener.sessionQueueDidStop((ISession)this, this.transferQueue);
            this.transferQueue = null;
        }
    }

    @Override
    public void queueDidTransferElementFailed(IKTQueue iKTQueue, Long l) {
        Message message = this.monitor.getMessageById(l);
        if (message != null) {
            message.setDeliveryTrials(1 + message.getDeliveryTrials());
            KontagentLog.d(String.format((String)"queueDidTransferElementFailed for message = %s", (Object[])new Object[]{message}));
        }
    }

    @Override
    public int queueSize() {
        if (this.transferQueue != null) {
            return this.transferQueue.queueSize();
        }
        return 0;
    }

    @Override
    public boolean resume() {
        this.transferQueue.resume();
        this.stopHeartbeatTimer();
        this.startHeartbeatTimer();
        return true;
    }

    @Override
    public void revenueTracking(Integer n, Map<String, String> map) {
        if (!Session.super.assertStarted("revenueTracking")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        map2.put((Object)"s", (Object)this.senderId);
        map2.put((Object)"v", (Object)n.toString());
        map2.put((Object)"kt_v", (Object)Kontagent.libraryVersion());
        Session.super.sendMessage(REVENUE_TRACKING, map2);
    }

    @Override
    public void sendDeviceInformation(Map<String, String> map) {
        if (!Session.super.assertStarted("sendDeviceInformation")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        map2.put((Object)"os", (Object)("android_" + Integer.toString((int)Build.VERSION.SDK_INT)));
        map2.put((Object)"m", (Object)Build.MANUFACTURER);
        map2.put((Object)"d", (Object)Build.MODEL);
        if (NetworkUtil.isValidCarrierName(this.context)) {
            map2.put((Object)"c", (Object)NetworkUtil.carrierName(this.context));
        }
        this.userInformation(map2);
    }

    public void setApiKeyForTimezone(String string2) {
        this.apiKeyForTimezone = string2;
    }

    public void setApiKeyTimezoneOffset(Integer n) {
        this.apiKeyTimezoneOffset = n;
    }

    public void setApiUrlPrefix(String string2) {
        this.apiUrlPrefix = string2;
    }

    public void setExperimentalServerBaseUrl(String string2) {
        this.experimentalServerBaseUrl = string2;
    }

    public void setFbAppID(String string2) {
        this.fbAppID = string2;
    }

    public void setSdkMode(String string2) {
        this.sdkMode = string2;
    }

    @Override
    public void setSenderId(String string2) {
        if (!Session.super.assertStarted("setSenderId")) {
            return;
        }
        if (string2 == null) {
            this.senderId = this.prefs.getSenderId(this.preferenceKey("keySessionSenderId"));
            Object[] arrobject = new Object[]{this.senderId};
            KontagentLog.d(String.format((String)"Reverting senderID to generated value - '%s'", (Object[])arrobject));
            return;
        }
        this.senderId = string2;
    }

    public Session setSessionListener(ISessionListener iSessionListener) {
        this.sessionListener = iSessionListener;
        KontagentLog.d(TAG, String.format((String)"New session listener set: %s", (Object[])new Object[]{iSessionListener}));
        return this;
    }

    public void setShouldSendApplicationAdded(boolean bl) {
        this.shouldSendApplicationAdded = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean start() {
        boolean bl;
        block11 : {
            String string2;
            block12 : {
                if (this.isStarted()) {
                    this.resume();
                    return true;
                }
                try {
                    string2 = this.prefs.getSenderId(this.preferenceKey("keySessionSenderId"));
                    boolean bl2 = TextUtils.isEmpty((CharSequence)this.senderId);
                    boolean bl3 = TextUtils.isEmpty((CharSequence)string2);
                    if (bl2 && bl3) {
                        this.senderId = GUIDUtil.generateSenderId().toString();
                        KontagentLog.d(" Generated Sender ID is " + this.senderId);
                        bl = true;
                        break block11;
                    }
                    if (!bl2 && bl3) {
                        bl = true;
                        break block11;
                    }
                    if (!bl2 || bl3) break block12;
                    this.senderId = string2;
                    bl = false;
                    break block11;
                }
                catch (Exception exception) {
                    KontagentLog.e("Failed to start session.", exception);
                    return false;
                }
            }
            String string3 = this.senderId;
            bl = false;
            if (string3 != null) {
                bl = false;
                if (string2 != null) {
                    boolean bl4 = this.senderId.equals((Object)string2);
                    bl = false;
                    if (!bl4) {
                        bl = true;
                    }
                }
            }
        }
        this.prefs.setSenderId(this.preferenceKey("keySessionSenderId"), this.senderId);
        if (!bl) {
            KontagentLog.d("Sender ID value: " + this.senderId);
        }
        Object[] arrobject = new Object[]{this.apiKey, this.sdkMode, this.senderId};
        KontagentLog.d(String.format((String)"STARTING NEW SESSION: key=%s, mode=%s, sender=%s", (Object[])arrobject));
        this.setupQueue();
        this.isSessionStarted = true;
        if (this.shouldSendApplicationAdded && bl) {
            this.onFirstRun();
        }
        this.resume();
        return true;
    }

    @Override
    public void stop() {
        this.isSessionStarted = false;
        if (this.transferQueue != null) {
            this.transferQueue.stop();
        }
        this.stopHeartbeatTimer();
    }

    protected void streamPost(String string2, String string3, Map<String, String> map) {
        if (!Session.super.assertStarted("streamPost")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        map2.put((Object)"s", (Object)this.senderId);
        map2.put((Object)"tu", (Object)string2);
        map2.put((Object)"u", (Object)string3);
        Session.super.sendMessage(STREAM_POST, map2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void streamPostResponse(boolean bl, String string2, String string3, Map<String, String> map) {
        if (!Session.super.assertStarted("streamPostResponse")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        String string4 = bl ? "1" : "0";
        map2.put((Object)"i", (Object)string4);
        map2.put((Object)"tu", (Object)string2);
        map2.put((Object)"u", (Object)string3);
        Session.super.sendMessage(STREAM_POST_RESPONSE, map2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void undirectedCommunicationClick(boolean bl, String string2, Map<String, String> map) {
        if (!Session.super.assertStarted("undirectedCommunicationClick")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        String string3 = bl ? "1" : "0";
        map2.put((Object)"i", (Object)string3);
        map2.put((Object)"tu", (Object)string2);
        Session.super.sendMessage(UNDIRECTED_COMMUNICATION_CLICK, map2);
    }

    public String urlPrefix() {
        if ("test".equals((Object)this.sdkMode)) {
            return TEST_URL_PREFIX;
        }
        if ("production".equals((Object)this.sdkMode)) {
            return URL_PREFIX;
        }
        return "";
    }

    protected void userInformation(Map<String, String> map) {
        if (!Session.super.assertStarted("userInformation")) {
            return;
        }
        Map<String, String> map2 = Session.super.processOptionalParams(map);
        map2.put((Object)"s", (Object)this.senderId);
        map2.put((Object)"kt_v", (Object)Kontagent.libraryVersion());
        Session.super.sendMessage(USER_INFORMATION, map2);
    }

}


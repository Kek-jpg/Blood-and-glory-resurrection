/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.ClassNotFoundException
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.HashMap
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.kontagent;

import android.content.Context;
import android.text.TextUtils;
import com.kontagent.KontagentLog;
import com.kontagent.configuration.SessionConfiguration;
import com.kontagent.session.ISession;
import com.kontagent.session.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Kontagent {
    public static String API_VERSION = "a1.2.5";
    public static final String FEEDPUB = "feedpub";
    public static final String PRODUCTION_MODE = "production";
    private static final String TAG;
    public static final String TEST_MODE = "test";
    private static boolean debug;
    private static boolean experimental;
    private static final Map<String, ISession> sdkSessions;
    private ISession session;

    static {
        debug = false;
        experimental = false;
        sdkSessions = new HashMap();
        TAG = Kontagent.class.getSimpleName();
    }

    private Kontagent() {
    }

    /* synthetic */ Kontagent(1 var1) {
    }

    public static void applicationAdded() {
        if (!Kontagent.assertStarted("applicationAdded")) {
            return;
        }
        Kontagent.applicationAdded((Map<String, String>)new HashMap());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void applicationAdded(Map<String, String> map) {
        ISession iSession;
        if (!Kontagent.assertStarted("applicationAdded") || (iSession = Kontagent.getInstance().getSession()) == null) {
            return;
        }
        iSession.applicationAdded(map);
    }

    private static boolean assertStarted(String string2) {
        ISession iSession = Kontagent.getInstance().getSession();
        if (iSession == null || !iSession.isStarted()) {
            KontagentLog.e("Kontagent not started. Ignoring request: " + string2, null);
            return false;
        }
        return true;
    }

    public static void changeMaxQueueSizeForSessionApiKey(int n, String string2) {
        ISession iSession = (ISession)sdkSessions.get((Object)string2);
        if (iSession != null) {
            iSession.changeMaxQueueSize(n);
            return;
        }
        KontagentLog.w("Failed to change max queue size - there is no such session for apiKey=" + string2);
    }

    public static ISession createSession(Context context, String string2, String string3, String string4) {
        return Kontagent.createSession(context, string2, string3, string4, true);
    }

    public static ISession createSession(Context context, String string2, String string3, String string4, boolean bl) {
        HashMap hashMap = new HashMap();
        hashMap.put((Object)"keySessionApiKey", (Object)string2);
        if (string3 != null) {
            hashMap.put((Object)"keySessionSenderId", (Object)string3);
        }
        hashMap.put((Object)"keySessionAPA", (Object)bl);
        hashMap.put((Object)"keySessionMode", (Object)string4);
        return Kontagent.createSession(context, (HashMap<String, Object>)hashMap);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ISession createSession(Context context, HashMap<String, Object> hashMap) {
        Object object;
        Object object2;
        String string2;
        Object object3;
        String string3;
        ISession iSession;
        Object object4;
        String string4;
        String string5;
        if (hashMap == null) {
            KontagentLog.e("Session configuration can not be null!", null);
            return null;
        }
        if (!SessionConfiguration.validate(hashMap)) {
            return null;
        }
        Object object5 = hashMap.get((Object)"keySessionMode");
        String string6 = null;
        if (object5 != null) {
            string6 = (String)object5;
        }
        if (TextUtils.isEmpty(string6)) {
            hashMap.put((Object)"keySessionMode", (Object)PRODUCTION_MODE);
        }
        if ((object2 = hashMap.get((Object)"keySessionAPA")) == null) {
            hashMap.put((Object)"keySessionAPA", (Object)true);
        } else {
            hashMap.put((Object)"keySessionAPA", object2);
        }
        if (TEST_MODE.equals(hashMap.get((Object)"keySessionMode"))) {
            hashMap.put((Object)"keySessionDebug", (Object)true);
            Kontagent.enableDebug();
        }
        if ((iSession = (ISession)sdkSessions.get((Object)(string2 = (String)hashMap.get((Object)"keySessionApiKey")))) == null) {
            Session session = new Session(context, hashMap);
            sdkSessions.put((Object)string2, (Object)session);
            return session;
        }
        String string7 = (String)hashMap.get((Object)"keySessionSenderId");
        if (!TextUtils.isEmpty((CharSequence)string7)) {
            iSession.setSenderId(string7);
        }
        if (!TextUtils.isEmpty((CharSequence)(string3 = (String)hashMap.get((Object)"keySessionMode")))) {
            ((Session)iSession).setSdkMode(string3);
            String string8 = (String)hashMap.get((Object)"keySessionApiURL");
            Session session = (Session)iSession;
            if (TextUtils.isEmpty((CharSequence)string8)) {
                string8 = session.urlPrefix();
            }
            session.setApiUrlPrefix(string8);
        }
        if ((object3 = hashMap.get((Object)"keySessionAPA")) != null) {
            ((Session)iSession).setShouldSendApplicationAdded((Boolean)object3);
        }
        Object object6 = hashMap.get((Object)"keySessionDebug");
        boolean bl = false;
        if (object6 != null) {
            bl = (Boolean)object6;
        }
        if (bl) {
            Kontagent.enableDebug();
        } else {
            Kontagent.disableDebug();
        }
        if ((object4 = hashMap.get((Object)"keySessionApiKeyForTimezone")) != null) {
            ((Session)iSession).setApiKeyForTimezone((String)object4);
        }
        if ((object = hashMap.get((Object)"keySessionApiKeyTimezoneOffset")) != null) {
            try {
                ((Session)iSession).setApiKeyTimezoneOffset(Integer.valueOf((String)((String)object)));
            }
            catch (NumberFormatException numberFormatException) {
                KontagentLog.e(TAG, "Unable to convert apiKeyTimezoneOffset from String to Integer", numberFormatException);
            }
        }
        if ((string4 = (String)hashMap.get((Object)"keySessionFBAppId")) != null) {
            ((Session)iSession).setFbAppID(string4);
        }
        if ((string5 = (String)hashMap.get((Object)"keySessionServUrl")) == null) return iSession;
        ((Session)iSession).setExperimentalServerBaseUrl(string5);
        return iSession;
    }

    public static int currentMaxQueueSizeForSessionApiKey(String string2) {
        ISession iSession = (ISession)sdkSessions.get((Object)string2);
        if (iSession != null) {
            return iSession.currentMaxQueueSize();
        }
        Object[] arrobject = new Object[]{string2, 500};
        KontagentLog.w(String.format((String)"Failed to retrieve max queue size - there is no such session for apiKey=%s. Default MAX queue size=%s", (Object[])arrobject));
        return 500;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void customEvent(String string2, Map<String, String> map) {
        ISession iSession;
        if (!Kontagent.assertStarted("customEvent") || (iSession = Kontagent.getInstance().getSession()) == null) {
            return;
        }
        iSession.customEvent(string2, map);
    }

    public static Boolean debugEnabled() {
        return debug;
    }

    public static void disableDebug() {
        debug = false;
    }

    public static void enableDebug() {
        debug = true;
        KontagentLog.enable();
    }

    public static void enableExperimentalFeatures() {
        experimental = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String generateShortUniqueTrackingTag() {
        ISession iSession;
        if (!Kontagent.assertStarted("generateShortUniqueTrackingTag") || (iSession = Kontagent.getInstance().getSession()) == null) {
            return null;
        }
        iSession.generateShortUniqueTrackingTag();
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String generateUniqueTrackingTag() {
        ISession iSession;
        if (!Kontagent.assertStarted("generateUniqueTrackingTag") || (iSession = Kontagent.getInstance().getSession()) == null) {
            return null;
        }
        return iSession.generateUniqueTrackingTag();
    }

    public static Kontagent getInstance() {
        return InstanceHolder.instance;
    }

    public static String getSenderId() {
        return Kontagent.getSenderId(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String getSenderId(String string2) {
        if (!Kontagent.assertStarted("get sender id")) {
            return null;
        }
        ISession iSession = string2 != null ? (ISession)sdkSessions.get((Object)string2) : Kontagent.getInstance().getSession();
        if (iSession != null) {
            return iSession.getSenderId();
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void heartbeat() {
        ISession iSession;
        if (!Kontagent.assertStarted("heartbeat") || (iSession = Kontagent.getInstance().getSession()) == null) {
            return;
        }
        iSession.heartbeat();
    }

    public static boolean isExperimental() {
        return experimental;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String libraryVersion() {
        boolean bl;
        bl = false;
        try {
            Class.forName((String)"com.kontagent.UnityStubClass");
            bl = true;
            KontagentLog.i("Kontagent SDK is built for Unity3D plugin");
        }
        catch (ClassNotFoundException classNotFoundException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("a");
        if (bl) {
            stringBuilder.append("u");
        }
        stringBuilder.append(API_VERSION.substring(1));
        return stringBuilder.toString();
    }

    public static void pauseSession() {
        ISession iSession = Kontagent.getInstance().getSession();
        if (iSession != null) {
            iSession.pause();
        }
    }

    public static void resumeSession() {
        ISession iSession = Kontagent.getInstance().getSession();
        if (iSession != null) {
            iSession.resume();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void revenueTracking(Integer n, Map<String, String> map) {
        ISession iSession;
        if (!Kontagent.assertStarted("revenueTracking") || (iSession = Kontagent.getInstance().getSession()) == null) {
            return;
        }
        iSession.revenueTracking(n, map);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void sendDeviceInformation(Map<String, String> map) {
        ISession iSession;
        if (!Kontagent.assertStarted("sendDeviceInformation") || (iSession = Kontagent.getInstance().getSession()) == null) {
            return;
        }
        iSession.sendDeviceInformation(map);
    }

    public static void setSenderId(String string2) {
        Kontagent.setSenderId(string2, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void setSenderId(String string2, String string3) {
        ISession iSession;
        if (!Kontagent.assertStarted("setSenderId") || (iSession = string3 != null ? (ISession)sdkSessions.get((Object)string3) : Kontagent.getInstance().getSession()) == null) {
            return;
        }
        iSession.setSenderId(string2);
    }

    public static void startSession(Context context, HashMap<String, Object> hashMap) {
        ISession iSession = Kontagent.createSession(context, hashMap);
        if (iSession != null) {
            Kontagent.getInstance().setSession(iSession);
            iSession.start();
        }
    }

    public static void startSession(String string2, Context context, String string3) {
        Kontagent.startSession(string2, context, string3, null);
    }

    public static void startSession(String string2, Context context, String string3, String string4) {
        Kontagent.startSession(string2, context, string3, string4, true);
    }

    public static void startSession(String string2, Context context, String string3, String string4, boolean bl) {
        Kontagent.startSession(string2, context, string3, string4, bl, null, null);
    }

    public static void startSession(String string2, Context context, String string3, String string4, boolean bl, String string5, String string6) {
        Kontagent.startSession(string2, context, string3, string4, bl, string5, string6, null);
    }

    public static void startSession(String string2, Context context, String string3, String string4, boolean bl, String string5, String string6, String string7) {
        HashMap hashMap = new HashMap();
        if (string2 != null) {
            hashMap.put((Object)"keySessionApiKey", (Object)string2);
        }
        if (string4 != null) {
            hashMap.put((Object)"keySessionSenderId", (Object)string4);
        }
        if (bl) {
            hashMap.put((Object)"keySessionAPA", (Object)bl);
        }
        if (string3 != null) {
            hashMap.put((Object)"keySessionMode", (Object)string3);
        }
        if (string5 != null) {
            hashMap.put((Object)"keySessionApiKeyForTimezone", (Object)string5);
        }
        if (string6 != null) {
            hashMap.put((Object)"keySessionApiKeyTimezoneOffset", (Object)string6);
        }
        if (string7 != null) {
            hashMap.put((Object)"keySessionFBAppId", (Object)string7);
        }
        Kontagent.startSession(context, (HashMap<String, Object>)hashMap);
    }

    public static void startSession(String string2, Context context, String string3, boolean bl) {
        Kontagent.startSession(string2, context, string3, null, bl);
    }

    public static void stopSession() {
        Kontagent kontagent = Kontagent.getInstance();
        ISession iSession = kontagent.getSession();
        if (iSession != null) {
            iSession.stop();
            for (Map.Entry entry : sdkSessions.entrySet()) {
                ISession iSession2 = (ISession)entry.getValue();
                if (iSession2 == null || iSession2 != iSession) continue;
                sdkSessions.remove(entry.getKey());
            }
            kontagent.setSession(null);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void undirectedCommunicationClick(boolean bl, String string2, Map<String, String> map) {
        ISession iSession;
        if (!Kontagent.assertStarted("undirectedCommunicationClick") || (iSession = Kontagent.getInstance().getSession()) == null) {
            return;
        }
        iSession.undirectedCommunicationClick(bl, string2, map);
    }

    public ISession getSession() {
        return this.session;
    }

    void setSession(ISession iSession) {
        this.session = iSession;
    }

    private static class InstanceHolder {
        public static final Kontagent instance = new Kontagent(null);

        private InstanceHolder() {
        }
    }

}


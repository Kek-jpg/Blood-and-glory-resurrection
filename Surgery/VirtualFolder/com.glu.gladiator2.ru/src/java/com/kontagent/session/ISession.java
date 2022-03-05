/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.kontagent.session;

import java.util.Map;

public interface ISession {
    public static final String EXPERIMENTAL_SERVER_URL = "http://src1.mobile.kontagent.com/api/v1/";
    public static final String KONTAGENT_SDK_MODE_PRODUCTION = "production";
    public static final String KONTAGENT_SDK_MODE_TEST = "test";
    public static final String SESSION_APA_CONFIG_KEY = "keySessionAPA";
    public static final String SESSION_API_KEY_CONFIG_KEY = "keySessionApiKey";
    public static final String SESSION_API_KEY_FOR_TIMEZONE_CONFIG_KEY = "keySessionApiKeyForTimezone";
    public static final String SESSION_API_KEY_TIMEZONE_OFFSET_CONFIG_KEY = "keySessionApiKeyTimezoneOffset";
    public static final String SESSION_API_URL_CONFIG_KEY = "keySessionApiURL";
    public static final String SESSION_DEBUG_CONFIG_KEY = "keySessionDebug";
    public static final String SESSION_FB_APP_ID_CONFIG_KEY = "keySessionFBAppId";
    public static final String SESSION_FB_EXPTAL_SERV_URL_CONFIG_KEY = "keySessionServUrl";
    public static final String SESSION_MODE_CONFIG_KEY = "keySessionMode";
    public static final String SESSION_SENDER_ID_CONFIG_KEY = "keySessionSenderId";

    public void applicationAdded(Map<String, String> var1);

    public void changeMaxQueueSize(int var1);

    public int currentMaxQueueSize();

    public void customEvent(String var1, Map<String, String> var2);

    public String generateShortUniqueTrackingTag();

    public String generateUniqueTrackingTag();

    public String getSenderId();

    public void heartbeat();

    public boolean isStarted();

    public boolean pause();

    public int queueSize();

    public boolean resume();

    public void revenueTracking(Integer var1, Map<String, String> var2);

    public void sendDeviceInformation(Map<String, String> var1);

    public void setSenderId(String var1);

    public boolean start();

    public void stop();

    public void undirectedCommunicationClick(boolean var1, String var2, Map<String, String> var3);
}


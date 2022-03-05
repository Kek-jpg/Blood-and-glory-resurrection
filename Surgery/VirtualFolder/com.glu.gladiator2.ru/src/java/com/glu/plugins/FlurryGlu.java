/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.telephony.TelephonyManager
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.reflect.Field
 *  java.util.HashMap
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.glu.plugins;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.flurry.android.FlurryAgent;
import com.glu.plugins.AStats;
import com.unity3d.player.UnityPlayer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class FlurryGlu {
    private static final String SHAREDPREF_KEY_ACTIVEKEY = "ACTIVE_KEY";
    private static final String SHAREDPREF_KEY_NEWUSER = "NEWUSER";
    private static final String SHAREDPREF_KEY_RUNS = "RUNS";
    private static final String SHAREDPREF_KEY_USER = "USER_ID";
    private static final String SHAREDPREF_NAME = "aflurry";
    private static boolean appStarted = false;
    private static boolean didInit = false;
    private static Map<String, String> extras;
    private static long startTime;

    public static void EndSession() {
        AStats.Log("Flurry.EndSession()");
        if (!didInit) {
            return;
        }
        String[] arrstring = new String[]{"length", Long.toString((long)((System.currentTimeMillis() - startTime) / 1000L))};
        FlurryGlu.LogEvent("SessionStop", arrstring);
        FlurryAgent.onEndSession((Context)UnityPlayer.currentActivity);
        SharedPreferences.Editor editor = UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0).edit();
        editor.remove(SHAREDPREF_KEY_ACTIVEKEY);
        editor.commit();
    }

    public static void EndTimedEvent(String string2) {
        AStats.Log("Flurry.EndTimedEvent( " + string2 + " )");
        if (!didInit) {
            return;
        }
        FlurryAgent.endTimedEvent(string2);
    }

    private static void InitializeExtras() {
        AStats.Log("Flurry.InitializeExtras()");
        extras = new HashMap();
        Locale locale = Locale.getDefault();
        extras.put((Object)"language", (Object)locale.getLanguage());
        AStats.Log("InitializeExtras() added language: " + locale.getLanguage());
        extras.put((Object)"country", (Object)locale.getCountry());
        AStats.Log("InitializeExtras() added country: " + locale.getCountry());
        extras.put((Object)"os", (Object)("Android " + Build.VERSION.RELEASE));
        AStats.Log("InitializeExtras() added os: " + Build.VERSION.RELEASE);
    }

    public static void LogEvent(String string2) {
        AStats.Log("Flurry.LogEvent( " + string2 + " )");
        FlurryGlu.LogEvent(string2, (Map<String, String>)null, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void LogEvent(String string2, Map<String, String> hashMap, boolean bl) {
        AStats.Log("Flurry.LogEvent( " + string2 + ", map, " + bl + " )");
        if (!didInit) {
            return;
        }
        if (AStats.DEBUG && !UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0).contains(SHAREDPREF_KEY_ACTIVEKEY)) {
            AStats.LogError("***********************************************************");
            AStats.LogError("***********************************************************");
            AStats.LogError("***********************************************************");
            AStats.LogError("**********                WARNING                **********");
            AStats.LogError("***********************************************************");
            AStats.LogError("***********************************************************");
            AStats.LogError("***********************************************************");
            AStats.LogError("LogEvent called while a session is not running. This event");
            AStats.LogError("will be lost.");
            AStats.LogError("***********************************************************");
            AStats.LogError("***********************************************************");
            AStats.LogError("***********************************************************");
            return;
        }
        if (AStats.DEBUG && hashMap != null && hashMap.size() > 0) {
            AStats.Log("Parameters:");
            for (Map.Entry entry : hashMap.entrySet()) {
                AStats.Log("[" + (String)entry.getKey() + "] " + (String)entry.getValue());
            }
        }
        if (extras == null) {
            FlurryGlu.InitializeExtras();
        }
        if (hashMap == null) {
            hashMap = new HashMap(extras);
        } else {
            hashMap.putAll(extras);
        }
        FlurryAgent.logEvent(string2, (Map<String, String>)hashMap, bl);
    }

    public static void LogEvent(String string2, boolean bl) {
        AStats.Log("Flurry.LogEvent( " + string2 + ", " + bl + " )");
        FlurryGlu.LogEvent(string2, (Map<String, String>)null, bl);
    }

    public static void LogEvent(String string2, String[] arrstring) {
        AStats.Log("Flurry.LogEvent( " + string2 + ", params[] )");
        FlurryGlu.LogEvent(string2, arrstring, false);
    }

    public static void LogEvent(String string2, String[] arrstring, boolean bl) {
        AStats.Log("Flurry.LogEvent( " + string2 + ", params[], " + bl + " )");
        if (AStats.DEBUG && arrstring != null && arrstring.length % 2 != 0) {
            AStats.LogError("Error - params has an odd length when it should be even (key/value pairs), last key will be ignored.");
        }
        HashMap hashMap = new HashMap();
        if (arrstring != null) {
            for (int i2 = 0; i2 < -1 + arrstring.length; i2 += 2) {
                hashMap.put((Object)arrstring[i2], (Object)arrstring[i2 + 1]);
            }
        }
        FlurryGlu.LogEvent(string2, (Map<String, String>)hashMap, bl);
    }

    public static void SetExtras(boolean bl, String[] arrstring) {
        AStats.Log("Flurry.SetExtras( params[] )");
        if (AStats.DEBUG && arrstring != null && arrstring.length % 2 != 0) {
            AStats.LogError("Error - params has an odd length when it should be even (key/value pairs), last key will be ignored.");
        }
        if (extras == null) {
            FlurryGlu.InitializeExtras();
        }
        if (arrstring != null) {
            for (int i2 = 0; i2 < -1 + arrstring.length; i2 += 2) {
                AStats.Log("params " + arrstring[i2] + ": " + arrstring[i2 + 1]);
                extras.put((Object)arrstring[i2], (Object)arrstring[i2 + 1]);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void StartSession(String string2) {
        String string3;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        block19 : {
            block20 : {
                String string4;
                AStats.Log("Flurry.StartSession( " + string2 + " )");
                if (string2 == null || string2.equals((Object)"")) {
                    AStats.LogError("***********************************************************");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("**********                WARNING                **********");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("Flurry is disabled, because no keys were passed in.");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("***********************************************************");
                    return;
                }
                startTime = System.currentTimeMillis();
                FlurryAgent.setLogEnabled(AStats.DEBUG);
                FlurryAgent.onStartSession((Context)UnityPlayer.currentActivity, string2);
                didInit = true;
                if (AStats.DEBUG && UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0).contains(SHAREDPREF_KEY_ACTIVEKEY)) {
                    AStats.LogError("***********************************************************");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("**********                WARNING                **********");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("StartSession called twice with no EndSession. Make sure to");
                    AStats.LogError("end all sessions.");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("***********************************************************");
                    AStats.LogError("***********************************************************");
                    return;
                }
                sharedPreferences = UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0);
                editor = sharedPreferences.edit();
                editor.putString(SHAREDPREF_KEY_ACTIVEKEY, string2);
                editor.commit();
                string3 = sharedPreferences.getString(SHAREDPREF_KEY_USER, null);
                if (string3 != null) break block19;
                if (string3 != null) break block20;
                try {
                    String string5;
                    TelephonyManager telephonyManager = (TelephonyManager)UnityPlayer.currentActivity.getSystemService("phone");
                    string4 = telephonyManager != null ? (string5 = telephonyManager.getDeviceId()) : string3;
                }
                catch (Exception exception) {}
                string3 = string4;
                if (string3 != null) {
                    AStats.Log("Got Telephony User ID: " + string3);
                }
            }
            if (string3 == null) {
                try {
                    String string6;
                    Class class_ = Class.forName((String)"android.os.Build");
                    string3 = string6 = (String)class_.getDeclaredField("SERIAL").get((Object)class_);
                }
                catch (Exception exception) {}
                if (string3 != null) {
                    AStats.Log("Got Serial User ID: " + string3);
                }
            }
            if (string3 == null) {
                try {
                    String string7;
                    string3 = string7 = Settings.Secure.getString((ContentResolver)UnityPlayer.currentActivity.getContentResolver(), (String)"android_id");
                }
                catch (Exception exception) {}
                if (string3 != null) {
                    AStats.Log("Got Android ID: " + string3);
                }
            }
            if (string3 == null) {
                string3 = "UNKNOWN";
                AStats.Log("Could not determine userID");
            } else {
                editor.putString(SHAREDPREF_KEY_USER, string3);
                editor.commit();
            }
        }
        FlurryAgent.setUserId(string3);
        if (!appStarted) {
            if (sharedPreferences.getBoolean(SHAREDPREF_KEY_NEWUSER, true)) {
                FlurryGlu.LogEvent("NewUser");
                editor.putBoolean(SHAREDPREF_KEY_NEWUSER, false);
                editor.commit();
            }
            int n = sharedPreferences.getInt(SHAREDPREF_KEY_RUNS, 0);
            String[] arrstring = new String[]{"count", Integer.toString((int)n)};
            editor.putInt(SHAREDPREF_KEY_RUNS, n + 1);
            editor.commit();
            FlurryGlu.LogEvent("AppStart", arrstring);
            appStarted = true;
        }
        FlurryGlu.LogEvent("SessionStart");
    }
}


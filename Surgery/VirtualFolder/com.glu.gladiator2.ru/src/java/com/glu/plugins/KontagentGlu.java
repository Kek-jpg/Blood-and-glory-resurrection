/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Arrays
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.glu.plugins;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.glu.plugins.AStats;
import com.glu.plugins.astats.Base64;
import com.kontagent.Kontagent;
import com.unity3d.player.UnityPlayer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KontagentGlu {
    private static boolean appStarted = false;
    private static boolean didInit;
    private static List<String> whitelist;

    static {
        whitelist = Arrays.asList((Object[])new String[]{"st1", "st2", "st3", "v", "l", "data", "tu", "n", "s"});
        didInit = false;
    }

    public static void EndSession() {
        AStats.Log("Kontagent.EndSession()");
        if (!didInit) {
            return;
        }
        Kontagent.stopSession();
    }

    public static void LogEvent(String string2) {
        AStats.Log("Kontagent.LogEvent( " + string2 + " )");
        KontagentGlu.LogEvent(string2, (Map<String, String>)null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void LogEvent(String string2, Map<String, String> map) {
        AStats.Log("Kontagent.LogEvent( " + string2 + ", map )");
        if (!didInit) {
            return;
        }
        if (AStats.DEBUG && map != null && map.size() > 0) {
            AStats.Log("Parameters:");
            for (Map.Entry entry : map.entrySet()) {
                AStats.Log("[" + (String)entry.getKey() + "] " + (String)entry.getValue());
            }
        }
        if (map != null && map.size() > 0) {
            String string3 = "{";
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                if (whitelist.contains(entry.getKey())) continue;
                String string4 = !string3.equals((Object)"{") ? string3 + "," : string3;
                string3 = string4 + "\"" + (String)entry.getKey() + "\":\"" + (String)entry.getValue() + "\"";
                iterator.remove();
            }
            String string5 = string3 + "}";
            if (!string5.equals((Object)"{}")) {
                AStats.Log("Generated data argument");
                AStats.Log("[data] " + string5);
                map.put((Object)"data", (Object)string5);
            }
            if (map.containsKey((Object)"data") && map.get((Object)"data") != null && ((String)map.get((Object)"data")).startsWith("{")) {
                byte[] arrby;
                AStats.Log("Base64 Encoding data");
                try {
                    byte[] arrby2;
                    arrby = arrby2 = ((String)map.get((Object)"data")).getBytes("UTF-8");
                }
                catch (Exception exception) {
                    if (AStats.DEBUG) {
                        exception.printStackTrace();
                    }
                    arrby = null;
                }
                String string6 = Base64.encode(arrby);
                map.put((Object)"data", (Object)string6);
                AStats.Log("[data] " + string6);
            }
        }
        Kontagent.customEvent(string2, map);
    }

    public static void LogEvent(String string2, String[] arrstring) {
        AStats.Log("Kontagent.LogEvent( " + string2 + ", params[] )");
        if (AStats.DEBUG && arrstring != null && arrstring.length % 2 != 0) {
            AStats.LogError("Error - params has an odd length when it should be even (key/value pairs), last key will be ignored.");
        }
        HashMap hashMap = new HashMap();
        if (arrstring != null) {
            for (int i2 = 0; i2 < -1 + arrstring.length; i2 += 2) {
                hashMap.put((Object)arrstring[i2], (Object)arrstring[i2 + 1]);
            }
        }
        KontagentGlu.LogEvent(string2, (Map<String, String>)hashMap);
    }

    public static void RevenueTracking(int n) {
        AStats.Log("Kontagent.RevenueTracking( " + n + " )");
        KontagentGlu.RevenueTracking(n, (Map<String, String>)null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void RevenueTracking(int n, Map<String, String> map) {
        AStats.Log("Kontagent.RevenueTracking( " + n + ", map )");
        if (!didInit) {
            return;
        }
        if (AStats.DEBUG && map != null && map.size() > 0) {
            AStats.Log("Parameters:");
            for (Map.Entry entry : map.entrySet()) {
                AStats.Log("[" + (String)entry.getKey() + "] " + (String)entry.getValue());
            }
        }
        if (map != null && map.size() > 0) {
            String string2 = "{";
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                if (whitelist.contains(entry.getKey())) continue;
                String string3 = !string2.equals((Object)"{") ? string2 + "," : string2;
                string2 = string3 + "\"" + (String)entry.getKey() + "\":\"" + (String)entry.getValue() + "\"";
                iterator.remove();
            }
            String string4 = string2 + "}";
            if (!string4.equals((Object)"{}")) {
                AStats.Log("Generated data argument");
                AStats.Log("[data] " + string4);
                map.put((Object)"data", (Object)string4);
            }
            if (((String)map.get((Object)"data")).startsWith("{")) {
                byte[] arrby;
                AStats.Log("Base64 Encoding data");
                try {
                    byte[] arrby2;
                    arrby = arrby2 = ((String)map.get((Object)"data")).getBytes("UTF-8");
                }
                catch (Exception exception) {
                    if (AStats.DEBUG) {
                        exception.printStackTrace();
                    }
                    arrby = null;
                }
                String string5 = Base64.encode(arrby);
                map.put((Object)"data", (Object)string5);
                AStats.Log("[data] " + string5);
            }
        }
        Kontagent.revenueTracking(n, map);
    }

    public static void RevenueTracking(int n, String[] arrstring) {
        AStats.Log("Kontagent.RevenueTracking( " + n + ", params[] )");
        if (AStats.DEBUG && arrstring != null && arrstring.length % 2 != 0) {
            AStats.LogError("Error - params has an odd length when it should be even (key/value pairs), last key will be ignored.");
        }
        HashMap hashMap = new HashMap();
        if (arrstring != null) {
            for (int i2 = 0; i2 < -1 + arrstring.length; i2 += 2) {
                hashMap.put((Object)arrstring[i2], (Object)arrstring[i2 + 1]);
            }
        }
        KontagentGlu.RevenueTracking(n, (Map<String, String>)hashMap);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void StartSession(String string2) {
        block4 : {
            AStats.Log("Kontagent.StartSession( " + string2 + " )");
            if (string2 == null || string2.equals((Object)"")) {
                AStats.LogError("***********************************************************");
                AStats.LogError("***********************************************************");
                AStats.LogError("***********************************************************");
                AStats.LogError("**********                WARNING                **********");
                AStats.LogError("***********************************************************");
                AStats.LogError("***********************************************************");
                AStats.LogError("***********************************************************");
                AStats.LogError("Kontagent is disabled, because no keys were passed in.");
                AStats.LogError("***********************************************************");
                AStats.LogError("***********************************************************");
                AStats.LogError("***********************************************************");
                return;
            }
            if (AStats.DEBUG) {
                Kontagent.enableDebug();
            }
            Kontagent.startSession(string2, (Context)UnityPlayer.currentActivity, "production");
            didInit = true;
            if (appStarted) return;
            try {
                HashMap hashMap = new HashMap();
                hashMap.put((Object)"v_maj", (Object)UnityPlayer.currentActivity.getPackageManager().getPackageInfo((String)UnityPlayer.currentActivity.getPackageName(), (int)0).versionName);
                Kontagent.sendDeviceInformation((Map<String, String>)hashMap);
            }
            catch (Exception exception) {
                if (!AStats.DEBUG) break block4;
                exception.printStackTrace();
            }
        }
        appStarted = true;
    }
}


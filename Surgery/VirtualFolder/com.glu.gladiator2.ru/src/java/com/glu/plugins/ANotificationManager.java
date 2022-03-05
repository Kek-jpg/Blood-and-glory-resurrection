/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlarmManager
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Environment
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.util.Log
 *  java.io.BufferedReader
 *  java.io.File
 *  java.io.FileReader
 *  java.io.Reader
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package com.glu.plugins;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import com.glu.plugins.NotificationReceiver;
import com.unity3d.player.UnityPlayer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class ANotificationManager {
    private static boolean DEBUG = false;
    private static final long DEBUG_SERVER_CHECK_FREQUENCY = 60000L;
    private static final String GLUDEBUG_KEY_URL = "ANM_SERVER_URL = ";
    private static final long SERVER_CHECK_FREQUENCY = 3600000L;
    private static final String SHAREDPREF_KEY_DEBUG = "DEBUG";
    private static final String SHAREDPREF_KEY_ENABLED = "ENABLED";
    private static final String SHAREDPREF_KEY_GAMESKU = "GAMESKU";
    private static final String SHAREDPREF_KEY_LOG = "LOG";
    private static final String SHAREDPREF_KEY_SERVERURL = "SERVERURL";
    private static final String SHAREDPREF_NAME = "anm";
    private static final String VERSION = "1.2.1";
    private static SharedPreferences sprefs;

    static {
        DEBUG = false;
    }

    public static void ClearActiveNotifications() {
        ANotificationManager.Log("ClearActiveNotifications()");
        try {
            Activity activity = UnityPlayer.currentActivity;
            ((NotificationManager)activity.getSystemService("notification")).cancelAll();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void ClearScheduledNotifications() {
        int n = 0;
        ANotificationManager.Log("ClearScheduledNotifications()");
        try {
            if (sprefs == null) {
                sprefs = UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0);
            }
            Intent intent = new Intent((Context)UnityPlayer.currentActivity, NotificationReceiver.class);
            AlarmManager alarmManager = (AlarmManager)UnityPlayer.currentActivity.getSystemService("alarm");
            String string2 = sprefs.getString(SHAREDPREF_KEY_LOG, null);
            if (string2 != null) {
                String[] arrstring = string2.split(";");
                while (n < arrstring.length) {
                    int n2 = arrstring[n].indexOf("|");
                    long l = Long.parseLong((String)arrstring[n].substring(0, n2));
                    alarmManager.cancel(PendingIntent.getBroadcast((Context)UnityPlayer.currentActivity, (int)((int)l), (Intent)intent, (int)134217728));
                    ++n;
                }
                SharedPreferences.Editor editor = sprefs.edit();
                editor.remove(SHAREDPREF_KEY_LOG);
                editor.commit();
            }
            Intent intent2 = new Intent((Context)UnityPlayer.currentActivity, NotificationReceiver.class);
            intent2.putExtra("message", "servercheck");
            PendingIntent pendingIntent = PendingIntent.getBroadcast((Context)UnityPlayer.currentActivity, (int)0, (Intent)intent2, (int)134217728);
            long l = 1000L + System.currentTimeMillis();
            long l2 = DEBUG ? 60000L : 3600000L;
            alarmManager.setInexactRepeating(0, l, l2, pendingIntent);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static void Init(boolean bl, boolean bl2, String string2, String string3) {
        if (sprefs == null) {
            sprefs = UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0);
        }
        if (bl2 || new File(Environment.getExternalStorageDirectory().toString() + "/.gludebug").exists()) {
            DEBUG = true;
            SharedPreferences.Editor editor = sprefs.edit();
            editor.putBoolean(SHAREDPREF_KEY_DEBUG, DEBUG);
            editor.commit();
        }
        ANotificationManager.Log("Init( " + bl + ", " + bl2 + ", " + string2 + ", " + string3 + " )");
        ANotificationManager.Log("ANotificationManager Version: 1.2.1");
        ANotificationManager.Log("ANDROID_ID: " + Settings.Secure.getString((ContentResolver)UnityPlayer.currentActivity.getContentResolver(), (String)"android_id"));
        ANotificationManager.ToggleEnabled(bl);
        ANotificationManager.ClearScheduledNotifications();
        ANotificationManager.ClearActiveNotifications();
        ANotificationManager.RegisterServerURL(string2);
        if (string3 != null || string3.equals((Object)"")) {
            SharedPreferences.Editor editor = sprefs.edit();
            editor.putString(SHAREDPREF_KEY_GAMESKU, string3);
            editor.commit();
        }
    }

    public static boolean IsEnabled() {
        if (sprefs == null) {
            sprefs = UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0);
        }
        boolean bl = sprefs.getBoolean(SHAREDPREF_KEY_ENABLED, false);
        ANotificationManager.Log("IsEnabled(): " + bl);
        return bl;
    }

    private static void Log(String string2) {
        if (DEBUG) {
            Log.d((String)"ANotificationManager", (String)string2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void RegisterServerURL(String string2) {
        block6 : {
            ANotificationManager.Log("RegisterServerURL( " + string2 + " )");
            if (!DEBUG) break block6;
            try {
                File file = new File(Environment.getExternalStorageDirectory().toString() + "/.gludebug");
                if (!file.exists() || !file.isFile()) break block6;
                BufferedReader bufferedReader = new BufferedReader((Reader)new FileReader(file));
                String string3 = bufferedReader.readLine();
                do {
                    String string4;
                    block8 : {
                        block7 : {
                            if (string3 == null) break block7;
                            if (!string3.startsWith(GLUDEBUG_KEY_URL)) break block8;
                            string2 = string3.substring(GLUDEBUG_KEY_URL.length());
                            ANotificationManager.Log("RegisterServerURL .gludebug overwriting URL with: " + string2);
                        }
                        bufferedReader.close();
                        break;
                    }
                    string3 = string4 = bufferedReader.readLine();
                } while (true);
            }
            catch (Exception exception) {}
        }
        if (!ANotificationManager.IsEnabled() || string2 == null || string2.equals((Object)"")) {
            return;
        }
        SharedPreferences.Editor editor = sprefs.edit();
        editor.putString(SHAREDPREF_KEY_SERVERURL, string2);
        editor.commit();
        Intent intent = new Intent((Context)UnityPlayer.currentActivity, NotificationReceiver.class);
        intent.putExtra("message", "servercheck");
        PendingIntent pendingIntent = PendingIntent.getBroadcast((Context)UnityPlayer.currentActivity, (int)0, (Intent)intent, (int)134217728);
        AlarmManager alarmManager = (AlarmManager)UnityPlayer.currentActivity.getSystemService("alarm");
        long l = 1000L + System.currentTimeMillis();
        long l2 = DEBUG ? 60000L : 3600000L;
        alarmManager.setInexactRepeating(0, l, l2, pendingIntent);
    }

    public static void ScheduleNotificationMillisFromEpoch(long l, String string2, String string3) {
        ANotificationManager.Log("ScheduleNotificationMillisFromEpoch( " + l + ", " + string2 + ", " + string3 + " )");
        if (!ANotificationManager.IsEnabled()) {
            return;
        }
        Intent intent = new Intent((Context)UnityPlayer.currentActivity, NotificationReceiver.class);
        intent.putExtra("time", l);
        intent.putExtra("message", string2);
        intent.putExtra("url", string3);
        intent.putExtra("fromserver", false);
        PendingIntent pendingIntent = PendingIntent.getBroadcast((Context)UnityPlayer.currentActivity, (int)((int)l), (Intent)intent, (int)134217728);
        ((AlarmManager)UnityPlayer.currentActivity.getSystemService("alarm")).set(0, l, pendingIntent);
        String string4 = sprefs.getString(SHAREDPREF_KEY_LOG, "");
        String string5 = string4 + l + "|" + string2 + "|" + string3 + "|false" + ";";
        SharedPreferences.Editor editor = sprefs.edit();
        editor.putString(SHAREDPREF_KEY_LOG, string5);
        editor.commit();
    }

    public static void ScheduleNotificationSecFromNow(int n, String string2, String string3) {
        ANotificationManager.Log("ScheduleNotificationSecFromNow( " + n + ", " + string2 + ", " + string3 + " )");
        ANotificationManager.ScheduleNotificationMillisFromEpoch(System.currentTimeMillis() + (long)(n * 1000), string2, string3);
    }

    public static void ToggleEnabled(boolean bl) {
        ANotificationManager.Log("ToggleEnabled(" + bl + ")");
        if (sprefs == null) {
            sprefs = UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0);
        }
        SharedPreferences.Editor editor = sprefs.edit();
        editor.putBoolean(SHAREDPREF_KEY_ENABLED, bl);
        editor.commit();
        if (!bl) {
            ANotificationManager.ClearScheduledNotifications();
        }
    }

    public static void UnregisterServerURL() {
        ANotificationManager.Log("UnregisterServerURL()");
        if (sprefs == null) {
            sprefs = UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0);
        }
        SharedPreferences.Editor editor = sprefs.edit();
        editor.remove(SHAREDPREF_KEY_SERVERURL);
        editor.commit();
    }
}


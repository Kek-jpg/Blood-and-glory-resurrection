/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Environment
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.util.Log
 *  java.io.BufferedReader
 *  java.io.File
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Thread
 *  java.net.URL
 *  java.text.SimpleDateFormat
 *  java.util.Calendar
 *  java.util.Date
 *  java.util.HashMap
 *  java.util.Locale
 *  java.util.Map
 *  java.util.TimeZone
 */
package com.glu.plugins;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import com.flurry.android.FlurryAgent;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class NotificationReceiver
extends BroadcastReceiver {
    private static boolean DEBUG = false;
    private static final String DEBUG_FLURRY_KEY = "CHKYUEPTKCQI8HLGL475";
    private static final long DEBUG_SERVER_CHECK_FREQUENCY = 60000L;
    private static final String FLURRY_KEY = "VL8EJNTDTAPSTST8QTUU";
    private static final long SERVER_CHECK_FREQUENCY = 3600000L;
    private static String SERVER_NOTIFICATION_URL = null;
    private static final String SHAREDPREF_FLURRY_KEY_ACTIVEKEY = "ACTIVE_KEY";
    private static final String SHAREDPREF_FLURRY_NAME = "aflurry";
    private static final String SHAREDPREF_KEY_DEBUG = "DEBUG";
    private static final String SHAREDPREF_KEY_ENABLED = "ENABLED";
    private static final String SHAREDPREF_KEY_GAMESKU = "GAMESKU";
    private static final String SHAREDPREF_KEY_LOG = "LOG";
    private static final String SHAREDPREF_KEY_SERVERURL = "SERVERURL";
    private static final String SHAREDPREF_NAME = "anm";
    private static SharedPreferences sprefs;

    static {
        DEBUG = false;
    }

    private static void Log(String string2) {
        if (DEBUG) {
            Log.d((String)"ANotificationManager", (String)string2);
        }
    }

    static /* synthetic */ String access$000() {
        return SERVER_NOTIFICATION_URL;
    }

    static /* synthetic */ void access$100(String string2) {
        NotificationReceiver.Log(string2);
    }

    static /* synthetic */ SharedPreferences access$200() {
        return sprefs;
    }

    static /* synthetic */ boolean access$300() {
        return DEBUG;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void displayNotification(Context context, long l, String string2, String string3, boolean bl) {
        String string4;
        String string5;
        int n;
        String string6;
        int n2;
        n2 = 0;
        NotificationReceiver.Log("NotificationReceiver displayNotification( " + l + ", " + string2 + ", " + string3 + ", " + bl + " )");
        try {
            int n3;
            n = n3 = context.getResources().getIdentifier("app_icon", "drawable", context.getPackageName());
        }
        catch (Exception exception) {
            n = 0;
        }
        string5 = "";
        try {
            String string7;
            string5 = string7 = context.getString(context.getResources().getIdentifier("app_name", "string", context.getPackageName()));
        }
        catch (Exception exception) {}
        Notification notification = new Notification(n, (CharSequence)string2, l);
        PendingIntent pendingIntent = string3 != null && !string3.equals((Object)"") ? PendingIntent.getActivity((Context)context, (int)0, (Intent)new Intent("android.intent.action.VIEW", Uri.parse((String)string3)), (int)0) : PendingIntent.getActivity((Context)context, (int)0, (Intent)context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()), (int)0);
        notification.setLatestEventInfo(context, (CharSequence)string5, (CharSequence)string2, pendingIntent);
        notification.defaults = -1;
        notification.flags = 16;
        boolean bl2 = context.getSharedPreferences(SHAREDPREF_FLURRY_NAME, 0).contains(SHAREDPREF_FLURRY_KEY_ACTIVEKEY);
        if (bl || !bl2) {
            ((NotificationManager)context.getSystemService("notification")).notify((int)l, notification);
        }
        if (bl && (string4 = sprefs.getString(SHAREDPREF_KEY_GAMESKU, null)) != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(l);
            calendar.setTimeZone(TimeZone.getTimeZone((String)"America/Los_Angeles"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy:HH:mm:sszzz", Locale.ENGLISH);
            String string8 = simpleDateFormat.format(calendar.getTime()) + ": " + string2;
            NotificationReceiver.Log("Flurry Event: " + string4 + "_SERVER_NOTIFICATION_DISPLAYED [info," + string8 + "]");
            if (DEBUG) {
                FlurryAgent.setLogEnabled(true);
            }
            if (!bl2) {
                NotificationReceiver.Log("Flurry was not running - starting new session");
                String string9 = DEBUG ? DEBUG_FLURRY_KEY : FLURRY_KEY;
                FlurryAgent.onStartSession(context, string9);
            }
            HashMap hashMap = new HashMap();
            hashMap.put((Object)"info", (Object)string8);
            FlurryAgent.logEvent(string4 + "_SERVER_NOTIFICATION_DISPLAYED", (Map<String, String>)hashMap);
            if (!bl2) {
                NotificationReceiver.Log("Flurry was not running - ending session");
                FlurryAgent.onEndSession(context);
            }
        }
        if ((string6 = sprefs.getString(SHAREDPREF_KEY_LOG, null)) != null) {
            String string10 = "";
            String[] arrstring = string6.split(";");
            while (n2 < arrstring.length) {
                if (!arrstring[n2].startsWith(String.valueOf((long)l))) {
                    string10 = string10 + arrstring[n2] + ";";
                }
                ++n2;
            }
            SharedPreferences.Editor editor = sprefs.edit();
            if (string10.equals((Object)"")) {
                editor.remove(SHAREDPREF_KEY_LOG);
            } else {
                editor.putString(SHAREDPREF_KEY_LOG, string10);
            }
            editor.commit();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readFromServer(final Context context) {
        try {
            new Thread(){

                /*
                 * Unable to fully structure code
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Lifted jumps to return sites
                 */
                public void run() {
                    block13 : {
                        block14 : {
                            try {
                                NotificationReceiver.access$100("NotificationReceiver readFromServer checking server: " + NotificationReceiver.access$000());
                                var2_1 = new BufferedReader((Reader)new InputStreamReader(new URL(NotificationReceiver.access$000()).openStream()));
                                var3_2 = var2_1.readLine();
lbl5: // 2 sources:
                                do {
                                    if (var3_2 == null) break;
                                    NotificationReceiver.access$100("NotificationReceiver readFromServer got: " + var3_2);
                                    var4_8 = var3_2.split("\\|");
                                    if (var4_8.length < 2 || var3_2.startsWith("#")) {
                                        NotificationReceiver.access$100("Skipping");
                                        var3_2 = var2_1.readLine();
                                        continue;
                                    }
                                    if (var4_8.length > 3) {
                                        var27_9 = Settings.Secure.getString((ContentResolver)context.getContentResolver(), (String)"android_id");
                                        NotificationReceiver.access$100("NotificationReceiver checking ANDROID_ID server: " + var4_8[3] + " device: " + var27_9);
                                        if (!var4_8[3].equals((Object)var27_9)) {
                                            NotificationReceiver.access$100("Skipping");
                                            var3_2 = var2_1.readLine();
                                            continue;
                                        }
                                    }
                                    var5_7 = new SimpleDateFormat("MM/dd/yyyy:HH:mm:sszzz", Locale.ENGLISH).parse(var4_8[0]);
                                    var6_10 = Calendar.getInstance();
                                    var6_10.setTime(var5_7);
                                    var7_18 = var6_10.getTimeInMillis();
                                    NotificationReceiver.access$100("NotificationReceiver readFromServer checking " + var7_18 + " at " + System.currentTimeMillis());
                                    if (var7_18 > System.currentTimeMillis()) {
                                        NotificationReceiver.access$100("NotificationReceiver readFromServer checking if already logged");
                                        var9_12 = NotificationReceiver.access$200().getString("LOG", null);
                                        if (var9_12 == null) break block13;
                                        var10_16 = var9_12.split(";");
                                        break block14;
                                    }
                                    NotificationReceiver.access$100("Skipping");
                                    ** GOTO lbl64
                                    break;
                                } while (true);
                            }
                            catch (Exception var1_20) {
                                if (NotificationReceiver.access$300() == false) return;
                                var1_20.printStackTrace();
                                return;
                            }
                            var2_1.close();
                            return;
                        }
                        for (var11_3 = 0; var11_3 < var10_16.length; ++var11_3) {
                            if (!var10_16[var11_3].startsWith(String.valueOf((long)var7_18))) continue;
                            var12_14 = true;
lbl44: // 2 sources:
                            do {
                                if (!var12_14) {
                                    NotificationReceiver.access$100("NotificationReceiver readFromServer queuing new notification");
                                    var13_4 = new Intent(context, NotificationReceiver.class);
                                    var13_4.putExtra("time", var7_18);
                                    var13_4.putExtra("message", var4_8[1]);
                                    var16_17 = var4_8.length > 2 ? var4_8[2] : "";
                                    var13_4.putExtra("url", var16_17);
                                    var13_4.putExtra("fromserver", true);
                                    var19_6 = PendingIntent.getBroadcast((Context)context, (int)((int)var7_18), (Intent)var13_4, (int)134217728);
                                    ((AlarmManager)context.getSystemService("alarm")).set(0, var7_18, var19_6);
                                    var20_19 = NotificationReceiver.access$200().getString("LOG", "");
                                    var21_15 = new StringBuilder().append(var20_19).append(var7_18).append("|").append(var4_8[1]).append("|");
                                    var22_5 = var4_8.length > 2 ? var4_8[2] : "";
                                    var23_11 = var21_15.append(var22_5).append("|true").append(";").toString();
                                    var24_13 = NotificationReceiver.access$200().edit();
                                    var24_13.putString("LOG", var23_11);
                                    var24_13.commit();
                                } else {
                                    NotificationReceiver.access$100("Skipping");
                                }
lbl64: // 3 sources:
                                var3_2 = var2_1.readLine();
                                ** continue;
                                break;
                            } while (true);
lbl66: // 1 sources:
                            ** GOTO lbl5
                        }
                    }
                    var12_14 = false;
                    ** while (true)
                }
            }.start();
            return;
        }
        catch (Exception exception) {
            if (!DEBUG) return;
            exception.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void scheduleAllAlarms(Context context) {
        String string2 = sprefs.getString(SHAREDPREF_KEY_LOG, null);
        if (string2 != null) {
            String[] arrstring = string2.split(";");
            for (int i2 = 0; i2 < arrstring.length; ++i2) {
                int n = arrstring[i2].indexOf("|");
                int n2 = arrstring[i2].indexOf("|", n + 1);
                int n3 = arrstring[i2].indexOf("|", n2 + 1);
                Intent intent = new Intent(context, NotificationReceiver.class);
                long l = Long.parseLong((String)arrstring[i2].substring(0, n));
                intent.putExtra("time", l);
                intent.putExtra("message", arrstring[i2].substring(n + 1, n2));
                intent.putExtra("url", arrstring[i2].substring(n2 + 1, n3));
                intent.putExtra("fromserver", Boolean.parseBoolean((String)arrstring[i2].substring(n3 + 1)));
                PendingIntent pendingIntent = PendingIntent.getBroadcast((Context)context, (int)((int)l), (Intent)intent, (int)134217728);
                ((AlarmManager)context.getSystemService("alarm")).set(0, l, pendingIntent);
            }
        }
        if (sprefs.contains(SHAREDPREF_KEY_SERVERURL)) {
            Intent intent = new Intent(context, NotificationReceiver.class);
            intent.putExtra("message", "servercheck");
            PendingIntent pendingIntent = PendingIntent.getBroadcast((Context)context, (int)0, (Intent)intent, (int)134217728);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService("alarm");
            long l = 1000L + System.currentTimeMillis();
            long l2 = DEBUG ? 60000L : 3600000L;
            alarmManager.setInexactRepeating(0, l, l2, pendingIntent);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onReceive(Context context, Intent intent) {
        block7 : {
            block6 : {
                if (intent == null || !(sprefs = context.getSharedPreferences(SHAREDPREF_NAME, 0)).getBoolean(SHAREDPREF_KEY_ENABLED, false)) break block6;
                if (!DEBUG && (sprefs.getBoolean(SHAREDPREF_KEY_DEBUG, false) || new File(Environment.getExternalStorageDirectory().toString() + "/.gludebug").exists())) {
                    DEBUG = true;
                }
                NotificationReceiver.Log("NotificationReceiver onReceive()");
                if (intent.getAction() != null && intent.getAction().equals((Object)"android.intent.action.BOOT_COMPLETED")) {
                    NotificationReceiver.super.scheduleAllAlarms(context);
                    return;
                }
                Bundle bundle = intent.getExtras();
                String string2 = bundle.getString("message");
                if (string2 == null) break block6;
                if (!string2.equals((Object)"servercheck")) {
                    NotificationReceiver.super.displayNotification(context, bundle.getLong("time"), string2, bundle.getString("url"), bundle.getBoolean("fromserver", false));
                    return;
                }
                SERVER_NOTIFICATION_URL = sprefs.getString(SHAREDPREF_KEY_SERVERURL, null);
                if (SERVER_NOTIFICATION_URL != null) break block7;
            }
            return;
        }
        NotificationReceiver.super.readFromServer(context);
    }

}


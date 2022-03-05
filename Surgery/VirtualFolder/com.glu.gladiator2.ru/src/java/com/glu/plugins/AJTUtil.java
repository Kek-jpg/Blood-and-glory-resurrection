/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlarmManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.Signature
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Environment
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.InputStream
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Thread
 *  java.security.Key
 *  java.security.spec.AlgorithmParameterSpec
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Properties
 *  javax.crypto.Cipher
 *  javax.crypto.CipherInputStream
 *  javax.crypto.spec.IvParameterSpec
 *  javax.crypto.spec.SecretKeySpec
 */
package com.glu.plugins;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import com.glu.plugins.AJTGameInfo;
import com.glu.plugins.AJTUI;
import com.glu.plugins.AJavaTools;
import com.unity3d.player.UnityPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AJTUtil {
    private static final String SHAREDPREF_KEY_DL_REPORTED_PER_VERSION = "dl-reported-";
    private static final String SHAREDPREF_KEY_DL_TYPE_PER_VERSION = "dl-type-";
    private static final String SHAREDPREF_KEY_RESTORED = "restored";
    private static final String SHAREDPREF_KEY_RUNS_PER_VERSION = "runcount-";
    private static final String SHAREDPREF_KEY_RUNS_TOTAL = "runcount";
    private static final String SHAREDPREF_NAME = "ajt";
    private static boolean grc_incremented = false;
    private static boolean grctv_incremented = false;
    private static Properties p;

    public static String GetOBBDownloadPlan() {
        AJavaTools.Log("GetOBBDownloadPlan()");
        String string2 = Integer.toString((int)AJTGameInfo.GetVersionCode());
        SharedPreferences sharedPreferences = UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0);
        String string3 = "old";
        if (!sharedPreferences.getBoolean(SHAREDPREF_KEY_DL_REPORTED_PER_VERSION + string2, false)) {
            string3 = sharedPreferences.getString(SHAREDPREF_KEY_DL_TYPE_PER_VERSION + string2, "aaa");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SHAREDPREF_KEY_DL_REPORTED_PER_VERSION + string2, true);
            editor.commit();
        }
        return string3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String GetProperty(String string2) {
        AJavaTools.Log("GetProperty( " + string2 + " )");
        if (string2 != null && string2.equals((Object)"DEVELOPMENT_BUILD")) {
            AJavaTools.Log("ERROR: Cannot query \"" + string2 + "\", returning \"false\"");
            return "false";
        }
        if (p != null) return p.getProperty(string2);
        AJavaTools.Log("Initializing properties");
        AJavaTools.Log("Checking for: " + AJTGameInfo.GetFilesPath() + "/properties-" + AJTGameInfo.GetVersionCode() + ".dat");
        File file = new File(AJTGameInfo.GetFilesPath() + "/properties-" + AJTGameInfo.GetVersionCode() + ".dat");
        if (file.exists()) {
            AJavaTools.Log("Found properties.dat override");
        }
        p = new Properties();
        try {
            FileInputStream fileInputStream;
            byte[] arrby = new byte[]{-87, -52, 54, -72, 14, -98, 92, 37, -108, 125, -128, -42, 118, -93, -10, 56};
            byte[] arrby2 = new byte[]{-5, 49, 4, -65, -56, -37, 77, -37, -69, -50, 44, 56, -25, -128, -42, 126};
            Cipher cipher = Cipher.getInstance((String)"AES/CBC/PKCS5Padding");
            cipher.init(2, (Key)new SecretKeySpec(arrby, "AES"), (AlgorithmParameterSpec)new IvParameterSpec(arrby2));
            Properties properties = p;
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
            } else {
                InputStream inputStream = UnityPlayer.currentActivity.getResources().openRawResource(UnityPlayer.currentActivity.getResources().getIdentifier("raw/properties", null, UnityPlayer.currentActivity.getPackageName()));
                fileInputStream = inputStream;
            }
            properties.load((InputStream)new CipherInputStream((InputStream)fileInputStream, cipher));
        }
        catch (Exception exception) {
            AJavaTools.Log("Failed to load properties");
            if (!AJavaTools.DEBUG) return p.getProperty(string2);
            exception.printStackTrace();
            return p.getProperty(string2);
        }
        return p.getProperty(string2);
    }

    public static int GetRunCount() {
        AJavaTools.Log("GetRunCount()");
        SharedPreferences sharedPreferences = UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0);
        int n = sharedPreferences.getInt(SHAREDPREF_KEY_RUNS_TOTAL, 0);
        if (!grc_incremented) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SHAREDPREF_KEY_RUNS_TOTAL, n + 1);
            editor.commit();
            grc_incremented = true;
        }
        return n;
    }

    public static int GetRunCountThisVersion() {
        AJavaTools.Log("GetRunCountThisVersion()");
        String string2 = Integer.toString((int)AJTGameInfo.GetVersionCode());
        SharedPreferences sharedPreferences = UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0);
        int n = sharedPreferences.getInt(SHAREDPREF_KEY_RUNS_PER_VERSION + string2, 0);
        if (!grctv_incremented) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SHAREDPREF_KEY_RUNS_PER_VERSION + string2, n + 1);
            editor.commit();
            grctv_incremented = true;
        }
        return n;
    }

    public static boolean IsDataRestored() {
        AJavaTools.Log("IsDataRestored()");
        SharedPreferences sharedPreferences = UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0);
        boolean bl = sharedPreferences.getBoolean(SHAREDPREF_KEY_RESTORED, false);
        if (bl) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(SHAREDPREF_KEY_RESTORED);
            editor.commit();
        }
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean LaunchGame(String string2, String string3) {
        block6 : {
            AJavaTools.Log("LaunchGame( " + string2 + ", " + string3 + " )");
            try {
                Iterator iterator = UnityPlayer.currentActivity.getPackageManager().getInstalledPackages(1).iterator();
                while (iterator.hasNext()) {
                    if (!((PackageInfo)iterator.next()).packageName.equals((Object)string2)) continue;
                    AJavaTools.Log("Game Found - Launching");
                    new Intent("android.intent.action.MAIN");
                    Intent intent = UnityPlayer.currentActivity.getPackageManager().getLaunchIntentForPackage(string2);
                    intent.addCategory("android.intent.category.LAUNCHER");
                    UnityPlayer.currentActivity.startActivity(intent);
                    return true;
                }
            }
            catch (Exception exception) {
                if (!AJavaTools.DEBUG) break;
                exception.printStackTrace();
                return false;
            }
            AJavaTools.Log("Game Not Found");
            if (string3 == null) break block6;
            if (string3.equals((Object)"")) break block6;
            AJavaTools.Log("Launching Alt URL");
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)string3));
            UnityPlayer.currentActivity.startActivity(intent);
        }
        do {
            return false;
            break;
        } while (true);
    }

    public static void RelaunchGame() {
        AJavaTools.Log("RelaunchGame()");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                PendingIntent pendingIntent = PendingIntent.getActivity((Context)UnityPlayer.currentActivity, (int)0, (Intent)UnityPlayer.currentActivity.getPackageManager().getLaunchIntentForPackage(UnityPlayer.currentActivity.getPackageName()), (int)0);
                ((AlarmManager)UnityPlayer.currentActivity.getSystemService("alarm")).set(0, 2000L + System.currentTimeMillis(), pendingIntent);
                UnityPlayer.currentActivity.finish();
            }
        });
    }

    public static void SendBroadcast(String string2, String string3) {
        AJavaTools.Log("SendBroadcast( " + string2 + ", " + string3 + " )");
        if (string3 != null && string3.contains((CharSequence)"external")) {
            string3 = string3.replaceFirst("external", "file://" + (Object)Environment.getExternalStorageDirectory());
            AJavaTools.Log("Expanded uri to: " + string3);
        }
        UnityPlayer.currentActivity.sendBroadcast(new Intent(string2, Uri.parse((String)string3)));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void VerifySignature() {
        block7 : {
            AJavaTools.Log("VerifySignature()");
            var1_1 = UnityPlayer.currentActivity.getPackageName();
            var2_2 = UnityPlayer.currentActivity.getApplicationContext().getResources();
            try {
                var6_3 = UnityPlayer.currentActivity.getPackageManager().getPackageInfo((String)var1_1, (int)64).signatures;
                var7_4 = var6_3.length;
                var4_5 = 0;
                break block7;
            }
            catch (Exception var3_8) {}
            ** GOTO lbl-1000
        }
        for (var0 = 0; var0 < var7_4; ++var0) {
            try {
                var9_6 = var6_3[var0];
                if (var9_6.hashCode() != 694135933 && (var10_7 = var9_6.hashCode()) != -1781156031) continue;
                var4_5 = 1;
                continue;
            }
            catch (Exception var8_11) {
                var0 = var4_5;
                var3_9 = var8_11;
            }
lbl-1000: // 2 sources:
            {
                var3_9.printStackTrace();
                var4_5 = var0;
                break;
            }
        }
        if (var4_5 != 0) return;
        AJavaTools.Log("VerifySignature() Failed");
        AJTUI.ShowToast(var2_2.getString(var2_2.getIdentifier("string/invalid_signature", null, var1_1)));
        try {
            Thread.sleep((long)3000L);
        }
        catch (Exception var5_10) {}
        UnityPlayer.currentActivity.finish();
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ContentResolver
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.View
 *  android.view.Window
 *  android.view.WindowManager
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Locale
 */
package com.glu.plugins;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.glu.plugins.AJavaTools;
import com.unity3d.player.UnityPlayer;
import java.util.List;
import java.util.Locale;

public class AJTDeviceInfo {
    private static int screenHeight;
    private static int screenWidth;

    static {
        screenWidth = 0;
        screenHeight = 0;
    }

    private static void CheckScreenDimensions() {
        try {
            AJavaTools.Log("Checking getCurrentFocus()");
            screenHeight = UnityPlayer.currentActivity.getWindow().getCurrentFocus().getHeight();
            screenWidth = UnityPlayer.currentActivity.getWindow().getCurrentFocus().getWidth();
            return;
        }
        catch (Exception exception) {
            AJavaTools.Log("geCurrentFocus() Failed");
            try {
                AJavaTools.Log("Checking getDecorView()");
                screenHeight = UnityPlayer.currentActivity.getWindow().getDecorView().getHeight();
                screenWidth = UnityPlayer.currentActivity.getWindow().getDecorView().getWidth();
                return;
            }
            catch (Exception exception2) {
                AJavaTools.Log("getDecorView() Failed");
                try {
                    AJavaTools.Log("Checking getWindowManager().getDefaultDisplay()");
                    screenHeight = UnityPlayer.currentActivity.getWindow().getWindowManager().getDefaultDisplay().getHeight();
                    screenWidth = UnityPlayer.currentActivity.getWindow().getWindowManager().getDefaultDisplay().getWidth();
                    return;
                }
                catch (Exception exception3) {
                    AJavaTools.Log("getWindowManager().getDefaultDisplay() Failed");
                    try {
                        AJavaTools.Log("Checking getSystemService(WINDOW_SERVICE).getDefaultDisplay()");
                        screenHeight = ((WindowManager)UnityPlayer.currentActivity.getSystemService("window")).getDefaultDisplay().getHeight();
                        screenWidth = ((WindowManager)UnityPlayer.currentActivity.getSystemService("window")).getDefaultDisplay().getWidth();
                        return;
                    }
                    catch (Exception exception4) {
                        AJavaTools.Log("getSystemService(WINDOW_SERVICE).getDefaultDisplay() Failed");
                        AJavaTools.Log("ERROR: Could not determine screen height");
                        return;
                    }
                }
            }
        }
    }

    public static String GetAndroidID() {
        AJavaTools.Log("GetAndroidID()");
        return Settings.Secure.getString((ContentResolver)UnityPlayer.currentActivity.getContentResolver(), (String)"android_id");
    }

    public static String GetDeviceCountry() {
        AJavaTools.Log("GetDeviceCountry()");
        return Locale.getDefault().getCountry();
    }

    public static String GetDeviceLanguage() {
        AJavaTools.Log("GetDeviceLanguage()");
        return Locale.getDefault().getLanguage();
    }

    public static int GetDeviceSDKVersion() {
        AJavaTools.Log("GetDeviceSDKVersion()");
        return Build.VERSION.SDK_INT;
    }

    public static String GetExternalStorageDirectory() {
        AJavaTools.Log("GetExternalStorageDirectory()");
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static double GetScreenDiagonalInches() {
        AJavaTools.Log("GetScreenDiagonalInches()");
        DisplayMetrics displayMetrics = UnityPlayer.currentActivity.getResources().getDisplayMetrics();
        float f2 = (float)displayMetrics.widthPixels / displayMetrics.xdpi;
        float f3 = (float)displayMetrics.heightPixels / displayMetrics.ydpi;
        return Math.sqrt((double)(Math.pow((double)f2, (double)2.0) + Math.pow((double)f3, (double)2.0)));
    }

    public static int GetScreenHeight() {
        AJavaTools.Log("GetScreenHeight()");
        if (screenHeight != 0) {
            return screenHeight;
        }
        AJTDeviceInfo.CheckScreenDimensions();
        return screenHeight;
    }

    public static int GetScreenLayout() {
        AJavaTools.Log("GetScreenLayout()");
        return 15 & UnityPlayer.currentActivity.getResources().getConfiguration().screenLayout;
    }

    public static int GetScreenWidth() {
        AJavaTools.Log("GetScreenWidth()");
        if (screenWidth != 0) {
            return screenWidth;
        }
        AJTDeviceInfo.CheckScreenDimensions();
        return screenWidth;
    }

    public static boolean IsDeviceRooted() {
        AJavaTools.Log("IsDeviceRooted()");
        try {
            for (PackageInfo packageInfo : UnityPlayer.currentActivity.getPackageManager().getInstalledPackages(1)) {
                boolean bl;
                if (!packageInfo.packageName.equals((Object)"com.noshufou.android.su") && !packageInfo.packageName.equals((Object)"com.noshufou.android.su.elite") && !packageInfo.packageName.equals((Object)"com.cih.game_cih") && !packageInfo.packageName.equals((Object)"com.cih.gamecih") && !packageInfo.packageName.equals((Object)"com.keramidas.TitaniumBackup") && !packageInfo.packageName.equals((Object)"com.keramidas.TitaniumBackupPro") && !packageInfo.packageName.equals((Object)"com.speedsoftware.rootexplorer") && !packageInfo.packageName.equals((Object)"com.koushikdutta.rommanager") && !packageInfo.packageName.equals((Object)"com.koushikdutta.rommanager.license") && !(bl = packageInfo.packageName.equals((Object)"eu.chainfire.supersu"))) continue;
                return true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    public static boolean IsExternalStorageMounted() {
        AJavaTools.Log("IsExternalStorageMounted()");
        return Environment.getExternalStorageState().equals((Object)"mounted");
    }
}


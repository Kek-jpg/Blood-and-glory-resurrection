/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.os.Environment
 *  java.io.File
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 */
package com.glu.plugins;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import com.glu.plugins.AJavaTools;
import com.unity3d.player.UnityPlayer;
import java.io.File;

public class AJTGameInfo {
    public static String GetExternalFilesPath() {
        AJavaTools.Log("GetExternalFilesPath()");
        return Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + AJTGameInfo.GetPackageName() + "/files";
    }

    public static String GetFilesPath() {
        AJavaTools.Log("GetFilesPath()");
        return UnityPlayer.currentActivity.getFilesDir().getPath();
    }

    public static String GetPackageName() {
        AJavaTools.Log("GetPackageName()");
        return UnityPlayer.currentActivity.getPackageName();
    }

    public static int GetVersionCode() {
        AJavaTools.Log("GetVersionCode()");
        try {
            int n = UnityPlayer.currentActivity.getPackageManager().getPackageInfo((String)UnityPlayer.currentActivity.getPackageName(), (int)0).versionCode;
            return n;
        }
        catch (Exception exception) {
            return 0;
        }
    }

    public static String GetVersionName() {
        AJavaTools.Log("GetVersionName()");
        try {
            String string2 = UnityPlayer.currentActivity.getPackageManager().getPackageInfo((String)UnityPlayer.currentActivity.getPackageName(), (int)0).versionName;
            return string2;
        }
        catch (Exception exception) {
            return "";
        }
    }
}


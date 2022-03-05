/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Environment
 *  android.util.Log
 *  android.view.Display
 *  android.view.View
 *  android.view.Window
 *  android.view.WindowManager
 *  java.io.File
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 */
package com.glu.plugins;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.unity3d.player.UnityPlayer;
import java.io.File;

public class AAds {
    public static boolean DEBUG = false;
    public static final String SHAREDPREF_NAME = "aads";
    private static final String VERSION = "2.5.1";
    private static int screenHeight;
    private static int screenWidth;

    static {
        DEBUG = false;
        screenWidth = 0;
        screenHeight = 0;
    }

    private static void CheckScreenDimensions() {
        try {
            AAds.Log("Checking getCurrentFocus()");
            screenHeight = UnityPlayer.currentActivity.getWindow().getCurrentFocus().getHeight();
            screenWidth = UnityPlayer.currentActivity.getWindow().getCurrentFocus().getWidth();
            return;
        }
        catch (Exception exception) {
            AAds.Log("geCurrentFocus() Failed");
            try {
                AAds.Log("Checking getDecorView()");
                screenHeight = UnityPlayer.currentActivity.getWindow().getDecorView().getHeight();
                screenWidth = UnityPlayer.currentActivity.getWindow().getDecorView().getWidth();
                return;
            }
            catch (Exception exception2) {
                AAds.Log("getDecorView() Failed");
                try {
                    AAds.Log("Checking getWindowManager().getDefaultDisplay()");
                    screenHeight = UnityPlayer.currentActivity.getWindow().getWindowManager().getDefaultDisplay().getHeight();
                    screenWidth = UnityPlayer.currentActivity.getWindow().getWindowManager().getDefaultDisplay().getWidth();
                    return;
                }
                catch (Exception exception3) {
                    AAds.Log("getWindowManager().getDefaultDisplay() Failed");
                    try {
                        AAds.Log("Checking getSystemService(WINDOW_SERVICE).getDefaultDisplay()");
                        screenHeight = ((WindowManager)UnityPlayer.currentActivity.getSystemService("window")).getDefaultDisplay().getHeight();
                        screenWidth = ((WindowManager)UnityPlayer.currentActivity.getSystemService("window")).getDefaultDisplay().getWidth();
                        return;
                    }
                    catch (Exception exception4) {
                        AAds.Log("getSystemService(WINDOW_SERVICE).getDefaultDisplay() Failed");
                        AAds.Log("ERROR: Could not determine screen height");
                        return;
                    }
                }
            }
        }
    }

    public static int GetScreenHeight() {
        if (screenHeight != 0) {
            return screenHeight;
        }
        AAds.CheckScreenDimensions();
        return screenHeight;
    }

    public static int GetScreenWidth() {
        if (screenWidth != 0) {
            return screenWidth;
        }
        AAds.CheckScreenDimensions();
        return screenWidth;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void Init(boolean bl) {
        boolean bl2 = bl || new File(Environment.getExternalStorageDirectory().toString() + "/.gludebug").exists();
        DEBUG = bl2;
        AAds.Log("AAds Version: 2.5.1");
    }

    public static void Log(String string2) {
        if (DEBUG) {
            Log.d((String)"AAds", (String)string2);
        }
    }

    public static void LogError(String string2) {
        if (DEBUG) {
            Log.e((String)"AAds", (String)string2);
        }
    }
}


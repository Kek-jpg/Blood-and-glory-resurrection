/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 */
package com.glu.plugins;

import android.app.Activity;
import android.content.Context;
import com.glu.plugins.AStats;
import com.mobileapptracker.MobileAppTracker;
import com.unity3d.player.UnityPlayer;
import java.util.HashMap;
import java.util.Map;

public class MobileAppTrackerGlu {
    private static MobileAppTracker mobileAppTracker;

    public static void Init(final String string2, final String string3) {
        AStats.Log("MobileAppTracker.Init( " + string2 + ", " + string3 + " )");
        if (string2 == null || string2.equals((Object)"") || string3 == null || string3.equals((Object)"")) {
            AStats.LogError("***********************************************************");
            AStats.LogError("***********************************************************");
            AStats.LogError("***********************************************************");
            AStats.LogError("**********                WARNING                **********");
            AStats.LogError("***********************************************************");
            AStats.LogError("***********************************************************");
            AStats.LogError("***********************************************************");
            AStats.LogError("MAT is disabled, because no keys were passed in.");
            AStats.LogError("***********************************************************");
            AStats.LogError("***********************************************************");
            AStats.LogError("***********************************************************");
            return;
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                MobileAppTrackerGlu.mobileAppTracker = new MobileAppTracker((Context)UnityPlayer.currentActivity);
                mobileAppTracker.setDebugMode(AStats.DEBUG);
                mobileAppTracker.setPackageName(string2);
                mobileAppTracker.setKey(string3);
                mobileAppTracker.trackInstall();
            }
        });
    }

    public static void TrackAction(String string2, String[] arrstring) {
        AStats.Log("MobileAppTracker.TrackAction( " + string2 + ", params[] )");
        if (mobileAppTracker == null) {
            return;
        }
        if (AStats.DEBUG && arrstring != null && arrstring.length % 2 != 0) {
            AStats.Log("Error - params has an odd length when it should be even (key/value pairs), last key will be ignored.");
        }
        HashMap hashMap = new HashMap();
        if (arrstring != null) {
            for (int i2 = 0; i2 < -1 + arrstring.length; i2 += 2) {
                AStats.Log("params " + arrstring[i2] + ": " + arrstring[i2 + 1]);
                hashMap.put((Object)arrstring[i2], (Object)arrstring[i2 + 1]);
            }
        }
        mobileAppTracker.trackAction(string2, (Map)hashMap);
    }

}


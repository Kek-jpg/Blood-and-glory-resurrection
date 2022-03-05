/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  java.lang.Object
 *  java.lang.String
 */
package com.unity3d.player;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.unity3d.player.f;

public final class g {
    private final Bundle a;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public g(Activity activity) {
        Bundle bundle = Bundle.EMPTY;
        PackageManager packageManager = activity.getPackageManager();
        ComponentName componentName = activity.getComponentName();
        try {
            ActivityInfo activityInfo = packageManager.getActivityInfo(componentName, 128);
            if (activityInfo != null && activityInfo.metaData != null) {
                bundle = activityInfo.metaData;
            }
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            f.Log(6, "Unable to retreive meta data for activity '" + (Object)componentName + "'");
        }
        this.a = new Bundle(bundle);
    }

    public final boolean a() {
        return this.a.getBoolean(String.format((String)"%s.%s", (Object[])new Object[]{"unityplayer", "ForwardNativeEventsToDalvik"}));
    }
}


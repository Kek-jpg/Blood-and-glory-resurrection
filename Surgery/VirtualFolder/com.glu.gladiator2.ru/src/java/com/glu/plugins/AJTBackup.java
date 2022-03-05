/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.backup.BackupManager
 *  android.app.backup.RestoreObserver
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  java.lang.Object
 *  java.lang.String
 */
package com.glu.plugins;

import android.app.Activity;
import android.app.backup.BackupManager;
import android.app.backup.RestoreObserver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import com.glu.plugins.AJTUI;
import com.glu.plugins.AJTUtil;
import com.glu.plugins.AJavaTools;
import com.unity3d.player.UnityPlayer;

public class AJTBackup {
    private static BackupManager bm;

    public static void DataChanged() {
        AJavaTools.Log("DataChanged()");
        if (Build.VERSION.SDK_INT < 8) {
            return;
        }
        if (bm == null) {
            bm = new BackupManager((Context)UnityPlayer.currentActivity);
        }
        bm.dataChanged();
    }

    public static void RequestRestore() {
        AJavaTools.Log("RequestRestore()");
        if (Build.VERSION.SDK_INT < 8) {
            return;
        }
        final String string2 = UnityPlayer.currentActivity.getPackageName();
        final Resources resources = UnityPlayer.currentActivity.getApplicationContext().getResources();
        if (bm == null) {
            bm = new BackupManager((Context)UnityPlayer.currentActivity);
        }
        bm.requestRestore(new RestoreObserver(){
            private int totalPackages = 0;

            public void onUpdate(int n, String string22) {
                AJavaTools.Log("RestoreObserver.onUpdate( " + n + ", " + string22 + " )");
            }

            public void restoreFinished(int n) {
                AJavaTools.Log("RestoreObserver.restoreFinished( " + n + " )");
                if (n == 0 && this.totalPackages > 0) {
                    AJTUtil.RelaunchGame();
                }
            }

            public void restoreStarting(int n) {
                AJavaTools.Log("RestoreObserver.restoreStarting( " + n + " )");
                this.totalPackages = n;
                if (this.totalPackages > 0) {
                    AJTUI.ShowToast(resources.getString(resources.getIdentifier("string/data_restored", null, string2)));
                }
            }
        });
    }

}


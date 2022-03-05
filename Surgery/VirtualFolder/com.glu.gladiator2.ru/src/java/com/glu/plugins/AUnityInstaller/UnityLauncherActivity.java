/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Environment
 *  com.google.android.vending.expansion.downloader.Helpers
 *  java.io.File
 *  java.lang.Boolean
 *  java.lang.String
 */
package com.glu.plugins.AUnityInstaller;

import android.content.Context;
import android.os.Environment;
import com.google.android.vending.expansion.downloader.Helpers;
import com.unity3d.player.UnityPlayerActivity;
import java.io.File;

public class UnityLauncherActivity
extends UnityPlayerActivity {
    private static final String KEY_OBB_ENABLED = "OBB_ENABLED";
    private static final String KEY_OBB_MAIN_FN = "OBB_MAIN_FILENAME";
    private static String OBB_PATH = null;
    private boolean obb_checked = false;
    private boolean obb_enabled = false;

    /*
     * Enabled aggressive block sorting
     */
    public String getPackageCodePath() {
        if (!this.obb_checked) {
            String string2 = Helpers.GetProperty((Context)this, (String)KEY_OBB_ENABLED);
            boolean bl = string2 != null ? Boolean.parseBoolean((String)string2) : false;
            this.obb_enabled = bl;
            this.obb_checked = true;
        }
        if (!this.obb_enabled) {
            return super.getPackageCodePath();
        }
        if (OBB_PATH == null) {
            String string3 = new File(Environment.getExternalStorageDirectory().toString() + "/Android/obb/" + this.getPackageName()).getAbsolutePath();
            OBB_PATH = string3 + "/" + Helpers.GetProperty((Context)this, (String)KEY_OBB_MAIN_FN);
        }
        return OBB_PATH;
    }
}


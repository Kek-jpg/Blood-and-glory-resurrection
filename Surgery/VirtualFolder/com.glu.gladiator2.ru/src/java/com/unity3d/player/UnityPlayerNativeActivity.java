/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.NativeActivity
 *  android.content.ContextWrapper
 *  android.content.res.Configuration
 *  android.os.Bundle
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback2
 *  android.view.View
 *  android.view.Window
 *  java.lang.String
 */
package com.unity3d.player;

import android.app.NativeActivity;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import com.unity3d.player.UnityPlayer;

public class UnityPlayerNativeActivity
extends NativeActivity {
    protected UnityPlayer mUnityPlayer;

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mUnityPlayer.configurationChanged(configuration);
    }

    protected void onCreate(Bundle bundle) {
        this.mUnityPlayer = new UnityPlayer((ContextWrapper)this);
        this.requestWindowFeature(1);
        super.onCreate(bundle);
        this.getWindow().takeSurface(null);
        this.setTheme(16973831);
        this.getWindow().setFormat(4);
        if (this.mUnityPlayer.getSettings().getBoolean("hide_status_bar", true)) {
            this.getWindow().setFlags(1024, 1024);
        }
        int n2 = this.mUnityPlayer.getSettings().getInt("gles_mode", 1);
        this.mUnityPlayer.init(n2, false);
        View view = this.mUnityPlayer.getView();
        this.setContentView(view);
        view.requestFocus();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mUnityPlayer.quit();
    }

    protected void onPause() {
        super.onPause();
        this.mUnityPlayer.pause();
    }

    protected void onResume() {
        super.onResume();
        this.mUnityPlayer.resume();
    }

    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        this.mUnityPlayer.windowFocusChanged(bl);
    }
}


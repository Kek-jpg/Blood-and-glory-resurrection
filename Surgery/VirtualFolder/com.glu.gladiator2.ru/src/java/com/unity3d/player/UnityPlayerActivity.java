/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ContextWrapper
 *  android.content.res.Configuration
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.Window
 *  java.lang.String
 */
package com.unity3d.player;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import com.unity3d.player.UnityPlayer;

public class UnityPlayerActivity
extends Activity {
    private UnityPlayer a;

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.a.configurationChanged(configuration);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setTheme(16973831);
        this.requestWindowFeature(1);
        this.a = new UnityPlayer((ContextWrapper)this);
        if (this.a.getSettings().getBoolean("hide_status_bar", true)) {
            this.getWindow().setFlags(1024, 1024);
        }
        int n = this.a.getSettings().getInt("gles_mode", 1);
        this.a.init(n, false);
        View view = this.a.getView();
        this.setContentView(view);
        view.requestFocus();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.a.quit();
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        return this.a.onKeyDown(n, keyEvent);
    }

    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        return this.a.onKeyUp(n, keyEvent);
    }

    protected void onPause() {
        super.onPause();
        this.a.pause();
    }

    protected void onResume() {
        super.onResume();
        this.a.resume();
    }

    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        this.a.windowFocusChanged(bl);
    }
}


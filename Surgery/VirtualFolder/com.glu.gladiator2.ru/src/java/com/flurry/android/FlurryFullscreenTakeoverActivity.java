/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.os.Bundle
 *  android.view.View
 *  java.lang.String
 */
package com.flurry.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import com.flurry.android.AdUnit;
import com.flurry.android.FlurryAgent;
import com.flurry.android.aj;
import com.flurry.android.aw;
import com.flurry.android.bb;
import com.flurry.android.be;

public final class FlurryFullscreenTakeoverActivity
extends Activity {
    private static final String a = FlurryFullscreenTakeoverActivity.class.getSimpleName();
    private aj b;
    private aw c;

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public final void onCreate(Bundle bundle) {
        this.setTheme(16973831);
        super.onCreate(bundle);
        String string = this.getIntent().getStringExtra("url");
        if (string == null) {
            be be2 = FlurryAgent.b();
            this.b = new aj((Context)this, be2, be2.m(), be2.n());
            this.b.initLayout((Context)this);
            this.setContentView((View)this.b);
            return;
        }
        this.c = new aw((Context)this, string);
        this.setContentView((View)this.c);
    }

    protected final void onDestroy() {
        super.onDestroy();
    }

    protected final void onPause() {
        super.onPause();
    }

    protected final void onRestart() {
        super.onRestart();
    }

    protected final void onResume() {
        super.onResume();
    }

    public final void onStart() {
        super.onStart();
        FlurryAgent.onStartSession((Context)this, FlurryAgent.c());
    }

    public final void onStop() {
        super.onStop();
        if (this.b != null) {
            this.b.a();
        }
        FlurryAgent.onEndSession((Context)this);
    }
}


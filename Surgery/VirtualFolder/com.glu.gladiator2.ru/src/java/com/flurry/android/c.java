/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.flurry.android;

import android.content.Context;
import android.content.Intent;
import com.flurry.android.FlurryFullscreenTakeoverActivity;
import com.flurry.android.bc;
import com.flurry.android.be;
import com.flurry.android.y;

final class c
implements Runnable {
    private /* synthetic */ String a;
    private /* synthetic */ Context b;
    private /* synthetic */ boolean c;
    private /* synthetic */ be d;

    c(be be2, String string, Context context, boolean bl) {
        this.d = be2;
        this.a = string;
        this.b = context;
        this.c = true;
    }

    public final void run() {
        if (this.a != null) {
            if (this.a.startsWith("market://")) {
                this.d.a(this.b, this.a);
                return;
            }
            Intent intent = new Intent(this.b, FlurryFullscreenTakeoverActivity.class);
            intent.putExtra("url", this.a);
            if (this.c && y.a(this.b, intent)) {
                this.b.startActivity(intent);
                return;
            }
            bc.b(be.a, "Unable to launch FlurryFullscreenTakeoverActivity, falling back to browser. Fix by declaring this Activity in your AndroidManifest.xml");
            y.a(this.b, this.a);
            return;
        }
        String string = "Unable to launch intent for: " + this.a;
        bc.d(be.a, string);
    }
}


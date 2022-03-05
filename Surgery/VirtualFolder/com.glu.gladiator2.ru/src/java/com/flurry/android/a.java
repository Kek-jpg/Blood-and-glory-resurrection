/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.Object
 *  java.lang.Runnable
 */
package com.flurry.android;

import android.content.Context;
import com.flurry.android.FlurryAgent;
import com.flurry.android.bc;
import com.flurry.android.p;

final class a
implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ FlurryAgent b;
    private /* synthetic */ boolean c;

    a(FlurryAgent flurryAgent, boolean bl, Context context) {
        this.b = flurryAgent;
        this.c = bl;
        this.a = context;
    }

    public final void run() {
        FlurryAgent.b(this.b);
        FlurryAgent.c(this.b);
        if (!this.c) {
            FlurryAgent.d(this.b).postDelayed((Runnable)new p(this), FlurryAgent.e());
        }
        if (FlurryAgent.f()) {
            bc.a("FlurryAgent", "Attempting to persist AdLogs");
            FlurryAgent.e(this.b).k();
        }
    }
}


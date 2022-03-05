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

final class e
implements Runnable {
    private /* synthetic */ Context a;
    private /* synthetic */ boolean b;
    private /* synthetic */ FlurryAgent c;

    e(FlurryAgent flurryAgent, Context context, boolean bl) {
        this.c = flurryAgent;
        this.a = context;
        this.b = bl;
    }

    public final void run() {
        if (!FlurryAgent.a(this.c)) {
            FlurryAgent.a(this.c, this.a);
        }
        FlurryAgent.a(this.c, this.a, this.b);
    }
}


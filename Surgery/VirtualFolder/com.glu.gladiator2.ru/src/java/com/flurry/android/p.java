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
import com.flurry.android.a;

final class p
implements Runnable {
    private /* synthetic */ a a;

    p(a a2) {
        this.a = a2;
    }

    public final void run() {
        FlurryAgent.b(this.a.b, this.a.a);
    }
}


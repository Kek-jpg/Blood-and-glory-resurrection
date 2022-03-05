/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Runnable
 */
package com.flurry.android;

import com.flurry.android.bd;
import com.flurry.android.t;

final class am
implements Runnable {
    private /* synthetic */ t a;
    private /* synthetic */ bd b;

    am(bd bd2, t t2) {
        this.b = bd2;
        this.a = t2;
    }

    public final void run() {
        this.b.b(this.a);
    }
}


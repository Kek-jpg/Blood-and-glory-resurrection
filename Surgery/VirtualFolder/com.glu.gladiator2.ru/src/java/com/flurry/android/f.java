/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.flurry.android;

import com.flurry.android.be;

final class f
implements Runnable {
    private /* synthetic */ be a;

    f(be be2) {
        this.a = be2;
    }

    public final void run() {
        this.a.e("/postAdLog.do");
    }
}


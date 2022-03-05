/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.flurry.android;

import android.content.Context;
import com.flurry.android.be;

final class d
implements Runnable {
    private /* synthetic */ Context a;
    private /* synthetic */ String b;
    private /* synthetic */ be c;

    d(be be2, Context context, String string) {
        this.c = be2;
        this.a = context;
        this.b = string;
    }

    public final void run() {
        this.c.b(this.a, this.b);
    }
}


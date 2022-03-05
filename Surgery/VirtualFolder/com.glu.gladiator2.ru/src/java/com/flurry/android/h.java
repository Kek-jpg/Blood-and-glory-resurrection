/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Thread
 */
package com.flurry.android;

import com.flurry.android.FlurryAdSize;
import com.flurry.android.FlurryAgent;
import com.flurry.android.bc;
import com.flurry.android.be;

final class h
implements Runnable {
    private /* synthetic */ String a;
    private /* synthetic */ int b;
    private /* synthetic */ int c;
    private /* synthetic */ boolean d;
    private /* synthetic */ FlurryAdSize e;
    private /* synthetic */ be f;

    h(be be2, String string, int n2, int n3, boolean bl, FlurryAdSize flurryAdSize) {
        this.f = be2;
        this.a = string;
        this.b = n2;
        this.c = n3;
        this.d = bl;
        this.e = flurryAdSize;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void run() {
        if (!FlurryAgent.a()) {
            try {
                Thread.sleep((long)1000L);
            }
            catch (Exception exception) {
                bc.b(be.a, " ");
            }
        }
        this.f.a(this.a, this.b, this.c, this.d, this.e, "/v3/getAds.do");
    }
}


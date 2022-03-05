/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.RelativeLayout
 *  java.lang.Class
 *  java.lang.String
 */
package com.flurry.android;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import com.flurry.android.FlurryAdSize;
import com.flurry.android.FlurryFullscreenTakeoverActivity;
import com.flurry.android.ar;
import com.flurry.android.bc;
import com.flurry.android.be;
import com.flurry.android.o;
import com.flurry.android.q;
import com.flurry.android.y;

final class t
extends RelativeLayout {
    private be a;
    private Context b;
    private String c;
    private ViewGroup d;
    private int e;
    private boolean f;

    t(be be2, Context context, String string, ViewGroup viewGroup) {
        super(context);
        this.a = be2;
        this.b = context;
        this.c = string;
        this.d = viewGroup;
        this.e = 0;
        this.f = false;
    }

    final int a() {
        return this.e;
    }

    /*
     * Enabled aggressive block sorting
     */
    final ar a(long l2) {
        int n2;
        boolean bl = true;
        boolean bl2 = false;
        int n3 = y.a(this.b, this.d.getWidth());
        q q2 = this.a.a(this.b, this.c, n3, n2 = y.a(this.b, this.d.getHeight()), false, null, l2);
        if (q2.a() != null && q2.c()) {
            this.removeAllViews();
            if (this.getParent() == null) {
                q2.a().a(this.d, (t)this);
            }
            this.addView((View)q2.a());
            this.e = 0;
            return new ar(bl, bl2);
        }
        if (!q2.b()) {
            bl2 = false;
            bl = false;
            return new ar(bl, bl2);
        }
        Intent intent = new Intent();
        intent.setClass(this.b, FlurryFullscreenTakeoverActivity.class);
        if (y.a(this.b, intent)) {
            this.b.startActivity(intent);
        } else {
            bc.b("FlurryAgent", "Unable to launch FlurryFullscreenTakeoverActivity. Fix by declaring this Activity in your AndroidManifest.xml");
        }
        bl2 = bl;
        bl = false;
        return new ar(bl, bl2);
    }

    final void a(int n2) {
        this.e = n2;
    }

    final void a(boolean bl) {
        this.f = bl;
    }

    final boolean b() {
        return this.f;
    }

    final ViewGroup c() {
        return this.d;
    }

    final String d() {
        return this.c;
    }
}


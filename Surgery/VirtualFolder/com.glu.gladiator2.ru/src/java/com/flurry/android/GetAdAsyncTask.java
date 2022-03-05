/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Void
 *  java.util.List
 */
package com.flurry.android;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.flurry.android.AdUnit;
import com.flurry.android.FlurryAdSize;
import com.flurry.android.FlurryFullscreenTakeoverActivity;
import com.flurry.android.bc;
import com.flurry.android.bd;
import com.flurry.android.be;
import com.flurry.android.o;
import com.flurry.android.q;
import com.flurry.android.t;
import com.flurry.android.y;
import java.util.List;

public class GetAdAsyncTask
extends AsyncTask<Void, Void, AdUnit> {
    private final String a = this.getClass().getSimpleName();
    private Context b;
    private String c;
    private FlurryAdSize d;
    private ViewGroup e;
    private bd f;
    private be g;
    private t h;
    private t i;

    public GetAdAsyncTask(Context context, String string, FlurryAdSize flurryAdSize, ViewGroup viewGroup, be be2) {
        this.b = context;
        this.c = string;
        this.d = flurryAdSize;
        this.e = viewGroup;
        this.f = be2.d;
        this.g = be2;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected /* varargs */ AdUnit doInBackground(Void ... arrvoid) {
        block3 : {
            AdUnit adUnit;
            block2 : {
                int n2 = y.a(this.b, this.e.getWidth());
                int n3 = y.a(this.b, this.e.getHeight());
                adUnit = this.g.d(this.c);
                if (adUnit != null) break block2;
                "Making ad request:" + this.c + "," + n2 + "x" + n3 + ",refresh:" + false;
                this.g.a(this.c, n2, n3, false, this.d, 0L);
                adUnit = this.g.d(this.c);
                if (adUnit == null) break block3;
            }
            return adUnit;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onPostExecute(AdUnit adUnit) {
        if (adUnit == null) return;
        q q2 = this.g.a(this.b, adUnit);
        if (q2.a() != null && q2.c()) {
            this.h.removeAllViews();
            if (this.h.getParent() == null) {
                q2.a().a(this.e, this.h);
            }
            this.h.addView((View)q2.a());
            this.h.a(0);
            if (!this.h.equals((Object)this.i)) {
                if (this.i != null) {
                    this.f.a(this.b, this.i);
                    this.e.removeView((View)this.i);
                }
                this.e.addView((View)this.h);
            }
            if (this.h.a() <= 0 || this.h.b()) return;
            {
                this.f.a(this.h);
                return;
            }
        }
        if (!q2.b()) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this.b, FlurryFullscreenTakeoverActivity.class);
        if (y.a(this.b, intent)) {
            this.b.startActivity(intent);
            return;
        }
        bc.b("FlurryAgent", "Unable to launch FlurryFullscreenTakeoverActivity. Fix by declaring this Activity in your AndroidManifest.xml");
    }

    protected void onPreExecute() {
        this.h = this.f.a(this.b, (View)this.e, this.c);
        if (this.h == null) {
            this.h = this.f.a(this.g, this.b, this.e, this.c);
            this.f.b(this.b, this.h);
        }
        this.i = this.f.a(this.e);
        for (t t2 : this.f.a(this.b, this.e, this.c)) {
            if (t2.equals((Object)this.i)) continue;
            this.f.a(this.b, t2);
            t2.c().removeView((View)t2);
        }
    }
}


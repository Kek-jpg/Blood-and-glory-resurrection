/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.android;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.flurry.android.AdFrame;
import com.flurry.android.AdSpaceLayout;
import com.flurry.android.AdUnit;
import com.flurry.android.ai;
import com.flurry.android.ba;
import com.flurry.android.bb;
import com.flurry.android.be;
import com.flurry.android.t;
import com.flurry.android.y;
import java.util.List;
import java.util.Map;

abstract class o
extends RelativeLayout {
    be a;
    bb b;
    AdUnit c;
    int d;
    private Context e;

    o(Context context) {
        super(context);
        o.super.a(context, null, null);
    }

    o(Context context, be be2, bb bb2) {
        super(context);
        o.super.a(context, be2, bb2);
    }

    private void a(Context context, be be2, bb bb2) {
        this.e = context;
        this.a = be2;
        this.b = bb2;
    }

    /*
     * Enabled aggressive block sorting
     */
    final void a(ViewGroup viewGroup, t t2) {
        if (this.c == null || this.c.c().size() < 1) {
            return;
        }
        if (!(viewGroup instanceof RelativeLayout)) {
            t2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            return;
        }
        AdSpaceLayout adSpaceLayout = ((AdFrame)this.c.c().get(0)).d();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(y.b(this.e, adSpaceLayout.a()), y.b(this.e, adSpaceLayout.b()));
        String[] arrstring = adSpaceLayout.e().toString().split("-");
        if (arrstring.length == 2) {
            if (arrstring[0].equals((Object)"b")) {
                layoutParams.addRule(12);
            } else if (arrstring[0].equals((Object)"t")) {
                layoutParams.addRule(10);
            } else if (arrstring[0].equals((Object)"m")) {
                layoutParams.addRule(15);
            }
            if (arrstring[1].equals((Object)"c")) {
                layoutParams.addRule(14);
            } else if (arrstring[1].equals((Object)"l")) {
                layoutParams.addRule(9);
            } else if (arrstring[1].equals((Object)"r")) {
                layoutParams.addRule(11);
            }
        }
        t2.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    final void a(String string, Map<String, String> map) {
        "AppSpotBannerView.onEvent " + string;
        this.a.a(this.b, string, true, map);
        if (this.c != null) {
            this.a.a(new ba(string, this.e, this.c, this.b, 0), (ai)this.a, 0);
        }
    }

    public abstract void initLayout(Context var1);
}


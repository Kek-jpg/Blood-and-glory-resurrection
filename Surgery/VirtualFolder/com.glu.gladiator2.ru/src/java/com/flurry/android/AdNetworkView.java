/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.String
 *  java.util.Map
 */
package com.flurry.android;

import android.content.Context;
import com.flurry.android.AdCreative;
import com.flurry.android.bb;
import com.flurry.android.be;
import com.flurry.android.o;
import java.util.Map;

public abstract class AdNetworkView
extends o {
    public static String sAdNetworkApiKey;
    public AdCreative fAdCreative;

    public AdNetworkView(Context context, AdCreative adCreative) {
        super(context);
        this.fAdCreative = adCreative;
    }

    AdNetworkView(Context context, be be2, bb bb2, AdCreative adCreative) {
        super(context, be2, bb2);
        this.fAdCreative = adCreative;
    }

    public void onAdClicked(Map<String, String> map) {
        super.a("clicked", map);
    }

    public void onAdClosed(Map<String, String> map) {
        super.a("adClosed", map);
    }

    public void onAdFilled(Map<String, String> map) {
        super.a("filled", map);
    }

    public void onAdShown(Map<String, String> map) {
        super.a("rendered", map);
    }

    public void onAdUnFilled(Map<String, String> map) {
        super.a("unfilled", map);
    }
}


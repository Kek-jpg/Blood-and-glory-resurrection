/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.util.Log
 *  android.view.View
 *  com.google.ads.AdListener
 *  com.google.ads.AdRequest
 *  com.google.ads.AdSize
 *  com.google.ads.AdView
 *  com.google.ads.InterstitialAd
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.android;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.flurry.android.AdCreative;
import com.flurry.android.AdNetworkView;
import com.flurry.android.ay;
import com.flurry.android.bb;
import com.flurry.android.be;
import com.flurry.android.i;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.InterstitialAd;

final class x
extends AdNetworkView {
    private static boolean e;
    private InterstitialAd f;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    x(Context context, be be2, bb bb2, AdCreative adCreative) {
        ApplicationInfo applicationInfo;
        super(context, be2, bb2, adCreative);
        try {
            ApplicationInfo applicationInfo2;
            applicationInfo = applicationInfo2 = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.d((String)"FlurryAgent", (String)"Cannot find manifest for app");
            applicationInfo = null;
        }
        Bundle bundle = applicationInfo.metaData;
        sAdNetworkApiKey = bundle.getString("com.flurry.admob.MY_AD_UNIT_ID");
        e = bundle.getBoolean("com.flurry.admob.test");
        if (sAdNetworkApiKey == null) {
            Log.d((String)"FlurryAgent", (String)"com.flurry.admob.MY_AD_UNIT_ID not set in manifest");
        }
        this.setFocusable(true);
    }

    static /* synthetic */ InterstitialAd a(x x2) {
        return x2.f;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void initLayout(Context context) {
        AdSize adSize;
        if (this.fAdCreative.getFormat().equals((Object)"takeover")) {
            this.f = new InterstitialAd((Activity)context, sAdNetworkApiKey);
            i i2 = new i((x)this);
            this.f.setAdListener((AdListener)i2);
            AdRequest adRequest = new AdRequest();
            if (e) {
                Log.d((String)"FlurryAgent", (String)"Admob AdView set to Test Mode.");
                adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
            }
            this.f.loadAd(adRequest);
            return;
        }
        int n2 = this.fAdCreative.getHeight();
        int n3 = this.fAdCreative.getWidth();
        if (n3 >= AdSize.IAB_LEADERBOARD.getWidth() && n2 >= AdSize.IAB_LEADERBOARD.getHeight()) {
            Log.d((String)"FlurryAgent", (String)"Determined Admob AdSize as IAB_LEADERBOARD");
            adSize = AdSize.IAB_LEADERBOARD;
        } else if (n3 >= AdSize.IAB_BANNER.getWidth() && n2 >= AdSize.IAB_BANNER.getHeight()) {
            Log.d((String)"FlurryAgent", (String)"Determined Admob AdSize as IAB_BANNER");
            adSize = AdSize.IAB_BANNER;
        } else if (n3 >= AdSize.BANNER.getWidth() && n2 >= AdSize.BANNER.getHeight()) {
            Log.d((String)"FlurryAgent", (String)"Determined Admob AdSize as BANNER");
            adSize = AdSize.BANNER;
        } else if (n3 >= AdSize.IAB_MRECT.getWidth() && n2 >= AdSize.IAB_MRECT.getHeight()) {
            Log.d((String)"FlurryAgent", (String)"Determined Admob AdSize as IAB_MRECT");
            adSize = AdSize.IAB_MRECT;
        } else {
            Log.d((String)"FlurryAgent", (String)"Could not find Admob AdSize that matches size");
            adSize = null;
        }
        if (adSize == null) {
            Log.d((String)"FlurryAgent", (String)"**********Could not load Admob Ad");
            return;
        }
        AdView adView = new AdView((Activity)context, adSize, sAdNetworkApiKey);
        adView.setAdListener((AdListener)new ay((x)this));
        this.addView((View)adView);
        AdRequest adRequest = new AdRequest();
        if (e) {
            Log.d((String)"FlurryAgent", (String)"Admob AdView set to Test Mode.");
            adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
        }
        adView.loadAd(adRequest);
    }
}


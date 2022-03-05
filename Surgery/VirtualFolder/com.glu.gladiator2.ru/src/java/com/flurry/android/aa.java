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
 *  com.inmobi.androidsdk.IMAdInterstitial
 *  com.inmobi.androidsdk.IMAdInterstitialListener
 *  com.inmobi.androidsdk.IMAdListener
 *  com.inmobi.androidsdk.IMAdRequest
 *  com.inmobi.androidsdk.IMAdView
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
import com.flurry.android.ab;
import com.flurry.android.bb;
import com.flurry.android.be;
import com.flurry.android.l;
import com.inmobi.androidsdk.IMAdInterstitial;
import com.inmobi.androidsdk.IMAdInterstitialListener;
import com.inmobi.androidsdk.IMAdListener;
import com.inmobi.androidsdk.IMAdRequest;
import com.inmobi.androidsdk.IMAdView;

final class aa
extends AdNetworkView {
    private static boolean e;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    aa(Context context, be be2, bb bb2, AdCreative adCreative) {
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
        sAdNetworkApiKey = bundle.getString("com.flurry.inmobi.MY_APP_ID");
        e = bundle.getBoolean("com.flurry.inmobi.test");
        if (sAdNetworkApiKey == null) {
            Log.d((String)"FlurryAgent", (String)"com.flurry.inmobi.MY_APP_ID not set in manifest");
        }
        this.setFocusable(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void initLayout(Context context) {
        int n2;
        if (this.fAdCreative.getFormat().equals((Object)"takeover")) {
            IMAdInterstitial iMAdInterstitial = new IMAdInterstitial((Activity)context, sAdNetworkApiKey);
            iMAdInterstitial.setImAdInterstitialListener((IMAdInterstitialListener)new l((aa)this));
            IMAdRequest iMAdRequest = new IMAdRequest();
            if (e) {
                Log.d((String)"FlurryAgent", (String)"InMobi Interstitial set to Test Mode.");
                iMAdRequest.setTestMode(true);
            }
            iMAdInterstitial.loadNewAd(iMAdRequest);
            return;
        }
        int n3 = this.fAdCreative.getHeight();
        int n4 = this.fAdCreative.getWidth();
        if (n4 >= 728 && n3 >= 90) {
            Log.d((String)"FlurryAgent", (String)"Determined InMobi AdSize as 728x90");
            n2 = 11;
        } else if (n4 >= 468 && n3 >= 60) {
            Log.d((String)"FlurryAgent", (String)"Determined InMobi AdSize as 468x60");
            n2 = 12;
        } else if (n4 >= 320 && n3 >= 50) {
            Log.d((String)"FlurryAgent", (String)"Determined InMobi AdSize as 320x50");
            n2 = 15;
        } else if (n4 >= 300 && n3 >= 250) {
            Log.d((String)"FlurryAgent", (String)"Determined InMobi AdSize as 300x250");
            n2 = 10;
        } else if (n4 >= 120 && n3 >= 600) {
            Log.d((String)"FlurryAgent", (String)"Determined InMobi AdSize as 120x600");
            n2 = 13;
        } else {
            Log.d((String)"FlurryAgent", (String)"Could not find InMobi AdSize that matches size");
            n2 = -1;
        }
        if (n2 == -1) {
            Log.d((String)"FlurryAgent", (String)"**********Could not load InMobi Ad");
            return;
        }
        IMAdView iMAdView = new IMAdView((Activity)context, n2, sAdNetworkApiKey);
        iMAdView.setIMAdListener((IMAdListener)new ab((aa)this));
        this.addView((View)iMAdView);
        IMAdRequest iMAdRequest = new IMAdRequest();
        if (e) {
            Log.d((String)"FlurryAgent", (String)"InMobi AdView set to Test Mode.");
            iMAdRequest.setTestMode(true);
        }
        iMAdView.setIMAdRequest(iMAdRequest);
        iMAdView.setRefreshInterval(-1);
        iMAdView.loadNewAd();
    }
}


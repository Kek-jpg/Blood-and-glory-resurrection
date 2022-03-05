/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  com.google.ads.Ad
 *  com.google.ads.AdListener
 *  com.google.ads.AdRequest
 *  com.google.ads.AdRequest$ErrorCode
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.flurry.android;

import android.util.Log;
import com.flurry.android.x;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import java.util.Map;

final class i
implements AdListener {
    private /* synthetic */ x a;

    i(x x2) {
        this.a = x2;
    }

    public final void onDismissScreen(Ad ad2) {
        Log.i((String)"FlurryAgent", (String)"Admob Interstitial dismissed from screen.");
    }

    public final void onFailedToReceiveAd(Ad ad2, AdRequest.ErrorCode errorCode) {
        this.a.onAdUnFilled(null);
        Log.d((String)"FlurryAgent", (String)"Admob Interstitial failed to receive takeover.");
    }

    public final void onLeaveApplication(Ad ad2) {
        this.a.onAdClicked(null);
        Log.i((String)"FlurryAgent", (String)"Admob Interstitial leave application.");
    }

    public final void onPresentScreen(Ad ad2) {
        Log.d((String)"FlurryAgent", (String)"Admob Interstitial present on screen.");
    }

    public final void onReceiveAd(Ad ad2) {
        this.a.onAdFilled(null);
        Log.d((String)"FlurryAgent", (String)"Admob Interstitial received takeover.");
        this.a.onAdShown(null);
        x.a(this.a).show();
    }
}


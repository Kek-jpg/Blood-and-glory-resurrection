/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  com.millennialmedia.android.MMAdView
 *  com.millennialmedia.android.MMAdView$MMAdListener
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.flurry.android;

import android.util.Log;
import com.flurry.android.ag;
import com.millennialmedia.android.MMAdView;
import java.util.Map;

final class as
implements MMAdView.MMAdListener {
    private /* synthetic */ ag a;

    as(ag ag2) {
        this.a = ag2;
    }

    public final void MMAdCachingCompleted(MMAdView mMAdView, boolean bl) {
        Log.d((String)"FlurryAgent", (String)"Millennial Interstitial caching completed.");
    }

    public final void MMAdClickedToNewBrowser(MMAdView mMAdView) {
        this.a.onAdClicked(null);
        Log.d((String)"FlurryAgent", (String)"Millennial Interstitial clicked to new browser.");
    }

    public final void MMAdClickedToOverlay(MMAdView mMAdView) {
        this.a.onAdClicked(null);
        Log.d((String)"FlurryAgent", (String)"Millennial Interstitial clicked to overlay.");
    }

    public final void MMAdFailed(MMAdView mMAdView) {
        this.a.onAdUnFilled(null);
        Log.d((String)"FlurryAgent", (String)"Millennial Interstitial failed to load ad.");
    }

    public final void MMAdOverlayLaunched(MMAdView mMAdView) {
        Log.d((String)"FlurryAgent", (String)"Millennial Interstitial overlay launched.");
    }

    public final void MMAdRequestIsCaching(MMAdView mMAdView) {
        Log.d((String)"FlurryAgent", (String)"Millennial Interstitial request is caching.");
    }

    public final void MMAdReturned(MMAdView mMAdView) {
        mMAdView.display();
        this.a.onAdFilled(null);
        this.a.onAdShown(null);
        Log.d((String)"FlurryAgent", (String)"Millennial In returned ad.");
    }
}


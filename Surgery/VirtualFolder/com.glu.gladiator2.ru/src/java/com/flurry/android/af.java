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

final class af
implements MMAdView.MMAdListener {
    private /* synthetic */ ag a;

    af(ag ag2) {
        this.a = ag2;
    }

    public final void MMAdCachingCompleted(MMAdView mMAdView, boolean bl) {
        Log.d((String)"FlurryAgent", (String)"Millennial MMAdView caching completed.");
    }

    public final void MMAdClickedToNewBrowser(MMAdView mMAdView) {
        this.a.onAdClicked(null);
        Log.d((String)"FlurryAgent", (String)"Millennial MMAdView clicked to new browser.");
    }

    public final void MMAdClickedToOverlay(MMAdView mMAdView) {
        this.a.onAdClicked(null);
        Log.d((String)"FlurryAgent", (String)"Millennial MMAdView clicked to overlay.");
    }

    public final void MMAdFailed(MMAdView mMAdView) {
        this.a.onAdUnFilled(null);
        Log.d((String)"FlurryAgent", (String)"Millennial MMAdView failed to load ad.");
    }

    public final void MMAdOverlayLaunched(MMAdView mMAdView) {
        Log.d((String)"FlurryAgent", (String)"Millennial MMAdView overlay launched.");
    }

    public final void MMAdRequestIsCaching(MMAdView mMAdView) {
        Log.d((String)"FlurryAgent", (String)"Millennial MMAdView request is caching.");
    }

    public final void MMAdReturned(MMAdView mMAdView) {
        this.a.onAdFilled(null);
        this.a.onAdShown(null);
        Log.d((String)"FlurryAgent", (String)"Millennial MMAdView returned ad.");
    }
}


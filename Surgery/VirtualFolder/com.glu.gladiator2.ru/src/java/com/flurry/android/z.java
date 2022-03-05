/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  com.mobclix.android.sdk.MobclixFullScreenAdView
 *  com.mobclix.android.sdk.MobclixFullScreenAdViewListener
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.flurry.android;

import android.util.Log;
import com.flurry.android.ac;
import com.mobclix.android.sdk.MobclixFullScreenAdView;
import com.mobclix.android.sdk.MobclixFullScreenAdViewListener;
import java.util.Map;

final class z
implements MobclixFullScreenAdViewListener {
    private /* synthetic */ ac a;

    z(ac ac2) {
        this.a = ac2;
    }

    public final String keywords() {
        Log.d((String)"FlurryAgent", (String)"Mobclix keyword callback.");
        return null;
    }

    public final void onDismissAd(MobclixFullScreenAdView mobclixFullScreenAdView) {
        this.a.onAdClosed(null);
        Log.d((String)"FlurryAgent", (String)"Mobclix Interstitial ad dismissed.");
    }

    public final void onFailedLoad(MobclixFullScreenAdView mobclixFullScreenAdView, int n2) {
        this.a.onAdUnFilled(null);
        Log.d((String)"FlurryAgent", (String)"Mobclix Interstitial ad failed to load.");
    }

    public final void onFinishLoad(MobclixFullScreenAdView mobclixFullScreenAdView) {
        this.a.onAdFilled(null);
        Log.d((String)"FlurryAgent", (String)"Mobclix Interstitial ad successfully loaded.");
    }

    public final void onPresentAd(MobclixFullScreenAdView mobclixFullScreenAdView) {
        this.a.onAdShown(null);
        Log.d((String)"FlurryAgent", (String)"Mobclix Interstitial ad successfully shown.");
    }

    public final String query() {
        Log.d((String)"FlurryAgent", (String)"Mobclix query callback.");
        return null;
    }
}


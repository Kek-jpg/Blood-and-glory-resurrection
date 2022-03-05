/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  com.inmobi.androidsdk.IMAdInterstitial
 *  com.inmobi.androidsdk.IMAdInterstitial$State
 *  com.inmobi.androidsdk.IMAdInterstitialListener
 *  com.inmobi.androidsdk.IMAdRequest
 *  com.inmobi.androidsdk.IMAdRequest$ErrorCode
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.flurry.android;

import android.util.Log;
import com.flurry.android.aa;
import com.inmobi.androidsdk.IMAdInterstitial;
import com.inmobi.androidsdk.IMAdInterstitialListener;
import com.inmobi.androidsdk.IMAdRequest;
import java.util.Map;

final class l
implements IMAdInterstitialListener {
    private /* synthetic */ aa a;

    l(aa aa2) {
        this.a = aa2;
    }

    public final void onAdRequestFailed(IMAdInterstitial iMAdInterstitial, IMAdRequest.ErrorCode errorCode) {
        this.a.onAdUnFilled(null);
        Log.d((String)"FlurryAgent", (String)"InMobi imAdView ad request failed.");
    }

    public final void onAdRequestLoaded(IMAdInterstitial iMAdInterstitial) {
        this.a.onAdFilled(null);
        Log.d((String)"FlurryAgent", (String)"InMobi Interstitial ad request completed.");
        if (IMAdInterstitial.State.READY.equals((Object)iMAdInterstitial.getState())) {
            this.a.onAdShown(null);
            iMAdInterstitial.show();
        }
    }

    public final void onDismissAdScreen(IMAdInterstitial iMAdInterstitial) {
        Log.d((String)"FlurryAgent", (String)"InMobi Interstitial ad dismissed.");
    }

    public final void onShowAdScreen(IMAdInterstitial iMAdInterstitial) {
        this.a.onAdClicked(null);
        Log.d((String)"FlurryAgent", (String)"InMobi Interstitial ad shown.");
    }
}


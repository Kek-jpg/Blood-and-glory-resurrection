/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  com.inmobi.androidsdk.IMAdListener
 *  com.inmobi.androidsdk.IMAdRequest
 *  com.inmobi.androidsdk.IMAdRequest$ErrorCode
 *  com.inmobi.androidsdk.IMAdView
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.flurry.android;

import android.util.Log;
import com.flurry.android.aa;
import com.inmobi.androidsdk.IMAdListener;
import com.inmobi.androidsdk.IMAdRequest;
import com.inmobi.androidsdk.IMAdView;
import java.util.Map;

final class ab
implements IMAdListener {
    private /* synthetic */ aa a;

    ab(aa aa2) {
        this.a = aa2;
    }

    public final void onAdRequestCompleted(IMAdView iMAdView) {
        this.a.onAdFilled(null);
        this.a.onAdShown(null);
        Log.d((String)"FlurryAgent", (String)"InMobi imAdView ad request completed.");
    }

    public final void onAdRequestFailed(IMAdView iMAdView, IMAdRequest.ErrorCode errorCode) {
        this.a.onAdUnFilled(null);
        Log.d((String)"FlurryAgent", (String)"InMobi imAdView ad request failed.");
    }

    public final void onDismissAdScreen(IMAdView iMAdView) {
        Log.d((String)"FlurryAgent", (String)"InMobi imAdView dismiss ad.");
    }

    public final void onShowAdScreen(IMAdView iMAdView) {
        this.a.onAdClicked(null);
        Log.d((String)"FlurryAgent", (String)"InMobi imAdView ad shown.");
    }
}


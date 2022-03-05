/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  com.mobclix.android.sdk.MobclixAdView
 *  com.mobclix.android.sdk.MobclixAdViewListener
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.flurry.android;

import android.util.Log;
import com.flurry.android.ac;
import com.mobclix.android.sdk.MobclixAdView;
import com.mobclix.android.sdk.MobclixAdViewListener;
import java.util.Map;

final class ah
implements MobclixAdViewListener {
    private /* synthetic */ ac a;

    ah(ac ac2) {
        this.a = ac2;
    }

    public final String keywords() {
        Log.d((String)"FlurryAgent", (String)"Mobclix keyword callback.");
        return null;
    }

    public final void onAdClick(MobclixAdView mobclixAdView) {
        this.a.onAdClicked(null);
        Log.d((String)"FlurryAgent", (String)"Mobclix AdView ad clicked.");
    }

    public final void onCustomAdTouchThrough(MobclixAdView mobclixAdView, String string) {
        this.a.onAdClicked(null);
        Log.d((String)"FlurryAgent", (String)"Mobclix AdView custom ad clicked.");
    }

    public final void onFailedLoad(MobclixAdView mobclixAdView, int n2) {
        this.a.onAdUnFilled(null);
        Log.d((String)"FlurryAgent", (String)"Mobclix AdView ad failed to load.");
    }

    public final boolean onOpenAllocationLoad(MobclixAdView mobclixAdView, int n2) {
        Log.d((String)"FlurryAgent", (String)"Mobclix AdView loaded an open allocation ad.");
        return true;
    }

    public final void onSuccessfulLoad(MobclixAdView mobclixAdView) {
        this.a.onAdFilled(null);
        this.a.onAdShown(null);
        Log.d((String)"FlurryAgent", (String)"Mobclix AdView ad successfully loaded.");
    }

    public final String query() {
        Log.d((String)"FlurryAgent", (String)"Mobclix query callback.");
        return null;
    }
}


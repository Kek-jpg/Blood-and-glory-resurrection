/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  com.jumptap.adtag.JtAdView
 *  com.jumptap.adtag.JtAdViewListener
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.flurry.android;

import android.util.Log;
import com.flurry.android.g;
import com.jumptap.adtag.JtAdView;
import com.jumptap.adtag.JtAdViewListener;
import java.util.Map;

final class s
implements JtAdViewListener {
    private /* synthetic */ g a;

    s(g g2) {
        this.a = g2;
    }

    public final void onAdError(JtAdView jtAdView, int n2, int n3) {
        Log.d((String)"FlurryAgent", (String)"Jumptap AdView error.");
    }

    public final void onFocusChange(JtAdView jtAdView, int n2, boolean bl) {
        Log.d((String)"FlurryAgent", (String)"Jumptap AdView focus changed.");
    }

    public final void onInterstitialDismissed(JtAdView jtAdView, int n2) {
        this.a.onAdClosed(null);
        Log.d((String)"FlurryAgent", (String)"Jumptap AdView dismissed.");
    }

    public final void onNewAd(JtAdView jtAdView, int n2, String string) {
        this.a.onAdFilled(null);
        this.a.onAdShown(null);
        Log.d((String)"FlurryAgent", (String)"Jumptap AdView new ad.");
    }

    public final void onNoAdFound(JtAdView jtAdView, int n2) {
        this.a.onAdUnFilled(null);
        Log.d((String)"FlurryAgent", (String)"Jumptap AdView no ad found.");
    }
}


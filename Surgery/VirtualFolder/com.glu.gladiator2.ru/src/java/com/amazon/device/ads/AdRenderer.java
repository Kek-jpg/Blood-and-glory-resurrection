/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  java.lang.Enum
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashSet
 *  java.util.Map
 *  java.util.Set
 */
package com.amazon.device.ads;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdBridge;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.Log;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class AdRenderer {
    protected static final String AAX_REDIRECT_BETA = "aax-beta.integ.amazon.com";
    protected static final String AAX_REDIRECT_GAMMA = "aax-us-east.amazon-adsystem.com";
    protected static final String AAX_REDIRECT_PROD = "aax-us-east.amazon-adsystem.com";
    protected static final String CORNERSTONE_BEST_ENDPOINT_BETA = "d16g-cornerstone-bes.integ.amazon.com";
    protected static final String CORNERSTONE_BEST_ENDPOINT_PROD = "pda-bes.amazon.com";
    private static final String LOG_TAG = "AdRenderer";
    protected Ad ad_ = null;
    protected AdBridge bridge_ = null;
    protected boolean isDestroyed_ = false;
    protected Set<String> redirectHosts_ = null;
    protected double scalingFactor_ = 1.0;
    protected boolean viewRemoved_ = false;

    protected AdRenderer(Ad ad2, AdBridge adBridge) {
        this.ad_ = ad2;
        this.bridge_ = adBridge;
        this.redirectHosts_ = new HashSet();
        this.redirectHosts_.add((Object)"aax-us-east.amazon-adsystem.com");
        this.redirectHosts_.add((Object)"aax-us-east.amazon-adsystem.com");
        this.redirectHosts_.add((Object)AAX_REDIRECT_BETA);
        this.redirectHosts_.add((Object)CORNERSTONE_BEST_ENDPOINT_PROD);
        this.redirectHosts_.add((Object)CORNERSTONE_BEST_ENDPOINT_BETA);
        this.determineScalingFactor();
    }

    protected void adLoaded(AdProperties adProperties) {
        this.bridge_.setIsLoading(false);
        AdBridge adBridge = this.bridge_;
        adBridge = adProperties;
        adBridge.adLoaded((AdProperties)((Object)adBridge));
    }

    protected abstract void destroy();

    /*
     * Enabled aggressive block sorting
     */
    protected void determineScalingFactor() {
        int n2 = (int)((float)this.ad_.getWidth() * this.bridge_.getScalingDensity());
        int n3 = (int)((float)this.ad_.getHeight() * this.bridge_.getScalingDensity());
        double d2 = (double)this.bridge_.getWindowWidth() / (double)n2;
        double d3 = (double)this.bridge_.getWindowHeight() / (double)n3;
        this.scalingFactor_ = d3 < d2 ? d3 : d2;
        Object[] arrobject = new Object[]{Float.valueOf((float)this.bridge_.getScalingDensity()), this.bridge_.getWindowWidth(), this.bridge_.getWindowHeight(), n2, n3, this.scalingFactor_};
        Log.d("AdRenderer", "SCALING params: scalingDensity: %f, windowWidth: %d, windowHeight: %d, adWidth: %d, adHeight: %d, scalingFactor: %f", arrobject);
    }

    protected abstract boolean getAdState(AdState var1);

    protected boolean isAdViewDestroyed() {
        return this.isDestroyed_;
    }

    protected boolean isAdViewRemoved() {
        return this.viewRemoved_;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void launchExternalBrowserForLink(String string) {
        if (this.isAdViewDestroyed()) {
            return;
        }
        if (string == null || string.equals((Object)"")) {
            string = "about:blank";
        }
        Log.d("AdRenderer", "Final URI to show in browser: %s", string);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)string));
        intent.setFlags(268435456);
        try {
            this.bridge_.getContext().startActivity(intent);
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            String string2 = intent.getAction();
            Object[] arrobject = new Object[2];
            String string3 = string2.startsWith("market://") ? "market" : "intent";
            arrobject[0] = string3;
            arrobject[1] = string2;
            Log.w("AdRenderer", "Could not handle %s action: %s", arrobject);
            return;
        }
    }

    protected abstract void prepareToGoAway();

    protected abstract void removeView();

    protected abstract void render();

    protected abstract boolean sendCommand(String var1, Map<String, String> var2);

    protected void setAd(Ad ad2) {
        this.ad_ = ad2;
    }

    protected abstract boolean shouldReuse();

    protected static final class AdState
    extends Enum<AdState> {
        private static final /* synthetic */ AdState[] $VALUES;
        public static final /* enum */ AdState EXPANDED = new AdState();

        static {
            AdState[] arradState = new AdState[]{EXPANDED};
            $VALUES = arradState;
        }

        public static AdState valueOf(String string) {
            return (AdState)Enum.valueOf(AdState.class, (String)string);
        }

        public static AdState[] values() {
            return (AdState[])$VALUES.clone();
        }
    }

}


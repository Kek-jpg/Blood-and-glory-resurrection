/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Handler
 *  android.os.Looper
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.net.URISyntaxException
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 */
package com.amazon.device.ads;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdError;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdListener;
import com.amazon.device.ads.AdLoader;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.AdRenderer;
import com.amazon.device.ads.AdRendererFactory;
import com.amazon.device.ads.AdRequest;
import com.amazon.device.ads.AdTargetingOptions;
import com.amazon.device.ads.AmazonDeviceLauncher;
import com.amazon.device.ads.InternalAdRegistration;
import com.amazon.device.ads.Log;
import com.amazon.device.ads.Metrics;
import com.amazon.device.ads.NonScopedAdServiceTimer;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class AdBridge {
    private static final String LOG_TAG = "AdBridge";
    protected AdLayout adLayout_;
    protected AdListener adListener_ = null;
    protected AdLayout.AdSize adSize_;
    protected int adWindowHeight_ = 0;
    protected int adWindowWidth_ = 0;
    private Ad ad_;
    protected Context context_;
    protected AdRenderer currentAdRenderer_ = null;
    protected AdLoader.AdRequestTask currentAdRequestTask_ = null;
    protected boolean invalidated_;
    protected boolean isLoading_;
    protected float scalingDensity_ = 1.0f;
    protected int timeout_;
    protected String userAgent_;

    AdBridge(AdLayout adLayout, AdLayout.AdSize adSize) {
        this.context_ = adLayout.getContext();
        this.adLayout_ = adLayout;
        this.initializeAdListener();
        this.isLoading_ = false;
        this.timeout_ = 20000;
        this.adSize_ = adSize;
        this.scalingDensity_ = Float.parseFloat((String)this.getAdRegistration().deviceNativeData_.sf);
        this.userAgent_ = new WebView(this.context_.getApplicationContext()).getSettings().getUserAgentString();
    }

    private void submitMetrics() {
        Metrics.getInstance().submitMetrics(this.getAd());
    }

    protected void adClosedExpansion() {
        Log.d(LOG_TAG, "adClosedExpansion");
        new Handler(this.context_.getMainLooper()).post(new Runnable(){

            public void run() {
                if (AdBridge.this.adListener_ != null) {
                    AdBridge.this.adListener_.onAdCollapsed(AdBridge.this.adLayout_);
                    return;
                }
                Log.w(AdBridge.LOG_TAG, "AdListener is null, was expecting non-null value.");
            }
        });
    }

    protected void adExpanded() {
        Log.d(LOG_TAG, "adExpanded");
        new Handler(this.context_.getMainLooper()).post(new Runnable(){

            public void run() {
                if (AdBridge.this.adListener_ != null) {
                    AdBridge.this.adListener_.onAdExpanded(AdBridge.this.adLayout_);
                    return;
                }
                Log.w(AdBridge.LOG_TAG, "AdListener is null, was expecting non-null value.");
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void adFailed(final AdError adError) {
        boolean bl = this.getAd() != null;
        if (bl) {
            NonScopedAdServiceTimer.remove(this.getAd(), Metrics.MetricType.AD_LATENCY_RENDER);
            if (this.getAd().getIsRendering()) {
                Metrics.getInstance().incrementMetric(this.getAd(), Metrics.MetricType.AD_COUNTER_RENDERING_FATAL);
                this.getAd().setIsRendering(false);
            }
        }
        Log.d(LOG_TAG, "adFailed");
        new Handler(this.context_.getMainLooper()).post(new Runnable(){

            public void run() {
                if (AdBridge.this.adListener_ != null) {
                    AdBridge.this.adListener_.onAdFailedToLoad(AdBridge.this.adLayout_, adError);
                    return;
                }
                Log.w(AdBridge.LOG_TAG, "AdListener is null, was expecting non-null value.");
            }
        });
        if (bl) {
            AdBridge.super.submitMetrics();
        }
    }

    protected void adLoaded(final AdProperties adProperties) {
        NonScopedAdServiceTimer.stop(this.getAd(), Metrics.MetricType.AD_LATENCY_RENDER);
        this.getAd().setIsRendering(false);
        Log.d(LOG_TAG, "adLoaded");
        new Handler(this.context_.getMainLooper()).post(new Runnable(){

            public void run() {
                if (AdBridge.this.adListener_ != null) {
                    AdBridge.this.adListener_.onAdLoaded(AdBridge.this.adLayout_, adProperties);
                    return;
                }
                Log.w(AdBridge.LOG_TAG, "AdListener is null, was expecting non-null value.");
            }
        });
        AdBridge.super.submitMetrics();
    }

    protected void destroy() {
        if (this.currentAdRequestTask_ != null) {
            this.currentAdRequestTask_.releaseResources();
        }
        if (this.currentAdRenderer_ != null) {
            this.currentAdRenderer_.removeView();
            this.currentAdRenderer_.destroy();
            this.currentAdRenderer_ = null;
        }
    }

    protected Activity getActivity() {
        return (Activity)this.context_;
    }

    protected Ad getAd() {
        return this.ad_;
    }

    protected AdLayout getAdLayout() {
        return this.adLayout_;
    }

    protected InternalAdRegistration getAdRegistration() {
        return this.adLayout_.getAdRegistration();
    }

    protected AdLayout.AdSize getAdSize() {
        return this.adSize_;
    }

    protected Context getContext() {
        return this.context_;
    }

    protected float getScalingDensity() {
        return this.scalingDensity_;
    }

    protected int getTimeout() {
        return this.timeout_;
    }

    protected int getWindowHeight() {
        return this.adWindowHeight_;
    }

    protected int getWindowWidth() {
        return this.adWindowWidth_;
    }

    protected void handleApplicationDefinedSpecialURL(String string) {
        Log.i(LOG_TAG, "Special url clicked, but was not handled by SDK. Url: %s", string);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void handleResponse() {
        if (this.currentAdRenderer_ != null) {
            this.currentAdRenderer_.removeView();
        }
        for (Ad.AAXCreative aAXCreative : this.ad_.getCreativeTypes()) {
            if (AdRendererFactory.shouldCreateNewRenderer(aAXCreative, this.currentAdRenderer_)) {
                Log.d(LOG_TAG, "Creating new renderer");
                if (this.currentAdRenderer_ != null) {
                    this.currentAdRenderer_.destroy();
                }
                this.currentAdRenderer_ = AdRendererFactory.getAdRenderer(aAXCreative, this.ad_, this);
                if (this.currentAdRenderer_ == null) continue;
                break;
            }
            Log.d(LOG_TAG, "Re-using renderer");
            this.currentAdRenderer_.setAd(this.ad_);
            break;
        }
        if (this.currentAdRenderer_ == null) {
            Log.d(LOG_TAG, "No renderer returned, not loading an ad");
            this.adFailed(new AdError(-2, "No renderer returned, not loading an ad"));
            return;
        }
        this.getAd().setIsRendering(true);
        NonScopedAdServiceTimer.start(this.getAd(), Metrics.MetricType.AD_LATENCY_RENDER);
        this.currentAdRenderer_.render();
    }

    protected void initializeAdListener() {
        this.adListener_ = new AdListener(){

            @Override
            public void onAdCollapsed(AdLayout adLayout) {
                Log.d(AdBridge.LOG_TAG, "Default ad listener called - Ad Collapsed.");
            }

            @Override
            public void onAdExpanded(AdLayout adLayout) {
                Log.d(AdBridge.LOG_TAG, "Default ad listener called - Ad Will Expand.");
            }

            @Override
            public void onAdFailedToLoad(AdLayout adLayout, AdError adError) {
                Log.d(AdBridge.LOG_TAG, "Default ad listener called - Ad Failed to Load. Error code: " + adError.code_ + ", Error Message: " + adError.getResponseMessage());
            }

            @Override
            public void onAdLoaded(AdLayout adLayout, AdProperties adProperties) {
                Log.d(AdBridge.LOG_TAG, "Default ad listener called - AdLoaded.");
            }
        };
    }

    protected boolean isAdExpanded() {
        if (this.currentAdRenderer_ == null) {
            return false;
        }
        return this.currentAdRenderer_.getAdState(AdRenderer.AdState.valueOf("EXPANDED"));
    }

    protected boolean isAdLoading() {
        return this.isLoading_;
    }

    protected boolean isInvalidated() {
        return this.invalidated_;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean launchExternalActivity(String string) {
        Intent intent = new Intent();
        if (string.startsWith("intent:")) {
            try {
                Intent intent2;
                intent = intent2 = Intent.parseUri((String)string, (int)1);
            }
            catch (URISyntaxException uRISyntaxException) {
                return false;
            }
        } else {
            intent.setData(Uri.parse((String)string));
        }
        try {
            intent.setAction("android.intent.action.VIEW");
            intent.addFlags(268435456);
            this.getContext().startActivity(intent);
            return true;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            return false;
        }
    }

    protected void loadAd(AdTargetingOptions adTargetingOptions) {
        this.ad_ = new Ad();
        AdRequest adRequest = new AdRequest((AdBridge)this, adTargetingOptions, this.adSize_, this.adWindowWidth_, this.adWindowHeight_, this.userAgent_, this.timeout_);
        this.isLoading_ = true;
        this.currentAdRequestTask_ = new AdLoader.AdRequestTask();
        this.currentAdRequestTask_.execute((Object[])new AdRequest[]{adRequest});
    }

    protected void prepareToGoAway() {
        if (this.currentAdRenderer_ != null) {
            this.currentAdRenderer_.prepareToGoAway();
        }
    }

    protected boolean sendCommand(String string, HashMap<String, String> hashMap) {
        if (this.currentAdRenderer_ != null) {
            return this.currentAdRenderer_.sendCommand(string, (Map<String, String>)hashMap);
        }
        return false;
    }

    protected void setAdSize(AdLayout.AdSize adSize) {
        this.adSize_ = adSize;
    }

    protected void setIsLoading(boolean bl) {
        this.isLoading_ = bl;
    }

    protected void setListener(AdListener adListener) {
        if (adListener != null) {
            this.adListener_ = adListener;
        }
    }

    protected void setTimeout(int n2) {
        this.timeout_ = n2;
    }

    protected void setWindowDimensions(int n2, int n3) {
        this.adWindowHeight_ = n2;
        this.adWindowWidth_ = n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void specialUrlClicked(String string) {
        List list;
        String string2;
        Uri uri = Uri.parse((String)string);
        try {
            List list2;
            list = list2 = uri.getQueryParameters("intent");
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            list = null;
        }
        if (list != null && list.size() > 0) {
            Iterator iterator = list.iterator();
            do {
                if (iterator.hasNext()) continue;
                this.handleApplicationDefinedSpecialURL(string);
                return;
            } while (!this.launchExternalActivity((String)iterator.next()));
            return;
        }
        if (!AmazonDeviceLauncher.isWindowshopPresent(this.context_)) {
            this.handleApplicationDefinedSpecialURL(string);
            return;
        }
        if (!uri.getHost().equals((Object)"shopping") || (string2 = uri.getQueryParameter("app-action")) == null || string2.length() == 0) return;
        if (string2.equals((Object)"detail")) {
            String string3 = uri.getQueryParameter("asin");
            if (string3 == null || string3.length() == 0) return;
            {
                AmazonDeviceLauncher.launchWindowshopDetailPage(this.context_, string3);
                return;
            }
        }
        if (string2.equals((Object)"search")) {
            String string4 = uri.getQueryParameter("keyword");
            if (string4 == null || string4.length() == 0) return;
            {
                AmazonDeviceLauncher.luanchWindowshopSearchPage(this.context_, string4);
                return;
            }
        }
        if (!string2.equals((Object)"webview")) {
            return;
        }
        this.handleApplicationDefinedSpecialURL(string);
    }

}


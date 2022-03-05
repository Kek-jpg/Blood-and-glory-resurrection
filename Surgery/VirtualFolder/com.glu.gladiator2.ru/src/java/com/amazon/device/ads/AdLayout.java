/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.webkit.WebViewDatabase
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Enum
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.util.HashMap
 */
package com.amazon.device.ads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewDatabase;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.amazon.device.ads.AdBridge;
import com.amazon.device.ads.AdError;
import com.amazon.device.ads.AdListener;
import com.amazon.device.ads.AdTargetingOptions;
import com.amazon.device.ads.InternalAdRegistration;
import com.amazon.device.ads.Log;
import com.amazon.device.ads.ResourceLookup;
import java.util.HashMap;

public class AdLayout
extends FrameLayout {
    private static final String LOG_TAG = "AmazonAdLayout";
    private InternalAdRegistration amznAdregistration_;
    private boolean attached_ = false;
    private AdBridge bridge_;
    private Context context_;
    private boolean hasRegisterBroadcastReciever_ = false;
    private boolean hasSetAdUnitId_ = false;
    private boolean isInForeground_;
    private int lastVisibility_ = 8;
    private BroadcastReceiver screenStateReceiver_;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AdLayout(Context context, AttributeSet attributeSet) throws IllegalArgumentException {
        ResourceLookup resourceLookup;
        super(context, attributeSet);
        String string = null;
        try {
            ResourceLookup resourceLookup2;
            resourceLookup = resourceLookup2 = new ResourceLookup("adsdk", context);
        }
        catch (RuntimeException runtimeException) {
            string = "custom";
            resourceLookup = null;
        }
        if (resourceLookup != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, resourceLookup.getStyleableArray("Amazon"));
            string = typedArray.getString(resourceLookup.getStyleableId("Amazon", "adSize"));
            typedArray.recycle();
        }
        AdLayout.super.initialize(context, AdSize.fromString(string));
    }

    public AdLayout(Context context, AdSize adSize) throws IllegalArgumentException {
        super(context);
        if (adSize == null) {
            throw new IllegalArgumentException();
        }
        AdLayout.super.initialize(context, adSize);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initialize(Context context, AdSize adSize) {
        this.context_ = context;
        if (this.isInEditMode()) {
            TextView textView = new TextView(context);
            textView.setText((CharSequence)"AdLayout");
            textView.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
            textView.setGravity(17);
            this.addView((View)textView);
            return;
        }
        this.amznAdregistration_ = InternalAdRegistration.getInstance(context);
        boolean bl = this.getVisibility() == 0;
        this.isInForeground_ = bl;
        this.setHorizontalScrollBarEnabled(false);
        this.setVerticalScrollBarEnabled(false);
        if (WebViewDatabase.getInstance((Context)context) == null) {
            Log.e(LOG_TAG, "Disabling ads. Local cache file is inaccessible so ads will fail if we try to create a WebView. Details of this Android bug found at: http://code.google.com/p/android/issues/detail?id=10789");
            return;
        }
        this.bridge_ = this.createAdBridge(adSize);
    }

    private void registerScreenStateBroadcastReceiver() {
        if (this.hasRegisterBroadcastReciever_) {
            return;
        }
        this.hasRegisterBroadcastReciever_ = true;
        this.screenStateReceiver_ = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals((Object)"android.intent.action.SCREEN_OFF")) {
                    if (!AdLayout.this.isInForeground_) return;
                    {
                        AdLayout.this.bridge_.sendCommand("close", null);
                        return;
                    }
                } else {
                    if (!intent.getAction().equals((Object)"android.intent.action.USER_PRESENT")) return;
                    return;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        this.context_.registerReceiver(this.screenStateReceiver_, intentFilter);
    }

    private void unregisterScreenStateBroadcastReceiver() {
        if (this.hasRegisterBroadcastReciever_) {
            this.hasRegisterBroadcastReciever_ = false;
            this.context_.unregisterReceiver(this.screenStateReceiver_);
        }
    }

    public boolean collapseAd() {
        return this.bridge_.sendCommand("close", null);
    }

    protected AdBridge createAdBridge(AdSize adSize) {
        return new AdBridge((AdLayout)this, adSize);
    }

    public void destroy() {
        this.unregisterScreenStateBroadcastReceiver();
        if (this.bridge_ != null) {
            this.bridge_.destroy();
        }
    }

    protected InternalAdRegistration getAdRegistration() {
        return this.amznAdregistration_;
    }

    public AdSize getAdSize() {
        return this.bridge_.getAdSize();
    }

    public int getTimeout() {
        return this.bridge_.getTimeout();
    }

    public boolean isAdLoading() {
        return this.bridge_.isAdLoading();
    }

    public boolean loadAd(AdTargetingOptions adTargetingOptions) {
        if (this.bridge_.isAdLoading()) {
            Log.e(LOG_TAG, "Can't load an ad because ad loading is already in progress");
            return false;
        }
        if (this.bridge_.isAdExpanded()) {
            Log.e(LOG_TAG, "Can't load an ad because another ad is currently expanded");
            return false;
        }
        if (!this.hasSetAdUnitId_) {
            if (this.amznAdregistration_.getAppId() == null) {
                Log.e(LOG_TAG, "Can't load an ad because App Key has not been set. Did you forget to call AdRegistration.setAppKey( ... )?");
                this.bridge_.adFailed(new AdError(400, "Can't load an ad because App Key has not been set. Did you forget to call AdRegistration.setAppKey( ... )?"));
                return true;
            }
            this.hasSetAdUnitId_ = true;
        }
        if (this.bridge_.getAdSize() == null) {
            Log.e(LOG_TAG, "Ad size is not defined.");
            this.bridge_.adFailed(new AdError(400, "Ad size is not defined."));
            return true;
        }
        if (this.bridge_.getAdSize() == AdSize.AD_SIZE_CUSTOM && !adTargetingOptions.containsAdvancedOption("sz")) {
            Log.e(LOG_TAG, "Can't load an ad because a custom ad size is specified, but ad size dimensions are not configured using AdTargetingOptions.setAdvancedOption( ... ) with key 'sz'.");
            this.bridge_.adFailed(new AdError(400, "Can't load an ad because a custom ad size is specified, but ad size dimensions are not configured using AdTargetingOptions.setAdvancedOption( ... ) with key 'sz'."));
            return true;
        }
        this.amznAdregistration_.registerIfNeeded();
        this.bridge_.loadAd(adTargetingOptions);
        return true;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isInEditMode()) {
            return;
        }
        this.attached_ = true;
        this.registerScreenStateBroadcastReceiver();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.attached_ = false;
        this.unregisterScreenStateBroadcastReceiver();
        this.bridge_.prepareToGoAway();
    }

    protected void onLayout(boolean bl, int n2, int n3, int n4, int n5) {
        int n6 = n4 - n2;
        int n7 = n5 - n3;
        super.onLayout(bl, n2, n3, n4, n5);
        if (this.isInEditMode()) {
            return;
        }
        this.bridge_.setWindowDimensions(n7, n6);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onWindowVisibilityChanged(int n2) {
        if (!this.attached_ || this.lastVisibility_ == n2) return;
        {
            if (n2 != 0) {
                this.isInForeground_ = false;
                AdLayout.super.unregisterScreenStateBroadcastReceiver();
                return;
            } else {
                if (n2 != 0) return;
                {
                    this.isInForeground_ = true;
                    return;
                }
            }
        }
    }

    public void setListener(AdListener adListener) {
        this.bridge_.setListener(adListener);
    }

    public void setTimeout(int n2) {
        this.bridge_.setTimeout(n2);
    }

    public static final class AdSize
    extends Enum<AdSize> {
        private static final /* synthetic */ AdSize[] $VALUES;
        public static final /* enum */ AdSize AD_SIZE_1024x50;
        public static final /* enum */ AdSize AD_SIZE_300x250;
        public static final /* enum */ AdSize AD_SIZE_300x50;
        public static final /* enum */ AdSize AD_SIZE_320x50;
        public static final /* enum */ AdSize AD_SIZE_600x90;
        public static final /* enum */ AdSize AD_SIZE_728x90;
        public static final /* enum */ AdSize AD_SIZE_CUSTOM;
        public final int height;
        public final String size;
        public final int width;

        static {
            AD_SIZE_300x50 = new AdSize("300x50", 300, 50);
            AD_SIZE_320x50 = new AdSize("320x50", 320, 50);
            AD_SIZE_300x250 = new AdSize("300x250", 300, 250);
            AD_SIZE_600x90 = new AdSize("600x90", 600, 90);
            AD_SIZE_728x90 = new AdSize("728x90", 728, 90);
            AD_SIZE_1024x50 = new AdSize("1024x50", 1024, 50);
            AD_SIZE_CUSTOM = new AdSize("CUSTOM", -1, -1);
            AdSize[] arradSize = new AdSize[]{AD_SIZE_300x50, AD_SIZE_320x50, AD_SIZE_300x250, AD_SIZE_600x90, AD_SIZE_728x90, AD_SIZE_1024x50, AD_SIZE_CUSTOM};
            $VALUES = arradSize;
        }

        private AdSize(String string2, int n3, int n4) {
            this.size = string2;
            this.width = n3;
            this.height = n4;
        }

        public static AdSize fromString(String string) {
            if (string != null && string.length() != 0) {
                if (string.equalsIgnoreCase("300x50")) {
                    return AD_SIZE_300x50;
                }
                if (string.equalsIgnoreCase("320x50")) {
                    return AD_SIZE_320x50;
                }
                if (string.equalsIgnoreCase("300x250")) {
                    return AD_SIZE_300x250;
                }
                if (string.equalsIgnoreCase("600x90")) {
                    return AD_SIZE_600x90;
                }
                if (string.equalsIgnoreCase("728x90")) {
                    return AD_SIZE_728x90;
                }
                if (string.equalsIgnoreCase("1024x50")) {
                    return AD_SIZE_1024x50;
                }
                if (string.equalsIgnoreCase("custom")) {
                    return AD_SIZE_CUSTOM;
                }
                throw new IllegalArgumentException("Invalid ad size: '" + string + "', see documentation for available ad sizes.");
            }
            throw new IllegalArgumentException("Invalid ad size string. See documentation for available ad sizes");
        }

        public static AdSize valueOf(String string) {
            return (AdSize)Enum.valueOf(AdSize.class, (String)string);
        }

        public static AdSize[] values() {
            return (AdSize[])$VALUES.clone();
        }
    }

}


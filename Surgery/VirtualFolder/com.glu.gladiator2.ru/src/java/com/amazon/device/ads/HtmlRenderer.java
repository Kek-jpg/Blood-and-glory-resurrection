/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 *  java.util.Set
 */
package com.amazon.device.ads;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdBridge;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.AdRenderer;
import com.amazon.device.ads.Log;
import java.util.Map;
import java.util.Set;

class HtmlRenderer
extends AdRenderer {
    private static final String LOG_TAG = "HtmlRenderer";
    protected WebView adView_;
    protected boolean hasLoadedAd_ = false;

    protected HtmlRenderer(Ad ad2, AdBridge adBridge) {
        super(ad2, adBridge);
        this.adView_ = new WebView(adBridge.getContext().getApplicationContext());
        this.adView_.setHorizontalScrollBarEnabled(false);
        this.adView_.setHorizontalScrollbarOverlay(false);
        this.adView_.setVerticalScrollBarEnabled(false);
        this.adView_.setVerticalScrollbarOverlay(false);
        this.adView_.getSettings().setSupportZoom(false);
        this.adView_.getSettings().setJavaScriptEnabled(true);
        this.adView_.getSettings().setPluginsEnabled(true);
        this.adView_.setBackgroundColor(0);
        this.adView_.setWebViewClient((WebViewClient)(HtmlRenderer)this.new AdWebViewClient());
    }

    private static boolean isHoneycombVersion() {
        return Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT <= 13;
    }

    @Override
    protected void adLoaded(AdProperties adProperties) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2, 17);
        this.bridge_.getAdLayout().addView((View)this.adView_, (ViewGroup.LayoutParams)layoutParams);
        super.adLoaded(adProperties);
    }

    @Override
    protected void destroy() {
        this.adView_ = null;
        this.isDestroyed_ = true;
    }

    @Override
    protected boolean getAdState(AdRenderer.AdState adState) {
        return false;
    }

    @Override
    protected void prepareToGoAway() {
    }

    @Override
    protected void removeView() {
        this.bridge_.getAdLayout().removeView((View)this.adView_);
        this.viewRemoved_ = true;
    }

    @Override
    protected void render() {
        if (this.adView_ == null || this.isAdViewDestroyed()) {
            return;
        }
        this.adView_.clearView();
        this.hasLoadedAd_ = false;
        String string = this.ad_.getCreative();
        String string2 = ("<html><meta name=\"viewport\" content=\"width=" + this.bridge_.getWindowWidth() + ", height=" + this.bridge_.getWindowHeight() + ", initial-scale=" + this.scalingFactor_ + ", minimum-scale=" + this.scalingFactor_ + ", maximum-scale=" + this.scalingFactor_ + "\">" + string + "</html>").replace((CharSequence)"<head>", (CharSequence)"<head><script type=\"text/javascript\">htmlWillCallFinishLoad = 1;</script>");
        this.adView_.loadDataWithBaseURL("http://amazon-adsystem.amazon.com/", string2, "text/html", "utf-8", null);
    }

    @Override
    protected boolean sendCommand(String string, Map<String, String> map) {
        return true;
    }

    @Override
    protected boolean shouldReuse() {
        return true;
    }

    protected class AdWebViewClient
    extends WebViewClient {
        protected AdWebViewClient() {
        }

        public void onPageFinished(WebView webView, String string) {
            if (!HtmlRenderer.this.isDestroyed_ && !HtmlRenderer.this.hasLoadedAd_) {
                HtmlRenderer.this.hasLoadedAd_ = true;
                HtmlRenderer.this.adLoaded(HtmlRenderer.this.ad_.getProperties());
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean shouldOverrideUrlLoading(WebView webView, String string) {
            if (HtmlRenderer.this.isDestroyed_) {
                return true;
            }
            Uri uri = Uri.parse((String)string);
            if (HtmlRenderer.this.redirectHosts_.contains((Object)uri.getHost()) && !HtmlRenderer.isHoneycombVersion()) {
                return false;
            }
            if (uri.getScheme().equals((Object)"mopub")) {
                return true;
            }
            if (uri.getScheme().equals((Object)"tel") || uri.getScheme().equals((Object)"voicemail:") || uri.getScheme().equals((Object)"sms:") || uri.getScheme().equals((Object)"mailto:") || uri.getScheme().equals((Object)"geo:") || uri.getScheme().equals((Object)"google.streetview:")) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)string));
                intent.addFlags(268435456);
                try {
                    HtmlRenderer.this.bridge_.getContext().startActivity(intent);
                    do {
                        return true;
                        break;
                    } while (true);
                }
                catch (ActivityNotFoundException activityNotFoundException) {
                    Log.w(HtmlRenderer.LOG_TAG, "Could not handle intent with URI: %s", string);
                    return true;
                }
            }
            if (uri.getScheme().equals((Object)"amazonmobile")) {
                HtmlRenderer.this.bridge_.specialUrlClicked(string);
                return true;
            }
            HtmlRenderer.this.launchExternalBrowserForLink(string);
            return true;
        }
    }

}


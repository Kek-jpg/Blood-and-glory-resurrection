/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.webkit.CookieSyncManager
 *  android.webkit.WebChromeClient
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.ImageButton
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 */
package com.amazon.device.ads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.amazon.device.ads.ResourceLookup;

public class MraidBrowser
extends Activity {
    public static final String URL_EXTRA = "extra_url";
    private ImageButton mBrowserBackButton;
    private ImageButton mBrowserForwardButton;
    private ImageButton mCloseButton;
    private ImageButton mRefreshButton;
    private WebView mWebView;

    private ImageButton createButton(String string) {
        ImageButton imageButton = new ImageButton((Context)this);
        imageButton.setImageBitmap(ResourceLookup.bitmapFromJar((Context)this.getApplication(), string));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.weight = 25.0f;
        layoutParams.gravity = 16;
        imageButton.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        imageButton.setBackgroundColor(0);
        return imageButton;
    }

    private void enableCookies() {
        CookieSyncManager.createInstance((Context)this);
        CookieSyncManager.getInstance().startSync();
    }

    private void initializeButtons(Intent intent) {
        this.mBrowserBackButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MraidBrowser.this.mWebView.canGoBack()) {
                    MraidBrowser.this.mWebView.goBack();
                }
            }
        });
        this.mBrowserForwardButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MraidBrowser.this.mWebView.canGoForward()) {
                    MraidBrowser.this.mWebView.goForward();
                }
            }
        });
        this.mRefreshButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MraidBrowser.this.mWebView.reload();
            }
        });
        this.mCloseButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MraidBrowser.this.finish();
            }
        });
    }

    private void initializeEntireView(Intent intent) {
        LinearLayout linearLayout = new LinearLayout((Context)this);
        linearLayout.setId(10280);
        linearLayout.setWeightSum(100.0f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(12);
        linearLayout.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        linearLayout.setBackgroundDrawable((Drawable)new BitmapDrawable(ResourceLookup.bitmapFromJar(this.getApplicationContext(), "resources/drawable/amazon_ads_bkgrnd.png")));
        this.mBrowserBackButton = MraidBrowser.super.createButton("ad_resources/drawable/amazon_ads_leftarrow.png");
        this.mBrowserForwardButton = MraidBrowser.super.createButton("ad_resources/drawable/amazon_ads_rightarrow.png");
        this.mRefreshButton = MraidBrowser.super.createButton("ad_resources/drawable/amazon_ads_refresh.png");
        this.mCloseButton = MraidBrowser.super.createButton("ad_resources/drawable/amazon_ads_close.png");
        linearLayout.addView((View)this.mBrowserBackButton);
        linearLayout.addView((View)this.mBrowserForwardButton);
        linearLayout.addView((View)this.mRefreshButton);
        linearLayout.addView((View)this.mCloseButton);
        this.mWebView = new WebView((Context)this);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams2.addRule(2, linearLayout.getId());
        this.mWebView.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        RelativeLayout relativeLayout = new RelativeLayout((Context)this);
        relativeLayout.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -2));
        relativeLayout.addView((View)this.mWebView);
        relativeLayout.addView((View)linearLayout);
        LinearLayout linearLayout2 = new LinearLayout((Context)this);
        linearLayout2.setOrientation(1);
        linearLayout2.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -1));
        linearLayout2.addView((View)relativeLayout);
        this.setContentView((View)linearLayout2);
    }

    private void initializeWebView(Intent intent) {
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.loadUrl(intent.getStringExtra(URL_EXTRA));
        this.mWebView.setWebViewClient(new WebViewClient(){

            /*
             * Enabled aggressive block sorting
             */
            public void onPageFinished(WebView webView, String string) {
                super.onPageFinished(webView, string);
                if (webView.canGoBack()) {
                    MraidBrowser.this.mBrowserBackButton.setImageBitmap(ResourceLookup.bitmapFromJar(MraidBrowser.this.getApplicationContext(), "ad_resources/drawable/amazon_ads_leftarrow.png"));
                } else {
                    MraidBrowser.this.mBrowserBackButton.setImageBitmap(ResourceLookup.bitmapFromJar(MraidBrowser.this.getApplicationContext(), "ad_resources/drawable/amazon_ads_unleftarrow.png"));
                }
                if (webView.canGoForward()) {
                    MraidBrowser.this.mBrowserForwardButton.setImageBitmap(ResourceLookup.bitmapFromJar(MraidBrowser.this.getApplicationContext(), "ad_resources/drawable/amazon_ads_rightarrow.png"));
                    return;
                }
                MraidBrowser.this.mBrowserForwardButton.setImageBitmap(ResourceLookup.bitmapFromJar(MraidBrowser.this.getApplicationContext(), "ad_resources/drawable/amazon_ads_unrightarrow.png"));
            }

            public void onPageStarted(WebView webView, String string, Bitmap bitmap) {
                super.onPageStarted(webView, string, bitmap);
                MraidBrowser.this.mBrowserForwardButton.setImageBitmap(ResourceLookup.bitmapFromJar(MraidBrowser.this.getApplicationContext(), "ad_resources/drawable/amazon_ads_unrightarrow.png"));
            }

            public void onReceivedError(WebView webView, int n2, String string, String string2) {
                Toast.makeText((Context)((Activity)webView.getContext()), (CharSequence)("MRAID error: " + string), (int)0).show();
            }

            /*
             * Enabled aggressive block sorting
             */
            public boolean shouldOverrideUrlLoading(WebView webView, String string) {
                if (!(string != null && (string.startsWith("market:") || string.startsWith("tel:") || string.startsWith("voicemail:") || string.startsWith("sms:") || string.startsWith("mailto:") || string.startsWith("geo:") || string.startsWith("google.streetview:")))) {
                    return false;
                }
                MraidBrowser.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)string)));
                return true;
            }
        });
        this.mWebView.setWebChromeClient(new WebChromeClient(){

            public void onProgressChanged(WebView webView, int n2) {
                Activity activity = (Activity)webView.getContext();
                activity.setTitle((CharSequence)"Loading...");
                activity.setProgress(n2 * 100);
                if (n2 == 100) {
                    activity.setTitle((CharSequence)webView.getUrl());
                }
            }
        });
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.getWindow().requestFeature(2);
        this.getWindow().setFeatureInt(2, -1);
        Intent intent = this.getIntent();
        MraidBrowser.super.initializeEntireView(intent);
        MraidBrowser.super.initializeWebView(intent);
        MraidBrowser.super.initializeButtons(intent);
        MraidBrowser.super.enableCookies();
    }

    protected void onPause() {
        super.onPause();
        CookieSyncManager.getInstance().stopSync();
    }

    protected void onResume() {
        super.onResume();
        CookieSyncManager.getInstance().startSync();
    }

}


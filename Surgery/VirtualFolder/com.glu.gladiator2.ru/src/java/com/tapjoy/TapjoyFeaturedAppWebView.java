/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.ProgressBar
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Void
 */
package com.tapjoy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.tapjoy.TJCOffersWebView;
import com.tapjoy.TapjoyConnectCore;
import com.tapjoy.TapjoyLog;

public class TapjoyFeaturedAppWebView
extends Activity {
    final String TAPJOY_FEATURED_APP = "Full Screen Ad";
    private String fullScreenAdURL = "";
    private ProgressBar progressBar;
    private boolean resumeCalled = false;
    private String urlParams = "";
    private String userID = "";
    private WebView webView = null;

    private void finishActivity() {
        this.finish();
    }

    private void showOffers() {
        TapjoyLog.i("Full Screen Ad", "Showing offers, userID: " + this.userID);
        TapjoyLog.i("Full Screen Ad", "Showing offers, URL PARAMS: " + this.urlParams);
        Intent intent = new Intent((Context)this, TJCOffersWebView.class);
        intent.putExtra("USER_ID", this.userID);
        intent.putExtra("URL_PARAMS", this.urlParams);
        this.startActivity(intent);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.webView != null) {
            new RefreshTask((TapjoyFeaturedAppWebView)this, null).execute((Object[])new Void[0]);
        }
    }

    protected void onCreate(Bundle bundle) {
        Bundle bundle2 = this.getIntent().getExtras();
        this.userID = bundle2.getString("USER_ID");
        this.urlParams = bundle2.getString("URL_PARAMS");
        this.fullScreenAdURL = bundle2.getString("FULLSCREEN_AD_URL");
        this.fullScreenAdURL = this.fullScreenAdURL.replaceAll(" ", "%20");
        super.onCreate(bundle);
        this.requestWindowFeature(1);
        RelativeLayout relativeLayout = new RelativeLayout((Context)this);
        this.webView = new WebView((Context)this);
        this.webView.setWebViewClient((WebViewClient)new TapjoyWebViewClient((TapjoyFeaturedAppWebView)this, null));
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.progressBar = new ProgressBar((Context)this, null, 16842874);
        this.progressBar.setVisibility(0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        this.progressBar.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        relativeLayout.addView((View)this.webView, -1, -1);
        relativeLayout.addView((View)this.progressBar);
        this.setContentView((View)relativeLayout);
        this.webView.loadUrl(this.fullScreenAdURL);
        TapjoyLog.i("Full Screen Ad", "Opening Full Screen AD URL = [" + this.fullScreenAdURL + "]");
    }

    protected void onResume() {
        super.onResume();
        if (this.resumeCalled && TapjoyConnectCore.getInstance() != null) {
            TapjoyLog.i("Full Screen Ad", "call connect");
            TapjoyConnectCore.getInstance().callConnect();
        }
        this.resumeCalled = true;
    }

    private class RefreshTask
    extends AsyncTask<Void, Void, Boolean> {
        final /* synthetic */ TapjoyFeaturedAppWebView this$0;

        private RefreshTask(TapjoyFeaturedAppWebView tapjoyFeaturedAppWebView) {
            this.this$0 = tapjoyFeaturedAppWebView;
        }

        /* synthetic */ RefreshTask(TapjoyFeaturedAppWebView tapjoyFeaturedAppWebView, 1 var2_2) {
            super(tapjoyFeaturedAppWebView);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        protected /* varargs */ Boolean doInBackground(Void ... arrvoid) {
            try {
                Thread.sleep((long)200L);
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
                return true;
            }
            do {
                return true;
                break;
            } while (true);
        }

        protected void onPostExecute(Boolean bl) {
            if (this.this$0.webView != null) {
                this.this$0.webView.loadUrl("javascript:window.onorientationchange();");
            }
        }
    }

    private class TapjoyWebViewClient
    extends WebViewClient {
        final /* synthetic */ TapjoyFeaturedAppWebView this$0;

        private TapjoyWebViewClient(TapjoyFeaturedAppWebView tapjoyFeaturedAppWebView) {
            this.this$0 = tapjoyFeaturedAppWebView;
        }

        /* synthetic */ TapjoyWebViewClient(TapjoyFeaturedAppWebView tapjoyFeaturedAppWebView, 1 var2_2) {
            super(tapjoyFeaturedAppWebView);
        }

        public void onPageFinished(WebView webView, String string2) {
            this.this$0.progressBar.setVisibility(8);
        }

        public void onPageStarted(WebView webView, String string2, Bitmap bitmap) {
            this.this$0.progressBar.setVisibility(0);
            this.this$0.progressBar.bringToFront();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean shouldOverrideUrlLoading(WebView webView, String string2) {
            TapjoyLog.i("Full Screen Ad", "URL = [" + string2 + "]");
            if (string2.contains((CharSequence)"showOffers")) {
                TapjoyLog.i("Full Screen Ad", "show offers");
                this.this$0.showOffers();
                do {
                    return true;
                    break;
                } while (true);
            }
            if (string2.contains((CharSequence)"dismiss")) {
                TapjoyLog.i("Full Screen Ad", "dismiss");
                this.this$0.finishActivity();
                return true;
            }
            if (string2.contains((CharSequence)"ws.tapjoyads.com")) {
                TapjoyLog.i("Full Screen Ad", "Open redirecting URL = [" + string2 + "]");
                webView.loadUrl(string2);
                return true;
            }
            TapjoyLog.i("Full Screen Ad", "Opening URL in new browser = [" + string2 + "]");
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)string2));
            this.this$0.startActivity(intent);
            return true;
        }
    }

}


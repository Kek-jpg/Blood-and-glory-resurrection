/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.graphics.Bitmap
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
import com.tapjoy.TapjoyLog;

public class TapjoyReEngagementAdWebView
extends Activity {
    final String TAPJOY_RE_ENGAGEMENT_AD = "Re-engagement Ad";
    private String htmlRawData = "";
    private ProgressBar progressBar;
    private WebView webView = null;

    private void finishActivity() {
        this.finish();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.webView != null) {
            new RefreshTask((TapjoyReEngagementAdWebView)this, null).execute((Object[])new Void[0]);
        }
    }

    protected void onCreate(Bundle bundle) {
        this.htmlRawData = this.getIntent().getExtras().getString("RE_ENGAGEMENT_HTML_DATA");
        super.onCreate(bundle);
        this.requestWindowFeature(1);
        RelativeLayout relativeLayout = new RelativeLayout((Context)this);
        this.webView = new WebView((Context)this);
        this.webView.setWebViewClient((WebViewClient)new TapjoyWebViewClient((TapjoyReEngagementAdWebView)this, null));
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.progressBar = new ProgressBar((Context)this, null, 16842874);
        this.progressBar.setVisibility(0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        this.progressBar.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        relativeLayout.addView((View)this.webView, -1, -1);
        relativeLayout.addView((View)this.progressBar);
        this.setContentView((View)relativeLayout);
        this.webView.loadDataWithBaseURL("https://ws.tapjoyads.com/", this.htmlRawData, "text/html", "utf-8", null);
        TapjoyLog.i("Re-engagement Ad", "Opening Re-engagement ad = [" + this.htmlRawData + "]");
    }

    private class RefreshTask
    extends AsyncTask<Void, Void, Boolean> {
        final /* synthetic */ TapjoyReEngagementAdWebView this$0;

        private RefreshTask(TapjoyReEngagementAdWebView tapjoyReEngagementAdWebView) {
            this.this$0 = tapjoyReEngagementAdWebView;
        }

        /* synthetic */ RefreshTask(TapjoyReEngagementAdWebView tapjoyReEngagementAdWebView, 1 var2_2) {
            super(tapjoyReEngagementAdWebView);
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
        final /* synthetic */ TapjoyReEngagementAdWebView this$0;

        private TapjoyWebViewClient(TapjoyReEngagementAdWebView tapjoyReEngagementAdWebView) {
            this.this$0 = tapjoyReEngagementAdWebView;
        }

        /* synthetic */ TapjoyWebViewClient(TapjoyReEngagementAdWebView tapjoyReEngagementAdWebView, 1 var2_2) {
            super(tapjoyReEngagementAdWebView);
        }

        public void onPageFinished(WebView webView, String string2) {
            this.this$0.progressBar.setVisibility(8);
        }

        public void onPageStarted(WebView webView, String string2, Bitmap bitmap) {
            this.this$0.progressBar.setVisibility(0);
            this.this$0.progressBar.bringToFront();
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String string2) {
            TapjoyLog.i("Re-engagement Ad", "URL = [" + string2 + "]");
            if (string2.startsWith("http://ok")) {
                TapjoyLog.i("Re-engagement Ad", "dismiss");
                this.this$0.finishActivity();
            }
            return true;
        }
    }

}


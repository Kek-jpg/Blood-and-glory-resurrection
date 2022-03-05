/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.View
 *  android.webkit.WebChromeClient
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Method
 */
package com.glu.plugins;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.glu.plugins.AJavaTools;
import com.unity3d.player.UnityPlayer;
import java.lang.reflect.Method;

public class AJTInternet {
    private static String WEBVIEW_GO;
    private static String WEBVIEW_URL;

    public static void LoadWebView(String string2, String string3) {
        AJavaTools.Log("LoadWebView( " + string2 + ", " + string3 + " )");
        WEBVIEW_URL = string2;
        WEBVIEW_GO = string3;
        UnityPlayer.currentActivity.startActivity(new Intent((Context)UnityPlayer.currentActivity, WebActivity.class));
    }

    public static class WebActivity
    extends Activity {
        private static WebView webview;

        protected void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            webview = new WebView((Context)this);
            WebSettings webSettings = webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setPluginsEnabled(true);
            webSettings.setAllowFileAccess(true);
            webview.setBackgroundColor(0);
            webview.setWebChromeClient(new WebChromeClient());
            webview.setWebViewClient(new WebViewClient(){

                public boolean shouldOverrideUrlLoading(WebView webView, String string2) {
                    if (string2.startsWith("market://") || string2.startsWith("amzn://")) {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)string2));
                        UnityPlayer.currentActivity.startActivity(intent);
                        return true;
                    }
                    if (string2.startsWith("unity://")) {
                        if (WEBVIEW_GO != null && !WEBVIEW_GO.equals((Object)"")) {
                            UnityPlayer.UnitySendMessage(WEBVIEW_GO, "onWebViewEvent", string2.substring(3 + string2.indexOf("://")));
                        }
                        WebActivity.this.finish();
                        return true;
                    }
                    return false;
                }
            });
            this.setContentView((View)webview);
            webview.loadUrl(WEBVIEW_URL);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean onKeyDown(int n, KeyEvent keyEvent) {
            if (keyEvent.getAction() != 0) return super.onKeyDown(n, keyEvent);
            switch (n) {
                default: {
                    return super.onKeyDown(n, keyEvent);
                }
                case 4: 
            }
            if (webview.canGoBack()) {
                webview.goBack();
                do {
                    return true;
                    break;
                } while (true);
            }
            if (WEBVIEW_GO != null && !WEBVIEW_GO.equals((Object)"")) {
                UnityPlayer.UnitySendMessage(WEBVIEW_GO, "onWebViewEvent", "back");
            }
            this.finish();
            return true;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onPause() {
            super.onPause();
            try {
                Class.forName((String)"android.webkit.WebView").getMethod("onPause", (Class[])null).invoke((Object)webview, (Object[])null);
                return;
            }
            catch (Exception exception) {
                if (!AJavaTools.DEBUG) return;
                exception.printStackTrace();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onResume() {
            super.onPause();
            try {
                Class.forName((String)"android.webkit.WebView").getMethod("onResume", (Class[])null).invoke((Object)webview, (Object[])null);
                return;
            }
            catch (Exception exception) {
                if (!AJavaTools.DEBUG) return;
                exception.printStackTrace();
                return;
            }
        }

    }

}


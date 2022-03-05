/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.webkit.WebSettings
 *  android.webkit.WebSettings$PluginState
 *  android.webkit.WebSettings$ZoomDensity
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 */
package com.wildtangent.brandboost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.wildtangent.brandboost.BrandBoostActivity;
import com.wildtangent.brandboost.JavascriptInterface;
import com.wildtangent.brandboost.a.a;
import com.wildtangent.brandboost.d;
import com.wildtangent.brandboost.util.b;

public class c
extends WebView {
    private static final String a = "com.wildtangent.brandboost__" + c.class.getSimpleName();
    private final JavascriptInterface b;
    private final BrandBoostActivity c;
    private final Runnable d;
    private final a e;
    private final Handler f;

    public c(BrandBoostActivity brandBoostActivity, Runnable runnable) {
        super((Context)brandBoostActivity);
        this.f = new d((c)this);
        c.super.c();
        this.setFocusable(true);
        this.setFocusableInTouchMode(false);
        this.setScrollBarStyle(0);
        this.setScrollbarFadingEnabled(true);
        this.setVerticalScrollbarOverlay(true);
        this.setHorizontalScrollbarOverlay(true);
        this.e = new a(brandBoostActivity, this.f);
        this.b = new JavascriptInterface(brandBoostActivity, (WebView)this, this.e, this.f);
        this.addJavascriptInterface((Object)this.b, "NativeDialogs");
        this.addJavascriptInterface((Object)this.b, "Library");
        this.addJavascriptInterface((Object)this.b, "EventManager");
        this.addJavascriptInterface((Object)this.b, "Environment");
        this.addJavascriptInterface((Object)this.b, "WindowManager");
        this.setWebViewClient(new WebViewClient(){

            public void onReceivedError(WebView webView, int n2, String string, String string2) {
                b.d(a, "onReceivedError: code: " + n2 + ", description: " + string + ", URL: " + string2);
                c.this.c.a();
            }
        });
        this.c = brandBoostActivity;
        this.d = runnable;
        String string = brandBoostActivity.getIntent().getData().toString();
        b.a(a, "Loading url: " + string);
        this.loadUrl(string);
    }

    private Runnable a(final String string, final String string2) {
        return new Runnable(){

            public void run() {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("javascript:");
                stringBuffer.append(string);
                stringBuffer.append('(');
                stringBuffer.append(string2);
                stringBuffer.append(')');
                c.this.loadUrl(stringBuffer.toString());
            }
        };
    }

    private void c() {
        WebSettings webSettings = this.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setBlockNetworkLoads(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setCacheMode(0);
        webSettings.setDatabaseEnabled(false);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        webSettings.setDomStorageEnabled(false);
        webSettings.setGeolocationEnabled(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLightTouchEnabled(false);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setNavDump(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setSaveFormData(true);
        webSettings.setSavePassword(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setSupportZoom(false);
    }

    public void a() {
        this.e.b();
        this.loadUrl("javascript:brandboost.ClearVideo();");
        this.loadUrl("javascript:brandboost.ClearRichMediaFrame();");
    }

    public void a(int n2, int n3) {
        this.b.setWidthAndHeight(n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(String string, boolean bl) {
        b.a(a, "Running itemGranted JS");
        StringBuilder stringBuilder = new StringBuilder("javascript:itemGranted('");
        stringBuilder.append(string + "', ");
        String string2 = bl ? "true" : "false";
        stringBuilder.append(string2);
        stringBuilder.append(");");
        this.loadUrl(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean a(Message message) {
        Bundle bundle = message.getData();
        b.a(a, "Got event, what: " + message.what);
        switch (message.what) {
            default: {
                return false;
            }
            case 1000: {
                if (bundle == null) return true;
                StringBuilder stringBuilder = new StringBuilder("javascript:");
                stringBuilder.append(bundle.getString("callback"));
                stringBuilder.append("()");
                this.loadUrl(stringBuilder.toString());
                return true;
            }
            case 1001: {
                if (bundle == null) return true;
                StringBuilder stringBuilder = new StringBuilder("javascript:");
                stringBuilder.append(bundle.getString("callback"));
                stringBuilder.append('(');
                stringBuilder.append(bundle.getDouble("progress"));
                stringBuilder.append(')');
                b.a(a, "Sending progress to JS: " + stringBuilder.toString());
                this.loadUrl(stringBuilder.toString());
                return true;
            }
            case 3585: {
                b.c(a, "Handling network disconnects");
                StringBuilder stringBuilder = new StringBuilder("javascript:");
                stringBuilder.append(bundle.getString("callback"));
                stringBuilder.append('(');
                stringBuilder.append(bundle.getInt("code"));
                stringBuilder.append(",'");
                stringBuilder.append(bundle.getString("location"));
                stringBuilder.append("')");
                this.loadUrl(stringBuilder.toString());
                return true;
            }
            case 3586: {
                b.c(a, "Not handling network disconnects");
            }
            case 1: {
                b.a(a, "Page load complete. Setting WebView as main content.");
                this.d.run();
                return true;
            }
            case 2: {
                b.a(a, "WHAT_SHOW_ALERT");
                String string = bundle.getString("KEY_TITLE_TEXT");
                String string2 = bundle.getString("KEY_MSG_TXT");
                String string3 = bundle.getString("KEY_OK_BUTTON");
                String string4 = bundle.getString("KEY_CALLBACK");
                Runnable runnable = c.super.a(string4, "0");
                Runnable runnable2 = c.super.a(string4, "-1");
                com.wildtangent.brandboost.b.a((Context)this.c, string, string2, string3, null, runnable, null, runnable2);
                return true;
            }
            case 3: {
                b.a(a, "WHAT_SHOW_CONFIRM");
                String string = bundle.getString("KEY_TITLE_TEXT");
                String string5 = bundle.getString("KEY_MSG_TXT");
                String string6 = bundle.getString("KEY_YES_BUTTON");
                String string7 = bundle.getString("KEY_NO_BUTTON");
                String string8 = bundle.getString("KEY_CALLBACK");
                Runnable runnable = c.super.a(string8, "0");
                Runnable runnable3 = c.super.a(string8, "1");
                Runnable runnable4 = c.super.a(string8, "-1");
                com.wildtangent.brandboost.b.a((Context)this.c, string, string5, string6, string7, runnable, runnable3, runnable4);
                return true;
            }
            case 4: 
        }
        String string = bundle.getString("KEY_URL");
        b.c(a, "Spawning external browser for url: " + string);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)string));
        this.c.startActivity(intent);
        return true;
    }

}


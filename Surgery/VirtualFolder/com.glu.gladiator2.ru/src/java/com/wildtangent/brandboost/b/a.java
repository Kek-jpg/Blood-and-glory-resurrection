/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.WindowManager
 *  android.webkit.WebSettings
 *  android.webkit.WebSettings$LayoutAlgorithm
 *  android.webkit.WebSettings$PluginState
 *  android.webkit.WebSettings$ZoomDensity
 *  android.webkit.WebView
 *  java.lang.System
 *  java.util.Random
 */
package com.wildtangent.brandboost.b;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.util.Random;

public final class a
extends WebView {
    private Activity a = null;

    public a(Activity activity) {
        super((Context)activity);
        this.a = activity;
        this.setBackgroundColor(0);
        this.setId(new Random((long)((int)System.currentTimeMillis())).nextInt());
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        a.super.a();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a() {
        WebSettings webSettings = this.getSettings();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        switch (displayMetrics.densityDpi) {
            default: {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
                break;
            }
            case 240: {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
                break;
            }
            case 160: {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
                break;
            }
            case 120: {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
            }
        }
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setBlockNetworkLoads(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setCacheMode(2);
        webSettings.setDatabaseEnabled(false);
        webSettings.setDomStorageEnabled(false);
        webSettings.setGeolocationEnabled(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setJavaScriptEnabled(false);
        webSettings.setLightTouchEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setNavDump(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setSaveFormData(true);
        webSettings.setSavePassword(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setSupportZoom(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webSettings.setLoadWithOverviewMode(false);
        webSettings.setUseWideViewPort(false);
    }
}


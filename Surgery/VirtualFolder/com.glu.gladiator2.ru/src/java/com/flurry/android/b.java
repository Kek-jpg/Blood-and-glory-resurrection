/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  java.lang.String
 */
package com.flurry.android;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.flurry.android.aw;

final class b
extends WebViewClient {
    private b() {
    }

    /* synthetic */ b(aw aw2) {
    }

    public final boolean shouldOverrideUrlLoading(WebView webView, String string) {
        webView.loadUrl(string);
        return true;
    }
}


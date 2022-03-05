/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.ImageView
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  java.lang.Class
 *  java.lang.ClassCastException
 *  java.lang.String
 */
package com.flurry.android;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.flurry.android.b;

final class aw
extends RelativeLayout
implements View.OnClickListener {
    private final String a;
    private WebView b;
    private ImageView c;
    private ImageView d;
    private ImageView e;

    public aw(Context context, String string) {
        super(context);
        this.a = this.getClass().getSimpleName();
        this.b = new WebView(context);
        this.b.getSettings().setJavaScriptEnabled(true);
        this.b.setWebViewClient((WebViewClient)new b((aw)this));
        this.b.loadUrl(string);
        this.c = new ImageView(context);
        this.c.setId(0);
        this.c.setImageDrawable(this.getResources().getDrawable(17301560));
        this.c.setOnClickListener((View.OnClickListener)this);
        this.d = new ImageView(context);
        this.d.setId(1);
        this.d.setImageDrawable(this.getResources().getDrawable(17301580));
        this.d.setOnClickListener((View.OnClickListener)this);
        this.e = new ImageView(context);
        this.e.setId(2);
        this.e.setImageDrawable(this.getResources().getDrawable(17301565));
        this.e.setOnClickListener((View.OnClickListener)this);
        this.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.addView((View)this.b);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        this.addView((View)this.c, (ViewGroup.LayoutParams)layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(9);
        this.addView((View)this.d, (ViewGroup.LayoutParams)layoutParams2);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.addRule(11);
        this.addView((View)this.e, (ViewGroup.LayoutParams)layoutParams3);
    }

    public final void onClick(View view) {
        switch (view.getId()) {
            default: {
                return;
            }
            case 0: {
                try {
                    ((Activity)this.getContext()).finish();
                    return;
                }
                catch (ClassCastException classCastException) {
                    classCastException.toString();
                    return;
                }
            }
            case 2: {
                this.b.goForward();
                return;
            }
            case 1: 
        }
        this.b.goBack();
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.amazon.device.ads;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.amazon.device.ads.Log;
import com.amazon.device.ads.MraidAbstractController;
import com.amazon.device.ads.MraidBrowser;
import com.amazon.device.ads.MraidDisplayController;
import com.amazon.device.ads.MraidRenderer;
import com.amazon.device.ads.MraidView;

class MraidBrowserController
extends MraidAbstractController {
    private static final String LOGTAG = "MraidBrowserController";

    MraidBrowserController(MraidView mraidView) {
        super(mraidView);
    }

    protected void executeAmazonMobileCallback(MraidView mraidView, String string) {
        if (mraidView.getOnSpecialUrlClickListener() != null) {
            mraidView.getDisplayController().close();
            mraidView.getOnSpecialUrlClickListener().onSpecialUrlClick(mraidView, string);
        }
    }

    protected void open(String string) {
        Log.d(LOGTAG, "Opening in-app browser: %s", string);
        MraidView mraidView = this.getView();
        Uri uri = Uri.parse((String)string);
        if (uri.getScheme().equals((Object)"amazonmobile")) {
            this.executeAmazonMobileCallback(mraidView, string);
            return;
        }
        String string2 = uri.getQueryParameter("d.url");
        if (string2 == null) {
            string2 = string;
        }
        if (string2.startsWith("amazonmobile:")) {
            this.executeAmazonMobileCallback(mraidView, string2);
            return;
        }
        if (mraidView.getOnOpenListener() != null) {
            mraidView.getOnOpenListener().onOpen(mraidView);
        }
        if (string2.startsWith("http:")) {
            Context context = this.getView().getContext();
            Intent intent = new Intent(context, MraidBrowser.class);
            intent.putExtra("extra_url", string2);
            intent.addFlags(268435456);
            context.startActivity(intent);
            return;
        }
        this.getView().getRenderer().launchExternalBrowserForLink(string2);
    }
}


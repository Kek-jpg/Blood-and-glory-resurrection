/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.util.Log
 *  android.view.View
 *  com.millennialmedia.android.MMAdView
 *  com.millennialmedia.android.MMAdView$MMAdListener
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Hashtable
 */
package com.flurry.android;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.flurry.android.AdCreative;
import com.flurry.android.AdNetworkView;
import com.flurry.android.af;
import com.flurry.android.as;
import com.flurry.android.bb;
import com.flurry.android.be;
import com.millennialmedia.android.MMAdView;
import java.util.Hashtable;

final class ag
extends AdNetworkView {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    ag(Context context, be be2, bb bb2, AdCreative adCreative) {
        ApplicationInfo applicationInfo;
        String string;
        super(context, be2, bb2, adCreative);
        try {
            ApplicationInfo applicationInfo2;
            applicationInfo = applicationInfo2 = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.d((String)"FlurryAgent", (String)"Cannot find manifest for app");
            applicationInfo = null;
        }
        sAdNetworkApiKey = string = applicationInfo.metaData.getString("com.flurry.millennial.MYAPID");
        if (string == null) {
            Log.d((String)"FlurryAgent", (String)"com.flurry.millennial.MYAPID not set in manifest");
        }
        this.setFocusable(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void initLayout(Context context) {
        String string;
        if (this.fAdCreative.getFormat().equals((Object)"takeover")) {
            MMAdView mMAdView = new MMAdView((Activity)context, sAdNetworkApiKey, "MMFullScreenAdTransition", true, null);
            mMAdView.setId(1897808289);
            mMAdView.setListener((MMAdView.MMAdListener)new as((ag)this));
            mMAdView.fetch();
            return;
        }
        int n2 = this.fAdCreative.getHeight();
        int n3 = this.fAdCreative.getWidth();
        if (n3 >= 320 && n2 >= 50) {
            Log.d((String)"FlurryAgent", (String)"Determined Millennial AdSize as MMBannerAdBottom");
            string = "MMBannerAdBottom";
        } else if (n3 >= 300 && n2 >= 250) {
            Log.d((String)"FlurryAgent", (String)"Determined Millennial AdSize as MMBannerAdRectangle");
            string = "MMBannerAdRectangle";
        } else {
            Log.d((String)"FlurryAgent", (String)"Could not find Millennial AdSize that matches size");
            string = null;
        }
        if (string != null) {
            MMAdView mMAdView = new MMAdView((Activity)context, sAdNetworkApiKey, string, 0);
            mMAdView.setId(1897808289);
            mMAdView.setListener((MMAdView.MMAdListener)new af((ag)this));
            this.addView((View)mMAdView);
            return;
        }
        Log.d((String)"FlurryAgent", (String)"**********Could not load Millennial Ad");
    }
}


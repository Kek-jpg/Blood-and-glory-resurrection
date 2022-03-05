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
 *  com.jumptap.adtag.JtAdInterstitial
 *  com.jumptap.adtag.JtAdView
 *  com.jumptap.adtag.JtAdViewListener
 *  com.jumptap.adtag.JtAdWidgetSettings
 *  com.jumptap.adtag.JtAdWidgetSettingsFactory
 *  com.jumptap.adtag.utils.JtException
 *  java.lang.Object
 *  java.lang.String
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
import com.flurry.android.ap;
import com.flurry.android.bb;
import com.flurry.android.be;
import com.flurry.android.s;
import com.jumptap.adtag.JtAdInterstitial;
import com.jumptap.adtag.JtAdView;
import com.jumptap.adtag.JtAdViewListener;
import com.jumptap.adtag.JtAdWidgetSettings;
import com.jumptap.adtag.JtAdWidgetSettingsFactory;
import com.jumptap.adtag.utils.JtException;

final class g
extends AdNetworkView {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    g(Context context, be be2, bb bb2, AdCreative adCreative) {
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
        sAdNetworkApiKey = string = applicationInfo.metaData.getString("com.flurry.jumptap.PUBLISHER_ID");
        if (string == null) {
            Log.d((String)"FlurryAgent", (String)"com.flurry.jumptap.PUBLISHER_ID not set in manifest");
        }
        this.setFocusable(true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void initLayout(Context context) {
        JtAdView jtAdView;
        if (this.fAdCreative.getFormat().equals((Object)"takeover")) {
            JtAdInterstitial jtAdInterstitial;
            JtAdWidgetSettings jtAdWidgetSettings = JtAdWidgetSettingsFactory.createWidgetSettings();
            jtAdWidgetSettings.setPublisherId(sAdNetworkApiKey);
            jtAdWidgetSettings.setRefreshPeriod(0);
            jtAdWidgetSettings.setShouldSendLocation(false);
            try {
                JtAdInterstitial jtAdInterstitial2;
                jtAdInterstitial = jtAdInterstitial2 = new JtAdInterstitial((Context)((Activity)context), jtAdWidgetSettings);
            }
            catch (JtException jtException) {
                Log.d((String)"FlurryAgent", (String)"Jumptap JtException when creating ad object.");
                jtAdInterstitial = null;
            }
            jtAdInterstitial.setAdViewListener((JtAdViewListener)new ap((g)this));
            ((Activity)context).setContentView((View)jtAdInterstitial);
            return;
        }
        JtAdWidgetSettings jtAdWidgetSettings = JtAdWidgetSettingsFactory.createWidgetSettings();
        jtAdWidgetSettings.setPublisherId(sAdNetworkApiKey);
        jtAdWidgetSettings.setRefreshPeriod(0);
        jtAdWidgetSettings.setShouldSendLocation(false);
        try {
            JtAdView jtAdView2;
            jtAdView = jtAdView2 = new JtAdView((Context)((Activity)context), jtAdWidgetSettings);
        }
        catch (JtException jtException) {
            Log.d((String)"FlurryAgent", (String)"Jumptap JtException when creating ad object.");
            jtAdView = null;
        }
        jtAdView.setAdViewListener((JtAdViewListener)new s((g)this));
        this.addView((View)jtAdView);
    }
}


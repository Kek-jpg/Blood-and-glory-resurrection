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
 *  com.mobclix.android.sdk.MobclixAdViewListener
 *  com.mobclix.android.sdk.MobclixFullScreenAdView
 *  com.mobclix.android.sdk.MobclixFullScreenAdViewListener
 *  com.mobclix.android.sdk.MobclixIABRectangleMAdView
 *  com.mobclix.android.sdk.MobclixMMABannerXLAdView
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
import com.flurry.android.ah;
import com.flurry.android.bb;
import com.flurry.android.be;
import com.flurry.android.z;
import com.mobclix.android.sdk.MobclixAdViewListener;
import com.mobclix.android.sdk.MobclixFullScreenAdView;
import com.mobclix.android.sdk.MobclixFullScreenAdViewListener;
import com.mobclix.android.sdk.MobclixIABRectangleMAdView;
import com.mobclix.android.sdk.MobclixMMABannerXLAdView;

final class ac
extends AdNetworkView {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    ac(Context context, be be2, bb bb2, AdCreative adCreative) {
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
        sAdNetworkApiKey = string = applicationInfo.metaData.getString("com.mobclix.APPLICATION_ID");
        if (string == null) {
            Log.d((String)"MobclixTestApp", (String)"com.mobclix.APPLICATION_ID not set in manifest");
        }
        this.setFocusable(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void initLayout(Context context) {
        MobclixMMABannerXLAdView mobclixMMABannerXLAdView;
        if (this.fAdCreative.getFormat().equals((Object)"takeover")) {
            MobclixFullScreenAdView mobclixFullScreenAdView = new MobclixFullScreenAdView((Activity)context);
            mobclixFullScreenAdView.addMobclixAdViewListener((MobclixFullScreenAdViewListener)new z((ac)this));
            mobclixFullScreenAdView.requestAndDisplayAd();
            return;
        }
        int n2 = this.fAdCreative.getHeight();
        int n3 = this.fAdCreative.getWidth();
        if (n3 >= 320 && n2 >= 50) {
            Log.d((String)"FlurryAgent", (String)"Determined Mobclix AdSize as BANNER");
            mobclixMMABannerXLAdView = new MobclixMMABannerXLAdView((Context)((Activity)context));
        } else if (n3 >= 300 && n2 >= 250) {
            Log.d((String)"FlurryAgent", (String)"Determined Mobclix AdSize as IAB_RECT");
            mobclixMMABannerXLAdView = new MobclixIABRectangleMAdView((Context)((Activity)context));
        } else {
            Log.d((String)"FlurryAgent", (String)"Could not find Mobclix AdSize that matches size");
            mobclixMMABannerXLAdView = null;
        }
        mobclixMMABannerXLAdView.addMobclixAdViewListener((MobclixAdViewListener)new ah((ac)this));
        this.addView((View)mobclixMMABannerXLAdView);
        mobclixMMABannerXLAdView.setRefreshTime(-1L);
    }
}


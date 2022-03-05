/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.glu.plugins;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdRegistration;
import com.amazon.device.ads.AdTargetingOptions;
import com.glu.plugins.AAds;
import com.unity3d.player.UnityPlayer;

public class AmazonGlu {
    private static int AD_GRAVITY;
    private static int AD_HEIGHT;
    private static int AD_WIDTH;
    private static int PADDING_BOTTOM;
    private static int PADDING_LEFT;
    private static int PADDING_RIGHT;
    private static int PADDING_TOP;
    private static AdLayout adLayout;
    private static int lastCheckedHeight;
    private static int lastCheckedWidth;
    private static LinearLayout linearLayout;
    private static AdTargetingOptions opt;
    private static int scaledHeight;
    private static int scaledWidth;
    private static String size;

    static {
        linearLayout = null;
        PADDING_LEFT = 0;
        PADDING_TOP = 0;
        PADDING_RIGHT = 0;
        PADDING_BOTTOM = 0;
        scaledWidth = 0;
        scaledHeight = 0;
        lastCheckedWidth = -1;
        lastCheckedHeight = -1;
    }

    private static int GetScaledHeight() {
        AAds.Log("Amazon.GetScaledHeight()");
        if (scaledHeight != 0 && lastCheckedHeight == AD_HEIGHT) {
            return scaledHeight;
        }
        AmazonGlu.MeasureScaledDimensions();
        lastCheckedWidth = AD_WIDTH;
        lastCheckedHeight = AD_HEIGHT;
        return scaledHeight;
    }

    private static int GetScaledWidth() {
        AAds.Log("Amazon.GetScaledWidth()");
        if (scaledWidth != 0 && lastCheckedWidth == AD_WIDTH) {
            return scaledWidth;
        }
        AmazonGlu.MeasureScaledDimensions();
        lastCheckedWidth = AD_WIDTH;
        lastCheckedHeight = AD_HEIGHT;
        return scaledWidth;
    }

    public static void Hide() {
        AAds.Log("Amazon.Hide()");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                if (linearLayout != null) {
                    linearLayout.removeAllViews();
                    AmazonGlu.linearLayout = null;
                }
            }
        });
    }

    public static void Init(final String string2, final String string3) {
        AAds.Log("Amazon.Init( " + string2 + ", " + string3 + " )");
        if (string2 == null || string2.equals((Object)"")) {
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("**********                WARNING                **********");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("AmazonAds is disabled, because no keys were passed in.");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            return;
        }
        size = string3;
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void run() {
                try {
                    AAds.Log("Amazon Ads Version: " + AdRegistration.getVersion((Context)UnityPlayer.currentActivity));
                    AdRegistration.enableTesting((Context)UnityPlayer.currentActivity, AAds.DEBUG);
                    AdRegistration.enableLogging((Context)UnityPlayer.currentActivity, AAds.DEBUG);
                    AdRegistration.setAppKey((Context)UnityPlayer.currentActivity, string2);
                    AmazonGlu.adLayout = new AdLayout((Context)UnityPlayer.currentActivity, AdLayout.AdSize.fromString(string3));
                    opt = new AdTargetingOptions();
                    opt.enableGeoLocation(true);
                    return;
                }
                catch (Exception exception) {
                    if (!AAds.DEBUG) return;
                    exception.printStackTrace();
                    return;
                }
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void MeasureScaledDimensions() {
        AAds.Log("MeasureScaledDimensions()");
        int n = AAds.GetScreenWidth();
        int n2 = AAds.GetScreenHeight();
        int n3 = Integer.parseInt((String)size.split("x")[0]);
        int n4 = Integer.parseInt((String)size.split("x")[1]);
        scaledWidth = n3;
        scaledHeight = n4;
        AAds.Log("Original Dimensions: " + scaledWidth + "x" + scaledHeight);
        if (AD_WIDTH != 0 || AD_HEIGHT != 0) {
            AAds.Log("Scaling ad based on custom input");
            scaledWidth = AD_WIDTH != 0 ? AD_WIDTH : n3 * AD_HEIGHT / n4;
            scaledHeight = AD_HEIGHT != 0 ? AD_HEIGHT : n4 * AD_WIDTH / n3;
            AAds.Log("New Dimensions: " + scaledWidth + "x" + scaledHeight);
        }
        if (scaledWidth > n) {
            AAds.Log("Scaling ad to fit screen width");
            scaledWidth = n;
            scaledHeight = n4 * scaledWidth / n3;
            AAds.Log("New Dimensions: " + scaledWidth + "x" + scaledHeight);
        }
        if (scaledHeight > n2) {
            AAds.Log("Scaling ad to fit screen height");
            scaledHeight = n2;
            scaledWidth = n3 * scaledHeight / n4;
            AAds.Log("New Dimensions: " + scaledWidth + "x" + scaledHeight);
        }
    }

    public static void SetPadding(int n, int n2, int n3, int n4) {
        AAds.Log("Amazon.SetPadding( " + n + ", " + n2 + ", " + n3 + ", " + n4 + " )");
        PADDING_LEFT = n;
        PADDING_TOP = n2;
        PADDING_RIGHT = n3;
        PADDING_BOTTOM = n4;
    }

    public static void Show(int n, int n2, int n3) {
        AAds.Log("Amazon.Show( " + n + ", " + n2 + ", " + n3 + " )");
        if (n >= 0) {
            AD_WIDTH = n;
        }
        if (n2 >= 0) {
            AD_HEIGHT = n2;
        }
        if (n3 >= 0) {
            AD_GRAVITY = n3;
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                if (adLayout == null || linearLayout != null) {
                    return;
                }
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(AmazonGlu.GetScaledWidth(), AmazonGlu.GetScaledHeight());
                adLayout.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                adLayout.loadAd(opt);
                int n = AAds.GetScreenWidth();
                int n2 = AAds.GetScreenHeight();
                AmazonGlu.linearLayout = new LinearLayout((Context)UnityPlayer.currentActivity);
                linearLayout.setLayoutParams(new ViewGroup.LayoutParams(n, n2));
                linearLayout.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
                linearLayout.setGravity(AD_GRAVITY);
                linearLayout.addView((View)adLayout);
                UnityPlayer.currentActivity.getWindow().addContentView((View)linearLayout, new ViewGroup.LayoutParams(n, n2));
            }
        });
    }

}


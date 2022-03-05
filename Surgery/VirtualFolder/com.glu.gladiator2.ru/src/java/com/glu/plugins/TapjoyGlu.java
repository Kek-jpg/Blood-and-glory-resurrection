/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Handler
 *  android.os.Message
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
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
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import com.glu.plugins.AAds;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyDisplayAd;
import com.tapjoy.TapjoyLog;
import com.unity3d.player.UnityPlayer;

public class TapjoyGlu {
    private static int AD_GRAVITY = 0;
    private static int AD_HEIGHT = 0;
    private static int AD_WIDTH = 0;
    private static int PADDING_BOTTOM = 0;
    private static int PADDING_LEFT = 0;
    private static int PADDING_RIGHT = 0;
    private static int PADDING_TOP = 0;
    private static final String SHAREDPREF_KEY_TJ_PENDING = "tjpending";
    private static View bannerView;
    private static boolean fetching;
    private static boolean getPending;
    private static int lastCheckedHeight;
    private static int lastCheckedWidth;
    private static LinearLayout linearLayout;
    static final Runnable mUpdateResults;
    private static int pendingNew;
    static final Runnable removeBannerAd;
    private static int scaledHeight;
    private static int scaledWidth;
    private static String unityGameObject;

    static {
        fetching = false;
        linearLayout = null;
        PADDING_LEFT = 0;
        PADDING_TOP = 0;
        PADDING_RIGHT = 0;
        PADDING_BOTTOM = 0;
        scaledWidth = 0;
        scaledHeight = 0;
        lastCheckedWidth = -1;
        lastCheckedHeight = -1;
        mUpdateResults = new Runnable(){

            public void run() {
                try {
                    int n = AAds.GetScreenWidth();
                    int n2 = AAds.GetScreenHeight();
                    bannerView = TapjoyConnect.getTapjoyConnectInstance().getBannerAdView();
                    if (bannerView == null) {
                        return;
                    }
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(TapjoyGlu.GetScaledWidth(), TapjoyGlu.GetScaledHeight());
                    bannerView.setLayoutParams(layoutParams);
                    if (linearLayout != null) {
                        linearLayout.removeAllViews();
                    }
                    TapjoyGlu.linearLayout = new LinearLayout((Context)UnityPlayer.currentActivity);
                    linearLayout.setLayoutParams(new ViewGroup.LayoutParams(n, n2));
                    linearLayout.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
                    linearLayout.setGravity(AD_GRAVITY);
                    linearLayout.addView(bannerView);
                    UnityPlayer.currentActivity.getWindow().addContentView((View)linearLayout, new ViewGroup.LayoutParams(n, n2));
                    return;
                }
                catch (Exception exception) {
                    AAds.Log("Exception adding banner: " + exception.toString());
                    if (AAds.DEBUG) {
                        exception.printStackTrace();
                    }
                    return;
                }
            }
        };
        removeBannerAd = new Runnable(){

            public void run() {
                if (linearLayout != null) {
                    linearLayout.removeAllViews();
                }
                TapjoyGlu.linearLayout = null;
                bannerView = null;
            }
        };
        getPending = false;
        pendingNew = 0;
    }

    public static void ActionComplete(String string2) {
        AAds.Log(" Tapjoy.ActionComplete( " + string2 + " )");
        if (TapjoyConnect.getTapjoyConnectInstance() != null) {
            TapjoyConnect.getTapjoyConnectInstance().actionComplete(string2);
        }
    }

    public static void ClearPoints() {
        AAds.Log("Tapjoy.ClearPoints()");
        if (UnityPlayer.currentActivity != null) {
            SharedPreferences.Editor editor = UnityPlayer.currentActivity.getSharedPreferences("aads", 0).edit();
            editor.remove(SHAREDPREF_KEY_TJ_PENDING);
            editor.commit();
        }
    }

    public static void GetPoints() {
        AAds.Log("Tapjoy.GetPoints()");
        if (!getPending && TapjoyConnect.getTapjoyConnectInstance() != null) {
            TapjoyConnect.getTapjoyConnectInstance().getTapPoints();
            getPending = true;
            pendingNew = 0;
        }
    }

    private static int GetScaledHeight() {
        AAds.Log("Tapjoy.GetScaledHeight()");
        if (scaledHeight != 0 && lastCheckedHeight == AD_HEIGHT) {
            return scaledHeight;
        }
        TapjoyGlu.MeasureScaledDimensions();
        lastCheckedWidth = AD_WIDTH;
        lastCheckedHeight = AD_HEIGHT;
        return scaledHeight;
    }

    private static int GetScaledWidth() {
        AAds.Log("Tapjoy.GetScaledWidth()");
        if (scaledWidth != 0 && lastCheckedWidth == AD_WIDTH) {
            return scaledWidth;
        }
        TapjoyGlu.MeasureScaledDimensions();
        lastCheckedWidth = AD_WIDTH;
        lastCheckedHeight = AD_HEIGHT;
        return scaledWidth;
    }

    public static void Hide() {
        AAds.Log("Tapjoy.Hide()");
        if (TapjoyConnect.getTapjoyConnectInstance() != null) {
            TapjoyConnect.getTapjoyConnectInstance().cancelShowImmediately();
            TapjoyConnect.getTapjoyConnectInstance().hideBannerAd();
            if (!fetching) {
                AAds.Log("Fetching new Tapjoy banner ad");
                fetching = true;
                TapjoyConnect.getTapjoyConnectInstance().getDisplayAd();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void Init(String string2, String string3, String string4, String string5) {
        AAds.Log("Tapjoy.Init( " + string2 + ", " + string3 + ", " + string4 + ", " + string5 + " )");
        if (string3 == null || string3.equals((Object)"") || string4 == null || string4.equals((Object)"")) {
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("**********                WARNING                **********");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("Tapjoy is disabled, because no keys were passed in.");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            return;
        } else {
            AAds.Log("Tapjoy Version: 8.3.1");
            TapjoyLog.enableLogging(AAds.DEBUG);
            unityGameObject = string2;
            boolean bl = string5.equals((Object)"ADVERTISER");
            boolean bl2 = false;
            if (bl) {
                bl2 = true;
            }
            if (!bl2) {
                UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

                    public void run() {
                        TapjoyConnect.setHandler(new Handler(){

                            public void handleMessage(Message message) {
                                if (message != null) {
                                    this.post(TapjoyGlu.removeBannerAd);
                                    return;
                                }
                                this.post(TapjoyGlu.mUpdateResults);
                            }
                        });
                    }

                });
            }
            TapjoyConnect.requestTapjoyConnect((Context)UnityPlayer.currentActivity, string3, string4);
            if (bl2) return;
            {
                fetching = true;
                AAds.Log("Fetching Tapjoy banner ad");
                TapjoyConnect.getTapjoyConnectInstance().setBannerAdSize(string5);
                TapjoyConnect.getTapjoyConnectInstance().getDisplayAd();
                TapjoyConnect.getTapjoyConnectInstance().setVideoCacheCount(10);
                TapjoyGlu.GetPoints();
                return;
            }
        }
    }

    public static boolean IsAvailable() {
        AAds.Log("Tapjoy.IsAvailable()");
        return TapjoyConnect.getTapjoyConnectInstance() != null && TapjoyConnect.getTapjoyConnectInstance().didReceiveDisplayAdData();
    }

    public static void Launch() {
        AAds.Log("Tapjoy.Launch()");
        if (TapjoyConnect.getTapjoyConnectInstance() != null) {
            TapjoyConnect.getTapjoyConnectInstance().showOffers();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void MeasureScaledDimensions() {
        AAds.Log("MeasureScaledDimensions()");
        int n = AAds.GetScreenWidth();
        int n2 = AAds.GetScreenHeight();
        int n3 = TapjoyDisplayAd.PREVIOUS_RECEIVED_WIDTH;
        int n4 = TapjoyDisplayAd.PREVIOUS_RECEIVED_HEIGHT;
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
        AAds.Log("Tapjoy.SetPadding( " + n + ", " + n2 + ", " + n3 + ", " + n4 + " )");
        PADDING_LEFT = n;
        PADDING_TOP = n2;
        PADDING_RIGHT = n3;
        PADDING_BOTTOM = n4;
    }

    public static void Show() {
        TapjoyGlu.Show(AD_WIDTH, AD_HEIGHT, AD_GRAVITY);
    }

    public static void Show(int n, int n2, int n3) {
        AAds.Log("Tapjoy.Show( " + n + ", " + n2 + ", " + n3 + " )");
        if (TapjoyConnect.getTapjoyConnectInstance() == null) {
            return;
        }
        if (n >= 0) {
            AD_WIDTH = n;
        }
        if (n2 >= 0) {
            AD_HEIGHT = n2;
        }
        if (n3 >= 0) {
            AD_GRAVITY = n3;
        }
        fetching = false;
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                if (bannerView != null) {
                    return;
                }
                if (!TapjoyConnect.getTapjoyConnectInstance().didReceiveDisplayAdData()) {
                    AAds.Log("Ad not ready, will display on delivery");
                    TapjoyConnect.getTapjoyConnectInstance().showImmediately();
                    return;
                }
                AAds.Log("Displaying TJ banner ad");
                TapjoyConnect.getTapjoyConnectInstance().showBannerAd();
            }
        });
    }

    public static void ShowFeaturedApp() {
        AAds.Log("Tapjoy.ShowFeaturedApp()");
        if (TapjoyConnect.getTapjoyConnectInstance() == null) {
            return;
        }
        AAds.Log("Fetching Featured App");
        TapjoyConnect.getTapjoyConnectInstance().getFeaturedApp();
    }

    public static void getSpendPointsResponse(String string2, int n) {
        AAds.Log("getSpendPointsResponse( " + string2 + ", " + n + " )");
        if (UnityPlayer.currentActivity != null) {
            SharedPreferences sharedPreferences = UnityPlayer.currentActivity.getSharedPreferences("aads", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SHAREDPREF_KEY_TJ_PENDING, sharedPreferences.getInt(SHAREDPREF_KEY_TJ_PENDING, 0) + pendingNew);
            editor.commit();
        }
        TapjoyGlu.reportPending();
        getPending = false;
    }

    public static void getSpendPointsResponseFailed(String string2) {
        AAds.Log("getSpendPointsResponseFailed( " + string2 + " )");
        TapjoyGlu.reportPending();
        getPending = false;
    }

    public static void getUpdatePoints(String string2, int n) {
        AAds.Log("getUpdatePoints( " + string2 + ", " + n + " )");
        if (n <= 0) {
            TapjoyGlu.reportPending();
            getPending = false;
            return;
        }
        pendingNew = n;
        TapjoyConnect.getTapjoyConnectInstance().spendTapPoints(n);
    }

    public static void getUpdatePointsFailed(String string2) {
        AAds.Log("getUpdatePointsFailed( " + string2 + " )");
        TapjoyGlu.reportPending();
        getPending = false;
    }

    private static void reportPending() {
        int n;
        AAds.Log("reportPending()");
        if (UnityPlayer.currentActivity != null && (n = UnityPlayer.currentActivity.getSharedPreferences("aads", 0).getInt(SHAREDPREF_KEY_TJ_PENDING, 0)) > 0) {
            UnityPlayer.UnitySendMessage(unityGameObject, "onTapjoyPointsReceived", Integer.toString((int)n));
        }
    }

}


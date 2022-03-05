/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.widget.LinearLayout
 *  com.burstly.lib.BurstlySdk
 *  com.burstly.lib.currency.CurrencyManager
 *  com.burstly.lib.currency.event.BalanceUpdateEvent
 *  com.burstly.lib.feature.currency.ICurrencyListener
 *  com.burstly.lib.ui.BurstlyView
 *  com.burstly.lib.util.LoggerExt
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import com.burstly.lib.BurstlySdk;
import com.burstly.lib.currency.CurrencyManager;
import com.burstly.lib.currency.event.BalanceUpdateEvent;
import com.burstly.lib.feature.currency.ICurrencyListener;
import com.burstly.lib.ui.BurstlyView;
import com.burstly.lib.util.LoggerExt;
import com.glu.plugins.AAds;
import com.unity3d.player.UnityPlayer;

public class BurstlyGlu {
    private static int AD_GRAVITY = 0;
    private static int AD_HEIGHT = 0;
    private static int AD_WIDTH = 0;
    private static int PADDING_BOTTOM = 0;
    private static int PADDING_LEFT = 0;
    private static int PADDING_RIGHT = 0;
    private static int PADDING_TOP = 0;
    private static final String SHAREDPREF_KEY_BURSTLY_PENDING = "burstlypending";
    private static BurstlyView bannerView;
    private static boolean fetching;
    private static boolean getPending;
    private static int lastCheckedHeight;
    private static int lastCheckedWidth;
    private static LinearLayout linearLayout;
    private static CurrencyManager mCurrencyMan;
    private static int pendingNew;
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
        getPending = false;
        pendingNew = 0;
    }

    public static void ClearPoints() {
        AAds.Log("Burstly.ClearPoints()");
        if (UnityPlayer.currentActivity != null) {
            SharedPreferences.Editor editor = UnityPlayer.currentActivity.getSharedPreferences("aads", 0).edit();
            editor.remove(SHAREDPREF_KEY_BURSTLY_PENDING);
            editor.commit();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void GetPoints() {
        AAds.Log("Burstly.GetPoints()");
        if (getPending || mCurrencyMan == null) return;
        try {
            getPending = true;
            pendingNew = 0;
            mCurrencyMan.checkForUpdate();
            return;
        }
        catch (Exception exception) {
            if (!AAds.DEBUG) return;
            exception.printStackTrace();
            return;
        }
    }

    private static int GetScaledHeight() {
        AAds.Log("Burstly.GetScaledHeight()");
        if (scaledHeight != 0 && lastCheckedHeight == AD_HEIGHT) {
            return scaledHeight;
        }
        BurstlyGlu.MeasureScaledDimensions();
        lastCheckedWidth = AD_WIDTH;
        lastCheckedHeight = AD_HEIGHT;
        return scaledHeight;
    }

    private static int GetScaledWidth() {
        AAds.Log("Burstly.GetScaledWidth()");
        if (scaledWidth != 0 && lastCheckedWidth == AD_WIDTH) {
            return scaledWidth;
        }
        BurstlyGlu.MeasureScaledDimensions();
        lastCheckedWidth = AD_WIDTH;
        lastCheckedHeight = AD_HEIGHT;
        return scaledWidth;
    }

    public static void Hide() {
        AAds.Log("Burstly.Hide()");
        if (!BurstlySdk.wasInit()) {
            return;
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                if (linearLayout != null) {
                    linearLayout.removeAllViews();
                    bannerView.sendRequestForAd();
                }
                BurstlyGlu.linearLayout = null;
                if (!fetching && bannerView != null) {
                    AAds.Log("Fetching new Burstly ad");
                    fetching = true;
                    bannerView.sendRequestForAd();
                }
            }
        });
    }

    public static void Init(final String string2, final String string3, final String string4) {
        AAds.Log("Burstly.Init( " + string2 + ", " + string3 + ", " + string4 + " )");
        if (string3 == null || string3.equals((Object)"") || string4 == null || string4.equals((Object)"")) {
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("**********                WARNING                **********");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("Burstly is disabled, because no keys were passed in.");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            return;
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            /*
             * Enabled aggressive block sorting
             */
            public void run() {
                BurstlySdk.init((Context)UnityPlayer.currentActivity);
                int n = AAds.DEBUG ? 3 : 7;
                LoggerExt.setLogLevel((int)n);
                AAds.Log("Burstly Version: " + BurstlySdk.getSdkVersion());
                unityGameObject = string2;
                mCurrencyMan = new CurrencyManager();
                mCurrencyMan.initManager((Context)UnityPlayer.currentActivity, string3);
                mCurrencyMan.addCurrencyListener((ICurrencyListener)new CurrencyListener(null));
                BurstlyGlu.GetPoints();
                fetching = true;
                bannerView = new BurstlyView((Context)UnityPlayer.currentActivity);
                bannerView.setPublisherId(string3);
                bannerView.setZoneId(string4);
                bannerView.setDefaultSessionLife(30);
                bannerView.sendRequestForAd();
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
        scaledWidth = 64;
        scaledHeight = 10;
        AAds.Log("Original Dimensions: " + scaledWidth + "x" + scaledHeight);
        if (AD_WIDTH != 0 || AD_HEIGHT != 0) {
            AAds.Log("Scaling ad based on custom input");
            scaledWidth = AD_WIDTH != 0 ? AD_WIDTH : 64 * AD_HEIGHT / 10;
            scaledHeight = AD_HEIGHT != 0 ? AD_HEIGHT : 10 * AD_WIDTH / 64;
            AAds.Log("New Dimensions: " + scaledWidth + "x" + scaledHeight);
        }
        if (scaledWidth > n) {
            AAds.Log("Scaling ad to fit screen width");
            scaledWidth = n;
            scaledHeight = 10 * scaledWidth / 64;
            AAds.Log("New Dimensions: " + scaledWidth + "x" + scaledHeight);
        }
        if (scaledHeight > n2) {
            AAds.Log("Scaling ad to fit screen height");
            scaledHeight = n2;
            scaledWidth = 64 * scaledHeight / 10;
            AAds.Log("New Dimensions: " + scaledWidth + "x" + scaledHeight);
        }
    }

    public static void SetPadding(int n, int n2, int n3, int n4) {
        AAds.Log("Burstly.SetPadding( " + n + ", " + n2 + ", " + n3 + ", " + n4 + " )");
        PADDING_LEFT = n;
        PADDING_TOP = n2;
        PADDING_RIGHT = n3;
        PADDING_BOTTOM = n4;
    }

    public static void Show() {
        BurstlyGlu.Show(AD_WIDTH, AD_HEIGHT, AD_GRAVITY);
    }

    public static void Show(int n, int n2, int n3) {
        AAds.Log("Burstly.Show( " + n + ", " + n2 + ", " + n3 + " )");
        if (!BurstlySdk.wasInit() || linearLayout != null) {
            return;
        }
        if (n3 >= 0) {
            AD_GRAVITY = n3;
        }
        fetching = false;
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                BurstlyGlu.mUpdateResults();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void mUpdateResults() {
        try {
            int n = AAds.GetScreenWidth();
            int n2 = AAds.GetScreenHeight();
            if (bannerView == null) {
                return;
            }
            int n3 = BurstlyGlu.GetScaledWidth();
            int n4 = BurstlyGlu.GetScaledHeight();
            if (n3 != 64 && n4 != 10) {
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(n3, n4);
                bannerView.setLayoutParams(layoutParams);
            }
            if (linearLayout != null) {
                linearLayout.removeAllViews();
            }
            linearLayout = new LinearLayout((Context)UnityPlayer.currentActivity);
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(n, n2));
            linearLayout.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
            linearLayout.setGravity(AD_GRAVITY);
            linearLayout.addView((View)bannerView);
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

    private static void reportPending() {
        int n;
        AAds.Log("reportPending()");
        if (UnityPlayer.currentActivity != null && (n = UnityPlayer.currentActivity.getSharedPreferences("aads", 0).getInt(SHAREDPREF_KEY_BURSTLY_PENDING, 0)) > 0) {
            UnityPlayer.UnitySendMessage(unityGameObject, "onBurstlyPointsReceived", Integer.toString((int)n));
        }
    }

    private static class CurrencyListener
    implements ICurrencyListener {
        private CurrencyListener() {
        }

        /* synthetic */ CurrencyListener(1 var1) {
        }

        public void didFailToUpdateBalance(BalanceUpdateEvent balanceUpdateEvent) {
            AAds.Log("didFailToUpdateBalance()");
            AAds.Log("Old Balance: " + balanceUpdateEvent.getOldBalance());
            AAds.Log("New Balance: " + balanceUpdateEvent.getNewBalance());
            BurstlyGlu.reportPending();
            getPending = false;
        }

        public void didUpdateBalance(BalanceUpdateEvent balanceUpdateEvent) {
            AAds.Log("didUpdateBalance()");
            AAds.Log("Old Balance: " + balanceUpdateEvent.getOldBalance());
            AAds.Log("New Balance: " + balanceUpdateEvent.getNewBalance());
            if (balanceUpdateEvent.getNewBalance() > balanceUpdateEvent.getOldBalance()) {
                pendingNew = balanceUpdateEvent.getNewBalance();
                mCurrencyMan.decreaseBalance(pendingNew);
                return;
            }
            if (balanceUpdateEvent.getNewBalance() < balanceUpdateEvent.getOldBalance()) {
                if (UnityPlayer.currentActivity != null) {
                    SharedPreferences sharedPreferences = UnityPlayer.currentActivity.getSharedPreferences("aads", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(BurstlyGlu.SHAREDPREF_KEY_BURSTLY_PENDING, sharedPreferences.getInt(BurstlyGlu.SHAREDPREF_KEY_BURSTLY_PENDING, 0) + pendingNew);
                    editor.commit();
                }
                BurstlyGlu.reportPending();
                getPending = false;
                return;
            }
            BurstlyGlu.reportPending();
            getPending = false;
        }
    }

}


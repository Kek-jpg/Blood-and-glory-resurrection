/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Handler
 *  android.util.Log
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.glu.plugins.google;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.glu.plugins.AInAppPurchase;
import com.glu.plugins.google.BillingService;
import com.glu.plugins.google.Consts;
import com.glu.plugins.google.PurchaseObserver;
import com.glu.plugins.google.ResponseHandler;
import com.unity3d.player.UnityPlayer;

public class GoogleIAP
implements AInAppPurchase.IAP {
    private static final String TAG = "AInAppPurchase - GoogleIAP";
    private BillingService mBillingService;
    private GooglePurchaseObserver mGooglePurchaseObserver;
    private Handler mHandler;

    @Override
    public void Destroy() {
        ResponseHandler.unregister(this.mGooglePurchaseObserver);
        if (this.mBillingService != null) {
            this.mBillingService.unbind();
        }
    }

    @Override
    public void Init() {
        Consts.DEBUG = AInAppPurchase.DEBUG;
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                GoogleIAP.this.mHandler = new Handler();
                GoogleIAP.this.mGooglePurchaseObserver = new GooglePurchaseObserver(GoogleIAP.this.mHandler);
                GoogleIAP.this.mBillingService = new BillingService();
                GoogleIAP.this.mBillingService.setContext((Context)UnityPlayer.currentActivity);
                ResponseHandler.register(GoogleIAP.this.mGooglePurchaseObserver);
                GoogleIAP.this.mBillingService.checkBillingSupported();
                GoogleIAP.this.mBillingService.checkSubscriptionSupported();
            }
        });
    }

    @Override
    public void Register() {
        ResponseHandler.register(this.mGooglePurchaseObserver);
    }

    @Override
    public void RequestPurchase(String string2, String string3) {
        if (this.mBillingService != null) {
            this.mBillingService.requestPurchase(string2, string3);
        }
    }

    @Override
    public void RestoreTransactions() {
        if (this.mBillingService != null) {
            this.mBillingService.restoreTransactions();
        }
    }

    @Override
    public void Unregister() {
        ResponseHandler.unregister(this.mGooglePurchaseObserver);
    }

    private class GooglePurchaseObserver
    extends PurchaseObserver {
        public GooglePurchaseObserver(Handler handler) {
            super(UnityPlayer.currentActivity, handler);
        }

        @Override
        public void onBillingSupported(boolean bl) {
            if (Consts.DEBUG) {
                Log.i((String)GoogleIAP.TAG, (String)("billing supported: " + bl));
            }
            AInAppPurchase.onBillingSupported(bl);
        }

        @Override
        public void onPurchaseStateChange(Consts.PurchaseState purchaseState, String string2, int n, long l, String string3, String string4) {
            if (Consts.DEBUG) {
                Log.i((String)GoogleIAP.TAG, (String)("onPurchaseStateChange() itemId: " + string2 + " " + (Object)((Object)purchaseState)));
            }
            AInAppPurchase.onPurchaseStateChange(Long.toString((long)l) + "|" + purchaseState.toString() + "|" + string2 + "|" + string3 + "|" + string4);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onRequestPurchaseResponse(BillingService.RequestPurchase requestPurchase, Consts.ResponseCode responseCode) {
            if (Consts.DEBUG) {
                Log.d((String)GoogleIAP.TAG, (String)(requestPurchase.mProductId + ": " + (Object)((Object)responseCode)));
                if (responseCode == Consts.ResponseCode.RESULT_OK) {
                    Log.i((String)GoogleIAP.TAG, (String)"purchase was successfully sent to server");
                } else if (responseCode == Consts.ResponseCode.RESULT_USER_CANCELED) {
                    Log.i((String)GoogleIAP.TAG, (String)"user canceled purchase");
                } else {
                    Log.i((String)GoogleIAP.TAG, (String)"purchase failed");
                }
            }
            AInAppPurchase.onRequestPurchaseResponse(responseCode.toString());
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onRestoreTransactionsResponse(BillingService.RestoreTransactions restoreTransactions, Consts.ResponseCode responseCode) {
            if (Consts.DEBUG) {
                if (responseCode == Consts.ResponseCode.RESULT_OK) {
                    Log.d((String)GoogleIAP.TAG, (String)"completed RestoreTransactions request");
                } else {
                    Log.d((String)GoogleIAP.TAG, (String)("RestoreTransactions error: " + (Object)((Object)responseCode)));
                }
            }
            AInAppPurchase.onRestoreTransactionsResponse(responseCode.toString());
        }

        @Override
        public void onSubscriptionSupported(boolean bl) {
            if (Consts.DEBUG) {
                Log.i((String)GoogleIAP.TAG, (String)("subscription supported: " + bl));
            }
            AInAppPurchase.onSubscriptionSupported(bl);
        }
    }

}


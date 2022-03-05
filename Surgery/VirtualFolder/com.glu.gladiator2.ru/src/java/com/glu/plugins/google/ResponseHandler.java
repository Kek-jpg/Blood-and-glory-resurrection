/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.util.Log
 *  java.lang.Class
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Thread
 */
package com.glu.plugins.google;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.glu.plugins.google.BillingService;
import com.glu.plugins.google.Consts;
import com.glu.plugins.google.PurchaseObserver;

public class ResponseHandler {
    private static final String SHAREDPREF_KEY_PENDING = "PENDING";
    private static final String SHAREDPREF_NAME = "aiap";
    private static final String TAG = "AInAppPurchase - ResponseHandler";
    private static PurchaseObserver sPurchaseObserver;

    public static void buyPageIntentResponse(PendingIntent pendingIntent, Intent intent) {
        if (sPurchaseObserver == null) {
            if (Consts.DEBUG) {
                Log.d((String)TAG, (String)"UI is not running");
            }
            return;
        }
        sPurchaseObserver.startBuyPageActivity(pendingIntent, intent);
    }

    public static void checkBillingSupportedResponse(boolean bl) {
        if (sPurchaseObserver != null) {
            sPurchaseObserver.onBillingSupported(bl);
        }
    }

    public static void checkSubscriptionSupportedResponse(boolean bl) {
        if (sPurchaseObserver != null) {
            sPurchaseObserver.onSubscriptionSupported(bl);
        }
    }

    public static void purchaseResponse(final Context context, final Consts.PurchaseState purchaseState, final String string2, String string3, final long l, final String string4, final String string5) {
        new Thread(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void run() {
                SharedPreferences sharedPreferences = context.getSharedPreferences(ResponseHandler.SHAREDPREF_NAME, 0);
                String string22 = sharedPreferences.getString(ResponseHandler.SHAREDPREF_KEY_PENDING, "");
                String string3 = string22 + Long.toString((long)l) + "|" + purchaseState.toString() + "|" + string2 + "|" + string4 + "|" + string5 + ";";
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(ResponseHandler.SHAREDPREF_KEY_PENDING, string3);
                editor.commit();
                Class<ResponseHandler> class_ = ResponseHandler.class;
                synchronized (ResponseHandler.class) {
                    if (sPurchaseObserver != null) {
                        sPurchaseObserver.postPurchaseStateChange(purchaseState, string2, 1, l, string4, string5);
                    }
                    // ** MonitorExit[var8_5] (shouldn't be in output)
                    return;
                }
            }
        }).start();
    }

    public static void register(PurchaseObserver purchaseObserver) {
        Class<ResponseHandler> class_ = ResponseHandler.class;
        synchronized (ResponseHandler.class) {
            sPurchaseObserver = purchaseObserver;
            // ** MonitorExit[var2_1] (shouldn't be in output)
            return;
        }
    }

    public static void responseCodeReceived(Context context, BillingService.RequestPurchase requestPurchase, Consts.ResponseCode responseCode) {
        if (sPurchaseObserver != null) {
            sPurchaseObserver.onRequestPurchaseResponse(requestPurchase, responseCode);
        }
    }

    public static void responseCodeReceived(Context context, BillingService.RestoreTransactions restoreTransactions, Consts.ResponseCode responseCode) {
        if (sPurchaseObserver != null) {
            sPurchaseObserver.onRestoreTransactionsResponse(restoreTransactions, responseCode);
        }
    }

    public static void unregister(PurchaseObserver purchaseObserver) {
        Class<ResponseHandler> class_ = ResponseHandler.class;
        synchronized (ResponseHandler.class) {
            sPurchaseObserver = null;
            // ** MonitorExit[var2_1] (shouldn't be in output)
            return;
        }
    }

}


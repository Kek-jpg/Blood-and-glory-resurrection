/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.os.Handler
 *  android.util.Log
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.NoSuchMethodException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.SecurityException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Method
 */
package com.glu.plugins.google;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Handler;
import android.util.Log;
import com.glu.plugins.google.BillingService;
import com.glu.plugins.google.Consts;
import java.lang.reflect.Method;

public abstract class PurchaseObserver {
    private static final Class[] START_INTENT_SENDER_SIG;
    private static final String TAG = "AInAppPurchase - PurchaseObserver";
    private final Activity mActivity;
    private final Handler mHandler;
    private Method mStartIntentSender;
    private Object[] mStartIntentSenderArgs = new Object[5];

    static {
        Class[] arrclass = new Class[]{IntentSender.class, Intent.class, Integer.TYPE, Integer.TYPE, Integer.TYPE};
        START_INTENT_SENDER_SIG = arrclass;
    }

    public PurchaseObserver(Activity activity, Handler handler) {
        this.mActivity = activity;
        this.mHandler = handler;
        PurchaseObserver.super.initCompatibilityLayer();
    }

    private void initCompatibilityLayer() {
        try {
            this.mStartIntentSender = this.mActivity.getClass().getMethod("startIntentSender", START_INTENT_SENDER_SIG);
            return;
        }
        catch (SecurityException securityException) {
            this.mStartIntentSender = null;
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            this.mStartIntentSender = null;
            return;
        }
    }

    public abstract void onBillingSupported(boolean var1);

    public abstract void onPurchaseStateChange(Consts.PurchaseState var1, String var2, int var3, long var4, String var6, String var7);

    public abstract void onRequestPurchaseResponse(BillingService.RequestPurchase var1, Consts.ResponseCode var2);

    public abstract void onRestoreTransactionsResponse(BillingService.RestoreTransactions var1, Consts.ResponseCode var2);

    public abstract void onSubscriptionSupported(boolean var1);

    void postPurchaseStateChange(final Consts.PurchaseState purchaseState, final String string2, final int n, final long l, final String string3, final String string4) {
        this.mHandler.post(new Runnable(){

            public void run() {
                PurchaseObserver.this.onPurchaseStateChange(purchaseState, string2, n, l, string3, string4);
            }
        });
    }

    void startBuyPageActivity(PendingIntent pendingIntent, Intent intent) {
        if (this.mStartIntentSender != null) {
            try {
                this.mStartIntentSenderArgs[0] = pendingIntent.getIntentSender();
                this.mStartIntentSenderArgs[1] = intent;
                this.mStartIntentSenderArgs[2] = 0;
                this.mStartIntentSenderArgs[3] = 0;
                this.mStartIntentSenderArgs[4] = 0;
                this.mStartIntentSender.invoke((Object)this.mActivity, this.mStartIntentSenderArgs);
                return;
            }
            catch (Exception exception) {
                Log.e((String)"AInAppPurchase - PurchaseObserver", (String)"error starting activity", (Throwable)exception);
                return;
            }
        }
        try {
            pendingIntent.send((Context)this.mActivity, 0, intent);
            return;
        }
        catch (PendingIntent.CanceledException canceledException) {
            Log.e((String)"AInAppPurchase - PurchaseObserver", (String)"error starting activity", (Throwable)canceledException);
            return;
        }
    }

}


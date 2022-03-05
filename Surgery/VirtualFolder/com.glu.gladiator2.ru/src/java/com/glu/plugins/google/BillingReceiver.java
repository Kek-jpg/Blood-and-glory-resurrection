/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.util.Log
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.glu.plugins.google;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.glu.plugins.google.BillingService;
import com.glu.plugins.google.Consts;

public class BillingReceiver
extends BroadcastReceiver {
    private static final String TAG = "AInAppPurchase - BillingReceiver";

    private void checkResponseCode(Context context, long l, int n) {
        Intent intent = new Intent("com.android.vending.billing.RESPONSE_CODE");
        intent.setClass(context, BillingService.class);
        intent.putExtra("request_id", l);
        intent.putExtra("response_code", n);
        context.startService(intent);
    }

    private void notify(Context context, String string2) {
        Intent intent = new Intent("com.glu.plugins.google.GET_PURCHASE_INFORMATION");
        intent.setClass(context, BillingService.class);
        intent.putExtra("notification_id", string2);
        context.startService(intent);
    }

    private void purchaseStateChanged(Context context, String string2, String string3) {
        Intent intent = new Intent("com.android.vending.billing.PURCHASE_STATE_CHANGED");
        intent.setClass(context, BillingService.class);
        intent.putExtra("inapp_signed_data", string2);
        intent.putExtra("inapp_signature", string3);
        context.startService(intent);
    }

    public void onReceive(Context context, Intent intent) {
        String string2 = intent.getAction();
        if ("com.android.vending.billing.PURCHASE_STATE_CHANGED".equals((Object)string2)) {
            BillingReceiver.super.purchaseStateChanged(context, intent.getStringExtra("inapp_signed_data"), intent.getStringExtra("inapp_signature"));
            return;
        }
        if ("com.android.vending.billing.IN_APP_NOTIFY".equals((Object)string2)) {
            String string3 = intent.getStringExtra("notification_id");
            if (Consts.DEBUG) {
                Log.i((String)TAG, (String)("notifyId: " + string3));
            }
            BillingReceiver.super.notify(context, string3);
            return;
        }
        if ("com.android.vending.billing.RESPONSE_CODE".equals((Object)string2)) {
            BillingReceiver.super.checkResponseCode(context, intent.getLongExtra("request_id", -1L), intent.getIntExtra("response_code", Consts.ResponseCode.RESULT_ERROR.ordinal()));
            return;
        }
        Log.w((String)TAG, (String)("unexpected action: " + string2));
    }
}


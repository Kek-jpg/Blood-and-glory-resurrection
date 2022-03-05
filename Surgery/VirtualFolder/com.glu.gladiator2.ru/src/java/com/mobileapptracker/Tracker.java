/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  java.lang.String
 */
package com.mobileapptracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Tracker
extends BroadcastReceiver {
    SharedPreferences SP;

    public void onReceive(Context context, Intent intent) {
        String string2 = intent.getStringExtra("referrer");
        if (string2 != null) {
            this.SP = context.getSharedPreferences("mat_referrer", 0);
            SharedPreferences.Editor editor = this.SP.edit();
            editor.putString("referrer", string2);
            editor.commit();
        }
    }
}


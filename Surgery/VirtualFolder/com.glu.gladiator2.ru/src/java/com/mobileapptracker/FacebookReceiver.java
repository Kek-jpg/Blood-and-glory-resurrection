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

public class FacebookReceiver
extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        SharedPreferences.Editor editor = context.getSharedPreferences("mat_fb_intent", 0).edit();
        editor.putString("action", intent.getAction());
        editor.commit();
    }
}


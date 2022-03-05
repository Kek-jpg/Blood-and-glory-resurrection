/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package com.amazon.device.ads;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

class AmazonDeviceLauncher {
    AmazonDeviceLauncher() {
    }

    static boolean isWindowshopPresent(Context context) {
        return context.getPackageManager().getLaunchIntentForPackage("com.amazon.windowshop") != null;
    }

    static void launchWindowshopDetailPage(Context context, String string) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.amazon.windowshop");
        if (intent != null) {
            intent.putExtra("com.amazon.windowshop.refinement.asin", string);
            context.startActivity(intent);
        }
    }

    static void luanchWindowshopSearchPage(Context context, String string) {
        Intent intent = new Intent("android.intent.action.SEARCH");
        intent.setComponent(new ComponentName("com.amazon.windowshop", "com.amazon.windowshop.search.SearchResultsGridActivity"));
        intent.putExtra("query", string);
        try {
            context.startActivity(intent);
            return;
        }
        catch (RuntimeException runtimeException) {
            return;
        }
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  java.lang.Object
 */
package com.wildtangent.brandboost.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class c {
    public static float a(float f2, Context context) {
        return f2 * ((float)context.getResources().getDisplayMetrics().densityDpi / 160.0f);
    }
}


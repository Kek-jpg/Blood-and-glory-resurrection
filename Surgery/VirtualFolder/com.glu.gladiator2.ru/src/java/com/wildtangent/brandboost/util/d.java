/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  java.lang.Object
 *  java.lang.String
 */
package com.wildtangent.brandboost.util;

import android.net.Uri;

public class d {
    /*
     * Enabled aggressive block sorting
     */
    public static Uri.Builder a(String string, boolean bl) {
        Uri.Builder builder = new Uri.Builder();
        String string2 = bl ? "https" : "http";
        builder.scheme(string2);
        builder.authority("vex.wildtangent.com");
        builder.path(string);
        return builder;
    }
}


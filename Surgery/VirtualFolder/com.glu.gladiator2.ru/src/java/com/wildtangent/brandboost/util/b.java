/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Thread
 *  java.lang.Throwable
 */
package com.wildtangent.brandboost.util;

import android.util.Log;

public class b {
    public static int a(String string) {
        return b.a("com.wildtangent.brandboost_", string);
    }

    public static int a(String string, String string2) {
        return 0;
    }

    public static int a(String string, String string2, Throwable throwable) {
        return Log.e((String)string, (String)b.e(string2), (Throwable)throwable);
    }

    public static int a(String string, Throwable throwable) {
        return b.a("com.wildtangent.brandboost_", string, throwable);
    }

    public static int b(String string) {
        return b.b("com.wildtangent.brandboost_", string);
    }

    public static int b(String string, String string2) {
        return Log.e((String)string, (String)b.e(string2));
    }

    public static int b(String string, String string2, Throwable throwable) {
        return 0;
    }

    public static int b(String string, Throwable throwable) {
        return b.c("com.wildtangent.brandboost_", string, throwable);
    }

    public static int c(String string) {
        return b.c("com.wildtangent.brandboost_", string);
    }

    public static int c(String string, String string2) {
        return 0;
    }

    public static int c(String string, String string2, Throwable throwable) {
        return 0;
    }

    public static int d(String string) {
        return b.d("com.wildtangent.brandboost_", string);
    }

    public static int d(String string, String string2) {
        return 0;
    }

    private static String e(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Thread.currentThread().getName());
        stringBuilder.append(" - ");
        stringBuilder.append(string);
        return stringBuilder.toString();
    }
}


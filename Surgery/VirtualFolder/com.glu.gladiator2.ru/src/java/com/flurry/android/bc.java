/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.flurry.android;

import android.util.Log;

final class bc {
    private static boolean a = false;
    private static int b = 5;

    bc() {
    }

    static int a(String string, String string2) {
        if (a || b > 3) {
            return 0;
        }
        return Log.d((String)string, (String)string2);
    }

    static int a(String string, String string2, Throwable throwable) {
        if (a || b > 3) {
            return 0;
        }
        return Log.d((String)string, (String)string2, (Throwable)throwable);
    }

    static void a() {
        a = true;
    }

    static void a(int n2) {
        b = n2;
    }

    static boolean a(String string) {
        return Log.isLoggable((String)string, (int)3);
    }

    static int b(String string, String string2) {
        if (a || b > 6) {
            return 0;
        }
        return Log.e((String)string, (String)string2);
    }

    static int b(String string, String string2, Throwable throwable) {
        if (a || b > 6) {
            return 0;
        }
        return Log.e((String)string, (String)string2, (Throwable)throwable);
    }

    static void b() {
        a = false;
    }

    static int c(String string, String string2) {
        if (a || b > 4) {
            return 0;
        }
        return Log.i((String)string, (String)string2);
    }

    static int d(String string, String string2) {
        if (a || b > 5) {
            return 0;
        }
        return Log.w((String)string, (String)string2);
    }
}


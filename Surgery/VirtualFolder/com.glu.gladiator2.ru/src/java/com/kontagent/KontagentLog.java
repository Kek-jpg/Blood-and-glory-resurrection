/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 */
package com.kontagent;

import android.util.Log;

public class KontagentLog {
    public static final String LOG_TAG = "Kontagent";
    private static int level;
    private static boolean log;

    static {
        log = false;
        level = 2;
    }

    private static String asMsg(String string2) {
        return KontagentLog.getThreadId() + string2;
    }

    private static String asTag(String string2) {
        if (string2 != null && string2.length() > 0) {
            return "Kontagent:" + string2;
        }
        return LOG_TAG;
    }

    public static int d(String string2) {
        return KontagentLog.d(null, string2);
    }

    public static int d(String string2, String string3) {
        if (log && 3 >= level) {
            return Log.d((String)KontagentLog.asTag(string2), (String)KontagentLog.asMsg(string3));
        }
        return -1;
    }

    public static int e(String string2, String string3, Throwable throwable) {
        return Log.e((String)KontagentLog.asTag(string2), (String)KontagentLog.asMsg(string3), (Throwable)throwable);
    }

    public static int e(String string2, Throwable throwable) {
        return KontagentLog.e(null, string2, throwable);
    }

    public static void enable() {
        KontagentLog.enable(true);
    }

    public static void enable(boolean bl) {
        log = bl;
    }

    private static String getThreadId() {
        Object[] arrobject = new Object[]{Thread.currentThread().getId()};
        return String.format((String)"T#%02d:", (Object[])arrobject);
    }

    public static int i(String string2) {
        return KontagentLog.i(null, string2);
    }

    public static int i(String string2, String string3) {
        if (log && 4 >= level) {
            return Log.i((String)KontagentLog.asTag(string2), (String)KontagentLog.asMsg(string3));
        }
        return -1;
    }

    public static void setLevel(int n) {
        level = n;
    }

    public static int w(String string2) {
        return KontagentLog.w(null, string2);
    }

    public static int w(String string2, String string3) {
        if (log && 5 >= level) {
            return Log.w((String)KontagentLog.asTag(string2), (String)KontagentLog.asMsg(string3));
        }
        return -1;
    }
}


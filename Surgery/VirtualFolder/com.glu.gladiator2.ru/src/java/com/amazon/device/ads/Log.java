/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  java.lang.Object
 *  java.lang.String
 */
package com.amazon.device.ads;

import com.amazon.device.ads.DebugProperties;

class Log {
    private static final String LOGTAG = "AmazonMobileAds ";
    private static boolean loggingEnabled_ = false;

    Log() {
    }

    public static boolean canLog() {
        if (!DebugProperties.isDebugModeOn()) {
            return loggingEnabled_;
        }
        return true;
    }

    public static void d(String string, String string2) {
        if (Log.canLog()) {
            android.util.Log.d((String)(LOGTAG + string), (String)string2);
        }
    }

    public static /* varargs */ void d(String string, String string2, Object ... arrobject) {
        if (Log.canLog()) {
            android.util.Log.d((String)(LOGTAG + string), (String)String.format((String)string2, (Object[])arrobject));
        }
    }

    public static void e(String string, String string2) {
        if (Log.canLog()) {
            android.util.Log.e((String)(LOGTAG + string), (String)string2);
        }
    }

    public static /* varargs */ void e(String string, String string2, Object ... arrobject) {
        if (Log.canLog()) {
            android.util.Log.e((String)(LOGTAG + string), (String)String.format((String)string2, (Object[])arrobject));
        }
    }

    public static void enableLogging(boolean bl) {
        loggingEnabled_ = bl;
    }

    public static void i(String string, String string2) {
        if (Log.canLog()) {
            android.util.Log.i((String)(LOGTAG + string), (String)string2);
        }
    }

    public static /* varargs */ void i(String string, String string2, Object ... arrobject) {
        if (Log.canLog()) {
            android.util.Log.i((String)(LOGTAG + string), (String)String.format((String)string2, (Object[])arrobject));
        }
    }

    public static void v(String string, String string2) {
        if (Log.canLog()) {
            android.util.Log.v((String)(LOGTAG + string), (String)string2);
        }
    }

    public static /* varargs */ void v(String string, String string2, Object ... arrobject) {
        if (Log.canLog()) {
            android.util.Log.v((String)(LOGTAG + string), (String)String.format((String)string2, (Object[])arrobject));
        }
    }

    public static void w(String string, String string2) {
        if (Log.canLog()) {
            android.util.Log.w((String)(LOGTAG + string), (String)string2);
        }
    }

    public static /* varargs */ void w(String string, String string2, Object ... arrobject) {
        if (Log.canLog()) {
            android.util.Log.w((String)(LOGTAG + string), (String)String.format((String)string2, (Object[])arrobject));
        }
    }
}


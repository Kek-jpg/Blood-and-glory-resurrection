/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  java.lang.Object
 *  java.lang.String
 */
package com.tapjoy;

import android.util.Log;

public class TapjoyLog {
    private static final int MAX_STRING_SIZE = 4096;
    private static boolean showLog = false;

    public static void d(String string2, String string3) {
        if (showLog) {
            Log.d((String)string2, (String)string3);
        }
    }

    public static void e(String string2, String string3) {
        if (showLog) {
            Log.e((String)string2, (String)string3);
        }
    }

    public static void enableLogging(boolean bl) {
        Log.i((String)"TapjoyLog", (String)("enableLogging: " + bl));
        showLog = bl;
    }

    public static void i(String string2, String string3) {
        if (showLog) {
            if (string3.length() > 4096) {
                for (int i2 = 0; i2 <= string3.length() / 4096; ++i2) {
                    int n = i2 * 4096;
                    int n2 = 4096 * (i2 + 1);
                    if (n2 > string3.length()) {
                        n2 = string3.length();
                    }
                    Log.i((String)string2, (String)string3.substring(n, n2));
                }
            } else {
                Log.i((String)string2, (String)string3);
            }
        }
    }

    public static void v(String string2, String string3) {
        if (showLog) {
            Log.v((String)string2, (String)string3);
        }
    }

    public static void w(String string2, String string3) {
        if (showLog) {
            Log.w((String)string2, (String)string3);
        }
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Environment
 *  android.util.Log
 *  java.io.File
 *  java.lang.Object
 *  java.lang.String
 */
package com.glu.plugins;

import android.os.Environment;
import android.util.Log;
import java.io.File;

public class AStats {
    public static boolean DEBUG = false;
    private static final String VERSION = "2.3.3";

    static {
        DEBUG = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void Init(boolean bl) {
        boolean bl2 = bl || new File(Environment.getExternalStorageDirectory().toString() + "/.gludebug").exists();
        DEBUG = bl2;
        AStats.Log("AStats Version: 2.3.3");
    }

    public static void Log(String string2) {
        if (DEBUG) {
            Log.d((String)"AStats", (String)string2);
        }
    }

    public static void LogError(String string2) {
        if (DEBUG) {
            Log.e((String)"AStats", (String)string2);
        }
    }
}


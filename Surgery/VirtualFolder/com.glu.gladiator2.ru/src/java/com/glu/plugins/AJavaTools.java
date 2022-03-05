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

public class AJavaTools {
    public static boolean DEBUG = false;
    public static boolean TRUE_DEBUG = false;
    private static final String VERSION = "2.3.1";

    static {
        DEBUG = false;
        TRUE_DEBUG = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void Init(boolean bl) {
        TRUE_DEBUG = bl;
        boolean bl2 = bl || new File(Environment.getExternalStorageDirectory().toString() + "/.gludebug").exists();
        DEBUG = bl2;
        AJavaTools.Log("AJavaTools Version: 2.3.1");
        AJavaTools.Log("Init( " + bl + " )");
    }

    public static void Log(String string2) {
        if (DEBUG) {
            Log.d((String)"AJavaTools", (String)string2);
        }
    }
}


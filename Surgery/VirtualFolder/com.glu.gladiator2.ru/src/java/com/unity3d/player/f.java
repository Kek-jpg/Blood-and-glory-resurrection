/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  java.lang.Object
 *  java.lang.String
 */
package com.unity3d.player;

import android.util.Log;

final class f {
    f() {
    }

    protected static void Log(int n2, String string) {
        if (n2 == 6) {
            Log.e((String)"Unity", (String)string);
        }
        if (n2 == 5) {
            Log.w((String)"Unity", (String)string);
        }
    }
}


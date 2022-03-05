/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.android;

import com.flurry.android.bc;
import java.io.File;

final class ad {
    private static String a = "FlurryAgent";

    ad() {
    }

    static boolean a(File file) {
        File file2 = file.getParentFile();
        if (!file2.mkdirs() && !file2.exists()) {
            bc.b(a, "Unable to create persistent dir: " + (Object)file2);
            return false;
        }
        return true;
    }
}


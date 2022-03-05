/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.playhaven.src.utils;

import com.playhaven.src.common.PHConfig;

public class PHConversionUtils {
    public static float dipToPixels(float f2) {
        return f2 * PHConfig.screen_density;
    }
}


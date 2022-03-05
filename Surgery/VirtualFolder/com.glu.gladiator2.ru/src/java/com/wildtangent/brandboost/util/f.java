/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Locale
 */
package com.wildtangent.brandboost.util;

import java.util.Locale;

public class f {
    public static String a() {
        return (Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry()).toLowerCase();
    }
}


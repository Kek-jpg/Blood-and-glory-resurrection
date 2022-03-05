/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.android;

public final class FlurryAdSize
extends Enum<FlurryAdSize> {
    public static final /* enum */ FlurryAdSize BANNER_BOTTOM;
    public static final /* enum */ FlurryAdSize BANNER_TOP;
    public static final /* enum */ FlurryAdSize FULLSCREEN;
    private static final /* synthetic */ FlurryAdSize[] b;
    private int a;

    static {
        BANNER_TOP = new FlurryAdSize(1);
        BANNER_BOTTOM = new FlurryAdSize(2);
        FULLSCREEN = new FlurryAdSize(3);
        FlurryAdSize[] arrflurryAdSize = new FlurryAdSize[]{BANNER_TOP, BANNER_BOTTOM, FULLSCREEN};
        b = arrflurryAdSize;
    }

    private FlurryAdSize(int n3) {
        this.a = n3;
    }

    public static FlurryAdSize valueOf(String string) {
        return (FlurryAdSize)Enum.valueOf(FlurryAdSize.class, (String)string);
    }

    public static FlurryAdSize[] values() {
        return (FlurryAdSize[])b.clone();
    }

    final int a() {
        return this.a;
    }
}


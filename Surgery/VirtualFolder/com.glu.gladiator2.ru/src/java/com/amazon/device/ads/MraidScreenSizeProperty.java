/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package com.amazon.device.ads;

import com.amazon.device.ads.MraidProperty;

class MraidScreenSizeProperty
extends MraidProperty {
    private final int mScreenHeight;
    private final int mScreenWidth;

    MraidScreenSizeProperty(int n2, int n3) {
        this.mScreenWidth = n2;
        this.mScreenHeight = n3;
    }

    public static MraidScreenSizeProperty createWithSize(int n2, int n3) {
        return new MraidScreenSizeProperty(n2, n3);
    }

    @Override
    public String toJsonPair() {
        return "screenSize: { width: " + this.mScreenWidth + ", height: " + this.mScreenHeight + " }";
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.amazon.device.ads;

import com.amazon.device.ads.MraidProperty;

class MraidViewableProperty
extends MraidProperty {
    private final boolean mViewable;

    MraidViewableProperty(boolean bl) {
        this.mViewable = bl;
    }

    public static MraidViewableProperty createWithViewable(boolean bl) {
        return new MraidViewableProperty(bl);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String toJsonPair() {
        String string;
        StringBuilder stringBuilder = new StringBuilder().append("viewable: ");
        if (this.mViewable) {
            string = "true";
            do {
                return stringBuilder.append(string).toString();
                break;
            } while (true);
        }
        string = "false";
        return stringBuilder.append(string).toString();
    }
}


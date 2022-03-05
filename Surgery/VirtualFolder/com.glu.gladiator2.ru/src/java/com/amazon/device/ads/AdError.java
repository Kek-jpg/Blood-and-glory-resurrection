/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.amazon.device.ads;

public final class AdError {
    protected int code_;
    protected String message_;

    AdError(int n2, String string) {
        this.code_ = n2;
        this.message_ = string;
    }

    public int getResponseCode() {
        return this.code_;
    }

    public String getResponseMessage() {
        return this.message_;
    }
}


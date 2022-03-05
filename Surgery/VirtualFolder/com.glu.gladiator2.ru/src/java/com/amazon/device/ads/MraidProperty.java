/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.amazon.device.ads;

abstract class MraidProperty {
    MraidProperty() {
    }

    private String sanitize(String string) {
        if (string != null) {
            return string.replaceAll("[^a-zA-Z0-9_,:\\s\\{\\}\\'\\\"]", "");
        }
        return "";
    }

    public abstract String toJsonPair();

    public String toString() {
        return this.sanitize(this.toJsonPair());
    }
}


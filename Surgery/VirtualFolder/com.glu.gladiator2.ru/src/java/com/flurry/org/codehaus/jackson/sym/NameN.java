/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.sym;

import com.flurry.org.codehaus.jackson.sym.Name;

public final class NameN
extends Name {
    final int mQuadLen;
    final int[] mQuads;

    NameN(String string2, int n, int[] arrn, int n2) {
        super(string2, n);
        if (n2 < 3) {
            throw new IllegalArgumentException("Qlen must >= 3");
        }
        this.mQuads = arrn;
        this.mQuadLen = n2;
    }

    @Override
    public boolean equals(int n) {
        return false;
    }

    @Override
    public boolean equals(int n, int n2) {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean equals(int[] arrn, int n) {
        if (n == this.mQuadLen) {
            int n2 = 0;
            do {
                if (n2 >= n) {
                    return true;
                }
                if (arrn[n2] != this.mQuads[n2]) break;
                ++n2;
            } while (true);
        }
        return false;
    }
}


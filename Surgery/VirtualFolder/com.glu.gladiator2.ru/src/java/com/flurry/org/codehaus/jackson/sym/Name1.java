/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.sym;

import com.flurry.org.codehaus.jackson.sym.Name;

public final class Name1
extends Name {
    static final Name1 sEmptyName = new Name1("", 0, 0);
    final int mQuad;

    Name1(String string2, int n, int n2) {
        super(string2, n);
        this.mQuad = n2;
    }

    static final Name1 getEmptyName() {
        return sEmptyName;
    }

    @Override
    public boolean equals(int n) {
        return n == this.mQuad;
    }

    @Override
    public boolean equals(int n, int n2) {
        return n == this.mQuad && n2 == 0;
    }

    @Override
    public boolean equals(int[] arrn, int n) {
        return n == 1 && arrn[0] == this.mQuad;
    }
}


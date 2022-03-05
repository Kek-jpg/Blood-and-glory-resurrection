/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.sym;

import com.flurry.org.codehaus.jackson.sym.Name;

public final class Name2
extends Name {
    final int mQuad1;
    final int mQuad2;

    Name2(String string2, int n, int n2, int n3) {
        super(string2, n);
        this.mQuad1 = n2;
        this.mQuad2 = n3;
    }

    @Override
    public boolean equals(int n) {
        return false;
    }

    @Override
    public boolean equals(int n, int n2) {
        return n == this.mQuad1 && n2 == this.mQuad2;
    }

    @Override
    public boolean equals(int[] arrn, int n) {
        return n == 2 && arrn[0] == this.mQuad1 && arrn[1] == this.mQuad2;
    }
}


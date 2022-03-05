/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.sym;

public abstract class Name {
    protected final int _hashCode;
    protected final String _name;

    protected Name(String string2, int n) {
        this._name = string2;
        this._hashCode = n;
    }

    public abstract boolean equals(int var1);

    public abstract boolean equals(int var1, int var2);

    public boolean equals(Object object) {
        return object == this;
    }

    public abstract boolean equals(int[] var1, int var2);

    public String getName() {
        return this._name;
    }

    public final int hashCode() {
        return this._hashCode;
    }

    public String toString() {
        return this._name;
    }
}


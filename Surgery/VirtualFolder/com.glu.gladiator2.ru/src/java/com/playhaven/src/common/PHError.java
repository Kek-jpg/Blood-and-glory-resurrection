/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.playhaven.src.common;

public class PHError {
    protected int errorCode;
    protected String message;

    public PHError(int n) {
        super("", n);
    }

    public PHError(String string2) {
        super(string2, -1);
    }

    public PHError(String string2, int n) {
        this.message = string2;
        this.errorCode = n;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        Object[] arrobject = new Object[]{this.message, this.errorCode};
        return String.format((String)"PHError with message '%s' and error code %d", (Object[])arrobject);
    }
}


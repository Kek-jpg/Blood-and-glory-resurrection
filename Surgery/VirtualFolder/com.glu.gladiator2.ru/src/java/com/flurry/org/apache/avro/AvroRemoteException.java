/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.flurry.org.apache.avro;

import java.io.IOException;

public class AvroRemoteException
extends IOException {
    private Object value;

    protected AvroRemoteException() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public AvroRemoteException(Object object) {
        String string = object != null ? object.toString() : null;
        super(string);
        this.value = object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public AvroRemoteException(Object object, Throwable throwable) {
        String string = object != null ? object.toString() : null;
        super(string, throwable);
        this.value = object;
    }

    public AvroRemoteException(Throwable throwable) {
        super(throwable.toString());
        this.initCause(throwable);
    }

    public Object getValue() {
        return this.value;
    }
}


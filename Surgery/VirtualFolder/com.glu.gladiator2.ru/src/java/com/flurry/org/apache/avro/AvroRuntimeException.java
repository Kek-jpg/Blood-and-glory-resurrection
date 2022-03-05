/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.flurry.org.apache.avro;

public class AvroRuntimeException
extends RuntimeException {
    public AvroRuntimeException(String string) {
        super(string);
    }

    public AvroRuntimeException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public AvroRuntimeException(Throwable throwable) {
        super(throwable);
    }
}


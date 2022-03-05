/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.flurry.org.apache.avro;

import com.flurry.org.apache.avro.AvroRuntimeException;

public class AvroTypeException
extends AvroRuntimeException {
    public AvroTypeException(String string) {
        super(string);
    }

    public AvroTypeException(String string, Throwable throwable) {
        super(string, throwable);
    }
}


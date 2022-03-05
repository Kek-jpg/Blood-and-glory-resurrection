/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Throwable
 */
package com.flurry.org.apache.avro.data;

import com.flurry.org.apache.avro.data.RecordBuilder;

public interface ErrorBuilder<T>
extends RecordBuilder<T> {
    public ErrorBuilder<T> clearCause();

    public ErrorBuilder<T> clearValue();

    public Throwable getCause();

    public Object getValue();

    public boolean hasCause();

    public boolean hasValue();

    public ErrorBuilder<T> setCause(Throwable var1);

    public ErrorBuilder<T> setValue(Object var1);
}


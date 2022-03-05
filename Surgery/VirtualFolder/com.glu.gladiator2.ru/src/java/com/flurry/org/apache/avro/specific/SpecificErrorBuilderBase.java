/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.lang.reflect.Constructor
 */
package com.flurry.org.apache.avro.specific;

import com.flurry.org.apache.avro.AvroRemoteException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.ErrorBuilder;
import com.flurry.org.apache.avro.data.RecordBuilderBase;
import com.flurry.org.apache.avro.generic.GenericData;
import com.flurry.org.apache.avro.specific.SpecificData;
import com.flurry.org.apache.avro.specific.SpecificExceptionBase;
import java.lang.reflect.Constructor;

public abstract class SpecificErrorBuilderBase<T extends SpecificExceptionBase>
extends RecordBuilderBase<T>
implements ErrorBuilder<T> {
    private Throwable cause;
    private Constructor<T> errorConstructor;
    private boolean hasCause;
    private boolean hasValue;
    private Object value;

    protected SpecificErrorBuilderBase(Schema schema) {
        super(schema, (GenericData)SpecificData.get());
    }

    protected SpecificErrorBuilderBase(SpecificErrorBuilderBase<T> specificErrorBuilderBase) {
        super(specificErrorBuilderBase, (GenericData)SpecificData.get());
        this.errorConstructor = specificErrorBuilderBase.errorConstructor;
        this.value = specificErrorBuilderBase.value;
        this.hasValue = specificErrorBuilderBase.hasValue;
        this.cause = specificErrorBuilderBase.cause;
        this.hasCause = specificErrorBuilderBase.hasCause;
    }

    protected SpecificErrorBuilderBase(T t2) {
        Throwable throwable;
        super(((SpecificExceptionBase)t2).getSchema(), (GenericData)SpecificData.get());
        Object object = ((AvroRemoteException)((Object)t2)).getValue();
        if (object != null) {
            this.setValue(object);
        }
        if ((throwable = t2.getCause()) != null) {
            this.setCause(throwable);
        }
    }

    @Override
    public SpecificErrorBuilderBase<T> clearCause() {
        this.cause = null;
        this.hasCause = false;
        return this;
    }

    @Override
    public SpecificErrorBuilderBase<T> clearValue() {
        this.value = null;
        this.hasValue = false;
        return this;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public boolean hasCause() {
        return this.hasCause;
    }

    @Override
    public boolean hasValue() {
        return this.hasValue;
    }

    @Override
    public SpecificErrorBuilderBase<T> setCause(Throwable throwable) {
        this.cause = throwable;
        this.hasCause = true;
        return this;
    }

    @Override
    public SpecificErrorBuilderBase<T> setValue(Object object) {
        this.value = object;
        this.hasValue = true;
        return this;
    }
}


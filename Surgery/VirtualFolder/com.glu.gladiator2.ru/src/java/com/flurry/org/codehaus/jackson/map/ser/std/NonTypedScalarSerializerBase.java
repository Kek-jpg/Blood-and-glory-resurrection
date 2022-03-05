/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.ScalarSerializerBase;
import java.io.IOException;

public abstract class NonTypedScalarSerializerBase<T>
extends ScalarSerializerBase<T> {
    protected NonTypedScalarSerializerBase(Class<T> class_) {
        super(class_);
    }

    @Override
    public final void serializeWithType(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        this.serialize(t, jsonGenerator, serializerProvider);
    }
}


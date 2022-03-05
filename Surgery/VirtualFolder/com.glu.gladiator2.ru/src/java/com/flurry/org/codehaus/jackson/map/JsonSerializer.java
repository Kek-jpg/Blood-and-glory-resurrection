/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import java.io.IOException;

public abstract class JsonSerializer<T> {
    public Class<T> handledType() {
        return null;
    }

    public boolean isUnwrappingSerializer() {
        return false;
    }

    public abstract void serialize(T var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonProcessingException;

    public void serializeWithType(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        this.serialize(t, jsonGenerator, serializerProvider);
    }

    public JsonSerializer<T> unwrappingSerializer() {
        return this;
    }

    public static abstract class None
    extends JsonSerializer<Object> {
    }

}


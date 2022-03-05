/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.UnsupportedOperationException
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import java.io.IOException;

public abstract class JsonDeserializer<T> {
    public abstract T deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException;

    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, T t) throws IOException, JsonProcessingException {
        throw new UnsupportedOperationException();
    }

    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

    public T getEmptyValue() {
        return this.getNullValue();
    }

    public T getNullValue() {
        return null;
    }

    public JsonDeserializer<T> unwrappingDeserializer() {
        return this;
    }

    public static abstract class None
    extends JsonDeserializer<Object> {
    }

}


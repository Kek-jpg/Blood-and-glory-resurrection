/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeIdResolver;
import java.io.IOException;

public abstract class TypeDeserializer {
    public abstract Object deserializeTypedFromAny(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException;

    public abstract Object deserializeTypedFromArray(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException;

    public abstract Object deserializeTypedFromObject(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException;

    public abstract Object deserializeTypedFromScalar(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException;

    public abstract Class<?> getDefaultImpl();

    public abstract String getPropertyName();

    public abstract TypeIdResolver getTypeIdResolver();

    public abstract JsonTypeInfo.As getTypeInclusion();
}


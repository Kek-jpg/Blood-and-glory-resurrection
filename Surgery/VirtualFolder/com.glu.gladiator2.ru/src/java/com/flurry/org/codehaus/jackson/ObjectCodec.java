/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.type.JavaType
 *  com.flurry.org.codehaus.jackson.type.TypeReference
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.util.Iterator
 */
package com.flurry.org.codehaus.jackson;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.type.JavaType;
import com.flurry.org.codehaus.jackson.type.TypeReference;
import java.io.IOException;
import java.util.Iterator;

public abstract class ObjectCodec {
    protected ObjectCodec() {
    }

    public abstract JsonNode createArrayNode();

    public abstract JsonNode createObjectNode();

    public abstract JsonNode readTree(JsonParser var1) throws IOException, JsonProcessingException;

    public abstract <T> T readValue(JsonParser var1, JavaType var2) throws IOException, JsonProcessingException;

    public abstract <T> T readValue(JsonParser var1, TypeReference<?> var2) throws IOException, JsonProcessingException;

    public abstract <T> T readValue(JsonParser var1, Class<T> var2) throws IOException, JsonProcessingException;

    public abstract <T> Iterator<T> readValues(JsonParser var1, JavaType var2) throws IOException, JsonProcessingException;

    public abstract <T> Iterator<T> readValues(JsonParser var1, TypeReference<?> var2) throws IOException, JsonProcessingException;

    public abstract <T> Iterator<T> readValues(JsonParser var1, Class<T> var2) throws IOException, JsonProcessingException;

    public abstract JsonParser treeAsTokens(JsonNode var1);

    public abstract <T> T treeToValue(JsonNode var1, Class<T> var2) throws IOException, JsonProcessingException;

    public abstract void writeTree(JsonGenerator var1, JsonNode var2) throws IOException, JsonProcessingException;

    public abstract void writeValue(JsonGenerator var1, Object var2) throws IOException, JsonProcessingException;
}


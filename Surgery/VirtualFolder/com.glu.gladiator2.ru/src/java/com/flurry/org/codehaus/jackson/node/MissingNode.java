/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.SerializerProvider
 *  com.flurry.org.codehaus.jackson.map.TypeSerializer
 *  com.flurry.org.codehaus.jackson.node.BaseJsonNode
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.node.BaseJsonNode;
import java.io.IOException;

public final class MissingNode
extends BaseJsonNode {
    private static final MissingNode instance = new MissingNode();

    private MissingNode() {
    }

    public static MissingNode getInstance() {
        return instance;
    }

    public double asDouble(double d2) {
        return 0.0;
    }

    public int asInt(int n) {
        return 0;
    }

    public long asLong(long l) {
        return 0L;
    }

    public String asText() {
        return "";
    }

    public JsonToken asToken() {
        return JsonToken.NOT_AVAILABLE;
    }

    public boolean equals(Object object) {
        return object == this;
    }

    public boolean isMissingNode() {
        return true;
    }

    public JsonNode path(int n) {
        return this;
    }

    public JsonNode path(String string2) {
        return this;
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNull();
    }

    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        jsonGenerator.writeNull();
    }

    public String toString() {
        return "";
    }
}


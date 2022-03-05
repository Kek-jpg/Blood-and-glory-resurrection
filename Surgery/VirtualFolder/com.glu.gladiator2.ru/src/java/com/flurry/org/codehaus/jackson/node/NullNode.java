/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.SerializerProvider
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.node.ValueNode;
import java.io.IOException;

public final class NullNode
extends ValueNode {
    public static final NullNode instance = new NullNode();

    private NullNode() {
    }

    public static NullNode getInstance() {
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
        return "null";
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NULL;
    }

    public boolean equals(Object object) {
        return object == this;
    }

    public boolean isNull() {
        return true;
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNull();
    }
}


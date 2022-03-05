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
import com.flurry.org.codehaus.jackson.node.MissingNode;
import java.io.IOException;

public abstract class ValueNode
extends BaseJsonNode {
    protected ValueNode() {
    }

    public abstract JsonToken asToken();

    public boolean isValueNode() {
        return true;
    }

    public JsonNode path(int n) {
        return MissingNode.getInstance();
    }

    public JsonNode path(String string2) {
        return MissingNode.getInstance();
    }

    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        typeSerializer.writeTypePrefixForScalar((Object)this, jsonGenerator);
        this.serialize(jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForScalar((Object)this, jsonGenerator);
    }

    public String toString() {
        return this.asText();
    }
}


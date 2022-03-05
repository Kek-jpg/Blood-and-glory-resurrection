/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.String
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.JsonSerializableWithType;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.node.MissingNode;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.node.TreeTraversingParser;
import java.io.IOException;
import java.util.List;

public abstract class BaseJsonNode
extends JsonNode
implements JsonSerializableWithType {
    protected BaseJsonNode() {
    }

    @Override
    public abstract JsonToken asToken();

    @Override
    public ObjectNode findParent(String string) {
        return null;
    }

    @Override
    public List<JsonNode> findParents(String string, List<JsonNode> list) {
        return list;
    }

    @Override
    public final JsonNode findPath(String string) {
        JsonNode jsonNode = this.findValue(string);
        if (jsonNode == null) {
            jsonNode = MissingNode.getInstance();
        }
        return jsonNode;
    }

    @Override
    public JsonNode findValue(String string) {
        return null;
    }

    @Override
    public List<JsonNode> findValues(String string, List<JsonNode> list) {
        return list;
    }

    @Override
    public List<String> findValuesAsText(String string, List<String> list) {
        return list;
    }

    @Override
    public JsonParser.NumberType getNumberType() {
        return null;
    }

    @Override
    public abstract void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException;

    @Override
    public abstract void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException, JsonProcessingException;

    @Override
    public JsonParser traverse() {
        return new TreeTraversingParser(this);
    }
}


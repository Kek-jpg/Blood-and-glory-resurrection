/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Deprecated
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.node.ArrayNode;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import java.io.IOException;

@Deprecated
public class JsonNodeDeserializer
extends com.flurry.org.codehaus.jackson.map.deser.std.JsonNodeDeserializer {
    @Deprecated
    public static final JsonNodeDeserializer instance = new JsonNodeDeserializer();

    @Deprecated
    protected final JsonNode deserializeAny(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserializeAny(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
    }

    @Deprecated
    protected final ArrayNode deserializeArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserializeArray(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
    }

    @Deprecated
    protected final ObjectNode deserializeObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserializeObject(jsonParser, deserializationContext, deserializationContext.getNodeFactory());
    }
}


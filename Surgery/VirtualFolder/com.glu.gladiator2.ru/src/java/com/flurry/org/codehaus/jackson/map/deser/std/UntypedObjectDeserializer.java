/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.NoSuchFieldError
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 *  java.math.BigDecimal
 *  java.math.BigInteger
 *  java.util.ArrayList
 *  java.util.LinkedHashMap
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.deser.std.StdDeserializer;
import com.flurry.org.codehaus.jackson.map.util.ObjectBuffer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@JacksonStdImpl
public class UntypedObjectDeserializer
extends StdDeserializer<Object> {
    private static final Object[] NO_OBJECTS = new Object[0];

    public UntypedObjectDeserializer() {
        super(Object.class);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
            default: {
                throw deserializationContext.mappingException(Object.class);
            }
            case 1: {
                return this.mapObject(jsonParser, deserializationContext);
            }
            case 3: {
                return this.mapArray(jsonParser, deserializationContext);
            }
            case 5: {
                return this.mapObject(jsonParser, deserializationContext);
            }
            case 6: {
                return jsonParser.getEmbeddedObject();
            }
            case 7: {
                return jsonParser.getText();
            }
            case 8: {
                if (deserializationContext.isEnabled(DeserializationConfig.Feature.USE_BIG_INTEGER_FOR_INTS)) {
                    return jsonParser.getBigIntegerValue();
                }
                return jsonParser.getNumberValue();
            }
            case 9: {
                if (deserializationContext.isEnabled(DeserializationConfig.Feature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jsonParser.getDecimalValue();
                }
                return jsonParser.getDoubleValue();
            }
            case 10: {
                return Boolean.TRUE;
            }
            case 11: {
                return Boolean.FALSE;
            }
            case 12: 
        }
        return null;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonToken.ordinal()]) {
            default: {
                throw deserializationContext.mappingException(Object.class);
            }
            case 1: 
            case 3: 
            case 5: {
                return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
            }
            case 7: {
                return jsonParser.getText();
            }
            case 8: {
                if (deserializationContext.isEnabled(DeserializationConfig.Feature.USE_BIG_INTEGER_FOR_INTS)) {
                    return jsonParser.getBigIntegerValue();
                }
                return jsonParser.getIntValue();
            }
            case 9: {
                if (deserializationContext.isEnabled(DeserializationConfig.Feature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jsonParser.getDecimalValue();
                }
                return jsonParser.getDoubleValue();
            }
            case 10: {
                return Boolean.TRUE;
            }
            case 11: {
                return Boolean.FALSE;
            }
            case 6: {
                return jsonParser.getEmbeddedObject();
            }
            case 12: 
        }
        return null;
    }

    protected Object mapArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (deserializationContext.isEnabled(DeserializationConfig.Feature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
            return this.mapArrayToArray(jsonParser, deserializationContext);
        }
        if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
            return new ArrayList(4);
        }
        ObjectBuffer objectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] arrobject = objectBuffer.resetAndStart();
        int n = 0;
        int n2 = 0;
        do {
            Object object = this.deserialize(jsonParser, deserializationContext);
            ++n2;
            if (n >= arrobject.length) {
                arrobject = objectBuffer.appendCompletedChunk(arrobject);
                n = 0;
            }
            int n3 = n + 1;
            arrobject[n] = object;
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                ArrayList arrayList = new ArrayList(1 + (n2 + (n2 >> 3)));
                objectBuffer.completeAndClearBuffer(arrobject, n3, (List<Object>)arrayList);
                return arrayList;
            }
            n = n3;
        } while (true);
    }

    protected Object[] mapArrayToArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
            return NO_OBJECTS;
        }
        ObjectBuffer objectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] arrobject = objectBuffer.resetAndStart();
        int n = 0;
        do {
            Object object = this.deserialize(jsonParser, deserializationContext);
            if (n >= arrobject.length) {
                arrobject = objectBuffer.appendCompletedChunk(arrobject);
                n = 0;
            }
            int n2 = n + 1;
            arrobject[n] = object;
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                return objectBuffer.completeAndClearBuffer(arrobject, n2);
            }
            n = n2;
        } while (true);
    }

    protected Object mapObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonToken = jsonParser.nextToken();
        }
        if (jsonToken != JsonToken.FIELD_NAME) {
            return new LinkedHashMap(4);
        }
        String string = jsonParser.getText();
        jsonParser.nextToken();
        Object object = this.deserialize(jsonParser, deserializationContext);
        if (jsonParser.nextToken() != JsonToken.FIELD_NAME) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(4);
            linkedHashMap.put((Object)string, object);
            return linkedHashMap;
        }
        String string2 = jsonParser.getText();
        jsonParser.nextToken();
        Object object2 = this.deserialize(jsonParser, deserializationContext);
        if (jsonParser.nextToken() != JsonToken.FIELD_NAME) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(4);
            linkedHashMap.put((Object)string, object);
            linkedHashMap.put((Object)string2, object2);
            return linkedHashMap;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put((Object)string, object);
        linkedHashMap.put((Object)string2, object2);
        do {
            String string3 = jsonParser.getText();
            jsonParser.nextToken();
            linkedHashMap.put((Object)string3, this.deserialize(jsonParser, deserializationContext));
        } while (jsonParser.nextToken() != JsonToken.END_OBJECT);
        return linkedHashMap;
    }

}


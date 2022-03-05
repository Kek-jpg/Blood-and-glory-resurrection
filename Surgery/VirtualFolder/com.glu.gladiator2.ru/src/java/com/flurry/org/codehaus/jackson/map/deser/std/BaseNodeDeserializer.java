/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.math.BigDecimal
 *  java.math.BigInteger
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.StdDeserializer;
import com.flurry.org.codehaus.jackson.node.ArrayNode;
import com.flurry.org.codehaus.jackson.node.BinaryNode;
import com.flurry.org.codehaus.jackson.node.BooleanNode;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.node.NullNode;
import com.flurry.org.codehaus.jackson.node.NumericNode;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.node.POJONode;
import com.flurry.org.codehaus.jackson.node.TextNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

abstract class BaseNodeDeserializer<N extends JsonNode>
extends StdDeserializer<N> {
    public BaseNodeDeserializer(Class<N> class_) {
        super(class_);
    }

    protected void _handleDuplicateField(String string, ObjectNode objectNode, JsonNode jsonNode, JsonNode jsonNode2) throws JsonProcessingException {
    }

    protected void _reportProblem(JsonParser jsonParser, String string) throws JsonMappingException {
        throw new JsonMappingException(string, jsonParser.getTokenLocation());
    }

    protected final JsonNode deserializeAny(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException, JsonProcessingException {
        switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
            default: {
                throw deserializationContext.mappingException(this.getValueClass());
            }
            case 1: {
                return this.deserializeObject(jsonParser, deserializationContext, jsonNodeFactory);
            }
            case 2: {
                return this.deserializeArray(jsonParser, deserializationContext, jsonNodeFactory);
            }
            case 5: {
                return this.deserializeObject(jsonParser, deserializationContext, jsonNodeFactory);
            }
            case 6: {
                Object object = jsonParser.getEmbeddedObject();
                if (object == null) {
                    return jsonNodeFactory.nullNode();
                }
                if (object.getClass() == byte[].class) {
                    return jsonNodeFactory.binaryNode((byte[])object);
                }
                return jsonNodeFactory.POJONode(object);
            }
            case 3: {
                return jsonNodeFactory.textNode(jsonParser.getText());
            }
            case 7: {
                JsonParser.NumberType numberType = jsonParser.getNumberType();
                if (numberType == JsonParser.NumberType.BIG_INTEGER || deserializationContext.isEnabled(DeserializationConfig.Feature.USE_BIG_INTEGER_FOR_INTS)) {
                    return jsonNodeFactory.numberNode(jsonParser.getBigIntegerValue());
                }
                if (numberType == JsonParser.NumberType.INT) {
                    return jsonNodeFactory.numberNode(jsonParser.getIntValue());
                }
                return jsonNodeFactory.numberNode(jsonParser.getLongValue());
            }
            case 8: {
                if (jsonParser.getNumberType() == JsonParser.NumberType.BIG_DECIMAL || deserializationContext.isEnabled(DeserializationConfig.Feature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jsonNodeFactory.numberNode(jsonParser.getDecimalValue());
                }
                return jsonNodeFactory.numberNode(jsonParser.getDoubleValue());
            }
            case 9: {
                return jsonNodeFactory.booleanNode(true);
            }
            case 10: {
                return jsonNodeFactory.booleanNode(false);
            }
            case 11: 
        }
        return jsonNodeFactory.nullNode();
    }

    protected final ArrayNode deserializeArray(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException, JsonProcessingException {
        ArrayNode arrayNode = jsonNodeFactory.arrayNode();
        block6 : do {
            switch (jsonParser.nextToken()) {
                default: {
                    arrayNode.add(this.deserializeAny(jsonParser, deserializationContext, jsonNodeFactory));
                    continue block6;
                }
                case START_OBJECT: {
                    arrayNode.add(this.deserializeObject(jsonParser, deserializationContext, jsonNodeFactory));
                    continue block6;
                }
                case START_ARRAY: {
                    arrayNode.add(this.deserializeArray(jsonParser, deserializationContext, jsonNodeFactory));
                    continue block6;
                }
                case VALUE_STRING: {
                    arrayNode.add(jsonNodeFactory.textNode(jsonParser.getText()));
                    continue block6;
                }
                case END_ARRAY: 
            }
            break;
        } while (true);
        return arrayNode;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final ObjectNode deserializeObject(JsonParser jsonParser, DeserializationContext deserializationContext, JsonNodeFactory jsonNodeFactory) throws IOException, JsonProcessingException {
        ObjectNode objectNode = jsonNodeFactory.objectNode();
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonToken = jsonParser.nextToken();
        }
        while (jsonToken == JsonToken.FIELD_NAME) {
            void var7_7;
            JsonNode jsonNode;
            String string = jsonParser.getCurrentName();
            switch (jsonParser.nextToken()) {
                default: {
                    JsonNode jsonNode2 = this.deserializeAny(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                }
                case START_OBJECT: {
                    ObjectNode objectNode2 = this.deserializeObject(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                }
                case START_ARRAY: {
                    ArrayNode arrayNode = this.deserializeArray(jsonParser, deserializationContext, jsonNodeFactory);
                    break;
                }
                case VALUE_STRING: {
                    TextNode textNode = jsonNodeFactory.textNode(jsonParser.getText());
                }
            }
            if ((jsonNode = objectNode.put(string, (JsonNode)var7_7)) != null) {
                this._handleDuplicateField(string, objectNode, jsonNode, (JsonNode)var7_7);
            }
            jsonToken = jsonParser.nextToken();
        }
        return objectNode;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

}


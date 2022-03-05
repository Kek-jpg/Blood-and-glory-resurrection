/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Double
 *  java.lang.Integer
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.jsontype.impl;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeIdResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.AsArrayTypeDeserializer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import com.flurry.org.codehaus.jackson.util.JsonParserSequence;
import com.flurry.org.codehaus.jackson.util.TokenBuffer;
import java.io.IOException;

public class AsPropertyTypeDeserializer
extends AsArrayTypeDeserializer {
    protected final String _typePropertyName;

    public AsPropertyTypeDeserializer(JavaType javaType, TypeIdResolver typeIdResolver, BeanProperty beanProperty, Class<?> class_, String string) {
        super(javaType, typeIdResolver, beanProperty, class_);
        this._typePropertyName = string;
    }

    @Deprecated
    public AsPropertyTypeDeserializer(JavaType javaType, TypeIdResolver typeIdResolver, BeanProperty beanProperty, String string) {
        super(javaType, typeIdResolver, beanProperty, null, string);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object _deserializeIfNatural(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
            case 1: {
                if (!this._baseType.getRawClass().isAssignableFrom(String.class)) return null;
                return jsonParser.getText();
            }
            case 2: {
                if (!this._baseType.getRawClass().isAssignableFrom(Integer.class)) return null;
                return jsonParser.getIntValue();
            }
            case 3: {
                if (!this._baseType.getRawClass().isAssignableFrom(Double.class)) return null;
                return jsonParser.getDoubleValue();
            }
            case 4: {
                if (!this._baseType.getRawClass().isAssignableFrom(Boolean.class)) return null;
                return Boolean.TRUE;
            }
            default: {
                return null;
            }
            case 5: 
        }
        if (!this._baseType.getRawClass().isAssignableFrom(Boolean.class)) return null;
        return Boolean.FALSE;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Object _deserializeTypedUsingDefaultImpl(JsonParser jsonParser, DeserializationContext deserializationContext, TokenBuffer tokenBuffer) throws IOException, JsonProcessingException {
        if (this._defaultImpl != null) {
            JsonDeserializer<Object> jsonDeserializer = this._findDefaultImplDeserializer(deserializationContext);
            if (tokenBuffer == null) return jsonDeserializer.deserialize(jsonParser, deserializationContext);
            tokenBuffer.writeEndObject();
            jsonParser = tokenBuffer.asParser(jsonParser);
            jsonParser.nextToken();
            return jsonDeserializer.deserialize(jsonParser, deserializationContext);
        }
        Object object = this._deserializeIfNatural(jsonParser, deserializationContext);
        if (object != null) return object;
        if (jsonParser.getCurrentToken() != JsonToken.START_ARRAY) throw deserializationContext.wrongTokenException(jsonParser, JsonToken.FIELD_NAME, "missing property '" + this._typePropertyName + "' that is to contain type id  (for class " + this.baseTypeName() + ")");
        return super.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

    @Override
    public Object deserializeTypedFromAny(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
            return super.deserializeTypedFromArray(jsonParser, deserializationContext);
        }
        return this.deserializeTypedFromObject(jsonParser, deserializationContext);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Object deserializeTypedFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonToken = jsonParser.nextToken();
        } else {
            if (jsonToken == JsonToken.START_ARRAY) {
                return this._deserializeTypedUsingDefaultImpl(jsonParser, deserializationContext, null);
            }
            if (jsonToken != JsonToken.FIELD_NAME) {
                return this._deserializeTypedUsingDefaultImpl(jsonParser, deserializationContext, null);
            }
        }
        TokenBuffer tokenBuffer = null;
        while (jsonToken == JsonToken.FIELD_NAME) {
            String string = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if (this._typePropertyName.equals((Object)string)) {
                JsonDeserializer<Object> jsonDeserializer = this._findDeserializer(deserializationContext, jsonParser.getText());
                if (tokenBuffer != null) {
                    jsonParser = JsonParserSequence.createFlattened(tokenBuffer.asParser(jsonParser), jsonParser);
                }
                jsonParser.nextToken();
                return jsonDeserializer.deserialize(jsonParser, deserializationContext);
            }
            if (tokenBuffer == null) {
                tokenBuffer = new TokenBuffer(null);
            }
            tokenBuffer.writeFieldName(string);
            tokenBuffer.copyCurrentStructure(jsonParser);
            jsonToken = jsonParser.nextToken();
        }
        return this._deserializeTypedUsingDefaultImpl(jsonParser, deserializationContext, tokenBuffer);
    }

    @Override
    public String getPropertyName() {
        return this._typePropertyName;
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.PROPERTY;
    }

}


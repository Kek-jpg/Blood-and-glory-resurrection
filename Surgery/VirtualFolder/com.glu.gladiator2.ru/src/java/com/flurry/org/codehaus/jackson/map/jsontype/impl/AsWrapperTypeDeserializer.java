/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.jsontype.impl;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeIdResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.TypeDeserializerBase;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;

public class AsWrapperTypeDeserializer
extends TypeDeserializerBase {
    @Deprecated
    public AsWrapperTypeDeserializer(JavaType javaType, TypeIdResolver typeIdResolver, BeanProperty beanProperty) {
        super(javaType, typeIdResolver, beanProperty, null);
    }

    public AsWrapperTypeDeserializer(JavaType javaType, TypeIdResolver typeIdResolver, BeanProperty beanProperty, Class<?> class_) {
        super(javaType, typeIdResolver, beanProperty, null);
    }

    private final Object _deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
            throw deserializationContext.wrongTokenException(jsonParser, JsonToken.START_OBJECT, "need JSON Object to contain As.WRAPPER_OBJECT type information for class " + this.baseTypeName());
        }
        if (jsonParser.nextToken() != JsonToken.FIELD_NAME) {
            throw deserializationContext.wrongTokenException(jsonParser, JsonToken.FIELD_NAME, "need JSON String that contains type id (for subtype of " + this.baseTypeName() + ")");
        }
        JsonDeserializer<Object> jsonDeserializer = this._findDeserializer(deserializationContext, jsonParser.getText());
        jsonParser.nextToken();
        Object object = jsonDeserializer.deserialize(jsonParser, deserializationContext);
        if (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            throw deserializationContext.wrongTokenException(jsonParser, JsonToken.END_OBJECT, "expected closing END_OBJECT after type information and deserialized value");
        }
        return object;
    }

    @Override
    public Object deserializeTypedFromAny(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return AsWrapperTypeDeserializer.super._deserialize(jsonParser, deserializationContext);
    }

    @Override
    public Object deserializeTypedFromArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return AsWrapperTypeDeserializer.super._deserialize(jsonParser, deserializationContext);
    }

    @Override
    public Object deserializeTypedFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return AsWrapperTypeDeserializer.super._deserialize(jsonParser, deserializationContext);
    }

    @Override
    public Object deserializeTypedFromScalar(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return AsWrapperTypeDeserializer.super._deserialize(jsonParser, deserializationContext);
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_OBJECT;
    }
}


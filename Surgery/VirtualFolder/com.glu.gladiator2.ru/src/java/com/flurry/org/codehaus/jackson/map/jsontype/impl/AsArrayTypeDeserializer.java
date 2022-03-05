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
import com.flurry.org.codehaus.jackson.map.jsontype.impl.TypeIdResolverBase;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;

public class AsArrayTypeDeserializer
extends TypeDeserializerBase {
    @Deprecated
    public AsArrayTypeDeserializer(JavaType javaType, TypeIdResolver typeIdResolver, BeanProperty beanProperty) {
        super(javaType, typeIdResolver, beanProperty, null);
    }

    public AsArrayTypeDeserializer(JavaType javaType, TypeIdResolver typeIdResolver, BeanProperty beanProperty, Class<?> class_) {
        super(javaType, typeIdResolver, beanProperty, class_);
    }

    private final Object _deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        boolean bl = jsonParser.isExpectedStartArrayToken();
        Object object = this._findDeserializer(deserializationContext, this._locateTypeId(jsonParser, deserializationContext)).deserialize(jsonParser, deserializationContext);
        if (bl && jsonParser.nextToken() != JsonToken.END_ARRAY) {
            throw deserializationContext.wrongTokenException(jsonParser, JsonToken.END_ARRAY, "expected closing END_ARRAY after type information and deserialized value");
        }
        return object;
    }

    protected final String _locateTypeId(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (!jsonParser.isExpectedStartArrayToken()) {
            if (this._idResolver instanceof TypeIdResolverBase && this._defaultImpl != null) {
                return ((TypeIdResolverBase)this._idResolver).idFromBaseType();
            }
            throw deserializationContext.wrongTokenException(jsonParser, JsonToken.START_ARRAY, "need JSON Array to contain As.WRAPPER_ARRAY type information for class " + this.baseTypeName());
        }
        if (jsonParser.nextToken() != JsonToken.VALUE_STRING) {
            if (this._idResolver instanceof TypeIdResolverBase && this._defaultImpl != null) {
                return ((TypeIdResolverBase)this._idResolver).idFromBaseType();
            }
            throw deserializationContext.wrongTokenException(jsonParser, JsonToken.VALUE_STRING, "need JSON String that contains type id (for subtype of " + this.baseTypeName() + ")");
        }
        String string = jsonParser.getText();
        jsonParser.nextToken();
        return string;
    }

    @Override
    public Object deserializeTypedFromAny(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return AsArrayTypeDeserializer.super._deserialize(jsonParser, deserializationContext);
    }

    @Override
    public Object deserializeTypedFromArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return AsArrayTypeDeserializer.super._deserialize(jsonParser, deserializationContext);
    }

    @Override
    public Object deserializeTypedFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return AsArrayTypeDeserializer.super._deserialize(jsonParser, deserializationContext);
    }

    @Override
    public Object deserializeTypedFromScalar(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return AsArrayTypeDeserializer.super._deserialize(jsonParser, deserializationContext);
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_ARRAY;
    }
}


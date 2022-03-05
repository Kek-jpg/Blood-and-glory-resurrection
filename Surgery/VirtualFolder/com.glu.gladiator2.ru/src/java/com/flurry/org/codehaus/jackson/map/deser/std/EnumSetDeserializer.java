/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Object
 *  java.util.EnumSet
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.EnumDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.StdDeserializer;
import com.flurry.org.codehaus.jackson.map.util.EnumResolver;
import java.io.IOException;
import java.util.EnumSet;

public class EnumSetDeserializer
extends StdDeserializer<EnumSet<?>> {
    protected final Class<Enum> _enumClass;
    protected final JsonDeserializer<Enum<?>> _enumDeserializer;

    public EnumSetDeserializer(EnumResolver enumResolver) {
        super(enumResolver.getEnumClass(), new EnumDeserializer(enumResolver));
    }

    public EnumSetDeserializer(Class<?> class_, JsonDeserializer<?> jsonDeserializer) {
        super(EnumSet.class);
        this._enumClass = class_;
        this._enumDeserializer = jsonDeserializer;
    }

    private EnumSet constructSet() {
        return EnumSet.noneOf(this._enumClass);
    }

    @Override
    public EnumSet<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken;
        if (!jsonParser.isExpectedStartArrayToken()) {
            throw deserializationContext.mappingException(EnumSet.class);
        }
        EnumSet enumSet = EnumSetDeserializer.super.constructSet();
        while ((jsonToken = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
            if (jsonToken == JsonToken.VALUE_NULL) {
                throw deserializationContext.mappingException(this._enumClass);
            }
            enumSet.add(this._enumDeserializer.deserialize(jsonParser, deserializationContext));
        }
        return enumSet;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }
}


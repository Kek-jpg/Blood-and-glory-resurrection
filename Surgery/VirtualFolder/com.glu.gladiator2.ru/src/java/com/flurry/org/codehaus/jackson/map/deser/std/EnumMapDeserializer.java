/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.util.EnumMap
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
import java.util.EnumMap;

public class EnumMapDeserializer
extends StdDeserializer<EnumMap<?, ?>> {
    protected final Class<?> _enumClass;
    protected final JsonDeserializer<Enum<?>> _keyDeserializer;
    protected final JsonDeserializer<Object> _valueDeserializer;

    @Deprecated
    public EnumMapDeserializer(EnumResolver<?> enumResolver, JsonDeserializer<Object> jsonDeserializer) {
        super(enumResolver.getEnumClass(), new EnumDeserializer(enumResolver), jsonDeserializer);
    }

    public EnumMapDeserializer(Class<?> class_, JsonDeserializer<?> jsonDeserializer, JsonDeserializer<Object> jsonDeserializer2) {
        super(EnumMap.class);
        this._enumClass = class_;
        this._keyDeserializer = jsonDeserializer;
        this._valueDeserializer = jsonDeserializer2;
    }

    private EnumMap<?, ?> constructMap() {
        return new EnumMap(this._enumClass);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public EnumMap<?, ?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
            throw deserializationContext.mappingException(EnumMap.class);
        }
        EnumMap<?, ?> enumMap = EnumMapDeserializer.super.constructMap();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            Enum<?> enum_ = this._keyDeserializer.deserialize(jsonParser, deserializationContext);
            if (enum_ == null) {
                throw deserializationContext.weirdStringException(this._enumClass, "value not one of declared Enum instance names");
            }
            Object object = jsonParser.nextToken() == JsonToken.VALUE_NULL ? null : this._valueDeserializer.deserialize(jsonParser, deserializationContext);
            enumMap.put(enum_, object);
        }
        return enumMap;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext);
    }
}


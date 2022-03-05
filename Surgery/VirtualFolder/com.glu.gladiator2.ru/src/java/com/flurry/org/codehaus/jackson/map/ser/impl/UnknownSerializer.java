/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.map.ser.impl;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;
import java.io.IOException;
import java.lang.reflect.Type;

public class UnknownSerializer
extends SerializerBase<Object> {
    public UnknownSerializer() {
        super(Object.class);
    }

    protected void failForEmpty(Object object) throws JsonMappingException {
        throw new JsonMappingException("No serializer found for class " + object.getClass().getName() + " and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS) )");
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        return null;
    }

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonMappingException {
        if (serializerProvider.isEnabled(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS)) {
            this.failForEmpty(object);
        }
        jsonGenerator.writeStartObject();
        jsonGenerator.writeEndObject();
    }

    @Override
    public final void serializeWithType(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        if (serializerProvider.isEnabled(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS)) {
            this.failForEmpty(object);
        }
        typeSerializer.writeTypePrefixForObject(object, jsonGenerator);
        typeSerializer.writeTypeSuffixForObject(object, jsonGenerator);
    }
}


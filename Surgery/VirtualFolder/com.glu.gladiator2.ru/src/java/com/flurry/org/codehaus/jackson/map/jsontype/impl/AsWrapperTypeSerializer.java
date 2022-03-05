/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.jsontype.impl;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeIdResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.TypeSerializerBase;
import java.io.IOException;

public class AsWrapperTypeSerializer
extends TypeSerializerBase {
    public AsWrapperTypeSerializer(TypeIdResolver typeIdResolver, BeanProperty beanProperty) {
        super(typeIdResolver, beanProperty);
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_OBJECT;
    }

    @Override
    public void writeTypePrefixForArray(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeArrayFieldStart(this._idResolver.idFromValue(object));
    }

    @Override
    public void writeTypePrefixForArray(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeArrayFieldStart(this._idResolver.idFromValueAndType(object, class_));
    }

    @Override
    public void writeTypePrefixForObject(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart(this._idResolver.idFromValue(object));
    }

    @Override
    public void writeTypePrefixForObject(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart(this._idResolver.idFromValueAndType(object, class_));
    }

    @Override
    public void writeTypePrefixForScalar(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName(this._idResolver.idFromValue(object));
    }

    @Override
    public void writeTypePrefixForScalar(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName(this._idResolver.idFromValueAndType(object, class_));
    }

    @Override
    public void writeTypeSuffixForArray(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }

    @Override
    public void writeTypeSuffixForObject(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }

    @Override
    public void writeTypeSuffixForScalar(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        jsonGenerator.writeEndObject();
    }
}


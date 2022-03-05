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

public class AsExternalTypeSerializer
extends TypeSerializerBase {
    protected final String _typePropertyName;

    public AsExternalTypeSerializer(TypeIdResolver typeIdResolver, BeanProperty beanProperty, String string) {
        super(typeIdResolver, beanProperty);
        this._typePropertyName = string;
    }

    protected final void _writePrefix(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
    }

    protected final void _writePrefix(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
    }

    protected final void _writeSuffix(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStringField(this._typePropertyName, this._idResolver.idFromValue(object));
    }

    @Override
    public String getPropertyName() {
        return this._typePropertyName;
    }

    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.EXTERNAL_PROPERTY;
    }

    @Override
    public void writeTypePrefixForArray(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        this._writePrefix(object, jsonGenerator);
    }

    @Override
    public void writeTypePrefixForArray(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException, JsonProcessingException {
        this._writePrefix(object, jsonGenerator, class_);
    }

    @Override
    public void writeTypePrefixForObject(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        this._writePrefix(object, jsonGenerator);
    }

    @Override
    public void writeTypePrefixForObject(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException, JsonProcessingException {
        this._writePrefix(object, jsonGenerator, class_);
    }

    @Override
    public void writeTypePrefixForScalar(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        this._writePrefix(object, jsonGenerator);
    }

    @Override
    public void writeTypePrefixForScalar(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException, JsonProcessingException {
        this._writePrefix(object, jsonGenerator, class_);
    }

    @Override
    public void writeTypeSuffixForArray(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        this._writeSuffix(object, jsonGenerator);
    }

    @Override
    public void writeTypeSuffixForObject(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        this._writeSuffix(object, jsonGenerator);
    }

    @Override
    public void writeTypeSuffixForScalar(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        this._writeSuffix(object, jsonGenerator);
    }
}


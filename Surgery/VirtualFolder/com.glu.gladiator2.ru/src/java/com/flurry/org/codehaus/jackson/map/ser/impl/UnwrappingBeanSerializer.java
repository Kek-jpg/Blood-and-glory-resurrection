/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.ser.impl;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.ser.std.BeanSerializerBase;
import java.io.IOException;

public class UnwrappingBeanSerializer
extends BeanSerializerBase {
    public UnwrappingBeanSerializer(BeanSerializerBase beanSerializerBase) {
        super(beanSerializerBase);
    }

    @Override
    public boolean isUnwrappingSerializer() {
        return true;
    }

    @Override
    public final void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(object, jsonGenerator, serializerProvider);
            return;
        }
        this.serializeFields(object, jsonGenerator, serializerProvider);
    }

    public String toString() {
        return "UnwrappingBeanSerializer for " + this.handledType().getName();
    }

    @Override
    public JsonSerializer<Object> unwrappingSerializer() {
        return this;
    }
}


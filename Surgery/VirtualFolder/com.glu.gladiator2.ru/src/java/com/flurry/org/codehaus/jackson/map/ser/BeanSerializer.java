/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.ser.AnyGetterWriter;
import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyWriter;
import com.flurry.org.codehaus.jackson.map.ser.impl.UnwrappingBeanSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.BeanSerializerBase;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;

public class BeanSerializer
extends BeanSerializerBase {
    protected BeanSerializer(BeanSerializer beanSerializer) {
        super(beanSerializer);
    }

    protected BeanSerializer(BeanSerializerBase beanSerializerBase) {
        super(beanSerializerBase);
    }

    public BeanSerializer(JavaType javaType, BeanPropertyWriter[] arrbeanPropertyWriter, BeanPropertyWriter[] arrbeanPropertyWriter2, AnyGetterWriter anyGetterWriter, Object object) {
        super(javaType, arrbeanPropertyWriter, arrbeanPropertyWriter2, anyGetterWriter, object);
    }

    public BeanSerializer(Class<?> class_, BeanPropertyWriter[] arrbeanPropertyWriter, BeanPropertyWriter[] arrbeanPropertyWriter2, AnyGetterWriter anyGetterWriter, Object object) {
        super(class_, arrbeanPropertyWriter, arrbeanPropertyWriter2, anyGetterWriter, object);
    }

    public static BeanSerializer createDummy(Class<?> class_) {
        return new BeanSerializer(class_, NO_PROPS, null, null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartObject();
        if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(object, jsonGenerator, serializerProvider);
        } else {
            this.serializeFields(object, jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndObject();
    }

    public String toString() {
        return "BeanSerializer for " + this.handledType().getName();
    }

    @Override
    public JsonSerializer<Object> unwrappingSerializer() {
        return new UnwrappingBeanSerializer(this);
    }
}


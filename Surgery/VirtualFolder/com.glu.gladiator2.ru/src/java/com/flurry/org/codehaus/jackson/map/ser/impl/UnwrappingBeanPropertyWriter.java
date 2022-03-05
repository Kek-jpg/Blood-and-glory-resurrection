/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.ser.impl;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyWriter;
import com.flurry.org.codehaus.jackson.map.ser.impl.PropertySerializerMap;
import com.flurry.org.codehaus.jackson.type.JavaType;

public class UnwrappingBeanPropertyWriter
extends BeanPropertyWriter {
    public UnwrappingBeanPropertyWriter(BeanPropertyWriter beanPropertyWriter) {
        super(beanPropertyWriter);
    }

    public UnwrappingBeanPropertyWriter(BeanPropertyWriter beanPropertyWriter, JsonSerializer<Object> jsonSerializer) {
        super(beanPropertyWriter, jsonSerializer);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, Class<?> class_, SerializerProvider serializerProvider) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer = this._nonTrivialBaseType != null ? serializerProvider.findValueSerializer(serializerProvider.constructSpecializedType(this._nonTrivialBaseType, class_), (BeanProperty)this) : serializerProvider.findValueSerializer(class_, (BeanProperty)this);
        if (!jsonSerializer.isUnwrappingSerializer()) {
            jsonSerializer = jsonSerializer.unwrappingSerializer();
        }
        this._dynamicSerializers = this._dynamicSerializers.newWith(class_, jsonSerializer);
        return jsonSerializer;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        Object object2;
        Class class_;
        PropertySerializerMap propertySerializerMap;
        block8 : {
            block7 : {
                object2 = this.get(object);
                if (object2 == null) break block7;
                if (object2 == object) {
                    this._reportSelfReference(object);
                }
                if (this._suppressableValue == null || !this._suppressableValue.equals(object2)) break block8;
            }
            return;
        }
        JsonSerializer<Object> jsonSerializer = this._serializer;
        if (jsonSerializer == null && (jsonSerializer = (propertySerializerMap = this._dynamicSerializers).serializerFor(class_ = object2.getClass())) == null) {
            jsonSerializer = this._findAndAddDynamic(propertySerializerMap, class_, serializerProvider);
        }
        if (!jsonSerializer.isUnwrappingSerializer()) {
            jsonGenerator.writeFieldName(this._name);
        }
        if (this._typeSerializer == null) {
            jsonSerializer.serialize(object2, jsonGenerator, serializerProvider);
            return;
        }
        jsonSerializer.serializeWithType(object2, jsonGenerator, serializerProvider, this._typeSerializer);
    }

    @Override
    public BeanPropertyWriter withSerializer(JsonSerializer<Object> jsonSerializer) {
        if (this.getClass() != UnwrappingBeanPropertyWriter.class) {
            throw new IllegalStateException("UnwrappingBeanPropertyWriter sub-class does not override 'withSerializer()'; needs to!");
        }
        if (!jsonSerializer.isUnwrappingSerializer()) {
            jsonSerializer = jsonSerializer.unwrappingSerializer();
        }
        return new UnwrappingBeanPropertyWriter((BeanPropertyWriter)this, jsonSerializer);
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.util.Iterator
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.ser.std.AsArraySerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.ContainerSerializerBase;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.util.Iterator;

@JacksonStdImpl
public class IterableSerializer
extends AsArraySerializerBase<Iterable<?>> {
    public IterableSerializer(JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty) {
        super(Iterable.class, javaType, bl, typeSerializer, beanProperty, null);
    }

    @Override
    public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new IterableSerializer(this._elementType, this._staticTyping, typeSerializer, this._property);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeContents(Iterable<?> iterable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        Iterator iterator = iterable.iterator();
        if (iterator.hasNext()) {
            TypeSerializer typeSerializer = this._valueTypeSerializer;
            JsonSerializer<Object> jsonSerializer = null;
            Class class_ = null;
            do {
                Object object;
                JsonSerializer<Object> jsonSerializer2;
                if ((object = iterator.next()) == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                    continue;
                }
                Class class_2 = object.getClass();
                if (class_2 == class_) {
                    jsonSerializer2 = jsonSerializer;
                } else {
                    jsonSerializer = jsonSerializer2 = serializerProvider.findValueSerializer(class_2, this._property);
                    class_ = class_2;
                }
                if (typeSerializer == null) {
                    jsonSerializer2.serialize(object, jsonGenerator, serializerProvider);
                    continue;
                }
                jsonSerializer2.serializeWithType(object, jsonGenerator, serializerProvider, typeSerializer);
            } while (iterator.hasNext());
        }
    }
}


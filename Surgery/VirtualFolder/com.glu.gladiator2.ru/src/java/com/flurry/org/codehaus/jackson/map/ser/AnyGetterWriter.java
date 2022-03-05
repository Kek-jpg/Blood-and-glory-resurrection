/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Method
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.ser.std.MapSerializer;
import java.lang.reflect.Method;
import java.util.Map;

public class AnyGetterWriter {
    protected final Method _anyGetter;
    protected final MapSerializer _serializer;

    public AnyGetterWriter(AnnotatedMethod annotatedMethod, MapSerializer mapSerializer) {
        this._anyGetter = annotatedMethod.getAnnotated();
        this._serializer = mapSerializer;
    }

    public void getAndSerialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        Object object2 = this._anyGetter.invoke(object, new Object[0]);
        if (object2 == null) {
            return;
        }
        if (!(object2 instanceof Map)) {
            throw new JsonMappingException("Value returned by 'any-getter' (" + this._anyGetter.getName() + "()) not java.util.Map but " + object2.getClass().getName());
        }
        this._serializer.serializeFields((Map)object2, jsonGenerator, serializerProvider);
    }

    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        this._serializer.resolve(serializerProvider);
    }
}


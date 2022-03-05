/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.ser.BeanPropertyWriter
 *  com.flurry.org.codehaus.jackson.map.ser.BeanSerializerBuilder
 *  java.lang.Object
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyWriter;
import com.flurry.org.codehaus.jackson.map.ser.BeanSerializerBuilder;
import java.util.List;

public abstract class BeanSerializerModifier {
    public List<BeanPropertyWriter> changeProperties(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription, List<BeanPropertyWriter> list) {
        return list;
    }

    public JsonSerializer<?> modifySerializer(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription, JsonSerializer<?> jsonSerializer) {
        return jsonSerializer;
    }

    public List<BeanPropertyWriter> orderProperties(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription, List<BeanPropertyWriter> list) {
        return list;
    }

    public BeanSerializerBuilder updateBuilder(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription, BeanSerializerBuilder beanSerializerBuilder) {
        return beanSerializerBuilder;
    }
}


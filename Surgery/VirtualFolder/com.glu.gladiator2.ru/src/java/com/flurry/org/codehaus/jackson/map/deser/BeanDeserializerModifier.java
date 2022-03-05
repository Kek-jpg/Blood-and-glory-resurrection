/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.deser.BeanDeserializerBuilder
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializerBuilder;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;

public abstract class BeanDeserializerModifier {
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, JsonDeserializer<?> jsonDeserializer) {
        return jsonDeserializer;
    }

    public BeanDeserializerBuilder updateBuilder(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, BeanDeserializerBuilder beanDeserializerBuilder) {
        return beanDeserializerBuilder;
    }
}


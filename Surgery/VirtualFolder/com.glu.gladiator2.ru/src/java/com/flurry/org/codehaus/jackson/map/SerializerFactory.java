/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Deprecated
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.Throwable
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.RuntimeJsonMappingException;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.Serializers;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.ser.BeanSerializerModifier;
import com.flurry.org.codehaus.jackson.type.JavaType;

public abstract class SerializerFactory {
    public abstract JsonSerializer<Object> createKeySerializer(SerializationConfig var1, JavaType var2, BeanProperty var3) throws JsonMappingException;

    public abstract JsonSerializer<Object> createSerializer(SerializationConfig var1, JavaType var2, BeanProperty var3) throws JsonMappingException;

    @Deprecated
    public final JsonSerializer<Object> createSerializer(JavaType javaType, SerializationConfig serializationConfig) {
        try {
            JsonSerializer<Object> jsonSerializer = this.createSerializer(serializationConfig, javaType, null);
            return jsonSerializer;
        }
        catch (JsonMappingException jsonMappingException) {
            throw new RuntimeJsonMappingException(jsonMappingException);
        }
    }

    public abstract TypeSerializer createTypeSerializer(SerializationConfig var1, JavaType var2, BeanProperty var3) throws JsonMappingException;

    @Deprecated
    public final TypeSerializer createTypeSerializer(JavaType javaType, SerializationConfig serializationConfig) {
        try {
            TypeSerializer typeSerializer = this.createTypeSerializer(serializationConfig, javaType, null);
            return typeSerializer;
        }
        catch (JsonMappingException jsonMappingException) {
            throw new RuntimeException((Throwable)jsonMappingException);
        }
    }

    public abstract Config getConfig();

    public final SerializerFactory withAdditionalKeySerializers(Serializers serializers) {
        return this.withConfig(this.getConfig().withAdditionalKeySerializers(serializers));
    }

    public final SerializerFactory withAdditionalSerializers(Serializers serializers) {
        return this.withConfig(this.getConfig().withAdditionalSerializers(serializers));
    }

    public abstract SerializerFactory withConfig(Config var1);

    public final SerializerFactory withSerializerModifier(BeanSerializerModifier beanSerializerModifier) {
        return this.withConfig(this.getConfig().withSerializerModifier(beanSerializerModifier));
    }

    public static abstract class Config {
        public abstract boolean hasKeySerializers();

        public abstract boolean hasSerializerModifiers();

        public abstract boolean hasSerializers();

        public abstract Iterable<Serializers> keySerializers();

        public abstract Iterable<BeanSerializerModifier> serializerModifiers();

        public abstract Iterable<Serializers> serializers();

        public abstract Config withAdditionalKeySerializers(Serializers var1);

        public abstract Config withAdditionalSerializers(Serializers var1);

        public abstract Config withSerializerModifier(BeanSerializerModifier var1);
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.module;

import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.Serializers;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import com.flurry.org.codehaus.jackson.map.type.CollectionLikeType;
import com.flurry.org.codehaus.jackson.map.type.CollectionType;
import com.flurry.org.codehaus.jackson.map.type.MapLikeType;
import com.flurry.org.codehaus.jackson.map.type.MapType;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.HashMap;

public class SimpleSerializers
extends Serializers.Base {
    protected HashMap<ClassKey, JsonSerializer<?>> _classMappings = null;
    protected HashMap<ClassKey, JsonSerializer<?>> _interfaceMappings = null;

    private void _addSerializer(Class<?> class_, JsonSerializer<?> jsonSerializer) {
        ClassKey classKey = new ClassKey(class_);
        if (class_.isInterface()) {
            if (this._interfaceMappings == null) {
                this._interfaceMappings = new HashMap();
            }
            this._interfaceMappings.put((Object)classKey, jsonSerializer);
            return;
        }
        if (this._classMappings == null) {
            this._classMappings = new HashMap();
        }
        this._classMappings.put((Object)classKey, jsonSerializer);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonSerializer<?> _findInterfaceMapping(Class<?> class_, ClassKey classKey) {
        Class[] arrclass = class_.getInterfaces();
        int n = arrclass.length;
        int n2 = 0;
        while (n2 < n) {
            Class class_2 = arrclass[n2];
            classKey.reset(class_2);
            JsonSerializer<?> jsonSerializer = (JsonSerializer<?>)this._interfaceMappings.get((Object)classKey);
            if (jsonSerializer != null || (jsonSerializer = this._findInterfaceMapping(class_2, classKey)) != null) {
                return jsonSerializer;
            }
            ++n2;
        }
        return null;
    }

    public void addSerializer(JsonSerializer<?> jsonSerializer) {
        Class<?> class_ = jsonSerializer.handledType();
        if (class_ == null || class_ == Object.class) {
            throw new IllegalArgumentException("JsonSerializer of type " + jsonSerializer.getClass().getName() + " does not define valid handledType() -- must either register with method that takes type argument " + " or make serializer extend 'org.codehaus.jackson.map.ser.std.SerializerBase'");
        }
        SimpleSerializers.super._addSerializer(class_, jsonSerializer);
    }

    public <T> void addSerializer(Class<? extends T> class_, JsonSerializer<T> jsonSerializer) {
        SimpleSerializers.super._addSerializer(class_, jsonSerializer);
    }

    @Override
    public JsonSerializer<?> findArraySerializer(SerializationConfig serializationConfig, ArrayType arrayType, BeanDescription beanDescription, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        return this.findSerializer(serializationConfig, arrayType, beanDescription, beanProperty);
    }

    @Override
    public JsonSerializer<?> findCollectionLikeSerializer(SerializationConfig serializationConfig, CollectionLikeType collectionLikeType, BeanDescription beanDescription, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        return this.findSerializer(serializationConfig, collectionLikeType, beanDescription, beanProperty);
    }

    @Override
    public JsonSerializer<?> findCollectionSerializer(SerializationConfig serializationConfig, CollectionType collectionType, BeanDescription beanDescription, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        return this.findSerializer(serializationConfig, collectionType, beanDescription, beanProperty);
    }

    @Override
    public JsonSerializer<?> findMapLikeSerializer(SerializationConfig serializationConfig, MapLikeType mapLikeType, BeanDescription beanDescription, BeanProperty beanProperty, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer2) {
        return this.findSerializer(serializationConfig, mapLikeType, beanDescription, beanProperty);
    }

    @Override
    public JsonSerializer<?> findMapSerializer(SerializationConfig serializationConfig, MapType mapType, BeanDescription beanDescription, BeanProperty beanProperty, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer2) {
        return this.findSerializer(serializationConfig, mapType, beanDescription, beanProperty);
    }

    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription, BeanProperty beanProperty) {
        Class class_ = javaType.getRawClass();
        ClassKey classKey = new ClassKey(class_);
        if (class_.isInterface()) {
            JsonSerializer jsonSerializer;
            if (this._interfaceMappings != null && (jsonSerializer = (JsonSerializer)this._interfaceMappings.get((Object)classKey)) != null) {
                return jsonSerializer;
            }
        } else if (this._classMappings != null) {
            JsonSerializer jsonSerializer = (JsonSerializer)this._classMappings.get((Object)classKey);
            if (jsonSerializer != null) {
                return jsonSerializer;
            }
            for (Class class_2 = class_; class_2 != null; class_2 = class_2.getSuperclass()) {
                classKey.reset(class_2);
                JsonSerializer jsonSerializer2 = (JsonSerializer)this._classMappings.get((Object)classKey);
                if (jsonSerializer2 == null) continue;
                return jsonSerializer2;
            }
        }
        if (this._interfaceMappings != null) {
            JsonSerializer<?> jsonSerializer = this._findInterfaceMapping(class_, classKey);
            if (jsonSerializer != null) {
                return jsonSerializer;
            }
            if (!class_.isInterface()) {
                while ((class_ = class_.getSuperclass()) != null) {
                    JsonSerializer<?> jsonSerializer3 = this._findInterfaceMapping(class_, classKey);
                    if (jsonSerializer3 == null) continue;
                    return jsonSerializer3;
                }
            }
        }
        return null;
    }
}


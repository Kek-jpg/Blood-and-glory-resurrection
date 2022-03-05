/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.module;

import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.Deserializers;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import com.flurry.org.codehaus.jackson.map.type.CollectionLikeType;
import com.flurry.org.codehaus.jackson.map.type.CollectionType;
import com.flurry.org.codehaus.jackson.map.type.MapLikeType;
import com.flurry.org.codehaus.jackson.map.type.MapType;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.HashMap;

public class SimpleDeserializers
implements Deserializers {
    protected HashMap<ClassKey, JsonDeserializer<?>> _classMappings = null;

    public <T> void addDeserializer(Class<T> class_, JsonDeserializer<? extends T> jsonDeserializer) {
        ClassKey classKey = new ClassKey(class_);
        if (this._classMappings == null) {
            this._classMappings = new HashMap();
        }
        this._classMappings.put((Object)classKey, jsonDeserializer);
    }

    @Override
    public JsonDeserializer<?> findArrayDeserializer(ArrayType arrayType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanProperty beanProperty, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return (JsonDeserializer)this._classMappings.get((Object)new ClassKey(arrayType.getRawClass()));
    }

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanDescription beanDescription, BeanProperty beanProperty) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return (JsonDeserializer)this._classMappings.get((Object)new ClassKey(javaType.getRawClass()));
    }

    @Override
    public JsonDeserializer<?> findCollectionDeserializer(CollectionType collectionType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanDescription beanDescription, BeanProperty beanProperty, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return (JsonDeserializer)this._classMappings.get((Object)new ClassKey(collectionType.getRawClass()));
    }

    @Override
    public JsonDeserializer<?> findCollectionLikeDeserializer(CollectionLikeType collectionLikeType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanDescription beanDescription, BeanProperty beanProperty, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return (JsonDeserializer)this._classMappings.get((Object)new ClassKey(collectionLikeType.getRawClass()));
    }

    @Override
    public JsonDeserializer<?> findEnumDeserializer(Class<?> class_, DeserializationConfig deserializationConfig, BeanDescription beanDescription, BeanProperty beanProperty) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return (JsonDeserializer)this._classMappings.get((Object)new ClassKey(class_));
    }

    @Override
    public JsonDeserializer<?> findMapDeserializer(MapType mapType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanDescription beanDescription, BeanProperty beanProperty, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return (JsonDeserializer)this._classMappings.get((Object)new ClassKey(mapType.getRawClass()));
    }

    @Override
    public JsonDeserializer<?> findMapLikeDeserializer(MapLikeType mapLikeType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanDescription beanDescription, BeanProperty beanProperty, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return (JsonDeserializer)this._classMappings.get((Object)new ClassKey(mapLikeType.getRawClass()));
    }

    @Override
    public JsonDeserializer<?> findTreeNodeDeserializer(Class<? extends JsonNode> class_, DeserializationConfig deserializationConfig, BeanProperty beanProperty) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return (JsonDeserializer)this._classMappings.get((Object)new ClassKey(class_));
    }
}


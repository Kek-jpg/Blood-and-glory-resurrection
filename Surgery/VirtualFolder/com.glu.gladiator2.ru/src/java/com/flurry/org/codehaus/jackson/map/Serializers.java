/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.map.type.CollectionLikeType;
import com.flurry.org.codehaus.jackson.map.type.CollectionType;
import com.flurry.org.codehaus.jackson.map.type.MapLikeType;
import com.flurry.org.codehaus.jackson.map.type.MapType;
import com.flurry.org.codehaus.jackson.type.JavaType;

public interface Serializers {
    public JsonSerializer<?> findArraySerializer(SerializationConfig var1, ArrayType var2, BeanDescription var3, BeanProperty var4, TypeSerializer var5, JsonSerializer<Object> var6);

    public JsonSerializer<?> findCollectionLikeSerializer(SerializationConfig var1, CollectionLikeType var2, BeanDescription var3, BeanProperty var4, TypeSerializer var5, JsonSerializer<Object> var6);

    public JsonSerializer<?> findCollectionSerializer(SerializationConfig var1, CollectionType var2, BeanDescription var3, BeanProperty var4, TypeSerializer var5, JsonSerializer<Object> var6);

    public JsonSerializer<?> findMapLikeSerializer(SerializationConfig var1, MapLikeType var2, BeanDescription var3, BeanProperty var4, JsonSerializer<Object> var5, TypeSerializer var6, JsonSerializer<Object> var7);

    public JsonSerializer<?> findMapSerializer(SerializationConfig var1, MapType var2, BeanDescription var3, BeanProperty var4, JsonSerializer<Object> var5, TypeSerializer var6, JsonSerializer<Object> var7);

    public JsonSerializer<?> findSerializer(SerializationConfig var1, JavaType var2, BeanDescription var3, BeanProperty var4);
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.map.type.CollectionLikeType;
import com.flurry.org.codehaus.jackson.map.type.CollectionType;
import com.flurry.org.codehaus.jackson.map.type.MapLikeType;
import com.flurry.org.codehaus.jackson.map.type.MapType;
import com.flurry.org.codehaus.jackson.type.JavaType;

public interface Deserializers {
    public JsonDeserializer<?> findArrayDeserializer(ArrayType var1, DeserializationConfig var2, DeserializerProvider var3, BeanProperty var4, TypeDeserializer var5, JsonDeserializer<?> var6) throws JsonMappingException;

    public JsonDeserializer<?> findBeanDeserializer(JavaType var1, DeserializationConfig var2, DeserializerProvider var3, BeanDescription var4, BeanProperty var5) throws JsonMappingException;

    public JsonDeserializer<?> findCollectionDeserializer(CollectionType var1, DeserializationConfig var2, DeserializerProvider var3, BeanDescription var4, BeanProperty var5, TypeDeserializer var6, JsonDeserializer<?> var7) throws JsonMappingException;

    public JsonDeserializer<?> findCollectionLikeDeserializer(CollectionLikeType var1, DeserializationConfig var2, DeserializerProvider var3, BeanDescription var4, BeanProperty var5, TypeDeserializer var6, JsonDeserializer<?> var7) throws JsonMappingException;

    public JsonDeserializer<?> findEnumDeserializer(Class<?> var1, DeserializationConfig var2, BeanDescription var3, BeanProperty var4) throws JsonMappingException;

    public JsonDeserializer<?> findMapDeserializer(MapType var1, DeserializationConfig var2, DeserializerProvider var3, BeanDescription var4, BeanProperty var5, KeyDeserializer var6, TypeDeserializer var7, JsonDeserializer<?> var8) throws JsonMappingException;

    public JsonDeserializer<?> findMapLikeDeserializer(MapLikeType var1, DeserializationConfig var2, DeserializerProvider var3, BeanDescription var4, BeanProperty var5, KeyDeserializer var6, TypeDeserializer var7, JsonDeserializer<?> var8) throws JsonMappingException;

    public JsonDeserializer<?> findTreeNodeDeserializer(Class<? extends JsonNode> var1, DeserializationConfig var2, BeanProperty var3) throws JsonMappingException;

    public static class Base
    implements Deserializers {
        @Override
        public JsonDeserializer<?> findArrayDeserializer(ArrayType arrayType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanProperty beanProperty, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findBeanDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanDescription beanDescription, BeanProperty beanProperty) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findCollectionDeserializer(CollectionType collectionType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanDescription beanDescription, BeanProperty beanProperty, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findCollectionLikeDeserializer(CollectionLikeType collectionLikeType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanDescription beanDescription, BeanProperty beanProperty, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findEnumDeserializer(Class<?> class_, DeserializationConfig deserializationConfig, BeanDescription beanDescription, BeanProperty beanProperty) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findMapDeserializer(MapType mapType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanDescription beanDescription, BeanProperty beanProperty, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findMapLikeDeserializer(MapLikeType mapLikeType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanDescription beanDescription, BeanProperty beanProperty, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
            return null;
        }

        @Override
        public JsonDeserializer<?> findTreeNodeDeserializer(Class<? extends JsonNode> class_, DeserializationConfig deserializationConfig, BeanProperty beanProperty) throws JsonMappingException {
            return null;
        }
    }

    @Deprecated
    public static class None
    extends Base {
    }

}


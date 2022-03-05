/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.reflect.Constructor
 *  java.util.Collection
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.Constructor;
import java.util.Collection;

@Deprecated
public class CollectionDeserializer
extends com.flurry.org.codehaus.jackson.map.deser.std.CollectionDeserializer {
    protected CollectionDeserializer(CollectionDeserializer collectionDeserializer) {
        super(collectionDeserializer);
    }

    public CollectionDeserializer(JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, ValueInstantiator valueInstantiator) {
        super(javaType, jsonDeserializer, typeDeserializer, valueInstantiator);
    }

    @Deprecated
    public CollectionDeserializer(JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, Constructor<Collection<Object>> constructor) {
        super(javaType, jsonDeserializer, typeDeserializer, constructor);
    }
}


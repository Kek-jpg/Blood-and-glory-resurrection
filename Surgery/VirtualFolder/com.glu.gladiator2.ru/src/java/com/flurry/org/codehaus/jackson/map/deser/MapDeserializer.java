/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.reflect.Constructor
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.Constructor;
import java.util.Map;

@Deprecated
public class MapDeserializer
extends com.flurry.org.codehaus.jackson.map.deser.std.MapDeserializer {
    protected MapDeserializer(MapDeserializer mapDeserializer) {
        super(mapDeserializer);
    }

    public MapDeserializer(JavaType javaType, ValueInstantiator valueInstantiator, KeyDeserializer keyDeserializer, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer) {
        super(javaType, valueInstantiator, keyDeserializer, jsonDeserializer, typeDeserializer);
    }

    @Deprecated
    public MapDeserializer(JavaType javaType, Constructor<Map<Object, Object>> constructor, KeyDeserializer keyDeserializer, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer) {
        super(javaType, constructor, keyDeserializer, jsonDeserializer, typeDeserializer);
    }
}


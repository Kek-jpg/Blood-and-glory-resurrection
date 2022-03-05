/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.util.HashMap
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.ser.impl;

import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.ser.impl.JsonSerializerMap;
import com.flurry.org.codehaus.jackson.map.ser.impl.SerializerCache;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.HashMap;
import java.util.Map;

public final class ReadOnlyClassToSerializerMap {
    protected final SerializerCache.TypeKey _cacheKey;
    protected final JsonSerializerMap _map;

    private ReadOnlyClassToSerializerMap(JsonSerializerMap jsonSerializerMap) {
        this._cacheKey = new SerializerCache.TypeKey(this.getClass(), false);
        this._map = jsonSerializerMap;
    }

    public static ReadOnlyClassToSerializerMap from(HashMap<SerializerCache.TypeKey, JsonSerializer<Object>> hashMap) {
        return new ReadOnlyClassToSerializerMap(new JsonSerializerMap((Map<SerializerCache.TypeKey, JsonSerializer<Object>>)hashMap));
    }

    public ReadOnlyClassToSerializerMap instance() {
        return new ReadOnlyClassToSerializerMap(this._map);
    }

    public JsonSerializer<Object> typedValueSerializer(JavaType javaType) {
        this._cacheKey.resetTyped(javaType);
        return this._map.find(this._cacheKey);
    }

    public JsonSerializer<Object> typedValueSerializer(Class<?> class_) {
        this._cacheKey.resetTyped(class_);
        return this._map.find(this._cacheKey);
    }

    public JsonSerializer<Object> untypedValueSerializer(JavaType javaType) {
        this._cacheKey.resetUntyped(javaType);
        return this._map.find(this._cacheKey);
    }

    public JsonSerializer<Object> untypedValueSerializer(Class<?> class_) {
        this._cacheKey.resetUntyped(class_);
        return this._map.find(this._cacheKey);
    }
}


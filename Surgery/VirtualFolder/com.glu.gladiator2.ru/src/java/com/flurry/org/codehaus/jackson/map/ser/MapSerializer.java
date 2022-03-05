/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashSet
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.HashSet;

@JacksonStdImpl
@Deprecated
public class MapSerializer
extends com.flurry.org.codehaus.jackson.map.ser.std.MapSerializer {
    protected MapSerializer() {
        this(null, null, null, false, null, null, null, null);
    }

    @Deprecated
    protected MapSerializer(HashSet<String> hashSet, JavaType javaType, JavaType javaType2, boolean bl, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer, BeanProperty beanProperty) {
        super(hashSet, javaType, javaType2, bl, typeSerializer, jsonSerializer, null, beanProperty);
    }

    protected MapSerializer(HashSet<String> hashSet, JavaType javaType, JavaType javaType2, boolean bl, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer, JsonSerializer<Object> jsonSerializer2, BeanProperty beanProperty) {
        super(hashSet, javaType, javaType2, bl, typeSerializer, jsonSerializer, jsonSerializer2, beanProperty);
    }

    @Deprecated
    protected MapSerializer(HashSet<String> hashSet, JavaType javaType, boolean bl, TypeSerializer typeSerializer) {
        super(hashSet, UNSPECIFIED_TYPE, javaType, bl, typeSerializer, null, null, null);
    }
}


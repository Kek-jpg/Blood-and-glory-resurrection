/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.type.JavaType;

public abstract class AbstractTypeResolver {
    public JavaType findTypeMapping(DeserializationConfig deserializationConfig, JavaType javaType) {
        return null;
    }

    public JavaType resolveAbstractType(DeserializationConfig deserializationConfig, JavaType javaType) {
        return null;
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Comparable
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.ParameterizedType
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference<T>
implements Comparable<TypeReference<T>> {
    final Type _type;

    protected TypeReference() {
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof Class) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        }
        this._type = ((ParameterizedType)type).getActualTypeArguments()[0];
    }

    public int compareTo(TypeReference<T> typeReference) {
        return 0;
    }

    public Type getType() {
        return this._type;
    }
}


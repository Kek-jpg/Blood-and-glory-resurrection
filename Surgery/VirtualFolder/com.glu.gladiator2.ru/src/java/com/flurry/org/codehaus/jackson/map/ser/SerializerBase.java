/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.type.JavaType;

@Deprecated
public abstract class SerializerBase<T>
extends com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase<T> {
    protected SerializerBase(JavaType javaType) {
        super(javaType);
    }

    protected SerializerBase(Class<T> class_) {
        super(class_);
    }

    protected SerializerBase(Class<?> class_, boolean bl) {
        super(class_, bl);
    }
}


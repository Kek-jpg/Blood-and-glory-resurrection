/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;

public abstract class ContainerSerializerBase<T>
extends SerializerBase<T> {
    protected ContainerSerializerBase(Class<T> class_) {
        super(class_);
    }

    protected ContainerSerializerBase(Class<?> class_, boolean bl) {
        super(class_, bl);
    }

    public abstract ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer var1);

    public ContainerSerializerBase<?> withValueTypeSerializer(TypeSerializer typeSerializer) {
        if (typeSerializer == null) {
            return this;
        }
        return this._withValueTypeSerializer(typeSerializer);
    }
}


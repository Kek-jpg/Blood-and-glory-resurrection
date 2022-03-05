/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;

@Deprecated
public abstract class ScalarSerializerBase<T>
extends SerializerBase<T> {
    protected ScalarSerializerBase(Class<T> class_) {
        super(class_);
    }

    protected ScalarSerializerBase(Class<?> class_, boolean bl) {
        super(class_);
    }
}


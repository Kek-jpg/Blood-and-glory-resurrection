/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.StdDeserializer;
import com.flurry.org.codehaus.jackson.type.JavaType;

public abstract class ContainerDeserializerBase<T>
extends StdDeserializer<T> {
    protected ContainerDeserializerBase(Class<?> class_) {
        super(class_);
    }

    public abstract JsonDeserializer<Object> getContentDeserializer();

    public abstract JavaType getContentType();
}


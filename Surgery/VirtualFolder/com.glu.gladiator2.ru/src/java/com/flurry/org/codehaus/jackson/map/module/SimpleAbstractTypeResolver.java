/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Modifier
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.module;

import com.flurry.org.codehaus.jackson.map.AbstractTypeResolver;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class SimpleAbstractTypeResolver
extends AbstractTypeResolver {
    protected final HashMap<ClassKey, Class<?>> _mappings = new HashMap();

    public <T> SimpleAbstractTypeResolver addMapping(Class<T> class_, Class<? extends T> class_2) {
        if (class_ == class_2) {
            throw new IllegalArgumentException("Can not add mapping from class to itself");
        }
        if (!class_.isAssignableFrom(class_2)) {
            throw new IllegalArgumentException("Can not add mapping from class " + class_.getName() + " to " + class_2.getName() + ", as latter is not a subtype of former");
        }
        if (!Modifier.isAbstract((int)class_.getModifiers())) {
            throw new IllegalArgumentException("Can not add mapping from class " + class_.getName() + " since it is not abstract");
        }
        this._mappings.put((Object)new ClassKey(class_), class_2);
        return this;
    }

    @Override
    public JavaType findTypeMapping(DeserializationConfig deserializationConfig, JavaType javaType) {
        Class<?> class_ = javaType.getRawClass();
        Class class_2 = (Class)this._mappings.get((Object)new ClassKey(class_));
        if (class_2 == null) {
            return null;
        }
        return javaType.narrowBy(class_2);
    }

    @Override
    public JavaType resolveAbstractType(DeserializationConfig deserializationConfig, JavaType javaType) {
        return null;
    }
}


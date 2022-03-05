/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.module;

import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiators;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import java.util.HashMap;

public class SimpleValueInstantiators
extends ValueInstantiators.Base {
    protected HashMap<ClassKey, ValueInstantiator> _classMappings = new HashMap();

    public SimpleValueInstantiators addValueInstantiator(Class<?> class_, ValueInstantiator valueInstantiator) {
        this._classMappings.put((Object)new ClassKey(class_), (Object)valueInstantiator);
        return this;
    }

    @Override
    public ValueInstantiator findValueInstantiator(DeserializationConfig deserializationConfig, BeanDescription beanDescription, ValueInstantiator valueInstantiator) {
        ValueInstantiator valueInstantiator2 = (ValueInstantiator)this._classMappings.get((Object)new ClassKey(beanDescription.getBeanClass()));
        if (valueInstantiator2 == null) {
            return valueInstantiator;
        }
        return valueInstantiator2;
    }
}


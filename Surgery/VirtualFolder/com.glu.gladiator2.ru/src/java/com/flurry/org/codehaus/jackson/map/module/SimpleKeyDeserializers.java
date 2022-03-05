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
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializers;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.HashMap;

public class SimpleKeyDeserializers
implements KeyDeserializers {
    protected HashMap<ClassKey, KeyDeserializer> _classMappings = null;

    public SimpleKeyDeserializers addDeserializer(Class<?> class_, KeyDeserializer keyDeserializer) {
        if (this._classMappings == null) {
            this._classMappings = new HashMap();
        }
        this._classMappings.put((Object)new ClassKey(class_), (Object)keyDeserializer);
        return this;
    }

    @Override
    public KeyDeserializer findKeyDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, BeanDescription beanDescription, BeanProperty beanProperty) {
        if (this._classMappings == null) {
            return null;
        }
        return (KeyDeserializer)this._classMappings.get((Object)new ClassKey(javaType.getRawClass()));
    }
}


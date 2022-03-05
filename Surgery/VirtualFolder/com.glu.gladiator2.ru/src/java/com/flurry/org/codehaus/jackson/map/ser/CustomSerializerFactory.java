/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Modifier
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerFactory;
import com.flurry.org.codehaus.jackson.map.ser.BeanSerializerFactory;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class CustomSerializerFactory
extends BeanSerializerFactory {
    protected HashMap<ClassKey, JsonSerializer<?>> _directClassMappings = null;
    protected JsonSerializer<?> _enumSerializerOverride;
    protected HashMap<ClassKey, JsonSerializer<?>> _interfaceMappings = null;
    protected HashMap<ClassKey, JsonSerializer<?>> _transitiveClassMappings = null;

    public CustomSerializerFactory() {
        this(null);
    }

    public CustomSerializerFactory(SerializerFactory.Config config) {
        super(config);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonSerializer<?> _findInterfaceMapping(Class<?> class_, ClassKey classKey) {
        Class[] arrclass = class_.getInterfaces();
        int n = arrclass.length;
        int n2 = 0;
        while (n2 < n) {
            Class class_2 = arrclass[n2];
            classKey.reset(class_2);
            JsonSerializer<?> jsonSerializer = (JsonSerializer<?>)this._interfaceMappings.get((Object)classKey);
            if (jsonSerializer != null || (jsonSerializer = this._findInterfaceMapping(class_2, classKey)) != null) {
                return jsonSerializer;
            }
            ++n2;
        }
        return null;
    }

    public <T> void addGenericMapping(Class<? extends T> class_, JsonSerializer<T> jsonSerializer) {
        ClassKey classKey = new ClassKey(class_);
        if (class_.isInterface()) {
            if (this._interfaceMappings == null) {
                this._interfaceMappings = new HashMap();
            }
            this._interfaceMappings.put((Object)classKey, jsonSerializer);
            return;
        }
        if (this._transitiveClassMappings == null) {
            this._transitiveClassMappings = new HashMap();
        }
        this._transitiveClassMappings.put((Object)classKey, jsonSerializer);
    }

    public <T> void addSpecificMapping(Class<? extends T> class_, JsonSerializer<T> jsonSerializer) {
        ClassKey classKey = new ClassKey(class_);
        if (class_.isInterface()) {
            throw new IllegalArgumentException("Can not add specific mapping for an interface (" + class_.getName() + ")");
        }
        if (Modifier.isAbstract((int)class_.getModifiers())) {
            throw new IllegalArgumentException("Can not add specific mapping for an abstract class (" + class_.getName() + ")");
        }
        if (this._directClassMappings == null) {
            this._directClassMappings = new HashMap();
        }
        this._directClassMappings.put((Object)classKey, jsonSerializer);
    }

    @Override
    public JsonSerializer<Object> createSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer = this.findCustomSerializer(javaType.getRawClass(), serializationConfig);
        if (jsonSerializer != null) {
            return jsonSerializer;
        }
        return super.createSerializer(serializationConfig, javaType, beanProperty);
    }

    protected JsonSerializer<?> findCustomSerializer(Class<?> class_, SerializationConfig serializationConfig) {
        JsonSerializer jsonSerializer;
        ClassKey classKey = new ClassKey(class_);
        if (this._directClassMappings != null && (jsonSerializer = (JsonSerializer)this._directClassMappings.get((Object)classKey)) != null) {
            return jsonSerializer;
        }
        if (class_.isEnum() && this._enumSerializerOverride != null) {
            return this._enumSerializerOverride;
        }
        if (this._transitiveClassMappings != null) {
            for (Class class_2 = class_; class_2 != null; class_2 = class_2.getSuperclass()) {
                classKey.reset(class_2);
                JsonSerializer jsonSerializer2 = (JsonSerializer)this._transitiveClassMappings.get((Object)classKey);
                if (jsonSerializer2 == null) continue;
                return jsonSerializer2;
            }
        }
        if (this._interfaceMappings != null) {
            classKey.reset(class_);
            JsonSerializer jsonSerializer3 = (JsonSerializer)this._interfaceMappings.get((Object)classKey);
            if (jsonSerializer3 != null) {
                return jsonSerializer3;
            }
            for (Class class_3 = class_; class_3 != null; class_3 = class_3.getSuperclass()) {
                JsonSerializer<?> jsonSerializer4 = this._findInterfaceMapping(class_3, classKey);
                if (jsonSerializer4 == null) continue;
                return jsonSerializer4;
            }
        }
        return null;
    }

    public void setEnumSerializer(JsonSerializer<?> jsonSerializer) {
        this._enumSerializerOverride = jsonSerializer;
    }

    @Override
    public SerializerFactory withConfig(SerializerFactory.Config config) {
        if (this.getClass() != CustomSerializerFactory.class) {
            throw new IllegalStateException("Subtype of CustomSerializerFactory (" + this.getClass().getName() + ") has not properly overridden method 'withAdditionalSerializers': can not instantiate subtype with " + "additional serializer definitions");
        }
        return new CustomSerializerFactory(config);
    }
}


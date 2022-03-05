/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializerFactory;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializerFactory;
import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.HashMap;

@Deprecated
public class CustomDeserializerFactory
extends BeanDeserializerFactory {
    protected HashMap<ClassKey, JsonDeserializer<Object>> _directClassMappings = null;
    protected HashMap<ClassKey, Class<?>> _mixInAnnotations;

    public CustomDeserializerFactory() {
        this(null);
    }

    protected CustomDeserializerFactory(DeserializerFactory.Config config) {
        super(config);
    }

    public void addMixInAnnotationMapping(Class<?> class_, Class<?> class_2) {
        if (this._mixInAnnotations == null) {
            this._mixInAnnotations = new HashMap();
        }
        this._mixInAnnotations.put((Object)new ClassKey(class_), class_2);
    }

    public <T> void addSpecificMapping(Class<T> class_, JsonDeserializer<? extends T> jsonDeserializer) {
        ClassKey classKey = new ClassKey(class_);
        if (this._directClassMappings == null) {
            this._directClassMappings = new HashMap();
        }
        this._directClassMappings.put((Object)classKey, jsonDeserializer);
    }

    @Override
    public JsonDeserializer<?> createArrayDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, ArrayType arrayType, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer jsonDeserializer;
        ClassKey classKey = new ClassKey(arrayType.getRawClass());
        if (this._directClassMappings != null && (jsonDeserializer = (JsonDeserializer)this._directClassMappings.get((Object)classKey)) != null) {
            return jsonDeserializer;
        }
        return super.createArrayDeserializer(deserializationConfig, deserializerProvider, arrayType, beanProperty);
    }

    @Override
    public JsonDeserializer<Object> createBeanDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer jsonDeserializer;
        ClassKey classKey = new ClassKey(javaType.getRawClass());
        if (this._directClassMappings != null && (jsonDeserializer = (JsonDeserializer)this._directClassMappings.get((Object)classKey)) != null) {
            return jsonDeserializer;
        }
        return super.createBeanDeserializer(deserializationConfig, deserializerProvider, javaType, beanProperty);
    }

    @Override
    public JsonDeserializer<?> createEnumDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer jsonDeserializer;
        ClassKey classKey;
        if (this._directClassMappings != null && (jsonDeserializer = (JsonDeserializer)this._directClassMappings.get((Object)(classKey = new ClassKey(javaType.getRawClass())))) != null) {
            return jsonDeserializer;
        }
        return super.createEnumDeserializer(deserializationConfig, deserializerProvider, javaType, beanProperty);
    }

    @Override
    public DeserializerFactory withConfig(DeserializerFactory.Config config) {
        if (this.getClass() != CustomDeserializerFactory.class) {
            throw new IllegalStateException("Subtype of CustomDeserializerFactory (" + this.getClass().getName() + ") has not properly overridden method 'withAdditionalDeserializers': can not instantiate subtype with " + "additional deserializer definitions");
        }
        return new CustomDeserializerFactory(config);
    }
}


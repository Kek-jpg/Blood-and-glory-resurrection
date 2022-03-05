/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.jsontype.impl;

import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeIdResolver;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.util.HashMap;

public abstract class TypeDeserializerBase
extends TypeDeserializer {
    protected final JavaType _baseType;
    protected final JavaType _defaultImpl;
    protected JsonDeserializer<Object> _defaultImplDeserializer;
    protected final HashMap<String, JsonDeserializer<Object>> _deserializers;
    protected final TypeIdResolver _idResolver;
    protected final BeanProperty _property;

    @Deprecated
    protected TypeDeserializerBase(JavaType javaType, TypeIdResolver typeIdResolver, BeanProperty beanProperty) {
        super(javaType, typeIdResolver, beanProperty, null);
    }

    protected TypeDeserializerBase(JavaType javaType, TypeIdResolver typeIdResolver, BeanProperty beanProperty, Class<?> class_) {
        this._baseType = javaType;
        this._idResolver = typeIdResolver;
        this._property = beanProperty;
        this._deserializers = new HashMap();
        if (class_ == null) {
            this._defaultImpl = null;
            return;
        }
        this._defaultImpl = javaType.forcedNarrowBy(class_);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final JsonDeserializer<Object> _findDefaultImplDeserializer(DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JavaType javaType;
        if (this._defaultImpl == null) {
            return null;
        }
        JavaType javaType2 = javaType = this._defaultImpl;
        synchronized (javaType2) {
            if (this._defaultImplDeserializer != null) return this._defaultImplDeserializer;
            this._defaultImplDeserializer = deserializationContext.getDeserializerProvider().findValueDeserializer(deserializationContext.getConfig(), this._defaultImpl, this._property);
            return this._defaultImplDeserializer;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final JsonDeserializer<Object> _findDeserializer(DeserializationContext deserializationContext, String string) throws IOException, JsonProcessingException {
        HashMap<String, JsonDeserializer<Object>> hashMap;
        HashMap<String, JsonDeserializer<Object>> hashMap2 = hashMap = this._deserializers;
        synchronized (hashMap2) {
            JsonDeserializer<Object> jsonDeserializer = (JsonDeserializer<Object>)this._deserializers.get((Object)string);
            if (jsonDeserializer == null) {
                JavaType javaType = this._idResolver.typeFromId(string);
                if (javaType == null) {
                    if (this._defaultImpl == null) {
                        throw deserializationContext.unknownTypeException(this._baseType, string);
                    }
                    jsonDeserializer = this._findDefaultImplDeserializer(deserializationContext);
                } else {
                    if (this._baseType != null && this._baseType.getClass() == javaType.getClass()) {
                        javaType = this._baseType.narrowBy(javaType.getRawClass());
                    }
                    JsonDeserializer<Object> jsonDeserializer2 = deserializationContext.getDeserializerProvider().findValueDeserializer(deserializationContext.getConfig(), javaType, this._property);
                    jsonDeserializer = jsonDeserializer2;
                }
                this._deserializers.put((Object)string, jsonDeserializer);
            }
            return jsonDeserializer;
        }
    }

    public String baseTypeName() {
        return this._baseType.getRawClass().getName();
    }

    @Override
    public Class<?> getDefaultImpl() {
        if (this._defaultImpl == null) {
            return null;
        }
        return this._defaultImpl.getRawClass();
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public TypeIdResolver getTypeIdResolver() {
        return this._idResolver;
    }

    @Override
    public abstract JsonTypeInfo.As getTypeInclusion();

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[').append(this.getClass().getName());
        stringBuilder.append("; base-type:").append((Object)this._baseType);
        stringBuilder.append("; id-resolver: ").append((Object)this._idResolver);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}


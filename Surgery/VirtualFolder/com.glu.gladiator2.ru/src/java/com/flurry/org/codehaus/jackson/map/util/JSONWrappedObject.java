/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.map.util;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonSerializableWithType;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.Type;

public class JSONWrappedObject
implements JsonSerializableWithType {
    protected final String _prefix;
    protected final JavaType _serializationType;
    protected final String _suffix;
    protected final Object _value;

    public JSONWrappedObject(String string, String string2, Object object) {
        super(string, string2, object, (JavaType)null);
    }

    public JSONWrappedObject(String string, String string2, Object object, JavaType javaType) {
        this._prefix = string;
        this._suffix = string2;
        this._value = object;
        this._serializationType = javaType;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    public JSONWrappedObject(String string, String string2, Object object, Class<?> class_) {
        this._prefix = string;
        this._suffix = string2;
        this._value = object;
        JavaType javaType = class_ == null ? null : TypeFactory.defaultInstance().constructType((Type)class_);
        this._serializationType = javaType;
    }

    public String getPrefix() {
        return this._prefix;
    }

    public JavaType getSerializationType() {
        return this._serializationType;
    }

    public String getSuffix() {
        return this._suffix;
    }

    public Object getValue() {
        return this._value;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (this._prefix != null) {
            jsonGenerator.writeRaw(this._prefix);
        }
        if (this._value == null) {
            serializerProvider.defaultSerializeNull(jsonGenerator);
        } else if (this._serializationType != null) {
            serializerProvider.findTypedValueSerializer(this._serializationType, true, null).serialize(this._value, jsonGenerator, serializerProvider);
        } else {
            serializerProvider.findTypedValueSerializer(this._value.getClass(), true, null).serialize(this._value, jsonGenerator, serializerProvider);
        }
        if (this._suffix != null) {
            jsonGenerator.writeRaw(this._suffix);
        }
    }

    @Override
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        this.serialize(jsonGenerator, serializerProvider);
    }
}


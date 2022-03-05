/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.lang.reflect.Method
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.Method;

public final class SettableAnyProperty {
    protected final BeanProperty _property;
    protected final Method _setter;
    protected final JavaType _type;
    protected JsonDeserializer<Object> _valueDeserializer;

    @Deprecated
    public SettableAnyProperty(BeanProperty beanProperty, AnnotatedMethod annotatedMethod, JavaType javaType) {
        super(beanProperty, annotatedMethod, javaType, null);
    }

    public SettableAnyProperty(BeanProperty beanProperty, AnnotatedMethod annotatedMethod, JavaType javaType, JsonDeserializer<Object> jsonDeserializer) {
        super(beanProperty, annotatedMethod.getAnnotated(), javaType, jsonDeserializer);
    }

    public SettableAnyProperty(BeanProperty beanProperty, Method method, JavaType javaType, JsonDeserializer<Object> jsonDeserializer) {
        this._property = beanProperty;
        this._type = javaType;
        this._setter = method;
        this._valueDeserializer = jsonDeserializer;
    }

    private String getClassName() {
        return this._setter.getDeclaringClass().getName();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _throwAsIOE(Exception exception, String string, Object object) throws IOException {
        if (exception instanceof IllegalArgumentException) {
            String string2 = object == null ? "[NULL]" : object.getClass().getName();
            StringBuilder stringBuilder = new StringBuilder("Problem deserializing \"any\" property '").append(string);
            stringBuilder.append("' of class " + SettableAnyProperty.super.getClassName() + " (expected type: ").append((Object)this._type);
            stringBuilder.append("; actual type: ").append(string2).append(")");
            String string3 = exception.getMessage();
            if (string3 != null) {
                stringBuilder.append(", problem: ").append(string3);
                throw new JsonMappingException(stringBuilder.toString(), null, exception);
            }
            stringBuilder.append(" (no error message provided)");
            throw new JsonMappingException(stringBuilder.toString(), null, exception);
        }
        if (exception instanceof IOException) {
            throw (IOException)((Object)exception);
        }
        if (exception instanceof RuntimeException) {
            throw (RuntimeException)exception;
        }
        Exception exception2 = exception;
        while (exception2.getCause() != null) {
            exception2 = exception2.getCause();
        }
        throw new JsonMappingException(exception2.getMessage(), null, exception2);
    }

    public final Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        }
        return this._valueDeserializer.deserialize(jsonParser, deserializationContext);
    }

    public final void deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, String string) throws IOException, JsonProcessingException {
        this.set(object, string, this.deserialize(jsonParser, deserializationContext));
    }

    public BeanProperty getProperty() {
        return this._property;
    }

    public JavaType getType() {
        return this._type;
    }

    public boolean hasValueDeserializer() {
        return this._valueDeserializer != null;
    }

    public final void set(Object object, String string, Object object2) throws IOException {
        try {
            this._setter.invoke(object, new Object[]{string, object2});
            return;
        }
        catch (Exception exception) {
            this._throwAsIOE(exception, string, object2);
            return;
        }
    }

    @Deprecated
    public void setValueDeserializer(JsonDeserializer<Object> jsonDeserializer) {
        if (this._valueDeserializer != null) {
            throw new IllegalStateException("Already had assigned deserializer for SettableAnyProperty");
        }
        this._valueDeserializer = jsonDeserializer;
    }

    public String toString() {
        return "[any property on class " + this.getClassName() + "]";
    }

    public SettableAnyProperty withValueDeserializer(JsonDeserializer<Object> jsonDeserializer) {
        return new SettableAnyProperty(this._property, this._setter, this._type, jsonDeserializer);
    }
}


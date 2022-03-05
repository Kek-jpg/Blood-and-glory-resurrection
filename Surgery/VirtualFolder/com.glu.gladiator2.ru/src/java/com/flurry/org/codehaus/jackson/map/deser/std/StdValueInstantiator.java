/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.ExceptionInInitializerError
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.impl.CreatorProperty;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedWithParams;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;

public class StdValueInstantiator
extends ValueInstantiator {
    protected final boolean _cfgEmptyStringsAsObjects;
    protected CreatorProperty[] _constructorArguments;
    protected AnnotatedWithParams _defaultCreator;
    protected AnnotatedWithParams _delegateCreator;
    protected JavaType _delegateType;
    protected AnnotatedWithParams _fromBooleanCreator;
    protected AnnotatedWithParams _fromDoubleCreator;
    protected AnnotatedWithParams _fromIntCreator;
    protected AnnotatedWithParams _fromLongCreator;
    protected AnnotatedWithParams _fromStringCreator;
    protected final String _valueTypeDesc;
    protected AnnotatedWithParams _withArgsCreator;

    /*
     * Enabled aggressive block sorting
     */
    public StdValueInstantiator(DeserializationConfig deserializationConfig, JavaType javaType) {
        boolean bl = deserializationConfig == null ? false : deserializationConfig.isEnabled(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this._cfgEmptyStringsAsObjects = bl;
        String string = javaType == null ? "UNKNOWN TYPE" : javaType.toString();
        this._valueTypeDesc = string;
    }

    /*
     * Enabled aggressive block sorting
     */
    public StdValueInstantiator(DeserializationConfig deserializationConfig, Class<?> class_) {
        boolean bl = deserializationConfig == null ? false : deserializationConfig.isEnabled(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this._cfgEmptyStringsAsObjects = bl;
        String string = class_ == null ? "UNKNOWN TYPE" : class_.getName();
        this._valueTypeDesc = string;
    }

    protected StdValueInstantiator(StdValueInstantiator stdValueInstantiator) {
        this._cfgEmptyStringsAsObjects = stdValueInstantiator._cfgEmptyStringsAsObjects;
        this._valueTypeDesc = stdValueInstantiator._valueTypeDesc;
        this._defaultCreator = stdValueInstantiator._defaultCreator;
        this._constructorArguments = stdValueInstantiator._constructorArguments;
        this._withArgsCreator = stdValueInstantiator._withArgsCreator;
        this._delegateType = stdValueInstantiator._delegateType;
        this._delegateCreator = stdValueInstantiator._delegateCreator;
        this._fromStringCreator = stdValueInstantiator._fromStringCreator;
        this._fromIntCreator = stdValueInstantiator._fromIntCreator;
        this._fromLongCreator = stdValueInstantiator._fromLongCreator;
        this._fromDoubleCreator = stdValueInstantiator._fromDoubleCreator;
        this._fromBooleanCreator = stdValueInstantiator._fromBooleanCreator;
    }

    protected Object _createFromStringFallbacks(String string) throws IOException, JsonProcessingException {
        if (this._fromBooleanCreator != null) {
            String string2 = string.trim();
            if ("true".equals((Object)string2)) {
                return this.createFromBoolean(true);
            }
            if ("false".equals((Object)string2)) {
                return this.createFromBoolean(false);
            }
        }
        if (this._cfgEmptyStringsAsObjects && string.length() == 0) {
            return null;
        }
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from JSON String; no single-String constructor/factory method");
    }

    @Override
    public boolean canCreateFromBoolean() {
        return this._fromBooleanCreator != null;
    }

    @Override
    public boolean canCreateFromDouble() {
        return this._fromDoubleCreator != null;
    }

    @Override
    public boolean canCreateFromInt() {
        return this._fromIntCreator != null;
    }

    @Override
    public boolean canCreateFromLong() {
        return this._fromLongCreator != null;
    }

    @Override
    public boolean canCreateFromObjectWith() {
        return this._withArgsCreator != null;
    }

    @Override
    public boolean canCreateFromString() {
        return this._fromStringCreator != null;
    }

    @Override
    public boolean canCreateUsingDefault() {
        return this._defaultCreator != null;
    }

    public void configureFromBooleanCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromBooleanCreator = annotatedWithParams;
    }

    public void configureFromDoubleCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromDoubleCreator = annotatedWithParams;
    }

    public void configureFromIntCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromIntCreator = annotatedWithParams;
    }

    public void configureFromLongCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromLongCreator = annotatedWithParams;
    }

    public void configureFromObjectSettings(AnnotatedWithParams annotatedWithParams, AnnotatedWithParams annotatedWithParams2, JavaType javaType, AnnotatedWithParams annotatedWithParams3, CreatorProperty[] arrcreatorProperty) {
        this._defaultCreator = annotatedWithParams;
        this._delegateCreator = annotatedWithParams2;
        this._delegateType = javaType;
        this._withArgsCreator = annotatedWithParams3;
        this._constructorArguments = arrcreatorProperty;
    }

    public void configureFromStringCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromStringCreator = annotatedWithParams;
    }

    @Override
    public Object createFromBoolean(boolean bl) throws IOException, JsonProcessingException {
        try {
            if (this._fromBooleanCreator != null) {
                Object object = this._fromBooleanCreator.call1(bl);
                return object;
            }
        }
        catch (Exception exception) {
            throw this.wrapException(exception);
        }
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from JSON boolean value; no single-boolean/Boolean-arg constructor/factory method");
    }

    @Override
    public Object createFromDouble(double d) throws IOException, JsonProcessingException {
        try {
            if (this._fromDoubleCreator != null) {
                Object object = this._fromDoubleCreator.call1(d);
                return object;
            }
        }
        catch (Exception exception) {
            throw this.wrapException(exception);
        }
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from JSON floating-point number; no one-double/Double-arg constructor/factory method");
    }

    @Override
    public Object createFromInt(int n) throws IOException, JsonProcessingException {
        try {
            if (this._fromIntCreator != null) {
                return this._fromIntCreator.call1(n);
            }
            if (this._fromLongCreator != null) {
                Object object = this._fromLongCreator.call1(n);
                return object;
            }
        }
        catch (Exception exception) {
            throw this.wrapException(exception);
        }
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from JSON integral number; no single-int-arg constructor/factory method");
    }

    @Override
    public Object createFromLong(long l) throws IOException, JsonProcessingException {
        try {
            if (this._fromLongCreator != null) {
                Object object = this._fromLongCreator.call1(l);
                return object;
            }
        }
        catch (Exception exception) {
            throw this.wrapException(exception);
        }
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from JSON long integral number; no single-long-arg constructor/factory method");
    }

    @Override
    public Object createFromObjectWith(Object[] arrobject) throws IOException, JsonProcessingException {
        if (this._withArgsCreator == null) {
            throw new IllegalStateException("No with-args constructor for " + this.getValueTypeDesc());
        }
        try {
            Object object = this._withArgsCreator.call(arrobject);
            return object;
        }
        catch (ExceptionInInitializerError exceptionInInitializerError) {
            throw this.wrapException(exceptionInInitializerError);
        }
        catch (Exception exception) {
            throw this.wrapException(exception);
        }
    }

    @Override
    public Object createFromString(String string) throws IOException, JsonProcessingException {
        if (this._fromStringCreator != null) {
            try {
                Object object = this._fromStringCreator.call1(string);
                return object;
            }
            catch (Exception exception) {
                throw this.wrapException(exception);
            }
        }
        return this._createFromStringFallbacks(string);
    }

    @Override
    public Object createUsingDefault() throws IOException, JsonProcessingException {
        if (this._defaultCreator == null) {
            throw new IllegalStateException("No default constructor for " + this.getValueTypeDesc());
        }
        try {
            Object object = this._defaultCreator.call();
            return object;
        }
        catch (ExceptionInInitializerError exceptionInInitializerError) {
            throw this.wrapException(exceptionInInitializerError);
        }
        catch (Exception exception) {
            throw this.wrapException(exception);
        }
    }

    @Override
    public Object createUsingDelegate(Object object) throws IOException, JsonProcessingException {
        if (this._delegateCreator == null) {
            throw new IllegalStateException("No delegate constructor for " + this.getValueTypeDesc());
        }
        try {
            Object object2 = this._delegateCreator.call1(object);
            return object2;
        }
        catch (ExceptionInInitializerError exceptionInInitializerError) {
            throw this.wrapException(exceptionInInitializerError);
        }
        catch (Exception exception) {
            throw this.wrapException(exception);
        }
    }

    @Override
    public AnnotatedWithParams getDefaultCreator() {
        return this._defaultCreator;
    }

    @Override
    public AnnotatedWithParams getDelegateCreator() {
        return this._delegateCreator;
    }

    @Override
    public JavaType getDelegateType() {
        return this._delegateType;
    }

    @Override
    public SettableBeanProperty[] getFromObjectArguments() {
        return this._constructorArguments;
    }

    @Override
    public String getValueTypeDesc() {
        return this._valueTypeDesc;
    }

    @Override
    public AnnotatedWithParams getWithArgsCreator() {
        return this._withArgsCreator;
    }

    protected JsonMappingException wrapException(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return new JsonMappingException("Instantiation of " + this.getValueTypeDesc() + " value failed: " + throwable.getMessage(), throwable);
    }
}


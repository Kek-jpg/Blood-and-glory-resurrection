/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedWithParams;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;

public abstract class ValueInstantiator {
    public boolean canCreateFromBoolean() {
        return false;
    }

    public boolean canCreateFromDouble() {
        return false;
    }

    public boolean canCreateFromInt() {
        return false;
    }

    public boolean canCreateFromLong() {
        return false;
    }

    public boolean canCreateFromObjectWith() {
        return false;
    }

    public boolean canCreateFromString() {
        return false;
    }

    public boolean canCreateUsingDefault() {
        return this.getDefaultCreator() != null;
    }

    public boolean canCreateUsingDelegate() {
        return this.getDelegateType() != null;
    }

    public boolean canInstantiate() {
        return this.canCreateUsingDefault() || this.canCreateUsingDelegate() || this.canCreateFromObjectWith() || this.canCreateFromString() || this.canCreateFromInt() || this.canCreateFromLong() || this.canCreateFromDouble() || this.canCreateFromBoolean();
    }

    public Object createFromBoolean(boolean bl) throws IOException, JsonProcessingException {
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from JSON boolean value");
    }

    public Object createFromDouble(double d) throws IOException, JsonProcessingException {
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from JSON floating-point number");
    }

    public Object createFromInt(int n) throws IOException, JsonProcessingException {
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from JSON int number");
    }

    public Object createFromLong(long l) throws IOException, JsonProcessingException {
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from JSON long number");
    }

    public Object createFromObjectWith(Object[] arrobject) throws IOException, JsonProcessingException {
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " with arguments");
    }

    public Object createFromString(String string) throws IOException, JsonProcessingException {
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from JSON String");
    }

    public Object createUsingDefault() throws IOException, JsonProcessingException {
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + "; no default creator found");
    }

    public Object createUsingDelegate(Object object) throws IOException, JsonProcessingException {
        throw new JsonMappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " using delegate");
    }

    public AnnotatedWithParams getDefaultCreator() {
        return null;
    }

    public AnnotatedWithParams getDelegateCreator() {
        return null;
    }

    public JavaType getDelegateType() {
        return null;
    }

    public SettableBeanProperty[] getFromObjectArguments() {
        return null;
    }

    public abstract String getValueTypeDesc();

    public AnnotatedWithParams getWithArgsCreator() {
        return null;
    }
}


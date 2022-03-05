/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.deser.impl;

import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.deser.SettableAnyProperty;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import java.io.IOException;

public abstract class PropertyValue {
    public final PropertyValue next;
    public final Object value;

    protected PropertyValue(PropertyValue propertyValue, Object object) {
        this.next = propertyValue;
        this.value = object;
    }

    public abstract void assign(Object var1) throws IOException, JsonProcessingException;

    static final class Any
    extends PropertyValue {
        final SettableAnyProperty _property;
        final String _propertyName;

        public Any(PropertyValue propertyValue, Object object, SettableAnyProperty settableAnyProperty, String string) {
            super(propertyValue, object);
            this._property = settableAnyProperty;
            this._propertyName = string;
        }

        @Override
        public void assign(Object object) throws IOException, JsonProcessingException {
            this._property.set(object, this._propertyName, this.value);
        }
    }

    static final class Map
    extends PropertyValue {
        final Object _key;

        public Map(PropertyValue propertyValue, Object object, Object object2) {
            super(propertyValue, object);
            this._key = object2;
        }

        @Override
        public void assign(Object object) throws IOException, JsonProcessingException {
            ((java.util.Map)object).put(this._key, this.value);
        }
    }

    static final class Regular
    extends PropertyValue {
        final SettableBeanProperty _property;

        public Regular(PropertyValue propertyValue, Object object, SettableBeanProperty settableBeanProperty) {
            super(propertyValue, object);
            this._property = settableBeanProperty;
        }

        @Override
        public void assign(Object object) throws IOException, JsonProcessingException {
            this._property.set(object, this.value);
        }
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.deser.impl;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.deser.SettableAnyProperty;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import com.flurry.org.codehaus.jackson.map.deser.impl.PropertyValue;

public final class PropertyValueBuffer {
    private PropertyValue _buffered;
    final DeserializationContext _context;
    final Object[] _creatorParameters;
    private int _paramsNeeded;
    final JsonParser _parser;

    public PropertyValueBuffer(JsonParser jsonParser, DeserializationContext deserializationContext, int n) {
        this._parser = jsonParser;
        this._context = deserializationContext;
        this._paramsNeeded = n;
        this._creatorParameters = new Object[n];
    }

    public boolean assignParameter(int n, Object object) {
        int n2;
        this._creatorParameters[n] = object;
        this._paramsNeeded = n2 = -1 + this._paramsNeeded;
        return n2 <= 0;
    }

    public void bufferAnyProperty(SettableAnyProperty settableAnyProperty, String string, Object object) {
        this._buffered = new PropertyValue.Any(this._buffered, object, settableAnyProperty, string);
    }

    public void bufferMapProperty(Object object, Object object2) {
        this._buffered = new PropertyValue.Map(this._buffered, object2, object);
    }

    public void bufferProperty(SettableBeanProperty settableBeanProperty, Object object) {
        this._buffered = new PropertyValue.Regular(this._buffered, object, settableBeanProperty);
    }

    protected PropertyValue buffered() {
        return this._buffered;
    }

    protected final Object[] getParameters(Object[] arrobject) {
        if (arrobject != null) {
            int n = this._creatorParameters.length;
            for (int i = 0; i < n; ++i) {
                Object object;
                if (this._creatorParameters[i] != null || (object = arrobject[i]) == null) continue;
                this._creatorParameters[i] = object;
            }
        }
        return this._creatorParameters;
    }

    public void inject(SettableBeanProperty[] arrsettableBeanProperty) {
        for (SettableBeanProperty settableBeanProperty : arrsettableBeanProperty) {
            if (settableBeanProperty == null) continue;
            this._creatorParameters[var2_2] = this._context.findInjectableValue(settableBeanProperty.getInjectableValueId(), settableBeanProperty, null);
        }
    }
}


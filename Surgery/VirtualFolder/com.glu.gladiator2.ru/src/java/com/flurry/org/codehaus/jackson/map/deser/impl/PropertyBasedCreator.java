/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.deser.impl;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.impl.PropertyValue;
import com.flurry.org.codehaus.jackson.map.deser.impl.PropertyValueBuffer;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public final class PropertyBasedCreator {
    protected Object[] _defaultValues;
    protected final HashMap<String, SettableBeanProperty> _properties;
    protected final SettableBeanProperty[] _propertiesWithInjectables;
    protected final ValueInstantiator _valueInstantiator;

    public PropertyBasedCreator(ValueInstantiator valueInstantiator) {
        this._valueInstantiator = valueInstantiator;
        this._properties = new HashMap();
        Object[] arrobject = null;
        SettableBeanProperty[] arrsettableBeanProperty = valueInstantiator.getFromObjectArguments();
        SettableBeanProperty[] arrsettableBeanProperty2 = null;
        int n = arrsettableBeanProperty.length;
        for (int i = 0; i < n; ++i) {
            SettableBeanProperty settableBeanProperty = arrsettableBeanProperty[i];
            this._properties.put((Object)settableBeanProperty.getName(), (Object)settableBeanProperty);
            if (settableBeanProperty.getType().isPrimitive()) {
                if (arrobject == null) {
                    arrobject = new Object[n];
                }
                arrobject[i] = ClassUtil.defaultValue(settableBeanProperty.getType().getRawClass());
            }
            if (settableBeanProperty.getInjectableValueId() == null) continue;
            if (arrsettableBeanProperty2 == null) {
                arrsettableBeanProperty2 = new SettableBeanProperty[n];
            }
            arrsettableBeanProperty2[i] = settableBeanProperty;
        }
        this._defaultValues = arrobject;
        this._propertiesWithInjectables = arrsettableBeanProperty2;
    }

    public void assignDeserializer(SettableBeanProperty settableBeanProperty, JsonDeserializer<Object> jsonDeserializer) {
        SettableBeanProperty settableBeanProperty2 = settableBeanProperty.withValueDeserializer(jsonDeserializer);
        this._properties.put((Object)settableBeanProperty2.getName(), (Object)settableBeanProperty2);
        Object object = jsonDeserializer.getNullValue();
        if (object != null) {
            if (this._defaultValues == null) {
                this._defaultValues = new Object[this._properties.size()];
            }
            this._defaultValues[settableBeanProperty2.getPropertyIndex()] = object;
        }
    }

    public Object build(PropertyValueBuffer propertyValueBuffer) throws IOException {
        Object object = this._valueInstantiator.createFromObjectWith(propertyValueBuffer.getParameters(this._defaultValues));
        PropertyValue propertyValue = propertyValueBuffer.buffered();
        while (propertyValue != null) {
            propertyValue.assign(object);
            propertyValue = propertyValue.next;
        }
        return object;
    }

    public SettableBeanProperty findCreatorProperty(String string) {
        return (SettableBeanProperty)this._properties.get((Object)string);
    }

    public Collection<SettableBeanProperty> getCreatorProperties() {
        return this._properties.values();
    }

    public PropertyValueBuffer startBuilding(JsonParser jsonParser, DeserializationContext deserializationContext) {
        PropertyValueBuffer propertyValueBuffer = new PropertyValueBuffer(jsonParser, deserializationContext, this._properties.size());
        if (this._propertiesWithInjectables != null) {
            propertyValueBuffer.inject(this._propertiesWithInjectables);
        }
        return propertyValueBuffer;
    }
}


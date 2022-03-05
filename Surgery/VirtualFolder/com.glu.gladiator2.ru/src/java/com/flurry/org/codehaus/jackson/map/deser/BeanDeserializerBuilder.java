/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.BeanPropertyDefinition;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.SettableAnyProperty;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.impl.BeanPropertyMap;
import com.flurry.org.codehaus.jackson.map.deser.impl.ValueInjector;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BeanDeserializerBuilder {
    protected SettableAnyProperty _anySetter;
    protected HashMap<String, SettableBeanProperty> _backRefProperties;
    protected final BasicBeanDescription _beanDesc;
    protected HashSet<String> _ignorableProps;
    protected boolean _ignoreAllUnknown;
    protected List<ValueInjector> _injectables;
    protected final HashMap<String, SettableBeanProperty> _properties = new LinkedHashMap();
    protected ValueInstantiator _valueInstantiator;

    protected BeanDeserializerBuilder(BeanDeserializerBuilder beanDeserializerBuilder) {
        this._beanDesc = beanDeserializerBuilder._beanDesc;
        this._anySetter = beanDeserializerBuilder._anySetter;
        this._ignoreAllUnknown = beanDeserializerBuilder._ignoreAllUnknown;
        this._properties.putAll(beanDeserializerBuilder._properties);
        this._backRefProperties = BeanDeserializerBuilder._copy(beanDeserializerBuilder._backRefProperties);
        this._ignorableProps = beanDeserializerBuilder._ignorableProps;
        this._valueInstantiator = beanDeserializerBuilder._valueInstantiator;
    }

    public BeanDeserializerBuilder(BasicBeanDescription basicBeanDescription) {
        this._beanDesc = basicBeanDescription;
    }

    private static HashMap<String, SettableBeanProperty> _copy(HashMap<String, SettableBeanProperty> hashMap) {
        if (hashMap == null) {
            return null;
        }
        return new HashMap(hashMap);
    }

    public void addBackReferenceProperty(String string, SettableBeanProperty settableBeanProperty) {
        if (this._backRefProperties == null) {
            this._backRefProperties = new HashMap(4);
        }
        this._backRefProperties.put((Object)string, (Object)settableBeanProperty);
        if (this._properties != null) {
            this._properties.remove((Object)settableBeanProperty.getName());
        }
    }

    public void addCreatorProperty(BeanPropertyDefinition beanPropertyDefinition) {
    }

    public void addIgnorable(String string) {
        if (this._ignorableProps == null) {
            this._ignorableProps = new HashSet();
        }
        this._ignorableProps.add((Object)string);
    }

    public void addInjectable(String string, JavaType javaType, Annotations annotations, AnnotatedMember annotatedMember, Object object) {
        if (this._injectables == null) {
            this._injectables = new ArrayList();
        }
        this._injectables.add((Object)new ValueInjector(string, javaType, annotations, annotatedMember, object));
    }

    public void addOrReplaceProperty(SettableBeanProperty settableBeanProperty, boolean bl) {
        this._properties.put((Object)settableBeanProperty.getName(), (Object)settableBeanProperty);
    }

    public void addProperty(SettableBeanProperty settableBeanProperty) {
        SettableBeanProperty settableBeanProperty2 = (SettableBeanProperty)this._properties.put((Object)settableBeanProperty.getName(), (Object)settableBeanProperty);
        if (settableBeanProperty2 != null && settableBeanProperty2 != settableBeanProperty) {
            throw new IllegalArgumentException("Duplicate property '" + settableBeanProperty.getName() + "' for " + this._beanDesc.getType());
        }
    }

    public JsonDeserializer<?> build(BeanProperty beanProperty) {
        BeanPropertyMap beanPropertyMap = new BeanPropertyMap((Collection<SettableBeanProperty>)this._properties.values());
        beanPropertyMap.assignIndexes();
        return new BeanDeserializer(this._beanDesc, beanProperty, this._valueInstantiator, beanPropertyMap, (Map<String, SettableBeanProperty>)this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, this._anySetter, this._injectables);
    }

    public Iterator<SettableBeanProperty> getProperties() {
        return this._properties.values().iterator();
    }

    public ValueInstantiator getValueInstantiator() {
        return this._valueInstantiator;
    }

    public boolean hasProperty(String string) {
        return this._properties.containsKey((Object)string);
    }

    public SettableBeanProperty removeProperty(String string) {
        return (SettableBeanProperty)this._properties.remove((Object)string);
    }

    public void setAnySetter(SettableAnyProperty settableAnyProperty) {
        if (this._anySetter != null && settableAnyProperty != null) {
            throw new IllegalStateException("_anySetter already set to non-null");
        }
        this._anySetter = settableAnyProperty;
    }

    public void setIgnoreUnknownProperties(boolean bl) {
        this._ignoreAllUnknown = bl;
    }

    public void setValueInstantiator(ValueInstantiator valueInstantiator) {
        this._valueInstantiator = valueInstantiator;
    }
}


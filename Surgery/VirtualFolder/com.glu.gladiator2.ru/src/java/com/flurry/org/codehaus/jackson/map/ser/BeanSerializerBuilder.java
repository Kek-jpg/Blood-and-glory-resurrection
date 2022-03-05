/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.ser.AnyGetterWriter;
import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyWriter;
import com.flurry.org.codehaus.jackson.map.ser.BeanSerializer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.List;

public class BeanSerializerBuilder {
    private static final BeanPropertyWriter[] NO_PROPERTIES = new BeanPropertyWriter[0];
    protected AnyGetterWriter _anyGetter;
    protected final BasicBeanDescription _beanDesc;
    protected Object _filterId;
    protected BeanPropertyWriter[] _filteredProperties;
    protected List<BeanPropertyWriter> _properties;

    public BeanSerializerBuilder(BasicBeanDescription basicBeanDescription) {
        this._beanDesc = basicBeanDescription;
    }

    protected BeanSerializerBuilder(BeanSerializerBuilder beanSerializerBuilder) {
        this._beanDesc = beanSerializerBuilder._beanDesc;
        this._properties = beanSerializerBuilder._properties;
        this._filteredProperties = beanSerializerBuilder._filteredProperties;
        this._anyGetter = beanSerializerBuilder._anyGetter;
        this._filterId = beanSerializerBuilder._filterId;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JsonSerializer<?> build() {
        BeanPropertyWriter[] arrbeanPropertyWriter;
        if (this._properties == null || this._properties.isEmpty()) {
            if (this._anyGetter == null) {
                return null;
            }
            arrbeanPropertyWriter = NO_PROPERTIES;
            do {
                return new BeanSerializer(this._beanDesc.getType(), arrbeanPropertyWriter, this._filteredProperties, this._anyGetter, this._filterId);
                break;
            } while (true);
        }
        arrbeanPropertyWriter = (BeanPropertyWriter[])this._properties.toArray((Object[])new BeanPropertyWriter[this._properties.size()]);
        return new BeanSerializer(this._beanDesc.getType(), arrbeanPropertyWriter, this._filteredProperties, this._anyGetter, this._filterId);
    }

    public BeanSerializer createDummy() {
        return BeanSerializer.createDummy(this._beanDesc.getBeanClass());
    }

    public BasicBeanDescription getBeanDescription() {
        return this._beanDesc;
    }

    public BeanPropertyWriter[] getFilteredProperties() {
        return this._filteredProperties;
    }

    public List<BeanPropertyWriter> getProperties() {
        return this._properties;
    }

    public boolean hasProperties() {
        return this._properties != null && this._properties.size() > 0;
    }

    public void setAnyGetter(AnyGetterWriter anyGetterWriter) {
        this._anyGetter = anyGetterWriter;
    }

    public void setFilterId(Object object) {
        this._filterId = object;
    }

    public void setFilteredProperties(BeanPropertyWriter[] arrbeanPropertyWriter) {
        this._filteredProperties = arrbeanPropertyWriter;
    }

    public void setProperties(List<BeanPropertyWriter> list) {
        this._properties = list;
    }
}


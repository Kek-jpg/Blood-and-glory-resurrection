/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.ser.impl;

import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyFilter;
import com.flurry.org.codehaus.jackson.map.ser.FilterProvider;
import java.util.HashMap;
import java.util.Map;

public class SimpleFilterProvider
extends FilterProvider {
    protected boolean _cfgFailOnUnknownId = true;
    protected BeanPropertyFilter _defaultFilter;
    protected final Map<String, BeanPropertyFilter> _filtersById;

    public SimpleFilterProvider() {
        this((Map<String, BeanPropertyFilter>)new HashMap());
    }

    public SimpleFilterProvider(Map<String, BeanPropertyFilter> map) {
        this._filtersById = map;
    }

    public SimpleFilterProvider addFilter(String string, BeanPropertyFilter beanPropertyFilter) {
        this._filtersById.put((Object)string, (Object)beanPropertyFilter);
        return this;
    }

    @Override
    public BeanPropertyFilter findFilter(Object object) {
        BeanPropertyFilter beanPropertyFilter = (BeanPropertyFilter)this._filtersById.get(object);
        if (beanPropertyFilter == null && (beanPropertyFilter = this._defaultFilter) == null && this._cfgFailOnUnknownId) {
            throw new IllegalArgumentException("No filter configured with id '" + object + "' (type " + object.getClass().getName() + ")");
        }
        return beanPropertyFilter;
    }

    public BeanPropertyFilter getDefaultFilter() {
        return this._defaultFilter;
    }

    public BeanPropertyFilter removeFilter(String string) {
        return (BeanPropertyFilter)this._filtersById.remove((Object)string);
    }

    public SimpleFilterProvider setDefaultFilter(BeanPropertyFilter beanPropertyFilter) {
        this._defaultFilter = beanPropertyFilter;
        return this;
    }

    public SimpleFilterProvider setFailOnUnknownId(boolean bl) {
        this._cfgFailOnUnknownId = bl;
        return this;
    }

    public boolean willFailOnUnknownId() {
        return this._cfgFailOnUnknownId;
    }
}


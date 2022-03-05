/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.HashSet
 *  java.util.Set
 */
package com.flurry.org.codehaus.jackson.map.ser.impl;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyFilter;
import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class SimpleBeanPropertyFilter
implements BeanPropertyFilter {
    protected SimpleBeanPropertyFilter() {
    }

    public static SimpleBeanPropertyFilter filterOutAllExcept(Set<String> set) {
        return new FilterExceptFilter(set);
    }

    public static /* varargs */ SimpleBeanPropertyFilter filterOutAllExcept(String ... arrstring) {
        HashSet hashSet = new HashSet(arrstring.length);
        Collections.addAll((Collection)hashSet, (Object[])arrstring);
        return new FilterExceptFilter((Set<String>)hashSet);
    }

    public static SimpleBeanPropertyFilter serializeAllExcept(Set<String> set) {
        return new SerializeExceptFilter(set);
    }

    public static /* varargs */ SimpleBeanPropertyFilter serializeAllExcept(String ... arrstring) {
        HashSet hashSet = new HashSet(arrstring.length);
        Collections.addAll((Collection)hashSet, (Object[])arrstring);
        return new SerializeExceptFilter((Set<String>)hashSet);
    }

    public static class FilterExceptFilter
    extends SimpleBeanPropertyFilter {
        protected final Set<String> _propertiesToInclude;

        public FilterExceptFilter(Set<String> set) {
            this._propertiesToInclude = set;
        }

        @Override
        public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, BeanPropertyWriter beanPropertyWriter) throws Exception {
            if (this._propertiesToInclude.contains((Object)beanPropertyWriter.getName())) {
                beanPropertyWriter.serializeAsField(object, jsonGenerator, serializerProvider);
            }
        }
    }

    public static class SerializeExceptFilter
    extends SimpleBeanPropertyFilter {
        protected final Set<String> _propertiesToExclude;

        public SerializeExceptFilter(Set<String> set) {
            this._propertiesToExclude = set;
        }

        @Override
        public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, BeanPropertyWriter beanPropertyWriter) throws Exception {
            if (!this._propertiesToExclude.contains((Object)beanPropertyWriter.getName())) {
                beanPropertyWriter.serializeAsField(object, jsonGenerator, serializerProvider);
            }
        }
    }

}


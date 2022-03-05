/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Type
 *  java.util.Collection
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Set
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.map.BeanPropertyDefinition;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.VisibilityChecker;
import com.flurry.org.codehaus.jackson.map.type.TypeBindings;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BeanDescription {
    protected final JavaType _type;

    protected BeanDescription(JavaType javaType) {
        this._type = javaType;
    }

    public abstract TypeBindings bindingsForBeanType();

    public abstract AnnotatedMethod findAnyGetter();

    public abstract AnnotatedMethod findAnySetter();

    public abstract AnnotatedConstructor findDefaultConstructor();

    @Deprecated
    public abstract LinkedHashMap<String, AnnotatedField> findDeserializableFields(VisibilityChecker<?> var1, Collection<String> var2);

    @Deprecated
    public abstract LinkedHashMap<String, AnnotatedMethod> findGetters(VisibilityChecker<?> var1, Collection<String> var2);

    public abstract Map<Object, AnnotatedMember> findInjectables();

    public abstract AnnotatedMethod findJsonValueMethod();

    public abstract List<BeanPropertyDefinition> findProperties();

    @Deprecated
    public abstract Map<String, AnnotatedField> findSerializableFields(VisibilityChecker<?> var1, Collection<String> var2);

    @Deprecated
    public abstract LinkedHashMap<String, AnnotatedMethod> findSetters(VisibilityChecker<?> var1);

    public Class<?> getBeanClass() {
        return this._type.getRawClass();
    }

    public abstract Annotations getClassAnnotations();

    public abstract AnnotatedClass getClassInfo();

    public abstract Set<String> getIgnoredPropertyNames();

    public JavaType getType() {
        return this._type;
    }

    public abstract boolean hasKnownClassAnnotations();

    public abstract JavaType resolveType(Type var1);
}


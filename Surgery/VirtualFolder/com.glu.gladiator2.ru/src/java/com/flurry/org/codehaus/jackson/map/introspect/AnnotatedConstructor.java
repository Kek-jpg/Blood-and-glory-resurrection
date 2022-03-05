/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.lang.reflect.AnnotatedElement
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.Member
 *  java.lang.reflect.Type
 *  java.lang.reflect.TypeVariable
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedWithParams;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap;
import com.flurry.org.codehaus.jackson.map.type.TypeBindings;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public final class AnnotatedConstructor
extends AnnotatedWithParams {
    protected final Constructor<?> _constructor;

    public AnnotatedConstructor(Constructor<?> constructor, AnnotationMap annotationMap, AnnotationMap[] arrannotationMap) {
        super(annotationMap, arrannotationMap);
        if (constructor == null) {
            throw new IllegalArgumentException("Null constructor not allowed");
        }
        this._constructor = constructor;
    }

    @Override
    public final Object call() throws Exception {
        return this._constructor.newInstance(new Object[0]);
    }

    @Override
    public final Object call(Object[] arrobject) throws Exception {
        return this._constructor.newInstance(arrobject);
    }

    @Override
    public final Object call1(Object object) throws Exception {
        return this._constructor.newInstance(new Object[]{object});
    }

    public Constructor<?> getAnnotated() {
        return this._constructor;
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this._constructor.getDeclaringClass();
    }

    @Override
    public Type getGenericType() {
        return this.getRawType();
    }

    @Override
    public Member getMember() {
        return this._constructor;
    }

    @Override
    public int getModifiers() {
        return this._constructor.getModifiers();
    }

    @Override
    public String getName() {
        return this._constructor.getName();
    }

    @Override
    public Class<?> getParameterClass(int n) {
        Class[] arrclass = this._constructor.getParameterTypes();
        if (n >= arrclass.length) {
            return null;
        }
        return arrclass[n];
    }

    @Override
    public int getParameterCount() {
        return this._constructor.getParameterTypes().length;
    }

    @Override
    public Type getParameterType(int n) {
        Type[] arrtype = this._constructor.getGenericParameterTypes();
        if (n >= arrtype.length) {
            return null;
        }
        return arrtype[n];
    }

    @Override
    public Class<?> getRawType() {
        return this._constructor.getDeclaringClass();
    }

    @Override
    public JavaType getType(TypeBindings typeBindings) {
        return this.getType(typeBindings, this._constructor.getTypeParameters());
    }

    @Override
    public void setValue(Object object, Object object2) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call setValue() on constructor of " + this.getDeclaringClass().getName());
    }

    public String toString() {
        return "[constructor for " + this.getName() + ", annotations: " + (Object)this._annotations + "]";
    }

    @Override
    public AnnotatedConstructor withAnnotations(AnnotationMap annotationMap) {
        return new AnnotatedConstructor(this._constructor, annotationMap, this._paramAnnotations);
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalAccessException
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.AnnotatedElement
 *  java.lang.reflect.InvocationTargetException
 *  java.lang.reflect.Member
 *  java.lang.reflect.Method
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public final class AnnotatedMethod
extends AnnotatedWithParams {
    protected final Method _method;
    protected Class<?>[] _paramTypes;

    public AnnotatedMethod(Method method, AnnotationMap annotationMap, AnnotationMap[] arrannotationMap) {
        super(annotationMap, arrannotationMap);
        this._method = method;
    }

    @Override
    public final Object call() throws Exception {
        return this._method.invoke(null, new Object[0]);
    }

    @Override
    public final Object call(Object[] arrobject) throws Exception {
        return this._method.invoke(null, arrobject);
    }

    @Override
    public final Object call1(Object object) throws Exception {
        return this._method.invoke(null, new Object[]{object});
    }

    public Method getAnnotated() {
        return this._method;
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this._method.getDeclaringClass();
    }

    public String getFullName() {
        return this.getDeclaringClass().getName() + "#" + this.getName() + "(" + this.getParameterCount() + " params)";
    }

    @Override
    public Type getGenericType() {
        return this._method.getGenericReturnType();
    }

    @Override
    public Member getMember() {
        return this._method;
    }

    @Override
    public int getModifiers() {
        return this._method.getModifiers();
    }

    @Override
    public String getName() {
        return this._method.getName();
    }

    @Override
    public Class<?> getParameterClass(int n) {
        Class[] arrclass = this._method.getParameterTypes();
        if (n >= arrclass.length) {
            return null;
        }
        return arrclass[n];
    }

    public Class<?>[] getParameterClasses() {
        if (this._paramTypes == null) {
            this._paramTypes = this._method.getParameterTypes();
        }
        return this._paramTypes;
    }

    @Override
    public int getParameterCount() {
        return this.getParameterTypes().length;
    }

    @Override
    public Type getParameterType(int n) {
        Type[] arrtype = this._method.getGenericParameterTypes();
        if (n >= arrtype.length) {
            return null;
        }
        return arrtype[n];
    }

    public Type[] getParameterTypes() {
        return this._method.getGenericParameterTypes();
    }

    @Override
    public Class<?> getRawType() {
        return this._method.getReturnType();
    }

    @Override
    public JavaType getType(TypeBindings typeBindings) {
        return this.getType(typeBindings, this._method.getTypeParameters());
    }

    @Override
    public void setValue(Object object, Object object2) throws IllegalArgumentException {
        try {
            this._method.invoke(object, new Object[]{object2});
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException("Failed to setValue() with method " + this.getFullName() + ": " + illegalAccessException.getMessage(), (Throwable)illegalAccessException);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new IllegalArgumentException("Failed to setValue() with method " + this.getFullName() + ": " + invocationTargetException.getMessage(), (Throwable)invocationTargetException);
        }
    }

    public String toString() {
        return "[method " + this.getName() + ", annotations: " + (Object)this._annotations + "]";
    }

    @Override
    public AnnotatedMethod withAnnotations(AnnotationMap annotationMap) {
        return new AnnotatedMethod(this._method, annotationMap, this._paramAnnotations);
    }

    public AnnotatedMethod withMethod(Method method) {
        return new AnnotatedMethod(method, this._annotations, this._paramAnnotations);
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.annotation.Annotation
 *  java.lang.reflect.Type
 *  java.lang.reflect.TypeVariable
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedParameter;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap;
import com.flurry.org.codehaus.jackson.map.type.TypeBindings;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public abstract class AnnotatedWithParams
extends AnnotatedMember {
    protected final AnnotationMap[] _paramAnnotations;

    protected AnnotatedWithParams(AnnotationMap annotationMap, AnnotationMap[] arrannotationMap) {
        super(annotationMap);
        this._paramAnnotations = arrannotationMap;
    }

    public final void addIfNotPresent(Annotation annotation) {
        this._annotations.addIfNotPresent(annotation);
    }

    public final void addOrOverride(Annotation annotation) {
        this._annotations.add(annotation);
    }

    public final void addOrOverrideParam(int n, Annotation annotation) {
        AnnotationMap annotationMap = this._paramAnnotations[n];
        if (annotationMap == null) {
            this._paramAnnotations[n] = annotationMap = new AnnotationMap();
        }
        annotationMap.add(annotation);
    }

    public abstract Object call() throws Exception;

    public abstract Object call(Object[] var1) throws Exception;

    public abstract Object call1(Object var1) throws Exception;

    @Override
    public final <A extends Annotation> A getAnnotation(Class<A> class_) {
        return (A)this._annotations.get(class_);
    }

    public final int getAnnotationCount() {
        return this._annotations.size();
    }

    public final AnnotatedParameter getParameter(int n) {
        return new AnnotatedParameter((AnnotatedWithParams)this, this.getParameterType(n), this._paramAnnotations[n], n);
    }

    public final AnnotationMap getParameterAnnotations(int n) {
        if (this._paramAnnotations != null && n >= 0 && n <= this._paramAnnotations.length) {
            return this._paramAnnotations[n];
        }
        return null;
    }

    public abstract Class<?> getParameterClass(int var1);

    public abstract int getParameterCount();

    public abstract Type getParameterType(int var1);

    /*
     * Enabled aggressive block sorting
     */
    protected JavaType getType(TypeBindings typeBindings, TypeVariable<?>[] arrtypeVariable) {
        if (arrtypeVariable != null && arrtypeVariable.length > 0) {
            typeBindings = typeBindings.childInstance();
            for (TypeVariable<?> typeVariable : arrtypeVariable) {
                typeBindings._addPlaceholder(typeVariable.getName());
                Type type = typeVariable.getBounds()[0];
                JavaType javaType = type == null ? TypeFactory.unknownType() : typeBindings.resolveType(type);
                typeBindings.addBinding(typeVariable.getName(), javaType);
            }
        }
        return typeBindings.resolveType(this.getGenericType());
    }

    protected AnnotatedParameter replaceParameterAnnotations(int n, AnnotationMap annotationMap) {
        this._paramAnnotations[n] = annotationMap;
        return this.getParameter(n);
    }

    public final JavaType resolveParameterType(int n, TypeBindings typeBindings) {
        return typeBindings.resolveType(this.getParameterType(n));
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.lang.annotation.Annotation
 *  java.lang.reflect.AnnotatedElement
 *  java.lang.reflect.Member
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedWithParams;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

public final class AnnotatedParameter
extends AnnotatedMember {
    protected final int _index;
    protected final AnnotatedWithParams _owner;
    protected final Type _type;

    public AnnotatedParameter(AnnotatedWithParams annotatedWithParams, Type type, AnnotationMap annotationMap, int n) {
        super(annotationMap);
        this._owner = annotatedWithParams;
        this._type = type;
        this._index = n;
    }

    public void addOrOverride(Annotation annotation) {
        this._annotations.add(annotation);
    }

    @Override
    public AnnotatedElement getAnnotated() {
        return null;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        return (A)this._annotations.get(class_);
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this._owner.getDeclaringClass();
    }

    @Override
    public Type getGenericType() {
        return this._type;
    }

    public int getIndex() {
        return this._index;
    }

    @Override
    public Member getMember() {
        return this._owner.getMember();
    }

    @Override
    public int getModifiers() {
        return this._owner.getModifiers();
    }

    @Override
    public String getName() {
        return "";
    }

    public AnnotatedWithParams getOwner() {
        return this._owner;
    }

    public Type getParameterType() {
        return this._type;
    }

    @Override
    public Class<?> getRawType() {
        if (this._type instanceof Class) {
            return (Class)this._type;
        }
        return TypeFactory.defaultInstance().constructType(this._type).getRawClass();
    }

    @Override
    public void setValue(Object object, Object object2) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call setValue() on constructor parameter of " + this.getDeclaringClass().getName());
    }

    public String toString() {
        return "[parameter #" + this.getIndex() + ", annotations: " + (Object)this._annotations + "]";
    }

    @Override
    public AnnotatedParameter withAnnotations(AnnotationMap annotationMap) {
        if (annotationMap == this._annotations) {
            return this;
        }
        return this._owner.replaceParameterAnnotations(this._index, annotationMap);
    }
}


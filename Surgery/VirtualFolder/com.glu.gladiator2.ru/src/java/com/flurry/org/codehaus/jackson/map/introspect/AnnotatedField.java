/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap
 *  java.lang.Class
 *  java.lang.IllegalAccessException
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.annotation.Annotation
 *  java.lang.reflect.AnnotatedElement
 *  java.lang.reflect.Field
 *  java.lang.reflect.Member
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

public final class AnnotatedField
extends AnnotatedMember {
    protected final Field _field;

    public AnnotatedField(Field field, AnnotationMap annotationMap) {
        super(annotationMap);
        this._field = field;
    }

    public void addOrOverride(Annotation annotation) {
        this._annotations.add(annotation);
    }

    public Field getAnnotated() {
        return this._field;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        return (A)this._annotations.get(class_);
    }

    public int getAnnotationCount() {
        return this._annotations.size();
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this._field.getDeclaringClass();
    }

    public String getFullName() {
        return this.getDeclaringClass().getName() + "#" + this.getName();
    }

    @Override
    public Type getGenericType() {
        return this._field.getGenericType();
    }

    @Override
    public Member getMember() {
        return this._field;
    }

    @Override
    public int getModifiers() {
        return this._field.getModifiers();
    }

    @Override
    public String getName() {
        return this._field.getName();
    }

    @Override
    public Class<?> getRawType() {
        return this._field.getType();
    }

    @Override
    public void setValue(Object object, Object object2) throws IllegalArgumentException {
        try {
            this._field.set(object, object2);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException("Failed to setValue() for field " + this.getFullName() + ": " + illegalAccessException.getMessage(), (Throwable)illegalAccessException);
        }
    }

    public String toString() {
        return "[field " + this.getName() + ", annotations: " + (Object)this._annotations + "]";
    }

    @Override
    public AnnotatedField withAnnotations(AnnotationMap annotationMap) {
        return new AnnotatedField(this._field, annotationMap);
    }
}


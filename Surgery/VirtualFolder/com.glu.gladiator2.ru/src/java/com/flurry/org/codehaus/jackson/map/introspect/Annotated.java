/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.annotation.Annotation
 *  java.lang.reflect.AnnotatedElement
 *  java.lang.reflect.Modifier
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap;
import com.flurry.org.codehaus.jackson.map.type.TypeBindings;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public abstract class Annotated {
    protected Annotated() {
    }

    protected abstract AnnotationMap getAllAnnotations();

    public abstract AnnotatedElement getAnnotated();

    public abstract <A extends Annotation> A getAnnotation(Class<A> var1);

    public abstract Type getGenericType();

    protected abstract int getModifiers();

    public abstract String getName();

    public abstract Class<?> getRawType();

    public JavaType getType(TypeBindings typeBindings) {
        return typeBindings.resolveType(this.getGenericType());
    }

    public final <A extends Annotation> boolean hasAnnotation(Class<A> class_) {
        return this.getAnnotation(class_) != null;
    }

    public final boolean isPublic() {
        return Modifier.isPublic((int)this.getModifiers());
    }

    public abstract Annotated withAnnotations(AnnotationMap var1);

    public final Annotated withFallBackAnnotationsFrom(Annotated annotated) {
        return this.withAnnotations(AnnotationMap.merge((AnnotationMap)this.getAllAnnotations(), (AnnotationMap)annotated.getAllAnnotations()));
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.annotation.Annotation
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.Iterator
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.util.Annotations;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public final class AnnotationMap
implements Annotations {
    protected HashMap<Class<? extends Annotation>, Annotation> _annotations;

    public AnnotationMap() {
    }

    private AnnotationMap(HashMap<Class<? extends Annotation>, Annotation> hashMap) {
        this._annotations = hashMap;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static AnnotationMap merge(AnnotationMap annotationMap, AnnotationMap annotationMap2) {
        if (annotationMap == null) return annotationMap2;
        if (annotationMap._annotations == null) return annotationMap2;
        if (annotationMap._annotations.isEmpty()) {
            return annotationMap2;
        }
        if (annotationMap2 == null) return annotationMap;
        if (annotationMap2._annotations == null) return annotationMap;
        if (annotationMap2._annotations.isEmpty()) return annotationMap;
        HashMap hashMap = new HashMap();
        for (Annotation annotation : annotationMap2._annotations.values()) {
            hashMap.put((Object)annotation.annotationType(), (Object)annotation);
        }
        Iterator iterator = annotationMap._annotations.values().iterator();
        while (iterator.hasNext()) {
            Annotation annotation = (Annotation)iterator.next();
            hashMap.put((Object)annotation.annotationType(), (Object)annotation);
        }
        return new AnnotationMap((HashMap<Class<? extends Annotation>, Annotation>)hashMap);
    }

    protected final void _add(Annotation annotation) {
        if (this._annotations == null) {
            this._annotations = new HashMap();
        }
        this._annotations.put((Object)annotation.annotationType(), (Object)annotation);
    }

    public void add(Annotation annotation) {
        this._add(annotation);
    }

    public void addIfNotPresent(Annotation annotation) {
        if (this._annotations == null || !this._annotations.containsKey((Object)annotation.annotationType())) {
            this._add(annotation);
        }
    }

    @Override
    public <A extends Annotation> A get(Class<A> class_) {
        if (this._annotations == null) {
            return null;
        }
        return (A)((Annotation)this._annotations.get(class_));
    }

    @Override
    public int size() {
        if (this._annotations == null) {
            return 0;
        }
        return this._annotations.size();
    }

    public String toString() {
        if (this._annotations == null) {
            return "[null]";
        }
        return this._annotations.toString();
    }
}


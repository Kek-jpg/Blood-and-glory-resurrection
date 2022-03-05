/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.UnsupportedOperationException
 *  java.lang.reflect.Member
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import java.lang.reflect.Member;

public abstract class AnnotatedMember
extends Annotated {
    protected final AnnotationMap _annotations;

    protected AnnotatedMember(AnnotationMap annotationMap) {
        this._annotations = annotationMap;
    }

    public final void fixAccess() {
        ClassUtil.checkAndFixAccess(this.getMember());
    }

    @Override
    protected AnnotationMap getAllAnnotations() {
        return this._annotations;
    }

    public abstract Class<?> getDeclaringClass();

    public abstract Member getMember();

    public abstract void setValue(Object var1, Object var2) throws UnsupportedOperationException, IllegalArgumentException;
}


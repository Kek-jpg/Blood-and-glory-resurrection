/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.annotation.Annotation
 */
package com.flurry.org.codehaus.jackson.map.util;

import java.lang.annotation.Annotation;

public interface Annotations {
    public <A extends Annotation> A get(Class<A> var1);

    public int size();
}


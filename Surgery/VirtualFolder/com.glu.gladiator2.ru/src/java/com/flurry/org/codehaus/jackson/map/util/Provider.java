/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.Collection
 */
package com.flurry.org.codehaus.jackson.map.util;

import java.util.Collection;

public interface Provider<T> {
    public Collection<T> provide();
}


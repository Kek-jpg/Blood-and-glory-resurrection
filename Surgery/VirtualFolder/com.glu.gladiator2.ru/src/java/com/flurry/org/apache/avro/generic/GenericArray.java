/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.List
 */
package com.flurry.org.apache.avro.generic;

import com.flurry.org.apache.avro.generic.GenericContainer;
import java.util.List;

public interface GenericArray<T>
extends List<T>,
GenericContainer {
    public T peek();

    public void reverse();
}


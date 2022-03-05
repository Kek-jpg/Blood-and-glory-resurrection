/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.flurry.org.apache.avro.generic;

import com.flurry.org.apache.avro.generic.GenericContainer;

public interface IndexedRecord
extends GenericContainer {
    public Object get(int var1);

    public void put(int var1, Object var2);
}


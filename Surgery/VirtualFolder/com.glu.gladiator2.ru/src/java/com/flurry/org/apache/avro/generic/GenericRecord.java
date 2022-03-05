/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.apache.avro.generic;

import com.flurry.org.apache.avro.generic.IndexedRecord;

public interface GenericRecord
extends IndexedRecord {
    public Object get(String var1);

    public void put(String var1, Object var2);
}


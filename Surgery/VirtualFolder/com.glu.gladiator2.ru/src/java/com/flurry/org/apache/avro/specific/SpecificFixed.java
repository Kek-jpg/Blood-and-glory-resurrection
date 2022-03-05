/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.reflect.Type
 */
package com.flurry.org.apache.avro.specific;

import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.generic.GenericData;
import com.flurry.org.apache.avro.specific.SpecificData;
import java.lang.reflect.Type;

public abstract class SpecificFixed
extends GenericData.Fixed {
    public SpecificFixed() {
        this.setSchema(SpecificData.get().getSchema((Type)this.getClass()));
    }

    public SpecificFixed(byte[] arrby) {
        this.bytes(arrby);
    }
}


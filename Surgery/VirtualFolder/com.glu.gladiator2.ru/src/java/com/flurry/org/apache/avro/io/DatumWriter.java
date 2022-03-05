/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.Encoder;
import java.io.IOException;

public interface DatumWriter<D> {
    public void setSchema(Schema var1);

    public void write(D var1, Encoder var2) throws IOException;
}


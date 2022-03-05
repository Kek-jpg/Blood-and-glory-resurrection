/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.Decoder;
import java.io.IOException;

public interface DatumReader<D> {
    public D read(D var1, Decoder var2) throws IOException;

    public void setSchema(Schema var1);
}


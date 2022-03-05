/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Closeable
 *  java.io.IOException
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.util.Iterator
 */
package com.flurry.org.apache.avro.file;

import com.flurry.org.apache.avro.Schema;
import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

public interface FileReader<D>
extends Iterator<D>,
Iterable<D>,
Closeable {
    public Schema getSchema();

    public D next(D var1) throws IOException;

    public boolean pastSync(long var1) throws IOException;

    public void sync(long var1) throws IOException;

    public long tell() throws IOException;
}


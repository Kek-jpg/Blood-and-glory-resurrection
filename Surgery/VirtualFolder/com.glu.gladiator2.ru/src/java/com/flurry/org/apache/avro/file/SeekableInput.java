/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Closeable
 *  java.io.IOException
 *  java.lang.Object
 */
package com.flurry.org.apache.avro.file;

import java.io.Closeable;
import java.io.IOException;

public interface SeekableInput
extends Closeable {
    public long length() throws IOException;

    public int read(byte[] var1, int var2, int var3) throws IOException;

    public void seek(long var1) throws IOException;

    public long tell() throws IOException;
}


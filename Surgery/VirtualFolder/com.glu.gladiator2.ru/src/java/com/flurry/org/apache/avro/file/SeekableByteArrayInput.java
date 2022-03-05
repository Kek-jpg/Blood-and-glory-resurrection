/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.IOException
 */
package com.flurry.org.apache.avro.file;

import com.flurry.org.apache.avro.file.SeekableInput;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SeekableByteArrayInput
extends ByteArrayInputStream
implements SeekableInput {
    public SeekableByteArrayInput(byte[] arrby) {
        super(arrby);
    }

    @Override
    public long length() throws IOException {
        return this.count;
    }

    @Override
    public void seek(long l2) throws IOException {
        this.reset();
        this.skip(l2);
    }

    @Override
    public long tell() throws IOException {
        return this.pos;
    }
}


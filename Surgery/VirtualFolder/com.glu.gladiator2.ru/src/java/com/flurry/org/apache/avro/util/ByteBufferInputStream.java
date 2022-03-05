/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.EOFException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.nio.ByteBuffer
 *  java.util.List
 */
package com.flurry.org.apache.avro.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class ByteBufferInputStream
extends InputStream {
    private List<ByteBuffer> buffers;
    private int current;

    public ByteBufferInputStream(List<ByteBuffer> list) {
        this.buffers = list;
    }

    private ByteBuffer getBuffer() throws IOException {
        while (this.current < this.buffers.size()) {
            ByteBuffer byteBuffer = (ByteBuffer)this.buffers.get(this.current);
            if (byteBuffer.hasRemaining()) {
                return byteBuffer;
            }
            this.current = 1 + this.current;
        }
        throw new EOFException();
    }

    public int read() throws IOException {
        return 255 & this.getBuffer().get();
    }

    public int read(byte[] arrby, int n2, int n3) throws IOException {
        if (n3 == 0) {
            return 0;
        }
        ByteBuffer byteBuffer = ByteBufferInputStream.super.getBuffer();
        int n4 = byteBuffer.remaining();
        if (n3 > n4) {
            byteBuffer.get(arrby, n2, n4);
            return n4;
        }
        byteBuffer.get(arrby, n2, n3);
        return n3;
    }

    public ByteBuffer readBuffer(int n2) throws IOException {
        if (n2 == 0) {
            return ByteBuffer.allocate((int)0);
        }
        ByteBuffer byteBuffer = ByteBufferInputStream.super.getBuffer();
        if (byteBuffer.remaining() == n2) {
            this.current = 1 + this.current;
            return byteBuffer;
        }
        ByteBuffer byteBuffer2 = ByteBuffer.allocate((int)n2);
        for (int i2 = 0; i2 < n2; i2 += this.read((byte[])byteBuffer2.array(), (int)i2, (int)(n2 - i2))) {
        }
        return byteBuffer2;
    }
}


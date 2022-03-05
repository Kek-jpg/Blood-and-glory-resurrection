/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.String
 *  java.nio.ByteBuffer
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.io.Encoder;
import com.flurry.org.apache.avro.util.Utf8;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class BinaryEncoder
extends Encoder {
    public abstract int bytesBuffered();

    @Override
    public void setItemCount(long l2) throws IOException {
        if (l2 > 0L) {
            this.writeLong(l2);
        }
    }

    @Override
    public void startItem() throws IOException {
    }

    @Override
    public void writeArrayEnd() throws IOException {
        this.writeZero();
    }

    @Override
    public void writeArrayStart() throws IOException {
    }

    @Override
    public void writeBytes(ByteBuffer byteBuffer) throws IOException {
        int n2 = byteBuffer.position();
        int n3 = n2 + byteBuffer.arrayOffset();
        int n4 = byteBuffer.limit() - n2;
        this.writeBytes(byteBuffer.array(), n3, n4);
    }

    @Override
    public void writeBytes(byte[] arrby, int n2, int n3) throws IOException {
        if (n3 == 0) {
            this.writeZero();
            return;
        }
        this.writeInt(n3);
        this.writeFixed(arrby, n2, n3);
    }

    @Override
    public void writeEnum(int n2) throws IOException {
        this.writeInt(n2);
    }

    @Override
    public void writeIndex(int n2) throws IOException {
        this.writeInt(n2);
    }

    @Override
    public void writeMapEnd() throws IOException {
        this.writeZero();
    }

    @Override
    public void writeMapStart() throws IOException {
    }

    @Override
    public void writeNull() throws IOException {
    }

    @Override
    public void writeString(Utf8 utf8) throws IOException {
        this.writeBytes(utf8.getBytes(), 0, utf8.getByteLength());
    }

    @Override
    public void writeString(String string) throws IOException {
        if (string.length() == 0) {
            this.writeZero();
            return;
        }
        byte[] arrby = string.getBytes("UTF-8");
        this.writeInt(arrby.length);
        this.writeFixed(arrby, 0, arrby.length);
    }

    protected abstract void writeZero() throws IOException;
}


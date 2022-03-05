/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.NullPointerException
 *  java.lang.String
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.io.BinaryData;
import com.flurry.org.apache.avro.io.BinaryEncoder;
import java.io.IOException;
import java.io.OutputStream;

public class DirectBinaryEncoder
extends BinaryEncoder {
    private final byte[] buf = new byte[12];
    private OutputStream out;

    DirectBinaryEncoder(OutputStream outputStream) {
        this.configure(outputStream);
    }

    @Override
    public int bytesBuffered() {
        return 0;
    }

    DirectBinaryEncoder configure(OutputStream outputStream) {
        if (outputStream == null) {
            throw new NullPointerException("OutputStream cannot be null!");
        }
        this.out = outputStream;
        return this;
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeBoolean(boolean bl) throws IOException {
        OutputStream outputStream = this.out;
        int n2 = bl ? 1 : 0;
        outputStream.write(n2);
    }

    @Override
    public void writeDouble(double d2) throws IOException {
        byte[] arrby = new byte[8];
        int n2 = BinaryData.encodeDouble(d2, arrby, 0);
        this.out.write(arrby, 0, n2);
    }

    @Override
    public void writeFixed(byte[] arrby, int n2, int n3) throws IOException {
        this.out.write(arrby, n2, n3);
    }

    @Override
    public void writeFloat(float f2) throws IOException {
        int n2 = BinaryData.encodeFloat(f2, this.buf, 0);
        this.out.write(this.buf, 0, n2);
    }

    @Override
    public void writeInt(int n2) throws IOException {
        int n3 = n2 << 1 ^ n2 >> 31;
        if ((n3 & -128) == 0) {
            this.out.write(n3);
            return;
        }
        if ((n3 & -16384) == 0) {
            this.out.write(n3 | 128);
            this.out.write(n3 >>> 7);
            return;
        }
        int n4 = BinaryData.encodeInt(n2, this.buf, 0);
        this.out.write(this.buf, 0, n4);
    }

    @Override
    public void writeLong(long l2) throws IOException {
        long l3 = l2 << 1 ^ l2 >> 63;
        if ((Integer.MIN_VALUE & l3) == 0L) {
            int n2 = (int)l3;
            while ((n2 & -128) != 0) {
                this.out.write((int)((byte)(255 & (n2 | 128))));
                n2 >>>= 7;
            }
            this.out.write((int)((byte)n2));
            return;
        }
        int n3 = BinaryData.encodeLong(l2, this.buf, 0);
        this.out.write(this.buf, 0, n3);
    }

    @Override
    protected void writeZero() throws IOException {
        this.out.write(0);
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.EOFException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.nio.Buffer
 *  java.nio.ByteBuffer
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.io.BinaryDecoder;
import com.flurry.org.apache.avro.util.ByteBufferInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

class DirectBinaryDecoder
extends BinaryDecoder {
    private final byte[] buf = new byte[8];
    private ByteReader byteReader;
    private InputStream in;

    DirectBinaryDecoder(InputStream inputStream) {
        this.configure(inputStream);
    }

    /*
     * Enabled aggressive block sorting
     */
    DirectBinaryDecoder configure(InputStream inputStream) {
        this.in = inputStream;
        ByteReader byteReader = inputStream instanceof ByteBufferInputStream ? (DirectBinaryDecoder)this.new ReuseByteReader((ByteBufferInputStream)inputStream) : new ByteReader((DirectBinaryDecoder)this, null);
        this.byteReader = byteReader;
        return this;
    }

    @Override
    protected void doReadBytes(byte[] arrby, int n2, int n3) throws IOException {
        int n4;
        while ((n4 = this.in.read(arrby, n2, n3)) != n3 && n3 != 0) {
            if (n4 < 0) {
                throw new EOFException();
            }
            n2 += n4;
            n3 -= n4;
        }
        return;
    }

    @Override
    protected void doSkipBytes(long l2) throws IOException {
        while (l2 > 0L) {
            long l3 = this.in.skip(l2);
            if (l3 <= 0L) {
                throw new EOFException();
            }
            l2 -= l3;
        }
    }

    @Override
    public InputStream inputStream() {
        return this.in;
    }

    @Override
    public boolean isEnd() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean readBoolean() throws IOException {
        int n2 = this.in.read();
        if (n2 < 0) {
            throw new EOFException();
        }
        return n2 == 1;
    }

    @Override
    public ByteBuffer readBytes(ByteBuffer byteBuffer) throws IOException {
        int n2 = this.readInt();
        return this.byteReader.read(byteBuffer, n2);
    }

    @Override
    public double readDouble() throws IOException {
        this.doReadBytes(this.buf, 0, 8);
        return Double.longBitsToDouble((long)(255L & (long)this.buf[0] | (255L & (long)this.buf[1]) << 8 | (255L & (long)this.buf[2]) << 16 | (255L & (long)this.buf[3]) << 24 | (255L & (long)this.buf[4]) << 32 | (255L & (long)this.buf[5]) << 40 | (255L & (long)this.buf[6]) << 48 | (255L & (long)this.buf[7]) << 56));
    }

    @Override
    public float readFloat() throws IOException {
        this.doReadBytes(this.buf, 0, 4);
        return Float.intBitsToFloat((int)(255 & this.buf[0] | (255 & this.buf[1]) << 8 | (255 & this.buf[2]) << 16 | (255 & this.buf[3]) << 24));
    }

    @Override
    public int readInt() throws IOException {
        int n2 = 0;
        int n3 = 0;
        do {
            int n4;
            if ((n4 = this.in.read()) >= 0) {
                n2 |= (n4 & 127) << n3;
                if ((n4 & 128) != 0) continue;
                return n2 >>> 1 ^ -(n2 & 1);
            }
            throw new EOFException();
        } while ((n3 += 7) < 32);
        throw new IOException("Invalid int encoding");
    }

    @Override
    public long readLong() throws IOException {
        long l2 = 0L;
        int n2 = 0;
        do {
            int n3;
            if ((n3 = this.in.read()) >= 0) {
                l2 |= (127L & (long)n3) << n2;
                if ((n3 & 128) != 0) continue;
                return l2 >>> 1 ^ -(1L & l2);
            }
            throw new EOFException();
        } while ((n2 += 7) < 64);
        throw new IOException("Invalid long encoding");
    }

    private class ByteReader {
        final /* synthetic */ DirectBinaryDecoder this$0;

        private ByteReader(DirectBinaryDecoder directBinaryDecoder) {
            this.this$0 = directBinaryDecoder;
        }

        /* synthetic */ ByteReader(DirectBinaryDecoder directBinaryDecoder, 1 var2_2) {
            super(directBinaryDecoder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public ByteBuffer read(ByteBuffer byteBuffer, int n2) throws IOException {
            ByteBuffer byteBuffer2;
            if (byteBuffer != null && n2 <= byteBuffer.capacity()) {
                byteBuffer2 = byteBuffer;
                byteBuffer2.clear();
            } else {
                byteBuffer2 = ByteBuffer.allocate((int)n2);
            }
            this.this$0.doReadBytes(byteBuffer2.array(), byteBuffer2.position(), n2);
            byteBuffer2.limit(n2);
            return byteBuffer2;
        }
    }

    private class ReuseByteReader
    extends ByteReader {
        private final ByteBufferInputStream bbi;

        public ReuseByteReader(ByteBufferInputStream byteBufferInputStream) {
            super(DirectBinaryDecoder.this, null);
            this.bbi = byteBufferInputStream;
        }

        @Override
        public ByteBuffer read(ByteBuffer byteBuffer, int n2) throws IOException {
            if (byteBuffer != null) {
                return super.read(byteBuffer, n2);
            }
            return this.bbi.readBuffer(n2);
        }
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.io.BinaryData;
import com.flurry.org.apache.avro.io.BinaryEncoder;
import java.io.IOException;
import java.io.OutputStream;

public class BufferedBinaryEncoder
extends BinaryEncoder {
    private byte[] buf;
    private int bulkLimit;
    private int pos;
    private ByteSink sink;

    BufferedBinaryEncoder(OutputStream outputStream, int n2) {
        this.configure(outputStream, n2);
    }

    private void ensureBounds(int n2) throws IOException {
        if (this.buf.length - this.pos < n2) {
            BufferedBinaryEncoder.super.flushBuffer();
        }
    }

    private void flushBuffer() throws IOException {
        if (this.pos > 0) {
            this.sink.innerWrite(this.buf, 0, this.pos);
            this.pos = 0;
        }
    }

    private void writeByte(int n2) throws IOException {
        if (this.pos == this.buf.length) {
            BufferedBinaryEncoder.super.flushBuffer();
        }
        byte[] arrby = this.buf;
        int n3 = this.pos;
        this.pos = n3 + 1;
        arrby[n3] = (byte)(n2 & 255);
    }

    @Override
    public int bytesBuffered() {
        return this.pos;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    BufferedBinaryEncoder configure(OutputStream outputStream, int n2) {
        if (outputStream == null) {
            throw new NullPointerException("OutputStream cannot be null!");
        }
        if (this.sink != null && this.pos > 0) {
            BufferedBinaryEncoder.super.flushBuffer();
        }
        this.sink = new OutputStreamSink(outputStream, null);
        this.pos = 0;
        if (this.buf == null || this.buf.length != n2) {
            this.buf = new byte[n2];
        }
        this.bulkLimit = this.buf.length >>> 1;
        if (this.bulkLimit <= 512) return this;
        this.bulkLimit = 512;
        return this;
        catch (IOException iOException) {
            throw new AvroRuntimeException("Failure flushing old output", iOException);
        }
    }

    public void flush() throws IOException {
        this.flushBuffer();
        this.sink.innerFlush();
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        if (this.buf.length == this.pos) {
            BufferedBinaryEncoder.super.flushBuffer();
        }
        this.pos += BinaryData.encodeBoolean(bl, this.buf, this.pos);
    }

    @Override
    public void writeDouble(double d2) throws IOException {
        BufferedBinaryEncoder.super.ensureBounds(8);
        this.pos += BinaryData.encodeDouble(d2, this.buf, this.pos);
    }

    @Override
    public void writeFixed(byte[] arrby, int n2, int n3) throws IOException {
        if (n3 > this.bulkLimit) {
            BufferedBinaryEncoder.super.flushBuffer();
            this.sink.innerWrite(arrby, n2, n3);
            return;
        }
        BufferedBinaryEncoder.super.ensureBounds(n3);
        System.arraycopy((Object)arrby, (int)n2, (Object)this.buf, (int)this.pos, (int)n3);
        this.pos = n3 + this.pos;
    }

    @Override
    public void writeFloat(float f2) throws IOException {
        BufferedBinaryEncoder.super.ensureBounds(4);
        this.pos += BinaryData.encodeFloat(f2, this.buf, this.pos);
    }

    @Override
    public void writeInt(int n2) throws IOException {
        BufferedBinaryEncoder.super.ensureBounds(5);
        this.pos += BinaryData.encodeInt(n2, this.buf, this.pos);
    }

    @Override
    public void writeLong(long l2) throws IOException {
        BufferedBinaryEncoder.super.ensureBounds(10);
        this.pos += BinaryData.encodeLong(l2, this.buf, this.pos);
    }

    @Override
    protected void writeZero() throws IOException {
        this.writeByte(0);
    }

    private static abstract class ByteSink {
        protected ByteSink() {
        }

        protected abstract void innerFlush() throws IOException;

        protected abstract void innerWrite(byte[] var1, int var2, int var3) throws IOException;
    }

    static class OutputStreamSink
    extends ByteSink {
        private final OutputStream out;

        private OutputStreamSink(OutputStream outputStream) {
            this.out = outputStream;
        }

        /* synthetic */ OutputStreamSink(OutputStream outputStream, 1 var2_2) {
            super(outputStream);
        }

        @Override
        protected void innerFlush() throws IOException {
            this.out.flush();
        }

        @Override
        protected void innerWrite(byte[] arrby, int n2, int n3) throws IOException {
            this.out.write(arrby, n2, n3);
        }
    }

}


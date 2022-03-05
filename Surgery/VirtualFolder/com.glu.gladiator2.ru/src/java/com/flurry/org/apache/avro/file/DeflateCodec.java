/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.ByteBuffer
 *  java.util.zip.Deflater
 *  java.util.zip.DeflaterOutputStream
 *  java.util.zip.Inflater
 *  java.util.zip.InflaterOutputStream
 */
package com.flurry.org.apache.avro.file;

import com.flurry.org.apache.avro.file.Codec;
import com.flurry.org.apache.avro.file.CodecFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

class DeflateCodec
extends Codec {
    private int compressionLevel;
    private Deflater deflater;
    private Inflater inflater;
    private boolean nowrap = true;
    private ByteArrayOutputStream outputBuffer;

    public DeflateCodec(int n2) {
        this.compressionLevel = n2;
    }

    private Deflater getDeflater() {
        if (this.deflater == null) {
            this.deflater = new Deflater(this.compressionLevel, this.nowrap);
        }
        this.deflater.reset();
        return this.deflater;
    }

    private Inflater getInflater() {
        if (this.inflater == null) {
            this.inflater = new Inflater(this.nowrap);
        }
        this.inflater.reset();
        return this.inflater;
    }

    private ByteArrayOutputStream getOutputBuffer(int n2) {
        if (this.outputBuffer == null) {
            this.outputBuffer = new ByteArrayOutputStream(n2);
        }
        this.outputBuffer.reset();
        return this.outputBuffer;
    }

    private void writeAndClose(ByteBuffer byteBuffer, OutputStream outputStream) throws IOException {
        byte[] arrby = byteBuffer.array();
        int n2 = byteBuffer.arrayOffset() + byteBuffer.position();
        int n3 = byteBuffer.remaining();
        try {
            outputStream.write(arrby, n2, n3);
            return;
        }
        finally {
            outputStream.close();
        }
    }

    @Override
    ByteBuffer compress(ByteBuffer byteBuffer) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = DeflateCodec.super.getOutputBuffer(byteBuffer.remaining());
        DeflateCodec.super.writeAndClose(byteBuffer, (OutputStream)new DeflaterOutputStream((OutputStream)byteArrayOutputStream, DeflateCodec.super.getDeflater()));
        return ByteBuffer.wrap((byte[])byteArrayOutputStream.toByteArray());
    }

    @Override
    ByteBuffer decompress(ByteBuffer byteBuffer) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = DeflateCodec.super.getOutputBuffer(byteBuffer.remaining());
        DeflateCodec.super.writeAndClose(byteBuffer, (OutputStream)new InflaterOutputStream((OutputStream)byteArrayOutputStream, DeflateCodec.super.getInflater()));
        return ByteBuffer.wrap((byte[])byteArrayOutputStream.toByteArray());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean equals(Object object) {
        block5 : {
            block4 : {
                if (this == object) break block4;
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                DeflateCodec deflateCodec = (DeflateCodec)object;
                if (this.nowrap != deflateCodec.nowrap) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    String getName() {
        return "deflate";
    }

    @Override
    public int hashCode() {
        return !this.nowrap;
    }

    @Override
    public String toString() {
        return this.getName() + "-" + this.compressionLevel;
    }

    static class Option
    extends CodecFactory {
        private int compressionLevel;

        Option(int n2) {
            this.compressionLevel = n2;
        }

        @Override
        protected Codec createInstance() {
            return new DeflateCodec(this.compressionLevel);
        }
    }

}


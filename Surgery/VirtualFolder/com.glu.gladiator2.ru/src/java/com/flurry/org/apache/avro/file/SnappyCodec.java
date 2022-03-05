/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.Buffer
 *  java.nio.ByteBuffer
 *  java.util.zip.CRC32
 *  org.xerial.snappy.Snappy
 */
package com.flurry.org.apache.avro.file;

import com.flurry.org.apache.avro.file.Codec;
import com.flurry.org.apache.avro.file.CodecFactory;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import org.xerial.snappy.Snappy;

class SnappyCodec
extends Codec {
    private CRC32 crc32;

    private SnappyCodec() {
        this.crc32 = new CRC32();
    }

    /* synthetic */ SnappyCodec(1 var1) {
    }

    @Override
    ByteBuffer compress(ByteBuffer byteBuffer) throws IOException {
        ByteBuffer byteBuffer2 = ByteBuffer.allocate((int)(4 + Snappy.maxCompressedLength((int)byteBuffer.remaining())));
        int n2 = Snappy.compress((byte[])byteBuffer.array(), (int)byteBuffer.position(), (int)byteBuffer.remaining(), (byte[])byteBuffer2.array(), (int)0);
        this.crc32.reset();
        this.crc32.update(byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
        byteBuffer2.putInt(n2, (int)this.crc32.getValue());
        byteBuffer2.limit(n2 + 4);
        return byteBuffer2;
    }

    @Override
    ByteBuffer decompress(ByteBuffer byteBuffer) throws IOException {
        ByteBuffer byteBuffer2 = ByteBuffer.allocate((int)Snappy.uncompressedLength((byte[])byteBuffer.array(), (int)byteBuffer.position(), (int)(-4 + byteBuffer.remaining())));
        int n2 = Snappy.uncompress((byte[])byteBuffer.array(), (int)byteBuffer.position(), (int)(-4 + byteBuffer.remaining()), (byte[])byteBuffer2.array(), (int)0);
        byteBuffer2.limit(n2);
        this.crc32.reset();
        this.crc32.update(byteBuffer2.array(), 0, n2);
        if (byteBuffer.getInt(-4 + byteBuffer.limit()) != (int)this.crc32.getValue()) {
            throw new IOException("Checksum failure");
        }
        return byteBuffer2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean equals(Object object) {
        return this == object || this.getClass() == object.getClass();
    }

    @Override
    String getName() {
        return "snappy";
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

    static class Option
    extends CodecFactory {
        Option() {
        }

        @Override
        protected Codec createInstance() {
            return new SnappyCodec(null);
        }
    }

}


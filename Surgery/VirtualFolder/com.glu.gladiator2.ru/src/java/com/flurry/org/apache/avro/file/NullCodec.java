/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.ByteBuffer
 */
package com.flurry.org.apache.avro.file;

import com.flurry.org.apache.avro.file.Codec;
import com.flurry.org.apache.avro.file.CodecFactory;
import java.io.IOException;
import java.nio.ByteBuffer;

final class NullCodec
extends Codec {
    private static final NullCodec INSTANCE = new NullCodec();
    public static final CodecFactory OPTION = new Option();

    NullCodec() {
    }

    @Override
    ByteBuffer compress(ByteBuffer byteBuffer) throws IOException {
        return byteBuffer;
    }

    @Override
    ByteBuffer decompress(ByteBuffer byteBuffer) throws IOException {
        return byteBuffer;
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
        return "null";
    }

    @Override
    public int hashCode() {
        return 2;
    }

    static class Option
    extends CodecFactory {
        Option() {
        }

        @Override
        protected Codec createInstance() {
            return INSTANCE;
        }
    }

}


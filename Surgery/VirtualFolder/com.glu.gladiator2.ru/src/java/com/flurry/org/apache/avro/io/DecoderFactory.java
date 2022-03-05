/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.BinaryDecoder;
import com.flurry.org.apache.avro.io.Decoder;
import com.flurry.org.apache.avro.io.DirectBinaryDecoder;
import com.flurry.org.apache.avro.io.JsonDecoder;
import com.flurry.org.apache.avro.io.ResolvingDecoder;
import com.flurry.org.apache.avro.io.ValidatingDecoder;
import java.io.IOException;
import java.io.InputStream;

public class DecoderFactory {
    static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final DecoderFactory DEFAULT_FACTORY = new DefaultDecoderFactory(null);
    int binaryDecoderBufferSize = 8192;

    @Deprecated
    public static DecoderFactory defaultFactory() {
        return DecoderFactory.get();
    }

    public static DecoderFactory get() {
        return DEFAULT_FACTORY;
    }

    public BinaryDecoder binaryDecoder(InputStream inputStream, BinaryDecoder binaryDecoder) {
        if (binaryDecoder == null || !binaryDecoder.getClass().equals(BinaryDecoder.class)) {
            return new BinaryDecoder(inputStream, this.binaryDecoderBufferSize);
        }
        return binaryDecoder.configure(inputStream, this.binaryDecoderBufferSize);
    }

    public BinaryDecoder binaryDecoder(byte[] arrby, int n2, int n3, BinaryDecoder binaryDecoder) {
        if (binaryDecoder == null || !binaryDecoder.getClass().equals(BinaryDecoder.class)) {
            return new BinaryDecoder(arrby, n2, n3);
        }
        return binaryDecoder.configure(arrby, n2, n3);
    }

    public BinaryDecoder binaryDecoder(byte[] arrby, BinaryDecoder binaryDecoder) {
        return this.binaryDecoder(arrby, 0, arrby.length, binaryDecoder);
    }

    public DecoderFactory configureDecoderBufferSize(int n2) {
        if (n2 < 32) {
            n2 = 32;
        }
        if (n2 > 16777216) {
            n2 = 16777216;
        }
        this.binaryDecoderBufferSize = n2;
        return this;
    }

    @Deprecated
    public BinaryDecoder createBinaryDecoder(InputStream inputStream, BinaryDecoder binaryDecoder) {
        return this.binaryDecoder(inputStream, binaryDecoder);
    }

    @Deprecated
    public BinaryDecoder createBinaryDecoder(byte[] arrby, int n2, int n3, BinaryDecoder binaryDecoder) {
        if (binaryDecoder == null || !binaryDecoder.getClass().equals(BinaryDecoder.class)) {
            return new BinaryDecoder(arrby, n2, n3);
        }
        return binaryDecoder.configure(arrby, n2, n3);
    }

    @Deprecated
    public BinaryDecoder createBinaryDecoder(byte[] arrby, BinaryDecoder binaryDecoder) {
        return this.binaryDecoder(arrby, 0, arrby.length, binaryDecoder);
    }

    public BinaryDecoder directBinaryDecoder(InputStream inputStream, BinaryDecoder binaryDecoder) {
        if (binaryDecoder == null || !binaryDecoder.getClass().equals(DirectBinaryDecoder.class)) {
            return new DirectBinaryDecoder(inputStream);
        }
        return ((DirectBinaryDecoder)binaryDecoder).configure(inputStream);
    }

    public int getConfiguredBufferSize() {
        return this.binaryDecoderBufferSize;
    }

    public JsonDecoder jsonDecoder(Schema schema, InputStream inputStream) throws IOException {
        return new JsonDecoder(schema, inputStream);
    }

    public JsonDecoder jsonDecoder(Schema schema, String string) throws IOException {
        return new JsonDecoder(schema, string);
    }

    public ResolvingDecoder resolvingDecoder(Schema schema, Schema schema2, Decoder decoder) throws IOException {
        return new ResolvingDecoder(schema, schema2, decoder);
    }

    public ValidatingDecoder validatingDecoder(Schema schema, Decoder decoder) throws IOException {
        return new ValidatingDecoder(schema, decoder);
    }

    private static class DefaultDecoderFactory
    extends DecoderFactory {
        private DefaultDecoderFactory() {
        }

        /* synthetic */ DefaultDecoderFactory(1 var1) {
        }

        @Override
        public DecoderFactory configureDecoderBufferSize(int n2) {
            throw new IllegalArgumentException("This Factory instance is Immutable");
        }
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.BinaryEncoder;
import com.flurry.org.apache.avro.io.BlockingBinaryEncoder;
import com.flurry.org.apache.avro.io.BufferedBinaryEncoder;
import com.flurry.org.apache.avro.io.DirectBinaryEncoder;
import com.flurry.org.apache.avro.io.Encoder;
import com.flurry.org.apache.avro.io.JsonEncoder;
import com.flurry.org.apache.avro.io.ValidatingEncoder;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import java.io.IOException;
import java.io.OutputStream;

public class EncoderFactory {
    private static final int DEFAULT_BLOCK_BUFFER_SIZE = 65536;
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private static final EncoderFactory DEFAULT_FACTORY = new DefaultEncoderFactory(null);
    private static final int MAX_BLOCK_BUFFER_SIZE = 1073741824;
    private static final int MIN_BLOCK_BUFFER_SIZE = 64;
    protected int binaryBlockSize = 65536;
    protected int binaryBufferSize = 2048;

    public static EncoderFactory get() {
        return DEFAULT_FACTORY;
    }

    public BinaryEncoder binaryEncoder(OutputStream outputStream, BinaryEncoder binaryEncoder) {
        if (binaryEncoder == null || !binaryEncoder.getClass().equals(BufferedBinaryEncoder.class)) {
            return new BufferedBinaryEncoder(outputStream, this.binaryBufferSize);
        }
        return ((BufferedBinaryEncoder)binaryEncoder).configure(outputStream, this.binaryBufferSize);
    }

    public BinaryEncoder blockingBinaryEncoder(OutputStream outputStream, BinaryEncoder binaryEncoder) {
        if (binaryEncoder == null || !binaryEncoder.getClass().equals(BlockingBinaryEncoder.class)) {
            return new BlockingBinaryEncoder(outputStream, this.binaryBlockSize, 32);
        }
        return ((BlockingBinaryEncoder)binaryEncoder).configure(outputStream, this.binaryBlockSize, 32);
    }

    public EncoderFactory configureBlockSize(int n2) {
        if (n2 < 64) {
            n2 = 64;
        }
        if (n2 > 1073741824) {
            n2 = 1073741824;
        }
        this.binaryBufferSize = n2;
        return this;
    }

    public EncoderFactory configureBufferSize(int n2) {
        if (n2 < 32) {
            n2 = 32;
        }
        if (n2 > 16777216) {
            n2 = 16777216;
        }
        this.binaryBufferSize = n2;
        return this;
    }

    public BinaryEncoder directBinaryEncoder(OutputStream outputStream, BinaryEncoder binaryEncoder) {
        if (binaryEncoder == null || !binaryEncoder.getClass().equals(DirectBinaryEncoder.class)) {
            return new DirectBinaryEncoder(outputStream);
        }
        return ((DirectBinaryEncoder)binaryEncoder).configure(outputStream);
    }

    public int getBlockSize() {
        return this.binaryBlockSize;
    }

    public int getBufferSize() {
        return this.binaryBufferSize;
    }

    public JsonEncoder jsonEncoder(Schema schema, JsonGenerator jsonGenerator) throws IOException {
        return new JsonEncoder(schema, jsonGenerator);
    }

    public JsonEncoder jsonEncoder(Schema schema, OutputStream outputStream) throws IOException {
        return new JsonEncoder(schema, outputStream);
    }

    public ValidatingEncoder validatingEncoder(Schema schema, Encoder encoder) throws IOException {
        return new ValidatingEncoder(schema, encoder);
    }

    private static class DefaultEncoderFactory
    extends EncoderFactory {
        private DefaultEncoderFactory() {
        }

        /* synthetic */ DefaultEncoderFactory(1 var1) {
        }

        @Override
        public EncoderFactory configureBlockSize(int n2) {
            throw new AvroRuntimeException("Default EncoderFactory cannot be configured");
        }

        @Override
        public EncoderFactory configureBufferSize(int n2) {
            throw new AvroRuntimeException("Default EncoderFactory cannot be configured");
        }
    }

}


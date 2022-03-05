/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.util;

public class BufferRecycler {
    public static final int DEFAULT_WRITE_CONCAT_BUFFER_LEN = 2000;
    protected final byte[][] _byteBuffers = new byte[ByteBufferType.values().length][];
    protected final char[][] _charBuffers = new char[CharBufferType.values().length][];

    private final byte[] balloc(int n) {
        return new byte[n];
    }

    private final char[] calloc(int n) {
        return new char[n];
    }

    public final byte[] allocByteBuffer(ByteBufferType byteBufferType) {
        int n = byteBufferType.ordinal();
        byte[] arrby = this._byteBuffers[n];
        if (arrby == null) {
            return BufferRecycler.super.balloc(byteBufferType.size);
        }
        this._byteBuffers[n] = null;
        return arrby;
    }

    public final char[] allocCharBuffer(CharBufferType charBufferType) {
        return this.allocCharBuffer(charBufferType, 0);
    }

    public final char[] allocCharBuffer(CharBufferType charBufferType, int n) {
        int n2;
        char[] arrc;
        if (charBufferType.size > n) {
            n = charBufferType.size;
        }
        if ((arrc = this._charBuffers[n2 = charBufferType.ordinal()]) == null || arrc.length < n) {
            return BufferRecycler.super.calloc(n);
        }
        this._charBuffers[n2] = null;
        return arrc;
    }

    public final void releaseByteBuffer(ByteBufferType byteBufferType, byte[] arrby) {
        this._byteBuffers[byteBufferType.ordinal()] = arrby;
    }

    public final void releaseCharBuffer(CharBufferType charBufferType, char[] arrc) {
        this._charBuffers[charBufferType.ordinal()] = arrc;
    }

    public static final class ByteBufferType
    extends Enum<ByteBufferType> {
        private static final /* synthetic */ ByteBufferType[] $VALUES;
        public static final /* enum */ ByteBufferType READ_IO_BUFFER = new ByteBufferType(4000);
        public static final /* enum */ ByteBufferType WRITE_CONCAT_BUFFER;
        public static final /* enum */ ByteBufferType WRITE_ENCODING_BUFFER;
        private final int size;

        static {
            WRITE_ENCODING_BUFFER = new ByteBufferType(4000);
            WRITE_CONCAT_BUFFER = new ByteBufferType(2000);
            ByteBufferType[] arrbyteBufferType = new ByteBufferType[]{READ_IO_BUFFER, WRITE_ENCODING_BUFFER, WRITE_CONCAT_BUFFER};
            $VALUES = arrbyteBufferType;
        }

        private ByteBufferType(int n2) {
            this.size = n2;
        }

        public static ByteBufferType valueOf(String string2) {
            return (ByteBufferType)Enum.valueOf(ByteBufferType.class, (String)string2);
        }

        public static ByteBufferType[] values() {
            return (ByteBufferType[])$VALUES.clone();
        }
    }

    public static final class CharBufferType
    extends Enum<CharBufferType> {
        private static final /* synthetic */ CharBufferType[] $VALUES;
        public static final /* enum */ CharBufferType CONCAT_BUFFER;
        public static final /* enum */ CharBufferType NAME_COPY_BUFFER;
        public static final /* enum */ CharBufferType TEXT_BUFFER;
        public static final /* enum */ CharBufferType TOKEN_BUFFER;
        private final int size;

        static {
            TOKEN_BUFFER = new CharBufferType(2000);
            CONCAT_BUFFER = new CharBufferType(2000);
            TEXT_BUFFER = new CharBufferType(200);
            NAME_COPY_BUFFER = new CharBufferType(200);
            CharBufferType[] arrcharBufferType = new CharBufferType[]{TOKEN_BUFFER, CONCAT_BUFFER, TEXT_BUFFER, NAME_COPY_BUFFER};
            $VALUES = arrcharBufferType;
        }

        private CharBufferType(int n2) {
            this.size = n2;
        }

        public static CharBufferType valueOf(String string2) {
            return (CharBufferType)Enum.valueOf(CharBufferType.class, (String)string2);
        }

        public static CharBufferType[] values() {
            return (CharBufferType[])$VALUES.clone();
        }
    }

}


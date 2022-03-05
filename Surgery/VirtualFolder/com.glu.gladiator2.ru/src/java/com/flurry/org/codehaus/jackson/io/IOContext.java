/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.io;

import com.flurry.org.codehaus.jackson.JsonEncoding;
import com.flurry.org.codehaus.jackson.util.BufferRecycler;
import com.flurry.org.codehaus.jackson.util.TextBuffer;

public final class IOContext {
    protected final BufferRecycler _bufferRecycler;
    protected char[] _concatCBuffer = null;
    protected JsonEncoding _encoding;
    protected final boolean _managedResource;
    protected char[] _nameCopyBuffer = null;
    protected byte[] _readIOBuffer = null;
    protected final Object _sourceRef;
    protected char[] _tokenCBuffer = null;
    protected byte[] _writeEncodingBuffer = null;

    public IOContext(BufferRecycler bufferRecycler, Object object, boolean bl) {
        this._bufferRecycler = bufferRecycler;
        this._sourceRef = object;
        this._managedResource = bl;
    }

    public final char[] allocConcatBuffer() {
        if (this._concatCBuffer != null) {
            throw new IllegalStateException("Trying to call allocConcatBuffer() second time");
        }
        this._concatCBuffer = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.CONCAT_BUFFER);
        return this._concatCBuffer;
    }

    public final char[] allocNameCopyBuffer(int n) {
        if (this._nameCopyBuffer != null) {
            throw new IllegalStateException("Trying to call allocNameCopyBuffer() second time");
        }
        this._nameCopyBuffer = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.NAME_COPY_BUFFER, n);
        return this._nameCopyBuffer;
    }

    public final byte[] allocReadIOBuffer() {
        if (this._readIOBuffer != null) {
            throw new IllegalStateException("Trying to call allocReadIOBuffer() second time");
        }
        this._readIOBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.READ_IO_BUFFER);
        return this._readIOBuffer;
    }

    public final char[] allocTokenBuffer() {
        if (this._tokenCBuffer != null) {
            throw new IllegalStateException("Trying to call allocTokenBuffer() second time");
        }
        this._tokenCBuffer = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.TOKEN_BUFFER);
        return this._tokenCBuffer;
    }

    public final byte[] allocWriteEncodingBuffer() {
        if (this._writeEncodingBuffer != null) {
            throw new IllegalStateException("Trying to call allocWriteEncodingBuffer() second time");
        }
        this._writeEncodingBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.WRITE_ENCODING_BUFFER);
        return this._writeEncodingBuffer;
    }

    public final TextBuffer constructTextBuffer() {
        return new TextBuffer(this._bufferRecycler);
    }

    public final JsonEncoding getEncoding() {
        return this._encoding;
    }

    public final Object getSourceReference() {
        return this._sourceRef;
    }

    public final boolean isResourceManaged() {
        return this._managedResource;
    }

    public final void releaseConcatBuffer(char[] arrc) {
        if (arrc != null) {
            if (arrc != this._concatCBuffer) {
                throw new IllegalArgumentException("Trying to release buffer not owned by the context");
            }
            this._concatCBuffer = null;
            this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.CONCAT_BUFFER, arrc);
        }
    }

    public final void releaseNameCopyBuffer(char[] arrc) {
        if (arrc != null) {
            if (arrc != this._nameCopyBuffer) {
                throw new IllegalArgumentException("Trying to release buffer not owned by the context");
            }
            this._nameCopyBuffer = null;
            this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.NAME_COPY_BUFFER, arrc);
        }
    }

    public final void releaseReadIOBuffer(byte[] arrby) {
        if (arrby != null) {
            if (arrby != this._readIOBuffer) {
                throw new IllegalArgumentException("Trying to release buffer not owned by the context");
            }
            this._readIOBuffer = null;
            this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.READ_IO_BUFFER, arrby);
        }
    }

    public final void releaseTokenBuffer(char[] arrc) {
        if (arrc != null) {
            if (arrc != this._tokenCBuffer) {
                throw new IllegalArgumentException("Trying to release buffer not owned by the context");
            }
            this._tokenCBuffer = null;
            this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.TOKEN_BUFFER, arrc);
        }
    }

    public final void releaseWriteEncodingBuffer(byte[] arrby) {
        if (arrby != null) {
            if (arrby != this._writeEncodingBuffer) {
                throw new IllegalArgumentException("Trying to release buffer not owned by the context");
            }
            this._writeEncodingBuffer = null;
            this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.WRITE_ENCODING_BUFFER, arrby);
        }
    }

    public void setEncoding(JsonEncoding jsonEncoding) {
        this._encoding = jsonEncoding;
    }
}


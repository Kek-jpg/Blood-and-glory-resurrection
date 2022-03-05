/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.Reader
 *  java.lang.ArrayIndexOutOfBoundsException
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.io;

import com.flurry.org.codehaus.jackson.io.IOContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

abstract class BaseReader
extends Reader {
    protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
    protected static final char NULL_BYTE;
    protected static final char NULL_CHAR;
    protected byte[] _buffer;
    protected final IOContext _context;
    protected InputStream _in;
    protected int _length;
    protected int _ptr;
    protected char[] _tmpBuf = null;

    protected BaseReader(IOContext iOContext, InputStream inputStream, byte[] arrby, int n, int n2) {
        this._context = iOContext;
        this._in = inputStream;
        this._buffer = arrby;
        this._ptr = n;
        this._length = n2;
    }

    public void close() throws IOException {
        InputStream inputStream = this._in;
        if (inputStream != null) {
            this._in = null;
            this.freeBuffers();
            inputStream.close();
        }
    }

    public final void freeBuffers() {
        byte[] arrby = this._buffer;
        if (arrby != null) {
            this._buffer = null;
            this._context.releaseReadIOBuffer(arrby);
        }
    }

    public int read() throws IOException {
        if (this._tmpBuf == null) {
            this._tmpBuf = new char[1];
        }
        if (this.read(this._tmpBuf, 0, 1) < 1) {
            return -1;
        }
        return this._tmpBuf[0];
    }

    protected void reportBounds(char[] arrc, int n, int n2) throws IOException {
        throw new ArrayIndexOutOfBoundsException("read(buf," + n + "," + n2 + "), cbuf[" + arrc.length + "]");
    }

    protected void reportStrangeStream() throws IOException {
        throw new IOException("Strange I/O stream, returned 0 bytes on read");
    }
}


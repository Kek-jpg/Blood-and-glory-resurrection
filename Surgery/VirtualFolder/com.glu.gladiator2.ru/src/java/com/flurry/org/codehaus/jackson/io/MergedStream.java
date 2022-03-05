/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.System
 */
package com.flurry.org.codehaus.jackson.io;

import com.flurry.org.codehaus.jackson.io.IOContext;
import java.io.IOException;
import java.io.InputStream;

public final class MergedStream
extends InputStream {
    byte[] _buffer;
    protected final IOContext _context;
    final int _end;
    final InputStream _in;
    int _ptr;

    public MergedStream(IOContext iOContext, InputStream inputStream, byte[] arrby, int n, int n2) {
        this._context = iOContext;
        this._in = inputStream;
        this._buffer = arrby;
        this._ptr = n;
        this._end = n2;
    }

    private void freeMergedBuffer() {
        byte[] arrby = this._buffer;
        if (arrby != null) {
            this._buffer = null;
            if (this._context != null) {
                this._context.releaseReadIOBuffer(arrby);
            }
        }
    }

    public int available() throws IOException {
        if (this._buffer != null) {
            return this._end - this._ptr;
        }
        return this._in.available();
    }

    public void close() throws IOException {
        this.freeMergedBuffer();
        this._in.close();
    }

    public void mark(int n) {
        if (this._buffer == null) {
            this._in.mark(n);
        }
    }

    public boolean markSupported() {
        return this._buffer == null && this._in.markSupported();
    }

    public int read() throws IOException {
        if (this._buffer != null) {
            byte[] arrby = this._buffer;
            int n = this._ptr;
            this._ptr = n + 1;
            int n2 = 255 & arrby[n];
            if (this._ptr >= this._end) {
                this.freeMergedBuffer();
            }
            return n2;
        }
        return this._in.read();
    }

    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (this._buffer != null) {
            int n3 = this._end - this._ptr;
            if (n2 > n3) {
                n2 = n3;
            }
            System.arraycopy((Object)this._buffer, (int)this._ptr, (Object)arrby, (int)n, (int)n2);
            this._ptr = n2 + this._ptr;
            if (this._ptr >= this._end) {
                MergedStream.super.freeMergedBuffer();
            }
            return n2;
        }
        return this._in.read(arrby, n, n2);
    }

    public void reset() throws IOException {
        if (this._buffer == null) {
            this._in.reset();
        }
    }

    public long skip(long l) throws IOException {
        long l2 = 0L;
        if (this._buffer != null) {
            int n = this._end - this._ptr;
            if ((long)n > l) {
                this._ptr += (int)l;
                return l;
            }
            MergedStream.super.freeMergedBuffer();
            l2 += (long)n;
            l -= (long)n;
        }
        if (l > 0L) {
            l2 += this._in.skip(l);
        }
        return l2;
    }
}


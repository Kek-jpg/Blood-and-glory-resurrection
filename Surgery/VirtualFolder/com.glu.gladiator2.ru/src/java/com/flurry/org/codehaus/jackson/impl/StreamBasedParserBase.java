/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package com.flurry.org.codehaus.jackson.impl;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.impl.JsonParserBase;
import com.flurry.org.codehaus.jackson.io.IOContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Deprecated
public abstract class StreamBasedParserBase
extends JsonParserBase {
    protected boolean _bufferRecyclable;
    protected byte[] _inputBuffer;
    protected InputStream _inputStream;

    protected StreamBasedParserBase(IOContext iOContext, int n, InputStream inputStream, byte[] arrby, int n2, int n3, boolean bl) {
        super(iOContext, n);
        this._inputStream = inputStream;
        this._inputBuffer = arrby;
        this._inputPtr = n2;
        this._inputEnd = n3;
        this._bufferRecyclable = bl;
    }

    @Override
    protected void _closeInput() throws IOException {
        if (this._inputStream != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
                this._inputStream.close();
            }
            this._inputStream = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final boolean _loadToHaveAtLeast(int n) throws IOException {
        if (this._inputStream == null) return false;
        int n2 = this._inputEnd - this._inputPtr;
        if (n2 > 0 && this._inputPtr > 0) {
            this._currInputProcessed += (long)this._inputPtr;
            this._currInputRowStart -= this._inputPtr;
            System.arraycopy((Object)this._inputBuffer, (int)this._inputPtr, (Object)this._inputBuffer, (int)0, (int)n2);
            this._inputEnd = n2;
        } else {
            this._inputEnd = 0;
        }
        this._inputPtr = 0;
        while (this._inputEnd < n) {
            int n3 = this._inputStream.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
            if (n3 < 1) {
                this._closeInput();
                if (n3 != 0) return false;
                throw new IOException("InputStream.read() returned 0 characters when trying to read " + n2 + " bytes");
            }
            this._inputEnd = n3 + this._inputEnd;
        }
        return true;
    }

    @Override
    protected void _releaseBuffers() throws IOException {
        byte[] arrby;
        super._releaseBuffers();
        if (this._bufferRecyclable && (arrby = this._inputBuffer) != null) {
            this._inputBuffer = null;
            this._ioContext.releaseReadIOBuffer(arrby);
        }
    }

    @Override
    public Object getInputSource() {
        return this._inputStream;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected final boolean loadMore() throws IOException {
        this._currInputProcessed += (long)this._inputEnd;
        this._currInputRowStart -= this._inputEnd;
        InputStream inputStream = this._inputStream;
        boolean bl = false;
        if (inputStream == null) return bl;
        int n = this._inputStream.read(this._inputBuffer, 0, this._inputBuffer.length);
        if (n > 0) {
            this._inputPtr = 0;
            this._inputEnd = n;
            return true;
        }
        this._closeInput();
        bl = false;
        if (n != 0) return bl;
        throw new IOException("InputStream.read() returned 0 characters when trying to read " + this._inputBuffer.length + " bytes");
    }

    @Override
    public int releaseBuffered(OutputStream outputStream) throws IOException {
        int n = this._inputEnd - this._inputPtr;
        if (n < 1) {
            return 0;
        }
        int n2 = this._inputPtr;
        outputStream.write(this._inputBuffer, n2, n);
        return n;
    }
}


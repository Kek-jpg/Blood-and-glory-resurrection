/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.Reader
 *  java.io.Writer
 *  java.lang.Character
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.flurry.org.codehaus.jackson.impl;

import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.impl.JsonParserBase;
import com.flurry.org.codehaus.jackson.io.IOContext;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

@Deprecated
public abstract class ReaderBasedParserBase
extends JsonParserBase {
    protected char[] _inputBuffer;
    protected Reader _reader;

    protected ReaderBasedParserBase(IOContext iOContext, int n, Reader reader) {
        super(iOContext, n);
        this._reader = reader;
        this._inputBuffer = iOContext.allocTokenBuffer();
    }

    @Override
    protected void _closeInput() throws IOException {
        if (this._reader != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
                this._reader.close();
            }
            this._reader = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final boolean _matchToken(String string, int n) throws IOException, JsonParseException {
        int n2 = string.length();
        do {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOFInValue();
            }
            if (this._inputBuffer[this._inputPtr] != string.charAt(n)) {
                this._reportInvalidToken(string.substring(0, n), "'null', 'true', 'false' or NaN");
            }
            this._inputPtr = 1 + this._inputPtr;
        } while (++n < n2);
        if (this._inputPtr >= this._inputEnd && !this.loadMore() || !Character.isJavaIdentifierPart((char)this._inputBuffer[this._inputPtr])) {
            return true;
        }
        this._inputPtr = 1 + this._inputPtr;
        this._reportInvalidToken(string.substring(0, n), "'null', 'true', 'false' or NaN");
        return true;
    }

    @Override
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        char[] arrc = this._inputBuffer;
        if (arrc != null) {
            this._inputBuffer = null;
            this._ioContext.releaseTokenBuffer(arrc);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _reportInvalidToken(String string, String string2) throws IOException, JsonParseException {
        StringBuilder stringBuilder = new StringBuilder(string);
        do {
            char c;
            if (this._inputPtr >= this._inputEnd && !this.loadMore() || !Character.isJavaIdentifierPart((char)(c = this._inputBuffer[this._inputPtr]))) {
                this._reportError("Unrecognized token '" + stringBuilder.toString() + "': was expecting ");
                return;
            }
            this._inputPtr = 1 + this._inputPtr;
            stringBuilder.append(c);
        } while (true);
    }

    @Override
    public Object getInputSource() {
        return this._reader;
    }

    protected char getNextChar(String string) throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(string);
        }
        char[] arrc = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        return arrc[n];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected final boolean loadMore() throws IOException {
        this._currInputProcessed += (long)this._inputEnd;
        this._currInputRowStart -= this._inputEnd;
        Reader reader = this._reader;
        boolean bl = false;
        if (reader == null) return bl;
        int n = this._reader.read(this._inputBuffer, 0, this._inputBuffer.length);
        if (n > 0) {
            this._inputPtr = 0;
            this._inputEnd = n;
            return true;
        }
        this._closeInput();
        bl = false;
        if (n != 0) return bl;
        throw new IOException("Reader returned 0 characters when trying to read " + this._inputEnd);
    }

    @Override
    public int releaseBuffered(Writer writer) throws IOException {
        int n = this._inputEnd - this._inputPtr;
        if (n < 1) {
            return 0;
        }
        int n2 = this._inputPtr;
        writer.write(this._inputBuffer, n2, n);
        return n;
    }
}


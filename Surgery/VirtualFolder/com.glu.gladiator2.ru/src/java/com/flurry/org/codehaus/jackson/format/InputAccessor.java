/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.EOFException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.format;

import com.flurry.org.codehaus.jackson.JsonFactory;
import com.flurry.org.codehaus.jackson.format.DataFormatMatcher;
import com.flurry.org.codehaus.jackson.format.MatchStrength;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public interface InputAccessor {
    public boolean hasMoreBytes() throws IOException;

    public byte nextByte() throws IOException;

    public void reset();

    public static class Std
    implements InputAccessor {
        protected final byte[] _buffer;
        protected int _bufferedAmount;
        protected final InputStream _in;
        protected int _ptr;

        public Std(InputStream inputStream, byte[] arrby) {
            this._in = inputStream;
            this._buffer = arrby;
            this._bufferedAmount = 0;
        }

        public Std(byte[] arrby) {
            this._in = null;
            this._buffer = arrby;
            this._bufferedAmount = arrby.length;
        }

        public DataFormatMatcher createMatcher(JsonFactory jsonFactory, MatchStrength matchStrength) {
            return new DataFormatMatcher(this._in, this._buffer, this._bufferedAmount, jsonFactory, matchStrength);
        }

        @Override
        public boolean hasMoreBytes() throws IOException {
            if (this._ptr < this._bufferedAmount) {
                return true;
            }
            int n2 = this._buffer.length - this._ptr;
            if (n2 < 1) {
                return false;
            }
            int n3 = this._in.read(this._buffer, this._ptr, n2);
            if (n3 <= 0) {
                return false;
            }
            this._bufferedAmount = n3 + this._bufferedAmount;
            return true;
        }

        @Override
        public byte nextByte() throws IOException {
            if (this._ptr > -this._bufferedAmount && !this.hasMoreBytes()) {
                throw new EOFException("Could not read more than " + this._ptr + " bytes (max buffer size: " + this._buffer.length + ")");
            }
            byte[] arrby = this._buffer;
            int n2 = this._ptr;
            this._ptr = n2 + 1;
            return arrby[n2];
        }

        @Override
        public void reset() {
            this._ptr = 0;
        }
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.CharConversionException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Integer
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.io;

import com.flurry.org.codehaus.jackson.io.BaseReader;
import com.flurry.org.codehaus.jackson.io.IOContext;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;

public final class UTF32Reader
extends BaseReader {
    final boolean mBigEndian;
    int mByteCount = 0;
    int mCharCount = 0;
    char mSurrogate = '\u0000';

    public UTF32Reader(IOContext iOContext, InputStream inputStream, byte[] arrby, int n, int n2, boolean bl) {
        super(iOContext, inputStream, arrby, n, n2);
        this.mBigEndian = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean loadMore(int n) throws IOException {
        this.mByteCount += this._length - n;
        if (n > 0) {
            if (this._ptr > 0) {
                for (int i = 0; i < n; ++i) {
                    this._buffer[i] = this._buffer[i + this._ptr];
                }
                this._ptr = 0;
            }
            this._length = n;
        } else {
            this._ptr = 0;
            int n2 = this._in.read(this._buffer);
            if (n2 < 1) {
                this._length = 0;
                if (n2 < 0) {
                    this.freeBuffers();
                    return false;
                }
                this.reportStrangeStream();
            }
            this._length = n2;
        }
        while (this._length < 4) {
            int n3 = this._in.read(this._buffer, this._length, this._buffer.length - this._length);
            if (n3 < 1) {
                if (n3 < 0) {
                    this.freeBuffers();
                    UTF32Reader.super.reportUnexpectedEOF(this._length, 4);
                }
                this.reportStrangeStream();
            }
            this._length = n3 + this._length;
        }
        return true;
    }

    private void reportInvalid(int n, int n2, String string) throws IOException {
        int n3 = -1 + (this.mByteCount + this._ptr);
        int n4 = n2 + this.mCharCount;
        throw new CharConversionException("Invalid UTF-32 character 0x" + Integer.toHexString((int)n) + string + " at char #" + n4 + ", byte #" + n3 + ")");
    }

    private void reportUnexpectedEOF(int n, int n2) throws IOException {
        int n3 = n + this.mByteCount;
        int n4 = this.mCharCount;
        throw new CharConversionException("Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + n + ", needed " + n2 + ", at char #" + n4 + ", byte #" + n3 + ")");
    }

    /*
     * Enabled aggressive block sorting
     */
    public int read(char[] arrc, int n, int n2) throws IOException {
        int n3;
        block12 : {
            int n4;
            int n5;
            block15 : {
                block16 : {
                    block13 : {
                        block14 : {
                            if (this._buffer == null) break block13;
                            if (n2 < 1) {
                                return n2;
                            }
                            if (n < 0 || n + n2 > arrc.length) {
                                this.reportBounds(arrc, n, n2);
                            }
                            n4 = n2 + n;
                            if (this.mSurrogate == '\u0000') break block14;
                            n5 = n + 1;
                            arrc[n] = this.mSurrogate;
                            this.mSurrogate = '\u0000';
                            break block15;
                        }
                        int n6 = this._length - this._ptr;
                        if (n6 >= 4 || UTF32Reader.super.loadMore(n6)) break block16;
                    }
                    return -1;
                }
                n5 = n;
            }
            while (n5 < n4) {
                int n7 = this._ptr;
                int n8 = this.mBigEndian ? this._buffer[n7] << 24 | (255 & this._buffer[n7 + 1]) << 16 | (255 & this._buffer[n7 + 2]) << 8 | 255 & this._buffer[n7 + 3] : 255 & this._buffer[n7] | (255 & this._buffer[n7 + 1]) << 8 | (255 & this._buffer[n7 + 2]) << 16 | this._buffer[n7 + 3] << 24;
                this._ptr = 4 + this._ptr;
                if (n8 > 65535) {
                    if (n8 > 1114111) {
                        UTF32Reader.super.reportInvalid(n8, n5 - n, "(above " + Integer.toHexString((int)1114111) + ") ");
                    }
                    int n9 = n8 - 65536;
                    n3 = n5 + 1;
                    arrc[n5] = (char)(55296 + (n9 >> 10));
                    n8 = 56320 | n9 & 1023;
                    if (n3 >= n4) {
                        this.mSurrogate = (char)n8;
                        break block12;
                    }
                } else {
                    n3 = n5;
                }
                n5 = n3 + 1;
                arrc[n3] = (char)n8;
                if (this._ptr < this._length) continue;
                n3 = n5;
                break block12;
            }
            n3 = n5;
        }
        int n10 = n3 - n;
        this.mCharCount = n10 + this.mCharCount;
        return n10;
    }
}


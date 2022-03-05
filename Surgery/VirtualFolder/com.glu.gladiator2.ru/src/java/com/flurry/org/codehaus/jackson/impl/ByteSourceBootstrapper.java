/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.impl.ReaderBasedParser
 *  com.flurry.org.codehaus.jackson.impl.Utf8StreamParser
 *  com.flurry.org.codehaus.jackson.io.IOContext
 *  com.flurry.org.codehaus.jackson.io.MergedStream
 *  com.flurry.org.codehaus.jackson.io.UTF32Reader
 *  com.flurry.org.codehaus.jackson.sym.BytesToNameCanonicalizer
 *  com.flurry.org.codehaus.jackson.sym.CharsToNameCanonicalizer
 *  java.io.ByteArrayInputStream
 *  java.io.CharConversionException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.impl;

import com.flurry.org.codehaus.jackson.JsonEncoding;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.format.InputAccessor;
import com.flurry.org.codehaus.jackson.format.MatchStrength;
import com.flurry.org.codehaus.jackson.impl.ReaderBasedParser;
import com.flurry.org.codehaus.jackson.impl.Utf8StreamParser;
import com.flurry.org.codehaus.jackson.io.IOContext;
import com.flurry.org.codehaus.jackson.io.MergedStream;
import com.flurry.org.codehaus.jackson.io.UTF32Reader;
import com.flurry.org.codehaus.jackson.sym.BytesToNameCanonicalizer;
import com.flurry.org.codehaus.jackson.sym.CharsToNameCanonicalizer;
import java.io.ByteArrayInputStream;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class ByteSourceBootstrapper {
    static final byte UTF8_BOM_1 = -17;
    static final byte UTF8_BOM_2 = -69;
    static final byte UTF8_BOM_3 = -65;
    protected boolean _bigEndian;
    private final boolean _bufferRecyclable;
    protected int _bytesPerChar;
    protected final IOContext _context;
    protected final InputStream _in;
    protected final byte[] _inputBuffer;
    private int _inputEnd;
    protected int _inputProcessed;
    private int _inputPtr;

    public ByteSourceBootstrapper(IOContext iOContext, InputStream inputStream) {
        this._bigEndian = true;
        this._bytesPerChar = 0;
        this._context = iOContext;
        this._in = inputStream;
        this._inputBuffer = iOContext.allocReadIOBuffer();
        this._inputPtr = 0;
        this._inputEnd = 0;
        this._inputProcessed = 0;
        this._bufferRecyclable = true;
    }

    public ByteSourceBootstrapper(IOContext iOContext, byte[] arrby, int n2, int n3) {
        this._bigEndian = true;
        this._bytesPerChar = 0;
        this._context = iOContext;
        this._in = null;
        this._inputBuffer = arrby;
        this._inputPtr = n2;
        this._inputEnd = n2 + n3;
        this._inputProcessed = -n2;
        this._bufferRecyclable = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkUTF16(int n2) {
        if ((65280 & n2) == 0) {
            this._bigEndian = true;
        } else {
            int n3 = n2 & 255;
            boolean bl = false;
            if (n3 != 0) return bl;
            this._bigEndian = false;
        }
        this._bytesPerChar = 2;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkUTF32(int n2) throws IOException {
        if (n2 >> 8 == 0) {
            this._bigEndian = true;
        } else if ((16777215 & n2) == 0) {
            this._bigEndian = false;
        } else if ((-16711681 & n2) == 0) {
            ByteSourceBootstrapper.super.reportWeirdUCS4("3412");
        } else {
            int n3 = -65281 & n2;
            boolean bl = false;
            if (n3 != 0) return bl;
            ByteSourceBootstrapper.super.reportWeirdUCS4("2143");
        }
        this._bytesPerChar = 4;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean handleBOM(int n2) throws IOException {
        switch (n2) {
            case 65279: {
                this._bigEndian = true;
                this._inputPtr = 4 + this._inputPtr;
                this._bytesPerChar = 4;
                return true;
            }
            case -131072: {
                this._inputPtr = 4 + this._inputPtr;
                this._bytesPerChar = 4;
                this._bigEndian = false;
                return true;
            }
            case 65534: {
                ByteSourceBootstrapper.super.reportWeirdUCS4("2143");
            }
            case -16842752: {
                ByteSourceBootstrapper.super.reportWeirdUCS4("3412");
            }
        }
        int n3 = n2 >>> 16;
        if (n3 == 65279) {
            this._inputPtr = 2 + this._inputPtr;
            this._bytesPerChar = 2;
            this._bigEndian = true;
            return true;
        }
        if (n3 == 65534) {
            this._inputPtr = 2 + this._inputPtr;
            this._bytesPerChar = 2;
            this._bigEndian = false;
            return true;
        }
        if (n2 >>> 8 == 15711167) {
            this._inputPtr = 3 + this._inputPtr;
            this._bytesPerChar = 1;
            this._bigEndian = true;
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static MatchStrength hasJSONFormat(InputAccessor inputAccessor) throws IOException {
        int n2;
        if (!inputAccessor.hasMoreBytes()) {
            return MatchStrength.INCONCLUSIVE;
        }
        byte by = inputAccessor.nextByte();
        if (by == -17) {
            if (!inputAccessor.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (inputAccessor.nextByte() != -69) {
                return MatchStrength.NO_MATCH;
            }
            if (!inputAccessor.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (inputAccessor.nextByte() != -65) {
                return MatchStrength.NO_MATCH;
            }
            if (!inputAccessor.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            by = inputAccessor.nextByte();
        }
        if ((n2 = ByteSourceBootstrapper.skipSpace(inputAccessor, by)) < 0) {
            return MatchStrength.INCONCLUSIVE;
        }
        if (n2 == 123) {
            int n3 = ByteSourceBootstrapper.skipSpace(inputAccessor);
            if (n3 < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (n3 == 34) return MatchStrength.SOLID_MATCH;
            if (n3 != 125) return MatchStrength.NO_MATCH;
            return MatchStrength.SOLID_MATCH;
        }
        if (n2 == 91) {
            int n4 = ByteSourceBootstrapper.skipSpace(inputAccessor);
            if (n4 < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (n4 == 93) return MatchStrength.SOLID_MATCH;
            if (n4 != 91) return MatchStrength.SOLID_MATCH;
            return MatchStrength.SOLID_MATCH;
        }
        MatchStrength matchStrength = MatchStrength.WEAK_MATCH;
        if (n2 == 34) return matchStrength;
        if (n2 <= 57) {
            if (n2 >= 48) return matchStrength;
        }
        if (n2 == 45) {
            int n5 = ByteSourceBootstrapper.skipSpace(inputAccessor);
            if (n5 < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (n5 > 57) return MatchStrength.NO_MATCH;
            if (n5 >= 48) return matchStrength;
            return MatchStrength.NO_MATCH;
        }
        if (n2 == 110) {
            return ByteSourceBootstrapper.tryMatch(inputAccessor, "ull", matchStrength);
        }
        if (n2 == 116) {
            return ByteSourceBootstrapper.tryMatch(inputAccessor, "rue", matchStrength);
        }
        if (n2 != 102) return MatchStrength.NO_MATCH;
        return ByteSourceBootstrapper.tryMatch(inputAccessor, "alse", matchStrength);
    }

    private void reportWeirdUCS4(String string) throws IOException {
        throw new CharConversionException("Unsupported UCS-4 endianness (" + string + ") detected");
    }

    private static final int skipSpace(InputAccessor inputAccessor) throws IOException {
        if (!inputAccessor.hasMoreBytes()) {
            return -1;
        }
        return ByteSourceBootstrapper.skipSpace(inputAccessor, inputAccessor.nextByte());
    }

    private static final int skipSpace(InputAccessor inputAccessor, byte by) throws IOException {
        int n2;
        while ((n2 = by & 255) == 32 || n2 == 13 || n2 == 10 || n2 == 9) {
            if (!inputAccessor.hasMoreBytes()) {
                return -1;
            }
            by = inputAccessor.nextByte();
            by & 255;
        }
        return n2;
    }

    private static final MatchStrength tryMatch(InputAccessor inputAccessor, String string, MatchStrength matchStrength) throws IOException {
        int n2 = 0;
        int n3 = string.length();
        do {
            block6 : {
                block5 : {
                    if (n2 >= n3) break block5;
                    if (inputAccessor.hasMoreBytes()) break block6;
                    matchStrength = MatchStrength.INCONCLUSIVE;
                }
                return matchStrength;
            }
            if (inputAccessor.nextByte() != string.charAt(n2)) {
                return MatchStrength.NO_MATCH;
            }
            ++n2;
        } while (true);
    }

    public JsonParser constructParser(int n2, ObjectCodec objectCodec, BytesToNameCanonicalizer bytesToNameCanonicalizer, CharsToNameCanonicalizer charsToNameCanonicalizer) throws IOException, JsonParseException {
        JsonEncoding jsonEncoding = this.detectEncoding();
        boolean bl = JsonParser.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(n2);
        boolean bl2 = JsonParser.Feature.INTERN_FIELD_NAMES.enabledIn(n2);
        if (jsonEncoding == JsonEncoding.UTF8 && bl) {
            BytesToNameCanonicalizer bytesToNameCanonicalizer2 = bytesToNameCanonicalizer.makeChild(bl, bl2);
            return new Utf8StreamParser(this._context, n2, this._in, objectCodec, bytesToNameCanonicalizer2, this._inputBuffer, this._inputPtr, this._inputEnd, this._bufferRecyclable);
        }
        return new ReaderBasedParser(this._context, n2, this.constructReader(), objectCodec, charsToNameCanonicalizer.makeChild(bl, bl2));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Reader constructReader() throws IOException {
        InputStream inputStream;
        JsonEncoding jsonEncoding = this._context.getEncoding();
        switch (1.$SwitchMap$org$codehaus$jackson$JsonEncoding[jsonEncoding.ordinal()]) {
            default: {
                throw new RuntimeException("Internal error");
            }
            case 1: 
            case 2: {
                return new UTF32Reader(this._context, this._in, this._inputBuffer, this._inputPtr, this._inputEnd, this._context.getEncoding().isBigEndian());
            }
            case 3: 
            case 4: 
            case 5: 
        }
        InputStream inputStream2 = this._in;
        if (inputStream2 == null) {
            inputStream = new ByteArrayInputStream(this._inputBuffer, this._inputPtr, this._inputEnd);
            do {
                return new InputStreamReader(inputStream, jsonEncoding.getJavaName());
                break;
            } while (true);
        }
        if (this._inputPtr < this._inputEnd) {
            inputStream = new MergedStream(this._context, inputStream2, this._inputBuffer, this._inputPtr, this._inputEnd);
            return new InputStreamReader(inputStream, jsonEncoding.getJavaName());
        }
        inputStream = inputStream2;
        return new InputStreamReader(inputStream, jsonEncoding.getJavaName());
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonEncoding detectEncoding() throws IOException, JsonParseException {
        JsonEncoding jsonEncoding;
        boolean bl;
        if (this.ensureLoaded(4)) {
            int n2 = this._inputBuffer[this._inputPtr] << 24 | (255 & this._inputBuffer[1 + this._inputPtr]) << 16 | (255 & this._inputBuffer[2 + this._inputPtr]) << 8 | 255 & this._inputBuffer[3 + this._inputPtr];
            if (this.handleBOM(n2)) {
                bl = true;
            } else if (this.checkUTF32(n2)) {
                bl = true;
            } else {
                boolean bl2 = this.checkUTF16(n2 >>> 16);
                bl = false;
                if (bl2) {
                    bl = true;
                }
            }
        } else {
            boolean bl3 = this.ensureLoaded(2);
            bl = false;
            if (bl3) {
                boolean bl4 = this.checkUTF16((255 & this._inputBuffer[this._inputPtr]) << 8 | 255 & this._inputBuffer[1 + this._inputPtr]);
                bl = false;
                if (bl4) {
                    bl = true;
                }
            }
        }
        if (!bl) {
            jsonEncoding = JsonEncoding.UTF8;
        } else {
            switch (this._bytesPerChar) {
                default: {
                    throw new RuntimeException("Internal error");
                }
                case 1: {
                    jsonEncoding = JsonEncoding.UTF8;
                    break;
                }
                case 2: {
                    if (this._bigEndian) {
                        jsonEncoding = JsonEncoding.UTF16_BE;
                        break;
                    }
                    jsonEncoding = JsonEncoding.UTF16_LE;
                    break;
                }
                case 4: {
                    jsonEncoding = this._bigEndian ? JsonEncoding.UTF32_BE : JsonEncoding.UTF32_LE;
                }
            }
        }
        this._context.setEncoding(jsonEncoding);
        return jsonEncoding;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean ensureLoaded(int n2) throws IOException {
        int n3 = 1;
        int n4 = this._inputEnd - this._inputPtr;
        while (n4 < n2) {
            int n5 = this._in == null ? -1 : this._in.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
            if (n5 < n3) {
                return (boolean)0;
            }
            this._inputEnd = n5 + this._inputEnd;
            n4 += n5;
        }
        return (boolean)n3;
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.math.BigDecimal
 *  java.math.BigInteger
 */
package com.flurry.org.codehaus.jackson.impl;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.PrettyPrinter;
import com.flurry.org.codehaus.jackson.SerializableString;
import com.flurry.org.codehaus.jackson.impl.JsonGeneratorBase;
import com.flurry.org.codehaus.jackson.impl.JsonWriteContext;
import com.flurry.org.codehaus.jackson.io.CharacterEscapes;
import com.flurry.org.codehaus.jackson.io.IOContext;
import com.flurry.org.codehaus.jackson.io.NumberOutput;
import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.util.CharTypes;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Utf8Generator
extends JsonGeneratorBase {
    private static final byte BYTE_0 = 48;
    private static final byte BYTE_BACKSLASH = 92;
    private static final byte BYTE_COLON = 58;
    private static final byte BYTE_COMMA = 44;
    private static final byte BYTE_LBRACKET = 91;
    private static final byte BYTE_LCURLY = 123;
    private static final byte BYTE_QUOTE = 34;
    private static final byte BYTE_RBRACKET = 93;
    private static final byte BYTE_RCURLY = 125;
    private static final byte BYTE_SPACE = 32;
    private static final byte BYTE_u = 117;
    private static final byte[] FALSE_BYTES;
    static final byte[] HEX_CHARS;
    private static final int MAX_BYTES_TO_BUFFER = 512;
    private static final byte[] NULL_BYTES;
    protected static final int SURR1_FIRST = 55296;
    protected static final int SURR1_LAST = 56319;
    protected static final int SURR2_FIRST = 56320;
    protected static final int SURR2_LAST = 57343;
    private static final byte[] TRUE_BYTES;
    protected static final int[] sOutputEscapes;
    protected boolean _bufferRecyclable;
    protected char[] _charBuffer;
    protected final int _charBufferLength;
    protected CharacterEscapes _characterEscapes;
    protected byte[] _entityBuffer;
    protected final IOContext _ioContext;
    protected int _maximumNonEscapedChar;
    protected byte[] _outputBuffer;
    protected final int _outputEnd;
    protected int[] _outputEscapes;
    protected final int _outputMaxContiguous;
    protected final OutputStream _outputStream;
    protected int _outputTail;

    static {
        HEX_CHARS = CharTypes.copyHexBytes();
        NULL_BYTES = new byte[]{110, 117, 108, 108};
        TRUE_BYTES = new byte[]{116, 114, 117, 101};
        FALSE_BYTES = new byte[]{102, 97, 108, 115, 101};
        sOutputEscapes = CharTypes.get7BitOutputEscapes();
    }

    public Utf8Generator(IOContext iOContext, int n, ObjectCodec objectCodec, OutputStream outputStream) {
        super(n, objectCodec);
        this._outputEscapes = sOutputEscapes;
        this._outputTail = 0;
        this._ioContext = iOContext;
        this._outputStream = outputStream;
        this._bufferRecyclable = true;
        this._outputBuffer = iOContext.allocWriteEncodingBuffer();
        this._outputEnd = this._outputBuffer.length;
        this._outputMaxContiguous = this._outputEnd >> 3;
        this._charBuffer = iOContext.allocConcatBuffer();
        this._charBufferLength = this._charBuffer.length;
        if (this.isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
            this.setHighestNonEscapedChar(127);
        }
    }

    public Utf8Generator(IOContext iOContext, int n, ObjectCodec objectCodec, OutputStream outputStream, byte[] arrby, int n2, boolean bl) {
        super(n, objectCodec);
        this._outputEscapes = sOutputEscapes;
        this._outputTail = 0;
        this._ioContext = iOContext;
        this._outputStream = outputStream;
        this._bufferRecyclable = bl;
        this._outputTail = n2;
        this._outputBuffer = arrby;
        this._outputEnd = this._outputBuffer.length;
        this._outputMaxContiguous = this._outputEnd >> 3;
        this._charBuffer = iOContext.allocConcatBuffer();
        this._charBufferLength = this._charBuffer.length;
        if (this.isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
            this.setHighestNonEscapedChar(127);
        }
    }

    private int _handleLongCustomEscape(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws IOException, JsonGenerationException {
        int n4 = arrby2.length;
        if (n + n4 > n2) {
            this._outputTail = n;
            this._flushBuffer();
            int n5 = this._outputTail;
            if (n4 > arrby.length) {
                this._outputStream.write(arrby2, 0, n4);
                return n5;
            }
            System.arraycopy((Object)arrby2, (int)0, (Object)arrby, (int)n5, (int)n4);
            n = n5 + n4;
        }
        if (n + n3 * 6 > n2) {
            this._flushBuffer();
            return this._outputTail;
        }
        return n;
    }

    private final int _outputMultiByteChar(int n, int n2) throws IOException {
        byte[] arrby = this._outputBuffer;
        if (n >= 55296 && n <= 57343) {
            int n3 = n2 + 1;
            arrby[n2] = 92;
            int n4 = n3 + 1;
            arrby[n3] = 117;
            int n5 = n4 + 1;
            arrby[n4] = HEX_CHARS[15 & n >> 12];
            int n6 = n5 + 1;
            arrby[n5] = HEX_CHARS[15 & n >> 8];
            int n7 = n6 + 1;
            arrby[n6] = HEX_CHARS[15 & n >> 4];
            int n8 = n7 + 1;
            arrby[n7] = HEX_CHARS[n & 15];
            return n8;
        }
        int n9 = n2 + 1;
        arrby[n2] = (byte)(224 | n >> 12);
        int n10 = n9 + 1;
        arrby[n9] = (byte)(128 | 63 & n >> 6);
        int n11 = n10 + 1;
        arrby[n10] = (byte)(128 | n & 63);
        return n11;
    }

    private final int _outputRawMultiByteChar(int n, char[] arrc, int n2, int n3) throws IOException {
        if (n >= 55296 && n <= 57343) {
            if (n2 >= n3) {
                this._reportError("Split surrogate on writeRaw() input (last character)");
            }
            this._outputSurrogates(n, arrc[n2]);
            return n2 + 1;
        }
        byte[] arrby = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrby[n4] = (byte)(224 | n >> 12);
        int n5 = this._outputTail;
        this._outputTail = n5 + 1;
        arrby[n5] = (byte)(128 | 63 & n >> 6);
        int n6 = this._outputTail;
        this._outputTail = n6 + 1;
        arrby[n6] = (byte)(128 | n & 63);
        return n2;
    }

    private final void _writeBytes(byte[] arrby) throws IOException {
        int n = arrby.length;
        if (n + this._outputTail > this._outputEnd) {
            this._flushBuffer();
            if (n > 512) {
                this._outputStream.write(arrby, 0, n);
                return;
            }
        }
        System.arraycopy((Object)arrby, (int)0, (Object)this._outputBuffer, (int)this._outputTail, (int)n);
        this._outputTail = n + this._outputTail;
    }

    private final void _writeBytes(byte[] arrby, int n, int n2) throws IOException {
        if (n2 + this._outputTail > this._outputEnd) {
            this._flushBuffer();
            if (n2 > 512) {
                this._outputStream.write(arrby, n, n2);
                return;
            }
        }
        System.arraycopy((Object)arrby, (int)n, (Object)this._outputBuffer, (int)this._outputTail, (int)n2);
        this._outputTail = n2 + this._outputTail;
    }

    private int _writeCustomEscape(byte[] arrby, int n, SerializableString serializableString, int n2) throws IOException, JsonGenerationException {
        byte[] arrby2 = serializableString.asUnquotedUTF8();
        int n3 = arrby2.length;
        if (n3 > 6) {
            return Utf8Generator.super._handleLongCustomEscape(arrby, n, this._outputEnd, arrby2, n2);
        }
        System.arraycopy((Object)arrby2, (int)0, (Object)arrby, (int)n, (int)n3);
        return n + n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeCustomStringSegment2(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        if (this._outputTail + 6 * (n2 - n) > this._outputEnd) {
            this._flushBuffer();
        }
        int n3 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        char c = this._maximumNonEscapedChar <= 0 ? (char)'\uffff' : this._maximumNonEscapedChar;
        CharacterEscapes characterEscapes = this._characterEscapes;
        int n4 = n3;
        int n5 = n;
        do {
            int n6;
            if (n5 >= n2) {
                this._outputTail = n4;
                return;
            }
            int n7 = n5 + 1;
            char c2 = arrc[n5];
            if (c2 <= '') {
                if (arrn[c2] == 0) {
                    int n8 = n4 + 1;
                    arrby[n4] = (byte)c2;
                    n4 = n8;
                    n5 = n7;
                    continue;
                }
                int n9 = arrn[c2];
                if (n9 > 0) {
                    int n10 = n4 + 1;
                    arrby[n4] = 92;
                    n4 = n10 + 1;
                    arrby[n10] = (byte)n9;
                    n5 = n7;
                    continue;
                }
                if (n9 == -2) {
                    SerializableString serializableString = characterEscapes.getEscapeSequence(c2);
                    if (serializableString == null) {
                        throw new JsonGenerationException("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString((int)c2) + ", although was supposed to have one");
                    }
                    n4 = Utf8Generator.super._writeCustomEscape(arrby, n4, serializableString, n2 - n7);
                    n5 = n7;
                    continue;
                }
                n4 = Utf8Generator.super._writeGenericEscape(c2, n4);
                n5 = n7;
                continue;
            }
            if (c2 > c) {
                n4 = Utf8Generator.super._writeGenericEscape(c2, n4);
                n5 = n7;
                continue;
            }
            SerializableString serializableString = characterEscapes.getEscapeSequence(c2);
            if (serializableString != null) {
                n4 = Utf8Generator.super._writeCustomEscape(arrby, n4, serializableString, n2 - n7);
                n5 = n7;
                continue;
            }
            if (c2 <= '\u07ff') {
                int n11 = n4 + 1;
                arrby[n4] = (byte)(192 | c2 >> 6);
                int n12 = n11 + 1;
                arrby[n11] = (byte)(128 | c2 & 63);
                n6 = n12;
            } else {
                n6 = Utf8Generator.super._outputMultiByteChar(c2, n4);
            }
            n4 = n6;
            n5 = n7;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int _writeGenericEscape(int n, int n2) throws IOException {
        int n3;
        byte[] arrby = this._outputBuffer;
        int n4 = n2 + 1;
        arrby[n2] = 92;
        int n5 = n4 + 1;
        arrby[n4] = 117;
        if (n > 255) {
            int n6 = 255 & n >> 8;
            int n7 = n5 + 1;
            arrby[n5] = HEX_CHARS[n6 >> 4];
            n3 = n7 + 1;
            arrby[n7] = HEX_CHARS[n6 & 15];
            n &= 255;
        } else {
            int n8 = n5 + 1;
            arrby[n5] = 48;
            n3 = n8 + 1;
            arrby[n8] = 48;
        }
        int n9 = n3 + 1;
        arrby[n3] = HEX_CHARS[n >> 4];
        int n10 = n9 + 1;
        arrby[n9] = HEX_CHARS[n & 15];
        return n10;
    }

    private final void _writeLongString(String string) throws IOException, JsonGenerationException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = 34;
        Utf8Generator.super._writeStringSegments(string);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby2[n2] = 34;
    }

    private final void _writeLongString(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby[n3] = 34;
        Utf8Generator.super._writeStringSegments(this._charBuffer, 0, n2);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrby2[n4] = 34;
    }

    private final void _writeNull() throws IOException {
        if (4 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        System.arraycopy((Object)NULL_BYTES, (int)0, (Object)this._outputBuffer, (int)this._outputTail, (int)4);
        this._outputTail = 4 + this._outputTail;
    }

    private final void _writeQuotedInt(int n) throws IOException {
        if (13 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 34;
        this._outputTail = NumberOutput.outputInt(n, this._outputBuffer, this._outputTail);
        byte[] arrby2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby2[n3] = 34;
    }

    private final void _writeQuotedLong(long l) throws IOException {
        if (23 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = 34;
        this._outputTail = NumberOutput.outputLong(l, this._outputBuffer, this._outputTail);
        byte[] arrby2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby2[n2] = 34;
    }

    private final void _writeQuotedRaw(Object object) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = 34;
        this.writeRaw(object.toString());
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby2[n2] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeSegmentedRaw(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        int n3 = this._outputEnd;
        byte[] arrby = this._outputBuffer;
        block0 : while (n < n2) {
            do {
                char c;
                if ((c = arrc[n]) >= '') {
                    if (3 + this._outputTail >= this._outputEnd) {
                        this._flushBuffer();
                    }
                    int n4 = n + 1;
                    char c2 = arrc[n];
                    if (c2 < '\u0800') {
                        int n5 = this._outputTail;
                        this._outputTail = n5 + 1;
                        arrby[n5] = (byte)(192 | c2 >> 6);
                        int n6 = this._outputTail;
                        this._outputTail = n6 + 1;
                        arrby[n6] = (byte)(128 | c2 & 63);
                    } else {
                        Utf8Generator.super._outputRawMultiByteChar(c2, arrc, n4, n2);
                    }
                    n = n4;
                    continue block0;
                }
                if (this._outputTail >= n3) {
                    this._flushBuffer();
                }
                int n7 = this._outputTail;
                this._outputTail = n7 + 1;
                arrby[n7] = (byte)c;
            } while (++n < n2);
        }
    }

    private final void _writeStringSegment(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        int n3 = n2 + n;
        int n4 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n5 = n4;
        do {
            char c;
            if (n >= n3 || (c = arrc[n]) > '' || arrn[c] != 0) {
                this._outputTail = n5;
                if (n < n3) {
                    if (this._characterEscapes == null) break;
                    Utf8Generator.super._writeCustomStringSegment2(arrc, n, n3);
                }
                return;
            }
            int n6 = n5 + 1;
            arrby[n5] = (byte)c;
            ++n;
            n5 = n6;
        } while (true);
        if (this._maximumNonEscapedChar == 0) {
            Utf8Generator.super._writeStringSegment2(arrc, n, n3);
            return;
        }
        Utf8Generator.super._writeStringSegmentASCII2(arrc, n, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeStringSegment2(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        if (this._outputTail + 6 * (n2 - n) > this._outputEnd) {
            this._flushBuffer();
        }
        int n3 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n4 = n3;
        int n5 = n;
        do {
            int n6;
            if (n5 >= n2) {
                this._outputTail = n4;
                return;
            }
            int n7 = n5 + 1;
            char c = arrc[n5];
            if (c <= '') {
                if (arrn[c] == 0) {
                    int n8 = n4 + 1;
                    arrby[n4] = (byte)c;
                    n4 = n8;
                    n5 = n7;
                    continue;
                }
                int n9 = arrn[c];
                if (n9 > 0) {
                    int n10 = n4 + 1;
                    arrby[n4] = 92;
                    n4 = n10 + 1;
                    arrby[n10] = (byte)n9;
                    n5 = n7;
                    continue;
                }
                n4 = Utf8Generator.super._writeGenericEscape(c, n4);
                n5 = n7;
                continue;
            }
            if (c <= '\u07ff') {
                int n11 = n4 + 1;
                arrby[n4] = (byte)(192 | c >> 6);
                int n12 = n11 + 1;
                arrby[n11] = (byte)(128 | c & 63);
                n6 = n12;
            } else {
                n6 = Utf8Generator.super._outputMultiByteChar(c, n4);
            }
            n4 = n6;
            n5 = n7;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeStringSegmentASCII2(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        if (this._outputTail + 6 * (n2 - n) > this._outputEnd) {
            this._flushBuffer();
        }
        int n3 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n4 = this._maximumNonEscapedChar;
        int n5 = n3;
        int n6 = n;
        do {
            int n7;
            if (n6 >= n2) {
                this._outputTail = n5;
                return;
            }
            int n8 = n6 + 1;
            char c = arrc[n6];
            if (c <= '') {
                if (arrn[c] == 0) {
                    int n9 = n5 + 1;
                    arrby[n5] = (byte)c;
                    n5 = n9;
                    n6 = n8;
                    continue;
                }
                int n10 = arrn[c];
                if (n10 > 0) {
                    int n11 = n5 + 1;
                    arrby[n5] = 92;
                    n5 = n11 + 1;
                    arrby[n11] = (byte)n10;
                    n6 = n8;
                    continue;
                }
                n5 = Utf8Generator.super._writeGenericEscape(c, n5);
                n6 = n8;
                continue;
            }
            if (c > n4) {
                n5 = Utf8Generator.super._writeGenericEscape(c, n5);
                n6 = n8;
                continue;
            }
            if (c <= '\u07ff') {
                int n12 = n5 + 1;
                arrby[n5] = (byte)(192 | c >> 6);
                int n13 = n12 + 1;
                arrby[n12] = (byte)(128 | c & 63);
                n7 = n13;
            } else {
                n7 = Utf8Generator.super._outputMultiByteChar(c, n5);
            }
            n5 = n7;
            n6 = n8;
        } while (true);
    }

    private final void _writeStringSegments(String string) throws IOException, JsonGenerationException {
        int n;
        int n2 = 0;
        char[] arrc = this._charBuffer;
        for (int i = string.length(); i > 0; i -= n) {
            n = Math.min((int)this._outputMaxContiguous, (int)i);
            string.getChars(n2, n2 + n, arrc, 0);
            if (n + this._outputTail > this._outputEnd) {
                this._flushBuffer();
            }
            Utf8Generator.super._writeStringSegment(arrc, 0, n);
            n2 += n;
        }
    }

    private final void _writeStringSegments(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        int n3;
        do {
            if ((n3 = Math.min((int)this._outputMaxContiguous, (int)n2)) + this._outputTail > this._outputEnd) {
                this._flushBuffer();
            }
            Utf8Generator.super._writeStringSegment(arrc, n, n3);
            n += n3;
        } while ((n2 -= n3) > 0);
    }

    private final void _writeUTF8Segment(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        int[] arrn = this._outputEscapes;
        int n3 = n + n2;
        int n4 = n;
        while (n4 < n3) {
            int n5 = n4 + 1;
            byte by = arrby[n4];
            if (by >= 0 && arrn[by] != 0) {
                Utf8Generator.super._writeUTF8Segment2(arrby, n, n2);
                return;
            }
            n4 = n5;
        }
        if (n2 + this._outputTail > this._outputEnd) {
            this._flushBuffer();
        }
        System.arraycopy((Object)arrby, (int)n, (Object)this._outputBuffer, (int)this._outputTail, (int)n2);
        this._outputTail = n2 + this._outputTail;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeUTF8Segment2(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        int n3 = this._outputTail;
        if (n3 + n2 * 6 > this._outputEnd) {
            this._flushBuffer();
            n3 = this._outputTail;
        }
        byte[] arrby2 = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n4 = n2 + n;
        int n5 = n3;
        int n6 = n;
        do {
            int n7;
            if (n6 >= n4) {
                this._outputTail = n5;
                return;
            }
            int n8 = n6 + 1;
            byte by = arrby[n6];
            if (by < 0 || arrn[by] == 0) {
                int n9 = n5 + 1;
                arrby2[n5] = by;
                n5 = n9;
                n6 = n8;
                continue;
            }
            int n10 = arrn[by];
            if (n10 > 0) {
                int n11 = n5 + 1;
                arrby2[n5] = 92;
                int n12 = n11 + 1;
                arrby2[n11] = (byte)n10;
                n7 = n12;
            } else {
                n7 = Utf8Generator.super._writeGenericEscape(by, n5);
            }
            n5 = n7;
            n6 = n8;
        } while (true);
    }

    private final void _writeUTF8Segments(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        int n3;
        do {
            n3 = Math.min((int)this._outputMaxContiguous, (int)n2);
            Utf8Generator.super._writeUTF8Segment(arrby, n, n3);
            n += n3;
        } while ((n2 -= n3) > 0);
    }

    protected final int _decodeSurrogate(int n, int n2) throws IOException {
        if (n2 < 56320 || n2 > 57343) {
            this._reportError("Incomplete surrogate pair: first char 0x" + Integer.toHexString((int)n) + ", second 0x" + Integer.toHexString((int)n2));
        }
        return 65536 + (n - 55296 << 10) + (n2 - 56320);
    }

    protected final void _flushBuffer() throws IOException {
        int n = this._outputTail;
        if (n > 0) {
            this._outputTail = 0;
            this._outputStream.write(this._outputBuffer, 0, n);
        }
    }

    protected final void _outputSurrogates(int n, int n2) throws IOException {
        int n3 = this._decodeSurrogate(n, n2);
        if (4 + this._outputTail > this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrby[n4] = (byte)(240 | n3 >> 18);
        int n5 = this._outputTail;
        this._outputTail = n5 + 1;
        arrby[n5] = (byte)(128 | 63 & n3 >> 12);
        int n6 = this._outputTail;
        this._outputTail = n6 + 1;
        arrby[n6] = (byte)(128 | 63 & n3 >> 6);
        int n7 = this._outputTail;
        this._outputTail = n7 + 1;
        arrby[n7] = (byte)(128 | n3 & 63);
    }

    @Override
    protected void _releaseBuffers() {
        char[] arrc;
        byte[] arrby = this._outputBuffer;
        if (arrby != null && this._bufferRecyclable) {
            this._outputBuffer = null;
            this._ioContext.releaseWriteEncodingBuffer(arrby);
        }
        if ((arrc = this._charBuffer) != null) {
            this._charBuffer = null;
            this._ioContext.releaseConcatBuffer(arrc);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _verifyPrettyValueWrite(String string, int n) throws IOException, JsonGenerationException {
        switch (n) {
            default: {
                this._cantHappen();
                return;
            }
            case 1: {
                this._cfgPrettyPrinter.writeArrayValueSeparator((JsonGenerator)this);
                return;
            }
            case 2: {
                this._cfgPrettyPrinter.writeObjectFieldValueSeparator((JsonGenerator)this);
                return;
            }
            case 3: {
                this._cfgPrettyPrinter.writeRootValueSeparator((JsonGenerator)this);
                return;
            }
            case 0: {
                if (this._writeContext.inArray()) {
                    this._cfgPrettyPrinter.beforeArrayValues((JsonGenerator)this);
                    return;
                }
                if (!this._writeContext.inObject()) return;
                this._cfgPrettyPrinter.beforeObjectEntries((JsonGenerator)this);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected final void _verifyValueWrite(String string) throws IOException, JsonGenerationException {
        int n;
        int n2 = this._writeContext.writeValue();
        if (n2 == 5) {
            this._reportError("Can not " + string + ", expecting field name");
        }
        if (this._cfgPrettyPrinter != null) {
            this._verifyPrettyValueWrite(string, n2);
            return;
        }
        switch (n2) {
            default: {
                return;
            }
            case 1: {
                n = 44;
                break;
            }
            case 2: {
                n = 58;
                break;
            }
            case 3: {
                n = 32;
            }
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail] = n;
        this._outputTail = 1 + this._outputTail;
    }

    protected void _writeBinary(Base64Variant base64Variant, byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        int n3 = n2 - 3;
        int n4 = -6 + this._outputEnd;
        int n5 = base64Variant.getMaxLineLength() >> 2;
        int n6 = n;
        while (n6 <= n3) {
            if (this._outputTail > n4) {
                this._flushBuffer();
            }
            int n7 = n6 + 1;
            int n8 = arrby[n6] << 8;
            int n9 = n7 + 1;
            int n10 = (n8 | 255 & arrby[n7]) << 8;
            int n11 = n9 + 1;
            this._outputTail = base64Variant.encodeBase64Chunk(n10 | 255 & arrby[n9], this._outputBuffer, this._outputTail);
            if (--n5 <= 0) {
                byte[] arrby2 = this._outputBuffer;
                int n12 = this._outputTail;
                this._outputTail = n12 + 1;
                arrby2[n12] = 92;
                byte[] arrby3 = this._outputBuffer;
                int n13 = this._outputTail;
                this._outputTail = n13 + 1;
                arrby3[n13] = 110;
                n5 = base64Variant.getMaxLineLength() >> 2;
            }
            n6 = n11;
        }
        int n14 = n2 - n6;
        if (n14 > 0) {
            if (this._outputTail > n4) {
                this._flushBuffer();
            }
            int n15 = n6 + 1;
            int n16 = arrby[n6] << 16;
            if (n14 == 2) {
                n15 + 1;
                n16 |= (255 & arrby[n15]) << 8;
            }
            this._outputTail = base64Variant.encodeBase64Partial(n16, n14, this._outputBuffer, this._outputTail);
            return;
        }
    }

    protected final void _writeFieldName(SerializableString serializableString) throws IOException, JsonGenerationException {
        byte[] arrby = serializableString.asQuotedUTF8();
        if (!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            Utf8Generator.super._writeBytes(arrby);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby2[n] = 34;
        int n2 = arrby.length;
        if (1 + (n2 + this._outputTail) < this._outputEnd) {
            System.arraycopy((Object)arrby, (int)0, (Object)this._outputBuffer, (int)this._outputTail, (int)n2);
            this._outputTail = n2 + this._outputTail;
            byte[] arrby3 = this._outputBuffer;
            int n3 = this._outputTail;
            this._outputTail = n3 + 1;
            arrby3[n3] = 34;
            return;
        }
        Utf8Generator.super._writeBytes(arrby);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby4 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrby4[n4] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _writeFieldName(String string) throws IOException, JsonGenerationException {
        if (!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            Utf8Generator.super._writeStringSegments(string);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = 34;
        int n2 = string.length();
        if (n2 <= this._charBufferLength) {
            string.getChars(0, n2, this._charBuffer, 0);
            if (n2 <= this._outputMaxContiguous) {
                if (n2 + this._outputTail > this._outputEnd) {
                    this._flushBuffer();
                }
                Utf8Generator.super._writeStringSegment(this._charBuffer, 0, n2);
            } else {
                Utf8Generator.super._writeStringSegments(this._charBuffer, 0, n2);
            }
        } else {
            Utf8Generator.super._writeStringSegments(string);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby2[n3] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _writePPFieldName(SerializableString serializableString, boolean bl) throws IOException, JsonGenerationException {
        boolean bl2;
        if (bl) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator((JsonGenerator)this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries((JsonGenerator)this);
        }
        if (bl2 = this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            byte[] arrby = this._outputBuffer;
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrby[n] = 34;
        }
        Utf8Generator.super._writeBytes(serializableString.asQuotedUTF8());
        if (bl2) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            byte[] arrby = this._outputBuffer;
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrby[n] = 34;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _writePPFieldName(String string, boolean bl) throws IOException, JsonGenerationException {
        if (bl) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator((JsonGenerator)this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries((JsonGenerator)this);
        }
        if (!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            Utf8Generator.super._writeStringSegments(string);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = 34;
        int n2 = string.length();
        if (n2 <= this._charBufferLength) {
            string.getChars(0, n2, this._charBuffer, 0);
            if (n2 <= this._outputMaxContiguous) {
                if (n2 + this._outputTail > this._outputEnd) {
                    this._flushBuffer();
                }
                Utf8Generator.super._writeStringSegment(this._charBuffer, 0, n2);
            } else {
                Utf8Generator.super._writeStringSegments(this._charBuffer, 0, n2);
            }
        } else {
            Utf8Generator.super._writeStringSegments(string);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby2[n3] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void close() throws IOException {
        super.close();
        if (this._outputBuffer != null && this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)) {
            do {
                JsonStreamContext jsonStreamContext;
                if ((jsonStreamContext = this.getOutputContext()).inArray()) {
                    this.writeEndArray();
                    continue;
                }
                if (!jsonStreamContext.inObject()) break;
                this.writeEndObject();
            } while (true);
        }
        this._flushBuffer();
        if (this._outputStream != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
                this._outputStream.close();
            } else if (this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
                this._outputStream.flush();
            }
        }
        this._releaseBuffers();
    }

    @Override
    public final void flush() throws IOException {
        this._flushBuffer();
        if (this._outputStream != null && this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
            this._outputStream.flush();
        }
    }

    @Override
    public CharacterEscapes getCharacterEscapes() {
        return this._characterEscapes;
    }

    @Override
    public int getHighestEscapedChar() {
        return this._maximumNonEscapedChar;
    }

    @Override
    public Object getOutputTarget() {
        return this._outputStream;
    }

    @Override
    public JsonGenerator setCharacterEscapes(CharacterEscapes characterEscapes) {
        this._characterEscapes = characterEscapes;
        if (characterEscapes == null) {
            this._outputEscapes = sOutputEscapes;
            return this;
        }
        this._outputEscapes = characterEscapes.getEscapeCodesForAscii();
        return this;
    }

    @Override
    public JsonGenerator setHighestNonEscapedChar(int n) {
        if (n < 0) {
            n = 0;
        }
        this._maximumNonEscapedChar = n;
        return this;
    }

    @Override
    public void writeBinary(Base64Variant base64Variant, byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write binary value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby2[n3] = 34;
        this._writeBinary(base64Variant, arrby, n, n + n2);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby3 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrby3[n4] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeBoolean(boolean bl) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write boolean value");
        if (5 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = bl ? TRUE_BYTES : FALSE_BYTES;
        int n = arrby.length;
        System.arraycopy((Object)arrby, (int)0, (Object)this._outputBuffer, (int)this._outputTail, (int)n);
        this._outputTail = n + this._outputTail;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void writeEndArray() throws IOException, JsonGenerationException {
        if (!this._writeContext.inArray()) {
            this._reportError("Current context not an ARRAY but " + this._writeContext.getTypeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            byte[] arrby = this._outputBuffer;
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrby[n] = 93;
        }
        this._writeContext = this._writeContext.getParent();
    }

    @Override
    public final void writeEndObject() throws IOException, JsonGenerationException {
        if (!this._writeContext.inObject()) {
            this._reportError("Current context not an object but " + this._writeContext.getTypeDesc());
        }
        this._writeContext = this._writeContext.getParent();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = 125;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void writeFieldName(SerializableString serializableString) throws IOException, JsonGenerationException {
        int n = 1;
        int n2 = this._writeContext.writeFieldName(serializableString.getValue());
        if (n2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (this._cfgPrettyPrinter != null) {
            if (n2 != n) {
                n = 0;
            }
            this._writePPFieldName(serializableString, (boolean)n);
            return;
        }
        if (n2 == n) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            byte[] arrby = this._outputBuffer;
            int n3 = this._outputTail;
            this._outputTail = n3 + 1;
            arrby[n3] = 44;
        }
        this._writeFieldName(serializableString);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void writeFieldName(SerializedString serializedString) throws IOException, JsonGenerationException {
        int n = 1;
        int n2 = this._writeContext.writeFieldName(serializedString.getValue());
        if (n2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (this._cfgPrettyPrinter != null) {
            if (n2 != n) {
                n = 0;
            }
            this._writePPFieldName(serializedString, (boolean)n);
            return;
        }
        if (n2 == n) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            byte[] arrby = this._outputBuffer;
            int n3 = this._outputTail;
            this._outputTail = n3 + 1;
            arrby[n3] = 44;
        }
        this._writeFieldName(serializedString);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void writeFieldName(String string) throws IOException, JsonGenerationException {
        int n = 1;
        int n2 = this._writeContext.writeFieldName(string);
        if (n2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (this._cfgPrettyPrinter != null) {
            if (n2 != n) {
                n = 0;
            }
            this._writePPFieldName(string, (boolean)n);
            return;
        }
        if (n2 == n) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            byte[] arrby = this._outputBuffer;
            int n3 = this._outputTail;
            this._outputTail = n3 + 1;
            arrby[n3] = 44;
        }
        this._writeFieldName(string);
    }

    @Override
    public void writeNull() throws IOException, JsonGenerationException {
        this._verifyValueWrite("write null value");
        this._writeNull();
    }

    @Override
    public void writeNumber(double d) throws IOException, JsonGenerationException {
        if (this._cfgNumbersAsStrings || (Double.isNaN((double)d) || Double.isInfinite((double)d)) && this.isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS)) {
            this.writeString(String.valueOf((double)d));
            return;
        }
        this._verifyValueWrite("write number");
        this.writeRaw(String.valueOf((double)d));
    }

    @Override
    public void writeNumber(float f) throws IOException, JsonGenerationException {
        if (this._cfgNumbersAsStrings || (Float.isNaN((float)f) || Float.isInfinite((float)f)) && this.isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS)) {
            this.writeString(String.valueOf((float)f));
            return;
        }
        this._verifyValueWrite("write number");
        this.writeRaw(String.valueOf((float)f));
    }

    @Override
    public void writeNumber(int n) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (11 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        if (this._cfgNumbersAsStrings) {
            Utf8Generator.super._writeQuotedInt(n);
            return;
        }
        this._outputTail = NumberOutput.outputInt(n, this._outputBuffer, this._outputTail);
    }

    @Override
    public void writeNumber(long l) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (this._cfgNumbersAsStrings) {
            Utf8Generator.super._writeQuotedLong(l);
            return;
        }
        if (21 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputLong(l, this._outputBuffer, this._outputTail);
    }

    @Override
    public void writeNumber(String string) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (this._cfgNumbersAsStrings) {
            Utf8Generator.super._writeQuotedRaw(string);
            return;
        }
        this.writeRaw(string);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (bigDecimal == null) {
            Utf8Generator.super._writeNull();
            return;
        }
        if (this._cfgNumbersAsStrings) {
            Utf8Generator.super._writeQuotedRaw((Object)bigDecimal);
            return;
        }
        this.writeRaw(bigDecimal.toString());
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (bigInteger == null) {
            Utf8Generator.super._writeNull();
            return;
        }
        if (this._cfgNumbersAsStrings) {
            Utf8Generator.super._writeQuotedRaw((Object)bigInteger);
            return;
        }
        this.writeRaw(bigInteger.toString());
    }

    @Override
    public void writeRaw(char c) throws IOException, JsonGenerationException {
        if (3 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        if (c <= '') {
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrby[n] = (byte)c;
            return;
        }
        if (c < '\u0800') {
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrby[n] = (byte)(192 | c >> 6);
            int n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrby[n2] = (byte)(128 | c & 63);
            return;
        }
        Utf8Generator.super._outputRawMultiByteChar(c, null, 0, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeRaw(String string) throws IOException, JsonGenerationException {
        int n = 0;
        int n2 = string.length();
        while (n2 > 0) {
            char[] arrc = this._charBuffer;
            int n3 = arrc.length;
            int n4 = n2 < n3 ? n2 : n3;
            string.getChars(n, n + n4, arrc, 0);
            this.writeRaw(arrc, 0, n4);
            n += n4;
            n2 -= n4;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeRaw(String string, int n, int n2) throws IOException, JsonGenerationException {
        while (n2 > 0) {
            char[] arrc = this._charBuffer;
            int n3 = arrc.length;
            int n4 = n2 < n3 ? n2 : n3;
            string.getChars(n, n + n4, arrc, 0);
            this.writeRaw(arrc, 0, n4);
            n += n4;
            n2 -= n4;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void writeRaw(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        int n3 = n2 + (n2 + n2);
        if (n3 + this._outputTail > this._outputEnd) {
            if (this._outputEnd < n3) {
                Utf8Generator.super._writeSegmentedRaw(arrc, n, n2);
                return;
            }
            this._flushBuffer();
        }
        int n4 = n2 + n;
        block0 : do {
            if (n >= n4) {
                return;
            }
            do {
                char c;
                if ((c = arrc[n]) > '') {
                    int n5 = n + 1;
                    char c2 = arrc[n];
                    if (c2 < '\u0800') {
                        byte[] arrby = this._outputBuffer;
                        int n6 = this._outputTail;
                        this._outputTail = n6 + 1;
                        arrby[n6] = (byte)(192 | c2 >> 6);
                        byte[] arrby2 = this._outputBuffer;
                        int n7 = this._outputTail;
                        this._outputTail = n7 + 1;
                        arrby2[n7] = (byte)(128 | c2 & 63);
                    } else {
                        Utf8Generator.super._outputRawMultiByteChar(c2, arrc, n5, n4);
                    }
                    n = n5;
                    continue block0;
                }
                byte[] arrby = this._outputBuffer;
                int n8 = this._outputTail;
                this._outputTail = n8 + 1;
                arrby[n8] = (byte)c;
            } while (++n < n4);
            break;
        } while (true);
    }

    @Override
    public void writeRawUTF8String(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write text value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby2[n3] = 34;
        Utf8Generator.super._writeBytes(arrby, n, n2);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby3 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrby3[n4] = 34;
    }

    @Override
    public final void writeStartArray() throws IOException, JsonGenerationException {
        this._verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = 91;
    }

    @Override
    public final void writeStartObject() throws IOException, JsonGenerationException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = 123;
    }

    @Override
    public final void writeString(SerializableString serializableString) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write text value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = 34;
        Utf8Generator.super._writeBytes(serializableString.asQuotedUTF8());
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby2[n2] = 34;
    }

    @Override
    public void writeString(String string) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write text value");
        if (string == null) {
            Utf8Generator.super._writeNull();
            return;
        }
        int n = string.length();
        if (n > this._charBufferLength) {
            Utf8Generator.super._writeLongString(string);
            return;
        }
        string.getChars(0, n, this._charBuffer, 0);
        if (n > this._outputMaxContiguous) {
            Utf8Generator.super._writeLongString(this._charBuffer, 0, n);
            return;
        }
        if (n + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = 34;
        Utf8Generator.super._writeStringSegment(this._charBuffer, 0, n);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby2[n3] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeString(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write text value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby[n3] = 34;
        if (n2 <= this._outputMaxContiguous) {
            if (n2 + this._outputTail > this._outputEnd) {
                this._flushBuffer();
            }
            Utf8Generator.super._writeStringSegment(arrc, n, n2);
        } else {
            Utf8Generator.super._writeStringSegments(arrc, n, n2);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrby2[n4] = 34;
    }

    @Override
    public final void writeStringField(String string, String string2) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeString(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeUTF8String(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write text value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby2[n3] = 34;
        if (n2 <= this._outputMaxContiguous) {
            Utf8Generator.super._writeUTF8Segment(arrby, n, n2);
        } else {
            Utf8Generator.super._writeUTF8Segments(arrby, n, n2);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby3 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrby3[n4] = 34;
    }
}


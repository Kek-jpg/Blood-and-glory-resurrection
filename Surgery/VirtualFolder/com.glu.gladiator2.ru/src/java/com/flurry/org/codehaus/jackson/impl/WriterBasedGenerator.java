/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.Writer
 *  java.lang.Double
 *  java.lang.Float
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
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class WriterBasedGenerator
extends JsonGeneratorBase {
    protected static final char[] HEX_CHARS = CharTypes.copyHexChars();
    protected static final int SHORT_WRITE = 32;
    protected static final int[] sOutputEscapes = CharTypes.get7BitOutputEscapes();
    protected CharacterEscapes _characterEscapes;
    protected SerializableString _currentEscape;
    protected char[] _entityBuffer;
    protected final IOContext _ioContext;
    protected int _maximumNonEscapedChar;
    protected char[] _outputBuffer;
    protected int _outputEnd;
    protected int[] _outputEscapes = sOutputEscapes;
    protected int _outputHead = 0;
    protected int _outputTail = 0;
    protected final Writer _writer;

    public WriterBasedGenerator(IOContext iOContext, int n, ObjectCodec objectCodec, Writer writer) {
        super(n, objectCodec);
        this._ioContext = iOContext;
        this._writer = writer;
        this._outputBuffer = iOContext.allocConcatBuffer();
        this._outputEnd = this._outputBuffer.length;
        if (this.isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
            this.setHighestNonEscapedChar(127);
        }
    }

    private char[] _allocateEntityBuffer() {
        char[] arrc = new char[14];
        arrc[0] = 92;
        arrc[2] = 92;
        arrc[3] = 117;
        arrc[4] = 48;
        arrc[5] = 48;
        arrc[8] = 92;
        arrc[9] = 117;
        this._entityBuffer = arrc;
        return arrc;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _appendCharacterEscape(char c, int n) throws IOException, JsonGenerationException {
        String string;
        int n2;
        if (n >= 0) {
            if (2 + this._outputTail > this._outputEnd) {
                this._flushBuffer();
            }
            char[] arrc = this._outputBuffer;
            int n3 = this._outputTail;
            this._outputTail = n3 + 1;
            arrc[n3] = 92;
            char[] arrc2 = this._outputBuffer;
            int n4 = this._outputTail;
            this._outputTail = n4 + 1;
            arrc2[n4] = (char)n;
            return;
        }
        if (n != -2) {
            int n5;
            if (2 + this._outputTail > this._outputEnd) {
                this._flushBuffer();
            }
            int n6 = this._outputTail;
            char[] arrc = this._outputBuffer;
            int n7 = n6 + 1;
            arrc[n6] = 92;
            int n8 = n7 + 1;
            arrc[n7] = 117;
            if (c > '\u00ff') {
                int n9 = 255 & c >> 8;
                int n10 = n8 + 1;
                arrc[n8] = HEX_CHARS[n9 >> 4];
                n5 = n10 + 1;
                arrc[n10] = HEX_CHARS[n9 & 15];
                c = (char)(c & 255);
            } else {
                int n11 = n8 + 1;
                arrc[n8] = 48;
                n5 = n11 + 1;
                arrc[n11] = 48;
            }
            int n12 = n5 + 1;
            arrc[n5] = HEX_CHARS[c >> 4];
            arrc[n12] = HEX_CHARS[c & 15];
            this._outputTail = n12;
            return;
        }
        if (this._currentEscape == null) {
            string = this._characterEscapes.getEscapeSequence(c).getValue();
        } else {
            string = this._currentEscape.getValue();
            this._currentEscape = null;
        }
        if ((n2 = string.length()) + this._outputTail > this._outputEnd) {
            this._flushBuffer();
            if (n2 > this._outputEnd) {
                this._writer.write(string);
                return;
            }
        }
        string.getChars(0, n2, this._outputBuffer, this._outputTail);
        this._outputTail = n2 + this._outputTail;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final int _prependOrWriteCharacterEscape(char[] arrc, int n, int n2, char c, int n3) throws IOException, JsonGenerationException {
        int n4;
        String string;
        if (n3 >= 0) {
            if (n > 1 && n < n2) {
                arrc[n -= 2] = 92;
                arrc[n + 1] = (char)n3;
                return n;
            }
            char[] arrc2 = this._entityBuffer;
            if (arrc2 == null) {
                arrc2 = this._allocateEntityBuffer();
            }
            arrc2[1] = (char)n3;
            this._writer.write(arrc2, 0, 2);
            return n;
        }
        if (n3 != -2) {
            if (n > 5 && n < n2) {
                int n5;
                int n6 = n - 6;
                int n7 = n6 + 1;
                arrc[n6] = 92;
                int n8 = n7 + 1;
                arrc[n7] = 117;
                if (c > '\u00ff') {
                    int n9 = 255 & c >> 8;
                    int n10 = n8 + 1;
                    arrc[n8] = HEX_CHARS[n9 >> 4];
                    n5 = n10 + 1;
                    arrc[n10] = HEX_CHARS[n9 & 15];
                    c = (char)(c & 255);
                } else {
                    int n11 = n8 + 1;
                    arrc[n8] = 48;
                    n5 = n11 + 1;
                    arrc[n11] = 48;
                }
                int n12 = n5 + 1;
                arrc[n5] = HEX_CHARS[c >> 4];
                arrc[n12] = HEX_CHARS[c & 15];
                return n12 - 5;
            }
            char[] arrc3 = this._entityBuffer;
            if (arrc3 == null) {
                arrc3 = this._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            if (c > '\u00ff') {
                int n13 = 255 & c >> 8;
                int n14 = c & 255;
                arrc3[10] = HEX_CHARS[n13 >> 4];
                arrc3[11] = HEX_CHARS[n13 & 15];
                arrc3[12] = HEX_CHARS[n14 >> 4];
                arrc3[13] = HEX_CHARS[n14 & 15];
                this._writer.write(arrc3, 8, 6);
                return n;
            }
            arrc3[6] = HEX_CHARS[c >> 4];
            arrc3[7] = HEX_CHARS[c & 15];
            this._writer.write(arrc3, 2, 6);
            return n;
        }
        if (this._currentEscape == null) {
            string = this._characterEscapes.getEscapeSequence(c).getValue();
        } else {
            string = this._currentEscape.getValue();
            this._currentEscape = null;
        }
        if (n >= (n4 = string.length()) && n < n2) {
            string.getChars(0, n4, arrc, n -= n4);
            return n;
        }
        this._writer.write(string);
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _prependOrWriteCharacterEscape(char c, int n) throws IOException, JsonGenerationException {
        String string;
        int n2;
        if (n >= 0) {
            if (this._outputTail >= 2) {
                int n3;
                this._outputHead = n3 = -2 + this._outputTail;
                char[] arrc = this._outputBuffer;
                int n4 = n3 + 1;
                arrc[n3] = 92;
                this._outputBuffer[n4] = (char)n;
                return;
            }
            char[] arrc = this._entityBuffer;
            if (arrc == null) {
                arrc = WriterBasedGenerator.super._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            arrc[1] = (char)n;
            this._writer.write(arrc, 0, 2);
            return;
        }
        if (n != -2) {
            if (this._outputTail >= 6) {
                int n5;
                int n6;
                char[] arrc = this._outputBuffer;
                this._outputHead = n5 = -6 + this._outputTail;
                arrc[n5] = 92;
                int n7 = n5 + 1;
                arrc[n7] = 117;
                if (c > '\u00ff') {
                    int n8 = 255 & c >> 8;
                    int n9 = n7 + 1;
                    arrc[n9] = HEX_CHARS[n8 >> 4];
                    n6 = n9 + 1;
                    arrc[n6] = HEX_CHARS[n8 & 15];
                    c = (char)(c & 255);
                } else {
                    int n10 = n7 + 1;
                    arrc[n10] = 48;
                    n6 = n10 + 1;
                    arrc[n6] = 48;
                }
                int n11 = n6 + 1;
                arrc[n11] = HEX_CHARS[c >> 4];
                arrc[n11 + 1] = HEX_CHARS[c & 15];
                return;
            }
            char[] arrc = this._entityBuffer;
            if (arrc == null) {
                arrc = WriterBasedGenerator.super._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            if (c > '\u00ff') {
                int n12 = 255 & c >> 8;
                int n13 = c & 255;
                arrc[10] = HEX_CHARS[n12 >> 4];
                arrc[11] = HEX_CHARS[n12 & 15];
                arrc[12] = HEX_CHARS[n13 >> 4];
                arrc[13] = HEX_CHARS[n13 & 15];
                this._writer.write(arrc, 8, 6);
                return;
            }
            arrc[6] = HEX_CHARS[c >> 4];
            arrc[7] = HEX_CHARS[c & 15];
            this._writer.write(arrc, 2, 6);
            return;
        }
        if (this._currentEscape == null) {
            string = this._characterEscapes.getEscapeSequence(c).getValue();
        } else {
            string = this._currentEscape.getValue();
            this._currentEscape = null;
        }
        if (this._outputTail >= (n2 = string.length())) {
            int n14;
            this._outputHead = n14 = this._outputTail - n2;
            string.getChars(0, n2, this._outputBuffer, n14);
            return;
        }
        this._outputHead = this._outputTail;
        this._writer.write(string);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _writeLongString(String string) throws IOException, JsonGenerationException {
        int n;
        this._flushBuffer();
        int n2 = string.length();
        int n3 = 0;
        do {
            int n4;
            n = n3 + (n4 = this._outputEnd) > n2 ? n2 - n3 : n4;
            string.getChars(n3, n3 + n, this._outputBuffer, 0);
            if (this._characterEscapes != null) {
                WriterBasedGenerator.super._writeSegmentCustom(n);
                continue;
            }
            if (this._maximumNonEscapedChar != 0) {
                WriterBasedGenerator.super._writeSegmentASCII(n, this._maximumNonEscapedChar);
                continue;
            }
            WriterBasedGenerator.super._writeSegment(n);
        } while ((n3 += n) < n2);
    }

    private final void _writeNull() throws IOException {
        if (4 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        int n = this._outputTail;
        char[] arrc = this._outputBuffer;
        arrc[n] = 110;
        int n2 = n + 1;
        arrc[n2] = 117;
        int n3 = n2 + 1;
        arrc[n3] = 108;
        int n4 = n3 + 1;
        arrc[n4] = 108;
        this._outputTail = n4 + 1;
    }

    private final void _writeQuotedInt(int n) throws IOException {
        if (13 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = 34;
        this._outputTail = NumberOutput.outputInt(n, this._outputBuffer, this._outputTail);
        char[] arrc2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrc2[n3] = 34;
    }

    private final void _writeQuotedLong(long l) throws IOException {
        if (23 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = 34;
        this._outputTail = NumberOutput.outputLong(l, this._outputBuffer, this._outputTail);
        char[] arrc2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc2[n2] = 34;
    }

    private final void _writeQuotedRaw(Object object) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = 34;
        this.writeRaw(object.toString());
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc2[n2] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeSegment(int n) throws IOException, JsonGenerationException {
        int[] arrn = this._outputEscapes;
        int n2 = arrn.length;
        int n3 = 0;
        int n4 = 0;
        while (n3 < n) {
            char c;
            while (((c = this._outputBuffer[n3]) >= n2 || arrn[c] == 0) && ++n3 < n) {
            }
            int n5 = n3 - n4;
            if (n5 > 0) {
                this._writer.write(this._outputBuffer, n4, n5);
                if (n3 >= n) break;
            }
            n4 = WriterBasedGenerator.super._prependOrWriteCharacterEscape(this._outputBuffer, ++n3, n, c, arrn[c]);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeSegmentASCII(int n, int n2) throws IOException, JsonGenerationException {
        int[] arrn = this._outputEscapes;
        int n3 = Math.min((int)arrn.length, (int)(1 + this._maximumNonEscapedChar));
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        while (n4 < n) {
            int n7;
            char c;
            do {
                if ((c = this._outputBuffer[n4]) < n3) {
                    n5 = arrn[c];
                    if (n5 == 0) continue;
                    break;
                }
                if (c <= n2) continue;
                n5 = -1;
                break;
            } while (++n4 < n);
            if ((n7 = n4 - n6) > 0) {
                this._writer.write(this._outputBuffer, n6, n7);
                if (n4 >= n) break;
            }
            n6 = WriterBasedGenerator.super._prependOrWriteCharacterEscape(this._outputBuffer, ++n4, n, c, n5);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeSegmentCustom(int n) throws IOException, JsonGenerationException {
        int[] arrn = this._outputEscapes;
        char c = this._maximumNonEscapedChar < 1 ? (char)'\uffff' : this._maximumNonEscapedChar;
        int n2 = Math.min((int)arrn.length, (int)(1 + this._maximumNonEscapedChar));
        CharacterEscapes characterEscapes = this._characterEscapes;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (n3 < n) {
            int n6;
            char c2;
            do {
                SerializableString serializableString;
                if ((c2 = this._outputBuffer[n3]) < n2) {
                    n4 = arrn[c2];
                    if (n4 == 0) continue;
                    break;
                }
                if (c2 > c) {
                    n4 = -1;
                    break;
                }
                this._currentEscape = serializableString = characterEscapes.getEscapeSequence(c2);
                if (serializableString == null) continue;
                n4 = -2;
                break;
            } while (++n3 < n);
            if ((n6 = n3 - n5) > 0) {
                this._writer.write(this._outputBuffer, n5, n6);
                if (n3 >= n) break;
            }
            n5 = WriterBasedGenerator.super._prependOrWriteCharacterEscape(this._outputBuffer, ++n3, n, c2, n4);
        }
    }

    private void _writeString(String string) throws IOException, JsonGenerationException {
        int n = string.length();
        if (n > this._outputEnd) {
            WriterBasedGenerator.super._writeLongString(string);
            return;
        }
        if (n + this._outputTail > this._outputEnd) {
            this._flushBuffer();
        }
        string.getChars(0, n, this._outputBuffer, this._outputTail);
        if (this._characterEscapes != null) {
            WriterBasedGenerator.super._writeStringCustom(n);
            return;
        }
        if (this._maximumNonEscapedChar != 0) {
            WriterBasedGenerator.super._writeStringASCII(n, this._maximumNonEscapedChar);
            return;
        }
        WriterBasedGenerator.super._writeString2(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeString(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        if (this._characterEscapes != null) {
            WriterBasedGenerator.super._writeStringCustom(arrc, n, n2);
            return;
        }
        if (this._maximumNonEscapedChar != 0) {
            WriterBasedGenerator.super._writeStringASCII(arrc, n, n2, this._maximumNonEscapedChar);
            return;
        }
        int n3 = n2 + n;
        int[] arrn = this._outputEscapes;
        int n4 = arrn.length;
        do {
            int n5;
            int n6;
            int n7;
            block13 : {
                if (n >= n3) {
                    return;
                }
                n6 = n;
                do {
                    char c;
                    if ((c = arrc[n]) >= n4 || arrn[c] == 0) continue;
                    n5 = n;
                    break block13;
                } while (++n < n3);
                n5 = n;
            }
            if ((n7 = n5 - n6) < 32) {
                if (n7 + this._outputTail > this._outputEnd) {
                    this._flushBuffer();
                }
                if (n7 > 0) {
                    System.arraycopy((Object)arrc, (int)n6, (Object)this._outputBuffer, (int)this._outputTail, (int)n7);
                    this._outputTail = n7 + this._outputTail;
                }
            } else {
                this._flushBuffer();
                this._writer.write(arrc, n6, n7);
            }
            if (n5 >= n3) {
                return;
            }
            n = n5 + 1;
            char c = arrc[n5];
            WriterBasedGenerator.super._appendCharacterEscape(c, arrn[c]);
        } while (true);
    }

    private void _writeString2(int n) throws IOException, JsonGenerationException {
        int n2 = n + this._outputTail;
        int[] arrn = this._outputEscapes;
        int n3 = arrn.length;
        block0 : while (this._outputTail < n2) {
            int n4;
            do {
                char c;
                if ((c = this._outputBuffer[this._outputTail]) < n3 && arrn[c] != 0) {
                    int n5 = this._outputTail - this._outputHead;
                    if (n5 > 0) {
                        this._writer.write(this._outputBuffer, this._outputHead, n5);
                    }
                    char[] arrc = this._outputBuffer;
                    int n6 = this._outputTail;
                    this._outputTail = n6 + 1;
                    char c2 = arrc[n6];
                    WriterBasedGenerator.super._prependOrWriteCharacterEscape(c2, arrn[c2]);
                    continue block0;
                }
                this._outputTail = n4 = 1 + this._outputTail;
            } while (n4 < n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _writeStringASCII(int n, int n2) throws IOException, JsonGenerationException {
        int n3 = n + this._outputTail;
        int[] arrn = this._outputEscapes;
        int n4 = Math.min((int)arrn.length, (int)(1 + this._maximumNonEscapedChar));
        block0 : while (this._outputTail < n3) {
            int n5;
            do {
                block7 : {
                    char c;
                    int n6;
                    int n7;
                    block8 : {
                        block6 : {
                            if ((c = this._outputBuffer[this._outputTail]) >= n4) break block6;
                            n7 = arrn[c];
                            if (n7 == 0) break block7;
                            break block8;
                        }
                        if (c <= n2) break block7;
                        n7 = -1;
                    }
                    if ((n6 = this._outputTail - this._outputHead) > 0) {
                        this._writer.write(this._outputBuffer, this._outputHead, n6);
                    }
                    this._outputTail = 1 + this._outputTail;
                    WriterBasedGenerator.super._prependOrWriteCharacterEscape(c, n7);
                    continue block0;
                }
                this._outputTail = n5 = 1 + this._outputTail;
            } while (n5 < n3);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeStringASCII(char[] arrc, int n, int n2, int n3) throws IOException, JsonGenerationException {
        int n4 = n2 + n;
        int[] arrn = this._outputEscapes;
        int n5 = Math.min((int)arrn.length, (int)(n3 + 1));
        int n6 = 0;
        while (n < n4) {
            int n7;
            char c;
            int n8 = n;
            do {
                if ((c = arrc[n]) < n5) {
                    n6 = arrn[c];
                    if (n6 == 0) continue;
                    break;
                }
                if (c <= n3) continue;
                n6 = -1;
                break;
            } while (++n < n4);
            if ((n7 = n - n8) < 32) {
                if (n7 + this._outputTail > this._outputEnd) {
                    this._flushBuffer();
                }
                if (n7 > 0) {
                    System.arraycopy((Object)arrc, (int)n8, (Object)this._outputBuffer, (int)this._outputTail, (int)n7);
                    this._outputTail = n7 + this._outputTail;
                }
            } else {
                this._flushBuffer();
                this._writer.write(arrc, n8, n7);
            }
            if (n >= n4) break;
            ++n;
            WriterBasedGenerator.super._appendCharacterEscape(c, n6);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _writeStringCustom(int n) throws IOException, JsonGenerationException {
        int n2 = n + this._outputTail;
        int[] arrn = this._outputEscapes;
        char c = this._maximumNonEscapedChar < 1 ? (char)'\uffff' : this._maximumNonEscapedChar;
        int n3 = Math.min((int)arrn.length, (int)(c + 1));
        CharacterEscapes characterEscapes = this._characterEscapes;
        block0 : while (this._outputTail < n2) {
            int n4;
            do {
                block7 : {
                    int n5;
                    char c2;
                    int n6;
                    block8 : {
                        SerializableString serializableString;
                        block9 : {
                            block6 : {
                                if ((c2 = this._outputBuffer[this._outputTail]) >= n3) break block6;
                                n6 = arrn[c2];
                                if (n6 == 0) break block7;
                                break block8;
                            }
                            if (c2 <= c) break block9;
                            n6 = -1;
                            break block8;
                        }
                        this._currentEscape = serializableString = characterEscapes.getEscapeSequence(c2);
                        if (serializableString == null) break block7;
                        n6 = -2;
                    }
                    if ((n5 = this._outputTail - this._outputHead) > 0) {
                        this._writer.write(this._outputBuffer, this._outputHead, n5);
                    }
                    this._outputTail = 1 + this._outputTail;
                    WriterBasedGenerator.super._prependOrWriteCharacterEscape(c2, n6);
                    continue block0;
                }
                this._outputTail = n4 = 1 + this._outputTail;
            } while (n4 < n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _writeStringCustom(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        int n3 = n2 + n;
        int[] arrn = this._outputEscapes;
        char c = this._maximumNonEscapedChar < 1 ? (char)'\uffff' : this._maximumNonEscapedChar;
        int n4 = Math.min((int)arrn.length, (int)(c + 1));
        CharacterEscapes characterEscapes = this._characterEscapes;
        int n5 = 0;
        while (n < n3) {
            char c2;
            int n6;
            int n7 = n;
            do {
                SerializableString serializableString;
                if ((c2 = arrc[n]) < n4) {
                    n5 = arrn[c2];
                    if (n5 == 0) continue;
                    break;
                }
                if (c2 > c) {
                    n5 = -1;
                    break;
                }
                this._currentEscape = serializableString = characterEscapes.getEscapeSequence(c2);
                if (serializableString == null) continue;
                n5 = -2;
                break;
            } while (++n < n3);
            if ((n6 = n - n7) < 32) {
                if (n6 + this._outputTail > this._outputEnd) {
                    this._flushBuffer();
                }
                if (n6 > 0) {
                    System.arraycopy((Object)arrc, (int)n7, (Object)this._outputBuffer, (int)this._outputTail, (int)n6);
                    this._outputTail = n6 + this._outputTail;
                }
            } else {
                this._flushBuffer();
                this._writer.write(arrc, n7, n6);
            }
            if (n >= n3) break;
            ++n;
            WriterBasedGenerator.super._appendCharacterEscape(c2, n5);
        }
    }

    private void writeRawLong(String string) throws IOException, JsonGenerationException {
        int n;
        int n2;
        int n3 = this._outputEnd - this._outputTail;
        string.getChars(0, n3, this._outputBuffer, this._outputTail);
        this._outputTail = n3 + this._outputTail;
        this._flushBuffer();
        int n4 = n3;
        for (n2 = string.length() - n3; n2 > this._outputEnd; n2 -= n) {
            n = this._outputEnd;
            string.getChars(n4, n4 + n, this._outputBuffer, 0);
            this._outputHead = 0;
            this._outputTail = n;
            this._flushBuffer();
            n4 += n;
        }
        string.getChars(n4, n4 + n2, this._outputBuffer, 0);
        this._outputHead = 0;
        this._outputTail = n2;
    }

    protected final void _flushBuffer() throws IOException {
        int n = this._outputTail - this._outputHead;
        if (n > 0) {
            int n2 = this._outputHead;
            this._outputHead = 0;
            this._outputTail = 0;
            this._writer.write(this._outputBuffer, n2, n);
        }
    }

    @Override
    protected void _releaseBuffers() {
        char[] arrc = this._outputBuffer;
        if (arrc != null) {
            this._outputBuffer = null;
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
                char[] arrc = this._outputBuffer;
                int n12 = this._outputTail;
                this._outputTail = n12 + 1;
                arrc[n12] = 92;
                char[] arrc2 = this._outputBuffer;
                int n13 = this._outputTail;
                this._outputTail = n13 + 1;
                arrc2[n13] = 110;
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

    public void _writeFieldName(SerializableString serializableString, boolean bl) throws IOException, JsonGenerationException {
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(serializableString, bl);
            return;
        }
        if (1 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        if (bl) {
            char[] arrc = this._outputBuffer;
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrc[n] = 44;
        }
        char[] arrc = serializableString.asQuotedChars();
        if (!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            this.writeRaw(arrc, 0, arrc.length);
            return;
        }
        char[] arrc2 = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc2[n] = 34;
        int n2 = arrc.length;
        if (1 + (n2 + this._outputTail) >= this._outputEnd) {
            this.writeRaw(arrc, 0, n2);
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            char[] arrc3 = this._outputBuffer;
            int n3 = this._outputTail;
            this._outputTail = n3 + 1;
            arrc3[n3] = 34;
            return;
        }
        System.arraycopy((Object)arrc, (int)0, (Object)this._outputBuffer, (int)this._outputTail, (int)n2);
        this._outputTail = n2 + this._outputTail;
        char[] arrc4 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrc4[n4] = 34;
    }

    protected void _writeFieldName(String string, boolean bl) throws IOException, JsonGenerationException {
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(string, bl);
            return;
        }
        if (1 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        if (bl) {
            char[] arrc = this._outputBuffer;
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrc[n] = 44;
        }
        if (!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            WriterBasedGenerator.super._writeString(string);
            return;
        }
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = 34;
        WriterBasedGenerator.super._writeString(string);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc2[n2] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _writePPFieldName(SerializableString serializableString, boolean bl) throws IOException, JsonGenerationException {
        if (bl) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator((JsonGenerator)this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries((JsonGenerator)this);
        }
        char[] arrc = serializableString.asQuotedChars();
        if (!this.isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
            this.writeRaw(arrc, 0, arrc.length);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc2[n] = 34;
        this.writeRaw(arrc, 0, arrc.length);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc3 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc3[n2] = 34;
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
            WriterBasedGenerator.super._writeString(string);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = 34;
        WriterBasedGenerator.super._writeString(string);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc2[n2] = 34;
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
        if (this._writer != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
                this._writer.close();
            } else if (this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
                this._writer.flush();
            }
        }
        this._releaseBuffers();
    }

    @Override
    public final void flush() throws IOException {
        this._flushBuffer();
        if (this._writer != null && this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
            this._writer.flush();
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
        return this._writer;
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
        char[] arrc = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrc[n3] = 34;
        this._writeBinary(base64Variant, arrby, n, n + n2);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrc2[n4] = 34;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeBoolean(boolean bl) throws IOException, JsonGenerationException {
        int n;
        this._verifyValueWrite("write boolean value");
        if (5 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        int n2 = this._outputTail;
        char[] arrc = this._outputBuffer;
        if (bl) {
            arrc[n2] = 116;
            int n3 = n2 + 1;
            arrc[n3] = 114;
            int n4 = n3 + 1;
            arrc[n4] = 117;
            n = n4 + 1;
            arrc[n] = 101;
        } else {
            arrc[n2] = 102;
            int n5 = n2 + 1;
            arrc[n5] = 97;
            int n6 = n5 + 1;
            arrc[n6] = 108;
            int n7 = n6 + 1;
            arrc[n7] = 115;
            n = n7 + 1;
            arrc[n] = 101;
        }
        this._outputTail = n + 1;
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
            char[] arrc = this._outputBuffer;
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrc[n] = 93;
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
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = 125;
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
        if (n2 != n) {
            n = 0;
        }
        this._writeFieldName(serializableString, (boolean)n);
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
        if (n2 != n) {
            n = 0;
        }
        this._writeFieldName(serializedString, (boolean)n);
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
        if (n2 != n) {
            n = 0;
        }
        this._writeFieldName(string, (boolean)n);
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
        if (this._cfgNumbersAsStrings) {
            WriterBasedGenerator.super._writeQuotedInt(n);
            return;
        }
        if (11 + this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputInt(n, this._outputBuffer, this._outputTail);
    }

    @Override
    public void writeNumber(long l) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (this._cfgNumbersAsStrings) {
            WriterBasedGenerator.super._writeQuotedLong(l);
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
            WriterBasedGenerator.super._writeQuotedRaw(string);
            return;
        }
        this.writeRaw(string);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (bigDecimal == null) {
            WriterBasedGenerator.super._writeNull();
            return;
        }
        if (this._cfgNumbersAsStrings) {
            WriterBasedGenerator.super._writeQuotedRaw((Object)bigDecimal);
            return;
        }
        this.writeRaw(bigDecimal.toString());
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write number");
        if (bigInteger == null) {
            WriterBasedGenerator.super._writeNull();
            return;
        }
        if (this._cfgNumbersAsStrings) {
            WriterBasedGenerator.super._writeQuotedRaw((Object)bigInteger);
            return;
        }
        this.writeRaw(bigInteger.toString());
    }

    @Override
    public void writeRaw(char c) throws IOException, JsonGenerationException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = c;
    }

    @Override
    public void writeRaw(String string) throws IOException, JsonGenerationException {
        int n = string.length();
        int n2 = this._outputEnd - this._outputTail;
        if (n2 == 0) {
            this._flushBuffer();
            n2 = this._outputEnd - this._outputTail;
        }
        if (n2 >= n) {
            string.getChars(0, n, this._outputBuffer, this._outputTail);
            this._outputTail = n + this._outputTail;
            return;
        }
        WriterBasedGenerator.super.writeRawLong(string);
    }

    @Override
    public void writeRaw(String string, int n, int n2) throws IOException, JsonGenerationException {
        int n3 = this._outputEnd - this._outputTail;
        if (n3 < n2) {
            this._flushBuffer();
            n3 = this._outputEnd - this._outputTail;
        }
        if (n3 >= n2) {
            string.getChars(n, n + n2, this._outputBuffer, this._outputTail);
            this._outputTail = n2 + this._outputTail;
            return;
        }
        WriterBasedGenerator.super.writeRawLong(string.substring(n, n + n2));
    }

    @Override
    public void writeRaw(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        if (n2 < 32) {
            if (n2 > this._outputEnd - this._outputTail) {
                this._flushBuffer();
            }
            System.arraycopy((Object)arrc, (int)n, (Object)this._outputBuffer, (int)this._outputTail, (int)n2);
            this._outputTail = n2 + this._outputTail;
            return;
        }
        this._flushBuffer();
        this._writer.write(arrc, n, n2);
    }

    @Override
    public void writeRawUTF8String(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
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
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = 91;
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
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = 123;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void writeString(SerializableString serializableString) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write text value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = 34;
        char[] arrc2 = serializableString.asQuotedChars();
        int n2 = arrc2.length;
        if (n2 < 32) {
            if (n2 > this._outputEnd - this._outputTail) {
                this._flushBuffer();
            }
            System.arraycopy((Object)arrc2, (int)0, (Object)this._outputBuffer, (int)this._outputTail, (int)n2);
            this._outputTail = n2 + this._outputTail;
        } else {
            this._flushBuffer();
            this._writer.write(arrc2, 0, n2);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc3 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrc3[n3] = 34;
    }

    @Override
    public void writeString(String string) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write text value");
        if (string == null) {
            WriterBasedGenerator.super._writeNull();
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = 34;
        WriterBasedGenerator.super._writeString(string);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc2[n2] = 34;
    }

    @Override
    public void writeString(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write text value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrc2[n3] = 34;
        WriterBasedGenerator.super._writeString(arrc, n, n2);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc3 = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n4 + 1;
        arrc3[n4] = 34;
    }

    @Override
    public final void writeStringField(String string, String string2) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeString(string2);
    }

    @Override
    public void writeUTF8String(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }
}


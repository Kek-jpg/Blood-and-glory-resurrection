/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Boolean
 *  java.lang.Character
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 */
package com.flurry.org.codehaus.jackson.impl;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.SerializableString;
import com.flurry.org.codehaus.jackson.impl.JsonParserBase;
import com.flurry.org.codehaus.jackson.impl.JsonReadContext;
import com.flurry.org.codehaus.jackson.io.IOContext;
import com.flurry.org.codehaus.jackson.sym.BytesToNameCanonicalizer;
import com.flurry.org.codehaus.jackson.sym.Name;
import com.flurry.org.codehaus.jackson.util.ByteArrayBuilder;
import com.flurry.org.codehaus.jackson.util.CharTypes;
import com.flurry.org.codehaus.jackson.util.TextBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class Utf8StreamParser
extends JsonParserBase {
    static final byte BYTE_LF = 10;
    private static final int[] sInputCodesLatin1;
    private static final int[] sInputCodesUtf8;
    protected boolean _bufferRecyclable;
    protected byte[] _inputBuffer;
    protected InputStream _inputStream;
    protected ObjectCodec _objectCodec;
    private int _quad1;
    protected int[] _quadBuffer = new int[16];
    protected final BytesToNameCanonicalizer _symbols;
    protected boolean _tokenIncomplete = false;

    static {
        sInputCodesUtf8 = CharTypes.getInputCodeUtf8();
        sInputCodesLatin1 = CharTypes.getInputCodeLatin1();
    }

    public Utf8StreamParser(IOContext iOContext, int n, InputStream inputStream, ObjectCodec objectCodec, BytesToNameCanonicalizer bytesToNameCanonicalizer, byte[] arrby, int n2, int n3, boolean bl) {
        super(iOContext, n);
        this._inputStream = inputStream;
        this._objectCodec = objectCodec;
        this._symbols = bytesToNameCanonicalizer;
        this._inputBuffer = arrby;
        this._inputPtr = n2;
        this._inputEnd = n3;
        this._bufferRecyclable = bl;
        if (!JsonParser.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(n)) {
            this._throwInternal();
        }
    }

    private final int _decodeUtf8_2(int n) throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        byte by = arrby[n2];
        if ((by & 192) != 128) {
            this._reportInvalidOther(by & 255, this._inputPtr);
        }
        return (n & 31) << 6 | by & 63;
    }

    private final int _decodeUtf8_3(int n) throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        int n2 = n & 15;
        byte[] arrby = this._inputBuffer;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        byte by = arrby[n3];
        if ((by & 192) != 128) {
            this._reportInvalidOther(by & 255, this._inputPtr);
        }
        int n4 = n2 << 6 | by & 63;
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby2 = this._inputBuffer;
        int n5 = this._inputPtr;
        this._inputPtr = n5 + 1;
        byte by2 = arrby2[n5];
        if ((by2 & 192) != 128) {
            this._reportInvalidOther(by2 & 255, this._inputPtr);
        }
        return n4 << 6 | by2 & 63;
    }

    private final int _decodeUtf8_3fast(int n) throws IOException, JsonParseException {
        int n2 = n & 15;
        byte[] arrby = this._inputBuffer;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        byte by = arrby[n3];
        if ((by & 192) != 128) {
            this._reportInvalidOther(by & 255, this._inputPtr);
        }
        int n4 = n2 << 6 | by & 63;
        byte[] arrby2 = this._inputBuffer;
        int n5 = this._inputPtr;
        this._inputPtr = n5 + 1;
        byte by2 = arrby2[n5];
        if ((by2 & 192) != 128) {
            this._reportInvalidOther(by2 & 255, this._inputPtr);
        }
        return n4 << 6 | by2 & 63;
    }

    private final int _decodeUtf8_4(int n) throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        byte by = arrby[n2];
        if ((by & 192) != 128) {
            this._reportInvalidOther(by & 255, this._inputPtr);
        }
        int n3 = (n & 7) << 6 | by & 63;
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby2 = this._inputBuffer;
        int n4 = this._inputPtr;
        this._inputPtr = n4 + 1;
        byte by2 = arrby2[n4];
        if ((by2 & 192) != 128) {
            this._reportInvalidOther(by2 & 255, this._inputPtr);
        }
        int n5 = n3 << 6 | by2 & 63;
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby3 = this._inputBuffer;
        int n6 = this._inputPtr;
        this._inputPtr = n6 + 1;
        byte by3 = arrby3[n6];
        if ((by3 & 192) != 128) {
            this._reportInvalidOther(by3 & 255, this._inputPtr);
        }
        return (n5 << 6 | by3 & 63) - 65536;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _finishString2(char[] arrc, int n) throws IOException, JsonParseException {
        int[] arrn = sInputCodesUtf8;
        byte[] arrby = this._inputBuffer;
        do {
            int n2;
            int n3;
            block17 : {
                int n4;
                block16 : {
                    int n5;
                    if ((n5 = this._inputPtr) >= this._inputEnd) {
                        this.loadMoreGuaranteed();
                        n5 = this._inputPtr;
                    }
                    if (n >= arrc.length) {
                        arrc = this._textBuffer.finishCurrentSegment();
                        n = 0;
                    }
                    int n6 = Math.min((int)this._inputEnd, (int)(n5 + (arrc.length - n)));
                    int n7 = n5;
                    n4 = n;
                    while (n7 < n6) {
                        int n8 = n7 + 1;
                        n2 = 255 & arrby[n7];
                        if (arrn[n2] != 0) {
                            this._inputPtr = n8;
                            if (n2 == 34) {
                                this._textBuffer.setCurrentLength(n4);
                                return;
                            }
                            break block16;
                        }
                        int n9 = n4 + 1;
                        arrc[n4] = (char)n2;
                        n7 = n8;
                        n4 = n9;
                    }
                    this._inputPtr = n7;
                    n = n4;
                    continue;
                }
                switch (arrn[n2]) {
                    default: {
                        if (n2 >= 32) break;
                        this._throwUnquotedSpace(n2, "string value");
                        n3 = n4;
                        break block17;
                    }
                    case 1: {
                        n2 = this._decodeEscaped();
                        n3 = n4;
                        break block17;
                    }
                    case 2: {
                        n2 = Utf8StreamParser.super._decodeUtf8_2(n2);
                        n3 = n4;
                        break block17;
                    }
                    case 3: {
                        if (this._inputEnd - this._inputPtr >= 2) {
                            n2 = Utf8StreamParser.super._decodeUtf8_3fast(n2);
                            n3 = n4;
                        } else {
                            n2 = Utf8StreamParser.super._decodeUtf8_3(n2);
                            n3 = n4;
                        }
                        break block17;
                    }
                    case 4: {
                        int n10 = Utf8StreamParser.super._decodeUtf8_4(n2);
                        n3 = n4 + 1;
                        arrc[n4] = (char)(55296 | n10 >> 10);
                        if (n3 >= arrc.length) {
                            arrc = this._textBuffer.finishCurrentSegment();
                            n3 = 0;
                        }
                        n2 = 56320 | n10 & 1023;
                        break block17;
                    }
                }
                this._reportInvalidChar(n2);
                n3 = n4;
            }
            if (n3 >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n3 = 0;
            }
            int n11 = n3 + 1;
            arrc[n3] = (char)n2;
            n = n11;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _isNextTokenNameNo(int n) throws IOException, JsonParseException {
        JsonToken jsonToken;
        int n2;
        Name name = this._parseFieldName(n);
        this._parsingContext.setCurrentName(name.getName());
        this._currToken = JsonToken.FIELD_NAME;
        int n3 = Utf8StreamParser.super._skipWS();
        if (n3 != 58) {
            this._reportUnexpectedChar(n3, "was expecting a colon to separate field name and value");
        }
        if ((n2 = Utf8StreamParser.super._skipWS()) == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return;
        }
        switch (n2) {
            default: {
                jsonToken = this._handleUnexpectedValue(n2);
                break;
            }
            case 91: {
                jsonToken = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                jsonToken = JsonToken.START_OBJECT;
                break;
            }
            case 93: 
            case 125: {
                this._reportUnexpectedChar(n2, "expected a value");
            }
            case 116: {
                this._matchToken("true", 1);
                jsonToken = JsonToken.VALUE_TRUE;
                break;
            }
            case 102: {
                this._matchToken("false", 1);
                jsonToken = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchToken("null", 1);
                jsonToken = JsonToken.VALUE_NULL;
                break;
            }
            case 45: 
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: {
                jsonToken = this.parseNumberText(n2);
            }
        }
        this._nextToken = jsonToken;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void _isNextTokenNameYes() throws IOException, JsonParseException {
        int n;
        if (this._inputPtr < this._inputEnd && this._inputBuffer[this._inputPtr] == 58) {
            this._inputPtr = 1 + this._inputPtr;
            byte[] arrby = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            byte by = arrby[n2];
            if (by == 34) {
                this._tokenIncomplete = true;
                this._nextToken = JsonToken.VALUE_STRING;
                return;
            }
            if (by == 123) {
                this._nextToken = JsonToken.START_OBJECT;
                return;
            }
            if (by == 91) {
                this._nextToken = JsonToken.START_ARRAY;
                return;
            }
            n = by & 255;
            if (n <= 32 || n == 47) {
                this._inputPtr = -1 + this._inputPtr;
                n = this._skipWS();
            }
        } else {
            n = this._skipColon();
        }
        switch (n) {
            default: {
                this._nextToken = this._handleUnexpectedValue(n);
                return;
            }
            case 34: {
                this._tokenIncomplete = true;
                this._nextToken = JsonToken.VALUE_STRING;
                return;
            }
            case 91: {
                this._nextToken = JsonToken.START_ARRAY;
                return;
            }
            case 123: {
                this._nextToken = JsonToken.START_OBJECT;
                return;
            }
            case 93: 
            case 125: {
                this._reportUnexpectedChar(n, "expected a value");
            }
            case 116: {
                this._matchToken("true", 1);
                this._nextToken = JsonToken.VALUE_TRUE;
                return;
            }
            case 102: {
                this._matchToken("false", 1);
                this._nextToken = JsonToken.VALUE_FALSE;
                return;
            }
            case 110: {
                this._matchToken("null", 1);
                this._nextToken = JsonToken.VALUE_NULL;
                return;
            }
            case 45: 
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: 
        }
        this._nextToken = this.parseNumberText(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final JsonToken _nextAfterName() {
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        this._currToken = jsonToken;
        return jsonToken;
    }

    private final JsonToken _nextTokenNotInObject(int n) throws IOException, JsonParseException {
        JsonToken jsonToken;
        if (n == 34) {
            JsonToken jsonToken2;
            this._tokenIncomplete = true;
            this._currToken = jsonToken2 = JsonToken.VALUE_STRING;
            return jsonToken2;
        }
        switch (n) {
            default: {
                JsonToken jsonToken3;
                this._currToken = jsonToken3 = this._handleUnexpectedValue(n);
                return jsonToken3;
            }
            case 91: {
                JsonToken jsonToken4;
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                this._currToken = jsonToken4 = JsonToken.START_ARRAY;
                return jsonToken4;
            }
            case 123: {
                JsonToken jsonToken5;
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                this._currToken = jsonToken5 = JsonToken.START_OBJECT;
                return jsonToken5;
            }
            case 93: 
            case 125: {
                this._reportUnexpectedChar(n, "expected a value");
            }
            case 116: {
                JsonToken jsonToken6;
                this._matchToken("true", 1);
                this._currToken = jsonToken6 = JsonToken.VALUE_TRUE;
                return jsonToken6;
            }
            case 102: {
                JsonToken jsonToken7;
                this._matchToken("false", 1);
                this._currToken = jsonToken7 = JsonToken.VALUE_FALSE;
                return jsonToken7;
            }
            case 110: {
                JsonToken jsonToken8;
                this._matchToken("null", 1);
                this._currToken = jsonToken8 = JsonToken.VALUE_NULL;
                return jsonToken8;
            }
            case 45: 
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: 
        }
        this._currToken = jsonToken = this.parseNumberText(n);
        return jsonToken;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final JsonToken _parseFloatText(char[] arrc, int n, int n2, boolean bl, int n3) throws IOException, JsonParseException {
        int n4;
        boolean bl2;
        int n5;
        block19 : {
            block18 : {
                bl2 = false;
                n4 = 0;
                if (n2 == 46) {
                    int n6 = n + 1;
                    arrc[n] = (char)n2;
                    n = n6;
                    do {
                        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                            bl2 = true;
                            break;
                        }
                        byte[] arrby = this._inputBuffer;
                        int n7 = this._inputPtr;
                        this._inputPtr = n7 + 1;
                        n2 = 255 & arrby[n7];
                        bl2 = false;
                        if (n2 < 48) break;
                        bl2 = false;
                        if (n2 > 57) break;
                        ++n4;
                        if (n >= arrc.length) {
                            arrc = this._textBuffer.finishCurrentSegment();
                            n = 0;
                        }
                        int n8 = n + 1;
                        arrc[n] = (char)n2;
                        n = n8;
                    } while (true);
                    if (n4 == 0) {
                        this.reportUnexpectedNumberChar(n2, "Decimal point not followed by a digit");
                    }
                }
                n5 = 0;
                if (n2 == 101) break block18;
                n5 = 0;
                if (n2 != 69) break block19;
            }
            if (n >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n = 0;
            }
            int n9 = n + 1;
            arrc[n] = (char)n2;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            byte[] arrby = this._inputBuffer;
            int n10 = this._inputPtr;
            this._inputPtr = n10 + 1;
            int n11 = 255 & arrby[n10];
            if (n11 == 45 || n11 == 43) {
                int n12;
                if (n9 >= arrc.length) {
                    arrc = this._textBuffer.finishCurrentSegment();
                    n12 = 0;
                } else {
                    n12 = n9;
                }
                int n13 = n12 + 1;
                arrc[n12] = (char)n11;
                if (this._inputPtr >= this._inputEnd) {
                    this.loadMoreGuaranteed();
                }
                byte[] arrby2 = this._inputBuffer;
                int n14 = this._inputPtr;
                this._inputPtr = n14 + 1;
                n11 = 255 & arrby2[n14];
                n = n13;
            } else {
                n = n9;
                n5 = 0;
            }
            do {
                int n15;
                block21 : {
                    block20 : {
                        if (n11 > 57 || n11 < 48) break block20;
                        ++n5;
                        if (n >= arrc.length) {
                            arrc = this._textBuffer.finishCurrentSegment();
                            n = 0;
                        }
                        n15 = n + 1;
                        arrc[n] = (char)n11;
                        if (this._inputPtr < this._inputEnd || this.loadMore()) break block21;
                        bl2 = true;
                        n = n15;
                    }
                    if (n5 != 0) break;
                    this.reportUnexpectedNumberChar(n11, "Exponent indicator not followed by a digit");
                    break;
                }
                byte[] arrby3 = this._inputBuffer;
                int n16 = this._inputPtr;
                this._inputPtr = n16 + 1;
                n11 = 255 & arrby3[n16];
                n = n15;
            } while (true);
        }
        if (!bl2) {
            this._inputPtr = -1 + this._inputPtr;
        }
        this._textBuffer.setCurrentLength(n);
        return this.resetFloat(bl, n3, n4, n5);
    }

    private final JsonToken _parserNumber2(char[] arrc, int n, boolean bl, int n2) throws IOException, JsonParseException {
        do {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._textBuffer.setCurrentLength(n);
                return this.resetInt(bl, n2);
            }
            byte[] arrby = this._inputBuffer;
            int n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            int n4 = 255 & arrby[n3];
            if (n4 > 57 || n4 < 48) {
                if (n4 != 46 && n4 != 101 && n4 != 69) break;
                return Utf8StreamParser.super._parseFloatText(arrc, n, n4, bl, n2);
            }
            if (n >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n = 0;
            }
            int n5 = n + 1;
            arrc[n] = (char)n4;
            ++n2;
            n = n5;
        } while (true);
        this._inputPtr = -1 + this._inputPtr;
        this._textBuffer.setCurrentLength(n);
        return this.resetInt(bl, n2);
    }

    private final void _skipCComment() throws IOException, JsonParseException {
        int[] arrn = CharTypes.getInputCodeComment();
        block8 : while (this._inputPtr < this._inputEnd || this.loadMore()) {
            byte[] arrby = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            int n2 = 255 & arrby[n];
            int n3 = arrn[n2];
            if (n3 == 0) continue;
            switch (n3) {
                default: {
                    this._reportInvalidChar(n2);
                    continue block8;
                }
                case 42: {
                    if (this._inputBuffer[this._inputPtr] != 47) continue block8;
                    this._inputPtr = 1 + this._inputPtr;
                    return;
                }
                case 10: {
                    this._skipLF();
                    continue block8;
                }
                case 13: {
                    this._skipCR();
                    continue block8;
                }
                case 2: {
                    this._skipUtf8_2(n2);
                    continue block8;
                }
                case 3: {
                    this._skipUtf8_3(n2);
                    continue block8;
                }
                case 4: 
            }
            this._skipUtf8_4(n2);
        }
        this._reportInvalidEOF(" in a comment");
    }

    /*
     * Enabled aggressive block sorting
     */
    private final int _skipColon() throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        byte by = arrby[n];
        if (by == 58) {
            int n2;
            if (this._inputPtr < this._inputEnd && (n2 = 255 & this._inputBuffer[this._inputPtr]) > 32 && n2 != 47) {
                this._inputPtr = 1 + this._inputPtr;
                return n2;
            }
        } else {
            int n3 = by & 255;
            block5 : do {
                switch (n3) {
                    default: {
                        if (n3 < 32) {
                            this._throwInvalidSpace(n3);
                        }
                        if (this._inputPtr >= this._inputEnd) {
                            this.loadMoreGuaranteed();
                        }
                        byte[] arrby2 = this._inputBuffer;
                        int n4 = this._inputPtr;
                        this._inputPtr = n4 + 1;
                        int n5 = 255 & arrby2[n4];
                        if (n5 == 58) break block5;
                        this._reportUnexpectedChar(n5, "was expecting a colon to separate field name and value");
                        break block5;
                    }
                    case 9: 
                    case 13: 
                    case 32: {
                        this._skipCR();
                        continue block5;
                    }
                    case 10: {
                        this._skipLF();
                        continue block5;
                    }
                    case 47: {
                        this._skipComment();
                        continue block5;
                    }
                }
                break;
            } while (true);
        }
        while (this._inputPtr < this._inputEnd || this.loadMore()) {
            byte[] arrby3 = this._inputBuffer;
            int n6 = this._inputPtr;
            this._inputPtr = n6 + 1;
            int n7 = 255 & arrby3[n6];
            if (n7 > 32) {
                if (n7 != 47) {
                    return n7;
                }
                this._skipComment();
                continue;
            }
            if (n7 == 32) continue;
            if (n7 == 10) {
                this._skipLF();
                continue;
            }
            if (n7 == 13) {
                this._skipCR();
                continue;
            }
            if (n7 == 9) continue;
            this._throwInvalidSpace(n7);
        }
        throw this._constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
    }

    private final void _skipComment() throws IOException, JsonParseException {
        if (!this.isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(" in a comment");
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        int n2 = 255 & arrby[n];
        if (n2 == 47) {
            this._skipCppComment();
            return;
        }
        if (n2 == 42) {
            this._skipCComment();
            return;
        }
        this._reportUnexpectedChar(n2, "was expecting either '*' or '/' for a comment");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final void _skipCppComment() throws IOException, JsonParseException {
        int[] arrn = CharTypes.getInputCodeComment();
        block8 : while (!(this._inputPtr >= this._inputEnd && !this.loadMore())) {
            byte[] arrby = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            int n2 = 255 & arrby[n];
            int n3 = arrn[n2];
            if (n3 == 0) continue;
            switch (n3) {
                case 42: {
                    continue block8;
                }
                default: {
                    this._reportInvalidChar(n2);
                    continue block8;
                }
                case 10: {
                    this._skipLF();
                    return;
                }
                case 13: {
                    this._skipCR();
                    return;
                }
                case 2: {
                    this._skipUtf8_2(n2);
                    continue block8;
                }
                case 3: {
                    this._skipUtf8_3(n2);
                    continue block8;
                }
                case 4: 
            }
            this._skipUtf8_4(n2);
        }
    }

    private final void _skipUtf8_2(int n) throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        byte by = arrby[n2];
        if ((by & 192) != 128) {
            this._reportInvalidOther(by & 255, this._inputPtr);
        }
    }

    private final void _skipUtf8_3(int n) throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        byte by = arrby[n2];
        if ((by & 192) != 128) {
            this._reportInvalidOther(by & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby2 = this._inputBuffer;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        byte by2 = arrby2[n3];
        if ((by2 & 192) != 128) {
            this._reportInvalidOther(by2 & 255, this._inputPtr);
        }
    }

    private final void _skipUtf8_4(int n) throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        byte by = arrby[n2];
        if ((by & 192) != 128) {
            this._reportInvalidOther(by & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby2 = this._inputBuffer;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        byte by2 = arrby2[n3];
        if ((by2 & 192) != 128) {
            this._reportInvalidOther(by2 & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby3 = this._inputBuffer;
        int n4 = this._inputPtr;
        this._inputPtr = n4 + 1;
        byte by3 = arrby3[n4];
        if ((by3 & 192) != 128) {
            this._reportInvalidOther(by3 & 255, this._inputPtr);
        }
    }

    private final int _skipWS() throws IOException, JsonParseException {
        while (this._inputPtr < this._inputEnd || this.loadMore()) {
            byte[] arrby = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            int n2 = 255 & arrby[n];
            if (n2 > 32) {
                if (n2 != 47) {
                    return n2;
                }
                this._skipComment();
                continue;
            }
            if (n2 == 32) continue;
            if (n2 == 10) {
                this._skipLF();
                continue;
            }
            if (n2 == 13) {
                this._skipCR();
                continue;
            }
            if (n2 == 9) continue;
            this._throwInvalidSpace(n2);
        }
        throw this._constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
    }

    private final int _skipWSOrEnd() throws IOException, JsonParseException {
        while (this._inputPtr < this._inputEnd || this.loadMore()) {
            byte[] arrby = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            int n2 = 255 & arrby[n];
            if (n2 > 32) {
                if (n2 != 47) {
                    return n2;
                }
                this._skipComment();
                continue;
            }
            if (n2 == 32) continue;
            if (n2 == 10) {
                this._skipLF();
                continue;
            }
            if (n2 == 13) {
                this._skipCR();
                continue;
            }
            if (n2 == 9) continue;
            this._throwInvalidSpace(n2);
        }
        this._handleEOF();
        return -1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final int _verifyNoLeadingZeroes() throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            return 48;
        }
        int n = 255 & this._inputBuffer[this._inputPtr];
        if (n < 48) return 48;
        if (n > 57) {
            return 48;
        }
        if (!this.isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            this.reportInvalidNumber("Leading zeroes not allowed");
        }
        this._inputPtr = 1 + this._inputPtr;
        if (n != 48) return n;
        do {
            if (this._inputPtr >= this._inputEnd) {
                if (!this.loadMore()) return n;
            }
            if ((n = 255 & this._inputBuffer[this._inputPtr]) < 48) return 48;
            if (n > 57) {
                return 48;
            }
            this._inputPtr = 1 + this._inputPtr;
        } while (n == 48);
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final Name addName(int[] arrn, int n, int n2) throws JsonParseException {
        int n3;
        int n4 = n2 + (-4 + (n << 2));
        if (n2 < 4) {
            n3 = arrn[n - 1];
            arrn[n - 1] = n3 << (4 - n2 << 3);
        } else {
            n3 = 0;
        }
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int n5 = 0;
        int n6 = 0;
        do {
            int n7;
            int n8;
            block20 : {
                block19 : {
                    block18 : {
                        int n9;
                        int n10;
                        if (n5 >= n4) break block18;
                        n7 = 255 & arrn[n5 >> 2] >> (3 - (n5 & 3) << 3);
                        ++n5;
                        if (n7 <= 127) break block19;
                        if ((n7 & 224) == 192) {
                            n9 = n7 & 31;
                            n10 = 1;
                        } else if ((n7 & 240) == 224) {
                            n9 = n7 & 15;
                            n10 = 2;
                        } else if ((n7 & 248) == 240) {
                            n9 = n7 & 7;
                            n10 = 3;
                        } else {
                            this._reportInvalidInitial(n7);
                            n10 = n9 = 1;
                        }
                        if (n5 + n10 > n4) {
                            this._reportInvalidEOF(" in field name");
                        }
                        int n11 = arrn[n5 >> 2] >> (3 - (n5 & 3) << 3);
                        ++n5;
                        if ((n11 & 192) != 128) {
                            this._reportInvalidOther(n11);
                        }
                        n7 = n9 << 6 | n11 & 63;
                        if (n10 > 1) {
                            int n12 = arrn[n5 >> 2] >> (3 - (n5 & 3) << 3);
                            ++n5;
                            if ((n12 & 192) != 128) {
                                this._reportInvalidOther(n12);
                            }
                            n7 = n7 << 6 | n12 & 63;
                            if (n10 > 2) {
                                int n13 = arrn[n5 >> 2] >> (3 - (n5 & 3) << 3);
                                ++n5;
                                if ((n13 & 192) != 128) {
                                    this._reportInvalidOther(n13 & 255);
                                }
                                n7 = n7 << 6 | n13 & 63;
                            }
                        }
                        if (n10 <= 2) break block19;
                        int n14 = n7 - 65536;
                        if (n6 >= arrc.length) {
                            arrc = this._textBuffer.expandCurrentSegment();
                        }
                        n8 = n6 + 1;
                        arrc[n6] = (char)(55296 + (n14 >> 10));
                        n7 = 56320 | n14 & 1023;
                        break block20;
                    }
                    String string = new String(arrc, 0, n6);
                    if (n2 < 4) {
                        arrn[n - 1] = n3;
                    }
                    return this._symbols.addName(string, arrn, n);
                }
                n8 = n6;
            }
            if (n8 >= arrc.length) {
                arrc = this._textBuffer.expandCurrentSegment();
            }
            n6 = n8 + 1;
            arrc[n8] = (char)n7;
        } while (true);
    }

    private final Name findName(int n, int n2) throws JsonParseException {
        Name name = this._symbols.findName(n);
        if (name != null) {
            return name;
        }
        this._quadBuffer[0] = n;
        return Utf8StreamParser.super.addName(this._quadBuffer, 1, n2);
    }

    private final Name findName(int n, int n2, int n3) throws JsonParseException {
        Name name = this._symbols.findName(n, n2);
        if (name != null) {
            return name;
        }
        this._quadBuffer[0] = n;
        this._quadBuffer[1] = n2;
        return Utf8StreamParser.super.addName(this._quadBuffer, 2, n3);
    }

    private final Name findName(int[] arrn, int n, int n2, int n3) throws JsonParseException {
        if (n >= arrn.length) {
            arrn = Utf8StreamParser.growArrayBy(arrn, arrn.length);
            this._quadBuffer = arrn;
        }
        int n4 = n + 1;
        arrn[n] = n2;
        Name name = this._symbols.findName(arrn, n4);
        if (name == null) {
            name = Utf8StreamParser.super.addName(arrn, n4, n3);
        }
        return name;
    }

    public static int[] growArrayBy(int[] arrn, int n) {
        if (arrn == null) {
            return new int[n];
        }
        int n2 = arrn.length;
        int[] arrn2 = new int[n2 + n];
        System.arraycopy((Object)arrn, (int)0, (Object)arrn2, (int)0, (int)n2);
        return arrn2;
    }

    private int nextByte() throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd) {
            this.loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        return 255 & arrby[n];
    }

    private final Name parseFieldName(int n, int n2, int n3) throws IOException, JsonParseException {
        return this.parseEscapedFieldName(this._quadBuffer, 0, n, n2, n3);
    }

    private final Name parseFieldName(int n, int n2, int n3, int n4) throws IOException, JsonParseException {
        this._quadBuffer[0] = n;
        return this.parseEscapedFieldName(this._quadBuffer, 1, n2, n3, n4);
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

    protected byte[] _decodeBase64(Base64Variant base64Variant) throws IOException, JsonParseException {
        ByteArrayBuilder byteArrayBuilder = this._getByteArrayBuilder();
        do {
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            byte[] arrby = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            int n2 = 255 & arrby[n];
            if (n2 <= 32) continue;
            int n3 = base64Variant.decodeBase64Char(n2);
            if (n3 < 0) {
                if (n2 == 34) {
                    return byteArrayBuilder.toByteArray();
                }
                n3 = this._decodeBase64Escape(base64Variant, n2, 0);
                if (n3 < 0) continue;
            }
            int n4 = n3;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            byte[] arrby2 = this._inputBuffer;
            int n5 = this._inputPtr;
            this._inputPtr = n5 + 1;
            int n6 = 255 & arrby2[n5];
            int n7 = base64Variant.decodeBase64Char(n6);
            if (n7 < 0) {
                n7 = this._decodeBase64Escape(base64Variant, n6, 1);
            }
            int n8 = n7 | n4 << 6;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            byte[] arrby3 = this._inputBuffer;
            int n9 = this._inputPtr;
            this._inputPtr = n9 + 1;
            int n10 = 255 & arrby3[n9];
            int n11 = base64Variant.decodeBase64Char(n10);
            if (n11 < 0) {
                if (n11 != -2) {
                    if (n10 == 34 && !base64Variant.usesPadding()) {
                        byteArrayBuilder.append(n8 >> 4);
                        return byteArrayBuilder.toByteArray();
                    }
                    n11 = this._decodeBase64Escape(base64Variant, n10, 2);
                }
                if (n11 == -2) {
                    if (this._inputPtr >= this._inputEnd) {
                        this.loadMoreGuaranteed();
                    }
                    byte[] arrby4 = this._inputBuffer;
                    int n12 = this._inputPtr;
                    this._inputPtr = n12 + 1;
                    int n13 = 255 & arrby4[n12];
                    if (!base64Variant.usesPaddingChar(n13)) {
                        throw this.reportInvalidBase64Char(base64Variant, n13, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                    }
                    byteArrayBuilder.append(n8 >> 4);
                    continue;
                }
            }
            int n14 = n11 | n8 << 6;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            byte[] arrby5 = this._inputBuffer;
            int n15 = this._inputPtr;
            this._inputPtr = n15 + 1;
            int n16 = 255 & arrby5[n15];
            int n17 = base64Variant.decodeBase64Char(n16);
            if (n17 < 0) {
                if (n17 != -2) {
                    if (n16 == 34 && !base64Variant.usesPadding()) {
                        byteArrayBuilder.appendTwoBytes(n14 >> 2);
                        return byteArrayBuilder.toByteArray();
                    }
                    n17 = this._decodeBase64Escape(base64Variant, n16, 3);
                }
                if (n17 == -2) {
                    byteArrayBuilder.appendTwoBytes(n14 >> 2);
                    continue;
                }
            }
            byteArrayBuilder.appendThreeBytes(n17 | n14 << 6);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int _decodeCharForError(int n) throws IOException, JsonParseException {
        int n2;
        int n3;
        int n4 = n;
        if (n4 >= 0) return n4;
        if ((n4 & 224) == 192) {
            n4 &= 31;
            n3 = 1;
        } else if ((n4 & 240) == 224) {
            n4 &= 15;
            n3 = 2;
        } else if ((n4 & 248) == 240) {
            n4 &= 7;
            n3 = 3;
        } else {
            this._reportInvalidInitial(n4 & 255);
            n3 = 1;
        }
        if (((n2 = Utf8StreamParser.super.nextByte()) & 192) != 128) {
            this._reportInvalidOther(n2 & 255);
        }
        n4 = n4 << 6 | n2 & 63;
        if (n3 <= 1) return n4;
        int n5 = Utf8StreamParser.super.nextByte();
        if ((n5 & 192) != 128) {
            this._reportInvalidOther(n5 & 255);
        }
        n4 = n4 << 6 | n5 & 63;
        if (n3 <= 2) return n4;
        int n6 = Utf8StreamParser.super.nextByte();
        if ((n6 & 192) == 128) return n4 << 6 | n6 & 63;
        this._reportInvalidOther(n6 & 255);
        return n4 << 6 | n6 & 63;
    }

    @Override
    protected final char _decodeEscaped() throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(" in character escape sequence");
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        byte by = arrby[n];
        switch (by) {
            default: {
                return this._handleUnrecognizedCharacterEscape((char)this._decodeCharForError(by));
            }
            case 98: {
                return '\b';
            }
            case 116: {
                return '\t';
            }
            case 110: {
                return '\n';
            }
            case 102: {
                return '\f';
            }
            case 114: {
                return '\r';
            }
            case 34: 
            case 47: 
            case 92: {
                return (char)by;
            }
            case 117: 
        }
        int n2 = 0;
        for (int i = 0; i < 4; ++i) {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(" in character escape sequence");
            }
            byte[] arrby2 = this._inputBuffer;
            int n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            byte by2 = arrby2[n3];
            int n4 = CharTypes.charToHex(by2);
            if (n4 < 0) {
                this._reportUnexpectedChar(by2, "expected a hex-digit for character escape sequence");
            }
            n2 = n4 | n2 << 4;
        }
        return (char)n2;
    }

    @Override
    protected void _finishString() throws IOException, JsonParseException {
        int n = this._inputPtr;
        if (n >= this._inputEnd) {
            this.loadMoreGuaranteed();
            n = this._inputPtr;
        }
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int[] arrn = sInputCodesUtf8;
        int n2 = Math.min((int)this._inputEnd, (int)(n + arrc.length));
        byte[] arrby = this._inputBuffer;
        int n3 = 0;
        while (n < n2) {
            int n4 = 255 & arrby[n];
            if (arrn[n4] != 0) {
                if (n4 != 34) break;
                this._inputPtr = n + 1;
                this._textBuffer.setCurrentLength(n3);
                return;
            }
            ++n;
            int n5 = n3 + 1;
            arrc[n3] = (char)n4;
            n3 = n5;
        }
        this._inputPtr = n;
        this._finishString2(arrc, n3);
    }

    protected final String _getText2(JsonToken jsonToken) {
        if (jsonToken == null) {
            return null;
        }
        switch (jsonToken) {
            default: {
                return jsonToken.asString();
            }
            case FIELD_NAME: {
                return this._parsingContext.getCurrentName();
            }
            case VALUE_STRING: 
            case VALUE_NUMBER_INT: 
            case VALUE_NUMBER_FLOAT: 
        }
        return this._textBuffer.contentsAsString();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonToken _handleApostropheValue() throws IOException, JsonParseException {
        int n = 0;
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int[] arrn = sInputCodesUtf8;
        byte[] arrby = this._inputBuffer;
        block6 : do {
            int n2;
            int n3;
            int n4;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            if (n >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n = 0;
            }
            if ((n3 = this._inputPtr + (arrc.length - n)) < (n4 = this._inputEnd)) {
                n4 = n3;
            }
            do {
                if (this._inputPtr >= n4) continue block6;
                int n5 = this._inputPtr;
                this._inputPtr = n5 + 1;
                n2 = 255 & arrby[n5];
                if (n2 == 39 || arrn[n2] != 0) {
                    if (n2 != 39) break;
                    this._textBuffer.setCurrentLength(n);
                    return JsonToken.VALUE_STRING;
                }
                int n6 = n + 1;
                arrc[n] = (char)n2;
                n = n6;
            } while (true);
            switch (arrn[n2]) {
                default: {
                    if (n2 < 32) {
                        this._throwUnquotedSpace(n2, "string value");
                    }
                    this._reportInvalidChar(n2);
                    break;
                }
                case 1: {
                    if (n2 == 34) break;
                    n2 = this._decodeEscaped();
                    break;
                }
                case 2: {
                    n2 = this._decodeUtf8_2(n2);
                    break;
                }
                case 3: {
                    if (this._inputEnd - this._inputPtr >= 2) {
                        n2 = this._decodeUtf8_3fast(n2);
                        break;
                    }
                    n2 = this._decodeUtf8_3(n2);
                    break;
                }
                case 4: {
                    int n7 = this._decodeUtf8_4(n2);
                    int n8 = n + 1;
                    arrc[n] = (char)(55296 | n7 >> 10);
                    if (n8 >= arrc.length) {
                        arrc = this._textBuffer.finishCurrentSegment();
                        n = 0;
                    } else {
                        n = n8;
                    }
                    n2 = 56320 | n7 & 1023;
                }
            }
            if (n >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n = 0;
            }
            int n9 = n + 1;
            arrc[n] = (char)n2;
            n = n9;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonToken _handleInvalidNumberStart(int n, boolean bl) throws IOException, JsonParseException {
        double d = Double.NEGATIVE_INFINITY;
        if (n == 73) {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOFInValue();
            }
            byte[] arrby = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            n = arrby[n2];
            if (n == 78) {
                String string = bl ? "-INF" : "+INF";
                this._matchToken(string, 3);
                if (this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    if (bl) {
                        return this.resetAsNaN(string, d);
                    }
                    d = Double.POSITIVE_INFINITY;
                    return this.resetAsNaN(string, d);
                }
                this._reportError("Non-standard token '" + string + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            } else if (n == 110) {
                String string = bl ? "-Infinity" : "+Infinity";
                this._matchToken(string, 3);
                if (this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    if (bl) {
                        return this.resetAsNaN(string, d);
                    }
                    d = Double.POSITIVE_INFINITY;
                    return this.resetAsNaN(string, d);
                }
                this._reportError("Non-standard token '" + string + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            }
        }
        this.reportUnexpectedNumberChar(n, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected JsonToken _handleUnexpectedValue(int var1) throws IOException, JsonParseException {
        switch (var1) {
            case 39: {
                if (this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
                    return this._handleApostropheValue();
                }
                ** GOTO lbl11
            }
            case 78: {
                this._matchToken("NaN", 1);
                if (this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN("NaN", Double.NaN);
                }
                this._reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            }
lbl11: // 3 sources:
            default: {
                this._reportUnexpectedChar(var1, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
                return null;
            }
            case 43: 
        }
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOFInValue();
        }
        var2_2 = this._inputBuffer;
        var3_3 = this._inputPtr;
        this._inputPtr = var3_3 + 1;
        return this._handleInvalidNumberStart(255 & var2_2[var3_3], false);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final Name _handleUnusualFieldName(int n) throws IOException, JsonParseException {
        int[] arrn;
        if (n == 39 && this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
            return this._parseApostropheFieldName();
        }
        if (!this.isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            this._reportUnexpectedChar(n, "was expecting double-quote to start field name");
        }
        if ((arrn = CharTypes.getInputCodeUtf8JsNames())[n] != 0) {
            this._reportUnexpectedChar(n, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int[] arrn2 = this._quadBuffer;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        do {
            int n5;
            if (n3 < 4) {
                ++n3;
                n2 = n | n2 << 8;
                n5 = n4;
            } else {
                if (n4 >= arrn2.length) {
                    this._quadBuffer = arrn2 = Utf8StreamParser.growArrayBy(arrn2, arrn2.length);
                }
                n5 = n4 + 1;
                arrn2[n4] = n2;
                n2 = n;
                n3 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(" in field name");
            }
            if (arrn[n = 255 & this._inputBuffer[this._inputPtr]] != 0) {
                Name name;
                if (n3 > 0) {
                    if (n5 >= arrn2.length) {
                        this._quadBuffer = arrn2 = Utf8StreamParser.growArrayBy(arrn2, arrn2.length);
                    }
                    int n6 = n5 + 1;
                    arrn2[n5] = n2;
                    n5 = n6;
                }
                if ((name = this._symbols.findName(arrn2, n5)) != null) return name;
                return Utf8StreamParser.super.addName(arrn2, n5, n3);
            }
            this._inputPtr = 1 + this._inputPtr;
            n4 = n5;
        } while (true);
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

    /*
     * Enabled aggressive block sorting
     */
    protected final void _matchToken(String string, int n) throws IOException, JsonParseException {
        int n2;
        int n3 = string.length();
        do {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(" in a value");
            }
            if (this._inputBuffer[this._inputPtr] != string.charAt(n)) {
                this._reportInvalidToken(string.substring(0, n), "'null', 'true', 'false' or NaN");
            }
            this._inputPtr = 1 + this._inputPtr;
        } while (++n < n3);
        if (this._inputPtr >= this._inputEnd && !this.loadMore() || (n2 = 255 & this._inputBuffer[this._inputPtr]) < 48 || n2 == 93 || n2 == 125 || !Character.isJavaIdentifierPart((char)((char)this._decodeCharForError(n2)))) {
            return;
        }
        this._inputPtr = 1 + this._inputPtr;
        this._reportInvalidToken(string.substring(0, n), "'null', 'true', 'false' or NaN");
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final Name _parseApostropheFieldName() throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(": was expecting closing ''' for name");
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        int n2 = 255 & arrby[n];
        if (n2 == 39) {
            return BytesToNameCanonicalizer.getEmptyName();
        }
        int[] arrn = this._quadBuffer;
        int n3 = 0;
        int n4 = 0;
        int[] arrn2 = sInputCodesLatin1;
        int n5 = 0;
        do {
            int n6;
            block16 : {
                int n7;
                block18 : {
                    int n8;
                    int n9;
                    block20 : {
                        block19 : {
                            Name name;
                            int n10;
                            block15 : {
                                block14 : {
                                    block17 : {
                                        block13 : {
                                            if (n2 != 39) break block13;
                                            if (n4 <= 0) break block14;
                                            if (n5 >= arrn.length) {
                                                this._quadBuffer = arrn = Utf8StreamParser.growArrayBy(arrn, arrn.length);
                                            }
                                            n10 = n5 + 1;
                                            arrn[n5] = n3;
                                            break block15;
                                        }
                                        if (n2 == 34 || arrn2[n2] == 0) break block16;
                                        if (n2 != 92) {
                                            this._throwUnquotedSpace(n2, "name");
                                        } else {
                                            n2 = this._decodeEscaped();
                                        }
                                        if (n2 <= 127) break block16;
                                        if (n4 >= 4) {
                                            if (n5 >= arrn.length) {
                                                this._quadBuffer = arrn = Utf8StreamParser.growArrayBy(arrn, arrn.length);
                                            }
                                            int n11 = n5 + 1;
                                            arrn[n5] = n3;
                                            n3 = 0;
                                            n4 = 0;
                                            n5 = n11;
                                        }
                                        if (n2 >= 2048) break block17;
                                        n3 = n3 << 8 | (192 | n2 >> 6);
                                        ++n4;
                                        n7 = n5;
                                        break block18;
                                    }
                                    n9 = n3 << 8 | (224 | n2 >> 12);
                                    n8 = n4 + 1;
                                    if (n8 < 4) break block19;
                                    if (n5 >= arrn.length) {
                                        this._quadBuffer = arrn = Utf8StreamParser.growArrayBy(arrn, arrn.length);
                                    }
                                    n7 = n5 + 1;
                                    arrn[n5] = n9;
                                    n9 = 0;
                                    n8 = 0;
                                    break block20;
                                }
                                n10 = n5;
                            }
                            if ((name = this._symbols.findName(arrn, n10)) != null) return name;
                            return this.addName(arrn, n10, n4);
                        }
                        n7 = n5;
                    }
                    n3 = n9 << 8 | (128 | 63 & n2 >> 6);
                    n4 = n8 + 1;
                }
                n2 = 128 | n2 & 63;
                n5 = n7;
            }
            if (n4 < 4) {
                ++n4;
                n3 = n2 | n3 << 8;
                n6 = n5;
            } else {
                if (n5 >= arrn.length) {
                    this._quadBuffer = arrn = Utf8StreamParser.growArrayBy(arrn, arrn.length);
                }
                n6 = n5 + 1;
                arrn[n5] = n3;
                n3 = n2;
                n4 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(" in field name");
            }
            byte[] arrby2 = this._inputBuffer;
            int n12 = this._inputPtr;
            this._inputPtr = n12 + 1;
            n2 = 255 & arrby2[n12];
            n5 = n6;
        } while (true);
    }

    protected final Name _parseFieldName(int n) throws IOException, JsonParseException {
        if (n != 34) {
            return this._handleUnusualFieldName(n);
        }
        if (9 + this._inputPtr > this._inputEnd) {
            return this.slowParseFieldName();
        }
        byte[] arrby = this._inputBuffer;
        int[] arrn = sInputCodesLatin1;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        int n3 = 255 & arrby[n2];
        if (arrn[n3] == 0) {
            int n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            int n5 = 255 & arrby[n4];
            if (arrn[n5] == 0) {
                int n6 = n5 | n3 << 8;
                int n7 = this._inputPtr;
                this._inputPtr = n7 + 1;
                int n8 = 255 & arrby[n7];
                if (arrn[n8] == 0) {
                    int n9 = n8 | n6 << 8;
                    int n10 = this._inputPtr;
                    this._inputPtr = n10 + 1;
                    int n11 = 255 & arrby[n10];
                    if (arrn[n11] == 0) {
                        int n12 = n11 | n9 << 8;
                        int n13 = this._inputPtr;
                        this._inputPtr = n13 + 1;
                        int n14 = 255 & arrby[n13];
                        if (arrn[n14] == 0) {
                            this._quad1 = n12;
                            return this.parseMediumFieldName(n14, arrn);
                        }
                        if (n14 == 34) {
                            return Utf8StreamParser.super.findName(n12, 4);
                        }
                        return Utf8StreamParser.super.parseFieldName(n12, n14, 4);
                    }
                    if (n11 == 34) {
                        return Utf8StreamParser.super.findName(n9, 3);
                    }
                    return Utf8StreamParser.super.parseFieldName(n9, n11, 3);
                }
                if (n8 == 34) {
                    return Utf8StreamParser.super.findName(n6, 2);
                }
                return Utf8StreamParser.super.parseFieldName(n6, n8, 2);
            }
            if (n5 == 34) {
                return Utf8StreamParser.super.findName(n3, 1);
            }
            return Utf8StreamParser.super.parseFieldName(n3, n5, 1);
        }
        if (n3 == 34) {
            return BytesToNameCanonicalizer.getEmptyName();
        }
        return Utf8StreamParser.super.parseFieldName(0, n3, 0);
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

    protected void _reportInvalidChar(int n) throws JsonParseException {
        if (n < 32) {
            this._throwInvalidSpace(n);
        }
        this._reportInvalidInitial(n);
    }

    protected void _reportInvalidInitial(int n) throws JsonParseException {
        this._reportError("Invalid UTF-8 start byte 0x" + Integer.toHexString((int)n));
    }

    protected void _reportInvalidOther(int n) throws JsonParseException {
        this._reportError("Invalid UTF-8 middle byte 0x" + Integer.toHexString((int)n));
    }

    protected void _reportInvalidOther(int n, int n2) throws JsonParseException {
        this._inputPtr = n2;
        this._reportInvalidOther(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _reportInvalidToken(String string, String string2) throws IOException, JsonParseException {
        StringBuilder stringBuilder = new StringBuilder(string);
        do {
            char c;
            block4 : {
                block3 : {
                    if (this._inputPtr >= this._inputEnd && !this.loadMore()) break block3;
                    byte[] arrby = this._inputBuffer;
                    int n = this._inputPtr;
                    this._inputPtr = n + 1;
                    c = (char)this._decodeCharForError(arrby[n]);
                    if (Character.isJavaIdentifierPart((char)c)) break block4;
                }
                this._reportError("Unrecognized token '" + stringBuilder.toString() + "': was expecting " + string2);
                return;
            }
            this._inputPtr = 1 + this._inputPtr;
            stringBuilder.append(c);
        } while (true);
    }

    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || this.loadMore()) && this._inputBuffer[this._inputPtr] == 10) {
            this._inputPtr = 1 + this._inputPtr;
        }
        this._currInputRow = 1 + this._currInputRow;
        this._currInputRowStart = this._inputPtr;
    }

    protected final void _skipLF() throws IOException {
        this._currInputRow = 1 + this._currInputRow;
        this._currInputRowStart = this._inputPtr;
    }

    protected void _skipString() throws IOException, JsonParseException {
        int n;
        int n2;
        this._tokenIncomplete = false;
        int[] arrn = sInputCodesUtf8;
        byte[] arrby = this._inputBuffer;
        block6 : while ((n2 = this._inputPtr) >= (n = this._inputEnd)) {
            this.loadMoreGuaranteed();
            int n3 = this._inputPtr;
            n = this._inputEnd;
            int n4 = n3;
            do {
                block10 : {
                    int n5;
                    block11 : {
                        block9 : {
                            if (n4 >= n) break block9;
                            n2 = n4 + 1;
                            n5 = 255 & arrby[n4];
                            if (arrn[n5] == 0) break block10;
                            this._inputPtr = n2;
                            if (n5 == 34) {
                                return;
                            }
                            break block11;
                        }
                        this._inputPtr = n4;
                        continue block6;
                    }
                    switch (arrn[n5]) {
                        default: {
                            if (n5 >= 32) break;
                            this._throwUnquotedSpace(n5, "string value");
                            continue block6;
                        }
                        case 1: {
                            this._decodeEscaped();
                            continue block6;
                        }
                        case 2: {
                            this._skipUtf8_2(n5);
                            continue block6;
                        }
                        case 3: {
                            this._skipUtf8_3(n5);
                            continue block6;
                        }
                        case 4: {
                            this._skipUtf8_4(n5);
                            continue block6;
                        }
                    }
                    this._reportInvalidChar(n5);
                    continue block6;
                }
                n4 = n2;
            } while (true);
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        this._symbols.release();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException, JsonParseException {
        if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
            this._reportError("Current token (" + (Object)((Object)this._currToken) + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
        }
        if (!this._tokenIncomplete) {
            if (this._binaryValue != null) return this._binaryValue;
            ByteArrayBuilder byteArrayBuilder = this._getByteArrayBuilder();
            this._decodeBase64(this.getText(), byteArrayBuilder, base64Variant);
            this._binaryValue = byteArrayBuilder.toByteArray();
            return this._binaryValue;
        }
        try {
            this._binaryValue = this._decodeBase64(base64Variant);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw this._constructError("Failed to decode VALUE_STRING as base64 (" + base64Variant + "): " + illegalArgumentException.getMessage());
        }
        this._tokenIncomplete = false;
        return this._binaryValue;
    }

    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override
    public Object getInputSource() {
        return this._inputStream;
    }

    @Override
    public String getText() throws IOException, JsonParseException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        return this._getText2(jsonToken);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public char[] getTextCharacters() throws IOException, JsonParseException {
        if (this._currToken == null) {
            return null;
        }
        switch (this._currToken) {
            default: {
                return this._currToken.asCharArray();
            }
            case FIELD_NAME: {
                if (!this._nameCopied) {
                    String string = this._parsingContext.getCurrentName();
                    int n = string.length();
                    if (this._nameCopyBuffer == null) {
                        this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(n);
                    } else if (this._nameCopyBuffer.length < n) {
                        this._nameCopyBuffer = new char[n];
                    }
                    string.getChars(0, n, this._nameCopyBuffer, 0);
                    this._nameCopied = true;
                }
                return this._nameCopyBuffer;
            }
            case VALUE_STRING: {
                if (!this._tokenIncomplete) break;
                this._tokenIncomplete = false;
                this._finishString();
            }
            case VALUE_NUMBER_INT: 
            case VALUE_NUMBER_FLOAT: 
        }
        return this._textBuffer.getTextBuffer();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int getTextLength() throws IOException, JsonParseException {
        JsonToken jsonToken = this._currToken;
        int n = 0;
        if (jsonToken == null) return n;
        switch (this._currToken) {
            default: {
                return this._currToken.asCharArray().length;
            }
            case FIELD_NAME: {
                return this._parsingContext.getCurrentName().length();
            }
            case VALUE_STRING: {
                if (!this._tokenIncomplete) return this._textBuffer.size();
                this._tokenIncomplete = false;
                this._finishString();
            }
            case VALUE_NUMBER_INT: 
            case VALUE_NUMBER_FLOAT: 
        }
        return this._textBuffer.size();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int getTextOffset() throws IOException, JsonParseException {
        if (this._currToken == null) return 0;
        switch (this._currToken) {
            default: {
                return 0;
            }
            case VALUE_STRING: {
                if (!this._tokenIncomplete) return this._textBuffer.getTextOffset();
                this._tokenIncomplete = false;
                this._finishString();
            }
            case VALUE_NUMBER_INT: 
            case VALUE_NUMBER_FLOAT: 
        }
        return this._textBuffer.getTextOffset();
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Boolean nextBooleanValue() throws IOException, JsonParseException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (jsonToken == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            }
            JsonToken jsonToken2 = JsonToken.START_OBJECT;
            Boolean bl = null;
            if (jsonToken != jsonToken2) return bl;
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return null;
        }
        switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[this.nextToken().ordinal()]) {
            default: {
                return null;
            }
            case 5: {
                return Boolean.TRUE;
            }
            case 6: 
        }
        return Boolean.FALSE;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean nextFieldName(SerializableString serializableString) throws IOException, JsonParseException {
        int n;
        int n2;
        byte[] arrby;
        int n3;
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            Utf8StreamParser.super._nextAfterName();
            return false;
        }
        if (this._tokenIncomplete) {
            this._skipString();
        }
        if ((n3 = Utf8StreamParser.super._skipWSOrEnd()) < 0) {
            this.close();
            this._currToken = null;
            return false;
        }
        this._tokenInputTotal = this._currInputProcessed + (long)this._inputPtr - 1L;
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = -1 + (this._inputPtr - this._currInputRowStart);
        this._binaryValue = null;
        if (n3 == 93) {
            if (!this._parsingContext.inArray()) {
                this._reportMismatchedEndMarker(n3, '}');
            }
            this._parsingContext = this._parsingContext.getParent();
            this._currToken = JsonToken.END_ARRAY;
            return false;
        }
        if (n3 == 125) {
            if (!this._parsingContext.inObject()) {
                this._reportMismatchedEndMarker(n3, ']');
            }
            this._parsingContext = this._parsingContext.getParent();
            this._currToken = JsonToken.END_OBJECT;
            return false;
        }
        if (this._parsingContext.expectComma()) {
            if (n3 != 44) {
                this._reportUnexpectedChar(n3, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
            }
            n3 = Utf8StreamParser.super._skipWS();
        }
        if (!this._parsingContext.inObject()) {
            Utf8StreamParser.super._nextTokenNotInObject(n3);
            return false;
        }
        if (n3 == 34 && (n2 = (arrby = serializableString.asQuotedUTF8()).length) + this._inputPtr < this._inputEnd && this._inputBuffer[n = n2 + this._inputPtr] == 34) {
            int n4 = 0;
            int n5 = this._inputPtr;
            do {
                if (n4 == n2) {
                    this._inputPtr = n + 1;
                    this._parsingContext.setCurrentName(serializableString.getValue());
                    this._currToken = JsonToken.FIELD_NAME;
                    Utf8StreamParser.super._isNextTokenNameYes();
                    return true;
                }
                if (arrby[n4] != this._inputBuffer[n5 + n4]) break;
                ++n4;
            } while (true);
        }
        Utf8StreamParser.super._isNextTokenNameNo(n3);
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int nextIntValue(int n) throws IOException, JsonParseException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
                return this.getIntValue();
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return n;
            }
            if (jsonToken != JsonToken.START_OBJECT) return n;
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return n;
        }
        if (this.nextToken() != JsonToken.VALUE_NUMBER_INT) return n;
        return this.getIntValue();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public long nextLongValue(long l) throws IOException, JsonParseException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
                return this.getLongValue();
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return l;
            }
            if (jsonToken != JsonToken.START_OBJECT) return l;
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return l;
        }
        if (this.nextToken() != JsonToken.VALUE_NUMBER_INT) return l;
        return this.getLongValue();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String nextTextValue() throws IOException, JsonParseException {
        String string;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_STRING) {
                if (!this._tokenIncomplete) return this._textBuffer.contentsAsString();
                this._tokenIncomplete = false;
                this._finishString();
                return this._textBuffer.contentsAsString();
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            }
            JsonToken jsonToken2 = JsonToken.START_OBJECT;
            string = null;
            if (jsonToken != jsonToken2) return string;
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return null;
        }
        JsonToken jsonToken = this.nextToken();
        JsonToken jsonToken3 = JsonToken.VALUE_STRING;
        string = null;
        if (jsonToken != jsonToken3) return string;
        return this.getText();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonToken nextToken() throws IOException, JsonParseException {
        int n;
        int n2;
        JsonToken jsonToken;
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._nextAfterName();
        }
        if (this._tokenIncomplete) {
            this._skipString();
        }
        if ((n2 = this._skipWSOrEnd()) < 0) {
            this.close();
            this._currToken = null;
            return null;
        }
        this._tokenInputTotal = this._currInputProcessed + (long)this._inputPtr - 1L;
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = -1 + (this._inputPtr - this._currInputRowStart);
        this._binaryValue = null;
        if (n2 == 93) {
            JsonToken jsonToken2;
            if (!this._parsingContext.inArray()) {
                this._reportMismatchedEndMarker(n2, '}');
            }
            this._parsingContext = this._parsingContext.getParent();
            this._currToken = jsonToken2 = JsonToken.END_ARRAY;
            return jsonToken2;
        }
        if (n2 == 125) {
            JsonToken jsonToken3;
            if (!this._parsingContext.inObject()) {
                this._reportMismatchedEndMarker(n2, ']');
            }
            this._parsingContext = this._parsingContext.getParent();
            this._currToken = jsonToken3 = JsonToken.END_OBJECT;
            return jsonToken3;
        }
        if (this._parsingContext.expectComma()) {
            if (n2 != 44) {
                this._reportUnexpectedChar(n2, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
            }
            n2 = this._skipWS();
        }
        if (!this._parsingContext.inObject()) {
            return this._nextTokenNotInObject(n2);
        }
        Name name = this._parseFieldName(n2);
        this._parsingContext.setCurrentName(name.getName());
        this._currToken = JsonToken.FIELD_NAME;
        int n3 = this._skipWS();
        if (n3 != 58) {
            this._reportUnexpectedChar(n3, "was expecting a colon to separate field name and value");
        }
        if ((n = this._skipWS()) == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return this._currToken;
        }
        switch (n) {
            default: {
                jsonToken = this._handleUnexpectedValue(n);
                break;
            }
            case 91: {
                jsonToken = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                jsonToken = JsonToken.START_OBJECT;
                break;
            }
            case 93: 
            case 125: {
                this._reportUnexpectedChar(n, "expected a value");
            }
            case 116: {
                this._matchToken("true", 1);
                jsonToken = JsonToken.VALUE_TRUE;
                break;
            }
            case 102: {
                this._matchToken("false", 1);
                jsonToken = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchToken("null", 1);
                jsonToken = JsonToken.VALUE_NULL;
                break;
            }
            case 45: 
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: {
                jsonToken = this.parseNumberText(n);
            }
        }
        this._nextToken = jsonToken;
        return this._currToken;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected Name parseEscapedFieldName(int[] var1_4, int var2_5, int var3_2, int var4_3, int var5_1) throws IOException, JsonParseException {
        var6_6 = Utf8StreamParser.sInputCodesLatin1;
        block0 : while (var6_6[var4_3] != 0) {
            if (var4_3 == 34) {
                if (var5_1 > 0) {
                    if (var2_5 >= var1_4.length) {
                        var1_4 = Utf8StreamParser.growArrayBy(var1_4, var1_4.length);
                        this._quadBuffer = var1_4;
                    }
                    var15_14 = var2_5 + 1;
                    var1_4[var2_5] = var3_2;
                    var2_5 = var15_14;
                }
                if ((var14_15 = this._symbols.findName(var1_4, var2_5)) != null) return var14_15;
                return this.addName(var1_4, var2_5, var5_1);
            }
            if (var4_3 != 92) {
                this._throwUnquotedSpace(var4_3, "name");
            } else {
                var4_3 = this._decodeEscaped();
            }
            if (var4_3 <= 127) break;
            if (var5_1 >= 4) {
                if (var2_5 >= var1_4.length) {
                    var1_4 = Utf8StreamParser.growArrayBy(var1_4, var1_4.length);
                    this._quadBuffer = var1_4;
                }
                var10_12 = var2_5 + 1;
                var1_4[var2_5] = var3_2;
                var3_2 = 0;
                var5_1 = 0;
            } else {
                var10_12 = var2_5;
            }
            if (var4_3 < 2048) {
                var3_2 = var3_2 << 8 | (192 | var4_3 >> 6);
                ++var5_1;
                var13_9 = var10_12;
            } else {
                var11_8 = var3_2 << 8 | (224 | var4_3 >> 12);
                var12_11 = var5_1 + 1;
                if (var12_11 >= 4) {
                    if (var10_12 >= var1_4.length) {
                        var1_4 = Utf8StreamParser.growArrayBy(var1_4, var1_4.length);
                        this._quadBuffer = var1_4;
                    }
                    var13_9 = var10_12 + 1;
                    var1_4[var10_12] = var11_8;
                    var11_8 = 0;
                    var12_11 = 0;
                } else {
                    var13_9 = var10_12;
                }
                var3_2 = var11_8 << 8 | (128 | 63 & var4_3 >> 6);
                var5_1 = var12_11 + 1;
            }
            var4_3 = 128 | var4_3 & 63;
            var7_13 = var13_9;
lbl49: // 2 sources:
            do {
                if (var5_1 < 4) {
                    ++var5_1;
                    var3_2 = var4_3 | var3_2 << 8;
                    var2_5 = var7_13;
                } else {
                    if (var7_13 >= var1_4.length) {
                        var1_4 = Utf8StreamParser.growArrayBy(var1_4, var1_4.length);
                        this._quadBuffer = var1_4;
                    }
                    var2_5 = var7_13 + 1;
                    var1_4[var7_13] = var3_2;
                    var3_2 = var4_3;
                    var5_1 = 1;
                }
                if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                    this._reportInvalidEOF(" in field name");
                }
                var8_7 = this._inputBuffer;
                var9_10 = this._inputPtr;
                this._inputPtr = var9_10 + 1;
                var4_3 = 255 & var8_7[var9_10];
                continue block0;
                break;
            } while (true);
        }
        var7_13 = var2_5;
        ** while (true)
    }

    protected Name parseLongFieldName(int n) throws IOException, JsonParseException {
        int[] arrn = sInputCodesLatin1;
        int n2 = 2;
        while (this._inputEnd - this._inputPtr >= 4) {
            byte[] arrby = this._inputBuffer;
            int n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            int n4 = 255 & arrby[n3];
            if (arrn[n4] != 0) {
                if (n4 == 34) {
                    return Utf8StreamParser.super.findName(this._quadBuffer, n2, n, 1);
                }
                return this.parseEscapedFieldName(this._quadBuffer, n2, n, n4, 1);
            }
            int n5 = n4 | n << 8;
            byte[] arrby2 = this._inputBuffer;
            int n6 = this._inputPtr;
            this._inputPtr = n6 + 1;
            int n7 = 255 & arrby2[n6];
            if (arrn[n7] != 0) {
                if (n7 == 34) {
                    return Utf8StreamParser.super.findName(this._quadBuffer, n2, n5, 2);
                }
                return this.parseEscapedFieldName(this._quadBuffer, n2, n5, n7, 2);
            }
            int n8 = n7 | n5 << 8;
            byte[] arrby3 = this._inputBuffer;
            int n9 = this._inputPtr;
            this._inputPtr = n9 + 1;
            int n10 = 255 & arrby3[n9];
            if (arrn[n10] != 0) {
                if (n10 == 34) {
                    return Utf8StreamParser.super.findName(this._quadBuffer, n2, n8, 3);
                }
                return this.parseEscapedFieldName(this._quadBuffer, n2, n8, n10, 3);
            }
            int n11 = n10 | n8 << 8;
            byte[] arrby4 = this._inputBuffer;
            int n12 = this._inputPtr;
            this._inputPtr = n12 + 1;
            int n13 = 255 & arrby4[n12];
            if (arrn[n13] != 0) {
                if (n13 == 34) {
                    return Utf8StreamParser.super.findName(this._quadBuffer, n2, n11, 4);
                }
                return this.parseEscapedFieldName(this._quadBuffer, n2, n11, n13, 4);
            }
            if (n2 >= this._quadBuffer.length) {
                this._quadBuffer = Utf8StreamParser.growArrayBy(this._quadBuffer, n2);
            }
            int[] arrn2 = this._quadBuffer;
            int n14 = n2 + 1;
            arrn2[n2] = n11;
            n = n13;
            n2 = n14;
        }
        return this.parseEscapedFieldName(this._quadBuffer, n2, 0, n, 0);
    }

    protected final Name parseMediumFieldName(int n, int[] arrn) throws IOException, JsonParseException {
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        int n3 = 255 & arrby[n2];
        if (arrn[n3] != 0) {
            if (n3 == 34) {
                return Utf8StreamParser.super.findName(this._quad1, n, 1);
            }
            return Utf8StreamParser.super.parseFieldName(this._quad1, n, n3, 1);
        }
        int n4 = n3 | n << 8;
        byte[] arrby2 = this._inputBuffer;
        int n5 = this._inputPtr;
        this._inputPtr = n5 + 1;
        int n6 = 255 & arrby2[n5];
        if (arrn[n6] != 0) {
            if (n6 == 34) {
                return Utf8StreamParser.super.findName(this._quad1, n4, 2);
            }
            return Utf8StreamParser.super.parseFieldName(this._quad1, n4, n6, 2);
        }
        int n7 = n6 | n4 << 8;
        byte[] arrby3 = this._inputBuffer;
        int n8 = this._inputPtr;
        this._inputPtr = n8 + 1;
        int n9 = 255 & arrby3[n8];
        if (arrn[n9] != 0) {
            if (n9 == 34) {
                return Utf8StreamParser.super.findName(this._quad1, n7, 3);
            }
            return Utf8StreamParser.super.parseFieldName(this._quad1, n7, n9, 3);
        }
        int n10 = n9 | n7 << 8;
        byte[] arrby4 = this._inputBuffer;
        int n11 = this._inputPtr;
        this._inputPtr = n11 + 1;
        int n12 = 255 & arrby4[n11];
        if (arrn[n12] != 0) {
            if (n12 == 34) {
                return Utf8StreamParser.super.findName(this._quad1, n10, 4);
            }
            return Utf8StreamParser.super.parseFieldName(this._quad1, n10, n12, 4);
        }
        this._quadBuffer[0] = this._quad1;
        this._quadBuffer[1] = n10;
        return this.parseLongFieldName(n12);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final JsonToken parseNumberText(int n) throws IOException, JsonParseException {
        int n2;
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        boolean bl = n == 45;
        if (bl) {
            n2 = 0 + 1;
            arrc[0] = 45;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            byte[] arrby = this._inputBuffer;
            int n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            n = 255 & arrby[n3];
            if (n < 48) return this._handleInvalidNumberStart(n, true);
            if (n > 57) {
                return this._handleInvalidNumberStart(n, true);
            }
        } else {
            n2 = 0;
        }
        if (n == 48) {
            n = Utf8StreamParser.super._verifyNoLeadingZeroes();
        }
        int n4 = n2 + 1;
        arrc[n2] = (char)n;
        int n5 = 1;
        int n6 = this._inputPtr + arrc.length;
        if (n6 > this._inputEnd) {
            n6 = this._inputEnd;
        }
        while (this._inputPtr < n6) {
            byte[] arrby = this._inputBuffer;
            int n7 = this._inputPtr;
            this._inputPtr = n7 + 1;
            int n8 = 255 & arrby[n7];
            if (n8 < 48 || n8 > 57) {
                if (n8 == 46) return Utf8StreamParser.super._parseFloatText(arrc, n4, n8, bl, n5);
                if (n8 == 101) return Utf8StreamParser.super._parseFloatText(arrc, n4, n8, bl, n5);
                if (n8 == 69) return Utf8StreamParser.super._parseFloatText(arrc, n4, n8, bl, n5);
                this._inputPtr = -1 + this._inputPtr;
                this._textBuffer.setCurrentLength(n4);
                return this.resetInt(bl, n5);
            }
            ++n5;
            int n9 = n4 + 1;
            arrc[n4] = (char)n8;
            n4 = n9;
        }
        return Utf8StreamParser.super._parserNumber2(arrc, n4, bl, n5);
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

    @Override
    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }

    protected Name slowParseFieldName() throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(": was expecting closing '\"' for name");
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        int n2 = 255 & arrby[n];
        if (n2 == 34) {
            return BytesToNameCanonicalizer.getEmptyName();
        }
        return this.parseEscapedFieldName(this._quadBuffer, 0, 0, n2, 0);
    }

}


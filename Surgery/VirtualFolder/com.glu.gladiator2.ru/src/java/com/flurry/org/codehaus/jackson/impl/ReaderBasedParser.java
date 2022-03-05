/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.Reader
 *  java.io.Writer
 *  java.lang.Boolean
 *  java.lang.Character
 *  java.lang.IllegalArgumentException
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.flurry.org.codehaus.jackson.impl;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.impl.JsonParserBase;
import com.flurry.org.codehaus.jackson.impl.JsonReadContext;
import com.flurry.org.codehaus.jackson.io.IOContext;
import com.flurry.org.codehaus.jackson.sym.CharsToNameCanonicalizer;
import com.flurry.org.codehaus.jackson.util.ByteArrayBuilder;
import com.flurry.org.codehaus.jackson.util.CharTypes;
import com.flurry.org.codehaus.jackson.util.TextBuffer;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public final class ReaderBasedParser
extends JsonParserBase {
    protected char[] _inputBuffer;
    protected ObjectCodec _objectCodec;
    protected Reader _reader;
    protected final CharsToNameCanonicalizer _symbols;
    protected boolean _tokenIncomplete = false;

    public ReaderBasedParser(IOContext iOContext, int n, Reader reader, ObjectCodec objectCodec, CharsToNameCanonicalizer charsToNameCanonicalizer) {
        super(iOContext, n);
        this._reader = reader;
        this._inputBuffer = iOContext.allocTokenBuffer();
        this._objectCodec = objectCodec;
        this._symbols = charsToNameCanonicalizer;
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

    /*
     * Enabled aggressive block sorting
     */
    private String _parseFieldName2(int n, int n2, int n3) throws IOException, JsonParseException {
        this._textBuffer.resetWithShared(this._inputBuffer, n, this._inputPtr - n);
        char[] arrc = this._textBuffer.getCurrentSegment();
        int n4 = this._textBuffer.getCurrentSegmentSize();
        do {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(": was expecting closing '" + (char)n3 + "' for name");
            }
            char[] arrc2 = this._inputBuffer;
            int n5 = this._inputPtr;
            this._inputPtr = n5 + 1;
            char c = arrc2[n5];
            char c2 = c;
            if (c2 <= '\\') {
                if (c2 == '\\') {
                    c = this._decodeEscaped();
                } else if (c2 <= n3) {
                    if (c2 == n3) {
                        this._textBuffer.setCurrentLength(n4);
                        TextBuffer textBuffer = this._textBuffer;
                        char[] arrc3 = textBuffer.getTextBuffer();
                        int n6 = textBuffer.getTextOffset();
                        int n7 = textBuffer.size();
                        return this._symbols.findSymbol(arrc3, n6, n7, n2);
                    }
                    if (c2 < ' ') {
                        this._throwUnquotedSpace(c2, "name");
                    }
                }
            }
            n2 = c2 + n2 * 31;
            int n8 = n4 + 1;
            arrc[n4] = c;
            if (n8 >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n4 = 0;
                continue;
            }
            n4 = n8;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private String _parseUnusualFieldName2(int n, int n2, int[] arrn) throws IOException, JsonParseException {
        this._textBuffer.resetWithShared(this._inputBuffer, n, this._inputPtr - n);
        char[] arrc = this._textBuffer.getCurrentSegment();
        int n3 = this._textBuffer.getCurrentSegmentSize();
        int n4 = arrn.length;
        while (this._inputPtr < this._inputEnd || this.loadMore()) {
            char c = this._inputBuffer[this._inputPtr];
            if (c <= n4) {
                if (arrn[c] != 0) break;
            } else if (!Character.isJavaIdentifierPart((char)c)) break;
            this._inputPtr = 1 + this._inputPtr;
            n2 = c + n2 * 31;
            int n5 = n3 + 1;
            arrc[n3] = c;
            if (n5 >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n3 = 0;
                continue;
            }
            n3 = n5;
        }
        this._textBuffer.setCurrentLength(n3);
        TextBuffer textBuffer = this._textBuffer;
        char[] arrc2 = textBuffer.getTextBuffer();
        int n6 = textBuffer.getTextOffset();
        int n7 = textBuffer.size();
        return this._symbols.findSymbol(arrc2, n6, n7, n2);
    }

    private final void _skipCComment() throws IOException, JsonParseException {
        do {
            char c;
            block7 : {
                block8 : {
                    block6 : {
                        if (this._inputPtr >= this._inputEnd && !this.loadMore()) break block6;
                        char[] arrc = this._inputBuffer;
                        int n = this._inputPtr;
                        this._inputPtr = n + 1;
                        c = arrc[n];
                        if (c > '*') continue;
                        if (c != '*') break block7;
                        if (this._inputPtr < this._inputEnd || this.loadMore()) break block8;
                    }
                    this._reportInvalidEOF(" in a comment");
                    return;
                }
                if (this._inputBuffer[this._inputPtr] != '/') continue;
                this._inputPtr = 1 + this._inputPtr;
                return;
            }
            if (c >= ' ') continue;
            if (c == '\n') {
                this._skipLF();
                continue;
            }
            if (c == '\r') {
                this._skipCR();
                continue;
            }
            if (c == '\t') continue;
            this._throwInvalidSpace(c);
        } while (true);
    }

    private final void _skipComment() throws IOException, JsonParseException {
        if (!this.isEnabled(JsonParser.Feature.ALLOW_COMMENTS)) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(" in a comment");
        }
        char[] arrc = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        char c = arrc[n];
        if (c == '/') {
            this._skipCppComment();
            return;
        }
        if (c == '*') {
            this._skipCComment();
            return;
        }
        this._reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
    }

    private final void _skipCppComment() throws IOException, JsonParseException {
        do {
            char c;
            block6 : {
                block5 : {
                    if (this._inputPtr >= this._inputEnd && !this.loadMore()) break block5;
                    char[] arrc = this._inputBuffer;
                    int n = this._inputPtr;
                    this._inputPtr = n + 1;
                    c = arrc[n];
                    if (c >= ' ') continue;
                    if (c != '\n') break block6;
                    this._skipLF();
                }
                return;
            }
            if (c == '\r') {
                this._skipCR();
                return;
            }
            if (c == '\t') continue;
            this._throwInvalidSpace(c);
        } while (true);
    }

    private final int _skipWS() throws IOException, JsonParseException {
        while (this._inputPtr < this._inputEnd || this.loadMore()) {
            char[] arrc = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            char c = arrc[n];
            if (c > ' ') {
                if (c != '/') {
                    return c;
                }
                this._skipComment();
                continue;
            }
            if (c == ' ') continue;
            if (c == '\n') {
                this._skipLF();
                continue;
            }
            if (c == '\r') {
                this._skipCR();
                continue;
            }
            if (c == '\t') continue;
            this._throwInvalidSpace(c);
        }
        throw this._constructError("Unexpected end-of-input within/between " + this._parsingContext.getTypeDesc() + " entries");
    }

    private final int _skipWSOrEnd() throws IOException, JsonParseException {
        int n;
        block5 : {
            while (this._inputPtr < this._inputEnd || this.loadMore()) {
                char[] arrc = this._inputBuffer;
                int n2 = this._inputPtr;
                this._inputPtr = n2 + 1;
                n = arrc[n2];
                if (n > 32) {
                    if (n == 47) {
                        this._skipComment();
                        continue;
                    }
                    break block5;
                }
                if (n == 32) continue;
                if (n == 10) {
                    this._skipLF();
                    continue;
                }
                if (n == 13) {
                    this._skipCR();
                    continue;
                }
                if (n == 9) continue;
                this._throwInvalidSpace(n);
            }
            this._handleEOF();
            n = -1;
        }
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final char _verifyNoLeadingZeroes() throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            return '0';
        }
        char c = this._inputBuffer[this._inputPtr];
        if (c < 48) return '0';
        if (c > '9') {
            return '0';
        }
        if (!this.isEnabled(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            this.reportInvalidNumber("Leading zeroes not allowed");
        }
        this._inputPtr = 1 + this._inputPtr;
        if (c != 48) return c;
        do {
            if (this._inputPtr >= this._inputEnd) {
                if (!this.loadMore()) return c;
            }
            if ((c = this._inputBuffer[this._inputPtr]) < 48) return '0';
            if (c > '9') {
                return '0';
            }
            this._inputPtr = 1 + this._inputPtr;
        } while (c == 48);
        return c;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final JsonToken parseNumberText2(boolean bl) throws IOException, JsonParseException {
        int n;
        int n2;
        boolean bl2;
        int n3;
        int n4;
        block32 : {
            char c;
            char c2;
            char[] arrc;
            block31 : {
                int n5;
                block30 : {
                    arrc = this._textBuffer.emptyAndGetCurrentSegment();
                    int n6 = 0;
                    if (bl) {
                        int n7 = 0 + 1;
                        arrc[0] = 45;
                        n6 = n7;
                    }
                    n4 = 0;
                    if (this._inputPtr < this._inputEnd) {
                        char[] arrc2 = this._inputBuffer;
                        int n8 = this._inputPtr;
                        this._inputPtr = n8 + 1;
                        c = arrc2[n8];
                    } else {
                        c = this.getNextChar("No digit following minus sign");
                    }
                    if (c == '0') {
                        c = ReaderBasedParser.super._verifyNoLeadingZeroes();
                    }
                    while (c >= '0' && c <= '9') {
                        ++n4;
                        if (n6 >= arrc.length) {
                            arrc = this._textBuffer.finishCurrentSegment();
                            n6 = 0;
                        }
                        n5 = n6 + 1;
                        arrc[n6] = c;
                        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                            c = '\u0000';
                            bl2 = true;
                            break block30;
                        }
                        char[] arrc3 = this._inputBuffer;
                        int n9 = this._inputPtr;
                        this._inputPtr = n9 + 1;
                        c = arrc3[n9];
                        n6 = n5;
                    }
                    n5 = n6;
                    bl2 = false;
                }
                if (n4 == 0) {
                    this.reportInvalidNumber("Missing integer part (next char " + ReaderBasedParser._getCharDesc(c) + ")");
                }
                n2 = 0;
                if (c != '.') {
                    n3 = n5;
                    n2 = 0;
                } else {
                    n3 = n5 + 1;
                    arrc[n5] = c;
                    do {
                        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                            bl2 = true;
                            break;
                        }
                        char[] arrc4 = this._inputBuffer;
                        int n10 = this._inputPtr;
                        this._inputPtr = n10 + 1;
                        c = arrc4[n10];
                        if (c < '0' || c > '9') break;
                        ++n2;
                        if (n3 >= arrc.length) {
                            arrc = this._textBuffer.finishCurrentSegment();
                            n3 = 0;
                        }
                        int n11 = n3 + 1;
                        arrc[n3] = c;
                        n3 = n11;
                    } while (true);
                    if (n2 == 0) {
                        this.reportUnexpectedNumberChar(c, "Decimal point not followed by a digit");
                    }
                }
                n = 0;
                if (c == 'e') break block31;
                n = 0;
                if (c != 'E') break block32;
            }
            if (n3 >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n3 = 0;
            }
            int n12 = n3 + 1;
            arrc[n3] = c;
            if (this._inputPtr < this._inputEnd) {
                char[] arrc5 = this._inputBuffer;
                int n13 = this._inputPtr;
                this._inputPtr = n13 + 1;
                c2 = arrc5[n13];
            } else {
                c2 = this.getNextChar("expected a digit for number exponent");
            }
            if (c2 == '-' || c2 == '+') {
                int n14;
                if (n12 >= arrc.length) {
                    arrc = this._textBuffer.finishCurrentSegment();
                    n14 = 0;
                } else {
                    n14 = n12;
                }
                int n15 = n14 + 1;
                arrc[n14] = c2;
                if (this._inputPtr < this._inputEnd) {
                    char[] arrc6 = this._inputBuffer;
                    int n16 = this._inputPtr;
                    this._inputPtr = n16 + 1;
                    c2 = arrc6[n16];
                } else {
                    c2 = this.getNextChar("expected a digit for number exponent");
                }
                n3 = n15;
            } else {
                n3 = n12;
                n = 0;
            }
            do {
                int n17;
                block34 : {
                    block33 : {
                        if (c2 > '9' || c2 < '0') break block33;
                        ++n;
                        if (n3 >= arrc.length) {
                            arrc = this._textBuffer.finishCurrentSegment();
                            n3 = 0;
                        }
                        n17 = n3 + 1;
                        arrc[n3] = c2;
                        if (this._inputPtr < this._inputEnd || this.loadMore()) break block34;
                        bl2 = true;
                        n3 = n17;
                    }
                    if (n != 0) break;
                    this.reportUnexpectedNumberChar(c2, "Exponent indicator not followed by a digit");
                    break;
                }
                char[] arrc7 = this._inputBuffer;
                int n18 = this._inputPtr;
                this._inputPtr = n18 + 1;
                c2 = arrc7[n18];
                n3 = n17;
            } while (true);
        }
        if (!bl2) {
            this._inputPtr = -1 + this._inputPtr;
        }
        this._textBuffer.setCurrentLength(n3);
        return this.reset(bl, n4, n2, n);
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

    protected byte[] _decodeBase64(Base64Variant base64Variant) throws IOException, JsonParseException {
        ByteArrayBuilder byteArrayBuilder = this._getByteArrayBuilder();
        do {
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            char[] arrc = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            char c = arrc[n];
            if (c <= ' ') continue;
            int n2 = base64Variant.decodeBase64Char(c);
            if (n2 < 0) {
                if (c == '\"') {
                    return byteArrayBuilder.toByteArray();
                }
                n2 = this._decodeBase64Escape(base64Variant, c, 0);
                if (n2 < 0) continue;
            }
            int n3 = n2;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            char[] arrc2 = this._inputBuffer;
            int n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            char c2 = arrc2[n4];
            int n5 = base64Variant.decodeBase64Char(c2);
            if (n5 < 0) {
                n5 = this._decodeBase64Escape(base64Variant, c2, 1);
            }
            int n6 = n5 | n3 << 6;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            char[] arrc3 = this._inputBuffer;
            int n7 = this._inputPtr;
            this._inputPtr = n7 + 1;
            char c3 = arrc3[n7];
            int n8 = base64Variant.decodeBase64Char(c3);
            if (n8 < 0) {
                if (n8 != -2) {
                    if (c3 == '\"' && !base64Variant.usesPadding()) {
                        byteArrayBuilder.append(n6 >> 4);
                        return byteArrayBuilder.toByteArray();
                    }
                    n8 = this._decodeBase64Escape(base64Variant, c3, 2);
                }
                if (n8 == -2) {
                    if (this._inputPtr >= this._inputEnd) {
                        this.loadMoreGuaranteed();
                    }
                    char[] arrc4 = this._inputBuffer;
                    int n9 = this._inputPtr;
                    this._inputPtr = n9 + 1;
                    char c4 = arrc4[n9];
                    if (!base64Variant.usesPaddingChar(c4)) {
                        throw this.reportInvalidBase64Char(base64Variant, c4, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                    }
                    byteArrayBuilder.append(n6 >> 4);
                    continue;
                }
            }
            int n10 = n8 | n6 << 6;
            if (this._inputPtr >= this._inputEnd) {
                this.loadMoreGuaranteed();
            }
            char[] arrc5 = this._inputBuffer;
            int n11 = this._inputPtr;
            this._inputPtr = n11 + 1;
            char c5 = arrc5[n11];
            int n12 = base64Variant.decodeBase64Char(c5);
            if (n12 < 0) {
                if (n12 != -2) {
                    if (c5 == '\"' && !base64Variant.usesPadding()) {
                        byteArrayBuilder.appendTwoBytes(n10 >> 2);
                        return byteArrayBuilder.toByteArray();
                    }
                    n12 = this._decodeBase64Escape(base64Variant, c5, 3);
                }
                if (n12 == -2) {
                    byteArrayBuilder.appendTwoBytes(n10 >> 2);
                    continue;
                }
            }
            byteArrayBuilder.appendThreeBytes(n12 | n10 << 6);
        } while (true);
    }

    @Override
    protected final char _decodeEscaped() throws IOException, JsonParseException {
        if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
            this._reportInvalidEOF(" in character escape sequence");
        }
        char[] arrc = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        char c = arrc[n];
        switch (c) {
            default: {
                c = this._handleUnrecognizedCharacterEscape(c);
            }
            case '\"': 
            case '/': 
            case '\\': {
                return c;
            }
            case 'b': {
                return '\b';
            }
            case 't': {
                return '\t';
            }
            case 'n': {
                return '\n';
            }
            case 'f': {
                return '\f';
            }
            case 'r': {
                return '\r';
            }
            case 'u': 
        }
        int n2 = 0;
        for (int i = 0; i < 4; ++i) {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(" in character escape sequence");
            }
            char[] arrc2 = this._inputBuffer;
            int n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            char c2 = arrc2[n3];
            int n4 = CharTypes.charToHex(c2);
            if (n4 < 0) {
                this._reportUnexpectedChar(c2, "expected a hex-digit for character escape sequence");
            }
            n2 = n4 | n2 << 4;
        }
        return (char)n2;
    }

    @Override
    protected void _finishString() throws IOException, JsonParseException {
        int n = this._inputPtr;
        int n2 = this._inputEnd;
        if (n < n2) {
            int[] arrn = CharTypes.getInputCodeLatin1();
            int n3 = arrn.length;
            do {
                char c;
                if ((c = this._inputBuffer[n]) >= n3 || arrn[c] == 0) continue;
                if (c != '\"') break;
                this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, n - this._inputPtr);
                this._inputPtr = n + 1;
                return;
            } while (++n < n2);
        }
        this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, n - this._inputPtr);
        this._inputPtr = n;
        this._finishString2();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _finishString2() throws IOException, JsonParseException {
        char[] arrc = this._textBuffer.getCurrentSegment();
        int n = this._textBuffer.getCurrentSegmentSize();
        do {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(": was expecting closing quote for a string value");
            }
            char[] arrc2 = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            char c = arrc2[n2];
            char c2 = c;
            if (c2 <= '\\') {
                if (c2 == '\\') {
                    c = this._decodeEscaped();
                } else if (c2 <= '\"') {
                    if (c2 == '\"') {
                        this._textBuffer.setCurrentLength(n);
                        return;
                    }
                    if (c2 < ' ') {
                        this._throwUnquotedSpace(c2, "string value");
                    }
                }
            }
            if (n >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n = 0;
            }
            int n3 = n + 1;
            arrc[n] = c;
            n = n3;
        } while (true);
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
    protected final JsonToken _handleApostropheValue() throws IOException, JsonParseException {
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int n = this._textBuffer.getCurrentSegmentSize();
        do {
            if (this._inputPtr >= this._inputEnd && !this.loadMore()) {
                this._reportInvalidEOF(": was expecting closing quote for a string value");
            }
            char[] arrc2 = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            char c = arrc2[n2];
            char c2 = c;
            if (c2 <= '\\') {
                if (c2 == '\\') {
                    c = this._decodeEscaped();
                } else if (c2 <= '\'') {
                    if (c2 == '\'') {
                        this._textBuffer.setCurrentLength(n);
                        return JsonToken.VALUE_STRING;
                    }
                    if (c2 < ' ') {
                        this._throwUnquotedSpace(c2, "string value");
                    }
                }
            }
            if (n >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n = 0;
            }
            int n3 = n + 1;
            arrc[n] = c;
            n = n3;
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
            char[] arrc = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            n = arrc[n2];
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
    protected final JsonToken _handleUnexpectedValue(int var1) throws IOException, JsonParseException {
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
        return this._handleInvalidNumberStart(var2_2[var3_3], false);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final String _handleUnusualFieldName(int n) throws IOException, JsonParseException {
        int[] arrn;
        int n2;
        if (n == 39 && this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
            return this._parseApostropheFieldName();
        }
        if (!this.isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            this._reportUnexpectedChar(n, "was expecting double-quote to start field name");
        }
        boolean bl = n < (n2 = (arrn = CharTypes.getInputCodeLatin1JsNames()).length) ? arrn[n] == 0 && (n < 48 || n > 57) : Character.isJavaIdentifierPart((char)((char)n));
        if (!bl) {
            this._reportUnexpectedChar(n, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int n3 = this._inputPtr;
        int n4 = this._inputEnd;
        int n5 = 0;
        if (n3 < n4) {
            do {
                char c;
                if ((c = this._inputBuffer[n3]) < n2) {
                    if (arrn[c] != 0) {
                        int n6 = -1 + this._inputPtr;
                        this._inputPtr = n3;
                        return this._symbols.findSymbol(this._inputBuffer, n6, n3 - n6, n5);
                    }
                } else if (!Character.isJavaIdentifierPart((char)c)) {
                    int n7 = -1 + this._inputPtr;
                    this._inputPtr = n3;
                    return this._symbols.findSymbol(this._inputBuffer, n7, n3 - n7, n5);
                }
                n5 = c + n5 * 31;
            } while (++n3 < n4);
        }
        int n8 = -1 + this._inputPtr;
        this._inputPtr = n3;
        return ReaderBasedParser.super._parseUnusualFieldName2(n8, n5, arrn);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _matchToken(String string, int n) throws IOException, JsonParseException {
        char c;
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
        if (this._inputPtr >= this._inputEnd && !this.loadMore() || (c = this._inputBuffer[this._inputPtr]) < '0' || c == ']' || c == '}' || !Character.isJavaIdentifierPart((char)c)) {
            return;
        }
        this._inputPtr = 1 + this._inputPtr;
        this._reportInvalidToken(string.substring(0, n), "'null', 'true', 'false' or NaN");
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final String _parseApostropheFieldName() throws IOException, JsonParseException {
        int n = this._inputPtr;
        int n2 = this._inputEnd;
        int n3 = 0;
        if (n < n2) {
            int[] arrn = CharTypes.getInputCodeLatin1();
            int n4 = arrn.length;
            do {
                char c;
                if ((c = this._inputBuffer[n]) == '\'') {
                    int n5 = this._inputPtr;
                    this._inputPtr = n + 1;
                    return this._symbols.findSymbol(this._inputBuffer, n5, n - n5, n3);
                }
                if (c < n4 && arrn[c] != 0) break;
                n3 = c + n3 * 31;
            } while (++n < n2);
        }
        int n6 = this._inputPtr;
        this._inputPtr = n;
        return this._parseFieldName2(n6, n3, 39);
    }

    protected final String _parseFieldName(int n) throws IOException, JsonParseException {
        if (n != 34) {
            return this._handleUnusualFieldName(n);
        }
        int n2 = this._inputPtr;
        int n3 = this._inputEnd;
        int n4 = 0;
        if (n2 < n3) {
            int[] arrn = CharTypes.getInputCodeLatin1();
            int n5 = arrn.length;
            do {
                char c;
                if ((c = this._inputBuffer[n2]) < n5 && arrn[c] != 0) {
                    if (c != '\"') break;
                    int n6 = this._inputPtr;
                    this._inputPtr = n2 + 1;
                    return this._symbols.findSymbol(this._inputBuffer, n6, n2 - n6, n4);
                }
                n4 = c + n4 * 31;
            } while (++n2 < n3);
        }
        int n7 = this._inputPtr;
        this._inputPtr = n2;
        return ReaderBasedParser.super._parseFieldName2(n7, n4, 34);
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

    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || this.loadMore()) && this._inputBuffer[this._inputPtr] == '\n') {
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
        this._tokenIncomplete = false;
        int n = this._inputPtr;
        int n2 = this._inputEnd;
        char[] arrc = this._inputBuffer;
        do {
            if (n >= n2) {
                this._inputPtr = n;
                if (!this.loadMore()) {
                    this._reportInvalidEOF(": was expecting closing quote for a string value");
                }
                n = this._inputPtr;
                n2 = this._inputEnd;
            }
            int n3 = n + 1;
            char c = arrc[n];
            if (c <= '\\') {
                if (c == '\\') {
                    this._inputPtr = n3;
                    this._decodeEscaped();
                    n = this._inputPtr;
                    n2 = this._inputEnd;
                    continue;
                }
                if (c <= '\"') {
                    if (c == '\"') {
                        this._inputPtr = n3;
                        return;
                    }
                    if (c < ' ') {
                        this._inputPtr = n3;
                        this._throwUnquotedSpace(c, "string value");
                    }
                }
            }
            n = n3;
        } while (true);
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

    @Override
    public final String getText() throws IOException, JsonParseException {
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
        boolean bl;
        int n;
        JsonToken jsonToken;
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._nextAfterName();
        }
        if (this._tokenIncomplete) {
            this._skipString();
        }
        if ((n = this._skipWSOrEnd()) < 0) {
            this.close();
            this._currToken = null;
            return null;
        }
        this._tokenInputTotal = this._currInputProcessed + (long)this._inputPtr - 1L;
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = -1 + (this._inputPtr - this._currInputRowStart);
        this._binaryValue = null;
        if (n == 93) {
            JsonToken jsonToken2;
            if (!this._parsingContext.inArray()) {
                this._reportMismatchedEndMarker(n, '}');
            }
            this._parsingContext = this._parsingContext.getParent();
            this._currToken = jsonToken2 = JsonToken.END_ARRAY;
            return jsonToken2;
        }
        if (n == 125) {
            JsonToken jsonToken3;
            if (!this._parsingContext.inObject()) {
                this._reportMismatchedEndMarker(n, ']');
            }
            this._parsingContext = this._parsingContext.getParent();
            this._currToken = jsonToken3 = JsonToken.END_OBJECT;
            return jsonToken3;
        }
        if (this._parsingContext.expectComma()) {
            if (n != 44) {
                this._reportUnexpectedChar(n, "was expecting comma to separate " + this._parsingContext.getTypeDesc() + " entries");
            }
            n = this._skipWS();
        }
        if (bl = this._parsingContext.inObject()) {
            String string = this._parseFieldName(n);
            this._parsingContext.setCurrentName(string);
            this._currToken = JsonToken.FIELD_NAME;
            int n2 = this._skipWS();
            if (n2 != 58) {
                this._reportUnexpectedChar(n2, "was expecting a colon to separate field name and value");
            }
            n = this._skipWS();
        }
        switch (n) {
            default: {
                jsonToken = this._handleUnexpectedValue(n);
                break;
            }
            case 34: {
                this._tokenIncomplete = true;
                jsonToken = JsonToken.VALUE_STRING;
                break;
            }
            case 91: {
                if (!bl) {
                    this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                }
                jsonToken = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                if (!bl) {
                    this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                }
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
        if (bl) {
            this._nextToken = jsonToken;
            return this._currToken;
        }
        this._currToken = jsonToken;
        return jsonToken;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected final JsonToken parseNumberText(int var1) throws IOException, JsonParseException {
        block15 : {
            block21 : {
                block20 : {
                    block16 : {
                        var2_2 = var1 == 45;
                        var3_3 = this._inputPtr;
                        var4_4 = var3_3 - 1;
                        var5_5 = this._inputEnd;
                        if (!var2_2) break block16;
                        if (var3_3 >= this._inputEnd) ** GOTO lbl19
                        var25_6 = this._inputBuffer;
                        var26_7 = var3_3 + 1;
                        var1 = var25_6[var3_3];
                        if (var1 > 57 || var1 < 48) {
                            this._inputPtr = var26_7;
                            return this._handleInvalidNumberStart(var1, true);
                        }
                        var3_3 = var26_7;
                    }
                    if (var1 == 48) ** GOTO lbl19
                    var6_8 = 1;
                    do {
                        block18 : {
                            block17 : {
                                if (var3_3 < this._inputEnd) break block17;
lbl19: // 7 sources:
                                do {
                                    if (var2_2) {
                                        ++var4_4;
                                    }
                                    this._inputPtr = var4_4;
                                    return ReaderBasedParser.super.parseNumberText2(var2_2);
                                    break;
                                } while (true);
                            }
                            var7_11 = this._inputBuffer;
                            var8_9 = var3_3 + 1;
                            var9_10 = var7_11[var3_3];
                            if (var9_10 >= '0' && var9_10 <= '9') break block18;
                            var10_12 = 0;
                            if (var9_10 == '.') {
                                break;
                            }
                            ** GOTO lbl46
                        }
                        ++var6_8;
                        var3_3 = var8_9;
                    } while (true);
                    do {
                        block19 : {
                            if (var8_9 >= var5_5) ** GOTO lbl19
                            var22_13 = this._inputBuffer;
                            var23_14 = var8_9 + 1;
                            var9_10 = var22_13[var8_9];
                            if (var9_10 >= '0' && var9_10 <= '9') break block19;
                            if (var10_12 == 0) {
                                this.reportUnexpectedNumberChar(var9_10, "Decimal point not followed by a digit");
                            }
                            var8_9 = var23_14;
lbl46: // 2 sources:
                            var11_15 = 0;
                            if (var9_10 != 'e') {
                                var11_15 = 0;
                                if (var9_10 != 'E') break block15;
                            }
                            if (var8_9 < var5_5) break;
                            ** GOTO lbl19
                        }
                        ++var10_12;
                        var8_9 = var23_14;
                    } while (true);
                    var14_16 = this._inputBuffer;
                    var15_17 = var8_9 + 1;
                    var16_18 = var14_16[var8_9];
                    if (var16_18 != '-' && var16_18 != '+') break block20;
                    if (var15_17 >= var5_5) ** GOTO lbl19
                    var17_19 = this._inputBuffer;
                    var8_9 = var15_17 + 1;
                    var16_18 = var17_19[var15_17];
                    break block21;
                }
                var8_9 = var15_17;
                var11_15 = 0;
            }
            while (var16_18 <= '9' && var16_18 >= '0') {
                ++var11_15;
                if (var8_9 < var5_5) ** break;
                ** continue;
                var18_21 = this._inputBuffer;
                var19_20 = var8_9 + 1;
                var16_18 = var18_21[var8_9];
                var8_9 = var19_20;
            }
            if (var11_15 == 0) {
                this.reportUnexpectedNumberChar(var16_18, "Exponent indicator not followed by a digit");
            }
        }
        this._inputPtr = var12_22 = -1 + var8_9;
        var13_23 = var12_22 - var4_4;
        this._textBuffer.resetWithShared(this._inputBuffer, var4_4, var13_23);
        return this.reset(var2_2, var6_8, var10_12, var11_15);
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

    @Override
    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }

}


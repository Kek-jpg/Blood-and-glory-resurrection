/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.impl.JsonReadContext
 *  com.flurry.org.codehaus.jackson.io.IOContext
 *  com.flurry.org.codehaus.jackson.io.NumberInput
 *  com.flurry.org.codehaus.jackson.util.ByteArrayBuilder
 *  com.flurry.org.codehaus.jackson.util.TextBuffer
 *  com.flurry.org.codehaus.jackson.util.VersionUtil
 *  java.io.IOException
 *  java.lang.Character
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Number
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.UnsupportedOperationException
 *  java.math.BigDecimal
 *  java.math.BigInteger
 */
package com.flurry.org.codehaus.jackson.impl;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.Version;
import com.flurry.org.codehaus.jackson.impl.JsonParserMinimalBase;
import com.flurry.org.codehaus.jackson.impl.JsonReadContext;
import com.flurry.org.codehaus.jackson.io.IOContext;
import com.flurry.org.codehaus.jackson.io.NumberInput;
import com.flurry.org.codehaus.jackson.util.ByteArrayBuilder;
import com.flurry.org.codehaus.jackson.util.TextBuffer;
import com.flurry.org.codehaus.jackson.util.VersionUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class JsonParserBase
extends JsonParserMinimalBase {
    static final BigDecimal BD_MAX_INT;
    static final BigDecimal BD_MAX_LONG;
    static final BigDecimal BD_MIN_INT;
    static final BigDecimal BD_MIN_LONG;
    protected static final char CHAR_NULL = '\u0000';
    protected static final int INT_0 = 48;
    protected static final int INT_1 = 49;
    protected static final int INT_2 = 50;
    protected static final int INT_3 = 51;
    protected static final int INT_4 = 52;
    protected static final int INT_5 = 53;
    protected static final int INT_6 = 54;
    protected static final int INT_7 = 55;
    protected static final int INT_8 = 56;
    protected static final int INT_9 = 57;
    protected static final int INT_DECIMAL_POINT = 46;
    protected static final int INT_E = 69;
    protected static final int INT_MINUS = 45;
    protected static final int INT_PLUS = 43;
    protected static final int INT_e = 101;
    static final double MAX_INT_D = 2.147483647E9;
    static final long MAX_INT_L = Integer.MAX_VALUE;
    static final double MAX_LONG_D = 9.223372036854776E18;
    static final double MIN_INT_D = -2.147483648E9;
    static final long MIN_INT_L = Integer.MIN_VALUE;
    static final double MIN_LONG_D = -9.223372036854776E18;
    protected static final int NR_BIGDECIMAL = 16;
    protected static final int NR_BIGINT = 4;
    protected static final int NR_DOUBLE = 8;
    protected static final int NR_INT = 1;
    protected static final int NR_LONG = 2;
    protected static final int NR_UNKNOWN;
    protected byte[] _binaryValue;
    protected ByteArrayBuilder _byteArrayBuilder = null;
    protected boolean _closed;
    protected long _currInputProcessed = 0L;
    protected int _currInputRow = 1;
    protected int _currInputRowStart = 0;
    protected int _expLength;
    protected int _fractLength;
    protected int _inputEnd = 0;
    protected int _inputPtr = 0;
    protected int _intLength;
    protected final IOContext _ioContext;
    protected boolean _nameCopied = false;
    protected char[] _nameCopyBuffer = null;
    protected JsonToken _nextToken;
    protected int _numTypesValid = 0;
    protected BigDecimal _numberBigDecimal;
    protected BigInteger _numberBigInt;
    protected double _numberDouble;
    protected int _numberInt;
    protected long _numberLong;
    protected boolean _numberNegative;
    protected JsonReadContext _parsingContext;
    protected final TextBuffer _textBuffer;
    protected int _tokenInputCol = 0;
    protected int _tokenInputRow = 1;
    protected long _tokenInputTotal = 0L;

    static {
        BD_MIN_LONG = new BigDecimal(Long.MIN_VALUE);
        BD_MAX_LONG = new BigDecimal(Long.MAX_VALUE);
        BD_MIN_INT = new BigDecimal(Long.MIN_VALUE);
        BD_MAX_INT = new BigDecimal(Long.MAX_VALUE);
    }

    protected JsonParserBase(IOContext iOContext, int n2) {
        this._features = n2;
        this._ioContext = iOContext;
        this._textBuffer = iOContext.constructTextBuffer();
        this._parsingContext = JsonReadContext.createRootContext();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final void _parseSlowFloatValue(int var1) throws IOException, JsonParseException {
        if (var1 != 16) ** GOTO lbl6
        try {
            this._numberBigDecimal = this._textBuffer.contentsAsDecimal();
            this._numTypesValid = 16;
            return;
lbl6: // 1 sources:
            this._numberDouble = this._textBuffer.contentsAsDouble();
            this._numTypesValid = 8;
            return;
        }
        catch (NumberFormatException var2_2) {
            this._wrapError("Malformed numeric value '" + this._textBuffer.contentsAsString() + "'", var2_2);
            return;
        }
    }

    private final void _parseSlowIntValue(int n2, char[] arrc, int n3, int n4) throws IOException, JsonParseException {
        String string = this._textBuffer.contentsAsString();
        try {
            if (NumberInput.inLongRange((char[])arrc, (int)n3, (int)n4, (boolean)this._numberNegative)) {
                this._numberLong = Long.parseLong((String)string);
                this._numTypesValid = 2;
                return;
            }
            this._numberBigInt = new BigInteger(string);
            this._numTypesValid = 4;
            return;
        }
        catch (NumberFormatException numberFormatException) {
            this._wrapError("Malformed numeric value '" + string + "'", numberFormatException);
            return;
        }
    }

    protected abstract void _closeInput() throws IOException;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final int _decodeBase64Escape(Base64Variant base64Variant, char c2, int n2) throws IOException, JsonParseException {
        if (c2 != '\\') {
            throw this.reportInvalidBase64Char(base64Variant, c2, n2);
        }
        char c3 = this._decodeEscaped();
        if (c3 <= ' ' && n2 == 0) {
            return -1;
        }
        int n3 = base64Variant.decodeBase64Char(c3);
        if (n3 >= 0) return n3;
        throw this.reportInvalidBase64Char(base64Variant, c3, n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final int _decodeBase64Escape(Base64Variant base64Variant, int n2, int n3) throws IOException, JsonParseException {
        if (n2 != 92) {
            throw this.reportInvalidBase64Char(base64Variant, n2, n3);
        }
        char c2 = this._decodeEscaped();
        if (c2 <= ' ' && n3 == 0) {
            return -1;
        }
        int n4 = base64Variant.decodeBase64Char((int)c2);
        if (n4 >= 0) return n4;
        throw this.reportInvalidBase64Char(base64Variant, c2, n3);
    }

    protected char _decodeEscaped() throws IOException, JsonParseException {
        throw new UnsupportedOperationException();
    }

    protected abstract void _finishString() throws IOException, JsonParseException;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ByteArrayBuilder _getByteArrayBuilder() {
        if (this._byteArrayBuilder == null) {
            this._byteArrayBuilder = new ByteArrayBuilder();
            do {
                return this._byteArrayBuilder;
                break;
            } while (true);
        }
        this._byteArrayBuilder.reset();
        return this._byteArrayBuilder;
    }

    @Override
    protected void _handleEOF() throws JsonParseException {
        if (!this._parsingContext.inRoot()) {
            this._reportInvalidEOF(": expected close marker for " + this._parsingContext.getTypeDesc() + " (from " + this._parsingContext.getStartLocation(this._ioContext.getSourceReference()) + ")");
        }
    }

    protected void _parseNumericValue(int n2) throws IOException, JsonParseException {
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            char[] arrc = this._textBuffer.getTextBuffer();
            int n3 = this._textBuffer.getTextOffset();
            int n4 = this._intLength;
            if (this._numberNegative) {
                ++n3;
            }
            if (n4 <= 9) {
                int n5 = NumberInput.parseInt((char[])arrc, (int)n3, (int)n4);
                if (this._numberNegative) {
                    n5 = -n5;
                }
                this._numberInt = n5;
                this._numTypesValid = 1;
                return;
            }
            if (n4 <= 18) {
                long l2 = NumberInput.parseLong((char[])arrc, (int)n3, (int)n4);
                if (this._numberNegative) {
                    l2 = -l2;
                }
                if (n4 == 10) {
                    if (this._numberNegative) {
                        if (l2 >= Integer.MIN_VALUE) {
                            this._numberInt = (int)l2;
                            this._numTypesValid = 1;
                            return;
                        }
                    } else if (l2 <= Integer.MAX_VALUE) {
                        this._numberInt = (int)l2;
                        this._numTypesValid = 1;
                        return;
                    }
                }
                this._numberLong = l2;
                this._numTypesValid = 2;
                return;
            }
            JsonParserBase.super._parseSlowIntValue(n2, arrc, n3, n4);
            return;
        }
        if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
            JsonParserBase.super._parseSlowFloatValue(n2);
            return;
        }
        this._reportError("Current token (" + (Object)((Object)this._currToken) + ") not numeric, can not use numeric value accessors");
    }

    protected void _releaseBuffers() throws IOException {
        this._textBuffer.releaseBuffers();
        char[] arrc = this._nameCopyBuffer;
        if (arrc != null) {
            this._nameCopyBuffer = null;
            this._ioContext.releaseNameCopyBuffer(arrc);
        }
    }

    protected void _reportMismatchedEndMarker(int n2, char c2) throws JsonParseException {
        String string = "" + this._parsingContext.getStartLocation(this._ioContext.getSourceReference());
        this._reportError("Unexpected close marker '" + (char)n2 + "': expected '" + c2 + "' (for " + this._parsingContext.getTypeDesc() + " starting at " + string + ")");
    }

    @Override
    public void close() throws IOException {
        if (!this._closed) {
            this._closed = true;
            this._closeInput();
        }
        return;
        finally {
            this._releaseBuffers();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void convertNumberToBigDecimal() throws IOException, JsonParseException {
        if ((8 & this._numTypesValid) != 0) {
            this._numberBigDecimal = new BigDecimal(this.getText());
        } else if ((4 & this._numTypesValid) != 0) {
            this._numberBigDecimal = new BigDecimal(this._numberBigInt);
        } else if ((2 & this._numTypesValid) != 0) {
            this._numberBigDecimal = BigDecimal.valueOf((long)this._numberLong);
        } else if ((1 & this._numTypesValid) != 0) {
            this._numberBigDecimal = BigDecimal.valueOf((long)this._numberInt);
        } else {
            this._throwInternal();
        }
        this._numTypesValid = 16 | this._numTypesValid;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void convertNumberToBigInteger() throws IOException, JsonParseException {
        if ((16 & this._numTypesValid) != 0) {
            this._numberBigInt = this._numberBigDecimal.toBigInteger();
        } else if ((2 & this._numTypesValid) != 0) {
            this._numberBigInt = BigInteger.valueOf((long)this._numberLong);
        } else if ((1 & this._numTypesValid) != 0) {
            this._numberBigInt = BigInteger.valueOf((long)this._numberInt);
        } else if ((8 & this._numTypesValid) != 0) {
            this._numberBigInt = BigDecimal.valueOf((double)this._numberDouble).toBigInteger();
        } else {
            this._throwInternal();
        }
        this._numTypesValid = 4 | this._numTypesValid;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void convertNumberToDouble() throws IOException, JsonParseException {
        if ((16 & this._numTypesValid) != 0) {
            this._numberDouble = this._numberBigDecimal.doubleValue();
        } else if ((4 & this._numTypesValid) != 0) {
            this._numberDouble = this._numberBigInt.doubleValue();
        } else if ((2 & this._numTypesValid) != 0) {
            this._numberDouble = this._numberLong;
        } else if ((1 & this._numTypesValid) != 0) {
            this._numberDouble = this._numberInt;
        } else {
            this._throwInternal();
        }
        this._numTypesValid = 8 | this._numTypesValid;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void convertNumberToInt() throws IOException, JsonParseException {
        if ((2 & this._numTypesValid) != 0) {
            int n2 = (int)this._numberLong;
            if ((long)n2 != this._numberLong) {
                this._reportError("Numeric value (" + this.getText() + ") out of range of int");
            }
            this._numberInt = n2;
        } else if ((4 & this._numTypesValid) != 0) {
            this._numberInt = this._numberBigInt.intValue();
        } else if ((8 & this._numTypesValid) != 0) {
            if (this._numberDouble < -2.147483648E9 || this._numberDouble > 2.147483647E9) {
                this.reportOverflowInt();
            }
            this._numberInt = (int)this._numberDouble;
        } else if ((16 & this._numTypesValid) != 0) {
            if (BD_MIN_INT.compareTo(this._numberBigDecimal) > 0 || BD_MAX_INT.compareTo(this._numberBigDecimal) < 0) {
                this.reportOverflowInt();
            }
            this._numberInt = this._numberBigDecimal.intValue();
        } else {
            this._throwInternal();
        }
        this._numTypesValid = 1 | this._numTypesValid;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void convertNumberToLong() throws IOException, JsonParseException {
        if ((1 & this._numTypesValid) != 0) {
            this._numberLong = this._numberInt;
        } else if ((4 & this._numTypesValid) != 0) {
            this._numberLong = this._numberBigInt.longValue();
        } else if ((8 & this._numTypesValid) != 0) {
            if (this._numberDouble < -9.223372036854776E18 || this._numberDouble > 9.223372036854776E18) {
                this.reportOverflowLong();
            }
            this._numberLong = (long)this._numberDouble;
        } else if ((16 & this._numTypesValid) != 0) {
            if (BD_MIN_LONG.compareTo(this._numberBigDecimal) > 0 || BD_MAX_LONG.compareTo(this._numberBigDecimal) < 0) {
                this.reportOverflowLong();
            }
            this._numberLong = this._numberBigDecimal.longValue();
        } else {
            this._throwInternal();
        }
        this._numTypesValid = 2 | this._numTypesValid;
    }

    @Override
    public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
        if ((4 & this._numTypesValid) == 0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(4);
            }
            if ((4 & this._numTypesValid) == 0) {
                this.convertNumberToBigInteger();
            }
        }
        return this._numberBigInt;
    }

    @Override
    public JsonLocation getCurrentLocation() {
        int n2 = 1 + (this._inputPtr - this._currInputRowStart);
        return new JsonLocation(this._ioContext.getSourceReference(), this._currInputProcessed + (long)this._inputPtr - 1L, this._currInputRow, n2);
    }

    @Override
    public String getCurrentName() throws IOException, JsonParseException {
        if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
            return this._parsingContext.getParent().getCurrentName();
        }
        return this._parsingContext.getCurrentName();
    }

    @Override
    public BigDecimal getDecimalValue() throws IOException, JsonParseException {
        if ((16 & this._numTypesValid) == 0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(16);
            }
            if ((16 & this._numTypesValid) == 0) {
                this.convertNumberToBigDecimal();
            }
        }
        return this._numberBigDecimal;
    }

    @Override
    public double getDoubleValue() throws IOException, JsonParseException {
        if ((8 & this._numTypesValid) == 0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(8);
            }
            if ((8 & this._numTypesValid) == 0) {
                this.convertNumberToDouble();
            }
        }
        return this._numberDouble;
    }

    @Override
    public float getFloatValue() throws IOException, JsonParseException {
        return (float)this.getDoubleValue();
    }

    @Override
    public int getIntValue() throws IOException, JsonParseException {
        if ((1 & this._numTypesValid) == 0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(1);
            }
            if ((1 & this._numTypesValid) == 0) {
                this.convertNumberToInt();
            }
        }
        return this._numberInt;
    }

    @Override
    public long getLongValue() throws IOException, JsonParseException {
        if ((2 & this._numTypesValid) == 0) {
            if (this._numTypesValid == 0) {
                this._parseNumericValue(2);
            }
            if ((2 & this._numTypesValid) == 0) {
                this.convertNumberToLong();
            }
        }
        return this._numberLong;
    }

    @Override
    public JsonParser.NumberType getNumberType() throws IOException, JsonParseException {
        if (this._numTypesValid == 0) {
            this._parseNumericValue(0);
        }
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            if ((1 & this._numTypesValid) != 0) {
                return JsonParser.NumberType.INT;
            }
            if ((2 & this._numTypesValid) != 0) {
                return JsonParser.NumberType.LONG;
            }
            return JsonParser.NumberType.BIG_INTEGER;
        }
        if ((16 & this._numTypesValid) != 0) {
            return JsonParser.NumberType.BIG_DECIMAL;
        }
        return JsonParser.NumberType.DOUBLE;
    }

    @Override
    public Number getNumberValue() throws IOException, JsonParseException {
        if (this._numTypesValid == 0) {
            this._parseNumericValue(0);
        }
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            if ((1 & this._numTypesValid) != 0) {
                return this._numberInt;
            }
            if ((2 & this._numTypesValid) != 0) {
                return this._numberLong;
            }
            if ((4 & this._numTypesValid) != 0) {
                return this._numberBigInt;
            }
            return this._numberBigDecimal;
        }
        if ((16 & this._numTypesValid) != 0) {
            return this._numberBigDecimal;
        }
        if ((8 & this._numTypesValid) == 0) {
            this._throwInternal();
        }
        return this._numberDouble;
    }

    public JsonReadContext getParsingContext() {
        return this._parsingContext;
    }

    public final long getTokenCharacterOffset() {
        return this._tokenInputTotal;
    }

    public final int getTokenColumnNr() {
        int n2 = this._tokenInputCol;
        if (n2 < 0) {
            return n2;
        }
        return n2 + 1;
    }

    public final int getTokenLineNr() {
        return this._tokenInputRow;
    }

    @Override
    public JsonLocation getTokenLocation() {
        return new JsonLocation(this._ioContext.getSourceReference(), this.getTokenCharacterOffset(), this.getTokenLineNr(), this.getTokenColumnNr());
    }

    @Override
    public boolean hasTextCharacters() {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return true;
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._nameCopied;
        }
        return false;
    }

    @Override
    public boolean isClosed() {
        return this._closed;
    }

    protected abstract boolean loadMore() throws IOException;

    protected final void loadMoreGuaranteed() throws IOException {
        if (!this.loadMore()) {
            this._reportInvalidEOF();
        }
    }

    protected IllegalArgumentException reportInvalidBase64Char(Base64Variant base64Variant, int n2, int n3) throws IllegalArgumentException {
        return this.reportInvalidBase64Char(base64Variant, n2, n3, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected IllegalArgumentException reportInvalidBase64Char(Base64Variant base64Variant, int n2, int n3, String string) throws IllegalArgumentException {
        String string2 = n2 <= 32 ? "Illegal white space character (code 0x" + Integer.toHexString((int)n2) + ") as character #" + (n3 + 1) + " of 4-char base64 unit: can only used between units" : (base64Variant.usesPaddingChar(n2) ? "Unexpected padding character ('" + base64Variant.getPaddingChar() + "') as character #" + (n3 + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character" : (!Character.isDefined((int)n2) || Character.isISOControl((int)n2) ? "Illegal character (code 0x" + Integer.toHexString((int)n2) + ") in base64 content" : "Illegal character '" + (char)n2 + "' (code 0x" + Integer.toHexString((int)n2) + ") in base64 content"));
        if (string != null) {
            string2 = string2 + ": " + string;
        }
        return new IllegalArgumentException(string2);
    }

    protected void reportInvalidNumber(String string) throws JsonParseException {
        this._reportError("Invalid numeric value: " + string);
    }

    protected void reportOverflowInt() throws IOException, JsonParseException {
        this._reportError("Numeric value (" + this.getText() + ") out of range of int (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
    }

    protected void reportOverflowLong() throws IOException, JsonParseException {
        this._reportError("Numeric value (" + this.getText() + ") out of range of long (" + Long.MIN_VALUE + " - " + Long.MAX_VALUE + ")");
    }

    protected void reportUnexpectedNumberChar(int n2, String string) throws JsonParseException {
        String string2 = "Unexpected character (" + JsonParserBase._getCharDesc(n2) + ") in numeric value";
        if (string != null) {
            string2 = string2 + ": " + string;
        }
        this._reportError(string2);
    }

    protected final JsonToken reset(boolean bl, int n2, int n3, int n4) {
        if (n3 < 1 && n4 < 1) {
            return this.resetInt(bl, n2);
        }
        return this.resetFloat(bl, n2, n3, n4);
    }

    protected final JsonToken resetAsNaN(String string, double d2) {
        this._textBuffer.resetWithString(string);
        this._numberDouble = d2;
        this._numTypesValid = 8;
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    protected final JsonToken resetFloat(boolean bl, int n2, int n3, int n4) {
        this._numberNegative = bl;
        this._intLength = n2;
        this._fractLength = n3;
        this._expLength = n4;
        this._numTypesValid = 0;
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    protected final JsonToken resetInt(boolean bl, int n2) {
        this._numberNegative = bl;
        this._intLength = n2;
        this._fractLength = 0;
        this._expLength = 0;
        this._numTypesValid = 0;
        return JsonToken.VALUE_NUMBER_INT;
    }

    @Override
    public Version version() {
        return VersionUtil.versionFor((Class)this.getClass());
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.impl.JsonParserMinimalBase$1
 *  com.flurry.org.codehaus.jackson.io.NumberInput
 *  com.flurry.org.codehaus.jackson.util.ByteArrayBuilder
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Character
 *  java.lang.Integer
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.flurry.org.codehaus.jackson.impl;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.impl.JsonParserMinimalBase;
import com.flurry.org.codehaus.jackson.io.NumberInput;
import com.flurry.org.codehaus.jackson.util.ByteArrayBuilder;
import java.io.IOException;

public abstract class JsonParserMinimalBase
extends JsonParser {
    protected static final int INT_APOSTROPHE = 39;
    protected static final int INT_ASTERISK = 42;
    protected static final int INT_BACKSLASH = 92;
    protected static final int INT_COLON = 58;
    protected static final int INT_COMMA = 44;
    protected static final int INT_CR = 13;
    protected static final int INT_LBRACKET = 91;
    protected static final int INT_LCURLY = 123;
    protected static final int INT_LF = 10;
    protected static final int INT_QUOTE = 34;
    protected static final int INT_RBRACKET = 93;
    protected static final int INT_RCURLY = 125;
    protected static final int INT_SLASH = 47;
    protected static final int INT_SPACE = 32;
    protected static final int INT_TAB = 9;
    protected static final int INT_b = 98;
    protected static final int INT_f = 102;
    protected static final int INT_n = 110;
    protected static final int INT_r = 114;
    protected static final int INT_t = 116;
    protected static final int INT_u = 117;

    protected JsonParserMinimalBase() {
    }

    protected JsonParserMinimalBase(int n2) {
        super(n2);
    }

    protected static final String _getCharDesc(int n2) {
        char c2 = (char)n2;
        if (Character.isISOControl((char)c2)) {
            return "(CTRL-CHAR, code " + n2 + ")";
        }
        if (n2 > 255) {
            return "'" + c2 + "' (code " + n2 + " / 0x" + Integer.toHexString((int)n2) + ")";
        }
        return "'" + c2 + "' (code " + n2 + ")";
    }

    protected final JsonParseException _constructError(String string, Throwable throwable) {
        return new JsonParseException(string, this.getCurrentLocation(), throwable);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void _decodeBase64(String string, ByteArrayBuilder byteArrayBuilder, Base64Variant base64Variant) throws IOException, JsonParseException {
        int n2 = 0;
        int n3 = string.length();
        block0 : while (n2 < n3) {
            do {
                int n4 = n2 + 1;
                char c2 = string.charAt(n2);
                if (n4 >= n3) {
                    return;
                }
                if (c2 > ' ') {
                    int n5 = base64Variant.decodeBase64Char(c2);
                    if (n5 < 0) {
                        this._reportInvalidBase64(base64Variant, c2, 0, null);
                    }
                    if (n4 >= n3) {
                        this._reportBase64EOF();
                    }
                    int n6 = n4 + 1;
                    char c3 = string.charAt(n4);
                    int n7 = base64Variant.decodeBase64Char(c3);
                    if (n7 < 0) {
                        this._reportInvalidBase64(base64Variant, c3, 1, null);
                    }
                    int n8 = n7 | n5 << 6;
                    if (n6 >= n3) {
                        if (!base64Variant.usesPadding()) {
                            byteArrayBuilder.append(n8 >> 4);
                            return;
                        }
                        this._reportBase64EOF();
                    }
                    int n9 = n6 + 1;
                    char c4 = string.charAt(n6);
                    int n10 = base64Variant.decodeBase64Char(c4);
                    if (n10 < 0) {
                        if (n10 != -2) {
                            this._reportInvalidBase64(base64Variant, c4, 2, null);
                        }
                        if (n9 >= n3) {
                            this._reportBase64EOF();
                        }
                        n2 = n9 + 1;
                        char c5 = string.charAt(n9);
                        if (!base64Variant.usesPaddingChar(c5)) {
                            this._reportInvalidBase64(base64Variant, c5, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                        }
                        byteArrayBuilder.append(n8 >> 4);
                        continue block0;
                    }
                    int n11 = n10 | n8 << 6;
                    if (n9 >= n3) {
                        if (!base64Variant.usesPadding()) {
                            byteArrayBuilder.appendTwoBytes(n11 >> 2);
                            return;
                        }
                        this._reportBase64EOF();
                    }
                    n2 = n9 + 1;
                    char c6 = string.charAt(n9);
                    int n12 = base64Variant.decodeBase64Char(c6);
                    if (n12 < 0) {
                        if (n12 != -2) {
                            this._reportInvalidBase64(base64Variant, c6, 3, null);
                        }
                        byteArrayBuilder.appendTwoBytes(n11 >> 2);
                        continue block0;
                    }
                    byteArrayBuilder.appendThreeBytes(n12 | n11 << 6);
                    continue block0;
                }
                n2 = n4;
            } while (true);
            break;
        }
        return;
    }

    protected abstract void _handleEOF() throws JsonParseException;

    /*
     * Enabled aggressive block sorting
     */
    protected char _handleUnrecognizedCharacterEscape(char c2) throws JsonProcessingException {
        if (this.isEnabled(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER) || c2 == '\'' && this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
            return c2;
        }
        this._reportError("Unrecognized character escape " + JsonParserMinimalBase._getCharDesc(c2));
        return c2;
    }

    protected void _reportBase64EOF() throws JsonParseException {
        throw this._constructError("Unexpected end-of-String in base64 content");
    }

    protected final void _reportError(String string) throws JsonParseException {
        throw this._constructError(string);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _reportInvalidBase64(Base64Variant base64Variant, char c2, int n2, String string) throws JsonParseException {
        String string2 = c2 <= ' ' ? "Illegal white space character (code 0x" + Integer.toHexString((int)c2) + ") as character #" + (n2 + 1) + " of 4-char base64 unit: can only used between units" : (base64Variant.usesPaddingChar(c2) ? "Unexpected padding character ('" + base64Variant.getPaddingChar() + "') as character #" + (n2 + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character" : (!Character.isDefined((char)c2) || Character.isISOControl((char)c2) ? "Illegal character (code 0x" + Integer.toHexString((int)c2) + ") in base64 content" : "Illegal character '" + c2 + "' (code 0x" + Integer.toHexString((int)c2) + ") in base64 content"));
        if (string != null) {
            string2 = string2 + ": " + string;
        }
        throw this._constructError(string2);
    }

    protected void _reportInvalidEOF() throws JsonParseException {
        this._reportInvalidEOF(" in " + (Object)((Object)this._currToken));
    }

    protected void _reportInvalidEOF(String string) throws JsonParseException {
        this._reportError("Unexpected end-of-input" + string);
    }

    protected void _reportInvalidEOFInValue() throws JsonParseException {
        this._reportInvalidEOF(" in a value");
    }

    protected void _reportUnexpectedChar(int n2, String string) throws JsonParseException {
        String string2 = "Unexpected character (" + JsonParserMinimalBase._getCharDesc(n2) + ")";
        if (string != null) {
            string2 = string2 + ": " + string;
        }
        this._reportError(string2);
    }

    protected final void _throwInternal() {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }

    protected void _throwInvalidSpace(int n2) throws JsonParseException {
        char c2 = (char)n2;
        this._reportError("Illegal character (" + JsonParserMinimalBase._getCharDesc(c2) + "): only regular white space (\\r, \\n, \\t) is allowed between tokens");
    }

    protected void _throwUnquotedSpace(int n2, String string) throws JsonParseException {
        if (!this.isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS) || n2 >= 32) {
            char c2 = (char)n2;
            this._reportError("Illegal unquoted character (" + JsonParserMinimalBase._getCharDesc(c2) + "): has to be escaped using backslash to be included in " + string);
        }
    }

    protected final void _wrapError(String string, Throwable throwable) throws JsonParseException {
        throw this._constructError(string, throwable);
    }

    @Override
    public abstract void close() throws IOException;

    @Override
    public abstract byte[] getBinaryValue(Base64Variant var1) throws IOException, JsonParseException;

    @Override
    public abstract String getCurrentName() throws IOException, JsonParseException;

    @Override
    public abstract JsonStreamContext getParsingContext();

    @Override
    public abstract String getText() throws IOException, JsonParseException;

    @Override
    public abstract char[] getTextCharacters() throws IOException, JsonParseException;

    @Override
    public abstract int getTextLength() throws IOException, JsonParseException;

    @Override
    public abstract int getTextOffset() throws IOException, JsonParseException;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean getValueAsBoolean(boolean var1) throws IOException, JsonParseException {
        var2_2 = true;
        if (this._currToken == null) ** GOTO lbl18
        switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[this._currToken.ordinal()]) {
            default: {
                ** GOTO lbl18
            }
            case 5: {
                if (this.getIntValue() != 0) return var2_2;
                return false;
            }
            case 7: 
            case 8: {
                return false;
            }
            case 9: {
                var3_3 = this.getEmbeddedObject();
                if (!(var3_3 instanceof Boolean)) ** break;
                return (Boolean)var3_3;
            }
            case 10: {
                if ("true".equals((Object)this.getText().trim())) {
                    return var2_2;
                }
lbl18: // 4 sources:
                var2_2 = var1;
            }
            case 6: 
        }
        return var2_2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public double getValueAsDouble(double d2) throws IOException, JsonParseException {
        if (this._currToken == null) return d2;
        {
            switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[this._currToken.ordinal()]) {
                default: {
                    return d2;
                }
                case 5: 
                case 11: {
                    return this.getDoubleValue();
                }
                case 6: {
                    return 1.0;
                }
                case 7: 
                case 8: {
                    return 0.0;
                }
                case 10: {
                    return NumberInput.parseAsDouble((String)this.getText(), (double)d2);
                }
                case 9: {
                    Object object = this.getEmbeddedObject();
                    if (!(object instanceof Number)) return d2;
                    return ((Number)object).doubleValue();
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int getValueAsInt(int n2) throws IOException, JsonParseException {
        if (this._currToken == null) return n2;
        {
            switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[this._currToken.ordinal()]) {
                default: {
                    return n2;
                }
                case 5: 
                case 11: {
                    return this.getIntValue();
                }
                case 6: {
                    return 1;
                }
                case 7: 
                case 8: {
                    return 0;
                }
                case 10: {
                    return NumberInput.parseAsInt((String)this.getText(), (int)n2);
                }
                case 9: {
                    Object object = this.getEmbeddedObject();
                    if (!(object instanceof Number)) return n2;
                    return ((Number)object).intValue();
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long getValueAsLong(long l2) throws IOException, JsonParseException {
        if (this._currToken == null) return l2;
        {
            switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[this._currToken.ordinal()]) {
                default: {
                    return l2;
                }
                case 5: 
                case 11: {
                    return this.getLongValue();
                }
                case 6: {
                    return 1L;
                }
                case 7: 
                case 8: {
                    return 0L;
                }
                case 10: {
                    return NumberInput.parseAsLong((String)this.getText(), (long)l2);
                }
                case 9: {
                    Object object = this.getEmbeddedObject();
                    if (!(object instanceof Number)) return l2;
                    return ((Number)object).longValue();
                }
            }
        }
    }

    @Override
    public abstract boolean hasTextCharacters();

    @Override
    public abstract boolean isClosed();

    @Override
    public abstract JsonToken nextToken() throws IOException, JsonParseException;

    @Override
    public JsonParser skipChildren() throws IOException, JsonParseException {
        if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
            return this;
        }
        int n2 = 1;
        block4 : do {
            JsonToken jsonToken;
            if ((jsonToken = this.nextToken()) == null) {
                this._handleEOF();
                return this;
            }
            switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonToken.ordinal()]) {
                default: {
                    continue block4;
                }
                case 1: 
                case 2: {
                    ++n2;
                    continue block4;
                }
                case 3: 
                case 4: 
            }
            if (--n2 == 0) break;
        } while (true);
        return this;
    }
}


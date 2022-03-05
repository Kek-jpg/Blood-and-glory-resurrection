/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.io.NumberInput
 *  com.flurry.org.codehaus.jackson.map.SerializerProvider
 *  java.io.IOException
 *  java.lang.Character
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.Base64Variants;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.io.NumberInput;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.node.ValueNode;
import com.flurry.org.codehaus.jackson.util.ByteArrayBuilder;
import com.flurry.org.codehaus.jackson.util.CharTypes;
import java.io.IOException;

public final class TextNode
extends ValueNode {
    static final TextNode EMPTY_STRING_NODE = new TextNode("");
    static final int INT_SPACE = 32;
    final String _value;

    public TextNode(String string2) {
        this._value = string2;
    }

    protected static void appendQuoted(StringBuilder stringBuilder, String string2) {
        stringBuilder.append('\"');
        CharTypes.appendQuoted(stringBuilder, string2);
        stringBuilder.append('\"');
    }

    public static TextNode valueOf(String string2) {
        if (string2 == null) {
            return null;
        }
        if (string2.length() == 0) {
            return EMPTY_STRING_NODE;
        }
        return new TextNode(string2);
    }

    protected void _reportBase64EOF() throws JsonParseException {
        throw new JsonParseException("Unexpected end-of-String when base64 content", JsonLocation.NA);
    }

    protected void _reportInvalidBase64(Base64Variant base64Variant, char c2, int n) throws JsonParseException {
        this._reportInvalidBase64(base64Variant, c2, n, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _reportInvalidBase64(Base64Variant base64Variant, char c2, int n, String string2) throws JsonParseException {
        String string3 = c2 <= ' ' ? "Illegal white space character (code 0x" + Integer.toHexString((int)c2) + ") as character #" + (n + 1) + " of 4-char base64 unit: can only used between units" : (base64Variant.usesPaddingChar(c2) ? "Unexpected padding character ('" + base64Variant.getPaddingChar() + "') as character #" + (n + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character" : (!Character.isDefined((char)c2) || Character.isISOControl((char)c2) ? "Illegal character (code 0x" + Integer.toHexString((int)c2) + ") in base64 content" : "Illegal character '" + c2 + "' (code 0x" + Integer.toHexString((int)c2) + ") in base64 content"));
        if (string2 != null) {
            string3 = string3 + ": " + string2;
        }
        throw new JsonParseException(string3, JsonLocation.NA);
    }

    public boolean asBoolean(boolean bl) {
        if (this._value != null && "true".equals((Object)this._value.trim())) {
            bl = true;
        }
        return bl;
    }

    public double asDouble(double d2) {
        return NumberInput.parseAsDouble((String)this._value, (double)d2);
    }

    public int asInt(int n) {
        return NumberInput.parseAsInt((String)this._value, (int)n);
    }

    public long asLong(long l) {
        return NumberInput.parseAsLong((String)this._value, (long)l);
    }

    public String asText() {
        return this._value;
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_STRING;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        boolean bl = false;
        if (object == null) return bl;
        Class class_ = object.getClass();
        Class class_2 = this.getClass();
        bl = false;
        if (class_ != class_2) return bl;
        return ((TextNode)object)._value.equals((Object)this._value);
    }

    public byte[] getBinaryValue() throws IOException {
        return this.getBinaryValue(Base64Variants.getDefaultVariant());
    }

    /*
     * Enabled aggressive block sorting
     */
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder(100);
        String string2 = this._value;
        int n = 0;
        int n2 = string2.length();
        block0 : while (n < n2) {
            do {
                int n3 = n + 1;
                char c2 = string2.charAt(n);
                if (n3 >= n2) {
                    return byteArrayBuilder.toByteArray();
                }
                if (c2 > ' ') {
                    int n4 = base64Variant.decodeBase64Char(c2);
                    if (n4 < 0) {
                        this._reportInvalidBase64(base64Variant, c2, 0);
                    }
                    if (n3 >= n2) {
                        this._reportBase64EOF();
                    }
                    int n5 = n3 + 1;
                    char c3 = string2.charAt(n3);
                    int n6 = base64Variant.decodeBase64Char(c3);
                    if (n6 < 0) {
                        this._reportInvalidBase64(base64Variant, c3, 1);
                    }
                    int n7 = n6 | n4 << 6;
                    if (n5 >= n2) {
                        if (!base64Variant.usesPadding()) {
                            byteArrayBuilder.append(n7 >> 4);
                            return byteArrayBuilder.toByteArray();
                        }
                        this._reportBase64EOF();
                    }
                    int n8 = n5 + 1;
                    char c4 = string2.charAt(n5);
                    int n9 = base64Variant.decodeBase64Char(c4);
                    if (n9 < 0) {
                        if (n9 != -2) {
                            this._reportInvalidBase64(base64Variant, c4, 2);
                        }
                        if (n8 >= n2) {
                            this._reportBase64EOF();
                        }
                        n = n8 + 1;
                        char c5 = string2.charAt(n8);
                        if (!base64Variant.usesPaddingChar(c5)) {
                            this._reportInvalidBase64(base64Variant, c5, 3, "expected padding character '" + base64Variant.getPaddingChar() + "'");
                        }
                        byteArrayBuilder.append(n7 >> 4);
                        continue block0;
                    }
                    int n10 = n9 | n7 << 6;
                    if (n8 >= n2) {
                        if (!base64Variant.usesPadding()) {
                            byteArrayBuilder.appendTwoBytes(n10 >> 2);
                            return byteArrayBuilder.toByteArray();
                        }
                        this._reportBase64EOF();
                    }
                    n = n8 + 1;
                    char c6 = string2.charAt(n8);
                    int n11 = base64Variant.decodeBase64Char(c6);
                    if (n11 < 0) {
                        if (n11 != -2) {
                            this._reportInvalidBase64(base64Variant, c6, 3);
                        }
                        byteArrayBuilder.appendTwoBytes(n10 >> 2);
                        continue block0;
                    }
                    byteArrayBuilder.appendThreeBytes(n11 | n10 << 6);
                    continue block0;
                }
                n = n3;
            } while (true);
            break;
        }
        return byteArrayBuilder.toByteArray();
    }

    public String getTextValue() {
        return this._value;
    }

    public int hashCode() {
        return this._value.hashCode();
    }

    public boolean isTextual() {
        return true;
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (this._value == null) {
            jsonGenerator.writeNull();
            return;
        }
        jsonGenerator.writeString(this._value);
    }

    @Override
    public String toString() {
        int n = this._value.length();
        StringBuilder stringBuilder = new StringBuilder(n + 2 + (n >> 4));
        TextNode.appendQuoted(stringBuilder, this._value);
        return stringBuilder.toString();
    }
}


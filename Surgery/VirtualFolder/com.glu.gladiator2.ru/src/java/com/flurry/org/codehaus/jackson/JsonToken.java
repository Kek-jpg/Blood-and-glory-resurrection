/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson;

public final class JsonToken
extends Enum<JsonToken> {
    private static final /* synthetic */ JsonToken[] $VALUES;
    public static final /* enum */ JsonToken END_ARRAY;
    public static final /* enum */ JsonToken END_OBJECT;
    public static final /* enum */ JsonToken FIELD_NAME;
    public static final /* enum */ JsonToken NOT_AVAILABLE;
    public static final /* enum */ JsonToken START_ARRAY;
    public static final /* enum */ JsonToken START_OBJECT;
    public static final /* enum */ JsonToken VALUE_EMBEDDED_OBJECT;
    public static final /* enum */ JsonToken VALUE_FALSE;
    public static final /* enum */ JsonToken VALUE_NULL;
    public static final /* enum */ JsonToken VALUE_NUMBER_FLOAT;
    public static final /* enum */ JsonToken VALUE_NUMBER_INT;
    public static final /* enum */ JsonToken VALUE_STRING;
    public static final /* enum */ JsonToken VALUE_TRUE;
    final String _serialized;
    final byte[] _serializedBytes;
    final char[] _serializedChars;

    static {
        NOT_AVAILABLE = new JsonToken(null);
        START_OBJECT = new JsonToken("{");
        END_OBJECT = new JsonToken("}");
        START_ARRAY = new JsonToken("[");
        END_ARRAY = new JsonToken("]");
        FIELD_NAME = new JsonToken(null);
        VALUE_EMBEDDED_OBJECT = new JsonToken(null);
        VALUE_STRING = new JsonToken(null);
        VALUE_NUMBER_INT = new JsonToken(null);
        VALUE_NUMBER_FLOAT = new JsonToken(null);
        VALUE_TRUE = new JsonToken("true");
        VALUE_FALSE = new JsonToken("false");
        VALUE_NULL = new JsonToken("null");
        JsonToken[] arrjsonToken = new JsonToken[]{NOT_AVAILABLE, START_OBJECT, END_OBJECT, START_ARRAY, END_ARRAY, FIELD_NAME, VALUE_EMBEDDED_OBJECT, VALUE_STRING, VALUE_NUMBER_INT, VALUE_NUMBER_FLOAT, VALUE_TRUE, VALUE_FALSE, VALUE_NULL};
        $VALUES = arrjsonToken;
    }

    /*
     * Enabled aggressive block sorting
     */
    private JsonToken(String string2) {
        if (string2 == null) {
            this._serialized = null;
            this._serializedChars = null;
            this._serializedBytes = null;
            return;
        } else {
            this._serialized = string2;
            this._serializedChars = string2.toCharArray();
            int n3 = this._serializedChars.length;
            this._serializedBytes = new byte[n3];
            for (int i2 = 0; i2 < n3; ++i2) {
                this._serializedBytes[i2] = (byte)this._serializedChars[i2];
            }
        }
    }

    public static JsonToken valueOf(String string) {
        return (JsonToken)Enum.valueOf(JsonToken.class, (String)string);
    }

    public static JsonToken[] values() {
        return (JsonToken[])$VALUES.clone();
    }

    public byte[] asByteArray() {
        return this._serializedBytes;
    }

    public char[] asCharArray() {
        return this._serializedChars;
    }

    public String asString() {
        return this._serialized;
    }

    public boolean isNumeric() {
        return this == VALUE_NUMBER_INT || this == VALUE_NUMBER_FLOAT;
    }

    public boolean isScalarValue() {
        return this.ordinal() >= VALUE_EMBEDDED_OBJECT.ordinal();
    }
}


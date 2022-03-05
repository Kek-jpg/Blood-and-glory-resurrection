/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.io;

import com.flurry.org.codehaus.jackson.SerializableString;
import com.flurry.org.codehaus.jackson.io.JsonStringEncoder;

public class SerializedString
implements SerializableString {
    protected char[] _quotedChars;
    protected byte[] _quotedUTF8Ref;
    protected byte[] _unquotedUTF8Ref;
    protected final String _value;

    public SerializedString(String string) {
        this._value = string;
    }

    @Override
    public final char[] asQuotedChars() {
        char[] arrc = this._quotedChars;
        if (arrc == null) {
            this._quotedChars = arrc = JsonStringEncoder.getInstance().quoteAsString(this._value);
        }
        return arrc;
    }

    @Override
    public final byte[] asQuotedUTF8() {
        byte[] arrby = this._quotedUTF8Ref;
        if (arrby == null) {
            this._quotedUTF8Ref = arrby = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
        }
        return arrby;
    }

    @Override
    public final byte[] asUnquotedUTF8() {
        byte[] arrby = this._unquotedUTF8Ref;
        if (arrby == null) {
            this._unquotedUTF8Ref = arrby = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
        }
        return arrby;
    }

    @Override
    public final int charLength() {
        return this._value.length();
    }

    public final boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        SerializedString serializedString = (SerializedString)object;
        return this._value.equals((Object)serializedString._value);
    }

    @Override
    public final String getValue() {
        return this._value;
    }

    public final int hashCode() {
        return this._value.hashCode();
    }

    public final String toString() {
        return this._value;
    }
}


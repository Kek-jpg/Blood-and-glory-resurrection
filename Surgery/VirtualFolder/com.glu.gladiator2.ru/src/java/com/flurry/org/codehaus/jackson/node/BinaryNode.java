/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.SerializerProvider
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.Arrays
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.Base64Variants;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.node.ValueNode;
import java.io.IOException;
import java.util.Arrays;

public final class BinaryNode
extends ValueNode {
    static final BinaryNode EMPTY_BINARY_NODE = new BinaryNode(new byte[0]);
    final byte[] _data;

    public BinaryNode(byte[] arrby) {
        this._data = arrby;
    }

    public BinaryNode(byte[] arrby, int n, int n2) {
        if (n == 0 && n2 == arrby.length) {
            this._data = arrby;
            return;
        }
        this._data = new byte[n2];
        System.arraycopy((Object)arrby, (int)n, (Object)this._data, (int)0, (int)n2);
    }

    public static BinaryNode valueOf(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        if (arrby.length == 0) {
            return EMPTY_BINARY_NODE;
        }
        return new BinaryNode(arrby);
    }

    public static BinaryNode valueOf(byte[] arrby, int n, int n2) {
        if (arrby == null) {
            return null;
        }
        if (n2 == 0) {
            return EMPTY_BINARY_NODE;
        }
        return new BinaryNode(arrby, n, n2);
    }

    public String asText() {
        return Base64Variants.getDefaultVariant().encode(this._data, false);
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_EMBEDDED_OBJECT;
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
        return Arrays.equals((byte[])((BinaryNode)object)._data, (byte[])this._data);
    }

    public byte[] getBinaryValue() {
        return this._data;
    }

    public int hashCode() {
        if (this._data == null) {
            return -1;
        }
        return this._data.length;
    }

    public boolean isBinary() {
        return true;
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeBinary(this._data);
    }

    @Override
    public String toString() {
        return Base64Variants.getDefaultVariant().encode(this._data, true);
    }
}


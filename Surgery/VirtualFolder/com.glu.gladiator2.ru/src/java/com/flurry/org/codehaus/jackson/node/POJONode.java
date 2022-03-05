/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.SerializerProvider
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.node.ValueNode;
import java.io.IOException;

public final class POJONode
extends ValueNode {
    protected final Object _value;

    public POJONode(Object object) {
        this._value = object;
    }

    public boolean asBoolean(boolean bl) {
        if (this._value != null && this._value instanceof Boolean) {
            bl = (Boolean)this._value;
        }
        return bl;
    }

    public double asDouble(double d2) {
        if (this._value instanceof Number) {
            d2 = ((Number)this._value).doubleValue();
        }
        return d2;
    }

    public int asInt(int n) {
        if (this._value instanceof Number) {
            n = ((Number)this._value).intValue();
        }
        return n;
    }

    public long asLong(long l) {
        if (this._value instanceof Number) {
            l = ((Number)this._value).longValue();
        }
        return l;
    }

    public String asText() {
        if (this._value == null) {
            return "null";
        }
        return this._value.toString();
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_EMBEDDED_OBJECT;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block7 : {
            block6 : {
                if (object == this) break block6;
                if (object == null) {
                    return false;
                }
                if (object.getClass() != this.getClass()) {
                    return false;
                }
                POJONode pOJONode = (POJONode)((Object)object);
                if (this._value != null) {
                    return this._value.equals(pOJONode._value);
                }
                if (pOJONode._value != null) break block7;
            }
            return true;
        }
        return false;
    }

    public byte[] getBinaryValue() throws IOException {
        if (this._value instanceof byte[]) {
            return (byte[])this._value;
        }
        return super.getBinaryValue();
    }

    public Object getPojo() {
        return this._value;
    }

    public int hashCode() {
        return this._value.hashCode();
    }

    public boolean isPojo() {
        return true;
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (this._value == null) {
            jsonGenerator.writeNull();
            return;
        }
        jsonGenerator.writeObject(this._value);
    }

    @Override
    public String toString() {
        return String.valueOf((Object)this._value);
    }
}


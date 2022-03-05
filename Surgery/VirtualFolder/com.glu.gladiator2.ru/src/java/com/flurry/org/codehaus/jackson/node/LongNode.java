/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.io.NumberOutput
 *  com.flurry.org.codehaus.jackson.map.SerializerProvider
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 *  java.math.BigDecimal
 *  java.math.BigInteger
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.io.NumberOutput;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.node.NumericNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class LongNode
extends NumericNode {
    final long _value;

    public LongNode(long l) {
        this._value = l;
    }

    public static LongNode valueOf(long l) {
        return new LongNode(l);
    }

    public boolean asBoolean(boolean bl) {
        return this._value != 0L;
    }

    @Override
    public String asText() {
        return NumberOutput.toString((long)this._value);
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_INT;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6 : {
            block5 : {
                if (object == this) break block5;
                if (object == null) {
                    return false;
                }
                if (object.getClass() != this.getClass()) {
                    return false;
                }
                if (((LongNode)object)._value != this._value) break block6;
            }
            return true;
        }
        return false;
    }

    @Override
    public BigInteger getBigIntegerValue() {
        return BigInteger.valueOf((long)this._value);
    }

    @Override
    public BigDecimal getDecimalValue() {
        return BigDecimal.valueOf((long)this._value);
    }

    @Override
    public double getDoubleValue() {
        return this._value;
    }

    @Override
    public int getIntValue() {
        return (int)this._value;
    }

    @Override
    public long getLongValue() {
        return this._value;
    }

    @Override
    public JsonParser.NumberType getNumberType() {
        return JsonParser.NumberType.LONG;
    }

    @Override
    public Number getNumberValue() {
        return this._value;
    }

    public int hashCode() {
        return (int)this._value ^ (int)(this._value >> 32);
    }

    public boolean isIntegralNumber() {
        return true;
    }

    public boolean isLong() {
        return true;
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(this._value);
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.io.NumberOutput
 *  com.flurry.org.codehaus.jackson.map.SerializerProvider
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Double
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

public final class DoubleNode
extends NumericNode {
    protected final double _value;

    public DoubleNode(double d2) {
        this._value = d2;
    }

    public static DoubleNode valueOf(double d2) {
        return new DoubleNode(d2);
    }

    @Override
    public String asText() {
        return NumberOutput.toString((double)this._value);
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_FLOAT;
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
                if (((DoubleNode)object)._value != this._value) break block6;
            }
            return true;
        }
        return false;
    }

    @Override
    public BigInteger getBigIntegerValue() {
        return this.getDecimalValue().toBigInteger();
    }

    @Override
    public BigDecimal getDecimalValue() {
        return BigDecimal.valueOf((double)this._value);
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
        return (long)this._value;
    }

    @Override
    public JsonParser.NumberType getNumberType() {
        return JsonParser.NumberType.DOUBLE;
    }

    @Override
    public Number getNumberValue() {
        return this._value;
    }

    public int hashCode() {
        long l = Double.doubleToLongBits((double)this._value);
        return (int)l ^ (int)(l >> 32);
    }

    public boolean isDouble() {
        return true;
    }

    public boolean isFloatingPointNumber() {
        return true;
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(this._value);
    }
}


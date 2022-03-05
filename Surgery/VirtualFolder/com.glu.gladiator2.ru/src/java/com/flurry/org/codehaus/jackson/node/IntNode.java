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

public final class IntNode
extends NumericNode {
    private static final IntNode[] CANONICALS = new IntNode[12];
    static final int MAX_CANONICAL = 10;
    static final int MIN_CANONICAL = -1;
    final int _value;

    static {
        for (int i2 = 0; i2 < 12; ++i2) {
            IntNode.CANONICALS[i2] = new IntNode(i2 - 1);
        }
    }

    public IntNode(int n) {
        this._value = n;
    }

    public static IntNode valueOf(int n) {
        if (n > 10 || n < -1) {
            return new IntNode(n);
        }
        return CANONICALS[n + 1];
    }

    public boolean asBoolean(boolean bl) {
        return this._value != 0;
    }

    @Override
    public String asText() {
        return NumberOutput.toString((int)this._value);
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
                if (((IntNode)object)._value != this._value) break block6;
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
        return this._value;
    }

    @Override
    public long getLongValue() {
        return this._value;
    }

    @Override
    public JsonParser.NumberType getNumberType() {
        return JsonParser.NumberType.INT;
    }

    @Override
    public Number getNumberValue() {
        return this._value;
    }

    public int hashCode() {
        return this._value;
    }

    public boolean isInt() {
        return true;
    }

    public boolean isIntegralNumber() {
        return true;
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(this._value);
    }
}


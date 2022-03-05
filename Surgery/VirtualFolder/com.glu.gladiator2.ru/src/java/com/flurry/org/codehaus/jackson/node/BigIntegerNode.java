/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
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
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.node.NumericNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class BigIntegerNode
extends NumericNode {
    protected final BigInteger _value;

    public BigIntegerNode(BigInteger bigInteger) {
        this._value = bigInteger;
    }

    public static BigIntegerNode valueOf(BigInteger bigInteger) {
        return new BigIntegerNode(bigInteger);
    }

    public boolean asBoolean(boolean bl) {
        return !BigInteger.ZERO.equals((Object)this._value);
    }

    @Override
    public String asText() {
        return this._value.toString();
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
                if (((BigIntegerNode)object)._value != this._value) break block6;
            }
            return true;
        }
        return false;
    }

    @Override
    public BigInteger getBigIntegerValue() {
        return this._value;
    }

    @Override
    public BigDecimal getDecimalValue() {
        return new BigDecimal(this._value);
    }

    @Override
    public double getDoubleValue() {
        return this._value.doubleValue();
    }

    @Override
    public int getIntValue() {
        return this._value.intValue();
    }

    @Override
    public long getLongValue() {
        return this._value.longValue();
    }

    @Override
    public JsonParser.NumberType getNumberType() {
        return JsonParser.NumberType.BIG_INTEGER;
    }

    @Override
    public Number getNumberValue() {
        return this._value;
    }

    public int hashCode() {
        return this._value.hashCode();
    }

    public boolean isBigInteger() {
        return true;
    }

    public boolean isIntegralNumber() {
        return true;
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(this._value);
    }
}


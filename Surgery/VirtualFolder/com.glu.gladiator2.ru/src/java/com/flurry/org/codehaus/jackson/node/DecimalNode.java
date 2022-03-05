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

public final class DecimalNode
extends NumericNode {
    protected final BigDecimal _value;

    public DecimalNode(BigDecimal bigDecimal) {
        this._value = bigDecimal;
    }

    public static DecimalNode valueOf(BigDecimal bigDecimal) {
        return new DecimalNode(bigDecimal);
    }

    @Override
    public String asText() {
        return this._value.toString();
    }

    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_FLOAT;
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
        return ((DecimalNode)object)._value.equals((Object)this._value);
    }

    @Override
    public BigInteger getBigIntegerValue() {
        return this._value.toBigInteger();
    }

    @Override
    public BigDecimal getDecimalValue() {
        return this._value;
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
        return JsonParser.NumberType.BIG_DECIMAL;
    }

    @Override
    public Number getNumberValue() {
        return this._value;
    }

    public int hashCode() {
        return this._value.hashCode();
    }

    public boolean isBigDecimal() {
        return true;
    }

    public boolean isFloatingPointNumber() {
        return true;
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(this._value);
    }
}


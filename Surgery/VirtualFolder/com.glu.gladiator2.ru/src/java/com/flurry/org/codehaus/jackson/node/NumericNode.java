/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Number
 *  java.lang.String
 *  java.math.BigDecimal
 *  java.math.BigInteger
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.node.ValueNode;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class NumericNode
extends ValueNode {
    protected NumericNode() {
    }

    public double asDouble() {
        return this.getDoubleValue();
    }

    public double asDouble(double d2) {
        return this.getDoubleValue();
    }

    public int asInt() {
        return this.getIntValue();
    }

    public int asInt(int n) {
        return this.getIntValue();
    }

    public long asLong() {
        return this.getLongValue();
    }

    public long asLong(long l) {
        return this.getLongValue();
    }

    public abstract String asText();

    public abstract BigInteger getBigIntegerValue();

    public abstract BigDecimal getDecimalValue();

    public abstract double getDoubleValue();

    public abstract int getIntValue();

    public abstract long getLongValue();

    public abstract JsonParser.NumberType getNumberType();

    public abstract Number getNumberValue();

    public final boolean isNumber() {
        return true;
    }
}


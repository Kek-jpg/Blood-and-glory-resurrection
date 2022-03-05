/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Byte
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.math.BigDecimal
 *  java.math.BigInteger
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.node.ArrayNode;
import com.flurry.org.codehaus.jackson.node.BigIntegerNode;
import com.flurry.org.codehaus.jackson.node.BinaryNode;
import com.flurry.org.codehaus.jackson.node.BooleanNode;
import com.flurry.org.codehaus.jackson.node.DecimalNode;
import com.flurry.org.codehaus.jackson.node.DoubleNode;
import com.flurry.org.codehaus.jackson.node.IntNode;
import com.flurry.org.codehaus.jackson.node.LongNode;
import com.flurry.org.codehaus.jackson.node.NullNode;
import com.flurry.org.codehaus.jackson.node.NumericNode;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.node.POJONode;
import com.flurry.org.codehaus.jackson.node.TextNode;
import com.flurry.org.codehaus.jackson.node.ValueNode;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNodeFactory {
    public static final JsonNodeFactory instance = new JsonNodeFactory();

    protected JsonNodeFactory() {
    }

    public POJONode POJONode(Object object) {
        return new POJONode(object);
    }

    public ArrayNode arrayNode() {
        return new ArrayNode(this);
    }

    public BinaryNode binaryNode(byte[] arrby) {
        return BinaryNode.valueOf(arrby);
    }

    public BinaryNode binaryNode(byte[] arrby, int n, int n2) {
        return BinaryNode.valueOf(arrby, n, n2);
    }

    public BooleanNode booleanNode(boolean bl) {
        if (bl) {
            return BooleanNode.getTrue();
        }
        return BooleanNode.getFalse();
    }

    public NullNode nullNode() {
        return NullNode.getInstance();
    }

    public NumericNode numberNode(byte by) {
        return IntNode.valueOf(by);
    }

    public NumericNode numberNode(double d2) {
        return DoubleNode.valueOf(d2);
    }

    public NumericNode numberNode(float f2) {
        return DoubleNode.valueOf(f2);
    }

    public NumericNode numberNode(int n) {
        return IntNode.valueOf(n);
    }

    public NumericNode numberNode(long l) {
        return LongNode.valueOf(l);
    }

    public NumericNode numberNode(BigDecimal bigDecimal) {
        return DecimalNode.valueOf(bigDecimal);
    }

    public NumericNode numberNode(BigInteger bigInteger) {
        return BigIntegerNode.valueOf(bigInteger);
    }

    public NumericNode numberNode(short s) {
        return IntNode.valueOf(s);
    }

    public ValueNode numberNode(Byte by) {
        if (by == null) {
            return this.nullNode();
        }
        return IntNode.valueOf(by.intValue());
    }

    public ValueNode numberNode(Double d2) {
        if (d2 == null) {
            return this.nullNode();
        }
        return DoubleNode.valueOf(d2);
    }

    public ValueNode numberNode(Float f2) {
        if (f2 == null) {
            return this.nullNode();
        }
        return DoubleNode.valueOf(f2.doubleValue());
    }

    public ValueNode numberNode(Integer n) {
        if (n == null) {
            return this.nullNode();
        }
        return IntNode.valueOf(n);
    }

    public ValueNode numberNode(Long l) {
        if (l == null) {
            return this.nullNode();
        }
        return LongNode.valueOf(l);
    }

    public ValueNode numberNode(Short s) {
        if (s == null) {
            return this.nullNode();
        }
        return IntNode.valueOf(s.shortValue());
    }

    public ObjectNode objectNode() {
        return new ObjectNode(this);
    }

    public TextNode textNode(String string2) {
        return TextNode.valueOf(string2);
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Iterable
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.math.BigDecimal
 *  java.math.BigInteger
 *  java.util.Collections
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 */
package com.flurry.org.codehaus.jackson;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonToken;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class JsonNode
implements Iterable<JsonNode> {
    protected static final List<JsonNode> NO_NODES = Collections.emptyList();
    protected static final List<String> NO_STRINGS = Collections.emptyList();

    protected JsonNode() {
    }

    public boolean asBoolean() {
        return this.asBoolean(false);
    }

    public boolean asBoolean(boolean bl) {
        return bl;
    }

    public double asDouble() {
        return this.asDouble(0.0);
    }

    public double asDouble(double d2) {
        return d2;
    }

    public int asInt() {
        return this.asInt(0);
    }

    public int asInt(int n2) {
        return n2;
    }

    public long asLong() {
        return this.asLong(0L);
    }

    public long asLong(long l2) {
        return l2;
    }

    public abstract String asText();

    public abstract JsonToken asToken();

    public abstract boolean equals(Object var1);

    public abstract JsonNode findParent(String var1);

    public final List<JsonNode> findParents(String string) {
        List list = this.findParents(string, null);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    public abstract List<JsonNode> findParents(String var1, List<JsonNode> var2);

    public abstract JsonNode findPath(String var1);

    public abstract JsonNode findValue(String var1);

    public final List<JsonNode> findValues(String string) {
        List list = this.findValues(string, null);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    public abstract List<JsonNode> findValues(String var1, List<JsonNode> var2);

    public final List<String> findValuesAsText(String string) {
        List list = this.findValuesAsText(string, null);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    public abstract List<String> findValuesAsText(String var1, List<String> var2);

    public JsonNode get(int n2) {
        return null;
    }

    public JsonNode get(String string) {
        return null;
    }

    public BigInteger getBigIntegerValue() {
        return BigInteger.ZERO;
    }

    public byte[] getBinaryValue() throws IOException {
        return null;
    }

    public boolean getBooleanValue() {
        return false;
    }

    public BigDecimal getDecimalValue() {
        return BigDecimal.ZERO;
    }

    public double getDoubleValue() {
        return 0.0;
    }

    public Iterator<JsonNode> getElements() {
        return NO_NODES.iterator();
    }

    public Iterator<String> getFieldNames() {
        return NO_STRINGS.iterator();
    }

    public Iterator<Map.Entry<String, JsonNode>> getFields() {
        return Collections.emptyList().iterator();
    }

    public int getIntValue() {
        return 0;
    }

    public long getLongValue() {
        return 0L;
    }

    public abstract JsonParser.NumberType getNumberType();

    public Number getNumberValue() {
        return null;
    }

    @Deprecated
    public final JsonNode getPath(int n2) {
        return this.path(n2);
    }

    @Deprecated
    public final JsonNode getPath(String string) {
        return this.path(string);
    }

    public String getTextValue() {
        return null;
    }

    @Deprecated
    public boolean getValueAsBoolean() {
        return this.asBoolean(false);
    }

    @Deprecated
    public boolean getValueAsBoolean(boolean bl) {
        return this.asBoolean(bl);
    }

    @Deprecated
    public double getValueAsDouble() {
        return this.asDouble(0.0);
    }

    @Deprecated
    public double getValueAsDouble(double d2) {
        return this.asDouble(d2);
    }

    @Deprecated
    public int getValueAsInt() {
        return this.asInt(0);
    }

    @Deprecated
    public int getValueAsInt(int n2) {
        return this.asInt(n2);
    }

    @Deprecated
    public long getValueAsLong() {
        return this.asLong(0L);
    }

    @Deprecated
    public long getValueAsLong(long l2) {
        return this.asLong(l2);
    }

    @Deprecated
    public String getValueAsText() {
        return this.asText();
    }

    public boolean has(int n2) {
        return this.get(n2) != null;
    }

    public boolean has(String string) {
        return this.get(string) != null;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isBigDecimal() {
        return false;
    }

    public boolean isBigInteger() {
        return false;
    }

    public boolean isBinary() {
        return false;
    }

    public boolean isBoolean() {
        return false;
    }

    public boolean isContainerNode() {
        return false;
    }

    public boolean isDouble() {
        return false;
    }

    public boolean isFloatingPointNumber() {
        return false;
    }

    public boolean isInt() {
        return false;
    }

    public boolean isIntegralNumber() {
        return false;
    }

    public boolean isLong() {
        return false;
    }

    public boolean isMissingNode() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isObject() {
        return false;
    }

    public boolean isPojo() {
        return false;
    }

    public boolean isTextual() {
        return false;
    }

    public boolean isValueNode() {
        return false;
    }

    public final Iterator<JsonNode> iterator() {
        return this.getElements();
    }

    public abstract JsonNode path(int var1);

    public abstract JsonNode path(String var1);

    public int size() {
        return 0;
    }

    public abstract String toString();

    public abstract JsonParser traverse();

    public JsonNode with(String string) {
        throw new UnsupportedOperationException("JsonNode not of type ObjectNode (but " + this.getClass().getName() + "), can not call with() on it");
    }
}


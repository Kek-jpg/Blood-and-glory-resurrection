/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.math.BigDecimal
 *  java.util.Iterator
 *  java.util.List
 *  java.util.NoSuchElementException
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.node.ArrayNode;
import com.flurry.org.codehaus.jackson.node.BaseJsonNode;
import com.flurry.org.codehaus.jackson.node.BinaryNode;
import com.flurry.org.codehaus.jackson.node.BooleanNode;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.node.NullNode;
import com.flurry.org.codehaus.jackson.node.NumericNode;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.node.POJONode;
import com.flurry.org.codehaus.jackson.node.TextNode;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class ContainerNode
extends BaseJsonNode {
    JsonNodeFactory _nodeFactory;

    protected ContainerNode(JsonNodeFactory jsonNodeFactory) {
        this._nodeFactory = jsonNodeFactory;
    }

    public final POJONode POJONode(Object object) {
        return this._nodeFactory.POJONode(object);
    }

    public final ArrayNode arrayNode() {
        return this._nodeFactory.arrayNode();
    }

    @Override
    public String asText() {
        return "";
    }

    @Override
    public abstract JsonToken asToken();

    public final BinaryNode binaryNode(byte[] arrby) {
        return this._nodeFactory.binaryNode(arrby);
    }

    public final BinaryNode binaryNode(byte[] arrby, int n, int n2) {
        return this._nodeFactory.binaryNode(arrby, n, n2);
    }

    public final BooleanNode booleanNode(boolean bl) {
        return this._nodeFactory.booleanNode(bl);
    }

    @Override
    public abstract ObjectNode findParent(String var1);

    @Override
    public abstract List<JsonNode> findParents(String var1, List<JsonNode> var2);

    @Override
    public abstract JsonNode findValue(String var1);

    @Override
    public abstract List<JsonNode> findValues(String var1, List<JsonNode> var2);

    @Override
    public abstract List<String> findValuesAsText(String var1, List<String> var2);

    @Override
    public abstract JsonNode get(int var1);

    @Override
    public abstract JsonNode get(String var1);

    @Override
    public String getValueAsText() {
        return null;
    }

    @Override
    public boolean isContainerNode() {
        return true;
    }

    public final NullNode nullNode() {
        return this._nodeFactory.nullNode();
    }

    public final NumericNode numberNode(byte by) {
        return this._nodeFactory.numberNode(by);
    }

    public final NumericNode numberNode(double d) {
        return this._nodeFactory.numberNode(d);
    }

    public final NumericNode numberNode(float f) {
        return this._nodeFactory.numberNode(f);
    }

    public final NumericNode numberNode(int n) {
        return this._nodeFactory.numberNode(n);
    }

    public final NumericNode numberNode(long l) {
        return this._nodeFactory.numberNode(l);
    }

    public final NumericNode numberNode(BigDecimal bigDecimal) {
        return this._nodeFactory.numberNode(bigDecimal);
    }

    public final NumericNode numberNode(short s) {
        return this._nodeFactory.numberNode(s);
    }

    public final ObjectNode objectNode() {
        return this._nodeFactory.objectNode();
    }

    public abstract ContainerNode removeAll();

    @Override
    public abstract int size();

    public final TextNode textNode(String string) {
        return this._nodeFactory.textNode(string);
    }

}


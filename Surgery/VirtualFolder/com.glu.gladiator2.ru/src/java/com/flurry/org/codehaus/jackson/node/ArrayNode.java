/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.SerializerProvider
 *  com.flurry.org.codehaus.jackson.map.TypeSerializer
 *  com.flurry.org.codehaus.jackson.node.BaseJsonNode
 *  com.flurry.org.codehaus.jackson.node.ContainerNode
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.IndexOutOfBoundsException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.math.BigDecimal
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Iterator
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.node.BaseJsonNode;
import com.flurry.org.codehaus.jackson.node.BinaryNode;
import com.flurry.org.codehaus.jackson.node.BooleanNode;
import com.flurry.org.codehaus.jackson.node.ContainerNode;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.node.MissingNode;
import com.flurry.org.codehaus.jackson.node.NullNode;
import com.flurry.org.codehaus.jackson.node.NumericNode;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.node.POJONode;
import com.flurry.org.codehaus.jackson.node.TextNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class ArrayNode
extends ContainerNode {
    protected ArrayList<JsonNode> _children;

    public ArrayNode(JsonNodeFactory jsonNodeFactory) {
        super(jsonNodeFactory);
    }

    private void _add(JsonNode jsonNode) {
        if (this._children == null) {
            this._children = new ArrayList();
        }
        this._children.add((Object)jsonNode);
    }

    private void _insert(int n, JsonNode jsonNode) {
        if (this._children == null) {
            this._children = new ArrayList();
            this._children.add((Object)jsonNode);
            return;
        }
        if (n < 0) {
            this._children.add(0, (Object)jsonNode);
            return;
        }
        if (n >= this._children.size()) {
            this._children.add((Object)jsonNode);
            return;
        }
        this._children.add(n, (Object)jsonNode);
    }

    private boolean _sameChildren(ArrayList<JsonNode> arrayList) {
        int n = arrayList.size();
        if (this.size() != n) {
            return false;
        }
        for (int i2 = 0; i2 < n; ++i2) {
            if (((JsonNode)this._children.get(i2)).equals(arrayList.get(i2))) continue;
            return false;
        }
        return true;
    }

    public JsonNode _set(int n, JsonNode jsonNode) {
        if (this._children == null || n < 0 || n >= this._children.size()) {
            throw new IndexOutOfBoundsException("Illegal index " + n + ", array size " + this.size());
        }
        return (JsonNode)this._children.set(n, (Object)jsonNode);
    }

    public void add(double d2) {
        ArrayNode.super._add((JsonNode)((Object)this.numberNode(d2)));
    }

    public void add(float f2) {
        ArrayNode.super._add((JsonNode)((Object)this.numberNode(f2)));
    }

    public void add(int n) {
        ArrayNode.super._add((JsonNode)((Object)this.numberNode(n)));
    }

    public void add(long l) {
        ArrayNode.super._add((JsonNode)((Object)this.numberNode(l)));
    }

    public void add(JsonNode object) {
        if (object == null) {
            object = this.nullNode();
        }
        ArrayNode.super._add((JsonNode)object);
    }

    public void add(Boolean bl) {
        if (bl == null) {
            this.addNull();
            return;
        }
        ArrayNode.super._add((JsonNode)((Object)this.booleanNode(bl.booleanValue())));
    }

    public void add(Double d2) {
        if (d2 == null) {
            this.addNull();
            return;
        }
        ArrayNode.super._add((JsonNode)((Object)this.numberNode(d2.doubleValue())));
    }

    public void add(Float f2) {
        if (f2 == null) {
            this.addNull();
            return;
        }
        ArrayNode.super._add((JsonNode)((Object)this.numberNode(f2.floatValue())));
    }

    public void add(Integer n) {
        if (n == null) {
            this.addNull();
            return;
        }
        ArrayNode.super._add((JsonNode)((Object)this.numberNode(n.intValue())));
    }

    public void add(Long l) {
        if (l == null) {
            this.addNull();
            return;
        }
        ArrayNode.super._add((JsonNode)((Object)this.numberNode(l.longValue())));
    }

    public void add(String string2) {
        if (string2 == null) {
            this.addNull();
            return;
        }
        ArrayNode.super._add((JsonNode)((Object)this.textNode(string2)));
    }

    public void add(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            this.addNull();
            return;
        }
        ArrayNode.super._add((JsonNode)((Object)this.numberNode(bigDecimal)));
    }

    public void add(boolean bl) {
        ArrayNode.super._add((JsonNode)((Object)this.booleanNode(bl)));
    }

    public void add(byte[] arrby) {
        if (arrby == null) {
            this.addNull();
            return;
        }
        ArrayNode.super._add((JsonNode)((Object)this.binaryNode(arrby)));
    }

    public JsonNode addAll(ArrayNode arrayNode) {
        int n = arrayNode.size();
        if (n > 0) {
            if (this._children == null) {
                this._children = new ArrayList(n + 2);
            }
            arrayNode.addContentsTo((List<JsonNode>)this._children);
        }
        return this;
    }

    public JsonNode addAll(Collection<JsonNode> collection) {
        block3 : {
            block2 : {
                if (collection.size() <= 0) break block2;
                if (this._children != null) break block3;
                this._children = new ArrayList(collection);
            }
            return this;
        }
        this._children.addAll(collection);
        return this;
    }

    public ArrayNode addArray() {
        ArrayNode arrayNode = this.arrayNode();
        this._add((JsonNode)((Object)arrayNode));
        return arrayNode;
    }

    protected void addContentsTo(List<JsonNode> list) {
        if (this._children != null) {
            Iterator iterator = this._children.iterator();
            while (iterator.hasNext()) {
                list.add((Object)((JsonNode)iterator.next()));
            }
        }
    }

    public void addNull() {
        this._add((JsonNode)((Object)this.nullNode()));
    }

    public ObjectNode addObject() {
        ObjectNode objectNode = this.objectNode();
        this._add((JsonNode)((Object)objectNode));
        return objectNode;
    }

    public void addPOJO(Object object) {
        if (object == null) {
            this.addNull();
            return;
        }
        ArrayNode.super._add((JsonNode)((Object)this.POJONode(object)));
    }

    public JsonToken asToken() {
        return JsonToken.START_ARRAY;
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
                ArrayNode arrayNode = (ArrayNode)((Object)object);
                if (this._children != null && this._children.size() != 0) {
                    return arrayNode._sameChildren(this._children);
                }
                if (arrayNode.size() != 0) break block7;
            }
            return true;
        }
        return false;
    }

    public ObjectNode findParent(String string2) {
        if (this._children != null) {
            Iterator iterator = this._children.iterator();
            while (iterator.hasNext()) {
                JsonNode jsonNode = ((JsonNode)iterator.next()).findParent(string2);
                if (jsonNode == null) continue;
                return (ObjectNode)((Object)jsonNode);
            }
        }
        return null;
    }

    public List<JsonNode> findParents(String string2, List<JsonNode> list) {
        if (this._children != null) {
            Iterator iterator = this._children.iterator();
            while (iterator.hasNext()) {
                list = ((JsonNode)iterator.next()).findParents(string2, list);
            }
        }
        return list;
    }

    public JsonNode findValue(String string2) {
        if (this._children != null) {
            Iterator iterator = this._children.iterator();
            while (iterator.hasNext()) {
                JsonNode jsonNode = ((JsonNode)iterator.next()).findValue(string2);
                if (jsonNode == null) continue;
                return jsonNode;
            }
        }
        return null;
    }

    public List<JsonNode> findValues(String string2, List<JsonNode> list) {
        if (this._children != null) {
            Iterator iterator = this._children.iterator();
            while (iterator.hasNext()) {
                list = ((JsonNode)iterator.next()).findValues(string2, list);
            }
        }
        return list;
    }

    public List<String> findValuesAsText(String string2, List<String> list) {
        if (this._children != null) {
            Iterator iterator = this._children.iterator();
            while (iterator.hasNext()) {
                list = ((JsonNode)iterator.next()).findValuesAsText(string2, list);
            }
        }
        return list;
    }

    public JsonNode get(int n) {
        if (n >= 0 && this._children != null && n < this._children.size()) {
            return (JsonNode)this._children.get(n);
        }
        return null;
    }

    public JsonNode get(String string2) {
        return null;
    }

    public Iterator<JsonNode> getElements() {
        if (this._children == null) {
            return ContainerNode.NoNodesIterator.instance();
        }
        return this._children.iterator();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        if (this._children == null) {
            return 1;
        }
        int n = this._children.size();
        Iterator iterator = this._children.iterator();
        while (iterator.hasNext()) {
            JsonNode jsonNode = (JsonNode)iterator.next();
            if (jsonNode == null) continue;
            n ^= jsonNode.hashCode();
        }
        return n;
    }

    public void insert(int n, double d2) {
        ArrayNode.super._insert(n, (JsonNode)((Object)this.numberNode(d2)));
    }

    public void insert(int n, float f2) {
        ArrayNode.super._insert(n, (JsonNode)((Object)this.numberNode(f2)));
    }

    public void insert(int n, int n2) {
        ArrayNode.super._insert(n, (JsonNode)((Object)this.numberNode(n2)));
    }

    public void insert(int n, long l) {
        ArrayNode.super._insert(n, (JsonNode)((Object)this.numberNode(l)));
    }

    public void insert(int n, JsonNode object) {
        if (object == null) {
            object = this.nullNode();
        }
        ArrayNode.super._insert(n, (JsonNode)object);
    }

    public void insert(int n, Boolean bl) {
        if (bl == null) {
            this.insertNull(n);
            return;
        }
        ArrayNode.super._insert(n, (JsonNode)((Object)this.booleanNode(bl.booleanValue())));
    }

    public void insert(int n, Double d2) {
        if (d2 == null) {
            this.insertNull(n);
            return;
        }
        ArrayNode.super._insert(n, (JsonNode)((Object)this.numberNode(d2.doubleValue())));
    }

    public void insert(int n, Float f2) {
        if (f2 == null) {
            this.insertNull(n);
            return;
        }
        ArrayNode.super._insert(n, (JsonNode)((Object)this.numberNode(f2.floatValue())));
    }

    public void insert(int n, Integer n2) {
        if (n2 == null) {
            this.insertNull(n);
            return;
        }
        ArrayNode.super._insert(n, (JsonNode)((Object)this.numberNode(n2.intValue())));
    }

    public void insert(int n, Long l) {
        if (l == null) {
            this.insertNull(n);
            return;
        }
        ArrayNode.super._insert(n, (JsonNode)((Object)this.numberNode(l.longValue())));
    }

    public void insert(int n, String string2) {
        if (string2 == null) {
            this.insertNull(n);
            return;
        }
        ArrayNode.super._insert(n, (JsonNode)((Object)this.textNode(string2)));
    }

    public void insert(int n, BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            this.insertNull(n);
            return;
        }
        ArrayNode.super._insert(n, (JsonNode)((Object)this.numberNode(bigDecimal)));
    }

    public void insert(int n, boolean bl) {
        ArrayNode.super._insert(n, (JsonNode)((Object)this.booleanNode(bl)));
    }

    public void insert(int n, byte[] arrby) {
        if (arrby == null) {
            this.insertNull(n);
            return;
        }
        ArrayNode.super._insert(n, (JsonNode)((Object)this.binaryNode(arrby)));
    }

    public ArrayNode insertArray(int n) {
        ArrayNode arrayNode = this.arrayNode();
        ArrayNode.super._insert(n, (JsonNode)((Object)arrayNode));
        return arrayNode;
    }

    public void insertNull(int n) {
        ArrayNode.super._insert(n, (JsonNode)((Object)this.nullNode()));
    }

    public ObjectNode insertObject(int n) {
        ObjectNode objectNode = this.objectNode();
        ArrayNode.super._insert(n, (JsonNode)((Object)objectNode));
        return objectNode;
    }

    public void insertPOJO(int n, Object object) {
        if (object == null) {
            this.insertNull(n);
            return;
        }
        ArrayNode.super._insert(n, (JsonNode)((Object)this.POJONode(object)));
    }

    public boolean isArray() {
        return true;
    }

    public JsonNode path(int n) {
        if (n >= 0 && this._children != null && n < this._children.size()) {
            return (JsonNode)this._children.get(n);
        }
        return MissingNode.getInstance();
    }

    public JsonNode path(String string2) {
        return MissingNode.getInstance();
    }

    public JsonNode remove(int n) {
        if (n >= 0 && this._children != null && n < this._children.size()) {
            return (JsonNode)this._children.remove(n);
        }
        return null;
    }

    public ArrayNode removeAll() {
        this._children = null;
        return this;
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartArray();
        if (this._children != null) {
            Iterator iterator = this._children.iterator();
            while (iterator.hasNext()) {
                ((BaseJsonNode)((JsonNode)iterator.next())).serialize(jsonGenerator, serializerProvider);
            }
        }
        jsonGenerator.writeEndArray();
    }

    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        typeSerializer.writeTypePrefixForArray((Object)this, jsonGenerator);
        if (this._children != null) {
            Iterator iterator = this._children.iterator();
            while (iterator.hasNext()) {
                ((BaseJsonNode)((JsonNode)iterator.next())).serialize(jsonGenerator, serializerProvider);
            }
        }
        typeSerializer.writeTypeSuffixForArray((Object)this, jsonGenerator);
    }

    public JsonNode set(int n, JsonNode object) {
        if (object == null) {
            object = this.nullNode();
        }
        return this._set(n, (JsonNode)object);
    }

    public int size() {
        if (this._children == null) {
            return 0;
        }
        return this._children.size();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(16 + (this.size() << 4));
        stringBuilder.append('[');
        if (this._children != null) {
            int n = this._children.size();
            for (int i2 = 0; i2 < n; ++i2) {
                if (i2 > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(((JsonNode)this._children.get(i2)).toString());
            }
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}


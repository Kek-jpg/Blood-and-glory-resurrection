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
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.UnsupportedOperationException
 *  java.math.BigDecimal
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.NoSuchElementException
 *  java.util.Set
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.node.ArrayNode;
import com.flurry.org.codehaus.jackson.node.BaseJsonNode;
import com.flurry.org.codehaus.jackson.node.BinaryNode;
import com.flurry.org.codehaus.jackson.node.BooleanNode;
import com.flurry.org.codehaus.jackson.node.ContainerNode;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.node.MissingNode;
import com.flurry.org.codehaus.jackson.node.NullNode;
import com.flurry.org.codehaus.jackson.node.NumericNode;
import com.flurry.org.codehaus.jackson.node.POJONode;
import com.flurry.org.codehaus.jackson.node.TextNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ObjectNode
extends ContainerNode {
    protected LinkedHashMap<String, JsonNode> _children = null;

    public ObjectNode(JsonNodeFactory jsonNodeFactory) {
        super(jsonNodeFactory);
    }

    private final JsonNode _put(String string2, JsonNode jsonNode) {
        if (this._children == null) {
            this._children = new LinkedHashMap();
        }
        return (JsonNode)this._children.put((Object)string2, (Object)jsonNode);
    }

    public JsonToken asToken() {
        return JsonToken.START_OBJECT;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        JsonNode jsonNode;
        JsonNode jsonNode2;
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        ObjectNode objectNode = (ObjectNode)((Object)object);
        if (objectNode.size() != this.size()) {
            return false;
        }
        if (this._children == null) return true;
        Iterator iterator = this._children.entrySet().iterator();
        do {
            if (!iterator.hasNext()) return true;
            Map.Entry entry = (Map.Entry)iterator.next();
            String string2 = (String)entry.getKey();
            jsonNode2 = (JsonNode)entry.getValue();
            jsonNode = objectNode.get(string2);
            if (jsonNode == null) return false;
        } while (jsonNode.equals(jsonNode2));
        return false;
    }

    public ObjectNode findParent(String string2) {
        if (this._children != null) {
            for (Map.Entry entry : this._children.entrySet()) {
                if (string2.equals(entry.getKey())) {
                    return this;
                }
                JsonNode jsonNode = ((JsonNode)entry.getValue()).findParent(string2);
                if (jsonNode == null) continue;
                return (ObjectNode)((Object)jsonNode);
            }
        }
        return null;
    }

    public List<JsonNode> findParents(String string2, List<JsonNode> list) {
        if (this._children != null) {
            for (Map.Entry entry : this._children.entrySet()) {
                if (string2.equals(entry.getKey())) {
                    if (list == null) {
                        list = new List<JsonNode>();
                    }
                    list.add((Object)this);
                    continue;
                }
                list = ((JsonNode)entry.getValue()).findParents(string2, list);
            }
        }
        return list;
    }

    public JsonNode findValue(String string2) {
        if (this._children != null) {
            for (Map.Entry entry : this._children.entrySet()) {
                if (string2.equals(entry.getKey())) {
                    return (JsonNode)entry.getValue();
                }
                JsonNode jsonNode = ((JsonNode)entry.getValue()).findValue(string2);
                if (jsonNode == null) continue;
                return jsonNode;
            }
        }
        return null;
    }

    public List<JsonNode> findValues(String string2, List<JsonNode> list) {
        if (this._children != null) {
            for (Map.Entry entry : this._children.entrySet()) {
                if (string2.equals(entry.getKey())) {
                    if (list == null) {
                        list = new List<JsonNode>();
                    }
                    list.add(entry.getValue());
                    continue;
                }
                list = ((JsonNode)entry.getValue()).findValues(string2, list);
            }
        }
        return list;
    }

    public List<String> findValuesAsText(String string2, List<String> list) {
        if (this._children != null) {
            for (Map.Entry entry : this._children.entrySet()) {
                if (string2.equals(entry.getKey())) {
                    if (list == null) {
                        list = new List<String>();
                    }
                    list.add((Object)((JsonNode)entry.getValue()).asText());
                    continue;
                }
                list = ((JsonNode)entry.getValue()).findValuesAsText(string2, list);
            }
        }
        return list;
    }

    public JsonNode get(int n) {
        return null;
    }

    public JsonNode get(String string2) {
        if (this._children != null) {
            return (JsonNode)this._children.get((Object)string2);
        }
        return null;
    }

    public Iterator<JsonNode> getElements() {
        if (this._children == null) {
            return ContainerNode.NoNodesIterator.instance();
        }
        return this._children.values().iterator();
    }

    public Iterator<String> getFieldNames() {
        if (this._children == null) {
            return ContainerNode.NoStringsIterator.instance();
        }
        return this._children.keySet().iterator();
    }

    public Iterator<Map.Entry<String, JsonNode>> getFields() {
        if (this._children == null) {
            return NoFieldsIterator.instance;
        }
        return this._children.entrySet().iterator();
    }

    public int hashCode() {
        if (this._children == null) {
            return -1;
        }
        return this._children.hashCode();
    }

    public boolean isObject() {
        return true;
    }

    public JsonNode path(int n) {
        return MissingNode.getInstance();
    }

    public JsonNode path(String string2) {
        JsonNode jsonNode;
        if (this._children != null && (jsonNode = (JsonNode)this._children.get((Object)string2)) != null) {
            return jsonNode;
        }
        return MissingNode.getInstance();
    }

    public JsonNode put(String string2, JsonNode object) {
        if (object == null) {
            object = this.nullNode();
        }
        return ObjectNode.super._put(string2, (JsonNode)object);
    }

    public void put(String string2, double d2) {
        ObjectNode.super._put(string2, (JsonNode)((Object)this.numberNode(d2)));
    }

    public void put(String string2, float f2) {
        ObjectNode.super._put(string2, (JsonNode)((Object)this.numberNode(f2)));
    }

    public void put(String string2, int n) {
        ObjectNode.super._put(string2, (JsonNode)((Object)this.numberNode(n)));
    }

    public void put(String string2, long l) {
        ObjectNode.super._put(string2, (JsonNode)((Object)this.numberNode(l)));
    }

    public void put(String string2, Boolean bl) {
        if (bl == null) {
            ObjectNode.super._put(string2, (JsonNode)((Object)this.nullNode()));
            return;
        }
        ObjectNode.super._put(string2, (JsonNode)((Object)this.booleanNode(bl.booleanValue())));
    }

    public void put(String string2, Double d2) {
        if (d2 == null) {
            ObjectNode.super._put(string2, (JsonNode)((Object)this.nullNode()));
            return;
        }
        ObjectNode.super._put(string2, (JsonNode)((Object)this.numberNode(d2.doubleValue())));
    }

    public void put(String string2, Float f2) {
        if (f2 == null) {
            ObjectNode.super._put(string2, (JsonNode)((Object)this.nullNode()));
            return;
        }
        ObjectNode.super._put(string2, (JsonNode)((Object)this.numberNode(f2.floatValue())));
    }

    public void put(String string2, Integer n) {
        if (n == null) {
            ObjectNode.super._put(string2, (JsonNode)((Object)this.nullNode()));
            return;
        }
        ObjectNode.super._put(string2, (JsonNode)((Object)this.numberNode(n.intValue())));
    }

    public void put(String string2, Long l) {
        if (l == null) {
            ObjectNode.super._put(string2, (JsonNode)((Object)this.nullNode()));
            return;
        }
        ObjectNode.super._put(string2, (JsonNode)((Object)this.numberNode(l.longValue())));
    }

    public void put(String string2, String string3) {
        if (string3 == null) {
            this.putNull(string2);
            return;
        }
        ObjectNode.super._put(string2, (JsonNode)((Object)this.textNode(string3)));
    }

    public void put(String string2, BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            this.putNull(string2);
            return;
        }
        ObjectNode.super._put(string2, (JsonNode)((Object)this.numberNode(bigDecimal)));
    }

    public void put(String string2, boolean bl) {
        ObjectNode.super._put(string2, (JsonNode)((Object)this.booleanNode(bl)));
    }

    public void put(String string2, byte[] arrby) {
        if (arrby == null) {
            ObjectNode.super._put(string2, (JsonNode)((Object)this.nullNode()));
            return;
        }
        ObjectNode.super._put(string2, (JsonNode)((Object)this.binaryNode(arrby)));
    }

    public JsonNode putAll(ObjectNode objectNode) {
        int n = objectNode.size();
        if (n > 0) {
            if (this._children == null) {
                this._children = new LinkedHashMap(n);
            }
            objectNode.putContentsTo((Map<String, JsonNode>)this._children);
        }
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonNode putAll(Map<String, JsonNode> map) {
        if (this._children == null) {
            this._children = new LinkedHashMap(map);
            return this;
        } else {
            for (Map.Entry entry : map.entrySet()) {
                void var4_3;
                JsonNode jsonNode = (JsonNode)entry.getValue();
                if (jsonNode == null) {
                    NullNode nullNode = this.nullNode();
                }
                this._children.put(entry.getKey(), (Object)var4_3);
            }
        }
        return this;
    }

    public ArrayNode putArray(String string2) {
        ArrayNode arrayNode = this.arrayNode();
        ObjectNode.super._put(string2, (JsonNode)((Object)arrayNode));
        return arrayNode;
    }

    protected void putContentsTo(Map<String, JsonNode> map) {
        if (this._children != null) {
            for (Map.Entry entry : this._children.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void putNull(String string2) {
        ObjectNode.super._put(string2, (JsonNode)((Object)this.nullNode()));
    }

    public ObjectNode putObject(String string2) {
        ObjectNode objectNode = this.objectNode();
        ObjectNode.super._put(string2, (JsonNode)((Object)objectNode));
        return objectNode;
    }

    public void putPOJO(String string2, Object object) {
        ObjectNode.super._put(string2, (JsonNode)((Object)this.POJONode(object)));
    }

    public JsonNode remove(String string2) {
        if (this._children != null) {
            return (JsonNode)this._children.remove((Object)string2);
        }
        return null;
    }

    public ObjectNode remove(Collection<String> collection) {
        if (this._children != null) {
            for (String string2 : collection) {
                this._children.remove((Object)string2);
            }
        }
        return this;
    }

    public ObjectNode removeAll() {
        this._children = null;
        return this;
    }

    public ObjectNode retain(Collection<String> collection) {
        if (this._children != null) {
            Iterator iterator = this._children.entrySet().iterator();
            while (iterator.hasNext()) {
                if (collection.contains(((Map.Entry)iterator.next()).getKey())) continue;
                iterator.remove();
            }
        }
        return this;
    }

    public /* varargs */ ObjectNode retain(String ... arrstring) {
        return this.retain((Collection<String>)Arrays.asList((Object[])arrstring));
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        if (this._children != null) {
            for (Map.Entry entry : this._children.entrySet()) {
                jsonGenerator.writeFieldName((String)entry.getKey());
                ((BaseJsonNode)entry.getValue()).serialize(jsonGenerator, serializerProvider);
            }
        }
        jsonGenerator.writeEndObject();
    }

    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        typeSerializer.writeTypePrefixForObject((Object)this, jsonGenerator);
        if (this._children != null) {
            for (Map.Entry entry : this._children.entrySet()) {
                jsonGenerator.writeFieldName((String)entry.getKey());
                ((BaseJsonNode)entry.getValue()).serialize(jsonGenerator, serializerProvider);
            }
        }
        typeSerializer.writeTypeSuffixForObject((Object)this, jsonGenerator);
    }

    public int size() {
        if (this._children == null) {
            return 0;
        }
        return this._children.size();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(32 + (this.size() << 4));
        stringBuilder.append("{");
        if (this._children != null) {
            int n = 0;
            for (Map.Entry entry : this._children.entrySet()) {
                if (n > 0) {
                    stringBuilder.append(",");
                }
                ++n;
                TextNode.appendQuoted(stringBuilder, (String)entry.getKey());
                stringBuilder.append(':');
                stringBuilder.append(((JsonNode)entry.getValue()).toString());
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public ObjectNode with(String string2) {
        if (this._children == null) {
            this._children = new LinkedHashMap();
        } else {
            JsonNode jsonNode = (JsonNode)this._children.get((Object)string2);
            if (jsonNode != null) {
                if (!(jsonNode instanceof ObjectNode)) throw new UnsupportedOperationException("Property '" + string2 + "' has value that is not of type ObjectNode (but " + jsonNode.getClass().getName() + ")");
                return (ObjectNode)((Object)jsonNode);
            }
        }
        ObjectNode objectNode = this.objectNode();
        this._children.put((Object)string2, (Object)objectNode);
        return objectNode;
    }

    protected static class NoFieldsIterator
    implements Iterator<Map.Entry<String, JsonNode>> {
        static final NoFieldsIterator instance = new NoFieldsIterator();

        private NoFieldsIterator() {
        }

        public boolean hasNext() {
            return false;
        }

        public Map.Entry<String, JsonNode> next() {
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new IllegalStateException();
        }
    }

}


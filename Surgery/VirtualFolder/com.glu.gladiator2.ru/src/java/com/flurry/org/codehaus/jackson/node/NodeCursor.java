/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.node.ContainerNode
 *  java.lang.Class
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Map$Entry
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.node.ContainerNode;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import java.util.Iterator;
import java.util.Map;

abstract class NodeCursor
extends JsonStreamContext {
    final NodeCursor _parent;

    public NodeCursor(int n, NodeCursor nodeCursor) {
        this._type = n;
        this._index = -1;
        this._parent = nodeCursor;
    }

    public abstract boolean currentHasChildren();

    public abstract JsonNode currentNode();

    public abstract JsonToken endToken();

    @Override
    public abstract String getCurrentName();

    @Override
    public final NodeCursor getParent() {
        return this._parent;
    }

    public final NodeCursor iterateChildren() {
        JsonNode jsonNode = this.currentNode();
        if (jsonNode == null) {
            throw new IllegalStateException("No current node");
        }
        if (jsonNode.isArray()) {
            return new Array(jsonNode, this);
        }
        if (jsonNode.isObject()) {
            return new Object(jsonNode, this);
        }
        throw new IllegalStateException("Current node of type " + jsonNode.getClass().getName());
    }

    public abstract JsonToken nextToken();

    public abstract JsonToken nextValue();

    protected static final class Array
    extends NodeCursor {
        Iterator<JsonNode> _contents;
        JsonNode _currentNode;

        public Array(JsonNode jsonNode, NodeCursor nodeCursor) {
            super(1, nodeCursor);
            this._contents = jsonNode.getElements();
        }

        @Override
        public boolean currentHasChildren() {
            return ((ContainerNode)this.currentNode()).size() > 0;
        }

        @Override
        public JsonNode currentNode() {
            return this._currentNode;
        }

        @Override
        public JsonToken endToken() {
            return JsonToken.END_ARRAY;
        }

        @Override
        public String getCurrentName() {
            return null;
        }

        @Override
        public JsonToken nextToken() {
            if (!this._contents.hasNext()) {
                this._currentNode = null;
                return null;
            }
            this._currentNode = (JsonNode)this._contents.next();
            return this._currentNode.asToken();
        }

        @Override
        public JsonToken nextValue() {
            return this.nextToken();
        }
    }

    protected static final class Object
    extends NodeCursor {
        Iterator<Map.Entry<String, JsonNode>> _contents;
        Map.Entry<String, JsonNode> _current;
        boolean _needEntry;

        public Object(JsonNode jsonNode, NodeCursor nodeCursor) {
            super(2, nodeCursor);
            this._contents = ((ObjectNode)((java.lang.Object)jsonNode)).getFields();
            this._needEntry = true;
        }

        @Override
        public boolean currentHasChildren() {
            return ((ContainerNode)this.currentNode()).size() > 0;
        }

        @Override
        public JsonNode currentNode() {
            if (this._current == null) {
                return null;
            }
            return (JsonNode)this._current.getValue();
        }

        @Override
        public JsonToken endToken() {
            return JsonToken.END_OBJECT;
        }

        @Override
        public String getCurrentName() {
            if (this._current == null) {
                return null;
            }
            return (String)this._current.getKey();
        }

        @Override
        public JsonToken nextToken() {
            if (this._needEntry) {
                if (!this._contents.hasNext()) {
                    this._current = null;
                    return null;
                }
                this._needEntry = false;
                this._current = (Map.Entry)this._contents.next();
                return JsonToken.FIELD_NAME;
            }
            this._needEntry = true;
            return ((JsonNode)this._current.getValue()).asToken();
        }

        @Override
        public JsonToken nextValue() {
            JsonToken jsonToken = this.nextToken();
            if (jsonToken == JsonToken.FIELD_NAME) {
                jsonToken = this.nextToken();
            }
            return jsonToken;
        }
    }

    protected static final class RootValue
    extends NodeCursor {
        protected boolean _done = false;
        JsonNode _node;

        public RootValue(JsonNode jsonNode, NodeCursor nodeCursor) {
            super(0, nodeCursor);
            this._node = jsonNode;
        }

        @Override
        public boolean currentHasChildren() {
            return false;
        }

        @Override
        public JsonNode currentNode() {
            return this._node;
        }

        @Override
        public JsonToken endToken() {
            return null;
        }

        @Override
        public String getCurrentName() {
            return null;
        }

        @Override
        public JsonToken nextToken() {
            if (!this._done) {
                this._done = true;
                return this._node.asToken();
            }
            this._node = null;
            return null;
        }

        @Override
        public JsonToken nextValue() {
            return this.nextToken();
        }
    }

}


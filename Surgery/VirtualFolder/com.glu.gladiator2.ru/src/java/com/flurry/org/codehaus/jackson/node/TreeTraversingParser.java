/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.NoSuchFieldError
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 *  java.math.BigDecimal
 *  java.math.BigInteger
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.impl.JsonParserMinimalBase;
import com.flurry.org.codehaus.jackson.node.BinaryNode;
import com.flurry.org.codehaus.jackson.node.NodeCursor;
import com.flurry.org.codehaus.jackson.node.POJONode;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TreeTraversingParser
extends JsonParserMinimalBase {
    protected boolean _closed;
    protected JsonToken _nextToken;
    protected NodeCursor _nodeCursor;
    protected ObjectCodec _objectCodec;
    protected boolean _startContainer;

    public TreeTraversingParser(JsonNode jsonNode) {
        super(jsonNode, null);
    }

    public TreeTraversingParser(JsonNode jsonNode, ObjectCodec objectCodec) {
        super(0);
        this._objectCodec = objectCodec;
        if (jsonNode.isArray()) {
            this._nextToken = JsonToken.START_ARRAY;
            this._nodeCursor = new NodeCursor.Array(jsonNode, null);
            return;
        }
        if (jsonNode.isObject()) {
            this._nextToken = JsonToken.START_OBJECT;
            this._nodeCursor = new NodeCursor.Object(jsonNode, null);
            return;
        }
        this._nodeCursor = new NodeCursor.RootValue(jsonNode, null);
    }

    @Override
    protected void _handleEOF() throws JsonParseException {
        this._throwInternal();
    }

    @Override
    public void close() throws IOException {
        if (!this._closed) {
            this._closed = true;
            this._nodeCursor = null;
            this._currToken = null;
        }
    }

    protected JsonNode currentNode() {
        if (this._closed || this._nodeCursor == null) {
            return null;
        }
        return this._nodeCursor.currentNode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JsonNode currentNumericNode() throws JsonParseException {
        JsonToken jsonToken;
        JsonNode jsonNode = this.currentNode();
        if (jsonNode != null && jsonNode.isNumber()) return jsonNode;
        if (jsonNode == null) {
            jsonToken = null;
            do {
                throw this._constructError("Current token (" + (Object)((Object)jsonToken) + ") not numeric, can not use numeric value accessors");
                break;
            } while (true);
        }
        jsonToken = jsonNode.asToken();
        throw this._constructError("Current token (" + (Object)((Object)jsonToken) + ") not numeric, can not use numeric value accessors");
    }

    @Override
    public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
        return this.currentNumericNode().getBigIntegerValue();
    }

    @Override
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException, JsonParseException {
        JsonNode jsonNode = this.currentNode();
        if (jsonNode != null) {
            Object object;
            byte[] arrby = jsonNode.getBinaryValue();
            if (arrby != null) {
                return arrby;
            }
            if (jsonNode.isPojo() && (object = ((POJONode)((Object)jsonNode)).getPojo()) instanceof byte[]) {
                return (byte[])object;
            }
        }
        return null;
    }

    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override
    public JsonLocation getCurrentLocation() {
        return JsonLocation.NA;
    }

    @Override
    public String getCurrentName() {
        if (this._nodeCursor == null) {
            return null;
        }
        return this._nodeCursor.getCurrentName();
    }

    @Override
    public BigDecimal getDecimalValue() throws IOException, JsonParseException {
        return this.currentNumericNode().getDecimalValue();
    }

    @Override
    public double getDoubleValue() throws IOException, JsonParseException {
        return this.currentNumericNode().getDoubleValue();
    }

    @Override
    public Object getEmbeddedObject() {
        JsonNode jsonNode;
        if (!this._closed && (jsonNode = this.currentNode()) != null) {
            if (jsonNode.isPojo()) {
                return ((POJONode)((Object)jsonNode)).getPojo();
            }
            if (jsonNode.isBinary()) {
                return ((BinaryNode)((Object)jsonNode)).getBinaryValue();
            }
        }
        return null;
    }

    @Override
    public float getFloatValue() throws IOException, JsonParseException {
        return (float)this.currentNumericNode().getDoubleValue();
    }

    @Override
    public int getIntValue() throws IOException, JsonParseException {
        return this.currentNumericNode().getIntValue();
    }

    @Override
    public long getLongValue() throws IOException, JsonParseException {
        return this.currentNumericNode().getLongValue();
    }

    @Override
    public JsonParser.NumberType getNumberType() throws IOException, JsonParseException {
        JsonNode jsonNode = this.currentNumericNode();
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.getNumberType();
    }

    @Override
    public Number getNumberValue() throws IOException, JsonParseException {
        return this.currentNumericNode().getNumberValue();
    }

    @Override
    public JsonStreamContext getParsingContext() {
        return this._nodeCursor;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public String getText() {
        block9 : {
            block8 : {
                if (this._closed) break block8;
                switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[this._currToken.ordinal()]) {
                    default: {
                        break;
                    }
                    case 1: {
                        return this._nodeCursor.getCurrentName();
                    }
                    case 2: {
                        return this.currentNode().getTextValue();
                    }
                    case 3: 
                    case 4: {
                        return String.valueOf((Object)this.currentNode().getNumberValue());
                    }
                    case 5: {
                        JsonNode jsonNode = this.currentNode();
                        if (jsonNode == null || !jsonNode.isBinary()) break;
                        return jsonNode.asText();
                    }
                }
                if (this._currToken != null) break block9;
            }
            return null;
        }
        return this._currToken.asString();
    }

    @Override
    public char[] getTextCharacters() throws IOException, JsonParseException {
        return this.getText().toCharArray();
    }

    @Override
    public int getTextLength() throws IOException, JsonParseException {
        return this.getText().length();
    }

    @Override
    public int getTextOffset() throws IOException, JsonParseException {
        return 0;
    }

    @Override
    public JsonLocation getTokenLocation() {
        return JsonLocation.NA;
    }

    @Override
    public boolean hasTextCharacters() {
        return false;
    }

    @Override
    public boolean isClosed() {
        return this._closed;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonToken nextToken() throws IOException, JsonParseException {
        if (this._nextToken != null) {
            this._currToken = this._nextToken;
            this._nextToken = null;
            return this._currToken;
        }
        if (this._startContainer) {
            this._startContainer = false;
            if (!this._nodeCursor.currentHasChildren()) {
                JsonToken jsonToken = this._currToken == JsonToken.START_OBJECT ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
                this._currToken = jsonToken;
                return this._currToken;
            }
            this._nodeCursor = this._nodeCursor.iterateChildren();
            this._currToken = this._nodeCursor.nextToken();
            if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
                this._startContainer = true;
            }
            return this._currToken;
        }
        if (this._nodeCursor == null) {
            this._closed = true;
            return null;
        }
        this._currToken = this._nodeCursor.nextToken();
        if (this._currToken == null) {
            this._currToken = this._nodeCursor.endToken();
            this._nodeCursor = this._nodeCursor.getParent();
            return this._currToken;
        }
        if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
            this._startContainer = true;
        }
        return this._currToken;
    }

    @Override
    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonParser skipChildren() throws IOException, JsonParseException {
        if (this._currToken == JsonToken.START_OBJECT) {
            this._startContainer = false;
            this._currToken = JsonToken.END_OBJECT;
            return this;
        } else {
            if (this._currToken != JsonToken.START_ARRAY) return this;
            {
                this._startContainer = false;
                this._currToken = JsonToken.END_ARRAY;
                return this;
            }
        }
    }

}


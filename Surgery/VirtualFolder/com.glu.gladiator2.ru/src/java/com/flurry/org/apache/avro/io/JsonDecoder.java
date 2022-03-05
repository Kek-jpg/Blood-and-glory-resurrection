/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.EOFException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.NoSuchFieldError
 *  java.lang.NullPointerException
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.UnsupportedOperationException
 *  java.math.BigDecimal
 *  java.math.BigInteger
 *  java.nio.ByteBuffer
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Set
 *  java.util.Stack
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.ParsingDecoder;
import com.flurry.org.apache.avro.io.parsing.JsonGrammarGenerator;
import com.flurry.org.apache.avro.io.parsing.Parser;
import com.flurry.org.apache.avro.io.parsing.SkipParser;
import com.flurry.org.apache.avro.io.parsing.Symbol;
import com.flurry.org.apache.avro.util.Utf8;
import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.JsonFactory;
import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class JsonDecoder
extends ParsingDecoder
implements Parser.ActionHandler {
    static final String CHARSET = "ISO-8859-1";
    private static JsonFactory jsonFactory = new JsonFactory();
    ReorderBuffer currentReorderBuffer;
    private JsonParser in;
    Stack<ReorderBuffer> reorderBuffers;

    JsonDecoder(Schema schema, InputStream inputStream) throws IOException {
        super(JsonDecoder.getSymbol(schema), inputStream);
    }

    JsonDecoder(Schema schema, String string) throws IOException {
        super(JsonDecoder.getSymbol(schema), string);
    }

    private JsonDecoder(Symbol symbol, InputStream inputStream) throws IOException {
        super(symbol);
        this.reorderBuffers = new Stack();
        this.configure(inputStream);
    }

    private JsonDecoder(Symbol symbol, String string) throws IOException {
        super(symbol);
        this.reorderBuffers = new Stack();
        this.configure(string);
    }

    private void advance(Symbol symbol) throws IOException {
        this.parser.processTrailingImplicitActions();
        if (this.in.getCurrentToken() == null && this.parser.depth() == 1) {
            throw new EOFException();
        }
        this.parser.advance(symbol);
    }

    private void checkFixed(int n2) throws IOException {
        JsonDecoder.super.advance(Symbol.FIXED);
        Symbol.IntCheckAction intCheckAction = (Symbol.IntCheckAction)this.parser.popSymbol();
        if (n2 != intCheckAction.size) {
            throw new AvroTypeException("Incorrect length for fixed binary: expected " + intCheckAction.size + " but received " + n2 + " bytes.");
        }
    }

    private long doArrayNext() throws IOException {
        if (this.in.getCurrentToken() == JsonToken.END_ARRAY) {
            this.parser.advance(Symbol.ARRAY_END);
            this.in.nextToken();
            return 0L;
        }
        return 1L;
    }

    private long doMapNext() throws IOException {
        if (this.in.getCurrentToken() == JsonToken.END_OBJECT) {
            this.in.nextToken();
            this.advance(Symbol.MAP_END);
            return 0L;
        }
        return 1L;
    }

    private void doSkipFixed(int n2) throws IOException {
        if (this.in.getCurrentToken() == JsonToken.VALUE_STRING) {
            byte[] arrby = JsonDecoder.super.readByteArray();
            this.in.nextToken();
            if (arrby.length != n2) {
                throw new AvroTypeException("Expected fixed length " + n2 + ", but got" + arrby.length);
            }
        } else {
            throw JsonDecoder.super.error("fixed");
        }
    }

    private AvroTypeException error(String string) {
        return new AvroTypeException("Expected " + string + ". Got " + (Object)((Object)this.in.getCurrentToken()));
    }

    private static Symbol getSymbol(Schema schema) {
        if (schema == null) {
            throw new NullPointerException("Schema cannot be null!");
        }
        return new JsonGrammarGenerator().generate(schema);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static List<JsonElement> getVaueAsTree(JsonParser jsonParser) throws IOException {
        int n2 = 0;
        ArrayList arrayList = new ArrayList();
        do {
            JsonToken jsonToken = jsonParser.getCurrentToken();
            switch (2.$SwitchMap$org$codehaus$jackson$JsonToken[jsonToken.ordinal()]) {
                case 1: 
                case 2: {
                    ++n2;
                    arrayList.add((Object)new JsonElement(jsonToken));
                    break;
                }
                case 3: 
                case 4: {
                    --n2;
                    arrayList.add((Object)new JsonElement(jsonToken));
                    break;
                }
                case 5: 
                case 6: 
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 11: {
                    arrayList.add((Object)new JsonElement(jsonToken, jsonParser.getText()));
                    break;
                }
            }
            jsonParser.nextToken();
        } while (n2 != 0);
        arrayList.add((Object)new JsonElement(null));
        return arrayList;
    }

    private JsonParser makeParser(final List<JsonElement> list) throws IOException {
        return new JsonParser(){
            int pos = 0;

            @Override
            public void close() throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public BigInteger getBigIntegerValue() throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public ObjectCodec getCodec() {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonLocation getCurrentLocation() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getCurrentName() throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonToken getCurrentToken() {
                return ((JsonElement)list.get((int)this.pos)).token;
            }

            @Override
            public BigDecimal getDecimalValue() throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public double getDoubleValue() throws IOException {
                return Double.parseDouble((String)this.getText());
            }

            @Override
            public float getFloatValue() throws IOException {
                return Float.parseFloat((String)this.getText());
            }

            @Override
            public int getIntValue() throws IOException {
                return Integer.parseInt((String)this.getText());
            }

            @Override
            public long getLongValue() throws IOException {
                return Long.parseLong((String)this.getText());
            }

            @Override
            public JsonParser.NumberType getNumberType() throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public Number getNumberValue() throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonStreamContext getParsingContext() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getText() throws IOException {
                return ((JsonElement)list.get((int)this.pos)).value;
            }

            @Override
            public char[] getTextCharacters() throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public int getTextLength() throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public int getTextOffset() throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonLocation getTokenLocation() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean isClosed() {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonToken nextToken() throws IOException {
                this.pos = 1 + this.pos;
                return ((JsonElement)list.get((int)this.pos)).token;
            }

            @Override
            public void setCodec(ObjectCodec objectCodec) {
                throw new UnsupportedOperationException();
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public JsonParser skipChildren() throws IOException {
                int n2 = 0;
                do {
                    int[] arrn = 2.$SwitchMap$org$codehaus$jackson$JsonToken;
                    List list2 = list;
                    int n3 = this.pos;
                    this.pos = n3 + 1;
                    switch (arrn[((JsonElement)list2.get((int)n3)).token.ordinal()]) {
                        case 1: 
                        case 2: {
                            ++n2;
                        }
                        default: {
                            break;
                        }
                        case 3: 
                        case 4: {
                            --n2;
                        }
                    }
                } while (n2 > 0);
                return this;
            }
        };
    }

    private byte[] readByteArray() throws IOException {
        return this.in.getText().getBytes(CHARSET);
    }

    @Override
    public long arrayNext() throws IOException {
        this.advance(Symbol.ITEM_END);
        return this.doArrayNext();
    }

    public JsonDecoder configure(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("InputStream to read from cannot be null!");
        }
        this.parser.reset();
        this.in = jsonFactory.createJsonParser(inputStream);
        this.in.nextToken();
        return this;
    }

    public JsonDecoder configure(String string) throws IOException {
        if (string == null) {
            throw new NullPointerException("String to read from cannot be null!");
        }
        this.parser.reset();
        this.in = new JsonFactory().createJsonParser(string);
        this.in.nextToken();
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Symbol doAction(Symbol symbol, Symbol symbol2) throws IOException {
        String string;
        if (symbol2 instanceof Symbol.FieldAdjustAction) {
            List list;
            Symbol.FieldAdjustAction fieldAdjustAction = (Symbol.FieldAdjustAction)symbol2;
            String string2 = fieldAdjustAction.fname;
            if (this.currentReorderBuffer != null && (list = (List)this.currentReorderBuffer.savedFields.get((Object)string2)) != null) {
                this.currentReorderBuffer.savedFields.remove((Object)string2);
                this.currentReorderBuffer.origParser = this.in;
                this.in = JsonDecoder.super.makeParser((List<JsonElement>)list);
                return null;
            }
            if (this.in.getCurrentToken() != JsonToken.FIELD_NAME) return null;
            do {
                String string3 = this.in.getText();
                this.in.nextToken();
                if (string2.equals((Object)string3)) return null;
                if (this.currentReorderBuffer == null) {
                    this.currentReorderBuffer = new ReorderBuffer(null);
                }
                this.currentReorderBuffer.savedFields.put((Object)string3, JsonDecoder.getVaueAsTree(this.in));
            } while (this.in.getCurrentToken() == JsonToken.FIELD_NAME);
            throw new AvroTypeException("Expected field name not found: " + fieldAdjustAction.fname);
        }
        if (symbol2 == Symbol.FIELD_END) {
            if (this.currentReorderBuffer == null) return null;
            if (this.currentReorderBuffer.origParser == null) return null;
            this.in = this.currentReorderBuffer.origParser;
            this.currentReorderBuffer.origParser = null;
            return null;
        }
        if (symbol2 == Symbol.RECORD_START) {
            if (this.in.getCurrentToken() != JsonToken.START_OBJECT) throw JsonDecoder.super.error("record-start");
            this.in.nextToken();
            this.reorderBuffers.push((Object)this.currentReorderBuffer);
            this.currentReorderBuffer = null;
            return null;
        }
        if (symbol2 != Symbol.RECORD_END) {
            if (symbol2 != Symbol.UNION_END) throw new AvroTypeException("Unknown action symbol " + symbol2);
        }
        if (this.in.getCurrentToken() == JsonToken.END_OBJECT) {
            this.in.nextToken();
            if (symbol2 != Symbol.RECORD_END) return null;
            if (this.currentReorderBuffer != null && !this.currentReorderBuffer.savedFields.isEmpty()) {
                throw JsonDecoder.super.error("Unknown fields: " + (Object)this.currentReorderBuffer.savedFields.keySet());
            }
            this.currentReorderBuffer = (ReorderBuffer)this.reorderBuffers.pop();
            return null;
        }
        if (symbol2 == Symbol.RECORD_END) {
            string = "record-end";
            throw JsonDecoder.super.error(string);
        }
        string = "union-end";
        throw JsonDecoder.super.error(string);
    }

    @Override
    public long mapNext() throws IOException {
        this.advance(Symbol.ITEM_END);
        return this.doMapNext();
    }

    @Override
    public long readArrayStart() throws IOException {
        this.advance(Symbol.ARRAY_START);
        if (this.in.getCurrentToken() == JsonToken.START_ARRAY) {
            this.in.nextToken();
            return this.doArrayNext();
        }
        throw this.error("array-start");
    }

    @Override
    public boolean readBoolean() throws IOException {
        this.advance(Symbol.BOOLEAN);
        JsonToken jsonToken = this.in.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_TRUE || jsonToken == JsonToken.VALUE_FALSE) {
            this.in.nextToken();
            return jsonToken == JsonToken.VALUE_TRUE;
        }
        throw this.error("boolean");
    }

    @Override
    public ByteBuffer readBytes(ByteBuffer byteBuffer) throws IOException {
        JsonDecoder.super.advance(Symbol.BYTES);
        if (this.in.getCurrentToken() == JsonToken.VALUE_STRING) {
            byte[] arrby = JsonDecoder.super.readByteArray();
            this.in.nextToken();
            return ByteBuffer.wrap((byte[])arrby);
        }
        throw JsonDecoder.super.error("bytes");
    }

    @Override
    public double readDouble() throws IOException {
        this.advance(Symbol.DOUBLE);
        if (this.in.getCurrentToken() == JsonToken.VALUE_NUMBER_FLOAT) {
            double d2 = this.in.getDoubleValue();
            this.in.nextToken();
            return d2;
        }
        throw this.error("double");
    }

    @Override
    public int readEnum() throws IOException {
        this.advance(Symbol.ENUM);
        Symbol.EnumLabelsAction enumLabelsAction = (Symbol.EnumLabelsAction)this.parser.popSymbol();
        if (this.in.getCurrentToken() == JsonToken.VALUE_STRING) {
            this.in.getText();
            int n2 = enumLabelsAction.findLabel(this.in.getText());
            if (n2 >= 0) {
                this.in.nextToken();
                return n2;
            }
            throw new AvroTypeException("Unknown symbol in enum " + this.in.getText());
        }
        throw this.error("fixed");
    }

    @Override
    public void readFixed(byte[] arrby, int n2, int n3) throws IOException {
        JsonDecoder.super.checkFixed(n3);
        if (this.in.getCurrentToken() == JsonToken.VALUE_STRING) {
            byte[] arrby2 = JsonDecoder.super.readByteArray();
            this.in.nextToken();
            if (arrby2.length != n3) {
                throw new AvroTypeException("Expected fixed length " + n3 + ", but got" + arrby2.length);
            }
            System.arraycopy((Object)arrby2, (int)0, (Object)arrby, (int)n2, (int)n3);
            return;
        }
        throw JsonDecoder.super.error("fixed");
    }

    @Override
    public float readFloat() throws IOException {
        this.advance(Symbol.FLOAT);
        if (this.in.getCurrentToken() == JsonToken.VALUE_NUMBER_FLOAT) {
            float f2 = this.in.getFloatValue();
            this.in.nextToken();
            return f2;
        }
        throw this.error("float");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int readIndex() throws IOException {
        int n2;
        String string;
        this.advance(Symbol.UNION);
        Symbol.Alternative alternative = (Symbol.Alternative)this.parser.popSymbol();
        if (this.in.getCurrentToken() == JsonToken.VALUE_NULL) {
            string = "null";
        } else {
            if (this.in.getCurrentToken() != JsonToken.START_OBJECT || this.in.nextToken() != JsonToken.FIELD_NAME) {
                throw this.error("start-union");
            }
            string = this.in.getText();
            this.in.nextToken();
            this.parser.pushSymbol(Symbol.UNION_END);
        }
        if ((n2 = alternative.findLabel(string)) < 0) {
            throw new AvroTypeException("Unknown union branch " + string);
        }
        this.parser.pushSymbol(alternative.getSymbol(n2));
        return n2;
    }

    @Override
    public int readInt() throws IOException {
        this.advance(Symbol.INT);
        if (this.in.getCurrentToken() == JsonToken.VALUE_NUMBER_INT) {
            int n2 = this.in.getIntValue();
            this.in.nextToken();
            return n2;
        }
        throw this.error("int");
    }

    @Override
    public long readLong() throws IOException {
        this.advance(Symbol.LONG);
        if (this.in.getCurrentToken() == JsonToken.VALUE_NUMBER_INT) {
            long l2 = this.in.getLongValue();
            this.in.nextToken();
            return l2;
        }
        throw this.error("long");
    }

    @Override
    public long readMapStart() throws IOException {
        this.advance(Symbol.MAP_START);
        if (this.in.getCurrentToken() == JsonToken.START_OBJECT) {
            this.in.nextToken();
            return this.doMapNext();
        }
        throw this.error("map-start");
    }

    @Override
    public void readNull() throws IOException {
        this.advance(Symbol.NULL);
        if (this.in.getCurrentToken() == JsonToken.VALUE_NULL) {
            this.in.nextToken();
            return;
        }
        throw this.error("null");
    }

    @Override
    public Utf8 readString(Utf8 utf8) throws IOException {
        return new Utf8(this.readString());
    }

    @Override
    public String readString() throws IOException {
        this.advance(Symbol.STRING);
        if (this.parser.topSymbol() == Symbol.MAP_KEY_MARKER) {
            this.parser.advance(Symbol.MAP_KEY_MARKER);
            if (this.in.getCurrentToken() != JsonToken.FIELD_NAME) {
                throw this.error("map-key");
            }
        } else if (this.in.getCurrentToken() != JsonToken.VALUE_STRING) {
            throw this.error("string");
        }
        String string = this.in.getText();
        this.in.nextToken();
        return string;
    }

    @Override
    public long skipArray() throws IOException {
        this.advance(Symbol.ARRAY_START);
        if (this.in.getCurrentToken() == JsonToken.START_ARRAY) {
            this.in.skipChildren();
            this.in.nextToken();
            this.advance(Symbol.ARRAY_END);
            return 0L;
        }
        throw this.error("array-start");
    }

    @Override
    public void skipBytes() throws IOException {
        this.advance(Symbol.BYTES);
        if (this.in.getCurrentToken() == JsonToken.VALUE_STRING) {
            this.in.nextToken();
            return;
        }
        throw this.error("bytes");
    }

    @Override
    protected void skipFixed() throws IOException {
        this.advance(Symbol.FIXED);
        this.doSkipFixed(((Symbol.IntCheckAction)this.parser.popSymbol()).size);
    }

    @Override
    public void skipFixed(int n2) throws IOException {
        JsonDecoder.super.checkFixed(n2);
        JsonDecoder.super.doSkipFixed(n2);
    }

    @Override
    public long skipMap() throws IOException {
        this.advance(Symbol.MAP_START);
        if (this.in.getCurrentToken() == JsonToken.START_OBJECT) {
            this.in.skipChildren();
            this.in.nextToken();
            this.advance(Symbol.MAP_END);
            return 0L;
        }
        throw this.error("map-start");
    }

    @Override
    public void skipString() throws IOException {
        this.advance(Symbol.STRING);
        if (this.parser.topSymbol() == Symbol.MAP_KEY_MARKER) {
            this.parser.advance(Symbol.MAP_KEY_MARKER);
            if (this.in.getCurrentToken() != JsonToken.FIELD_NAME) {
                throw this.error("map-key");
            }
        } else if (this.in.getCurrentToken() != JsonToken.VALUE_STRING) {
            throw this.error("string");
        }
        this.in.nextToken();
    }

    private static class JsonElement {
        public final JsonToken token;
        public final String value;

        public JsonElement(JsonToken jsonToken) {
            super(jsonToken, null);
        }

        public JsonElement(JsonToken jsonToken, String string) {
            this.token = jsonToken;
            this.value = string;
        }
    }

    private static class ReorderBuffer {
        public JsonParser origParser;
        public Map<String, List<JsonElement>> savedFields;

        private ReorderBuffer() {
            this.savedFields = new HashMap();
            this.origParser = null;
        }

        /* synthetic */ ReorderBuffer(1 var1) {
        }
    }

}


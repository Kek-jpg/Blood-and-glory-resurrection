/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.impl.JsonReadContext
 *  com.flurry.org.codehaus.jackson.impl.JsonWriteContext
 *  com.flurry.org.codehaus.jackson.io.SerializedString
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.NoSuchFieldError
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Throwable
 *  java.lang.UnsupportedOperationException
 *  java.math.BigDecimal
 *  java.math.BigInteger
 */
package com.flurry.org.codehaus.jackson.util;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.SerializableString;
import com.flurry.org.codehaus.jackson.impl.JsonParserMinimalBase;
import com.flurry.org.codehaus.jackson.impl.JsonReadContext;
import com.flurry.org.codehaus.jackson.impl.JsonWriteContext;
import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.util.ByteArrayBuilder;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TokenBuffer
extends JsonGenerator {
    protected static final int DEFAULT_PARSER_FEATURES = JsonParser.Feature.collectDefaults();
    protected int _appendOffset;
    protected boolean _closed;
    protected Segment _first;
    protected int _generatorFeatures;
    protected Segment _last;
    protected ObjectCodec _objectCodec;
    protected JsonWriteContext _writeContext;

    public TokenBuffer(ObjectCodec objectCodec) {
        Segment segment;
        this._objectCodec = objectCodec;
        this._generatorFeatures = DEFAULT_PARSER_FEATURES;
        this._writeContext = JsonWriteContext.createRootContext();
        this._last = segment = new Segment();
        this._first = segment;
        this._appendOffset = 0;
    }

    protected final void _append(JsonToken jsonToken) {
        Segment segment = this._last.append(this._appendOffset, jsonToken);
        if (segment == null) {
            this._appendOffset = 1 + this._appendOffset;
            return;
        }
        this._last = segment;
        this._appendOffset = 1;
    }

    protected final void _append(JsonToken jsonToken, Object object) {
        Segment segment = this._last.append(this._appendOffset, jsonToken, object);
        if (segment == null) {
            this._appendOffset = 1 + this._appendOffset;
            return;
        }
        this._last = segment;
        this._appendOffset = 1;
    }

    protected void _reportUnsupportedOperation() {
        throw new UnsupportedOperationException("Called operation not supported for TokenBuffer");
    }

    public JsonParser asParser() {
        return this.asParser(this._objectCodec);
    }

    public JsonParser asParser(JsonParser jsonParser) {
        Parser parser = new Parser(this._first, jsonParser.getCodec());
        parser.setLocation(jsonParser.getTokenLocation());
        return parser;
    }

    public JsonParser asParser(ObjectCodec objectCodec) {
        return new Parser(this._first, objectCodec);
    }

    @Override
    public void close() throws IOException {
        this._closed = true;
    }

    @Override
    public void copyCurrentEvent(JsonParser jsonParser) throws IOException, JsonProcessingException {
        switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
            default: {
                throw new RuntimeException("Internal error: should never end up through this code path");
            }
            case 1: {
                this.writeStartObject();
                return;
            }
            case 2: {
                this.writeEndObject();
                return;
            }
            case 3: {
                this.writeStartArray();
                return;
            }
            case 4: {
                this.writeEndArray();
                return;
            }
            case 5: {
                this.writeFieldName(jsonParser.getCurrentName());
                return;
            }
            case 6: {
                if (jsonParser.hasTextCharacters()) {
                    this.writeString(jsonParser.getTextCharacters(), jsonParser.getTextOffset(), jsonParser.getTextLength());
                    return;
                }
                this.writeString(jsonParser.getText());
                return;
            }
            case 7: {
                switch (jsonParser.getNumberType()) {
                    default: {
                        this.writeNumber(jsonParser.getLongValue());
                        return;
                    }
                    case INT: {
                        this.writeNumber(jsonParser.getIntValue());
                        return;
                    }
                    case BIG_INTEGER: 
                }
                this.writeNumber(jsonParser.getBigIntegerValue());
                return;
            }
            case 8: {
                switch (jsonParser.getNumberType()) {
                    default: {
                        this.writeNumber(jsonParser.getDoubleValue());
                        return;
                    }
                    case BIG_DECIMAL: {
                        this.writeNumber(jsonParser.getDecimalValue());
                        return;
                    }
                    case FLOAT: 
                }
                this.writeNumber(jsonParser.getFloatValue());
                return;
            }
            case 9: {
                this.writeBoolean(true);
                return;
            }
            case 10: {
                this.writeBoolean(false);
                return;
            }
            case 11: {
                this.writeNull();
                return;
            }
            case 12: 
        }
        this.writeObject(jsonParser.getEmbeddedObject());
    }

    @Override
    public void copyCurrentStructure(JsonParser jsonParser) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.FIELD_NAME) {
            this.writeFieldName(jsonParser.getCurrentName());
            jsonToken = jsonParser.nextToken();
        }
        switch (jsonToken) {
            default: {
                this.copyCurrentEvent(jsonParser);
                return;
            }
            case START_ARRAY: {
                this.writeStartArray();
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    this.copyCurrentStructure(jsonParser);
                }
                this.writeEndArray();
                return;
            }
            case START_OBJECT: 
        }
        this.writeStartObject();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            this.copyCurrentStructure(jsonParser);
        }
        this.writeEndObject();
    }

    @Override
    public JsonGenerator disable(JsonGenerator.Feature feature) {
        this._generatorFeatures &= -1 ^ feature.getMask();
        return this;
    }

    @Override
    public JsonGenerator enable(JsonGenerator.Feature feature) {
        this._generatorFeatures |= feature.getMask();
        return this;
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    public final JsonWriteContext getOutputContext() {
        return this._writeContext;
    }

    @Override
    public boolean isClosed() {
        return this._closed;
    }

    @Override
    public boolean isEnabled(JsonGenerator.Feature feature) {
        return (this._generatorFeatures & feature.getMask()) != 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void serialize(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        Segment segment = this._first;
        int n = -1;
        block14 : do {
            JsonToken jsonToken;
            block29 : {
                block28 : {
                    block27 : {
                        if (++n < 16) break block27;
                        segment = segment.next();
                        n = 0;
                        if (segment == null) break block28;
                    }
                    if ((jsonToken = segment.type(n)) != null) break block29;
                }
                return;
            }
            switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonToken.ordinal()]) {
                default: {
                    throw new RuntimeException("Internal error: should never end up through this code path");
                }
                case 1: {
                    jsonGenerator.writeStartObject();
                    continue block14;
                }
                case 2: {
                    jsonGenerator.writeEndObject();
                    continue block14;
                }
                case 3: {
                    jsonGenerator.writeStartArray();
                    continue block14;
                }
                case 4: {
                    jsonGenerator.writeEndArray();
                    continue block14;
                }
                case 5: {
                    Object object = segment.get(n);
                    if (object instanceof SerializableString) {
                        jsonGenerator.writeFieldName((SerializableString)object);
                        continue block14;
                    }
                    jsonGenerator.writeFieldName((String)object);
                    continue block14;
                }
                case 6: {
                    Object object = segment.get(n);
                    if (object instanceof SerializableString) {
                        jsonGenerator.writeString((SerializableString)object);
                        continue block14;
                    }
                    jsonGenerator.writeString((String)object);
                    continue block14;
                }
                case 7: {
                    Number number = (Number)segment.get(n);
                    if (number instanceof BigInteger) {
                        jsonGenerator.writeNumber((BigInteger)number);
                        continue block14;
                    }
                    if (number instanceof Long) {
                        jsonGenerator.writeNumber(number.longValue());
                        continue block14;
                    }
                    jsonGenerator.writeNumber(number.intValue());
                    continue block14;
                }
                case 8: {
                    Object object = segment.get(n);
                    if (object instanceof BigDecimal) {
                        jsonGenerator.writeNumber((BigDecimal)object);
                        continue block14;
                    }
                    if (object instanceof Float) {
                        jsonGenerator.writeNumber(((Float)object).floatValue());
                        continue block14;
                    }
                    if (object instanceof Double) {
                        jsonGenerator.writeNumber((Double)object);
                        continue block14;
                    }
                    if (object == null) {
                        jsonGenerator.writeNull();
                        continue block14;
                    }
                    if (!(object instanceof String)) {
                        throw new JsonGenerationException("Unrecognized value type for VALUE_NUMBER_FLOAT: " + object.getClass().getName() + ", can not serialize");
                    }
                    jsonGenerator.writeNumber((String)object);
                    continue block14;
                }
                case 9: {
                    jsonGenerator.writeBoolean(true);
                    continue block14;
                }
                case 10: {
                    jsonGenerator.writeBoolean(false);
                    continue block14;
                }
                case 11: {
                    jsonGenerator.writeNull();
                    continue block14;
                }
                case 12: 
            }
            jsonGenerator.writeObject(segment.get(n));
        } while (true);
    }

    @Override
    public JsonGenerator setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[TokenBuffer: ");
        JsonParser jsonParser = this.asParser();
        int n = 0;
        do {
            JsonToken jsonToken;
            block5 : {
                block6 : {
                    try {
                        jsonToken = jsonParser.nextToken();
                        if (jsonToken != null) break block5;
                        if (n < 100) break block6;
                    }
                    catch (IOException iOException) {
                        throw new IllegalStateException((Throwable)iOException);
                    }
                    stringBuilder.append(" ... (truncated ").append(n - 100).append(" entries)");
                }
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            if (n < 100) {
                if (n > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(jsonToken.toString());
            }
            ++n;
        } while (true);
    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        return this;
    }

    @Override
    public void writeBinary(Base64Variant base64Variant, byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        byte[] arrby2 = new byte[n2];
        System.arraycopy((Object)arrby, (int)n, (Object)arrby2, (int)0, (int)n2);
        this.writeObject(arrby2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeBoolean(boolean bl) throws IOException, JsonGenerationException {
        JsonToken jsonToken = bl ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE;
        this._append(jsonToken);
    }

    @Override
    public final void writeEndArray() throws IOException, JsonGenerationException {
        this._append(JsonToken.END_ARRAY);
        JsonWriteContext jsonWriteContext = this._writeContext.getParent();
        if (jsonWriteContext != null) {
            this._writeContext = jsonWriteContext;
        }
    }

    @Override
    public final void writeEndObject() throws IOException, JsonGenerationException {
        this._append(JsonToken.END_OBJECT);
        JsonWriteContext jsonWriteContext = this._writeContext.getParent();
        if (jsonWriteContext != null) {
            this._writeContext = jsonWriteContext;
        }
    }

    @Override
    public void writeFieldName(SerializableString serializableString) throws IOException, JsonGenerationException {
        this._append(JsonToken.FIELD_NAME, serializableString);
        this._writeContext.writeFieldName(serializableString.getValue());
    }

    @Override
    public void writeFieldName(SerializedString serializedString) throws IOException, JsonGenerationException {
        this._append(JsonToken.FIELD_NAME, (Object)serializedString);
        this._writeContext.writeFieldName(serializedString.getValue());
    }

    @Override
    public final void writeFieldName(String string2) throws IOException, JsonGenerationException {
        this._append(JsonToken.FIELD_NAME, string2);
        this._writeContext.writeFieldName(string2);
    }

    @Override
    public void writeNull() throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NULL);
    }

    @Override
    public void writeNumber(double d2) throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NUMBER_FLOAT, d2);
    }

    @Override
    public void writeNumber(float f2) throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NUMBER_FLOAT, (Object)Float.valueOf((float)f2));
    }

    @Override
    public void writeNumber(int n) throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NUMBER_INT, n);
    }

    @Override
    public void writeNumber(long l) throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NUMBER_INT, l);
    }

    @Override
    public void writeNumber(String string2) throws IOException, JsonGenerationException {
        this._append(JsonToken.VALUE_NUMBER_FLOAT, string2);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException, JsonGenerationException {
        if (bigDecimal == null) {
            this.writeNull();
            return;
        }
        this._append(JsonToken.VALUE_NUMBER_FLOAT, (Object)bigDecimal);
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException, JsonGenerationException {
        if (bigInteger == null) {
            this.writeNull();
            return;
        }
        this._append(JsonToken.VALUE_NUMBER_INT, (Object)bigInteger);
    }

    @Override
    public void writeObject(Object object) throws IOException, JsonProcessingException {
        this._append(JsonToken.VALUE_EMBEDDED_OBJECT, object);
    }

    @Override
    public void writeRaw(char c2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRaw(String string2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRaw(String string2, int n, int n2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRaw(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRawUTF8String(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRawValue(String string2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRawValue(String string2, int n, int n2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeRawValue(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    @Override
    public final void writeStartArray() throws IOException, JsonGenerationException {
        this._append(JsonToken.START_ARRAY);
        this._writeContext = this._writeContext.createChildArrayContext();
    }

    @Override
    public final void writeStartObject() throws IOException, JsonGenerationException {
        this._append(JsonToken.START_OBJECT);
        this._writeContext = this._writeContext.createChildObjectContext();
    }

    @Override
    public void writeString(SerializableString serializableString) throws IOException, JsonGenerationException {
        if (serializableString == null) {
            this.writeNull();
            return;
        }
        this._append(JsonToken.VALUE_STRING, serializableString);
    }

    @Override
    public void writeString(String string2) throws IOException, JsonGenerationException {
        if (string2 == null) {
            this.writeNull();
            return;
        }
        this._append(JsonToken.VALUE_STRING, string2);
    }

    @Override
    public void writeString(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        this.writeString(new String(arrc, n, n2));
    }

    @Override
    public void writeTree(JsonNode jsonNode) throws IOException, JsonProcessingException {
        this._append(JsonToken.VALUE_EMBEDDED_OBJECT, jsonNode);
    }

    @Override
    public void writeUTF8String(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        this._reportUnsupportedOperation();
    }

    protected static final class Parser
    extends JsonParserMinimalBase {
        protected transient ByteArrayBuilder _byteBuilder;
        protected boolean _closed;
        protected ObjectCodec _codec;
        protected JsonLocation _location = null;
        protected JsonReadContext _parsingContext;
        protected Segment _segment;
        protected int _segmentPtr;

        public Parser(Segment segment, ObjectCodec objectCodec) {
            super(0);
            this._segment = segment;
            this._segmentPtr = -1;
            this._codec = objectCodec;
            this._parsingContext = JsonReadContext.createRootContext((int)-1, (int)-1);
        }

        protected final void _checkIsNumber() throws JsonParseException {
            if (this._currToken == null || !this._currToken.isNumeric()) {
                throw this._constructError("Current token (" + (Object)((Object)this._currToken) + ") not numeric, can not use numeric value accessors");
            }
        }

        protected final Object _currentObject() {
            return this._segment.get(this._segmentPtr);
        }

        @Override
        protected void _handleEOF() throws JsonParseException {
            this._throwInternal();
        }

        @Override
        public void close() throws IOException {
            if (!this._closed) {
                this._closed = true;
            }
        }

        @Override
        public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
            Number number = this.getNumberValue();
            if (number instanceof BigInteger) {
                return (BigInteger)number;
            }
            switch (this.getNumberType()) {
                default: {
                    return BigInteger.valueOf((long)number.longValue());
                }
                case BIG_DECIMAL: 
            }
            return ((BigDecimal)number).toBigInteger();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException, JsonParseException {
            Object object;
            if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT && (object = this._currentObject()) instanceof byte[]) {
                return (byte[])object;
            }
            if (this._currToken != JsonToken.VALUE_STRING) {
                throw this._constructError("Current token (" + (Object)((Object)this._currToken) + ") not VALUE_STRING (or VALUE_EMBEDDED_OBJECT with byte[]), can not access as binary");
            }
            String string2 = this.getText();
            if (string2 == null) {
                return null;
            }
            ByteArrayBuilder byteArrayBuilder = this._byteBuilder;
            if (byteArrayBuilder == null) {
                this._byteBuilder = byteArrayBuilder = new ByteArrayBuilder(100);
            } else {
                this._byteBuilder.reset();
            }
            this._decodeBase64(string2, byteArrayBuilder, base64Variant);
            return byteArrayBuilder.toByteArray();
        }

        @Override
        public ObjectCodec getCodec() {
            return this._codec;
        }

        @Override
        public JsonLocation getCurrentLocation() {
            if (this._location == null) {
                return JsonLocation.NA;
            }
            return this._location;
        }

        @Override
        public String getCurrentName() {
            return this._parsingContext.getCurrentName();
        }

        @Override
        public BigDecimal getDecimalValue() throws IOException, JsonParseException {
            Number number = this.getNumberValue();
            if (number instanceof BigDecimal) {
                return (BigDecimal)number;
            }
            switch (this.getNumberType()) {
                default: {
                    return BigDecimal.valueOf((double)number.doubleValue());
                }
                case INT: 
                case LONG: {
                    return BigDecimal.valueOf((long)number.longValue());
                }
                case BIG_INTEGER: 
            }
            return new BigDecimal((BigInteger)number);
        }

        @Override
        public double getDoubleValue() throws IOException, JsonParseException {
            return this.getNumberValue().doubleValue();
        }

        @Override
        public Object getEmbeddedObject() {
            if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
                return this._currentObject();
            }
            return null;
        }

        @Override
        public float getFloatValue() throws IOException, JsonParseException {
            return this.getNumberValue().floatValue();
        }

        @Override
        public int getIntValue() throws IOException, JsonParseException {
            if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
                return ((Number)this._currentObject()).intValue();
            }
            return this.getNumberValue().intValue();
        }

        @Override
        public long getLongValue() throws IOException, JsonParseException {
            return this.getNumberValue().longValue();
        }

        @Override
        public JsonParser.NumberType getNumberType() throws IOException, JsonParseException {
            Number number = this.getNumberValue();
            if (number instanceof Integer) {
                return JsonParser.NumberType.INT;
            }
            if (number instanceof Long) {
                return JsonParser.NumberType.LONG;
            }
            if (number instanceof Double) {
                return JsonParser.NumberType.DOUBLE;
            }
            if (number instanceof BigDecimal) {
                return JsonParser.NumberType.BIG_DECIMAL;
            }
            if (number instanceof Float) {
                return JsonParser.NumberType.FLOAT;
            }
            if (number instanceof BigInteger) {
                return JsonParser.NumberType.BIG_INTEGER;
            }
            return null;
        }

        @Override
        public final Number getNumberValue() throws IOException, JsonParseException {
            this._checkIsNumber();
            return (Number)this._currentObject();
        }

        @Override
        public JsonStreamContext getParsingContext() {
            return this._parsingContext;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public String getText() {
            String string2;
            if (this._currToken == JsonToken.VALUE_STRING || this._currToken == JsonToken.FIELD_NAME) {
                Object object = this._currentObject();
                if (object instanceof String) {
                    return (String)object;
                }
                string2 = null;
                if (object == null) return string2;
                return object.toString();
            }
            JsonToken jsonToken = this._currToken;
            string2 = null;
            if (jsonToken == null) return string2;
            switch (this._currToken) {
                default: {
                    return this._currToken.asString();
                }
                case VALUE_NUMBER_INT: 
                case VALUE_NUMBER_FLOAT: 
            }
            Object object = this._currentObject();
            string2 = null;
            if (object == null) return string2;
            return object.toString();
        }

        @Override
        public char[] getTextCharacters() {
            String string2 = this.getText();
            if (string2 == null) {
                return null;
            }
            return string2.toCharArray();
        }

        @Override
        public int getTextLength() {
            String string2 = this.getText();
            if (string2 == null) {
                return 0;
            }
            return string2.length();
        }

        @Override
        public int getTextOffset() {
            return 0;
        }

        @Override
        public JsonLocation getTokenLocation() {
            return this.getCurrentLocation();
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
            int n;
            if (this._closed) return null;
            if (this._segment == null) {
                return null;
            }
            this._segmentPtr = n = 1 + this._segmentPtr;
            if (n >= 16) {
                this._segmentPtr = 0;
                this._segment = this._segment.next();
                if (this._segment == null) return null;
            }
            this._currToken = this._segment.type(this._segmentPtr);
            if (this._currToken == JsonToken.FIELD_NAME) {
                Object object = this._currentObject();
                String string2 = object instanceof String ? (String)object : object.toString();
                this._parsingContext.setCurrentName(string2);
                return this._currToken;
            }
            if (this._currToken == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(-1, -1);
                return this._currToken;
            }
            if (this._currToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(-1, -1);
                return this._currToken;
            }
            if (this._currToken != JsonToken.END_OBJECT) {
                if (this._currToken != JsonToken.END_ARRAY) return this._currToken;
            }
            this._parsingContext = this._parsingContext.getParent();
            if (this._parsingContext != null) return this._currToken;
            this._parsingContext = JsonReadContext.createRootContext((int)-1, (int)-1);
            return this._currToken;
        }

        /*
         * Enabled aggressive block sorting
         */
        public JsonToken peekNextToken() throws IOException, JsonParseException {
            if (this._closed) return null;
            Segment segment = this._segment;
            int n = 1 + this._segmentPtr;
            if (n >= 16) {
                n = 0;
                if (segment == null) {
                    return null;
                }
                segment = segment.next();
                n = 0;
            }
            if (segment != null) return segment.type(n);
            return null;
        }

        @Override
        public void setCodec(ObjectCodec objectCodec) {
            this._codec = objectCodec;
        }

        public void setLocation(JsonLocation jsonLocation) {
            this._location = jsonLocation;
        }
    }

    protected static final class Segment {
        public static final int TOKENS_PER_SEGMENT = 16;
        private static final JsonToken[] TOKEN_TYPES_BY_INDEX = new JsonToken[16];
        protected Segment _next;
        protected long _tokenTypes;
        protected final Object[] _tokens = new Object[16];

        static {
            JsonToken[] arrjsonToken = JsonToken.values();
            System.arraycopy((Object)arrjsonToken, (int)1, (Object)TOKEN_TYPES_BY_INDEX, (int)1, (int)Math.min((int)15, (int)(-1 + arrjsonToken.length)));
        }

        public Segment append(int n, JsonToken jsonToken) {
            if (n < 16) {
                this.set(n, jsonToken);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, jsonToken);
            return this._next;
        }

        public Segment append(int n, JsonToken jsonToken, Object object) {
            if (n < 16) {
                this.set(n, jsonToken, object);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, jsonToken, object);
            return this._next;
        }

        public Object get(int n) {
            return this._tokens[n];
        }

        public Segment next() {
            return this._next;
        }

        public void set(int n, JsonToken jsonToken) {
            long l = jsonToken.ordinal();
            if (n > 0) {
                l <<= n << 2;
            }
            this._tokenTypes = l | this._tokenTypes;
        }

        public void set(int n, JsonToken jsonToken, Object object) {
            this._tokens[n] = object;
            long l = jsonToken.ordinal();
            if (n > 0) {
                l <<= n << 2;
            }
            this._tokenTypes = l | this._tokenTypes;
        }

        public JsonToken type(int n) {
            long l = this._tokenTypes;
            if (n > 0) {
                l >>= n << 2;
            }
            int n2 = 15 & (int)l;
            return TOKEN_TYPES_BY_INDEX[n2];
        }
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.impl.JsonWriteContext
 *  com.flurry.org.codehaus.jackson.util.VersionUtil
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Byte
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.NoSuchFieldError
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.math.BigDecimal
 *  java.math.BigInteger
 *  java.util.concurrent.atomic.AtomicBoolean
 *  java.util.concurrent.atomic.AtomicInteger
 *  java.util.concurrent.atomic.AtomicLong
 */
package com.flurry.org.codehaus.jackson.impl;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.PrettyPrinter;
import com.flurry.org.codehaus.jackson.Version;
import com.flurry.org.codehaus.jackson.impl.JsonWriteContext;
import com.flurry.org.codehaus.jackson.util.DefaultPrettyPrinter;
import com.flurry.org.codehaus.jackson.util.VersionUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class JsonGeneratorBase
extends JsonGenerator {
    protected boolean _cfgNumbersAsStrings;
    protected boolean _closed;
    protected int _features;
    protected ObjectCodec _objectCodec;
    protected JsonWriteContext _writeContext;

    protected JsonGeneratorBase(int n2, ObjectCodec objectCodec) {
        this._features = n2;
        this._writeContext = JsonWriteContext.createRootContext();
        this._objectCodec = objectCodec;
        this._cfgNumbersAsStrings = this.isEnabled(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
    }

    protected void _cantHappen() {
        throw new RuntimeException("Internal error: should never end up through this code path");
    }

    protected abstract void _releaseBuffers();

    protected void _reportError(String string) throws JsonGenerationException {
        throw new JsonGenerationException(string);
    }

    protected void _reportUnsupportedOperation() {
        throw new UnsupportedOperationException("Operation not supported by generator of type " + this.getClass().getName());
    }

    protected final void _throwInternal() {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }

    protected abstract void _verifyValueWrite(String var1) throws IOException, JsonGenerationException;

    @Deprecated
    protected void _writeEndArray() throws IOException, JsonGenerationException {
    }

    @Deprecated
    protected void _writeEndObject() throws IOException, JsonGenerationException {
    }

    protected void _writeSimpleObject(Object object) throws IOException, JsonGenerationException {
        if (object == null) {
            this.writeNull();
            return;
        }
        if (object instanceof String) {
            this.writeString((String)object);
            return;
        }
        if (object instanceof Number) {
            Number number = (Number)object;
            if (number instanceof Integer) {
                this.writeNumber(number.intValue());
                return;
            }
            if (number instanceof Long) {
                this.writeNumber(number.longValue());
                return;
            }
            if (number instanceof Double) {
                this.writeNumber(number.doubleValue());
                return;
            }
            if (number instanceof Float) {
                this.writeNumber(number.floatValue());
                return;
            }
            if (number instanceof Short) {
                this.writeNumber(number.shortValue());
                return;
            }
            if (number instanceof Byte) {
                this.writeNumber(number.byteValue());
                return;
            }
            if (number instanceof BigInteger) {
                this.writeNumber((BigInteger)number);
                return;
            }
            if (number instanceof BigDecimal) {
                this.writeNumber((BigDecimal)number);
                return;
            }
            if (number instanceof AtomicInteger) {
                this.writeNumber(((AtomicInteger)number).get());
                return;
            }
            if (number instanceof AtomicLong) {
                this.writeNumber(((AtomicLong)number).get());
                return;
            }
        } else {
            if (object instanceof byte[]) {
                this.writeBinary((byte[])object);
                return;
            }
            if (object instanceof Boolean) {
                this.writeBoolean((Boolean)object);
                return;
            }
            if (object instanceof AtomicBoolean) {
                this.writeBoolean(((AtomicBoolean)object).get());
                return;
            }
        }
        throw new IllegalStateException("No ObjectCodec defined for the generator, can only serialize simple wrapper types (type passed " + object.getClass().getName() + ")");
    }

    @Deprecated
    protected void _writeStartArray() throws IOException, JsonGenerationException {
    }

    @Deprecated
    protected void _writeStartObject() throws IOException, JsonGenerationException {
    }

    @Override
    public void close() throws IOException {
        this._closed = true;
    }

    @Override
    public final void copyCurrentEvent(JsonParser jsonParser) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == null) {
            this._reportError("No current event to copy");
        }
        switch (jsonToken) {
            default: {
                this._cantHappen();
                return;
            }
            case START_OBJECT: {
                this.writeStartObject();
                return;
            }
            case END_OBJECT: {
                this.writeEndObject();
                return;
            }
            case START_ARRAY: {
                this.writeStartArray();
                return;
            }
            case END_ARRAY: {
                this.writeEndArray();
                return;
            }
            case FIELD_NAME: {
                this.writeFieldName(jsonParser.getCurrentName());
                return;
            }
            case VALUE_STRING: {
                if (jsonParser.hasTextCharacters()) {
                    this.writeString(jsonParser.getTextCharacters(), jsonParser.getTextOffset(), jsonParser.getTextLength());
                    return;
                }
                this.writeString(jsonParser.getText());
                return;
            }
            case VALUE_NUMBER_INT: {
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
            case VALUE_NUMBER_FLOAT: {
                switch (1.$SwitchMap$org$codehaus$jackson$JsonParser$NumberType[jsonParser.getNumberType().ordinal()]) {
                    default: {
                        this.writeNumber(jsonParser.getDoubleValue());
                        return;
                    }
                    case 3: {
                        this.writeNumber(jsonParser.getDecimalValue());
                        return;
                    }
                    case 4: 
                }
                this.writeNumber(jsonParser.getFloatValue());
                return;
            }
            case VALUE_TRUE: {
                this.writeBoolean(true);
                return;
            }
            case VALUE_FALSE: {
                this.writeBoolean(false);
                return;
            }
            case VALUE_NULL: {
                this.writeNull();
                return;
            }
            case VALUE_EMBEDDED_OBJECT: 
        }
        this.writeObject(jsonParser.getEmbeddedObject());
    }

    @Override
    public final void copyCurrentStructure(JsonParser jsonParser) throws IOException, JsonProcessingException {
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

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonGenerator disable(JsonGenerator.Feature feature) {
        this._features &= -1 ^ feature.getMask();
        if (feature == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
            this._cfgNumbersAsStrings = false;
            return this;
        } else {
            if (feature != JsonGenerator.Feature.ESCAPE_NON_ASCII) return this;
            {
                this.setHighestNonEscapedChar(0);
                return this;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonGenerator enable(JsonGenerator.Feature feature) {
        this._features |= feature.getMask();
        if (feature == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
            this._cfgNumbersAsStrings = true;
            return this;
        } else {
            if (feature != JsonGenerator.Feature.ESCAPE_NON_ASCII) return this;
            {
                this.setHighestNonEscapedChar(127);
                return this;
            }
        }
    }

    @Override
    public abstract void flush() throws IOException;

    @Override
    public final ObjectCodec getCodec() {
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
    public final boolean isEnabled(JsonGenerator.Feature feature) {
        return (this._features & feature.getMask()) != 0;
    }

    @Override
    public JsonGenerator setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
        return this;
    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        return this.setPrettyPrinter(new DefaultPrettyPrinter());
    }

    @Override
    public Version version() {
        return VersionUtil.versionFor((Class)this.getClass());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeEndArray() throws IOException, JsonGenerationException {
        if (!this._writeContext.inArray()) {
            this._reportError("Current context not an ARRAY but " + this._writeContext.getTypeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
        } else {
            this._writeEndArray();
        }
        this._writeContext = this._writeContext.getParent();
    }

    @Override
    public void writeEndObject() throws IOException, JsonGenerationException {
        if (!this._writeContext.inObject()) {
            this._reportError("Current context not an object but " + this._writeContext.getTypeDesc());
        }
        this._writeContext = this._writeContext.getParent();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
            return;
        }
        this._writeEndObject();
    }

    @Override
    public void writeObject(Object object) throws IOException, JsonProcessingException {
        if (object == null) {
            this.writeNull();
            return;
        }
        if (this._objectCodec != null) {
            this._objectCodec.writeValue((JsonGenerator)this, object);
            return;
        }
        this._writeSimpleObject(object);
    }

    @Override
    public void writeRawValue(String string) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(string);
    }

    @Override
    public void writeRawValue(String string, int n2, int n3) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(string, n2, n3);
    }

    @Override
    public void writeRawValue(char[] arrc, int n2, int n3) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(arrc, n2, n3);
    }

    @Override
    public void writeStartArray() throws IOException, JsonGenerationException {
        this._verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
            return;
        }
        this._writeStartArray();
    }

    @Override
    public void writeStartObject() throws IOException, JsonGenerationException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
            return;
        }
        this._writeStartObject();
    }

    @Override
    public void writeTree(JsonNode jsonNode) throws IOException, JsonProcessingException {
        if (jsonNode == null) {
            this.writeNull();
            return;
        }
        if (this._objectCodec == null) {
            throw new IllegalStateException("No ObjectCodec defined for the generator, can not serialize JsonNode-based trees");
        }
        this._objectCodec.writeTree((JsonGenerator)this, jsonNode);
    }

}


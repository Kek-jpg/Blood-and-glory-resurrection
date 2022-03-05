/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.type.TypeReference
 *  java.io.Closeable
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.io.Writer
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.IllegalStateException
 *  java.lang.NoSuchFieldError
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.math.BigDecimal
 *  java.math.BigInteger
 *  java.util.Iterator
 */
package com.flurry.org.codehaus.jackson;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.Base64Variants;
import com.flurry.org.codehaus.jackson.FormatSchema;
import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.SerializableString;
import com.flurry.org.codehaus.jackson.Version;
import com.flurry.org.codehaus.jackson.Versioned;
import com.flurry.org.codehaus.jackson.type.TypeReference;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

public abstract class JsonParser
implements Closeable,
Versioned {
    private static final int MAX_BYTE_I = 127;
    private static final int MAX_SHORT_I = 32767;
    private static final int MIN_BYTE_I = -128;
    private static final int MIN_SHORT_I = -32768;
    protected JsonToken _currToken;
    protected int _features;
    protected JsonToken _lastClearedToken;

    protected JsonParser() {
    }

    protected JsonParser(int n2) {
        this._features = n2;
    }

    protected JsonParseException _constructError(String string) {
        return new JsonParseException(string, this.getCurrentLocation());
    }

    public boolean canUseSchema(FormatSchema formatSchema) {
        return false;
    }

    public void clearCurrentToken() {
        if (this._currToken != null) {
            this._lastClearedToken = this._currToken;
            this._currToken = null;
        }
    }

    public abstract void close() throws IOException;

    public JsonParser configure(Feature feature, boolean bl) {
        if (bl) {
            this.enableFeature(feature);
            return this;
        }
        this.disableFeature(feature);
        return this;
    }

    public JsonParser disable(Feature feature) {
        this._features &= -1 ^ feature.getMask();
        return this;
    }

    public void disableFeature(Feature feature) {
        this.disable(feature);
    }

    public JsonParser enable(Feature feature) {
        this._features |= feature.getMask();
        return this;
    }

    public void enableFeature(Feature feature) {
        this.enable(feature);
    }

    public abstract BigInteger getBigIntegerValue() throws IOException, JsonParseException;

    public byte[] getBinaryValue() throws IOException, JsonParseException {
        return this.getBinaryValue(Base64Variants.getDefaultVariant());
    }

    public abstract byte[] getBinaryValue(Base64Variant var1) throws IOException, JsonParseException;

    public boolean getBooleanValue() throws IOException, JsonParseException {
        if (this.getCurrentToken() == JsonToken.VALUE_TRUE) {
            return true;
        }
        if (this.getCurrentToken() == JsonToken.VALUE_FALSE) {
            return false;
        }
        throw new JsonParseException("Current token (" + (Object)((Object)this._currToken) + ") not of boolean type", this.getCurrentLocation());
    }

    public byte getByteValue() throws IOException, JsonParseException {
        int n2 = this.getIntValue();
        if (n2 < -128 || n2 > 127) {
            throw this._constructError("Numeric value (" + this.getText() + ") out of range of Java byte");
        }
        return (byte)n2;
    }

    public abstract ObjectCodec getCodec();

    public abstract JsonLocation getCurrentLocation();

    public abstract String getCurrentName() throws IOException, JsonParseException;

    public JsonToken getCurrentToken() {
        return this._currToken;
    }

    public abstract BigDecimal getDecimalValue() throws IOException, JsonParseException;

    public abstract double getDoubleValue() throws IOException, JsonParseException;

    public Object getEmbeddedObject() throws IOException, JsonParseException {
        return null;
    }

    public abstract float getFloatValue() throws IOException, JsonParseException;

    public Object getInputSource() {
        return null;
    }

    public abstract int getIntValue() throws IOException, JsonParseException;

    public JsonToken getLastClearedToken() {
        return this._lastClearedToken;
    }

    public abstract long getLongValue() throws IOException, JsonParseException;

    public abstract NumberType getNumberType() throws IOException, JsonParseException;

    public abstract Number getNumberValue() throws IOException, JsonParseException;

    public abstract JsonStreamContext getParsingContext();

    public short getShortValue() throws IOException, JsonParseException {
        int n2 = this.getIntValue();
        if (n2 < -32768 || n2 > 32767) {
            throw this._constructError("Numeric value (" + this.getText() + ") out of range of Java short");
        }
        return (short)n2;
    }

    public abstract String getText() throws IOException, JsonParseException;

    public abstract char[] getTextCharacters() throws IOException, JsonParseException;

    public abstract int getTextLength() throws IOException, JsonParseException;

    public abstract int getTextOffset() throws IOException, JsonParseException;

    public abstract JsonLocation getTokenLocation();

    public boolean getValueAsBoolean() throws IOException, JsonParseException {
        return this.getValueAsBoolean(false);
    }

    public boolean getValueAsBoolean(boolean bl) throws IOException, JsonParseException {
        return bl;
    }

    public double getValueAsDouble() throws IOException, JsonParseException {
        return this.getValueAsDouble(0.0);
    }

    public double getValueAsDouble(double d2) throws IOException, JsonParseException {
        return d2;
    }

    public int getValueAsInt() throws IOException, JsonParseException {
        return this.getValueAsInt(0);
    }

    public int getValueAsInt(int n2) throws IOException, JsonParseException {
        return n2;
    }

    public long getValueAsLong() throws IOException, JsonParseException {
        return this.getValueAsInt(0);
    }

    public long getValueAsLong(long l2) throws IOException, JsonParseException {
        return l2;
    }

    public boolean hasCurrentToken() {
        return this._currToken != null;
    }

    public boolean hasTextCharacters() {
        return false;
    }

    public abstract boolean isClosed();

    public boolean isEnabled(Feature feature) {
        return (this._features & feature.getMask()) != 0;
    }

    public boolean isExpectedStartArrayToken() {
        return this.getCurrentToken() == JsonToken.START_ARRAY;
    }

    public final boolean isFeatureEnabled(Feature feature) {
        return this.isEnabled(feature);
    }

    public Boolean nextBooleanValue() throws IOException, JsonParseException {
        switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[this.nextToken().ordinal()]) {
            default: {
                return null;
            }
            case 1: {
                return Boolean.TRUE;
            }
            case 2: 
        }
        return Boolean.FALSE;
    }

    public boolean nextFieldName(SerializableString serializableString) throws IOException, JsonParseException {
        return this.nextToken() == JsonToken.FIELD_NAME && serializableString.getValue().equals((Object)this.getCurrentName());
    }

    public int nextIntValue(int n2) throws IOException, JsonParseException {
        if (this.nextToken() == JsonToken.VALUE_NUMBER_INT) {
            n2 = this.getIntValue();
        }
        return n2;
    }

    public long nextLongValue(long l2) throws IOException, JsonParseException {
        if (this.nextToken() == JsonToken.VALUE_NUMBER_INT) {
            l2 = this.getLongValue();
        }
        return l2;
    }

    public String nextTextValue() throws IOException, JsonParseException {
        if (this.nextToken() == JsonToken.VALUE_STRING) {
            return this.getText();
        }
        return null;
    }

    public abstract JsonToken nextToken() throws IOException, JsonParseException;

    public JsonToken nextValue() throws IOException, JsonParseException {
        JsonToken jsonToken = this.nextToken();
        if (jsonToken == JsonToken.FIELD_NAME) {
            jsonToken = this.nextToken();
        }
        return jsonToken;
    }

    public <T> T readValueAs(TypeReference<?> typeReference) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = this.getCodec();
        if (objectCodec == null) {
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
        }
        return objectCodec.readValue((JsonParser)this, typeReference);
    }

    public <T> T readValueAs(Class<T> class_) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = this.getCodec();
        if (objectCodec == null) {
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
        }
        return objectCodec.readValue((JsonParser)this, class_);
    }

    public JsonNode readValueAsTree() throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = this.getCodec();
        if (objectCodec == null) {
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into JsonNode tree");
        }
        return objectCodec.readTree(this);
    }

    public <T> Iterator<T> readValuesAs(TypeReference<?> typeReference) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = this.getCodec();
        if (objectCodec == null) {
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
        }
        return objectCodec.readValues((JsonParser)this, typeReference);
    }

    public <T> Iterator<T> readValuesAs(Class<T> class_) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = this.getCodec();
        if (objectCodec == null) {
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
        }
        return objectCodec.readValues((JsonParser)this, class_);
    }

    public int releaseBuffered(OutputStream outputStream) throws IOException {
        return -1;
    }

    public int releaseBuffered(Writer writer) throws IOException {
        return -1;
    }

    public abstract void setCodec(ObjectCodec var1);

    public void setFeature(Feature feature, boolean bl) {
        this.configure(feature, bl);
    }

    public void setSchema(FormatSchema formatSchema) {
        throw new UnsupportedOperationException("Parser of type " + this.getClass().getName() + " does not support schema of type '" + formatSchema.getSchemaType() + "'");
    }

    public abstract JsonParser skipChildren() throws IOException, JsonParseException;

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    public static final class Feature
    extends Enum<Feature> {
        private static final /* synthetic */ Feature[] $VALUES;
        public static final /* enum */ Feature ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER;
        public static final /* enum */ Feature ALLOW_COMMENTS;
        public static final /* enum */ Feature ALLOW_NON_NUMERIC_NUMBERS;
        public static final /* enum */ Feature ALLOW_NUMERIC_LEADING_ZEROS;
        public static final /* enum */ Feature ALLOW_SINGLE_QUOTES;
        public static final /* enum */ Feature ALLOW_UNQUOTED_CONTROL_CHARS;
        public static final /* enum */ Feature ALLOW_UNQUOTED_FIELD_NAMES;
        public static final /* enum */ Feature AUTO_CLOSE_SOURCE;
        public static final /* enum */ Feature CANONICALIZE_FIELD_NAMES;
        public static final /* enum */ Feature INTERN_FIELD_NAMES;
        final boolean _defaultState;

        static {
            AUTO_CLOSE_SOURCE = new Feature(true);
            ALLOW_COMMENTS = new Feature(false);
            ALLOW_UNQUOTED_FIELD_NAMES = new Feature(false);
            ALLOW_SINGLE_QUOTES = new Feature(false);
            ALLOW_UNQUOTED_CONTROL_CHARS = new Feature(false);
            ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER = new Feature(false);
            ALLOW_NUMERIC_LEADING_ZEROS = new Feature(false);
            ALLOW_NON_NUMERIC_NUMBERS = new Feature(false);
            INTERN_FIELD_NAMES = new Feature(true);
            CANONICALIZE_FIELD_NAMES = new Feature(true);
            Feature[] arrfeature = new Feature[]{AUTO_CLOSE_SOURCE, ALLOW_COMMENTS, ALLOW_UNQUOTED_FIELD_NAMES, ALLOW_SINGLE_QUOTES, ALLOW_UNQUOTED_CONTROL_CHARS, ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, ALLOW_NUMERIC_LEADING_ZEROS, ALLOW_NON_NUMERIC_NUMBERS, INTERN_FIELD_NAMES, CANONICALIZE_FIELD_NAMES};
            $VALUES = arrfeature;
        }

        private Feature(boolean bl) {
            this._defaultState = bl;
        }

        public static int collectDefaults() {
            int n2 = 0;
            for (Feature feature : Feature.values()) {
                if (!feature.enabledByDefault()) continue;
                n2 |= feature.getMask();
            }
            return n2;
        }

        public static Feature valueOf(String string) {
            return (Feature)Enum.valueOf(Feature.class, (String)string);
        }

        public static Feature[] values() {
            return (Feature[])$VALUES.clone();
        }

        public boolean enabledByDefault() {
            return this._defaultState;
        }

        public boolean enabledIn(int n2) {
            return (n2 & this.getMask()) != 0;
        }

        public int getMask() {
            return 1 << this.ordinal();
        }
    }

    public static final class NumberType
    extends Enum<NumberType> {
        private static final /* synthetic */ NumberType[] $VALUES;
        public static final /* enum */ NumberType BIG_DECIMAL;
        public static final /* enum */ NumberType BIG_INTEGER;
        public static final /* enum */ NumberType DOUBLE;
        public static final /* enum */ NumberType FLOAT;
        public static final /* enum */ NumberType INT;
        public static final /* enum */ NumberType LONG;

        static {
            INT = new NumberType();
            LONG = new NumberType();
            BIG_INTEGER = new NumberType();
            FLOAT = new NumberType();
            DOUBLE = new NumberType();
            BIG_DECIMAL = new NumberType();
            NumberType[] arrnumberType = new NumberType[]{INT, LONG, BIG_INTEGER, FLOAT, DOUBLE, BIG_DECIMAL};
            $VALUES = arrnumberType;
        }

        public static NumberType valueOf(String string) {
            return (NumberType)Enum.valueOf(NumberType.class, (String)string);
        }

        public static NumberType[] values() {
            return (NumberType[])$VALUES.clone();
        }
    }

}


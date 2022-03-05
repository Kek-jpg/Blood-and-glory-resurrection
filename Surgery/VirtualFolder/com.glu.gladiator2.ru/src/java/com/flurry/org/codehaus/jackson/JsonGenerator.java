/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.io.CharacterEscapes
 *  com.flurry.org.codehaus.jackson.io.SerializedString
 *  java.io.Closeable
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.math.BigDecimal
 *  java.math.BigInteger
 */
package com.flurry.org.codehaus.jackson;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.Base64Variants;
import com.flurry.org.codehaus.jackson.FormatSchema;
import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.PrettyPrinter;
import com.flurry.org.codehaus.jackson.SerializableString;
import com.flurry.org.codehaus.jackson.Version;
import com.flurry.org.codehaus.jackson.Versioned;
import com.flurry.org.codehaus.jackson.io.CharacterEscapes;
import com.flurry.org.codehaus.jackson.io.SerializedString;
import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class JsonGenerator
implements Closeable,
Versioned {
    protected PrettyPrinter _cfgPrettyPrinter;

    protected JsonGenerator() {
    }

    public boolean canUseSchema(FormatSchema formatSchema) {
        return false;
    }

    public abstract void close() throws IOException;

    public JsonGenerator configure(Feature feature, boolean bl) {
        if (bl) {
            this.enable(feature);
            return this;
        }
        this.disable(feature);
        return this;
    }

    public abstract void copyCurrentEvent(JsonParser var1) throws IOException, JsonProcessingException;

    public abstract void copyCurrentStructure(JsonParser var1) throws IOException, JsonProcessingException;

    public abstract JsonGenerator disable(Feature var1);

    @Deprecated
    public void disableFeature(Feature feature) {
        this.disable(feature);
    }

    public abstract JsonGenerator enable(Feature var1);

    @Deprecated
    public void enableFeature(Feature feature) {
        this.enable(feature);
    }

    public abstract void flush() throws IOException;

    public CharacterEscapes getCharacterEscapes() {
        return null;
    }

    public abstract ObjectCodec getCodec();

    public int getHighestEscapedChar() {
        return 0;
    }

    public abstract JsonStreamContext getOutputContext();

    public Object getOutputTarget() {
        return null;
    }

    public abstract boolean isClosed();

    public abstract boolean isEnabled(Feature var1);

    @Deprecated
    public boolean isFeatureEnabled(Feature feature) {
        return this.isEnabled(feature);
    }

    public JsonGenerator setCharacterEscapes(CharacterEscapes characterEscapes) {
        return this;
    }

    public abstract JsonGenerator setCodec(ObjectCodec var1);

    @Deprecated
    public void setFeature(Feature feature, boolean bl) {
        this.configure(feature, bl);
    }

    public JsonGenerator setHighestNonEscapedChar(int n2) {
        return this;
    }

    public JsonGenerator setPrettyPrinter(PrettyPrinter prettyPrinter) {
        this._cfgPrettyPrinter = prettyPrinter;
        return this;
    }

    public void setSchema(FormatSchema formatSchema) {
        throw new UnsupportedOperationException("Generator of type " + this.getClass().getName() + " does not support schema of type '" + formatSchema.getSchemaType() + "'");
    }

    public abstract JsonGenerator useDefaultPrettyPrinter();

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    public final void writeArrayFieldStart(String string) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeStartArray();
    }

    public abstract void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException;

    public void writeBinary(byte[] arrby) throws IOException, JsonGenerationException {
        this.writeBinary(Base64Variants.getDefaultVariant(), arrby, 0, arrby.length);
    }

    public void writeBinary(byte[] arrby, int n2, int n3) throws IOException, JsonGenerationException {
        this.writeBinary(Base64Variants.getDefaultVariant(), arrby, n2, n3);
    }

    public final void writeBinaryField(String string, byte[] arrby) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeBinary(arrby);
    }

    public abstract void writeBoolean(boolean var1) throws IOException, JsonGenerationException;

    public final void writeBooleanField(String string, boolean bl) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeBoolean(bl);
    }

    public abstract void writeEndArray() throws IOException, JsonGenerationException;

    public abstract void writeEndObject() throws IOException, JsonGenerationException;

    public void writeFieldName(SerializableString serializableString) throws IOException, JsonGenerationException {
        this.writeFieldName(serializableString.getValue());
    }

    public void writeFieldName(SerializedString serializedString) throws IOException, JsonGenerationException {
        this.writeFieldName(serializedString.getValue());
    }

    public abstract void writeFieldName(String var1) throws IOException, JsonGenerationException;

    public abstract void writeNull() throws IOException, JsonGenerationException;

    public final void writeNullField(String string) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeNull();
    }

    public abstract void writeNumber(double var1) throws IOException, JsonGenerationException;

    public abstract void writeNumber(float var1) throws IOException, JsonGenerationException;

    public abstract void writeNumber(int var1) throws IOException, JsonGenerationException;

    public abstract void writeNumber(long var1) throws IOException, JsonGenerationException;

    public abstract void writeNumber(String var1) throws IOException, JsonGenerationException, UnsupportedOperationException;

    public abstract void writeNumber(BigDecimal var1) throws IOException, JsonGenerationException;

    public abstract void writeNumber(BigInteger var1) throws IOException, JsonGenerationException;

    public final void writeNumberField(String string, double d2) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeNumber(d2);
    }

    public final void writeNumberField(String string, float f2) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeNumber(f2);
    }

    public final void writeNumberField(String string, int n2) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeNumber(n2);
    }

    public final void writeNumberField(String string, long l2) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeNumber(l2);
    }

    public final void writeNumberField(String string, BigDecimal bigDecimal) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeNumber(bigDecimal);
    }

    public abstract void writeObject(Object var1) throws IOException, JsonProcessingException;

    public final void writeObjectField(String string, Object object) throws IOException, JsonProcessingException {
        this.writeFieldName(string);
        this.writeObject(object);
    }

    public final void writeObjectFieldStart(String string) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeStartObject();
    }

    public abstract void writeRaw(char var1) throws IOException, JsonGenerationException;

    public abstract void writeRaw(String var1) throws IOException, JsonGenerationException;

    public abstract void writeRaw(String var1, int var2, int var3) throws IOException, JsonGenerationException;

    public abstract void writeRaw(char[] var1, int var2, int var3) throws IOException, JsonGenerationException;

    public abstract void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException;

    public abstract void writeRawValue(String var1) throws IOException, JsonGenerationException;

    public abstract void writeRawValue(String var1, int var2, int var3) throws IOException, JsonGenerationException;

    public abstract void writeRawValue(char[] var1, int var2, int var3) throws IOException, JsonGenerationException;

    public abstract void writeStartArray() throws IOException, JsonGenerationException;

    public abstract void writeStartObject() throws IOException, JsonGenerationException;

    public void writeString(SerializableString serializableString) throws IOException, JsonGenerationException {
        this.writeString(serializableString.getValue());
    }

    public abstract void writeString(String var1) throws IOException, JsonGenerationException;

    public abstract void writeString(char[] var1, int var2, int var3) throws IOException, JsonGenerationException;

    public void writeStringField(String string, String string2) throws IOException, JsonGenerationException {
        this.writeFieldName(string);
        this.writeString(string2);
    }

    public abstract void writeTree(JsonNode var1) throws IOException, JsonProcessingException;

    public abstract void writeUTF8String(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException;

    public static final class Feature
    extends Enum<Feature> {
        private static final /* synthetic */ Feature[] $VALUES;
        public static final /* enum */ Feature AUTO_CLOSE_JSON_CONTENT;
        public static final /* enum */ Feature AUTO_CLOSE_TARGET;
        public static final /* enum */ Feature ESCAPE_NON_ASCII;
        public static final /* enum */ Feature FLUSH_PASSED_TO_STREAM;
        public static final /* enum */ Feature QUOTE_FIELD_NAMES;
        public static final /* enum */ Feature QUOTE_NON_NUMERIC_NUMBERS;
        public static final /* enum */ Feature WRITE_NUMBERS_AS_STRINGS;
        final boolean _defaultState;
        final int _mask;

        static {
            AUTO_CLOSE_TARGET = new Feature(true);
            AUTO_CLOSE_JSON_CONTENT = new Feature(true);
            QUOTE_FIELD_NAMES = new Feature(true);
            QUOTE_NON_NUMERIC_NUMBERS = new Feature(true);
            WRITE_NUMBERS_AS_STRINGS = new Feature(false);
            FLUSH_PASSED_TO_STREAM = new Feature(true);
            ESCAPE_NON_ASCII = new Feature(false);
            Feature[] arrfeature = new Feature[]{AUTO_CLOSE_TARGET, AUTO_CLOSE_JSON_CONTENT, QUOTE_FIELD_NAMES, QUOTE_NON_NUMERIC_NUMBERS, WRITE_NUMBERS_AS_STRINGS, FLUSH_PASSED_TO_STREAM, ESCAPE_NON_ASCII};
            $VALUES = arrfeature;
        }

        private Feature(boolean bl) {
            this._defaultState = bl;
            this._mask = 1 << this.ordinal();
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

        public int getMask() {
            return this._mask;
        }
    }

}


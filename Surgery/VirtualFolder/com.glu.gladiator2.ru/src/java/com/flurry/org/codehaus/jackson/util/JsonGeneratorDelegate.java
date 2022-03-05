/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.io.SerializedString
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.math.BigDecimal
 *  java.math.BigInteger
 */
package com.flurry.org.codehaus.jackson.util;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.FormatSchema;
import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.SerializableString;
import com.flurry.org.codehaus.jackson.Version;
import com.flurry.org.codehaus.jackson.io.SerializedString;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonGeneratorDelegate
extends JsonGenerator {
    protected JsonGenerator delegate;

    public JsonGeneratorDelegate(JsonGenerator jsonGenerator) {
        this.delegate = jsonGenerator;
    }

    @Override
    public boolean canUseSchema(FormatSchema formatSchema) {
        return this.delegate.canUseSchema(formatSchema);
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    @Override
    public void copyCurrentEvent(JsonParser jsonParser) throws IOException, JsonProcessingException {
        this.delegate.copyCurrentEvent(jsonParser);
    }

    @Override
    public void copyCurrentStructure(JsonParser jsonParser) throws IOException, JsonProcessingException {
        this.delegate.copyCurrentStructure(jsonParser);
    }

    @Override
    public JsonGenerator disable(JsonGenerator.Feature feature) {
        return this.delegate.disable(feature);
    }

    @Override
    public JsonGenerator enable(JsonGenerator.Feature feature) {
        return this.delegate.enable(feature);
    }

    @Override
    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override
    public ObjectCodec getCodec() {
        return this.delegate.getCodec();
    }

    @Override
    public JsonStreamContext getOutputContext() {
        return this.delegate.getOutputContext();
    }

    @Override
    public Object getOutputTarget() {
        return this.delegate.getOutputTarget();
    }

    @Override
    public boolean isClosed() {
        return this.delegate.isClosed();
    }

    @Override
    public boolean isEnabled(JsonGenerator.Feature feature) {
        return this.delegate.isEnabled(feature);
    }

    @Override
    public JsonGenerator setCodec(ObjectCodec objectCodec) {
        this.delegate.setCodec(objectCodec);
        return this;
    }

    @Override
    public void setSchema(FormatSchema formatSchema) {
        this.delegate.setSchema(formatSchema);
    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        this.delegate.useDefaultPrettyPrinter();
        return this;
    }

    @Override
    public Version version() {
        return this.delegate.version();
    }

    @Override
    public void writeBinary(Base64Variant base64Variant, byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        this.delegate.writeBinary(base64Variant, arrby, n, n2);
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException, JsonGenerationException {
        this.delegate.writeBoolean(bl);
    }

    @Override
    public void writeEndArray() throws IOException, JsonGenerationException {
        this.delegate.writeEndArray();
    }

    @Override
    public void writeEndObject() throws IOException, JsonGenerationException {
        this.delegate.writeEndObject();
    }

    @Override
    public void writeFieldName(SerializableString serializableString) throws IOException, JsonGenerationException {
        this.delegate.writeFieldName(serializableString);
    }

    @Override
    public void writeFieldName(SerializedString serializedString) throws IOException, JsonGenerationException {
        this.delegate.writeFieldName(serializedString);
    }

    @Override
    public void writeFieldName(String string2) throws IOException, JsonGenerationException {
        this.delegate.writeFieldName(string2);
    }

    @Override
    public void writeNull() throws IOException, JsonGenerationException {
        this.delegate.writeNull();
    }

    @Override
    public void writeNumber(double d2) throws IOException, JsonGenerationException {
        this.delegate.writeNumber(d2);
    }

    @Override
    public void writeNumber(float f2) throws IOException, JsonGenerationException {
        this.delegate.writeNumber(f2);
    }

    @Override
    public void writeNumber(int n) throws IOException, JsonGenerationException {
        this.delegate.writeNumber(n);
    }

    @Override
    public void writeNumber(long l) throws IOException, JsonGenerationException {
        this.delegate.writeNumber(l);
    }

    @Override
    public void writeNumber(String string2) throws IOException, JsonGenerationException, UnsupportedOperationException {
        this.delegate.writeNumber(string2);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException, JsonGenerationException {
        this.delegate.writeNumber(bigDecimal);
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException, JsonGenerationException {
        this.delegate.writeNumber(bigInteger);
    }

    @Override
    public void writeObject(Object object) throws IOException, JsonProcessingException {
        this.delegate.writeObject(object);
    }

    @Override
    public void writeRaw(char c2) throws IOException, JsonGenerationException {
        this.delegate.writeRaw(c2);
    }

    @Override
    public void writeRaw(String string2) throws IOException, JsonGenerationException {
        this.delegate.writeRaw(string2);
    }

    @Override
    public void writeRaw(String string2, int n, int n2) throws IOException, JsonGenerationException {
        this.delegate.writeRaw(string2, n, n2);
    }

    @Override
    public void writeRaw(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        this.delegate.writeRaw(arrc, n, n2);
    }

    @Override
    public void writeRawUTF8String(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        this.delegate.writeRawUTF8String(arrby, n, n2);
    }

    @Override
    public void writeRawValue(String string2) throws IOException, JsonGenerationException {
        this.delegate.writeRawValue(string2);
    }

    @Override
    public void writeRawValue(String string2, int n, int n2) throws IOException, JsonGenerationException {
        this.delegate.writeRawValue(string2, n, n2);
    }

    @Override
    public void writeRawValue(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        this.delegate.writeRawValue(arrc, n, n2);
    }

    @Override
    public void writeStartArray() throws IOException, JsonGenerationException {
        this.delegate.writeStartArray();
    }

    @Override
    public void writeStartObject() throws IOException, JsonGenerationException {
        this.delegate.writeStartObject();
    }

    @Override
    public void writeString(SerializableString serializableString) throws IOException, JsonGenerationException {
        this.delegate.writeString(serializableString);
    }

    @Override
    public void writeString(String string2) throws IOException, JsonGenerationException {
        this.delegate.writeString(string2);
    }

    @Override
    public void writeString(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        this.delegate.writeString(arrc, n, n2);
    }

    @Override
    public void writeTree(JsonNode jsonNode) throws IOException, JsonProcessingException {
        this.delegate.writeTree(jsonNode);
    }

    @Override
    public void writeUTF8String(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        this.delegate.writeUTF8String(arrby, n, n2);
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 *  java.math.BigDecimal
 *  java.math.BigInteger
 */
package com.flurry.org.codehaus.jackson.util;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.FormatSchema;
import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.Version;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonParserDelegate
extends JsonParser {
    protected JsonParser delegate;

    public JsonParserDelegate(JsonParser jsonParser) {
        this.delegate = jsonParser;
    }

    @Override
    public boolean canUseSchema(FormatSchema formatSchema) {
        return this.delegate.canUseSchema(formatSchema);
    }

    @Override
    public void clearCurrentToken() {
        this.delegate.clearCurrentToken();
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    @Override
    public JsonParser disable(JsonParser.Feature feature) {
        this.delegate.disable(feature);
        return this;
    }

    @Override
    public JsonParser enable(JsonParser.Feature feature) {
        this.delegate.enable(feature);
        return this;
    }

    @Override
    public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
        return this.delegate.getBigIntegerValue();
    }

    @Override
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException, JsonParseException {
        return this.delegate.getBinaryValue(base64Variant);
    }

    @Override
    public boolean getBooleanValue() throws IOException, JsonParseException {
        return this.delegate.getBooleanValue();
    }

    @Override
    public byte getByteValue() throws IOException, JsonParseException {
        return this.delegate.getByteValue();
    }

    @Override
    public ObjectCodec getCodec() {
        return this.delegate.getCodec();
    }

    @Override
    public JsonLocation getCurrentLocation() {
        return this.delegate.getCurrentLocation();
    }

    @Override
    public String getCurrentName() throws IOException, JsonParseException {
        return this.delegate.getCurrentName();
    }

    @Override
    public JsonToken getCurrentToken() {
        return this.delegate.getCurrentToken();
    }

    @Override
    public BigDecimal getDecimalValue() throws IOException, JsonParseException {
        return this.delegate.getDecimalValue();
    }

    @Override
    public double getDoubleValue() throws IOException, JsonParseException {
        return this.delegate.getDoubleValue();
    }

    @Override
    public Object getEmbeddedObject() throws IOException, JsonParseException {
        return this.delegate.getEmbeddedObject();
    }

    @Override
    public float getFloatValue() throws IOException, JsonParseException {
        return this.delegate.getFloatValue();
    }

    @Override
    public Object getInputSource() {
        return this.delegate.getInputSource();
    }

    @Override
    public int getIntValue() throws IOException, JsonParseException {
        return this.delegate.getIntValue();
    }

    @Override
    public JsonToken getLastClearedToken() {
        return this.delegate.getLastClearedToken();
    }

    @Override
    public long getLongValue() throws IOException, JsonParseException {
        return this.delegate.getLongValue();
    }

    @Override
    public JsonParser.NumberType getNumberType() throws IOException, JsonParseException {
        return this.delegate.getNumberType();
    }

    @Override
    public Number getNumberValue() throws IOException, JsonParseException {
        return this.delegate.getNumberValue();
    }

    @Override
    public JsonStreamContext getParsingContext() {
        return this.delegate.getParsingContext();
    }

    @Override
    public short getShortValue() throws IOException, JsonParseException {
        return this.delegate.getShortValue();
    }

    @Override
    public String getText() throws IOException, JsonParseException {
        return this.delegate.getText();
    }

    @Override
    public char[] getTextCharacters() throws IOException, JsonParseException {
        return this.delegate.getTextCharacters();
    }

    @Override
    public int getTextLength() throws IOException, JsonParseException {
        return this.delegate.getTextLength();
    }

    @Override
    public int getTextOffset() throws IOException, JsonParseException {
        return this.delegate.getTextOffset();
    }

    @Override
    public JsonLocation getTokenLocation() {
        return this.delegate.getTokenLocation();
    }

    @Override
    public boolean hasCurrentToken() {
        return this.delegate.hasCurrentToken();
    }

    @Override
    public boolean isClosed() {
        return this.delegate.isClosed();
    }

    @Override
    public boolean isEnabled(JsonParser.Feature feature) {
        return this.delegate.isEnabled(feature);
    }

    @Override
    public JsonToken nextToken() throws IOException, JsonParseException {
        return this.delegate.nextToken();
    }

    @Override
    public void setCodec(ObjectCodec objectCodec) {
        this.delegate.setCodec(objectCodec);
    }

    @Override
    public void setSchema(FormatSchema formatSchema) {
        this.delegate.setSchema(formatSchema);
    }

    @Override
    public JsonParser skipChildren() throws IOException, JsonParseException {
        this.delegate.skipChildren();
        return this;
    }

    @Override
    public Version version() {
        return this.delegate.version();
    }
}


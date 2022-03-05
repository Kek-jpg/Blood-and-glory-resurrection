/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.util;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.PrettyPrinter;
import java.io.IOException;

public class MinimalPrettyPrinter
implements PrettyPrinter {
    public static final String DEFAULT_ROOT_VALUE_SEPARATOR = " ";
    protected String _rootValueSeparator = " ";

    public MinimalPrettyPrinter() {
        this(DEFAULT_ROOT_VALUE_SEPARATOR);
    }

    public MinimalPrettyPrinter(String string2) {
        this._rootValueSeparator = string2;
    }

    @Override
    public void beforeArrayValues(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
    }

    @Override
    public void beforeObjectEntries(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
    }

    public void setRootValueSeparator(String string2) {
        this._rootValueSeparator = string2;
    }

    @Override
    public void writeArrayValueSeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(',');
    }

    @Override
    public void writeEndArray(JsonGenerator jsonGenerator, int n) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(']');
    }

    @Override
    public void writeEndObject(JsonGenerator jsonGenerator, int n) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw('}');
    }

    @Override
    public void writeObjectEntrySeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(',');
    }

    @Override
    public void writeObjectFieldValueSeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(':');
    }

    @Override
    public void writeRootValueSeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        if (this._rootValueSeparator != null) {
            jsonGenerator.writeRaw(this._rootValueSeparator);
        }
    }

    @Override
    public void writeStartArray(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw('[');
    }

    @Override
    public void writeStartObject(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw('{');
    }
}


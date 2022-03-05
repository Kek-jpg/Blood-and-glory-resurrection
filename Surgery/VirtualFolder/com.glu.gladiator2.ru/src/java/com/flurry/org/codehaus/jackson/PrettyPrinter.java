/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import java.io.IOException;

public interface PrettyPrinter {
    public void beforeArrayValues(JsonGenerator var1) throws IOException, JsonGenerationException;

    public void beforeObjectEntries(JsonGenerator var1) throws IOException, JsonGenerationException;

    public void writeArrayValueSeparator(JsonGenerator var1) throws IOException, JsonGenerationException;

    public void writeEndArray(JsonGenerator var1, int var2) throws IOException, JsonGenerationException;

    public void writeEndObject(JsonGenerator var1, int var2) throws IOException, JsonGenerationException;

    public void writeObjectEntrySeparator(JsonGenerator var1) throws IOException, JsonGenerationException;

    public void writeObjectFieldValueSeparator(JsonGenerator var1) throws IOException, JsonGenerationException;

    public void writeRootValueSeparator(JsonGenerator var1) throws IOException, JsonGenerationException;

    public void writeStartArray(JsonGenerator var1) throws IOException, JsonGenerationException;

    public void writeStartObject(JsonGenerator var1) throws IOException, JsonGenerationException;
}


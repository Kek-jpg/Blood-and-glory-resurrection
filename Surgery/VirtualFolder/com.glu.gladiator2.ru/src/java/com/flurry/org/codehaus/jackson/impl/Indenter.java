/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.impl;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import java.io.IOException;

public interface Indenter {
    public boolean isInline();

    public void writeIndentation(JsonGenerator var1, int var2) throws IOException, JsonGenerationException;
}


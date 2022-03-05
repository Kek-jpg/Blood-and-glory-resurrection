/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.flurry.org.codehaus.jackson;

import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonProcessingException;

public class JsonGenerationException
extends JsonProcessingException {
    static final long serialVersionUID = 123L;

    public JsonGenerationException(String string) {
        super(string, (JsonLocation)null);
    }

    public JsonGenerationException(String string, Throwable throwable) {
        super(string, null, throwable);
    }

    public JsonGenerationException(Throwable throwable) {
        super(throwable);
    }
}


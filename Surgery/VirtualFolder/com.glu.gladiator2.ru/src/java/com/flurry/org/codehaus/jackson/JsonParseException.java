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

public class JsonParseException
extends JsonProcessingException {
    static final long serialVersionUID = 123L;

    public JsonParseException(String string, JsonLocation jsonLocation) {
        super(string, jsonLocation);
    }

    public JsonParseException(String string, JsonLocation jsonLocation, Throwable throwable) {
        super(string, jsonLocation, throwable);
    }
}


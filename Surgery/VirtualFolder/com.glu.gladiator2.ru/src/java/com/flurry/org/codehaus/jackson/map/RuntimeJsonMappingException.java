/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.map.JsonMappingException;

public class RuntimeJsonMappingException
extends RuntimeException {
    public RuntimeJsonMappingException(JsonMappingException jsonMappingException) {
        super((Throwable)jsonMappingException);
    }

    public RuntimeJsonMappingException(String string) {
        super(string);
    }

    public RuntimeJsonMappingException(String string, JsonMappingException jsonMappingException) {
        super(string, (Throwable)jsonMappingException);
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import java.io.IOException;

public abstract class KeyDeserializer {
    public abstract Object deserializeKey(String var1, DeserializationContext var2) throws IOException, JsonProcessingException;

    public static abstract class None
    extends KeyDeserializer {
    }

}


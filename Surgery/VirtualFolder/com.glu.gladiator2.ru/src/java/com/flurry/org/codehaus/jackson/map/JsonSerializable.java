/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Deprecated
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import java.io.IOException;

@Deprecated
public interface JsonSerializable {
    public void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException;
}


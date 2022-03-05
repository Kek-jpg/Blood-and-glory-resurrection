/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializable;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import java.io.IOException;

public interface JsonSerializableWithType
extends JsonSerializable {
    public void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException, JsonProcessingException;
}


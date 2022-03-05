/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.schema;

import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import java.lang.reflect.Type;

public interface SchemaAware {
    public JsonNode getSchema(SerializerProvider var1, Type var2) throws JsonMappingException;
}


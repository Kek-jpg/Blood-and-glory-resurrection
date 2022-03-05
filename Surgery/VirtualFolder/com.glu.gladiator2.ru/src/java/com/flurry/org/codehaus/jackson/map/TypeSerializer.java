/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeIdResolver;
import java.io.IOException;

public abstract class TypeSerializer {
    public abstract String getPropertyName();

    public abstract TypeIdResolver getTypeIdResolver();

    public abstract JsonTypeInfo.As getTypeInclusion();

    public abstract void writeTypePrefixForArray(Object var1, JsonGenerator var2) throws IOException, JsonProcessingException;

    public void writeTypePrefixForArray(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException, JsonProcessingException {
        this.writeTypePrefixForArray(object, jsonGenerator);
    }

    public abstract void writeTypePrefixForObject(Object var1, JsonGenerator var2) throws IOException, JsonProcessingException;

    public void writeTypePrefixForObject(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException, JsonProcessingException {
        this.writeTypePrefixForObject(object, jsonGenerator);
    }

    public abstract void writeTypePrefixForScalar(Object var1, JsonGenerator var2) throws IOException, JsonProcessingException;

    public void writeTypePrefixForScalar(Object object, JsonGenerator jsonGenerator, Class<?> class_) throws IOException, JsonProcessingException {
        this.writeTypePrefixForScalar(object, jsonGenerator);
    }

    public abstract void writeTypeSuffixForArray(Object var1, JsonGenerator var2) throws IOException, JsonProcessingException;

    public abstract void writeTypeSuffixForObject(Object var1, JsonGenerator var2) throws IOException, JsonProcessingException;

    public abstract void writeTypeSuffixForScalar(Object var1, JsonGenerator var2) throws IOException, JsonProcessingException;
}


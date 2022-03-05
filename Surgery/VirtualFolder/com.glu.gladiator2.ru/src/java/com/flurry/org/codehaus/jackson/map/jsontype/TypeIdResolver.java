/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.jsontype;

import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.type.JavaType;

public interface TypeIdResolver {
    public JsonTypeInfo.Id getMechanism();

    public String idFromValue(Object var1);

    public String idFromValueAndType(Object var1, Class<?> var2);

    public void init(JavaType var1);

    public JavaType typeFromId(String var1);
}


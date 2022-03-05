/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.flurry.org.codehaus.jackson.map.jsontype.impl;

import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.ClassNameIdResolver;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;

public class MinimalClassNameIdResolver
extends ClassNameIdResolver {
    protected final String _basePackageName;
    protected final String _basePackagePrefix;

    protected MinimalClassNameIdResolver(JavaType javaType, TypeFactory typeFactory) {
        super(javaType, typeFactory);
        String string = javaType.getRawClass().getName();
        int n = string.lastIndexOf(46);
        if (n < 0) {
            this._basePackageName = "";
            this._basePackagePrefix = ".";
            return;
        }
        this._basePackagePrefix = string.substring(0, n + 1);
        this._basePackageName = string.substring(0, n);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.MINIMAL_CLASS;
    }

    @Override
    public String idFromValue(Object object) {
        String string = object.getClass().getName();
        if (string.startsWith(this._basePackagePrefix)) {
            string = string.substring(-1 + this._basePackagePrefix.length());
        }
        return string;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JavaType typeFromId(String string) {
        if (string.startsWith(".")) {
            StringBuilder stringBuilder = new StringBuilder(string.length() + this._basePackageName.length());
            if (this._basePackageName.length() == 0) {
                stringBuilder.append(string.substring(1));
            } else {
                stringBuilder.append(this._basePackageName).append(string);
            }
            string = stringBuilder.toString();
        }
        return super.typeFromId(string);
    }
}


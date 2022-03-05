/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.util;

import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import java.util.HashMap;

public class EnumResolver<T extends Enum<T>> {
    protected final Class<T> _enumClass;
    protected final T[] _enums;
    protected final HashMap<String, T> _enumsById;

    protected EnumResolver(Class<T> class_, T[] arrT, HashMap<String, T> hashMap) {
        this._enumClass = class_;
        this._enums = arrT;
        this._enumsById = hashMap;
    }

    public static <ET extends Enum<ET>> EnumResolver<ET> constructFor(Class<ET> class_, AnnotationIntrospector annotationIntrospector) {
        Enum[] arrenum = (Enum[])class_.getEnumConstants();
        if (arrenum == null) {
            throw new IllegalArgumentException("No enum constants for class " + class_.getName());
        }
        HashMap hashMap = new HashMap();
        int n = arrenum.length;
        for (int i = 0; i < n; ++i) {
            Enum enum_ = arrenum[i];
            hashMap.put((Object)annotationIntrospector.findEnumValue(enum_), (Object)enum_);
        }
        return new EnumResolver(class_, arrenum, hashMap);
    }

    public static EnumResolver<?> constructUnsafe(Class<?> class_, AnnotationIntrospector annotationIntrospector) {
        return EnumResolver.constructFor(class_, annotationIntrospector);
    }

    public static EnumResolver<?> constructUnsafeUsingToString(Class<?> class_) {
        return EnumResolver.constructUsingToString(class_);
    }

    public static <ET extends Enum<ET>> EnumResolver<ET> constructUsingToString(Class<ET> class_) {
        Enum[] arrenum = (Enum[])class_.getEnumConstants();
        HashMap hashMap = new HashMap();
        int n = arrenum.length;
        while (--n >= 0) {
            Enum enum_ = arrenum[n];
            hashMap.put((Object)enum_.toString(), (Object)enum_);
        }
        return new EnumResolver(class_, arrenum, hashMap);
    }

    public T findEnum(String string) {
        return (T)((Enum)this._enumsById.get((Object)string));
    }

    public T getEnum(int n) {
        if (n < 0 || n >= this._enums.length) {
            return null;
        }
        return this._enums[n];
    }

    public Class<T> getEnumClass() {
        return this._enumClass;
    }

    public int lastValidIndex() {
        return -1 + this._enums.length;
    }
}


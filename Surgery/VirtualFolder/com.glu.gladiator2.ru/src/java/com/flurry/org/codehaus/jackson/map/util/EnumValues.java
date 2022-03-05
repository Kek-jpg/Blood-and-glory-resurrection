/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Enum
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 *  java.util.EnumMap
 *  java.util.HashMap
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.util;

import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public final class EnumValues {
    private final EnumMap<?, SerializedString> _values;

    private EnumValues(Map<Enum<?>, SerializedString> map) {
        this._values = new EnumMap(map);
    }

    public static EnumValues construct(Class<Enum<?>> class_, AnnotationIntrospector annotationIntrospector) {
        return EnumValues.constructFromName(class_, annotationIntrospector);
    }

    public static EnumValues constructFromName(Class<Enum<?>> class_, AnnotationIntrospector annotationIntrospector) {
        Enum[] arrenum = (Enum[])ClassUtil.findEnumType(class_).getEnumConstants();
        if (arrenum != null) {
            HashMap hashMap = new HashMap();
            for (Enum enum_ : arrenum) {
                hashMap.put((Object)enum_, (Object)new SerializedString(annotationIntrospector.findEnumValue(enum_)));
            }
            return new EnumValues((Map<Enum<?>, SerializedString>)hashMap);
        }
        throw new IllegalArgumentException("Can not determine enum constants for Class " + class_.getName());
    }

    public static EnumValues constructFromToString(Class<Enum<?>> class_, AnnotationIntrospector annotationIntrospector) {
        Enum[] arrenum = (Enum[])ClassUtil.findEnumType(class_).getEnumConstants();
        if (arrenum != null) {
            HashMap hashMap = new HashMap();
            for (Enum enum_ : arrenum) {
                hashMap.put((Object)enum_, (Object)new SerializedString(enum_.toString()));
            }
            return new EnumValues((Map<Enum<?>, SerializedString>)hashMap);
        }
        throw new IllegalArgumentException("Can not determine enum constants for Class " + class_.getName());
    }

    public SerializedString serializedValueFor(Enum<?> enum_) {
        return (SerializedString)this._values.get(enum_);
    }

    @Deprecated
    public String valueFor(Enum<?> enum_) {
        SerializedString serializedString = (SerializedString)this._values.get(enum_);
        if (serializedString == null) {
            return null;
        }
        return serializedString.getValue();
    }

    public Collection<SerializedString> values() {
        return this._values.values();
    }
}


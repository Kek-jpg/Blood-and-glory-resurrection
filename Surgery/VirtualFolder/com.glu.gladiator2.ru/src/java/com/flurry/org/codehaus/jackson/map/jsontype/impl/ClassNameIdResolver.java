/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.ClassLoader
 *  java.lang.ClassNotFoundException
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.util.Collection
 *  java.util.EnumMap
 *  java.util.EnumSet
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.jsontype.impl;

import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.TypeIdResolverBase;
import com.flurry.org.codehaus.jackson.map.type.CollectionType;
import com.flurry.org.codehaus.jackson.map.type.MapType;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public class ClassNameIdResolver
extends TypeIdResolverBase {
    public ClassNameIdResolver(JavaType javaType, TypeFactory typeFactory) {
        super(javaType, typeFactory);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final String _idFrom(Object object, Class<?> class_) {
        String string;
        if (Enum.class.isAssignableFrom(class_) && !class_.isEnum()) {
            class_ = class_.getSuperclass();
        }
        if ((string = class_.getName()).startsWith("java.util")) {
            if (object instanceof EnumSet) {
                Class<? extends Enum<?>> class_2 = ClassUtil.findEnumType((EnumSet)object);
                return TypeFactory.defaultInstance().constructCollectionType(EnumSet.class, class_2).toCanonical();
            }
            if (object instanceof EnumMap) {
                Class<? extends Enum<?>> class_3 = ClassUtil.findEnumType((EnumMap)object);
                return TypeFactory.defaultInstance().constructMapType(EnumMap.class, class_3, Object.class).toCanonical();
            }
            String string2 = string.substring(9);
            if (!string2.startsWith(".Arrays$")) {
                if (!string2.startsWith(".Collections$")) return string;
            }
            if (string.indexOf("List") < 0) return string;
            return "java.util.ArrayList";
        }
        if (string.indexOf(36) < 0) return string;
        if (ClassUtil.getOuterClass(class_) == null) return string;
        if (ClassUtil.getOuterClass(this._baseType.getRawClass()) != null) return string;
        return this._baseType.getRawClass().getName();
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CLASS;
    }

    @Override
    public String idFromValue(Object object) {
        return this._idFrom(object, object.getClass());
    }

    @Override
    public String idFromValueAndType(Object object, Class<?> class_) {
        return this._idFrom(object, class_);
    }

    public void registerSubtype(Class<?> class_, String string) {
    }

    @Override
    public JavaType typeFromId(String string) {
        if (string.indexOf(60) > 0) {
            return TypeFactory.fromCanonical(string);
        }
        try {
            Class class_ = Class.forName((String)string, (boolean)true, (ClassLoader)Thread.currentThread().getContextClassLoader());
            JavaType javaType = this._typeFactory.constructSpecializedType(this._baseType, class_);
            return javaType;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new IllegalArgumentException("Invalid type id '" + string + "' (for id type 'Id.class'): no such class found");
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("Invalid type id '" + string + "' (for id type 'Id.class'): " + exception.getMessage(), (Throwable)exception);
        }
    }
}


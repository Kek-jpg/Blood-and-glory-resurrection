/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.type.TypeBase
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.type;

import com.flurry.org.codehaus.jackson.map.type.TypeBase;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.Map;

public class MapLikeType
extends TypeBase {
    protected final JavaType _keyType;
    protected final JavaType _valueType;

    @Deprecated
    protected MapLikeType(Class<?> class_, JavaType javaType, JavaType javaType2) {
        super(class_, javaType.hashCode() ^ javaType2.hashCode(), null, null);
        this._keyType = javaType;
        this._valueType = javaType2;
    }

    protected MapLikeType(Class<?> class_, JavaType javaType, JavaType javaType2, Object object, Object object2) {
        super(class_, javaType.hashCode() ^ javaType2.hashCode(), object, object2);
        this._keyType = javaType;
        this._valueType = javaType2;
    }

    public static MapLikeType construct(Class<?> class_, JavaType javaType, JavaType javaType2) {
        return new MapLikeType(class_, javaType, javaType2, null, null);
    }

    protected JavaType _narrow(Class<?> class_) {
        return new MapLikeType(class_, this._keyType, this._valueType, this._valueHandler, this._typeHandler);
    }

    protected String buildCanonicalName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._class.getName());
        if (this._keyType != null) {
            stringBuilder.append('<');
            stringBuilder.append(this._keyType.toCanonical());
            stringBuilder.append(',');
            stringBuilder.append(this._valueType.toCanonical());
            stringBuilder.append('>');
        }
        return stringBuilder.toString();
    }

    public JavaType containedType(int n) {
        if (n == 0) {
            return this._keyType;
        }
        if (n == 1) {
            return this._valueType;
        }
        return null;
    }

    public int containedTypeCount() {
        return 2;
    }

    public String containedTypeName(int n) {
        if (n == 0) {
            return "K";
        }
        if (n == 1) {
            return "V";
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6 : {
            block5 : {
                if (object == this) break block5;
                if (object == null) {
                    return false;
                }
                if (object.getClass() != this.getClass()) {
                    return false;
                }
                MapLikeType mapLikeType = (MapLikeType)((Object)object);
                if (this._class != mapLikeType._class || !this._keyType.equals(mapLikeType._keyType) || !this._valueType.equals(mapLikeType._valueType)) break block6;
            }
            return true;
        }
        return false;
    }

    public JavaType getContentType() {
        return this._valueType;
    }

    public StringBuilder getErasedSignature(StringBuilder stringBuilder) {
        return MapLikeType._classSignature((Class)this._class, (StringBuilder)stringBuilder, (boolean)true);
    }

    public StringBuilder getGenericSignature(StringBuilder stringBuilder) {
        MapLikeType._classSignature((Class)this._class, (StringBuilder)stringBuilder, (boolean)false);
        stringBuilder.append('<');
        this._keyType.getGenericSignature(stringBuilder);
        this._valueType.getGenericSignature(stringBuilder);
        stringBuilder.append(">;");
        return stringBuilder;
    }

    public JavaType getKeyType() {
        return this._keyType;
    }

    public boolean isContainerType() {
        return true;
    }

    public boolean isMapLikeType() {
        return true;
    }

    public boolean isTrueMapType() {
        return Map.class.isAssignableFrom(this._class);
    }

    public JavaType narrowContentsBy(Class<?> class_) {
        if (class_ == this._valueType.getRawClass()) {
            return this;
        }
        return new MapLikeType(this._class, this._keyType, this._valueType.narrowBy(class_), this._valueHandler, this._typeHandler);
    }

    public JavaType narrowKey(Class<?> class_) {
        if (class_ == this._keyType.getRawClass()) {
            return this;
        }
        return new MapLikeType(this._class, this._keyType.narrowBy(class_), this._valueType, this._valueHandler, this._typeHandler);
    }

    public String toString() {
        return "[map-like type; class " + this._class.getName() + ", " + this._keyType + " -> " + this._valueType + "]";
    }

    public JavaType widenContentsBy(Class<?> class_) {
        if (class_ == this._valueType.getRawClass()) {
            return this;
        }
        return new MapLikeType(this._class, this._keyType, this._valueType.widenBy(class_), this._valueHandler, this._typeHandler);
    }

    public JavaType widenKey(Class<?> class_) {
        if (class_ == this._keyType.getRawClass()) {
            return this;
        }
        return new MapLikeType(this._class, this._keyType.widenBy(class_), this._valueType, this._valueHandler, this._typeHandler);
    }

    public MapLikeType withContentTypeHandler(Object object) {
        return new MapLikeType(this._class, this._keyType, this._valueType.withTypeHandler(object), this._valueHandler, this._typeHandler);
    }

    public MapLikeType withContentValueHandler(Object object) {
        return new MapLikeType(this._class, this._keyType, this._valueType.withValueHandler(object), this._valueHandler, this._typeHandler);
    }

    public MapLikeType withKeyTypeHandler(Object object) {
        return new MapLikeType(this._class, this._keyType.withTypeHandler(object), this._valueType, this._valueHandler, this._typeHandler);
    }

    public MapLikeType withKeyValueHandler(Object object) {
        return new MapLikeType(this._class, this._keyType.withValueHandler(object), this._valueType, this._valueHandler, this._typeHandler);
    }

    public MapLikeType withTypeHandler(Object object) {
        return new MapLikeType(this._class, this._keyType, this._valueType, this._valueHandler, object);
    }

    public MapLikeType withValueHandler(Object object) {
        return new MapLikeType(this._class, this._keyType, this._valueType, object, this._typeHandler);
    }
}


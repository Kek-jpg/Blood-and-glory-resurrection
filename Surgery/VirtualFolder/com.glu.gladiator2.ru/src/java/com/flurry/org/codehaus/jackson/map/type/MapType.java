/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.type;

import com.flurry.org.codehaus.jackson.map.type.MapLikeType;
import com.flurry.org.codehaus.jackson.type.JavaType;

public final class MapType
extends MapLikeType {
    @Deprecated
    private MapType(Class<?> class_, JavaType javaType, JavaType javaType2) {
        super(class_, javaType, javaType2, null, null);
    }

    private MapType(Class<?> class_, JavaType javaType, JavaType javaType2, Object object, Object object2) {
        super(class_, javaType, javaType2, object, object2);
    }

    public static MapType construct(Class<?> class_, JavaType javaType, JavaType javaType2) {
        return new MapType(class_, javaType, javaType2, null, null);
    }

    @Override
    protected JavaType _narrow(Class<?> class_) {
        return new MapType(class_, this._keyType, this._valueType, this._valueHandler, this._typeHandler);
    }

    @Override
    public JavaType narrowContentsBy(Class<?> class_) {
        if (class_ == this._valueType.getRawClass()) {
            return this;
        }
        return new MapType(this._class, this._keyType, this._valueType.narrowBy(class_), this._valueHandler, this._typeHandler);
    }

    @Override
    public JavaType narrowKey(Class<?> class_) {
        if (class_ == this._keyType.getRawClass()) {
            return this;
        }
        return new MapType(this._class, this._keyType.narrowBy(class_), this._valueType, this._valueHandler, this._typeHandler);
    }

    @Override
    public String toString() {
        return "[map type; class " + this._class.getName() + ", " + this._keyType + " -> " + this._valueType + "]";
    }

    @Override
    public JavaType widenContentsBy(Class<?> class_) {
        if (class_ == this._valueType.getRawClass()) {
            return this;
        }
        return new MapType(this._class, this._keyType, this._valueType.widenBy(class_), this._valueHandler, this._typeHandler);
    }

    @Override
    public JavaType widenKey(Class<?> class_) {
        if (class_ == this._keyType.getRawClass()) {
            return this;
        }
        return new MapType(this._class, this._keyType.widenBy(class_), this._valueType, this._valueHandler, this._typeHandler);
    }

    @Override
    public MapType withContentTypeHandler(Object object) {
        return new MapType(this._class, this._keyType, this._valueType.withTypeHandler(object), this._valueHandler, this._typeHandler);
    }

    @Override
    public MapType withContentValueHandler(Object object) {
        return new MapType(this._class, this._keyType, this._valueType.withValueHandler(object), this._valueHandler, this._typeHandler);
    }

    @Override
    public MapType withKeyTypeHandler(Object object) {
        return new MapType(this._class, this._keyType.withTypeHandler(object), this._valueType, this._valueHandler, this._typeHandler);
    }

    @Override
    public MapType withKeyValueHandler(Object object) {
        return new MapType(this._class, this._keyType.withValueHandler(object), this._valueType, this._valueHandler, this._typeHandler);
    }

    @Override
    public MapType withTypeHandler(Object object) {
        return new MapType(this._class, this._keyType, this._valueType, this._valueHandler, object);
    }

    @Override
    public MapType withValueHandler(Object object) {
        return new MapType(this._class, this._keyType, this._valueType, object, this._typeHandler);
    }
}


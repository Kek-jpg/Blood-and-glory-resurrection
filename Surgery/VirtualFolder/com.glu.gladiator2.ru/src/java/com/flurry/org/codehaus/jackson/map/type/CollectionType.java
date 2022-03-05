/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.type;

import com.flurry.org.codehaus.jackson.map.type.CollectionLikeType;
import com.flurry.org.codehaus.jackson.type.JavaType;

public final class CollectionType
extends CollectionLikeType {
    private CollectionType(Class<?> class_, JavaType javaType, Object object, Object object2) {
        super(class_, javaType, object, object2);
    }

    public static CollectionType construct(Class<?> class_, JavaType javaType) {
        return new CollectionType(class_, javaType, null, null);
    }

    @Override
    protected JavaType _narrow(Class<?> class_) {
        return new CollectionType(class_, this._elementType, null, null);
    }

    @Override
    public JavaType narrowContentsBy(Class<?> class_) {
        if (class_ == this._elementType.getRawClass()) {
            return this;
        }
        return new CollectionType(this._class, this._elementType.narrowBy(class_), this._valueHandler, this._typeHandler);
    }

    @Override
    public String toString() {
        return "[collection type; class " + this._class.getName() + ", contains " + this._elementType + "]";
    }

    @Override
    public JavaType widenContentsBy(Class<?> class_) {
        if (class_ == this._elementType.getRawClass()) {
            return this;
        }
        return new CollectionType(this._class, this._elementType.widenBy(class_), this._valueHandler, this._typeHandler);
    }

    @Override
    public CollectionType withContentTypeHandler(Object object) {
        return new CollectionType(this._class, this._elementType.withTypeHandler(object), this._valueHandler, this._typeHandler);
    }

    @Override
    public CollectionType withContentValueHandler(Object object) {
        return new CollectionType(this._class, this._elementType.withValueHandler(object), this._valueHandler, this._typeHandler);
    }

    @Override
    public CollectionType withTypeHandler(Object object) {
        return new CollectionType(this._class, this._elementType, this._valueHandler, object);
    }

    @Override
    public CollectionType withValueHandler(Object object) {
        return new CollectionType(this._class, this._elementType, object, this._typeHandler);
    }
}


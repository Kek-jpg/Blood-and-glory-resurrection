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
 *  java.util.Collection
 */
package com.flurry.org.codehaus.jackson.map.type;

import com.flurry.org.codehaus.jackson.map.type.TypeBase;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.Collection;

public class CollectionLikeType
extends TypeBase {
    protected final JavaType _elementType;

    @Deprecated
    protected CollectionLikeType(Class<?> class_, JavaType javaType) {
        super(class_, javaType.hashCode(), null, null);
        this._elementType = javaType;
    }

    protected CollectionLikeType(Class<?> class_, JavaType javaType, Object object, Object object2) {
        super(class_, javaType.hashCode(), object, object2);
        this._elementType = javaType;
    }

    public static CollectionLikeType construct(Class<?> class_, JavaType javaType) {
        return new CollectionLikeType(class_, javaType, null, null);
    }

    protected JavaType _narrow(Class<?> class_) {
        return new CollectionLikeType(class_, this._elementType, this._valueHandler, this._typeHandler);
    }

    protected String buildCanonicalName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._class.getName());
        if (this._elementType != null) {
            stringBuilder.append('<');
            stringBuilder.append(this._elementType.toCanonical());
            stringBuilder.append('>');
        }
        return stringBuilder.toString();
    }

    public JavaType containedType(int n) {
        if (n == 0) {
            return this._elementType;
        }
        return null;
    }

    public int containedTypeCount() {
        return 1;
    }

    public String containedTypeName(int n) {
        if (n == 0) {
            return "E";
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
                CollectionLikeType collectionLikeType = (CollectionLikeType)((Object)object);
                if (this._class != collectionLikeType._class || !this._elementType.equals(collectionLikeType._elementType)) break block6;
            }
            return true;
        }
        return false;
    }

    public JavaType getContentType() {
        return this._elementType;
    }

    public StringBuilder getErasedSignature(StringBuilder stringBuilder) {
        return CollectionLikeType._classSignature((Class)this._class, (StringBuilder)stringBuilder, (boolean)true);
    }

    public StringBuilder getGenericSignature(StringBuilder stringBuilder) {
        CollectionLikeType._classSignature((Class)this._class, (StringBuilder)stringBuilder, (boolean)false);
        stringBuilder.append('<');
        this._elementType.getGenericSignature(stringBuilder);
        stringBuilder.append(">;");
        return stringBuilder;
    }

    public boolean isCollectionLikeType() {
        return true;
    }

    public boolean isContainerType() {
        return true;
    }

    public boolean isTrueCollectionType() {
        return Collection.class.isAssignableFrom(this._class);
    }

    public JavaType narrowContentsBy(Class<?> class_) {
        if (class_ == this._elementType.getRawClass()) {
            return this;
        }
        return new CollectionLikeType(this._class, this._elementType.narrowBy(class_), this._valueHandler, this._typeHandler);
    }

    public String toString() {
        return "[collection-like type; class " + this._class.getName() + ", contains " + this._elementType + "]";
    }

    public JavaType widenContentsBy(Class<?> class_) {
        if (class_ == this._elementType.getRawClass()) {
            return this;
        }
        return new CollectionLikeType(this._class, this._elementType.widenBy(class_), this._valueHandler, this._typeHandler);
    }

    public CollectionLikeType withContentTypeHandler(Object object) {
        return new CollectionLikeType(this._class, this._elementType.withTypeHandler(object), this._valueHandler, this._typeHandler);
    }

    public CollectionLikeType withContentValueHandler(Object object) {
        return new CollectionLikeType(this._class, this._elementType.withValueHandler(object), this._valueHandler, this._typeHandler);
    }

    public CollectionLikeType withTypeHandler(Object object) {
        return new CollectionLikeType(this._class, this._elementType, this._valueHandler, object);
    }

    public CollectionLikeType withValueHandler(Object object) {
        return new CollectionLikeType(this._class, this._elementType, object, this._typeHandler);
    }
}


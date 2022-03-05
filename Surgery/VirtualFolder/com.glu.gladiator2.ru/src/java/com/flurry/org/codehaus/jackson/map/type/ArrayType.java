/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.type.TypeBase
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.reflect.Array
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.map.type;

import com.flurry.org.codehaus.jackson.map.type.TypeBase;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.Array;
import java.lang.reflect.Type;

public final class ArrayType
extends TypeBase {
    protected final JavaType _componentType;
    protected final Object _emptyArray;

    private ArrayType(JavaType javaType, Object object, Object object2, Object object3) {
        super(object.getClass(), javaType.hashCode(), object2, object3);
        this._componentType = javaType;
        this._emptyArray = object;
    }

    @Deprecated
    public static ArrayType construct(JavaType javaType) {
        return ArrayType.construct(javaType, null, null);
    }

    public static ArrayType construct(JavaType javaType, Object object, Object object2) {
        return new ArrayType(javaType, Array.newInstance(javaType.getRawClass(), (int)0), null, null);
    }

    protected JavaType _narrow(Class<?> class_) {
        if (!class_.isArray()) {
            throw new IllegalArgumentException("Incompatible narrowing operation: trying to narrow " + this.toString() + " to class " + class_.getName());
        }
        Class class_2 = class_.getComponentType();
        return ArrayType.construct(TypeFactory.defaultInstance().constructType((Type)class_2), this._valueHandler, this._typeHandler);
    }

    protected String buildCanonicalName() {
        return this._class.getName();
    }

    public JavaType containedType(int n) {
        if (n == 0) {
            return this._componentType;
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
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        boolean bl = false;
        if (object == null) return bl;
        Class class_ = object.getClass();
        Class class_2 = this.getClass();
        bl = false;
        if (class_ != class_2) return bl;
        ArrayType arrayType = (ArrayType)((Object)object);
        return this._componentType.equals(arrayType._componentType);
    }

    public JavaType getContentType() {
        return this._componentType;
    }

    public StringBuilder getErasedSignature(StringBuilder stringBuilder) {
        stringBuilder.append('[');
        return this._componentType.getErasedSignature(stringBuilder);
    }

    public StringBuilder getGenericSignature(StringBuilder stringBuilder) {
        stringBuilder.append('[');
        return this._componentType.getGenericSignature(stringBuilder);
    }

    public boolean hasGenericTypes() {
        return this._componentType.hasGenericTypes();
    }

    public boolean isAbstract() {
        return false;
    }

    public boolean isArrayType() {
        return true;
    }

    public boolean isConcrete() {
        return true;
    }

    public boolean isContainerType() {
        return true;
    }

    public JavaType narrowContentsBy(Class<?> class_) {
        if (class_ == this._componentType.getRawClass()) {
            return this;
        }
        return ArrayType.construct(this._componentType.narrowBy(class_), this._valueHandler, this._typeHandler);
    }

    public String toString() {
        return "[array type, component type: " + this._componentType + "]";
    }

    public JavaType widenContentsBy(Class<?> class_) {
        if (class_ == this._componentType.getRawClass()) {
            return this;
        }
        return ArrayType.construct(this._componentType.widenBy(class_), this._valueHandler, this._typeHandler);
    }

    public ArrayType withContentTypeHandler(Object object) {
        if (object == this._componentType.getTypeHandler()) {
            return this;
        }
        return new ArrayType(this._componentType.withTypeHandler(object), this._emptyArray, this._valueHandler, this._typeHandler);
    }

    public ArrayType withContentValueHandler(Object object) {
        if (object == this._componentType.getValueHandler()) {
            return this;
        }
        return new ArrayType(this._componentType.withValueHandler(object), this._emptyArray, this._valueHandler, this._typeHandler);
    }

    public ArrayType withTypeHandler(Object object) {
        if (object == this._typeHandler) {
            return this;
        }
        return new ArrayType(this._componentType, this._emptyArray, this._valueHandler, object);
    }

    public ArrayType withValueHandler(Object object) {
        if (object == this._valueHandler) {
            return this;
        }
        return new ArrayType(this._componentType, this._emptyArray, object, this._typeHandler);
    }
}


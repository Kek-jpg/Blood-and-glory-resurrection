/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.lang.reflect.Modifier
 */
package com.flurry.org.codehaus.jackson.type;

import java.lang.reflect.Modifier;

public abstract class JavaType {
    protected final Class<?> _class;
    protected final int _hashCode;
    protected Object _typeHandler;
    protected Object _valueHandler;

    protected JavaType(Class<?> class_, int n) {
        this._class = class_;
        this._hashCode = n + class_.getName().hashCode();
        this._valueHandler = null;
        this._typeHandler = null;
    }

    protected void _assertSubclass(Class<?> class_, Class<?> class_2) {
        if (!this._class.isAssignableFrom(class_)) {
            throw new IllegalArgumentException("Class " + class_.getName() + " is not assignable to " + this._class.getName());
        }
    }

    protected abstract JavaType _narrow(Class<?> var1);

    protected JavaType _widen(Class<?> class_) {
        return this._narrow(class_);
    }

    public JavaType containedType(int n) {
        return null;
    }

    public int containedTypeCount() {
        return 0;
    }

    public String containedTypeName(int n) {
        return null;
    }

    public abstract boolean equals(Object var1);

    public JavaType forcedNarrowBy(Class<?> class_) {
        if (class_ == this._class) {
            return this;
        }
        JavaType javaType = this._narrow(class_);
        if (this._valueHandler != javaType.getValueHandler()) {
            javaType = javaType.withValueHandler(this._valueHandler);
        }
        if (this._typeHandler != javaType.getTypeHandler()) {
            javaType = javaType.withTypeHandler(this._typeHandler);
        }
        return javaType;
    }

    public JavaType getContentType() {
        return null;
    }

    public String getErasedSignature() {
        StringBuilder stringBuilder = new StringBuilder(40);
        this.getErasedSignature(stringBuilder);
        return stringBuilder.toString();
    }

    public abstract StringBuilder getErasedSignature(StringBuilder var1);

    public String getGenericSignature() {
        StringBuilder stringBuilder = new StringBuilder(40);
        this.getGenericSignature(stringBuilder);
        return stringBuilder.toString();
    }

    public abstract StringBuilder getGenericSignature(StringBuilder var1);

    public JavaType getKeyType() {
        return null;
    }

    public final Class<?> getRawClass() {
        return this._class;
    }

    public <T> T getTypeHandler() {
        return (T)this._typeHandler;
    }

    public <T> T getValueHandler() {
        return (T)this._valueHandler;
    }

    public boolean hasGenericTypes() {
        return this.containedTypeCount() > 0;
    }

    public final boolean hasRawClass(Class<?> class_) {
        return this._class == class_;
    }

    public final int hashCode() {
        return this._hashCode;
    }

    public boolean isAbstract() {
        return Modifier.isAbstract((int)this._class.getModifiers());
    }

    public boolean isArrayType() {
        return false;
    }

    public boolean isCollectionLikeType() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isConcrete() {
        return (1536 & this._class.getModifiers()) == 0 || this._class.isPrimitive();
    }

    public abstract boolean isContainerType();

    public final boolean isEnumType() {
        return this._class.isEnum();
    }

    public final boolean isFinal() {
        return Modifier.isFinal((int)this._class.getModifiers());
    }

    public final boolean isInterface() {
        return this._class.isInterface();
    }

    public boolean isMapLikeType() {
        return false;
    }

    public final boolean isPrimitive() {
        return this._class.isPrimitive();
    }

    public boolean isThrowable() {
        return Throwable.class.isAssignableFrom(this._class);
    }

    public JavaType narrowBy(Class<?> class_) {
        if (class_ == this._class) {
            return this;
        }
        this._assertSubclass(class_, this._class);
        JavaType javaType = this._narrow(class_);
        if (this._valueHandler != javaType.getValueHandler()) {
            javaType = javaType.withValueHandler(this._valueHandler);
        }
        if (this._typeHandler != javaType.getTypeHandler()) {
            javaType = javaType.withTypeHandler(this._typeHandler);
        }
        return javaType;
    }

    public abstract JavaType narrowContentsBy(Class<?> var1);

    @Deprecated
    public void setValueHandler(Object object) {
        if (object != null && this._valueHandler != null) {
            throw new IllegalStateException("Trying to reset value handler for type [" + this.toString() + "]; old handler of type " + this._valueHandler.getClass().getName() + ", new handler of type " + object.getClass().getName());
        }
        this._valueHandler = object;
    }

    public abstract String toCanonical();

    public abstract String toString();

    public JavaType widenBy(Class<?> class_) {
        if (class_ == this._class) {
            return this;
        }
        this._assertSubclass(this._class, class_);
        return this._widen(class_);
    }

    public abstract JavaType widenContentsBy(Class<?> var1);

    public abstract JavaType withContentTypeHandler(Object var1);

    public JavaType withContentValueHandler(Object object) {
        this.getContentType().setValueHandler(object);
        return this;
    }

    public abstract JavaType withTypeHandler(Object var1);

    public JavaType withValueHandler(Object object) {
        this.setValueHandler(object);
        return this;
    }
}


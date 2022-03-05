/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Collection
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.type;

import com.flurry.org.codehaus.jackson.map.type.TypeBase;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.Collection;
import java.util.Map;

public final class SimpleType
extends TypeBase {
    protected final String[] _typeNames;
    protected final JavaType[] _typeParameters;

    protected SimpleType(Class<?> class_) {
        super(class_, null, null, null, null);
    }

    @Deprecated
    protected SimpleType(Class<?> class_, String[] arrstring, JavaType[] arrjavaType) {
        super(class_, arrstring, arrjavaType, null, null);
    }

    protected SimpleType(Class<?> class_, String[] arrstring, JavaType[] arrjavaType, Object object, Object object2) {
        super(class_, 0, object, object2);
        if (arrstring == null || arrstring.length == 0) {
            this._typeNames = null;
            this._typeParameters = null;
            return;
        }
        this._typeNames = arrstring;
        this._typeParameters = arrjavaType;
    }

    public static SimpleType construct(Class<?> class_) {
        if (Map.class.isAssignableFrom(class_)) {
            throw new IllegalArgumentException("Can not construct SimpleType for a Map (class: " + class_.getName() + ")");
        }
        if (Collection.class.isAssignableFrom(class_)) {
            throw new IllegalArgumentException("Can not construct SimpleType for a Collection (class: " + class_.getName() + ")");
        }
        if (class_.isArray()) {
            throw new IllegalArgumentException("Can not construct SimpleType for an array (class: " + class_.getName() + ")");
        }
        return new SimpleType(class_);
    }

    public static SimpleType constructUnsafe(Class<?> class_) {
        return new SimpleType(class_, null, null, null, null);
    }

    @Override
    protected JavaType _narrow(Class<?> class_) {
        return new SimpleType(class_, this._typeNames, this._typeParameters, this._valueHandler, this._typeHandler);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected String buildCanonicalName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._class.getName());
        if (this._typeParameters != null && this._typeParameters.length > 0) {
            stringBuilder.append('<');
            boolean bl = true;
            for (JavaType javaType : this._typeParameters) {
                if (bl) {
                    bl = false;
                } else {
                    stringBuilder.append(',');
                }
                stringBuilder.append(javaType.toCanonical());
            }
            stringBuilder.append('>');
        }
        return stringBuilder.toString();
    }

    @Override
    public JavaType containedType(int n) {
        if (n < 0 || this._typeParameters == null || n >= this._typeParameters.length) {
            return null;
        }
        return this._typeParameters[n];
    }

    @Override
    public int containedTypeCount() {
        if (this._typeParameters == null) {
            return 0;
        }
        return this._typeParameters.length;
    }

    @Override
    public String containedTypeName(int n) {
        if (n < 0 || this._typeNames == null || n >= this._typeNames.length) {
            return null;
        }
        return this._typeNames[n];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
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
        SimpleType simpleType = (SimpleType)object;
        Class class_3 = simpleType._class;
        Class class_4 = this._class;
        bl = false;
        if (class_3 != class_4) return bl;
        JavaType[] arrjavaType = this._typeParameters;
        JavaType[] arrjavaType2 = simpleType._typeParameters;
        if (arrjavaType == null) {
            if (arrjavaType2 == null) return true;
            int n = arrjavaType2.length;
            bl = false;
            if (n != 0) return bl;
            return true;
        }
        bl = false;
        if (arrjavaType2 == null) return bl;
        int n = arrjavaType.length;
        int n2 = arrjavaType2.length;
        bl = false;
        if (n != n2) return bl;
        int n3 = 0;
        int n4 = arrjavaType.length;
        while (n3 < n4) {
            boolean bl2 = arrjavaType[n3].equals(arrjavaType2[n3]);
            bl = false;
            if (!bl2) return bl;
            ++n3;
        }
        return true;
    }

    @Override
    public StringBuilder getErasedSignature(StringBuilder stringBuilder) {
        return SimpleType._classSignature(this._class, stringBuilder, true);
    }

    @Override
    public StringBuilder getGenericSignature(StringBuilder stringBuilder) {
        SimpleType._classSignature(this._class, stringBuilder, false);
        if (this._typeParameters != null) {
            stringBuilder.append('<');
            JavaType[] arrjavaType = this._typeParameters;
            int n = arrjavaType.length;
            for (int i = 0; i < n; ++i) {
                stringBuilder = arrjavaType[i].getGenericSignature(stringBuilder);
            }
            stringBuilder.append('>');
        }
        stringBuilder.append(';');
        return stringBuilder;
    }

    @Override
    public boolean isContainerType() {
        return false;
    }

    @Override
    public JavaType narrowContentsBy(Class<?> class_) {
        throw new IllegalArgumentException("Internal error: SimpleType.narrowContentsBy() should never be called");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(40);
        stringBuilder.append("[simple type, class ").append(this.buildCanonicalName()).append(']');
        return stringBuilder.toString();
    }

    @Override
    public JavaType widenContentsBy(Class<?> class_) {
        throw new IllegalArgumentException("Internal error: SimpleType.widenContentsBy() should never be called");
    }

    @Override
    public JavaType withContentTypeHandler(Object object) {
        throw new IllegalArgumentException("Simple types have no content types; can not call withContenTypeHandler()");
    }

    @Override
    public SimpleType withContentValueHandler(Object object) {
        throw new IllegalArgumentException("Simple types have no content types; can not call withContenValueHandler()");
    }

    @Override
    public SimpleType withTypeHandler(Object object) {
        return new SimpleType(this._class, this._typeNames, this._typeParameters, this._valueHandler, object);
    }

    @Override
    public SimpleType withValueHandler(Object object) {
        if (object == this._valueHandler) {
            return this;
        }
        return new SimpleType(this._class, this._typeNames, this._typeParameters, object, this._typeHandler);
    }
}


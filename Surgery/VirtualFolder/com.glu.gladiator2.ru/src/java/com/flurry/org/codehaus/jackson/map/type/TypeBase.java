/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Byte
 *  java.lang.Character
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Void
 */
package com.flurry.org.codehaus.jackson.map.type;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializableWithType;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;

public abstract class TypeBase
extends JavaType
implements JsonSerializableWithType {
    volatile String _canonicalName;

    @Deprecated
    protected TypeBase(Class<?> class_, int n) {
        super(class_, n);
    }

    protected TypeBase(Class<?> class_, int n, Object object, Object object2) {
        super(class_, n);
        this._valueHandler = object;
        this._typeHandler = object2;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected static StringBuilder _classSignature(Class<?> class_, StringBuilder stringBuilder, boolean bl) {
        if (class_.isPrimitive()) {
            if (class_ == Boolean.TYPE) {
                stringBuilder.append('Z');
                return stringBuilder;
            }
            if (class_ == Byte.TYPE) {
                stringBuilder.append('B');
                return stringBuilder;
            }
            if (class_ == Short.TYPE) {
                stringBuilder.append('S');
                return stringBuilder;
            }
            if (class_ == Character.TYPE) {
                stringBuilder.append('C');
                return stringBuilder;
            }
            if (class_ == Integer.TYPE) {
                stringBuilder.append('I');
                return stringBuilder;
            }
            if (class_ == Long.TYPE) {
                stringBuilder.append('J');
                return stringBuilder;
            }
            if (class_ == Float.TYPE) {
                stringBuilder.append('F');
                return stringBuilder;
            }
            if (class_ == Double.TYPE) {
                stringBuilder.append('D');
                return stringBuilder;
            }
            if (class_ != Void.TYPE) throw new IllegalStateException("Unrecognized primitive type: " + class_.getName());
            stringBuilder.append('V');
            return stringBuilder;
        }
        stringBuilder.append('L');
        String string = class_.getName();
        int n = 0;
        int n2 = string.length();
        do {
            if (n >= n2) {
                if (!bl) return stringBuilder;
                stringBuilder.append(';');
                return stringBuilder;
            }
            char c = string.charAt(n);
            if (c == '.') {
                c = '/';
            }
            stringBuilder.append(c);
            ++n;
        } while (true);
    }

    protected abstract String buildCanonicalName();

    @Override
    public abstract StringBuilder getErasedSignature(StringBuilder var1);

    @Override
    public abstract StringBuilder getGenericSignature(StringBuilder var1);

    @Override
    public <T> T getTypeHandler() {
        return (T)this._typeHandler;
    }

    @Override
    public <T> T getValueHandler() {
        return (T)this._valueHandler;
    }

    @Override
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(this.toCanonical());
    }

    @Override
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        typeSerializer.writeTypePrefixForScalar(this, jsonGenerator);
        this.serialize(jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForScalar(this, jsonGenerator);
    }

    @Override
    public String toCanonical() {
        String string = this._canonicalName;
        if (string == null) {
            string = this.buildCanonicalName();
        }
        return string;
    }
}


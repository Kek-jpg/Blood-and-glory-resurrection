/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Byte
 *  java.lang.Class
 *  java.lang.IllegalAccessException
 *  java.lang.InstantiationException
 *  java.lang.Integer
 *  java.lang.NoSuchMethodException
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Array
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.InvocationTargetException
 *  java.lang.reflect.Type
 *  java.nio.ByteBuffer
 *  java.util.ArrayList
 *  java.util.Collection
 */
package com.flurry.org.apache.avro.reflect;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.Decoder;
import com.flurry.org.apache.avro.reflect.ReflectData;
import com.flurry.org.apache.avro.specific.SpecificData;
import com.flurry.org.apache.avro.specific.SpecificDatumReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

public class ReflectDatumReader<T>
extends SpecificDatumReader<T> {
    public ReflectDatumReader() {
        this(null, null, ReflectData.get());
    }

    public ReflectDatumReader(Schema schema) {
        super(schema, schema, ReflectData.get());
    }

    public ReflectDatumReader(Schema schema, Schema schema2) {
        super(schema, schema2, ReflectData.get());
    }

    protected ReflectDatumReader(Schema schema, Schema schema2, ReflectData reflectData) {
        super(schema, schema2, reflectData);
    }

    public ReflectDatumReader(Class<T> class_) {
        super(ReflectData.get().getSchema((Type)class_));
    }

    @Override
    protected void addToArray(Object object, long l2, Object object2) {
        if (object instanceof Collection) {
            ((Collection)object).add(object2);
            return;
        }
        Array.set((Object)object, (int)((int)l2), (Object)object2);
    }

    @Override
    protected Object createString(String string) {
        return string;
    }

    @Override
    protected Object newArray(Object object, int n2, Schema schema) {
        ReflectData reflectData = ReflectData.get();
        Class class_ = ReflectData.getClassProp(schema, "java-class");
        if (class_ != null) {
            if (object instanceof Collection) {
                ((Collection)object).clear();
                return object;
            }
            if (class_.isAssignableFrom(ArrayList.class)) {
                return new ArrayList();
            }
            return ReflectData.newInstance(class_, schema);
        }
        Class class_2 = ReflectData.getClassProp(schema, "java-element-class");
        if (class_2 == null) {
            class_2 = reflectData.getClass(schema.getElementType());
        }
        return Array.newInstance((Class)class_2, (int)n2);
    }

    @Override
    protected Object peekArray(Object object) {
        return null;
    }

    @Override
    protected Object readBytes(Object object, Decoder decoder) throws IOException {
        ByteBuffer byteBuffer = decoder.readBytes(null);
        byte[] arrby = new byte[byteBuffer.remaining()];
        byteBuffer.get(arrby);
        return arrby;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected Object readInt(Object object, Schema schema, Decoder decoder) throws IOException {
        Integer n2 = decoder.readInt();
        String string = schema.getProp("java-class");
        if (Byte.class.getName().equals((Object)string)) {
            return n2.byteValue();
        }
        if (!Short.class.getName().equals((Object)string)) return n2;
        return n2.shortValue();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected Object readString(Object object, Schema schema, Decoder decoder) throws IOException {
        String object2 = (String)this.readString(null, decoder);
        Class class_ = ReflectData.getClassProp(schema, "java-class");
        if (class_ == null) return object2;
        try {
            Object object3 = class_.getConstructor(new Class[]{String.class}).newInstance(new Object[]{object2});
            return object3;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new AvroRuntimeException(noSuchMethodException);
        }
        catch (InstantiationException instantiationException) {
            throw new AvroRuntimeException(instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new AvroRuntimeException(illegalAccessException);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new AvroRuntimeException(invocationTargetException);
        }
    }

    @Override
    protected Object readString(Object object, Decoder decoder) throws IOException {
        return super.readString(null, decoder).toString();
    }
}


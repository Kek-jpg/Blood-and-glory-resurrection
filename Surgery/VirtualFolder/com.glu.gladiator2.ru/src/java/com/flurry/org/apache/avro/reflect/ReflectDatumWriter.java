/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Byte
 *  java.lang.Class
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.UnsupportedOperationException
 *  java.lang.reflect.Array
 *  java.lang.reflect.Type
 *  java.util.Collection
 *  java.util.Iterator
 */
package com.flurry.org.apache.avro.reflect;

import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.Encoder;
import com.flurry.org.apache.avro.reflect.ReflectData;
import com.flurry.org.apache.avro.specific.SpecificData;
import com.flurry.org.apache.avro.specific.SpecificDatumWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

public class ReflectDatumWriter<T>
extends SpecificDatumWriter<T> {
    public ReflectDatumWriter() {
        this(ReflectData.get());
    }

    public ReflectDatumWriter(Schema schema) {
        super(schema, ReflectData.get());
    }

    protected ReflectDatumWriter(Schema schema, ReflectData reflectData) {
        super(schema, reflectData);
    }

    protected ReflectDatumWriter(ReflectData reflectData) {
        super(reflectData);
    }

    public ReflectDatumWriter(Class<T> class_) {
        super(class_, ReflectData.get());
    }

    public ReflectDatumWriter(Class<T> class_, ReflectData reflectData) {
        super(reflectData.getSchema((Type)class_), reflectData);
    }

    @Override
    protected Iterator<Object> getArrayElements(final Object object) {
        if (object instanceof Collection) {
            return ((Collection)object).iterator();
        }
        return new Iterator<Object>(){
            private int i = 0;
            private final int length = Array.getLength((Object)object);

            public boolean hasNext() {
                return this.i < this.length;
            }

            public Object next() {
                Object object2 = object;
                int n2 = this.i;
                this.i = n2 + 1;
                return Array.get((Object)object2, (int)n2);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    protected long getArraySize(Object object) {
        if (object instanceof Collection) {
            return ((Collection)object).size();
        }
        return Array.getLength((Object)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void write(Schema schema, Object object, Encoder encoder) throws IOException {
        if (object instanceof Byte) {
            object = ((Byte)object).intValue();
        } else if (object instanceof Short) {
            object = ((Short)object).intValue();
        }
        try {
            super.write(schema, object, encoder);
            return;
        }
        catch (NullPointerException nullPointerException) {
            Throwable throwable;
            NullPointerException nullPointerException2 = new NullPointerException("in " + schema.getFullName() + " " + nullPointerException.getMessage());
            if (nullPointerException.getCause() != null) {
                throwable = nullPointerException.getCause();
            }
            nullPointerException2.initCause(throwable);
            throw nullPointerException2;
        }
    }

    @Override
    protected void writeBytes(Object object, Encoder encoder) throws IOException {
        if (object instanceof byte[]) {
            encoder.writeBytes((byte[])object);
            return;
        }
        super.writeBytes(object, encoder);
    }

    @Override
    protected void writeString(Schema schema, Object object, Encoder encoder) throws IOException {
        if (schema.getProp("java-class") != null) {
            object = object.toString();
        }
        this.writeString(object, encoder);
    }

}


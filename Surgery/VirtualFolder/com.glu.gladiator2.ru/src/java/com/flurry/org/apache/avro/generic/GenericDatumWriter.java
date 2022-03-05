/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Iterable
 *  java.lang.Long
 *  java.lang.NoSuchFieldError
 *  java.lang.NullPointerException
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.nio.ByteBuffer
 *  java.util.Collection
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.org.apache.avro.generic;

import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.generic.GenericData;
import com.flurry.org.apache.avro.generic.GenericFixed;
import com.flurry.org.apache.avro.io.DatumWriter;
import com.flurry.org.apache.avro.io.Encoder;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenericDatumWriter<D>
implements DatumWriter<D> {
    private final GenericData data;
    private Schema root;

    public GenericDatumWriter() {
        this(GenericData.get());
    }

    public GenericDatumWriter(Schema schema) {
        this.setSchema(schema);
    }

    protected GenericDatumWriter(Schema schema, GenericData genericData) {
        super(genericData);
        this.setSchema(schema);
    }

    protected GenericDatumWriter(GenericData genericData) {
        this.data = genericData;
    }

    private void error(Schema schema, Object object) {
        throw new AvroTypeException("Not a " + schema + ": " + object);
    }

    protected Iterator<? extends Object> getArrayElements(Object object) {
        return ((Collection)object).iterator();
    }

    protected long getArraySize(Object object) {
        return ((Collection)object).size();
    }

    public GenericData getData() {
        return this.data;
    }

    protected Iterable<Map.Entry<Object, Object>> getMapEntries(Object object) {
        return ((Map)object).entrySet();
    }

    protected int getMapSize(Object object) {
        return ((Map)object).size();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected NullPointerException npe(NullPointerException object, String string) {
        void var2_4;
        void var1_2;
        NullPointerException nullPointerException = new NullPointerException(object.getMessage() + (String)var2_4);
        if (object.getCause() != null) {
            Throwable throwable = object.getCause();
        }
        nullPointerException.initCause((Throwable)var1_2);
        return nullPointerException;
    }

    protected int resolveUnion(Schema schema, Object object) {
        return this.data.resolveUnion(schema, object);
    }

    @Override
    public void setSchema(Schema schema) {
        this.root = schema;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void write(Schema schema, Object object, Encoder encoder) throws IOException {
        try {
            switch (1.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()]) {
                default: {
                    GenericDatumWriter.super.error(schema, object);
                    return;
                }
                case 1: {
                    this.writeRecord(schema, object, encoder);
                    return;
                }
                case 2: {
                    this.writeEnum(schema, object, encoder);
                    return;
                }
                case 3: {
                    this.writeArray(schema, object, encoder);
                    return;
                }
                case 4: {
                    this.writeMap(schema, object, encoder);
                    return;
                }
                case 5: {
                    int n2 = this.resolveUnion(schema, object);
                    encoder.writeIndex(n2);
                    this.write((Schema)schema.getTypes().get(n2), object, encoder);
                    return;
                }
                case 6: {
                    this.writeFixed(schema, object, encoder);
                    return;
                }
                case 7: {
                    this.writeString(schema, object, encoder);
                    return;
                }
                case 8: {
                    this.writeBytes(object, encoder);
                    return;
                }
                case 9: {
                    encoder.writeInt(((Number)object).intValue());
                    return;
                }
                case 10: {
                    encoder.writeLong((Long)object);
                    return;
                }
                case 11: {
                    encoder.writeFloat(((Float)object).floatValue());
                    return;
                }
                case 12: {
                    encoder.writeDouble((Double)object);
                    return;
                }
                case 13: {
                    encoder.writeBoolean((Boolean)object);
                    return;
                }
                case 14: 
            }
            encoder.writeNull();
            return;
        }
        catch (NullPointerException nullPointerException) {
            throw this.npe(nullPointerException, " of " + schema.getFullName());
        }
    }

    @Override
    public void write(D d2, Encoder encoder) throws IOException {
        this.write(this.root, d2, encoder);
    }

    protected void writeArray(Schema schema, Object object, Encoder encoder) throws IOException {
        Schema schema2 = schema.getElementType();
        long l2 = this.getArraySize(object);
        encoder.writeArrayStart();
        encoder.setItemCount(l2);
        Iterator<Object> iterator = this.getArrayElements(object);
        while (iterator.hasNext()) {
            encoder.startItem();
            this.write(schema2, iterator.next(), encoder);
        }
        encoder.writeArrayEnd();
    }

    protected void writeBytes(Object object, Encoder encoder) throws IOException {
        encoder.writeBytes((ByteBuffer)object);
    }

    protected void writeEnum(Schema schema, Object object, Encoder encoder) throws IOException {
        encoder.writeEnum(schema.getEnumOrdinal(object.toString()));
    }

    protected void writeFixed(Schema schema, Object object, Encoder encoder) throws IOException {
        encoder.writeFixed(((GenericFixed)object).bytes(), 0, schema.getFixedSize());
    }

    protected void writeMap(Schema schema, Object object, Encoder encoder) throws IOException {
        Schema schema2 = schema.getValueType();
        int n2 = this.getMapSize(object);
        encoder.writeMapStart();
        encoder.setItemCount(n2);
        for (Map.Entry entry : this.getMapEntries(object)) {
            encoder.startItem();
            this.writeString(entry.getKey(), encoder);
            this.write(schema2, entry.getValue(), encoder);
        }
        encoder.writeMapEnd();
    }

    protected void writeRecord(Schema schema, Object object, Encoder encoder) throws IOException {
        Object object2 = this.data.getRecordState(object, schema);
        for (Schema.Field field : schema.getFields()) {
            Object object3 = this.data.getField(object, field.name(), field.pos(), object2);
            try {
                this.write(field.schema(), object3, encoder);
            }
            catch (NullPointerException nullPointerException) {
                throw this.npe(nullPointerException, " in field " + field.name());
            }
        }
    }

    protected void writeString(Schema schema, Object object, Encoder encoder) throws IOException {
        this.writeString(object, encoder);
    }

    protected void writeString(Object object, Encoder encoder) throws IOException {
        encoder.writeString((CharSequence)object);
    }

}


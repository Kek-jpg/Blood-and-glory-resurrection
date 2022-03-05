/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.List
 */
package com.flurry.org.apache.avro.generic;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilderBase;
import com.flurry.org.apache.avro.generic.GenericData;
import java.io.IOException;
import java.util.List;

public class GenericRecordBuilder
extends RecordBuilderBase<GenericData.Record> {
    private final GenericData.Record record;

    public GenericRecordBuilder(Schema schema) {
        super(schema, GenericData.get());
        this.record = new GenericData.Record(schema);
    }

    public GenericRecordBuilder(GenericData.Record record) {
        super(record.getSchema(), GenericData.get());
        this.record = new GenericData.Record(record, true);
        for (Schema.Field field : this.schema().getFields()) {
            Object object;
            if (!GenericRecordBuilder.isValidValue(field, object = record.get(field.pos()))) continue;
            this.set(field, this.data().deepCopy(field.schema(), object));
        }
    }

    public GenericRecordBuilder(GenericRecordBuilder genericRecordBuilder) {
        super(genericRecordBuilder, GenericData.get());
        this.record = new GenericData.Record(genericRecordBuilder.record, true);
    }

    private Object getWithDefault(Schema.Field field) throws IOException {
        if (this.fieldSetFlags()[field.pos()]) {
            return this.record.get(field.pos());
        }
        return this.defaultValue(field);
    }

    private GenericRecordBuilder set(Schema.Field field, int n2, Object object) {
        this.validate(field, object);
        this.record.put(n2, object);
        this.fieldSetFlags()[n2] = true;
        return this;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public GenericData.Record build() {
        try {}
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
        GenericData.Record record = new GenericData.Record(this.schema());
        Schema.Field[] arrfield = this.fields();
        int n2 = arrfield.length;
        int n3 = 0;
        while (n3 < n2) {
            block5 : {
                Schema.Field field = arrfield[n3];
                Object object = this.getWithDefault(field);
                if (object == null) break block5;
                record.put(field.pos(), object);
            }
            ++n3;
        }
        return record;
        catch (IOException iOException) {
            throw new AvroRuntimeException(iOException);
        }
    }

    protected GenericRecordBuilder clear(int n2) {
        this.record.put(n2, null);
        this.fieldSetFlags()[n2] = false;
        return this;
    }

    public GenericRecordBuilder clear(Schema.Field field) {
        return this.clear(field.pos());
    }

    public GenericRecordBuilder clear(String string) {
        return this.clear(this.schema().getField(string));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!super.equals(object)) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        GenericRecordBuilder genericRecordBuilder = (GenericRecordBuilder)object;
        if (this.record == null) {
            if (genericRecordBuilder.record == null) return true;
            return false;
        }
        if (!this.record.equals(genericRecordBuilder.record)) return false;
        return true;
    }

    protected Object get(int n2) {
        return this.record.get(n2);
    }

    public Object get(Schema.Field field) {
        return this.get(field.pos());
    }

    public Object get(String string) {
        return this.get(this.schema().getField(string));
    }

    protected boolean has(int n2) {
        return this.fieldSetFlags()[n2];
    }

    public boolean has(Schema.Field field) {
        return this.has(field.pos());
    }

    public boolean has(String string) {
        return this.has(this.schema().getField(string));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int hashCode() {
        int n2;
        int n3 = 31 * super.hashCode();
        if (this.record == null) {
            n2 = 0;
            do {
                return n3 + n2;
                break;
            } while (true);
        }
        n2 = this.record.hashCode();
        return n3 + n2;
    }

    protected GenericRecordBuilder set(int n2, Object object) {
        return GenericRecordBuilder.super.set(this.fields()[n2], n2, object);
    }

    public GenericRecordBuilder set(Schema.Field field, Object object) {
        return GenericRecordBuilder.super.set(field, field.pos(), object);
    }

    public GenericRecordBuilder set(String string, Object object) {
        return this.set(this.schema().getField(string), object);
    }
}


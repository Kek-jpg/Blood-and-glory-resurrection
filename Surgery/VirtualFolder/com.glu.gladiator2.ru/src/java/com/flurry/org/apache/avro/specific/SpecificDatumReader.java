/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Type
 */
package com.flurry.org.apache.avro.specific;

import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.generic.GenericData;
import com.flurry.org.apache.avro.generic.GenericDatumReader;
import com.flurry.org.apache.avro.specific.SpecificData;
import com.flurry.org.apache.avro.specific.SpecificRecord;
import java.lang.reflect.Type;

public class SpecificDatumReader<T>
extends GenericDatumReader<T> {
    public SpecificDatumReader() {
        this(null, null, SpecificData.get());
    }

    public SpecificDatumReader(Schema schema) {
        super(schema, schema, SpecificData.get());
    }

    public SpecificDatumReader(Schema schema, Schema schema2) {
        super(schema, schema2, SpecificData.get());
    }

    public SpecificDatumReader(Schema schema, Schema schema2, SpecificData specificData) {
        super(schema, schema2, specificData);
    }

    public SpecificDatumReader(Class<T> class_) {
        super(SpecificData.get().getSchema((Type)class_));
    }

    @Override
    protected Object createEnum(String string, Schema schema) {
        Class class_ = this.getSpecificData().getClass(schema);
        if (class_ == null) {
            return super.createEnum(string, schema);
        }
        return Enum.valueOf((Class)class_, (String)string);
    }

    public SpecificData getSpecificData() {
        return (SpecificData)this.getData();
    }

    @Override
    public void setSchema(Schema schema) {
        SpecificData specificData;
        Class class_;
        if (this.getExpected() == null && schema != null && schema.getType() == Schema.Type.RECORD && (class_ = (specificData = this.getSpecificData()).getClass(schema)) != null && SpecificRecord.class.isAssignableFrom(class_)) {
            this.setExpected(specificData.getSchema((Type)class_));
        }
        super.setSchema(schema);
    }
}


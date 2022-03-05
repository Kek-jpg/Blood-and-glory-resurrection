/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.reflect.Type
 */
package com.flurry.org.apache.avro.specific;

import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.generic.GenericData;
import com.flurry.org.apache.avro.generic.GenericDatumWriter;
import com.flurry.org.apache.avro.io.Encoder;
import com.flurry.org.apache.avro.specific.SpecificData;
import java.io.IOException;
import java.lang.reflect.Type;

public class SpecificDatumWriter<T>
extends GenericDatumWriter<T> {
    public SpecificDatumWriter() {
        super(SpecificData.get());
    }

    public SpecificDatumWriter(Schema schema) {
        super(schema, SpecificData.get());
    }

    protected SpecificDatumWriter(Schema schema, SpecificData specificData) {
        super(schema, specificData);
    }

    protected SpecificDatumWriter(SpecificData specificData) {
        super(specificData);
    }

    public SpecificDatumWriter(Class<T> class_) {
        super(SpecificData.get().getSchema((Type)class_), SpecificData.get());
    }

    @Override
    protected void writeEnum(Schema schema, Object object, Encoder encoder) throws IOException {
        if (!(object instanceof Enum)) {
            super.writeEnum(schema, object, encoder);
            return;
        }
        encoder.writeEnum(((Enum)object).ordinal());
    }
}


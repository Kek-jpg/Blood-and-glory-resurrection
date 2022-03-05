/*
 * Decompiled with CFR 0.0.
 */
package com.flurry.org.apache.avro.specific;

import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilderBase;
import com.flurry.org.apache.avro.generic.GenericData;
import com.flurry.org.apache.avro.specific.SpecificData;
import com.flurry.org.apache.avro.specific.SpecificRecord;

public abstract class SpecificRecordBuilderBase<T extends SpecificRecord>
extends RecordBuilderBase<T> {
    protected SpecificRecordBuilderBase(Schema schema) {
        super(schema, (GenericData)SpecificData.get());
    }

    protected SpecificRecordBuilderBase(T t2) {
        super(t2.getSchema(), (GenericData)SpecificData.get());
    }

    protected SpecificRecordBuilderBase(SpecificRecordBuilderBase<T> specificRecordBuilderBase) {
        super(specificRecordBuilderBase, (GenericData)SpecificData.get());
    }
}


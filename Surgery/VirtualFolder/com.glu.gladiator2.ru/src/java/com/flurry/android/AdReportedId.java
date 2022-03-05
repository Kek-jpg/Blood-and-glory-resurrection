/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.ByteBuffer
 */
package com.flurry.android;

import com.flurry.android.AdReportedId$Builder;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.specific.SpecificRecord;
import com.flurry.org.apache.avro.specific.SpecificRecordBase;
import java.nio.ByteBuffer;

class AdReportedId
extends SpecificRecordBase
implements SpecificRecord {
    public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AdReportedId\",\"namespace\":\"com.flurry.android\",\"fields\":[{\"name\":\"type\",\"type\":\"int\"},{\"name\":\"id\",\"type\":\"bytes\"}]}");
    public int a;
    public ByteBuffer b;

    AdReportedId() {
    }

    public static AdReportedId$Builder a() {
        return new AdReportedId$Builder();
    }

    @Override
    public Object get(int n2) {
        switch (n2) {
            default: {
                throw new AvroRuntimeException("Bad index");
            }
            case 0: {
                return this.a;
            }
            case 1: 
        }
        return this.b;
    }

    @Override
    public Schema getSchema() {
        return SCHEMA$;
    }

    @Override
    public void put(int n2, Object object) {
        switch (n2) {
            default: {
                throw new AvroRuntimeException("Bad index");
            }
            case 0: {
                this.a = (Integer)object;
                return;
            }
            case 1: 
        }
        this.b = (ByteBuffer)object;
    }
}


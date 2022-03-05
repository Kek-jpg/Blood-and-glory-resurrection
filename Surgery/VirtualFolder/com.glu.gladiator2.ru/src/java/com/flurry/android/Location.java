/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.android;

import com.flurry.android.Location$Builder;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.specific.SpecificRecord;
import com.flurry.org.apache.avro.specific.SpecificRecordBase;

class Location
extends SpecificRecordBase
implements SpecificRecord {
    public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Location\",\"namespace\":\"com.flurry.android\",\"fields\":[{\"name\":\"lat\",\"type\":\"float\",\"default\":0.0},{\"name\":\"lon\",\"type\":\"float\",\"default\":0.0}]}");
    public float a;
    public float b;

    Location() {
    }

    public static Location$Builder a() {
        return new Location$Builder();
    }

    @Override
    public Object get(int n2) {
        switch (n2) {
            default: {
                throw new AvroRuntimeException("Bad index");
            }
            case 0: {
                return Float.valueOf((float)this.a);
            }
            case 1: 
        }
        return Float.valueOf((float)this.b);
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
                this.a = ((Float)object).floatValue();
                return;
            }
            case 1: 
        }
        this.b = ((Float)object).floatValue();
    }
}


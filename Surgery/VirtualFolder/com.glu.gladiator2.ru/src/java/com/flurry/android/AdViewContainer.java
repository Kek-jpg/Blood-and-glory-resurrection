/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.android;

import com.flurry.android.AdViewContainer$Builder;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.specific.SpecificRecord;
import com.flurry.org.apache.avro.specific.SpecificRecordBase;

class AdViewContainer
extends SpecificRecordBase
implements SpecificRecord {
    public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AdViewContainer\",\"namespace\":\"com.flurry.android\",\"fields\":[{\"name\":\"viewWidth\",\"type\":\"int\",\"default\":0},{\"name\":\"viewHeight\",\"type\":\"int\",\"default\":0},{\"name\":\"screenWidth\",\"type\":\"int\",\"default\":0},{\"name\":\"screenHeight\",\"type\":\"int\",\"default\":0}]}");
    public int a;
    public int b;
    public int c;
    public int d;

    AdViewContainer() {
    }

    public static AdViewContainer$Builder a() {
        return new AdViewContainer$Builder();
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
            case 1: {
                return this.b;
            }
            case 2: {
                return this.c;
            }
            case 3: 
        }
        return this.d;
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
            case 1: {
                this.b = (Integer)object;
                return;
            }
            case 2: {
                this.c = (Integer)object;
                return;
            }
            case 3: 
        }
        this.d = (Integer)object;
    }
}


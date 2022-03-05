/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.flurry.android;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.specific.SpecificRecord;
import com.flurry.org.apache.avro.specific.SpecificRecordBase;
import java.util.Map;

class SdkAdEvent
extends SpecificRecordBase
implements SpecificRecord {
    public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"SdkAdEvent\",\"namespace\":\"com.flurry.android\",\"fields\":[{\"name\":\"type\",\"type\":\"string\"},{\"name\":\"params\",\"type\":{\"type\":\"map\",\"values\":\"string\"}},{\"name\":\"timeOffset\",\"type\":\"long\"}]}");
    public CharSequence a;
    public Map<CharSequence, CharSequence> b;
    public long c;

    SdkAdEvent() {
    }

    public final void a(CharSequence charSequence) {
        this.a = charSequence;
    }

    public final void a(Long l2) {
        this.c = l2;
    }

    public final void a(Map<CharSequence, CharSequence> map) {
        this.b = map;
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
            case 2: 
        }
        return this.c;
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
                this.a = (CharSequence)object;
                return;
            }
            case 1: {
                this.b = (Map)object;
                return;
            }
            case 2: 
        }
        this.c = (Long)object;
    }
}


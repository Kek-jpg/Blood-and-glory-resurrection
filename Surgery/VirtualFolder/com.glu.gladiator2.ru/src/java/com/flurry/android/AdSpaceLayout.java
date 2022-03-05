/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.android;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.specific.SpecificRecord;
import com.flurry.org.apache.avro.specific.SpecificRecordBase;

class AdSpaceLayout
extends SpecificRecordBase
implements SpecificRecord {
    public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AdSpaceLayout\",\"namespace\":\"com.flurry.android\",\"fields\":[{\"name\":\"adWidth\",\"type\":\"int\"},{\"name\":\"adHeight\",\"type\":\"int\"},{\"name\":\"fix\",\"type\":\"string\"},{\"name\":\"format\",\"type\":\"string\"},{\"name\":\"alignment\",\"type\":\"string\"}]}");
    public int a;
    public int b;
    public CharSequence c;
    public CharSequence d;
    public CharSequence e;

    AdSpaceLayout() {
    }

    public final Integer a() {
        return this.a;
    }

    public final Integer b() {
        return this.b;
    }

    public final CharSequence c() {
        return this.c;
    }

    public final CharSequence d() {
        return this.d;
    }

    public final CharSequence e() {
        return this.e;
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
            case 3: {
                return this.d;
            }
            case 4: 
        }
        return this.e;
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
                this.c = (CharSequence)object;
                return;
            }
            case 3: {
                this.d = (CharSequence)object;
                return;
            }
            case 4: 
        }
        this.e = (CharSequence)object;
    }
}


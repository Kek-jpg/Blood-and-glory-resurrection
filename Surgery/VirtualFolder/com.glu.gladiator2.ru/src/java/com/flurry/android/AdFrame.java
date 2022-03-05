/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package com.flurry.android;

import com.flurry.android.AdSpaceLayout;
import com.flurry.android.Callback;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.specific.SpecificRecord;
import com.flurry.org.apache.avro.specific.SpecificRecordBase;
import java.util.List;

class AdFrame
extends SpecificRecordBase
implements SpecificRecord {
    public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AdFrame\",\"namespace\":\"com.flurry.android\",\"fields\":[{\"name\":\"binding\",\"type\":\"int\"},{\"name\":\"display\",\"type\":\"string\"},{\"name\":\"content\",\"type\":\"string\"},{\"name\":\"adSpaceLayout\",\"type\":{\"type\":\"record\",\"name\":\"AdSpaceLayout\",\"fields\":[{\"name\":\"adWidth\",\"type\":\"int\"},{\"name\":\"adHeight\",\"type\":\"int\"},{\"name\":\"fix\",\"type\":\"string\"},{\"name\":\"format\",\"type\":\"string\"},{\"name\":\"alignment\",\"type\":\"string\"}]}},{\"name\":\"callbacks\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Callback\",\"fields\":[{\"name\":\"event\",\"type\":\"string\"},{\"name\":\"actions\",\"type\":{\"type\":\"array\",\"items\":\"string\"}}]}}},{\"name\":\"adGuid\",\"type\":\"string\"}]}");
    public int a;
    public CharSequence b;
    public CharSequence c;
    public AdSpaceLayout d;
    public List<Callback> e;
    public CharSequence f;

    AdFrame() {
    }

    public final Integer a() {
        return this.a;
    }

    public final CharSequence b() {
        return this.b;
    }

    public final CharSequence c() {
        return this.c;
    }

    public final AdSpaceLayout d() {
        return this.d;
    }

    public final List<Callback> e() {
        return this.e;
    }

    public final CharSequence f() {
        return this.f;
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
            case 4: {
                return this.e;
            }
            case 5: 
        }
        return this.f;
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
                this.b = (CharSequence)object;
                return;
            }
            case 2: {
                this.c = (CharSequence)object;
                return;
            }
            case 3: {
                this.d = (AdSpaceLayout)object;
                return;
            }
            case 4: {
                this.e = (List)object;
                return;
            }
            case 5: 
        }
        this.f = (CharSequence)object;
    }
}


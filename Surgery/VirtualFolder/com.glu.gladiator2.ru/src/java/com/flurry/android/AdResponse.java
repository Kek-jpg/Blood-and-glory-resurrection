/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package com.flurry.android;

import com.flurry.android.AdUnit;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.specific.SpecificRecord;
import com.flurry.org.apache.avro.specific.SpecificRecordBase;
import java.util.List;

class AdResponse
extends SpecificRecordBase
implements SpecificRecord {
    public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AdResponse\",\"namespace\":\"com.flurry.android\",\"fields\":[{\"name\":\"adUnits\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"AdUnit\",\"fields\":[{\"name\":\"adSpace\",\"type\":\"string\"},{\"name\":\"expiration\",\"type\":\"long\"},{\"name\":\"adFrames\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"AdFrame\",\"fields\":[{\"name\":\"binding\",\"type\":\"int\"},{\"name\":\"display\",\"type\":\"string\"},{\"name\":\"content\",\"type\":\"string\"},{\"name\":\"adSpaceLayout\",\"type\":{\"type\":\"record\",\"name\":\"AdSpaceLayout\",\"fields\":[{\"name\":\"adWidth\",\"type\":\"int\"},{\"name\":\"adHeight\",\"type\":\"int\"},{\"name\":\"fix\",\"type\":\"string\"},{\"name\":\"format\",\"type\":\"string\"},{\"name\":\"alignment\",\"type\":\"string\"}]}},{\"name\":\"callbacks\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Callback\",\"fields\":[{\"name\":\"event\",\"type\":\"string\"},{\"name\":\"actions\",\"type\":{\"type\":\"array\",\"items\":\"string\"}}]}}},{\"name\":\"adGuid\",\"type\":\"string\"}]}}},{\"name\":\"combinable\",\"type\":\"int\",\"default\":0}]}}},{\"name\":\"errors\",\"type\":{\"type\":\"array\",\"items\":\"string\"},\"default\":[]}]}");
    public List<AdUnit> a;
    public List<CharSequence> b;

    AdResponse() {
    }

    public final List<AdUnit> a() {
        return this.a;
    }

    public final List<CharSequence> b() {
        return this.b;
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
                this.a = (List)object;
                return;
            }
            case 1: 
        }
        this.b = (List)object;
    }
}


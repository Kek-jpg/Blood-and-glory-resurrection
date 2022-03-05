/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package com.flurry.android;

import com.flurry.android.AdReportedId;
import com.flurry.android.SdkAdLog;
import com.flurry.android.SdkLogRequest$Builder;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.specific.SpecificRecord;
import com.flurry.org.apache.avro.specific.SpecificRecordBase;
import java.util.List;

class SdkLogRequest
extends SpecificRecordBase
implements SpecificRecord {
    public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"SdkLogRequest\",\"namespace\":\"com.flurry.android\",\"fields\":[{\"name\":\"apiKey\",\"type\":\"string\"},{\"name\":\"adReportedIds\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"AdReportedId\",\"fields\":[{\"name\":\"type\",\"type\":\"int\"},{\"name\":\"id\",\"type\":\"bytes\"}]}}},{\"name\":\"sdkAdLogs\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"SdkAdLog\",\"fields\":[{\"name\":\"sessionId\",\"type\":\"long\"},{\"name\":\"adLogGUID\",\"type\":\"string\"},{\"name\":\"sdkAdEvents\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"SdkAdEvent\",\"fields\":[{\"name\":\"type\",\"type\":\"string\"},{\"name\":\"params\",\"type\":{\"type\":\"map\",\"values\":\"string\"}},{\"name\":\"timeOffset\",\"type\":\"long\"}]}}}]}}},{\"name\":\"agentTimestamp\",\"type\":\"long\"},{\"name\":\"testDevice\",\"type\":\"boolean\",\"default\":false}]}");
    public CharSequence a;
    public List<AdReportedId> b;
    public List<SdkAdLog> c;
    public long d;
    public boolean e;

    SdkLogRequest() {
    }

    public static SdkLogRequest$Builder a() {
        return new SdkLogRequest$Builder();
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
                this.a = (CharSequence)object;
                return;
            }
            case 1: {
                this.b = (List)object;
                return;
            }
            case 2: {
                this.c = (List)object;
                return;
            }
            case 3: {
                this.d = (Long)object;
                return;
            }
            case 4: 
        }
        this.e = (Boolean)object;
    }
}


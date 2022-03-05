/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.android;

import com.flurry.android.AdReportedId;
import com.flurry.android.AdRequest$Builder;
import com.flurry.android.AdViewContainer;
import com.flurry.android.Location;
import com.flurry.android.TestAds;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.specific.SpecificRecord;
import com.flurry.org.apache.avro.specific.SpecificRecordBase;
import java.util.List;
import java.util.Map;

class AdRequest
extends SpecificRecordBase
implements SpecificRecord {
    public static final Schema SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AdRequest\",\"namespace\":\"com.flurry.android\",\"fields\":[{\"name\":\"apiKey\",\"type\":\"string\"},{\"name\":\"agentVersion\",\"type\":\"string\",\"default\":\"null\"},{\"name\":\"adSpaceName\",\"type\":\"string\"},{\"name\":\"sessionId\",\"type\":\"long\"},{\"name\":\"adReportedIds\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"AdReportedId\",\"fields\":[{\"name\":\"type\",\"type\":\"int\"},{\"name\":\"id\",\"type\":\"bytes\"}]}}},{\"name\":\"location\",\"type\":{\"type\":\"record\",\"name\":\"Location\",\"fields\":[{\"name\":\"lat\",\"type\":\"float\",\"default\":0.0},{\"name\":\"lon\",\"type\":\"float\",\"default\":0.0}]},\"default\":\"null\"},{\"name\":\"testDevice\",\"type\":\"boolean\",\"default\":false},{\"name\":\"bindings\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"adViewContainer\",\"type\":{\"type\":\"record\",\"name\":\"AdViewContainer\",\"fields\":[{\"name\":\"viewWidth\",\"type\":\"int\",\"default\":0},{\"name\":\"viewHeight\",\"type\":\"int\",\"default\":0},{\"name\":\"screenWidth\",\"type\":\"int\",\"default\":0},{\"name\":\"screenHeight\",\"type\":\"int\",\"default\":0}]},\"default\":\"null\"},{\"name\":\"locale\",\"type\":\"string\",\"default\":\"null\"},{\"name\":\"timezone\",\"type\":\"string\",\"default\":\"null\"},{\"name\":\"osVersion\",\"type\":\"string\",\"default\":\"null\"},{\"name\":\"devicePlatform\",\"type\":\"string\",\"default\":\"null\"},{\"name\":\"testAds\",\"type\":{\"type\":\"record\",\"name\":\"TestAds\",\"fields\":[{\"name\":\"adspacePlacement\",\"type\":\"int\",\"default\":0}]},\"default\":\"null\"},{\"name\":\"keywords\",\"type\":{\"type\":\"map\",\"values\":\"string\"},\"default\":[]},{\"name\":\"refresh\",\"type\":\"boolean\",\"default\":false}]}");
    public CharSequence a;
    public CharSequence b;
    public CharSequence c;
    public long d;
    public List<AdReportedId> e;
    public Location f;
    public boolean g;
    public List<Integer> h;
    public AdViewContainer i;
    public CharSequence j;
    public CharSequence k;
    public CharSequence l;
    public CharSequence m;
    public TestAds n;
    public Map<CharSequence, CharSequence> o;
    public boolean p;

    AdRequest() {
    }

    public static AdRequest$Builder a() {
        return new AdRequest$Builder();
    }

    public final void a(TestAds testAds) {
        this.n = testAds;
    }

    public final void a(Boolean bl) {
        this.p = bl;
    }

    public final void a(CharSequence charSequence) {
        this.c = charSequence;
    }

    public final void a(Map<CharSequence, CharSequence> map) {
        this.o = map;
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
            case 5: {
                return this.f;
            }
            case 6: {
                return this.g;
            }
            case 7: {
                return this.h;
            }
            case 8: {
                return this.i;
            }
            case 9: {
                return this.j;
            }
            case 10: {
                return this.k;
            }
            case 11: {
                return this.l;
            }
            case 12: {
                return this.m;
            }
            case 13: {
                return this.n;
            }
            case 14: {
                return this.o;
            }
            case 15: 
        }
        return this.p;
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
                this.b = (CharSequence)object;
                return;
            }
            case 2: {
                this.c = (CharSequence)object;
                return;
            }
            case 3: {
                this.d = (Long)object;
                return;
            }
            case 4: {
                this.e = (List)object;
                return;
            }
            case 5: {
                this.f = (Location)object;
                return;
            }
            case 6: {
                this.g = (Boolean)object;
                return;
            }
            case 7: {
                this.h = (List)object;
                return;
            }
            case 8: {
                this.i = (AdViewContainer)object;
                return;
            }
            case 9: {
                this.j = (CharSequence)object;
                return;
            }
            case 10: {
                this.k = (CharSequence)object;
                return;
            }
            case 11: {
                this.l = (CharSequence)object;
                return;
            }
            case 12: {
                this.m = (CharSequence)object;
                return;
            }
            case 13: {
                this.n = (TestAds)object;
                return;
            }
            case 14: {
                this.o = (Map)object;
                return;
            }
            case 15: 
        }
        this.p = (Boolean)object;
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.util.List
 */
package com.flurry.android;

import com.flurry.android.AdResponse;
import com.flurry.android.AdUnit;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;
import java.util.List;

public class AdResponse$Builder
extends SpecificRecordBuilderBase<AdResponse>
implements RecordBuilder<AdResponse> {
    private List<AdUnit> a;
    private List<CharSequence> b;

    private AdResponse$Builder() {
        super(AdResponse.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AdResponse build() {
        try {
            AdResponse adResponse = new AdResponse();
            List list = this.fieldSetFlags()[0] ? this.a : (List)this.defaultValue(this.fields()[0]);
            adResponse.a = list;
            List list2 = this.fieldSetFlags()[1] ? this.b : (List)this.defaultValue(this.fields()[1]);
            adResponse.b = list2;
            return adResponse;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public AdResponse$Builder clearAdUnits() {
        this.a = null;
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public AdResponse$Builder clearErrors() {
        this.b = null;
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public List<AdUnit> getAdUnits() {
        return this.a;
    }

    public List<CharSequence> getErrors() {
        return this.b;
    }

    public boolean hasAdUnits() {
        return this.fieldSetFlags()[0];
    }

    public boolean hasErrors() {
        return this.fieldSetFlags()[1];
    }

    public AdResponse$Builder setAdUnits(List<AdUnit> list) {
        this.validate(this.fields()[0], list);
        this.a = list;
        this.fieldSetFlags()[0] = true;
        return this;
    }

    public AdResponse$Builder setErrors(List<CharSequence> list) {
        this.validate(this.fields()[1], list);
        this.b = list;
        this.fieldSetFlags()[1] = true;
        return this;
    }
}


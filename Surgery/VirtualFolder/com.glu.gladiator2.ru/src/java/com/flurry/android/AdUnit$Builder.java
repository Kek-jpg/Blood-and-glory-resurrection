/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.util.List
 */
package com.flurry.android;

import com.flurry.android.AdFrame;
import com.flurry.android.AdUnit;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;
import java.util.List;

public class AdUnit$Builder
extends SpecificRecordBuilderBase<AdUnit>
implements RecordBuilder<AdUnit> {
    private CharSequence a;
    private long b;
    private List<AdFrame> c;
    private int d;

    private AdUnit$Builder() {
        super(AdUnit.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AdUnit build() {
        try {
            int n2;
            AdUnit adUnit = new AdUnit();
            CharSequence charSequence = this.fieldSetFlags()[0] ? this.a : (CharSequence)this.defaultValue(this.fields()[0]);
            adUnit.a = charSequence;
            long l2 = this.fieldSetFlags()[1] ? this.b : (Long)this.defaultValue(this.fields()[1]);
            adUnit.b = l2;
            List list = this.fieldSetFlags()[2] ? this.c : (List)this.defaultValue(this.fields()[2]);
            adUnit.c = list;
            int n3 = this.fieldSetFlags()[3] ? this.d : (n2 = ((Integer)this.defaultValue(this.fields()[3])).intValue());
            adUnit.d = n3;
            return adUnit;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public AdUnit$Builder clearAdFrames() {
        this.c = null;
        this.fieldSetFlags()[2] = false;
        return this;
    }

    public AdUnit$Builder clearAdSpace() {
        this.a = null;
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public AdUnit$Builder clearCombinable() {
        this.fieldSetFlags()[3] = false;
        return this;
    }

    public AdUnit$Builder clearExpiration() {
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public List<AdFrame> getAdFrames() {
        return this.c;
    }

    public CharSequence getAdSpace() {
        return this.a;
    }

    public Integer getCombinable() {
        return this.d;
    }

    public Long getExpiration() {
        return this.b;
    }

    public boolean hasAdFrames() {
        return this.fieldSetFlags()[2];
    }

    public boolean hasAdSpace() {
        return this.fieldSetFlags()[0];
    }

    public boolean hasCombinable() {
        return this.fieldSetFlags()[3];
    }

    public boolean hasExpiration() {
        return this.fieldSetFlags()[1];
    }

    public AdUnit$Builder setAdFrames(List<AdFrame> list) {
        this.validate(this.fields()[2], list);
        this.c = list;
        this.fieldSetFlags()[2] = true;
        return this;
    }

    public AdUnit$Builder setAdSpace(CharSequence charSequence) {
        this.validate(this.fields()[0], (Object)charSequence);
        this.a = charSequence;
        this.fieldSetFlags()[0] = true;
        return this;
    }

    public AdUnit$Builder setCombinable(int n2) {
        this.validate(this.fields()[3], n2);
        this.d = n2;
        this.fieldSetFlags()[3] = true;
        return this;
    }

    public AdUnit$Builder setExpiration(long l2) {
        this.validate(this.fields()[1], l2);
        this.b = l2;
        this.fieldSetFlags()[1] = true;
        return this;
    }
}


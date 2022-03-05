/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Throwable
 */
package com.flurry.android;

import com.flurry.android.AdViewContainer;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;

public class AdViewContainer$Builder
extends SpecificRecordBuilderBase<AdViewContainer>
implements RecordBuilder<AdViewContainer> {
    private int a;
    private int b;
    private int c;
    private int d;

    /* synthetic */ AdViewContainer$Builder() {
        this(0);
    }

    private AdViewContainer$Builder(byte by) {
        super(AdViewContainer.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AdViewContainer build() {
        try {
            int n2;
            AdViewContainer adViewContainer = new AdViewContainer();
            int n3 = this.fieldSetFlags()[0] ? this.a : (Integer)this.defaultValue(this.fields()[0]);
            adViewContainer.a = n3;
            int n4 = this.fieldSetFlags()[1] ? this.b : (Integer)this.defaultValue(this.fields()[1]);
            adViewContainer.b = n4;
            int n5 = this.fieldSetFlags()[2] ? this.c : (Integer)this.defaultValue(this.fields()[2]);
            adViewContainer.c = n5;
            int n6 = this.fieldSetFlags()[3] ? this.d : (n2 = ((Integer)this.defaultValue(this.fields()[3])).intValue());
            adViewContainer.d = n6;
            return adViewContainer;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public AdViewContainer$Builder clearScreenHeight() {
        this.fieldSetFlags()[3] = false;
        return this;
    }

    public AdViewContainer$Builder clearScreenWidth() {
        this.fieldSetFlags()[2] = false;
        return this;
    }

    public AdViewContainer$Builder clearViewHeight() {
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public AdViewContainer$Builder clearViewWidth() {
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public Integer getScreenHeight() {
        return this.d;
    }

    public Integer getScreenWidth() {
        return this.c;
    }

    public Integer getViewHeight() {
        return this.b;
    }

    public Integer getViewWidth() {
        return this.a;
    }

    public boolean hasScreenHeight() {
        return this.fieldSetFlags()[3];
    }

    public boolean hasScreenWidth() {
        return this.fieldSetFlags()[2];
    }

    public boolean hasViewHeight() {
        return this.fieldSetFlags()[1];
    }

    public boolean hasViewWidth() {
        return this.fieldSetFlags()[0];
    }

    public AdViewContainer$Builder setScreenHeight(int n2) {
        this.validate(this.fields()[3], n2);
        this.d = n2;
        this.fieldSetFlags()[3] = true;
        return this;
    }

    public AdViewContainer$Builder setScreenWidth(int n2) {
        this.validate(this.fields()[2], n2);
        this.c = n2;
        this.fieldSetFlags()[2] = true;
        return this;
    }

    public AdViewContainer$Builder setViewHeight(int n2) {
        this.validate(this.fields()[1], n2);
        this.b = n2;
        this.fieldSetFlags()[1] = true;
        return this;
    }

    public AdViewContainer$Builder setViewWidth(int n2) {
        this.validate(this.fields()[0], n2);
        this.a = n2;
        this.fieldSetFlags()[0] = true;
        return this;
    }
}


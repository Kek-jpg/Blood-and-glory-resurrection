/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Throwable
 */
package com.flurry.android;

import com.flurry.android.AdSpaceLayout;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;

public class AdSpaceLayout$Builder
extends SpecificRecordBuilderBase<AdSpaceLayout>
implements RecordBuilder<AdSpaceLayout> {
    private int a;
    private int b;
    private CharSequence c;
    private CharSequence d;
    private CharSequence e;

    private AdSpaceLayout$Builder() {
        super(AdSpaceLayout.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AdSpaceLayout build() {
        try {
            AdSpaceLayout adSpaceLayout = new AdSpaceLayout();
            int n2 = this.fieldSetFlags()[0] ? this.a : (Integer)this.defaultValue(this.fields()[0]);
            adSpaceLayout.a = n2;
            int n3 = this.fieldSetFlags()[1] ? this.b : (Integer)this.defaultValue(this.fields()[1]);
            adSpaceLayout.b = n3;
            CharSequence charSequence = this.fieldSetFlags()[2] ? this.c : (CharSequence)this.defaultValue(this.fields()[2]);
            adSpaceLayout.c = charSequence;
            CharSequence charSequence2 = this.fieldSetFlags()[3] ? this.d : (CharSequence)this.defaultValue(this.fields()[3]);
            adSpaceLayout.d = charSequence2;
            CharSequence charSequence3 = this.fieldSetFlags()[4] ? this.e : (CharSequence)this.defaultValue(this.fields()[4]);
            adSpaceLayout.e = charSequence3;
            return adSpaceLayout;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public AdSpaceLayout$Builder clearAdHeight() {
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public AdSpaceLayout$Builder clearAdWidth() {
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public AdSpaceLayout$Builder clearAlignment() {
        this.e = null;
        this.fieldSetFlags()[4] = false;
        return this;
    }

    public AdSpaceLayout$Builder clearFix() {
        this.c = null;
        this.fieldSetFlags()[2] = false;
        return this;
    }

    public AdSpaceLayout$Builder clearFormat() {
        this.d = null;
        this.fieldSetFlags()[3] = false;
        return this;
    }

    public Integer getAdHeight() {
        return this.b;
    }

    public Integer getAdWidth() {
        return this.a;
    }

    public CharSequence getAlignment() {
        return this.e;
    }

    public CharSequence getFix() {
        return this.c;
    }

    public CharSequence getFormat() {
        return this.d;
    }

    public boolean hasAdHeight() {
        return this.fieldSetFlags()[1];
    }

    public boolean hasAdWidth() {
        return this.fieldSetFlags()[0];
    }

    public boolean hasAlignment() {
        return this.fieldSetFlags()[4];
    }

    public boolean hasFix() {
        return this.fieldSetFlags()[2];
    }

    public boolean hasFormat() {
        return this.fieldSetFlags()[3];
    }

    public AdSpaceLayout$Builder setAdHeight(int n2) {
        this.validate(this.fields()[1], n2);
        this.b = n2;
        this.fieldSetFlags()[1] = true;
        return this;
    }

    public AdSpaceLayout$Builder setAdWidth(int n2) {
        this.validate(this.fields()[0], n2);
        this.a = n2;
        this.fieldSetFlags()[0] = true;
        return this;
    }

    public AdSpaceLayout$Builder setAlignment(CharSequence charSequence) {
        this.validate(this.fields()[4], (Object)charSequence);
        this.e = charSequence;
        this.fieldSetFlags()[4] = true;
        return this;
    }

    public AdSpaceLayout$Builder setFix(CharSequence charSequence) {
        this.validate(this.fields()[2], (Object)charSequence);
        this.c = charSequence;
        this.fieldSetFlags()[2] = true;
        return this;
    }

    public AdSpaceLayout$Builder setFormat(CharSequence charSequence) {
        this.validate(this.fields()[3], (Object)charSequence);
        this.d = charSequence;
        this.fieldSetFlags()[3] = true;
        return this;
    }
}


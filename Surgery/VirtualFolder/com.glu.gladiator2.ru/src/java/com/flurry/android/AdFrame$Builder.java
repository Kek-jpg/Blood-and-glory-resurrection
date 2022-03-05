/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.util.List
 */
package com.flurry.android;

import com.flurry.android.AdFrame;
import com.flurry.android.AdSpaceLayout;
import com.flurry.android.Callback;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;
import java.util.List;

public class AdFrame$Builder
extends SpecificRecordBuilderBase<AdFrame>
implements RecordBuilder<AdFrame> {
    private int a;
    private CharSequence b;
    private CharSequence c;
    private AdSpaceLayout d;
    private List<Callback> e;
    private CharSequence f;

    private AdFrame$Builder() {
        super(AdFrame.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AdFrame build() {
        try {
            AdFrame adFrame = new AdFrame();
            int n2 = this.fieldSetFlags()[0] ? this.a : (Integer)this.defaultValue(this.fields()[0]);
            adFrame.a = n2;
            CharSequence charSequence = this.fieldSetFlags()[1] ? this.b : (CharSequence)this.defaultValue(this.fields()[1]);
            adFrame.b = charSequence;
            CharSequence charSequence2 = this.fieldSetFlags()[2] ? this.c : (CharSequence)this.defaultValue(this.fields()[2]);
            adFrame.c = charSequence2;
            AdSpaceLayout adSpaceLayout = this.fieldSetFlags()[3] ? this.d : (AdSpaceLayout)this.defaultValue(this.fields()[3]);
            adFrame.d = adSpaceLayout;
            List list = this.fieldSetFlags()[4] ? this.e : (List)this.defaultValue(this.fields()[4]);
            adFrame.e = list;
            CharSequence charSequence3 = this.fieldSetFlags()[5] ? this.f : (CharSequence)this.defaultValue(this.fields()[5]);
            adFrame.f = charSequence3;
            return adFrame;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public AdFrame$Builder clearAdGuid() {
        this.f = null;
        this.fieldSetFlags()[5] = false;
        return this;
    }

    public AdFrame$Builder clearAdSpaceLayout() {
        this.d = null;
        this.fieldSetFlags()[3] = false;
        return this;
    }

    public AdFrame$Builder clearBinding() {
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public AdFrame$Builder clearCallbacks() {
        this.e = null;
        this.fieldSetFlags()[4] = false;
        return this;
    }

    public AdFrame$Builder clearContent() {
        this.c = null;
        this.fieldSetFlags()[2] = false;
        return this;
    }

    public AdFrame$Builder clearDisplay() {
        this.b = null;
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public CharSequence getAdGuid() {
        return this.f;
    }

    public AdSpaceLayout getAdSpaceLayout() {
        return this.d;
    }

    public Integer getBinding() {
        return this.a;
    }

    public List<Callback> getCallbacks() {
        return this.e;
    }

    public CharSequence getContent() {
        return this.c;
    }

    public CharSequence getDisplay() {
        return this.b;
    }

    public boolean hasAdGuid() {
        return this.fieldSetFlags()[5];
    }

    public boolean hasAdSpaceLayout() {
        return this.fieldSetFlags()[3];
    }

    public boolean hasBinding() {
        return this.fieldSetFlags()[0];
    }

    public boolean hasCallbacks() {
        return this.fieldSetFlags()[4];
    }

    public boolean hasContent() {
        return this.fieldSetFlags()[2];
    }

    public boolean hasDisplay() {
        return this.fieldSetFlags()[1];
    }

    public AdFrame$Builder setAdGuid(CharSequence charSequence) {
        this.validate(this.fields()[5], (Object)charSequence);
        this.f = charSequence;
        this.fieldSetFlags()[5] = true;
        return this;
    }

    public AdFrame$Builder setAdSpaceLayout(AdSpaceLayout adSpaceLayout) {
        this.validate(this.fields()[3], adSpaceLayout);
        this.d = adSpaceLayout;
        this.fieldSetFlags()[3] = true;
        return this;
    }

    public AdFrame$Builder setBinding(int n2) {
        this.validate(this.fields()[0], n2);
        this.a = n2;
        this.fieldSetFlags()[0] = true;
        return this;
    }

    public AdFrame$Builder setCallbacks(List<Callback> list) {
        this.validate(this.fields()[4], list);
        this.e = list;
        this.fieldSetFlags()[4] = true;
        return this;
    }

    public AdFrame$Builder setContent(CharSequence charSequence) {
        this.validate(this.fields()[2], (Object)charSequence);
        this.c = charSequence;
        this.fieldSetFlags()[2] = true;
        return this;
    }

    public AdFrame$Builder setDisplay(CharSequence charSequence) {
        this.validate(this.fields()[1], (Object)charSequence);
        this.b = charSequence;
        this.fieldSetFlags()[1] = true;
        return this;
    }
}


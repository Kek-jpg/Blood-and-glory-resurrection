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

import com.flurry.android.SdkLogResponse;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;
import java.util.List;

public class SdkLogResponse$Builder
extends SpecificRecordBuilderBase<SdkLogResponse>
implements RecordBuilder<SdkLogResponse> {
    private CharSequence a;
    private List<CharSequence> b;

    private SdkLogResponse$Builder() {
        super(SdkLogResponse.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SdkLogResponse build() {
        try {
            SdkLogResponse sdkLogResponse = new SdkLogResponse();
            CharSequence charSequence = this.fieldSetFlags()[0] ? this.a : (CharSequence)this.defaultValue(this.fields()[0]);
            sdkLogResponse.a = charSequence;
            List list = this.fieldSetFlags()[1] ? this.b : (List)this.defaultValue(this.fields()[1]);
            sdkLogResponse.b = list;
            return sdkLogResponse;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public SdkLogResponse$Builder clearErrors() {
        this.b = null;
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public SdkLogResponse$Builder clearResult() {
        this.a = null;
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public List<CharSequence> getErrors() {
        return this.b;
    }

    public CharSequence getResult() {
        return this.a;
    }

    public boolean hasErrors() {
        return this.fieldSetFlags()[1];
    }

    public boolean hasResult() {
        return this.fieldSetFlags()[0];
    }

    public SdkLogResponse$Builder setErrors(List<CharSequence> list) {
        this.validate(this.fields()[1], list);
        this.b = list;
        this.fieldSetFlags()[1] = true;
        return this;
    }

    public SdkLogResponse$Builder setResult(CharSequence charSequence) {
        this.validate(this.fields()[0], (Object)charSequence);
        this.a = charSequence;
        this.fieldSetFlags()[0] = true;
        return this;
    }
}


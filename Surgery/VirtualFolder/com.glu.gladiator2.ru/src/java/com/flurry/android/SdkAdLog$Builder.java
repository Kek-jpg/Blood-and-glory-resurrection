/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.util.List
 */
package com.flurry.android;

import com.flurry.android.SdkAdEvent;
import com.flurry.android.SdkAdLog;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;
import java.util.List;

public class SdkAdLog$Builder
extends SpecificRecordBuilderBase<SdkAdLog>
implements RecordBuilder<SdkAdLog> {
    private long a;
    private CharSequence b;
    private List<SdkAdEvent> c;

    private SdkAdLog$Builder() {
        super(SdkAdLog.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SdkAdLog build() {
        try {
            SdkAdLog sdkAdLog = new SdkAdLog();
            long l2 = this.fieldSetFlags()[0] ? this.a : (Long)this.defaultValue(this.fields()[0]);
            sdkAdLog.a = l2;
            CharSequence charSequence = this.fieldSetFlags()[1] ? this.b : (CharSequence)this.defaultValue(this.fields()[1]);
            sdkAdLog.b = charSequence;
            List list = this.fieldSetFlags()[2] ? this.c : (List)this.defaultValue(this.fields()[2]);
            sdkAdLog.c = list;
            return sdkAdLog;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public SdkAdLog$Builder clearAdLogGUID() {
        this.b = null;
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public SdkAdLog$Builder clearSdkAdEvents() {
        this.c = null;
        this.fieldSetFlags()[2] = false;
        return this;
    }

    public SdkAdLog$Builder clearSessionId() {
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public CharSequence getAdLogGUID() {
        return this.b;
    }

    public List<SdkAdEvent> getSdkAdEvents() {
        return this.c;
    }

    public Long getSessionId() {
        return this.a;
    }

    public boolean hasAdLogGUID() {
        return this.fieldSetFlags()[1];
    }

    public boolean hasSdkAdEvents() {
        return this.fieldSetFlags()[2];
    }

    public boolean hasSessionId() {
        return this.fieldSetFlags()[0];
    }

    public SdkAdLog$Builder setAdLogGUID(CharSequence charSequence) {
        this.validate(this.fields()[1], (Object)charSequence);
        this.b = charSequence;
        this.fieldSetFlags()[1] = true;
        return this;
    }

    public SdkAdLog$Builder setSdkAdEvents(List<SdkAdEvent> list) {
        this.validate(this.fields()[2], list);
        this.c = list;
        this.fieldSetFlags()[2] = true;
        return this;
    }

    public SdkAdLog$Builder setSessionId(long l2) {
        this.validate(this.fields()[0], l2);
        this.a = l2;
        this.fieldSetFlags()[0] = true;
        return this;
    }
}


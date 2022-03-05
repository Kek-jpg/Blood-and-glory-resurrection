/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.util.List
 */
package com.flurry.android;

import com.flurry.android.AdReportedId;
import com.flurry.android.SdkAdLog;
import com.flurry.android.SdkLogRequest;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;
import java.util.List;

public class SdkLogRequest$Builder
extends SpecificRecordBuilderBase<SdkLogRequest>
implements RecordBuilder<SdkLogRequest> {
    private CharSequence a;
    private List<AdReportedId> b;
    private List<SdkAdLog> c;
    private long d;
    private boolean e;

    /* synthetic */ SdkLogRequest$Builder() {
        this(0);
    }

    private SdkLogRequest$Builder(byte by) {
        super(SdkLogRequest.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SdkLogRequest build() {
        try {
            boolean bl;
            SdkLogRequest sdkLogRequest = new SdkLogRequest();
            CharSequence charSequence = this.fieldSetFlags()[0] ? this.a : (CharSequence)this.defaultValue(this.fields()[0]);
            sdkLogRequest.a = charSequence;
            List list = this.fieldSetFlags()[1] ? this.b : (List)this.defaultValue(this.fields()[1]);
            sdkLogRequest.b = list;
            List list2 = this.fieldSetFlags()[2] ? this.c : (List)this.defaultValue(this.fields()[2]);
            sdkLogRequest.c = list2;
            long l2 = this.fieldSetFlags()[3] ? this.d : (Long)this.defaultValue(this.fields()[3]);
            sdkLogRequest.d = l2;
            boolean bl2 = this.fieldSetFlags()[4] ? this.e : (bl = ((Boolean)this.defaultValue(this.fields()[4])).booleanValue());
            sdkLogRequest.e = bl2;
            return sdkLogRequest;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public SdkLogRequest$Builder clearAdReportedIds() {
        this.b = null;
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public SdkLogRequest$Builder clearAgentTimestamp() {
        this.fieldSetFlags()[3] = false;
        return this;
    }

    public SdkLogRequest$Builder clearApiKey() {
        this.a = null;
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public SdkLogRequest$Builder clearSdkAdLogs() {
        this.c = null;
        this.fieldSetFlags()[2] = false;
        return this;
    }

    public SdkLogRequest$Builder clearTestDevice() {
        this.fieldSetFlags()[4] = false;
        return this;
    }

    public List<AdReportedId> getAdReportedIds() {
        return this.b;
    }

    public Long getAgentTimestamp() {
        return this.d;
    }

    public CharSequence getApiKey() {
        return this.a;
    }

    public List<SdkAdLog> getSdkAdLogs() {
        return this.c;
    }

    public Boolean getTestDevice() {
        return this.e;
    }

    public boolean hasAdReportedIds() {
        return this.fieldSetFlags()[1];
    }

    public boolean hasAgentTimestamp() {
        return this.fieldSetFlags()[3];
    }

    public boolean hasApiKey() {
        return this.fieldSetFlags()[0];
    }

    public boolean hasSdkAdLogs() {
        return this.fieldSetFlags()[2];
    }

    public boolean hasTestDevice() {
        return this.fieldSetFlags()[4];
    }

    public SdkLogRequest$Builder setAdReportedIds(List<AdReportedId> list) {
        this.validate(this.fields()[1], list);
        this.b = list;
        this.fieldSetFlags()[1] = true;
        return this;
    }

    public SdkLogRequest$Builder setAgentTimestamp(long l2) {
        this.validate(this.fields()[3], l2);
        this.d = l2;
        this.fieldSetFlags()[3] = true;
        return this;
    }

    public SdkLogRequest$Builder setApiKey(CharSequence charSequence) {
        this.validate(this.fields()[0], (Object)charSequence);
        this.a = charSequence;
        this.fieldSetFlags()[0] = true;
        return this;
    }

    public SdkLogRequest$Builder setSdkAdLogs(List<SdkAdLog> list) {
        this.validate(this.fields()[2], list);
        this.c = list;
        this.fieldSetFlags()[2] = true;
        return this;
    }

    public SdkLogRequest$Builder setTestDevice(boolean bl) {
        this.validate(this.fields()[4], bl);
        this.e = bl;
        this.fieldSetFlags()[4] = true;
        return this;
    }
}


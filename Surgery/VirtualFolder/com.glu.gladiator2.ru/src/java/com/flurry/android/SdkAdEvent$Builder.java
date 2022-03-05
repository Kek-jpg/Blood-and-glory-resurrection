/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.util.Map
 */
package com.flurry.android;

import com.flurry.android.SdkAdEvent;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;
import java.util.Map;

public class SdkAdEvent$Builder
extends SpecificRecordBuilderBase<SdkAdEvent>
implements RecordBuilder<SdkAdEvent> {
    private CharSequence a;
    private Map<CharSequence, CharSequence> b;
    private long c;

    private SdkAdEvent$Builder() {
        super(SdkAdEvent.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SdkAdEvent build() {
        try {
            long l2;
            SdkAdEvent sdkAdEvent = new SdkAdEvent();
            CharSequence charSequence = this.fieldSetFlags()[0] ? this.a : (CharSequence)this.defaultValue(this.fields()[0]);
            sdkAdEvent.a = charSequence;
            Map map = this.fieldSetFlags()[1] ? this.b : (Map)this.defaultValue(this.fields()[1]);
            sdkAdEvent.b = map;
            long l3 = this.fieldSetFlags()[2] ? this.c : (l2 = ((Long)this.defaultValue(this.fields()[2])).longValue());
            sdkAdEvent.c = l3;
            return sdkAdEvent;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public SdkAdEvent$Builder clearParams() {
        this.b = null;
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public SdkAdEvent$Builder clearTimeOffset() {
        this.fieldSetFlags()[2] = false;
        return this;
    }

    public SdkAdEvent$Builder clearType() {
        this.a = null;
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public Map<CharSequence, CharSequence> getParams() {
        return this.b;
    }

    public Long getTimeOffset() {
        return this.c;
    }

    public CharSequence getType() {
        return this.a;
    }

    public boolean hasParams() {
        return this.fieldSetFlags()[1];
    }

    public boolean hasTimeOffset() {
        return this.fieldSetFlags()[2];
    }

    public boolean hasType() {
        return this.fieldSetFlags()[0];
    }

    public SdkAdEvent$Builder setParams(Map<CharSequence, CharSequence> map) {
        this.validate(this.fields()[1], map);
        this.b = map;
        this.fieldSetFlags()[1] = true;
        return this;
    }

    public SdkAdEvent$Builder setTimeOffset(long l2) {
        this.validate(this.fields()[2], l2);
        this.c = l2;
        this.fieldSetFlags()[2] = true;
        return this;
    }

    public SdkAdEvent$Builder setType(CharSequence charSequence) {
        this.validate(this.fields()[0], (Object)charSequence);
        this.a = charSequence;
        this.fieldSetFlags()[0] = true;
        return this;
    }
}


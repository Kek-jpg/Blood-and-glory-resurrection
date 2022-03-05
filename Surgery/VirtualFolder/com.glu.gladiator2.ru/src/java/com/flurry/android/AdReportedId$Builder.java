/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.nio.ByteBuffer
 */
package com.flurry.android;

import com.flurry.android.AdReportedId;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;
import java.nio.ByteBuffer;

public class AdReportedId$Builder
extends SpecificRecordBuilderBase<AdReportedId>
implements RecordBuilder<AdReportedId> {
    private int a;
    private ByteBuffer b;

    /* synthetic */ AdReportedId$Builder() {
        this(0);
    }

    private AdReportedId$Builder(byte by) {
        super(AdReportedId.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AdReportedId build() {
        try {
            AdReportedId adReportedId = new AdReportedId();
            int n2 = this.fieldSetFlags()[0] ? this.a : (Integer)this.defaultValue(this.fields()[0]);
            adReportedId.a = n2;
            ByteBuffer byteBuffer = this.fieldSetFlags()[1] ? this.b : (ByteBuffer)this.defaultValue(this.fields()[1]);
            adReportedId.b = byteBuffer;
            return adReportedId;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public AdReportedId$Builder clearId() {
        this.b = null;
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public AdReportedId$Builder clearType() {
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public ByteBuffer getId() {
        return this.b;
    }

    public Integer getType() {
        return this.a;
    }

    public boolean hasId() {
        return this.fieldSetFlags()[1];
    }

    public boolean hasType() {
        return this.fieldSetFlags()[0];
    }

    public AdReportedId$Builder setId(ByteBuffer byteBuffer) {
        this.validate(this.fields()[1], (Object)byteBuffer);
        this.b = byteBuffer;
        this.fieldSetFlags()[1] = true;
        return this;
    }

    public AdReportedId$Builder setType(int n2) {
        this.validate(this.fields()[0], n2);
        this.a = n2;
        this.fieldSetFlags()[0] = true;
        return this;
    }
}


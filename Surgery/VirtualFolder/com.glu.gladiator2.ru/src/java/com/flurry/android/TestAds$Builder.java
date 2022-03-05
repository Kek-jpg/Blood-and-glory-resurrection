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

import com.flurry.android.TestAds;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;

public class TestAds$Builder
extends SpecificRecordBuilderBase<TestAds>
implements RecordBuilder<TestAds> {
    private int a;

    /* synthetic */ TestAds$Builder() {
        this(0);
    }

    private TestAds$Builder(byte by) {
        super(TestAds.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public TestAds build() {
        try {
            int n2;
            TestAds testAds = new TestAds();
            int n3 = this.fieldSetFlags()[0] ? this.a : (n2 = ((Integer)this.defaultValue(this.fields()[0])).intValue());
            testAds.a = n3;
            return testAds;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public TestAds$Builder clearAdspacePlacement() {
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public Integer getAdspacePlacement() {
        return this.a;
    }

    public boolean hasAdspacePlacement() {
        return this.fieldSetFlags()[0];
    }

    public TestAds$Builder setAdspacePlacement(int n2) {
        this.validate(this.fields()[0], n2);
        this.a = n2;
        this.fieldSetFlags()[0] = true;
        return this;
    }
}


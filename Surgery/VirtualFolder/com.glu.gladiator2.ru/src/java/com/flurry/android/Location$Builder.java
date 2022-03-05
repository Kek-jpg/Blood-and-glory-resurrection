/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.Throwable
 */
package com.flurry.android;

import com.flurry.android.Location;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;

public class Location$Builder
extends SpecificRecordBuilderBase<Location>
implements RecordBuilder<Location> {
    private float a;
    private float b;

    /* synthetic */ Location$Builder() {
        this(0);
    }

    private Location$Builder(byte by) {
        super(Location.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Location build() {
        try {
            float f2;
            Location location = new Location();
            float f3 = this.fieldSetFlags()[0] ? this.a : ((Float)this.defaultValue(this.fields()[0])).floatValue();
            location.a = f3;
            float f4 = this.fieldSetFlags()[1] ? this.b : (f2 = ((Float)this.defaultValue(this.fields()[1])).floatValue());
            location.b = f4;
            return location;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public Location$Builder clearLat() {
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public Location$Builder clearLon() {
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public Float getLat() {
        return Float.valueOf((float)this.a);
    }

    public Float getLon() {
        return Float.valueOf((float)this.b);
    }

    public boolean hasLat() {
        return this.fieldSetFlags()[0];
    }

    public boolean hasLon() {
        return this.fieldSetFlags()[1];
    }

    public Location$Builder setLat(float f2) {
        this.validate(this.fields()[0], (Object)Float.valueOf((float)f2));
        this.a = f2;
        this.fieldSetFlags()[0] = true;
        return this;
    }

    public Location$Builder setLon(float f2) {
        this.validate(this.fields()[1], (Object)Float.valueOf((float)f2));
        this.b = f2;
        this.fieldSetFlags()[1] = true;
        return this;
    }
}


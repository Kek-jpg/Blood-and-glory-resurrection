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

import com.flurry.android.Callback;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.specific.SpecificRecordBuilderBase;
import java.util.List;

public class Callback$Builder
extends SpecificRecordBuilderBase<Callback>
implements RecordBuilder<Callback> {
    private CharSequence a;
    private List<CharSequence> b;

    private Callback$Builder() {
        super(Callback.SCHEMA$);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Callback build() {
        try {
            Callback callback = new Callback();
            CharSequence charSequence = this.fieldSetFlags()[0] ? this.a : (CharSequence)this.defaultValue(this.fields()[0]);
            callback.a = charSequence;
            List list = this.fieldSetFlags()[1] ? this.b : (List)this.defaultValue(this.fields()[1]);
            callback.b = list;
            return callback;
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
    }

    public Callback$Builder clearActions() {
        this.b = null;
        this.fieldSetFlags()[1] = false;
        return this;
    }

    public Callback$Builder clearEvent() {
        this.a = null;
        this.fieldSetFlags()[0] = false;
        return this;
    }

    public List<CharSequence> getActions() {
        return this.b;
    }

    public CharSequence getEvent() {
        return this.a;
    }

    public boolean hasActions() {
        return this.fieldSetFlags()[1];
    }

    public boolean hasEvent() {
        return this.fieldSetFlags()[0];
    }

    public Callback$Builder setActions(List<CharSequence> list) {
        this.validate(this.fields()[1], list);
        this.b = list;
        this.fieldSetFlags()[1] = true;
        return this;
    }

    public Callback$Builder setEvent(CharSequence charSequence) {
        this.validate(this.fields()[0], (Object)charSequence);
        this.a = charSequence;
        this.fieldSetFlags()[0] = true;
        return this;
    }
}


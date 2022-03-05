/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.Throwable
 */
package com.flurry.org.apache.avro.specific;

import com.flurry.org.apache.avro.AvroRemoteException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.specific.SpecificData;
import com.flurry.org.apache.avro.specific.SpecificRecord;

public abstract class SpecificExceptionBase
extends AvroRemoteException
implements SpecificRecord {
    public SpecificExceptionBase() {
    }

    public SpecificExceptionBase(Object object) {
        super(object);
    }

    public SpecificExceptionBase(Object object, Throwable throwable) {
        super(object, throwable);
    }

    public SpecificExceptionBase(Throwable throwable) {
        super(throwable);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6 : {
            block5 : {
                if (object == this) break block5;
                if (!(object instanceof SpecificExceptionBase)) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                if (SpecificData.get().compare(this, object, this.getSchema()) != 0) break block6;
            }
            return true;
        }
        return false;
    }

    @Override
    public abstract Object get(int var1);

    @Override
    public abstract Schema getSchema();

    public int hashCode() {
        return SpecificData.get().hashCode(this, this.getSchema());
    }

    @Override
    public abstract void put(int var1, Object var2);
}


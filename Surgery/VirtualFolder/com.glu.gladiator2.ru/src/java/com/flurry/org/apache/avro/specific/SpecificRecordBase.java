/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Comparable
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.apache.avro.specific;

import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.specific.SpecificData;
import com.flurry.org.apache.avro.specific.SpecificRecord;

public abstract class SpecificRecordBase
implements SpecificRecord,
Comparable<SpecificRecord> {
    public int compareTo(SpecificRecord specificRecord) {
        return SpecificData.get().compare(this, specificRecord, this.getSchema());
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6 : {
            block5 : {
                if (object == this) break block5;
                if (!(object instanceof SpecificRecord)) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                if (this.compareTo((SpecificRecord)object) != 0) break block6;
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

    public String toString() {
        return SpecificData.get().toString(this);
    }
}


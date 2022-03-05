/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.apache.avro;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;

public class UnresolvedUnionException
extends AvroRuntimeException {
    private Schema unionSchema;
    private Object unresolvedDatum;

    public UnresolvedUnionException(Schema schema, Object object) {
        super("Not in union " + schema + ": " + object);
        this.unionSchema = schema;
        this.unresolvedDatum = object;
    }

    public Schema getUnionSchema() {
        return this.unionSchema;
    }

    public Object getUnresolvedDatum() {
        return this.unresolvedDatum;
    }
}


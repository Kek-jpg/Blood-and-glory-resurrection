/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.String
 *  java.util.Arrays
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.io.Encoder;
import java.io.IOException;
import java.util.Arrays;

public abstract class ParsingEncoder
extends Encoder {
    private long[] counts = new long[10];
    protected int pos = -1;

    protected final int depth() {
        return this.pos;
    }

    protected final void pop() {
        if (this.counts[this.pos] != 0L) {
            throw new AvroTypeException("Incorrect number of items written. " + this.counts[this.pos] + " more required.");
        }
        this.pos = -1 + this.pos;
    }

    protected final void push() {
        int n2;
        if (this.pos == this.counts.length) {
            this.counts = Arrays.copyOf((long[])this.counts, (int)(10 + this.pos));
        }
        long[] arrl = this.counts;
        this.pos = n2 = 1 + this.pos;
        arrl[n2] = 0L;
    }

    @Override
    public void setItemCount(long l2) throws IOException {
        if (this.counts[this.pos] != 0L) {
            throw new AvroTypeException("Incorrect number of items written. " + this.counts[this.pos] + " more required.");
        }
        this.counts[this.pos] = l2;
    }

    @Override
    public void startItem() throws IOException {
        long[] arrl = this.counts;
        int n2 = this.pos;
        arrl[n2] = arrl[n2] - 1L;
    }
}


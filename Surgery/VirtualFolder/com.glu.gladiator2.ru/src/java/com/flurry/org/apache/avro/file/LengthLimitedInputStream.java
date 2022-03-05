/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.FilterInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Math
 */
package com.flurry.org.apache.avro.file;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

class LengthLimitedInputStream
extends FilterInputStream {
    private long remaining;

    protected LengthLimitedInputStream(InputStream inputStream, long l2) {
        super(inputStream);
        this.remaining = l2;
    }

    private int remainingInt() {
        return (int)Math.min((long)this.remaining, (long)Integer.MAX_VALUE);
    }

    public int available() throws IOException {
        return Math.min((int)super.available(), (int)this.remainingInt());
    }

    public int read() throws IOException {
        if (this.remaining > 0L) {
            int n2 = super.read();
            if (n2 != -1) {
                --this.remaining;
            }
            return n2;
        }
        return -1;
    }

    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int read(byte[] arrby, int n2, int n3) throws IOException {
        int n4;
        if (this.remaining == 0L) {
            return -1;
        }
        if ((long)n3 > this.remaining) {
            n3 = LengthLimitedInputStream.super.remainingInt();
        }
        if ((n4 = super.read(arrby, n2, n3)) == -1) return n4;
        this.remaining -= (long)n4;
        return n4;
    }

    public long skip(long l2) throws IOException {
        long l3 = super.skip(Math.min((long)this.remaining, (long)l2));
        this.remaining -= l3;
        return l3;
    }
}


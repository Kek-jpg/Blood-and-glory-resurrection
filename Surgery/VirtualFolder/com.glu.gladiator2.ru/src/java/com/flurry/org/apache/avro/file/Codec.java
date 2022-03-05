/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.ByteBuffer
 */
package com.flurry.org.apache.avro.file;

import java.io.IOException;
import java.nio.ByteBuffer;

abstract class Codec {
    Codec() {
    }

    abstract ByteBuffer compress(ByteBuffer var1) throws IOException;

    abstract ByteBuffer decompress(ByteBuffer var1) throws IOException;

    public abstract boolean equals(Object var1);

    abstract String getName();

    public abstract int hashCode();

    public String toString() {
        return this.getName();
    }
}


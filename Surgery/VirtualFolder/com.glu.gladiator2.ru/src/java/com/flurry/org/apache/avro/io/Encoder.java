/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Flushable
 *  java.io.IOException
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.ByteBuffer
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.util.Utf8;
import java.io.Flushable;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class Encoder
implements Flushable {
    public abstract void setItemCount(long var1) throws IOException;

    public abstract void startItem() throws IOException;

    public abstract void writeArrayEnd() throws IOException;

    public abstract void writeArrayStart() throws IOException;

    public abstract void writeBoolean(boolean var1) throws IOException;

    public abstract void writeBytes(ByteBuffer var1) throws IOException;

    public void writeBytes(byte[] arrby) throws IOException {
        this.writeBytes(arrby, 0, arrby.length);
    }

    public abstract void writeBytes(byte[] var1, int var2, int var3) throws IOException;

    public abstract void writeDouble(double var1) throws IOException;

    public abstract void writeEnum(int var1) throws IOException;

    public void writeFixed(byte[] arrby) throws IOException {
        this.writeFixed(arrby, 0, arrby.length);
    }

    public abstract void writeFixed(byte[] var1, int var2, int var3) throws IOException;

    public abstract void writeFloat(float var1) throws IOException;

    public abstract void writeIndex(int var1) throws IOException;

    public abstract void writeInt(int var1) throws IOException;

    public abstract void writeLong(long var1) throws IOException;

    public abstract void writeMapEnd() throws IOException;

    public abstract void writeMapStart() throws IOException;

    public abstract void writeNull() throws IOException;

    public abstract void writeString(Utf8 var1) throws IOException;

    public void writeString(CharSequence charSequence) throws IOException {
        if (charSequence instanceof Utf8) {
            this.writeString((Utf8)charSequence);
            return;
        }
        this.writeString(charSequence.toString());
    }

    public void writeString(String string) throws IOException {
        this.writeString(new Utf8(string));
    }
}


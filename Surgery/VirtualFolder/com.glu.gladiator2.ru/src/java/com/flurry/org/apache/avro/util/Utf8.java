/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.UnsupportedEncodingException
 *  java.lang.CharSequence
 *  java.lang.Comparable
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package com.flurry.org.apache.avro.util;

import com.flurry.org.apache.avro.io.BinaryData;
import java.io.UnsupportedEncodingException;

public class Utf8
implements Comparable<Utf8>,
CharSequence {
    private static final byte[] EMPTY = new byte[0];
    private byte[] bytes;
    private int length;
    private String string;

    public Utf8() {
        this.bytes = EMPTY;
    }

    public Utf8(Utf8 utf8) {
        this.bytes = EMPTY;
        this.length = utf8.length;
        this.bytes = new byte[utf8.length];
        System.arraycopy((Object)utf8.bytes, (int)0, (Object)this.bytes, (int)0, (int)this.length);
        this.string = utf8.string;
    }

    public Utf8(String string) {
        this.bytes = EMPTY;
        this.bytes = Utf8.getBytesFor(string);
        this.length = this.bytes.length;
        this.string = string;
    }

    public Utf8(byte[] arrby) {
        this.bytes = EMPTY;
        this.bytes = arrby;
        this.length = arrby.length;
    }

    public static final byte[] getBytesFor(String string) {
        try {
            byte[] arrby = string.getBytes("UTF-8");
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return new byte[0];
        }
    }

    public char charAt(int n2) {
        return this.toString().charAt(n2);
    }

    public int compareTo(Utf8 utf8) {
        return BinaryData.compareBytes(this.bytes, 0, this.length, utf8.bytes, 0, utf8.length);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (object != this) {
            if (!(object instanceof Utf8)) {
                return false;
            }
            Utf8 utf8 = (Utf8)object;
            if (this.length != utf8.length) {
                return false;
            }
            byte[] arrby = utf8.bytes;
            for (int i2 = 0; i2 < this.length; ++i2) {
                if (this.bytes[i2] == arrby[i2]) continue;
                return false;
            }
        }
        return true;
    }

    public int getByteLength() {
        return this.length;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public int getLength() {
        return this.length;
    }

    public int hashCode() {
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.length) {
            n2 = n2 * 31 + this.bytes[n3];
            ++n3;
        }
        return n2;
    }

    public int length() {
        return this.toString().length();
    }

    public Utf8 setByteLength(int n2) {
        if (this.length < n2) {
            byte[] arrby = new byte[n2];
            System.arraycopy((Object)this.bytes, (int)0, (Object)arrby, (int)0, (int)this.length);
            this.bytes = arrby;
        }
        this.length = n2;
        this.string = null;
        return this;
    }

    public Utf8 setLength(int n2) {
        return this.setByteLength(n2);
    }

    public CharSequence subSequence(int n2, int n3) {
        return this.toString().subSequence(n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        if (this.string != null) return this.string;
        try {
            this.string = new String(this.bytes, 0, this.length, "UTF-8");
            return this.string;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return this.string;
        }
    }
}


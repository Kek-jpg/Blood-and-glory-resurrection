/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  java.nio.ByteBuffer
 *  java.security.MessageDigest
 *  java.util.zip.CRC32
 */
package com.flurry.android;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.zip.CRC32;

public class CrcMessageDigest
extends MessageDigest {
    private CRC32 a = new CRC32();

    public CrcMessageDigest() {
        super("CRC");
    }

    protected byte[] engineDigest() {
        long l2 = this.a.getValue();
        byte[] arrby = new byte[]{(byte)((-16777216L & l2) >> 24), (byte)((0xFF0000L & l2) >> 16), (byte)((65280L & l2) >> 8), (byte)(l2 & 255L)};
        return arrby;
    }

    protected void engineReset() {
        this.a.reset();
    }

    protected void engineUpdate(byte by) {
        this.a.update((int)by);
    }

    protected void engineUpdate(byte[] arrby, int n2, int n3) {
        this.a.update(arrby, n2, n3);
    }

    public int getChecksum() {
        return ByteBuffer.wrap((byte[])this.engineDigest()).getInt();
    }

    public byte[] getDigest() {
        return this.engineDigest();
    }
}


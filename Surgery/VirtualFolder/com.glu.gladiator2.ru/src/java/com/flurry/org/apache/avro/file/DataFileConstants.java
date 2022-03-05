/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.apache.avro.file;

public class DataFileConstants {
    public static final String CODEC = "avro.codec";
    public static final int DEFAULT_SYNC_INTERVAL = 16000;
    public static final String DEFLATE_CODEC = "deflate";
    public static final long FOOTER_BLOCK = -1L;
    public static final byte[] MAGIC = new byte[]{79, 98, 106, 1};
    public static final String NULL_CODEC = "null";
    public static final String SCHEMA = "avro.schema";
    public static final String SNAPPY_CODEC = "snappy";
    public static final int SYNC_SIZE = 16;
    public static final byte VERSION = 1;

    private DataFileConstants() {
    }
}


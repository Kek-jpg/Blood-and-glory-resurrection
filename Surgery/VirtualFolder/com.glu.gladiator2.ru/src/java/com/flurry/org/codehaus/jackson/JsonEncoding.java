/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson;

public final class JsonEncoding
extends Enum<JsonEncoding> {
    private static final /* synthetic */ JsonEncoding[] $VALUES;
    public static final /* enum */ JsonEncoding UTF16_BE;
    public static final /* enum */ JsonEncoding UTF16_LE;
    public static final /* enum */ JsonEncoding UTF32_BE;
    public static final /* enum */ JsonEncoding UTF32_LE;
    public static final /* enum */ JsonEncoding UTF8;
    protected final boolean _bigEndian;
    protected final String _javaName;

    static {
        UTF8 = new JsonEncoding("UTF-8", false);
        UTF16_BE = new JsonEncoding("UTF-16BE", true);
        UTF16_LE = new JsonEncoding("UTF-16LE", false);
        UTF32_BE = new JsonEncoding("UTF-32BE", true);
        UTF32_LE = new JsonEncoding("UTF-32LE", false);
        JsonEncoding[] arrjsonEncoding = new JsonEncoding[]{UTF8, UTF16_BE, UTF16_LE, UTF32_BE, UTF32_LE};
        $VALUES = arrjsonEncoding;
    }

    private JsonEncoding(String string2, boolean bl) {
        this._javaName = string2;
        this._bigEndian = bl;
    }

    public static JsonEncoding valueOf(String string) {
        return (JsonEncoding)Enum.valueOf(JsonEncoding.class, (String)string);
    }

    public static JsonEncoding[] values() {
        return (JsonEncoding[])$VALUES.clone();
    }

    public String getJavaName() {
        return this._javaName;
    }

    public boolean isBigEndian() {
        return this._bigEndian;
    }
}


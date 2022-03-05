/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson;

public interface SerializableString {
    public char[] asQuotedChars();

    public byte[] asQuotedUTF8();

    public byte[] asUnquotedUTF8();

    public int charLength();

    public String getValue();
}


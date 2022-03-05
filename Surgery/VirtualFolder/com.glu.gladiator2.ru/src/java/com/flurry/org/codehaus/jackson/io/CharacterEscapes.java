/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.System
 */
package com.flurry.org.codehaus.jackson.io;

import com.flurry.org.codehaus.jackson.SerializableString;
import com.flurry.org.codehaus.jackson.util.CharTypes;

public abstract class CharacterEscapes {
    public static final int ESCAPE_CUSTOM = -2;
    public static final int ESCAPE_NONE = 0;
    public static final int ESCAPE_STANDARD = -1;

    public static int[] standardAsciiEscapesForJSON() {
        int[] arrn = CharTypes.get7BitOutputEscapes();
        int[] arrn2 = new int[arrn.length];
        System.arraycopy((Object)arrn, (int)0, (Object)arrn2, (int)0, (int)arrn.length);
        return arrn2;
    }

    public abstract int[] getEscapeCodesForAscii();

    public abstract SerializableString getEscapeSequence(int var1);
}


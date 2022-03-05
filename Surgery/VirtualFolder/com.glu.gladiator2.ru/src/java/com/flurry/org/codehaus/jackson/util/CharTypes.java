/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.util.Arrays
 */
package com.flurry.org.codehaus.jackson.util;

import java.util.Arrays;

public final class CharTypes {
    private static final byte[] HEX_BYTES;
    private static final char[] HEX_CHARS;
    static final int[] sHexValues;
    static final int[] sInputCodes;
    static final int[] sInputCodesComment;
    static final int[] sInputCodesJsNames;
    static final int[] sInputCodesUtf8;
    static final int[] sInputCodesUtf8JsNames;
    static final int[] sOutputEscapes128;

    /*
     * Enabled aggressive block sorting
     */
    static {
        HEX_CHARS = "0123456789ABCDEF".toCharArray();
        int n = HEX_CHARS.length;
        HEX_BYTES = new byte[n];
        for (int i2 = 0; i2 < n; ++i2) {
            CharTypes.HEX_BYTES[i2] = (byte)HEX_CHARS[i2];
        }
        int[] arrn = new int[256];
        for (int i3 = 0; i3 < 32; ++i3) {
            arrn[i3] = -1;
        }
        arrn[34] = 1;
        arrn[92] = 1;
        sInputCodes = arrn;
        int[] arrn2 = new int[sInputCodes.length];
        System.arraycopy((Object)sInputCodes, (int)0, (Object)arrn2, (int)0, (int)sInputCodes.length);
        for (int i4 = 128; i4 < 256; ++i4) {
            int n2 = (i4 & 224) == 192 ? 2 : ((i4 & 240) == 224 ? 3 : ((i4 & 248) == 240 ? 4 : -1));
            arrn2[i4] = n2;
        }
        sInputCodesUtf8 = arrn2;
        int[] arrn3 = new int[256];
        Arrays.fill((int[])arrn3, (int)-1);
        for (int i5 = 33; i5 < 256; ++i5) {
            if (!Character.isJavaIdentifierPart((char)((char)i5))) continue;
            arrn3[i5] = 0;
        }
        arrn3[64] = 0;
        arrn3[35] = 0;
        arrn3[42] = 0;
        arrn3[45] = 0;
        arrn3[43] = 0;
        sInputCodesJsNames = arrn3;
        int[] arrn4 = new int[256];
        System.arraycopy((Object)sInputCodesJsNames, (int)0, (Object)arrn4, (int)0, (int)sInputCodesJsNames.length);
        Arrays.fill((int[])arrn4, (int)128, (int)128, (int)0);
        sInputCodesUtf8JsNames = arrn4;
        sInputCodesComment = new int[256];
        System.arraycopy((Object)sInputCodesUtf8, (int)128, (Object)sInputCodesComment, (int)128, (int)128);
        Arrays.fill((int[])sInputCodesComment, (int)0, (int)32, (int)-1);
        CharTypes.sInputCodesComment[9] = 0;
        CharTypes.sInputCodesComment[10] = 10;
        CharTypes.sInputCodesComment[13] = 13;
        CharTypes.sInputCodesComment[42] = 42;
        int[] arrn5 = new int[128];
        for (int i6 = 0; i6 < 32; ++i6) {
            arrn5[i6] = -1;
        }
        arrn5[34] = 34;
        arrn5[92] = 92;
        arrn5[8] = 98;
        arrn5[9] = 116;
        arrn5[12] = 102;
        arrn5[10] = 110;
        arrn5[13] = 114;
        sOutputEscapes128 = arrn5;
        sHexValues = new int[128];
        Arrays.fill((int[])sHexValues, (int)-1);
        int n3 = 0;
        while (n3 < 10) {
            CharTypes.sHexValues[n3 + 48] = n3++;
        }
        int n4 = 0;
        while (n4 < 6) {
            CharTypes.sHexValues[n4 + 97] = n4 + 10;
            CharTypes.sHexValues[n4 + 65] = n4 + 10;
            ++n4;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void appendQuoted(StringBuilder stringBuilder, String string2) {
        int[] arrn = sOutputEscapes128;
        int n = arrn.length;
        int n2 = 0;
        int n3 = string2.length();
        while (n2 < n3) {
            char c2 = string2.charAt(n2);
            if (c2 >= n || arrn[c2] == 0) {
                stringBuilder.append(c2);
            } else {
                stringBuilder.append('\\');
                int n4 = arrn[c2];
                if (n4 < 0) {
                    stringBuilder.append('u');
                    stringBuilder.append('0');
                    stringBuilder.append('0');
                    int n5 = -(n4 + 1);
                    stringBuilder.append(HEX_CHARS[n5 >> 4]);
                    stringBuilder.append(HEX_CHARS[n5 & 15]);
                } else {
                    stringBuilder.append((char)n4);
                }
            }
            ++n2;
        }
        return;
    }

    public static int charToHex(int n) {
        if (n > 127) {
            return -1;
        }
        return sHexValues[n];
    }

    public static byte[] copyHexBytes() {
        return (byte[])HEX_BYTES.clone();
    }

    public static char[] copyHexChars() {
        return (char[])HEX_CHARS.clone();
    }

    public static final int[] get7BitOutputEscapes() {
        return sOutputEscapes128;
    }

    public static final int[] getInputCodeComment() {
        return sInputCodesComment;
    }

    public static final int[] getInputCodeLatin1() {
        return sInputCodes;
    }

    public static final int[] getInputCodeLatin1JsNames() {
        return sInputCodesJsNames;
    }

    public static final int[] getInputCodeUtf8() {
        return sInputCodesUtf8;
    }

    public static final int[] getInputCodeUtf8JsNames() {
        return sInputCodesUtf8JsNames;
    }
}


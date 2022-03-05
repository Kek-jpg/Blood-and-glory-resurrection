/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.util.Arrays
 */
package com.flurry.org.codehaus.jackson;

import java.util.Arrays;

public final class Base64Variant {
    public static final int BASE64_VALUE_INVALID = -1;
    public static final int BASE64_VALUE_PADDING = -2;
    static final char PADDING_CHAR_NONE;
    private final int[] _asciiToBase64;
    private final byte[] _base64ToAsciiB;
    private final char[] _base64ToAsciiC;
    final int _maxLineLength;
    final String _name;
    final char _paddingChar;
    final boolean _usesPadding;

    public Base64Variant(Base64Variant base64Variant, String string, int n2) {
        super(base64Variant, string, base64Variant._usesPadding, base64Variant._paddingChar, n2);
    }

    public Base64Variant(Base64Variant base64Variant, String string, boolean bl, char c2, int n2) {
        this._asciiToBase64 = new int[128];
        this._base64ToAsciiC = new char[64];
        this._base64ToAsciiB = new byte[64];
        this._name = string;
        byte[] arrby = base64Variant._base64ToAsciiB;
        System.arraycopy((Object)arrby, (int)0, (Object)this._base64ToAsciiB, (int)0, (int)arrby.length);
        char[] arrc = base64Variant._base64ToAsciiC;
        System.arraycopy((Object)arrc, (int)0, (Object)this._base64ToAsciiC, (int)0, (int)arrc.length);
        int[] arrn = base64Variant._asciiToBase64;
        System.arraycopy((Object)arrn, (int)0, (Object)this._asciiToBase64, (int)0, (int)arrn.length);
        this._usesPadding = bl;
        this._paddingChar = c2;
        this._maxLineLength = n2;
    }

    public Base64Variant(String string, String string2, boolean bl, char c2, int n2) {
        this._asciiToBase64 = new int[128];
        this._base64ToAsciiC = new char[64];
        this._base64ToAsciiB = new byte[64];
        this._name = string;
        this._usesPadding = bl;
        this._paddingChar = c2;
        this._maxLineLength = n2;
        int n3 = string2.length();
        if (n3 != 64) {
            throw new IllegalArgumentException("Base64Alphabet length must be exactly 64 (was " + n3 + ")");
        }
        string2.getChars(0, n3, this._base64ToAsciiC, 0);
        Arrays.fill((int[])this._asciiToBase64, (int)-1);
        int n4 = 0;
        while (n4 < n3) {
            char c3 = this._base64ToAsciiC[n4];
            this._base64ToAsciiB[n4] = (byte)c3;
            this._asciiToBase64[c3] = n4++;
        }
        if (bl) {
            this._asciiToBase64[c2] = -2;
        }
    }

    public int decodeBase64Byte(byte by) {
        if (by <= 127) {
            return this._asciiToBase64[by];
        }
        return -1;
    }

    public int decodeBase64Char(char c2) {
        if (c2 <= '') {
            return this._asciiToBase64[c2];
        }
        return -1;
    }

    public int decodeBase64Char(int n2) {
        if (n2 <= 127) {
            return this._asciiToBase64[n2];
        }
        return -1;
    }

    public String encode(byte[] arrby) {
        return this.encode(arrby, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String encode(byte[] arrby, boolean bl) {
        int n2 = arrby.length;
        StringBuilder stringBuilder = new StringBuilder(n2 + (n2 >> 2) + (n2 >> 3));
        if (bl) {
            stringBuilder.append('\"');
        }
        int n3 = this.getMaxLineLength() >> 2;
        int n4 = n2 - 3;
        int n5 = 0;
        while (n5 <= n4) {
            int n6 = n5 + 1;
            int n7 = arrby[n5] << 8;
            int n8 = n6 + 1;
            int n9 = (n7 | 255 & arrby[n6]) << 8;
            int n10 = n8 + 1;
            this.encodeBase64Chunk(stringBuilder, n9 | 255 & arrby[n8]);
            if (--n3 <= 0) {
                stringBuilder.append('\\');
                stringBuilder.append('n');
                n3 = this.getMaxLineLength() >> 2;
            }
            n5 = n10;
        }
        int n11 = n2 - n5;
        if (n11 > 0) {
            int n12 = n5 + 1;
            int n13 = arrby[n5] << 16;
            if (n11 == 2) {
                n12 + 1;
                n13 |= (255 & arrby[n12]) << 8;
            }
            this.encodeBase64Partial(stringBuilder, n13, n11);
        }
        if (bl) {
            stringBuilder.append('\"');
        }
        return stringBuilder.toString();
    }

    public byte encodeBase64BitsAsByte(int n2) {
        return this._base64ToAsciiB[n2];
    }

    public char encodeBase64BitsAsChar(int n2) {
        return this._base64ToAsciiC[n2];
    }

    public int encodeBase64Chunk(int n2, byte[] arrby, int n3) {
        int n4 = n3 + 1;
        arrby[n3] = this._base64ToAsciiB[63 & n2 >> 18];
        int n5 = n4 + 1;
        arrby[n4] = this._base64ToAsciiB[63 & n2 >> 12];
        int n6 = n5 + 1;
        arrby[n5] = this._base64ToAsciiB[63 & n2 >> 6];
        int n7 = n6 + 1;
        arrby[n6] = this._base64ToAsciiB[n2 & 63];
        return n7;
    }

    public int encodeBase64Chunk(int n2, char[] arrc, int n3) {
        int n4 = n3 + 1;
        arrc[n3] = this._base64ToAsciiC[63 & n2 >> 18];
        int n5 = n4 + 1;
        arrc[n4] = this._base64ToAsciiC[63 & n2 >> 12];
        int n6 = n5 + 1;
        arrc[n5] = this._base64ToAsciiC[63 & n2 >> 6];
        int n7 = n6 + 1;
        arrc[n6] = this._base64ToAsciiC[n2 & 63];
        return n7;
    }

    public void encodeBase64Chunk(StringBuilder stringBuilder, int n2) {
        stringBuilder.append(this._base64ToAsciiC[63 & n2 >> 18]);
        stringBuilder.append(this._base64ToAsciiC[63 & n2 >> 12]);
        stringBuilder.append(this._base64ToAsciiC[63 & n2 >> 6]);
        stringBuilder.append(this._base64ToAsciiC[n2 & 63]);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int encodeBase64Partial(int n2, int n3, byte[] arrby, int n4) {
        int n5 = n4 + 1;
        arrby[n4] = this._base64ToAsciiB[63 & n2 >> 18];
        int n6 = n5 + 1;
        arrby[n5] = this._base64ToAsciiB[63 & n2 >> 12];
        if (this._usesPadding) {
            byte by = (byte)this._paddingChar;
            int n7 = n6 + 1;
            byte by2 = n3 == 2 ? this._base64ToAsciiB[63 & n2 >> 6] : by;
            arrby[n6] = by2;
            n6 = n7 + 1;
            arrby[n7] = by;
            return n6;
        } else {
            if (n3 != 2) return n6;
            {
                int n8 = n6 + 1;
                arrby[n6] = this._base64ToAsciiB[63 & n2 >> 6];
                return n8;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public int encodeBase64Partial(int n2, int n3, char[] arrc, int n4) {
        int n5 = n4 + 1;
        arrc[n4] = this._base64ToAsciiC[63 & n2 >> 18];
        int n6 = n5 + 1;
        arrc[n5] = this._base64ToAsciiC[63 & n2 >> 12];
        if (this._usesPadding) {
            int n7 = n6 + 1;
            char c2 = n3 == 2 ? this._base64ToAsciiC[63 & n2 >> 6] : this._paddingChar;
            arrc[n6] = c2;
            n6 = n7 + 1;
            arrc[n7] = this._paddingChar;
            return n6;
        } else {
            if (n3 != 2) return n6;
            {
                int n8 = n6 + 1;
                arrc[n6] = this._base64ToAsciiC[63 & n2 >> 6];
                return n8;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void encodeBase64Partial(StringBuilder stringBuilder, int n2, int n3) {
        stringBuilder.append(this._base64ToAsciiC[63 & n2 >> 18]);
        stringBuilder.append(this._base64ToAsciiC[63 & n2 >> 12]);
        if (this._usesPadding) {
            char c2 = n3 == 2 ? this._base64ToAsciiC[63 & n2 >> 6] : this._paddingChar;
            stringBuilder.append(c2);
            stringBuilder.append(this._paddingChar);
            return;
        } else {
            if (n3 != 2) return;
            {
                stringBuilder.append(this._base64ToAsciiC[63 & n2 >> 6]);
                return;
            }
        }
    }

    public int getMaxLineLength() {
        return this._maxLineLength;
    }

    public String getName() {
        return this._name;
    }

    public byte getPaddingByte() {
        return (byte)this._paddingChar;
    }

    public char getPaddingChar() {
        return this._paddingChar;
    }

    public String toString() {
        return this._name;
    }

    public boolean usesPadding() {
        return this._usesPadding;
    }

    public boolean usesPaddingChar(char c2) {
        return c2 == this._paddingChar;
    }

    public boolean usesPaddingChar(int n2) {
        return n2 == this._paddingChar;
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package com.unity3d.player.b;

import com.unity3d.player.b.b;

public class a {
    private static final byte[] a;
    private static final byte[] b;
    private static /* synthetic */ boolean c;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !a.class.desiredAssertionStatus();
        c = bl;
        a = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        byte[] arrby = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        b = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9};
        byte[] arrby2 = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9};
    }

    private a() {
    }

    private static int a(byte[] arrby, byte[] arrby2, int n2, byte[] arrby3) {
        if (arrby[2] == 61) {
            arrby2[n2] = (byte)((arrby3[arrby[0]] << 24 >>> 6 | arrby3[arrby[1]] << 24 >>> 12) >>> 16);
            return 1;
        }
        if (arrby[3] == 61) {
            int n3 = arrby3[arrby[0]] << 24 >>> 6 | arrby3[arrby[1]] << 24 >>> 12 | arrby3[arrby[2]] << 24 >>> 18;
            arrby2[n2] = (byte)(n3 >>> 16);
            arrby2[n2 + 1] = (byte)(n3 >>> 8);
            return 2;
        }
        int n4 = arrby3[arrby[0]] << 24 >>> 6 | arrby3[arrby[1]] << 24 >>> 12 | arrby3[arrby[2]] << 24 >>> 18 | arrby3[arrby[3]] << 24 >>> 24;
        arrby2[n2] = (byte)(n4 >> 16);
        arrby2[n2 + 1] = (byte)(n4 >> 8);
        arrby2[n2 + 2] = (byte)n4;
        return 3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String a(byte[] arrby) {
        int n2;
        int n3 = arrby.length;
        byte[] arrby2 = a;
        int n4 = 4 * ((n3 + 2) / 3);
        byte[] arrby3 = new byte[n4 + n4 / Integer.MAX_VALUE];
        int n5 = n3 - 2;
        int n6 = 0;
        int n7 = 0;
        for (n2 = 0; n2 < n5; n2 += 3, n7 += 4) {
            int n8 = arrby[n2 + 0] << 24 >>> 8 | arrby[0 + (n2 + 1)] << 24 >>> 16 | arrby[0 + (n2 + 2)] << 24 >>> 24;
            arrby3[n7] = arrby2[n8 >>> 18];
            arrby3[n7 + 1] = arrby2[63 & n8 >>> 12];
            arrby3[n7 + 2] = arrby2[63 & n8 >>> 6];
            arrby3[n7 + 3] = arrby2[n8 & 63];
            int n9 = n6 + 4;
            if (n9 == Integer.MAX_VALUE) {
                arrby3[n7 + 4] = 10;
                ++n7;
                n9 = 0;
            }
            n6 = n9;
        }
        if (n2 < n3) {
            int n10 = n2 + 0;
            int n11 = n3 - n2;
            int n12 = n11 > 0 ? arrby[n10] << 24 >>> 8 : 0;
            int n13 = n11 > 1 ? arrby[n10 + 1] << 24 >>> 16 : 0;
            int n14 = n12 | n13;
            int n15 = n11 > 2 ? arrby[n10 + 2] << 24 >>> 24 : 0;
            int n16 = n15 | n14;
            switch (n11) {
                case 3: {
                    arrby3[n7] = arrby2[n16 >>> 18];
                    arrby3[n7 + 1] = arrby2[63 & n16 >>> 12];
                    arrby3[n7 + 2] = arrby2[63 & n16 >>> 6];
                    arrby3[n7 + 3] = arrby2[n16 & 63];
                    break;
                }
                case 2: {
                    arrby3[n7] = arrby2[n16 >>> 18];
                    arrby3[n7 + 1] = arrby2[63 & n16 >>> 12];
                    arrby3[n7 + 2] = arrby2[63 & n16 >>> 6];
                    arrby3[n7 + 3] = 61;
                    break;
                }
                case 1: {
                    arrby3[n7] = arrby2[n16 >>> 18];
                    arrby3[n7 + 1] = arrby2[63 & n16 >>> 12];
                    arrby3[n7 + 2] = 61;
                    arrby3[n7 + 3] = 61;
                }
            }
            if (n6 + 4 == Integer.MAX_VALUE) {
                arrby3[n7 + 4] = 10;
                ++n7;
            }
            n7 += 4;
        }
        if (!c && n7 != arrby3.length) {
            throw new AssertionError();
        }
        return new String(arrby3, 0, arrby3.length);
    }

    public static byte[] a(String string) {
        byte[] arrby = string.getBytes();
        return a.a(arrby, arrby.length);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static byte[] a(byte[] arrby, int n2) {
        byte[] arrby2 = b;
        byte[] arrby3 = new byte[2 + n2 * 3 / 4];
        byte[] arrby4 = new byte[4];
        int n3 = 0;
        int n4 = 0;
        for (int i2 = 0; i2 < n2; ++i2) {
            int n5;
            int n6;
            byte by = (byte)(127 & arrby[i2 + 0]);
            byte by2 = arrby2[by];
            if (by2 < -5) {
                throw new b("Bad Base64 input character at " + i2 + ": " + arrby[i2 + 0] + "(decimal)");
            }
            if (by2 >= -1) {
                if (by == 61) {
                    int n7 = n2 - i2;
                    byte by3 = (byte)(127 & arrby[0 + (n2 - 1)]);
                    if (n3 == 0 || n3 == 1) {
                        throw new b("invalid padding byte '=' at byte offset " + i2);
                    }
                    if (n3 == 3 && n7 > 2 || n3 == 4 && n7 > 1) {
                        throw new b("padding byte '=' falsely signals end of encoded value at offset " + i2);
                    }
                    if (by3 == 61 || by3 == 10) break;
                    throw new b("encoded value has invalid trailing byte");
                }
                n5 = n3 + 1;
                arrby4[n3] = by;
                if (n5 == 4) {
                    n6 = n4 + a.a(arrby4, arrby3, n4, arrby2);
                    n5 = 0;
                } else {
                    n6 = n4;
                }
            } else {
                n5 = n3;
                n6 = n4;
            }
            n4 = n6;
            n3 = n5;
        }
        if (n3 != 0) {
            if (n3 == 1) {
                throw new b("single trailing character at offset " + (n2 - 1));
            }
            arrby4[n3] = 61;
            n4 += a.a(arrby4, arrby3, n4, arrby2);
        }
        byte[] arrby5 = new byte[n4];
        System.arraycopy((Object)arrby3, (int)0, (Object)arrby5, (int)0, (int)n4);
        return arrby5;
    }

    public static byte[] b(byte[] arrby) {
        return a.a(arrby, arrby.length);
    }
}


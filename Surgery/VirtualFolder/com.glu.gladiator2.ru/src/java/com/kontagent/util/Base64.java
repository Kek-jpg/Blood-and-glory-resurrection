/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.UnsupportedEncodingException
 *  java.lang.AssertionError
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package com.kontagent.util;

import java.io.UnsupportedEncodingException;

public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CRLF = 4;
    public static final int DEFAULT = 0;
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Base64.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    private Base64() {
    }

    public static byte[] decode(String string2, int n) {
        return Base64.decode(string2.getBytes(), n);
    }

    public static byte[] decode(byte[] arrby, int n) {
        return Base64.decode(arrby, 0, arrby.length, n);
    }

    public static byte[] decode(byte[] arrby, int n, int n2, int n3) {
        Decoder decoder = new Decoder(n3, new byte[n2 * 3 / 4]);
        if (!decoder.process(arrby, n, n2, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (decoder.op == decoder.output.length) {
            return decoder.output;
        }
        byte[] arrby2 = new byte[decoder.op];
        System.arraycopy((Object)decoder.output, (int)0, (Object)arrby2, (int)0, (int)decoder.op);
        return arrby2;
    }

    public static byte[] encode(byte[] arrby, int n) {
        return Base64.encode(arrby, 0, arrby.length, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static byte[] encode(byte[] arrby, int n, int n2, int n3) {
        Encoder encoder = new Encoder(n3, null);
        int n4 = 4 * (n2 / 3);
        if (encoder.do_padding) {
            if (n2 % 3 > 0) {
                n4 += 4;
            }
        } else {
            switch (n2 % 3) {
                case 0: {
                    break;
                }
                default: {
                    break;
                }
                case 1: {
                    n4 += 2;
                    break;
                }
                case 2: {
                    n4 += 3;
                }
            }
        }
        if (encoder.do_newline && n2 > 0) {
            int n5 = 1 + (n2 - 1) / 57;
            int n6 = encoder.do_cr ? 2 : 1;
            n4 += n6 * n5;
        }
        encoder.output = new byte[n4];
        encoder.process(arrby, n, n2, true);
        if (!$assertionsDisabled && encoder.op != n4) {
            throw new AssertionError();
        }
        return encoder.output;
    }

    public static String encodeToString(byte[] arrby, int n) {
        try {
            String string2 = new String(Base64.encode(arrby, n), "US-ASCII");
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError((Object)unsupportedEncodingException);
        }
    }

    public static String encodeToString(byte[] arrby, int n, int n2, int n3) {
        try {
            String string2 = new String(Base64.encode(arrby, n, n2, n3), "US-ASCII");
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError((Object)unsupportedEncodingException);
        }
    }

    static abstract class Coder {
        public int op;
        public byte[] output;

        Coder() {
        }

        public abstract int maxOutputSize(int var1);

        public abstract boolean process(byte[] var1, int var2, int var3, boolean var4);
    }

    static class Decoder
    extends Coder {
        private static final int[] DECODE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] DECODE_WEBSAFE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int EQUALS = -2;
        private static final int SKIP = -1;
        private final int[] alphabet;
        private int state;
        private int value;

        /*
         * Enabled aggressive block sorting
         */
        public Decoder(int n, byte[] arrby) {
            this.output = arrby;
            int[] arrn = (n & 8) == 0 ? DECODE : DECODE_WEBSAFE;
            this.alphabet = arrn;
            this.state = 0;
            this.value = 0;
        }

        @Override
        public int maxOutputSize(int n) {
            return 10 + n * 3 / 4;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public boolean process(byte[] var1_1, int var2_4, int var3_3, boolean var4) {
            block34 : {
                if (this.state == 6) {
                    return false;
                }
                var5_5 = var2_4;
                var6_6 = var3_3 + var2_4;
                var7_7 = this.state;
                var8_8 = this.value;
                var9_9 = 0;
                var10_10 = this.output;
                var11_11 = this.alphabet;
                while (var5_5 < var6_6) {
                    block35 : {
                        if (var7_7 == 0) {
                            while (var5_5 + 4 <= var6_6 && (var8_8 = var11_11[255 & var1_1[var5_5]] << 18 | var11_11[255 & var1_1[var5_5 + 1]] << 12 | var11_11[255 & var1_1[var5_5 + 2]] << 6 | var11_11[255 & var1_1[var5_5 + 3]]) >= 0) {
                                var10_10[var9_9 + 2] = (byte)var8_8;
                                var10_10[var9_9 + 1] = (byte)(var8_8 >> 8);
                                var10_10[var9_9] = (byte)(var8_8 >> 16);
                                var9_9 += 3;
                                var5_5 += 4;
                            }
                            if (var5_5 >= var6_6) {
                                var12_15 = var9_9;
                                break block34;
                            }
                        }
                        var16_14 = var5_5 + 1;
                        var17_13 = var11_11[255 & var1_1[var5_5]];
                        switch (var7_7) {
                            case 0: {
                                if (var17_13 >= 0) {
                                    var8_8 = var17_13;
                                    ++var7_7;
                                    ** break;
                                }
                                if (var17_13 != -1) {
                                    this.state = 6;
                                    return false;
                                }
                                ** GOTO lbl84
                            }
                            case 1: {
                                if (var17_13 >= 0) {
                                    var8_8 = var17_13 | var8_8 << 6;
                                    ++var7_7;
                                    ** break;
                                }
                                if (var17_13 != -1) {
                                    this.state = 6;
                                    return false;
                                }
                                ** GOTO lbl84
                            }
                            case 2: {
                                if (var17_13 >= 0) {
                                    var8_8 = var17_13 | var8_8 << 6;
                                    ++var7_7;
                                    ** break;
                                }
                                if (var17_13 == -2) {
                                    var18_12 = var9_9 + 1;
                                    var10_10[var9_9] = (byte)(var8_8 >> 4);
                                    var7_7 = 4;
                                    var9_9 = var18_12;
                                    ** break;
                                }
                                if (var17_13 != -1) {
                                    this.state = 6;
                                    return false;
                                }
                                ** GOTO lbl84
                            }
                            case 3: {
                                if (var17_13 >= 0) {
                                    var8_8 = var17_13 | var8_8 << 6;
                                    var10_10[var9_9 + 2] = (byte)var8_8;
                                    var10_10[var9_9 + 1] = (byte)(var8_8 >> 8);
                                    var10_10[var9_9] = (byte)(var8_8 >> 16);
                                    var9_9 += 3;
                                    var7_7 = 0;
                                    ** break;
                                }
                                if (var17_13 == -2) {
                                    var10_10[var9_9 + 1] = (byte)(var8_8 >> 2);
                                    var10_10[var9_9] = (byte)(var8_8 >> 10);
                                    var9_9 += 2;
                                    var7_7 = 5;
                                    ** break;
                                }
                                if (var17_13 != -1) {
                                    this.state = 6;
                                    return false;
                                }
                                ** GOTO lbl84
                            }
                            case 4: {
                                if (var17_13 == -2) {
                                    ++var7_7;
                                    ** break;
                                }
                                if (var17_13 != -1) {
                                    this.state = 6;
                                    return false;
                                }
                            }
lbl84: // 14 sources:
                            default: {
                                break block35;
                            }
                            case 5: 
                        }
                        if (var17_13 != -1) {
                            this.state = 6;
                            return false;
                        }
                    }
                    var5_5 = var16_14;
                }
                var12_15 = var9_9;
            }
            if (!var4) {
                this.state = var7_7;
                this.value = var8_8;
                this.op = var12_15;
                return true;
            }
            switch (var7_7) {
                default: {
                    var15_16 = var12_15;
                    ** GOTO lbl120
                }
                case 0: {
                    var15_16 = var12_15;
                    ** GOTO lbl120
                }
                case 1: {
                    this.state = 6;
                    return false;
                }
                case 2: {
                    var15_16 = var12_15 + 1;
                    var10_10[var12_15] = (byte)(var8_8 >> 4);
                    ** GOTO lbl120
                }
                case 3: {
                    var13_17 = var12_15 + 1;
                    var10_10[var12_15] = (byte)(var8_8 >> 10);
                    var14_18 = var13_17 + 1;
                    var10_10[var13_17] = (byte)(var8_8 >> 2);
                    var15_16 = var14_18;
lbl120: // 4 sources:
                    this.state = var7_7;
                    this.op = var15_16;
                    return true;
                }
                case 4: 
            }
            this.state = 6;
            return false;
        }
    }

    static class Encoder
    extends Coder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final byte[] ENCODE;
        private static final byte[] ENCODE_WEBSAFE;
        public static final int LINE_GROUPS = 19;
        private final byte[] alphabet;
        private int count;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        private final byte[] tail;
        int tailLen;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !Base64.class.desiredAssertionStatus();
            $assertionsDisabled = bl;
            ENCODE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
            ENCODE_WEBSAFE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        }

        /*
         * Enabled aggressive block sorting
         */
        public Encoder(int n, byte[] arrby) {
            boolean bl = true;
            this.output = arrby;
            boolean bl2 = (n & 1) == 0 ? bl : false;
            this.do_padding = bl2;
            boolean bl3 = (n & 2) == 0 ? bl : false;
            this.do_newline = bl3;
            if ((n & 4) == 0) {
                bl = false;
            }
            this.do_cr = bl;
            byte[] arrby2 = (n & 8) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.alphabet = arrby2;
            this.tail = new byte[2];
            this.tailLen = 0;
            int n2 = this.do_newline ? 19 : -1;
            this.count = n2;
        }

        @Override
        public int maxOutputSize(int n) {
            return 10 + n * 8 / 5;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public boolean process(byte[] var1_1, int var2_4, int var3_3, boolean var4) {
            var5_5 = this.alphabet;
            var6_6 = this.output;
            var7_7 = this.count;
            var8_8 = var2_4;
            var9_9 = var3_3 + var2_4;
            var10_10 = -1;
            switch (this.tailLen) {
                case 1: {
                    if (var8_8 + 2 > var9_9) break;
                    var57_12 = (255 & this.tail[0]) << 16;
                    var58_15 = var8_8 + 1;
                    var59_13 = var57_12 | (255 & var1_1[var8_8]) << 8;
                    var8_8 = var58_15 + 1;
                    var10_10 = var59_13 | 255 & var1_1[var58_15];
                    this.tailLen = 0;
                    break;
                }
                case 2: {
                    if (var8_8 + 1 > var9_9) break;
                    var11_11 = (255 & this.tail[0]) << 16 | (255 & this.tail[1]) << 8;
                    var12_14 = var8_8 + 1;
                    var10_10 = var11_11 | 255 & var1_1[var8_8];
                    this.tailLen = 0;
                    var8_8 = var12_14;
                }
            }
            var13_16 = 0;
            if (var10_10 == -1) ** GOTO lbl180
            var53_17 = 0 + 1;
            var6_6[0] = var5_5[63 & var10_10 >> 18];
            var54_18 = var53_17 + 1;
            var6_6[var53_17] = var5_5[63 & var10_10 >> 12];
            var55_19 = var54_18 + 1;
            var6_6[var54_18] = var5_5[63 & var10_10 >> 6];
            var13_16 = var55_19 + 1;
            var6_6[var55_19] = var5_5[var10_10 & 63];
            if (--var7_7 != 0) ** GOTO lbl180
            if (this.do_cr) {
                var56_20 = var13_16 + 1;
                var6_6[var13_16] = 13;
                var13_16 = var56_20;
            }
            var15_21 = var13_16 + 1;
            var6_6[var13_16] = 10;
            var7_7 = 19;
            var14_22 = var8_8;
            do {
                block29 : {
                    block35 : {
                        block30 : {
                            block32 : {
                                block34 : {
                                    block33 : {
                                        block31 : {
                                            block28 : {
                                                if (var14_22 + 3 > var9_9) break block28;
                                                var51_24 = (255 & var1_1[var14_22]) << 16 | (255 & var1_1[var14_22 + 1]) << 8 | 255 & var1_1[var14_22 + 2];
                                                var6_6[var15_21] = var5_5[63 & var51_24 >> 18];
                                                var6_6[var15_21 + 1] = var5_5[63 & var51_24 >> 12];
                                                var6_6[var15_21 + 2] = var5_5[63 & var51_24 >> 6];
                                                var6_6[var15_21 + 3] = var5_5[var51_24 & 63];
                                                var8_8 = var14_22 + 3;
                                                var13_16 = var15_21 + 4;
                                                if (--var7_7 == 0) {
                                                    if (this.do_cr) {
                                                        var52_23 = var13_16 + 1;
                                                        var6_6[var13_16] = 13;
                                                        var13_16 = var52_23;
                                                    }
                                                    var15_21 = var13_16 + 1;
                                                    var6_6[var13_16] = 10;
                                                    var7_7 = 19;
                                                    var14_22 = var8_8;
                                                    continue;
                                                }
                                                break block29;
                                            }
                                            if (!var4) break block30;
                                            if (var14_22 - this.tailLen != var9_9 - 1) break block31;
                                            if (this.tailLen > 0) {
                                                var49_25 = this.tail;
                                                var50_26 = 0 + 1;
                                                var42_27 = var49_25[0];
                                                var43_28 = var50_26;
                                                var25_29 = var14_22;
                                            } else {
                                                var25_29 = var14_22 + 1;
                                                var42_27 = var1_1[var14_22];
                                                var43_28 = 0;
                                            }
                                            var44_30 = (var42_27 & 255) << 4;
                                            this.tailLen -= var43_28;
                                            var45_31 = var15_21 + 1;
                                            var6_6[var15_21] = var5_5[63 & var44_30 >> 6];
                                            var46_32 = var45_31 + 1;
                                            var6_6[var45_31] = var5_5[var44_30 & 63];
                                            if (this.do_padding) {
                                                var48_33 = var46_32 + 1;
                                                var6_6[var46_32] = 61;
                                                var46_32 = var48_33 + 1;
                                                var6_6[var48_33] = 61;
                                            }
                                            var17_34 = var46_32;
                                            if (!this.do_newline) break block32;
                                            if (this.do_cr) {
                                                var47_35 = var17_34 + 1;
                                                var6_6[var17_34] = 13;
                                                var17_34 = var47_35;
                                            }
                                            var35_36 = var17_34 + 1;
                                            var6_6[var17_34] = 10;
                                            break block33;
                                        }
                                        if (var14_22 - this.tailLen != var9_9 - 2) break block34;
                                        if (this.tailLen > 1) {
                                            var40_37 = this.tail;
                                            var41_38 = 0 + 1;
                                            var27_39 = var40_37[0];
                                            var28_40 = var41_38;
                                            var25_29 = var14_22;
                                        } else {
                                            var25_29 = var14_22 + 1;
                                            var27_39 = var1_1[var14_22];
                                            var28_40 = 0;
                                        }
                                        var29_41 = (var27_39 & 255) << 10;
                                        if (this.tailLen > 0) {
                                            var38_42 = this.tail;
                                            var39_43 = var28_40 + 1;
                                            var31_44 = var38_42[var28_40];
                                            var28_40 = var39_43;
                                        } else {
                                            var30_50 = var25_29 + 1;
                                            var31_44 = var1_1[var25_29];
                                            var25_29 = var30_50;
                                        }
                                        var32_45 = var29_41 | (var31_44 & 255) << 2;
                                        this.tailLen -= var28_40;
                                        var33_46 = var15_21 + 1;
                                        var6_6[var15_21] = var5_5[63 & var32_45 >> 12];
                                        var34_47 = var33_46 + 1;
                                        var6_6[var33_46] = var5_5[63 & var32_45 >> 6];
                                        var17_34 = var34_47 + 1;
                                        var6_6[var34_47] = var5_5[var32_45 & 63];
                                        if (this.do_padding) {
                                            var37_48 = var17_34 + 1;
                                            var6_6[var17_34] = 61;
                                            var17_34 = var37_48;
                                        }
                                        if (!this.do_newline) break block32;
                                        if (this.do_cr) {
                                            var36_49 = var17_34 + 1;
                                            var6_6[var17_34] = 13;
                                            var17_34 = var36_49;
                                        }
                                        var35_36 = var17_34 + 1;
                                        var6_6[var17_34] = 10;
                                    }
                                    var17_34 = var35_36;
                                    break block32;
                                }
                                if (this.do_newline && var15_21 > 0 && var7_7 != 19) {
                                    if (this.do_cr) {
                                        var26_51 = var15_21 + 1;
                                        var6_6[var15_21] = 13;
                                    } else {
                                        var26_51 = var15_21;
                                    }
                                    var15_21 = var26_51 + 1;
                                    var6_6[var26_51] = 10;
                                }
                                var25_29 = var14_22;
                                var17_34 = var15_21;
                            }
                            if (!Encoder.$assertionsDisabled && this.tailLen != 0) {
                                throw new AssertionError();
                            }
                            if (!Encoder.$assertionsDisabled && var25_29 != var9_9) {
                                throw new AssertionError();
                            }
                            break block35;
                        }
                        if (var14_22 == var9_9 - 1) {
                            var22_52 = this.tail;
                            var23_53 = this.tailLen;
                            this.tailLen = var23_53 + 1;
                            var22_52[var23_53] = var1_1[var14_22];
                            var17_34 = var15_21;
                        } else {
                            if (var14_22 == var9_9 - 2) {
                                var18_54 = this.tail;
                                var19_55 = this.tailLen;
                                this.tailLen = var19_55 + 1;
                                var18_54[var19_55] = var1_1[var14_22];
                                var20_56 = this.tail;
                                var21_57 = this.tailLen;
                                this.tailLen = var21_57 + 1;
                                var20_56[var21_57] = var1_1[var14_22 + 1];
                            }
                            var17_34 = var15_21;
                        }
                    }
                    this.op = var17_34;
                    this.count = var7_7;
                    return true;
                }
                var14_22 = var8_8;
                var15_21 = var13_16;
            } while (true);
        }
    }

}


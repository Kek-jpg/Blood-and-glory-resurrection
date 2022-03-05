/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Double
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.io;

public final class NumberOutput {
    private static int BILLION;
    static final char[] FULL_TRIPLETS;
    static final byte[] FULL_TRIPLETS_B;
    static final char[] LEADING_TRIPLETS;
    private static long MAX_INT_AS_LONG;
    private static int MILLION;
    private static long MIN_INT_AS_LONG;
    private static final char NULL_CHAR;
    static final String SMALLEST_LONG;
    private static long TEN_BILLION_L;
    private static long THOUSAND_L;
    static final String[] sSmallIntStrs;
    static final String[] sSmallIntStrs2;

    /*
     * Enabled aggressive block sorting
     */
    static {
        MILLION = 1000000;
        BILLION = 1000000000;
        TEN_BILLION_L = 10000000000L;
        THOUSAND_L = 1000L;
        MIN_INT_AS_LONG = Integer.MIN_VALUE;
        MAX_INT_AS_LONG = Integer.MAX_VALUE;
        SMALLEST_LONG = String.valueOf((long)Long.MIN_VALUE);
        LEADING_TRIPLETS = new char[4000];
        FULL_TRIPLETS = new char[4000];
        int n = 0;
        int n2 = 0;
        do {
            char c;
            char c2;
            if (n2 < 10) {
                c2 = (char)(n2 + 48);
                c = n2 == 0 ? (char)'\u0000' : c2;
            } else {
                FULL_TRIPLETS_B = new byte[4000];
                int n3 = 0;
                do {
                    if (n3 >= 4000) {
                        sSmallIntStrs = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
                        sSmallIntStrs2 = new String[]{"-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10"};
                        return;
                    }
                    NumberOutput.FULL_TRIPLETS_B[n3] = (byte)FULL_TRIPLETS[n3];
                    ++n3;
                } while (true);
            }
            for (int i = 0; i < 10; ++i) {
                char c3 = (char)(i + 48);
                char c4 = n2 == 0 && i == 0 ? (char)'\u0000' : c3;
                for (int j = 0; j < 10; n += 4, ++j) {
                    char c5 = (char)(j + 48);
                    NumberOutput.LEADING_TRIPLETS[n] = c;
                    NumberOutput.LEADING_TRIPLETS[n + 1] = c4;
                    NumberOutput.LEADING_TRIPLETS[n + 2] = c5;
                    NumberOutput.FULL_TRIPLETS[n] = c2;
                    NumberOutput.FULL_TRIPLETS[n + 1] = c3;
                    NumberOutput.FULL_TRIPLETS[n + 2] = c5;
                }
            }
            ++n2;
        } while (true);
    }

    private static int calcLongStrLength(long l) {
        int n = 10;
        long l2 = TEN_BILLION_L;
        while (l >= l2 && n != 19) {
            ++n;
            l2 = (l2 << 3) + (l2 << 1);
        }
        return n;
    }

    private static int outputFullTriplet(int n, byte[] arrby, int n2) {
        int n3 = n << 2;
        int n4 = n2 + 1;
        byte[] arrby2 = FULL_TRIPLETS_B;
        int n5 = n3 + 1;
        arrby[n2] = arrby2[n3];
        int n6 = n4 + 1;
        byte[] arrby3 = FULL_TRIPLETS_B;
        int n7 = n5 + 1;
        arrby[n4] = arrby3[n5];
        int n8 = n6 + 1;
        arrby[n6] = FULL_TRIPLETS_B[n7];
        return n8;
    }

    private static int outputFullTriplet(int n, char[] arrc, int n2) {
        int n3 = n << 2;
        int n4 = n2 + 1;
        char[] arrc2 = FULL_TRIPLETS;
        int n5 = n3 + 1;
        arrc[n2] = arrc2[n3];
        int n6 = n4 + 1;
        char[] arrc3 = FULL_TRIPLETS;
        int n7 = n5 + 1;
        arrc[n4] = arrc3[n5];
        int n8 = n6 + 1;
        arrc[n6] = FULL_TRIPLETS[n7];
        return n8;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int outputInt(int n, byte[] arrby, int n2) {
        int n3;
        if (n < 0) {
            if (n == Integer.MIN_VALUE) {
                return NumberOutput.outputLong((long)n, arrby, n2);
            }
            int n4 = n2 + 1;
            arrby[n2] = 45;
            n = -n;
            n2 = n4;
        }
        if (n < MILLION) {
            if (n >= 1000) {
                int n5 = n / 1000;
                return NumberOutput.outputFullTriplet(n - n5 * 1000, arrby, NumberOutput.outputLeadingTriplet(n5, arrby, n2));
            }
            if (n >= 10) return NumberOutput.outputLeadingTriplet(n, arrby, n2);
            int n6 = n2 + 1;
            arrby[n2] = (byte)(n + 48);
            return n6;
        }
        boolean bl = n >= BILLION;
        if (bl) {
            if ((n -= BILLION) >= BILLION) {
                n -= BILLION;
                int n7 = n2 + 1;
                arrby[n2] = 50;
                n2 = n7;
            } else {
                int n8 = n2 + 1;
                arrby[n2] = 49;
                n2 = n8;
            }
        }
        int n9 = n / 1000;
        int n10 = n - n9 * 1000;
        int n11 = n9 / 1000;
        int n12 = n9 - n11 * 1000;
        if (bl) {
            n3 = NumberOutput.outputFullTriplet(n11, arrby, n2);
            return NumberOutput.outputFullTriplet(n10, arrby, NumberOutput.outputFullTriplet(n12, arrby, n3));
        }
        n3 = NumberOutput.outputLeadingTriplet(n11, arrby, n2);
        return NumberOutput.outputFullTriplet(n10, arrby, NumberOutput.outputFullTriplet(n12, arrby, n3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int outputInt(int n, char[] arrc, int n2) {
        int n3;
        if (n < 0) {
            if (n == Integer.MIN_VALUE) {
                return NumberOutput.outputLong((long)n, arrc, n2);
            }
            int n4 = n2 + 1;
            arrc[n2] = 45;
            n = -n;
            n2 = n4;
        }
        if (n < MILLION) {
            if (n >= 1000) {
                int n5 = n / 1000;
                return NumberOutput.outputFullTriplet(n - n5 * 1000, arrc, NumberOutput.outputLeadingTriplet(n5, arrc, n2));
            }
            if (n >= 10) return NumberOutput.outputLeadingTriplet(n, arrc, n2);
            int n6 = n2 + 1;
            arrc[n2] = (char)(n + 48);
            return n6;
        }
        boolean bl = n >= BILLION;
        if (bl) {
            if ((n -= BILLION) >= BILLION) {
                n -= BILLION;
                int n7 = n2 + 1;
                arrc[n2] = 50;
                n2 = n7;
            } else {
                int n8 = n2 + 1;
                arrc[n2] = 49;
                n2 = n8;
            }
        }
        int n9 = n / 1000;
        int n10 = n - n9 * 1000;
        int n11 = n9 / 1000;
        int n12 = n9 - n11 * 1000;
        if (bl) {
            n3 = NumberOutput.outputFullTriplet(n11, arrc, n2);
            return NumberOutput.outputFullTriplet(n10, arrc, NumberOutput.outputFullTriplet(n12, arrc, n3));
        }
        n3 = NumberOutput.outputLeadingTriplet(n11, arrc, n2);
        return NumberOutput.outputFullTriplet(n10, arrc, NumberOutput.outputFullTriplet(n12, arrc, n3));
    }

    private static int outputLeadingTriplet(int n, byte[] arrby, int n2) {
        int n3 = n << 2;
        char[] arrc = LEADING_TRIPLETS;
        int n4 = n3 + 1;
        char c = arrc[n3];
        if (c != '\u0000') {
            int n5 = n2 + 1;
            arrby[n2] = (byte)c;
            n2 = n5;
        }
        char[] arrc2 = LEADING_TRIPLETS;
        int n6 = n4 + 1;
        char c2 = arrc2[n4];
        if (c2 != '\u0000') {
            int n7 = n2 + 1;
            arrby[n2] = (byte)c2;
            n2 = n7;
        }
        int n8 = n2 + 1;
        arrby[n2] = (byte)LEADING_TRIPLETS[n6];
        return n8;
    }

    private static int outputLeadingTriplet(int n, char[] arrc, int n2) {
        int n3 = n << 2;
        char[] arrc2 = LEADING_TRIPLETS;
        int n4 = n3 + 1;
        char c = arrc2[n3];
        if (c != '\u0000') {
            int n5 = n2 + 1;
            arrc[n2] = c;
            n2 = n5;
        }
        char[] arrc3 = LEADING_TRIPLETS;
        int n6 = n4 + 1;
        char c2 = arrc3[n4];
        if (c2 != '\u0000') {
            int n7 = n2 + 1;
            arrc[n2] = c2;
            n2 = n7;
        }
        int n8 = n2 + 1;
        arrc[n2] = LEADING_TRIPLETS[n6];
        return n8;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int outputLong(long l, byte[] arrby, int n) {
        int n2;
        if (l < 0L) {
            if (l > MIN_INT_AS_LONG) {
                return NumberOutput.outputInt((int)l, arrby, n);
            }
            if (l == Long.MIN_VALUE) {
                int n3 = SMALLEST_LONG.length();
                int n4 = 0;
                int n5 = n;
                do {
                    if (n4 >= n3) {
                        return n5;
                    }
                    int n6 = n5 + 1;
                    arrby[n5] = (byte)SMALLEST_LONG.charAt(n4);
                    ++n4;
                    n5 = n6;
                } while (true);
            }
            int n7 = n + 1;
            arrby[n] = 45;
            l = -l;
            n = n7;
        } else if (l <= MAX_INT_AS_LONG) {
            return NumberOutput.outputInt((int)l, arrby, n);
        }
        int n8 = n;
        int n9 = n2 = n + NumberOutput.calcLongStrLength(l);
        while (l > MAX_INT_AS_LONG) {
            long l2 = l / THOUSAND_L;
            NumberOutput.outputFullTriplet((int)(l - l2 * THOUSAND_L), arrby, n9 -= 3);
            l = l2;
        }
        int n10 = (int)l;
        do {
            if (n10 < 1000) {
                NumberOutput.outputLeadingTriplet(n10, arrby, n8);
                return n2;
            }
            int n11 = n10 / 1000;
            NumberOutput.outputFullTriplet(n10 - n11 * 1000, arrby, n9 -= 3);
            n10 = n11;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int outputLong(long l, char[] arrc, int n) {
        int n2;
        if (l < 0L) {
            if (l > MIN_INT_AS_LONG) {
                return NumberOutput.outputInt((int)l, arrc, n);
            }
            if (l == Long.MIN_VALUE) {
                int n3 = SMALLEST_LONG.length();
                SMALLEST_LONG.getChars(0, n3, arrc, n);
                return n + n3;
            }
            int n4 = n + 1;
            arrc[n] = 45;
            l = -l;
            n = n4;
        } else if (l <= MAX_INT_AS_LONG) {
            return NumberOutput.outputInt((int)l, arrc, n);
        }
        int n5 = n;
        int n6 = n2 = n + NumberOutput.calcLongStrLength(l);
        while (l > MAX_INT_AS_LONG) {
            long l2 = l / THOUSAND_L;
            NumberOutput.outputFullTriplet((int)(l - l2 * THOUSAND_L), arrc, n6 -= 3);
            l = l2;
        }
        int n7 = (int)l;
        do {
            if (n7 < 1000) {
                NumberOutput.outputLeadingTriplet(n7, arrc, n5);
                return n2;
            }
            int n8 = n7 / 1000;
            NumberOutput.outputFullTriplet(n7 - n8 * 1000, arrc, n6 -= 3);
            n7 = n8;
        } while (true);
    }

    public static String toString(double d) {
        return Double.toString((double)d);
    }

    public static String toString(int n) {
        if (n < sSmallIntStrs.length) {
            if (n >= 0) {
                return sSmallIntStrs[n];
            }
            int n2 = -1 + -n;
            if (n2 < sSmallIntStrs2.length) {
                return sSmallIntStrs2[n2];
            }
        }
        return Integer.toString((int)n);
    }

    public static String toString(long l) {
        if (l <= Integer.MAX_VALUE && l >= Integer.MIN_VALUE) {
            return NumberOutput.toString((int)l);
        }
        return Long.toString((long)l);
    }
}


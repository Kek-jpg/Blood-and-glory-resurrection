/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Double
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.io;

public final class NumberInput {
    static final long L_BILLION = 1000000000L;
    static final String MAX_LONG_STR;
    static final String MIN_LONG_STR_NO_SIGN;
    public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";

    static {
        MIN_LONG_STR_NO_SIGN = String.valueOf((long)Long.MIN_VALUE).substring(1);
        MAX_LONG_STR = String.valueOf((long)Long.MAX_VALUE);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final boolean inLongRange(String string, boolean bl) {
        String string2 = bl ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int n = string2.length();
        int n2 = string.length();
        if (n2 >= n) {
            if (n2 > n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                int n3 = string.charAt(i) - string2.charAt(i);
                if (n3 == 0) continue;
                if (n3 < 0) break;
                return false;
            }
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final boolean inLongRange(char[] arrc, int n, int n2, boolean bl) {
        String string = bl ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int n3 = string.length();
        if (n2 >= n3) {
            if (n2 > n3) {
                return false;
            }
            for (int i = 0; i < n3; ++i) {
                int n4 = arrc[n + i] - string.charAt(i);
                if (n4 == 0) continue;
                if (n4 < 0) break;
                return false;
            }
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static double parseAsDouble(String string, double d) {
        if (string == null) {
            return d;
        }
        String string2 = string.trim();
        if (string2.length() == 0) return d;
        try {
            return NumberInput.parseDouble(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return d;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int parseAsInt(String string, int n) {
        if (string == null) return n;
        String string2 = string.trim();
        int n2 = string2.length();
        if (n2 == 0) {
            return n;
        }
        int n3 = 0;
        if (n2 < 0) {
            char c = string2.charAt(0);
            if (c == '+') {
                string2 = string2.substring(1);
                n2 = string2.length();
            } else {
                n3 = 0;
                if (c == '-') {
                    n3 = 0 + 1;
                }
            }
        }
        while (n3 < n2) {
            char c = string2.charAt(n3);
            if (c > '9' || c < '0') {
                double d;
                try {
                    d = NumberInput.parseDouble(string2);
                }
                catch (NumberFormatException numberFormatException) {
                    return n;
                }
                return (int)d;
            }
            ++n3;
        }
        try {
            return Integer.parseInt((String)string2);
        }
        catch (NumberFormatException numberFormatException) {
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static long parseAsLong(String string, long l) {
        if (string == null) return l;
        String string2 = string.trim();
        int n = string2.length();
        if (n == 0) {
            return l;
        }
        int n2 = 0;
        if (n < 0) {
            char c = string2.charAt(0);
            if (c == '+') {
                string2 = string2.substring(1);
                n = string2.length();
            } else {
                n2 = 0;
                if (c == '-') {
                    n2 = 0 + 1;
                }
            }
        }
        while (n2 < n) {
            char c = string2.charAt(n2);
            if (c > '9' || c < '0') {
                double d;
                try {
                    d = NumberInput.parseDouble(string2);
                }
                catch (NumberFormatException numberFormatException) {
                    return l;
                }
                return (long)d;
            }
            ++n2;
        }
        try {
            return Long.parseLong((String)string2);
        }
        catch (NumberFormatException numberFormatException) {
            return l;
        }
    }

    public static final double parseDouble(String string) throws NumberFormatException {
        if (NASTY_SMALL_DOUBLE.equals((Object)string)) {
            return Double.MIN_NORMAL;
        }
        return Double.parseDouble((String)string);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final int parseInt(String string) {
        int n;
        char c = string.charAt(0);
        int n2 = string.length();
        boolean bl = false;
        if (c == '-') {
            bl = true;
        }
        if (bl) {
            if (n2 == 1) return Integer.parseInt((String)string);
            if (n2 > 10) {
                return Integer.parseInt((String)string);
            }
            n = 1 + 1;
            c = string.charAt(1);
        } else {
            if (n2 > 9) {
                return Integer.parseInt((String)string);
            }
            n = 1;
        }
        if (c > '9') return Integer.parseInt((String)string);
        if (c < '0') {
            return Integer.parseInt((String)string);
        }
        int n3 = c - 48;
        if (n < n2) {
            int n4 = n + 1;
            char c2 = string.charAt(n);
            if (c2 > '9') return Integer.parseInt((String)string);
            if (c2 < '0') {
                return Integer.parseInt((String)string);
            }
            n3 = n3 * 10 + (c2 - 48);
            if (n4 < n2) {
                n = n4 + 1;
                char c3 = string.charAt(n4);
                if (c3 > '9') return Integer.parseInt((String)string);
                if (c3 < '0') {
                    return Integer.parseInt((String)string);
                }
                n3 = n3 * 10 + (c3 - 48);
                if (n < n2) {
                    do {
                        int n5 = n;
                        n = n5 + 1;
                        char c4 = string.charAt(n5);
                        if (c4 > '9') return Integer.parseInt((String)string);
                        if (c4 < '0') {
                            return Integer.parseInt((String)string);
                        }
                        n3 = n3 * 10 + (c4 - 48);
                    } while (n < n2);
                }
            }
        }
        if (!bl) return n3;
        return -n3;
    }

    public static final int parseInt(char[] arrc, int n, int n2) {
        int n3 = -48 + arrc[n];
        int n4 = n + 1;
        int n5 = n2 + n;
        if (n4 < n5) {
            n3 = n3 * 10 + (-48 + arrc[n4]);
            int n6 = n4 + 1;
            if (n6 < n5) {
                n3 = n3 * 10 + (-48 + arrc[n6]);
                int n7 = n6 + 1;
                if (n7 < n5) {
                    n3 = n3 * 10 + (-48 + arrc[n7]);
                    int n8 = n7 + 1;
                    if (n8 < n5) {
                        n3 = n3 * 10 + (-48 + arrc[n8]);
                        int n9 = n8 + 1;
                        if (n9 < n5) {
                            n3 = n3 * 10 + (-48 + arrc[n9]);
                            int n10 = n9 + 1;
                            if (n10 < n5) {
                                n3 = n3 * 10 + (-48 + arrc[n10]);
                                int n11 = n10 + 1;
                                if (n11 < n5) {
                                    n3 = n3 * 10 + (-48 + arrc[n11]);
                                    int n12 = n11 + 1;
                                    if (n12 < n5) {
                                        n3 = n3 * 10 + (-48 + arrc[n12]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return n3;
    }

    public static final long parseLong(String string) {
        if (string.length() <= 9) {
            return NumberInput.parseInt(string);
        }
        return Long.parseLong((String)string);
    }

    public static final long parseLong(char[] arrc, int n, int n2) {
        int n3 = n2 - 9;
        return 1000000000L * (long)NumberInput.parseInt(arrc, n, n3) + (long)NumberInput.parseInt(arrc, n + n3, 9);
    }
}


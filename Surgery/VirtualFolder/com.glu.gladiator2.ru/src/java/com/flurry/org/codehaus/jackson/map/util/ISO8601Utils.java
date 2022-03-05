/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.IllegalArgumentException
 *  java.lang.IndexOutOfBoundsException
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.util.Date
 *  java.util.GregorianCalendar
 *  java.util.Locale
 *  java.util.TimeZone
 */
package com.flurry.org.codehaus.jackson.map.util;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils {
    private static final String GMT_ID = "GMT";
    private static final TimeZone TIMEZONE_GMT = TimeZone.getTimeZone((String)"GMT");

    private static void checkOffset(String string, int n, char c) throws IndexOutOfBoundsException {
        char c2 = string.charAt(n);
        if (c2 != c) {
            throw new IndexOutOfBoundsException("Expected '" + c + "' character but found '" + c2 + "'");
        }
    }

    public static String format(Date date) {
        return ISO8601Utils.format(date, false, TIMEZONE_GMT);
    }

    public static String format(Date date, boolean bl) {
        return ISO8601Utils.format(date, bl, TIMEZONE_GMT);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String format(Date date, boolean bl, TimeZone timeZone) {
        int n;
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        int n2 = "yyyy-MM-ddThh:mm:ss".length();
        int n3 = bl ? ".sss".length() : 0;
        int n4 = n2 + n3;
        int n5 = timeZone.getRawOffset() == 0 ? "Z".length() : "+hh:mm".length();
        StringBuilder stringBuilder = new StringBuilder(n4 + n5);
        ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(1), "yyyy".length());
        stringBuilder.append('-');
        ISO8601Utils.padInt(stringBuilder, 1 + gregorianCalendar.get(2), "MM".length());
        stringBuilder.append('-');
        ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(5), "dd".length());
        stringBuilder.append('T');
        ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(11), "hh".length());
        stringBuilder.append(':');
        ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(12), "mm".length());
        stringBuilder.append(':');
        ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(13), "ss".length());
        if (bl) {
            stringBuilder.append('.');
            ISO8601Utils.padInt(stringBuilder, gregorianCalendar.get(14), "sss".length());
        }
        if ((n = timeZone.getOffset(gregorianCalendar.getTimeInMillis())) == 0) {
            stringBuilder.append('Z');
            return stringBuilder.toString();
        }
        int n6 = Math.abs((int)(n / 60000 / 60));
        int n7 = Math.abs((int)(n / 60000 % 60));
        char c = n < 0 ? (char)'-' : '+';
        stringBuilder.append(c);
        ISO8601Utils.padInt(stringBuilder, n6, "hh".length());
        stringBuilder.append(':');
        ISO8601Utils.padInt(stringBuilder, n7, "mm".length());
        return stringBuilder.toString();
    }

    private static void padInt(StringBuilder stringBuilder, int n, int n2) {
        String string = Integer.toString((int)n);
        for (int i = n2 - string.length(); i > 0; --i) {
            stringBuilder.append('0');
        }
        stringBuilder.append(string);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Date parse(String var0) {
        block14 : {
            var1_1 = 0 + 4;
            var11_2 = ISO8601Utils.parseInt(var0, 0, var1_1);
            ISO8601Utils.checkOffset(var0, var1_1, '-');
            var12_3 = var1_1 + 1;
            var1_1 = var12_3 + 2;
            var13_4 = ISO8601Utils.parseInt(var0, var12_3, var1_1);
            ISO8601Utils.checkOffset(var0, var1_1, '-');
            var14_5 = var1_1 + 1;
            var1_1 = var14_5 + 2;
            var15_6 = ISO8601Utils.parseInt(var0, var14_5, var1_1);
            ISO8601Utils.checkOffset(var0, var1_1, 'T');
            var16_7 = var1_1 + 1;
            var1_1 = var16_7 + 2;
            var17_8 = ISO8601Utils.parseInt(var0, var16_7, var1_1);
            ISO8601Utils.checkOffset(var0, var1_1, ':');
            var18_9 = var1_1 + 1;
            var1_1 = var18_9 + 2;
            var19_10 = ISO8601Utils.parseInt(var0, var18_9, var1_1);
            ISO8601Utils.checkOffset(var0, var1_1, ':');
            var20_11 = var1_1 + 1;
            var1_1 = var20_11 + 2;
            var21_12 = ISO8601Utils.parseInt(var0, var20_11, var1_1);
            if (var0.charAt(var1_1) == '.') {
                ISO8601Utils.checkOffset(var0, var1_1, '.');
                var29_13 = var1_1 + 1;
                var1_1 = var29_13 + 3;
                var23_15 = var30_14 = ISO8601Utils.parseInt(var0, var29_13, var1_1);
                var22_16 = var1_1;
                break block14;
            }
            var22_16 = var1_1;
            var23_15 = 0;
        }
        try {
            var24_17 = var0.charAt(var22_16);
            if (var24_17 == '+' || var24_17 == '-') {
                var25_18 = "GMT" + var0.substring(var22_16);
            } else {
                if (var24_17 != 'Z') throw new IndexOutOfBoundsException("Invalid time zone indicator " + var24_17);
                var25_18 = "GMT";
            }
            if (!(var26_19 = TimeZone.getTimeZone((String)var25_18)).getID().equals((Object)var25_18)) {
                throw new IndexOutOfBoundsException();
            }
            var27_28 = new GregorianCalendar(var26_19);
            var27_28.setLenient(false);
            var27_28.set(1, var11_2);
            var27_28.set(2, var13_4 - 1);
            var27_28.set(5, var15_6);
            var27_28.set(11, var17_8);
            var27_28.set(12, var19_10);
            var27_28.set(13, var21_12);
            var27_28.set(14, var23_15);
            return var27_28.getTime();
        }
        catch (IndexOutOfBoundsException var2_20) {}
        ** GOTO lbl-1000
        catch (NumberFormatException var5_24) {}
        ** GOTO lbl-1000
        catch (IllegalArgumentException var8_30) {}
        ** GOTO lbl-1000
        catch (IllegalArgumentException var8_32) {}
lbl-1000: // 2 sources:
        {
            var10_33 = new IllegalArgumentException("Failed to parse date " + var0, (Throwable)var8_31);
            throw var10_33;
        }
        catch (NumberFormatException var5_26) {}
lbl-1000: // 2 sources:
        {
            var7_27 = new IllegalArgumentException("Failed to parse date " + var0, (Throwable)var5_25);
            throw var7_27;
        }
        catch (IndexOutOfBoundsException var2_22) {}
lbl-1000: // 2 sources:
        {
            var4_23 = new IllegalArgumentException("Failed to parse date " + var0, (Throwable)var2_21);
            throw var4_23;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int parseInt(String string, int n, int n2) throws NumberFormatException {
        int n3;
        int n4;
        if (n < 0 || n2 > string.length() || n > n2) {
            throw new NumberFormatException(string);
        }
        if (n < n2) {
            n4 = n + 1;
            int n5 = Character.digit((char)string.charAt(n), (int)10);
            if (n5 < 0) {
                throw new NumberFormatException("Invalid number: " + string);
            }
            n3 = -n5;
        } else {
            n4 = n;
            n3 = 0;
        }
        while (n4 < n2) {
            int n6 = n4 + 1;
            int n7 = Character.digit((char)string.charAt(n4), (int)10);
            if (n7 < 0) {
                throw new NumberFormatException("Invalid number: " + string);
            }
            n3 = n3 * 10 - n7;
            n4 = n6;
        }
        return -n3;
    }
}


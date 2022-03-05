/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 *  java.text.DateFormat
 *  java.text.FieldPosition
 *  java.text.ParseException
 *  java.text.ParsePosition
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.TimeZone
 */
package com.flurry.org.codehaus.jackson.map.util;

import com.flurry.org.codehaus.jackson.io.NumberInput;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class StdDateFormat
extends DateFormat {
    protected static final String[] ALL_FORMATS = new String[]{"yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd"};
    protected static final DateFormat DATE_FORMAT_ISO8601;
    protected static final DateFormat DATE_FORMAT_ISO8601_Z;
    protected static final DateFormat DATE_FORMAT_PLAIN;
    protected static final DateFormat DATE_FORMAT_RFC1123;
    protected static final String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    protected static final String DATE_FORMAT_STR_ISO8601_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    protected static final String DATE_FORMAT_STR_PLAIN = "yyyy-MM-dd";
    protected static final String DATE_FORMAT_STR_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final StdDateFormat instance;
    protected transient DateFormat _formatISO8601;
    protected transient DateFormat _formatISO8601_z;
    protected transient DateFormat _formatPlain;
    protected transient DateFormat _formatRFC1123;

    static {
        TimeZone timeZone = TimeZone.getTimeZone((String)"GMT");
        DATE_FORMAT_RFC1123 = new SimpleDateFormat(DATE_FORMAT_STR_RFC1123);
        DATE_FORMAT_RFC1123.setTimeZone(timeZone);
        DATE_FORMAT_ISO8601 = new SimpleDateFormat(DATE_FORMAT_STR_ISO8601);
        DATE_FORMAT_ISO8601.setTimeZone(timeZone);
        DATE_FORMAT_ISO8601_Z = new SimpleDateFormat(DATE_FORMAT_STR_ISO8601_Z);
        DATE_FORMAT_ISO8601_Z.setTimeZone(timeZone);
        DATE_FORMAT_PLAIN = new SimpleDateFormat(DATE_FORMAT_STR_PLAIN);
        DATE_FORMAT_PLAIN.setTimeZone(timeZone);
        instance = new StdDateFormat();
    }

    public static DateFormat getBlueprintISO8601Format() {
        return DATE_FORMAT_ISO8601;
    }

    public static DateFormat getBlueprintRFC1123Format() {
        return DATE_FORMAT_RFC1123;
    }

    public static DateFormat getISO8601Format(TimeZone timeZone) {
        DateFormat dateFormat = (DateFormat)DATE_FORMAT_ISO8601.clone();
        dateFormat.setTimeZone(timeZone);
        return dateFormat;
    }

    public static DateFormat getRFC1123Format(TimeZone timeZone) {
        DateFormat dateFormat = (DateFormat)DATE_FORMAT_RFC1123.clone();
        dateFormat.setTimeZone(timeZone);
        return dateFormat;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final boolean hasTimeZone(String string) {
        char c;
        char c2;
        char c3;
        int n = string.length();
        return n >= 6 && ((c = string.charAt(n - 6)) == '+' || c == '-' || (c2 = string.charAt(n - 5)) == '+' || c2 == '-' || (c3 = string.charAt(n - 3)) == '+' || c3 == '-');
    }

    public StdDateFormat clone() {
        return new StdDateFormat();
    }

    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (this._formatISO8601 == null) {
            this._formatISO8601 = (DateFormat)DATE_FORMAT_ISO8601.clone();
        }
        return this._formatISO8601.format(date, stringBuffer, fieldPosition);
    }

    protected boolean looksLikeISO8601(String string) {
        int n = string.length();
        boolean bl = false;
        if (n >= 5) {
            boolean bl2 = Character.isDigit((char)string.charAt(0));
            bl = false;
            if (bl2) {
                boolean bl3 = Character.isDigit((char)string.charAt(3));
                bl = false;
                if (bl3) {
                    char c = string.charAt(4);
                    bl = false;
                    if (c == '-') {
                        bl = true;
                    }
                }
            }
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Date parse(String string) throws ParseException {
        ParsePosition parsePosition;
        String string2 = string.trim();
        Date date = this.parse(string2, parsePosition = new ParsePosition(0));
        if (date != null) {
            return date;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String[] arrstring = ALL_FORMATS;
        int n = arrstring.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                stringBuilder.append('\"');
                Object[] arrobject = new Object[]{string2, stringBuilder.toString()};
                throw new ParseException(String.format((String)"Can not parse date \"%s\": not compatible with any of standard forms (%s)", (Object[])arrobject), parsePosition.getErrorIndex());
            }
            String string3 = arrstring[n2];
            if (stringBuilder.length() > 0) {
                stringBuilder.append("\", \"");
            } else {
                stringBuilder.append('\"');
            }
            stringBuilder.append(string3);
            ++n2;
        } while (true);
    }

    public Date parse(String string, ParsePosition parsePosition) {
        char c;
        if (this.looksLikeISO8601(string)) {
            return this.parseAsISO8601(string, parsePosition);
        }
        int n = string.length();
        while (--n >= 0 && (c = string.charAt(n)) >= '0' && c <= '9') {
        }
        if (n < 0 && NumberInput.inLongRange(string, false)) {
            return new Date(Long.parseLong((String)string));
        }
        return this.parseAsRFC1123(string, parsePosition);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Date parseAsISO8601(String string, ParsePosition parsePosition) {
        DateFormat dateFormat;
        int n = string.length();
        char c = string.charAt(n - 1);
        if (n <= 10 && Character.isDigit((char)c)) {
            dateFormat = this._formatPlain;
            if (dateFormat != null) return dateFormat.parse(string, parsePosition);
            this._formatPlain = dateFormat = (DateFormat)DATE_FORMAT_PLAIN.clone();
            return dateFormat.parse(string, parsePosition);
        }
        if (c == 'Z') {
            dateFormat = this._formatISO8601_z;
            if (dateFormat == null) {
                this._formatISO8601_z = dateFormat = (DateFormat)DATE_FORMAT_ISO8601_Z.clone();
            }
            if (string.charAt(n - 4) != ':') return dateFormat.parse(string, parsePosition);
            StringBuilder stringBuilder = new StringBuilder(string);
            stringBuilder.insert(n - 1, ".000");
            string = stringBuilder.toString();
            return dateFormat.parse(string, parsePosition);
        }
        if (StdDateFormat.hasTimeZone(string)) {
            int n2;
            char c2 = string.charAt(n - 3);
            if (c2 == ':') {
                StringBuilder stringBuilder = new StringBuilder(string);
                stringBuilder.delete(n - 3, n - 2);
                string = stringBuilder.toString();
            } else if (c2 == '+' || c2 == '-') {
                string = string + "00";
            }
            if (Character.isDigit((char)string.charAt((n2 = string.length()) - 9))) {
                StringBuilder stringBuilder = new StringBuilder(string);
                stringBuilder.insert(n2 - 5, ".000");
                string = stringBuilder.toString();
            }
            dateFormat = this._formatISO8601;
            if (this._formatISO8601 != null) return dateFormat.parse(string, parsePosition);
            this._formatISO8601 = dateFormat = (DateFormat)DATE_FORMAT_ISO8601.clone();
            return dateFormat.parse(string, parsePosition);
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        if (-1 + (n - string.lastIndexOf(84)) <= 8) {
            stringBuilder.append(".000");
        }
        stringBuilder.append('Z');
        string = stringBuilder.toString();
        dateFormat = this._formatISO8601_z;
        if (dateFormat != null) return dateFormat.parse(string, parsePosition);
        this._formatISO8601_z = dateFormat = (DateFormat)DATE_FORMAT_ISO8601_Z.clone();
        return dateFormat.parse(string, parsePosition);
    }

    protected Date parseAsRFC1123(String string, ParsePosition parsePosition) {
        if (this._formatRFC1123 == null) {
            this._formatRFC1123 = (DateFormat)DATE_FORMAT_RFC1123.clone();
        }
        return this._formatRFC1123.parse(string, parsePosition);
    }
}


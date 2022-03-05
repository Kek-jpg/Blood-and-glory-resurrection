/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.text.DateFormat
 *  java.text.DecimalFormat
 *  java.text.FieldPosition
 *  java.text.NumberFormat
 *  java.text.ParsePosition
 *  java.util.Calendar
 *  java.util.Date
 *  java.util.GregorianCalendar
 */
package com.flurry.org.codehaus.jackson.map.util;

import com.flurry.org.codehaus.jackson.map.util.ISO8601Utils;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ISO8601DateFormat
extends DateFormat {
    private static Calendar CALENDAR = new GregorianCalendar();
    private static NumberFormat NUMBER_FORMAT = new DecimalFormat();
    private static final long serialVersionUID = 1L;

    public ISO8601DateFormat() {
        this.numberFormat = NUMBER_FORMAT;
        this.calendar = CALENDAR;
    }

    public Object clone() {
        return this;
    }

    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        stringBuffer.append(ISO8601Utils.format(date));
        return stringBuffer;
    }

    public Date parse(String string, ParsePosition parsePosition) {
        parsePosition.setIndex(string.length());
        return ISO8601Utils.parse(string);
    }
}


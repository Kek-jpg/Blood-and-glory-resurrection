/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.io.PrintWriter
 *  java.io.StringWriter
 *  java.io.Writer
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.lang.UnsupportedOperationException
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.Hashtable
 */
package com.playhaven.src.common;

import android.content.Context;
import com.playhaven.src.common.PHAPIRequest;
import com.playhaven.src.common.PHAsyncRequest;
import com.playhaven.src.common.PHConfig;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class PHCrashReport
extends PHAPIRequest {
    private final String CRASH_REPORT_TEMPLATE;
    private final String DATE_FORMAT;
    private final String POST_PAYLOAD_NAME;
    private Exception exception;
    private Urgency level;
    private Date reportTime;
    private String tag;

    public PHCrashReport() {
        super(null);
        this.level = Urgency.critical;
        this.reportTime = new Date();
        this.DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
        this.POST_PAYLOAD_NAME = "payload";
        this.CRASH_REPORT_TEMPLATE = "Crash Report [PHCrashReport]\nTag: %s\nPlatform: %s\nVersion: %s\nTime: %s\nSession: %s\nDevice: %s\nUrgency: %s\nMessage: %sStack Trace:\n\n%s";
        this.reportTime = new Date();
        this.exception = null;
    }

    public PHCrashReport(PHAPIRequest.Delegate delegate) {
        super(null);
        this.level = Urgency.critical;
        this.reportTime = new Date();
        this.DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
        this.POST_PAYLOAD_NAME = "payload";
        this.CRASH_REPORT_TEMPLATE = "Crash Report [PHCrashReport]\nTag: %s\nPlatform: %s\nVersion: %s\nTime: %s\nSession: %s\nDevice: %s\nUrgency: %s\nMessage: %sStack Trace:\n\n%s";
        throw new UnsupportedOperationException("PHCrashReport does not accept a delegate");
    }

    public PHCrashReport(Exception exception, Urgency urgency) {
        this.exception = exception;
        this.tag = null;
    }

    public PHCrashReport(Exception exception, String string2, Urgency urgency) {
        this.exception = exception;
        this.tag = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private String generateCrashReport() {
        if (this.exception == null) {
            return "(No Exception)";
        }
        this.exception.fillInStackTrace();
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter((Writer)stringWriter);
        this.exception.printStackTrace(printWriter);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Object[] arrobject = new Object[9];
        String string2 = this.tag != null ? this.tag : "(No Tag)";
        arrobject[0] = string2;
        arrobject[1] = "android";
        arrobject[2] = PHConfig.sdk_version;
        arrobject[3] = simpleDateFormat.format(this.reportTime);
        arrobject[4] = "(No Session)";
        arrobject[5] = PHConfig.device_id;
        arrobject[6] = this.level.toString();
        arrobject[7] = this.exception.getMessage();
        arrobject[8] = stringWriter.toString();
        return String.format((String)"Crash Report [PHCrashReport]\nTag: %s\nPlatform: %s\nVersion: %s\nTime: %s\nSession: %s\nDevice: %s\nUrgency: %s\nMessage: %sStack Trace:\n\n%s", (Object[])arrobject);
    }

    public static PHCrashReport reportCrash(Exception exception, Urgency urgency) {
        if (PHConfig.runningTests) {
            throw new RuntimeException((Throwable)exception);
        }
        exception.printStackTrace();
        return null;
    }

    public static PHCrashReport reportCrash(Exception exception, String string2, Urgency urgency) {
        if (PHConfig.runningTests) {
            throw new RuntimeException((Throwable)exception);
        }
        exception.printStackTrace();
        return null;
    }

    @Override
    public String baseURL() {
        return super.createAPIURL("/v3/publisher/crash/");
    }

    @Override
    public Hashtable<String, String> getAdditionalParams() {
        Hashtable hashtable = new Hashtable();
        hashtable.put((Object)"ts", (Object)Long.toString((long)System.currentTimeMillis()));
        hashtable.put((Object)"urgency", (Object)this.level.toString());
        if (this.tag != null) {
            hashtable.put((Object)"tag", (Object)this.tag);
        }
        return hashtable;
    }

    @Override
    public Hashtable<String, String> getPostParams() {
        Hashtable hashtable = new Hashtable();
        hashtable.put((Object)"payload", (Object)this.generateCrashReport());
        return hashtable;
    }

    @Override
    public PHAsyncRequest.RequestType getRequestType() {
        return PHAsyncRequest.RequestType.Post;
    }

    @Override
    public void send() {
    }

    public static final class Urgency
    extends Enum<Urgency> {
        private static final /* synthetic */ Urgency[] $VALUES;
        public static final /* enum */ Urgency critical = new Urgency();
        public static final /* enum */ Urgency high = new Urgency();
        public static final /* enum */ Urgency low;
        public static final /* enum */ Urgency medium;
        public static final /* enum */ Urgency none;

        static {
            medium = new Urgency();
            low = new Urgency();
            none = new Urgency();
            Urgency[] arrurgency = new Urgency[]{critical, high, medium, low, none};
            $VALUES = arrurgency;
        }

        public static Urgency valueOf(String string2) {
            return (Urgency)Enum.valueOf(Urgency.class, (String)string2);
        }

        public static Urgency[] values() {
            return (Urgency[])$VALUES.clone();
        }
    }

}


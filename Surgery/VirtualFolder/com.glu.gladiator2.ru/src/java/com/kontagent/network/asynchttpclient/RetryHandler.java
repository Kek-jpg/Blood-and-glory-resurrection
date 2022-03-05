/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  java.io.IOException
 *  java.io.InterruptedIOException
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.net.SocketException
 *  java.net.UnknownHostException
 *  java.util.HashSet
 *  javax.net.ssl.SSLHandshakeException
 *  org.apache.http.NoHttpResponseException
 *  org.apache.http.client.HttpRequestRetryHandler
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.protocol.HttpContext
 */
package com.kontagent.network.asynchttpclient;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

class RetryHandler
implements HttpRequestRetryHandler {
    private static final int RETRY_SLEEP_TIME_MILLIS = 1500;
    private static HashSet<Class<?>> exceptionBlacklist;
    private static HashSet<Class<?>> exceptionWhitelist;
    private final int maxRetries;

    static {
        exceptionWhitelist = new HashSet();
        exceptionBlacklist = new HashSet();
        exceptionWhitelist.add(NoHttpResponseException.class);
        exceptionWhitelist.add(UnknownHostException.class);
        exceptionWhitelist.add(SocketException.class);
        exceptionBlacklist.add(InterruptedIOException.class);
        exceptionBlacklist.add(SSLHandshakeException.class);
    }

    public RetryHandler(int n) {
        this.maxRetries = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean retryRequest(IOException iOException, int n, HttpContext httpContext) {
        Boolean bl = (Boolean)httpContext.getAttribute("http.request_sent");
        boolean bl2 = bl != null && bl != false;
        boolean bl3 = n > this.maxRetries ? false : (exceptionBlacklist.contains((Object)iOException.getClass()) ? false : (exceptionWhitelist.contains((Object)iOException.getClass()) ? true : (!bl2 ? true : !((HttpUriRequest)httpContext.getAttribute("http.request")).getMethod().equals((Object)"POST"))));
        if (bl3) {
            SystemClock.sleep((long)1500L);
            return bl3;
        }
        iOException.printStackTrace();
        return bl3;
    }
}


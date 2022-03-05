/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.webkit.CookieManager
 *  android.webkit.CookieSyncManager
 *  java.io.IOException
 *  java.io.UnsupportedEncodingException
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.net.URLEncoder
 *  java.util.Date
 *  java.util.List
 *  java.util.concurrent.Callable
 *  java.util.concurrent.ExecutionException
 *  java.util.concurrent.ExecutorService
 *  java.util.concurrent.Executors
 *  java.util.concurrent.Future
 *  org.apache.http.Header
 *  org.apache.http.HttpException
 *  org.apache.http.HttpHost
 *  org.apache.http.HttpRequest
 *  org.apache.http.HttpRequestInterceptor
 *  org.apache.http.HttpResponse
 *  org.apache.http.RequestLine
 *  org.apache.http.client.CookieStore
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.cookie.Cookie
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.impl.cookie.DateUtils
 *  org.apache.http.params.HttpParams
 *  org.apache.http.protocol.HttpContext
 *  org.apache.http.protocol.RequestUserAgent
 */
package com.wildtangent.brandboost;

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.wildtangent.brandboost.util.b;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.RequestLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.RequestUserAgent;

public final class l
extends DefaultHttpClient {
    private static final String a = "com.wildtangent.brandboost__" + l.class.getSimpleName();
    private final HttpParams b;

    public l(Context context, int n2) {
        this.b = this.getParams();
        this.b.setIntParameter("http.connection.timeout", n2);
        this.b.setIntParameter("http.socket.timeout", n2);
        this.b.setLongParameter("http.conn-manager.timeout", (long)n2);
        this.removeRequestInterceptorByClass(RequestUserAgent.class);
        this.addRequestInterceptor(new HttpRequestInterceptor(){

            /*
             * Enabled aggressive block sorting
             */
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
                HttpHost httpHost = (HttpHost)httpContext.getAttribute("http.target_host");
                b.a(a, "INTERCEPTED: host: " + httpHost.getHostName() + " Request line: " + (Object)httpRequest.getRequestLine());
                String string = CookieManager.getInstance().getCookie(httpHost.getHostName());
                if (string != null) {
                    httpRequest.removeHeaders("Cookie");
                    httpRequest.addHeader("Cookie", string);
                    b.c(a, "Replaced header. Host: " + httpHost.getHostName() + " Cookie: " + httpRequest.getFirstHeader("Cookie").getName() + ": " + httpRequest.getFirstHeader("Cookie").getValue());
                } else {
                    b.d(a, "No cookie for " + httpHost.getHostName());
                }
                httpRequest.setParams(l.this.b);
            }
        });
    }

    public static String a(String string) {
        try {
            String string2 = string.replace((CharSequence)"|", (CharSequence)URLEncoder.encode((String)"|", (String)"UTF-8"));
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            b.a(a, unsupportedEncodingException);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void b() {
        CookieManager cookieManager = CookieManager.getInstance();
        List list = this.getCookieStore().getCookies();
        if (list.size() > 0) {
            b.c(a, "Setting " + list.size() + " cookies from HttpClient to CookieManager");
        }
        for (Cookie cookie : list) {
            String string;
            boolean bl = cookie.isSecure();
            String string2 = string = cookie.getDomain();
            if (Build.VERSION.SDK_INT < 11) {
                string2 = string.charAt(0) == '.' ? string.substring(1) : string;
            }
            StringBuilder stringBuilder = new StringBuilder();
            String string3 = bl ? "https://" : "http://";
            String string4 = stringBuilder.append(string3).append(string2).append(cookie.getPath()).toString();
            String string5 = cookie.getName() + "=" + cookie.getValue() + "; domain=" + cookie.getDomain() + "; path=" + cookie.getPath();
            Date date = cookie.getExpiryDate();
            if (date != null) {
                string5 = string5 + "; expires=" + DateUtils.formatDate((Date)date);
            }
            if (bl) {
                string5 = string5 + "; secure";
            }
            b.c(a, "Setting cookie in CookieManager. URL: " + string4 + " value: " + string5);
            if (cookie.isExpired(new Date())) {
                b.d(a, "This cookie is expired! " + cookie.toString());
            }
            cookieManager.setCookie(string4, string5);
        }
        CookieSyncManager cookieSyncManager = CookieSyncManager.getInstance();
        if (cookieSyncManager != null) {
            b.a(a, "Syncing cookies");
            cookieSyncManager.sync();
        }
    }

    public HttpResponse a(final HttpUriRequest httpUriRequest) throws ExecutionException {
        HttpResponse httpResponse;
        b.a(a, "Sending request: " + (Object)httpUriRequest.getRequestLine());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit((Callable)new Callable<HttpResponse>(){

            public HttpResponse a() throws Exception {
                return l.this.execute(httpUriRequest);
            }
        });
        try {
            httpResponse = (HttpResponse)future.get();
        }
        catch (InterruptedException interruptedException) {
            executorService.shutdownNow();
            b.d(a, "Abandoning WtHttpClient execution!");
            throw new ExecutionException((Throwable)interruptedException);
        }
        l.super.b();
        return httpResponse;
    }

}


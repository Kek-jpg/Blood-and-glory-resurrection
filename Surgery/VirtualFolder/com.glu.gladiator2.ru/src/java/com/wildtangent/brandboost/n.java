/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  java.io.IOException
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.net.URISyntaxException
 *  org.apache.http.Header
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.methods.HttpHead
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.client.params.HttpClientParams
 *  org.apache.http.conn.ClientConnectionManager
 *  org.apache.http.conn.scheme.PlainSocketFactory
 *  org.apache.http.conn.scheme.Scheme
 *  org.apache.http.conn.scheme.SchemeRegistry
 *  org.apache.http.conn.scheme.SocketFactory
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.params.HttpParams
 */
package com.wildtangent.brandboost;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.wildtangent.brandboost.util.b;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

public class n
extends AsyncTask<Uri, Integer, Uri> {
    private static final String a = "com.wildtangent.brandboost__" + n.class.getSimpleName();
    private final DefaultHttpClient b;
    private Handler c;

    public n() {
        this(null);
    }

    public n(Handler handler) {
        this.c = handler;
        this.b = new DefaultHttpClient();
        Scheme scheme = new Scheme("rtsp", (SocketFactory)new PlainSocketFactory(), 554);
        this.b.getConnectionManager().getSchemeRegistry().register(scheme);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Uri b(Uri uri) throws URISyntaxException, IOException {
        Header header;
        block3 : {
            block2 : {
                if (uri.getScheme().equals((Object)"rtsp")) break block2;
                HttpHead httpHead = new HttpHead(uri.toString());
                HttpClientParams.setRedirecting((HttpParams)httpHead.getParams(), (boolean)false);
                HttpResponse httpResponse = this.b.execute((HttpUriRequest)httpHead);
                b.a(a, httpResponse.getStatusLine().toString());
                header = httpResponse.getFirstHeader("location");
                if (header != null) break block3;
            }
            return null;
        }
        return Uri.parse((String)header.getValue());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected /* varargs */ Uri a(Uri ... arruri) {
        if (arruri.length == 0) {
            return null;
        }
        if (arruri.length > 1) {
            throw new IllegalStateException("only one uri can be processed at a time");
        }
        Uri uri = arruri[0];
        b.a(a, "resolving uri: " + (Object)uri);
        Uri uri2 = uri;
        while (uri != null) {
            if (this.isCancelled()) return null;
            try {
                Uri uri3 = n.super.b(uri);
                if ((uri = uri3) == null) continue;
            }
            catch (Exception exception) {
                b.a(a, "could not get redirect for uri: " + (Object)uri, exception);
                return null;
            }
            b.a(a, "found redirect: " + (Object)uri);
            uri2 = uri;
        }
        b.a(a, "Resolved URI: " + (Object)uri2);
        if (!this.isCancelled()) return uri2;
        return null;
    }

    protected void a(Uri uri) {
        if (uri != null) {
            Message message = this.c.obtainMessage(1002);
            message.getData().putString("uri", uri.toString());
            message.sendToTarget();
            return;
        }
        b.b(a, "NO RESULT");
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.InputStream
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.security.KeyStore
 *  org.apache.http.client.HttpClient
 *  org.apache.http.conn.ClientConnectionManager
 *  org.apache.http.conn.scheme.PlainSocketFactory
 *  org.apache.http.conn.scheme.Scheme
 *  org.apache.http.conn.scheme.SchemeRegistry
 *  org.apache.http.conn.scheme.SocketFactory
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
 *  org.apache.http.params.HttpParams
 */
package com.flurry.android;

import com.flurry.android.az;
import java.io.InputStream;
import java.security.KeyStore;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

final class ax {
    ax() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final HttpClient a(HttpParams httpParams) {
        void var10_2 = this;
        synchronized (var10_2) {
            try {
                KeyStore keyStore = KeyStore.getInstance((String)KeyStore.getDefaultType());
                keyStore.load(null, null);
                az az2 = new az((ax)this, keyStore);
                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme("http", (SocketFactory)PlainSocketFactory.getSocketFactory(), 80));
                schemeRegistry.register(new Scheme("https", (SocketFactory)az2, 443));
                return new DefaultHttpClient((ClientConnectionManager)new ThreadSafeClientConnManager(httpParams, schemeRegistry), httpParams);
            }
            catch (Exception exception) {
                return new DefaultHttpClient(httpParams);
            }
        }
    }
}


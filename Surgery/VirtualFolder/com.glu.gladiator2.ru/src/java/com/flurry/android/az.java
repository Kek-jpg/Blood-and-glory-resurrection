/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  java.net.Socket
 *  java.security.KeyStore
 *  java.security.SecureRandom
 *  javax.net.ssl.KeyManager
 *  javax.net.ssl.SSLContext
 *  javax.net.ssl.SSLSocketFactory
 *  javax.net.ssl.TrustManager
 *  org.apache.http.conn.ssl.SSLSocketFactory
 */
package com.flurry.android;

import com.flurry.android.ax;
import com.flurry.android.m;
import java.net.Socket;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

final class az
extends org.apache.http.conn.ssl.SSLSocketFactory {
    private SSLContext a = SSLContext.getInstance((String)"TLS");

    public az(ax ax2, KeyStore keyStore) {
        super(keyStore);
        m m2 = new m();
        this.a.init(null, new TrustManager[]{m2}, null);
    }

    public final Socket createSocket() {
        return this.a.getSocketFactory().createSocket();
    }

    public final Socket createSocket(Socket socket, String string, int n2, boolean bl) {
        return this.a.getSocketFactory().createSocket(socket, string, n2, bl);
    }
}


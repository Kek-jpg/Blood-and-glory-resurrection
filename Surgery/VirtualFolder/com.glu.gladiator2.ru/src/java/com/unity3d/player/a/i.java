/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.net.URI
 *  java.net.URISyntaxException
 *  java.util.HashMap
 *  java.util.Map
 *  org.apache.http.NameValuePair
 *  org.apache.http.client.utils.URLEncodedUtils
 */
package com.unity3d.player.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.unity3d.player.a.h;
import com.unity3d.player.a.j;
import com.unity3d.player.a.k;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public final class i {
    private long a;
    private long b;
    private long c;
    private long d;
    private long e = 0L;
    private int f;
    private j g;

    public i(Context context, h h2) {
        this.g = new j(context.getSharedPreferences("com.android.vending.licensing.ServerManagedPolicy", 0), h2);
        this.f = Integer.decode((String)this.g.b("lastResponse", Integer.toString((int)-1)));
        this.a = Long.parseLong((String)this.g.b("validityTimestamp", "0"));
        this.b = Long.parseLong((String)this.g.b("retryUntil", "0"));
        this.c = Long.parseLong((String)this.g.b("maxRetries", "0"));
        this.d = Long.parseLong((String)this.g.b("retryCount", "0"));
    }

    private void a(long l2) {
        this.d = l2;
        this.g.a("retryCount", Long.toString((long)l2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(String string2) {
        Long l2;
        try {
            Long l3;
            l2 = l3 = Long.valueOf((long)Long.parseLong((String)string2));
        }
        catch (NumberFormatException numberFormatException) {
            l2 = 60000L + System.currentTimeMillis();
            string2 = Long.toString((long)l2);
        }
        this.a = l2;
        this.g.a("validityTimestamp", string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void b(String string2) {
        Long l2;
        try {
            Long l3;
            l2 = l3 = Long.valueOf((long)Long.parseLong((String)string2));
        }
        catch (NumberFormatException numberFormatException) {
            string2 = "0";
            l2 = 0L;
        }
        this.b = l2;
        this.g.a("retryUntil", string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void c(String string2) {
        Long l2;
        try {
            Long l3;
            l2 = l3 = Long.valueOf((long)Long.parseLong((String)string2));
        }
        catch (NumberFormatException numberFormatException) {
            string2 = "0";
            l2 = 0L;
        }
        this.c = l2;
        this.g.a("maxRetries", string2);
    }

    private static Map d(String string2) {
        HashMap hashMap = new HashMap();
        try {
            for (NameValuePair nameValuePair : URLEncodedUtils.parse((URI)new URI("?" + string2), (String)"UTF-8")) {
                hashMap.put((Object)nameValuePair.getName(), (Object)nameValuePair.getValue());
            }
        }
        catch (URISyntaxException uRISyntaxException) {
            // empty catch block
        }
        return hashMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(int n2, k k2) {
        if (n2 != -1) {
            i.super.a(0L);
        } else {
            i.super.a(1L + this.d);
        }
        if (n2 == 1) {
            Map map = i.d(k2.g);
            this.f = n2;
            i.super.a((String)map.get((Object)"VT"));
            i.super.b((String)map.get((Object)"GT"));
            i.super.c((String)map.get((Object)"GR"));
        } else if (n2 == 0) {
            i.super.a("0");
            i.super.b("0");
            i.super.c("0");
        }
        this.e = System.currentTimeMillis();
        this.f = n2;
        this.g.a("lastResponse", Integer.toString((int)n2));
        this.g.a();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean a() {
        long l2 = System.currentTimeMillis();
        if (this.f == 1) {
            if (l2 > this.a) return false;
            return true;
        }
        if (this.f != -1 || l2 >= 60000L + this.e) return false;
        if (l2 > this.b && this.d > this.c) return false;
        return true;
    }
}


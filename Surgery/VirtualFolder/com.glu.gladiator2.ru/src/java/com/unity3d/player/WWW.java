/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Double
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Thread
 *  java.net.HttpURLConnection
 *  java.net.MalformedURLException
 *  java.net.URL
 *  java.net.URLConnection
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.unity3d.player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

class WWW
extends Thread {
    private int a;
    private int b;
    private String c;
    private byte[] d;
    private Map e;

    WWW(int n2, String string2, byte[] arrby, Map map) {
        this.b = n2;
        this.c = string2;
        this.d = arrby;
        this.e = map;
        this.a = 0;
        this.start();
    }

    private static native void doneCallback(int var0);

    private static native void errorCallback(int var0, String var1);

    private static native boolean headerCallback(int var0, String var1);

    private static native void progressCallback(int var0, float var1, float var2, double var3, int var5);

    private static native boolean readCallback(int var0, byte[] var1, int var2);

    protected boolean headerCallback(String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(": ");
        stringBuilder.append(string3);
        stringBuilder.append("\n\r");
        return WWW.headerCallback(this.b, stringBuilder.toString());
    }

    protected boolean headerCallback(Map map) {
        if (map == null || map.size() == 0) {
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            for (String string2 : (List)entry.getValue()) {
                stringBuilder.append((String)entry.getKey());
                stringBuilder.append(": ");
                stringBuilder.append(string2);
                stringBuilder.append("\n\r");
            }
        }
        return WWW.headerCallback(this.b, stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void progressCallback(int n2, int n3, int n4, int n5, long l2, long l3) {
        block6 : {
            double d2;
            float f2;
            float f3;
            block5 : {
                block4 : {
                    double d3;
                    if (n5 <= 0) break block4;
                    f3 = (float)n4 / (float)n5;
                    int n6 = Math.max((int)(n5 - n4), (int)0);
                    double d4 = (double)n6 / (d3 = 1000.0 * (double)n4 / Math.max((double)(l2 - l3), (double)0.1));
                    if (Double.isInfinite((double)d4) || Double.isNaN((double)d4)) {
                        d4 = 0.0;
                    }
                    double d5 = d4;
                    f2 = 1.0f;
                    d2 = d5;
                    break block5;
                }
                if (n3 <= 0) break block6;
                f2 = n2 / n3;
                d2 = 0.0;
                f3 = 0.0f;
            }
            WWW.progressCallback(this.b, f2, f3, d2, n5);
        }
    }

    protected boolean readCallback(byte[] arrby, int n2) {
        return WWW.readCallback(this.b, arrby, n2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void run() {
        this.a = var1_1 = 1 + this.a;
        if (var1_1 > 5) {
            WWW.errorCallback(this.b, "Too many redirects");
            return;
        }
        try {
            var2_2 = new URL(this.c);
            var5_3 = var2_2.openConnection();
            ** if (this.e == null) goto lbl-1000
        }
        catch (MalformedURLException var4_6) {
            WWW.errorCallback(this.b, var4_6.toString());
            return;
        }
        catch (IOException var3_7) {
            WWW.errorCallback(this.b, var3_7.toString());
            return;
        }
lbl-1000: // 2 sources:
        {
            for (Map.Entry var28_5 : this.e.entrySet()) {
                var5_3.addRequestProperty((String)var28_5.getKey(), (String)var28_5.getValue());
            }
        }
lbl-1000: // 2 sources:
        {
        }
        if (this.d != null) {
            var5_3.setDoOutput(true);
            try {
                var24_8 = var5_3.getOutputStream();
                var25_9 = 0;
                while (var25_9 < this.d.length) {
                    var26_10 = Math.min((int)1428, (int)(this.d.length - var25_9));
                    var24_8.write(this.d, var25_9, var26_10);
                    this.progressCallback(var25_9 += var26_10, this.d.length, 0, 0, 0L, 0L);
                }
            }
            catch (Exception var23_11) {
                WWW.errorCallback(this.b, var23_11.toString());
                return;
            }
        }
        if (var5_3 instanceof HttpURLConnection) {
            var18_12 = (HttpURLConnection)var5_3;
            try {
                var20_13 = var18_12.getResponseCode();
            }
            catch (IOException var19_16) {
                WWW.errorCallback(this.b, var19_16.toString());
                return;
            }
            var21_14 = var18_12.getHeaderFields();
            if (!(var21_14 == null || var20_13 != 301 && var20_13 != 302 || (var22_15 = (List)var21_14.get((Object)"Location")) == null || var22_15.isEmpty())) {
                var18_12.disconnect();
                this.c = (String)var22_15.get(0);
                this.run();
                return;
            }
        }
        var6_17 = var5_3.getHeaderFields();
        var7_18 = this.headerCallback(var6_17);
        if (!(var6_17 != null && var6_17.containsKey((Object)"content-length") || var5_3.getContentLength() == -1)) {
            var7_18 = var7_18 != false || this.headerCallback("content-length", String.valueOf((int)var5_3.getContentLength())) != false;
        }
        if (!(var6_17 != null && var6_17.containsKey((Object)"content-type") || var5_3.getContentType() == null)) {
            var7_18 = var7_18 != false || this.headerCallback("content-type", var5_3.getContentType()) != false;
        }
        if (var7_18) {
            WWW.errorCallback(this.b, this.c + " aborted");
            return;
        }
        var8_19 = var5_3.getContentLength() > 0 ? var5_3.getContentLength() : 0;
        var9_20 = var2_2.getProtocol().equalsIgnoreCase("file") || var2_2.getProtocol().equalsIgnoreCase("jar") ? (var8_19 == 0 ? 32768 : Math.min((int)var8_19, (int)32768)) : 1428;
        var10_21 = 0;
        try {
            var12_22 = System.currentTimeMillis();
            var14_23 = new byte[var9_20];
            var15_24 = var5_3.getInputStream();
            var16_25 = 0;
            while (var16_25 != -1) {
                if (this.readCallback(var14_23, var16_25)) {
                    WWW.errorCallback(this.b, this.c + " aborted");
                    return;
                }
                this.progressCallback(0, 0, var10_21 += var16_25, var8_19, System.currentTimeMillis(), var12_22);
                var16_25 = var17_26 = var15_24.read(var14_23);
            }
        }
        catch (Exception var11_27) {
            WWW.errorCallback(this.b, var11_27.toString());
            return;
        }
        this.progressCallback(0, 0, var10_21, var10_21, 0L, 0L);
        WWW.doneCallback(this.b);
    }
}


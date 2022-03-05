/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.util.DisplayMetrics
 *  java.io.Closeable
 *  java.io.UnsupportedEncodingException
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.net.URLDecoder
 *  java.net.URLEncoder
 *  java.net.UnknownHostException
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  org.apache.http.HttpResponse
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.flurry.android;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;
import com.flurry.android.ax;
import com.flurry.android.bb;
import com.flurry.android.bc;
import com.flurry.android.be;
import java.io.Closeable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

final class y {
    y() {
    }

    static int a(Context context, int n2) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round((float)((float)n2 / displayMetrics.density));
    }

    static bb a(be be2, String string) {
        long l2 = be2.b();
        be2.d();
        bb bb2 = new bb(l2, string);
        be2.a(bb2);
        return bb2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static String a(String string) {
        if (string == null) {
            return "";
        }
        if (string.length() <= 255) return string;
        return string.substring(0, 255);
    }

    static HttpResponse a(ax ax2, String string, int n2, int n3, boolean bl) {
        try {
            HttpGet httpGet = new HttpGet(string);
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, (int)10000);
            HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, (int)15000);
            basicHttpParams.setParameter("http.protocol.handle-redirects", (Object)bl);
            HttpResponse httpResponse = ax2.a((HttpParams)basicHttpParams).execute((HttpUriRequest)httpGet);
            return httpResponse;
        }
        catch (UnknownHostException unknownHostException) {
            bc.a("FlurryAgent", "Unknown host: " + unknownHostException.getMessage());
            return null;
        }
        catch (Exception exception) {
            bc.a("FlurryAgent", "Failed to hit URL: " + string, exception);
            return null;
        }
    }

    static void a(Context context, String string) {
        Intent intent = new Intent("android.intent.action.VIEW").setData(Uri.parse((String)string));
        if (y.a(context, intent)) {
            bc.a("FlurryAgent", "Launching intent for " + string);
            context.startActivity(intent);
            return;
        }
        bc.b("FlurryAgent", "Unable to launch intent for " + string);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void a(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (Throwable throwable) {
            return;
        }
    }

    static boolean a(long l2) {
        long l3 = System.currentTimeMillis() LCMP l2;
        boolean bl = false;
        if (l3 <= 0) {
            bl = true;
        }
        return bl;
    }

    static boolean a(Context context, Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }

    static int b(Context context, int n2) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round((float)((float)n2 * displayMetrics.density));
    }

    static String b(String string) {
        try {
            String string2 = URLEncoder.encode((String)string, (String)"UTF-8");
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            bc.d("FlurryAgent", "Cannot encode '" + string + "'");
            return "";
        }
    }

    static String c(String string) {
        try {
            String string2 = URLDecoder.decode((String)string, (String)"UTF-8");
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            bc.d("FlurryAgent", "Cannot decode '" + string + "'");
            return "";
        }
    }

    static byte[] d(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance((String)"SHA-1");
            messageDigest.update(string.getBytes(), 0, string.length());
            byte[] arrby = messageDigest.digest();
            return arrby;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            bc.b("FlurryAgent", "Unsupported SHA1: " + noSuchAlgorithmException.getMessage());
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static Map<String, String> e(String var0) {
        var1_1 = null;
        try {
            var2_2 = new JSONObject(var0);
            var6_3 = var2_2.keys();
            ** GOTO lbl9
        }
        catch (JSONException var3_8) {
            block10 : {
                var4_9 = null;
                break block10;
lbl9: // 1 sources:
                do {
                    if (var6_3.hasNext() == false) return var1_1;
                    var8_4 = (String)var6_3.next();
                    var11_6 = var2_2.getString(var8_4);
                    var12_5 = new HashMap();
                    break;
                } while (true);
                catch (JSONException var9_7) {}
                {
                    var12_5.put((Object)var8_4, (Object)var11_6);
                    var1_1 = var12_5;
                    continue;
                }
                ** GOTO lbl-1000
                catch (JSONException var13_11) {
                    var1_1 = var12_5;
                }
lbl-1000: // 2 sources:
                {
                    try {
                        bc.a("FlurryAgent", "Cannot iterate over key: " + var8_4);
                        return var1_1;
                    }
                    catch (JSONException var7_10) {
                        var4_9 = var1_1;
                    }
                }
            }
            bc.a("FlurryAgent", "Cannot convert json to map for: " + var0);
            return var4_9;
        }
    }

    static String f(String string) {
        return string.replace((CharSequence)"'", (CharSequence)"\\'");
    }
}


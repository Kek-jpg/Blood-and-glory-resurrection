/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  java.io.BufferedReader
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.net.HttpURLConnection
 *  java.net.URL
 *  java.net.URLConnection
 *  java.util.ArrayList
 *  java.util.Hashtable
 *  java.util.List
 *  java.util.Map$Entry
 *  java.util.Set
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.entity.UrlEncodedFormEntity
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.message.BasicNameValuePair
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 *  org.apache.http.util.EntityUtils
 */
package com.tapjoy;

import android.net.Uri;
import com.tapjoy.TapjoyHttpURLResponse;
import com.tapjoy.TapjoyLog;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class TapjoyURLConnection {
    private static final String TAPJOY_URL_CONNECTION = "TapjoyURLConnection";
    public static final int TYPE_GET = 0;
    public static final int TYPE_POST = 1;

    public String connectToURL(String string2) {
        return this.connectToURL(string2, "");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String connectToURL(String var1, String var2_2) {
        var6_3 = (var1 + var2_2).replaceAll(" ", "%20");
        TapjoyLog.i("TapjoyURLConnection", "baseURL: " + var1);
        TapjoyLog.i("TapjoyURLConnection", "requestURL: " + var6_3);
        var7_4 = (HttpURLConnection)new URL(var6_3).openConnection();
        var7_4.setConnectTimeout(15000);
        var7_4.setReadTimeout(30000);
        var8_5 = var7_4.getResponseMessage();
        try {
            var7_4.connect();
            var10_6 = new BufferedReader((Reader)new InputStreamReader(var7_4.getInputStream()));
            var11_7 = new StringBuilder();
            while ((var12_8 = var10_6.readLine()) != null) {
                var11_7.append(var12_8 + '\n');
            }
            var5_10 = var14_13 = var11_7.toString();
            ** GOTO lbl22
        }
        catch (Exception var9_9) {
            block8 : {
                var5_10 = var8_5;
                var4_11 = var9_9;
                break block8;
lbl22: // 1 sources:
                try {
                    TapjoyLog.i("TapjoyURLConnection", "--------------------");
                    TapjoyLog.i("TapjoyURLConnection", "response size: " + var5_10.length());
                    TapjoyLog.i("TapjoyURLConnection", "response: ");
                    TapjoyLog.i("TapjoyURLConnection", "" + var5_10);
                    TapjoyLog.i("TapjoyURLConnection", "--------------------");
                    return var5_10;
                }
                catch (Exception var4_12) {}
                break block8;
                catch (Exception var3_14) {
                    var4_11 = var3_14;
                    var5_10 = null;
                }
            }
            TapjoyLog.e("TapjoyURLConnection", "Exception: " + var4_11.toString());
            return var5_10;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String connectToURLwithPOST(String var1, Hashtable<String, String> var2_3, Hashtable<String, String> var3_2) {
        try {
            var7_4 = var1.replaceAll(" ", "%20");
            TapjoyLog.i("TapjoyURLConnection", "baseURL: " + var1);
            TapjoyLog.i("TapjoyURLConnection", "requestURL: " + var7_4);
            var8_5 = new HttpPost(var7_4);
            var9_6 = new ArrayList();
            for (Map.Entry var17_8 : var2_3.entrySet()) {
                var9_6.add((Object)new BasicNameValuePair((String)var17_8.getKey(), (String)var17_8.getValue()));
                TapjoyLog.i("TapjoyURLConnection", "key: " + (String)var17_8.getKey() + ", value: " + Uri.encode((String)((String)var17_8.getValue())));
            }
            if (var3_2 == null || var3_2.size() <= 0) ** GOTO lbl21
            for (Map.Entry var15_14 : var3_2.entrySet()) {
                var9_6.add((Object)new BasicNameValuePair("data[" + (String)var15_14.getKey() + "]", (String)var15_14.getValue()));
                TapjoyLog.i("TapjoyURLConnection", "key: " + (String)var15_14.getKey() + ", value: " + Uri.encode((String)((String)var15_14.getValue())));
            }
            ** GOTO lbl21
        }
        catch (Exception var4_9) {
            block7 : {
                var5_10 = var4_9;
                var6_12 = null;
                break block7;
lbl21: // 2 sources:
                var8_5.setEntity((HttpEntity)new UrlEncodedFormEntity((List)var9_6));
                TapjoyLog.i("TapjoyURLConnection", "HTTP POST: " + var8_5.toString());
                var11_15 = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout((HttpParams)var11_15, (int)15000);
                HttpConnectionParams.setSoTimeout((HttpParams)var11_15, (int)30000);
                var12_16 = new DefaultHttpClient((HttpParams)var11_15).execute((HttpUriRequest)var8_5);
                var6_12 = var13_17 = EntityUtils.toString((HttpEntity)var12_16.getEntity());
                try {
                    TapjoyLog.i("TapjoyURLConnection", "--------------------");
                    TapjoyLog.i("TapjoyURLConnection", "response status: " + var12_16.getStatusLine().getStatusCode());
                    TapjoyLog.i("TapjoyURLConnection", "response size: " + var6_12.length());
                    TapjoyLog.i("TapjoyURLConnection", "response: ");
                    TapjoyLog.i("TapjoyURLConnection", "" + var6_12);
                    TapjoyLog.i("TapjoyURLConnection", "--------------------");
                    return var6_12;
                }
                catch (Exception var5_11) {}
            }
            TapjoyLog.e("TapjoyURLConnection", "Exception: " + var5_10.toString());
            return var6_12;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getContentLength(String string2) {
        String string3;
        try {
            String string4;
            String string5 = string2.replaceAll(" ", "%20");
            TapjoyLog.i(TAPJOY_URL_CONNECTION, "requestURL: " + string5);
            HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(string5).openConnection();
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(30000);
            string3 = string4 = httpURLConnection.getHeaderField("content-length");
        }
        catch (Exception exception) {
            TapjoyLog.e(TAPJOY_URL_CONNECTION, "Exception: " + exception.toString());
            string3 = null;
        }
        TapjoyLog.i(TAPJOY_URL_CONNECTION, "content-length: " + string3);
        return string3;
    }

    public TapjoyHttpURLResponse getResponseFromURL(String string2, String string3) {
        return this.getResponseFromURL(string2, string3, 0);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public TapjoyHttpURLResponse getResponseFromURL(String var1, String var2_3, int var3_2) {
        var4_4 = new TapjoyHttpURLResponse();
        var12_5 = (var1 + var2_3).replaceAll(" ", "%20");
        TapjoyLog.i("TapjoyURLConnection", "baseURL: " + var1);
        TapjoyLog.i("TapjoyURLConnection", "requestURL: " + var12_5);
        TapjoyLog.i("TapjoyURLConnection", "type: " + var3_2);
        var13_6 = (HttpURLConnection)new URL(var12_5).openConnection();
        var13_6.setConnectTimeout(15000);
        var13_6.setReadTimeout(30000);
        if (var3_2 == 1) {
            var13_6.setRequestMethod("POST");
        }
        var13_6.connect();
        var4_4.statusCode = var13_6.getResponseCode();
        var15_7 = new BufferedReader((Reader)new InputStreamReader(var13_6.getInputStream()));
        var16_8 = new StringBuilder();
        while ((var17_9 = var15_7.readLine()) != null) {
            var16_8.append(var17_9 + '\n');
        }
        var4_4.response = var16_8.toString();
        var19_10 = var13_6.getHeaderField("content-length");
        ** if (var19_10 == null) goto lbl30
lbl-1000: // 1 sources:
        {
            try {
                var4_4.contentLength = Integer.valueOf((String)var19_10);
            }
            catch (Exception var20_12) {
                TapjoyLog.e("TapjoyURLConnection", "Exception: " + var20_12.toString());
            }
        }
lbl30: // 1 sources:
        ** GOTO lbl51
        {
            catch (Exception var14_11) {
                block17 : {
                    var6_13 = var13_6;
                    var5_14 = var14_11;
                    break block17;
                    catch (Exception var5_15) {
                        var6_13 = null;
                    }
                }
                TapjoyLog.e("TapjoyURLConnection", "Exception: " + var5_14.toString());
                if (var6_13 != null) {
                    try {
                        if (var4_4.response == null) {
                            var8_16 = new BufferedReader((Reader)new InputStreamReader(var6_13.getErrorStream()));
                            var9_17 = new StringBuilder();
                            while ((var10_18 = var8_16.readLine()) != null) {
                                var9_17.append(var10_18 + '\n');
                            }
                            var4_4.response = var9_17.toString();
                        }
                    }
                    catch (Exception var7_19) {
                        TapjoyLog.e("TapjoyURLConnection", "Exception trying to get error code/content: " + var7_19.toString());
                    }
                }
lbl51: // 7 sources:
                TapjoyLog.i("TapjoyURLConnection", "--------------------");
                TapjoyLog.i("TapjoyURLConnection", "response status: " + var4_4.statusCode);
                TapjoyLog.i("TapjoyURLConnection", "response size: " + var4_4.contentLength);
                TapjoyLog.i("TapjoyURLConnection", "response: ");
                TapjoyLog.i("TapjoyURLConnection", "" + var4_4.response);
                TapjoyLog.i("TapjoyURLConnection", "--------------------");
                return var4_4;
            }
        }
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.AsyncTask
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 *  java.lang.UnsupportedOperationException
 *  java.lang.Void
 *  java.util.ArrayList
 *  java.util.HashSet
 *  java.util.concurrent.atomic.AtomicBoolean
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.conn.ClientConnectionManager
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.json.JSONTokener
 */
package com.amazon.device.ads;

import android.net.Uri;
import android.os.AsyncTask;
import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdBridge;
import com.amazon.device.ads.AdError;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.AdRequest;
import com.amazon.device.ads.AdServiceTimer;
import com.amazon.device.ads.Log;
import com.amazon.device.ads.Metrics;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

class AdLoader {
    protected static String LOG_TAG = "AdLoader";

    AdLoader() {
    }

    protected static class AdRequestTask
    extends AsyncTask<AdRequest, Void, Ad> {
        protected AdBridge bridge_ = null;
        protected AtomicBoolean canceled_ = new AtomicBoolean(false);
        protected HttpClient client_ = null;
        protected AdError error_;
        protected Exception exception_;
        protected HttpParams httpParams_ = new BasicHttpParams();

        protected /* varargs */ Ad doInBackground(AdRequest ... arradRequest) {
            this.bridge_ = arradRequest[0].getAdBridge();
            try {
                Ad ad2 = this.fetchAd(arradRequest[0]);
                return ad2;
            }
            catch (Exception exception) {
                this.exception_ = exception;
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
        protected Ad fetchAd(AdRequest var1) throws Exception {
            HttpConnectionParams.setConnectionTimeout((HttpParams)this.httpParams_, (int)var1.timeout_);
            HttpConnectionParams.setSoTimeout((HttpParams)this.httpParams_, (int)var1.timeout_);
            HttpConnectionParams.setSocketBufferSize((HttpParams)this.httpParams_, (int)8192);
            this.client_ = new DefaultHttpClient(this.httpParams_);
            var2_2 = var1.url_.toString();
            var3_3 = new HttpPost(var2_2);
            var4_4 = new AdServiceTimer(var1.getAdBridge().getAd(), Metrics.MetricType.AAX_LATENCY_GET_AD);
            try {
                var10_5 = this.client_.execute((HttpUriRequest)var3_3);
            }
            catch (IOException var5_9) {
                if (this.canceled_.get()) {
                    var6_10 = "Ad Request cancelled because view was destroyed.";
                    this.error_ = var9_11 = new AdError(500, var6_10);
                } else {
                    var6_10 = "Unknown error occurred.";
                    this.error_ = var7_13 = new AdError(-1, var6_10);
                }
                var8_12 = new Exception(var6_10);
                throw var8_12;
            }
            var4_4.stop();
            var11_6 = var10_5.getEntity();
            if (var10_5.getStatusLine().getStatusCode() != 200 || var11_6 == null || var11_6.getContentLength() == 0L) {
                this.error_ = var12_7 = new AdError(503, "Network error reading data from ad server");
                var13_8 = new Exception("Network error reading data from ad server");
                throw var13_8;
            }
            var14_14 = var11_6.getContent();
            var15_15 = new StringBuffer();
            var16_16 = new byte[4096];
            while ((var17_17 = var14_14.read(var16_16)) != -1) {
                var18_18 = new String(var16_16, 0, var17_17);
                var15_15.append(var18_18);
            }
            try {
                var25_19 = (JSONObject)new JSONTokener(var15_15.toString()).nextValue();
                var26_20 = var25_19.getString("status");
                if (var26_20 == null || var26_20.length() == 0 || var26_20.equals((Object)"ok")) {
                    var27_21 = var25_19.getString("html").replace((CharSequence)"\\n", (CharSequence)"").replace((CharSequence)"\\r", (CharSequence)"").replace((CharSequence)"\\", (CharSequence)"").trim();
                    Log.d(AdLoader.LOG_TAG, "ad Contents: %s", new Object[]{var27_21});
                    var28_22 = var25_19.getJSONArray("creativeTypes");
                    var29_23 = new AdProperties(var28_22);
                    var30_24 = new HashSet();
                } else {
                    var43_38 = var25_19.getString("error");
                    var44_39 = var25_19.getString("errorCode");
                    var45_40 = "Server Message: " + var43_38;
                    this.error_ = var46_41 = new AdError(500, var45_40);
                    Log.w(AdLoader.LOG_TAG, "%s; code: %s", new Object[]{var45_40, var44_39});
                    throw new Exception("AAX did not return an ad");
                }
                for (var31_25 = 0; var31_25 < (var32_27 = var28_22.length()); ++var31_25) {
                    try {
                        var30_24.add((Object)Ad.AAXCreative.getCreative(var28_22.getInt(var31_25)));
                        continue;
                    }
                    catch (UnsupportedOperationException var41_26) {}
                }
                if (!var30_24.isEmpty()) ** break block17
                this.error_ = var33_28 = new AdError(500, "No valid creative types found.");
                Log.w(AdLoader.LOG_TAG, "No valid creative types found.");
                var34_29 = new Exception("No valid creative types found.");
                throw var34_29;
            }
            catch (JSONException var23_30) {
                this.error_ = var24_31 = new AdError(500, "Unable to parse response from ad server.");
                Log.w(AdLoader.LOG_TAG, "Unable to parse response from ad server.");
                throw new Exception("AAX did not return an ad");
            }
            {
                
                var35_32 = Ad.AAXCreative.determineTypeOrder((HashSet<Ad.AAXCreative>)var30_24);
                var36_33 = Uri.parse((String)var2_2).getQueryParameter("sz").split("x");
                var37_34 = Integer.parseInt((String)var36_33[0]);
                var38_35 = Integer.parseInt((String)var36_33[1]);
                var39_36 = var25_19.getString("pixelURL");
                var40_37 = var1.getAdBridge().getAd();
                var40_37.setHeight(var38_35);
                var40_37.setWidth(var37_34);
                var40_37.setCreative(var27_21);
                var40_37.setCreativeTypes(var35_32);
                var40_37.setPixelUrl(var39_36);
                var40_37.setProperties(var29_23);
                return var40_37;
            }
            catch (UnsupportedOperationException var20_42) {
                var21_43 = var20_42.getMessage();
                this.error_ = var22_44 = new AdError(500, var21_43);
                Log.w(AdLoader.LOG_TAG, var21_43);
                throw new Exception("AAX did not return an ad");
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void onPostExecute(Ad ad2) {
            if (ad2 == null) {
                this.bridge_.setIsLoading(false);
                if (this.exception_ != null) {
                    String string = "Exception caught while loading ad: " + (Object)((Object)this.exception_);
                    Log.d(AdLoader.LOG_TAG, string);
                    this.exception_.printStackTrace();
                    if (this.error_ == null) {
                        this.error_ = new AdError(-1, string);
                    }
                }
                if (this.error_ != null) {
                    this.bridge_.adFailed(this.error_);
                } else {
                    this.bridge_.adFailed(new AdError(-1, "Unknown error occurred."));
                }
            } else {
                this.bridge_.handleResponse();
            }
            this.releaseResources();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void releaseResources() {
            AdRequestTask adRequestTask = this;
            synchronized (adRequestTask) {
                this.canceled_.set(true);
                if (this.client_ != null) {
                    ClientConnectionManager clientConnectionManager = this.client_.getConnectionManager();
                    if (clientConnectionManager != null) {
                        clientConnectionManager.shutdown();
                    }
                    this.client_ = null;
                }
            }
            this.exception_ = null;
        }
    }

}


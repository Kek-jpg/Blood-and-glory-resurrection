/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  java.io.IOException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Void
 *  org.apache.http.HttpResponse
 *  org.apache.http.client.ClientProtocolException
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 */
package com.amazon.device.ads;

import android.os.AsyncTask;
import com.amazon.device.ads.AdMetrics;
import com.amazon.device.ads.Log;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

class AdMetricsSubmitAaxTask
extends AsyncTask<AdMetrics, Void, Void> {
    private static final int HTTP_TIMEOUT = 20000;
    private static final String LOG_TAG = "AdMetricsSubmitTask";
    private static final int SOCKET_BUFFER_SIZE = 8192;

    AdMetricsSubmitAaxTask() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* varargs */ Void doInBackground(AdMetrics ... arradMetrics) {
        int n2 = arradMetrics.length;
        int n3 = 0;
        while (n3 < n2) {
            AdMetrics adMetrics = arradMetrics[n3];
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, (int)20000);
            HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, (int)20000);
            HttpConnectionParams.setSocketBufferSize((HttpParams)basicHttpParams, (int)8192);
            HttpGet httpGet = new HttpGet(adMetrics.getAaxUrl());
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient((HttpParams)basicHttpParams);
            try {
                defaultHttpClient.execute((HttpUriRequest)httpGet);
            }
            catch (ClientProtocolException clientProtocolException) {
                Object[] arrobject = new Object[]{clientProtocolException.getMessage()};
                Log.e(LOG_TAG, "Unable to submit metrics for ad due to a ClientProtocolException, msg: %s", arrobject);
            }
            catch (IOException iOException) {
                Object[] arrobject = new Object[]{iOException.getMessage()};
                Log.e(LOG_TAG, "Unable to submit metrics for ad due to an IOException, msg: %s", arrobject);
            }
            catch (IllegalStateException illegalStateException) {
                Object[] arrobject = new Object[]{illegalStateException.getMessage()};
                Log.e(LOG_TAG, "Unable to submit metrics for ad due to an IllegalStateException, msg: %s", arrobject);
            }
            ++n3;
        }
        return null;
    }
}


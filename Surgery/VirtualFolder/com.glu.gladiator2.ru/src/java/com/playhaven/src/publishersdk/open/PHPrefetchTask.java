/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  java.io.BufferedOutputStream
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.net.MalformedURLException
 *  java.net.URL
 *  java.util.zip.GZIPInputStream
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.conn.ClientConnectionManager
 *  org.apache.http.impl.client.DefaultHttpClient
 */
package com.playhaven.src.publishersdk.open;

import android.os.AsyncTask;
import android.os.Build;
import com.jakewharton.DiskLruCache;
import com.playhaven.src.common.PHAPIRequest;
import com.playhaven.src.common.PHCrashReport;
import com.playhaven.src.utils.PHStringUtil;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;

public class PHPrefetchTask
extends AsyncTask<Integer, Integer, Integer> {
    private static final Integer BUFFER_SIZE = 1024;
    private DiskLruCache cache;
    public Listener listener;
    public URL url;

    private void disableConnectionReuseIfNecessary() {
        if (Build.VERSION.SDK_INT < 8) {
            System.setProperty((String)"http.keepAlive", (String)"false");
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected /* varargs */ Integer doInBackground(Integer ... var1) {
        var2_2 = 400;
        PHPrefetchTask.super.disableConnectionReuseIfNecessary();
        var22_3 = this;
        // MONITORENTER : var22_3
        if (this.url == null) {
            var21_4 = 400;
            // MONITOREXIT : var22_3
            return var21_4;
        }
        var7_5 = new DefaultHttpClient();
        var8_6 = new HttpGet(this.url.toString());
        var8_6.addHeader("Accept-Encoding", "gzip");
        var9_7 = var7_5.execute((HttpUriRequest)var8_6);
        var4_9 = var10_8 = var9_7.getStatusLine().getStatusCode();
        if (var4_9 == 200) ** GOTO lbl21
        try {
            var20_10 = var4_9;
            // MONITOREXIT : var22_3
            return var20_10;
lbl21: // 1 sources:
            var12_11 = var9_7.getEntity();
            var13_12 = this.getCache().edit(this.url.toString());
            var14_13 = new BufferedOutputStream(var13_12.newOutputStream(PHAPIRequest.PRECACHE_FILE_KEY_INDEX));
            var15_14 = var12_11.getContentEncoding();
            var16_15 = var15_14 == null ? null : var15_14.getValue();
            if (var16_15 == null || !var16_15.equalsIgnoreCase("gzip")) ** GOTO lbl39
            var17_16 = new GZIPInputStream(var12_11.getContent());
            var18_17 = new byte[PHPrefetchTask.BUFFER_SIZE.intValue()];
            while ((var19_18 = var17_16.read(var18_17)) != -1) {
                var14_13.write(var18_17, 0, var19_18);
            }
            ** GOTO lbl37
        }
        catch (Throwable var11_19) {
            block12 : {
                block13 : {
                    var2_2 = var4_9;
                    var6_20 = var11_19;
                    break block12;
lbl37: // 1 sources:
                    var17_16.close();
                    break block13;
lbl39: // 1 sources:
                    var12_11.writeTo((OutputStream)var14_13);
                }
                var14_13.flush();
                var14_13.close();
                var13_12.commit();
                var7_5.getConnectionManager().shutdown();
                this.getCache().flush();
                // MONITOREXIT : var22_3
                return var4_9;
                catch (Throwable var6_21) {}
            }
            try {
                throw var6_20;
            }
            catch (Exception var3_22) {
                var4_9 = var2_2;
                PHCrashReport.reportCrash(var3_22, "PHPrefetchTask - doInBackground", PHCrashReport.Urgency.low);
                return var4_9;
            }
        }
    }

    public DiskLruCache getCache() {
        if (this.cache == null) {
            this.cache = DiskLruCache.getSharedDiskCache();
        }
        return this.cache;
    }

    public Listener getOnPrefetchDoneListener() {
        return this.listener;
    }

    public URL getURL() {
        return this.url;
    }

    protected void onPostExecute(Integer n) {
        PHStringUtil.log("Pre-fetch finished with response code: " + (Object)n);
        if (this.listener != null) {
            this.listener.prefetchDone(n);
        }
    }

    protected /* varargs */ void onProgressUpdate(Integer ... arrinteger) {
        PHStringUtil.log("Progress update in prefetch operation: " + arrinteger);
    }

    public void setCache(DiskLruCache diskLruCache) {
        this.cache = diskLruCache;
    }

    public void setOnPrefetchDoneListener(Listener listener) {
        this.listener = listener;
    }

    public void setURL(String string2) {
        try {
            this.url = new URL(string2);
            return;
        }
        catch (MalformedURLException malformedURLException) {
            this.url = null;
            PHStringUtil.log("Malformed URL in PHPrefetchTask: " + string2);
            return;
        }
    }

    public static interface Listener {
        public void prefetchDone(int var1);
    }

}


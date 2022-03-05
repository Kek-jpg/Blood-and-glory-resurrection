/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.AsyncTask
 *  java.io.File
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Hashtable
 *  java.util.concurrent.ConcurrentLinkedQueue
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.playhaven.src.publishersdk.open;

import android.content.Context;
import android.os.AsyncTask;
import com.jakewharton.DiskLruCache;
import com.playhaven.src.common.PHAPIRequest;
import com.playhaven.src.common.PHConfig;
import com.playhaven.src.common.PHCrashReport;
import com.playhaven.src.common.PHSession;
import com.playhaven.src.publishersdk.open.PHPrefetchTask;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.json.JSONArray;
import org.json.JSONObject;

public class PHPublisherOpenRequest
extends PHAPIRequest
implements PHPrefetchTask.Listener {
    private ConcurrentLinkedQueue<PHPrefetchTask> prefetchTasks;
    private PrefetchListener prefetch_listener;
    private PHSession session;
    public boolean startPrecachingImmediately;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public PHPublisherOpenRequest(Context context) {
        super(context);
        this.prefetchTasks = new ConcurrentLinkedQueue();
        this.startPrecachingImmediately = true;
        Class<PHPublisherOpenRequest> class_ = PHPublisherOpenRequest.class;
        synchronized (PHPublisherOpenRequest.class) {
            try {
                if (PHConfig.precache) {
                    DiskLruCache diskLruCache = DiskLruCache.getSharedDiskCache();
                    if (diskLruCache == null) {
                        DiskLruCache.createSharedDiskCache(new File((Object)context.getCacheDir() + File.separator + "apicache"), APP_CACHE_VERSION, 1, PHConfig.precache_size);
                    } else if (diskLruCache.isClosed()) {
                        diskLruCache.open();
                    }
                }
            }
            catch (Exception exception) {
                PHCrashReport.reportCrash(exception, "PHPublisherOpenRequest - PHPublisherOpenRequest", PHCrashReport.Urgency.critical);
            }
            this.session = PHSession.getInstance(context);
            return;
        }
    }

    public PHPublisherOpenRequest(Context context, PHAPIRequest.Delegate delegate) {
        super(context);
        this.setDelegate(delegate);
    }

    @Override
    public String baseURL() {
        return super.createAPIURL("/v3/publisher/open/");
    }

    @Override
    public Hashtable<String, String> getAdditionalParams() {
        Hashtable hashtable = new Hashtable();
        hashtable.put((Object)"ssum", (Object)String.valueOf((long)this.session.getTotalTime()));
        hashtable.put((Object)"scount", (Object)String.valueOf((long)this.session.getSessionCount()));
        return hashtable;
    }

    public PrefetchListener getPrefetchListener() {
        return this.prefetch_listener;
    }

    public ConcurrentLinkedQueue<PHPrefetchTask> getPrefetchTasks() {
        return this.prefetchTasks;
    }

    public PHSession getSession() {
        return this.session;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void handleRequestSuccess(JSONObject jSONObject) {
        if (PHConfig.precache && jSONObject.has("precache")) {
            this.prefetchTasks.clear();
            JSONArray jSONArray = jSONObject.optJSONArray("precache");
            if (jSONArray != null) {
                for (int i2 = 0; i2 < jSONArray.length(); ++i2) {
                    String string2 = jSONArray.optString(i2);
                    if (string2 == null) continue;
                    PHPrefetchTask pHPrefetchTask = new PHPrefetchTask();
                    pHPrefetchTask.setOnPrefetchDoneListener((PHPrefetchTask.Listener)this);
                    pHPrefetchTask.setURL(string2);
                    this.prefetchTasks.add((Object)pHPrefetchTask);
                }
            }
            if (this.startPrecachingImmediately) {
                this.startNextPrefetch();
            }
        }
        if (this.prefetchTasks.size() == 0) {
            try {
                DiskLruCache.getSharedDiskCache().close();
            }
            catch (IOException iOException) {
                PHCrashReport.reportCrash((Exception)((Object)iOException), "PHPublisherOpenRequest - handleRequestSuccess", PHCrashReport.Urgency.high);
            }
        }
        this.session.startAndReset();
        super.handleRequestSuccess(jSONObject);
    }

    @Override
    public void prefetchDone(int n) {
        try {
            if (this.prefetchTasks.size() > 0 && this.startPrecachingImmediately) {
                this.startNextPrefetch();
                return;
            }
            DiskLruCache.getSharedDiskCache().close();
            if (this.prefetch_listener != null) {
                this.prefetch_listener.prefetchFinished((PHPublisherOpenRequest)this);
                return;
            }
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHPublisherOpenRequest - prefetchDone", PHCrashReport.Urgency.low);
        }
    }

    @Override
    public void send() {
        this.session.start();
        super.send();
    }

    public void setPrefetchListener(PrefetchListener prefetchListener) {
        this.prefetch_listener = prefetchListener;
    }

    public void startNextPrefetch() {
        if (this.prefetchTasks.size() > 0) {
            ((PHPrefetchTask)((Object)this.prefetchTasks.poll())).execute((Object[])new Integer[0]);
        }
    }

    public static interface PrefetchListener {
        public void prefetchFinished(PHPublisherOpenRequest var1);
    }

}


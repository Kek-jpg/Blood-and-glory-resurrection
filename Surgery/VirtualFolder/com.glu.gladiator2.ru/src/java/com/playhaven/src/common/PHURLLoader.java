/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.net.Uri
 *  android.os.AsyncTask
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.ref.WeakReference
 *  java.nio.ByteBuffer
 *  java.util.LinkedHashSet
 *  java.util.List
 *  org.json.JSONObject
 */
package com.playhaven.src.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import com.playhaven.src.common.PHAsyncRequest;
import com.playhaven.src.common.PHConfig;
import com.playhaven.src.utils.PHStringUtil;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.LinkedHashSet;
import java.util.List;
import org.json.JSONObject;

public class PHURLLoader
implements PHAsyncRequest.Delegate {
    private static LinkedHashSet<PHURLLoader> allLoaders = new LinkedHashSet();
    private final String MARKET_URL_TEMPLATE;
    private final int MAXIMUM_REDIRECTS;
    private String callback;
    private PHAsyncRequest conn;
    private WeakReference<Context> context;
    public Delegate delegate;
    public boolean isLoading;
    public boolean openFinalURL;
    private ProgressDialog progressDialog;
    private String targetURL;

    public PHURLLoader(Context context) {
        this.MARKET_URL_TEMPLATE = "http://play.google.com/store/apps/details?id=%s";
        this.isLoading = false;
        this.MAXIMUM_REDIRECTS = 10;
        this.progressDialog = new ProgressDialog(context);
        this.context = new WeakReference((Object)context);
        this.openFinalURL = true;
    }

    public PHURLLoader(Context context, Delegate delegate) {
        super(context);
        this.delegate = delegate;
    }

    public static void addLoader(PHURLLoader pHURLLoader) {
        allLoaders.add((Object)pHURLLoader);
    }

    private void fail() {
        if (this.delegate != null) {
            this.delegate.loaderFailed(this);
        }
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void finish() {
        if (this.isLoading) {
            this.isLoading = false;
            this.targetURL = this.conn.getLastRedirectURL();
            PHStringUtil.log("PHURLLoader - final redirect location: " + this.targetURL);
            if (this.openFinalURL && this.targetURL != null && !this.targetURL.equals((Object)"") && this.context.get() != null) {
                if (this.targetURL.startsWith("market:")) {
                    this.redirectMarketURL(this.targetURL);
                } else {
                    this.launchActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)this.targetURL)));
                }
            }
            if (this.delegate != null) {
                this.delegate.loaderFinished(this);
            }
            this.invalidate();
        }
    }

    public static LinkedHashSet<PHURLLoader> getAllLoaders() {
        return allLoaders;
    }

    public static void invalidateLoaders(Delegate delegate) {
        for (PHURLLoader pHURLLoader : (LinkedHashSet)allLoaders.clone()) {
            if (pHURLLoader.delegate != delegate) continue;
            pHURLLoader.invalidate();
        }
    }

    private void launchActivity(Intent intent) {
        if (PHConfig.runningTests) {
            return;
        }
        ((Context)this.context.get()).startActivity(intent);
    }

    public static void removeLoader(PHURLLoader pHURLLoader) {
        allLoaders.remove((Object)pHURLLoader);
    }

    public String getCallback() {
        return this.callback;
    }

    public PHAsyncRequest getConnection() {
        return this.conn;
    }

    public ProgressDialog getDialog() {
        return this.progressDialog;
    }

    public String getTargetURL() {
        return this.targetURL;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void invalidate() {
        this.delegate = null;
        if (this.conn != null && this.progressDialog != null) {
            PHURLLoader pHURLLoader = this;
            synchronized (pHURLLoader) {
                this.conn.cancel(true);
                PHURLLoader.removeLoader(this);
            }
            this.progressDialog.dismiss();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void open() {
        if (!JSONObject.NULL.equals((Object)this.targetURL) && this.targetURL.length() > 0) {
            this.progressDialog.show();
            Object[] arrobject = new Object[]{this.targetURL};
            PHStringUtil.log(String.format((String)"Opening url in PHURLLoader: %s", (Object[])arrobject));
            this.isLoading = true;
            this.conn = new PHAsyncRequest(this);
            this.conn.setMaxRedirects(10);
            this.conn.request_type = PHAsyncRequest.RequestType.Get;
            PHAsyncRequest pHAsyncRequest = this.conn;
            Object[] arrobject2 = new Uri[]{Uri.parse((String)this.targetURL)};
            pHAsyncRequest.execute(arrobject2);
            PHURLLoader pHURLLoader = this;
            synchronized (pHURLLoader) {
                PHURLLoader.addLoader(this);
                return;
            }
        }
        if (this.delegate != null) {
            this.delegate.loaderFinished(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void redirectMarketURL(String string2) {
        Intent intent;
        if (this.context.get() == null) {
            return;
        }
        PHStringUtil.log("Got a market:// URL, verifying market app is installed");
        Intent intent2 = new Intent("android.intent.action.VIEW");
        intent2.setData(Uri.parse((String)string2));
        if (((Context)this.context.get()).getPackageManager().queryIntentActivities(intent2, 65536).size() == 0) {
            PHStringUtil.log("Market app is not installed and market:// not supported!");
            Object[] arrobject = new Object[]{Uri.parse((String)string2).getQueryParameter("id")};
            intent = new Intent("android.intent.action.VIEW", Uri.parse((String)String.format((String)"http://play.google.com/store/apps/details?id=%s", (Object[])arrobject)));
        } else {
            intent = intent2;
        }
        PHURLLoader.super.launchActivity(intent);
    }

    @Override
    public void requestFailed(Exception exception) {
        PHStringUtil.log("PHURLLoader failed with error: " + (Object)((Object)exception));
        PHURLLoader.super.fail();
    }

    @Override
    public void requestFinished(ByteBuffer byteBuffer, int n) {
        if (n < 300) {
            PHStringUtil.log("PHURLLoader finishing from initial url: " + this.targetURL);
            PHURLLoader.super.finish();
            return;
        }
        PHStringUtil.log("PHURLLoader failing from initial url: " + this.targetURL + " with error code: " + n);
        PHURLLoader.super.fail();
    }

    public void setCallback(String string2) {
        this.callback = string2;
    }

    public void setConnection(PHAsyncRequest pHAsyncRequest) {
        this.conn = pHAsyncRequest;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public void setTargetURL(String string2) {
        this.targetURL = string2;
    }

    public static interface Delegate {
        public void loaderFailed(PHURLLoader var1);

        public void loaderFinished(PHURLLoader var1);
    }

}


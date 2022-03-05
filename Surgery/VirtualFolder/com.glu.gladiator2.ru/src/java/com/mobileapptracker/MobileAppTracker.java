/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.database.Cursor
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.Uri
 *  android.net.wifi.WifiInfo
 *  android.net.wifi.WifiManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.telephony.TelephonyManager
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.Display
 *  android.view.WindowManager
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  java.io.BufferedReader
 *  java.io.File
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.io.UnsupportedEncodingException
 *  java.lang.CharSequence
 *  java.lang.Character
 *  java.lang.ClassCastException
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.InterruptedException
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.net.URLEncoder
 *  java.text.SimpleDateFormat
 *  java.util.Arrays
 *  java.util.Date
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Set
 *  java.util.TimeZone
 *  java.util.UUID
 *  java.util.concurrent.ConcurrentHashMap
 *  java.util.concurrent.ExecutorService
 *  java.util.concurrent.Executors
 *  java.util.concurrent.Semaphore
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.conn.ClientConnectionManager
 *  org.apache.http.conn.scheme.PlainSocketFactory
 *  org.apache.http.conn.scheme.Scheme
 *  org.apache.http.conn.scheme.SchemeRegistry
 *  org.apache.http.conn.scheme.SocketFactory
 *  org.apache.http.conn.ssl.SSLSocketFactory
 *  org.apache.http.entity.StringEntity
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mobileapptracker;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.mobileapptracker.Encryption;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MobileAppTracker {
    private static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
    private static final Uri ATTRIBUTION_ID_CONTENT_URI = Uri.parse((String)"content://com.facebook.katana.provider.AttributionIdProvider");
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static boolean DEBUG = false;
    private static final String IV = "heF9BATUfWuISyO8";
    private static final int MAX_DUMP_SIZE = 50;
    static final String PREFS_FACEBOOK_INTENT = "mat_fb_intent";
    private static final String PREFS_INSTALL = "mat_install";
    private static final String PREFS_MAT_ID = "mat_id";
    private static final String PREFS_NAME = "mat_queue";
    static final String PREFS_REFERRER = "mat_referrer";
    private static final String PREFS_VERSION = "mat_app_version";
    private static final String SDK_VERSION = "2.0";
    private static final String TAG = "MobileAppTracker";
    private static final int TIMEOUT = 5000;
    private SharedPreferences EventQueue;
    private SharedPreferences SP;
    private Encryption URLEnc;
    private HttpClient client;
    private boolean constructed;
    private Context context;
    private List<String> encryptList;
    private boolean httpsEncryption;
    private boolean initialized;
    private String install;
    private ConcurrentHashMap<String, String> paramTable;
    private ExecutorService pool;
    private Semaphore queueAvailable;

    static {
        DEBUG = false;
    }

    public MobileAppTracker(Context context) {
        super(context, true, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MobileAppTracker(Context context, boolean bl, boolean bl2) {
        BroadcastReceiver broadcastReceiver;
        this.initialized = false;
        this.constructed = false;
        this.httpsEncryption = true;
        if (this.constructed) {
            return;
        }
        this.constructed = true;
        this.context = context;
        this.pool = Executors.newSingleThreadExecutor();
        this.queueAvailable = new Semaphore(1, true);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", (SocketFactory)PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", (SocketFactory)SSLSocketFactory.getSocketFactory(), 443));
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setSocketBufferSize((HttpParams)basicHttpParams, (int)8192);
        HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, (int)5000);
        HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, (int)5000);
        this.client = new DefaultHttpClient((ClientConnectionManager)new ThreadSafeClientConnManager((HttpParams)basicHttpParams, schemeRegistry), (HttpParams)basicHttpParams);
        this.paramTable = new ConcurrentHashMap();
        this.encryptList = Arrays.asList((Object[])new String[]{"ir", "d", "db", "dm", "ma", "ov", "cc", "l", "an", "pn", "av", "dc", "ad", "r", "c", "id,", "ua", "tpid", "ar", "connection_type", "mobile_country_code", "mobile_network_code", "screen_density", "screen_layout_size", "tracking_id"});
        this.initialized = MobileAppTracker.super.initializeVariables(context, bl, bl2);
        this.URLEnc = new Encryption(this.getKey(), IV);
        this.EventQueue = context.getSharedPreferences(PREFS_NAME, 0);
        this.SP = context.getSharedPreferences(PREFS_INSTALL, 0);
        this.install = this.SP.getString("install", "");
        if (this.initialized && MobileAppTracker.super.getQueueSize() > 0 && MobileAppTracker.super.isOnline()) {
            MobileAppTracker.super.dumpQueue();
        }
        broadcastReceiver = new BroadcastReceiver(){

            public void onReceive(Context context, Intent intent) {
                if (((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo() != null && MobileAppTracker.this.getQueueSize() > 0) {
                    MobileAppTracker.this.dumpQueue();
                }
            }
        };
        try {
            context.getApplicationContext().unregisterReceiver(broadcastReceiver);
        }
        catch (IllegalArgumentException illegalArgumentException) {}
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        context.getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void addEventToQueue(String string2, String string3) {
        try {
            this.queueAvailable.acquire();
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("link", (Object)string2);
            if (string3 != null) {
                jSONObject.put("json", (Object)string3);
            }
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
        SharedPreferences.Editor editor = this.EventQueue.edit();
        int n = 1 + this.EventQueue.getInt("queuesize", 0);
        editor.putString(Integer.valueOf((int)n).toString(), jSONObject.toString());
        editor.putInt("queuesize", n);
        editor.commit();
        this.queueAvailable.release();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String buildLink() {
        String string2;
        StringBuilder stringBuilder;
        block17 : {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            string2 = simpleDateFormat.format(new Date());
            try {
                String string3;
                string2 = string3 = URLEncoder.encode((String)string2, (String)"UTF-8");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                if (!DEBUG) break block17;
                Log.d((String)TAG, (String)"convert system date failed");
            }
        }
        String string4 = "https://";
        if (!this.httpsEncryption) {
            string4 = "http://";
        }
        stringBuilder = new StringBuilder(string4);
        stringBuilder.append(this.getBrand_id()).append(".engine.mobileapptracking.com/serve?s=android&ver=").append(SDK_VERSION);
        for (String string5 : this.paramTable.keySet()) {
            if (this.encryptList.contains((Object)string5)) continue;
            stringBuilder.append("&").append(string5).append("=").append((String)this.paramTable.get((Object)string5));
        }
        if (DEBUG) {
            stringBuilder.append("&debug=1&skip_dup=1");
        }
        Uri uri = Uri.parse((String)("content://" + this.getPackageName() + "/referrer_apps"));
        Cursor cursor = this.context.getContentResolver().query(uri, null, null, null, "publisher_package_name desc");
        if (cursor != null && cursor.moveToFirst()) {
            String string6;
            string6 = cursor.getString(cursor.getColumnIndex("tracking_id"));
            try {
                String string7;
                string6 = string7 = URLEncoder.encode((String)string6, (String)"UTF-8");
            }
            catch (Exception exception) {}
            this.paramTable.put((Object)"tracking_id", (Object)string6);
        }
        try {
            cursor.close();
        }
        catch (NullPointerException nullPointerException) {}
        StringBuilder stringBuilder2 = new StringBuilder();
        for (String string8 : this.encryptList) {
            if (this.paramTable.get((Object)string8) == null) continue;
            stringBuilder2.append("&").append(string8).append("=").append((String)this.paramTable.get((Object)string8));
        }
        stringBuilder2.append("&sd=").append(string2);
        if (this.getFacebookAttributionId() != null) {
            stringBuilder.append("&fb_cookie_id=").append(this.getFacebookAttributionId());
        }
        this.SP = this.context.getSharedPreferences(PREFS_FACEBOOK_INTENT, 0);
        String string9 = this.SP.getString("action", "");
        if (string9.length() != 0) {
            try {
                String string10;
                string9 = string10 = URLEncoder.encode((String)string9, (String)"UTF-8");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {}
            stringBuilder.append("&source=").append(string9);
            SharedPreferences.Editor editor = this.SP.edit();
            editor.remove("action");
            editor.commit();
        }
        try {
            StringBuilder stringBuilder3;
            stringBuilder2 = stringBuilder3 = new StringBuilder(this.URLEnc.bytesToHex(this.URLEnc.encrypt(stringBuilder2.toString())));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        stringBuilder.append("&da=").append(stringBuilder2.toString());
        return stringBuilder.toString();
    }

    private static boolean containsChar(String string2) {
        char[] arrc = string2.toCharArray();
        int n = arrc.length;
        for (int i2 = 0; i2 < n; ++i2) {
            if (!Character.isLetter((char)arrc[i2])) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dumpQueue() {
        MobileAppTracker mobileAppTracker = this;
        synchronized (mobileAppTracker) {
            int n = this.getQueueSize();
            if (n != 0) {
                int n2 = 1;
                if (n > 50) {
                    n2 = 1 + (n - 50);
                }
                while (n2 <= n) {
                    String string2 = Integer.valueOf((int)n2).toString();
                    String string3 = this.EventQueue.getString(string2, null);
                    if (string3 != null) {
                        String string4;
                        String string5 = null;
                        try {
                            JSONObject jSONObject = new JSONObject(string3);
                            string5 = (String)jSONObject.get("link");
                            boolean bl = jSONObject.has("json");
                            string4 = null;
                            if (bl) {
                                string4 = (String)jSONObject.get("json");
                            }
                        }
                        catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                            string4 = null;
                        }
                        if (string5 != null) {
                            try {
                                this.setQueueSize(-1 + this.getQueueSize());
                                SharedPreferences.Editor editor = this.EventQueue.edit();
                                editor.remove(string2);
                                editor.commit();
                                this.pool.execute((Runnable)new getLink(string5, string4));
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                    ++n2;
                }
            }
            return;
        }
    }

    private String getDeviceId(Context context) {
        return ((TelephonyManager)context.getSystemService("phone")).getDeviceId();
    }

    private String getFacebookAttributionId() {
        String[] arrstring = new String[]{ATTRIBUTION_ID_COLUMN_NAME};
        Cursor cursor = this.context.getContentResolver().query(ATTRIBUTION_ID_CONTENT_URI, arrstring, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        return cursor.getString(cursor.getColumnIndex(ATTRIBUTION_ID_COLUMN_NAME));
    }

    private int getQueueSize() {
        return this.EventQueue.getInt("queuesize", 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean initializeVariables(Context context, boolean bl, boolean bl2) {
        WifiInfo wifiInfo;
        WifiManager wifiManager;
        this.setBrand_id("2376");
        this.setEvent_id("3464240");
        this.setKey("e0228dbb857348d34496d746b54c6316");
        this.setAction("conversion");
        if (!(this.paramTable.containsKey((Object)"adv") && this.paramTable.containsKey((Object)"ei") && this.paramTable.containsKey((Object)"k") && this.paramTable.containsKey((Object)"ac"))) {
            return false;
        }
        this.SP = context.getSharedPreferences(PREFS_MAT_ID, 0);
        String string2 = this.SP.getString(PREFS_MAT_ID, "");
        if (string2.length() == 0) {
            string2 = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = this.SP.edit();
            editor.putString(PREFS_MAT_ID, string2);
            editor.commit();
        }
        MobileAppTracker.super.setMatId(string2);
        MobileAppTracker.super.setAndroidId(Settings.Secure.getString((ContentResolver)context.getContentResolver(), (String)"android_id"));
        MobileAppTracker.super.setDeviceModel(Build.MODEL);
        MobileAppTracker.super.setDeviceBrand(Build.MANUFACTURER);
        MobileAppTracker.super.setOsVersion(Build.VERSION.RELEASE);
        if (bl) {
            MobileAppTracker.super.setDeviceId(MobileAppTracker.super.getDeviceId(context));
        }
        if (bl2 && (wifiManager = (WifiManager)context.getSystemService("wifi")) != null && (wifiInfo = wifiManager.getConnectionInfo()) != null && wifiInfo.getMacAddress() != null) {
            MobileAppTracker.super.setMacAddress(wifiInfo.getMacAddress());
        }
        if (((ConnectivityManager)context.getSystemService("connectivity")).getNetworkInfo(1).isConnected()) {
            MobileAppTracker.super.setConnectionType("WIFI");
        } else {
            MobileAppTracker.super.setConnectionType("mobile");
        }
        MobileAppTracker.super.setCountryCode("unknown");
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        if (telephonyManager != null) {
            if (telephonyManager.getNetworkCountryIso() != null) {
                MobileAppTracker.super.setCountryCode(telephonyManager.getNetworkCountryIso());
            } else if (bl && telephonyManager.getSimCountryIso() != null) {
                MobileAppTracker.super.setCountryCode(telephonyManager.getSimCountryIso());
            }
            MobileAppTracker.super.setCarrier(telephonyManager.getNetworkOperatorName());
            String string3 = telephonyManager.getNetworkOperator();
            if (string3 != null) {
                try {
                    String string4 = string3.substring(0, 3);
                    String string5 = string3.substring(3);
                    MobileAppTracker.super.setMCC(string4);
                    MobileAppTracker.super.setMNC(string5);
                }
                catch (Exception exception) {}
            }
        }
        MobileAppTracker.super.setLanguage(Locale.getDefault().getDisplayLanguage(Locale.US));
        this.setCurrencyCode("USD");
        try {
            Resources resources = context.getResources();
            MobileAppTracker.super.setAppName(resources.getText(resources.getIdentifier("app_name", "string", context.getPackageName())).toString());
        }
        catch (Resources.NotFoundException notFoundException) {
            MobileAppTracker.super.setAppName("unknown");
        }
        this.setPackageName(context.getPackageName());
        try {
            this.SP = context.getSharedPreferences(PREFS_REFERRER, 0);
            this.setReferrer(this.SP.getString("referrer", ""));
        }
        catch (ClassCastException classCastException) {
            this.setReferrer("unknown");
        }
        try {
            this.SP = context.getSharedPreferences(PREFS_INSTALL, 0);
            this.install = this.SP.getString("install", "");
        }
        catch (ClassCastException classCastException) {
            this.install = "";
        }
        try {
            MobileAppTracker.super.setAppVersion(context.getPackageManager().getPackageInfo((String)this.context.getPackageName(), (int)0).versionCode);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            MobileAppTracker.super.setAppVersion(0);
        }
        try {
            String string6 = context.getPackageManager().getApplicationInfo((String)this.context.getPackageName(), (int)0).sourceDir;
            File file = new File(string6);
            Date date = new Date(file.lastModified());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone((String)"UTC"));
            MobileAppTracker.super.setInstallDate(simpleDateFormat.format(date));
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            MobileAppTracker.super.setInstallDate("0");
        }
        WebView webView = new WebView(this.context);
        webView.setVisibility(8);
        MobileAppTracker.super.setUserAgent(webView.getSettings().getUserAgentString());
        webView.destroy();
        float f2 = context.getResources().getDisplayMetrics().density;
        if ((double)f2 == 0.75) {
            MobileAppTracker.super.setScreenDensity("ldpi");
        } else if ((double)f2 == 1.0) {
            MobileAppTracker.super.setScreenDensity("mdpi");
        } else if ((double)f2 == 1.5) {
            MobileAppTracker.super.setScreenDensity("hdpi");
        } else if ((double)f2 == 2.0) {
            MobileAppTracker.super.setScreenDensity("xhdpi");
        }
        WindowManager windowManager = (WindowManager)context.getSystemService("window");
        int n = windowManager.getDefaultDisplay().getWidth();
        int n2 = windowManager.getDefaultDisplay().getHeight();
        MobileAppTracker.super.setScreenSize(Integer.toString((int)n) + "x" + Integer.toString((int)n2));
        return true;
    }

    private boolean isOnline() {
        try {
            NetworkInfo networkInfo = ((ConnectivityManager)this.context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (networkInfo != null) {
                return true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void putInParamTable(String string2, String string3) {
        try {
            string3 = URLEncoder.encode((String)string3, (String)"UTF-8");
            this.paramTable.put((Object)string2, (Object)string3);
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            if (!DEBUG) return;
            {
                Log.d((String)TAG, (String)("Failed encoding " + string3));
                return;
            }
        }
        catch (NullPointerException nullPointerException) {
            if (!DEBUG) return;
            Log.d((String)TAG, (String)("Failed to set " + string2 + ": received null"));
            return;
        }
    }

    private void setAndroidId(String string2) {
        MobileAppTracker.super.putInParamTable("ad", string2);
    }

    private void setAppName(String string2) {
        MobileAppTracker.super.putInParamTable("an", string2);
    }

    private void setAppVersion(int n) {
        MobileAppTracker.super.putInParamTable("av", Integer.toString((int)n));
    }

    private void setCarrier(String string2) {
        MobileAppTracker.super.putInParamTable("dc", string2);
    }

    private void setConnectionType(String string2) {
        MobileAppTracker.super.putInParamTable("connection_type", string2);
    }

    private void setCountryCode(String string2) {
        MobileAppTracker.super.putInParamTable("cc", string2);
    }

    private void setDeviceBrand(String string2) {
        MobileAppTracker.super.putInParamTable("db", string2);
    }

    private void setDeviceId(String string2) {
        MobileAppTracker.super.putInParamTable("d", string2);
    }

    private void setDeviceModel(String string2) {
        MobileAppTracker.super.putInParamTable("dm", string2);
    }

    private void setInstallDate(String string2) {
        MobileAppTracker.super.putInParamTable("id", string2);
    }

    private void setLanguage(String string2) {
        MobileAppTracker.super.putInParamTable("l", string2);
    }

    private void setMCC(String string2) {
        MobileAppTracker.super.putInParamTable("mobile_country_code", string2);
    }

    private void setMNC(String string2) {
        MobileAppTracker.super.putInParamTable("mobile_network_code", string2);
    }

    private void setMacAddress(String string2) {
        MobileAppTracker.super.putInParamTable("ma", string2);
    }

    private void setMatId(String string2) {
        MobileAppTracker.super.putInParamTable("mi", string2);
    }

    private void setOsVersion(String string2) {
        MobileAppTracker.super.putInParamTable("ov", string2);
    }

    private void setQueueSize(int n) {
        SharedPreferences.Editor editor = this.EventQueue.edit();
        if (n < 0) {
            n = 0;
        }
        editor.putInt("queuesize", n);
        editor.commit();
    }

    private void setScreenDensity(String string2) {
        MobileAppTracker.super.putInParamTable("screen_density", string2);
    }

    private void setScreenSize(String string2) {
        MobileAppTracker.super.putInParamTable("screen_layout_size", string2);
    }

    private void setUserAgent(String string2) {
        MobileAppTracker.super.putInParamTable("ua", string2);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private int track(String string2, String string3, String string4, String string5) {
        String string6;
        block26 : {
            int n = -1;
            void var17_6 = this;
            // MONITORENTER : var17_6
            boolean bl = this.initialized;
            if (!bl) {
                // MONITOREXIT : var17_6
                return n;
            }
            this.paramTable.remove((Object)"ei");
            this.paramTable.remove((Object)"en");
            this.setAction("conversion");
            if (MobileAppTracker.containsChar(string2)) {
                if (string2.equals((Object)"open")) {
                    this.setAction("open");
                } else if (string2.equals((Object)"close")) {
                    this.setAction("close");
                } else if (string2.equals((Object)"install")) {
                    this.setAction("install");
                } else if (string2.equals((Object)"update")) {
                    this.setAction("update");
                } else {
                    this.setEventName(string2);
                }
            } else {
                this.setEvent_id(string2);
            }
            if (string4 != null) {
                this.setRevenue(string4);
            }
            if (string5 != null) {
                this.setCurrencyCode(string5);
            }
            if (this.getReferrer() != null && this.getReferrer().length() == 0) {
                this.SP = this.context.getSharedPreferences(PREFS_REFERRER, 0);
                try {
                    String string7 = this.SP.getString("referrer", "");
                    if (string7.length() != 0) {
                        this.setReferrer(string7);
                    }
                }
                catch (ClassCastException classCastException) {
                    classCastException.printStackTrace();
                }
            }
            try {
                string6 = MobileAppTracker.super.buildLink();
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return n;
            }
            boolean bl2 = MobileAppTracker.super.isOnline();
            if (bl2) {
                this.pool.execute((Runnable)(MobileAppTracker)this.new getLink(string6, string3));
                return 1;
            }
            break block26;
            catch (Exception exception) {
                exception.printStackTrace();
            }
            return 1;
        }
        MobileAppTracker.super.addEventToQueue(string6, string3);
        if (!DEBUG) return 1;
        Log.d((String)TAG, (String)"Not online: track will be queued");
        return 1;
    }

    public String getAction() {
        return (String)this.paramTable.get((Object)"ac");
    }

    public String getAndroidId() {
        return (String)this.paramTable.get((Object)"ad");
    }

    public String getAppName() {
        return (String)this.paramTable.get((Object)"an");
    }

    public int getAppVersion() {
        return Integer.parseInt((String)((String)this.paramTable.get((Object)"av")));
    }

    public String getBrand_id() {
        return (String)this.paramTable.get((Object)"adv");
    }

    public String getCarrier() {
        return (String)this.paramTable.get((Object)"dc");
    }

    public String getConnectionType() {
        return (String)this.paramTable.get((Object)"connection_type");
    }

    public String getCountryCode() {
        return (String)this.paramTable.get((Object)"cc");
    }

    public String getCurrencyCode() {
        return (String)this.paramTable.get((Object)"c");
    }

    public String getDeviceBrand() {
        return (String)this.paramTable.get((Object)"db");
    }

    public String getDeviceId() {
        return (String)this.paramTable.get((Object)"d");
    }

    public String getDeviceModel() {
        return (String)this.paramTable.get((Object)"dm");
    }

    public String getEventName() {
        return (String)this.paramTable.get((Object)"en");
    }

    public String getEvent_id() {
        return (String)this.paramTable.get((Object)"ei");
    }

    public String getInstallDate() {
        return (String)this.paramTable.get((Object)"id");
    }

    public final String getKey() {
        return (String)this.paramTable.get((Object)"k");
    }

    public String getLanguage() {
        return (String)this.paramTable.get((Object)"l");
    }

    public String getMCC() {
        return (String)this.paramTable.get((Object)"mobile_country_code");
    }

    public String getMNC() {
        return (String)this.paramTable.get((Object)"mobile_network_code");
    }

    public String getMacAddress() {
        return (String)this.paramTable.get((Object)"ma");
    }

    public String getOsId() {
        return (String)this.paramTable.get((Object)"oi");
    }

    public String getOsVersion() {
        return (String)this.paramTable.get((Object)"ov");
    }

    public String getPackageName() {
        return (String)this.paramTable.get((Object)"pn");
    }

    public String getRefId() {
        return (String)this.paramTable.get((Object)"ar");
    }

    public String getReferrer() {
        return (String)this.paramTable.get((Object)"ir");
    }

    public String getRevenue() {
        return (String)this.paramTable.get((Object)"r");
    }

    public String getScreenDensity() {
        return (String)this.paramTable.get((Object)"screen_density");
    }

    public String getScreenSize() {
        return (String)this.paramTable.get((Object)"screen_layout_size");
    }

    public String getTRUSTeId() {
        return (String)this.paramTable.get((Object)"tpid");
    }

    public String getUserId() {
        return (String)this.paramTable.get((Object)"ui");
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void setAction(String string2) {
        MobileAppTracker.super.putInParamTable("ac", string2);
    }

    public void setBrand_id(String string2) {
        MobileAppTracker.super.putInParamTable("adv", string2);
    }

    public void setCurrencyCode(String string2) {
        MobileAppTracker.super.putInParamTable("c", string2);
    }

    public void setDebugMode(boolean bl) {
        DEBUG = bl;
    }

    public void setEventName(String string2) {
        MobileAppTracker.super.putInParamTable("en", string2);
    }

    public void setEvent_id(String string2) {
        MobileAppTracker.super.putInParamTable("ei", string2);
    }

    public void setHttpsEncryption(boolean bl) {
        this.httpsEncryption = bl;
    }

    public void setKey(String string2) {
        MobileAppTracker.super.putInParamTable("k", string2);
        this.URLEnc = new Encryption((String)this.paramTable.get((Object)"k"), IV);
    }

    public void setOsId(String string2) {
        MobileAppTracker.super.putInParamTable("oi", string2);
    }

    public void setPackageName(String string2) {
        MobileAppTracker.super.putInParamTable("pn", string2);
    }

    public void setRefId(String string2) {
        MobileAppTracker.super.putInParamTable("ar", string2);
    }

    public void setReferrer(String string2) {
        MobileAppTracker.super.putInParamTable("ir", string2);
    }

    public void setRevenue(String string2) {
        MobileAppTracker.super.putInParamTable("r", string2);
    }

    public void setTRUSTeId(String string2) {
        MobileAppTracker.super.putInParamTable("tpid", string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setTracking(String string2, String string3, String string4, String string5) {
        String string6 = "";
        StringBuilder stringBuilder = new StringBuilder("http://engine.mobileapptracking.com/serve?action=click");
        stringBuilder.append("&publisher_advertiser_id=").append(string2);
        stringBuilder.append("&package_name=").append(string3);
        if (string4 != null) {
            stringBuilder.append("&publisher_id=").append(string4);
        }
        if (string5 != null) {
            stringBuilder.append("&campaign_id=").append(string5);
        }
        stringBuilder.append("&format=json");
        HttpGet httpGet = new HttpGet(stringBuilder.toString());
        try {
            String string7;
            string6 = string7 = new JSONObject(new BufferedReader((Reader)new InputStreamReader(this.client.execute((HttpUriRequest)httpGet).getEntity().getContent(), "UTF-8"), 8192).readLine()).getString("tracking_id");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("publisher_package_name", this.getPackageName());
        contentValues.put("publisher_advertiser_id", string2);
        contentValues.put("publisher_id", string4);
        contentValues.put("campaign_id", string5);
        contentValues.put("tracking_id", string6);
        Uri uri = Uri.parse((String)("content://" + string3 + "/referrer_apps"));
        this.context.getContentResolver().insert(uri, contentValues);
    }

    public void setUserId(String string2) {
        MobileAppTracker.super.putInParamTable("ui", string2);
    }

    public int trackAction(String string2) {
        return MobileAppTracker.super.track(string2, null, null, null);
    }

    public int trackAction(String string2, String string3) {
        return MobileAppTracker.super.track(string2, null, string3, null);
    }

    public int trackAction(String string2, String string3, String string4) {
        return MobileAppTracker.super.track(string2, null, string3, string4);
    }

    public int trackAction(String string2, List list) {
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < list.size(); ++i2) {
            jSONArray.put((Object)new JSONObject((Map)list.get(i2)));
        }
        return MobileAppTracker.super.track(string2, jSONArray.toString(), null, null);
    }

    public int trackAction(String string2, Map map) {
        JSONObject jSONObject = new JSONObject(map);
        JSONArray jSONArray = new JSONArray();
        jSONArray.put((Object)jSONObject);
        return MobileAppTracker.super.track(string2, jSONArray.toString(), null, null);
    }

    public int trackInstall() {
        return this.trackInstall(this.context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int trackInstall(Context context) {
        void var18_2 = this;
        synchronized (var18_2) {
            this.SP = context.getSharedPreferences(PREFS_INSTALL, 0);
            this.install = this.SP.getString("install", "");
            if (!this.install.equals((Object)"")) {
                this.SP = context.getSharedPreferences(PREFS_VERSION, 0);
                String string2 = this.SP.getString("version", "");
                if (string2.length() != 0 && Integer.parseInt((String)string2) != this.getAppVersion()) {
                    if (DEBUG) {
                        Log.d((String)TAG, (String)"App version has changed since last trackInstall, sending update to server");
                    }
                    MobileAppTracker.super.track("update", null, null, null);
                    SharedPreferences.Editor editor = this.SP.edit();
                    editor.putString("version", Integer.toString((int)this.getAppVersion()));
                    editor.commit();
                    return 3;
                }
            } else {
                this.SP = context.getSharedPreferences(PREFS_INSTALL, 0);
                SharedPreferences.Editor editor = this.SP.edit();
                editor.putString("install", "installed");
                editor.commit();
                this.SP = context.getSharedPreferences(PREFS_VERSION, 0);
                SharedPreferences.Editor editor2 = this.SP.edit();
                editor2.putString("version", Integer.toString((int)this.getAppVersion()));
                editor2.commit();
                this.install = "installed";
                int n = MobileAppTracker.super.track("install", null, null, null);
                return n;
            }
            if (!DEBUG) return 2;
            Log.d((String)TAG, (String)"Install has been tracked before");
            return 2;
        }
    }

    public int trackUpdate() {
        this.SP = this.context.getSharedPreferences(PREFS_INSTALL, 0);
        SharedPreferences.Editor editor = this.SP.edit();
        editor.putString("install", "installed");
        editor.commit();
        this.SP = this.context.getSharedPreferences(PREFS_VERSION, 0);
        SharedPreferences.Editor editor2 = this.SP.edit();
        editor2.putString("version", Integer.toString((int)this.getAppVersion()));
        editor2.commit();
        return this.track("update", null, null, null);
    }

    public class getLink
    implements Runnable {
        private String json = null;
        private String link = null;
        public int status = -2;

        public getLink(String string2, String string3) {
            this.link = string2;
            this.json = string3;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void run() {
            if (DEBUG) {
                Log.d((String)MobileAppTracker.TAG, (String)"Sending event to server...");
            }
            try {
                HttpResponse httpResponse;
                if (this.json == null) {
                    HttpGet httpGet = new HttpGet(this.link);
                    httpResponse = MobileAppTracker.this.client.execute((HttpUriRequest)httpGet);
                } else {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("data", (Object)new JSONArray(this.json));
                    StringEntity stringEntity = new StringEntity(jSONObject.toString());
                    stringEntity.setContentType("application/json");
                    HttpPost httpPost = new HttpPost(this.link);
                    httpPost.setEntity((HttpEntity)stringEntity);
                    httpResponse = MobileAppTracker.this.client.execute((HttpUriRequest)httpPost);
                }
                this.status = httpResponse.getStatusLine().getStatusCode();
                if (this.status == 200) {
                    if (DEBUG) {
                        Log.d((String)MobileAppTracker.TAG, (String)"Event was sent");
                    }
                    BufferedReader bufferedReader = new BufferedReader((Reader)new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"), 8192);
                    String string2 = bufferedReader.readLine();
                    bufferedReader.close();
                    if (string2 == null) return;
                    String string3 = string2.split("\\s*\\\"status\\\"\\s*\\:\\s*")[1];
                    if (string3.startsWith("\"rejected\"")) {
                        String string4 = string3.split("\\s*\\\"status_code\\\"\\:\\s*")[1].substring(0, 2);
                        if (!DEBUG) return;
                        {
                            Log.d((String)MobileAppTracker.TAG, (String)("Event was rejected by server: status code " + string4));
                            return;
                        }
                    }
                } else {
                    MobileAppTracker.this.addEventToQueue(this.link, this.json);
                    if (!DEBUG) return;
                    {
                        Log.d((String)MobileAppTracker.TAG, (String)("Event failed with status " + this.status));
                        return;
                    }
                }
                if (!DEBUG) return;
                {
                    Log.d((String)MobileAppTracker.TAG, (String)"Event was accepted by server");
                    return;
                }
            }
            catch (Exception exception) {
                this.status = -3;
                return;
            }
        }
    }

}


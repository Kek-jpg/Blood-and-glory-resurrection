/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.net.wifi.WifiInfo
 *  android.net.wifi.WifiManager
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.telephony.TelephonyManager
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.WindowManager
 *  java.io.BufferedReader
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.SecurityException
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Throwable
 *  java.lang.reflect.Field
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 *  java.util.HashMap
 *  java.util.Map
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.amazon.device.ads;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.amazon.device.ads.AdRegistrationTasks;
import com.amazon.device.ads.Configuration;
import com.amazon.device.ads.DebugProperties;
import com.amazon.device.ads.Log;
import com.amazon.device.ads.Metrics;
import com.amazon.device.ads.ResourceLookup;
import com.amazon.device.ads.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

final class InternalAdRegistration {
    protected static final String ADID_PREF_NAME = "amzn-ad-id";
    protected static final String APPID_PREF_NAME = "amzn-ad-app-id";
    protected static final String AUD_PREF_NAME = "amzn-ad-auth-domain";
    public static final String LOG_TAG = "AmazonAdRegistration";
    protected static final long SIS_CHECKIN_INTERVAL = 86400000L;
    protected static final String SIS_LAST_CHECKIN_PREF_NAME = "amzn-ad-sis-last-checkin";
    protected static final String THIRD_PARTY_APP_DOMAIN = "3p";
    protected static final String THIRD_PARTY_APP_NAME = "app";
    private static InternalAdRegistration instance_ = null;
    private String amznDeviceId_ = null;
    private Context context_ = null;
    protected DeviceInfo deviceInfo_ = null;
    protected DeviceNativeData deviceNativeData_ = null;
    private long lastSISCheckin_ = 0L;
    private boolean registerNewAppID_ = false;
    private boolean registerNewAuthDomain_ = false;
    private String sdkVersionID_;
    private boolean testMode_;

    private InternalAdRegistration(Context context) {
        this.context_ = context;
        DebugProperties.readDebugProperties();
        Configuration.getInstance().setContext(context);
        this.amznDeviceId_ = this.getAmazonDeviceAdID();
        this.deviceNativeData_ = new DeviceNativeData(context);
        this.deviceInfo_ = new DeviceInfo(context);
        this.deviceInfo_.setAppId(this.getAmazonApplicationId());
        this.deviceInfo_.aud = InternalAdRegistration.super.getAuthenticationDomain();
        this.testMode_ = false;
        InternalAdRegistration.super.determineSDKVersion(context);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String convertStreamToString(InputStream inputStream) {
        bufferedReader = new BufferedReader((Reader)new InputStreamReader(inputStream));
        stringBuilder = new StringBuilder();
        try {
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string + "\n");
            }
            ** GOTO lbl14
        }
        catch (IOException iOException) {
            try {
                inputStream.close();
                return "";
            }
            catch (IOException iOException) {
                return "";
            }
lbl14: // 1 sources:
            try {
                inputStream.close();
            }
            catch (IOException iOException) {
                return "";
            }
            return stringBuilder.toString();
        }
        catch (Throwable throwable) {
            try {
                inputStream.close();
            }
            catch (IOException iOException) {
                return "";
            }
            throw throwable;
        }
    }

    private static String decodeIfNeeded(boolean bl, String string) {
        if (bl) {
            string = Utils.getURLDecodedString(string);
        }
        return string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void determineSDKVersion(Context context) {
        this.sdkVersionID_ = null;
        try {
            InputStream inputStream = ResourceLookup.getResourceFile(context, "ad_resources/raw/version.json");
            if (inputStream != null) {
                this.sdkVersionID_ = new JSONObject(InternalAdRegistration.convertStreamToString(inputStream)).getString("sdk_version");
            }
        }
        catch (JSONException jSONException) {}
        if (this.sdkVersionID_ == null) {
            this.sdkVersionID_ = "(DEV)";
        } else if (this.sdkVersionID_.endsWith("x.x")) {
            this.sdkVersionID_ = this.sdkVersionID_ + "(DEV)";
        }
        this.sdkVersionID_ = "amaznAdSDK-android-" + this.sdkVersionID_;
    }

    protected static final String generateSha1Hash(String string) {
        MessageDigest messageDigest = MessageDigest.getInstance((String)"SHA-1");
        messageDigest.update(string.getBytes());
        byte[] arrby = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        do {
            if (n2 >= arrby.length) break;
            stringBuffer.append(Integer.toHexString((int)(256 | 255 & arrby[n2])).substring(1));
            ++n2;
        } while (true);
        try {
            String string2 = stringBuffer.toString();
            return string2;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return "";
        }
    }

    private String getAuthenticationDomain() {
        return this.context_.getSharedPreferences("AmazonMobileAds", 0).getString(AUD_PREF_NAME, this.deviceInfo_.aud);
    }

    protected static final InternalAdRegistration getInstance() {
        Class<InternalAdRegistration> class_ = InternalAdRegistration.class;
        synchronized (InternalAdRegistration.class) {
            InternalAdRegistration internalAdRegistration = instance_;
            // ** MonitorExit[var2] (shouldn't be in output)
            return internalAdRegistration;
        }
    }

    protected static final InternalAdRegistration getInstance(Context context) {
        Class<InternalAdRegistration> class_ = InternalAdRegistration.class;
        synchronized (InternalAdRegistration.class) {
            if (instance_ == null) {
                instance_ = new InternalAdRegistration(context);
            }
            InternalAdRegistration internalAdRegistration = instance_;
            // ** MonitorExit[var3_1] (shouldn't be in output)
            return internalAdRegistration;
        }
    }

    private long getLastSISCheckin() {
        InternalAdRegistration internalAdRegistration = this;
        synchronized (internalAdRegistration) {
            if (this.lastSISCheckin_ == 0L) {
                this.lastSISCheckin_ = this.context_.getSharedPreferences("AmazonMobileAds", 0).getLong(SIS_LAST_CHECKIN_PREF_NAME, 0L);
            }
            long l2 = this.lastSISCheckin_;
            return l2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static final void getMacAddress(Context context, DeviceInfo deviceInfo) {
        try {
            String string = ((WifiManager)context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (string == null || string.length() == 0) {
                deviceInfo.sha1_mac = null;
                deviceInfo.bad_mac = true;
                return;
            }
            if (!Pattern.compile((String)"((([0-9a-fA-F]){1,2}[-:]){5}([0-9a-fA-F]){1,2})").matcher((CharSequence)string).find()) {
                deviceInfo.sha1_mac = null;
                deviceInfo.bad_mac = true;
                return;
            }
            deviceInfo.sha1_mac = Utils.getURLEncodedString(InternalAdRegistration.generateSha1Hash(string));
            return;
        }
        catch (SecurityException securityException) {
            Log.d(LOG_TAG, "Unable to get WIFI Manager: %s", new Object[]{securityException});
            deviceInfo.sha1_mac = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static final void getTelephonyID(Context context, DeviceInfo deviceInfo) {
        try {
            deviceInfo.sha1_tel = null;
            TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
            if (telephonyManager.getPhoneType() == 0) {
                return;
            }
            String string = telephonyManager.getDeviceId();
            if (string == null || string.equalsIgnoreCase("000000000000000")) {
                deviceInfo.bad_tel = true;
                return;
            }
            deviceInfo.sha1_tel = Utils.getURLEncodedString(InternalAdRegistration.generateSha1Hash(string));
            return;
        }
        catch (SecurityException securityException) {
            Log.d(LOG_TAG, "Unable to get Telephony Manager: %s", new Object[]{securityException});
            deviceInfo.sha1_tel = null;
            return;
        }
    }

    protected static final void getUdid(Context context, DeviceInfo deviceInfo) {
        String string = Settings.Secure.getString((ContentResolver)context.getContentResolver(), (String)"android_id");
        if (string == null || string.length() == 0 || string.equalsIgnoreCase("9774d56d682e549c")) {
            deviceInfo.sha1_udid = null;
            deviceInfo.bad_udid = true;
            return;
        }
        deviceInfo.sha1_udid = Utils.getURLEncodedString(InternalAdRegistration.generateSha1Hash(string));
    }

    private void setLastSISCheckin(long l2) {
        void var7_2 = this;
        synchronized (var7_2) {
            this.lastSISCheckin_ = l2;
            SharedPreferences.Editor editor = this.context_.getSharedPreferences("AmazonMobileAds", 0).edit();
            editor.putLong(SIS_LAST_CHECKIN_PREF_NAME, l2);
            editor.commit();
            return;
        }
    }

    protected String getAmazonApplicationId() {
        return this.context_.getSharedPreferences("AmazonMobileAds", 0).getString(APPID_PREF_NAME, null);
    }

    protected String getAmazonDeviceAdID() {
        return this.context_.getSharedPreferences("AmazonMobileAds", 0).getString(ADID_PREF_NAME, null);
    }

    protected String getAmazonDeviceId() {
        return this.amznDeviceId_;
    }

    protected String getAppId() {
        return this.deviceInfo_.getAppId();
    }

    protected Context getContext() {
        return this.context_;
    }

    protected DeviceInfo getDeviceInfo() {
        return this.deviceInfo_;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Map<String, String> getDeviceInfoParams(boolean bl) {
        HashMap hashMap = new HashMap();
        if (this.deviceInfo_ == null) {
            Log.w(LOG_TAG, "deviceInfo is null");
            return hashMap;
        } else {
            hashMap.put((Object)"dt", (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.dt));
            hashMap.put((Object)THIRD_PARTY_APP_NAME, (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.app));
            hashMap.put((Object)"aud", (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.aud));
            hashMap.put((Object)"appId", (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.getAppId()));
            if (this.deviceInfo_.sha1_mac != null) {
                hashMap.put((Object)"sha1_mac", (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.sha1_mac));
            }
            if (this.deviceInfo_.md5_mac != null) {
                hashMap.put((Object)"md5_mac", (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.md5_mac));
            }
            if (this.deviceInfo_.sha1_udid != null) {
                hashMap.put((Object)"sha1_udid", (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.sha1_udid));
            }
            if (this.deviceInfo_.md5_udid != null) {
                hashMap.put((Object)"md5_udid", (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.md5_udid));
            }
            if (this.deviceInfo_.sha1_tel != null) {
                hashMap.put((Object)"sha1_tel", (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.sha1_tel));
            }
            if (this.deviceInfo_.md5_tel != null) {
                hashMap.put((Object)"md5_tel", (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.md5_tel));
            }
            if (this.deviceInfo_.sha1_serial != null) {
                hashMap.put((Object)"sha1_serial", (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.sha1_serial));
            }
            if (this.deviceInfo_.md5_serial != null) {
                hashMap.put((Object)"md5_serial", (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.md5_serial));
            }
            if (this.deviceInfo_.referrer == null) return hashMap;
            {
                hashMap.put((Object)"referrer", (Object)InternalAdRegistration.decodeIfNeeded(bl, this.deviceInfo_.referrer));
                return hashMap;
            }
        }
    }

    protected DeviceNativeData getDeviceNativeData() {
        return this.deviceNativeData_;
    }

    protected Map<String, String> getDeviceNativeDataParams() {
        HashMap hashMap = new HashMap();
        if (this.deviceNativeData_.os != null) {
            hashMap.put((Object)"os", (Object)this.deviceNativeData_.os);
        }
        if (this.deviceNativeData_.model != null) {
            hashMap.put((Object)"model", (Object)this.deviceNativeData_.model);
        }
        if (this.deviceNativeData_.make != null) {
            hashMap.put((Object)"make", (Object)this.deviceNativeData_.make);
        }
        if (this.deviceNativeData_.osVersion != null) {
            hashMap.put((Object)"osVersion", (Object)this.deviceNativeData_.osVersion);
        }
        if (this.deviceNativeData_.screenSize != null) {
            hashMap.put((Object)"screenSize", (Object)this.deviceNativeData_.screenSize);
        }
        if (this.deviceNativeData_.sf != null) {
            hashMap.put((Object)"sf", (Object)this.deviceNativeData_.sf);
        }
        return hashMap;
    }

    protected String getSDKVersionID() {
        return this.sdkVersionID_;
    }

    protected boolean getTestMode() {
        return this.testMode_;
    }

    protected boolean isRegistered() {
        return this.amznDeviceId_ != null && this.amznDeviceId_.length() > 0;
    }

    protected void ping() {
        AdRegistrationTasks.A9Request a9Request = new AdRegistrationTasks.A9Request(this.context_, "Ping", this.deviceInfo_, this.deviceNativeData_, this.amznDeviceId_);
        new AdRegistrationTasks.PingTask().execute((Object[])new AdRegistrationTasks.A9Request[]{a9Request});
    }

    protected void register() {
        AdRegistrationTasks.A9Request a9Request = new AdRegistrationTasks.A9Request(this.context_, "RegisterDevice", this.deviceInfo_, this.deviceNativeData_, this.amznDeviceId_);
        new AdRegistrationTasks.RegisterDeviceTask().execute((Object[])new AdRegistrationTasks.A9Request[]{a9Request});
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void registerIfNeeded() {
        InternalAdRegistration internalAdRegistration = this;
        synchronized (internalAdRegistration) {
            long l2 = System.currentTimeMillis();
            if (this.amznDeviceId_ != null && !this.registerNewAppID_) {
                if (l2 - this.getLastSISCheckin() > 86400000L || this.registerNewAuthDomain_) {
                    this.setLastSISCheckin(l2);
                    this.updateDeviceInfo();
                    this.ping();
                }
            } else if (this.registerNewAppID_) {
                this.setLastSISCheckin(l2);
                this.register();
            }
            this.registerNewAuthDomain_ = false;
            this.registerNewAppID_ = false;
            return;
        }
    }

    protected void setAmazonDeviceId(String string) {
        if (string != this.amznDeviceId_) {
            if (this.amznDeviceId_ != null && this.amznDeviceId_ != "") {
                Metrics.getInstance().incrementMetric(Metrics.MetricType.SIS_COUNTER_IDENTIFIED_DEVICE_CHANGED);
            }
            SharedPreferences.Editor editor = this.context_.getSharedPreferences("AmazonMobileAds", 0).edit();
            editor.putString(ADID_PREF_NAME, string);
            editor.commit();
            this.amznDeviceId_ = string;
        }
    }

    protected void setApplicationId(String string) {
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException("ApplicationId must not be null or empty.");
        }
        if (string.equals((Object)this.getAmazonApplicationId())) {
            if (this.deviceInfo_.getAppId() == null) {
                this.deviceInfo_.setAppId(Utils.getURLEncodedString(string));
                this.registerNewAppID_ = true;
            }
            return;
        }
        this.deviceInfo_.setAppId(Utils.getURLEncodedString(string));
        this.registerNewAppID_ = true;
        SharedPreferences.Editor editor = this.context_.getSharedPreferences("AmazonMobileAds", 0).edit();
        editor.putString(APPID_PREF_NAME, string);
        editor.remove(ADID_PREF_NAME);
        editor.commit();
    }

    protected void setApplicationName(String string) {
        this.deviceInfo_.app = Utils.getURLEncodedString(string);
    }

    protected void setAuthenticationDomain(String string) {
        if (this.deviceInfo_.aud == null || !this.deviceInfo_.aud.equals((Object)string)) {
            this.deviceInfo_.aud = Utils.getURLEncodedString(string);
            this.registerNewAuthDomain_ = true;
            SharedPreferences.Editor editor = this.context_.getSharedPreferences("AmazonMobileAds", 0).edit();
            editor.putString(AUD_PREF_NAME, this.deviceInfo_.aud);
            editor.commit();
        }
    }

    protected void setLoggingEnabled(boolean bl) {
        Log.enableLogging(bl);
    }

    void setTestMode(boolean bl) {
        this.testMode_ = bl;
    }

    protected void updateDeviceInfo() {
        AdRegistrationTasks.A9Request a9Request = new AdRegistrationTasks.A9Request(this.context_, "UpdateDeviceInfo", this.deviceInfo_, this.deviceNativeData_, this.amznDeviceId_);
        new AdRegistrationTasks.UpdateDeviceInfoTask().execute((Object[])new AdRegistrationTasks.A9Request[]{a9Request});
    }

    protected static class DeviceInfo {
        public String app = "app";
        private String appId_ = null;
        public String aud = "3p";
        public boolean bad_mac = false;
        public boolean bad_serial = false;
        public boolean bad_tel = false;
        public boolean bad_udid = false;
        public String dt = "android";
        public String md5_mac = null;
        public String md5_serial = null;
        public String md5_tel = null;
        public String md5_udid = null;
        public String referrer = null;
        public String sha1_mac = null;
        public String sha1_serial = null;
        public String sha1_tel = null;
        public String sha1_udid = null;
        public String ua = null;

        public DeviceInfo(Context context) {
            InternalAdRegistration.getMacAddress(context, (DeviceInfo)this);
            InternalAdRegistration.getUdid(context, (DeviceInfo)this);
            InternalAdRegistration.getTelephonyID(context, (DeviceInfo)this);
            this.setSerial();
        }

        public String getAppId() {
            if (!DebugProperties.isDebugModeOn()) {
                return this.appId_;
            }
            return DebugProperties.getDebugProperties().getProperty("debug.appid", this.appId_);
        }

        public void setAppId(String string) {
            this.appId_ = string;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void setSerial() {
            try {
                String string = (String)Build.class.getDeclaredField("SERIAL").get(Build.class);
                if (string != null && string.length() != 0 && !string.equalsIgnoreCase("unknown")) {
                    this.sha1_serial = Utils.getURLEncodedString(InternalAdRegistration.generateSha1Hash(string));
                    return;
                }
                this.bad_serial = true;
                return;
            }
            catch (Exception exception) {
                return;
            }
        }
    }

    protected static class DeviceNativeData {
        public JSONObject json;
        public final String make = Build.MANUFACTURER;
        public final String model = Build.MODEL;
        public String orientation = "";
        public final String os = "Android";
        public final String osVersion = Build.VERSION.RELEASE;
        public final String screenSize;
        public final String sf;
        public String urlEncodedJson;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public DeviceNativeData(Context context) {
            WindowManager windowManager = (WindowManager)context.getSystemService("window");
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            this.screenSize = String.valueOf((int)displayMetrics.widthPixels) + "x" + String.valueOf((int)displayMetrics.heightPixels);
            this.sf = String.valueOf((float)displayMetrics.scaledDensity);
            this.json = new JSONObject();
            try {
                this.json.put("os", (Object)this.os);
                this.json.put("model", (Object)this.model);
                this.json.put("make", (Object)this.make);
                this.json.put("osVersion", (Object)this.osVersion);
                this.json.put("screenSize", (Object)this.screenSize);
                this.json.put("sf", (Object)this.sf);
            }
            catch (JSONException jSONException) {}
            this.urlEncodedJson = Utils.getURLEncodedString(this.json.toString());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public String getJsonEncodedWithOrientation() {
            try {
                this.json.put("orientation", (Object)this.orientation);
            }
            catch (JSONException jSONException) {}
            this.urlEncodedJson = Utils.getURLEncodedString(this.json.toString());
            return this.urlEncodedJson;
        }
    }

}


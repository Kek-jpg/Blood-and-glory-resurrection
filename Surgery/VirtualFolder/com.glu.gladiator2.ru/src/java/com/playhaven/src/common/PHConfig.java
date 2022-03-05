/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.provider.Settings
 *  android.provider.Settings$System
 *  android.util.DisplayMetrics
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 */
package com.playhaven.src.common;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import com.playhaven.src.common.PHConnectionManager;
import com.playhaven.src.common.PHCrashReport;
import com.playhaven.src.common.PHSDKVersion;

public class PHConfig {
    public static final String JSON_CONFIG = "{\n   \"prod\":{\n      \"api\":\"http://api2.playhaven.com\",\n      \"precache\":true,\n      \"protocol\":4,\n      \"urgency_level\":\"low\"\n   },\n   \"dev\":null\n}";
    public static String api;
    public static String app_package;
    public static String app_version;
    public static Rect available_screen_size;
    public static boolean cache;
    public static ConnectionType connection;
    public static String device_id;
    public static String device_model;
    public static int device_size;
    public static String environment;
    public static String os_name;
    public static int os_version;
    public static String password;
    public static boolean precache;
    public static int precache_size;
    public static int protocol;
    public static boolean runningTests;
    public static float screen_density;
    public static int screen_density_type;
    public static Rect screen_size;
    public static int screen_size_type;
    public static String sdk_version;
    public static String secret;
    public static String token;
    public static String urgency_level;
    public static String username;

    static {
        sdk_version = PHSDKVersion.getCurrentVersion();
        token = "";
        secret = "";
        os_name = "";
        device_id = "";
        device_model = "";
        api = "";
        username = null;
        password = null;
        protocol = -1;
        screen_size = new Rect(0, 0, 0, 0);
        available_screen_size = new Rect(0, 0, 0, 0);
        urgency_level = "";
        app_package = "";
        app_version = "";
        precache_size = 8388608;
        precache = true;
        runningTests = false;
        environment = "prod";
        PHConfig.loadConfig(JSON_CONFIG, environment);
    }

    public static void cacheDeviceInfo(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Must supply a valid Context when extracting device info");
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            app_package = packageInfo.packageName;
            app_version = packageInfo.versionName;
            os_name = Build.VERSION.RELEASE;
            os_version = Build.VERSION.SDK_INT;
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            screen_size = new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
            screen_density_type = displayMetrics.densityDpi;
            screen_density = displayMetrics.density;
            device_size = 15 & context.getResources().getConfiguration().screenLayout;
            device_id = Settings.System.getString((ContentResolver)context.getContentResolver(), (String)"android_id");
            device_model = Build.MODEL;
            sdk_version = PHSDKVersion.getCurrentVersion();
            connection = PHConnectionManager.getConnectionType(context);
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, PHCrashReport.Urgency.low);
            return;
        }
    }

    /*
     * Exception decompiling
     */
    public static void loadConfig(String var0_1, String var1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[CATCHBLOCK]
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
        // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
        // org.benf.cfr.reader.b.a(Driver.java:128)
        // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
        // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
        // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
        // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1112)
        // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:587)
        // java.lang.Thread.run(Thread.java:818)
        throw new IllegalStateException("Decompilation failed");
    }

    public static final class ConnectionType
    extends Enum<ConnectionType> {
        private static final /* synthetic */ ConnectionType[] $VALUES;
        public static final /* enum */ ConnectionType MOBILE;
        public static final /* enum */ ConnectionType NO_NETWORK;
        public static final /* enum */ ConnectionType NO_PERMISSION;
        public static final /* enum */ ConnectionType WIFI;

        static {
            NO_NETWORK = new ConnectionType();
            MOBILE = new ConnectionType();
            WIFI = new ConnectionType();
            NO_PERMISSION = new ConnectionType();
            ConnectionType[] arrconnectionType = new ConnectionType[]{NO_NETWORK, MOBILE, WIFI, NO_PERMISSION};
            $VALUES = arrconnectionType;
        }

        public static ConnectionType valueOf(String string2) {
            return (ConnectionType)Enum.valueOf(ConnectionType.class, (String)string2);
        }

        public static ConnectionType[] values() {
            return (ConnectionType[])$VALUES.clone();
        }
    }

}


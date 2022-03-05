/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.util.Log
 *  java.io.Closeable
 *  java.io.File
 *  java.io.FileInputStream
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 *  java.lang.Thread
 *  java.lang.Thread$UncaughtExceptionHandler
 *  java.lang.Throwable
 *  java.net.URLDecoder
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import com.flurry.android.FlurryAgent;
import com.flurry.android.FlurryAgent$FlurryDefaultExceptionHandler;
import com.flurry.android.av;
import com.flurry.android.bc;
import com.flurry.android.y;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InstallReceiver
extends BroadcastReceiver {
    private final Handler a;
    private File b = null;

    public InstallReceiver() {
        HandlerThread handlerThread = new HandlerThread("InstallReceiver");
        handlerThread.start();
        this.a = new Handler(handlerThread.getLooper());
    }

    static /* synthetic */ File a(InstallReceiver installReceiver) {
        return installReceiver.b;
    }

    static /* synthetic */ String a(File file) {
        return InstallReceiver.b(file);
    }

    /*
     * Enabled aggressive block sorting
     */
    static Map<String, List<String>> a(String string) {
        HashMap hashMap = new HashMap();
        String[] arrstring = string.split("&");
        int n2 = arrstring.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            String[] arrstring2 = arrstring[i2].split("=");
            if (arrstring2.length != 2) {
                bc.a("InstallReceiver", "Invalid referrer Element: " + arrstring[i2] + " in referrer tag " + string);
                continue;
            }
            String string2 = URLDecoder.decode((String)arrstring2[0]);
            String string3 = URLDecoder.decode((String)arrstring2[1]);
            if (hashMap.get((Object)string2) == null) {
                hashMap.put((Object)string2, (Object)new ArrayList());
            }
            ((List)hashMap.get((Object)string2)).add((Object)string3);
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (hashMap.get((Object)"utm_source") == null) {
            stringBuilder.append("Campaign Source is missing.\n");
        }
        if (hashMap.get((Object)"utm_medium") == null) {
            stringBuilder.append("Campaign Medium is missing.\n");
        }
        if (hashMap.get((Object)"utm_campaign") == null) {
            stringBuilder.append("Campaign Name is missing.\n");
        }
        if (stringBuilder.length() > 0) {
            Log.w((String)"Detected missing referrer keys", (String)stringBuilder.toString());
        }
        return hashMap;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private static String b(File var0) {
        var1_1 = new FileInputStream(var0);
        try {
            var2_2 = new StringBuffer();
            try {
                var7_3 = new byte[1024];
                while ((var8_4 = var1_1.read(var7_3)) > 0) {
                    var2_2.append(new String(var7_3, 0, var8_4));
                }
            }
            catch (Throwable var4_5) lbl-1000: // 3 sources:
            {
                do {
                    bc.b("InstallReceiver", "Error when loading persistent file", (Throwable)var4_6);
                    y.a((Closeable)var1_1);
lbl16: // 2 sources:
                    do {
                        var6_9 = null;
                        if (var2_2 != null) {
                            var6_9 = var2_2.toString();
                        }
                        return var6_9;
                        break;
                    } while (true);
                    break;
                } while (true);
            }
        }
        catch (Throwable var3_12) {
            ** continue;
        }
        y.a((Closeable)var1_1);
        ** while (true)
        catch (Throwable var10_10) {
            var1_1 = null;
            var3_11 = var10_10;
lbl26: // 2 sources:
            do {
                y.a((Closeable)var1_1);
                throw var3_11;
                break;
            } while (true);
        }
        catch (Throwable var4_7) {
            var2_2 = null;
            var1_1 = null;
            ** GOTO lbl-1000
        }
        {
            catch (Throwable var4_8) {
                var2_2 = null;
                ** continue;
            }
        }
    }

    private void b(String string) {
        void var5_2 = this;
        synchronized (var5_2) {
            av av2 = new av((InstallReceiver)this, string);
            this.a.post((Runnable)av2);
            return;
        }
    }

    public final void onReceive(Context context, Intent intent) {
        bc.c("InstallReceiver", "Received an Install nofication of " + intent.getAction());
        this.b = context.getFileStreamPath(".flurryinstallreceiver.");
        bc.c("InstallReceiver", "fInstallReceiverFile is " + (Object)this.b);
        if (FlurryAgent.isCaptureUncaughtExceptions()) {
            Thread.setDefaultUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new FlurryAgent$FlurryDefaultExceptionHandler());
        }
        String string = intent.getExtras().getString("referrer");
        bc.c("InstallReceiver", "Received an Install referrer of " + string);
        if (string == null || !"com.android.vending.INSTALL_REFERRER".equals((Object)intent.getAction())) {
            bc.c("InstallReceiver", "referrer is null");
            return;
        }
        if (!string.contains((CharSequence)"=")) {
            bc.c("InstallReceiver", "referrer is before decoding: " + string);
            string = URLDecoder.decode((String)string);
            bc.c("InstallReceiver", "referrer is: " + string);
        }
        InstallReceiver.super.b(string);
    }
}


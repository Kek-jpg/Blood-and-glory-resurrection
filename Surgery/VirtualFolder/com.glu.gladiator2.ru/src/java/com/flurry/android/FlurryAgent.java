/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.location.Criteria
 *  android.location.Location
 *  android.location.LocationListener
 *  android.location.LocationManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.SystemClock
 *  android.provider.Settings
 *  android.provider.Settings$System
 *  android.telephony.TelephonyManager
 *  android.util.Log
 *  android.view.ViewGroup
 *  java.io.ByteArrayOutputStream
 *  java.io.Closeable
 *  java.io.DataInputStream
 *  java.io.DataOutputStream
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.StackTraceElement
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Thread
 *  java.lang.Thread$UncaughtExceptionHandler
 *  java.lang.Throwable
 *  java.net.URLDecoder
 *  java.nio.ByteBuffer
 *  java.security.DigestOutputStream
 *  java.security.MessageDigest
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.Date
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.TimeZone
 *  java.util.WeakHashMap
 *  java.util.concurrent.atomic.AtomicInteger
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.entity.ByteArrayEntity
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 */
package com.flurry.android;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ViewGroup;
import com.flurry.android.CrcMessageDigest;
import com.flurry.android.FlurryAdSize;
import com.flurry.android.FlurryAgent$FlurryDefaultExceptionHandler;
import com.flurry.android.ICustomAdNetworkHandler;
import com.flurry.android.IListener;
import com.flurry.android.a;
import com.flurry.android.ad;
import com.flurry.android.ak;
import com.flurry.android.au;
import com.flurry.android.ax;
import com.flurry.android.bc;
import com.flurry.android.be;
import com.flurry.android.e;
import com.flurry.android.k;
import com.flurry.android.n;
import com.flurry.android.y;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public final class FlurryAgent
implements LocationListener {
    private static final String[] a = new String[]{"9774d56d682e549c", "dead00beef"};
    private static volatile String b = null;
    private static volatile String c = "http://data.flurry.com/aap.do";
    private static volatile String d = "https://data.flurry.com/aap.do";
    private static final FlurryAgent e = new FlurryAgent();
    private static long f = 10000L;
    private static boolean g = true;
    private static boolean h = false;
    private static boolean i = false;
    private static boolean j = true;
    private static Criteria k = null;
    private static boolean l = false;
    private static AtomicInteger n = new AtomicInteger(0);
    private static AtomicInteger o = new AtomicInteger(0);
    private List<byte[]> A;
    private LocationManager B;
    private String C;
    private Map<Integer, ByteBuffer> D = new HashMap();
    private boolean E;
    private long F;
    private List<byte[]> G = new ArrayList();
    private long H;
    private long I;
    private long J;
    private String K = "";
    private String L = "";
    private byte M = (byte)-1;
    private String N = "";
    private byte O = (byte)-1;
    private Long P;
    private int Q;
    private Location R;
    private Map<String, k> S = new HashMap();
    private List<ak> T = new ArrayList();
    private boolean U;
    private int V;
    private List<au> W = new ArrayList();
    private int X;
    private Map<String, List<String>> Y;
    private be Z = new be();
    private ax aa = new ax();
    private ad ab = new ad();
    private final Handler m;
    private File p;
    private File q = null;
    private File r = null;
    private volatile boolean s = false;
    private volatile boolean t = false;
    private long u;
    private Map<Context, Context> v = new WeakHashMap();
    private String w;
    private String x;
    private String y;
    private boolean z = true;

    private FlurryAgent() {
        HandlerThread handlerThread = new HandlerThread("FlurryAgent");
        handlerThread.start();
        this.m = new Handler(handlerThread.getLooper());
    }

    private static double a(double d2) {
        return (double)Math.round((double)(d2 * 1000.0)) / 1000.0;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private static String a(File var0) {
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
                    bc.b("FlurryAgent", "Error when loading persistent file", (Throwable)var4_6);
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

    private void a(Context context) {
        if (!l) {
            if (!this.Z.g()) {
                bc.a("FlurryAgent", "Initializing Flurry Ads");
                n n2 = new n();
                n2.a = this.w;
                n2.b = this.aa;
                n2.c = this.ab;
                n2.d = this.K;
                n2.e = this.L;
                this.Z.a(context, n2);
                this.Z.l();
                bc.a("FlurryAgent", "Flurry Ads initialized");
            }
            this.Z.a(context, this.H, this.I);
            l = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(Context context, String string) {
        void var15_3 = this;
        synchronized (var15_3) {
            if (this.w != null && !this.w.equals((Object)string)) {
                bc.b("FlurryAgent", "onStartSession called with different api keys: " + this.w + " and " + string);
            }
            if ((Context)this.v.put((Object)context, (Object)context) != null) {
                bc.d("FlurryAgent", "onStartSession called with duplicate context, use a specific Activity or Service as context instead of using a global context");
            }
            if (!this.s) {
                bc.a("FlurryAgent", "Initializing Flurry session");
                n.set(0);
                o.set(0);
                this.w = string;
                this.q = context.getFileStreamPath(".flurryagent." + Integer.toString((int)this.w.hashCode(), (int)16));
                this.p = context.getFileStreamPath(".flurryb.");
                this.r = context.getFileStreamPath(".flurryinstallreceiver.");
                if (j) {
                    Thread.setDefaultUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new FlurryAgent$FlurryDefaultExceptionHandler());
                }
                Context context2 = context.getApplicationContext();
                if (this.y == null) {
                    this.y = FlurryAgent.d(context2);
                }
                String string2 = context2.getPackageName();
                if (this.x != null && !this.x.equals((Object)string2)) {
                    bc.b("FlurryAgent", "onStartSession called from different application packages: " + this.x + " and " + string2);
                }
                this.x = string2;
                long l2 = SystemClock.elapsedRealtime();
                if (l2 - this.u > f) {
                    bc.a("FlurryAgent", "New session");
                    this.H = System.currentTimeMillis();
                    this.I = l2;
                    this.J = -1L;
                    this.N = "";
                    this.Q = 0;
                    this.R = null;
                    this.L = TimeZone.getDefault().getID();
                    this.K = Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();
                    this.S = new HashMap();
                    this.T = new ArrayList();
                    this.U = true;
                    this.W = new ArrayList();
                    this.V = 0;
                    this.X = 0;
                    if (l) {
                        this.Z.a(context, this.H, this.I);
                    }
                    FlurryAgent.super.a(new e((FlurryAgent)this, context2, this.z));
                } else {
                    bc.a("FlurryAgent", "Continuing previous session");
                    if (!this.G.isEmpty()) {
                        this.G.remove(-1 + this.G.size());
                    }
                    if (l) {
                        this.Z.a(context);
                    }
                }
                this.s = true;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(Context context, boolean bl) {
        void var13_3 = this;
        synchronized (var13_3) {
            if (context != null && (Context)this.v.remove((Object)context) == null) {
                bc.d("FlurryAgent", "onEndSession called without context from corresponding onStartSession");
            }
            if (this.s && this.v.isEmpty()) {
                Context context2;
                String string;
                long l2;
                bc.a("FlurryAgent", "Ending session");
                FlurryAgent.super.n();
                Context context3 = context == null ? null : (context2 = context.getApplicationContext());
                if (context != null && !this.x.equals((Object)(string = context3.getPackageName()))) {
                    bc.b("FlurryAgent", "onEndSession called from different application package, expected: " + this.x + " actual: " + string);
                }
                this.u = l2 = SystemClock.elapsedRealtime();
                this.J = l2 - this.I;
                if (FlurryAgent.super.o() == null) {
                    bc.b("FlurryAgent", "Not creating report because of bad Android ID or generated ID is null");
                }
                FlurryAgent.super.a(new a((FlurryAgent)this, bl, context3));
                if (l) {
                    this.Z.a();
                }
                this.s = false;
            }
            return;
        }
    }

    static /* synthetic */ void a(FlurryAgent flurryAgent, Context context) {
        flurryAgent.b(context);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    static /* synthetic */ void a(FlurryAgent var0_1, Context var1, boolean var2_2) {
        var3_3 = null;
        if (!var2_2) ** GOTO lbl5
        try {
            var3_3 = var0_1.e(var1);
lbl5: // 2 sources:
            var9_4 = var0_1;
            // MONITORENTER : var9_4
        }
        catch (Throwable var4_6) {
            bc.b("FlurryAgent", "", var4_6);
            return;
        }
        var0_1.R = var3_3;
        // MONITOREXIT : var9_4
        var7_5 = FlurryAgent.f(var1);
        if (var7_5 != null) {
            var0_1.D.put((Object)be.b, (Object)ByteBuffer.wrap((byte[])var7_5));
        }
        var0_1.k();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(DataInputStream dataInputStream) {
        int n2 = 0;
        void var18_3 = this;
        synchronized (var18_3) {
            int n3 = dataInputStream.readUnsignedShort();
            if (n3 > 2) {
                bc.b("FlurryAgent", "Unknown agent file version: " + n3);
                throw new IOException("Unknown agent file version: " + n3);
            }
            if (n3 >= 2) {
                String string = dataInputStream.readUTF();
                bc.a("FlurryAgent", "Loading API key: " + FlurryAgent.c(this.w));
                if (string.equals((Object)this.w)) {
                    int n4;
                    String string2 = dataInputStream.readUTF();
                    if (FlurryAgent.super.o() == null) {
                        bc.a("FlurryAgent", "Loading phoneId: " + string2);
                    }
                    FlurryAgent.super.d(string2);
                    this.E = dataInputStream.readBoolean();
                    this.F = dataInputStream.readLong();
                    bc.a("FlurryAgent", "Loading session reports");
                    while ((n4 = dataInputStream.readUnsignedShort()) != 0) {
                        byte[] arrby = new byte[n4];
                        dataInputStream.readFully(arrby);
                        this.G.add(0, (Object)arrby);
                        StringBuilder stringBuilder = new StringBuilder().append("Session report added: ");
                        bc.a("FlurryAgent", stringBuilder.append(++n2).toString());
                    }
                    bc.a("FlurryAgent", "Persistent file loaded");
                    this.t = true;
                } else {
                    bc.a("FlurryAgent", "Api keys do not match, old: " + FlurryAgent.c(string) + ", new: " + FlurryAgent.c(this.w));
                }
            } else {
                bc.d("FlurryAgent", "Deleting old file version: " + n3);
            }
            return;
        }
    }

    private void a(Runnable runnable) {
        this.m.post(runnable);
    }

    private void a(String string) {
        void var5_2 = this;
        synchronized (var5_2) {
            for (ak ak2 : this.T) {
                if (!ak2.a(string)) continue;
                ak2.a(SystemClock.elapsedRealtime() - this.I);
                break;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void a(String string, String string2, String string3) {
        void var17_4 = this;
        // MONITORENTER : var17_4
        if (this.W == null) {
            bc.b("FlurryAgent", "onError called before onStartSession.  Error: " + string);
            return;
        }
        boolean bl = string != null && "uncaught".equals((Object)string);
        this.Q = 1 + this.Q;
        if (this.W.size() < 50) {
            Long l2 = System.currentTimeMillis();
            au au2 = new au(o.incrementAndGet(), l2, string, string2, string3);
            this.W.add((Object)au2);
            bc.a("FlurryAgent", "Error logged: " + au2.b());
            return;
        }
        if (!bl) {
            bc.a("FlurryAgent", "Max errors logged. No more errors logged.");
            return;
        }
        int n2 = 0;
        do {
            if (n2 >= this.W.size()) {
                // MONITOREXIT : var17_4
                return;
            }
            au au3 = (au)this.W.get(n2);
            if (au3.b() != null && !"uncaught".equals((Object)au3.b())) {
                Long l3 = System.currentTimeMillis();
                au au4 = new au(o.incrementAndGet(), l3, string, string2, string3);
                this.W.set(n2, (Object)au4);
                return;
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(String string, Map<String, String> map, boolean bl) {
        void var21_4 = this;
        synchronized (var21_4) {
            if (this.T == null) {
                bc.b("FlurryAgent", "onEvent called before onStartSession.  Event: " + string);
            } else {
                long l2 = SystemClock.elapsedRealtime() - this.I;
                String string2 = y.a(string);
                if (string2.length() != 0) {
                    k k2 = (k)this.S.get((Object)string2);
                    if (k2 == null) {
                        if (this.S.size() < 100) {
                            k k3 = new k();
                            k3.a = 1;
                            this.S.put((Object)string2, (Object)k3);
                            bc.a("FlurryAgent", "Event count incremented: " + string2);
                        } else if (bc.a("FlurryAgent")) {
                            bc.a("FlurryAgent", "Too many different events. Event not counted: " + string2);
                        }
                    } else {
                        k2.a = 1 + k2.a;
                        bc.a("FlurryAgent", "Event count incremented: " + string2);
                    }
                    if (g && this.T.size() < 200 && this.V < 16000) {
                        Map<String, String> map2 = map == null ? Collections.emptyMap() : map;
                        if (map2.size() > 10) {
                            if (bc.a("FlurryAgent")) {
                                bc.a("FlurryAgent", "MaxEventParams exceeded: " + map2.size());
                            }
                        } else {
                            ak ak2 = new ak(n.incrementAndGet(), string2, map2, l2, bl);
                            if (ak2.a().length + this.V <= 16000) {
                                this.T.add((Object)ak2);
                                this.V += ak2.a().length;
                                bc.a("FlurryAgent", "Logged event: " + string2);
                            } else {
                                this.V = 16000;
                                this.U = false;
                                bc.a("FlurryAgent", "Event Log size exceeded. No more event details logged.");
                            }
                        }
                    } else {
                        this.U = false;
                    }
                }
            }
            return;
        }
    }

    static boolean a() {
        return FlurryAgent.e.t && FlurryAgent.e.s;
    }

    static /* synthetic */ boolean a(FlurryAgent flurryAgent) {
        return flurryAgent.t;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean a(byte[] arrby) {
        String string;
        FlurryAgent flurryAgent;
        boolean bl;
        String string2 = FlurryAgent.j();
        if (string2 == null) {
            return false;
        }
        try {
            boolean bl2;
            bl = bl2 = FlurryAgent.super.a(arrby, string2);
        }
        catch (Exception exception) {
            bc.a("FlurryAgent", "Sending report exception: " + exception.getMessage());
            bl = false;
        }
        if (bl) return bl;
        if (b != null) return bl;
        if (!h) return bl;
        if (i) return bl;
        FlurryAgent flurryAgent2 = flurryAgent = e;
        synchronized (flurryAgent2) {
            i = true;
            string = FlurryAgent.j();
            if (string == null) {
                return false;
            }
        }
        try {
            return FlurryAgent.super.a(arrby, string);
        }
        catch (Exception exception) {
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean a(byte[] arrby, String string) {
        boolean bl = true;
        if ("local".equals((Object)string)) {
            return bl;
        }
        bc.a("FlurryAgent", "Sending report to: " + string);
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(arrby);
        byteArrayEntity.setContentType("application/octet-stream");
        HttpPost httpPost = new HttpPost(string);
        httpPost.setEntity((HttpEntity)byteArrayEntity);
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, (int)10000);
        HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, (int)15000);
        httpPost.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        int n2 = this.aa.a((HttpParams)basicHttpParams).execute((HttpUriRequest)httpPost).getStatusLine().getStatusCode();
        void var14_8 = this;
        synchronized (var14_8) {
            if (n2 == 200) {
                bc.a("FlurryAgent", "Report successful");
                this.E = true;
                this.G.removeAll(this.A);
            } else {
                bc.a("FlurryAgent", "Report failed. HTTP response: " + n2);
                bl = false;
            }
            this.A = null;
            return bl;
        }
    }

    static be b() {
        return FlurryAgent.e.Z;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Map<String, List<String>> b(String string) {
        HashMap hashMap = new HashMap();
        String[] arrstring = string.split("&");
        int n2 = arrstring.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            String[] arrstring2 = arrstring[i2].split("=");
            if (arrstring2.length != 2) {
                bc.a("FlurryAgent", "Invalid referrer Element: " + arrstring[i2] + " in referrer tag " + string);
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void b(Context var1) {
        block23 : {
            block24 : {
                block22 : {
                    var19_2 = this;
                    // MONITORENTER : var19_2
                    var3_3 = FlurryAgent.super.c(var1);
                    if (!this.q.exists()) ** GOTO lbl22
                    bc.c("FlurryAgent", "loading persistent data: " + this.q.getAbsolutePath());
                    var10_4 = new DataInputStream((InputStream)new FileInputStream(this.q));
                    try {
                        if (var10_4.readUnsignedShort() == 46586) {
                            FlurryAgent.super.a(var10_4);
                            break block22;
                        }
                        bc.a("FlurryAgent", "Unexpected file type");
                    }
                    catch (Throwable var12_7) {}
                }
                y.a((Closeable)var10_4);
                break block24;
                ** GOTO lbl-1000
                catch (Throwable var11_10) {
                    block25 : {
                        var10_4 = null;
                        break block25;
lbl22: // 1 sources:
                        bc.c("FlurryAgent", "Agent cache file doesn't exist.");
                        break block23;
                        catch (Throwable var11_12) {}
                    }
                    y.a(var10_4);
                    throw var11_11;
                }
                catch (Throwable var12_9) {
                    var10_4 = null;
                }
lbl-1000: // 2 sources:
                {
                    bc.b("FlurryAgent", "Error when loading persistent file", (Throwable)var12_8);
                    y.a((Closeable)var10_4);
                }
            }
            try {
                if (this.t) break block23;
                if (this.q.delete()) {
                    bc.a("FlurryAgent", "Deleted persistence file");
                    break block23;
                }
                bc.b("FlurryAgent", "Cannot delete persistence file");
            }
            catch (Throwable var14_13) {
                bc.b("FlurryAgent", "", var14_13);
            }
        }
        if (!this.t) {
            this.E = false;
            this.F = this.H;
            this.t = true;
        }
        if (var3_3 == null) {
            var5_5 = Double.doubleToLongBits((double)Math.random()) + 37L * (System.nanoTime() + (long)(37 * this.w.hashCode()));
            var7_6 = "ID" + Long.toString((long)var5_5, (int)16);
            bc.c("FlurryAgent", "Generated id");
        } else {
            var7_6 = var3_3;
        }
        FlurryAgent.super.d(var7_6);
        this.Z.a(this.C);
        this.Z.a(this.D);
        if (!var7_6.startsWith("AND") && !this.p.exists()) {
            FlurryAgent.super.b(var1, var7_6);
        }
        FlurryAgent.super.l();
        // MONITOREXIT : var19_2
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void b(Context var1, String var2_2) {
        block13 : {
            var9_3 = this;
            // MONITORENTER : var9_3
            this.p = var1.getFileStreamPath(".flurryb.");
            var4_4 = ad.a(this.p);
            if (!var4_4) {
                // MONITOREXIT : var9_3
                return;
            }
            var5_5 = new DataOutputStream((OutputStream)new FileOutputStream(this.p));
            try {
                var5_5.writeInt(1);
                var5_5.writeUTF(var2_2);
            }
            catch (Throwable var6_8) {
                ** continue;
            }
            y.a((Closeable)var5_5);
            return;
            catch (Throwable var6_6) {
                block14 : {
                    var5_5 = null;
                    break block14;
                    catch (Throwable var7_11) {
                        var5_5 = null;
                        break block13;
                    }
                }
lbl22: // 2 sources:
                do {
                    try {
                        bc.b("FlurryAgent", "Error when saving b file", (Throwable)var6_7);
                    }
                    catch (Throwable var7_10) {
                        break;
                    }
                    y.a((Closeable)var5_5);
                    return;
                    break;
                } while (true);
            }
        }
        y.a(var5_5);
        throw var7_9;
    }

    static /* synthetic */ void b(FlurryAgent flurryAgent) {
        flurryAgent.g();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    static /* synthetic */ void b(FlurryAgent flurryAgent, Context context) {
        try {
            FlurryAgent flurryAgent2 = flurryAgent;
            // MONITORENTER : flurryAgent2
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
            return;
        }
        long l2 = SystemClock.elapsedRealtime() - flurryAgent.u;
        boolean bl = !flurryAgent.s && l2 > f && flurryAgent.G.size() > 0;
        // MONITOREXIT : flurryAgent2
        if (!bl) return;
        flurryAgent.k();
    }

    static String c() {
        return FlurryAgent.e.w;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private String c(Context var1) {
        block15 : {
            block14 : {
                block13 : {
                    var2_2 = FlurryAgent.super.o();
                    if (var2_2 != null) lbl-1000: // 2 sources:
                    {
                        do {
                            return var2_2;
                            break;
                        } while (true);
                    }
                    var3_3 = Settings.System.getString((ContentResolver)var1.getContentResolver(), (String)"android_id");
                    var4_4 = false;
                    if (var3_3 == null) break block13;
                    var12_5 = var3_3.length();
                    var4_4 = false;
                    if (var12_5 <= 0) break block13;
                    var13_6 = var3_3.equals((Object)"null");
                    var4_4 = false;
                    if (!var13_6) break block14;
                }
lbl15: // 3 sources:
                while (var4_4) {
                    return "AND" + var3_3;
                }
                break block15;
            }
            var14_7 = FlurryAgent.a;
            var15_8 = var14_7.length;
            for (var16_9 = 0; var16_9 < var15_8; ++var16_9) {
                var17_10 = var3_3.equals((Object)var14_7[var16_9]);
                var4_4 = false;
                if (var17_10) ** GOTO lbl15
            }
            var4_4 = true;
            ** GOTO lbl15
        }
        ** while (!(var5_11 = var1.getFileStreamPath((String)".flurryb.")).exists())
lbl31: // 2 sources:
        var6_12 = new DataInputStream((InputStream)new FileInputStream(var5_11));
        try {
            var6_12.readInt();
            var11_13 = var6_12.readUTF();
        }
        catch (Throwable var7_16) {
            ** continue;
        }
        y.a((Closeable)var6_12);
        return var11_13;
        catch (Throwable var7_14) {
            var6_12 = null;
lbl39: // 2 sources:
            do {
                bc.b("FlurryAgent", "Error when loading b file", (Throwable)var7_15);
                y.a((Closeable)var6_12);
                return var2_2;
                break;
            } while (true);
        }
        catch (Throwable var8_17) {
            var6_12 = null;
lbl46: // 2 sources:
            do {
                y.a(var6_12);
                throw var8_18;
                break;
            } while (true);
        }
        {
            catch (Throwable var8_19) {
                ** continue;
            }
        }
    }

    private static String c(String string) {
        if (string != null && string.length() > 4) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i2 = 0; i2 < -4 + string.length(); ++i2) {
                stringBuilder.append('*');
            }
            stringBuilder.append(string.substring(-4 + string.length()));
            string = stringBuilder.toString();
        }
        return string;
    }

    static /* synthetic */ void c(FlurryAgent flurryAgent) {
        flurryAgent.m();
    }

    public static void clearTargetingKeywords() {
        FlurryAgent.e.Z.f();
    }

    public static void clearUserCookies() {
        FlurryAgent.e.Z.h();
    }

    static /* synthetic */ Handler d(FlurryAgent flurryAgent) {
        return flurryAgent.m;
    }

    static /* synthetic */ FlurryAgent d() {
        return e;
    }

    private static String d(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo.versionName != null) {
                return packageInfo.versionName;
            }
            if (packageInfo.versionCode != 0) {
                String string = Integer.toString((int)packageInfo.versionCode);
                return string;
            }
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
        }
        return "Unknown";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void d(String string) {
        void var3_2 = this;
        synchronized (var3_2) {
            if (string != null) {
                this.C = string;
            }
            return;
        }
    }

    static /* synthetic */ long e() {
        return f;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Location e(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0 || context.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
            String string;
            LocationManager locationManager = (LocationManager)context.getSystemService("location");
            void var6_3 = this;
            synchronized (var6_3) {
                if (this.B == null) {
                    this.B = locationManager;
                } else {
                    locationManager = this.B;
                }
            }
            Criteria criteria = k;
            if (criteria == null) {
                criteria = new Criteria();
            }
            if ((string = locationManager.getBestProvider(criteria, true)) != null) {
                locationManager.requestLocationUpdates(string, 0L, 0.0f, (LocationListener)this, Looper.getMainLooper());
                return locationManager.getLastKnownLocation(string);
            }
        }
        return null;
    }

    static /* synthetic */ be e(FlurryAgent flurryAgent) {
        return flurryAgent.Z;
    }

    public static void enableTestAds(boolean bl) {
        FlurryAgent.e.Z.a(bl);
    }

    public static void endTimedEvent(String string) {
        try {
            e.a(string);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "Failed to signify the end of event: " + string, throwable);
            return;
        }
    }

    static /* synthetic */ boolean f() {
        return l;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static byte[] f(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) return null;
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        if (telephonyManager == null) return null;
        String string = telephonyManager.getDeviceId();
        if (string == null) return null;
        if (string.trim().length() <= 0) return null;
        try {
            byte[] arrby = y.d(string);
            if (arrby != null && arrby.length == 20) {
                return arrby;
            }
            bc.b("FlurryAgent", "sha1 is not 20 bytes long: " + Arrays.toString((byte[])arrby));
            return null;
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Exception decompiling
     */
    private void g() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[TRYBLOCK]], but top level block is 19[FORLOOP]
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

    public static boolean getAd(Context context, String string, ViewGroup viewGroup, FlurryAdSize flurryAdSize, long l2) {
        if (context == null) {
            bc.b("FlurryAgent", "Context passed to getAd was null.");
            return false;
        }
        if (string == null) {
            bc.b("FlurryAgent", "Ad space name passed to getAd was null.");
            return false;
        }
        if (string.length() == 0) {
            bc.b("FlurryAgent", "Ad space name passed to getAd was empty.");
            return false;
        }
        if (viewGroup == null) {
            bc.b("FlurryAgent", "ViewGroup passed to getAd was null.");
            return false;
        }
        e.a(context);
        try {
            boolean bl = FlurryAgent.e.Z.a(context, string, flurryAdSize, viewGroup, l2);
            return bl;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
            return false;
        }
    }

    public static int getAgentVersion() {
        return 131;
    }

    public static boolean getForbidPlaintextFallback() {
        return false;
    }

    public static String getPhoneId() {
        return e.o();
    }

    public static boolean getUseHttps() {
        return h;
    }

    private void h() {
        FlurryAgent flurryAgent = this;
        synchronized (flurryAgent) {
            this.X = 1 + this.X;
            return;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private byte[] i() {
        var27_1 = this;
        // MONITORENTER : var27_1
        var1_2 = new CrcMessageDigest();
        var2_3 = new ByteArrayOutputStream();
        var3_4 = new DigestOutputStream((OutputStream)var2_3, (MessageDigest)var1_2);
        var4_5 = new DataOutputStream((OutputStream)var3_4);
        var4_5.writeShort(24);
        var4_5.writeShort(0);
        var4_5.writeLong(0L);
        var4_5.writeShort(0);
        var4_5.writeShort(3);
        var4_5.writeShort(131);
        var4_5.writeLong(System.currentTimeMillis());
        var4_5.writeUTF(this.w);
        var4_5.writeUTF(this.y);
        var4_5.writeShort(1 + this.D.size());
        var4_5.writeShort(0);
        var4_5.writeUTF(this.o());
        if (!this.D.isEmpty()) {
            for (Map.Entry var25_8 : this.D.entrySet()) {
                var4_5.writeShort(((Integer)var25_8.getKey()).intValue());
                var26_7 = ((ByteBuffer)var25_8.getValue()).array();
                var4_5.writeShort(var26_7.length);
                var4_5.write(var26_7);
            }
        }
        var4_5.writeByte(0);
        var4_5.writeLong(this.F);
        var4_5.writeLong(this.H);
        var4_5.writeShort(6);
        var4_5.writeUTF("device.model");
        var4_5.writeUTF(Build.MODEL);
        var4_5.writeUTF("build.brand");
        var4_5.writeUTF(Build.BRAND);
        var4_5.writeUTF("build.id");
        var4_5.writeUTF(Build.ID);
        var4_5.writeUTF("version.release");
        var4_5.writeUTF(Build.VERSION.RELEASE);
        var4_5.writeUTF("build.device");
        var4_5.writeUTF(Build.DEVICE);
        var4_5.writeUTF("build.product");
        var4_5.writeUTF(Build.PRODUCT);
        var11_14 = this.Y != null ? this.Y.keySet().size() : 0;
        "refMapSize is:  " + var11_14;
        if (var11_14 == 0) {
            "Referrer file Name if it exists:  " + (Object)this.r;
            this.l();
        }
        var4_5.writeShort(var11_14);
        if (this.Y != null) {
            for (Map.Entry var18_17 : this.Y.entrySet()) {
                "Referrer Entry:  " + (String)var18_17.getKey() + "=" + var18_17.getValue();
                var4_5.writeUTF((String)var18_17.getKey());
                "referrer key is :" + (String)var18_17.getKey();
                var4_5.writeShort(((List)var18_17.getValue()).size());
                for (String var22_16 : (List)var18_17.getValue()) {
                    var4_5.writeUTF(var22_16);
                    "referrer value is :" + var22_16;
                }
            }
        }
        var4_5.writeBoolean(false);
        var14_23 = this.G.size();
        var4_5.writeShort(var14_23);
        catch (Throwable var5_21) {
            var4_5 = null;
            ** GOTO lbl-1000
        }
        catch (Throwable var7_11) {
            var8_12 = null;
            ** GOTO lbl73
        }
        catch (Throwable var7_9) {
            var8_12 = var4_5;
lbl73: // 2 sources:
            try {
                bc.b("FlurryAgent", "Error when generating report", (Throwable)var7_10);
            }
            catch (Throwable var5_22) {
                var4_5 = var8_12;
                ** GOTO lbl-1000
            }
            y.a(var8_12);
            var10_13 = null;
            // MONITOREXIT : var27_1
            return var10_13;
        }
        catch (Throwable var5_19) lbl-1000: // 3 sources:
        {
            y.a(var4_5);
            throw var5_20;
        }
        for (var15_24 = 0; var15_24 < var14_23; ++var15_24) {
            var4_5.write((byte[])this.G.get(var15_24));
        }
        {
            this.A = new ArrayList(this.G);
            var3_4.on(false);
            var4_5.write(var1_2.getDigest());
            var4_5.close();
            var10_13 = var16_25 = var2_3.toByteArray();
        }
        y.a((Closeable)var4_5);
        return var10_13;
    }

    public static void initializeAds(Context context) {
        if (context == null) {
            bc.b("FlurryAgent", "Context passed to initializeAds was null.");
            return;
        }
        e.a(context);
        try {
            FlurryAgent.e.Z.b(context);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
            return;
        }
    }

    public static boolean isAdAvailable(Context context, String string, FlurryAdSize flurryAdSize, long l2) {
        if (context == null) {
            bc.b("FlurryAgent", "Context passed to isAdAvailable was null.");
            return false;
        }
        if (string == null) {
            bc.b("FlurryAgent", "Ad space name passed to isAdAvailable was null.");
            return false;
        }
        if (string.length() == 0) {
            bc.b("FlurryAgent", "Ad space name passed to isAdAvailable was empty.");
            return false;
        }
        e.a(context);
        try {
            boolean bl = FlurryAgent.e.Z.a(context, string, flurryAdSize, l2);
            return bl;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
            return false;
        }
    }

    protected static boolean isCaptureUncaughtExceptions() {
        return j;
    }

    private static String j() {
        if (b != null) {
            return b;
        }
        if (i) {
            return c;
        }
        if (h) {
            return d;
        }
        return c;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void k() {
        try {
            bc.a("FlurryAgent", "generating report");
            byte[] arrby = this.i();
            if (arrby == null) {
                bc.a("FlurryAgent", "Error generating report");
                return;
            }
            if (!this.a(arrby)) return;
            {
                StringBuilder stringBuilder = new StringBuilder().append("Done sending ");
                String string = this.s ? "initial " : "";
                bc.a("FlurryAgent", stringBuilder.append(string).append("agent report").toString());
                this.m();
                return;
            }
        }
        catch (IOException iOException) {
            bc.a("FlurryAgent", "", iOException);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
        }
    }

    private void l() {
        if (this.r.exists()) {
            bc.c("FlurryAgent", "Loading referrer info from file:  " + this.r.getAbsolutePath());
            String string = FlurryAgent.a(this.r);
            if (string != null) {
                this.Y = FlurryAgent.b(string);
            }
        }
    }

    public static void logEvent(String string) {
        try {
            e.a(string, null, false);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "Failed to log event: " + string, throwable);
            return;
        }
    }

    public static void logEvent(String string, Map<String, String> map) {
        try {
            e.a(string, map, false);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "Failed to log event: " + string, throwable);
            return;
        }
    }

    public static void logEvent(String string, Map<String, String> map, boolean bl) {
        try {
            e.a(string, map, bl);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "Failed to log event: " + string, throwable);
            return;
        }
    }

    public static void logEvent(String string, boolean bl) {
        try {
            e.a(string, null, bl);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "Failed to log event: " + string, throwable);
            return;
        }
    }

    /*
     * Exception decompiling
     */
    private void m() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 13[UNCONDITIONALDOLOOP]
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

    private void n() {
        FlurryAgent flurryAgent = this;
        synchronized (flurryAgent) {
            if (this.B != null) {
                this.B.removeUpdates((LocationListener)this);
            }
            return;
        }
    }

    private String o() {
        FlurryAgent flurryAgent = this;
        synchronized (flurryAgent) {
            String string = this.C;
            return string;
        }
    }

    public static void onEndSession(Context context) {
        if (context == null) {
            throw new NullPointerException("Null context");
        }
        try {
            e.a(context, false);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
            return;
        }
    }

    public static void onError(String string, String string2, String string3) {
        try {
            e.a(string, string2, string3);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
            return;
        }
    }

    public static void onEvent(String string) {
        try {
            e.a(string, null, false);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
            return;
        }
    }

    public static void onEvent(String string, Map<String, String> map) {
        try {
            e.a(string, map, false);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
            return;
        }
    }

    public static void onPageView() {
        try {
            e.h();
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
            return;
        }
    }

    public static void onStartSession(Context context, String string) {
        if (context == null) {
            throw new NullPointerException("Null context");
        }
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException("Api key not specified");
        }
        try {
            e.a(context, string);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
            return;
        }
    }

    public static void removeAd(Context context, String string, ViewGroup viewGroup) {
        if (context == null) {
            bc.b("FlurryAgent", "Context passed to removeAd was null.");
            return;
        }
        if (string == null) {
            bc.b("FlurryAgent", "Ad space name passed to removeAd was null.");
            return;
        }
        if (string.length() == 0) {
            bc.b("FlurryAgent", "Ad space name passed to removeAd was empty.");
            return;
        }
        if (viewGroup == null) {
            bc.b("FlurryAgent", "ViewGroup passed to removeAd was null.");
            return;
        }
        try {
            FlurryAgent.e.Z.a(context, viewGroup);
            return;
        }
        catch (Throwable throwable) {
            bc.b("FlurryAgent", "", throwable);
            return;
        }
    }

    public static void sendAdLogsToServer() {
        FlurryAgent.e.Z.j();
    }

    public static void setAdListener(IListener iListener) {
        FlurryAgent.e.Z.a(iListener);
    }

    public static void setAdLogUrl(String string) {
        FlurryAgent.e.Z.c(string);
    }

    public static void setAdServerUrl(String string) {
        FlurryAgent.e.Z.b(string);
    }

    public static void setAge(int n2) {
        if (n2 > 0 && n2 < 110) {
            Date date = new Date(new Date(System.currentTimeMillis() - 31449600000L * (long)n2).getYear(), 1, 1);
            FlurryAgent.e.P = date.getTime();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setCaptureUncaughtExceptions(boolean bl) {
        FlurryAgent flurryAgent;
        FlurryAgent flurryAgent2 = flurryAgent = e;
        synchronized (flurryAgent2) {
            if (FlurryAgent.e.s) {
                bc.b("FlurryAgent", "Cannot setCaptureUncaughtExceptions after onStartSession");
                return;
            }
            j = bl;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setContinueSessionMillis(long l2) {
        FlurryAgent flurryAgent;
        if (l2 < 5000L) {
            bc.b("FlurryAgent", "Invalid time set for session resumption: " + l2);
            return;
        }
        FlurryAgent flurryAgent2 = flurryAgent = e;
        synchronized (flurryAgent2) {
            f = l2;
            return;
        }
    }

    public static void setCustomAdNetworkHandler(ICustomAdNetworkHandler iCustomAdNetworkHandler) {
        FlurryAgent.e.Z.a(iCustomAdNetworkHandler);
    }

    public static void setGender(byte by) {
        switch (by) {
            default: {
                FlurryAgent.e.O = (byte)-1;
                return;
            }
            case 0: 
            case 1: 
        }
        FlurryAgent.e.O = by;
    }

    public static void setLocation(float f2, float f3) {
        FlurryAgent.e.Z.a(f2, f3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setLocationCriteria(Criteria criteria) {
        FlurryAgent flurryAgent;
        FlurryAgent flurryAgent2 = flurryAgent = e;
        synchronized (flurryAgent2) {
            k = criteria;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setLogEnabled(boolean bl) {
        FlurryAgent flurryAgent;
        FlurryAgent flurryAgent2 = flurryAgent = e;
        synchronized (flurryAgent2) {
            if (bl) {
                bc.b();
            } else {
                bc.a();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setLogEvents(boolean bl) {
        FlurryAgent flurryAgent;
        FlurryAgent flurryAgent2 = flurryAgent = e;
        synchronized (flurryAgent2) {
            g = bl;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setLogLevel(int n2) {
        FlurryAgent flurryAgent;
        FlurryAgent flurryAgent2 = flurryAgent = e;
        synchronized (flurryAgent2) {
            bc.a(n2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setReportLocation(boolean bl) {
        FlurryAgent flurryAgent;
        FlurryAgent flurryAgent2 = flurryAgent = e;
        synchronized (flurryAgent2) {
            FlurryAgent.e.z = bl;
            return;
        }
    }

    public static void setReportUrl(String string) {
        b = string;
    }

    public static void setTargetingKeywords(Map<String, String> map) {
        FlurryAgent.e.Z.b(map);
    }

    public static void setUseHttps(boolean bl) {
        h = bl;
    }

    public static void setUserCookies(Map<String, String> map) {
        FlurryAgent.e.Z.c(map);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setUserId(String string) {
        FlurryAgent flurryAgent;
        FlurryAgent flurryAgent2 = flurryAgent = e;
        synchronized (flurryAgent2) {
            FlurryAgent.e.N = y.a(string);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setVersionName(String string) {
        FlurryAgent flurryAgent;
        FlurryAgent flurryAgent2 = flurryAgent = e;
        synchronized (flurryAgent2) {
            FlurryAgent.e.y = string;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    final void a(Throwable throwable) {
        throwable.printStackTrace();
        String string = "";
        StackTraceElement[] arrstackTraceElement = throwable.getStackTrace();
        if (arrstackTraceElement != null && arrstackTraceElement.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            if (throwable.getMessage() != null) {
                stringBuilder.append(" (" + throwable.getMessage() + ")\n");
            }
            for (int i2 = 0; i2 < arrstackTraceElement.length; ++i2) {
                if (i2 != 0) {
                    stringBuilder.append('\n');
                }
                StackTraceElement stackTraceElement = arrstackTraceElement[i2];
                stringBuilder.append(stackTraceElement.getClassName()).append(".").append(stackTraceElement.getMethodName()).append(":").append(stackTraceElement.getLineNumber());
            }
            string = stringBuilder.toString();
        } else if (throwable.getMessage() != null) {
            string = throwable.getMessage();
        }
        FlurryAgent.onError("uncaught", string, throwable.getClass().toString());
        this.v.clear();
        FlurryAgent.super.a(null, true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void onLocationChanged(Location location) {
        void var5_2 = this;
        synchronized (var5_2) {
            try {
                this.R = location;
                FlurryAgent.super.n();
                do {
                    return;
                    break;
                } while (true);
            }
            catch (Throwable throwable) {
                try {
                    bc.b("FlurryAgent", "", throwable);
                    return;
                }
                catch (Throwable throwable2) {
                    throw throwable2;
                }
                finally {
                }
            }
        }
    }

    public final void onProviderDisabled(String string) {
    }

    public final void onProviderEnabled(String string) {
    }

    public final void onStatusChanged(String string, int n2, Bundle bundle) {
    }
}


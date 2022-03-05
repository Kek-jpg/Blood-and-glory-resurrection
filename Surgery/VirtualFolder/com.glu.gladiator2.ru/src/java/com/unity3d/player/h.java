/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.hardware.Sensor
 *  android.hardware.SensorEvent
 *  android.hardware.SensorEventListener
 *  android.location.Criteria
 *  android.location.Location
 *  android.location.LocationListener
 *  android.location.LocationManager
 *  android.location.LocationProvider
 *  android.os.Bundle
 *  android.os.Looper
 *  android.view.WindowManager
 *  com.unity3d.player.UnityPlayer
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.util.Iterator
 *  java.util.List
 */
package com.unity3d.player;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Looper;
import android.view.WindowManager;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.f;
import com.unity3d.player.i;
import com.unity3d.player.j;
import com.unity3d.player.k;
import com.unity3d.player.l;
import com.unity3d.player.m;
import com.unity3d.player.n;
import com.unity3d.player.o;
import java.util.Iterator;
import java.util.List;

final class h
implements SensorEventListener,
LocationListener {
    private static final int[] d = new int[]{1, 1, 0, 1, -1, 1, 1, 0, -1, -1, 0, 1, 1, -1, 1, 0};
    private Runnable A;
    private Runnable B;
    private Runnable C;
    private Runnable D;
    private Runnable E;
    private float[] F;
    private double G;
    private Runnable H;
    private float[] I;
    private float[] J;
    private int K;
    private Runnable L;
    private Location M;
    private float N;
    private boolean O;
    private int P;
    private boolean Q;
    private int R;
    private final Context a;
    private final UnityPlayer b;
    private final WindowManager c;
    private float[] e = new float[3];
    private float[] f = new float[3];
    private float g;
    private float h;
    private float i;
    private long j;
    private float k;
    private float l;
    private float m;
    private long n;
    private float o;
    private float p;
    private float q;
    private long r;
    private float s;
    private float t;
    private float u;
    private long v;
    private float w;
    private float x;
    private float y;
    private float z;

    protected h(Context context, UnityPlayer unityPlayer) {
        this.A = new i((h)this);
        this.B = new j((h)this);
        this.C = new k((h)this);
        this.D = new l((h)this);
        this.E = new m((h)this);
        this.F = new float[5];
        this.H = new n((h)this);
        this.I = new float[9];
        this.J = new float[3];
        this.L = new o((h)this);
        this.N = 0.0f;
        this.O = false;
        this.P = 0;
        this.Q = false;
        this.R = 0;
        this.a = context;
        this.b = unityPlayer;
        this.c = (WindowManager)this.a.getSystemService("window");
    }

    static /* synthetic */ float a(h h2) {
        return h2.g;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(Location location) {
        boolean bl = true;
        if (location == null) return;
        Location location2 = this.M;
        if (location2 != null) {
            long l2 = location.getTime() - location2.getTime();
            boolean bl2 = l2 > 120000L ? bl : false;
            boolean bl3 = l2 < -120000L ? bl : false;
            boolean bl4 = l2 > 0L ? bl : false;
            if (!bl2) {
                if (bl3) return;
                int n2 = (int)(location.getAccuracy() - location2.getAccuracy());
                boolean bl5 = n2 > 0 ? bl : false;
                boolean bl6 = n2 < 0 ? bl : false;
                boolean bl7 = n2 > 200 ? bl : false;
                boolean bl8 = location.getAccuracy() == 0.0f ? bl : false;
                boolean bl9 = bl8 | bl7;
                String string = location.getProvider();
                String string2 = location2.getProvider();
                boolean bl10 = string == null ? (string2 == null ? bl : false) : string.equals((Object)string2);
                if (!(bl6 || bl4 && !bl5)) {
                    if (!bl4) return;
                    if (bl9) return;
                    if (!bl10) {
                        return;
                    }
                }
            }
        }
        if (!bl) {
            return;
        }
        this.M = location;
        this.b.nativeSetLocation((float)location.getLatitude(), (float)location.getLongitude(), (float)location.getAltitude(), location.getAccuracy(), (double)location.getTime() / 1000.0);
    }

    static /* synthetic */ float b(h h2) {
        return h2.h;
    }

    static /* synthetic */ float c(h h2) {
        return h2.i;
    }

    static /* synthetic */ long d(h h2) {
        return h2.j;
    }

    static /* synthetic */ UnityPlayer e(h h2) {
        return h2.b;
    }

    static /* synthetic */ float f(h h2) {
        return h2.k;
    }

    static /* synthetic */ long f() {
        return 0L;
    }

    static /* synthetic */ float g(h h2) {
        return h2.l;
    }

    static /* synthetic */ float h(h h2) {
        return h2.m;
    }

    static /* synthetic */ long i(h h2) {
        return h2.n;
    }

    static /* synthetic */ float j(h h2) {
        return h2.o;
    }

    static /* synthetic */ float k(h h2) {
        return h2.p;
    }

    static /* synthetic */ float l(h h2) {
        return h2.q;
    }

    static /* synthetic */ long m(h h2) {
        return h2.r;
    }

    static /* synthetic */ float n(h h2) {
        return h2.s;
    }

    static /* synthetic */ float o(h h2) {
        return h2.t;
    }

    static /* synthetic */ float p(h h2) {
        return h2.u;
    }

    static /* synthetic */ long q(h h2) {
        return h2.v;
    }

    static /* synthetic */ float r(h h2) {
        return h2.w;
    }

    static /* synthetic */ float s(h h2) {
        return h2.x;
    }

    static /* synthetic */ float t(h h2) {
        return h2.y;
    }

    static /* synthetic */ float u(h h2) {
        return h2.z;
    }

    static /* synthetic */ float[] v(h h2) {
        return h2.F;
    }

    static /* synthetic */ double w(h h2) {
        return h2.G;
    }

    static /* synthetic */ int x(h h2) {
        return h2.K;
    }

    public final void a(float f2) {
        this.N = f2;
    }

    public final boolean a() {
        return !((LocationManager)this.a.getSystemService("location")).getProviders(new Criteria(), true).isEmpty();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void b() {
        this.Q = false;
        if (this.O) {
            f.Log(5, "Location_StartUpdatingLocation already started!");
            return;
        } else {
            LocationProvider locationProvider;
            if (!this.a()) {
                this.R = 3;
                this.b.nativeSetLocationStatus(3);
                return;
            }
            LocationManager locationManager = (LocationManager)this.a.getSystemService("location");
            this.R = 1;
            this.b.nativeSetLocationStatus(1);
            List list = locationManager.getProviders(true);
            if (list.isEmpty()) {
                this.R = 3;
                this.b.nativeSetLocationStatus(3);
                return;
            }
            if (this.P == 2) {
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    LocationProvider locationProvider2 = locationManager.getProvider((String)iterator.next());
                    if (locationProvider2.getAccuracy() != 2) continue;
                    locationProvider = locationProvider2;
                    break;
                }
            } else {
                locationProvider = null;
            }
            for (String string : list) {
                if (locationProvider != null && locationManager.getProvider(string).getAccuracy() == 1) continue;
                this.a(locationManager.getLastKnownLocation(string));
                locationManager.requestLocationUpdates(string, 0L, this.N, (LocationListener)this, this.a.getMainLooper());
                this.O = true;
            }
        }
    }

    public final void b(float f2) {
        if (f2 < 100.0f) {
            this.P = 1;
            return;
        }
        if (f2 < 500.0f) {
            this.P = 1;
            return;
        }
        this.P = 2;
    }

    public final void c() {
        ((LocationManager)this.a.getSystemService("location")).removeUpdates((LocationListener)this);
        this.O = false;
        this.M = null;
        this.R = 0;
        this.b.nativeSetLocationStatus(0);
    }

    public final void d() {
        if (this.R == 1 || this.R == 2) {
            this.Q = true;
            this.c();
        }
    }

    public final void e() {
        if (this.Q) {
            this.b();
        }
    }

    public final void onAccuracyChanged(Sensor sensor, int n2) {
    }

    public final void onLocationChanged(Location location) {
        this.R = 2;
        this.b.nativeSetLocationStatus(2);
        h.super.a(location);
    }

    public final void onProviderDisabled(String string) {
        this.M = null;
    }

    public final void onProviderEnabled(String string) {
    }

    /*
     * Exception decompiling
     */
    public final void onSensorChanged(SensorEvent var1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.b.a.a.b.as.a(SwitchReplacer.java:358)
        // org.benf.cfr.reader.b.a.a.b.as.a(SwitchReplacer.java:328)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:462)
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

    public final void onStatusChanged(String string, int n2, Bundle bundle) {
    }
}


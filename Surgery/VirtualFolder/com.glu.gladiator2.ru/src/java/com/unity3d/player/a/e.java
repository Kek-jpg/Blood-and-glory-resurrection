/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.RemoteException
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.RuntimeException
 *  java.lang.SecurityException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.security.KeyFactory
 *  java.security.NoSuchAlgorithmException
 *  java.security.PublicKey
 *  java.security.SecureRandom
 *  java.security.spec.InvalidKeySpecException
 *  java.security.spec.KeySpec
 *  java.security.spec.X509EncodedKeySpec
 *  java.util.HashSet
 *  java.util.LinkedList
 *  java.util.Queue
 *  java.util.Set
 */
package com.unity3d.player.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.unity3d.player.a.b;
import com.unity3d.player.a.c;
import com.unity3d.player.a.d;
import com.unity3d.player.a.f;
import com.unity3d.player.a.g;
import com.unity3d.player.a.i;
import com.unity3d.player.a.k;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public final class e
implements ServiceConnection {
    protected static byte[] a = new byte[]{-99, -111, -109, -46, -97, -110, -100, -114, -111, -105, -100, -46, -118, -101, -110, -100, -105, -110, -103, -46, -108, -105, -99, -101, -110, -115, -105, -110, -103, -46, -73, -76, -105, -99, -101, -110, -115, -101, -82, -101, -115, -117, -108, -116, -76, -105, -115, -116, -101, -110, -101, -114};
    protected static byte[] b;
    private static final SecureRandom c;
    private d d;
    private PublicKey e;
    private final Context f;
    private final i g;
    private Handler h;
    private final String i;
    private final String j;
    private final Set k = new HashSet();
    private final Queue l = new LinkedList();

    static {
        byte[] arrby = new byte[]{54, 51, 22, 28, 26, 17, 12, 22, 17, 24, 44, 26, 13, 9, 22, 28, 26};
        b = new byte[30 + arrby.length];
        for (int i2 = 0; i2 < a.length; ++i2) {
            e.a[i2] = -a[i2];
            if (i2 >= 30) continue;
            e.b[i2] = a[i2];
            if (i2 >= arrby.length) continue;
            e.b[i2 + 30] = (byte)(127 ^ arrby[i2]);
        }
        c = new SecureRandom();
    }

    public e(Context context, i i2, String string2) {
        this.f = context;
        this.g = i2;
        this.e = e.a(string2);
        this.i = this.f.getPackageName();
        this.j = e.a(context, this.i);
        HandlerThread handlerThread = new HandlerThread(this.i);
        handlerThread.start();
        this.h = new Handler(handlerThread.getLooper());
    }

    private static String a(Context context, String string2) {
        try {
            String string3 = String.valueOf((int)context.getPackageManager().getPackageInfo((String)string2, (int)0).versionCode);
            return string3;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return "";
        }
    }

    private static PublicKey a(String string2) {
        try {
            byte[] arrby = com.unity3d.player.b.a.a(string2);
            PublicKey publicKey = KeyFactory.getInstance((String)"RSA").generatePublic((KeySpec)new X509EncodedKeySpec(arrby));
            return publicKey;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException((Throwable)noSuchAlgorithmException);
        }
        catch (com.unity3d.player.b.b b2) {
            throw new IllegalArgumentException((Throwable)b2);
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            throw new IllegalArgumentException((Throwable)invalidKeySpecException);
        }
    }

    private void a(g g2) {
        void var4_2 = this;
        synchronized (var4_2) {
            this.k.remove((Object)g2);
            if (this.k.isEmpty()) {
                e.super.c();
            }
            return;
        }
    }

    private void b() {
        g g2;
        while ((g2 = (g)this.l.poll()) != null) {
            try {
                this.d.a(g2.b(), g2.c(), new a(g2));
                this.k.add((Object)g2);
            }
            catch (RemoteException remoteException) {
                this.b(g2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void b(g g2) {
        void var3_2 = this;
        synchronized (var3_2) {
            this.g.a(-1, null);
            if (this.g.a()) {
                g2.a().a();
            } else {
                g2.a().b();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void c() {
        if (this.d != null) {
            try {
                this.f.unbindService((ServiceConnection)this);
            }
            catch (IllegalArgumentException illegalArgumentException) {}
            this.d = null;
        }
    }

    public final void a() {
        e e2 = this;
        synchronized (e2) {
            this.c();
            this.h.getLooper().quit();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a(f f2) {
        void var9_2 = this;
        synchronized (var9_2) {
            if (this.g.a()) {
                f2.a();
            } else {
                g g2 = new g(this.g, new b(), f2, c.nextInt(), this.i, this.j);
                d d2 = this.d;
                if (d2 == null) {
                    try {
                        if (this.f.bindService(new Intent(new String(b)), (ServiceConnection)this, 1)) {
                            this.l.offer((Object)g2);
                        }
                        e.super.b(g2);
                    }
                    catch (SecurityException securityException) {}
                } else {
                    this.l.offer((Object)g2);
                    e.super.b();
                }
            }
            return;
        }
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        void var4_3 = this;
        synchronized (var4_3) {
            this.d = d.a.a(iBinder);
            e.super.b();
            return;
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        void var3_2 = this;
        synchronized (var3_2) {
            this.d = null;
            return;
        }
    }

    final class a
    extends c.a {
        private final g b;
        private Runnable c;

        public a(g g2) {
            this.b = g2;
            this.c = new Runnable((a)this){
                private /* synthetic */ a a;
                {
                    this.a = a2;
                }

                public final void run() {
                    this.a.e.this.b(this.a.b);
                    this.a.e.this.a(this.a.b);
                }
            };
            e.this.h.postDelayed(this.c, 10000L);
        }

        @Override
        public final void a(int n2, String string2, String string3) {
            e.this.h.post(new Runnable((a)this, n2, string2, string3){
                private /* synthetic */ int a;
                private /* synthetic */ String b;
                private /* synthetic */ String c;
                private /* synthetic */ a d;
                {
                    this.d = a2;
                    this.a = n2;
                    this.b = string2;
                    this.c = string3;
                }

                public final void run() {
                    if (this.d.e.this.k.contains((Object)this.d.b)) {
                        this.d.e.this.h.removeCallbacks(this.d.c);
                        this.d.b.a(this.d.e.this.e, this.a, this.b, this.c);
                        this.d.e.this.a(this.d.b);
                    }
                }
            });
        }

    }

}


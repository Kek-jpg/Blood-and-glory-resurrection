/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnCompletionListener
 *  android.media.MediaPlayer$OnErrorListener
 *  android.media.MediaPlayer$OnPreparedListener
 *  android.media.MediaPlayer$OnVideoSizeChangedListener
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.wildtangent.brandboost.a;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.wildtangent.brandboost.a.b;
import com.wildtangent.brandboost.a.c;
import com.wildtangent.brandboost.a.d;
import com.wildtangent.brandboost.a.e;
import com.wildtangent.brandboost.a.f;
import com.wildtangent.brandboost.a.g;
import com.wildtangent.brandboost.n;

public final class a {
    private static final String a = "com.wildtangent.brandboost__" + a.class.getSimpleName();
    private final Activity b;
    private final FrameLayout c;
    private g d;
    private int e = -1;
    private int f = 0;
    private int g = 0;
    private final Handler h;
    private String i;
    private String j;
    private boolean k = false;
    private boolean l = false;
    private boolean m = false;
    private final Runnable n;
    private final Runnable o;
    private double p;
    private final Runnable q;
    private final Runnable r;
    private final Handler s;

    public a(Activity activity, Handler handler) {
        this.n = new d((a)this);
        this.o = new e((a)this);
        this.q = new b((a)this);
        this.r = new c((a)this);
        this.s = new f((a)this);
        this.b = activity;
        this.h = handler;
        this.c = new FrameLayout((Context)this.b);
    }

    static /* synthetic */ double a(a a2, double d2) {
        a2.p = d2;
        return d2;
    }

    static /* synthetic */ void a(a a2, int n2, int n3, int n4, int n5) {
        a2.b(n2, n3, n4, n5);
    }

    static /* synthetic */ boolean a(a a2) {
        return a2.k;
    }

    private void b(int n2, int n3, int n4, int n5) {
        com.wildtangent.brandboost.util.b.a(a, "positionVideo. x: " + n2 + ", y: " + n3 + ", w: " + n4 + ", h: " + n5);
        this.c.setPadding(n2, n3, 0, 0);
        this.d.a(n4, n5);
    }

    static /* synthetic */ boolean b(a a2, boolean bl) {
        a2.m = bl;
        return bl;
    }

    static /* synthetic */ double e(a a2) {
        return a2.p;
    }

    static /* synthetic */ Handler f(a a2) {
        return a2.h;
    }

    static /* synthetic */ String g(a a2) {
        return a2.i;
    }

    private void g() {
        com.wildtangent.brandboost.util.b.a(a, "Resetting video overlay.");
        if (this.c.getParent() == null) {
            this.b.addContentView((View)this.c, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
        }
        this.c.removeAllViews();
        this.d = new g((Context)this.b);
        this.c.addView((View)this.d);
        this.d.setZOrderOnTop(true);
        this.d.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

            public void onCompletion(MediaPlayer mediaPlayer) {
                com.wildtangent.brandboost.util.b.a(a, "onCompletion");
                a.this.a();
                a.this.d.post(a.this.r);
            }
        });
        this.d.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){

            public void onPrepared(MediaPlayer mediaPlayer) {
                a.this.e = mediaPlayer.getDuration();
                a.this.f = mediaPlayer.getVideoWidth();
                a.this.g = mediaPlayer.getVideoHeight();
                com.wildtangent.brandboost.util.b.a(a, "onPrepared. w: " + a.this.f + ", h: " + a.this.g + " duration: " + a.this.e);
                if (a.this.f != 0 && a.this.g != 0) {
                    a.this.h();
                    return;
                }
                com.wildtangent.brandboost.util.b.d(a, "Video not ready yet...");
                a.this.d.removeCallbacks(a.this.n);
                a.this.d.postDelayed(a.this.n, 5000L);
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener(){

                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int n2, int n3) {
                        a.this.f = mediaPlayer.getVideoWidth();
                        a.this.g = mediaPlayer.getVideoHeight();
                        if (a.this.f != 0 && a.this.g != 0) {
                            com.wildtangent.brandboost.util.b.c(a, "Now it's ready.");
                            a.this.h();
                        }
                    }
                });
            }

        });
        this.d.setOnErrorListener(new MediaPlayer.OnErrorListener(){

            public boolean onError(MediaPlayer mediaPlayer, int n2, int n3) {
                com.wildtangent.brandboost.util.b.b(a, "onError: " + n2 + ", " + n3);
                a.this.d.removeCallbacks(a.this.n);
                a.this.d.removeCallbacks(a.this.o);
                a.this.a();
                a.this.l = true;
                a.this.d.post(a.this.r);
                a.this.e = -2;
                return true;
            }
        });
        this.a(false);
    }

    static /* synthetic */ Runnable h(a a2) {
        return a2.q;
    }

    private void h() {
        this.d.removeCallbacks(this.n);
        Message message = this.h.obtainMessage(1000);
        message.getData().putString("callback", this.j);
        message.sendToTarget();
    }

    static /* synthetic */ boolean i(a a2) {
        return a2.m;
    }

    static /* synthetic */ boolean j(a a2) {
        return a2.l;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a() {
        try {
            this.d.stopPlayback();
        }
        catch (IllegalArgumentException illegalArgumentException) {}
        this.d.removeCallbacks(this.q);
        this.d.removeCallbacks(this.o);
        this.a(false);
    }

    public void a(int n2, int n3, int n4, int n5) {
        com.wildtangent.brandboost.util.b.a(a, "starting video playback");
        a.super.b(n2, n3, n4, n5);
        this.d.removeCallbacks(this.q);
        this.d.start();
        this.p = -1.0;
        this.d.post(this.q);
        this.k = true;
        this.d.postDelayed(this.o, (long)(1.2 * (double)this.e));
    }

    public void a(String string, String string2, String string3) {
        this.m = false;
        a.super.g();
        this.e = -1;
        this.j = string2;
        this.i = string3;
        com.wildtangent.brandboost.util.b.a(a, "Setup: " + string);
        Uri uri = Uri.parse((String)string);
        new n(this.s).execute((Object[])new Uri[]{uri});
        this.k = false;
        this.d.postDelayed(this.n, 30000L);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(boolean bl) {
        int n2 = bl ? 0 : 8;
        this.d.setVisibility(n2);
        this.c.setVisibility(n2);
    }

    public void b() {
        if (this.d != null) {
            this.d.removeCallbacks(this.n);
            this.d.removeCallbacks(this.q);
            this.d.removeCallbacks(this.o);
            this.g();
        }
    }

    public int c() {
        return this.e;
    }

    public int d() {
        return this.f;
    }

    public int e() {
        return this.g;
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 *  android.os.Handler
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.AnimationSet
 *  android.webkit.WebViewClient
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.net.MalformedURLException
 *  java.net.URL
 *  java.util.Random
 */
package com.wildtangent.brandboost.b;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import com.wildtangent.brandboost.BrandBoostAPI;
import com.wildtangent.brandboost.b.b;
import com.wildtangent.brandboost.b.c;
import com.wildtangent.brandboost.b.d;
import com.wildtangent.brandboost.b.e;
import com.wildtangent.brandboost.b.g;
import com.wildtangent.brandboost.b.h;
import com.wildtangent.brandboost.b.i;
import com.wildtangent.brandboost.b.l;
import com.wildtangent.brandboost.b.m;
import com.wildtangent.brandboost.util.a;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public final class f
extends RelativeLayout
implements a.a {
    private static final String c = "com.wildtangent.brandboost__" + f.class.getSimpleName();
    Animation.AnimationListener a;
    Animation.AnimationListener b;
    private com.wildtangent.brandboost.b.a d = null;
    private m e = null;
    private int f = 0;
    private int g = 0;
    private l h = null;
    private Activity i = null;
    private a j = null;
    private boolean k = false;
    private boolean l = false;
    private RelativeLayout m;
    private RelativeLayout.LayoutParams n;
    private BrandBoostAPI.Position o;
    private com.wildtangent.brandboost.util.a p = null;
    private Random q;
    private WebViewClient r;
    private Handler s;
    private View.OnClickListener t;
    private View.OnTouchListener u;

    /*
     * Enabled aggressive block sorting
     */
    public f(Activity activity, m m2, BrandBoostAPI.Position position, a a2) {
        super((Context)activity);
        this.r = new e((f)this);
        this.s = new d((f)this);
        this.a = new c((f)this);
        this.b = new i((f)this);
        this.t = new h((f)this);
        this.u = new g((f)this);
        this.i = activity;
        this.e = m2;
        this.j = a2;
        this.o = position;
        this.q = new Random(System.currentTimeMillis());
        this.setPadding(10, 10, 10, 10);
        this.setBackgroundColor(0);
        if (!f.super.b(this.i, m2)) {
            f.super.o();
            return;
        } else {
            if (f.super.a(this.i, m2)) return;
            {
                f.super.o();
                return;
            }
        }
    }

    private URL a(String string) {
        try {
            URL uRL = new URL(string);
            return uRL;
        }
        catch (MalformedURLException malformedURLException) {
            com.wildtangent.brandboost.util.b.a(c, malformedURLException);
            return null;
        }
    }

    static /* synthetic */ void a(f f2) {
        f2.o();
    }

    private boolean a(Activity activity, m m2) {
        com.wildtangent.brandboost.util.b.a(c, "asyncCreateHover");
        if (!com.wildtangent.brandboost.util.g.a(m2.e())) {
            this.k = false;
            this.d = new com.wildtangent.brandboost.b.a(activity);
            int n2 = this.e.b();
            int n3 = this.e.c();
            com.wildtangent.brandboost.util.b.a(c, "close top: " + n2);
            com.wildtangent.brandboost.util.b.a(c, "close right: " + n3);
            this.d.setPadding(0, n2, n3, 0);
            this.d.setOnTouchListener(this.u);
            this.d.setWebViewClient(this.r);
            this.d.loadUrl(m2.e());
            return true;
        }
        com.wildtangent.brandboost.util.b.d(c, "URL SIP is invalid");
        return false;
    }

    static /* synthetic */ boolean a(f f2, boolean bl) {
        f2.k = bl;
        return bl;
    }

    private boolean a(m m2) {
        if (m2 == null) {
            return false;
        }
        return m2.a();
    }

    private RelativeLayout.LayoutParams b(BrandBoostAPI.Position position) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.e.f(), this.e.g());
        switch (1.a[position.ordinal()]) {
            default: {
                com.wildtangent.brandboost.util.b.b(c, "Cannot layout hover. Invalid hover position specified. " + (Object)((Object)position));
                return null;
            }
            case 1: {
                layoutParams.addRule(10);
                layoutParams.addRule(14);
                return layoutParams;
            }
            case 2: {
                layoutParams.addRule(10);
                layoutParams.addRule(11);
                return layoutParams;
            }
            case 3: {
                layoutParams.addRule(11);
                layoutParams.addRule(15);
                return layoutParams;
            }
            case 4: {
                layoutParams.addRule(12);
                layoutParams.addRule(11);
                return layoutParams;
            }
            case 5: {
                layoutParams.addRule(12);
                layoutParams.addRule(14);
                return layoutParams;
            }
            case 6: {
                layoutParams.addRule(12);
                layoutParams.addRule(9);
                return layoutParams;
            }
            case 7: {
                layoutParams.addRule(9);
                layoutParams.addRule(15);
                return layoutParams;
            }
            case 8: {
                layoutParams.addRule(10);
                layoutParams.addRule(9);
                return layoutParams;
            }
            case 9: 
        }
        layoutParams.addRule(13);
        return layoutParams;
    }

    private boolean b(Activity activity, m m2) {
        if (f.super.a(m2)) {
            this.a(f.super.a(m2.d()));
            return true;
        }
        com.wildtangent.brandboost.util.b.d("Cant create close button. Invalid hover properties.");
        f.super.o();
        return false;
    }

    static /* synthetic */ boolean b(f f2) {
        return f2.l;
    }

    static /* synthetic */ void c(f f2) {
        f2.j();
    }

    static /* synthetic */ void d(f f2) {
        f2.m();
    }

    static /* synthetic */ boolean e(f f2) {
        return f2.l();
    }

    static /* synthetic */ String f() {
        return c;
    }

    static /* synthetic */ void f(f f2) {
        f2.i();
    }

    static /* synthetic */ l g(f f2) {
        return f2.h;
    }

    private void g() {
        ViewGroup viewGroup = (ViewGroup)this.i.getWindow().getDecorView().findViewById(16908290);
        if (viewGroup != null) {
            AnimationSet animationSet = b.a(viewGroup.getWidth(), viewGroup.getHeight(), this.o, this.e);
            animationSet.setAnimationListener(this.b);
            this.startAnimation((Animation)animationSet);
            return;
        }
        com.wildtangent.brandboost.util.b.d(c, "No viewgroup, translate animation not performed.");
    }

    static /* synthetic */ void h(f f2) {
        f2.s();
    }

    private boolean h() {
        if (this.m != null) {
            if (this.d != null) {
                if (this.d.indexOfChild((View)this.m) != -1) {
                    if (this.h != null) {
                        if (this.m.indexOfChild((View)this.h) != -1) {
                            return true;
                        }
                        com.wildtangent.brandboost.util.b.a(c, "isCloseButtonAdded, _buttonClose not child of _closeRelativeLayout");
                        return false;
                    }
                    com.wildtangent.brandboost.util.b.a(c, "isCloseButtonAdded, null _buttonClose");
                    return false;
                }
                com.wildtangent.brandboost.util.b.a(c, "isCloseButtonAdded, _closeRelativeLayout not child of _hoverWebView");
                return false;
            }
            com.wildtangent.brandboost.util.b.a(c, "isCloseButtonAdded, null _hoverWebView");
            return false;
        }
        com.wildtangent.brandboost.util.b.a(c, "isCloseButtonAdded, null _closeRelativeLayout");
        return false;
    }

    private void i() {
        if (!this.h()) {
            this.m = new RelativeLayout((Context)this.i);
            this.m.setId(this.q.nextInt());
            this.m.setBackgroundColor(0);
            this.n = new RelativeLayout.LayoutParams(-1, -1);
            this.d.addView((View)this.m, (ViewGroup.LayoutParams)this.n);
            com.wildtangent.brandboost.util.b.a(c, "pre-convert close (" + this.f + ", " + this.g + ")");
            float f2 = com.wildtangent.brandboost.util.c.a(this.f, (Context)this.i);
            float f3 = com.wildtangent.brandboost.util.c.a(this.g, (Context)this.i);
            com.wildtangent.brandboost.util.b.a(c, "post-convert close (" + f2 + ", " + f3 + ")");
            float f4 = f2 * 1.4f;
            float f5 = f3 * 1.4f;
            com.wildtangent.brandboost.util.b.a(c, "final close (" + f4 + ", " + f5 + ")");
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)f4, (int)f5);
            layoutParams.addRule(10);
            layoutParams.addRule(11);
            this.m.addView((View)this.h, (ViewGroup.LayoutParams)layoutParams);
            return;
        }
        com.wildtangent.brandboost.util.b.d(c, "close button is already added.");
    }

    static /* synthetic */ void i(f f2) {
        f2.q();
    }

    static /* synthetic */ a j(f f2) {
        return f2.j;
    }

    private void j() {
        RelativeLayout.LayoutParams layoutParams = this.b(this.o);
        this.addView((View)this.d, 0, (ViewGroup.LayoutParams)layoutParams);
    }

    private boolean k() {
        return this.d != null && this.e != null;
    }

    private boolean l() {
        return this.h != null;
    }

    private void m() {
        if (this.j != null) {
            this.j.a();
            return;
        }
        com.wildtangent.brandboost.util.b.a(c, "no callback for onHoverUiReady");
    }

    private void n() {
        if (this.p != null) {
            boolean bl = this.p.cancel(true);
            com.wildtangent.brandboost.util.b.a(c, "close button image task cancel: " + bl);
            return;
        }
        com.wildtangent.brandboost.util.b.d(c, "image load task is not defined.");
    }

    private void o() {
        if (!this.l) {
            com.wildtangent.brandboost.util.b.d(c, "Failed to create hover");
            this.l = true;
            this.b();
            this.p();
            return;
        }
        com.wildtangent.brandboost.util.b.a(c, "Hover already marked as failed. No notification to controller necessary.");
    }

    private void p() {
        if (this.j != null) {
            this.j.b();
        }
    }

    private void q() {
        if (this.l()) {
            if (!this.h()) {
                com.wildtangent.brandboost.util.b.a(c, "Waiting 3000 before displaying close button");
                if (!this.s.sendEmptyMessageDelayed(0, 3000L)) {
                    com.wildtangent.brandboost.util.b.d(c, "Failed to start timer before displaying close button");
                }
                return;
            }
            com.wildtangent.brandboost.util.b.a(c, "onHoverAnimationComplete, close button already added");
            return;
        }
        com.wildtangent.brandboost.util.b.d(c, "Close button not valid. Cannot start animation");
        this.o();
    }

    private void r() {
        if (this.l()) {
            this.h.setEnabled(false);
            return;
        }
        com.wildtangent.brandboost.util.b.d(c, "Cant disable close button.");
    }

    private void s() {
        if (this.l()) {
            this.h.setEnabled(true);
            return;
        }
        com.wildtangent.brandboost.util.b.d(c, "Cant enable close button.");
    }

    public void a(BrandBoostAPI.Position position) {
        this.o = position;
        if (f.super.k() && this.a()) {
            if (this.indexOfChild((View)this.d) != -1) {
                this.removeViewAt(0);
            }
            RelativeLayout.LayoutParams layoutParams = f.super.b(this.o);
            this.addView((View)this.d, 0, (ViewGroup.LayoutParams)layoutParams);
            return;
        }
        com.wildtangent.brandboost.util.b.d(c, "Cant animate hover. No hover properties or hover instance.");
        f.super.o();
    }

    public void a(URL uRL) {
        if (uRL != null) {
            this.p = new com.wildtangent.brandboost.util.a((a.a)this);
            this.p.execute((Object[])new URL[]{uRL});
            return;
        }
        com.wildtangent.brandboost.util.b.d(c, "Invalid URL for close button");
        f.super.o();
    }

    @Override
    public void a(Bitmap[] arrbitmap) {
        if (arrbitmap != null && arrbitmap.length > 0 && arrbitmap[0] != null) {
            Bitmap bitmap = arrbitmap[0];
            this.f = bitmap.getWidth();
            this.g = bitmap.getHeight();
            com.wildtangent.brandboost.util.b.a(c, "Close button image wxh: " + this.f + ", " + this.g);
            this.h = new l((Context)this.i, bitmap);
            this.h.setOnClickListener(this.t);
            f.super.r();
            if (this.a()) {
                f.super.j();
                f.super.m();
            }
            return;
        }
        com.wildtangent.brandboost.util.b.d(c, "Close button image not loaded.");
        f.super.o();
    }

    public boolean a() {
        return this.h != null && this.k;
    }

    public void b() {
        if (this.k()) {
            this.d.stopLoading();
            this.d.setEnabled(false);
        }
        this.n();
        if (this.l()) {
            this.h.a();
            this.h.setEnabled(false);
        }
    }

    public void c() {
        this.j = null;
    }

    public void d() {
        if (this.k() && this.a()) {
            this.g();
            return;
        }
        com.wildtangent.brandboost.util.b.d(c, "Cant animate hover. No hover properties or hover instance.");
        this.o();
    }

    public void e() {
        com.wildtangent.brandboost.util.b.a("Removing close button");
        this.h.setEnabled(false);
        this.h.setVisibility(8);
        this.m.removeView((View)this.h);
    }

    public static interface a {
        public void a();

        public void b();

        public void c();

        public void d();
    }

}


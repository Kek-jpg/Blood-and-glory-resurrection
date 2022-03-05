/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.AlphaAnimation
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.AnimationSet
 *  android.view.animation.Interpolator
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package com.wildtangent.brandboost.b;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import com.wildtangent.brandboost.BrandBoostAPI;
import com.wildtangent.brandboost.b.f;
import com.wildtangent.brandboost.b.m;
import com.wildtangent.brandboost.b.n;
import com.wildtangent.brandboost.g;
import com.wildtangent.brandboost.util.b;
import java.util.ArrayList;
import java.util.List;

public final class j
extends Handler
implements f.a {
    private static final String a = "com.wildtangent.brandboost__" + j.class.getSimpleName();
    private f b = null;
    private Activity c = null;
    private boolean d = false;
    private BrandBoostAPI.Position e = BrandBoostAPI.Position.North;
    private Messenger f = null;
    private g g = null;
    private Animation h = null;
    private boolean i = false;
    private Animation.AnimationListener j;

    public j(Activity activity) {
        this.j = new n((j)this);
        if (activity == null) {
            throw new IllegalArgumentException("Invalid parameters supplied to the hover controller");
        }
        this.c = activity;
        this.g = new g((Context)activity);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Message a(j j2, BrandBoostAPI.Position position, Messenger messenger) {
        if (j2 != null) {
            Message message = Message.obtain((Handler)j2, (int)2);
            if (message != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("KEY_HOVER_POSITION", position.ordinal());
                bundle.putParcelable("BUNDLE_MESSENGER", (Parcelable)messenger);
                message.setData(bundle);
                return message;
            }
            b.d(a, "Invalid msg created for showing hover");
            do {
                return null;
                break;
            } while (true);
        }
        b.d(a, "Invalid HoverController instance.");
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Message a(j j2, m m2, Messenger messenger) {
        if (j2 != null) {
            if (m2 != null) {
                Message message = Message.obtain((Handler)j2, (int)0);
                if (message != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("KEY_HOVER_PROPERTIES", (Parcelable)m2);
                    bundle.putParcelable("BUNDLE_MESSENGER", (Parcelable)messenger);
                    message.setData(bundle);
                    return message;
                }
                b.d(a, "Invalid msg created for showing hover");
                do {
                    return null;
                    break;
                } while (true);
            }
            b.d(a, "Invalid hover properties for WHAT_SHOW msg");
            return null;
        }
        b.d(a, "Invalid HoverController instance.");
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Message a(j j2, m m2, Messenger messenger, boolean bl) {
        if (j2 != null) {
            if (m2 != null) {
                Message message = Message.obtain((Handler)j2, (int)1);
                if (message != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("KEY_HOVER_PROPERTIES", (Parcelable)m2);
                    bundle.putParcelable("BUNDLE_MESSENGER", (Parcelable)messenger);
                    bundle.putBoolean("BUNDLE_DISMISS_NOW", bl);
                    message.setData(bundle);
                    return message;
                }
                b.d(a, "Invalid msg created for hiding hover");
                do {
                    return null;
                    break;
                } while (true);
            }
            b.d(a, "Invalid hover properties for WHAT_HIDE msg");
            return null;
        }
        b.d(a, "Invalid HoverController instance.");
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Message a(j j2, ArrayList<String> arrayList, String string) {
        if (j2 != null) {
            Message message = Message.obtain((Handler)j2, (int)3);
            if (message != null) {
                Bundle bundle = new Bundle();
                bundle.putString("KEY_ANALYTICS_SERVER_HOSTNAME", string);
                bundle.putStringArrayList("KEY_ANALYTICS_URLS", arrayList);
                message.setData(bundle);
                return message;
            }
            b.d(a, "Invalid msg created for initializing analytics");
            do {
                return null;
                break;
            } while (true);
        }
        b.d(a, "Invalid HoverController instance.");
        return null;
    }

    public static j a(Activity activity) {
        return new j(activity);
    }

    private String a(int n2) {
        switch (n2) {
            default: {
                return "";
            }
            case 0: {
                return "SHOW";
            }
            case 1: {
                return "HIDE";
            }
            case 2: {
                return "SET HOVER POSITION";
            }
            case 3: 
        }
        return "INIT ANALYTICS";
    }

    private void a(Message message) {
        if (j.super.h()) {
            if (message != null) {
                Bundle bundle = message.getData();
                if (bundle != null) {
                    this.b.b();
                    if (bundle.getBoolean("BUNDLE_DISMISS_NOW")) {
                        b.a(a, "dismissing immediately");
                        j.super.g();
                        return;
                    }
                    j.super.k();
                    return;
                }
                b.d(a, "dismissing immediately, but had null bundle");
                j.super.g();
                return;
            }
            b.d(a, "dismissing immediately, but had null msg");
            j.super.g();
            return;
        }
        if (this.i) {
            b.a(a, "Hover is loading. Closing...");
            this.b.b();
            return;
        }
        b.d(a, "Hide msg ignored. Hover is not displayed");
    }

    static /* synthetic */ void a(j j2) {
        j2.g();
    }

    private void a(m m2, BrandBoostAPI.Position position) {
        if (j.super.a(m2)) {
            b.a(a, "Creating new hover instance");
            this.i = true;
            this.b = new f(this.c, m2, position, (f.a)this);
            return;
        }
        b.d(a, "Trying to create a hover without valid properties");
    }

    private boolean a(m m2) {
        if (m2 == null) {
            return false;
        }
        return m2.a();
    }

    private void b(Message message) {
        if (message != null) {
            Bundle bundle = message.getData();
            if (bundle != null) {
                ArrayList arrayList = bundle.getStringArrayList("KEY_ANALYTICS_URLS");
                String string = bundle.getString("KEY_ANALYTICS_SERVER_HOSTNAME");
                this.g.b(string);
                this.g.a((List<String>)arrayList);
                return;
            }
            b.d(a, "No bundle to init analytics");
            return;
        }
        b.d(a, "No msg to init anlytics");
    }

    private void c(Message message) {
        if (message != null) {
            Bundle bundle = message.getData();
            if (bundle != null) {
                this.f = (Messenger)bundle.getParcelable("BUNDLE_MESSENGER");
                int n2 = bundle.getInt("KEY_HOVER_POSITION");
                if (n2 >= 0 && n2 < BrandBoostAPI.Position.values().length) {
                    this.e = BrandBoostAPI.Position.values()[n2];
                    b.a(a, "hover position set to: " + (Object)((Object)this.e));
                    if (this.d) {
                        b.a(a, "hover is already displayed. Updating position. " + (Object)((Object)this.e));
                        this.b.a(this.e);
                        return;
                    }
                    b.b(a, "Hover not displayed, position not updated.");
                    return;
                }
                b.b(a, "Invalid hover position: " + n2);
                return;
            }
            b.d(a, "No bundle to set hover position");
            return;
        }
        b.d(a, "No msg to set hover position");
    }

    private void d(Message message) {
        Bundle bundle = message.getData();
        if (bundle != null) {
            this.f = (Messenger)bundle.getParcelable("BUNDLE_MESSENGER");
            m m2 = (m)bundle.getParcelable("KEY_HOVER_PROPERTIES");
            if (m2 != null) {
                b.a(a, m2.toString());
                if (!j.super.h()) {
                    if (j.super.a(m2)) {
                        j.super.a(m2, this.e);
                        return;
                    }
                    b.d(a, "Trying to create a hover without valid properties");
                    return;
                }
                if (j.super.i()) {
                    b.d(a, "Hover is being hidden. Show pending...");
                    j.super.j();
                    if (j.super.a(m2)) {
                        j.super.a(m2, this.e);
                        return;
                    }
                    b.d(a, "Trying to create a hover without valid properties");
                    return;
                }
                b.d(a, "Hover is already displayed. Show Hover ignored.");
                return;
            }
            b.d(a, "No hover properties when attempting to show the hover");
            return;
        }
        b.d(a, "No bundle when attempting to show the hover");
    }

    static /* synthetic */ String e() {
        return a;
    }

    private void f() {
        b.a(a, "showHover");
        ViewGroup viewGroup = (ViewGroup)this.c.getWindow().getDecorView().findViewById(16908290);
        if (viewGroup != null) {
            this.d = true;
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
            viewGroup.addView((View)this.b, layoutParams);
            this.b.d();
            this.g.b(g.a.d);
            return;
        }
        b.d(a, "Invalid view group. Cant add hover to display.");
    }

    /*
     * Enabled aggressive block sorting
     */
    private void g() {
        b.a(a, "hideHover");
        this.b.setVisibility(8);
        ViewGroup viewGroup = (ViewGroup)this.c.getWindow().getDecorView().findViewById(16908290);
        if (viewGroup != null) {
            viewGroup.removeView((View)this.b);
        } else {
            b.d(a, "Invalid view group. Cant remove hover.");
        }
        this.d = false;
        this.g.b(g.a.e);
    }

    private boolean h() {
        return this.b != null && this.d;
    }

    private boolean i() {
        return this.h != null && this.h.hasStarted() && !this.h.hasEnded();
    }

    private void j() {
        if (this.h != null) {
            b.a(a, "Canceling hover fade out animation.");
            this.h.cancel();
            return;
        }
        b.d(a, "Cant stop hover fadeout. No fadeout animation created.");
    }

    private void k() {
        if (this.b != null) {
            this.h = new AlphaAnimation(1.0f, 0.0f);
            this.h.setInterpolator((Interpolator)new AccelerateInterpolator());
            this.h.setDuration(700L);
            AnimationSet animationSet = new AnimationSet(false);
            animationSet.addAnimation(this.h);
            animationSet.setAnimationListener(this.j);
            this.b.startAnimation((Animation)animationSet);
            return;
        }
        b.d(a, "Not fading out hover. The hover is null");
    }

    private void l() {
        if (this.f != null) {
            Message message = Message.obtain();
            message.what = 100;
            try {
                this.f.send(message);
                return;
            }
            catch (RemoteException remoteException) {
                b.d(a, "Cant tell BrandBoost that hover was tapped. " + (Object)((Object)remoteException));
                return;
            }
        }
        b.d(a, "No messenger set. Cant tell BrandBoost that hover was tapped.");
    }

    private void m() {
        if (this.f != null) {
            Message message = Message.obtain();
            message.what = 101;
            try {
                this.f.send(message);
                return;
            }
            catch (RemoteException remoteException) {
                b.d(a, "Cant tell BrandBoost that hover was closed. " + (Object)((Object)remoteException));
                return;
            }
        }
        b.d(a, "No messenger set. Cant tell BrandBoost that hover was closed.");
    }

    private void n() {
        if (this.f != null) {
            Message message = Message.obtain();
            message.what = 102;
            try {
                this.f.send(message);
                return;
            }
            catch (RemoteException remoteException) {
                b.d(a, "Cant tell BrandBoost that hover failed. " + (Object)((Object)remoteException));
                return;
            }
        }
        b.d(a, "No messenger set. Cant tell BrandBoost that hover failed.");
    }

    @Override
    public void a() {
        b.a(a, "Hover UI ready");
        this.i = false;
        this.f();
    }

    @Override
    public void b() {
        b.d(a, "Hover UI failed to be created.");
        this.d = false;
        this.i = false;
        this.n();
    }

    @Override
    public void c() {
        b.c(a, "Hover closed button pressed");
        this.b.c();
        this.b.e();
        this.k();
        this.m();
    }

    @Override
    public void d() {
        b.c(a, "Hover webview pressed");
        this.b.c();
        this.b.b();
        this.k();
        this.l();
    }

    public void handleMessage(Message message) {
        if (message != null) {
            b.a(a, "Rcvd what: " + message.what + " (" + j.super.a(message.what) + ")");
            switch (message.what) {
                default: {
                    b.d(a, "Invalid msg received on hover controller. " + message.what);
                    return;
                }
                case 0: {
                    j.super.d(message);
                    return;
                }
                case 1: {
                    j.super.a(message);
                    return;
                }
                case 2: {
                    j.super.c(message);
                    return;
                }
                case 3: 
            }
            j.super.b(message);
            return;
        }
        b.b(a, "null message");
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  com.wildtangent.brandboost.BrandBoostCallbacks
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 */
package com.wildtangent.brandboost;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import com.wildtangent.brandboost.BrandBoostAPI;
import com.wildtangent.brandboost.BrandBoostActivity;
import com.wildtangent.brandboost.BrandBoostCallbacks;
import com.wildtangent.brandboost.BrandBoostCallbacksWithVerification;
import com.wildtangent.brandboost.GameSpecification;
import com.wildtangent.brandboost.a;
import com.wildtangent.brandboost.b.m;
import com.wildtangent.brandboost.e;
import com.wildtangent.brandboost.f;
import com.wildtangent.brandboost.g;
import com.wildtangent.brandboost.i;
import com.wildtangent.brandboost.j;
import com.wildtangent.brandboost.k;
import com.wildtangent.brandboost.o;
import com.wildtangent.brandboost.p;
import com.wildtangent.brandboost.q;
import com.wildtangent.brandboost.s;
import com.wildtangent.brandboost.util.b;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class BrandBoost
implements BrandBoostAPI {
    private static final String a = "com.wildtangent.brandboost__" + BrandBoost.class.getSimpleName();
    private k b = null;
    private String c = null;
    private Activity d;
    private o e;
    private p f;
    private GameSpecification g;
    private String h;
    private boolean i;
    private BrandBoostCallbacks j;
    private a.a k;
    private com.wildtangent.brandboost.m l;
    private m m;
    private com.wildtangent.brandboost.b.j n;
    private g o;
    private boolean p;
    private boolean q;
    private boolean r;
    private Messenger s;
    private Messenger t;
    private Messenger u;
    private Handler v;
    private Handler w;
    private Handler x;
    private Handler y;

    public BrandBoost(Activity activity, GameSpecification gameSpecification, String string, boolean bl, BrandBoostCallbacks brandBoostCallbacks) {
        this(activity, gameSpecification, string, bl, brandBoostCallbacks, new o((Context)activity, gameSpecification), new p((Context)activity), new g((Context)activity));
    }

    BrandBoost(Activity activity, GameSpecification gameSpecification, String string, boolean bl, BrandBoostCallbacks brandBoostCallbacks, o o2, p p2, g g2) {
        this.k = new a((BrandBoost)this, null);
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = false;
        this.q = false;
        this.r = false;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = new j((BrandBoost)this);
        this.w = new i((BrandBoost)this);
        this.x = new f((BrandBoost)this);
        this.y = new e((BrandBoost)this);
        BrandBoost.super.a((Context)activity);
        b.a(a, "Instantiating BB");
        this.d = activity;
        this.g = gameSpecification;
        this.h = string;
        this.j = brandBoostCallbacks;
        this.i = bl;
        this.e = o2;
        this.f = p2;
        this.t = new Messenger(this.w);
        this.u = new Messenger(this.v);
        this.o = g2;
        this.p = this.j instanceof BrandBoostCallbacksWithVerification;
        this.n = com.wildtangent.brandboost.b.j.a(activity);
        BrandBoost.super.b();
    }

    static /* synthetic */ Messenger a(Message message) {
        return BrandBoost.b(message);
    }

    static /* synthetic */ Messenger a(BrandBoost brandBoost, Messenger messenger) {
        brandBoost.s = messenger;
        return messenger;
    }

    static /* synthetic */ BrandBoostCallbacks a(BrandBoost brandBoost) {
        return brandBoost.j;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(int n2) {
        if (this.x.hasMessages(0)) {
            b.a(a, "Already has queued up campaign check");
            BrandBoost.super.m();
        } else {
            b.a(a, "No queued campaign check requests.");
        }
        if (!this.x.sendEmptyMessageDelayed(0, (long)n2)) {
            b.d(a, "Cant check for campaign, usually because the looper processing the message queue is exiting.");
            return;
        }
        b.a(a, "Checking for campaign again in " + n2 + " ms");
    }

    private void a(int n2, Bundle bundle) {
        if (this.s != null) {
            Message message = Message.obtain(null, (int)n2);
            if (bundle != null) {
                message.setData(bundle);
            }
            try {
                this.s.send(message);
                return;
            }
            catch (RemoteException remoteException) {
                b.c(a, "Can't send itemGranted. Failed on send.", remoteException);
                return;
            }
        }
        b.d(a, "Can't send message.what " + n2 + ". Invalid BrandBoostActivity Messenger object");
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(int n2, String string, Bundle bundle, Throwable throwable) {
        boolean bl = true;
        if (n2 == 200 && string != null && throwable == null) {
            this.l = com.wildtangent.brandboost.m.a(string);
            if (this.l != null) {
                BrandBoost.super.a(this.l);
                this.o.b(g.a.a);
                this.m = m.a(string);
                BrandBoost.super.a(this.l.c());
                return;
            }
            BrandBoost.super.c();
        } else {
            b.d(a, "handleAvailableCampaignsResponse, HTTP response code: " + n2 + " JSON: " + string + " exception: " + (Object)throwable);
            BrandBoost.super.a(com.wildtangent.brandboost.m.b(this.l));
        }
        if (!bl) return;
        BrandBoost.super.h();
    }

    private void a(Context context) throws IllegalArgumentException {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            if (packageManager.checkPermission("android.permission.INTERNET", context.getPackageName()) == -1) {
                throw new IllegalArgumentException("BrandBoost failed to be initialized. Please check that your AndroidManifest.xml has the permission enabled for android.permission.INTERNET");
            }
            if (packageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) == -1) {
                throw new IllegalArgumentException("BrandBoost failed to be initialized. Please check that your AndroidManifest.xml has the permission enabled for android.permission.ACCESS_NETWORK_STATE");
            }
        }
    }

    static /* synthetic */ void a(BrandBoost brandBoost, int n2) {
        brandBoost.a(n2);
    }

    private void a(BrandBoostCallbacks.ClosedReason closedReason) {
        b.a(a, "calling onBrandBoostClosed(" + (Object)((Object)closedReason) + ")");
        if (this.j != null) {
            this.j.onBrandBoostClosed(closedReason);
            return;
        }
        b.d(a, "Cant call onBrandBoostClosed. No callback registered");
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(k k2) {
        this.b = k2;
        if (this.b != null) {
            b.c(a, "Available campaign: " + this.b.toString());
            this.c = this.b.d();
        } else {
            this.c = null;
        }
        if (this.m != null) {
            this.m.b(this.b.c());
        } else {
            b.d(a, "campaign ready, but no hover properties");
        }
        this.o.b(g.a.c);
    }

    private void a(com.wildtangent.brandboost.m m2) {
        this.o.a(this.l.a());
        this.o.b(this.l.b());
        BrandBoost.super.b(m2);
    }

    private void a(s s2) {
        b.a("Got item ready message");
        if (this.j != null) {
            this.j.onBrandBoostItemReady(s2.b());
            return;
        }
        b.b("Can't invoke onBrandBoostItemReady. Invalid callback interface.");
    }

    private void a(String string) {
        this.o.b(g.a.b);
        this.f.a(string, this.k);
    }

    static /* synthetic */ boolean a(BrandBoost brandBoost, Message message) {
        return brandBoost.c(message);
    }

    private static Messenger b(Message message) {
        Messenger messenger = (Messenger)message.getData().getParcelable("BUNDLE_MESSENGER");
        if (messenger == null) {
            b.b(a, "Couldn't find BB activity messenger in message!");
        }
        return messenger;
    }

    static /* synthetic */ com.wildtangent.brandboost.m b(BrandBoost brandBoost) {
        return brandBoost.l;
    }

    private void b() {
        this.e.a(this.h, this.k);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void b(int n2) {
        if (this.y.hasMessages(1)) {
            b.a(a, "Already has queued up AutoHover delay");
            BrandBoost.super.n();
        } else {
            b.a(a, "No queued hover delays.");
        }
        if (!this.y.sendEmptyMessageDelayed(1, (long)n2)) {
            b.d(a, "Cant delay autohover, usually because the looper processing the message queue is exiting.");
            return;
        }
        b.a(a, "AutoHover cannot be shown again for " + n2 + " ms");
    }

    private void b(int n2, String string, Bundle bundle, Throwable throwable) {
        if (n2 == 200 && string != null && throwable == null) {
            b.a(a, "Handling verification response, " + string);
            s s2 = s.a(string);
            if (s2 != null) {
                if (s2.a(this.g, this.c, this.h)) {
                    BrandBoost.super.a(s2);
                    return;
                }
                b.b(a, "Verification response didn't match expectations. Expected: " + this.g + "," + this.c + "," + this.h + ". Actual: " + s2.a() + "," + s2.b() + "," + s2.c());
                BrandBoost.super.i();
                return;
            }
            b.b(a, "Invalid verification response");
            BrandBoost.super.i();
            return;
        }
        b.b(a, "Invalid http status, JSON, or exception on verification: " + n2 + "," + string + " exception: " + (Object)throwable);
        BrandBoost.super.i();
    }

    static /* synthetic */ void b(BrandBoost brandBoost, int n2) {
        brandBoost.b(n2);
    }

    static /* synthetic */ void b(BrandBoost brandBoost, Message message) {
        brandBoost.e(message);
    }

    private void b(com.wildtangent.brandboost.m m2) {
        Message message = com.wildtangent.brandboost.b.j.a(this.n, (ArrayList<String>)new ArrayList(this.l.a()), this.l.b());
        if (message != null) {
            message.sendToTarget();
        }
    }

    private void b(String string) {
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_ITEM_KEY", string);
        BrandBoost.super.a(1, bundle);
    }

    private void c() {
        this.a(com.wildtangent.brandboost.m.a(this.l));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void c(int n2, String string, Bundle bundle, Throwable throwable) {
        if (n2 == 200 && string != null && throwable == null) {
            q q2 = q.a(string);
            if (this.l != null && q2 != null) {
                k k2 = this.l.a(q2);
                if (k2 != null) {
                    BrandBoost.super.a(k2);
                } else {
                    b.d(a, "No campaign available. Checking again in " + this.l.d() + " ms");
                    BrandBoost.super.c();
                }
            } else {
                b.a(a, "No campaigns or response from ad server. Checking again in 3600000 ms");
                BrandBoost.super.c();
            }
        } else {
            b.a(a, "Invalid ad server response. Checking again in 30000 ms httpResponseCode: " + n2 + " json: " + string + " possibleFailureReason: " + (Object)throwable);
            BrandBoost.super.a(30000);
        }
        BrandBoost.super.h();
    }

    static /* synthetic */ void c(BrandBoost brandBoost) {
        brandBoost.b();
    }

    static /* synthetic */ void c(BrandBoost brandBoost, Message message) {
        brandBoost.d(message);
    }

    private void c(String string) {
        b.a(a, "Asking game to do verification: " + string);
        try {
            ((BrandBoostCallbacksWithVerification)this.j).verifyVexCode(string);
            return;
        }
        catch (Throwable throwable) {
            b.a(a, "Game verification failed", throwable);
            BrandBoost.super.i();
            return;
        }
    }

    private boolean c(Message message) {
        return message != null && message.getData() != null;
    }

    private void d() {
        int n2 = com.wildtangent.brandboost.m.b(this.l);
        b.c(a, "App refused the campaign. Rechecking in " + n2 + " ms");
        this.a(n2);
        this.e();
    }

    private void d(Message message) {
        if (BrandBoost.super.c(message)) {
            int n2 = message.getData().getInt("BUNDLE_CLOSE_REASON");
            if (n2 >= 0 && n2 < BrandBoostCallbacks.ClosedReason.values().length) {
                BrandBoost.super.e();
                BrandBoostCallbacks.ClosedReason closedReason = BrandBoostCallbacks.ClosedReason.values()[n2];
                BrandBoost.super.a(closedReason);
                if (BrandBoostCallbacks.ClosedReason.Success == closedReason) {
                    BrandBoost.super.a(this.l.f());
                    BrandBoost.super.b(com.wildtangent.brandboost.m.c(this.l));
                    return;
                }
                BrandBoost.super.a(0);
                BrandBoost.super.b(com.wildtangent.brandboost.m.c(this.l));
                return;
            }
            b.d(a, "Invalid closed reason: " + n2);
            return;
        }
        b.d(a, "Cant handle Interstitial Done message form BrandBoostActivity. invalid msg");
    }

    static /* synthetic */ boolean d(BrandBoost brandBoost) {
        return brandBoost.f();
    }

    private void e() {
        this.c = null;
        this.b = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void e(Message message) {
        b.a(a, "Invoking verification");
        Bundle bundle = message != null ? message.getData() : null;
        boolean bl = false;
        if (bundle != null) {
            String string = bundle.getString("BUNDLE_VEXCODE");
            if (!com.wildtangent.brandboost.util.g.a(string)) {
                bl = true;
                if (!this.p) {
                    this.e.b(string, this.k);
                } else {
                    BrandBoost.super.c(string);
                }
            } else {
                b.d(a, "no VEX code present from Javascript");
                bl = false;
            }
        }
        if (!bl) {
            b.b(a, "No VEX code in the message from the BB Activity");
        }
    }

    static /* synthetic */ void e(BrandBoost brandBoost) {
        brandBoost.k();
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean f() {
        String string = a;
        StringBuilder stringBuilder = new StringBuilder().append("canShowAutoHover, Campaign Avail: ");
        boolean bl = this.b != null;
        b.a(string, stringBuilder.append(bl).append(" Campaign Accepted: ").append(this.r).append(", AutoHover: ").append(this.i).append(", Delayed: ").append(this.o()).toString());
        return this.b != null && this.r && this.i && !this.o();
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean g() {
        String string = a;
        StringBuilder stringBuilder = new StringBuilder().append("canShowHover, Campaign Avail: ");
        boolean bl = this.b != null;
        b.a(string, stringBuilder.append(bl).append(" Campaign Accepted: ").append(this.r).toString());
        return this.b != null && this.r;
    }

    private void h() {
        block5 : {
            if (this.j != null) {
                try {
                    this.r = this.j.onBrandBoostCampaignReady(this.c);
                    b.a(a, "Campaign accepted by app: " + this.r);
                    if (this.f()) {
                        this.k();
                        return;
                    }
                    b.a(a, "cannot currently show autohover");
                    if (!this.r && this.b != null) {
                        this.d();
                        return;
                    }
                    break block5;
                }
                catch (Throwable throwable) {
                    b.a(a, "onBrandBoostCampaignReady failed", throwable);
                    return;
                }
            }
            b.d(a, "cant send status update. null callback");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void i() {
        Bundle bundle = new Bundle();
        String string = "";
        if (this.b != null) {
            string = this.b.d();
        } else {
            b.d(a, "dont have item key to give to JS");
        }
        bundle.putString("BUNDLE_ITEM_KEY", string);
        this.a(4, bundle);
    }

    private void j() {
        if (this.s != null) {
            Message message = Message.obtain();
            if (message != null) {
                message.what = 3;
                try {
                    this.s.send(message);
                    return;
                }
                catch (RemoteException remoteException) {
                    b.c(a, "Can't send closeIntersititial. Failed on send.", remoteException);
                    return;
                }
            }
            b.d(a, "Can't send closeIntersititial. Invalid msg.");
            return;
        }
        b.d(a, "Can't send closeIntersititial. Invalid BrandBoostActivity Messenger object");
    }

    private void k() {
        if (this.d != null && this.n != null && this.m != null && this.i) {
            Message message = com.wildtangent.brandboost.b.j.a(this.n, this.m, this.u);
            if (message != null) {
                message.sendToTarget();
                return;
            }
            b.d(a, "No message created to show hover");
            return;
        }
        b.d("Activity instance is invalid or autohover is not enabled.");
    }

    private void l() {
        Message message = com.wildtangent.brandboost.b.j.a(this.n, this.m, this.u, true);
        if (message != null) {
            message.sendToTarget();
            return;
        }
        b.d(a, "Hide hover msg not sent to HoverController. msg null");
    }

    private void m() {
        b.a(a, "Removing any existing queued up delayed campaign check.");
        this.x.removeMessages(0);
    }

    private void n() {
        b.a(a, "Removing any existing queued up delayed autohover display.");
        this.y.removeMessages(1);
    }

    private boolean o() {
        return this.y.hasMessages(1);
    }

    private boolean p() {
        return this.r;
    }

    static void setOwnership(String string) {
        b.a(a, "Setting owner to " + string);
        g.a(string);
    }

    @Override
    public boolean isCampaignAvailable() {
        if (!this.q) {
            k k2 = this.b;
            boolean bl = false;
            if (k2 != null) {
                bl = true;
            }
            return bl;
        }
        b.b(a, "BrandBoost shutdown. Campaigns no longer available. Please create a new BrandBoost object");
        return false;
    }

    @Override
    public void itemGranted(String string) {
        if (!this.q) {
            if (com.wildtangent.brandboost.util.g.a(string)) {
                b.b(a, "*** Note: itemGranted has been passed a null itemKey, skipping the end page.");
                BrandBoost.super.j();
                return;
            }
            BrandBoost.super.b(string);
            return;
        }
        b.b(a, "BrandBoost shutdown. itemGranted not performed. Please create a new BrandBoost object");
    }

    @Override
    public void launch() {
        if (!this.q) {
            if (this.isCampaignAvailable()) {
                if (this.p()) {
                    b.c(a, "Launching");
                    BrandBoostActivity.a((Context)this.d, this.b.b(), this.t);
                    return;
                }
                b.a(a, "launch ignored. App did not accept the campaign.");
                return;
            }
            b.b(a, "Asked to launch, but no campaign is available");
            return;
        }
        b.b(a, "BrandBoost shutdown. launch is not performed. Please create a new BrandBoost object");
    }

    @Override
    public String retrieveItemKey() {
        if (!this.q) {
            if (this.b != null) {
                return this.b.d();
            }
        } else {
            b.b(a, "BrandBoost shutdown. retrieveItemKey returning null. Please create a new BrandBoost object");
        }
        return null;
    }

    @Override
    public void setAutoHover(boolean bl) {
        b.a(a, "setAutoHover, autoHoverEnabled: " + bl);
        if (!this.q) {
            this.i = bl;
            if (!this.i) {
                BrandBoost.super.l();
                return;
            }
            if (BrandBoost.super.f()) {
                BrandBoost.super.k();
                return;
            }
            b.a(a, "setAutoHover, cannot currently show autohover");
            return;
        }
        b.b(a, "BrandBoost shutdown. setAutoHover not performed. Please create a new BrandBoost object");
    }

    void setDp(String string) {
        b.a(a, "Setting dp to " + string);
        BrandBoostActivity.a = string;
    }

    @Override
    public void setHoverPosition(BrandBoostAPI.Position position) {
        if (!this.q) {
            if (this.n != null) {
                Message message = com.wildtangent.brandboost.b.j.a(this.n, position, this.u);
                if (message != null) {
                    message.sendToTarget();
                    return;
                }
                b.d(a, "No message created to set hover position");
                return;
            }
            b.d("No hover controller to set hover position");
            return;
        }
        b.b(a, "BrandBoost shutdown. setHoverPosition not performed. Please create a new BrandBoost object");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void showHover(boolean bl) {
        Message message;
        b.a(a, "showHover, shouldShowHover: " + bl);
        if (this.q) {
            b.b(a, "BrandBoost shutdown. showHover is not performed. Please create a new BrandBoost object");
            return;
        }
        if (bl) {
            if (BrandBoost.super.g()) {
                message = com.wildtangent.brandboost.b.j.a(this.n, this.m, this.u);
            } else {
                b.a(a, "Cannot currently show hover");
                message = null;
            }
        } else {
            message = com.wildtangent.brandboost.b.j.a(this.n, this.m, this.u, true);
        }
        if (message != null) {
            message.sendToTarget();
            return;
        }
        b.d(a, "showHover(" + bl + ") msg not sent to HoverController. msg null");
    }

    @Override
    public void shutdown() {
        b.a(a, "Shutting down BB");
        this.q = true;
        this.j = null;
        this.m();
        this.j();
        this.l();
        this.n();
    }

    private class a
    implements a.a {
        final /* synthetic */ BrandBoost a;

        private a(BrandBoost brandBoost) {
            this.a = brandBoost;
        }

        /* synthetic */ a(BrandBoost brandBoost, j j2) {
            super(brandBoost);
        }

        @Override
        public void a(int n2, int n3, String string, Bundle bundle, Throwable throwable) {
            b.a(a, "Received callback message, what: " + n2);
            switch (n2) {
                default: {
                    b.b(a, "Unknown message, what: " + n2);
                    return;
                }
                case 1: {
                    this.a.a(n3, string, bundle, throwable);
                    return;
                }
                case 2: {
                    this.a.c(n3, string, bundle, throwable);
                    return;
                }
                case 3: 
            }
            this.a.b(n3, string, bundle, throwable);
        }
    }

}


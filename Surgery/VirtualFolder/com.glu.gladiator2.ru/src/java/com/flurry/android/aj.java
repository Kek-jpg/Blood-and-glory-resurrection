/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnCompletionListener
 *  android.media.MediaPlayer$OnErrorListener
 *  android.media.MediaPlayer$OnPreparedListener
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.webkit.WebView
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.ClassCastException
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Collections
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import com.flurry.android.AdFrame;
import com.flurry.android.AdSpaceLayout;
import com.flurry.android.AdUnit;
import com.flurry.android.ai;
import com.flurry.android.al;
import com.flurry.android.ao;
import com.flurry.android.at;
import com.flurry.android.ba;
import com.flurry.android.bb;
import com.flurry.android.bd;
import com.flurry.android.be;
import com.flurry.android.o;
import com.flurry.android.t;
import com.flurry.android.u;
import com.flurry.android.w;
import com.flurry.android.y;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class aj
extends o
implements MediaPlayer.OnCompletionListener,
MediaPlayer.OnErrorListener,
MediaPlayer.OnPreparedListener,
ai {
    private final String e;
    private ProgressDialog f;
    private at g;
    private WebView h;
    private int i;
    private AdUnit j;
    private int k;
    private bb l;
    private List<AdFrame> m;
    private boolean n;
    private Map<String, AdUnit> o;
    private Map<String, bb> p;
    private Context q;
    private be r;
    private w s;

    /*
     * Enabled aggressive block sorting
     */
    aj(Context context, be be2, bb bb2, AdUnit adUnit) {
        int n2 = 1;
        super(context, be2, bb2);
        this.e = this.getClass().getSimpleName();
        this.q = context;
        this.j = adUnit;
        this.k = 0;
        this.l = bb2;
        this.m = this.j.c();
        if (this.j.d() != n2) {
            n2 = 0;
        }
        this.n = n2;
        if (this.n) {
            this.p = new HashMap();
            this.o = new HashMap();
            this.p.put((Object)bb2.b(), (Object)bb2);
            this.o.put((Object)((AdFrame)adUnit.c().get(0)).f().toString(), (Object)adUnit);
        }
        this.r = be2;
        this.s = this.r.c;
        this.c = this.j;
        this.b = this.l;
    }

    static /* synthetic */ WebView a(aj aj2) {
        return aj2.h;
    }

    static /* synthetic */ AdUnit a(aj aj2, String string) {
        if (aj2.o == null) {
            return null;
        }
        return (AdUnit)aj2.o.get((Object)string);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private bb a(String string) {
        if (this.p == null) {
            return null;
        }
        bb bb2 = (bb)this.p.get((Object)string);
        if (bb2 != null) return bb2;
        bb bb3 = y.a(this.r, string);
        this.p.put((Object)string, (Object)bb3);
        return bb3;
    }

    private static String a(List<AdUnit> list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'{\"adComponents\":[");
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(y.f(((AdFrame)((AdUnit)iterator.next()).c().get(0)).c().toString()));
            if (!iterator.hasNext()) continue;
            stringBuilder.append(",");
        }
        stringBuilder.append("]}'");
        return stringBuilder.toString();
    }

    private List<AdUnit> a(int n2, int n3) {
        List<AdUnit> list = this.s.a(this.j.a().toString(), n3);
        for (AdUnit adUnit : list) {
            if (adUnit.c().size() <= 0) continue;
            this.o.put((Object)((AdFrame)adUnit.c().get(0)).f().toString(), (Object)adUnit);
        }
        return list;
    }

    static /* synthetic */ void a(aj aj2, String string, Map map, AdUnit adUnit, bb bb2, int n2, int n3) {
        aj2.a(string, (Map<String, String>)map, adUnit, bb2, n2, n3);
    }

    private void a(String string, Map<String, String> map, AdUnit adUnit, bb bb2, int n2, int n3) {
        this.a.a(new ba(string, this.q, adUnit, bb2, n2), this, n3);
    }

    static /* synthetic */ bb b(aj aj2, String string) {
        return aj2.a(string);
    }

    static /* synthetic */ boolean b(aj aj2) {
        return aj2.n;
    }

    static /* synthetic */ AdUnit c(aj aj2) {
        return aj2.j;
    }

    static /* synthetic */ bb d(aj aj2) {
        return aj2.l;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void d() {
        ViewParent viewParent;
        if (this.c().equals((Object)"takeover")) {
            try {
                ((Activity)this.q).finish();
                this.a("adClosed", (Map<String, String>)Collections.emptyMap(), this.j, this.l, this.k, 0);
                return;
            }
            catch (ClassCastException classCastException) {
                "caught class cast exception: " + (Object)((Object)classCastException);
                return;
            }
        }
        ViewParent viewParent2 = this.getParent();
        if (viewParent2 == null || (viewParent = viewParent2.getParent()) == null) return;
        try {
            ViewGroup viewGroup = (ViewGroup)viewParent;
            be be2 = this.r;
            Context context = this.q;
            this.j.a().toString();
            be2.a(context, viewGroup);
            return;
        }
        catch (ClassCastException classCastException) {
            "failed to remove view from holder: " + classCastException.getMessage();
            return;
        }
    }

    private int e() {
        return ((AdFrame)this.m.get(this.k)).a();
    }

    static /* synthetic */ int e(aj aj2) {
        return aj2.k;
    }

    private String f() {
        return ((AdFrame)this.m.get(this.k)).b().toString();
    }

    static /* synthetic */ String f(aj aj2) {
        return aj2.e;
    }

    static /* synthetic */ Context g(aj aj2) {
        return aj2.q;
    }

    private AdFrame g() {
        return (AdFrame)this.m.get(this.k);
    }

    final void a() {
        if (this.e() == 3) {
            if (this.f != null && this.f.isShowing()) {
                this.f.dismiss();
            }
            if (this.g != null && this.g.isPlaying()) {
                this.g.stopPlayback();
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final void a(u var1, be var2_3, int var3_2) {
        block24 : {
            block23 : {
                var4_4 = var1.a;
                var5_5 = var1.c;
                var6_6 = var1.b;
                if (var3_2 > 10) {
                    "Maximum depth for event/action loop exceeded when performing action:" + var4_4 + "," + var6_6 + ",triggered by:" + var5_5.a;
                    return;
                }
                "performAction(" + var4_4 + "...)";
                if (var4_4.equals((Object)"nextAdUnit")) {
                    try {
                        var36_7 = (t)this.getParent();
                        if (var36_7 == null) return;
                        try {
                            var38_9 = var40_8 = Integer.parseInt((String)((String)var1.b.get((Object)"delay")));
                        }
                        catch (NumberFormatException var37_12) {
                            var38_9 = 0;
                        }
                    }
                    catch (ClassCastException var34_11) {
                        var34_11.toString();
                        aj.super.d();
                        return;
                    }
                    var39_10 = var38_9 * 1000;
                    var36_7.a(var39_10);
                    this.a.d.a(var36_7);
                    return;
                }
                if (!var4_4.equals((Object)"nextFrame")) break block24;
                var28_13 = 1 + this.k;
                var29_14 = (String)var6_6.get((Object)"offset");
                if (var29_14 == null) ** GOTO lbl41
                if (var29_14.equals((Object)"next")) {
                    var30_15 = 1 + this.k;
                } else if (var29_14.equals((Object)"current")) {
                    var30_15 = this.k;
                } else {
                    try {
                        var30_15 = var33_16 = Integer.parseInt((String)var29_14);
                        break block23;
                    }
                    catch (NumberFormatException var31_17) {
                        "caught: " + var31_17.getMessage();
                    }
lbl41: // 2 sources:
                    var30_15 = var28_13;
                }
            }
            if (var30_15 == this.k) return;
            if (var30_15 >= this.m.size()) return;
            this.k = var30_15;
            this.initLayout(this.q);
            return;
        }
        if (var4_4.equals((Object)"closeAd")) {
            aj.super.d();
            return;
        }
        if (var4_4.equals((Object)"notifyUser")) {
            var8_18 = new AlertDialog.Builder(this.q);
            var9_19 = var6_6.containsKey((Object)"message") != false ? (String)var6_6.get((Object)"message") : "Are you sure?";
            var10_20 = var6_6.containsKey((Object)"confirmDisplay") != false ? (String)var6_6.get((Object)"confirmDisplay") : "Yes";
            var11_21 = var6_6.containsKey((Object)"cancelDisplay") != false ? (String)var6_6.get((Object)"cancelDisplay") : "No";
            var8_18.setMessage((CharSequence)var9_19).setCancelable(false).setPositiveButton((CharSequence)var10_20, (DialogInterface.OnClickListener)new ao((aj)this, var5_5, var3_2)).setNegativeButton((CharSequence)var11_21, (DialogInterface.OnClickListener)new al((aj)this, var5_5, var3_2));
            var8_18.create().show();
            return;
        }
        if (var4_4.equals((Object)"logEvent")) {
            var25_22 = var6_6.containsKey((Object)"__sendToServer") != false && ((String)var6_6.get((Object)"__sendToServer")).equals((Object)"true") != false;
            var6_6.remove((Object)"__sendToServer");
            this.a.a(var1.c.d, var5_5.a, var25_22, var6_6);
            return;
        }
        if (!var4_4.equals((Object)"loadAdComponents")) {
            this.a.a(var1, var2_3, var3_2);
            return;
        }
        var13_23 = 1;
        var14_24 = 3;
        if (var6_6.containsKey((Object)"min") && var6_6.containsKey((Object)"max")) {
            try {
                var13_23 = Integer.parseInt((String)((String)var6_6.get((Object)"min")));
                var14_24 = var24_25 = Integer.parseInt((String)((String)var6_6.get((Object)"max")));
            }
            catch (NumberFormatException var23_26) {
                var13_23 = 1;
                var14_24 = 3;
            }
        }
        this.j.a().toString();
        var16_27 = aj.super.a(var13_23, var14_24);
        if (var16_27.size() <= 0) {
            aj.super.a("renderFailed", (Map<String, String>)Collections.emptyMap(), this.j, this.l, this.k, 0);
            return;
        }
        this.j.a().toString();
        var18_28 = aj.a(var16_27);
        this.h.loadUrl("javascript:(function() {var multiadwraps=document.getElementsByClassName('multiAdWrap');if(multiadwraps.length>0){var template=document.getElementsByClassName('multiAdWrap')[0];var compiled=Hogan.compile(template.innerHTML);template.innerHTML='';template.innerHTML=compiled.render(JSON.parse(" + var18_28 + "));}})();");
        this.h.loadUrl("javascript:flurryadapter.callComplete();");
        var19_29 = var16_27.iterator();
        do {
            if (!var19_29.hasNext()) {
                this.addView((View)this.h);
                return;
            }
            var20_31 = (AdUnit)var19_29.next();
            var21_30 = new HashMap();
            var21_30.put((Object)"guid", (Object)((AdFrame)var20_31.c().get(0)).f().toString());
            aj.super.a("rendered", (Map<String, String>)var21_30, var20_31, aj.super.a(((AdFrame)var20_31.c().get(0)).f().toString()), 0, 0);
        } while (true);
    }

    final String b() {
        return ((AdFrame)this.m.get(this.k)).c().toString();
    }

    final String c() {
        return ((AdFrame)this.m.get(this.k)).d().d().toString();
    }

    /*
     * Exception decompiling
     */
    @Override
    public final void initLayout(Context var1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CASE]], but top level block is 1[TRYBLOCK]
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

    public final void onCompletion(MediaPlayer mediaPlayer) {
        aj.super.a("videoCompleted", (Map<String, String>)Collections.emptyMap(), this.j, this.l, this.k, 0);
    }

    public final boolean onError(MediaPlayer mediaPlayer, int n2, int n3) {
        aj.super.a("renderFailed", (Map<String, String>)Collections.emptyMap(), this.j, this.l, this.k, 0);
        return true;
    }

    public final boolean onKeyDown(int n2, KeyEvent keyEvent) {
        if (n2 == 4) {
            aj.super.a("adWillClose", (Map<String, String>)Collections.emptyMap(), this.j, this.l, this.k, 0);
            return true;
        }
        return super.onKeyUp(n2, keyEvent);
    }

    public final void onPrepared(MediaPlayer mediaPlayer) {
        if (aj.super.e() == 3) {
            this.f.dismiss();
            this.g.start();
            aj.super.a("rendered", (Map<String, String>)Collections.emptyMap(), this.j, this.l, this.k, 0);
            aj.super.a("videoStarted", (Map<String, String>)Collections.emptyMap(), this.j, this.l, this.k, 0);
        }
    }
}


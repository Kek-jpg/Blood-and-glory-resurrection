/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.flurry.android.am;
import com.flurry.android.ar;
import com.flurry.android.be;
import com.flurry.android.t;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class bd {
    private static String a = "FlurryAgent";
    private static String b = "FlurryBannerTag";
    private Map<Context, List<t>> c = new HashMap();
    private Context d;

    bd(Context context) {
        this.d = context;
    }

    final ar a(t t2, long l2) {
        void var6_3 = this;
        synchronized (var6_3) {
            ar ar2 = t2.a(l2);
            return ar2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final t a(Context context, View view, String string) {
        void var9_4 = this;
        synchronized (var9_4) {
            boolean bl;
            t t2;
            List list = (List)this.c.get((Object)context);
            if (list == null) {
                return null;
            }
            Iterator iterator = list.iterator();
            do {
                if (!iterator.hasNext()) return null;
            } while (!view.equals((Object)(t2 = (t)((Object)iterator.next())).c()) || !(bl = string.equals((Object)t2.d())));
            return t2;
        }
    }

    final t a(ViewGroup viewGroup) {
        void var4_2 = this;
        synchronized (var4_2) {
            t t2 = (t)viewGroup.findViewWithTag((Object)b);
            return t2;
        }
    }

    final t a(be be2, Context context, ViewGroup viewGroup, String string) {
        void var7_5 = this;
        synchronized (var7_5) {
            t t2 = new t(be2, context, string, viewGroup);
            t2.setTag((Object)b);
            return t2;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final List<t> a(Context context, ViewGroup viewGroup, String string) {
        void var11_4 = this;
        synchronized (var11_4) {
            List list = (List)this.c.get((Object)context);
            ArrayList arrayList = new ArrayList();
            if (list == null) return arrayList;
            try {
                for (t t2 : list) {
                    if (t2.c().equals((Object)viewGroup) && !t2.d().equals((Object)string)) {
                        arrayList.add((Object)t2);
                    }
                    if (t2.c().equals((Object)viewGroup) || !t2.d().equals((Object)string)) continue;
                    arrayList.add((Object)t2);
                }
                return arrayList;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final void a() {
        bd bd2 = this;
        synchronized (bd2) {
            List list = (List)this.c.get((Object)this.d);
            if (list == null) return;
            try {
                for (t t2 : list) {
                    t2.a(0L);
                    if (t2.a() <= 0 || t2.b()) continue;
                    this.a(t2);
                }
                return;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
            }
        }
    }

    final void a(Context context) {
        void var3_2 = this;
        synchronized (var3_2) {
            this.d = context;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void a(Context context, t t2) {
        void var7_3 = this;
        synchronized (var7_3) {
            List list = (List)this.c.get((Object)context);
            if (list != null) {
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    if (!((t)((Object)iterator.next())).equals((Object)t2)) continue;
                    "BannerHolder removed for adSpace: " + t2.d();
                    iterator.remove();
                    break;
                }
            }
            return;
        }
    }

    final void a(t t2) {
        void var4_2 = this;
        synchronized (var4_2) {
            t2.postDelayed((Runnable)new am((bd)this, t2), (long)t2.a());
            t2.a(true);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final void b() {
        bd bd2 = this;
        synchronized (bd2) {
            List list = (List)this.c.get((Object)this.d);
            if (list == null) return;
            try {
                for (t t2 : list) {
                    if (t2.a() <= 0 || t2.b()) continue;
                    this.a(t2);
                }
                return;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void b(Context context, t t2) {
        void var7_3 = this;
        synchronized (var7_3) {
            if (t2 != null) {
                List list = (List)this.c.get((Object)context);
                if (list == null) {
                    list = new ArrayList();
                }
                list.add((Object)t2);
                this.c.put((Object)context, (Object)list);
            }
            return;
        }
    }

    final void b(t t2) {
        void var5_2 = this;
        synchronized (var5_2) {
            t2.a(false);
            if (t2.getContext().equals((Object)this.d) && ((List)this.c.get((Object)this.d)).contains((Object)t2)) {
                "Rotating banner for adSpace: " + t2.d();
                t2.a(1L);
                if (t2.a() > 0) {
                    this.a(t2);
                }
            }
            return;
        }
    }
}


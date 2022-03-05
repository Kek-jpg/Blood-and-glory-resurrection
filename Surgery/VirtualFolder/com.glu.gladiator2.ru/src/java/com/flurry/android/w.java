/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.android;

import com.flurry.android.AdFrame;
import com.flurry.android.AdResponse;
import com.flurry.android.AdUnit;
import com.flurry.android.y;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class w {
    private Map<String, List<AdUnit>> a = new HashMap();
    private Map<String, AdUnit> b = new HashMap();

    w() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final AdUnit a(String string) {
        void var7_2 = this;
        synchronized (var7_2) {
            AdUnit adUnit;
            List list = (List)this.a.get((Object)string);
            if (list == null) return null;
            Iterator iterator = list.iterator();
            do {
                if (!iterator.hasNext()) return null;
            } while (!y.a((adUnit = (AdUnit)iterator.next()).b()) || adUnit.c().size() <= 0);
            iterator.remove();
            this.b.put((Object)((AdFrame)adUnit.c().get(0)).f().toString(), (Object)adUnit);
            return adUnit;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final List<AdUnit> a(String string, int n2) {
        void var10_3 = this;
        synchronized (var10_3) {
            ArrayList arrayList = new ArrayList();
            List list = (List)this.a.get((Object)string);
            if (list == null) return arrayList;
            try {
                Iterator iterator = list.iterator();
                while (iterator.hasNext() && arrayList.size() <= n2) {
                    AdUnit adUnit = (AdUnit)iterator.next();
                    if (!y.a(adUnit.b()) || adUnit.d() != 1 || adUnit.c().size() <= 0) continue;
                    arrayList.add((Object)adUnit);
                    this.b.put((Object)((AdFrame)adUnit.c().get(0)).f().toString(), (Object)adUnit);
                    iterator.remove();
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

    final void a(AdResponse adResponse) {
        void var9_2 = this;
        synchronized (var9_2) {
            this.a.clear();
            for (AdUnit adUnit : adResponse.a()) {
                String string = adUnit.a().toString();
                List list = (List)this.a.get((Object)string);
                if (list == null) {
                    list = new ArrayList();
                }
                list.add((Object)adUnit);
                this.a.put((Object)string, (Object)list);
            }
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final boolean b(String string) {
        void var7_2 = this;
        synchronized (var7_2) {
            boolean bl;
            List list = (List)this.a.get((Object)string);
            if (list == null) return false;
            if (list.isEmpty()) return false;
            Iterator iterator = list.iterator();
            do {
                if (!iterator.hasNext()) return false;
            } while (!(bl = y.a(((AdUnit)iterator.next()).b())));
            return true;
        }
    }
}


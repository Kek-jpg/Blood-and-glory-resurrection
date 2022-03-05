/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.wildtangent.brandboost;

import com.wildtangent.brandboost.k;
import com.wildtangent.brandboost.q;
import com.wildtangent.brandboost.util.b;
import com.wildtangent.brandboost.util.h;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class m {
    private static final String a = "com.wildtangent.brandboost__" + m.class.getSimpleName();
    private List<k> b;
    private String c;
    private int d;
    private String e;
    private String f;
    private List<String> g;
    private String h;
    private boolean i;
    private String j;
    private String k;
    private int l = 3600000;
    private int m = 30000;
    private int n = 900000;

    public m(List<k> list, String string, int n2, String string2, String string3, List<String> list2, String string4, boolean bl, String string5, String string6) {
        this.b = list;
        this.c = string;
        this.d = n2;
        this.e = string2;
        this.f = string3;
        this.g = list2;
        this.h = string4;
        this.i = bl;
        this.j = string5;
        this.k = string6;
    }

    public static int a(m m2) {
        if (m2 != null) {
            return m2.d();
        }
        return 3600000;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static m a(String var0) {
        try {
            b.b(m.a, var0);
            var5_1 = h.a(var0);
            if (var5_1 == null) return null;
            var6_2 = var5_1.getJSONObject("Campaigns");
            var7_3 = var6_2.keys();
            var8_4 = new ArrayList(var6_2.length());
            while (var7_3.hasNext()) {
                var22_5 = k.a(var6_2, (String)var7_3.next());
                if (var22_5 == null) continue;
                var8_4.add((Object)var22_5);
            }
            var9_10 = var5_1.getString("PageSize");
            var10_11 = var5_1.getInt("MaxPageWidth");
            var11_12 = var5_1.getString("ServerEnvironment");
            var12_13 = var5_1.getString("DistributionPartner");
            var13_14 = var5_1.getBoolean("SecureBrandBoostEnabled");
            var14_15 = var5_1.getString("JsonAdLink");
            var15_16 = var5_1.getString("AnalyticsServerLink");
            var16_17 = var5_1.getJSONArray("AnalyticsLinks");
            var17_18 = null;
            if (var16_17 != null) {
                var17_18 = new ArrayList(var16_17.length());
            }
            ** GOTO lbl-1000
        }
        catch (JSONException var1_6) lbl-1000: // 2 sources:
        {
            do {
                b.a(m.a, "Invalid JSON for available campaigns response", (Throwable)var1_7);
                return null;
                break;
            } while (true);
        }
        for (var18_19 = 0; var18_19 < (var19_20 = var16_17.length()); ++var18_19) {
            var17_18.add((Object)var16_17.getString(var18_19));
        }
lbl-1000: // 2 sources:
        {
            var3_9 = new m((List<k>)var8_4, var9_10, var10_11, var11_12, var15_16, (List<String>)var17_18, var12_13, var13_14, var14_15.replace((CharSequence)"#position#", (CharSequence)"0"), var5_1.getString("CloseButtonLink"));
        }
        try {
            if (var5_1.has("Intervals") == false) return var3_9;
            var21_21 = var5_1.getJSONObject("Intervals");
            if (var21_21 == null) return var3_9;
            var3_9.l = 1000 * var21_21.getInt("PromoRequestInterval");
            var3_9.m = 1000 * var21_21.getInt("PromoErrorRequestInterval");
            var3_9.n = 1000 * var21_21.getInt("HoverDisplayInterval");
            return var3_9;
        }
        catch (JSONException var1_8) {
            ** continue;
        }
    }

    public static int b(m m2) {
        if (m2 != null) {
            return m2.e();
        }
        return 30000;
    }

    public static int c(m m2) {
        if (m2 != null) {
            return m2.f();
        }
        return 900000;
    }

    public k a(q q2) {
        k k2;
        block2 : {
            List<k> list = this.b;
            k2 = null;
            if (list != null) {
                k k3;
                Iterator iterator = this.b.iterator();
                do {
                    boolean bl = iterator.hasNext();
                    k2 = null;
                    if (!bl) break block2;
                } while (!(k3 = (k)iterator.next()).a(q2));
                k2 = k3;
            }
        }
        return k2;
    }

    public List<String> a() {
        return this.g;
    }

    public String b() {
        return this.f;
    }

    public String c() {
        return this.j;
    }

    public int d() {
        return this.l;
    }

    public int e() {
        return this.m;
    }

    public int f() {
        return this.n;
    }
}


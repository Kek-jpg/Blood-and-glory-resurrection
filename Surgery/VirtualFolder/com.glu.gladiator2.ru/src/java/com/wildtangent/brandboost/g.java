/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  java.lang.CharSequence
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.net.URL
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 */
package com.wildtangent.brandboost;

import android.content.Context;
import android.os.Bundle;
import com.wildtangent.brandboost.a;
import com.wildtangent.brandboost.util.b;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class g {
    private static String a = "other";
    private static final String b = "com.wildtangent.brandboost__" + g.class.getSimpleName();
    private List<String> c = null;
    private com.wildtangent.brandboost.a d = null;
    private String e = null;

    public g(Context context) {
        this.d = new com.wildtangent.brandboost.a(context);
    }

    public static String a() {
        b.a(b, "reading analytics owner: " + a);
        return a;
    }

    public static void a(String string) {
        a = string;
        b.a(b, "analytics owner: " + a);
    }

    public List<String> a(a a2) {
        ArrayList arrayList = new ArrayList();
        if (a2 != null && this.e != null && this.c != null) {
            for (String string : this.c) {
                arrayList.add((Object)("http:" + string.replace((CharSequence)"+replaceevent+", (CharSequence)a2.toString())));
            }
        }
        return arrayList;
    }

    public void a(List<String> list) {
        this.c = list;
    }

    public void b(a a2) {
        List<String> list = this.a(a2);
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                URL uRL = com.wildtangent.brandboost.a.a((String)iterator.next());
                if (uRL != null) {
                    b.d(b, "Sending event " + (Object)((Object)a2) + " to URL " + uRL.toString());
                    this.d.a(uRL, null, 0, null);
                    continue;
                }
                b.d(b, "URL is null. Analytics not sent.");
            }
        } else {
            b.d(b, "No analytics URLs for event " + (Object)((Object)a2));
        }
    }

    public void b(String string) {
        this.e = string;
    }

    public static final class a
    extends Enum<a> {
        public static final /* enum */ a a = new a("BrandBoost+Initialized");
        public static final /* enum */ a b = new a("Ad+Server+Queried");
        public static final /* enum */ a c = new a("Campaign+Available");
        public static final /* enum */ a d = new a("Hover+Shown");
        public static final /* enum */ a e = new a("Hover+Hidden");
        private static final /* synthetic */ a[] g;
        private String f;

        static {
            a[] arra = new a[]{a, b, c, d, e};
            g = arra;
        }

        private a(String string2) {
            this.f = string2;
        }

        public String toString() {
            return this.f;
        }
    }

}


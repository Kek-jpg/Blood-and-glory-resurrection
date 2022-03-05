/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.android;

import com.flurry.android.AdUnit;
import com.flurry.android.bb;
import com.flurry.android.bc;
import com.flurry.android.be;
import com.flurry.android.y;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class r {
    private static String a = "FlurryAgent";
    private be b;

    r(be be2) {
        this.b = be2;
    }

    private static boolean a(String string, String string2) {
        return string2.equals((Object)("%{" + string + "}"));
    }

    final String a(bb bb2, AdUnit adUnit, String string, String string2) {
        if (r.a("fids", string2)) {
            String string3 = 0 + ":" + this.b.e();
            bc.a(a, "Replacing param fids with: " + string3);
            return string.replace((CharSequence)string2, (CharSequence)y.b(string3));
        }
        if (r.a("sid", string2)) {
            String string4 = String.valueOf((long)this.b.b());
            bc.a(a, "Replacing param sid with: " + string4);
            return string.replace((CharSequence)string2, (CharSequence)y.b(string4));
        }
        if (r.a("lid", string2)) {
            String string5 = String.valueOf((int)bb2.a());
            bc.a(a, "Replacing param lid with: " + string5);
            return string.replace((CharSequence)string2, (CharSequence)y.b(string5));
        }
        if (r.a("guid", string2)) {
            String string6 = bb2.b();
            bc.a(a, "Replacing param guid with: " + string6);
            return string.replace((CharSequence)string2, (CharSequence)y.b(string6));
        }
        if (r.a("ats", string2)) {
            String string7 = String.valueOf((long)System.currentTimeMillis());
            bc.a(a, "Replacing param ats with: " + string7);
            return string.replace((CharSequence)string2, (CharSequence)y.b(string7));
        }
        if (r.a("apik", string2)) {
            String string8 = this.b.c();
            bc.a(a, "Replacing param apik with: " + string8);
            return string.replace((CharSequence)string2, (CharSequence)y.b(string8));
        }
        if (r.a("hid", string2)) {
            String string9 = adUnit.a().toString();
            bc.a(a, "Replacing param hid with: " + string9);
            return string.replace((CharSequence)string2, (CharSequence)y.b(string9));
        }
        if (r.a("eso", string2)) {
            String string10 = Long.toString((long)(System.currentTimeMillis() - this.b.b()));
            bc.a(a, "Replacing param eso with: " + string10);
            return string.replace((CharSequence)string2, (CharSequence)y.b(string10));
        }
        if (r.a("uc", string2)) {
            Iterator iterator = this.b.i().entrySet().iterator();
            String string11 = "";
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                string11 = string11 + "c_" + y.b((String)entry.getKey()) + "=" + y.b((String)entry.getValue()) + "&";
            }
            bc.a(a, "Replacing param uc with: " + string11);
            return string.replace((CharSequence)string2, (CharSequence)string11);
        }
        bc.a(a, "Unknown param: " + string2);
        return string.replace((CharSequence)string2, (CharSequence)"");
    }
}


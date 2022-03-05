/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.wildtangent.brandboost;

import com.wildtangent.brandboost.util.b;
import com.wildtangent.brandboost.util.h;
import org.json.JSONException;
import org.json.JSONObject;

public class q {
    private static final String a = "com.wildtangent.brandboost__" + q.class.getSimpleName();
    private String b;

    public q(String string) {
        this.b = string;
    }

    public static q a(String string) {
        block3 : {
            q q2;
            block2 : {
                q2 = null;
                if (string == null) break block2;
                int n2 = string.indexOf("BrandBoost.loadJSONSponsor(") + "BrandBoost.loadJSONSponsor(".length();
                int n3 = string.lastIndexOf(");");
                if (n2 < 0 || n3 <= n2) break block3;
                q2 = q.b(string.substring(n2, n3));
            }
            return q2;
        }
        b.b(a, "Invalid JSONP, start/end tags not found: " + string);
        return null;
    }

    public static q b(String string) {
        q q2;
        block3 : {
            JSONObject jSONObject;
            try {
                jSONObject = h.a(string);
                q2 = null;
                if (jSONObject == null) break block3;
            }
            catch (JSONException jSONException) {
                b.a(a, "Invalid JSON for ad server response", jSONException);
                return null;
            }
            q2 = new q(h.a("VAST/Ad/id", jSONObject));
        }
        return q2;
    }

    public String a() {
        return this.b;
    }
}


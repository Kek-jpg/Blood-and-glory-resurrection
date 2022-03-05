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

import com.wildtangent.brandboost.GameSpecification;
import com.wildtangent.brandboost.util.b;
import com.wildtangent.brandboost.util.g;
import com.wildtangent.brandboost.util.h;
import org.json.JSONException;
import org.json.JSONObject;

public class s {
    private static final String a = "com.wildtangent.brandboost__" + s.class.getSimpleName();
    private boolean b = true;
    private String c;
    private GameSpecification d;
    private String e;
    private int f;
    private String g;
    private boolean h = false;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static s a(String string) {
        s s2;
        boolean bl = g.a(string);
        s s3 = null;
        if (bl) return s3;
        JSONObject jSONObject = h.a(string);
        s3 = null;
        if (jSONObject == null) return s3;
        try {
            s2 = new s();
        }
        catch (JSONException jSONException) {}
        try {
            s2.b = jSONObject.getBoolean("IsError");
            s2.c = h.a(jSONObject, "Error");
            s2.e = h.a(jSONObject, "ItemName");
            s2.f = jSONObject.getInt("Nonce");
            s2.g = h.a(jSONObject, "UserId");
            s2.h = jSONObject.getBoolean("ConfirmationRequired");
            String string2 = h.a(jSONObject, "PartnerName");
            String string3 = h.a(jSONObject, "SiteName");
            String string4 = h.a(jSONObject, "GameName");
            if (string2 == null) return s2;
            if (string3 == null) return s2;
            if (string4 == null) return s2;
            s2.d = new GameSpecification(string2, string3, string4);
            return s2;
        }
        catch (JSONException jSONException) {
            s3 = s2;
        }
        {
            b.b(a, "Failed to parse JSON verification response");
            return s3;
        }
    }

    public GameSpecification a() {
        return this.d;
    }

    public boolean a(GameSpecification gameSpecification, String string, String string2) {
        return gameSpecification != null && gameSpecification.equals(this.d) && string != null && string.equals((Object)this.e) && (string2 != null && string2.equals((Object)this.g) || g.a(string2) && g.a(this.g));
    }

    public String b() {
        return this.e;
    }

    public String c() {
        return this.g;
    }
}


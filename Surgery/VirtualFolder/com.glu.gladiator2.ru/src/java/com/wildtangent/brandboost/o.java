/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  java.lang.Integer
 *  java.lang.String
 *  java.lang.System
 *  java.net.URL
 *  java.util.Random
 */
package com.wildtangent.brandboost;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.wildtangent.brandboost.GameSpecification;
import com.wildtangent.brandboost.a;
import com.wildtangent.brandboost.g;
import com.wildtangent.brandboost.util.b;
import com.wildtangent.brandboost.util.d;
import com.wildtangent.brandboost.util.f;
import java.net.URL;
import java.util.Random;

public class o
extends a {
    private static final String a = "com.wildtangent.brandboost__" + o.class.getSimpleName();
    private GameSpecification b;
    private Random c;

    public o(Context context, GameSpecification gameSpecification) {
        super(context);
        this.b = gameSpecification;
        this.c = new Random(System.currentTimeMillis());
    }

    private void a(Uri.Builder builder) {
        builder.appendQueryParameter("pn", this.b.getPartnerName());
        builder.appendQueryParameter("sn", this.b.getSiteName());
        builder.appendQueryParameter("gn", this.b.getGameName());
        builder.appendQueryParameter("mdt", "android");
    }

    /*
     * Enabled aggressive block sorting
     */
    private URL b(String string) {
        Uri.Builder builder = d.a("/v1/VexPromo", false);
        if (string != null && string.length() > 0) {
            builder.appendQueryParameter("uid", string);
        } else {
            b.a(a, "No user id specified");
        }
        o.super.a(builder);
        builder.appendQueryParameter("prmi", "fixed");
        builder.appendQueryParameter("clv", "1.0.0.74");
        builder.appendQueryParameter("c", f.a());
        builder.appendQueryParameter("ip", "begin");
        builder.appendQueryParameter("ord", new Integer(this.c.nextInt()).toString());
        builder.appendQueryParameter("src", g.a());
        return o.a(builder.build().toString());
    }

    private URL c(String string) {
        Uri.Builder builder = d.a("/v1/client/Verification", true);
        builder.appendQueryParameter("vexCode", string);
        builder.appendQueryParameter("clv", "1.0.0.74");
        builder.appendQueryParameter("json", "true");
        return o.a(builder.build().toString());
    }

    public void a(String string, a.a a2) {
        super.a(o.super.b(string), a2, 1, null);
    }

    public void b(String string, a.a a2) {
        super.a(o.super.c(string), a2, 3, null);
    }
}


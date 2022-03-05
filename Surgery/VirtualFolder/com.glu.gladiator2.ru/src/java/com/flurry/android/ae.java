/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.os.AsyncTask
 *  java.lang.Class
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Void
 *  org.apache.http.Header
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 */
package com.flurry.android;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import com.flurry.android.be;
import com.flurry.android.y;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

final class ae
extends AsyncTask<Void, Void, String> {
    private final String a;
    private Context b;
    private String c;
    private /* synthetic */ be d;

    public ae(be be2, Context context, String string) {
        this.d = be2;
        this.a = this.getClass().getSimpleName();
        this.b = context;
        this.c = string;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private /* varargs */ String a() {
        block9 : {
            block10 : {
                var1_1 = 0;
                block2 : do {
                    block12 : {
                        block13 : {
                            block11 : {
                                var2_5 = null;
                                if (var1_1 >= 5) break block11;
                                if (!Uri.parse((String)this.c).getScheme().equals((Object)"http")) break block9;
                                if (!be.c(this.b)) break block10;
                                var6_4 = y.a(be.c(this.d), this.c, 10000, 15000, false);
                                if (var6_4 == null) break block12;
                                var7_3 = var6_4.getStatusLine().getStatusCode();
                                if (var7_3 != 200) break block13;
                                "Redirect URL found for: " + this.c;
                                var2_5 = this.c;
                            }
lbl14: // 2 sources:
                            do {
                                return var2_5;
                                break;
                            } while (true);
                        }
                        if (var7_3 < 300 || var7_3 >= 400) break;
                        "NumRedirects: " + (var1_1 + 1);
                        if (var6_4.containsHeader("Location")) {
                            this.c = var6_4.getFirstHeader("Location").getValue();
                        }
                    }
lbl22: // 2 sources:
                    do {
                        ++var1_1;
                        continue block2;
                        break;
                    } while (true);
                    break;
                } while (true);
                "Bad Response status code: " + var7_3;
                return null;
            }
            try {
                Thread.sleep((long)100L);
            }
            catch (InterruptedException var4_2) {
                var4_2.getMessage();
            }
            ** while (true)
        }
        var3_6 = be.a(this.b, this.c, "android.intent.action.VIEW");
        var2_5 = null;
        ** while (!var3_6)
lbl38: // 1 sources:
        return this.c;
    }
}


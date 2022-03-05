/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  java.io.Serializable
 *  java.lang.Object
 *  java.lang.String
 *  java.net.URI
 *  java.net.URISyntaxException
 *  java.util.List
 *  org.apache.http.NameValuePair
 *  org.apache.http.client.utils.URLEncodedUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.wildtangent.brandboost;

import android.net.Uri;
import com.wildtangent.brandboost.q;
import com.wildtangent.brandboost.util.b;
import com.wildtangent.brandboost.util.g;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONException;
import org.json.JSONObject;

public final class k
implements Serializable {
    private static final String a = "com.wildtangent.brandboost_" + k.class.getSimpleName();
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;

    public k(String string, String string2, String string3, String string4, String string5) {
        this.b = string;
        this.c = string2;
        this.d = string3;
        this.e = string4;
        this.f = string5;
        this.a(this.e);
    }

    public static k a(JSONObject jSONObject, String string) throws JSONException {
        String string2;
        block6 : {
            k k2;
            block5 : {
                k2 = null;
                if (jSONObject == null) break block5;
                JSONObject jSONObject2 = jSONObject.getJSONObject(string);
                k2 = null;
                if (jSONObject2 == null) break block5;
                string2 = jSONObject2.getString("AdServerResponseType");
                if (!"JSON".equals((Object)string2) && !"".equals((Object)string2)) break block6;
                String string3 = jSONObject2.getString("OrderName");
                JSONObject jSONObject3 = jSONObject2.getJSONObject("promos");
                k2 = null;
                if (jSONObject3 != null) {
                    JSONObject jSONObject4 = jSONObject3.getJSONObject("fixed");
                    k2 = null;
                    if (jSONObject4 != null) {
                        k2 = new k(string, string2, string3, jSONObject4.getString("launch"), jSONObject4.getString("sip"));
                    }
                }
            }
            return k2;
        }
        b.c(a, "Invalid ad server response type (" + string2 + " for campaign: " + string);
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(String string) {
        if (!g.a(string)) {
            List list;
            try {
                List list2;
                list = list2 = URLEncodedUtils.parse((URI)new URI(string), (String)"UTF-8");
            }
            catch (URISyntaxException uRISyntaxException) {
                b.a("Failed to construct URL", uRISyntaxException);
                list = null;
            }
            if (list != null) {
                for (NameValuePair nameValuePair : list) {
                    if (!nameValuePair.getName().equalsIgnoreCase("i")) continue;
                    this.g = nameValuePair.getValue();
                    b.a(a, "Item key found for campaign: " + this.g);
                    return;
                }
            }
        }
        b.a(a, "No item key found for campaign.");
    }

    public String a() {
        return this.e;
    }

    public boolean a(q q2) {
        boolean bl = false;
        if (q2 != null) {
            String string = this.b;
            bl = false;
            if (string != null) {
                String string2 = q2.a();
                bl = this.b.equals((Object)string2);
                b.a(a, "Checking availability [adServer=" + string2 + ",campaign=" + this.b + ",shouldEnable=" + bl + "]");
            }
        }
        return bl;
    }

    public Uri b() {
        return Uri.parse((String)this.a());
    }

    public String c() {
        return this.f;
    }

    public String d() {
        return this.g;
    }

    public String toString() {
        return "Campaign [_campaignId=" + this.b + ", _adServerResponseType=" + this.c + ", _orderName=" + this.d + ", _launchUrl=" + this.e + ", _insertUrl=" + this.f + ", _itemKey=" + this.g + "]";
    }
}


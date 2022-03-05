/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.json.JSONTokener
 */
package com.wildtangent.brandboost.util;

import com.wildtangent.brandboost.util.b;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class h {
    private static final String a = "com.wildtangent.brandboost__" + h.class.getSimpleName();

    public static String a(String string, JSONObject jSONObject) throws JSONException {
        return h.a(string, jSONObject, "/");
    }

    public static String a(String string, JSONObject jSONObject, String string2) throws JSONException {
        int n2 = string.lastIndexOf(string2);
        String string3 = null;
        if (n2 > 0) {
            int n3 = string.length();
            string3 = null;
            if (n2 < n3) {
                String string4 = string.substring(0, n2);
                String string5 = string.substring(n2 + 1);
                b.a(a, "objectQuery: " + string4 + ",objectId: " + string5);
                string3 = h.b(string4, jSONObject, string2).getString(string5);
            }
        }
        return string3;
    }

    public static String a(JSONObject jSONObject, String string) {
        String string2 = jSONObject.optString(string, null);
        if ("null".equals((Object)string2)) {
            string2 = null;
        }
        return string2;
    }

    public static JSONObject a(String string) {
        if (string != null) {
            try {
                Object object = new JSONTokener(string).nextValue();
                if (object instanceof JSONObject) {
                    return (JSONObject)object;
                }
                b.b(a, "Failed to parse JSON: " + string);
                return null;
            }
            catch (JSONException jSONException) {
                b.b(a, "Failed to parse JSON: " + string);
            }
        }
        return null;
    }

    public static JSONObject b(String string, JSONObject jSONObject, String string2) throws JSONException {
        JSONObject jSONObject2 = jSONObject;
        String[] arrstring = string.split(string2);
        int n2 = arrstring.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            jSONObject2 = jSONObject2.getJSONObject(arrstring[i2]);
        }
        return jSONObject2;
    }
}


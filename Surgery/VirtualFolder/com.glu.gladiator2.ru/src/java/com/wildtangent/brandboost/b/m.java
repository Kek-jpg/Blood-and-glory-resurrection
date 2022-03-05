/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.wildtangent.brandboost.b;

import android.os.Parcel;
import android.os.Parcelable;
import com.wildtangent.brandboost.b.k;
import com.wildtangent.brandboost.util.b;
import com.wildtangent.brandboost.util.g;
import com.wildtangent.brandboost.util.h;
import org.json.JSONException;
import org.json.JSONObject;

public final class m
implements Parcelable {
    public static final Parcelable.Creator a;
    private static final String b;
    private String c;
    private String d;
    private int e;
    private int f;
    private int g;
    private int h;

    static {
        b = "com.wildtangent.brandboost__" + m.class.getSimpleName();
        a = new k();
    }

    public m() {
        this.c = null;
        this.d = null;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = 0;
    }

    public m(Parcel parcel) {
        m.super.a(parcel);
    }

    public static m a(String string) {
        block6 : {
            block7 : {
                JSONObject jSONObject;
                m m2 = new m();
                try {
                    jSONObject = h.a(string);
                    if (jSONObject == null) break block6;
                }
                catch (JSONException jSONException) {
                    b.a(b, "Invalid JSON for available campaigns response", jSONException);
                    return null;
                }
                JSONObject jSONObject2 = jSONObject.getJSONObject("Hover");
                if (jSONObject2 == null) break block7;
                String string2 = jSONObject2.getString("CloseButtonMarginRight");
                String string3 = jSONObject2.getString("CloseButtonMarginTop");
                String string4 = jSONObject2.getString("Height");
                m2.a(Integer.parseInt((String)jSONObject2.getString("Width")));
                m2.b(Integer.parseInt((String)string4));
                m2.c(Integer.parseInt((String)string3));
                m2.d(Integer.parseInt((String)string2));
                m2.c(jSONObject.getString("CloseButtonLink"));
                return m2;
            }
            b.d(b, "No hover object parsed from JSON");
            return null;
        }
        b.d(b, "No response parsed from JSON");
        return null;
    }

    private void a(Parcel parcel) {
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readInt();
        this.f = parcel.readInt();
        this.g = parcel.readInt();
        this.h = parcel.readInt();
    }

    public void a(int n2) {
        this.e = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean a() {
        return !g.a(this.e()) && !g.a(this.d());
    }

    public int b() {
        return this.g;
    }

    public void b(int n2) {
        this.f = n2;
    }

    public void b(String string) {
        this.c = string;
    }

    public int c() {
        return this.h;
    }

    public void c(int n2) {
        this.g = n2;
    }

    public void c(String string) {
        this.d = string;
    }

    public String d() {
        return this.d;
    }

    public void d(int n2) {
        this.h = n2;
    }

    public int describeContents() {
        return 0;
    }

    public String e() {
        return this.c;
    }

    public int f() {
        return this.e;
    }

    public int g() {
        return this.f;
    }

    public String toString() {
        return "HoverProperties [_urlSip=" + this.c + ", _urlCloseImage=" + this.d + ", _width=" + this.e + ", _height=" + this.f + ", _closeMarginTop=" + this.g + ", _closeMarginRight=" + this.h + "]";
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e);
        parcel.writeInt(this.f);
        parcel.writeInt(this.g);
        parcel.writeInt(this.h);
    }
}


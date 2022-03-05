/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 */
package com.unity3d.player.a;

import android.text.TextUtils;

final class k {
    public int a;
    public int b;
    public String c;
    public String d;
    public String e;
    public long f;
    public String g;

    k() {
    }

    public final String toString() {
        Object[] arrobject = new Object[]{this.a, this.b, this.c, this.d, this.e, this.f};
        return TextUtils.join((CharSequence)"|", (Object[])arrobject);
    }
}


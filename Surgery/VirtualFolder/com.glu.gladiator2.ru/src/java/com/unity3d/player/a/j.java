/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  java.lang.Object
 *  java.lang.String
 */
package com.unity3d.player.a;

import android.content.SharedPreferences;
import com.unity3d.player.a.h;
import com.unity3d.player.a.l;

public final class j {
    private final SharedPreferences a;
    private final h b;
    private SharedPreferences.Editor c;

    public j(SharedPreferences sharedPreferences, h h2) {
        this.a = sharedPreferences;
        this.b = h2;
        this.c = null;
    }

    public final void a() {
        if (this.c != null) {
            this.c.commit();
            this.c = null;
        }
    }

    public final void a(String string2, String string3) {
        if (this.c == null) {
            this.c = this.a.edit();
        }
        String string4 = this.b.a(string3);
        this.c.putString(string2, string4);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final String b(String string2, String string3) {
        String string4 = this.a.getString(string2, null);
        if (string4 == null) return string3;
        try {
            String string5 = this.b.b(string4);
            return string5;
        }
        catch (l l2) {
            return string3;
        }
    }
}


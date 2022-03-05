/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 */
package com.flurry.android;

import android.content.DialogInterface;
import com.flurry.android.aj;
import com.flurry.android.ba;
import java.util.HashMap;
import java.util.Map;

final class al
implements DialogInterface.OnClickListener {
    private /* synthetic */ ba a;
    private /* synthetic */ int b;
    private /* synthetic */ aj c;

    al(aj aj2, ba ba2, int n2) {
        this.c = aj2;
        this.a = ba2;
        this.b = n2;
    }

    public final void onClick(DialogInterface dialogInterface, int n2) {
        HashMap hashMap = new HashMap();
        hashMap.put((Object)"sourceEvent", (Object)this.a.a);
        aj.a(this.c, "userCanceled", (Map)hashMap, aj.c(this.c), aj.d(this.c), aj.e(this.c), 1 + this.b);
        dialogInterface.dismiss();
    }
}


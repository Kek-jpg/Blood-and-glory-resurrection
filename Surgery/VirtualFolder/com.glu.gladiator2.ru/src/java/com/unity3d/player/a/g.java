/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.text.TextUtils$SimpleStringSplitter
 *  java.lang.CharSequence
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.security.InvalidKeyException
 *  java.security.NoSuchAlgorithmException
 *  java.security.PublicKey
 *  java.security.Signature
 *  java.security.SignatureException
 *  java.util.Iterator
 *  java.util.regex.Pattern
 */
package com.unity3d.player.a;

import android.text.TextUtils;
import com.unity3d.player.a.b;
import com.unity3d.player.a.f;
import com.unity3d.player.a.i;
import com.unity3d.player.a.k;
import com.unity3d.player.b.a;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Iterator;
import java.util.regex.Pattern;

final class g {
    private final i a;
    private final f b;
    private final int c;
    private final String d;
    private final String e;
    private final b f;

    g(i i2, b b2, f f2, int n2, String string2, String string3) {
        this.a = i2;
        this.f = b2;
        this.b = f2;
        this.c = n2;
        this.d = string2;
        this.e = string3;
    }

    private void a(int n2, k k2) {
        this.a.a(n2, k2);
        if (this.a.a()) {
            this.b.a();
            return;
        }
        this.b.b();
    }

    public final f a() {
        return this.b;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a(PublicKey publicKey, int n2, String string2, String string3) {
        k k2;
        block29 : {
            String string4;
            String[] arrstring;
            block28 : {
                if (n2 == 0 || n2 == 1) break block28;
                k2 = null;
                if (n2 != 2) break block29;
            }
            try {
                Signature signature = Signature.getInstance((String)"SHA1withRSA");
                signature.initVerify(publicKey);
                signature.update(string2.getBytes());
                if (!signature.verify(a.a(string3))) {
                    this.b.b();
                    return;
                }
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new RuntimeException((Throwable)noSuchAlgorithmException);
            }
            catch (InvalidKeyException invalidKeyException) {
                return;
            }
            catch (SignatureException signatureException) {
                throw new RuntimeException((Throwable)signatureException);
            }
            catch (com.unity3d.player.b.b b2) {
                this.b.b();
                return;
            }
            try {
                TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
                simpleStringSplitter.setString(string2);
                Iterator iterator = simpleStringSplitter.iterator();
                if (!iterator.hasNext()) {
                    throw new IllegalArgumentException("");
                }
                String string5 = (String)iterator.next();
                string4 = "";
                if (iterator.hasNext()) {
                    string4 = (String)iterator.next();
                }
                if ((arrstring = TextUtils.split((String)string5, (String)Pattern.quote((String)"|"))).length < 6) {
                    throw new IllegalArgumentException("");
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                this.b.b();
                return;
            }
            k2 = new k();
            k2.g = string4;
            k2.a = Integer.parseInt((String)arrstring[0]);
            k2.b = Integer.parseInt((String)arrstring[1]);
            k2.c = arrstring[2];
            k2.d = arrstring[3];
            k2.e = arrstring[4];
            k2.f = Long.parseLong((String)arrstring[5]);
            if (k2.a != n2) {
                this.b.b();
                return;
            }
            if (k2.b != this.c) {
                this.b.b();
                return;
            }
            if (!k2.c.equals((Object)this.d)) {
                this.b.b();
                return;
            }
            if (!k2.d.equals((Object)this.e)) {
                this.b.b();
                return;
            }
            if (TextUtils.isEmpty((CharSequence)k2.e)) {
                this.b.b();
                return;
            }
        }
        switch (n2) {
            default: {
                this.b.b();
                return;
            }
            case 0: 
            case 2: {
                g.super.a(1, k2);
                return;
            }
            case 1: {
                g.super.a(0, k2);
                return;
            }
            case 257: {
                g.super.a(-1, k2);
                return;
            }
            case 4: {
                g.super.a(-1, k2);
                return;
            }
            case 5: {
                g.super.a(-1, k2);
                return;
            }
            case 258: {
                return;
            }
            case 259: {
                return;
            }
            case 3: 
        }
    }

    public final int b() {
        return this.c;
    }

    public final String c() {
        return this.d;
    }
}


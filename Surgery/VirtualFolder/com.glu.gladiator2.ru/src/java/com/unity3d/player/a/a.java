/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.UnsupportedEncodingException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.security.GeneralSecurityException
 *  java.security.Key
 *  java.security.spec.AlgorithmParameterSpec
 *  java.security.spec.KeySpec
 *  javax.crypto.BadPaddingException
 *  javax.crypto.Cipher
 *  javax.crypto.IllegalBlockSizeException
 *  javax.crypto.SecretKey
 *  javax.crypto.SecretKeyFactory
 *  javax.crypto.spec.IvParameterSpec
 *  javax.crypto.spec.PBEKeySpec
 *  javax.crypto.spec.SecretKeySpec
 */
package com.unity3d.player.a;

import com.unity3d.player.a.h;
import com.unity3d.player.a.l;
import com.unity3d.player.b.b;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public final class a
implements h {
    private static final byte[] a = new byte[]{16, 74, 71, -80, 32, 101, -47, 72, 117, -14, 0, -29, 70, 65, -12, 74};
    private Cipher b;
    private Cipher c;

    public a(byte[] arrby, String string2, String string3) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance((String)"PBEWITHSHAAND256BITAES-CBC-BC").generateSecret((KeySpec)new PBEKeySpec((string2 + string3).toCharArray(), arrby, 1024, 256)).getEncoded(), "AES");
            this.b = Cipher.getInstance((String)"AES/CBC/PKCS5Padding");
            this.b.init(1, (Key)secretKeySpec, (AlgorithmParameterSpec)new IvParameterSpec(a));
            this.c = Cipher.getInstance((String)"AES/CBC/PKCS5Padding");
            this.c.init(2, (Key)secretKeySpec, (AlgorithmParameterSpec)new IvParameterSpec(a));
            return;
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw new RuntimeException("Invalid environment", (Throwable)generalSecurityException);
        }
    }

    @Override
    public final String a(String string2) {
        if (string2 == null) {
            return null;
        }
        try {
            String string3 = com.unity3d.player.b.a.a(this.b.doFinal(("com.android.vending.licensing.AESObfuscator-1|" + string2).getBytes("UTF-8")));
            return string3;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("Invalid environment", (Throwable)unsupportedEncodingException);
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw new RuntimeException("Invalid environment", (Throwable)generalSecurityException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final String b(String string2) {
        if (string2 == null) {
            return null;
        }
        try {
            String string3 = new String(this.c.doFinal(com.unity3d.player.b.a.a(string2)), "UTF-8");
            if (string3.indexOf("com.android.vending.licensing.AESObfuscator-1|") == 0) return string3.substring("com.android.vending.licensing.AESObfuscator-1|".length(), string3.length());
            throw new l("Header not found (invalid data or key):" + string2);
        }
        catch (b b2) {
            throw new l(b2.getMessage() + ":" + string2);
        }
        catch (IllegalBlockSizeException illegalBlockSizeException) {
            throw new l(illegalBlockSizeException.getMessage() + ":" + string2);
        }
        catch (BadPaddingException badPaddingException) {
            throw new l(badPaddingException.getMessage() + ":" + string2);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("Invalid environment", (Throwable)unsupportedEncodingException);
        }
    }
}


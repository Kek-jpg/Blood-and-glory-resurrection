/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.security.Key
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 *  java.security.spec.AlgorithmParameterSpec
 *  javax.crypto.Cipher
 *  javax.crypto.NoSuchPaddingException
 *  javax.crypto.spec.IvParameterSpec
 *  javax.crypto.spec.SecretKeySpec
 */
package com.mobileapptracker;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    private Cipher cipher;
    private IvParameterSpec ivspec;
    private SecretKeySpec keyspec;

    public Encryption(String string2, String string3) {
        this.ivspec = new IvParameterSpec(string3.getBytes());
        this.keyspec = new SecretKeySpec(string2.getBytes(), "AES");
        try {
            this.cipher = Cipher.getInstance((String)"AES/CBC/NoPadding");
            return;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
            return;
        }
        catch (NoSuchPaddingException noSuchPaddingException) {
            noSuchPaddingException.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static byte[] hexToBytes(String string2) {
        byte[] arrby = null;
        if (string2 != null) {
            int n = string2.length();
            arrby = null;
            if (n >= 2) {
                int n2 = string2.length() / 2;
                arrby = new byte[n2];
                for (int i2 = 0; i2 < n2; ++i2) {
                    arrby[i2] = (byte)Integer.parseInt((String)string2.substring(i2 * 2, 2 + i2 * 2), (int)16);
                }
            }
        }
        return arrby;
    }

    private static String padString(String string2) {
        int n = 16 - string2.length() % 16;
        for (int i2 = 0; i2 < n; ++i2) {
            string2 = string2 + ' ';
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String bytesToHex(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        int n = arrby.length;
        String string2 = "";
        int n2 = 0;
        while (n2 < n) {
            string2 = (255 & arrby[n2]) < 16 ? string2 + "0" + Integer.toHexString((int)(255 & arrby[n2])) : string2 + Integer.toHexString((int)(255 & arrby[n2]));
            ++n2;
        }
        return string2;
    }

    public byte[] decrypt(String string2) throws Exception {
        if (string2 == null || string2.length() == 0) {
            throw new Exception("Empty string");
        }
        try {
            this.cipher.init(2, (Key)this.keyspec, (AlgorithmParameterSpec)this.ivspec);
            byte[] arrby = this.cipher.doFinal(Encryption.hexToBytes(string2));
            return arrby;
        }
        catch (Exception exception) {
            throw new Exception("[decrypt] " + exception.getMessage());
        }
    }

    public byte[] encrypt(String string2) throws Exception {
        if (string2 == null || string2.length() == 0) {
            throw new Exception("Empty string");
        }
        try {
            this.cipher.init(1, (Key)this.keyspec, (AlgorithmParameterSpec)this.ivspec);
            byte[] arrby = this.cipher.doFinal(Encryption.padString(string2).getBytes());
            return arrby;
        }
        catch (Exception exception) {
            throw new Exception("[encrypt] " + exception.getMessage());
        }
    }

    public String md5(String string2) {
        MessageDigest messageDigest = MessageDigest.getInstance((String)"MD5");
        messageDigest.update(string2.getBytes());
        byte[] arrby = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        int n = 0;
        do {
            if (n >= arrby.length) break;
            stringBuffer.append(Integer.toHexString((int)(255 & arrby[n])));
            ++n;
        } while (true);
        try {
            String string3 = stringBuffer.toString().toUpperCase();
            return string3;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
            return "";
        }
    }
}


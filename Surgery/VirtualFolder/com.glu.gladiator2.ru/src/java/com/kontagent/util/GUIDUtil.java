/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.wifi.WifiInfo
 *  android.net.wifi.WifiManager
 *  android.text.TextUtils
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 *  java.util.UUID
 */
package com.kontagent.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.kontagent.KontagentLog;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class GUIDUtil {
    private static String byteArrayToHexString(byte[] arrby) throws Exception {
        String string2 = "";
        for (int i2 = 0; i2 < arrby.length; ++i2) {
            string2 = string2 + Integer.toString((int)(256 + (255 & arrby[i2])), (int)16).substring(1);
        }
        return string2;
    }

    public static Long generateSenderId() {
        return Math.abs((long)UUID.randomUUID().getLeastSignificantBits());
    }

    public static String generateShortTrackingId(Context context, String string2) {
        if (context == null || TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("Both context and apiKey params must be not null");
        }
        return GUIDUtil.generateShortTrackingId(context, string2, null, GUIDUtil.getDeviceMacAddress(context));
    }

    private static String generateShortTrackingId(Context context, String string2, String string3, String string4) {
        StringBuilder stringBuilder = new StringBuilder();
        if (string4 != null) {
            stringBuilder.append(GUIDUtil.md5(String.format((String)"%s%s", (Object[])new Object[]{GUIDUtil.sanitizeCustomID(string4), string2})));
        }
        return stringBuilder.toString();
    }

    public static String generateTrackingId() {
        return GUIDUtil.shaUUID().substring(0, 16);
    }

    private static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    private static String getDeviceMacAddress(Context context) {
        String string2 = ((WifiManager)context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        if (TextUtils.isEmpty((CharSequence)string2)) {
            KontagentLog.w("Device's MAC address is null - possibly due to running under emulator", null);
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String md5(String string2) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance((String)"MD5");
            messageDigest.update(string2.getBytes());
            byte[] arrby = messageDigest.digest();
            String string3 = "";
            try {
                String string4;
                string3 = string4 = GUIDUtil.byteArrayToHexString(arrby);
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return string3.toString();
            }
            return string3.toString();
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
            return "";
        }
    }

    public static String sanitizeCustomID(String string2) {
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            String string3 = string2.replaceAll("[^01234567890abcdefABCDEF]", "").toUpperCase();
            KontagentLog.d(String.format((String)"SANITIZED CUSTOM ID(%s)=%s)", (Object[])new Object[]{string2, string3}));
            return string3;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String shaUUID() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance((String)"SHA1");
            messageDigest.update(GUIDUtil.generateUUID().getBytes("UTF-8"));
            return GUIDUtil.byteArrayToHexString(messageDigest.digest());
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
            do {
                return null;
                break;
            } while (true);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}


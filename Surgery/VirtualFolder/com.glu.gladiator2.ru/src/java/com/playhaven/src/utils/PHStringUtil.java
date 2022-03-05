/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.util.Base64
 *  android.util.Log
 *  java.io.UnsupportedEncodingException
 *  java.lang.Appendable
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.UnsupportedOperationException
 *  java.net.URLDecoder
 *  java.net.URLEncoder
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 *  java.util.Formatter
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.UUID
 */
package com.playhaven.src.utils;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.playhaven.src.common.PHConfig;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PHStringUtil {
    public static UUIDGenerator UUID_GENERATOR = new DefaultUUIDGenerator(null);

    public static String base64Digest(String string2) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String string3 = PHStringUtil.convertToBase64(PHStringUtil.dataDigest(string2));
        return string3.substring(0, -1 + string3.length());
    }

    private static String convertToBase64(byte[] arrby) throws UnsupportedEncodingException {
        if (arrby == null) {
            return null;
        }
        return new String(Base64.encode((byte[])arrby, (int)9), "UTF8");
    }

    private static String convertToHex(byte[] arrby) {
        StringBuilder stringBuilder = new StringBuilder(2 * arrby.length);
        Formatter formatter = new Formatter((Appendable)stringBuilder);
        for (byte by : arrby) {
            Object[] arrobject = new Object[]{by};
            formatter.format("%02x", arrobject);
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String createQuery(HashMap<String, String> hashMap) {
        if (hashMap == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Iterator iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            String string2 = (String)entry.getKey();
            String string3 = (String)entry.getValue();
            if (string2 == null || string3 == null) continue;
            String string4 = PHStringUtil.urlEncode(string2);
            String string5 = PHStringUtil.weakUrlEncode(string3);
            String string6 = stringBuilder.length() == 0 ? "%s=%s" : "&%s=%s";
            stringBuilder.append(String.format((String)string6, (Object[])new Object[]{string4, string5}));
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static HashMap<String, String> createQueryDict(String string2) {
        if (string2 == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        String[] arrstring = string2.split("&|\\?");
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String[] arrstring2 = arrstring[n2].split("=");
            if (arrstring2.length >= 2) {
                hashMap.put((Object)PHStringUtil.urlDecode(arrstring2[0]), (Object)PHStringUtil.urlDecode(arrstring2[1]));
            }
            ++n2;
        }
        return hashMap;
    }

    private static byte[] dataDigest(String string2) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (string2 == null) {
            return null;
        }
        return MessageDigest.getInstance((String)"SHA-1").digest(string2.getBytes("UTF8"));
    }

    public static String decodeURL(String string2) {
        throw new UnsupportedOperationException("This method is not yet implemented");
    }

    public static String encodeHtml(String string2) {
        return TextUtils.htmlEncode((String)string2);
    }

    public static String encodeURL(String string2) {
        throw new UnsupportedOperationException("This method is not yet implemented");
    }

    public static String generateUUID() {
        return UUID_GENERATOR.generateUUID();
    }

    public static String hexDigest(String string2) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return PHStringUtil.convertToHex(PHStringUtil.dataDigest(string2));
    }

    public static void log(String string2) {
        Object[] arrobject = new Object[]{PHConfig.sdk_version};
        Log.i((String)String.format((String)"|Playhaven-%s|", (Object[])arrobject), (String)string2);
    }

    public static String queryComponent(String string2) {
        int n = string2.indexOf("?");
        if (n == -1) {
            return null;
        }
        return string2.substring(n + 1);
    }

    public static String urlDecode(String string2) {
        return URLDecoder.decode((String)string2);
    }

    public static String urlEncode(String string2) {
        return URLEncoder.encode((String)string2);
    }

    public static String weakUrlEncode(String string2) {
        if (string2 == null) {
            return null;
        }
        String[] arrstring = new String[]{";", "?", " ", "&", "=", "$", ",", "[", "]", "#", "!", "'", "(", ")", "*"};
        String[] arrstring2 = new String[]{"%3B", "%3F", "+", "%26", "%3D", "%24", "%2C", "%5B", "%5D", "%21", "%27", "%28", "%28", "%29", "%2A"};
        StringBuilder stringBuilder = new StringBuilder(string2);
        for (int i2 = 0; i2 < arrstring2.length; ++i2) {
            String string3 = arrstring[i2];
            String string4 = arrstring2[i2];
            int n = stringBuilder.indexOf(string3);
            while (n != -1) {
                stringBuilder.replace(n, n + string3.length(), string4);
                n = stringBuilder.indexOf(string3);
            }
        }
        return stringBuilder.toString();
    }

    private static class DefaultUUIDGenerator
    implements UUIDGenerator {
        private DefaultUUIDGenerator() {
        }

        /* synthetic */ DefaultUUIDGenerator(1 var1) {
        }

        @Override
        public String generateUUID() {
            return UUID.randomUUID().toString();
        }
    }

    public static interface UUIDGenerator {
        public String generateUUID();
    }

}


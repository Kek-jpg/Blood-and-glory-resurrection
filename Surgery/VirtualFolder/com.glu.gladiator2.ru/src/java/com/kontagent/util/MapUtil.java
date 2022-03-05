/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.UnsupportedEncodingException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.net.URLDecoder
 *  java.net.URLEncoder
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Set
 */
package com.kontagent.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapUtil {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String mapToString(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String string2;
            String string3 = (String)iterator.next();
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            String string4 = (String)map.get((Object)string3);
            if (string3 != null) {
                try {
                    string2 = URLEncoder.encode((String)string3, (String)"UTF-8");
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    throw new RuntimeException("This method requires UTF-8 encoding support", (Throwable)unsupportedEncodingException);
                }
            } else {
                string2 = "";
            }
            stringBuilder.append(string2);
            stringBuilder.append("=");
            String string5 = string4 != null ? URLEncoder.encode((String)string4, (String)"UTF-8") : "";
            stringBuilder.append(string5);
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Map<String, String> stringToMap(String string2) {
        HashMap hashMap = new HashMap();
        String[] arrstring = string2.split("&");
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String[] arrstring2 = arrstring[n2].split("=");
            try {
                String string3 = URLDecoder.decode((String)arrstring2[0], (String)"UTF-8");
                String string4 = arrstring2.length > 1 ? URLDecoder.decode((String)arrstring2[1], (String)"UTF-8") : "";
                hashMap.put((Object)string3, (Object)string4);
                ++n2;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new RuntimeException("This method requires UTF-8 encoding support", (Throwable)unsupportedEncodingException);
            }
        }
        return hashMap;
    }
}


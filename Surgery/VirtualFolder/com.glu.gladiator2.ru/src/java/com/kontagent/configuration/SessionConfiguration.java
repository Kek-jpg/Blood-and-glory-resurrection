/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.kontagent.configuration;

import com.kontagent.KontagentLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class SessionConfiguration {
    private static boolean checkAPA(HashMap<String, Object> hashMap) {
        return SessionConfiguration.checkBoolOption(hashMap, "keySessionAPA");
    }

    private static boolean checkApiKey(HashMap<String, Object> hashMap) {
        return SessionConfiguration.checkStringOption(hashMap, "keySessionApiKey");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean checkBoolOption(HashMap<String, Object> hashMap, String string2) {
        Object object = hashMap.get((Object)string2);
        if (object == null) return false;
        boolean bl = true;
        if (!bl) {
            return bl;
        }
        bl = object instanceof Boolean;
        if (bl) return bl;
        return bl;
    }

    private static boolean checkDebug(HashMap<String, Object> hashMap) {
        return SessionConfiguration.checkBoolOption(hashMap, "keySessionDebug");
    }

    private static boolean checkMode(HashMap<String, Object> hashMap) {
        boolean bl = SessionConfiguration.checkStringOption(hashMap, "keySessionMode");
        String string2 = "";
        if (bl) {
            string2 = (String)hashMap.get((Object)"keySessionMode");
        }
        return bl && (string2.equals((Object)"production") || string2.equals((Object)"test"));
    }

    private static boolean checkSenderID(HashMap<String, Object> hashMap) {
        return SessionConfiguration.checkStringOption(hashMap, "keySessionSenderId");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean checkStringOption(HashMap<String, Object> hashMap, String string2) {
        Object object = hashMap.get((Object)string2);
        if (object == null) return false;
        boolean bl = true;
        if (!bl) {
            return bl;
        }
        bl = object instanceof String;
        if (!bl) return bl;
        if (((String)object).length() <= 0) return false;
        return true;
    }

    public static boolean validate(HashMap<String, Object> hashMap) {
        boolean bl = SessionConfiguration.validateRequired(hashMap);
        if (bl) {
            bl = SessionConfiguration.validateOptional(hashMap);
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean validateOptional(HashMap<String, Object> hashMap) {
        boolean bl = true;
        Iterator iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            String string2 = (String)((Map.Entry)iterator.next()).getKey();
            if (string2.equals((Object)"keySessionSenderId")) {
                bl = SessionConfiguration.checkSenderID(hashMap);
            } else if (string2.equals((Object)"keySessionMode")) {
                bl = SessionConfiguration.checkMode(hashMap);
            } else if (string2.equals((Object)"keySessionDebug")) {
                bl = SessionConfiguration.checkDebug(hashMap);
            } else if (string2.equals((Object)"keySessionAPA")) {
                bl = SessionConfiguration.checkAPA(hashMap);
            }
            if (bl) continue;
            KontagentLog.e(String.format((String)"Failed for %s!", (Object[])new Object[]{string2}), null);
            return bl;
        }
        return bl;
    }

    private static boolean validateRequired(HashMap<String, Object> hashMap) {
        boolean bl = SessionConfiguration.checkApiKey(hashMap);
        if (!bl) {
            KontagentLog.e(String.format((String)"Failed for %s!", (Object[])new Object[]{"keySessionApiKey"}), null);
        }
        return bl;
    }
}


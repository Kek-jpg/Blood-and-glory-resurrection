/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  java.lang.Enum
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.net.URLDecoder
 *  java.util.HashMap
 *  java.util.Map
 *  java.util.Set
 */
package com.kontagent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.kontagent.KontagentLog;
import com.kontagent.KontagentPrefs;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InstallReceiver
extends BroadcastReceiver {
    protected static final String ACTION_INSTALL_REFERRER = "com.android.vending.INSTALL_REFERRER";
    protected static final String DEFAULT_SUBTYPE1_KEY = "st1";
    protected static final String DEFAULT_SUBTYPE2_KEY = "st2";
    protected static final String DEFAULT_SUBTYPE3_KEY = "st3";
    protected static final String DEFAULT_TYPE_KEY = "tu";
    protected static final String EVENT_TYPE_AD = "ad";
    protected static final String EVENT_TYPE_PARTNER = "partner";
    protected static final String EXTRA_REFERRER = "referrer";

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Map<String, String> parseReferrerArguments(String string2) {
        if (string2 == null) {
            return new HashMap();
        }
        String string3 = URLDecoder.decode((String)string2).trim();
        if (string3.length() == 0) {
            return new HashMap();
        }
        String[] arrstring = string3.split("&");
        HashMap hashMap = new HashMap();
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String[] arrstring2 = arrstring[n2].split("=");
            String string4 = arrstring2[0];
            int n3 = arrstring2.length;
            String string5 = null;
            if (n3 > 1) {
                string5 = arrstring2[1];
            }
            hashMap.put((Object)string4, (Object)string5);
            ++n2;
        }
        return hashMap;
    }

    protected void dump(Context context) {
        KontagentPrefs kontagentPrefs = new KontagentPrefs(context);
        String string2 = kontagentPrefs.getReferrerEventType();
        String string3 = kontagentPrefs.getReferrerEventSubtype1();
        String string4 = kontagentPrefs.getReferrerEventSubtype2();
        String string5 = kontagentPrefs.getReferrerEventSubtype3();
        KontagentLog.d("Kontagent referrer arguments: ");
        KontagentLog.d("\ttu=" + string2);
        KontagentLog.d("\tst1=" + string3);
        KontagentLog.d("\tst2=" + string4);
        KontagentLog.d("\tst2=" + string5);
    }

    public void onReceive(Context context, Intent intent) {
        if (ACTION_INSTALL_REFERRER.equals((Object)intent.getAction())) {
            String string2 = intent.getStringExtra(EXTRA_REFERRER);
            KontagentLog.i("Installation detected. Referrer: " + string2);
            Map<String, String> map = InstallReceiver.parseReferrerArguments(string2);
            for (String string3 : map.keySet()) {
                KontagentLog.i("\t" + string3 + " = " + (String)map.get((Object)string3));
            }
            this.onReferralEvent(context, intent, map);
            this.dump(context);
        }
    }

    public void onReferralEvent(Context context, Intent intent, Map<String, String> map) {
        String string2;
        if (map.containsKey((Object)DEFAULT_TYPE_KEY) && (EVENT_TYPE_AD.equals((Object)(string2 = (String)map.get((Object)DEFAULT_TYPE_KEY))) || EVENT_TYPE_PARTNER.equals((Object)string2))) {
            new KontagentPrefs(context).setReferrerEventType(string2);
        }
        if (map.containsKey((Object)DEFAULT_SUBTYPE1_KEY)) {
            this.setReferrerEventSubtype1(context, (String)map.get((Object)DEFAULT_SUBTYPE1_KEY));
        }
        if (map.containsKey((Object)DEFAULT_SUBTYPE2_KEY)) {
            this.setReferrerEventSubtype2(context, (String)map.get((Object)DEFAULT_SUBTYPE2_KEY));
        }
        if (map.containsKey((Object)DEFAULT_SUBTYPE3_KEY)) {
            this.setReferrerEventSubtype3(context, (String)map.get((Object)DEFAULT_SUBTYPE3_KEY));
        }
    }

    public void setReferrerEventSubtype1(Context context, String string2) {
        new KontagentPrefs(context).setReferrerEventSubtype1(string2);
    }

    public void setReferrerEventSubtype2(Context context, String string2) {
        new KontagentPrefs(context).setReferrerEventSubtype2(string2);
    }

    public void setReferrerEventSubtype3(Context context, String string2) {
        new KontagentPrefs(context).setReferrerEventSubtype3(string2);
    }

    public void setReferrerEventType(Context context, ReferrerEventType referrerEventType) {
        KontagentPrefs kontagentPrefs = new KontagentPrefs(context);
        switch (1.$SwitchMap$com$kontagent$InstallReceiver$ReferrerEventType[referrerEventType.ordinal()]) {
            default: {
                return;
            }
            case 1: {
                kontagentPrefs.setReferrerEventType(EVENT_TYPE_AD);
                return;
            }
            case 2: 
        }
        kontagentPrefs.setReferrerEventType(EVENT_TYPE_PARTNER);
    }

    protected static final class ReferrerEventType
    extends Enum<ReferrerEventType> {
        private static final /* synthetic */ ReferrerEventType[] $VALUES;
        public static final /* enum */ ReferrerEventType Ad = new ReferrerEventType();
        public static final /* enum */ ReferrerEventType Partner = new ReferrerEventType();

        static {
            ReferrerEventType[] arrreferrerEventType = new ReferrerEventType[]{Ad, Partner};
            $VALUES = arrreferrerEventType;
        }

        public static ReferrerEventType valueOf(String string2) {
            return (ReferrerEventType)Enum.valueOf(ReferrerEventType.class, (String)string2);
        }

        public static ReferrerEventType[] values() {
            return (ReferrerEventType[])$VALUES.clone();
        }
    }

}


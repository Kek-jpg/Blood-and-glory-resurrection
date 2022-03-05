/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  java.lang.Object
 *  java.lang.String
 */
package com.kontagent;

import android.content.Context;
import android.content.SharedPreferences;

public class KontagentPrefs {
    private static final String FIRST_RUN_KEY = "firstRun";
    private static final String PREFS_NAME = "kontagent";
    private static final String REFERRER_SUBTYPE1_KEY = "referrer_st1";
    private static final String REFERRER_SUBTYPE2_KEY = "referrer_st2";
    private static final String REFERRER_SUBTYPE3_KEY = "referrer_st3";
    private static final String REFERRER_TYPE_KEY = "referrer_type";
    private static final String SENDER_ID_KEY = "senderId";
    private final Context mContext;

    public KontagentPrefs(Context context) {
        this.mContext = context;
    }

    public SharedPreferences getPreferences() {
        return this.mContext.getSharedPreferences(PREFS_NAME, 0);
    }

    public String getReferrerEventSubtype1() {
        return this.getPreferences().getString(REFERRER_SUBTYPE1_KEY, null);
    }

    public String getReferrerEventSubtype2() {
        return this.getPreferences().getString(REFERRER_SUBTYPE2_KEY, null);
    }

    public String getReferrerEventSubtype3() {
        return this.getPreferences().getString(REFERRER_SUBTYPE3_KEY, null);
    }

    public String getReferrerEventType() {
        return this.getPreferences().getString(REFERRER_TYPE_KEY, null);
    }

    public String getSenderId() {
        return this.getPreferences().getString(SENDER_ID_KEY, null);
    }

    public String getSenderId(String string2) {
        return this.getPreferences().getString(string2, null);
    }

    public boolean isFirstRun() {
        return this.getPreferences().getBoolean(FIRST_RUN_KEY, true);
    }

    public void resetPrefs() {
        this.getPreferences().edit().clear().commit();
    }

    public void setFirstRun(boolean bl) {
        this.getPreferences().edit().putBoolean(FIRST_RUN_KEY, bl).commit();
    }

    public void setReferrerEventSubtype1(String string2) {
        this.getPreferences().edit().putString(REFERRER_SUBTYPE1_KEY, string2).commit();
    }

    public void setReferrerEventSubtype2(String string2) {
        this.getPreferences().edit().putString(REFERRER_SUBTYPE2_KEY, string2).commit();
    }

    public void setReferrerEventSubtype3(String string2) {
        this.getPreferences().edit().putString(REFERRER_SUBTYPE3_KEY, string2).commit();
    }

    public void setReferrerEventType(String string2) {
        this.getPreferences().edit().putString(REFERRER_TYPE_KEY, string2).commit();
    }

    public void setSenderId(String string2) {
        this.getPreferences().edit().putString(SENDER_ID_KEY, string2).commit();
    }

    public void setSenderId(String string2, String string3) {
        this.getPreferences().edit().putString(string2, string3).commit();
    }
}


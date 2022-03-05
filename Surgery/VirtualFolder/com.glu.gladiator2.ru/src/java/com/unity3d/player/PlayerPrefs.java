/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  java.lang.ClassCastException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.concurrent.atomic.AtomicBoolean
 */
package com.unity3d.player;

import android.content.SharedPreferences;
import java.util.concurrent.atomic.AtomicBoolean;

class PlayerPrefs {
    private SharedPreferences a;
    private SharedPreferences.Editor b;
    private AtomicBoolean c = new AtomicBoolean(false);

    PlayerPrefs(SharedPreferences sharedPreferences) {
        this.a = sharedPreferences;
        this.b = this.a.edit();
        PlayerPrefs.super.InitPlayerPrefs();
    }

    private void DeleteAll() {
        this.b.clear();
        this.c.set(true);
    }

    private void DeleteKey(String string2) {
        this.b.remove(string2);
        this.c.set(true);
    }

    private float GetFloat(String string2, float f2) {
        PlayerPrefs.super.Sync();
        try {
            float f3 = this.a.getFloat(string2, f2);
            return f3;
        }
        catch (ClassCastException classCastException) {
            return f2;
        }
    }

    private int GetInt(String string2, int n) {
        PlayerPrefs.super.Sync();
        try {
            int n2 = this.a.getInt(string2, n);
            return n2;
        }
        catch (ClassCastException classCastException) {
            return n;
        }
    }

    private String GetString(String string2, String string3) {
        PlayerPrefs.super.Sync();
        try {
            String string4 = this.a.getString(string2, string3);
            return string4;
        }
        catch (ClassCastException classCastException) {
            return string3;
        }
    }

    private boolean HasKey(String string2) {
        PlayerPrefs.super.Sync();
        return this.a.contains(string2);
    }

    private final native void InitPlayerPrefs();

    private boolean SetFloat(String string2, float f2) {
        this.b.putFloat(string2, f2);
        this.c.set(true);
        return true;
    }

    private boolean SetInt(String string2, int n) {
        this.b.putInt(string2, n);
        this.c.set(true);
        return true;
    }

    private boolean SetString(String string2, String string3) {
        this.b.putString(string2, string3);
        this.c.set(true);
        return true;
    }

    private void Sync() {
        if (!this.c.getAndSet(false)) {
            return;
        }
        this.b.commit();
    }
}


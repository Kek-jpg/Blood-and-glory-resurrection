/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  java.lang.Class
 *  java.lang.ClassNotFoundException
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import java.util.Map;
import java.util.Set;

public class UnityPlayerProxyActivity
extends Activity {
    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected static void copyPlayerPrefs(Context context, String[] arrstring) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        if (!sharedPreferences.getAll().isEmpty()) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int n2 = arrstring.length;
        int n3 = 0;
        while (n3 < n2) {
            Map map = context.getSharedPreferences(arrstring[n3], 0).getAll();
            if (!map.isEmpty()) {
                for (Map.Entry entry : map.entrySet()) {
                    Object object = entry.getValue();
                    if (object.getClass() == Integer.class) {
                        editor.putInt((String)entry.getKey(), ((Integer)object).intValue());
                        continue;
                    }
                    if (object.getClass() == Float.class) {
                        editor.putFloat((String)entry.getKey(), ((Float)object).floatValue());
                        continue;
                    }
                    if (object.getClass() != String.class) continue;
                    editor.putString((String)entry.getKey(), (String)object);
                }
                editor.commit();
            }
            ++n3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onCreate(Bundle bundle) {
        String[] arrstring;
        int n2;
        int n3;
        n3 = 1;
        super.onCreate(bundle);
        arrstring = new String[2];
        arrstring[0] = "com.unity3d.player.UnityPlayerActivity";
        arrstring[n3] = "com.unity3d.player.UnityPlayerNativeActivity";
        UnityPlayerProxyActivity.copyPlayerPrefs((Context)this, arrstring);
        try {
            n2 = Build.VERSION.SDK_INT >= 9 ? n3 : 0;
        }
        catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
            return;
        }
        finally {
            this.finish();
        }
        if (n2 == 0) {
            n3 = 0;
        }
        Intent intent = new Intent((Context)this, Class.forName((String)arrstring[n3]));
        intent.addFlags(65536);
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            intent.putExtras(bundle2);
        }
        this.startActivity(intent);
        this.finish();
    }
}


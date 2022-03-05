/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.view.View
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.glu.platform.gwallet;

import android.util.Log;
import android.view.View;

public class GWalletDebug {
    public static final boolean DEBUG = true;
    private View outputView;

    private GWalletDebug() {
    }

    /* synthetic */ GWalletDebug(1 var1) {
    }

    public static final void DDD(String string2, String string3) {
        Log.d((String)string2, (String)string3);
    }

    public static final void DDD(String string2, String string3, Throwable throwable) {
        Log.d((String)string2, (String)string3, (Throwable)throwable);
    }

    public static final void DDD(String string2, char[] arrc) {
        Log.d((String)string2, (String)new String(arrc));
    }

    public static GWalletDebug getInstance() {
        return InstanceHolder.instance;
    }

    public View getOutputView() {
        return this.outputView;
    }

    public void setOutputView(View view) {
        this.outputView = view;
    }

    private static class InstanceHolder {
        public static GWalletDebug instance = new GWalletDebug(null);

        private InstanceHolder() {
        }
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.UnsatisfiedLinkError
 */
package com.glu.platform.gwallet;

import android.content.Context;
import com.glu.platform.gwallet.GWalletDebug;

public class GWallet {
    public static final String TAG = "GWallet";

    static {
        try {
            System.loadLibrary((String)"gwallet");
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {}
    }

    private GWallet() {
    }

    /* synthetic */ GWallet(1 var1) {
    }

    private native int addCurrency(int var1);

    private native int create(byte[] var1, Object var2);

    private native void destroy(int var1);

    private native int getBalance();

    public static GWallet getInstance() {
        return InstanceHolder.instance;
    }

    private native void handleupdate(Context var1, long var2);

    private native void initialise(Context var1, String var2, String var3, String var4);

    private native void pause(int var1);

    private native void restart(int var1);

    private native void resume(int var1);

    private native int runTests();

    private native void start(int var1);

    private native void stop(int var1);

    private native int subscribeWithReceipt(String var1, Object var2);

    private native int subtractCurrency(int var1);

    private native void uninitialise();

    public int doAddCurrency(int n) {
        return GWallet.super.addCurrency(n);
    }

    public int doGetBalance() {
        return this.getBalance();
    }

    public int doRunTests() {
        return this.runTests();
    }

    public int doSubscribe(String string2, Object object) {
        return GWallet.super.subscribeWithReceipt(string2, object);
    }

    public int doSubtractCurrency(int n) {
        return GWallet.super.subtractCurrency(n);
    }

    public int onCreate(byte[] arrby, Object object) {
        return GWallet.super.create(arrby, object);
    }

    public void onDestroy(int n) {
        GWallet.super.destroy(n);
    }

    public void onHandleUpdate(Context context, long l) {
        GWallet.super.handleupdate(context, l);
    }

    public void onInitialise(Context context, String string2, String string3, String string4) {
        GWalletDebug.DDD("GWallet", "Calling initialize with " + (Object)context + "," + string2 + "," + string3 + "," + string4);
        GWallet.super.initialise(context, string2, string3, string4);
    }

    public void onPause(int n) {
        GWallet.super.pause(n);
    }

    public void onResume(int n) {
        GWallet.super.resume(n);
    }

    public void onStart(int n) {
        GWallet.super.start(n);
    }

    public void onStop(int n) {
        GWallet.super.stop(n);
    }

    public void onUninitialise() {
        this.uninitialise();
    }

    private static class InstanceHolder {
        public static GWallet instance = new GWallet(null);

        private InstanceHolder() {
        }
    }

}


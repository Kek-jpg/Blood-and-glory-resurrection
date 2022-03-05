/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Runnable
 */
package com.unity3d.player;

class UnityJavaRunnable
implements Runnable {
    private final int a;

    UnityJavaRunnable(int n) {
        this.a = n;
    }

    private native void nativeFinalize(int var1);

    private native void nativeRun(int var1);

    protected void finalize() {
        try {
            this.nativeFinalize(this.a);
            return;
        }
        finally {
            super.finalize();
        }
    }

    public void run() {
        this.nativeRun(this.a);
    }
}


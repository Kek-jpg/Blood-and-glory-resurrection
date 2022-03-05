/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Thread
 */
package com.kontagent.util;

import com.kontagent.Kontagent;
import com.kontagent.KontagentLog;

public final class Waiter {
    public static final long DEFAULT_WAIT_TIMEOUT = 30000L;
    public static final long DEFAULT_WAIT_TIMEOUT_INFINITY = -1L;
    private final Object mSyncObject;

    private Waiter() {
        this.mSyncObject = new Object();
        Kontagent.enableDebug();
    }

    /* synthetic */ Waiter(1 var1) {
    }

    public static Waiter getInstance() {
        return InstanceHolder.instance;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyOperationCompleted() {
        Object object;
        Object object2 = object = this.mSyncObject;
        synchronized (object2) {
            this.mSyncObject.notifyAll();
            KontagentLog.d("Operation completed");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void waitForOperationToComplete(Runnable runnable, long l) {
        Object object;
        if (runnable == null) {
            throw new IllegalArgumentException("operation can not be null");
        }
        new Thread(runnable).start();
        Object object2 = object = this.mSyncObject;
        synchronized (object2) {
            block6 : {
                try {
                    KontagentLog.d("Waiting for operation to be completed...");
                    if (l == -1L) {
                        this.mSyncObject.wait();
                        break block6;
                    }
                    this.mSyncObject.wait(l);
                }
                catch (InterruptedException interruptedException) {}
            }
            return;
        }
    }

    private static class InstanceHolder {
        public static final Waiter instance = new Waiter(null);

        private InstanceHolder() {
        }
    }

}


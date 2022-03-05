/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.kontagent.connectivity;

import com.kontagent.connectivity.ConnectivityTracker;

public class ConnectivitySnapshot {
    private int mConnectivityCounter;
    private final ConnectivityTracker mTracker;

    public ConnectivitySnapshot(ConnectivityTracker connectivityTracker) {
        this.mTracker = connectivityTracker;
        this.mConnectivityCounter = connectivityTracker.getCounter();
    }

    public boolean isChanged() {
        return this.mConnectivityCounter != this.mTracker.getCounter();
    }

    public void sync() {
        this.mConnectivityCounter = this.mTracker.getCounter();
    }
}


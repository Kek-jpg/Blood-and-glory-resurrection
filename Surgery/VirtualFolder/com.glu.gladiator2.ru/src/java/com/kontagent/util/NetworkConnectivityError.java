/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.kontagent.util;

public class NetworkConnectivityError
extends Exception {
    private static final long serialVersionUID = 1L;

    public NetworkConnectivityError() {
    }

    public NetworkConnectivityError(String string2) {
        super(string2);
    }

    public NetworkConnectivityError(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    public NetworkConnectivityError(Throwable throwable) {
        super(throwable);
    }
}


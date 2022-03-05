/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.NetworkInfo$State
 *  java.lang.Object
 *  java.lang.SecurityException
 *  java.lang.String
 */
package com.playhaven.src.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.playhaven.src.common.PHConfig;

public class PHConnectionManager {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static PHConfig.ConnectionType getConnectionType(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return PHConfig.ConnectionType.NO_NETWORK;
            }
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
            NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(1);
            if (networkInfo == null) return PHConfig.ConnectionType.NO_NETWORK;
            if (networkInfo2 == null) {
                return PHConfig.ConnectionType.NO_NETWORK;
            }
            NetworkInfo.State state = networkInfo.getState();
            NetworkInfo.State state2 = networkInfo2.getState();
            if (state == NetworkInfo.State.CONNECTED) return PHConfig.ConnectionType.MOBILE;
            if (state == NetworkInfo.State.CONNECTING) {
                return PHConfig.ConnectionType.MOBILE;
            }
            if (state2 == NetworkInfo.State.CONNECTED) return PHConfig.ConnectionType.WIFI;
            if (state2 != NetworkInfo.State.CONNECTING) return PHConfig.ConnectionType.NO_NETWORK;
            return PHConfig.ConnectionType.WIFI;
        }
        catch (SecurityException securityException) {
            return PHConfig.ConnectionType.NO_PERMISSION;
        }
    }
}


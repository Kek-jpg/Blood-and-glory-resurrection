/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.NetworkInfo
 *  android.os.Parcelable
 *  java.lang.Object
 *  java.lang.String
 */
package com.kontagent.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.os.Parcelable;
import com.kontagent.KontagentLog;
import com.kontagent.connectivity.ConnectivityListener;
import com.kontagent.connectivity.ConnectivitySnapshot;

public class ConnectivityTracker
extends BroadcastReceiver {
    private static final String TAG = ConnectivityTracker.class.getSimpleName();
    private boolean mConnected;
    private int mConnectivityCounter = 0;
    private final Context mContext;
    private NetworkInfo mLastNetworkInfo;
    private ConnectivityListener mListener;

    public ConnectivityTracker(Context context) {
        this.mContext = context.getApplicationContext();
        this.mConnectivityCounter = 0;
    }

    public int getCounter() {
        return this.mConnectivityCounter;
    }

    public NetworkInfo getLastNetworkInfo() {
        return this.mLastNetworkInfo;
    }

    public ConnectivityListener getListener() {
        return this.mListener;
    }

    public boolean isOffline() {
        return !this.mConnected;
    }

    public boolean isOnline() {
        return this.mConnected;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onReceive(Context context, Intent intent) {
        String string2 = intent.getAction();
        if ("android.intent.action.AIRPLANE_MODE".equals((Object)string2)) {
            KontagentLog.d("Airplane mode broadcast received.");
            this.mConnectivityCounter = 1 + this.mConnectivityCounter;
            return;
        }
        if (!"android.net.conn.CONNECTIVITY_CHANGE".equals((Object)string2)) return;
        {
            KontagentLog.d("Connectivity broadcast received.");
            this.mConnectivityCounter = 1 + this.mConnectivityCounter;
            this.mLastNetworkInfo = (NetworkInfo)intent.getParcelableExtra("networkInfo");
            if (intent.getBooleanExtra("noConnectivity", false)) {
                this.setConnectedState(false);
                return;
            }
        }
        this.setConnectedState(this.mLastNetworkInfo.isConnected());
    }

    public void resetCounter() {
        KontagentLog.d(TAG, "Reseting connectivity counter (was " + this.mConnectivityCounter + ")");
        this.mConnectivityCounter = 0;
    }

    protected void setConnectedState(boolean bl) {
        if (this.mConnected != bl) {
            KontagentLog.d(TAG, "Connectivity state changed. Connected: " + bl);
            this.mConnected = bl;
            if (this.mListener != null) {
                this.mListener.onConnectivityChanged((ConnectivityTracker)this, this.mConnected);
            }
        }
    }

    public void setListener(ConnectivityListener connectivityListener) {
        this.mListener = connectivityListener;
        if (this.mListener != null) {
            this.mListener.onConnectivityChanged((ConnectivityTracker)this, this.mConnected);
        }
    }

    public ConnectivitySnapshot snapshot() {
        return new ConnectivitySnapshot(this);
    }

    public void startTracking() {
        KontagentLog.d(TAG, "Started tracking connectivity.");
        this.mContext.registerReceiver((BroadcastReceiver)this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        this.mContext.registerReceiver((BroadcastReceiver)this, new IntentFilter("android.intent.action.AIRPLANE_MODE"));
    }

    public void stopTracking() {
        KontagentLog.d(TAG, "Stopped tracking connectivity.");
        this.mContext.unregisterReceiver((BroadcastReceiver)this);
    }
}


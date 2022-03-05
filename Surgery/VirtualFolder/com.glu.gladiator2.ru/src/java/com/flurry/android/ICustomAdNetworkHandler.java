/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.android;

import android.content.Context;
import com.flurry.android.AdCreative;
import com.flurry.android.AdNetworkView;

public interface ICustomAdNetworkHandler {
    public AdNetworkView getAdFromNetwork(Context var1, AdCreative var2, String var3);
}


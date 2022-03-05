/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.flurry.android;

import com.flurry.android.FlurryAdType;
import java.util.Map;

public interface IListener {
    public void onAdClosed(String var1);

    public void onApplicationExit(String var1);

    public void onRenderFailed(String var1);

    public void onReward(String var1, Map<String, String> var2);

    public boolean shouldDisplayAd(String var1, FlurryAdType var2);
}


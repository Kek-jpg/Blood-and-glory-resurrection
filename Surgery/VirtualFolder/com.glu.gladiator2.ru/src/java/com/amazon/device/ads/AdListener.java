/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.amazon.device.ads;

import com.amazon.device.ads.AdError;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdProperties;

public interface AdListener {
    public void onAdCollapsed(AdLayout var1);

    public void onAdExpanded(AdLayout var1);

    public void onAdFailedToLoad(AdLayout var1, AdError var2);

    public void onAdLoaded(AdLayout var1, AdProperties var2);
}


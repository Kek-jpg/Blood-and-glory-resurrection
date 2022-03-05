/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.wildtangent.brandboost;

public interface BrandBoostCallbacks {
    public boolean onBrandBoostCampaignReady(String var1);

    public void onBrandBoostClosed(ClosedReason var1);

    public void onBrandBoostHoverTapped();

    public void onBrandBoostItemReady(String var1);

}


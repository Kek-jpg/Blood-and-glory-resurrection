/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package com.amazon.device.ads;

import com.amazon.device.ads.MraidProperty;
import com.amazon.device.ads.MraidView;

class MraidPlacementTypeProperty
extends MraidProperty {
    private final MraidView.PlacementType mPlacementType;

    MraidPlacementTypeProperty(MraidView.PlacementType placementType) {
        this.mPlacementType = placementType;
    }

    public static MraidPlacementTypeProperty createWithType(MraidView.PlacementType placementType) {
        return new MraidPlacementTypeProperty(placementType);
    }

    @Override
    public String toJsonPair() {
        return "placementType: '" + this.mPlacementType.toString().toLowerCase() + "'";
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package com.amazon.device.ads;

import com.amazon.device.ads.MraidProperty;
import com.amazon.device.ads.MraidView;

class MraidStateProperty
extends MraidProperty {
    private final MraidView.ViewState mViewState;

    MraidStateProperty(MraidView.ViewState viewState) {
        this.mViewState = viewState;
    }

    public static MraidStateProperty createWithViewState(MraidView.ViewState viewState) {
        return new MraidStateProperty(viewState);
    }

    @Override
    public String toJsonPair() {
        return "state: '" + this.mViewState.toString().toLowerCase() + "'";
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.amazon.device.ads;

import com.amazon.device.ads.MraidView;

class MraidAbstractController {
    private final MraidView mView;

    MraidAbstractController(MraidView mraidView) {
        this.mView = mraidView;
    }

    public MraidView getView() {
        return this.mView;
    }
}


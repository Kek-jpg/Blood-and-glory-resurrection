/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  java.util.Map
 */
package com.amazon.device.ads;

import com.amazon.device.ads.MraidCommand;
import com.amazon.device.ads.MraidDisplayController;
import com.amazon.device.ads.MraidView;
import java.util.Map;

class MraidCommandUseCustomClose
extends MraidCommand {
    MraidCommandUseCustomClose(Map<String, String> map, MraidView mraidView) {
        super(map, mraidView);
    }

    @Override
    void execute() {
        boolean bl = this.getBooleanFromParamsForKey("shouldUseCustomClose");
        this.mView.getDisplayController().useCustomClose(bl);
    }
}


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

class MraidCommandExpand
extends MraidCommand {
    MraidCommandExpand(Map<String, String> map, MraidView mraidView) {
        super(map, mraidView);
    }

    @Override
    void execute() {
        int n2 = this.getIntFromParamsForKey("w");
        int n3 = this.getIntFromParamsForKey("h");
        String string = this.getStringFromParamsForKey("url");
        boolean bl = this.getBooleanFromParamsForKey("shouldUseCustomClose");
        boolean bl2 = this.getBooleanFromParamsForKey("lockOrientation");
        if (n2 <= 0) {
            n2 = this.mView.getDisplayController().mScreenWidth;
        }
        if (n3 <= 0) {
            n3 = this.mView.getDisplayController().mScreenHeight;
        }
        this.mView.getDisplayController().expand(string, n2, n3, bl, bl2);
    }
}


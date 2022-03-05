/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 *  java.util.Map
 */
package com.amazon.device.ads;

import com.amazon.device.ads.MraidBrowserController;
import com.amazon.device.ads.MraidCommand;
import com.amazon.device.ads.MraidView;
import java.util.Map;

class MraidCommandOpen
extends MraidCommand {
    MraidCommandOpen(Map<String, String> map, MraidView mraidView) {
        super(map, mraidView);
    }

    @Override
    void execute() {
        String string = this.getStringFromParamsForKey("url");
        this.mView.getBrowserController().open(string);
    }
}


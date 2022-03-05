/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.String
 *  java.util.Map
 */
package com.amazon.device.ads;

import com.amazon.device.ads.Controller;
import com.amazon.device.ads.MraidCommand;
import com.amazon.device.ads.MraidDisplayController;
import com.amazon.device.ads.MraidView;
import java.util.Map;

class MraidCommandPlayVideo
extends MraidCommand {
    MraidCommandPlayVideo(Map<String, String> map, MraidView mraidView) {
        super(map, mraidView);
    }

    @Override
    void execute() {
        Integer[] arrinteger = this.getIntArrayFromParamsFroKey("position");
        int n2 = arrinteger[0];
        Controller.Dimensions dimensions = null;
        if (n2 != -1) {
            dimensions = new Controller.Dimensions();
            dimensions.y = arrinteger[0];
            dimensions.x = arrinteger[1];
            dimensions.width = arrinteger[2];
            dimensions.height = arrinteger[3];
        }
        String string = this.getStringFromParamsForKey("url");
        Controller.PlayerProperties playerProperties = new Controller.PlayerProperties();
        playerProperties.setProperties(this.getBooleanFromParamsForKey("audioMuted"), this.getBooleanFromParamsForKey("autoPlay"), this.getBooleanFromParamsForKey("controls"), true, this.getBooleanFromParamsForKey("loop"), this.getStringFromParamsForKey("startStyle"), this.getStringFromParamsForKey("stopStyle"));
        this.mView.getDisplayController().playVideo(string, dimensions, playerProperties);
    }
}


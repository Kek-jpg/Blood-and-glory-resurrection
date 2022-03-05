/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 */
package com.amazon.device.ads;

import com.amazon.device.ads.MraidCommand;
import com.amazon.device.ads.MraidCommandClose;
import com.amazon.device.ads.MraidCommandExpand;
import com.amazon.device.ads.MraidCommandOpen;
import com.amazon.device.ads.MraidCommandPlayVideo;
import com.amazon.device.ads.MraidCommandUseCustomClose;
import com.amazon.device.ads.MraidView;
import java.util.HashMap;
import java.util.Map;

class MraidCommandRegistry {
    private static Map<String, MraidCommandFactory> commandMap = new HashMap();

    static {
        commandMap.put((Object)"close", (Object)new MraidCommandFactory(){

            @Override
            public MraidCommand create(Map<String, String> map, MraidView mraidView) {
                return new MraidCommandClose(map, mraidView);
            }
        });
        commandMap.put((Object)"expand", (Object)new MraidCommandFactory(){

            @Override
            public MraidCommand create(Map<String, String> map, MraidView mraidView) {
                return new MraidCommandExpand(map, mraidView);
            }
        });
        commandMap.put((Object)"usecustomclose", (Object)new MraidCommandFactory(){

            @Override
            public MraidCommand create(Map<String, String> map, MraidView mraidView) {
                return new MraidCommandUseCustomClose(map, mraidView);
            }
        });
        commandMap.put((Object)"open", (Object)new MraidCommandFactory(){

            @Override
            public MraidCommand create(Map<String, String> map, MraidView mraidView) {
                return new MraidCommandOpen(map, mraidView);
            }
        });
        commandMap.put((Object)"playVideo", (Object)new MraidCommandFactory(){

            @Override
            public MraidCommand create(Map<String, String> map, MraidView mraidView) {
                return new MraidCommandPlayVideo(map, mraidView);
            }
        });
    }

    MraidCommandRegistry() {
    }

    static MraidCommand createCommand(String string, Map<String, String> map, MraidView mraidView) {
        MraidCommandFactory mraidCommandFactory = (MraidCommandFactory)commandMap.get((Object)string);
        if (mraidCommandFactory != null) {
            return mraidCommandFactory.create(map, mraidView);
        }
        return null;
    }

    private static interface MraidCommandFactory {
        public MraidCommand create(Map<String, String> var1, MraidView var2);
    }

}


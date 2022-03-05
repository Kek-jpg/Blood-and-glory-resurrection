/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.android;

public final class FlurryAdType
extends Enum<FlurryAdType> {
    public static final /* enum */ FlurryAdType VIDEO_TAKEOVER;
    public static final /* enum */ FlurryAdType WEB_BANNER;
    public static final /* enum */ FlurryAdType WEB_TAKEOVER;
    private static final /* synthetic */ FlurryAdType[] a;

    static {
        WEB_BANNER = new FlurryAdType();
        WEB_TAKEOVER = new FlurryAdType();
        VIDEO_TAKEOVER = new FlurryAdType();
        FlurryAdType[] arrflurryAdType = new FlurryAdType[]{WEB_BANNER, WEB_TAKEOVER, VIDEO_TAKEOVER};
        a = arrflurryAdType;
    }

    public static FlurryAdType valueOf(String string) {
        return (FlurryAdType)Enum.valueOf(FlurryAdType.class, (String)string);
    }

    public static FlurryAdType[] values() {
        return (FlurryAdType[])a.clone();
    }
}


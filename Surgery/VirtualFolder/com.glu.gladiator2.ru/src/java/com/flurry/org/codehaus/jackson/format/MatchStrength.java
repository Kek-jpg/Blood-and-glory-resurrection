/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.format;

public final class MatchStrength
extends Enum<MatchStrength> {
    private static final /* synthetic */ MatchStrength[] $VALUES;
    public static final /* enum */ MatchStrength FULL_MATCH;
    public static final /* enum */ MatchStrength INCONCLUSIVE;
    public static final /* enum */ MatchStrength NO_MATCH;
    public static final /* enum */ MatchStrength SOLID_MATCH;
    public static final /* enum */ MatchStrength WEAK_MATCH;

    static {
        NO_MATCH = new MatchStrength();
        INCONCLUSIVE = new MatchStrength();
        WEAK_MATCH = new MatchStrength();
        SOLID_MATCH = new MatchStrength();
        FULL_MATCH = new MatchStrength();
        MatchStrength[] arrmatchStrength = new MatchStrength[]{NO_MATCH, INCONCLUSIVE, WEAK_MATCH, SOLID_MATCH, FULL_MATCH};
        $VALUES = arrmatchStrength;
    }

    public static MatchStrength valueOf(String string) {
        return (MatchStrength)Enum.valueOf(MatchStrength.class, (String)string);
    }

    public static MatchStrength[] values() {
        return (MatchStrength[])$VALUES.clone();
    }
}


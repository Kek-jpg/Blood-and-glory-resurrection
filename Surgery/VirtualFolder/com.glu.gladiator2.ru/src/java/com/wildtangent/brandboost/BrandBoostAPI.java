/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.wildtangent.brandboost;

public interface BrandBoostAPI {
    public boolean isCampaignAvailable();

    public void itemGranted(String var1);

    public void launch();

    public String retrieveItemKey();

    public void setAutoHover(boolean var1);

    public void setHoverPosition(Position var1);

    public void showHover(boolean var1);

    public void shutdown();

    public static final class Position
    extends Enum<Position> {
        private static final /* synthetic */ Position[] $VALUES;
        public static final /* enum */ Position Center;
        public static final /* enum */ Position East;
        public static final /* enum */ Position North;
        public static final /* enum */ Position Northeast;
        public static final /* enum */ Position Northwest;
        public static final /* enum */ Position South;
        public static final /* enum */ Position Southeast;
        public static final /* enum */ Position Southwest;
        public static final /* enum */ Position West;

        static {
            North = new Position();
            Northeast = new Position();
            East = new Position();
            Southeast = new Position();
            South = new Position();
            Southwest = new Position();
            West = new Position();
            Northwest = new Position();
            Center = new Position();
            Position[] arrposition = new Position[]{North, Northeast, East, Southeast, South, Southwest, West, Northwest, Center};
            $VALUES = arrposition;
        }

        public static Position valueOf(String string) {
            return (Position)Enum.valueOf(Position.class, (String)string);
        }

        public static Position[] values() {
            return (Position[])$VALUES.clone();
        }
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.util.ArrayList
 *  java.util.HashSet
 */
package com.amazon.device.ads;

import com.amazon.device.ads.AdProperties;
import java.util.ArrayList;
import java.util.HashSet;

class Ad {
    private ArrayList<AAXCreative> creativeTypes_;
    private String creative_;
    private int height_ = 0;
    private boolean isRendering_ = false;
    private String pixelUrl_;
    private AdProperties properties_;
    private int width_ = 0;

    Ad() {
    }

    protected String getCreative() {
        return this.creative_;
    }

    protected ArrayList<AAXCreative> getCreativeTypes() {
        return this.creativeTypes_;
    }

    protected int getHeight() {
        return this.height_;
    }

    protected boolean getIsRendering() {
        return this.isRendering_;
    }

    protected String getPixelUrl() {
        return this.pixelUrl_;
    }

    protected AdProperties getProperties() {
        return this.properties_;
    }

    protected int getWidth() {
        return this.width_;
    }

    protected void setCreative(String string) {
        this.creative_ = string;
    }

    protected void setCreativeTypes(ArrayList<AAXCreative> arrayList) {
        this.creativeTypes_ = arrayList;
    }

    protected void setHeight(int n2) {
        this.height_ = n2;
    }

    protected void setIsRendering(boolean bl) {
        this.isRendering_ = bl;
    }

    protected void setPixelUrl(String string) {
        this.pixelUrl_ = string;
    }

    protected void setProperties(AdProperties adProperties) {
        this.properties_ = adProperties;
    }

    protected void setWidth(int n2) {
        this.width_ = n2;
    }

    protected static final class AAXCreative
    extends Enum<AAXCreative> {
        private static final /* synthetic */ AAXCreative[] $VALUES;
        public static final /* enum */ AAXCreative HTML = new AAXCreative(1007, "com.amazon.device.ads.HtmlRenderer");
        public static final /* enum */ AAXCreative MRAID1 = new AAXCreative(1016, "com.amazon.device.ads.MraidRenderer");
        private final String class_;
        private final int id_;

        static {
            AAXCreative[] arraAXCreative = new AAXCreative[]{HTML, MRAID1};
            $VALUES = arraAXCreative;
        }

        private AAXCreative(int n3, String string2) {
            this.id_ = n3;
            this.class_ = string2;
        }

        static ArrayList<AAXCreative> determineTypeOrder(HashSet<AAXCreative> hashSet) {
            ArrayList arrayList = new ArrayList(2);
            if (hashSet.contains((Object)MRAID1)) {
                arrayList.add((Object)MRAID1);
            }
            if (hashSet.contains((Object)HTML)) {
                arrayList.add((Object)HTML);
            }
            return arrayList;
        }

        static AAXCreative getCreative(int n2) {
            switch (n2) {
                default: {
                    throw new UnsupportedOperationException("Invalid creative type: " + n2);
                }
                case 1007: {
                    return HTML;
                }
                case 1016: 
            }
            return MRAID1;
        }

        public static AAXCreative valueOf(String string) {
            return (AAXCreative)Enum.valueOf(AAXCreative.class, (String)string);
        }

        public static AAXCreative[] values() {
            return (AAXCreative[])$VALUES.clone();
        }

        String getClassName() {
            return this.class_;
        }
    }

}


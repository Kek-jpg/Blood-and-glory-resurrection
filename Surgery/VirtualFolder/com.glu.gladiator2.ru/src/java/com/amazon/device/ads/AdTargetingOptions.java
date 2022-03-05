/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 */
package com.amazon.device.ads;

import java.util.HashMap;
import java.util.Map;

public class AdTargetingOptions {
    private Map<String, String> advanced_ = new HashMap();
    private int age_ = -1;
    private boolean enableGeoTargeting_ = false;
    private Gender gender_ = Gender.UNKNOWN;

    public boolean containsAdvancedOption(String string) {
        return this.advanced_.containsKey((Object)string);
    }

    public AdTargetingOptions enableGeoLocation(boolean bl) {
        this.enableGeoTargeting_ = bl;
        return this;
    }

    public String getAdvancedOption(String string) {
        return (String)this.advanced_.get((Object)string);
    }

    public int getAge() {
        return this.age_;
    }

    protected HashMap<String, String> getCopyOfAdvancedOptions() {
        HashMap hashMap = new HashMap(this.advanced_.size());
        hashMap.putAll(this.advanced_);
        return hashMap;
    }

    public Gender getGender() {
        return this.gender_;
    }

    public boolean isGeoLocationEnabled() {
        return this.enableGeoTargeting_;
    }

    public AdTargetingOptions setAdvancedOption(String string, String string2) {
        if (string2 != null) {
            this.advanced_.put((Object)string, (Object)string2);
            return this;
        }
        this.advanced_.remove((Object)string);
        return this;
    }

    public AdTargetingOptions setAge(int n2) {
        this.age_ = n2;
        return this;
    }

    public AdTargetingOptions setGender(Gender gender) {
        this.gender_ = gender;
        return this;
    }

    public static final class Gender
    extends Enum<Gender> {
        private static final /* synthetic */ Gender[] $VALUES;
        public static final /* enum */ Gender FEMALE;
        public static final /* enum */ Gender MALE;
        public static final /* enum */ Gender UNKNOWN;
        public final String gender;

        static {
            UNKNOWN = new Gender("unknown");
            MALE = new Gender("male");
            FEMALE = new Gender("female");
            Gender[] arrgender = new Gender[]{UNKNOWN, MALE, FEMALE};
            $VALUES = arrgender;
        }

        private Gender(String string2) {
            this.gender = string2;
        }

        public static Gender valueOf(String string) {
            return (Gender)Enum.valueOf(Gender.class, (String)string);
        }

        public static Gender[] values() {
            return (Gender[])$VALUES.clone();
        }
    }

}


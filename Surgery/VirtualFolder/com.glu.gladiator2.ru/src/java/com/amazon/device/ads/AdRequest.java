/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.location.Location
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.amazon.device.ads;

import android.app.Activity;
import android.location.Location;
import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdBridge;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdLocation;
import com.amazon.device.ads.AdTargetingOptions;
import com.amazon.device.ads.Configuration;
import com.amazon.device.ads.DebugProperties;
import com.amazon.device.ads.InternalAdRegistration;
import com.amazon.device.ads.Log;
import com.amazon.device.ads.Metrics;
import com.amazon.device.ads.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class AdRequest {
    protected static String LOG_TAG = "AdRequest";
    protected AdBridge bridge_;
    protected AdTargetingOptions opt_;
    protected AdLayout.AdSize size_;
    protected int timeout_;
    protected StringBuilder url_ = new StringBuilder("http://");
    protected String userAgent_;
    protected int windowHeight_;
    protected int windowWidth_;

    public AdRequest(AdBridge adBridge, AdTargetingOptions adTargetingOptions, AdLayout.AdSize adSize, int n2, int n3, String string, int n4) {
        this.bridge_ = adBridge;
        this.opt_ = adTargetingOptions;
        this.size_ = adSize;
        this.userAgent_ = string;
        this.timeout_ = n4;
        this.windowHeight_ = n3;
        this.windowWidth_ = n2;
        this.initialize();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getAAXParam(AAXParameters aAXParameters, HashMap<String, String> hashMap) {
        Object object = aAXParameters.getDefaultValue();
        if (hashMap.containsKey((Object)aAXParameters.name_)) {
            object = (String)hashMap.get((Object)aAXParameters.name_);
            hashMap.remove((Object)aAXParameters.name_);
        }
        if (object == null) {
            if (aAXParameters == AAXParameters.SIZE) {
                object = this.size_.size;
            } else if (aAXParameters == AAXParameters.APPID) {
                object = this.bridge_.getAdRegistration().getAppId();
            } else if (aAXParameters == AAXParameters.ADID) {
                object = this.bridge_.getAdRegistration().getAmazonDeviceId();
                if (object != null && !object.equals((Object)"")) {
                    Metrics.getInstance().incrementMetric(this.bridge_.getAd(), Metrics.MetricType.AD_COUNTER_IDENTIFIED_DEVICE);
                }
            } else if (aAXParameters == AAXParameters.USER_AGENT) {
                object = Utils.getURLEncodedString(this.userAgent_);
            } else if (aAXParameters == AAXParameters.DEVICE_INFO && this.bridge_.getAdRegistration().getDeviceNativeData().json != null) {
                this.bridge_.getAdRegistration().deviceNativeData_.orientation = Utils.isPortrait(this.bridge_.getActivity()) ? "portrait" : "landscape";
                object = this.bridge_.getAdRegistration().getDeviceNativeData().getJsonEncodedWithOrientation();
            } else if (aAXParameters == AAXParameters.USER_INFO) {
                boolean bl = false;
                JSONObject jSONObject = new JSONObject();
                try {
                    int n2 = this.opt_.getAge();
                    bl = false;
                    if (n2 != -1) {
                        jSONObject.put("age", (Object)String.valueOf((int)this.opt_.getAge()));
                        bl = true;
                    }
                    if (this.opt_.getGender() != AdTargetingOptions.Gender.UNKNOWN) {
                        jSONObject.put("gender", (Object)this.opt_.getGender().gender);
                        bl = true;
                    }
                }
                catch (JSONException jSONException) {
                    Log.w(LOG_TAG, "JSON error creating 'ui' object: %s", new Object[]{jSONException});
                }
                if (bl) {
                    object = Utils.getURLEncodedString(jSONObject.toString());
                }
            } else if (aAXParameters == AAXParameters.TEST) {
                object = this.bridge_.getAdRegistration().getTestMode() ? "true" : null;
            } else if (aAXParameters == AAXParameters.GEOLOCATION) {
                Location location;
                object = this.opt_.isGeoLocationEnabled() ? ((location = AdLocation.getInstance().getLocation()) == null ? null : location.getLatitude() + "," + location.getLongitude()) : null;
            } else if (aAXParameters == AAXParameters.SHA1_UDID) {
                object = this.bridge_.getAdRegistration().deviceInfo_.sha1_udid == null ? null : this.bridge_.getAdRegistration().deviceInfo_.sha1_udid;
            } else if (aAXParameters == AAXParameters.MD5_UDID) {
                object = this.bridge_.getAdRegistration().deviceInfo_.md5_udid == null ? null : this.bridge_.getAdRegistration().deviceInfo_.md5_udid;
            } else if (aAXParameters == AAXParameters.SLOT) {
                object = Utils.isPortrait(this.bridge_.getActivity()) ? "portrait" : "landscape";
            }
        }
        if (!DebugProperties.isDebugModeOn()) return object;
        return DebugProperties.getDebugProperties().getProperty(aAXParameters.getDebugName(), (String)object);
    }

    public AdBridge getAdBridge() {
        return this.bridge_;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void initialize() {
        this.url_.append(Configuration.getInstance().getAAXEndpoint());
        boolean bl = true;
        HashMap<String, String> hashMap = this.opt_.getCopyOfAdvancedOptions();
        for (AAXParameters aAXParameters : AAXParameters.values()) {
            String string = this.getAAXParam(aAXParameters, hashMap);
            if (string == null || string.equals((Object)"")) continue;
            StringBuilder stringBuilder = this.url_;
            StringBuilder stringBuilder2 = new StringBuilder();
            char c2 = bl ? (char)'?' : '&';
            stringBuilder.append(stringBuilder2.append(aAXParameters.getUrlComponent(c2)).append(string).toString());
            bl = false;
        }
        Iterator iterator = hashMap.entrySet().iterator();
        do {
            if (!iterator.hasNext()) {
                String string = LOG_TAG;
                Object[] arrobject = new Object[]{this.url_};
                Log.d(string, "Generated AAX url: %s", arrobject);
                return;
            }
            Map.Entry entry = (Map.Entry)iterator.next();
            if (entry.getValue() == null || ((String)entry.getValue()).equals((Object)"")) continue;
            StringBuilder stringBuilder = this.url_;
            String string = bl ? "?" : "&";
            stringBuilder.append(string);
            this.url_.append(Utils.getURLEncodedString((String)entry.getKey()));
            this.url_.append("=");
            this.url_.append(Utils.getURLEncodedString((String)entry.getValue()));
            bl = false;
        } while (true);
    }

    static final class AAXParameters
    extends Enum<AAXParameters> {
        private static final /* synthetic */ AAXParameters[] $VALUES;
        public static final /* enum */ AAXParameters ADID;
        public static final /* enum */ AAXParameters APPID;
        public static final /* enum */ AAXParameters ATF;
        public static final /* enum */ AAXParameters CHANNEL;
        public static final /* enum */ AAXParameters DEVICE_INFO;
        public static final /* enum */ AAXParameters GEOLOCATION;
        public static final /* enum */ AAXParameters MD5_UDID;
        public static final /* enum */ AAXParameters PAGE_TYPE;
        public static final /* enum */ AAXParameters PUBLISHER_ASINS;
        public static final /* enum */ AAXParameters PUBLISHER_KEYWORDS;
        public static final /* enum */ AAXParameters SDK_VERSION;
        public static final /* enum */ AAXParameters SHA1_UDID;
        public static final /* enum */ AAXParameters SIZE;
        public static final /* enum */ AAXParameters SLOT;
        public static final /* enum */ AAXParameters SLOT_POSITION;
        public static final /* enum */ AAXParameters TEST;
        public static final /* enum */ AAXParameters USER_AGENT;
        public static final /* enum */ AAXParameters USER_INFO;
        private final String debugName_;
        private final String defaultValue_;
        final String name_;

        static {
            APPID = new AAXParameters("appId", null, "debug.appid");
            CHANNEL = new AAXParameters("c", null, "debug.channel");
            SIZE = new AAXParameters("sz", null, "debug.size");
            PAGE_TYPE = new AAXParameters("pt", null, "debug.pt");
            SLOT = new AAXParameters("slot", null, "debug.slot");
            PUBLISHER_KEYWORDS = new AAXParameters("pk", null, "debug.pk");
            PUBLISHER_ASINS = new AAXParameters("pa", null, "debug.pa");
            USER_AGENT = new AAXParameters("ua", null, "debug.ua");
            SDK_VERSION = new AAXParameters("adsdk", InternalAdRegistration.getInstance().getSDKVersionID(), "debug.ver");
            GEOLOCATION = new AAXParameters("geoloc", null, "debug.geoloc");
            USER_INFO = new AAXParameters("uinfo", null, "debug.ui");
            DEVICE_INFO = new AAXParameters("dinfo", null, "debug.dinfo");
            TEST = new AAXParameters("isTest", null, "debug.test");
            ATF = new AAXParameters("atf", null, "debug.atf");
            ADID = new AAXParameters("ad-id", null, "debug.adid");
            SHA1_UDID = new AAXParameters("sha1_udid", null, "debug.sha1udid");
            MD5_UDID = new AAXParameters("md5_udid", null, "debug.md5udid");
            SLOT_POSITION = new AAXParameters("sp", null, "debug.sp");
            AAXParameters[] arraAXParameters = new AAXParameters[]{APPID, CHANNEL, SIZE, PAGE_TYPE, SLOT, PUBLISHER_KEYWORDS, PUBLISHER_ASINS, USER_AGENT, SDK_VERSION, GEOLOCATION, USER_INFO, DEVICE_INFO, TEST, ATF, ADID, SHA1_UDID, MD5_UDID, SLOT_POSITION};
            $VALUES = arraAXParameters;
        }

        private AAXParameters(String string2, String string3, String string4) {
            this.name_ = string2;
            this.defaultValue_ = string3;
            this.debugName_ = string4;
        }

        public static AAXParameters valueOf(String string) {
            return (AAXParameters)Enum.valueOf(AAXParameters.class, (String)string);
        }

        public static AAXParameters[] values() {
            return (AAXParameters[])$VALUES.clone();
        }

        public String getDebugName() {
            return this.debugName_;
        }

        public String getDefaultValue() {
            return this.defaultValue_;
        }

        public String getUrlComponent(char c2) {
            return c2 + this.name_ + "=";
        }
    }

}


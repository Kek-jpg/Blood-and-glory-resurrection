/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.concurrent.ConcurrentHashMap
 */
package com.amazon.device.ads;

import com.amazon.device.ads.Ad;
import com.amazon.device.ads.Metrics;
import com.amazon.device.ads.Utils;
import java.util.concurrent.ConcurrentHashMap;

class AdMetrics {
    public static final String LOG_TAG = "AdMetrics";
    private Ad ad_;
    private ConcurrentHashMap<Metrics.MetricType, Long> metrics_;

    public AdMetrics(Ad ad2) {
        this.ad_ = ad2;
        this.metrics_ = new ConcurrentHashMap();
    }

    private String getAaxJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"c\":\"msdk\"");
        for (Metrics.MetricType metricType : Metrics.MetricType.values()) {
            Long l2 = (Long)this.metrics_.get((Object)metricType);
            if (l2 == null || metricType.getAaxName() == null) continue;
            stringBuilder.append(",\"" + metricType.getAaxName() + "\":" + (Object)l2);
        }
        return stringBuilder.toString();
    }

    public boolean canSubmit() {
        boolean bl = true;
        String string = this.ad_.getPixelUrl();
        if (string == null || string.equals((Object)"")) {
            bl = false;
        }
        return bl;
    }

    public String getAaxUrl() {
        String string = Utils.getURLEncodedString(this.getAaxJson());
        return this.ad_.getPixelUrl() + string;
    }

    public Long getLong(Metrics.MetricType metricType) {
        Long l2 = (Long)this.metrics_.get((Object)metricType);
        if (l2 != null) {
            return l2;
        }
        return -1L;
    }

    public void put(Metrics.MetricType metricType, long l2) {
        this.metrics_.put((Object)metricType, (Object)l2);
    }
}


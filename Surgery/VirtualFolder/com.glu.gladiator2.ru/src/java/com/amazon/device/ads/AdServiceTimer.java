/*
 * Decompiled with CFR 0.0.
 */
package com.amazon.device.ads;

import com.amazon.device.ads.Ad;
import com.amazon.device.ads.Metrics;
import com.amazon.device.ads.ServiceTimer;

class AdServiceTimer
extends ServiceTimer {
    private Ad ad_;

    public AdServiceTimer(Ad ad2, Metrics.MetricType metricType) {
        super(ad2, metricType, true);
    }

    public AdServiceTimer(Ad ad2, Metrics.MetricType metricType, boolean bl) {
        AdServiceTimer.super.initialize(ad2, metricType, bl);
    }

    private void initialize(Ad ad2, Metrics.MetricType metricType, boolean bl) {
        this.ad_ = ad2;
        this.initialize(metricType, bl);
    }

    @Override
    protected void publishMetric(long l2) {
        Metrics.getInstance().publishMetric(this.ad_, this.getMetric(), l2);
    }
}


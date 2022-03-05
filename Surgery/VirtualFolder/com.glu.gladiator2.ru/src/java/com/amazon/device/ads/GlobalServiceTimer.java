/*
 * Decompiled with CFR 0.0.
 */
package com.amazon.device.ads;

import com.amazon.device.ads.Metrics;
import com.amazon.device.ads.ServiceTimer;

class GlobalServiceTimer
extends ServiceTimer {
    public GlobalServiceTimer(Metrics.MetricType metricType) {
        this.initialize(metricType, true);
    }

    @Override
    protected void publishMetric(long l2) {
        Metrics.getInstance().publishMetric(this.getMetric(), l2);
    }
}


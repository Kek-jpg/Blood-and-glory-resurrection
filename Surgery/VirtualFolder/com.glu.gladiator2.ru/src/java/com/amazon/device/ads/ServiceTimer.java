/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.System
 */
package com.amazon.device.ads;

import com.amazon.device.ads.Metrics;

abstract class ServiceTimer {
    private Metrics.MetricType metric_;
    private long startTime_;

    ServiceTimer() {
    }

    protected Metrics.MetricType getMetric() {
        return this.metric_;
    }

    protected void initialize(Metrics.MetricType metricType, boolean bl) {
        this.metric_ = metricType;
        if (bl) {
            this.start();
        }
    }

    protected abstract void publishMetric(long var1);

    public void setEndTime(long l2) {
        long l3 = (l2 - this.startTime_) / 1000000L;
        this.startTime_ = 0L;
        this.publishMetric(l3);
    }

    public void start() {
        this.startTime_ = System.nanoTime();
    }

    public void stop() {
        long l2 = System.nanoTime();
        if (this.startTime_ == 0L) {
            return;
        }
        this.setEndTime(l2);
    }
}


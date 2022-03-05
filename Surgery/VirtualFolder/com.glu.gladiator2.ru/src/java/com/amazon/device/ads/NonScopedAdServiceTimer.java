/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.System
 *  java.util.HashMap
 */
package com.amazon.device.ads;

import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdServiceTimer;
import com.amazon.device.ads.Metrics;
import com.amazon.device.ads.ServiceTimer;
import java.util.HashMap;

final class NonScopedAdServiceTimer {
    private static HashMap<Ad, ServiceTimer> metrics_ = new HashMap();

    NonScopedAdServiceTimer() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void remove(Ad ad2, Metrics.MetricType metricType) {
        HashMap<Ad, ServiceTimer> hashMap;
        HashMap<Ad, ServiceTimer> hashMap2 = hashMap = metrics_;
        synchronized (hashMap2) {
            metrics_.remove((Object)ad2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void start(Ad ad2, Metrics.MetricType metricType) {
        HashMap<Ad, ServiceTimer> hashMap;
        AdServiceTimer adServiceTimer = new AdServiceTimer(ad2, metricType, false);
        HashMap<Ad, ServiceTimer> hashMap2 = hashMap = metrics_;
        synchronized (hashMap2) {
            metrics_.put((Object)ad2, (Object)adServiceTimer);
        }
        adServiceTimer.start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void stop(Ad ad2, Metrics.MetricType metricType) {
        ServiceTimer serviceTimer;
        HashMap<Ad, ServiceTimer> hashMap;
        long l2 = System.nanoTime();
        HashMap<Ad, ServiceTimer> hashMap2 = hashMap = metrics_;
        synchronized (hashMap2) {
            serviceTimer = (ServiceTimer)metrics_.remove((Object)ad2);
        }
        serviceTimer.setEndTime(l2);
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.HashMap
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.amazon.device.ads;

import android.os.AsyncTask;
import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdMetrics;
import com.amazon.device.ads.AdMetricsSubmitAaxTask;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final class Metrics {
    private static Metrics instance_;
    private HashMap<Ad, AdMetrics> adMetrics_ = new HashMap();
    private HashMap<MetricType, Long> globalMetrics_ = new HashMap();

    private Metrics() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private AdMetrics getAdMetrics(Ad ad2) {
        HashMap<Ad, AdMetrics> hashMap;
        HashMap<Ad, AdMetrics> hashMap2 = hashMap = this.adMetrics_;
        synchronized (hashMap2) {
            if (this.adMetrics_.containsKey((Object)ad2)) {
                return (AdMetrics)this.adMetrics_.get((Object)ad2);
            }
            AdMetrics adMetrics = new AdMetrics(ad2);
            try {
                this.adMetrics_.put((Object)ad2, (Object)adMetrics);
                return adMetrics;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public static final Metrics getInstance() {
        Class<Metrics> class_ = Metrics.class;
        synchronized (Metrics.class) {
            if (instance_ == null) {
                instance_ = new Metrics();
            }
            Metrics metrics = instance_;
            // ** MonitorExit[var2] (shouldn't be in output)
            return metrics;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void submitMetrics(AdMetrics[] arradMetrics) {
        if (arradMetrics.length > 0) {
            HashMap<MetricType, Long> hashMap;
            HashMap<MetricType, Long> hashMap2 = hashMap = this.globalMetrics_;
            synchronized (hashMap2) {
                for (Map.Entry entry : this.globalMetrics_.entrySet()) {
                    arradMetrics[0].put((MetricType)((Object)entry.getKey()), (Long)entry.getValue());
                }
                this.globalMetrics_.clear();
            }
            new AdMetricsSubmitAaxTask().execute((Object[])arradMetrics);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void incrementMetric(Ad ad2, MetricType metricType) {
        HashMap<Ad, AdMetrics> hashMap;
        if (ad2 == null) {
            return;
        }
        HashMap<Ad, AdMetrics> hashMap2 = hashMap = this.adMetrics_;
        synchronized (hashMap2) {
            AdMetrics adMetrics = Metrics.super.getAdMetrics(ad2);
            adMetrics.put(metricType, 1L + adMetrics.getLong(metricType));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void incrementMetric(MetricType metricType) {
        HashMap<MetricType, Long> hashMap;
        HashMap<MetricType, Long> hashMap2 = hashMap = this.globalMetrics_;
        synchronized (hashMap2) {
            Long l2 = (Long)this.globalMetrics_.get((Object)metricType);
            if (l2 == null) {
                l2 = 0L;
            }
            Long l3 = 1L + l2;
            this.globalMetrics_.put((Object)metricType, (Object)l3);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void publishMetric(Ad ad2, MetricType metricType, long l2) {
        HashMap<Ad, AdMetrics> hashMap;
        if (ad2 == null) {
            return;
        }
        HashMap<Ad, AdMetrics> hashMap2 = hashMap = this.adMetrics_;
        synchronized (hashMap2) {
            Metrics.super.getAdMetrics(ad2).put(metricType, l2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void publishMetric(MetricType metricType, long l2) {
        HashMap<MetricType, Long> hashMap;
        HashMap<MetricType, Long> hashMap2 = hashMap = this.globalMetrics_;
        synchronized (hashMap2) {
            this.globalMetrics_.put((Object)metricType, (Object)l2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void submitMetrics(Ad ad2) {
        HashMap<Ad, AdMetrics> hashMap;
        HashMap<Ad, AdMetrics> hashMap2 = hashMap = this.adMetrics_;
        // MONITORENTER : hashMap2
        AdMetrics adMetrics = (AdMetrics)this.adMetrics_.remove((Object)ad2);
        // MONITOREXIT : hashMap2
        if (adMetrics == null) return;
        if (!adMetrics.canSubmit()) return;
        Metrics.super.submitMetrics(new AdMetrics[]{adMetrics});
    }

    static final class MetricType
    extends Enum<MetricType> {
        private static final /* synthetic */ MetricType[] $VALUES;
        public static final /* enum */ MetricType AAX_LATENCY_GET_AD = new MetricType("al");
        public static final /* enum */ MetricType AD_COUNTER_IDENTIFIED_DEVICE = new MetricType("id");
        public static final /* enum */ MetricType AD_COUNTER_RENDERING_FATAL = new MetricType("rf");
        public static final /* enum */ MetricType AD_LATENCY_RENDER = new MetricType("rl");
        public static final /* enum */ MetricType CONFIG_DOWNLOAD_ERROR;
        public static final /* enum */ MetricType CONFIG_DOWNLOAD_LATENCY;
        public static final /* enum */ MetricType CONFIG_PARSE_ERROR;
        public static final /* enum */ MetricType SIS_COUNTER_IDENTIFIED_DEVICE_CHANGED;
        public static final /* enum */ MetricType SIS_LATENCY_PING;
        public static final /* enum */ MetricType SIS_LATENCY_REGISTER;
        public static final /* enum */ MetricType SIS_LATENCY_UPDATE_DEVICE_INFO;
        private final String aaxName_;

        static {
            SIS_COUNTER_IDENTIFIED_DEVICE_CHANGED = new MetricType("sid");
            SIS_LATENCY_PING = new MetricType("spl");
            SIS_LATENCY_REGISTER = new MetricType("srl");
            SIS_LATENCY_UPDATE_DEVICE_INFO = new MetricType("sul");
            CONFIG_DOWNLOAD_ERROR = new MetricType("cde");
            CONFIG_DOWNLOAD_LATENCY = new MetricType("cdt");
            CONFIG_PARSE_ERROR = new MetricType("cpe");
            MetricType[] arrmetricType = new MetricType[]{AAX_LATENCY_GET_AD, AD_COUNTER_IDENTIFIED_DEVICE, AD_COUNTER_RENDERING_FATAL, AD_LATENCY_RENDER, SIS_COUNTER_IDENTIFIED_DEVICE_CHANGED, SIS_LATENCY_PING, SIS_LATENCY_REGISTER, SIS_LATENCY_UPDATE_DEVICE_INFO, CONFIG_DOWNLOAD_ERROR, CONFIG_DOWNLOAD_LATENCY, CONFIG_PARSE_ERROR};
            $VALUES = arrmetricType;
        }

        private MetricType(String string2) {
            this.aaxName_ = string2;
        }

        public static MetricType valueOf(String string) {
            return (MetricType)Enum.valueOf(MetricType.class, (String)string);
        }

        public static MetricType[] values() {
            return (MetricType[])$VALUES.clone();
        }

        public String getAaxName() {
            return this.aaxName_;
        }
    }

}


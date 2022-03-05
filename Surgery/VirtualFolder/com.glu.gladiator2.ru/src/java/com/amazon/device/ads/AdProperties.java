/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  org.json.JSONArray
 */
package com.amazon.device.ads;

import org.json.JSONArray;

public class AdProperties {
    public static final String LOG_TAG = "AdProperties";
    private AdType adType_;
    private boolean canExpand_;
    private boolean canPlayAudio_;
    private boolean canPlayVideo_;

    AdProperties(AdType adType) {
        this.canExpand_ = false;
        this.canPlayAudio_ = false;
        this.canPlayVideo_ = false;
        this.adType_ = adType;
    }

    /*
     * Exception decompiling
     */
    AdProperties(JSONArray var1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.b.a.a.b.as.a(SwitchReplacer.java:478)
        // org.benf.cfr.reader.b.a.a.b.as.a(SwitchReplacer.java:61)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:372)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
        // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
        // org.benf.cfr.reader.b.a(Driver.java:128)
        // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
        // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
        // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
        // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1112)
        // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:587)
        // java.lang.Thread.run(Thread.java:818)
        throw new IllegalStateException("Decompilation failed");
    }

    public boolean canExpand() {
        return this.canExpand_;
    }

    public boolean canPlayAudio() {
        return this.canPlayAudio_;
    }

    public boolean canPlayVideo() {
        return this.canPlayVideo_;
    }

    public AdType getAdType() {
        return this.adType_;
    }

    void setAdType(AdType adType) {
        this.adType_ = adType;
    }

    void setCanExpand(boolean bl) {
        this.canExpand_ = bl;
    }

    void setCanPlayAudio(boolean bl) {
        this.canPlayAudio_ = bl;
    }

    void setCanPlayVideo(boolean bl) {
        this.canPlayVideo_ = bl;
    }

    public static final class AdType
    extends Enum<AdType> {
        private static final /* synthetic */ AdType[] $VALUES;
        public static final /* enum */ AdType IMAGE_BANNER = new AdType("Image Banner");
        public static final /* enum */ AdType MRAID_1 = new AdType("MRAID 1.0");
        public static final /* enum */ AdType MRAID_2 = new AdType("MRAID 2.0");
        private String type_;

        static {
            AdType[] arradType = new AdType[]{IMAGE_BANNER, MRAID_1, MRAID_2};
            $VALUES = arradType;
        }

        private AdType(String string2) {
            this.type_ = string2;
        }

        public static AdType valueOf(String string) {
            return (AdType)Enum.valueOf(AdType.class, (String)string);
        }

        public static AdType[] values() {
            return (AdType[])$VALUES.clone();
        }

        public String toString() {
            return this.type_;
        }
    }

}


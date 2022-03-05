/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.unity3d.player.a;

public interface f {
    public void a();

    public void b();

    public static final class a
    extends Enum {
        public static final /* enum */ a a = new a("INVALID_PACKAGE_NAME", 0);
        public static final /* enum */ a b = new a("NON_MATCHING_UID", 1);
        public static final /* enum */ a c = new a("NOT_MARKET_MANAGED", 2);
        public static final /* enum */ a d;
        public static final /* enum */ a e;
        private static /* enum */ a f;

        static {
            f = new a("CHECK_IN_PROGRESS", 3);
            d = new a("INVALID_PUBLIC_KEY", 4);
            e = new a("MISSING_PERMISSION", 5);
            a[] arra = new a[]{a, b, c, f, d, e};
        }

        /*
         * Exception decompiling
         */
        private a() {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // java.lang.IllegalStateException
            // org.benf.cfr.reader.b.a.f.g.a(VariableFactory.java:53)
            // org.benf.cfr.reader.b.a.a.g.b(Op02WithProcessedDataAndRefs.java:911)
            // org.benf.cfr.reader.b.a.a.g.a(Op02WithProcessedDataAndRefs.java:959)
            // org.benf.cfr.reader.b.a.a.g.a(Op02WithProcessedDataAndRefs.java:56)
            // org.benf.cfr.reader.b.a.a.g$3.a(Op02WithProcessedDataAndRefs.java:2010)
            // org.benf.cfr.reader.b.a.a.g$3.a(Op02WithProcessedDataAndRefs.java:2007)
            // org.benf.cfr.reader.util.d.a.d(AbstractGraphVisitorFI.java:60)
            // org.benf.cfr.reader.b.a.a.g.a(Op02WithProcessedDataAndRefs.java:2019)
            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:340)
            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
            // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.g.p(Method.java:396)
            // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
            // org.benf.cfr.reader.entities.d.c(ClassFile.java:773)
            // org.benf.cfr.reader.entities.d.e(ClassFile.java:870)
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
    }

}


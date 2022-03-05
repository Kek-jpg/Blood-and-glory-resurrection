/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  java.io.BufferedReader
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.lang.Void
 *  java.net.MalformedURLException
 *  java.net.URL
 */
package com.wildtangent.brandboost;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class a {
    private static final String a = "com.wildtangent.brandboost__" + a.class.getSimpleName();
    private Context b;
    private int c;

    public a(Context context) {
        super(context, 30000);
    }

    public a(Context context, int n2) {
        this.b = context;
        this.c = n2;
    }

    static /* synthetic */ Context a(a a2) {
        return a2.b;
    }

    public static URL a(String string) {
        try {
            URL uRL = new URL(string.toString());
            return uRL;
        }
        catch (MalformedURLException malformedURLException) {
            com.wildtangent.brandboost.util.b.a("Failed to construct URL", malformedURLException);
            return null;
        }
    }

    static /* synthetic */ int b(a a2) {
        return a2.c;
    }

    public void a(URL uRL, a a2, int n2, Bundle bundle) {
        (a)this.new c(uRL, a2, n2, bundle).execute((Object[])new Void[0]);
    }

    public static interface a {
        public void a(int var1, int var2, String var3, Bundle var4, Throwable var5);
    }

    private class b {
        public int a;
        public String b;
        public Throwable c;
        final /* synthetic */ a d;

        private b(a a2) {
            this.d = a2;
        }

        /* synthetic */ b(a a2, 1 var2_2) {
            super(a2);
        }
    }

    private class c
    extends AsyncTask<Void, Void, b> {
        private URL b;
        private a c;
        private int d;
        private Bundle e;

        public c(URL uRL, a a3, int n2, Bundle bundle) {
            this.b = uRL;
            this.d = n2;
            this.c = a3;
            this.e = bundle;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private String a(InputStream var1) throws IOException {
            var2_2 = new StringBuilder();
            var3_3 = new BufferedReader((Reader)new InputStreamReader(var1), 4096);
            try {
                while ((var6_4 = var3_3.readLine()) != null) {
                    var2_2.append(var6_4);
                }
                ** GOTO lbl12
            }
            catch (Throwable var4_5) {
                block5 : {
                    var5_8 = var3_3;
                    break block5;
lbl12: // 1 sources:
                    if (var3_3 == null) return var2_2.toString();
                    var3_3.close();
                    return var2_2.toString();
                    catch (Throwable var4_7) {
                        var5_8 = null;
                    }
                }
                if (var5_8 == null) throw var4_6;
                var5_8.close();
                throw var4_6;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private void b(b b2) {
            if (this.c == null || b2 == null) return;
            com.wildtangent.brandboost.util.b.a(a, "Sending success message");
            try {
                this.c.a(this.d, b2.a, b2.b, this.e, null);
                return;
            }
            catch (Throwable throwable) {
                com.wildtangent.brandboost.util.b.a(a, "Callback failure for " + this.d, throwable);
                return;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private void c(b b2) {
            if (this.c == null || b2 == null) return;
            com.wildtangent.brandboost.util.b.a(a, "Error message:" + b2.b);
            try {
                this.c.a(this.d, b2.a, b2.b, this.e, b2.c);
                return;
            }
            catch (Throwable throwable) {
                com.wildtangent.brandboost.util.b.a(a, "Callback failure for " + this.d, throwable);
                return;
            }
        }

        /*
         * Exception decompiling
         */
        protected /* varargs */ b a(Void ... var1) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[CATCHBLOCK]
            // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
            // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
            // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
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

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        protected void a(b var1) {
            if (var1 == null) ** GOTO lbl10
            try {
                switch (var1.a) {
                    default: {
                        c.super.c(var1);
                        return;
                    }
                    case 200: 
                    case 202: 
                    case 204: 
                }
                c.super.b(var1);
                return;
lbl10: // 1 sources:
                com.wildtangent.brandboost.util.b.b(a.a(), "onPostExecute got an invalid response!");
                return;
            }
            catch (Throwable var3_2) {
                com.wildtangent.brandboost.util.b.a(a.a(), "onPostExecute failed!", var3_2);
                var5_3 = new b(a.this, null);
                var5_3.c = var3_2;
                c.super.c(var5_3);
                return;
            }
        }
    }

}


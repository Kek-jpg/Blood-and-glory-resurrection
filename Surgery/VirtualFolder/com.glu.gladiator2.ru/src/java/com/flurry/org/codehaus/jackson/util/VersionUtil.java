/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.regex.Pattern
 */
package com.flurry.org.codehaus.jackson.util;

import com.flurry.org.codehaus.jackson.Version;
import java.util.regex.Pattern;

public class VersionUtil {
    public static final String VERSION_FILE = "VERSION.txt";
    private static final Pattern VERSION_SEPARATOR = Pattern.compile((String)"[-_./;:]");

    /*
     * Enabled aggressive block sorting
     */
    public static Version parseVersion(String string2) {
        String[] arrstring;
        String string3;
        if (string2 == null || (string3 = string2.trim()).length() == 0 || (arrstring = VERSION_SEPARATOR.split((CharSequence)string3)).length < 2) {
            return null;
        }
        int n = VersionUtil.parseVersionPart(arrstring[0]);
        int n2 = VersionUtil.parseVersionPart(arrstring[1]);
        int n3 = arrstring.length;
        int n4 = 0;
        if (n3 > 2) {
            n4 = VersionUtil.parseVersionPart(arrstring[2]);
        }
        int n5 = arrstring.length;
        String string4 = null;
        if (n5 > 3) {
            string4 = arrstring[3];
        }
        return new Version(n, n2, n4, string4);
    }

    protected static int parseVersionPart(String string2) {
        String string3 = string2.toString();
        int n = string3.length();
        int n2 = 0;
        int n3 = 0;
        char c2;
        while (n3 < n && (c2 = string3.charAt(n3)) <= '9' && c2 >= '0') {
            n2 = n2 * 10 + (c2 - 48);
            ++n3;
        }
        return n2;
    }

    /*
     * Exception decompiling
     */
    public static Version versionFor(Class<?> var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.b.a.a.j.b(Op04StructuredStatement.java:409)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:487)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
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
}


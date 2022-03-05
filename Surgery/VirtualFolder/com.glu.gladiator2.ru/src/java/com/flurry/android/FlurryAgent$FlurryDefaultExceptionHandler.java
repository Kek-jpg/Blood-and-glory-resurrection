/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Thread
 *  java.lang.Thread$UncaughtExceptionHandler
 *  java.lang.Throwable
 */
package com.flurry.android;

import com.flurry.android.FlurryAgent;
import com.flurry.android.bc;

public class FlurryAgent$FlurryDefaultExceptionHandler
implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler a = Thread.getDefaultUncaughtExceptionHandler();

    FlurryAgent$FlurryDefaultExceptionHandler() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            FlurryAgent.d().a(throwable);
        }
        catch (Throwable throwable2) {
            bc.b("FlurryAgent", "", throwable2);
        }
        if (this.a != null) {
            this.a.uncaughtException(thread, throwable);
        }
    }
}


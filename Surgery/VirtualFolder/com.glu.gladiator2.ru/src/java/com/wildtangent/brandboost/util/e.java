/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.view.View
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.util.concurrent.Callable
 *  java.util.concurrent.ExecutionException
 *  java.util.concurrent.FutureTask
 */
package com.wildtangent.brandboost.util;

import android.app.Activity;
import android.view.View;
import com.wildtangent.brandboost.util.b;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class e {
    private View a;

    public e(View view) {
        this.a = view;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public <T> T a(Activity activity, Callable<T> callable) {
        Object object;
        try {
            FutureTask futureTask = new FutureTask(callable);
            activity.runOnUiThread((Runnable)futureTask);
            object = futureTask.get();
        }
        catch (InterruptedException interruptedException) {
            b.a("Interrupted when trying to execute task", interruptedException);
            do {
                return null;
                break;
            } while (true);
        }
        catch (ExecutionException executionException) {
            b.a("Failed to execute task", executionException);
            return null;
        }
        return (T)object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public <T> T a(Callable<T> callable) {
        Object object;
        try {
            FutureTask futureTask = new FutureTask(callable);
            this.a.post((Runnable)futureTask);
            object = futureTask.get();
        }
        catch (InterruptedException interruptedException) {
            b.a("Interrupted when trying to execute task", interruptedException);
            do {
                return null;
                break;
            } while (true);
        }
        catch (ExecutionException executionException) {
            b.a("Failed to execute task", executionException);
            return null;
        }
        return (T)object;
    }
}


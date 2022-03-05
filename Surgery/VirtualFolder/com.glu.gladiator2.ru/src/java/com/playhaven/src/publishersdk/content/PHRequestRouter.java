/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.util.Hashtable
 */
package com.playhaven.src.publishersdk.content;

import android.net.Uri;
import java.util.Hashtable;

public class PHRequestRouter {
    private static Uri mCurUrl;
    Hashtable<String, Runnable> mRoutes = new Hashtable();

    public static void clearCurrentURL() {
        mCurUrl = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getCurrentQueryVar(String string2) {
        Class<PHRequestRouter> class_ = PHRequestRouter.class;
        synchronized (PHRequestRouter.class) {
            if (mCurUrl == null) {
                // ** MonitorExit[var3_1] (shouldn't be in output)
                return null;
            }
            String string3 = mCurUrl.getQueryParameter(string2);
            if (string3 != null && !string3.equals((Object)"")) {
                // ** MonitorExit[var3_1] (shouldn't be in output)
                return string3;
            }
            // ** MonitorExit[var3_1] (shouldn't be in output)
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getCurrentURL() {
        Class<PHRequestRouter> class_ = PHRequestRouter.class;
        synchronized (PHRequestRouter.class) {
            if (mCurUrl == null) return null;
            return mCurUrl.toString();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String stripQuery(String string2) {
        int n;
        if (string2.indexOf("?") > 0) {
            n = string2.indexOf("?");
            do {
                return string2.substring(0, n);
                break;
            } while (true);
        }
        n = string2.length();
        return string2.substring(0, n);
    }

    public void addRoute(String string2, Runnable runnable) {
        this.mRoutes.put((Object)string2, (Object)runnable);
    }

    public void clearRoutes() {
        this.mRoutes.clear();
    }

    public boolean hasRoute(String string2) {
        return this.mRoutes.containsKey((Object)PHRequestRouter.super.stripQuery(string2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void route(String string2) {
        void var5_2 = this;
        synchronized (var5_2) {
            mCurUrl = Uri.parse((String)string2);
            if (mCurUrl == null) {
                return;
            }
            String string3 = PHRequestRouter.super.stripQuery(string2);
            Runnable runnable = (Runnable)this.mRoutes.get((Object)string3);
            if (runnable != null) {
                runnable.run();
            }
            return;
        }
    }
}


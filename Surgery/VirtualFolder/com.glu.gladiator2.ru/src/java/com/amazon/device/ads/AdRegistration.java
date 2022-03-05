/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 */
package com.amazon.device.ads;

import android.content.Context;
import com.amazon.device.ads.InternalAdRegistration;

public final class AdRegistration {
    private AdRegistration() {
    }

    public static final void enableLogging(Context context, boolean bl) {
        InternalAdRegistration.getInstance(context).setLoggingEnabled(bl);
    }

    public static final void enableTesting(Context context, boolean bl) {
        InternalAdRegistration.getInstance(context).setTestMode(bl);
    }

    public static final String getVersion(Context context) {
        return InternalAdRegistration.getInstance(context).getSDKVersionID();
    }

    public static final void registerApp(Context context) {
        InternalAdRegistration.getInstance(context).registerIfNeeded();
    }

    public static final void setAppGUID(Context context, String string) throws IllegalArgumentException {
        InternalAdRegistration.getInstance(context).setApplicationId(string);
    }

    public static final void setAppKey(Context context, String string) throws IllegalArgumentException {
        InternalAdRegistration.getInstance(context).setApplicationId(string);
    }

    public static final void setAppUniqueId(Context context, String string) throws IllegalArgumentException {
        InternalAdRegistration.getInstance(context).setApplicationId(string);
    }
}


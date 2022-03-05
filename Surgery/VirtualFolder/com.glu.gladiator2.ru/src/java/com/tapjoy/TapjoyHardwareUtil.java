/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Field
 */
package com.tapjoy;

import android.os.Build;
import com.tapjoy.TapjoyLog;
import java.lang.reflect.Field;

public class TapjoyHardwareUtil {
    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String getSerial() {
        String string2;
        try {
            String string3;
            Field field = Class.forName((String)"android.os.Build").getDeclaredField("SERIAL");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            string2 = string3 = field.get(Build.class).toString();
        }
        catch (Exception exception) {
            string2 = null;
            Exception exception2 = exception;
            TapjoyLog.e("TapjoyHardwareUtil", exception2.toString());
            return string2;
        }
        TapjoyLog.i("TapjoyHardwareUtil", "serial: " + string2);
        return string2;
        {
            catch (Exception exception) {}
        }
    }
}


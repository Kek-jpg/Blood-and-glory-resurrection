/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Closeable
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.android;

import com.flurry.android.InstallReceiver;
import com.flurry.android.bc;
import com.flurry.android.y;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;

final class av
implements Runnable {
    private /* synthetic */ String a;
    private /* synthetic */ InstallReceiver b;

    av(InstallReceiver installReceiver, String string) {
        this.b = installReceiver;
        this.a = string;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void run() {
        block10 : {
            var1_1 = null;
            var5_2 = InstallReceiver.a(this.b).getParentFile();
            "dir is..." + var5_2.toString();
            var7_3 = var5_2.mkdirs();
            var1_1 = null;
            if (var7_3 || var5_2.exists()) break block10;
            bc.b("InstallReceiver", "Unable to create persistent dir: " + (Object)var5_2);
            y.a(null);
            return;
        }
        var8_4 = new FileOutputStream(InstallReceiver.a(this.b));
        try {
            var8_4.write(this.a.getBytes());
            for (Map.Entry var10_6 : InstallReceiver.a(InstallReceiver.a(InstallReceiver.a(this.b))).entrySet()) {
                "entry: " + (String)var10_6.getKey() + "=" + var10_6.getValue();
            }
            ** GOTO lbl23
        }
        catch (Throwable var2_7) {
            block11 : {
                var1_1 = var8_4;
                break block11;
lbl23: // 1 sources:
                y.a((Closeable)var8_4);
                return;
                catch (Throwable var3_12) {
                    var1_1 = var8_4;
                    ** GOTO lbl-1000
                }
            }
lbl29: // 2 sources:
            do {
                try {
                    bc.b("InstallReceiver", "", (Throwable)var2_8);
                }
                catch (Throwable var3_10) lbl-1000: // 2 sources:
                {
                    y.a(var1_1);
                    throw var3_11;
                }
                y.a(var1_1);
                return;
                break;
            } while (true);
        }
        catch (Throwable var2_9) {
            var1_1 = null;
            ** continue;
        }
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  java.io.Closeable
 *  java.io.DataInputStream
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.Void
 */
package com.flurry.android;

import android.os.AsyncTask;
import com.flurry.android.bc;
import com.flurry.android.be;
import com.flurry.android.y;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

final class j
extends AsyncTask<Void, Object, Object> {
    private /* synthetic */ be a;

    j(be be2) {
        this.a = be2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private /* varargs */ Object a(Void ... var1) {
        block13 : {
            if (!be.a(this.a).exists()) ** GOTO lbl13
            var2_2 = new DataInputStream((InputStream)new FileInputStream(be.a(this.a)));
            try {
                if (var2_2.readUnsignedShort() != 46586) break block13;
                this.a.a(var2_2);
            }
            catch (Throwable var3_6) {
                ** continue;
            }
        }
        y.a((Closeable)var2_2);
lbl9: // 2 sources:
        do {
            if (be.b(this.a) || !(var8_3 = be.a(this.a).delete())) {
                // empty if block
            }
lbl13: // 4 sources:
            return var1;
            break;
        } while (true);
        catch (Throwable var3_4) {
            var2_2 = null;
lbl16: // 2 sources:
            do {
                bc.b(be.a, "Error when loading persistent file", (Throwable)var3_5);
                y.a((Closeable)var2_2);
                ** continue;
                break;
            } while (true);
        }
        catch (Throwable var4_7) {
            var2_2 = null;
lbl23: // 2 sources:
            do {
                y.a(var2_2);
                throw var4_8;
                break;
            } while (true);
        }
        catch (Throwable var6_10) {
            bc.b(be.a, "", var6_10);
            return var1;
        }
        {
            catch (Throwable var4_9) {
                ** continue;
            }
        }
    }
}


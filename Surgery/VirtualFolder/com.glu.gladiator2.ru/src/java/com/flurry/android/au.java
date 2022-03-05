/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.Closeable
 *  java.io.DataOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.flurry.android;

import com.flurry.android.y;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

final class au {
    private int a;
    private long b;
    private String c;
    private String d;
    private String e;

    public au(int n2, long l2, String string, String string2, String string3) {
        this.a = n2;
        this.b = l2;
        this.c = string;
        this.d = string2;
        this.e = string3;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public final byte[] a() {
        var1_1 = new ByteArrayOutputStream();
        var2_2 = new DataOutputStream((OutputStream)var1_1);
        try {
            var2_2.writeShort(this.a);
            var2_2.writeLong(this.b);
            var2_2.writeUTF(this.c);
            var2_2.writeUTF(this.d);
            var2_2.writeUTF(this.e);
            var2_2.flush();
            var6_3 = var1_1.toByteArray();
        }
        catch (IOException var3_9) {
            ** continue;
        }
        y.a((Closeable)var2_2);
        return var6_3;
        catch (IOException var8_4) {
            var2_2 = null;
lbl16: // 2 sources:
            do {
                var4_5 = new byte[]{};
                y.a((Closeable)var2_2);
                return var4_5;
                break;
            } while (true);
        }
        catch (Throwable var7_6) {
            var2_2 = null;
            var5_7 = var7_6;
lbl24: // 2 sources:
            do {
                y.a(var2_2);
                throw var5_7;
                break;
            } while (true);
        }
        {
            catch (Throwable var5_8) {
                ** continue;
            }
        }
    }

    public final String b() {
        return this.c;
    }
}


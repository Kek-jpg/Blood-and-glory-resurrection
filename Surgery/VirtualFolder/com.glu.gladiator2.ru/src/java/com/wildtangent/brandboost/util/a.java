/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.os.AsyncTask
 *  java.io.BufferedInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.net.URL
 *  java.net.URLConnection
 */
package com.wildtangent.brandboost.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import com.wildtangent.brandboost.util.b;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public final class a
extends AsyncTask<URL, Integer, Bitmap[]> {
    private static final String a = "com.wildtangent.brandboost__" + a.class.getSimpleName();
    private a b;

    public a() {
    }

    public a(a a2) {
        this.b = a2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Bitmap a(URL var1_1) {
        var2_2 = null;
        var3_3 = null;
        b.a(a.a, "Starting to load image: " + (Object)var1_1);
        var12_4 = var1_1.openConnection();
        var12_4.connect();
        var13_5 = new BufferedInputStream(var12_4.getInputStream());
        var3_3 = BitmapFactory.decodeStream((InputStream)var13_5);
        if (var3_3 != null) {
            b.a(a.a, "Successfully loaded image: " + (Object)var1_1);
        }
        if (var13_5 == null) return var3_3;
        try {
            var13_5.close();
            return var3_3;
        }
        catch (IOException var15_6) {
            b.b(a.a, "Failed to close input stream", var15_6);
            return var3_3;
        }
        catch (IOException var8_7) {}
        ** GOTO lbl-1000
        catch (Throwable var5_13) {
            var2_2 = var13_5;
            ** GOTO lbl-1000
        }
        catch (IOException var8_9) {
            var2_2 = var13_5;
        }
lbl-1000: // 2 sources:
        {
            try {
                b.a(a.a, "Failed to load image", (Throwable)var8_8);
                if (var2_2 == null) return var3_3;
            }
            catch (Throwable var5_11) lbl-1000: // 2 sources:
            {
                if (var2_2 == null) throw var5_12;
                try {
                    var2_2.close();
                }
                catch (IOException var6_14) {
                    b.b(a.a, "Failed to close input stream", var6_14);
                    throw var5_12;
                }
                throw var5_12;
            }
            try {
                var2_2.close();
                return var3_3;
            }
            catch (IOException var10_10) {
                b.b(a.a, "Failed to close input stream", var10_10);
                return var3_3;
            }
        }
    }

    protected void a(Bitmap[] arrbitmap) {
        try {
            this.b.a(arrbitmap);
            return;
        }
        catch (Throwable throwable) {
            b.a(a, "onPostExecute image load callback failed!", throwable);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected /* varargs */ Bitmap[] a(URL ... arruRL) {
        Bitmap[] arrbitmap = null;
        if (arruRL != null) {
            arrbitmap = new Bitmap[arruRL.length];
            Integer n2 = 0;
            for (URL uRL : arruRL) {
                if (!this.isCancelled()) {
                    arrbitmap[n2.intValue()] = a.super.a(uRL);
                    n2 = 1 + n2;
                    this.publishProgress((Object[])new Integer[]{n2});
                    continue;
                }
                b.d(a, "ImageLoadTask canceled.");
            }
        }
        return arrbitmap;
    }

    public static interface a {
        public void a(Bitmap[] var1);
    }

}


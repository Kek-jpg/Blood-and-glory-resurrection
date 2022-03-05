/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.util.Base64
 *  java.lang.ArrayIndexOutOfBoundsException
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.util.Hashtable
 *  java.util.Iterator
 *  java.util.Set
 */
package com.playhaven.resources;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.playhaven.resources.PHResource;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class PHImageResource
extends PHResource {
    private Hashtable<Integer, Bitmap> cached_images = new Hashtable();
    private Hashtable<Integer, String> data_map = new Hashtable();
    protected int densityType = 160;

    /*
     * Enabled aggressive block sorting
     */
    private Bitmap getClosestImage(int n) {
        if (this.data_map.size() == 0) {
            return null;
        }
        Iterator iterator = this.data_map.keySet().iterator();
        int n2 = Integer.MAX_VALUE;
        int n3 = 160;
        while (iterator.hasNext()) {
            int n4;
            int n5;
            Integer n6 = (Integer)iterator.next();
            int n7 = Math.abs((int)(n6 - n));
            if (n7 < n2) {
                n4 = n6;
                n5 = n7;
            } else {
                n4 = n3;
                n5 = n2;
            }
            n2 = n5;
            n3 = n4;
        }
        byte[] arrby = this.getData(n3);
        if (arrby == null) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray((byte[])arrby, (int)0, (int)arrby.length);
        if (bitmap == null) return bitmap;
        bitmap.setDensity(n3);
        return bitmap;
    }

    private Bitmap loadImage() throws ArrayIndexOutOfBoundsException {
        Bitmap bitmap = (Bitmap)this.cached_images.get((Object)new Integer(this.densityType));
        if (bitmap == null) {
            bitmap = this.getClosestImage(this.densityType);
            if (bitmap == null) {
                throw new ArrayIndexOutOfBoundsException("You have not specified image data for the requested density or the image data is invalid");
            }
            this.cached_images.put((Object)new Integer(this.densityType), (Object)bitmap);
        }
        return bitmap;
    }

    @Override
    public byte[] getData() {
        throw new UnsupportedOperationException("You must use getData(density) when loading images");
    }

    public byte[] getData(int n) {
        String string2 = (String)this.data_map.get((Object)new Integer(n));
        if (string2 == null) {
            return null;
        }
        return Base64.decode((byte[])string2.getBytes(), (int)1);
    }

    public Bitmap loadImage(int n) throws ArrayIndexOutOfBoundsException {
        this.densityType = n;
        return PHImageResource.super.loadImage();
    }

    public void setDataStr(int n, String string2) {
        if (string2 != null) {
            this.data_map.put((Object)new Integer(n), (Object)string2);
        }
    }

    @Override
    public void setDataStr(String string2) {
        throw new UnsupportedOperationException("You must use setDataStr(density, data) when setting image data");
    }

    public void setDataStr(int[] arrn, String string2) {
        int n = arrn.length;
        for (int i2 = 0; i2 < n; ++i2) {
            this.setDataStr(arrn[i2], string2);
        }
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.NinePatch
 *  android.graphics.drawable.NinePatchDrawable
 *  java.lang.ArrayIndexOutOfBoundsException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 */
package com.playhaven.resources;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.NinePatch;
import android.graphics.drawable.NinePatchDrawable;
import com.playhaven.resources.PHImageResource;
import java.util.HashMap;

public class PHNinePatchResource
extends PHImageResource {
    private HashMap<Integer, NinePatch> nine_patch_cache = new HashMap();

    private NinePatch loadNinePatch() throws ArrayIndexOutOfBoundsException {
        Bitmap bitmap;
        byte[] arrby;
        block3 : {
            NinePatch ninePatch;
            block2 : {
                ninePatch = (NinePatch)this.nine_patch_cache.get((Object)this.densityType);
                if (ninePatch != null) break block2;
                bitmap = super.loadImage(this.densityType);
                arrby = bitmap.getNinePatchChunk();
                if (NinePatch.isNinePatchChunk((byte[])arrby)) break block3;
                ninePatch = null;
            }
            return ninePatch;
        }
        NinePatch ninePatch = new NinePatch(bitmap, arrby, null);
        this.nine_patch_cache.put((Object)this.densityType, (Object)ninePatch);
        return ninePatch;
    }

    public NinePatch loadNinePatch(int n) {
        this.densityType = n;
        return PHNinePatchResource.super.loadNinePatch();
    }

    public NinePatchDrawable loadNinePatchDrawable(Resources resources, int n) throws ArrayIndexOutOfBoundsException {
        return new NinePatchDrawable(resources, this.loadNinePatch(n));
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Class
 *  java.lang.ClassLoader
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.lang.reflect.Array
 *  java.lang.reflect.Field
 *  java.net.URL
 *  java.util.Collections
 *  java.util.LinkedHashMap
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.jar.JarEntry
 *  java.util.jar.JarFile
 *  java.util.zip.ZipEntry
 */
package com.amazon.device.ads;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

class ResourceLookup {
    private static final int CACHE_SIZE = 101;
    private Class<?> arrr_ = null;
    private Map<String, Integer> cache_ = null;
    private String id_ = null;

    public ResourceLookup(String string, Context context) {
        this.id_ = string.replace('.', '_').replace('-', '_');
        try {
            this.arrr_ = Class.forName((String)(context.getPackageName() + ".R"));
        }
        catch (Throwable throwable) {
            throw new RuntimeException("Exception finding R class", throwable);
        }
        this.cache_ = Collections.synchronizedMap((Map)(ResourceLookup)this.new Cache());
    }

    public static Bitmap bitmapFromJar(Context context, String string) {
        return BitmapFactory.decodeStream((InputStream)ResourceLookup.getResourceFile(context, string));
    }

    private Class<?> getResourceClass(String string) {
        for (Class class_ : this.arrr_.getClasses()) {
            if (!string.equals((Object)class_.getSimpleName())) continue;
            return class_;
        }
        return null;
    }

    public static InputStream getResourceFile(Context context, String string) {
        int n2;
        URL uRL = context.getClassLoader().getResource(string);
        if (uRL == null) {
            try {
                InputStream inputStream = context.getAssets().open(string);
                return inputStream;
            }
            catch (IOException iOException) {
                return null;
            }
        }
        String string2 = uRL.getFile();
        if (string2.startsWith("file:")) {
            string2 = string2.substring(6);
        }
        if ((n2 = string2.indexOf("!")) > 0) {
            string2 = string2.substring(0, n2);
        }
        try {
            JarFile jarFile = new JarFile(string2);
            InputStream inputStream = jarFile.getInputStream((ZipEntry)jarFile.getJarEntry(string));
            return inputStream;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public int getDrawableId(String string) {
        return this.getIdentifier(string, "drawable", true);
    }

    public int getIdentifier(String string, String string2, boolean bl) {
        int n2;
        block6 : {
            Class<?> class_;
            n2 = -1;
            StringBuilder stringBuilder = new StringBuilder(string);
            stringBuilder.append('|');
            stringBuilder.append(string2);
            Integer n3 = (Integer)this.cache_.get((Object)stringBuilder.toString());
            if (n3 != null) {
                return n3;
            }
            if (!string.startsWith(this.id_) && bl) {
                StringBuilder stringBuilder2 = new StringBuilder(this.id_);
                stringBuilder2.append('_');
                stringBuilder2.append(string);
                string = stringBuilder2.toString();
            }
            try {
                class_ = ResourceLookup.super.getResourceClass(string2);
                if (class_ == null) break block6;
            }
            catch (Throwable throwable) {
                throw new RuntimeException("Exception finding resource identifier", throwable);
            }
            Field field = class_.getDeclaredField(string);
            if (field == null) break block6;
            n2 = field.getInt(class_);
            this.cache_.put((Object)stringBuilder.toString(), (Object)n2);
        }
        return n2;
    }

    public int getItemId(String string) {
        return this.getIdentifier(string, "id", false);
    }

    public int getLayoutId(String string) {
        return this.getIdentifier(string, "layout", true);
    }

    public int getRawId(String string) {
        return this.getIdentifier(string, "raw", false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int[] getStyleableArray(String string) {
        int n2;
        Object object;
        int[] arrn;
        try {
            Class<?> class_ = ResourceLookup.super.getResourceClass("styleable");
            if (class_ == null) return new int[0];
            Field field = class_.getDeclaredField(string);
            if (field == null) return new int[0];
            object = field.get(class_);
            if (!(object instanceof int[])) return new int[0];
            arrn = new int[Array.getLength((Object)object)];
            n2 = 0;
        }
        catch (Throwable throwable) {
            throw new RuntimeException("Exception finding styleable", throwable);
        }
        while (n2 < Array.getLength((Object)object)) {
            arrn[n2] = Array.getInt((Object)object, (int)n2);
            ++n2;
        }
        return arrn;
    }

    public int getStyleableId(String string, String string2) {
        return this.getIdentifier(string + "_" + string2, "styleable", false);
    }

    public class Cache
    extends LinkedHashMap<String, Integer> {
        private static final long serialVersionUID = -5258829086548460019L;

        public Cache() {
            super(101, 1.1f, true);
        }

        protected boolean removedEldestEntry(Map.Entry<String, Integer> entry) {
            return this.size() > 101;
        }
    }

}


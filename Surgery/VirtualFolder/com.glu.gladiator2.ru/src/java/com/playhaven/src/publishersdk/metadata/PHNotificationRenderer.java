/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  java.lang.Object
 *  org.json.JSONObject
 */
package com.playhaven.src.publishersdk.metadata;

import android.graphics.Canvas;
import android.graphics.Rect;
import org.json.JSONObject;

public abstract class PHNotificationRenderer {
    public abstract void draw(Canvas var1, JSONObject var2);

    public abstract Rect size(JSONObject var1);
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Rect
 *  android.graphics.drawable.NinePatchDrawable
 *  java.lang.Integer
 *  java.lang.String
 *  org.json.JSONObject
 */
package com.playhaven.src.publishersdk.metadata;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import com.playhaven.resources.PHNinePatchResource;
import com.playhaven.resources.PHResource;
import com.playhaven.resources.PHResourceManager;
import com.playhaven.src.common.PHConfig;
import com.playhaven.src.publishersdk.metadata.PHNotificationRenderer;
import com.playhaven.src.utils.PHConversionUtils;
import com.playhaven.src.utils.PHStringUtil;
import org.json.JSONObject;

public class PHNotificationBadgeRenderer
extends PHNotificationRenderer {
    private final float TEXT_SIZE = 17.0f;
    private final float TEXT_SIZE_REDUCE = 8.0f;
    private NinePatchDrawable badgeImage;
    private Paint whitePaint;

    public PHNotificationBadgeRenderer(Resources resources) {
        this.badgeImage = ((PHNinePatchResource)PHResourceManager.sharedResourceManager().getResource("badge_image")).loadNinePatchDrawable(resources, PHConfig.screen_density_type);
        this.badgeImage.setFilterBitmap(true);
    }

    private Paint getTextPaint() {
        if (this.whitePaint == null) {
            this.whitePaint = new Paint();
            this.whitePaint.setStyle(Paint.Style.FILL);
            this.whitePaint.setAntiAlias(true);
            this.whitePaint.setTextSize(PHConversionUtils.dipToPixels(17.0f));
            this.whitePaint.setColor(-1);
        }
        return this.whitePaint;
    }

    private int requestedValue(JSONObject jSONObject) {
        if (jSONObject == null || jSONObject.isNull("value")) {
            return 0;
        }
        return jSONObject.optInt("value", -1);
    }

    @Override
    public void draw(Canvas canvas, JSONObject jSONObject) {
        int n = PHNotificationBadgeRenderer.super.requestedValue(jSONObject);
        if (n == 0) {
            return;
        }
        Rect rect = this.size(jSONObject);
        this.badgeImage.setBounds(rect);
        this.badgeImage.draw(canvas);
        canvas.drawText(Integer.toString((int)n), PHConversionUtils.dipToPixels(10.0f), PHConversionUtils.dipToPixels(17.0f), PHNotificationBadgeRenderer.super.getTextPaint());
    }

    @Override
    public Rect size(JSONObject jSONObject) {
        float f2 = this.badgeImage.getMinimumWidth();
        float f3 = this.badgeImage.getMinimumHeight();
        if (PHNotificationBadgeRenderer.super.requestedValue(jSONObject) == 0) {
            return new Rect(0, 0, 0, 0);
        }
        float f4 = PHNotificationBadgeRenderer.super.getTextPaint().measureText(String.valueOf((int)PHNotificationBadgeRenderer.super.requestedValue(jSONObject)));
        float f5 = f2 + f4 - PHConversionUtils.dipToPixels(8.0f);
        PHStringUtil.log("Notification Width: " + f5 + " valueWidth: " + f4);
        return new Rect(0, 0, (int)f5, (int)f3);
    }
}


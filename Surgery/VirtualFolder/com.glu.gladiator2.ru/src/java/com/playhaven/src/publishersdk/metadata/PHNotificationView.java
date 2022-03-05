/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.view.View
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Constructor
 *  java.util.HashMap
 *  org.json.JSONObject
 */
package com.playhaven.src.publishersdk.metadata;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import com.playhaven.src.common.PHAPIRequest;
import com.playhaven.src.common.PHCrashReport;
import com.playhaven.src.publishersdk.metadata.PHNotificationBadgeRenderer;
import com.playhaven.src.publishersdk.metadata.PHNotificationRenderer;
import com.playhaven.src.publishersdk.metadata.PHPublisherMetadataRequest;
import com.playhaven.src.utils.PHStringUtil;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import org.json.JSONObject;

public class PHNotificationView
extends View
implements PHAPIRequest.Delegate {
    private static HashMap<String, Class> renderMap = new HashMap();
    private JSONObject notificationData;
    private PHNotificationRenderer notificationRenderer;
    private String placement;
    public PHPublisherMetadataRequest request;

    public PHNotificationView(Context context, String string2) {
        super(context);
        this.placement = string2;
    }

    public static HashMap<String, Class> getRenderMap() {
        return renderMap;
    }

    public static void initRenderers() {
        renderMap.put((Object)"badge", PHNotificationBadgeRenderer.class);
    }

    public static void setRenderer(Class class_, String string2) {
        if (class_.getSuperclass() != PHNotificationRenderer.class) {
            throw new IllegalArgumentException("Cannot create a new renderer of type " + string2 + " because it does not implement the PHNotificationRenderer interface");
        }
        renderMap.put((Object)string2, (Object)class_);
    }

    public void clear() {
        this.request = null;
        this.notificationData = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public PHNotificationRenderer createRenderer(JSONObject jSONObject) {
        PHNotificationRenderer pHNotificationRenderer;
        if (renderMap.size() == 0) {
            PHNotificationView.initRenderers();
        }
        String string2 = jSONObject.optString("type", "badge");
        try {
            Constructor constructor = ((Class)renderMap.get((Object)string2)).getConstructor(new Class[]{Resources.class});
            Object[] arrobject = new Object[]{this.getContext().getResources()};
            pHNotificationRenderer = (PHNotificationRenderer)constructor.newInstance(arrobject);
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHNotificationView - createRenderer", PHCrashReport.Urgency.critical);
            pHNotificationRenderer = null;
        }
        PHStringUtil.log("Created subclass of PHNotificationRenderer of type: " + string2);
        return pHNotificationRenderer;
    }

    public JSONObject getNotificationData() {
        return this.notificationData;
    }

    public PHNotificationRenderer getNotificationRenderer() {
        return this.notificationRenderer;
    }

    public String getPlacement() {
        return this.placement;
    }

    public PHPublisherMetadataRequest getRequest() {
        return this.request;
    }

    protected void onDraw(Canvas canvas) {
        try {
            if (this.notificationRenderer == null) {
                return;
            }
            this.notificationRenderer.draw(canvas, this.notificationData);
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHNotificationView - onDraw", PHCrashReport.Urgency.critical);
            return;
        }
    }

    protected void onMeasure(int n, int n2) {
        try {
            Rect rect = new Rect(0, 0, n, n2);
            if (this.notificationRenderer != null) {
                rect = this.notificationRenderer.size(this.notificationData);
            }
            this.setMeasuredDimension(rect.width(), rect.height());
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHNotificationView - onDraw", PHCrashReport.Urgency.critical);
            return;
        }
    }

    public void refresh() {
        this.request = new PHPublisherMetadataRequest(this.getContext(), this, this.placement);
        this.request.send();
    }

    @Override
    public void requestFailed(PHAPIRequest pHAPIRequest, Exception exception) {
        this.request = null;
        this.updateNotificationData(null);
    }

    @Override
    public void requestSucceeded(PHAPIRequest pHAPIRequest, JSONObject jSONObject) {
        this.request = null;
        JSONObject jSONObject2 = jSONObject.optJSONObject("notification");
        if (!JSONObject.NULL.equals((Object)jSONObject2) && jSONObject2.length() > 0) {
            this.updateNotificationData(jSONObject2);
        }
    }

    public void updateNotificationData(JSONObject jSONObject) {
        if (jSONObject == null) {
            return;
        }
        try {
            this.notificationData = jSONObject;
            this.notificationRenderer = this.createRenderer(jSONObject);
            this.requestLayout();
            this.invalidate();
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHNotificationView - updateNotificationData", PHCrashReport.Urgency.critical);
            return;
        }
    }
}


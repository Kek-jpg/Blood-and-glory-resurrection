/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.Object
 *  java.lang.String
 *  org.json.JSONObject
 */
package com.playhaven.src.publishersdk.content;

import android.content.Context;
import com.playhaven.src.common.PHAPIRequest;
import com.playhaven.src.publishersdk.content.PHContentView;
import com.playhaven.src.utils.PHStringUtil;
import org.json.JSONObject;

public class PHPublisherSubContentRequest
extends PHAPIRequest {
    public String callback;
    public PHContentView source;

    public PHPublisherSubContentRequest(Context context, PHAPIRequest.Delegate delegate) {
        super(context, delegate);
    }

    @Override
    public String getURL() {
        if (this.fullUrl == null) {
            this.fullUrl = this.baseURL();
        }
        return this.fullUrl;
    }

    @Override
    public void send() {
        if (!JSONObject.NULL.equals((Object)this.baseURL()) && this.baseURL().length() > 0) {
            super.send();
            return;
        }
        PHStringUtil.log("No URL set for PHPublisherSubContentRequest");
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Hashtable
 */
package com.playhaven.src.publishersdk.metadata;

import android.content.Context;
import com.playhaven.src.common.PHAPIRequest;
import java.util.Hashtable;

public class PHPublisherMetadataRequest
extends PHAPIRequest {
    public String placement;

    public PHPublisherMetadataRequest(Context context, PHAPIRequest.Delegate delegate, String string2) {
        super(context, string2);
        this.setDelegate(delegate);
    }

    public PHPublisherMetadataRequest(Context context, String string2) {
        super(context);
        this.placement = "";
        this.placement = string2;
    }

    @Override
    public String baseURL() {
        return super.createAPIURL("/v3/publisher/content/");
    }

    @Override
    public Hashtable<String, String> getAdditionalParams() {
        Hashtable hashtable = new Hashtable();
        if (this.placement != null) {
            hashtable.put((Object)"placement_id", (Object)this.placement);
        }
        hashtable.put((Object)"metadata", (Object)"1");
        return hashtable;
    }
}


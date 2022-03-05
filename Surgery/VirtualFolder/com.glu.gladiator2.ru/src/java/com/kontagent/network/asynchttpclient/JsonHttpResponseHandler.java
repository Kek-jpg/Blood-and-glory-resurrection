/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.json.JSONTokener
 */
package com.kontagent.network.asynchttpclient;

import com.kontagent.network.asynchttpclient.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonHttpResponseHandler
extends AsyncHttpResponseHandler {
    @Override
    protected void handleSuccessMessage(String string2) {
        super.handleSuccessMessage(string2);
        try {
            Object object = this.parseResponse(string2);
            if (object instanceof JSONObject) {
                this.onSuccess((JSONObject)object);
                return;
            }
            if (object instanceof JSONArray) {
                this.onSuccess((JSONArray)object);
                return;
            }
        }
        catch (JSONException jSONException) {
            this.onFailure(jSONException, string2);
        }
    }

    public void onSuccess(JSONArray jSONArray) {
    }

    public void onSuccess(JSONObject jSONObject) {
    }

    protected Object parseResponse(String string2) throws JSONException {
        return new JSONTokener(string2).nextValue();
    }
}


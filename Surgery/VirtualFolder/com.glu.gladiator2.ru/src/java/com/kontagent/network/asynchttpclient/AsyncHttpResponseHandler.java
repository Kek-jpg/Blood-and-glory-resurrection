/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.HttpResponseException
 *  org.apache.http.entity.BufferedHttpEntity
 *  org.apache.http.util.EntityUtils
 */
package com.kontagent.network.asynchttpclient;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

public class AsyncHttpResponseHandler {
    private static final int FAILURE_MESSAGE = 1;
    private static final int FINISH_MESSAGE = 3;
    private static final int START_MESSAGE = 2;
    private static final int SUCCESS_MESSAGE;
    private Handler handler;

    public AsyncHttpResponseHandler() {
        if (Looper.myLooper() != null) {
            this.handler = new Handler(){

                public void handleMessage(Message message) {
                    AsyncHttpResponseHandler.this.handleMessage(message);
                }
            };
        }
    }

    protected void handleFailureMessage(Throwable throwable, String string2) {
        this.onFailure(throwable, string2);
    }

    protected void handleMessage(Message message) {
        switch (message.what) {
            default: {
                return;
            }
            case 0: {
                this.handleSuccessMessage((String)message.obj);
                return;
            }
            case 1: {
                Object[] arrobject = (Object[])message.obj;
                this.handleFailureMessage((Throwable)arrobject[0], (String)arrobject[1]);
                return;
            }
            case 2: {
                this.onStart();
                return;
            }
            case 3: 
        }
        this.onFinish();
    }

    protected void handleSuccessMessage(String string2) {
        this.onSuccess(string2);
    }

    protected Message obtainMessage(int n, Object object) {
        if (this.handler != null) {
            return this.handler.obtainMessage(n, object);
        }
        Message message = new Message();
        message.what = n;
        message.obj = object;
        return message;
    }

    public void onFailure(Throwable throwable) {
    }

    public void onFailure(Throwable throwable, String string2) {
        this.onFailure(throwable);
    }

    public void onFinish() {
    }

    public void onStart() {
    }

    public void onSuccess(String string2) {
    }

    protected void sendFailureMessage(Throwable throwable, String string2) {
        this.sendMessage(this.obtainMessage(1, new Object[]{throwable, string2}));
    }

    protected void sendFinishMessage() {
        this.sendMessage(this.obtainMessage(3, null));
    }

    protected void sendMessage(Message message) {
        if (this.handler != null) {
            this.handler.sendMessage(message);
            return;
        }
        this.handleMessage(message);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void sendResponseMessage(HttpResponse httpResponse) {
        String string2;
        StatusLine statusLine = httpResponse.getStatusLine();
        try {
            String string3;
            HttpEntity httpEntity = httpResponse.getEntity();
            BufferedHttpEntity bufferedHttpEntity = null;
            if (httpEntity != null) {
                bufferedHttpEntity = new BufferedHttpEntity(httpEntity);
            }
            string2 = string3 = EntityUtils.toString(bufferedHttpEntity);
        }
        catch (IOException iOException) {
            this.sendFailureMessage(iOException, null);
            string2 = null;
        }
        if (statusLine.getStatusCode() >= 300) {
            this.sendFailureMessage((Throwable)new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase()), string2);
            return;
        }
        this.sendSuccessMessage(string2);
    }

    protected void sendStartMessage() {
        this.sendMessage(this.obtainMessage(2, null));
    }

    protected void sendSuccessMessage(String string2) {
        this.sendMessage(this.obtainMessage(0, string2));
    }

}


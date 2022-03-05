/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  com.loopj.android.http.AsyncHttpClient
 *  com.loopj.android.http.AsyncHttpResponseHandler
 *  java.io.UnsupportedEncodingException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  org.apache.http.HttpEntity
 *  org.apache.http.entity.StringEntity
 */
package com.glu.platform.gwallet;

import android.content.Context;
import com.glu.platform.gwallet.GWalletDebug;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

public class GWalletJNI {
    private static AsyncHttpClient client = null;
    private static byte[] iv = new byte[]{71, 108, 117, 32, 77, 111, 98, 105, 108, 101, 32, 71, 97, 109, 101, 115};
    private Context m_context;

    private GWalletJNI() {
    }

    /* synthetic */ GWalletJNI(1 var1) {
    }

    public static GWalletJNI getInstance() {
        return InstanceHolder.instance;
    }

    public static native void onHandleResponse(String var0, int var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void GetContent(String string2, String string3) {
        StringEntity stringEntity;
        GWalletDebug.DDD("GWallet.GetContent", "enter");
        try {
            StringEntity stringEntity2;
            stringEntity = stringEntity2 = new StringEntity(string3);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            stringEntity = null;
        }
        GWalletDebug.DDD("GWallet.GetContent", "URL : " + string2);
        GWalletDebug.DDD("GWallet.GetContent", "Request : " + string3);
        if (client == null) {
            client = new AsyncHttpClient();
        }
        client.put(this.getContext(), string2, (HttpEntity)stringEntity, "application/json", new AsyncHttpResponseHandler(){

            public void onFailure(Throwable throwable) {
                GWalletDebug.DDD("GWallet.GetContent", "onFailure", throwable);
                GWalletJNI.onHandleResponse("", 0);
            }

            public void onFinish() {
                GWalletDebug.DDD("GWallet.GetContent", "onFinish");
                GWalletJNI.this.enableHTTPTransfers();
            }

            public void onStart() {
                GWalletDebug.DDD("GWallet.GetContent", "onStart");
            }

            public void onSuccess(String string2) {
                GWalletDebug.DDD("GWallet.GetContent", "onSuccess");
                GWalletDebug.DDD("GWallet.GetContent", "response =" + string2);
                GWalletJNI.onHandleResponse(string2, string2.length());
            }
        });
        GWalletDebug.DDD("GWallet.GetContent", "exit");
    }

    public native void enableHTTPTransfers();

    public Context getContext() {
        return this.m_context;
    }

    public void setContext(Context context) {
        this.m_context = context;
    }

    private static class InstanceHolder {
        public static GWalletJNI instance = new GWalletJNI(null);

        private InstanceHolder() {
        }
    }

}


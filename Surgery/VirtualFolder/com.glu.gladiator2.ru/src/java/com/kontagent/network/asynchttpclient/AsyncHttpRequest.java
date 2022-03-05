/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.net.ConnectException
 *  org.apache.http.HttpResponse
 *  org.apache.http.client.HttpRequestRetryHandler
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.impl.client.AbstractHttpClient
 *  org.apache.http.protocol.HttpContext
 */
package com.kontagent.network.asynchttpclient;

import com.kontagent.network.asynchttpclient.AsyncHttpResponseHandler;
import java.io.IOException;
import java.net.ConnectException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

class AsyncHttpRequest
implements Runnable {
    private final AbstractHttpClient client;
    private final HttpContext context;
    private int executionCount;
    private final HttpUriRequest request;
    private final AsyncHttpResponseHandler responseHandler;

    public AsyncHttpRequest(AbstractHttpClient abstractHttpClient, HttpContext httpContext, HttpUriRequest httpUriRequest, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.client = abstractHttpClient;
        this.context = httpContext;
        this.request = httpUriRequest;
        this.responseHandler = asyncHttpResponseHandler;
    }

    private void makeRequest() throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            HttpResponse httpResponse = this.client.execute(this.request, this.context);
            if (!Thread.currentThread().isInterrupted() && this.responseHandler != null) {
                this.responseHandler.sendResponseMessage(httpResponse);
            }
        }
    }

    private void makeRequestWithRetries() throws ConnectException {
        boolean bl = true;
        IOException iOException = null;
        HttpRequestRetryHandler httpRequestRetryHandler = this.client.getHttpRequestRetryHandler();
        while (bl) {
            try {
                this.makeRequest();
                return;
            }
            catch (IOException iOException2) {
                int n;
                iOException = iOException2;
                this.executionCount = n = 1 + this.executionCount;
                bl = httpRequestRetryHandler.retryRequest(iOException, n, this.context);
            }
            catch (NullPointerException nullPointerException) {
                int n;
                iOException = new IOException("NPE in HttpClient" + nullPointerException.getMessage());
                this.executionCount = n = 1 + this.executionCount;
                bl = httpRequestRetryHandler.retryRequest(iOException, n, this.context);
            }
        }
        ConnectException connectException = new ConnectException();
        connectException.initCause((Throwable)iOException);
        throw connectException;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void run() {
        try {
            if (this.responseHandler != null) {
                this.responseHandler.sendStartMessage();
            }
            this.makeRequestWithRetries();
            if (this.responseHandler == null) return;
            {
                this.responseHandler.sendFinishMessage();
                return;
            }
        }
        catch (IOException iOException) {
            if (this.responseHandler == null) return;
            this.responseHandler.sendFinishMessage();
            this.responseHandler.sendFailureMessage(iOException, null);
            return;
        }
    }
}


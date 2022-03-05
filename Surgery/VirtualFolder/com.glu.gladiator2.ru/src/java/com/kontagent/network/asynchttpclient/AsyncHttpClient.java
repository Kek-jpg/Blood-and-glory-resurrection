/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.ref.WeakReference
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.LinkedList
 *  java.util.List
 *  java.util.Map
 *  java.util.Set
 *  java.util.WeakHashMap
 *  java.util.concurrent.Executors
 *  java.util.concurrent.Future
 *  java.util.concurrent.ThreadPoolExecutor
 *  java.util.zip.GZIPInputStream
 *  org.apache.http.Header
 *  org.apache.http.HeaderElement
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpRequest
 *  org.apache.http.HttpRequestInterceptor
 *  org.apache.http.HttpResponse
 *  org.apache.http.HttpResponseInterceptor
 *  org.apache.http.HttpVersion
 *  org.apache.http.ProtocolVersion
 *  org.apache.http.client.CookieStore
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.HttpRequestRetryHandler
 *  org.apache.http.client.methods.HttpDelete
 *  org.apache.http.client.methods.HttpEntityEnclosingRequestBase
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpPut
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.conn.ClientConnectionManager
 *  org.apache.http.conn.params.ConnManagerParams
 *  org.apache.http.conn.params.ConnPerRoute
 *  org.apache.http.conn.params.ConnPerRouteBean
 *  org.apache.http.conn.scheme.PlainSocketFactory
 *  org.apache.http.conn.scheme.Scheme
 *  org.apache.http.conn.scheme.SchemeRegistry
 *  org.apache.http.conn.scheme.SocketFactory
 *  org.apache.http.conn.ssl.SSLSocketFactory
 *  org.apache.http.entity.HttpEntityWrapper
 *  org.apache.http.impl.client.AbstractHttpClient
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 *  org.apache.http.params.HttpProtocolParams
 *  org.apache.http.protocol.BasicHttpContext
 *  org.apache.http.protocol.HttpContext
 *  org.apache.http.protocol.SyncBasicHttpContext
 */
package com.kontagent.network.asynchttpclient;

import android.content.Context;
import com.kontagent.network.asynchttpclient.AsyncHttpRequest;
import com.kontagent.network.asynchttpclient.AsyncHttpResponseHandler;
import com.kontagent.network.asynchttpclient.RequestParams;
import com.kontagent.network.asynchttpclient.RetryHandler;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

public class AsyncHttpClient {
    private static final int DEFAULT_MAX_CONNECTIONS = 10;
    private static final int DEFAULT_MAX_RETRIES = 5;
    private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    private static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    private static final String ENCODING_GZIP = "gzip";
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String VERSION = "1.3.1";
    private static int maxConnections = 10;
    private static int socketTimeout = 10000;
    private final Map<String, String> clientHeaderMap;
    private final DefaultHttpClient httpClient;
    private final HttpContext httpContext;
    private final Map<Context, List<WeakReference<Future<?>>>> requestMap;
    private ThreadPoolExecutor threadPool;

    public AsyncHttpClient() {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout((HttpParams)basicHttpParams, (long)socketTimeout);
        ConnManagerParams.setMaxConnectionsPerRoute((HttpParams)basicHttpParams, (ConnPerRoute)new ConnPerRouteBean(maxConnections));
        ConnManagerParams.setMaxTotalConnections((HttpParams)basicHttpParams, (int)10);
        HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, (int)socketTimeout);
        HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, (int)socketTimeout);
        HttpConnectionParams.setTcpNoDelay((HttpParams)basicHttpParams, (boolean)true);
        HttpConnectionParams.setSocketBufferSize((HttpParams)basicHttpParams, (int)8192);
        HttpProtocolParams.setVersion((HttpParams)basicHttpParams, (ProtocolVersion)HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent((HttpParams)basicHttpParams, (String)String.format((String)"android-async-http/%s (http://loopj.com/android-async-http)", (Object[])new Object[]{VERSION}));
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", (SocketFactory)PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", (SocketFactory)SSLSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager((HttpParams)basicHttpParams, schemeRegistry);
        this.httpContext = new SyncBasicHttpContext((HttpContext)new BasicHttpContext());
        this.httpClient = new DefaultHttpClient((ClientConnectionManager)threadSafeClientConnManager, (HttpParams)basicHttpParams);
        this.httpClient.addRequestInterceptor(new HttpRequestInterceptor(){

            public void process(HttpRequest httpRequest, HttpContext httpContext) {
                if (!httpRequest.containsHeader(AsyncHttpClient.HEADER_ACCEPT_ENCODING)) {
                    httpRequest.addHeader(AsyncHttpClient.HEADER_ACCEPT_ENCODING, AsyncHttpClient.ENCODING_GZIP);
                }
                for (String string2 : AsyncHttpClient.this.clientHeaderMap.keySet()) {
                    httpRequest.addHeader(string2, (String)AsyncHttpClient.this.clientHeaderMap.get((Object)string2));
                }
            }
        });
        this.httpClient.addResponseInterceptor(new HttpResponseInterceptor(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public void process(HttpResponse httpResponse, HttpContext httpContext) {
                Header header = httpResponse.getEntity().getContentEncoding();
                if (header == null) return;
                HeaderElement[] arrheaderElement = header.getElements();
                int n = arrheaderElement.length;
                int n2 = 0;
                while (n2 < n) {
                    if (arrheaderElement[n2].getName().equalsIgnoreCase(AsyncHttpClient.ENCODING_GZIP)) {
                        httpResponse.setEntity((HttpEntity)new InflatingEntity(httpResponse.getEntity()));
                        return;
                    }
                    ++n2;
                }
            }
        });
        this.httpClient.setHttpRequestRetryHandler((HttpRequestRetryHandler)new RetryHandler(5));
        this.threadPool = (ThreadPoolExecutor)Executors.newCachedThreadPool();
        this.requestMap = new WeakHashMap();
        this.clientHeaderMap = new HashMap();
    }

    private HttpEntityEnclosingRequestBase addEntityToRequestBase(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, HttpEntity httpEntity) {
        if (httpEntity != null) {
            httpEntityEnclosingRequestBase.setEntity(httpEntity);
        }
        return httpEntityEnclosingRequestBase;
    }

    private String getUrlWithQueryString(String string2, RequestParams requestParams) {
        if (requestParams != null) {
            String string3 = requestParams.getParamString();
            string2 = string2 + "?" + string3;
        }
        return string2;
    }

    private HttpEntity paramsToEntity(RequestParams requestParams) {
        HttpEntity httpEntity = null;
        if (requestParams != null) {
            httpEntity = requestParams.getEntity();
        }
        return httpEntity;
    }

    private void sendRequest(DefaultHttpClient defaultHttpClient, HttpContext httpContext, HttpUriRequest httpUriRequest, String string2, AsyncHttpResponseHandler asyncHttpResponseHandler, Context context) {
        if (string2 != null) {
            httpUriRequest.addHeader("Content-Type", string2);
        }
        Future future = this.threadPool.submit((Runnable)new AsyncHttpRequest((AbstractHttpClient)defaultHttpClient, httpContext, httpUriRequest, asyncHttpResponseHandler));
        if (context != null) {
            List list = (List)this.requestMap.get((Object)context);
            if (list == null) {
                list = new LinkedList();
                this.requestMap.put((Object)context, (Object)list);
            }
            list.add((Object)new WeakReference((Object)future));
        }
    }

    public void addHeader(String string2, String string3) {
        this.clientHeaderMap.put((Object)string2, (Object)string3);
    }

    public void cancelRequests(Context context, boolean bl) {
        List list = (List)this.requestMap.get((Object)context);
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Future future = (Future)((WeakReference)iterator.next()).get();
                if (future == null) continue;
                future.cancel(bl);
            }
        }
        this.requestMap.remove((Object)context);
    }

    public void delete(Context context, String string2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        HttpDelete httpDelete = new HttpDelete(string2);
        AsyncHttpClient.super.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)httpDelete, null, asyncHttpResponseHandler, context);
    }

    public void delete(String string2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.delete(null, string2, asyncHttpResponseHandler);
    }

    public void get(Context context, String string2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.get(context, string2, null, asyncHttpResponseHandler);
    }

    public void get(Context context, String string2, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        AsyncHttpClient.super.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)new HttpGet(AsyncHttpClient.super.getUrlWithQueryString(string2, requestParams)), null, asyncHttpResponseHandler, context);
    }

    public void get(String string2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.get(null, string2, null, asyncHttpResponseHandler);
    }

    public void get(String string2, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.get(null, string2, requestParams, asyncHttpResponseHandler);
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public boolean isRunning() {
        return this.threadPool.getActiveCount() > 0;
    }

    public void post(Context context, String string2, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.post(context, string2, AsyncHttpClient.super.paramsToEntity(requestParams), null, asyncHttpResponseHandler);
    }

    public void post(Context context, String string2, HttpEntity httpEntity, String string3, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)this.addEntityToRequestBase((HttpEntityEnclosingRequestBase)new HttpPost(string2), httpEntity), string3, asyncHttpResponseHandler, context);
    }

    public void post(String string2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.post(null, string2, null, asyncHttpResponseHandler);
    }

    public void post(String string2, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.post(null, string2, requestParams, asyncHttpResponseHandler);
    }

    public void put(Context context, String string2, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.put(context, string2, AsyncHttpClient.super.paramsToEntity(requestParams), null, asyncHttpResponseHandler);
    }

    public void put(Context context, String string2, HttpEntity httpEntity, String string3, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)this.addEntityToRequestBase((HttpEntityEnclosingRequestBase)new HttpPut(string2), httpEntity), string3, asyncHttpResponseHandler, context);
    }

    public void put(String string2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.put(null, string2, null, asyncHttpResponseHandler);
    }

    public void put(String string2, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.put(null, string2, requestParams, asyncHttpResponseHandler);
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.httpContext.setAttribute("http.cookie-store", (Object)cookieStore);
    }

    public void setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", (SocketFactory)sSLSocketFactory, 443));
    }

    public void setThreadPool(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPool = threadPoolExecutor;
    }

    public void setUserAgent(String string2) {
        HttpProtocolParams.setUserAgent((HttpParams)this.httpClient.getParams(), (String)string2);
    }

    private static class InflatingEntity
    extends HttpEntityWrapper {
        public InflatingEntity(HttpEntity httpEntity) {
            super(httpEntity);
        }

        public InputStream getContent() throws IOException {
            return new GZIPInputStream(this.wrappedEntity.getContent());
        }

        public long getContentLength() {
            return -1L;
        }
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.util.Base64
 *  java.io.ByteArrayOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.UnsupportedEncodingException
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.nio.ByteBuffer
 *  java.util.ArrayList
 *  java.util.Hashtable
 *  java.util.Map$Entry
 *  java.util.Set
 *  org.apache.http.Header
 *  org.apache.http.HttpResponse
 *  org.apache.http.HttpVersion
 *  org.apache.http.NameValuePair
 *  org.apache.http.StatusLine
 *  org.apache.http.client.RedirectHandler
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.client.params.HttpClientParams
 *  org.apache.http.conn.scheme.Scheme
 *  org.apache.http.conn.scheme.SchemeRegistry
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.impl.client.DefaultRedirectHandler
 *  org.apache.http.message.BasicNameValuePair
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpParams
 *  org.apache.http.protocol.HttpContext
 */
package com.playhaven.src.common;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import com.playhaven.src.common.PHCrashReport;
import com.playhaven.src.utils.PHStringUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class PHAsyncRequest
extends AsyncTask<Uri, Integer, ByteBuffer> {
    public static final int INFINITE_REDIRECTS = Integer.MAX_VALUE;
    private PHHttpConn client;
    private Delegate delegate;
    private boolean isDownloading;
    private Exception lastError;
    public HttpParams params;
    private String password;
    private ArrayList<NameValuePair> postParams = new ArrayList();
    private long requestStart;
    public RequestType request_type;
    private int responseCode;
    public Uri url;
    private String username;

    public PHAsyncRequest(Delegate delegate) {
        this.delegate = delegate;
        this.client = new PHHttpConn();
        this.request_type = RequestType.Get;
    }

    /*
     * Exception decompiling
     */
    private /* varargs */ ByteBuffer execRequest(Uri ... var1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 5[TRYBLOCK]
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
        // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
        // org.benf.cfr.reader.b.a(Driver.java:128)
        // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
        // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
        // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
        // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1112)
        // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:587)
        // java.lang.Thread.run(Thread.java:818)
        throw new IllegalStateException("Decompilation failed");
    }

    private static ByteBuffer readStream(InputStream inputStream) throws IOException {
        int n;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrby = new byte[1024];
        while ((n = inputStream.read(arrby)) != -1) {
            byteArrayOutputStream.write(arrby, 0, n);
        }
        byteArrayOutputStream.flush();
        return ByteBuffer.wrap((byte[])byteArrayOutputStream.toByteArray());
    }

    public static String streamToString(InputStream inputStream) throws IOException, UnsupportedEncodingException {
        return new String(PHAsyncRequest.readStream(inputStream).array(), "UTF-8");
    }

    public void addPostParam(String string2, String string3) {
        this.postParams.add((Object)new BasicNameValuePair(string2, string3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addPostParams(Hashtable<String, String> hashtable) {
        if (hashtable != null) {
            this.postParams.clear();
            for (Map.Entry entry : hashtable.entrySet()) {
                this.postParams.add((Object)new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
            }
        }
    }

    protected /* varargs */ ByteBuffer doInBackground(Uri ... arruri) {
        return PHAsyncRequest.super.execRequest(arruri);
    }

    public String getLastRedirectURL() {
        return this.client.getLastRedirect();
    }

    public int getMaxRedirects() {
        return this.client.getMaxRedirects();
    }

    public PHHttpConn getPHHttpClient() {
        return this.client;
    }

    public String getPassword() {
        return this.password;
    }

    public ArrayList<NameValuePair> getPostParams() {
        return this.postParams;
    }

    public RequestType getRequestType() {
        return this.request_type;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isDownloading() {
        return this.isDownloading;
    }

    protected void onCancelled() {
        this.isDownloading = false;
    }

    protected void onPostExecute(ByteBuffer byteBuffer) {
        super.onPostExecute((Object)byteBuffer);
        try {
            this.isDownloading = false;
            long l = System.currentTimeMillis() - this.requestStart;
            PHStringUtil.log("PHAsyncRequest elapsed time (ms) = " + l);
            if (this.lastError != null && this.delegate != null) {
                this.delegate.requestFailed(this.lastError);
                return;
            }
            if (this.delegate != null) {
                this.delegate.requestFinished(byteBuffer, this.responseCode);
                return;
            }
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHAsyncRequest - onPostExecute", PHCrashReport.Urgency.critical);
        }
    }

    public void setMaxRedirects(int n) {
        this.client.setMaxRedirect(n);
    }

    public void setPHHttpClient(PHHttpConn pHHttpConn) {
        this.client = pHHttpConn;
    }

    public void setPassword(String string2) {
        this.password = string2;
        this.client.setPassword(string2);
    }

    public void setUsername(String string2) {
        this.username = string2;
        this.client.setUsername(string2);
    }

    public static interface Delegate {
        public void requestFailed(Exception var1);

        public void requestFinished(ByteBuffer var1, int var2);
    }

    public static class PHHttpConn {
        protected DefaultHttpClient client = new DefaultHttpClient(this.enableRedirecting(null));
        private HttpUriRequest cur_request;
        private PHSchemeRegistry mSchemeReg = new PHSchemeRegistry();
        private int max_redirects = Integer.MAX_VALUE;
        private String password;
        private ArrayList<String> redirectUrls = new ArrayList();
        private int totalRedirects = 0;
        private String username;

        public PHHttpConn() {
            this.client.setRedirectHandler((RedirectHandler)new PHRedirectHandler(this, null));
        }

        private HttpParams enableRedirecting(HttpParams httpParams) {
            if (httpParams == null) {
                httpParams = new BasicHttpParams();
            }
            httpParams.setParameter("http.protocol.version", (Object)HttpVersion.HTTP_1_1);
            httpParams.setBooleanParameter("http.protocol.allow-circular-redirects", true);
            HttpClientParams.setRedirecting((HttpParams)httpParams, (boolean)true);
            return httpParams;
        }

        public void addRedirectUrl(String string2) {
            this.redirectUrls.add((Object)string2);
        }

        public void clearRedirects() {
            this.redirectUrls.clear();
        }

        public HttpUriRequest getCurrentRequest() {
            return this.cur_request;
        }

        public DefaultHttpClient getHTTPClient() {
            return this.client;
        }

        public String getLastRedirect() {
            if (this.redirectUrls.size() == 0) {
                return null;
            }
            return (String)this.redirectUrls.get(-1 + this.redirectUrls.size());
        }

        public int getMaxRedirects() {
            return this.max_redirects;
        }

        public String getPassword() {
            return this.password;
        }

        public String getUsername() {
            return this.username;
        }

        public boolean isRedirectResponse(int n) {
            return n >= 300 && n <= 307;
        }

        public void setMaxRedirect(int n) {
            this.max_redirects = n;
        }

        public void setPassword(String string2) {
            this.password = string2;
        }

        public void setSchemeRegistry(PHSchemeRegistry pHSchemeRegistry) {
            this.mSchemeReg = pHSchemeRegistry;
        }

        public void setUsername(String string2) {
            this.username = string2;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean shouldRedirect(HttpResponse httpResponse) {
            block3 : {
                block2 : {
                    String string2;
                    Uri uri;
                    int n;
                    if (!this.isRedirectResponse(httpResponse.getStatusLine().getStatusCode()) || httpResponse.getHeaders("Location").length == 0 || (string2 = httpResponse.getHeaders("Location")[0].getValue()) == null || (uri = Uri.parse((String)string2)) == null || uri.getScheme() == null || uri.getPath() == null) break block2;
                    this.addRedirectUrl(uri.toString());
                    if (this.mSchemeReg.get(uri.getScheme()) == null) break block2;
                    this.totalRedirects = n = 1 + this.totalRedirects;
                    if (n <= this.max_redirects) break block3;
                }
                return false;
            }
            return true;
        }

        public HttpResponse start(HttpUriRequest httpUriRequest) throws IOException {
            this.cur_request = httpUriRequest;
            this.totalRedirects = 0;
            this.clearRedirects();
            if (this.username != null && this.password != null) {
                httpUriRequest.setHeader("Authorization", String.format((String)"Basic %s", (Object[])new Object[]{Base64.encodeToString((byte[])(this.username + ":" + this.password).getBytes(), (int)10)}));
            }
            return this.client.execute(httpUriRequest);
        }

        private class PHRedirectHandler
        extends DefaultRedirectHandler {
            final /* synthetic */ PHHttpConn this$0;

            private PHRedirectHandler(PHHttpConn pHHttpConn) {
                this.this$0 = pHHttpConn;
            }

            /* synthetic */ PHRedirectHandler(PHHttpConn pHHttpConn, 1 var2_2) {
                super(pHHttpConn);
            }

            public boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext) {
                return this.this$0.shouldRedirect(httpResponse);
            }
        }

        public static class PHSchemeRegistry {
            private SchemeRegistry schemeReg = new SchemeRegistry();

            public Scheme get(String string2) {
                return this.schemeReg.get(string2);
            }
        }

    }

    public static final class RequestType
    extends Enum<RequestType> {
        private static final /* synthetic */ RequestType[] $VALUES;
        public static final /* enum */ RequestType Delete;
        public static final /* enum */ RequestType Get;
        public static final /* enum */ RequestType Post;
        public static final /* enum */ RequestType Put;

        static {
            Post = new RequestType();
            Get = new RequestType();
            Put = new RequestType();
            Delete = new RequestType();
            RequestType[] arrrequestType = new RequestType[]{Post, Get, Put, Delete};
            $VALUES = arrrequestType;
        }

        public static RequestType valueOf(String string2) {
            return (RequestType)Enum.valueOf(RequestType.class, (String)string2);
        }

        public static RequestType[] values() {
            return (RequestType[])$VALUES.clone();
        }
    }

}


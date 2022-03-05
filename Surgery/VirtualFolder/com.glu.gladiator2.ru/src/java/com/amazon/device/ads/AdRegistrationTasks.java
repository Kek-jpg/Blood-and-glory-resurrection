/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.AsyncTask
 *  java.io.InputStream
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 *  java.lang.Void
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.json.JSONTokener
 */
package com.amazon.device.ads;

import android.content.Context;
import android.os.AsyncTask;
import com.amazon.device.ads.Configuration;
import com.amazon.device.ads.DebugProperties;
import com.amazon.device.ads.GlobalServiceTimer;
import com.amazon.device.ads.InternalAdRegistration;
import com.amazon.device.ads.Log;
import com.amazon.device.ads.Metrics;
import com.amazon.device.ads.Utils;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

final class AdRegistrationTasks {
    public static int HTTP_TIMEOUT = 0;
    public static final String LOG_TAG = "AmazonAdRegistrationTasks";

    static {
        HTTP_TIMEOUT = 20000;
    }

    AdRegistrationTasks() {
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static String buildDeviceInfoParams(A9Request a9Request, boolean bl) throws Exception {
        StringBuilder stringBuilder = new StringBuilder("http://");
        stringBuilder.append(Configuration.getInstance().getSISEndpoint());
        if (bl) {
            stringBuilder.append("/update_dev_info");
            stringBuilder.append("?adId=");
            if (a9Request.amazonDeviceId_ == null) {
                throw new IllegalArgumentException("AmazonDeviceId is null");
            }
            if (!DebugProperties.isDebugModeOn()) {
                stringBuilder.append(a9Request.amazonDeviceId_);
            } else {
                stringBuilder.append(DebugProperties.getDebugProperties().getProperty("debug.adid", a9Request.amazonDeviceId_));
            }
            stringBuilder.append("&dt=");
        } else {
            stringBuilder.append("/generate_did");
            stringBuilder.append("?dt=");
        }
        stringBuilder.append(a9Request.deviceInfo_.dt);
        stringBuilder.append("&app=");
        stringBuilder.append(a9Request.deviceInfo_.app);
        stringBuilder.append("&aud=");
        stringBuilder.append(a9Request.deviceInfo_.aud);
        stringBuilder.append("&appId=");
        stringBuilder.append(a9Request.deviceInfo_.getAppId());
        stringBuilder.append("&sdkVer=");
        stringBuilder.append(InternalAdRegistration.getInstance().getSDKVersionID());
        if (a9Request.deviceInfo_.sha1_mac != null) {
            stringBuilder.append("&sha1_mac=");
            stringBuilder.append(a9Request.deviceInfo_.sha1_mac);
        }
        if (a9Request.deviceInfo_.sha1_udid != null) {
            stringBuilder.append("&sha1_udid=");
            stringBuilder.append(a9Request.deviceInfo_.sha1_udid);
        }
        if (a9Request.deviceInfo_.sha1_tel != null) {
            stringBuilder.append("&sha1_tel=");
            stringBuilder.append(a9Request.deviceInfo_.sha1_tel);
        }
        if (a9Request.deviceInfo_.sha1_serial != null) {
            stringBuilder.append("&sha1_serial=");
            stringBuilder.append(a9Request.deviceInfo_.sha1_serial);
        }
        if (a9Request.deviceNativeData_.json != null) {
            stringBuilder.append("&dinfo=");
            stringBuilder.append(a9Request.deviceNativeData_.urlEncodedJson);
        }
        if (a9Request.deviceInfo_.ua != null) {
            stringBuilder.append("&ua=");
            stringBuilder.append(a9Request.deviceInfo_.ua);
        }
        if (a9Request.deviceInfo_.bad_mac) {
            stringBuilder.append("&badMac=true");
        }
        if (a9Request.deviceInfo_.bad_serial) {
            stringBuilder.append("&badSerial=true");
        }
        if (a9Request.deviceInfo_.bad_tel) {
            stringBuilder.append("&badTel=true");
        }
        if (a9Request.deviceInfo_.bad_udid) {
            stringBuilder.append("&badUdid=true");
        }
        return stringBuilder.toString();
    }

    protected static int getIntegerFromJSON(JSONObject jSONObject, String string, int n2) {
        try {
            int n3 = jSONObject.getInt(string);
            return n3;
        }
        catch (JSONException jSONException) {
            return n2;
        }
    }

    protected static String getStringFromJSON(JSONObject jSONObject, String string, String string2) {
        try {
            String string3 = jSONObject.getString(string);
            return string3;
        }
        catch (JSONException jSONException) {
            return string2;
        }
    }

    protected static class A9Request {
        protected String amazonDeviceId_;
        protected Context context_;
        protected InternalAdRegistration.DeviceInfo deviceInfo_;
        protected InternalAdRegistration.DeviceNativeData deviceNativeData_;
        protected String requestTag_;

        public A9Request(Context context, String string, InternalAdRegistration.DeviceInfo deviceInfo, InternalAdRegistration.DeviceNativeData deviceNativeData, String string2) {
            this.context_ = context;
            this.requestTag_ = string;
            this.deviceInfo_ = deviceInfo;
            this.deviceNativeData_ = deviceNativeData;
            this.amazonDeviceId_ = string2;
        }
    }

    protected static abstract class A9RequestTask
    extends AsyncTask<A9Request, Void, A9TaskResult> {
        protected Exception exception_;
        protected HttpClient httpClient_;
        protected String requestTag_ = "unknown";

        public A9RequestTask() {
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, (int)AdRegistrationTasks.HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, (int)AdRegistrationTasks.HTTP_TIMEOUT);
            HttpConnectionParams.setSocketBufferSize((HttpParams)basicHttpParams, (int)8192);
            this.httpClient_ = new DefaultHttpClient((HttpParams)basicHttpParams);
        }

        public /* varargs */ A9TaskResult doInBackground(A9Request ... arra9Request) {
            try {
                this.requestTag_ = arra9Request[0].requestTag_;
                A9TaskResult a9TaskResult = this.executeA9Request(arra9Request[0]);
                return a9TaskResult;
            }
            catch (Exception exception) {
                this.exception_ = exception;
                return null;
            }
        }

        protected abstract A9TaskResult executeA9Request(A9Request var1) throws Exception;

        /*
         * Enabled aggressive block sorting
         */
        protected void onPostExecute(A9TaskResult a9TaskResult) {
            if (this.exception_ != null) {
                Object[] arrobject = new Object[]{this.requestTag_, this.exception_};
                Log.d("AmazonAdRegistrationTasks", "Exception caught while performing %s request to A9: %s", arrobject);
                return;
            } else {
                if (a9TaskResult == null) return;
                {
                    a9TaskResult.execute();
                    return;
                }
            }
        }
    }

    protected static abstract class A9TaskResult {
        protected Context context_;

        public A9TaskResult(Context context) {
            this.context_ = context;
        }

        public abstract void execute();
    }

    protected static class NoOpA9TaskResult
    extends A9TaskResult {
        public NoOpA9TaskResult(Context context) {
            super(context);
        }

        @Override
        public void execute() {
        }
    }

    protected static class PingTask
    extends A9RequestTask {
        protected PingTask() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected A9TaskResult executeA9Request(A9Request a9Request) throws Exception {
            StringBuilder stringBuilder = new StringBuilder("http://");
            stringBuilder.append(Configuration.getInstance().getSISEndpoint());
            stringBuilder.append("/ping");
            stringBuilder.append("?adId=");
            if (a9Request.amazonDeviceId_ == null) {
                throw new IllegalArgumentException("AmazonDeviceId is null");
            }
            if (!DebugProperties.isDebugModeOn()) {
                stringBuilder.append(a9Request.amazonDeviceId_);
            } else {
                stringBuilder.append(DebugProperties.getDebugProperties().getProperty("debug.adid", a9Request.amazonDeviceId_));
            }
            stringBuilder.append("&appId=");
            stringBuilder.append(a9Request.deviceInfo_.getAppId());
            stringBuilder.append("&sdkVer=");
            stringBuilder.append(InternalAdRegistration.getInstance().getSDKVersionID());
            HttpPost httpPost = new HttpPost(stringBuilder.toString());
            Object[] arrobject = new Object[]{stringBuilder.toString()};
            Log.d(AdRegistrationTasks.LOG_TAG, "Sending: %s", arrobject);
            GlobalServiceTimer globalServiceTimer = new GlobalServiceTimer(Metrics.MetricType.SIS_LATENCY_PING);
            HttpResponse httpResponse = this.httpClient_.execute((HttpUriRequest)httpPost);
            globalServiceTimer.stop();
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode() != 200 || httpEntity == null || httpEntity.getContentLength() == 0L) {
                String string = "SIS ping call failed: Status code: " + httpResponse.getStatusLine().getStatusCode();
                Log.e(AdRegistrationTasks.LOG_TAG, string);
                throw new Exception(string);
            }
            StringBuffer stringBuffer = Utils.extractHttpResponse(httpEntity.getContent());
            try {
                JSONObject jSONObject = (JSONObject)new JSONTokener(stringBuffer.toString()).nextValue();
                int n2 = AdRegistrationTasks.getIntegerFromJSON(jSONObject, "rcode", 0);
                String string = AdRegistrationTasks.getStringFromJSON(jSONObject, "msg", "");
                if (n2 == 1) {
                    return new NoOpA9TaskResult(a9Request.context_);
                }
                String string2 = "SIS ping failed -- code: " + n2 + ", msg: " + string;
                Log.e(AdRegistrationTasks.LOG_TAG, string2);
                throw new Exception(string2);
            }
            catch (JSONException jSONException) {
                String string = "JSON error parsing return from SIS: " + jSONException.getMessage();
                Log.e(AdRegistrationTasks.LOG_TAG, string);
                throw new Exception(string);
            }
        }
    }

    protected static class RegisterDeviceTask
    extends A9RequestTask {
        protected RegisterDeviceTask() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected A9TaskResult executeA9Request(A9Request a9Request) throws Exception {
            String string = AdRegistrationTasks.buildDeviceInfoParams(a9Request, false);
            HttpPost httpPost = new HttpPost(string);
            Log.d(AdRegistrationTasks.LOG_TAG, "Sending: %s", string);
            GlobalServiceTimer globalServiceTimer = new GlobalServiceTimer(Metrics.MetricType.SIS_LATENCY_REGISTER);
            HttpResponse httpResponse = this.httpClient_.execute((HttpUriRequest)httpPost);
            globalServiceTimer.stop();
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode() != 200 || httpEntity == null || httpEntity.getContentLength() == 0L) {
                String string2 = "SIS registration call failed: Status code: " + httpResponse.getStatusLine().getStatusCode();
                Log.e(AdRegistrationTasks.LOG_TAG, string2);
                throw new Exception(string2);
            }
            StringBuffer stringBuffer = Utils.extractHttpResponse(httpEntity.getContent());
            try {
                JSONObject jSONObject = (JSONObject)new JSONTokener(stringBuffer.toString()).nextValue();
                int n2 = AdRegistrationTasks.getIntegerFromJSON(jSONObject, "rcode", 0);
                String string3 = AdRegistrationTasks.getStringFromJSON(jSONObject, "msg", "");
                String string4 = AdRegistrationTasks.getStringFromJSON(jSONObject, "adId", "");
                if (n2 == 1) {
                    return new RegisterTaskResult(a9Request.context_, string4);
                }
                String string5 = "SIS failed registering device -- code: " + n2 + ", msg: " + string3;
                Log.e(AdRegistrationTasks.LOG_TAG, string5);
                throw new Exception(string5);
            }
            catch (JSONException jSONException) {
                String string6 = "JSON error parsing return from SIS: " + jSONException.getMessage();
                Log.e(AdRegistrationTasks.LOG_TAG, string6);
                throw new Exception(string6);
            }
        }
    }

    protected static class RegisterTaskResult
    extends A9TaskResult {
        protected String adid_;

        public RegisterTaskResult(Context context, String string) {
            super(context);
            this.adid_ = string;
            Log.d(AdRegistrationTasks.LOG_TAG, "Register Device returned adid: %s", string);
        }

        @Override
        public void execute() {
            InternalAdRegistration.getInstance().setAmazonDeviceId(this.adid_);
        }
    }

    protected static class UpdateDeviceInfoTask
    extends A9RequestTask {
        protected UpdateDeviceInfoTask() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected A9TaskResult executeA9Request(A9Request a9Request) throws Exception {
            int n2;
            String string;
            String string2 = AdRegistrationTasks.buildDeviceInfoParams(a9Request, true);
            HttpPost httpPost = new HttpPost(string2);
            Log.d(AdRegistrationTasks.LOG_TAG, "Sending: %s", string2);
            GlobalServiceTimer globalServiceTimer = new GlobalServiceTimer(Metrics.MetricType.SIS_LATENCY_UPDATE_DEVICE_INFO);
            HttpResponse httpResponse = this.httpClient_.execute((HttpUriRequest)httpPost);
            globalServiceTimer.stop();
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode() != 200 || httpEntity == null || httpEntity.getContentLength() == 0L) {
                String string3 = "SIS update_device_info call failed: Status code: " + httpResponse.getStatusLine().getStatusCode();
                Log.e(AdRegistrationTasks.LOG_TAG, string3);
                throw new Exception(string3);
            }
            StringBuffer stringBuffer = Utils.extractHttpResponse(httpEntity.getContent());
            try {
                JSONObject jSONObject = (JSONObject)new JSONTokener(stringBuffer.toString()).nextValue();
                n2 = AdRegistrationTasks.getIntegerFromJSON(jSONObject, "rcode", 0);
                string = AdRegistrationTasks.getStringFromJSON(jSONObject, "msg", "");
                String string4 = AdRegistrationTasks.getStringFromJSON(jSONObject, "adId", "");
                String string5 = AdRegistrationTasks.getStringFromJSON(jSONObject, "idChanged", "");
                if (n2 == 1) {
                    if (string5.length() <= 0) return new NoOpA9TaskResult(a9Request.context_);
                    if (!string5.equals((Object)"true")) return new NoOpA9TaskResult(a9Request.context_);
                    if (string4.length() != 0) return new RegisterTaskResult(a9Request.context_, string4);
                    return new NoOpA9TaskResult(a9Request.context_);
                }
            }
            catch (JSONException jSONException) {
                String string6 = "JSON error parsing return from SIS: " + jSONException.getMessage();
                Log.e(AdRegistrationTasks.LOG_TAG, string6);
                throw new Exception(string6);
            }
            String string7 = "SIS failed updating device info -- code: " + n2 + ", msg: " + string;
            Log.e(AdRegistrationTasks.LOG_TAG, string7);
            throw new Exception(string7);
        }
    }

}


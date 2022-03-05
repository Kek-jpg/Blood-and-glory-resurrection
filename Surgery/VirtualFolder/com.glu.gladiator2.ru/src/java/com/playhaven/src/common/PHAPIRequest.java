/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.graphics.Rect
 *  android.net.Uri
 *  android.os.AsyncTask
 *  java.io.IOException
 *  java.io.UnsupportedEncodingException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.lang.ref.WeakReference
 *  java.nio.ByteBuffer
 *  java.security.NoSuchAlgorithmException
 *  java.util.HashMap
 *  java.util.Hashtable
 *  java.util.Locale
 *  java.util.Map
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.playhaven.src.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import com.playhaven.src.common.PHAsyncRequest;
import com.playhaven.src.common.PHConfig;
import com.playhaven.src.common.PHCrashReport;
import com.playhaven.src.utils.PHStringUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class PHAPIRequest
implements PHAsyncRequest.Delegate {
    public static final String API_CACHE_SUBDIR = "apicache";
    public static final Integer APP_CACHE_VERSION;
    public static final Integer PRECACHE_FILE_KEY_INDEX;
    private static WeakReference<SharedPreferences> preferences;
    private final String SESSION_PREFERENCES;
    private Hashtable<String, String> additionalParams;
    private PHAsyncRequest conn;
    public Delegate delegate;
    protected String fullUrl;
    private int requestTag;
    private HashMap<String, String> signedParams;
    private String urlPath;

    static {
        PRECACHE_FILE_KEY_INDEX = 0;
        APP_CACHE_VERSION = 100;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public PHAPIRequest(Context context) {
        this.SESSION_PREFERENCES = "com_playhaven_sdk_session";
        if (context != null) {
            Class<PHAPIRequest> class_ = PHAPIRequest.class;
            // MONITORENTER : com.playhaven.src.common.PHAPIRequest.class
            PHConfig.cacheDeviceInfo(context);
            preferences = new WeakReference((Object)context.getSharedPreferences("com_playhaven_sdk_session", 2));
            // MONITOREXIT : class_
        }
        if (PHConfig.token == null) throw new IllegalArgumentException("You must set your token and secret from the Playhaven dashboard");
        if (PHConfig.secret == null) throw new IllegalArgumentException("You must set your token and secret from the Playhaven dashboard");
        if (PHConfig.token.length() == 0) throw new IllegalArgumentException("You must set your token and secret from the Playhaven dashboard");
        if (PHConfig.secret.length() != 0) return;
        throw new IllegalArgumentException("You must set your token and secret from the Playhaven dashboard");
    }

    public PHAPIRequest(Context context, Delegate delegate) {
        super(context);
        this.delegate = delegate;
    }

    public String baseURL() {
        return this.urlPath;
    }

    public void cancel() {
        PHStringUtil.log(this.toString() + " canceled!");
        this.finish();
    }

    public String createAPIURL(String string2) {
        return PHConfig.api + string2;
    }

    protected void finish() {
        this.conn.cancel(true);
    }

    public Hashtable<String, String> getAdditionalParams() {
        return this.additionalParams;
    }

    public PHAsyncRequest getConnection() {
        return this.conn;
    }

    public Hashtable<String, String> getPostParams() {
        return null;
    }

    public int getRequestTag() {
        return this.requestTag;
    }

    public PHAsyncRequest.RequestType getRequestType() {
        return PHAsyncRequest.RequestType.Get;
    }

    /*
     * Enabled aggressive block sorting
     */
    public HashMap<String, String> getSignedParams() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (this.signedParams == null) {
            String string2 = PHConfig.device_id != null ? PHConfig.device_id : "null";
            String string3 = String.valueOf((int)PHConfig.device_size);
            String string4 = PHStringUtil.base64Digest(PHStringUtil.generateUUID());
            if (PHConfig.token == null || PHConfig.secret == null) {
                throw new UnsupportedOperationException("You must set both the token and secret from the Playhaven Dashboard!");
            }
            Object[] arrobject = new Object[4];
            arrobject[0] = PHConfig.token;
            String string5 = string2 != null ? string2 : "";
            arrobject[1] = string5;
            String string6 = string4 != null ? string4 : "";
            arrobject[2] = string6;
            arrobject[3] = PHConfig.secret;
            String string7 = PHStringUtil.hexDigest(String.format((String)"%s:%s:%s:%s", (Object[])arrobject));
            String string8 = PHConfig.app_package;
            String string9 = PHConfig.app_version;
            String string10 = PHConfig.device_model;
            Object[] arrobject2 = new Object[]{PHConfig.os_name, PHConfig.os_version};
            String string11 = String.format((String)"%s %s", (Object[])arrobject2);
            String string12 = PHConfig.sdk_version;
            String string13 = String.valueOf((int)PHConfig.screen_size.width());
            String string14 = String.valueOf((int)PHConfig.screen_size.height());
            String string15 = String.valueOf((int)PHConfig.screen_density_type);
            String string16 = PHConfig.connection == PHConfig.ConnectionType.NO_PERMISSION ? null : String.valueOf((int)PHConfig.connection.ordinal());
            Hashtable<String, String> hashtable = this.getAdditionalParams();
            HashMap hashMap = hashtable != null ? new HashMap(hashtable) : new HashMap();
            this.signedParams = new HashMap();
            this.signedParams.put((Object)"device", (Object)string2);
            this.signedParams.put((Object)"token", (Object)PHConfig.token);
            this.signedParams.put((Object)"signature", (Object)string7);
            this.signedParams.put((Object)"nonce", (Object)string4);
            this.signedParams.put((Object)"app", (Object)string8);
            this.signedParams.put((Object)"app_version", (Object)string9);
            this.signedParams.put((Object)"hardware", (Object)string10);
            this.signedParams.put((Object)"os", (Object)string11);
            this.signedParams.put((Object)"idiom", (Object)string3);
            this.signedParams.put((Object)"width", (Object)string13);
            this.signedParams.put((Object)"height", (Object)string14);
            this.signedParams.put((Object)"sdk_version", (Object)string12);
            this.signedParams.put((Object)"sdk_platform", (Object)"android");
            this.signedParams.put((Object)"orientation", (Object)"0");
            this.signedParams.put((Object)"dpi", (Object)string15);
            this.signedParams.put((Object)"languages", (Object)Locale.getDefault().getLanguage());
            if (string16 != null) {
                this.signedParams.put((Object)"connection", (Object)string16);
            }
            hashMap.putAll(this.signedParams);
            this.signedParams = hashMap;
        }
        return this.signedParams;
    }

    public String getURL() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (this.fullUrl == null) {
            Object[] arrobject = new Object[]{this.baseURL(), this.signedParamsStr()};
            this.fullUrl = String.format((String)"%s?%s", (Object[])arrobject);
        }
        return this.fullUrl;
    }

    public void handleRequestSuccess(JSONObject jSONObject) {
        if (this.delegate == null) {
            return;
        }
        if (jSONObject != null) {
            this.delegate.requestSucceeded((PHAPIRequest)this, jSONObject);
            return;
        }
        this.requestFailed((Exception)((Object)new JSONException("Couldn't parse json")));
    }

    public void processRequestResponse(JSONObject jSONObject) {
        String string2 = jSONObject.optString("error");
        JSONObject jSONObject2 = jSONObject.optJSONObject("errobj");
        if (!JSONObject.NULL.equals((Object)jSONObject2) && jSONObject2.length() > 0 || !jSONObject.isNull("error") && string2.length() > 0) {
            this.requestFailed(new Exception("Request failed with error: " + string2));
            return;
        }
        JSONObject jSONObject3 = jSONObject.optJSONObject("response");
        if (JSONObject.NULL.equals((Object)jSONObject3) || jSONObject3.equals((Object)"") || jSONObject3.equals((Object)"undefined") || jSONObject3.length() == 0) {
            jSONObject3 = new JSONObject();
        }
        this.handleRequestSuccess(jSONObject3);
    }

    @Override
    public void requestFailed(Exception exception) {
        if (this.delegate != null) {
            this.delegate.requestFailed((PHAPIRequest)this, exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void requestFinished(ByteBuffer byteBuffer, int n) {
        PHStringUtil.log("Received response code: " + n);
        if (n != 200) {
            this.requestFailed((Exception)((Object)new IOException("Request failed with code: " + n)));
            return;
        }
        if (byteBuffer == null || byteBuffer.array() == null) return;
        try {
            String string2 = new String(byteBuffer.array(), "UTF8");
            PHStringUtil.log("Unparsed JSON: " + string2);
            this.processRequestResponse(new JSONObject(string2));
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            PHCrashReport.reportCrash((Exception)((Object)unsupportedEncodingException), "PHAPIRequest - requestFinished", PHCrashReport.Urgency.low);
            return;
        }
        catch (JSONException jSONException) {
            this.requestFailed((Exception)((Object)new JSONException("Could not parse JSON: " + jSONException.getMessage())));
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHAPIRequest - requestFinished", PHCrashReport.Urgency.critical);
            return;
        }
    }

    public void send() {
        this.conn = new PHAsyncRequest(this);
        if (PHConfig.username != null && PHConfig.password != null) {
            this.conn.setUsername(PHConfig.username);
            this.conn.setPassword(PHConfig.password);
        }
        this.conn.request_type = this.getRequestType();
        this.send(this.conn);
    }

    public void send(PHAsyncRequest pHAsyncRequest) {
        try {
            this.conn = pHAsyncRequest;
            if (this.conn.request_type == PHAsyncRequest.RequestType.Post) {
                this.conn.addPostParams(this.getPostParams());
            }
            PHStringUtil.log("Sending PHAPIRequest of type: " + this.getRequestType().toString());
            PHStringUtil.log("PHAPIRequest URL: " + this.getURL());
            PHAsyncRequest pHAsyncRequest2 = this.conn;
            Object[] arrobject = new Uri[]{Uri.parse((String)this.getURL())};
            pHAsyncRequest2.execute(arrobject);
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHAPIRequest - send()", PHCrashReport.Urgency.critical);
            return;
        }
    }

    public void setAdditionalParameters(Hashtable<String, String> hashtable) {
        this.additionalParams = hashtable;
    }

    public void setBaseURL(String string2) {
        this.urlPath = string2;
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    public void setRequestTag(int n) {
        this.requestTag = n;
    }

    public String signedParamsStr() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return PHStringUtil.createQuery(this.getSignedParams());
    }

    public static interface Delegate {
        public void requestFailed(PHAPIRequest var1, Exception var2);

        public void requestSucceeded(PHAPIRequest var1, JSONObject var2);
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.concurrent.ConcurrentHashMap
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.ClientProtocolException
 *  org.apache.http.client.ResponseHandler
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 *  org.json.JSONObject
 */
package com.amazon.device.ads;

import android.content.Context;
import android.content.SharedPreferences;
import com.amazon.device.ads.DebugProperties;
import com.amazon.device.ads.GlobalServiceTimer;
import com.amazon.device.ads.Log;
import com.amazon.device.ads.Metrics;
import com.amazon.device.ads.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

class Configuration {
    private static final long EXTERNAL_CONFIG_DOWNLOAD_INTERVAL = 86400000L;
    private static final String EXTERNAL_CONFIG_URL = "http://z-ecx.images-amazon.com/images/G/01/mobile/advertising/amazonMobileSDKv2._TTH_.json";
    private static final String LOG_TAG = "Configuration";
    static final String PREFS_NAME = "AmazonMobileAds";
    private static Context context_;
    private static Configuration instance_;
    private String countryCode_ = "us";
    private RegionEndpointConfiguration endpointConfiguration_;
    private boolean externalConfigDownloadSuccessful_ = false;
    private long externalConfigLastDownloadTime_ = 0L;
    private JSONObject externalConfig_ = null;
    private Stage stage_ = Stage.PROD;

    private Configuration() {
        this.initialize();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private JSONObject getExternalConfiguration() {
        long l2 = System.currentTimeMillis();
        if (!this.externalConfigDownloadSuccessful_ || l2 - this.externalConfigLastDownloadTime_ > 86400000L) {
            GlobalServiceTimer globalServiceTimer;
            block6 : {
                globalServiceTimer = new GlobalServiceTimer(Metrics.MetricType.CONFIG_DOWNLOAD_LATENCY);
                this.externalConfigLastDownloadTime_ = l2;
                BasicHttpParams basicHttpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, (int)20000);
                HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, (int)20000);
                HttpConnectionParams.setSocketBufferSize((HttpParams)basicHttpParams, (int)8192);
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient((HttpParams)basicHttpParams);
                try {
                    String string = (String)defaultHttpClient.execute((HttpUriRequest)new HttpGet(EXTERNAL_CONFIG_URL), (ResponseHandler)new ResponseHandler<String>(){

                        public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                            HttpEntity httpEntity;
                            if (httpResponse.getStatusLine() != null && httpResponse.getStatusLine().getStatusCode() == 200 && (httpEntity = httpResponse.getEntity()) != null) {
                                return Utils.extractHttpResponse(httpEntity.getContent()).toString();
                            }
                            return null;
                        }
                    });
                    if (string != null) {
                        this.externalConfig_ = new JSONObject(string);
                        this.externalConfigDownloadSuccessful_ = true;
                        Log.d(LOG_TAG, "Successfully downloaded external configuration file.");
                        break block6;
                    }
                    this.externalConfigDownloadSuccessful_ = false;
                    Log.d(LOG_TAG, "Unsuccessfully downloaded external configuration file.");
                }
                catch (Exception exception) {
                    this.externalConfigDownloadSuccessful_ = false;
                    Log.e(LOG_TAG, "Error downloading external configuration file: %s", new Object[]{exception});
                }
            }
            globalServiceTimer.stop();
        }
        if (this.externalConfigDownloadSuccessful_) {
            return this.externalConfig_;
        }
        Metrics.getInstance().incrementMetric(Metrics.MetricType.CONFIG_DOWNLOAD_ERROR);
        return null;
    }

    public static final Configuration getInstance() {
        Class<Configuration> class_ = Configuration.class;
        synchronized (Configuration.class) {
            if (instance_ == null) {
                instance_ = new Configuration();
            }
            Configuration configuration = instance_;
            // ** MonitorExit[var2] (shouldn't be in output)
            return configuration;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initialize() {
        if (this.countryCode_ == "us") {
            Log.d(LOG_TAG, "Setting configuration endpoints to North America.");
            this.endpointConfiguration_ = new NorthAmericaEndpointConfiguration();
            return;
        } else {
            if (this.countryCode_ != "jp") return;
            {
                Log.d(LOG_TAG, "Setting configuration endpoints to Far East.");
                this.endpointConfiguration_ = new FarEastEndpointConfiguration();
                return;
            }
        }
    }

    private void readDefaultsFromSharedPreferences() {
        switch (context_.getSharedPreferences(PREFS_NAME, 0).getInt("0x6164616c706861", 4)) {
            default: {
                return;
            }
            case 0: 
            case 4: {
                this.setStage(Stage.PROD);
                return;
            }
            case 1: 
            case 5: {
                this.setStage(Stage.GAMMA);
                return;
            }
            case 2: 
            case 3: 
            case 6: 
        }
        this.setStage(Stage.DEVO);
    }

    public String getAAXEndpoint() {
        if (!DebugProperties.isDebugModeOn()) {
            return this.endpointConfiguration_.getAAXEndpoint(this.stage_);
        }
        return DebugProperties.getDebugProperties().getProperty("debug.aaxEndpoint", this.endpointConfiguration_.getAAXEndpoint(this.stage_));
    }

    public String getAdPreferencesURL() {
        return this.endpointConfiguration_.getAdPreferencesURL();
    }

    public String getCountry() {
        return this.countryCode_;
    }

    public String getSISEndpoint() {
        if (!DebugProperties.isDebugModeOn()) {
            return this.endpointConfiguration_.getSISEndpoint(this.stage_);
        }
        return DebugProperties.getDebugProperties().getProperty("debug.sisEndpoint", this.endpointConfiguration_.getSISEndpoint(this.stage_));
    }

    public Stage getStage() {
        return this.stage_;
    }

    public void setContext(Context context) {
        context_ = context;
        Configuration.super.readDefaultsFromSharedPreferences();
    }

    public void setCountry(String string) {
        if (string != this.countryCode_ && string.length() == 2) {
            Log.d(LOG_TAG, "Country code set to %s", string);
            this.countryCode_ = string.toLowerCase();
            Configuration.super.initialize();
        }
    }

    public void setStage(Stage stage) {
        Log.d(LOG_TAG, "Stage set to %s", new Object[]{stage});
        this.stage_ = stage;
    }

    private class FarEastEndpointConfiguration
    extends RegionEndpointConfiguration {
        FarEastEndpointConfiguration() {
            super(Configuration.this, null);
            this.aaxHosts_.put((Object)Stage.PROD, (Object)"aax-fe.amazon-adsystem.com");
            this.aaxHosts_.put((Object)Stage.GAMMA, (Object)"aax-fe-gamma.amazon-adsystem.com");
            this.aaxHosts_.put((Object)Stage.DEVO, (Object)"aax-beta.integ.amazon.com");
            this.sisHosts_.put((Object)Stage.PROD, (Object)"aax-fe.amazon-adsystem.com/s");
            this.sisHosts_.put((Object)Stage.GAMMA, (Object)"aax-fe-gamma.amazon-adsystem.com/s");
            this.sisHosts_.put((Object)Stage.DEVO, (Object)"s-beta.amazon-adsystem.com");
            this.adPreferencesURLDefault_ = "http://www.amazon.co.jp/gp/aw/aap/app/";
        }
    }

    private class NorthAmericaEndpointConfiguration
    extends RegionEndpointConfiguration {
        NorthAmericaEndpointConfiguration() {
            super(Configuration.this, null);
            this.aaxHosts_.put((Object)Stage.PROD, (Object)"aax-us-east.amazon-adsystem.com");
            this.aaxHosts_.put((Object)Stage.GAMMA, (Object)"aax-us-east-gamma.amazon-adsystem.com");
            this.aaxHosts_.put((Object)Stage.DEVO, (Object)"aax-beta.integ.amazon.com");
            this.sisHosts_.put((Object)Stage.PROD, (Object)"s.amazon-adsystem.com");
            this.sisHosts_.put((Object)Stage.GAMMA, (Object)"s-gamma.amazon-adsystem.com");
            this.sisHosts_.put((Object)Stage.DEVO, (Object)"s-beta.amazon-adsystem.com");
            this.adPreferencesURLDefault_ = "http://www.amazon.com/gp/aw/aap/app/";
        }
    }

    private abstract class RegionEndpointConfiguration {
        protected String aaxHandler_;
        protected ConcurrentHashMap<Stage, String> aaxHosts_;
        protected String adPreferencesURLDefault_;
        protected String sisHandler_;
        protected ConcurrentHashMap<Stage, String> sisHosts_;
        final /* synthetic */ Configuration this$0;

        private RegionEndpointConfiguration(Configuration configuration) {
            this.this$0 = configuration;
            this.aaxHandler_ = "/x/msdk";
            this.sisHandler_ = "/api3";
            this.aaxHosts_ = new ConcurrentHashMap();
            this.sisHosts_ = new ConcurrentHashMap();
            this.adPreferencesURLDefault_ = null;
        }

        /* synthetic */ RegionEndpointConfiguration(Configuration configuration, 1 var2_2) {
            super(configuration);
        }

        public String getAAXEndpoint(Stage stage) {
            return (String)this.aaxHosts_.get((Object)stage) + this.aaxHandler_;
        }

        public String getAdPreferencesURL() {
            JSONObject jSONObject = this.this$0.getExternalConfiguration();
            if (jSONObject != null) {
                String string = "";
                if (this.this$0.countryCode_ == "jp") {
                    string = "-jp";
                }
                try {
                    String string2;
                    this.adPreferencesURLDefault_ = string2 = jSONObject.optJSONObject("amazonMobileAdsSDK").optJSONObject("platform").optJSONObject("common").optJSONObject("adPreferences").optString("location" + string);
                    return string2;
                }
                catch (Exception exception) {
                    Metrics.getInstance().incrementMetric(Metrics.MetricType.CONFIG_PARSE_ERROR);
                    Object[] arrobject = new Object[]{exception.getMessage()};
                    Log.e(Configuration.LOG_TAG, "Error reading ad preferences url from external configuration: %s", arrobject);
                    return this.adPreferencesURLDefault_;
                }
            }
            return this.adPreferencesURLDefault_;
        }

        public String getSISEndpoint(Stage stage) {
            return (String)this.sisHosts_.get((Object)stage) + this.sisHandler_;
        }
    }

    static final class Stage
    extends Enum<Stage> {
        private static final /* synthetic */ Stage[] $VALUES;
        public static final /* enum */ Stage DEVO;
        public static final /* enum */ Stage GAMMA;
        public static final /* enum */ Stage PROD;

        static {
            PROD = new Stage();
            GAMMA = new Stage();
            DEVO = new Stage();
            Stage[] arrstage = new Stage[]{PROD, GAMMA, DEVO};
            $VALUES = arrstage;
        }

        public static Stage valueOf(String string) {
            return (Stage)Enum.valueOf(Stage.class, (String)string);
        }

        public static Stage[] values() {
            return (Stage[])$VALUES.clone();
        }
    }

}


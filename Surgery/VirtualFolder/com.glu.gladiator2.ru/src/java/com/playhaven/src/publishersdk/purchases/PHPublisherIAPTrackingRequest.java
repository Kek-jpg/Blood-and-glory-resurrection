/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.Double
 *  java.lang.Enum
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Currency
 *  java.util.Hashtable
 *  java.util.Locale
 *  org.json.JSONObject
 */
package com.playhaven.src.publishersdk.purchases;

import android.content.Context;
import com.playhaven.src.common.PHAPIRequest;
import com.playhaven.src.common.PHError;
import com.playhaven.src.publishersdk.content.PHPurchase;
import java.util.Currency;
import java.util.Hashtable;
import java.util.Locale;
import org.json.JSONObject;

public class PHPublisherIAPTrackingRequest
extends PHAPIRequest {
    private static Hashtable<String, String> cookies = new Hashtable();
    public Currency currencyLocale;
    public PHError error;
    public double price;
    public String product;
    public int quantity;
    public PHPurchase.Resolution resolution;
    public PHPurchaseOrigin store;

    public PHPublisherIAPTrackingRequest(Context context) {
        super(context);
        this.product = "";
        this.quantity = 0;
        this.price = 0.0;
        this.store = PHPurchaseOrigin.Google;
        this.resolution = PHPurchase.Resolution.Cancel;
        this.error = null;
    }

    public PHPublisherIAPTrackingRequest(Context context, PHAPIRequest.Delegate delegate) {
        super(context, delegate);
        this.product = "";
        this.quantity = 0;
        this.price = 0.0;
        this.store = PHPurchaseOrigin.Google;
        this.resolution = PHPurchase.Resolution.Cancel;
        this.error = null;
    }

    public PHPublisherIAPTrackingRequest(Context context, PHError pHError) {
        super(context);
        this.product = "";
        this.quantity = 0;
        this.price = 0.0;
        this.store = PHPurchaseOrigin.Google;
        this.resolution = PHPurchase.Resolution.Cancel;
        this.error = pHError;
    }

    public PHPublisherIAPTrackingRequest(Context context, PHPurchase pHPurchase) {
        super(context, pHPurchase.product, pHPurchase.quantity, pHPurchase.resolution);
    }

    public PHPublisherIAPTrackingRequest(Context context, String string2, int n, PHPurchase.Resolution resolution) {
        super(context);
        this.product = "";
        this.quantity = 0;
        this.price = 0.0;
        this.store = PHPurchaseOrigin.Google;
        this.resolution = PHPurchase.Resolution.Cancel;
        this.product = string2;
        this.quantity = n;
        this.resolution = resolution;
        this.error = null;
    }

    public static String getAndExpireCookie(String string2) {
        String string3 = (String)cookies.get((Object)string2);
        cookies.remove((Object)string2);
        return string3;
    }

    public static void setConversionCookie(String string2, String string3) {
        if (JSONObject.NULL.equals((Object)string3) || string3.length() == 0) {
            return;
        }
        cookies.put((Object)string2, (Object)string3);
    }

    @Override
    public String baseURL() {
        return super.createAPIURL("/v3/publisher/iap/");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Hashtable<String, String> getAdditionalParams() {
        this.currencyLocale = Currency.getInstance((Locale)Locale.getDefault());
        Hashtable hashtable = new Hashtable();
        String string2 = this.product != null ? this.product : "";
        hashtable.put((Object)"product", (Object)string2);
        hashtable.put((Object)"quantity", (Object)Integer.toString((int)this.quantity));
        String string3 = this.resolution != null ? this.resolution.getType() : "";
        hashtable.put((Object)"resolution", (Object)string3);
        hashtable.put((Object)"price", (Object)Double.toString((double)this.price));
        if (this.error != null && this.error.getErrorCode() != 0) {
            hashtable.put((Object)"error", (Object)Integer.toString((int)this.error.getErrorCode()));
        }
        String string4 = this.currencyLocale != null ? this.currencyLocale.getCurrencyCode() : "";
        hashtable.put((Object)"price_locale", (Object)string4);
        String string5 = this.store != null ? this.store.getOrigin() : null;
        hashtable.put((Object)"store", (Object)string5);
        String string6 = PHPublisherIAPTrackingRequest.getAndExpireCookie(this.product);
        if (string6 == null) {
            string6 = "";
        }
        hashtable.put((Object)"cookie", (Object)string6);
        return hashtable;
    }

    public static final class PHPurchaseOrigin
    extends Enum<PHPurchaseOrigin> {
        private static final /* synthetic */ PHPurchaseOrigin[] $VALUES;
        public static final /* enum */ PHPurchaseOrigin Amazon;
        public static final /* enum */ PHPurchaseOrigin Crossmo;
        public static final /* enum */ PHPurchaseOrigin Google;
        public static final /* enum */ PHPurchaseOrigin Motorola;
        public static final /* enum */ PHPurchaseOrigin Paypal;
        private String originStr;

        static {
            Google = new PHPurchaseOrigin("GoogleMarketplace");
            Amazon = new PHPurchaseOrigin("AmazonAppstore");
            Paypal = new PHPurchaseOrigin("Paypal");
            Crossmo = new PHPurchaseOrigin("Crossmo");
            Motorola = new PHPurchaseOrigin("MotorolaAppstore");
            PHPurchaseOrigin[] arrpHPurchaseOrigin = new PHPurchaseOrigin[]{Google, Amazon, Paypal, Crossmo, Motorola};
            $VALUES = arrpHPurchaseOrigin;
        }

        private PHPurchaseOrigin(String string3) {
            this.originStr = string3;
        }

        public static PHPurchaseOrigin valueOf(String string2) {
            return (PHPurchaseOrigin)Enum.valueOf(PHPurchaseOrigin.class, (String)string2);
        }

        public static PHPurchaseOrigin[] values() {
            return (PHPurchaseOrigin[])$VALUES.clone();
        }

        public String getOrigin() {
            return this.originStr;
        }
    }

}


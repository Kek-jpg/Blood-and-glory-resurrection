/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.glu.plugins.google;

public class Consts {
    public static final String ACTION_CONFIRM_NOTIFICATION = "com.glu.plugins.google.CONFIRM_NOTIFICATION";
    public static final String ACTION_GET_PURCHASE_INFORMATION = "com.glu.plugins.google.GET_PURCHASE_INFORMATION";
    public static final String ACTION_NOTIFY = "com.android.vending.billing.IN_APP_NOTIFY";
    public static final String ACTION_PURCHASE_STATE_CHANGED = "com.android.vending.billing.PURCHASE_STATE_CHANGED";
    public static final String ACTION_RESPONSE_CODE = "com.android.vending.billing.RESPONSE_CODE";
    public static final String ACTION_RESTORE_TRANSACTIONS = "com.glu.plugins.google.RESTORE_TRANSACTIONS";
    public static final String BILLING_REQUEST_API_VERSION = "API_VERSION";
    public static final String BILLING_REQUEST_DEVELOPER_PAYLOAD = "DEVELOPER_PAYLOAD";
    public static final String BILLING_REQUEST_ITEM_ID = "ITEM_ID";
    public static final String BILLING_REQUEST_METHOD = "BILLING_REQUEST";
    public static final String BILLING_REQUEST_NONCE = "NONCE";
    public static final String BILLING_REQUEST_NOTIFY_IDS = "NOTIFY_IDS";
    public static final String BILLING_REQUEST_PACKAGE_NAME = "PACKAGE_NAME";
    public static long BILLING_RESPONSE_INVALID_REQUEST_ID = 0L;
    public static final String BILLING_RESPONSE_PURCHASE_INTENT = "PURCHASE_INTENT";
    public static final String BILLING_RESPONSE_REQUEST_ID = "REQUEST_ID";
    public static final String BILLING_RESPONSE_RESPONSE_CODE = "RESPONSE_CODE";
    public static boolean DEBUG = false;
    public static final String INAPP_REQUEST_ID = "request_id";
    public static final String INAPP_RESPONSE_CODE = "response_code";
    public static final String INAPP_SIGNATURE = "inapp_signature";
    public static final String INAPP_SIGNED_DATA = "inapp_signed_data";
    public static final String MARKET_BILLING_SERVICE_ACTION = "com.android.vending.billing.MarketBillingService.BIND";
    public static final String NOTIFICATION_ID = "notification_id";

    static {
        BILLING_RESPONSE_INVALID_REQUEST_ID = -1L;
        DEBUG = false;
    }

    public static final class PurchaseState
    extends Enum<PurchaseState> {
        private static final /* synthetic */ PurchaseState[] $VALUES;
        public static final /* enum */ PurchaseState CANCELED;
        public static final /* enum */ PurchaseState PURCHASED;
        public static final /* enum */ PurchaseState REFUNDED;

        static {
            PURCHASED = new PurchaseState();
            CANCELED = new PurchaseState();
            REFUNDED = new PurchaseState();
            PurchaseState[] arrpurchaseState = new PurchaseState[]{PURCHASED, CANCELED, REFUNDED};
            $VALUES = arrpurchaseState;
        }

        public static PurchaseState valueOf(int n) {
            PurchaseState[] arrpurchaseState = PurchaseState.values();
            if (n < 0 || n >= arrpurchaseState.length) {
                return CANCELED;
            }
            return arrpurchaseState[n];
        }

        public static PurchaseState valueOf(String string2) {
            return (PurchaseState)Enum.valueOf(PurchaseState.class, (String)string2);
        }

        public static PurchaseState[] values() {
            return (PurchaseState[])$VALUES.clone();
        }
    }

    public static final class ResponseCode
    extends Enum<ResponseCode> {
        private static final /* synthetic */ ResponseCode[] $VALUES;
        public static final /* enum */ ResponseCode RESULT_BILLING_UNAVAILABLE;
        public static final /* enum */ ResponseCode RESULT_DEVELOPER_ERROR;
        public static final /* enum */ ResponseCode RESULT_ERROR;
        public static final /* enum */ ResponseCode RESULT_ITEM_UNAVAILABLE;
        public static final /* enum */ ResponseCode RESULT_OK;
        public static final /* enum */ ResponseCode RESULT_SERVICE_UNAVAILABLE;
        public static final /* enum */ ResponseCode RESULT_USER_CANCELED;

        static {
            RESULT_OK = new ResponseCode();
            RESULT_USER_CANCELED = new ResponseCode();
            RESULT_SERVICE_UNAVAILABLE = new ResponseCode();
            RESULT_BILLING_UNAVAILABLE = new ResponseCode();
            RESULT_ITEM_UNAVAILABLE = new ResponseCode();
            RESULT_DEVELOPER_ERROR = new ResponseCode();
            RESULT_ERROR = new ResponseCode();
            ResponseCode[] arrresponseCode = new ResponseCode[]{RESULT_OK, RESULT_USER_CANCELED, RESULT_SERVICE_UNAVAILABLE, RESULT_BILLING_UNAVAILABLE, RESULT_ITEM_UNAVAILABLE, RESULT_DEVELOPER_ERROR, RESULT_ERROR};
            $VALUES = arrresponseCode;
        }

        public static ResponseCode valueOf(int n) {
            ResponseCode[] arrresponseCode = ResponseCode.values();
            if (n < 0 || n >= arrresponseCode.length) {
                return RESULT_ERROR;
            }
            return arrresponseCode[n];
        }

        public static ResponseCode valueOf(String string2) {
            return (ResponseCode)Enum.valueOf(ResponseCode.class, (String)string2);
        }

        public static ResponseCode[] values() {
            return (ResponseCode[])$VALUES.clone();
        }
    }

}


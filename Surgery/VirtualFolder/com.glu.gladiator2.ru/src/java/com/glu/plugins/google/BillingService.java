/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.app.Service
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.SharedPreferences
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.Log
 *  com.android.vending.billing.IMarketBillingService
 *  com.android.vending.billing.IMarketBillingService$Stub
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Long
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.SecurityException
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.LinkedList
 */
package com.glu.plugins.google;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.android.vending.billing.IMarketBillingService;
import com.glu.plugins.google.Consts;
import com.glu.plugins.google.ResponseHandler;
import com.glu.plugins.google.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class BillingService
extends Service
implements ServiceConnection {
    private static final String SHAREDPREF_KEY_DEBUG = "DEBUG";
    private static final String SHAREDPREF_NAME = "aiap";
    private static final String TAG = "AInAppPurchase - BillingService";
    private static LinkedList<BillingRequest> mPendingRequests = new LinkedList();
    private static HashMap<Long, BillingRequest> mSentRequests = new HashMap();
    private static IMarketBillingService mService;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean bindToMarketBillingService() {
        block5 : {
            try {
                if (Consts.DEBUG) {
                    Log.i((String)TAG, (String)"binding to Market billing service");
                }
                if (!this.bindService(new Intent("com.android.vending.billing.MarketBillingService.BIND"), (ServiceConnection)this, 1)) break block5;
                return true;
            }
            catch (SecurityException securityException) {
                Log.e((String)TAG, (String)("Security exception: " + (Object)((Object)securityException)));
                return false;
            }
        }
        Log.e((String)TAG, (String)"Could not bind to service.");
        do {
            return false;
            break;
        } while (true);
    }

    private void checkResponseCode(long l, Consts.ResponseCode responseCode) {
        BillingRequest billingRequest = (BillingRequest)mSentRequests.get((Object)l);
        if (billingRequest != null) {
            if (Consts.DEBUG) {
                Log.d((String)TAG, (String)(billingRequest.getClass().getSimpleName() + ": " + (Object)((Object)responseCode)));
            }
            billingRequest.responseCodeReceived(responseCode);
        }
        mSentRequests.remove((Object)l);
    }

    private boolean confirmNotifications(int n, String[] arrstring) {
        return (BillingService)this.new ConfirmNotifications(n, arrstring).runRequest();
    }

    private boolean getPurchaseInformation(int n, String[] arrstring) {
        return (BillingService)this.new GetPurchaseInformation(n, arrstring).runRequest();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void purchaseStateChanged(int n, String string2, String string3) {
        ArrayList arrayList;
        block6 : {
            block5 : {
                ArrayList<Security.VerifiedPurchase> arrayList2 = Security.verifyPurchase(string2, string3);
                if (arrayList2 == null) break block5;
                arrayList = new ArrayList();
                for (Security.VerifiedPurchase verifiedPurchase : arrayList2) {
                    if (verifiedPurchase.notificationId != null) {
                        arrayList.add((Object)verifiedPurchase.notificationId);
                    }
                    ResponseHandler.purchaseResponse((Context)this, verifiedPurchase.purchaseState, verifiedPurchase.productId, verifiedPurchase.orderId, verifiedPurchase.purchaseTime, verifiedPurchase.developerPayload, string2);
                }
                if (!arrayList.isEmpty()) break block6;
            }
            return;
        }
        BillingService.super.confirmNotifications(n, (String[])arrayList.toArray((Object[])new String[arrayList.size()]));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void runPendingRequests() {
        BillingRequest billingRequest;
        int n = -1;
        while ((billingRequest = (BillingRequest)mPendingRequests.peek()) != null) {
            if (billingRequest.runIfConnected()) {
                mPendingRequests.remove();
                if (n >= billingRequest.getStartId()) continue;
                n = billingRequest.getStartId();
                continue;
            }
            this.bindToMarketBillingService();
            return;
        }
        if (n < 0) return;
        {
            if (Consts.DEBUG) {
                Log.i((String)TAG, (String)("stopping service, startId: " + n));
            }
            this.stopSelf(n);
            return;
        }
    }

    public boolean checkBillingSupported() {
        return new CheckBillingSupported().runRequest();
    }

    public boolean checkSubscriptionSupported() {
        return new CheckSubscriptionSupported().runRequest();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void handleCommand(Intent intent, int n) {
        String string2 = intent.getAction();
        if (Consts.DEBUG) {
            Log.i((String)TAG, (String)("handleCommand() action: " + string2));
        }
        if ("com.glu.plugins.google.CONFIRM_NOTIFICATION".equals((Object)string2)) {
            BillingService.super.confirmNotifications(n, intent.getStringArrayExtra("notification_id"));
        } else if ("com.glu.plugins.google.GET_PURCHASE_INFORMATION".equals((Object)string2)) {
            BillingService.super.getPurchaseInformation(n, new String[]{intent.getStringExtra("notification_id")});
        } else if ("com.android.vending.billing.PURCHASE_STATE_CHANGED".equals((Object)string2)) {
            BillingService.super.purchaseStateChanged(n, intent.getStringExtra("inapp_signed_data"), intent.getStringExtra("inapp_signature"));
        } else if ("com.android.vending.billing.RESPONSE_CODE".equals((Object)string2)) {
            BillingService.super.checkResponseCode(intent.getLongExtra("request_id", -1L), Consts.ResponseCode.valueOf(intent.getIntExtra("response_code", Consts.ResponseCode.RESULT_ERROR.ordinal())));
        }
        this.stopSelf();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (Consts.DEBUG) {
            Log.d((String)TAG, (String)"Billing service connected");
        }
        mService = IMarketBillingService.Stub.asInterface((IBinder)iBinder);
        BillingService.super.runPendingRequests();
    }

    public void onServiceDisconnected(ComponentName componentName) {
        Log.w((String)TAG, (String)"Billing service disconnected");
        mService = null;
    }

    public void onStart(Intent intent, int n) {
        if (intent == null) {
            return;
        }
        Consts.DEBUG = this.getSharedPreferences(SHAREDPREF_NAME, 0).getBoolean(SHAREDPREF_KEY_DEBUG, false);
        this.handleCommand(intent, n);
    }

    public boolean requestPurchase(String string2, String string3) {
        return new RequestPurchase((BillingService)this, string2, string3).runRequest();
    }

    public boolean restoreTransactions() {
        return new RestoreTransactions().runRequest();
    }

    public void setContext(Context context) {
        this.attachBaseContext(context);
    }

    public void unbind() {
        try {
            this.unbindService((ServiceConnection)this);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    abstract class BillingRequest {
        protected long mRequestId;
        private final int mStartId;

        public BillingRequest(int n) {
            this.mStartId = n;
        }

        public int getStartId() {
            return this.mStartId;
        }

        protected void logResponseCode(String string2, Bundle bundle) {
            Consts.ResponseCode responseCode = Consts.ResponseCode.valueOf(bundle.getInt("RESPONSE_CODE"));
            if (Consts.DEBUG) {
                Log.e((String)BillingService.TAG, (String)(string2 + " received " + responseCode.toString()));
            }
        }

        protected Bundle makeRequestBundle(String string2) {
            return this.makeRequestBundle(string2, 1);
        }

        protected Bundle makeRequestBundle(String string2, int n) {
            Bundle bundle = new Bundle();
            bundle.putString("BILLING_REQUEST", string2);
            bundle.putInt("API_VERSION", n);
            bundle.putString("PACKAGE_NAME", BillingService.this.getPackageName());
            return bundle;
        }

        protected void onRemoteException(RemoteException remoteException) {
            Log.w((String)BillingService.TAG, (String)"remote billing service crashed");
            mService = null;
        }

        protected void responseCodeReceived(Consts.ResponseCode responseCode) {
        }

        protected abstract long run() throws RemoteException;

        public boolean runIfConnected() {
            if (Consts.DEBUG) {
                Log.d((String)"AInAppPurchase - BillingService", (String)this.getClass().getSimpleName());
            }
            if (mService != null) {
                try {
                    this.mRequestId = this.run();
                    if (Consts.DEBUG) {
                        Log.d((String)"AInAppPurchase - BillingService", (String)("request id: " + this.mRequestId));
                    }
                    if (this.mRequestId >= 0L) {
                        mSentRequests.put((Object)this.mRequestId, (Object)this);
                    }
                    return true;
                }
                catch (RemoteException remoteException) {
                    this.onRemoteException(remoteException);
                }
            }
            return false;
        }

        public boolean runRequest() {
            if (this.runIfConnected()) {
                return true;
            }
            if (BillingService.this.bindToMarketBillingService()) {
                mPendingRequests.add((Object)this);
                return true;
            }
            return false;
        }
    }

    class CheckBillingSupported
    extends BillingRequest {
        public CheckBillingSupported() {
            super(-1);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected long run() throws RemoteException {
            Bundle bundle = this.makeRequestBundle("CHECK_BILLING_SUPPORTED");
            try {
                int n = mService.sendBillingRequest(bundle).getInt("RESPONSE_CODE");
                if (Consts.DEBUG) {
                    Log.i((String)BillingService.TAG, (String)("CheckBillingSupported response code: " + (Object)((Object)Consts.ResponseCode.valueOf(n))));
                }
                boolean bl = n == Consts.ResponseCode.RESULT_OK.ordinal();
                ResponseHandler.checkBillingSupportedResponse(bl);
                return Consts.BILLING_RESPONSE_INVALID_REQUEST_ID;
            }
            catch (NullPointerException nullPointerException) {
                ResponseHandler.checkBillingSupportedResponse(false);
                return Consts.BILLING_RESPONSE_INVALID_REQUEST_ID;
            }
        }
    }

    class CheckSubscriptionSupported
    extends BillingRequest {
        public CheckSubscriptionSupported() {
            super(-1);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected long run() throws RemoteException {
            Bundle bundle = this.makeRequestBundle("CHECK_BILLING_SUPPORTED", 2);
            bundle.putString("ITEM_TYPE", "subs");
            try {
                int n = mService.sendBillingRequest(bundle).getInt("RESPONSE_CODE");
                if (Consts.DEBUG) {
                    Log.i((String)BillingService.TAG, (String)("CheckSubscriptionSupported response code: " + (Object)((Object)Consts.ResponseCode.valueOf(n))));
                }
                boolean bl = n == Consts.ResponseCode.RESULT_OK.ordinal();
                ResponseHandler.checkSubscriptionSupportedResponse(bl);
                return Consts.BILLING_RESPONSE_INVALID_REQUEST_ID;
            }
            catch (NullPointerException nullPointerException) {
                ResponseHandler.checkSubscriptionSupportedResponse(false);
                return Consts.BILLING_RESPONSE_INVALID_REQUEST_ID;
            }
        }
    }

    class ConfirmNotifications
    extends BillingRequest {
        final String[] mNotifyIds;

        public ConfirmNotifications(int n, String[] arrstring) {
            super(n);
            this.mNotifyIds = arrstring;
        }

        @Override
        protected long run() throws RemoteException {
            Bundle bundle = this.makeRequestBundle("CONFIRM_NOTIFICATIONS");
            bundle.putStringArray("NOTIFY_IDS", this.mNotifyIds);
            try {
                Bundle bundle2 = mService.sendBillingRequest(bundle);
                this.logResponseCode("confirmNotifications", bundle2);
                long l = bundle2.getLong("REQUEST_ID", Consts.BILLING_RESPONSE_INVALID_REQUEST_ID);
                return l;
            }
            catch (NullPointerException nullPointerException) {
                return Consts.BILLING_RESPONSE_INVALID_REQUEST_ID;
            }
        }
    }

    class GetPurchaseInformation
    extends BillingRequest {
        long mNonce;
        final String[] mNotifyIds;

        public GetPurchaseInformation(int n, String[] arrstring) {
            super(n);
            this.mNotifyIds = arrstring;
        }

        @Override
        protected void onRemoteException(RemoteException remoteException) {
            super.onRemoteException(remoteException);
            Security.removeNonce(this.mNonce);
        }

        @Override
        protected long run() throws RemoteException {
            this.mNonce = Security.generateNonce();
            Bundle bundle = this.makeRequestBundle("GET_PURCHASE_INFORMATION");
            bundle.putLong("NONCE", this.mNonce);
            bundle.putStringArray("NOTIFY_IDS", this.mNotifyIds);
            try {
                Bundle bundle2 = mService.sendBillingRequest(bundle);
                this.logResponseCode("getPurchaseInformation", bundle2);
                long l = bundle2.getLong("REQUEST_ID", Consts.BILLING_RESPONSE_INVALID_REQUEST_ID);
                return l;
            }
            catch (NullPointerException nullPointerException) {
                return Consts.BILLING_RESPONSE_INVALID_REQUEST_ID;
            }
        }
    }

    class RequestPurchase
    extends BillingRequest {
        public final String mDeveloperPayload;
        public final String mProductId;
        final /* synthetic */ BillingService this$0;

        public RequestPurchase(BillingService billingService, String string2) {
            super(billingService, string2, null);
        }

        public RequestPurchase(BillingService billingService, String string2, String string3) {
            this.this$0 = billingService;
            super(-1);
            this.mProductId = string2;
            this.mDeveloperPayload = string3;
        }

        @Override
        protected void responseCodeReceived(Consts.ResponseCode responseCode) {
            ResponseHandler.responseCodeReceived((Context)this.this$0, (RequestPurchase)this, responseCode);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected long run() throws RemoteException {
            boolean bl = this.mDeveloperPayload != null ? this.mDeveloperPayload.toLowerCase().equals((Object)"subscription") : false;
            int n = bl ? 2 : 1;
            Bundle bundle = this.makeRequestBundle("REQUEST_PURCHASE", n);
            bundle.putString("ITEM_ID", this.mProductId);
            if (bl) {
                bundle.putString("ITEM_TYPE", "subs");
            }
            if (this.mDeveloperPayload != null && !this.mDeveloperPayload.equals((Object)"")) {
                bundle.putString("DEVELOPER_PAYLOAD", this.mDeveloperPayload);
            }
            try {
                Bundle bundle2 = mService.sendBillingRequest(bundle);
                PendingIntent pendingIntent = (PendingIntent)bundle2.getParcelable("PURCHASE_INTENT");
                if (pendingIntent == null) {
                    Log.e((String)BillingService.TAG, (String)"Error with requestPurchase");
                    return Consts.BILLING_RESPONSE_INVALID_REQUEST_ID;
                }
                ResponseHandler.buyPageIntentResponse(pendingIntent, new Intent());
                return bundle2.getLong("REQUEST_ID", Consts.BILLING_RESPONSE_INVALID_REQUEST_ID);
            }
            catch (NullPointerException nullPointerException) {
                return Consts.BILLING_RESPONSE_INVALID_REQUEST_ID;
            }
        }
    }

    class RestoreTransactions
    extends BillingRequest {
        long mNonce;

        public RestoreTransactions() {
            super(-1);
        }

        @Override
        protected void onRemoteException(RemoteException remoteException) {
            super.onRemoteException(remoteException);
            Security.removeNonce(this.mNonce);
        }

        @Override
        protected void responseCodeReceived(Consts.ResponseCode responseCode) {
            ResponseHandler.responseCodeReceived((Context)BillingService.this, (RestoreTransactions)this, responseCode);
        }

        @Override
        protected long run() throws RemoteException {
            this.mNonce = Security.generateNonce();
            Bundle bundle = this.makeRequestBundle("RESTORE_TRANSACTIONS");
            bundle.putLong("NONCE", this.mNonce);
            try {
                Bundle bundle2 = mService.sendBillingRequest(bundle);
                this.logResponseCode("restoreTransactions", bundle2);
                long l = bundle2.getLong("REQUEST_ID", Consts.BILLING_RESPONSE_INVALID_REQUEST_ID);
                return l;
            }
            catch (NullPointerException nullPointerException) {
                return Consts.BILLING_RESPONSE_INVALID_REQUEST_ID;
            }
        }
    }

}


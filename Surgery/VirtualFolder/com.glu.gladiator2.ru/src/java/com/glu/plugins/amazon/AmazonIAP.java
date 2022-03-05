/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.util.Log
 *  com.amazon.inapp.purchasing.BasePurchasingObserver
 *  com.amazon.inapp.purchasing.GetUserIdResponse
 *  com.amazon.inapp.purchasing.GetUserIdResponse$GetUserIdRequestStatus
 *  com.amazon.inapp.purchasing.Item
 *  com.amazon.inapp.purchasing.Item$ItemType
 *  com.amazon.inapp.purchasing.ItemDataResponse
 *  com.amazon.inapp.purchasing.ItemDataResponse$ItemDataRequestStatus
 *  com.amazon.inapp.purchasing.Offset
 *  com.amazon.inapp.purchasing.PurchaseResponse
 *  com.amazon.inapp.purchasing.PurchaseResponse$PurchaseRequestStatus
 *  com.amazon.inapp.purchasing.PurchaseUpdatesResponse
 *  com.amazon.inapp.purchasing.PurchaseUpdatesResponse$PurchaseUpdatesRequestStatus
 *  com.amazon.inapp.purchasing.PurchasingManager
 *  com.amazon.inapp.purchasing.PurchasingObserver
 *  com.amazon.inapp.purchasing.Receipt
 *  com.google.gson.Gson
 *  java.lang.Long
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Set
 */
package com.glu.plugins.amazon;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.amazon.inapp.purchasing.BasePurchasingObserver;
import com.amazon.inapp.purchasing.GetUserIdResponse;
import com.amazon.inapp.purchasing.Item;
import com.amazon.inapp.purchasing.ItemDataResponse;
import com.amazon.inapp.purchasing.Offset;
import com.amazon.inapp.purchasing.PurchaseResponse;
import com.amazon.inapp.purchasing.PurchaseUpdatesResponse;
import com.amazon.inapp.purchasing.PurchasingManager;
import com.amazon.inapp.purchasing.PurchasingObserver;
import com.amazon.inapp.purchasing.Receipt;
import com.glu.plugins.AInAppPurchase;
import com.google.gson.Gson;
import com.unity3d.player.UnityPlayer;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AmazonIAP
implements AInAppPurchase.IAP {
    private static final String TAG = "AInAppPurchase - AmazonIAP";
    private static boolean sandbox = false;
    private AmazonPurchaseObserver mAmazonPurchaseObserver;

    @Override
    public void Destroy() {
        this.mAmazonPurchaseObserver = null;
    }

    @Override
    public void Init() {
        AInAppPurchase.onBillingSupported(false);
        AInAppPurchase.onSubscriptionSupported(false);
        this.mAmazonPurchaseObserver = new AmazonPurchaseObserver();
        PurchasingManager.registerObserver((PurchasingObserver)this.mAmazonPurchaseObserver);
    }

    @Override
    public void Register() {
    }

    @Override
    public void RequestPurchase(String string2, String string3) {
        PurchasingManager.initiatePurchaseRequest((String)string2);
    }

    @Override
    public void RestoreTransactions() {
        PurchasingManager.initiatePurchaseUpdatesRequest((Offset)Offset.BEGINNING);
    }

    @Override
    public void Unregister() {
    }

    private class AmazonPurchaseObserver
    extends BasePurchasingObserver {
        private Offset off;

        public AmazonPurchaseObserver() {
            super((Context)UnityPlayer.currentActivity);
        }

        private String genJSON(Receipt receipt) {
            if (receipt == null) {
                return "{}";
            }
            return new Gson().toJson((Object)receipt);
        }

        public void onGetUserIdResponse(GetUserIdResponse getUserIdResponse) {
            Log.v((String)AmazonIAP.TAG, (String)("onGetUserIdResponse recieved: Response -" + (Object)getUserIdResponse));
            Log.v((String)AmazonIAP.TAG, (String)("RequestId:" + getUserIdResponse.getRequestId()));
            Log.v((String)AmazonIAP.TAG, (String)("IdRequestStatus:" + (Object)getUserIdResponse.getUserIdRequestStatus()));
            if (getUserIdResponse.getUserIdRequestStatus() == GetUserIdResponse.GetUserIdRequestStatus.SUCCESSFUL) {
                String string2 = getUserIdResponse.getUserId();
                if (string2 != null) {
                    AInAppPurchase.onSubscriptionSupported(true);
                }
                AInAppPurchase.onGetUserIdResponse(string2);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public void onItemDataResponse(ItemDataResponse itemDataResponse) {
            Log.v((String)AmazonIAP.TAG, (String)"onItemDataResponse recieved");
            Log.v((String)AmazonIAP.TAG, (String)("ItemDataRequestStatus" + (Object)itemDataResponse.getItemDataRequestStatus()));
            Log.v((String)AmazonIAP.TAG, (String)("ItemDataRequestId" + itemDataResponse.getRequestId()));
            switch (itemDataResponse.getItemDataRequestStatus()) {
                default: {
                    return;
                }
                case SUCCESSFUL_WITH_UNAVAILABLE_SKUS: {
                    for (String string2 : itemDataResponse.getUnavailableSkus()) {
                        Log.v((String)AmazonIAP.TAG, (String)("Unavailable SKU:" + string2));
                    }
                    break;
                }
                case SUCCESSFUL: 
            }
            Map map = itemDataResponse.getItemData();
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Item item = (Item)map.get((Object)((String)iterator.next()));
                Object[] arrobject = new Object[]{item.getTitle(), item.getItemType(), item.getSku(), item.getPrice(), item.getDescription()};
                Log.v((String)AmazonIAP.TAG, (String)String.format((String)"Item: %s\n Type: %s\n SKU: %s\n Price: %s\n Description: %s\n", (Object[])arrobject));
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onPurchaseResponse(PurchaseResponse purchaseResponse) {
            String string2;
            Log.v((String)AmazonIAP.TAG, (String)"onPurchaseResponse recieved");
            Log.v((String)AmazonIAP.TAG, (String)("PurchaseRequestStatus:" + (Object)purchaseResponse.getPurchaseRequestStatus()));
            Receipt receipt = purchaseResponse.getReceipt();
            StringBuilder stringBuilder = new StringBuilder();
            if (receipt == null) {
                string2 = null;
            } else {
                StringBuilder stringBuilder2 = new StringBuilder().append(receipt.getPurchaseToken());
                String string3 = sandbox ? "SNDBX" + Long.toString((long)System.currentTimeMillis()) : "";
                string2 = stringBuilder2.append(string3).toString();
            }
            StringBuilder stringBuilder3 = stringBuilder.append(string2).append("|").append(purchaseResponse.getPurchaseRequestStatus().toString()).append("|");
            String string4 = receipt == null ? null : receipt.getSku();
            StringBuilder stringBuilder4 = stringBuilder3.append(string4).append("|");
            String string5 = null;
            if (receipt != null) {
                string5 = receipt.getItemType().toString();
            }
            AInAppPurchase.onPurchaseStateChange(stringBuilder4.append(string5).append("|").append(AmazonPurchaseObserver.super.genJSON(receipt)).toString());
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onPurchaseUpdatesResponse(PurchaseUpdatesResponse purchaseUpdatesResponse) {
            Log.v((String)AmazonIAP.TAG, (String)("onPurchaseUpdatesRecived recieved: Response -" + (Object)purchaseUpdatesResponse));
            Log.v((String)AmazonIAP.TAG, (String)("PurchaseUpdatesRequestStatus:" + (Object)purchaseUpdatesResponse.getPurchaseUpdatesRequestStatus()));
            Log.v((String)AmazonIAP.TAG, (String)("RequestID:" + purchaseUpdatesResponse.getRequestId()));
            for (Receipt receipt : purchaseUpdatesResponse.getReceipts()) {
                String string2;
                if (purchaseUpdatesResponse.getPurchaseUpdatesRequestStatus() != PurchaseUpdatesResponse.PurchaseUpdatesRequestStatus.SUCCESSFUL) continue;
                StringBuilder stringBuilder = new StringBuilder();
                if (receipt == null) {
                    string2 = null;
                } else {
                    StringBuilder stringBuilder2 = new StringBuilder().append(receipt.getPurchaseToken());
                    String string3 = sandbox ? "SNDBX" + Long.toString((long)System.currentTimeMillis()) : "";
                    string2 = stringBuilder2.append(string3).toString();
                }
                StringBuilder stringBuilder3 = stringBuilder.append(string2).append("|").append(purchaseUpdatesResponse.getPurchaseUpdatesRequestStatus().toString()).append("|");
                String string4 = receipt == null ? null : receipt.getSku();
                StringBuilder stringBuilder4 = stringBuilder3.append(string4).append("|");
                String string5 = receipt == null ? null : receipt.getItemType().toString();
                AInAppPurchase.onPurchaseStateChange(stringBuilder4.append(string5).append("|").append(AmazonPurchaseObserver.super.genJSON(receipt)).toString());
            }
            this.off = purchaseUpdatesResponse.getOffset();
            if (purchaseUpdatesResponse.isMore()) {
                Log.v((String)AmazonIAP.TAG, (String)("Initiating Another Purchase Updates with offset: " + this.off.toString()));
                PurchasingManager.initiatePurchaseUpdatesRequest((Offset)this.off);
                return;
            }
            AInAppPurchase.onRestoreTransactionsResponse(purchaseUpdatesResponse.getPurchaseUpdatesRequestStatus().toString());
        }

        public void onSdkAvailable(boolean bl) {
            Log.v((String)AmazonIAP.TAG, (String)("onSdkAvailable recieved: Response -" + bl));
            sandbox = bl;
            AInAppPurchase.onBillingSupported(true);
            PurchasingManager.initiateGetUserIdRequest();
        }
    }

}


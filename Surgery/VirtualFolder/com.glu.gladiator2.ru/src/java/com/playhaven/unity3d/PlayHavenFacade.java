/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.graphics.Bitmap
 *  android.util.Log
 *  com.playhaven.src.common.PHAPIRequest$PHAPIRequestDelegate
 *  com.playhaven.src.common.PHConstants
 *  com.playhaven.src.common.PHConstants$Environment
 *  com.playhaven.src.common.PHConstants$Production
 *  com.playhaven.src.publishersdk.content.PHPublisherContentRequest$PHPublisherContentRequestDelegate
 *  com.playhaven.src.publishersdk.content.PHPublisherContentRequest$PHPurchaseDelegate
 *  com.playhaven.src.publishersdk.content.PHPurchase$PHPurchaseResolutionType
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 *  org.json.JSONObject
 */
package com.playhaven.unity3d;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import com.playhaven.src.common.PHAPIRequest;
import com.playhaven.src.common.PHConstants;
import com.playhaven.src.publishersdk.content.PHContent;
import com.playhaven.src.publishersdk.content.PHPublisherContentRequest;
import com.playhaven.src.publishersdk.content.PHPurchase;
import com.playhaven.src.publishersdk.content.PHReward;
import com.playhaven.src.publishersdk.metadata.PHPublisherMetadataRequest;
import com.playhaven.src.publishersdk.open.PHPublisherOpenRequest;
import com.playhaven.src.publishersdk.purchases.PHPublisherIAPTrackingRequest;
import com.unity3d.player.UnityPlayer;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class PlayHavenFacade
implements PHAPIRequest.PHAPIRequestDelegate,
PHPublisherContentRequest.PHPublisherContentRequestDelegate,
PHPublisherContentRequest.PHPurchaseDelegate {
    private Activity currentActivity;
    private PHPurchase currentPurchase;

    public PlayHavenFacade(Activity activity, String string2, String string3) {
        PHConstants.setEnvironment((PHConstants.Environment)new PHConstants.Production());
        this.setCurrentActivity(activity);
        this.setKeys(string2, string3);
    }

    public int borderColor(PHPublisherContentRequest pHPublisherContentRequest, PHContent pHContent) {
        return -1;
    }

    public Bitmap closeButton(PHPublisherContentRequest pHPublisherContentRequest, int n) {
        return null;
    }

    public void contentDidFail(PHPublisherContentRequest pHPublisherContentRequest, Exception exception) {
        this.requestFailed(pHPublisherContentRequest, exception);
    }

    public void contentRequest(int n, String string2) {
        Log.d((String)"PlayHavenFacade", (String)"contentRequest");
        PHPublisherContentRequest pHPublisherContentRequest = new PHPublisherContentRequest((PHPublisherContentRequest.PHPublisherContentRequestDelegate)this, this.currentActivity, string2);
        pHPublisherContentRequest.setHashCode(n);
        new RequestRunner((PlayHavenFacade)this, null).run(this.currentActivity, pHPublisherContentRequest);
    }

    public void didDismissContent(PHPublisherContentRequest pHPublisherContentRequest, PHPublisherContentRequest.PHDismissType pHDismissType) {
        Log.d((String)"PlayHavenFacade", (String)"didDismissContent");
        HashMap hashMap = new HashMap(4);
        hashMap.put((Object)"data", (Object)"");
        hashMap.put((Object)"name", (Object)"dismiss");
        hashMap.put((Object)"hash", (Object)pHPublisherContentRequest.getHashCode());
        UnityPlayer.UnitySendMessage("PlayHavenManager", "HandleNativeEvent", new JSONObject((Map)hashMap).toString());
    }

    public void didDisplayContent(PHPublisherContentRequest pHPublisherContentRequest, PHContent pHContent) {
    }

    public void didFail(PHPublisherContentRequest pHPublisherContentRequest, Exception exception) {
        this.requestFailed(pHPublisherContentRequest, exception);
    }

    public void didFail(PHPublisherContentRequest pHPublisherContentRequest, String string2) {
        Log.d((String)"PlayHavenFacade", (String)"requestFailed");
        HashMap hashMap = new HashMap(2);
        hashMap.put((Object)"code", (Object)0);
        hashMap.put((Object)"description", (Object)string2);
        HashMap hashMap2 = new HashMap(4);
        hashMap2.put((Object)"data", (Object)hashMap);
        hashMap2.put((Object)"name", (Object)"dismiss");
        hashMap2.put((Object)"hash", (Object)pHPublisherContentRequest.getHashCode());
        UnityPlayer.UnitySendMessage("PlayHavenManager", "HandleNativeEvent", new JSONObject((Map)hashMap2).toString());
    }

    public void iapTrackingRequest(String string2, int n, int n2) {
        Log.d((String)"PlayHavenFacade", (String)"iapTrackingRequest");
        PHPublisherIAPTrackingRequest pHPublisherIAPTrackingRequest = new PHPublisherIAPTrackingRequest((PHAPIRequest.PHAPIRequestDelegate)this);
        pHPublisherIAPTrackingRequest.product = string2;
        pHPublisherIAPTrackingRequest.quantity = n;
        pHPublisherIAPTrackingRequest.resolution = PHPurchase.PHPurchaseResolutionType.values()[n2];
        new RequestRunner((PlayHavenFacade)this, null).run(this.currentActivity, pHPublisherIAPTrackingRequest);
    }

    public void makePurchase(PHPublisherContentRequest pHPublisherContentRequest, PHPurchase pHPurchase) {
        Log.d((String)"PlayHavenFacade", (String)"makePurchase");
        this.currentPurchase = pHPurchase;
        HashMap hashMap = new HashMap(4);
        hashMap.put((Object)"productIdentifier", (Object)pHPurchase.productIdentifier);
        hashMap.put((Object)"name", (Object)pHPurchase.name);
        hashMap.put((Object)"quantity", (Object)pHPurchase.quantity);
        hashMap.put((Object)"receipt", (Object)pHPurchase.receipt);
        HashMap hashMap2 = new HashMap(4);
        hashMap2.put((Object)"data", (Object)hashMap);
        hashMap2.put((Object)"name", (Object)"purchasePresentation");
        hashMap2.put((Object)"hash", (Object)pHPublisherContentRequest.getHashCode());
        UnityPlayer.UnitySendMessage("PlayHavenManager", "HandleNativeEvent", new JSONObject((Map)hashMap2).toString());
    }

    public void metaDataRequest(int n, String string2) {
        Log.d((String)"PlayHavenFacade", (String)"metaDataRequest");
        PHPublisherMetadataRequest pHPublisherMetadataRequest = new PHPublisherMetadataRequest((PHAPIRequest.PHAPIRequestDelegate)this, string2);
        pHPublisherMetadataRequest.setHashCode(n);
        new RequestRunner((PlayHavenFacade)this, null).run(this.currentActivity, pHPublisherMetadataRequest);
    }

    public void openRequest(int n) {
        Log.d((String)"PlayHavenFacade", (String)"openRequest");
        PHPublisherOpenRequest pHPublisherOpenRequest = new PHPublisherOpenRequest((PHAPIRequest.PHAPIRequestDelegate)this);
        pHPublisherOpenRequest.setHashCode(n);
        new RequestRunner((PlayHavenFacade)this, null).run(this.currentActivity, pHPublisherOpenRequest);
    }

    public void reportResolution(int n) {
        Log.d((String)"PlayHavenFacade", (String)"reportResolution");
        if (this.currentPurchase != null) {
            this.currentPurchase.reportResolution(PHPurchase.PHPurchaseResolutionType.values()[n]);
        }
        this.currentPurchase = null;
    }

    public void requestFailed(PHAPIRequest pHAPIRequest, Exception exception) {
        Log.d((String)"PlayHavenFacade", (String)"requestFailed");
        HashMap hashMap = new HashMap(2);
        hashMap.put((Object)"code", (Object)0);
        hashMap.put((Object)"description", (Object)exception.getMessage());
        HashMap hashMap2 = new HashMap(4);
        hashMap2.put((Object)"data", (Object)hashMap);
        hashMap2.put((Object)"name", (Object)"dismiss");
        hashMap2.put((Object)"hash", (Object)pHAPIRequest.getHashCode());
        UnityPlayer.UnitySendMessage("PlayHavenManager", "HandleNativeEvent", new JSONObject((Map)hashMap2).toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public void requestSucceeded(PHAPIRequest pHAPIRequest, JSONObject jSONObject) {
        Log.d((String)"PlayHavenFacade", (String)"requestSucceeded");
        HashMap hashMap = new HashMap(1);
        hashMap.put((Object)"name", (Object)"success");
        hashMap.put((Object)"hash", (Object)pHAPIRequest.getHashCode());
        if (jSONObject == null) {
            hashMap.put((Object)"data", (Object)"");
        } else {
            hashMap.put((Object)"data", (Object)jSONObject);
        }
        UnityPlayer.UnitySendMessage("PlayHavenManager", "HandleNativeEvent", new JSONObject((Map)hashMap).toString());
    }

    public void setCurrentActivity(Activity activity) {
        this.currentActivity = activity;
        PHConstants.findDeviceInfo((Activity)activity);
    }

    public void setKeys(String string2, String string3) {
        Log.d((String)"PlayHavenFacade", (String)"setKeys");
        PHConstants.setKeys((String)string2, (String)string3);
    }

    public void unlockedReward(PHPublisherContentRequest pHPublisherContentRequest, PHReward pHReward) {
        Log.d((String)"PlayHavenFacade", (String)"unlockedReward");
        HashMap hashMap = new HashMap(4);
        hashMap.put((Object)"name", (Object)pHReward.name);
        hashMap.put((Object)"quantity", (Object)pHReward.quantity);
        hashMap.put((Object)"receipt", (Object)pHReward.receipt);
        HashMap hashMap2 = new HashMap(4);
        hashMap2.put((Object)"data", (Object)hashMap);
        hashMap2.put((Object)"name", (Object)"reward");
        hashMap2.put((Object)"hash", (Object)pHPublisherContentRequest.getHashCode());
        UnityPlayer.UnitySendMessage("PlayHavenManager", "HandleNativeEvent", new JSONObject((Map)hashMap2).toString());
    }

    public void willDisplayContent(PHPublisherContentRequest pHPublisherContentRequest, PHContent pHContent) {
    }

    public void willGetContent(PHPublisherContentRequest pHPublisherContentRequest) {
    }

    private class RequestRunner
    implements Runnable {
        private PHAPIRequest request;
        final /* synthetic */ PlayHavenFacade this$0;

        private RequestRunner(PlayHavenFacade playHavenFacade) {
            this.this$0 = playHavenFacade;
        }

        /* synthetic */ RequestRunner(PlayHavenFacade playHavenFacade, 1 var2_2) {
            super(playHavenFacade);
        }

        public void run() {
            this.request.send();
        }

        public void run(Activity activity, PHAPIRequest pHAPIRequest) {
            this.request = pHAPIRequest;
            activity.runOnUiThread((Runnable)this);
        }
    }

}


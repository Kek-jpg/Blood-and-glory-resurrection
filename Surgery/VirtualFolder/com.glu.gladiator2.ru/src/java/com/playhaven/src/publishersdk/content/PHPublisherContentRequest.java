/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.graphics.Bitmap
 *  android.graphics.drawable.BitmapDrawable
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.Parcelable
 *  java.io.File
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.ref.WeakReference
 *  java.util.HashMap
 *  java.util.Hashtable
 *  java.util.concurrent.ConcurrentLinkedQueue
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.playhaven.src.publishersdk.content;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import com.jakewharton.DiskLruCache;
import com.playhaven.src.common.PHAPIRequest;
import com.playhaven.src.common.PHConfig;
import com.playhaven.src.common.PHCrashReport;
import com.playhaven.src.common.PHSession;
import com.playhaven.src.publishersdk.content.PHContent;
import com.playhaven.src.publishersdk.content.PHContentView;
import com.playhaven.src.publishersdk.content.PHPurchase;
import com.playhaven.src.publishersdk.content.PHReward;
import com.playhaven.src.publishersdk.open.PHPrefetchTask;
import com.playhaven.src.utils.PHStringUtil;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.json.JSONArray;
import org.json.JSONObject;

public class PHPublisherContentRequest
extends PHAPIRequest {
    private static ConcurrentLinkedQueue<Long> dismissedStamps = new ConcurrentLinkedQueue();
    private WeakReference<Context> activityContext;
    private WeakReference<Context> applicationContext;
    private DiskLruCache cache;
    private PHContent content;
    public String contentTag;
    private ContentDelegate content_listener;
    private PHRequestState currentState;
    private CustomizeDelegate customize_listener;
    private FailureDelegate failure_listener;
    public String placement;
    private PurchaseDelegate purchase_listener;
    private RewardDelegate reward_listener;
    private boolean showsOverlayImmediately;
    private PHRequestState targetState;

    public PHPublisherContentRequest(Activity activity, ContentDelegate contentDelegate, String string2) {
        super(activity, string2);
        this.delegate = contentDelegate;
        this.setDelegates(contentDelegate);
    }

    public PHPublisherContentRequest(Activity activity, String string2) {
        super((Context)activity);
        this.showsOverlayImmediately = false;
        this.content_listener = null;
        this.reward_listener = null;
        this.purchase_listener = null;
        this.customize_listener = null;
        this.failure_listener = null;
        this.placement = string2;
        this.applicationContext = new WeakReference((Object)activity.getApplicationContext());
        this.activityContext = new WeakReference((Object)activity);
        this.cache = null;
        PHPublisherContentRequest.super.registerReceiver();
        this.setCurrentState(PHRequestState.Initialized);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void cacheImage(JSONObject jSONObject) {
        String string2;
        if (jSONObject == null || (string2 = jSONObject.optString("url", null)) == null) {
            return;
        }
        PHStringUtil.log("Sending cache request for: " + string2);
        PHPrefetchTask pHPrefetchTask = new PHPrefetchTask();
        pHPrefetchTask.setURL(string2);
        pHPrefetchTask.execute((Object[])new Integer[0]);
    }

    private void continueLoading() {
        switch (2.$SwitchMap$com$playhaven$src$publishersdk$content$PHPublisherContentRequest$PHRequestState[this.currentState.ordinal()]) {
            default: {
                return;
            }
            case 1: {
                this.loadContent();
                return;
            }
            case 2: 
        }
        this.showContent();
    }

    public static boolean didDismissContentWithin(long l) {
        long l2 = System.currentTimeMillis();
        long l3 = 0L;
        while (l2 - l3 > l && dismissedStamps.size() > 0) {
            l3 = (Long)dismissedStamps.poll();
        }
        return l2 - l3 <= l;
    }

    public static void dismissedContent() {
        dismissedStamps.add((Object)System.currentTimeMillis());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void fetchImageFromCache(JSONObject jSONObject) {
        if (jSONObject == null) {
            return;
        }
        String string2 = jSONObject.optString("url", null);
        DiskLruCache diskLruCache = DiskLruCache.getSharedDiskCache();
        if (string2 == null) return;
        if (diskLruCache == null) return;
        if (diskLruCache.isClosed()) return;
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(string2);
            if (snapshot == null) return;
            File file = snapshot.getInputStreamFile(PHAPIRequest.PRECACHE_FILE_KEY_INDEX);
            snapshot.close();
            if (file == null) return;
            jSONObject.put("url", (Object)("file://" + file.getAbsolutePath()));
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHPublisherContentRequest - checkCacheForImage", PHCrashReport.Urgency.high);
            return;
        }
    }

    private void loadContent() {
        this.setCurrentState(PHRequestState.Preloading);
        super.send();
        if (this.content_listener != null) {
            this.content_listener.willGetContent(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void processResponse(JSONArray jSONArray, boolean bl) {
        if (jSONArray != null) {
            for (int i2 = 0; i2 < jSONArray.length(); ++i2) {
                JSONObject jSONObject = jSONArray.optJSONObject(i2);
                if (jSONObject != null) {
                    PHPublisherContentRequest.processResponse(jSONObject, bl);
                    continue;
                }
                PHPublisherContentRequest.processResponse(jSONArray.optJSONArray(i2), bl);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void processResponse(JSONObject jSONObject, boolean bl) {
        if (jSONObject != null && jSONObject.names() != null) {
            JSONArray jSONArray = jSONObject.names();
            for (int i2 = 0; i2 < jSONArray.length(); ++i2) {
                String string2 = jSONArray.optString(i2);
                if (string2.equals((Object)"image")) {
                    JSONObject jSONObject2 = jSONObject.optJSONObject(string2);
                    if (jSONObject2 == null) continue;
                    if (bl) {
                        PHPublisherContentRequest.cacheImage(jSONObject2.optJSONObject("PH_PORTRAIT"));
                        PHPublisherContentRequest.cacheImage(jSONObject2.optJSONObject("PH_LANDSCAPE"));
                        continue;
                    }
                    PHPublisherContentRequest.fetchImageFromCache(jSONObject2.optJSONObject("PH_PORTRAIT"));
                    PHPublisherContentRequest.fetchImageFromCache(jSONObject2.optJSONObject("PH_LANDSCAPE"));
                    continue;
                }
                JSONArray jSONArray2 = jSONObject.optJSONArray(string2);
                if (jSONArray2 == null) {
                    PHPublisherContentRequest.processResponse(jSONObject.optJSONObject(string2), bl);
                    continue;
                }
                PHPublisherContentRequest.processResponse(jSONArray2, bl);
            }
        }
    }

    private void registerReceiver() {
        if (this.applicationContext.get() != null) {
            ((Context)this.applicationContext.get()).registerReceiver(new BroadcastReceiver(){

                /*
                 * Enabled aggressive block sorting
                 */
                public void onReceive(Context context, Intent intent) {
                    String string2;
                    Bundle bundle = intent.getBundleExtra("com.playhaven.md");
                    if (bundle == null || (string2 = bundle.getString(PHContentView.Detail.Event.getKey())) == null) return;
                    PHContentView.Event event = PHContentView.Event.valueOf(string2);
                    String string3 = bundle.getString(PHContentView.Detail.Tag.getKey());
                    if (string3 == null || !string3.equals((Object)PHPublisherContentRequest.this.contentTag)) return;
                    if (event == PHContentView.Event.DidShow) {
                        PHContent pHContent = (PHContent)bundle.getParcelable(PHContentView.Detail.Content.getKey());
                        if (PHPublisherContentRequest.this.content_listener == null) return;
                        {
                            PHPublisherContentRequest.this.content_listener.didDisplayContent(PHPublisherContentRequest.this, pHContent);
                            return;
                        }
                    }
                    if (event == PHContentView.Event.DidDismiss) {
                        PHDismissType pHDismissType = PHDismissType.valueOf(bundle.getString(PHContentView.Detail.CloseType.getKey()));
                        if (PHPublisherContentRequest.this.content_listener == null) return;
                        {
                            PHPublisherContentRequest.this.content_listener.didDismissContent(PHPublisherContentRequest.this, pHDismissType);
                            return;
                        }
                    }
                    if (event == PHContentView.Event.DidFail) {
                        String string4 = bundle.getString(PHContentView.Detail.Error.getKey());
                        if (PHPublisherContentRequest.this.failure_listener == null) return;
                        {
                            PHPublisherContentRequest.this.failure_listener.didFail(PHPublisherContentRequest.this, string4);
                            return;
                        }
                    }
                    if (event == PHContentView.Event.DidUnlockReward) {
                        PHReward pHReward = (PHReward)bundle.getParcelable(PHContentView.Detail.Reward.getKey());
                        if (PHPublisherContentRequest.this.reward_listener == null) return;
                        {
                            PHPublisherContentRequest.this.reward_listener.unlockedReward(PHPublisherContentRequest.this, pHReward);
                            return;
                        }
                    }
                    if (event != PHContentView.Event.DidMakePurchase) return;
                    PHPurchase pHPurchase = (PHPurchase)bundle.getParcelable(PHContentView.Detail.Purchase.getKey());
                    if (PHPublisherContentRequest.this.purchase_listener == null) {
                        return;
                    }
                    PHPublisherContentRequest.this.purchase_listener.shouldMakePurchase(PHPublisherContentRequest.this, pHPurchase);
                }
            }, new IntentFilter("com.playhaven.src.publishersdk.content.PHContentViewEvent"));
        }
    }

    private void showContent() {
        if (this.targetState == PHRequestState.DisplayingContent || this.targetState == PHRequestState.Done) {
            if (this.content_listener != null) {
                this.content_listener.willDisplayContent(this, this.content);
            }
            this.setCurrentState(PHRequestState.DisplayingContent);
            HashMap hashMap = new HashMap();
            if (this.customize_listener != null) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(this.customize_listener.closeButton(this, PHContentView.ButtonState.Up));
                BitmapDrawable bitmapDrawable2 = new BitmapDrawable(this.customize_listener.closeButton(this, PHContentView.ButtonState.Up));
                hashMap.put((Object)PHContentView.ButtonState.Up.name(), (Object)bitmapDrawable.getBitmap());
                hashMap.put((Object)PHContentView.ButtonState.Down.name(), (Object)bitmapDrawable2.getBitmap());
            }
            String string2 = "PHContentView: " + this.hashCode();
            this.contentTag = PHContentView.pushContent(this.content, (Context)this.activityContext.get(), (HashMap<String, Bitmap>)hashMap, string2);
            if (this.content_listener != null) {
                this.content_listener.didDisplayContent(this, this.content);
            }
        }
    }

    @Override
    public String baseURL() {
        return super.createAPIURL("/v3/publisher/content/");
    }

    @Override
    public void finish() {
        this.setCurrentState(PHRequestState.Done);
        super.finish();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Hashtable<String, String> getAdditionalParams() {
        Hashtable hashtable = new Hashtable();
        String string2 = this.placement != null ? this.placement : "";
        hashtable.put((Object)"placement_id", (Object)string2);
        String string3 = this.targetState == PHRequestState.Preloaded ? "1" : "0";
        hashtable.put((Object)"preload", (Object)string3);
        hashtable.put((Object)"stime", (Object)String.valueOf((long)PHSession.getInstance((Context)this.activityContext.get()).getSessionTime()));
        return hashtable;
    }

    public PHContent getContent() {
        return this.content;
    }

    public PHRequestState getCurrentState() {
        return this.currentState;
    }

    public boolean getOverlayImmediately() {
        return this.showsOverlayImmediately;
    }

    public PHRequestState getTargetState() {
        return this.targetState;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void handleRequestSuccess(JSONObject jSONObject) {
        if (JSONObject.NULL.equals((Object)jSONObject) || jSONObject.length() == 0) {
            return;
        }
        this.content = new PHContent(jSONObject);
        if (this.content.url == null) {
            this.setCurrentState(PHRequestState.Done);
        } else {
            if (PHConfig.precache && this.targetState == PHRequestState.Preloaded && this.cache != null && !this.cache.isClosed()) {
                try {
                    DiskLruCache.Snapshot snapshot = this.cache.get(this.content.url.toString());
                    if (snapshot != null) {
                        File file = snapshot.getInputStreamFile(PHAPIRequest.PRECACHE_FILE_KEY_INDEX);
                        snapshot.close();
                        if (file != null) {
                            PHPublisherContentRequest.processResponse(jSONObject.optJSONObject("context"), true);
                            this.content.preloaded = true;
                        }
                    }
                }
                catch (IOException iOException) {
                    PHCrashReport.reportCrash((Exception)((Object)iOException), "PHPublisherContentRequest - handleRequestSuccess", PHCrashReport.Urgency.high);
                }
            }
            this.setCurrentState(PHRequestState.Preloaded);
        }
        PHPublisherContentRequest.super.continueLoading();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void preload() {
        if (PHConfig.precache) {
            Class<PHPublisherContentRequest> class_ = PHPublisherContentRequest.class;
            // MONITORENTER : com.playhaven.src.publishersdk.content.PHPublisherContentRequest.class
            try {
                this.cache = DiskLruCache.getSharedDiskCache();
                if (this.cache == null) {
                    DiskLruCache.createSharedDiskCache(new File((Object)((Context)this.activityContext.get()).getCacheDir() + File.separator + "apicache"), APP_CACHE_VERSION, 1, PHConfig.precache_size);
                    this.cache = DiskLruCache.getSharedDiskCache();
                    // MONITOREXIT : class_
                } else if (this.cache.isClosed()) {
                    this.cache.open();
                }
            }
            catch (Exception exception) {
                PHCrashReport.reportCrash(exception, "PHPublisherContentRequest - preload", PHCrashReport.Urgency.critical);
            }
        }
        this.targetState = PHRequestState.Preloaded;
        this.continueLoading();
    }

    @Override
    public void send() {
        try {
            this.targetState = PHRequestState.DisplayingContent;
            if (this.content_listener != null) {
                this.content_listener.willGetContent(this);
            }
            this.continueLoading();
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHPublisherContentRequest - send", PHCrashReport.Urgency.critical);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCurrentState(PHRequestState pHRequestState) {
        block5 : {
            block4 : {
                if (pHRequestState == null) break block4;
                if (this.currentState == null) {
                    this.currentState = pHRequestState;
                }
                if (pHRequestState.ordinal() > this.currentState.ordinal()) break block5;
            }
            return;
        }
        this.currentState = pHRequestState;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDelegates(Object object) {
        if (object instanceof RewardDelegate) {
            this.reward_listener = (RewardDelegate)object;
        } else {
            this.reward_listener = null;
            PHStringUtil.log("*** RewardDelegate is not implemented. If you are using rewards this needs to be implemented.");
        }
        if (object instanceof PurchaseDelegate) {
            this.purchase_listener = (PurchaseDelegate)object;
        } else {
            this.purchase_listener = null;
            PHStringUtil.log("*** PurchaseDelegate is not implemented. If you are using VGP this needs to be implemented.");
        }
        if (object instanceof CustomizeDelegate) {
            this.customize_listener = (CustomizeDelegate)object;
        } else {
            this.customize_listener = null;
            PHStringUtil.log("*** CustomizeDelegate is not implemented, using Play Haven close button bitmap. Implement to use own close button bitmap.");
        }
        if (object instanceof FailureDelegate) {
            this.failure_listener = (FailureDelegate)object;
        } else {
            this.failure_listener = null;
            PHStringUtil.log("*** FailureDelegate is not implemented. Implement if want to be notified of failed content downloads.");
        }
        if (object instanceof ContentDelegate) {
            this.content_listener = (ContentDelegate)object;
            return;
        }
        this.content_listener = null;
        PHStringUtil.log("*** ContentDelegate is not implemented. Implement if want to be notified of content request states.");
    }

    public void setOnContentListener(ContentDelegate contentDelegate) {
        this.content_listener = contentDelegate;
    }

    public void setOnContentListener(RewardDelegate rewardDelegate) {
        this.reward_listener = rewardDelegate;
    }

    public void setOnCustomizeListener(CustomizeDelegate customizeDelegate) {
        this.customize_listener = customizeDelegate;
    }

    public void setOnFailureListener(FailureDelegate failureDelegate) {
        this.failure_listener = failureDelegate;
    }

    public void setOnPurchaseListener(PurchaseDelegate purchaseDelegate) {
        this.purchase_listener = purchaseDelegate;
    }

    public void setOverlayImmediately(boolean bl) {
        this.showsOverlayImmediately = bl;
    }

    public void setTargetState(PHRequestState pHRequestState) {
        this.targetState = pHRequestState;
    }

    public static interface ContentDelegate
    extends PHAPIRequest.Delegate {
        public void didDismissContent(PHPublisherContentRequest var1, PHDismissType var2);

        public void didDisplayContent(PHPublisherContentRequest var1, PHContent var2);

        public void willDisplayContent(PHPublisherContentRequest var1, PHContent var2);

        public void willGetContent(PHPublisherContentRequest var1);
    }

    public static interface CustomizeDelegate {
        public int borderColor(PHPublisherContentRequest var1, PHContent var2);

        public Bitmap closeButton(PHPublisherContentRequest var1, PHContentView.ButtonState var2);
    }

    public static interface FailureDelegate {
        public void contentDidFail(PHPublisherContentRequest var1, Exception var2);

        public void didFail(PHPublisherContentRequest var1, String var2);
    }

    public static final class PHDismissType
    extends Enum<PHDismissType> {
        private static final /* synthetic */ PHDismissType[] $VALUES;
        public static final /* enum */ PHDismissType ApplicationTriggered;
        public static final /* enum */ PHDismissType CloseButtonTriggered;
        public static final /* enum */ PHDismissType ContentUnitTriggered;
        public static final /* enum */ PHDismissType NoContentTriggered;

        static {
            ContentUnitTriggered = new PHDismissType();
            CloseButtonTriggered = new PHDismissType();
            ApplicationTriggered = new PHDismissType();
            NoContentTriggered = new PHDismissType();
            PHDismissType[] arrpHDismissType = new PHDismissType[]{ContentUnitTriggered, CloseButtonTriggered, ApplicationTriggered, NoContentTriggered};
            $VALUES = arrpHDismissType;
        }

        public static PHDismissType valueOf(String string2) {
            return (PHDismissType)Enum.valueOf(PHDismissType.class, (String)string2);
        }

        public static PHDismissType[] values() {
            return (PHDismissType[])$VALUES.clone();
        }
    }

    public static final class PHRequestState
    extends Enum<PHRequestState> {
        private static final /* synthetic */ PHRequestState[] $VALUES;
        public static final /* enum */ PHRequestState DisplayingContent;
        public static final /* enum */ PHRequestState Done;
        public static final /* enum */ PHRequestState Initialized;
        public static final /* enum */ PHRequestState Preloaded;
        public static final /* enum */ PHRequestState Preloading;

        static {
            Initialized = new PHRequestState();
            Preloading = new PHRequestState();
            Preloaded = new PHRequestState();
            DisplayingContent = new PHRequestState();
            Done = new PHRequestState();
            PHRequestState[] arrpHRequestState = new PHRequestState[]{Initialized, Preloading, Preloaded, DisplayingContent, Done};
            $VALUES = arrpHRequestState;
        }

        public static PHRequestState valueOf(String string2) {
            return (PHRequestState)Enum.valueOf(PHRequestState.class, (String)string2);
        }

        public static PHRequestState[] values() {
            return (PHRequestState[])$VALUES.clone();
        }
    }

    public static interface PurchaseDelegate {
        public void shouldMakePurchase(PHPublisherContentRequest var1, PHPurchase var2);
    }

    public static interface RewardDelegate {
        public void unlockedReward(PHPublisherContentRequest var1, PHReward var2);
    }

}


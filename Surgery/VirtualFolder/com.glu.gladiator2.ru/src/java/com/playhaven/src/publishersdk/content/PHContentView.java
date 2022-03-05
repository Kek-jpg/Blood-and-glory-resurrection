/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.RectF
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Parcelable
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.webkit.ConsoleMessage
 *  android.webkit.WebChromeClient
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  java.io.File
 *  java.io.IOException
 *  java.io.Serializable
 *  java.io.UnsupportedEncodingException
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.security.NoSuchAlgorithmException
 *  java.util.HashMap
 *  java.util.Hashtable
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.playhaven.src.publishersdk.content;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.jakewharton.DiskLruCache;
import com.playhaven.resources.PHResource;
import com.playhaven.resources.PHResourceManager;
import com.playhaven.resources.files.PHCloseActiveImageResource;
import com.playhaven.resources.files.PHCloseImageResource;
import com.playhaven.src.common.PHAPIRequest;
import com.playhaven.src.common.PHConfig;
import com.playhaven.src.common.PHCrashReport;
import com.playhaven.src.common.PHURLLoader;
import com.playhaven.src.publishersdk.content.PHContent;
import com.playhaven.src.publishersdk.content.PHPublisherContentRequest;
import com.playhaven.src.publishersdk.content.PHPublisherSubContentRequest;
import com.playhaven.src.publishersdk.content.PHPurchase;
import com.playhaven.src.publishersdk.content.PHRequestRouter;
import com.playhaven.src.publishersdk.content.PHReward;
import com.playhaven.src.publishersdk.purchases.PHPublisherIAPTrackingRequest;
import com.playhaven.src.utils.PHConversionUtils;
import com.playhaven.src.utils.PHStringUtil;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Hashtable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PHContentView
extends Activity
implements PHURLLoader.Delegate,
PHAPIRequest.Delegate {
    public static final String BROADCAST_EVENT = "";
    public static final String BROADCAST_INTENT = "com.playhaven.src.publishersdk.content.PHContentViewEvent";
    public static final String BROADCAST_METADATA = "com.playhaven.md";
    private static final int CLOSE_BTN_TIMEOUT = 4000;
    private static final String JAVASCRIPT_CALLBACK_TEMPLATE = "javascript:PlayHaven.nativeAPI.callback(\"%s\", %s, %s)";
    private static final String JAVASCRIPT_SET_PROTOCOL_TEMPLATE = "javascript:window.PlayHavenDispatchProtocolVersion = %d";
    public static final String PURCHASE_CALLBACK_INTENT = "com.playhaven.src.publishersdk.content.PHContentViewPurchaseCallback";
    private final float CLOSE_MARGIN = 10.0f;
    private ImageButton closeBtn;
    private Handler closeBtnDelay;
    private Runnable closeBtnDelayRunnable;
    public PHContent content;
    private HashMap<String, Bitmap> customCloseStates = new HashMap();
    private boolean isBackBtnCancelable;
    private boolean isTouchCancelable;
    private PHRequestRouter mRouter;
    private View overlayView;
    private BroadcastReceiver purchaseReceiver;
    private RelativeLayout rootView;
    public boolean showsOverlayImmediately;
    private String tag;
    private WebView webview;

    private void broadcastEvent(Event event, Bundle bundle) {
        Intent intent = new Intent(BROADCAST_INTENT);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(Detail.Tag.getKey(), this.tag);
        bundle.putString(Detail.Event.getKey(), event.toString());
        intent.putExtra(BROADCAST_METADATA, bundle);
        this.sendBroadcast(intent);
    }

    private void broadcastStartedSubrequest(JSONObject jSONObject, String string2) {
        Bundle bundle = new Bundle();
        bundle.putString(Detail.Context.getKey(), jSONObject.toString());
        bundle.putString(Detail.Callback.getKey(), string2);
        bundle.putString(Detail.Tag.getKey(), this.tag);
        PHContentView.super.broadcastEvent(Event.DidSendSubrequest, bundle);
    }

    private void buttonDismiss() {
        PHStringUtil.log("User dismissed " + this.toString());
        Bundle bundle = new Bundle();
        bundle.putString(Detail.CloseType.getKey(), PHPublisherContentRequest.PHDismissType.CloseButtonTriggered.toString());
        this.broadcastEvent(Event.DidDismiss, bundle);
        super.finish();
    }

    private void closeView() {
        if (this.webview != null) {
            this.webview.setWebChromeClient(null);
            this.webview.setWebViewClient(null);
            this.webview.stopLoading();
        }
        PHURLLoader.invalidateLoaders(this);
        super.finish();
    }

    /*
     * Enabled aggressive block sorting
     */
    private ImageButton getCloseBtn() {
        if (this.closeBtn == null) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            BitmapDrawable bitmapDrawable = this.customCloseStates.get((Object)ButtonState.Up.name()) != null ? new BitmapDrawable((Bitmap)this.customCloseStates.get((Object)ButtonState.Up.name())) : null;
            BitmapDrawable bitmapDrawable2 = this.customCloseStates.get((Object)ButtonState.Down.name()) != null ? new BitmapDrawable((Bitmap)this.customCloseStates.get((Object)ButtonState.Down.name())) : null;
            BitmapDrawable bitmapDrawable3 = bitmapDrawable == null ? new BitmapDrawable(((PHCloseImageResource)PHResourceManager.sharedResourceManager().getResource("close_inactive")).loadImage(PHConfig.screen_density_type)) : bitmapDrawable;
            BitmapDrawable bitmapDrawable4 = bitmapDrawable2 == null ? new BitmapDrawable(((PHCloseActiveImageResource)PHResourceManager.sharedResourceManager().getResource("close_active")).loadImage(PHConfig.screen_density_type)) : bitmapDrawable2;
            int[] arrn = new int[]{ButtonState.Down.getAndroidState()};
            stateListDrawable.addState(arrn, (Drawable)bitmapDrawable4);
            int[] arrn2 = new int[]{ButtonState.Up.getAndroidState()};
            stateListDrawable.addState(arrn2, (Drawable)bitmapDrawable3);
            this.closeBtn = new ImageButton((Context)this);
            this.closeBtn.setContentDescription((CharSequence)"closeButton");
            this.closeBtn.setVisibility(4);
            this.closeBtn.setBackgroundDrawable(null);
            this.closeBtn.setImageDrawable((Drawable)stateListDrawable);
            this.closeBtn.setScaleType(ImageView.ScaleType.FIT_XY);
            this.closeBtn.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    try {
                        PHContentView.this.buttonDismiss();
                        return;
                    }
                    catch (Exception exception) {
                        PHCrashReport.reportCrash(exception, "closeBtn - onClick", PHCrashReport.Urgency.critical);
                        return;
                    }
                }
            });
        }
        return this.closeBtn;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private JSONObject getRequestContext() {
        try {
            JSONObject jSONObject;
            String string2 = PHRequestRouter.getCurrentQueryVar("context");
            if (string2 == null || string2.equals((Object)"undefined")) {
                string2 = "{}";
            }
            if (JSONObject.NULL.equals((Object)(jSONObject = new JSONObject(string2))) || jSONObject.length() <= 0) return null;
            return jSONObject;
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean hasOrientationFrame() {
        block3 : {
            block2 : {
                if (this.content == null) break block2;
                int n = this.getResources().getConfiguration().orientation;
                RectF rectF = this.content.getFrame(n);
                if ((double)rectF.right != 0.0 && (double)rectF.bottom != 0.0) break block3;
            }
            return false;
        }
        return true;
    }

    private void hideCloseButton() {
        if (this.closeBtnDelay != null) {
            this.closeBtnDelay.removeCallbacks(this.closeBtnDelayRunnable);
        }
        this.closeBtn.setVisibility(8);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean loadPrecachedIfExists(String string2) {
        if (!string2.startsWith("http://")) {
            return false;
        }
        if (!PHConfig.precache) return false;
        if (DiskLruCache.getSharedDiskCache() == null) return false;
        PHStringUtil.log("Loading precached version of '" + string2 + "'");
        try {
            DiskLruCache.Snapshot snapshot = DiskLruCache.getSharedDiskCache().get(string2);
            if (snapshot == null) return false;
            File file = snapshot.getInputStreamFile(PHAPIRequest.PRECACHE_FILE_KEY_INDEX);
            snapshot.close();
            if (file == null) return false;
            this.webview.loadUrl("file:///" + file.getAbsolutePath());
            return true;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void loadURLOrPrecached(String string2) {
        try {
            PHStringUtil.log("Loading template URL: '" + string2 + "'");
            boolean bl = PHConfig.precache;
            boolean bl2 = false;
            if (bl) {
                bl2 = PHContentView.super.loadPrecachedIfExists(string2);
            }
            if (bl2) {
                if (!this.content.preloaded) return;
                PHPublisherContentRequest.processResponse(this.content.context, false);
                return;
            }
            this.webview.loadUrl(string2);
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHContentView - loadURLOrPrecache", PHCrashReport.Urgency.low);
        }
    }

    private void placeCloseButton() {
        float f2 = PHConversionUtils.dipToPixels(10.0f);
        float f3 = PHConversionUtils.dipToPixels(10.0f);
        ImageButton imageButton = this.getCloseBtn();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(11);
        layoutParams.setMargins(0, (int)f3, (int)f2, 0);
        imageButton.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        if (imageButton.getParent() != null) {
            ((ViewGroup)imageButton.getParent()).removeView((View)imageButton);
        }
        this.getRootView().addView((View)imageButton);
    }

    public static String pushContent(PHContent pHContent, Context context, HashMap<String, Bitmap> hashMap, String string2) {
        if (context != null) {
            Intent intent = new Intent(context, PHContentView.class);
            intent.putExtra(IntentArgument.Content.getKey(), (Parcelable)pHContent);
            if (hashMap != null && hashMap.size() > 0) {
                intent.putExtra(IntentArgument.CustomCloseBtn.getKey(), hashMap);
            }
            intent.putExtra(IntentArgument.Tag.getKey(), string2);
            context.startActivity(intent);
            return string2;
        }
        return null;
    }

    private void registerPurchaseReceiver() {
        if (this.getApplicationContext() != null) {
            this.purchaseReceiver = new BroadcastReceiver(){

                public void onReceive(Context context, Intent intent) {
                    PHPurchase pHPurchase = (PHPurchase)intent.getBundleExtra(PHContentView.BROADCAST_METADATA).getParcelable(Detail.Purchase.getKey());
                    PHStringUtil.log("Received purchase resolution report in PHContentView: " + pHPurchase.product + " Resolution: " + pHPurchase.resolution.getType());
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("resolution", (Object)pHPurchase.resolution.getType());
                        PHContentView.this.sendCallback(pHPurchase.callback, jSONObject, null);
                        return;
                    }
                    catch (JSONException jSONException) {
                        PHCrashReport.reportCrash((Exception)((Object)jSONException), "PHContentView - BroadcastReceiver - onReceive", PHCrashReport.Urgency.critical);
                        return;
                    }
                }
            };
            this.getApplicationContext().registerReceiver(this.purchaseReceiver, new IntentFilter(PURCHASE_CALLBACK_INTENT + this.tag));
        }
    }

    private void setWebViewCachingDisabled(boolean bl) {
        if (bl) {
            this.webview.getSettings().setCacheMode(2);
            return;
        }
        this.webview.getSettings().setCacheMode(-1);
        this.webview.getSettings().setAppCacheMaxSize((long)PHConfig.precache_size);
        this.webview.getSettings().setAppCachePath(this.getApplicationContext().getCacheDir().getAbsolutePath());
        this.webview.getSettings().setAllowFileAccess(true);
        this.webview.getSettings().setAppCacheEnabled(true);
        this.webview.getSettings().setDomStorageEnabled(true);
        this.webview.getSettings().setDatabaseEnabled(true);
    }

    public static void setWebviewProtocolVersion(WebView webView) {
        if (webView == null) {
            return;
        }
        Object[] arrobject = new Object[]{PHConfig.protocol};
        String string2 = String.format((String)JAVASCRIPT_SET_PROTOCOL_TEMPLATE, (Object[])arrobject);
        PHStringUtil.log("Setting protocol: " + string2);
        webView.loadUrl(string2);
    }

    private void setupWebview() {
        this.webview.setBackgroundColor(0);
        this.setWebViewCachingDisabled(false);
        this.webview.getSettings().setUseWideViewPort(true);
        this.webview.getSettings().setSupportZoom(true);
        this.webview.getSettings().setLoadWithOverviewMode(true);
        this.webview.getSettings().setJavaScriptEnabled(true);
        this.webview.setInitialScale(0);
        this.webview.setWebViewClient((WebViewClient)new PHWebViewClient(this, null));
        this.webview.setWebChromeClient((WebChromeClient)new PHWebViewChrome());
        this.webview.setScrollBarStyle(0);
    }

    private void setupWebviewRoutes() {
        this.mRouter.addRoute("ph://dismiss", new Runnable(){

            public void run() {
                PHContentView.this.handleDismiss();
            }
        });
        this.mRouter.addRoute("ph://launch", new Runnable(){

            public void run() {
                PHContentView.this.handleLaunch();
            }
        });
        this.mRouter.addRoute("ph://loadContext", new Runnable(){

            public void run() {
                PHContentView.this.handleLoadContext();
            }
        });
        this.mRouter.addRoute("ph://reward", new Runnable(){

            public void run() {
                PHContentView.this.handleRewards();
            }
        });
        this.mRouter.addRoute("ph://purchase", new Runnable(){

            public void run() {
                PHContentView.this.handlePurchases();
            }
        });
        this.mRouter.addRoute("ph://subcontent", new Runnable(){

            public void run() {
                PHContentView.this.handleSubrequest();
            }
        });
        this.mRouter.addRoute("ph://closeButton", new Runnable(){

            public void run() {
                PHContentView.this.handleCloseButton();
            }
        });
    }

    private void showCloseAfterTimeout() {
        if (this.closeBtnDelay == null) {
            this.closeBtnDelay = new Handler();
        }
        this.closeBtnDelayRunnable = new Runnable(){

            public void run() {
                PHContentView.this.showCloseButton();
            }
        };
        this.closeBtnDelay.postDelayed(this.closeBtnDelayRunnable, 4000L);
    }

    private void showCloseButton() {
        if (this.closeBtn != null) {
            this.closeBtn.setVisibility(0);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void sizeToOrientation() {
        int n = this.getResources().getConfiguration().orientation;
        RectF rectF = this.content.getFrame(n);
        if (rectF.right == 2.1474836E9f && rectF.bottom == 2.1474836E9f) {
            rectF.right = -1.0f;
            rectF.bottom = -1.0f;
            rectF.top = 0.0f;
            rectF.left = 0.0f;
            this.getWindow().setFlags(1024, 1024);
            this.getWindow().clearFlags(2048);
        } else {
            this.getWindow().clearFlags(1024);
            this.getWindow().addFlags(2048);
        }
        this.getWindow().setLayout((int)rectF.width(), (int)rectF.height());
    }

    public void dismiss() {
        this.closeView();
    }

    public void dismiss(String string2) {
        new Hashtable();
    }

    public PHContent getContent() {
        return this.content;
    }

    public boolean getIsBackBtnCancelable() {
        return this.isBackBtnCancelable;
    }

    public boolean getIsTouchCancelable() {
        return this.isTouchCancelable;
    }

    public View getOverlayView() {
        if (this.overlayView == null) {
            // empty if block
        }
        return this.overlayView;
    }

    public RelativeLayout getRootView() {
        return this.rootView;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleCloseButton() {
        try {
            String string2;
            JSONObject jSONObject = this.getRequestContext();
            if (jSONObject == null) {
                return;
            }
            if (this.closeBtnDelay != null) {
                this.closeBtnDelay.removeCallbacks(this.closeBtnDelayRunnable);
            }
            if (!JSONObject.NULL.equals((Object)(string2 = jSONObject.optString("hidden"))) && string2.length() > 0) {
                ImageButton imageButton = this.closeBtn;
                int n = Boolean.parseBoolean((String)string2) ? 8 : 0;
                imageButton.setVisibility(n);
            }
            JSONObject jSONObject2 = new JSONObject();
            String string3 = this.closeBtn.getVisibility() == 0 ? "false" : "true";
            jSONObject2.put("hidden", (Object)string3);
            this.sendCallback(PHRequestRouter.getCurrentQueryVar("callback"), jSONObject2, null);
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHContentView - handleCloseButton", PHCrashReport.Urgency.critical);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void handleDismiss() {
        JSONObject jSONObject = this.getRequestContext();
        PHURLLoader pHURLLoader = new PHURLLoader((Context)this, this);
        pHURLLoader.openFinalURL = false;
        String string2 = jSONObject != null ? jSONObject.optString("ping", BROADCAST_EVENT) : null;
        pHURLLoader.setTargetURL(string2);
        pHURLLoader.delegate = this;
        pHURLLoader.setCallback(null);
        pHURLLoader.open();
    }

    public void handleLaunch() {
        JSONObject jSONObject = this.getRequestContext();
        if (jSONObject == null) {
            return;
        }
        PHURLLoader pHURLLoader = new PHURLLoader((Context)this, this);
        pHURLLoader.setTargetURL(jSONObject.optString("url", BROADCAST_EVENT));
        pHURLLoader.setCallback(PHRequestRouter.getCurrentQueryVar("callback"));
        pHURLLoader.open();
    }

    public void handleLoadContext() {
        this.sendCallback(PHRequestRouter.getCurrentQueryVar("callback"), this.content.context, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handlePurchases() {
        JSONArray jSONArray;
        try {
            JSONObject jSONObject = this.getRequestContext();
            if (jSONObject == null) {
                return;
            }
            jSONArray = jSONObject.isNull("purchases") ? new JSONArray() : jSONObject.optJSONArray("purchases");
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHContentView - handlePurchase", PHCrashReport.Urgency.critical);
            return;
        }
        int n = 0;
        do {
            if (n >= jSONArray.length()) {
                this.sendCallback(PHRequestRouter.getCurrentQueryVar("callback"), null, null);
                return;
            }
            JSONObject jSONObject = jSONArray.optJSONObject(n);
            if (this.validatePurchase(jSONObject)) {
                PHPurchase pHPurchase = new PHPurchase(PURCHASE_CALLBACK_INTENT + this.tag);
                pHPurchase.product = jSONObject.optString(Purchase.ProductIDKey.key(), BROADCAST_EVENT);
                pHPurchase.name = jSONObject.optString(Purchase.NameKey.key(), BROADCAST_EVENT);
                pHPurchase.quantity = jSONObject.optInt(Purchase.QuantityKey.key(), -1);
                pHPurchase.receipt = jSONObject.optString(Purchase.ReceiptKey.key(), BROADCAST_EVENT);
                pHPurchase.callback = PHRequestRouter.getCurrentQueryVar("callback");
                String string2 = jSONObject.optString(Purchase.CookieKey.key());
                PHPublisherIAPTrackingRequest.setConversionCookie(pHPurchase.product, string2);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Detail.Purchase.getKey(), (Parcelable)pHPurchase);
                this.broadcastEvent(Event.DidMakePurchase, bundle);
            }
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleRewards() {
        JSONArray jSONArray;
        try {
            JSONObject jSONObject = this.getRequestContext();
            if (jSONObject == null) {
                return;
            }
            jSONArray = jSONObject.isNull("rewards") ? new JSONArray() : jSONObject.optJSONArray("rewards");
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHContentView - handleRewards", PHCrashReport.Urgency.low);
            return;
        }
        int n = 0;
        do {
            if (n >= jSONArray.length()) {
                this.sendCallback(PHRequestRouter.getCurrentQueryVar("callback"), null, null);
                return;
            }
            JSONObject jSONObject = jSONArray.optJSONObject(n);
            if (this.validateReward(jSONObject)) {
                PHReward pHReward = new PHReward();
                pHReward.name = jSONObject.optString(Reward.IDKey.key(), BROADCAST_EVENT);
                pHReward.quantity = jSONObject.optInt(Reward.QuantityKey.key(), -1);
                pHReward.receipt = jSONObject.optString(Reward.ReceiptKey.key(), BROADCAST_EVENT);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Detail.Reward.getKey(), (Parcelable)pHReward);
                this.broadcastEvent(Event.DidUnlockReward, bundle);
            }
            ++n;
        } while (true);
    }

    public void handleSubrequest() {
        JSONObject jSONObject = this.getRequestContext();
        if (jSONObject == null) {
            return;
        }
        PHPublisherSubContentRequest pHPublisherSubContentRequest = new PHPublisherSubContentRequest((Context)this, this);
        pHPublisherSubContentRequest.setBaseURL(jSONObject.optString("url", BROADCAST_EVENT));
        pHPublisherSubContentRequest.callback = PHRequestRouter.getCurrentQueryVar("callback");
        pHPublisherSubContentRequest.source = this;
        pHPublisherSubContentRequest.send();
        this.broadcastStartedSubrequest(jSONObject, pHPublisherSubContentRequest.callback);
    }

    protected void loadTemplate() {
        this.webview.stopLoading();
        try {
            PHStringUtil.log("Template URL: " + (Object)this.content.url);
            this.loadURLOrPrecached(this.content.url.toString());
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void loaderFailed(PHURLLoader pHURLLoader) {
        if (pHURLLoader.getCallback() != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("error", (Object)"1");
                jSONObject.put("url", (Object)pHURLLoader.getTargetURL());
                this.sendCallback(pHURLLoader.getCallback(), jSONObject, jSONObject2);
            }
            catch (JSONException jSONException) {
                PHCrashReport.reportCrash((Exception)((Object)jSONException), "PHContentView - loaderFailed", PHCrashReport.Urgency.critical);
            }
            catch (Exception exception) {
                PHCrashReport.reportCrash(exception, "PHContentView - loaderFailed", PHCrashReport.Urgency.critical);
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString(Detail.CloseType.getKey(), PHPublisherContentRequest.PHDismissType.NoContentTriggered.toString());
        PHContentView.super.broadcastEvent(Event.DidDismiss, bundle);
        this.dismiss();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void loaderFinished(PHURLLoader pHURLLoader) {
        if (pHURLLoader.getCallback() != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("url", (Object)pHURLLoader.getTargetURL());
                this.sendCallback(pHURLLoader.getCallback(), jSONObject, null);
                Bundle bundle = new Bundle();
                bundle.putString(Detail.LaunchURL.getKey(), pHURLLoader.getTargetURL());
                PHContentView.super.broadcastEvent(Event.DidLaunchURL, bundle);
            }
            catch (JSONException jSONException) {
                PHCrashReport.reportCrash((Exception)((Object)jSONException), "PHContentView - loaderFinished", PHCrashReport.Urgency.critical);
            }
            catch (Exception exception) {
                PHCrashReport.reportCrash(exception, "PHContentView - loaderFinished", PHCrashReport.Urgency.critical);
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString(Detail.CloseType.getKey(), PHPublisherContentRequest.PHDismissType.ContentUnitTriggered.toString());
        PHContentView.super.broadcastEvent(Event.DidDismiss, bundle);
        this.dismiss();
    }

    public void onAttachedToWindow() {
        try {
            super.onAttachedToWindow();
            if (!this.hasOrientationFrame()) {
                this.dismiss("The content you requested was not able to be shown because it is missing required orientation data.");
                return;
            }
            this.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHContentView - onAttachedToWindow()", PHCrashReport.Urgency.critical);
            return;
        }
    }

    public void onBackPressed() {
        if (this.getIsBackBtnCancelable()) {
            PHStringUtil.log("The content unit was dismissed by the user using back button");
            Bundle bundle = new Bundle();
            bundle.putString(Detail.CloseType.getKey(), PHPublisherContentRequest.PHDismissType.CloseButtonTriggered.toString());
            this.broadcastEvent(Event.DidDismiss, bundle);
            super.onBackPressed();
        }
    }

    protected void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            this.mRouter = new PHRequestRouter();
            this.content = (PHContent)this.getIntent().getParcelableExtra(IntentArgument.Content.getKey());
            this.tag = this.getIntent().getStringExtra(IntentArgument.Tag.getKey());
            if (this.tag != null) {
                PHContentView.super.registerPurchaseReceiver();
            }
            if (this.getIntent().hasExtra(IntentArgument.CustomCloseBtn.getKey())) {
                this.customCloseStates = (HashMap)this.getIntent().getSerializableExtra(IntentArgument.CustomCloseBtn.getKey());
            }
            this.setCancelable(false, true);
            this.getWindow().requestFeature(1);
            PHContentView.super.setupWebviewRoutes();
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, PHCrashReport.Urgency.critical);
            return;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        this.getApplicationContext().unregisterReceiver(this.purchaseReceiver);
        if (this.webview != null) {
            this.webview.setWebChromeClient(null);
            this.webview.setWebViewClient(null);
        }
        if (this.closeBtnDelay != null) {
            this.closeBtnDelay.removeCallbacks(this.closeBtnDelayRunnable);
        }
        this.hideCloseButton();
    }

    protected void onPause() {
        super.onPause();
        PHPublisherContentRequest.dismissedContent();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onStart() {
        super.onStart();
        try {
            DiskLruCache diskLruCache = DiskLruCache.getSharedDiskCache();
            if (PHConfig.precache && diskLruCache != null && diskLruCache.isClosed()) {
                diskLruCache.open();
            }
        }
        catch (IOException iOException) {
            PHCrashReport.reportCrash((Exception)((Object)iOException), "PHContentView - onStart", PHCrashReport.Urgency.critical);
        }
        try {
            this.rootView = new RelativeLayout(this.getApplicationContext());
            this.setContentView((View)this.rootView, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
            this.sizeToOrientation();
            this.webview = new WebView((Context)this);
            this.setupWebview();
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            this.webview.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            this.rootView.addView((View)this.webview);
            if (this.hasOrientationFrame()) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Detail.Content.getKey(), (Parcelable)this.content);
                this.broadcastEvent(Event.DidShow, bundle);
                this.loadTemplate();
            }
            PHContentView.setWebviewProtocolVersion(this.webview);
            this.placeCloseButton();
            this.showCloseAfterTimeout();
            return;
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHContentView - onStart()", PHCrashReport.Urgency.critical);
            return;
        }
    }

    protected void onStop() {
        super.onStop();
        try {
            if (PHConfig.precache && DiskLruCache.getSharedDiskCache() != null) {
                DiskLruCache.getSharedDiskCache().close();
            }
            return;
        }
        catch (IOException iOException) {
            PHCrashReport.reportCrash((Exception)((Object)iOException), "PHContentView - onStop", PHCrashReport.Urgency.critical);
            return;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        try {
            if (motionEvent.getAction() == 4) {
                if (this.getIsTouchCancelable()) {
                    this.finish();
                }
                return true;
            }
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHContentView - onTouchEvent()", PHCrashReport.Urgency.critical);
        }
        return false;
    }

    @Override
    public void requestFailed(PHAPIRequest pHAPIRequest, Exception exception) {
        if (pHAPIRequest == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.putOpt("error", (Object)"1");
            PHPublisherSubContentRequest pHPublisherSubContentRequest = (PHPublisherSubContentRequest)pHAPIRequest;
            pHPublisherSubContentRequest.source.sendCallback(pHPublisherSubContentRequest.callback, null, jSONObject);
            return;
        }
        catch (JSONException jSONException) {
            PHCrashReport.reportCrash((Exception)((Object)jSONException), "PHContentView - requestFailed(request, responseData)", PHCrashReport.Urgency.low);
            return;
        }
        catch (Exception exception2) {
            PHCrashReport.reportCrash(exception2, "PHContentView - requestFailed(request, responseData)", PHCrashReport.Urgency.critical);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void requestSucceeded(PHAPIRequest pHAPIRequest, JSONObject jSONObject) {
        if (jSONObject.length() == 0) {
            return;
        }
        try {
            PHContent pHContent = new PHContent(jSONObject);
            PHPublisherSubContentRequest pHPublisherSubContentRequest = (PHPublisherSubContentRequest)pHAPIRequest;
            if (pHContent.url != null) {
                PHContentView.pushContent(pHContent, (Context)this, null, this.tag);
                pHPublisherSubContentRequest.source.sendCallback(pHPublisherSubContentRequest.callback, jSONObject, null);
                return;
            }
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("error", (Object)"1");
                pHPublisherSubContentRequest.source.sendCallback(pHPublisherSubContentRequest.callback, jSONObject, jSONObject2);
                Bundle bundle = new Bundle();
                bundle.putString(Detail.CloseType.getKey(), PHPublisherContentRequest.PHDismissType.ApplicationTriggered.toString());
                PHContentView.super.broadcastEvent(Event.DidDismiss, bundle);
                return;
            }
            catch (JSONException jSONException) {
                jSONException.printStackTrace();
                return;
            }
        }
        catch (Exception exception) {
            PHCrashReport.reportCrash(exception, "PHContentView - requestSucceeded(request, responseData)", PHCrashReport.Urgency.critical);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void sendCallback(String string2, JSONObject jSONObject, JSONObject jSONObject2) {
        Object[] arrobject = new Object[3];
        if (string2 == null) {
            string2 = "null";
        }
        arrobject[0] = string2;
        String string3 = jSONObject != null ? jSONObject.toString() : "null";
        arrobject[1] = string3;
        String string4 = jSONObject2 != null ? jSONObject2.toString() : "null";
        arrobject[2] = string4;
        String string5 = String.format((String)JAVASCRIPT_CALLBACK_TEMPLATE, (Object[])arrobject);
        PHStringUtil.log("Sending JavaScript callback to webview: '" + string5);
        this.webview.loadUrl(string5);
    }

    public void setCancelable(boolean bl, boolean bl2) {
        this.isTouchCancelable = bl;
        this.isBackBtnCancelable = bl2;
    }

    public void setContent(PHContent pHContent) {
        if (pHContent != null) {
            this.content = pHContent;
        }
    }

    public boolean setIsBackBtnCancelable(boolean bl) {
        this.isBackBtnCancelable = bl;
        return bl;
    }

    public void setOverlayView(View view) {
        this.overlayView = view;
    }

    public boolean validatePurchase(JSONObject jSONObject) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (JSONObject.NULL.equals((Object)jSONObject) || jSONObject.length() == 0) {
            return false;
        }
        String string2 = jSONObject.optString(Purchase.ProductIDKey.key(), BROADCAST_EVENT);
        String string3 = jSONObject.optString(Purchase.NameKey.key(), BROADCAST_EVENT);
        String string4 = jSONObject.optString(Purchase.QuantityKey.key(), BROADCAST_EVENT);
        String string5 = jSONObject.optString(Purchase.ReceiptKey.key(), BROADCAST_EVENT);
        String string6 = jSONObject.optString(Purchase.SignatureKey.key(), BROADCAST_EVENT);
        Object[] arrobject = new Object[]{string2, string3, string4, PHConfig.device_id, string5, PHConfig.secret};
        String string7 = PHStringUtil.hexDigest(String.format((String)"%s:%s:%s:%s:%s:%s", (Object[])arrobject));
        PHStringUtil.log("Checking purchase signature:  " + string6 + " against: " + string7);
        return string7.equals((Object)string6);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean validateReward(JSONObject jSONObject) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (JSONObject.NULL.equals((Object)jSONObject) || jSONObject.length() == 0) {
            return false;
        }
        String string2 = jSONObject.optString(Reward.IDKey.key(), BROADCAST_EVENT);
        String string3 = jSONObject.optString(Reward.QuantityKey.key(), BROADCAST_EVENT);
        String string4 = jSONObject.optString(Reward.ReceiptKey.key(), BROADCAST_EVENT);
        String string5 = jSONObject.optString(Reward.SignatureKey.key(), BROADCAST_EVENT);
        String string6 = PHConfig.device_id != null ? PHConfig.device_id : "null";
        Object[] arrobject = new Object[]{string2, string3, string6, string4, PHConfig.secret};
        String string7 = PHStringUtil.hexDigest(String.format((String)"%s:%s:%s:%s:%s", (Object[])arrobject));
        PHStringUtil.log("Checking reward signature:  " + string5 + " against: " + string7);
        return string7.equalsIgnoreCase(string5);
    }

    public static final class ButtonState
    extends Enum<ButtonState> {
        private static final /* synthetic */ ButtonState[] $VALUES;
        public static final /* enum */ ButtonState Down = new ButtonState(16842919);
        public static final /* enum */ ButtonState Up = new ButtonState(16842910);
        private int android_state;

        static {
            ButtonState[] arrbuttonState = new ButtonState[]{Down, Up};
            $VALUES = arrbuttonState;
        }

        private ButtonState(int n2) {
            this.android_state = n2;
        }

        public static ButtonState valueOf(String string2) {
            return (ButtonState)Enum.valueOf(ButtonState.class, (String)string2);
        }

        public static ButtonState[] values() {
            return (ButtonState[])$VALUES.clone();
        }

        public int getAndroidState() {
            return this.android_state;
        }
    }

    public static final class Detail
    extends Enum<Detail> {
        private static final /* synthetic */ Detail[] $VALUES;
        public static final /* enum */ Detail Callback;
        public static final /* enum */ Detail CloseType;
        public static final /* enum */ Detail Content;
        public static final /* enum */ Detail Context;
        public static final /* enum */ Detail Dispatch;
        public static final /* enum */ Detail Error;
        public static final /* enum */ Detail Event;
        public static final /* enum */ Detail LaunchURL;
        public static final /* enum */ Detail Purchase;
        public static final /* enum */ Detail Reward;
        public static final /* enum */ Detail Tag;
        private String key;

        static {
            Event = new Detail("event_contentview");
            CloseType = new Detail("closetype_contentview");
            Callback = new Detail("callback_contentview");
            Context = new Detail("context_contentview");
            Content = new Detail("content_contentview");
            Error = new Detail("error_contentview");
            Reward = new Detail("reward_contentview");
            Purchase = new Detail("purchase_contentview");
            Tag = new Detail("content_tag");
            Dispatch = new Detail("dispatch_contentview");
            LaunchURL = new Detail("launchurl_contentview");
            Detail[] arrdetail = new Detail[]{Event, CloseType, Callback, Context, Content, Error, Reward, Purchase, Tag, Dispatch, LaunchURL};
            $VALUES = arrdetail;
        }

        private Detail(String string3) {
            this.key = string3;
        }

        public static Detail valueOf(String string2) {
            return (Detail)Enum.valueOf(Detail.class, (String)string2);
        }

        public static Detail[] values() {
            return (Detail[])$VALUES.clone();
        }

        public String getKey() {
            return this.key;
        }
    }

    public static final class Event
    extends Enum<Event> {
        private static final /* synthetic */ Event[] $VALUES;
        public static final /* enum */ Event DidDismiss;
        public static final /* enum */ Event DidFail;
        public static final /* enum */ Event DidLaunchURL;
        public static final /* enum */ Event DidLoad;
        public static final /* enum */ Event DidMakePurchase;
        public static final /* enum */ Event DidReceiveDispatch;
        public static final /* enum */ Event DidSendSubrequest;
        public static final /* enum */ Event DidShow;
        public static final /* enum */ Event DidUnlockReward;

        static {
            DidShow = new Event();
            DidLoad = new Event();
            DidDismiss = new Event();
            DidFail = new Event();
            DidUnlockReward = new Event();
            DidMakePurchase = new Event();
            DidSendSubrequest = new Event();
            DidReceiveDispatch = new Event();
            DidLaunchURL = new Event();
            Event[] arrevent = new Event[]{DidShow, DidLoad, DidDismiss, DidFail, DidUnlockReward, DidMakePurchase, DidSendSubrequest, DidReceiveDispatch, DidLaunchURL};
            $VALUES = arrevent;
        }

        public static Event valueOf(String string2) {
            return (Event)Enum.valueOf(Event.class, (String)string2);
        }

        public static Event[] values() {
            return (Event[])$VALUES.clone();
        }
    }

    public static final class IntentArgument
    extends Enum<IntentArgument> {
        private static final /* synthetic */ IntentArgument[] $VALUES;
        public static final /* enum */ IntentArgument Content;
        public static final /* enum */ IntentArgument CustomCloseBtn;
        public static final /* enum */ IntentArgument Tag;
        private String key;

        static {
            CustomCloseBtn = new IntentArgument("custom_close");
            Content = new IntentArgument("init_content_contentview");
            Tag = new IntentArgument("content_tag");
            IntentArgument[] arrintentArgument = new IntentArgument[]{CustomCloseBtn, Content, Tag};
            $VALUES = arrintentArgument;
        }

        private IntentArgument(String string3) {
            this.key = string3;
        }

        public static IntentArgument valueOf(String string2) {
            return (IntentArgument)Enum.valueOf(IntentArgument.class, (String)string2);
        }

        public static IntentArgument[] values() {
            return (IntentArgument[])$VALUES.clone();
        }

        public String getKey() {
            return this.key;
        }
    }

    public class PHWebViewChrome
    extends WebChromeClient {
        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            String string2 = "(no file)";
            try {
                if (consoleMessage.sourceId() != null) {
                    string2 = Uri.parse((String)consoleMessage.sourceId()).getLastPathSegment();
                }
                PHStringUtil.log("Javascript: " + consoleMessage.message() + " at line (" + string2 + ") :" + consoleMessage.lineNumber());
                do {
                    return true;
                    break;
                } while (true);
            }
            catch (Exception exception) {
                PHCrashReport.reportCrash(exception, "PHWebViewChrome - onConsoleMessage", PHCrashReport.Urgency.low);
                return true;
            }
        }
    }

    private class PHWebViewClient
    extends WebViewClient {
        final /* synthetic */ PHContentView this$0;

        private PHWebViewClient(PHContentView pHContentView) {
            this.this$0 = pHContentView;
        }

        /* synthetic */ PHWebViewClient(PHContentView pHContentView, 1 var2_2) {
            super(pHContentView);
        }

        private void broadcastDispatch(String string2) {
            if (string2 == null || string2.length() == 0) {
                return;
            }
            int n = string2.indexOf("?");
            if (n != -1) {
                string2 = string2.substring(0, n);
            }
            Bundle bundle = new Bundle();
            bundle.putString(Detail.Dispatch.getKey(), string2);
            this.this$0.broadcastEvent(Event.DidReceiveDispatch, bundle);
        }

        private boolean routePlayhavenCallback(String string2) {
            PHStringUtil.log("Received webview callback: " + string2);
            try {
                if (this.this$0.mRouter.hasRoute(string2)) {
                    PHWebViewClient.super.broadcastDispatch(string2);
                    this.this$0.mRouter.route(string2);
                    return true;
                }
            }
            catch (Exception exception) {
                PHCrashReport.reportCrash(exception, "PHWebViewClient - url routing", PHCrashReport.Urgency.critical);
            }
            return false;
        }

        public void onLoadResource(WebView webView, String string2) {
            try {
                if (string2.startsWith("ph://")) {
                    PHWebViewClient.super.routePlayhavenCallback(string2);
                }
                return;
            }
            catch (Exception exception) {
                PHCrashReport.reportCrash(exception, "PHWebViewClient - onLoadResource", PHCrashReport.Urgency.critical);
                return;
            }
        }

        public void onPageFinished(WebView webView, String string2) {
            try {
                this.this$0.broadcastEvent(Event.DidLoad, null);
                return;
            }
            catch (Exception exception) {
                PHCrashReport.reportCrash(exception, "PHWebViewClient - onPageFinished()", PHCrashReport.Urgency.critical);
                return;
            }
        }

        public void onReceivedError(WebView webView, int n, String string2, String string3) {
            try {
                webView.loadUrl(PHContentView.BROADCAST_EVENT);
                PHCrashReport.reportCrash((Exception)((Object)new RuntimeException(string2)), "PHWebViewClient - onRecievedError", PHCrashReport.Urgency.low);
                Object[] arrobject = new Object[]{string3, n, string2};
                PHStringUtil.log(String.format((String)"Error loading template at url: %s Code: %d Description: %s", (Object[])arrobject));
                return;
            }
            catch (Exception exception) {
                PHCrashReport.reportCrash(exception, "PHWebViewClient - onReceivedError", PHCrashReport.Urgency.low);
                return;
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String string2) {
            boolean bl;
            block3 : {
                boolean bl2;
                try {
                    bl = PHWebViewClient.super.routePlayhavenCallback(string2);
                    if (bl) break block3;
                }
                catch (Exception exception) {
                    PHCrashReport.reportCrash(exception, "PHWebViewClient - shouldOverrideUrlLoading", PHCrashReport.Urgency.critical);
                    return false;
                }
                if (!PHConfig.precache) break block3;
                bl = bl2 = this.this$0.loadPrecachedIfExists(string2);
            }
            return bl;
        }
    }

    public static final class Purchase
    extends Enum<Purchase> {
        private static final /* synthetic */ Purchase[] $VALUES;
        public static final /* enum */ Purchase CookieKey;
        public static final /* enum */ Purchase NameKey;
        public static final /* enum */ Purchase ProductIDKey;
        public static final /* enum */ Purchase QuantityKey;
        public static final /* enum */ Purchase ReceiptKey;
        public static final /* enum */ Purchase SignatureKey;
        private final String keyName;

        static {
            ProductIDKey = new Purchase("product");
            NameKey = new Purchase("name");
            QuantityKey = new Purchase("quantity");
            ReceiptKey = new Purchase("receipt");
            SignatureKey = new Purchase("signature");
            CookieKey = new Purchase("cookie");
            Purchase[] arrpurchase = new Purchase[]{ProductIDKey, NameKey, QuantityKey, ReceiptKey, SignatureKey, CookieKey};
            $VALUES = arrpurchase;
        }

        private Purchase(String string3) {
            this.keyName = string3;
        }

        public static Purchase valueOf(String string2) {
            return (Purchase)Enum.valueOf(Purchase.class, (String)string2);
        }

        public static Purchase[] values() {
            return (Purchase[])$VALUES.clone();
        }

        public String key() {
            return this.keyName;
        }
    }

    public static final class Reward
    extends Enum<Reward> {
        private static final /* synthetic */ Reward[] $VALUES;
        public static final /* enum */ Reward IDKey = new Reward("reward");
        public static final /* enum */ Reward QuantityKey = new Reward("quantity");
        public static final /* enum */ Reward ReceiptKey = new Reward("receipt");
        public static final /* enum */ Reward SignatureKey = new Reward("signature");
        private final String keyName;

        static {
            Reward[] arrreward = new Reward[]{IDKey, QuantityKey, ReceiptKey, SignatureKey};
            $VALUES = arrreward;
        }

        private Reward(String string3) {
            this.keyName = string3;
        }

        public static Reward valueOf(String string2) {
            return (Reward)Enum.valueOf(Reward.class, (String)string2);
        }

        public static Reward[] values() {
            return (Reward[])$VALUES.clone();
        }

        public String key() {
            return this.keyName;
        }
    }

}


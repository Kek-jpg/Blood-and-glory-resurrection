/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.webkit.JsResult
 *  android.webkit.WebChromeClient
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  java.io.File
 *  java.io.FileNotFoundException
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.CharSequence
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.Throwable
 *  java.net.URI
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.NameValuePair
 *  org.apache.http.client.ClientProtocolException
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.client.utils.URLEncodedUtils
 *  org.apache.http.impl.client.DefaultHttpClient
 */
package com.amazon.device.ads;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.amazon.device.ads.AdBridge;
import com.amazon.device.ads.Log;
import com.amazon.device.ads.MraidBrowserController;
import com.amazon.device.ads.MraidCommand;
import com.amazon.device.ads.MraidCommandRegistry;
import com.amazon.device.ads.MraidDisplayController;
import com.amazon.device.ads.MraidPlacementTypeProperty;
import com.amazon.device.ads.MraidProperty;
import com.amazon.device.ads.MraidRenderer;
import com.amazon.device.ads.ResourceLookup;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

class MraidView
extends WebView {
    public static final int AD_CONTAINER_LAYOUT_ID = 102;
    private static final String LOGTAG = "MraidView";
    public static final int MODAL_CONTAINER_LAYOUT_ID = 101;
    public static final int PLACEHOLDER_VIEW_ID = 100;
    private boolean mAttached;
    private MraidBrowserController mBrowserController;
    private MraidDisplayController mDisplayController;
    private boolean mGoingAway;
    private boolean mHasFiredReadyEvent;
    private int mLastVisibility;
    private MraidListenerInfo mListenerInfo;
    private final PlacementType mPlacementType;
    private WebChromeClient mWebChromeClient;
    private WebViewClient mWebViewClient;
    private MraidRenderer renderer_;
    private double scalingFactor_;
    private int windowHeight_;
    private int windowWidth_;

    public MraidView(MraidRenderer mraidRenderer, int n2, int n3, double d2) {
        super(mraidRenderer, n2, n3, d2, ExpansionStyle.ENABLED, NativeCloseButtonStyle.AD_CONTROLLED, PlacementType.INLINE);
    }

    MraidView(MraidRenderer mraidRenderer, int n2, int n3, double d2, ExpansionStyle expansionStyle, NativeCloseButtonStyle nativeCloseButtonStyle, PlacementType placementType) {
        super(mraidRenderer.bridge_.getContext());
        this.mAttached = false;
        this.mLastVisibility = 8;
        this.mGoingAway = false;
        this.renderer_ = mraidRenderer;
        this.mPlacementType = placementType;
        this.windowHeight_ = n3;
        this.windowWidth_ = n2;
        this.scalingFactor_ = d2;
        MraidView.super.initialize(expansionStyle, nativeCloseButtonStyle);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private String copyRawResourceToFilesDir(String string, String string2) {
        FileOutputStream fileOutputStream;
        InputStream inputStream;
        String string3;
        block13 : {
            inputStream = ResourceLookup.getResourceFile(this.getContext(), string);
            if (inputStream == null) {
                return "";
            }
            string3 = this.getContext().getFilesDir().getAbsolutePath() + File.separator + string2;
            File file = new File(string3);
            fileOutputStream = new FileOutputStream(file);
            byte[] arrby = new byte[8192];
            try {
                int n2;
                while ((n2 = inputStream.read(arrby)) != -1) {
                    fileOutputStream.write(arrby, 0, n2);
                }
                break block13;
            }
            catch (IOException iOException) {
                try {
                    inputStream.close();
                    fileOutputStream.close();
                    return "";
                }
                catch (IOException iOException2) {
                    return "";
                }
            }
            catch (FileNotFoundException fileNotFoundException) {
                return "";
            }
        }
        try {
            inputStream.close();
            fileOutputStream.close();
            return string3;
        }
        catch (IOException iOException) {
            return string3;
        }
        catch (Throwable throwable) {
            try {
                inputStream.close();
                fileOutputStream.close();
            }
            catch (IOException iOException) {
                throw throwable;
            }
            throw throwable;
        }
    }

    private void initialize(ExpansionStyle expansionStyle, NativeCloseButtonStyle nativeCloseButtonStyle) {
        this.setScrollContainer(false);
        this.setBackgroundColor(0);
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setPluginsEnabled(true);
        this.mBrowserController = new MraidBrowserController((MraidView)this);
        this.mDisplayController = new MraidDisplayController((MraidView)this, expansionStyle, nativeCloseButtonStyle);
        this.mWebViewClient = new MraidWebViewClient((MraidView)this, null);
        this.setWebViewClient(this.mWebViewClient);
        this.mWebChromeClient = new MraidWebChromeClient((MraidView)this, null);
        this.setWebChromeClient(this.mWebChromeClient);
        this.mListenerInfo = new MraidListenerInfo();
    }

    private void notifyOnFailureListener() {
        if (this.mListenerInfo.mOnFailureListener != null) {
            this.mListenerInfo.mOnFailureListener.onFailure(this);
        }
    }

    private boolean tryCommand(URI uRI) {
        String string = uRI.getHost();
        List list = URLEncodedUtils.parse((URI)uRI, (String)"UTF-8");
        HashMap hashMap = new HashMap();
        for (NameValuePair nameValuePair : list) {
            hashMap.put((Object)nameValuePair.getName(), (Object)nameValuePair.getValue());
        }
        MraidCommand mraidCommand = MraidCommandRegistry.createCommand(string, (Map<String, String>)hashMap, (MraidView)this);
        if (mraidCommand == null) {
            this.fireNativeCommandCompleteEvent(string);
            return false;
        }
        mraidCommand.execute();
        this.fireNativeCommandCompleteEvent(string);
        return true;
    }

    public void destroy() {
        this.mDisplayController.destroy();
        super.destroy();
    }

    protected void fireChangeEventForProperties(ArrayList<MraidProperty> arrayList) {
        String string = arrayList.toString();
        if (string.length() < 2) {
            return;
        }
        String string2 = "{" + string.substring(1, -1 + string.length()) + "}";
        this.injectJavaScript("window.mraidbridge.fireChangeEvent(" + string2 + ");");
        Log.d(LOGTAG, "Fire changes: %s", string2);
    }

    protected void fireChangeEventForProperty(MraidProperty mraidProperty) {
        String string = "{" + mraidProperty.toString() + "}";
        this.injectJavaScript("window.mraidbridge.fireChangeEvent(" + string + ");");
        Log.d(LOGTAG, "Fire change: %s", string);
    }

    protected void fireErrorEvent(String string, String string2) {
        this.injectJavaScript("window.mraidbridge.fireErrorEvent('" + string + "', '" + string2 + "');");
    }

    protected void fireNativeCommandCompleteEvent(String string) {
        this.injectJavaScript("window.mraidbridge.nativeCallComplete('" + string + "');");
    }

    protected void fireReadyEvent() {
        this.injectJavaScript("window.mraidbridge.fireReadyEvent();");
    }

    protected MraidBrowserController getBrowserController() {
        return this.mBrowserController;
    }

    protected MraidDisplayController getDisplayController() {
        return this.mDisplayController;
    }

    public OnCloseButtonStateChangeListener getOnCloseButtonStateChangeListener() {
        return this.mListenerInfo.mOnCloseButtonListener;
    }

    public OnCloseListener getOnCloseListener() {
        return this.mListenerInfo.mOnCloseListener;
    }

    public OnExpandListener getOnExpandListener() {
        return this.mListenerInfo.mOnExpandListener;
    }

    public OnFailureListener getOnFailureListener() {
        return this.mListenerInfo.mOnFailureListener;
    }

    public OnOpenListener getOnOpenListener() {
        return this.mListenerInfo.mOnOpenListener;
    }

    public OnReadyListener getOnReadyListener() {
        return this.mListenerInfo.mOnReadyListener;
    }

    public OnSpecialUrlClickListener getOnSpecialUrlClickListener() {
        return this.mListenerInfo.mOnSpecialUrlClickListener;
    }

    protected MraidRenderer getRenderer() {
        return this.renderer_;
    }

    protected double getScalingFactor() {
        return this.scalingFactor_;
    }

    protected int getWindowHeight() {
        return this.windowHeight_;
    }

    protected int getWindowWidth() {
        return this.windowWidth_;
    }

    protected void injectJavaScript(String string) {
        if (string != null) {
            super.loadUrl("javascript:" + string);
        }
    }

    public void loadHtmlData(String string) {
        if (string.indexOf("<html>") == -1) {
            string = "<html><meta name=\"viewport\" content=\"width=" + this.windowWidth_ + ", height=" + this.windowHeight_ + ", initial-scale=" + this.scalingFactor_ + ", minimum-scale=" + this.scalingFactor_ + ", maximum-scale=" + this.scalingFactor_ + "\"/>" + "<head></head><body style='margin:0;padding:0;'>" + string + "</body></html>";
        }
        String string2 = "file://" + MraidView.super.copyRawResourceToFilesDir("ad_resources/raw/amazon_ads_mraid.js", "mraid.js");
        this.loadDataWithBaseURL(null, string.replace((CharSequence)"<head>", (CharSequence)("<head><script src='" + string2 + "'></script>")), "text/html", "UTF-8", null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void loadUrl(String string) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(string);
        StringBuffer stringBuffer = new StringBuffer();
        try {
            HttpEntity httpEntity = defaultHttpClient.execute((HttpUriRequest)httpGet).getEntity();
            if (httpEntity != null) {
                int n2;
                InputStream inputStream = httpEntity.getContent();
                byte[] arrby = new byte[4096];
                while ((n2 = inputStream.read(arrby)) != -1) {
                    stringBuffer.append(new String(arrby, 0, n2));
                }
            }
        }
        catch (ClientProtocolException clientProtocolException) {
            MraidView.super.notifyOnFailureListener();
            return;
        }
        catch (IOException iOException) {
            MraidView.super.notifyOnFailureListener();
            return;
        }
        this.loadHtmlData(stringBuffer.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onAttachedToWindow() {
        block3 : {
            block2 : {
                if (this.mGoingAway) break block2;
                super.onAttachedToWindow();
                this.mAttached = true;
                if (this.mDisplayController != null) break block3;
            }
            return;
        }
        this.mDisplayController.registerRecievers();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttached = false;
        if (this.mDisplayController != null) {
            this.mDisplayController.unregisterRecievers();
        }
    }

    protected void onWindowVisibilityChanged(int n2) {
        if (this.mAttached && this.mLastVisibility != n2 && n2 != 0 && this.mDisplayController != null) {
            this.mDisplayController.unregisterRecievers();
        }
    }

    public void prepareToGoAway() {
        this.mGoingAway = true;
        if (this.mDisplayController != null) {
            this.mDisplayController.detachExpandedView();
        }
    }

    public void setOnCloseButtonStateChange(OnCloseButtonStateChangeListener onCloseButtonStateChangeListener) {
        this.mListenerInfo.mOnCloseButtonListener = onCloseButtonStateChangeListener;
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.mListenerInfo.mOnCloseListener = onCloseListener;
    }

    public void setOnExpandListener(OnExpandListener onExpandListener) {
        this.mListenerInfo.mOnExpandListener = onExpandListener;
    }

    public void setOnFailureListener(OnFailureListener onFailureListener) {
        this.mListenerInfo.mOnFailureListener = onFailureListener;
    }

    public void setOnOpenListener(OnOpenListener onOpenListener) {
        this.mListenerInfo.mOnOpenListener = onOpenListener;
    }

    public void setOnReadyListener(OnReadyListener onReadyListener) {
        this.mListenerInfo.mOnReadyListener = onReadyListener;
    }

    public void setOnSpecialUrlClickListener(OnSpecialUrlClickListener onSpecialUrlClickListener) {
        this.mListenerInfo.mOnSpecialUrlClickListener = onSpecialUrlClickListener;
    }

    static final class ExpansionStyle
    extends Enum<ExpansionStyle> {
        private static final /* synthetic */ ExpansionStyle[] $VALUES;
        public static final /* enum */ ExpansionStyle DISABLED;
        public static final /* enum */ ExpansionStyle ENABLED;

        static {
            ENABLED = new ExpansionStyle();
            DISABLED = new ExpansionStyle();
            ExpansionStyle[] arrexpansionStyle = new ExpansionStyle[]{ENABLED, DISABLED};
            $VALUES = arrexpansionStyle;
        }

        public static ExpansionStyle valueOf(String string) {
            return (ExpansionStyle)Enum.valueOf(ExpansionStyle.class, (String)string);
        }

        public static ExpansionStyle[] values() {
            return (ExpansionStyle[])$VALUES.clone();
        }
    }

    static class MraidListenerInfo {
        private OnCloseButtonStateChangeListener mOnCloseButtonListener;
        private OnCloseListener mOnCloseListener;
        private OnExpandListener mOnExpandListener;
        private OnFailureListener mOnFailureListener;
        private OnOpenListener mOnOpenListener;
        private OnReadyListener mOnReadyListener;
        private OnSpecialUrlClickListener mOnSpecialUrlClickListener;

        MraidListenerInfo() {
        }
    }

    private class MraidWebChromeClient
    extends WebChromeClient {
        final /* synthetic */ MraidView this$0;

        private MraidWebChromeClient(MraidView mraidView) {
            this.this$0 = mraidView;
        }

        /* synthetic */ MraidWebChromeClient(MraidView mraidView, 1 var2_2) {
            super(mraidView);
        }

        public boolean onJsAlert(WebView webView, String string, String string2, JsResult jsResult) {
            Log.d(MraidView.LOGTAG, string2);
            return false;
        }
    }

    private class MraidWebViewClient
    extends WebViewClient {
        final /* synthetic */ MraidView this$0;

        private MraidWebViewClient(MraidView mraidView) {
            this.this$0 = mraidView;
        }

        /* synthetic */ MraidWebViewClient(MraidView mraidView, 1 var2_2) {
            super(mraidView);
        }

        public void onLoadResource(WebView webView, String string) {
            Log.d(MraidView.LOGTAG, "Loaded resource: %s", string);
        }

        public void onPageFinished(WebView webView, String string) {
            if (!this.this$0.mHasFiredReadyEvent) {
                this.this$0.mDisplayController.initializeJavaScriptState();
                this.this$0.fireChangeEventForProperty(MraidPlacementTypeProperty.createWithType(this.this$0.mPlacementType));
                this.this$0.fireReadyEvent();
                if (this.this$0.getOnReadyListener() != null) {
                    this.this$0.getOnReadyListener().onReady(this.this$0);
                }
                this.this$0.mHasFiredReadyEvent = true;
                this.this$0.mDisplayController.surfaceAd();
            }
        }

        public void onReceivedError(WebView webView, int n2, String string, String string2) {
            Log.d(MraidView.LOGTAG, "Error: %s", string);
            super.onReceivedError(webView, n2, string, string2);
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String string) {
            String string2 = Uri.parse((String)string).getScheme();
            if (string2.equals((Object)"mopub")) {
                return true;
            }
            if (string2.equals((Object)"mraid")) {
                this.this$0.tryCommand(URI.create((String)string));
                return true;
            }
            if (string2.equals((Object)"amazonmobile")) {
                this.this$0.mBrowserController.executeAmazonMobileCallback(this.this$0, string);
                return true;
            }
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse((String)string));
            intent.addFlags(268435456);
            try {
                this.this$0.getContext().startActivity(intent);
                return true;
            }
            catch (ActivityNotFoundException activityNotFoundException) {
                return false;
            }
        }
    }

    static final class NativeCloseButtonStyle
    extends Enum<NativeCloseButtonStyle> {
        private static final /* synthetic */ NativeCloseButtonStyle[] $VALUES;
        public static final /* enum */ NativeCloseButtonStyle AD_CONTROLLED;
        public static final /* enum */ NativeCloseButtonStyle ALWAYS_HIDDEN;
        public static final /* enum */ NativeCloseButtonStyle ALWAYS_VISIBLE;

        static {
            ALWAYS_VISIBLE = new NativeCloseButtonStyle();
            ALWAYS_HIDDEN = new NativeCloseButtonStyle();
            AD_CONTROLLED = new NativeCloseButtonStyle();
            NativeCloseButtonStyle[] arrnativeCloseButtonStyle = new NativeCloseButtonStyle[]{ALWAYS_VISIBLE, ALWAYS_HIDDEN, AD_CONTROLLED};
            $VALUES = arrnativeCloseButtonStyle;
        }

        public static NativeCloseButtonStyle valueOf(String string) {
            return (NativeCloseButtonStyle)Enum.valueOf(NativeCloseButtonStyle.class, (String)string);
        }

        public static NativeCloseButtonStyle[] values() {
            return (NativeCloseButtonStyle[])$VALUES.clone();
        }
    }

    public static interface OnCloseButtonStateChangeListener {
        public void onCloseButtonStateChange(MraidView var1, boolean var2);
    }

    public static interface OnCloseListener {
        public void onClose(MraidView var1, ViewState var2);
    }

    public static interface OnExpandListener {
        public void onExpand(MraidView var1);
    }

    public static interface OnFailureListener {
        public void onFailure(MraidView var1);
    }

    public static interface OnOpenListener {
        public void onOpen(MraidView var1);
    }

    public static interface OnReadyListener {
        public void onReady(MraidView var1);
    }

    public static interface OnSpecialUrlClickListener {
        public void onSpecialUrlClick(MraidView var1, String var2);
    }

    static final class PlacementType
    extends Enum<PlacementType> {
        private static final /* synthetic */ PlacementType[] $VALUES;
        public static final /* enum */ PlacementType INLINE = new PlacementType();
        public static final /* enum */ PlacementType INTERSTITIAL = new PlacementType();

        static {
            PlacementType[] arrplacementType = new PlacementType[]{INLINE, INTERSTITIAL};
            $VALUES = arrplacementType;
        }

        public static PlacementType valueOf(String string) {
            return (PlacementType)Enum.valueOf(PlacementType.class, (String)string);
        }

        public static PlacementType[] values() {
            return (PlacementType[])$VALUES.clone();
        }
    }

    public static final class ViewState
    extends Enum<ViewState> {
        private static final /* synthetic */ ViewState[] $VALUES;
        public static final /* enum */ ViewState DEFAULT;
        public static final /* enum */ ViewState EXPANDED;
        public static final /* enum */ ViewState HIDDEN;
        public static final /* enum */ ViewState LOADING;

        static {
            LOADING = new ViewState();
            DEFAULT = new ViewState();
            EXPANDED = new ViewState();
            HIDDEN = new ViewState();
            ViewState[] arrviewState = new ViewState[]{LOADING, DEFAULT, EXPANDED, HIDDEN};
            $VALUES = arrviewState;
        }

        public static ViewState valueOf(String string) {
            return (ViewState)Enum.valueOf(ViewState.class, (String)string);
        }

        public static ViewState[] values() {
            return (ViewState[])$VALUES.clone();
        }
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 *  android.view.View
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Hashtable
 */
package com.tapjoy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import com.glu.plugins.TapjoyGlu;
import com.tapjoy.TJCOffers;
import com.tapjoy.TJCVirtualGoodUtil;
import com.tapjoy.TJCVirtualGoods;
import com.tapjoy.TJCVirtualGoodsData;
import com.tapjoy.TapjoyAwardPointsNotifier;
import com.tapjoy.TapjoyConnectCore;
import com.tapjoy.TapjoyDisplayAd;
import com.tapjoy.TapjoyDisplayAdNotifier;
import com.tapjoy.TapjoyEarnedPointsNotifier;
import com.tapjoy.TapjoyEvent;
import com.tapjoy.TapjoyFeaturedApp;
import com.tapjoy.TapjoyFeaturedAppNotifier;
import com.tapjoy.TapjoyFeaturedAppObject;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyNotifier;
import com.tapjoy.TapjoyReEngagementAd;
import com.tapjoy.TapjoyReEngagementAdNotifier;
import com.tapjoy.TapjoySpendPointsNotifier;
import com.tapjoy.TapjoyVideo;
import com.tapjoy.TapjoyVideoNotifier;
import com.tapjoy.VGStoreItem;
import java.util.ArrayList;
import java.util.Hashtable;

public final class TapjoyConnect
implements TapjoyNotifier,
TapjoyFeaturedAppNotifier,
TapjoySpendPointsNotifier,
TapjoyDisplayAdNotifier,
TapjoyAwardPointsNotifier,
TapjoyVideoNotifier,
TapjoyReEngagementAdNotifier {
    public static final String TAPJOY_CONNECT = "TapjoyConnect";
    private static int bannerX;
    private static int bannerY;
    private static Handler handler;
    private static String handlerClassName;
    private static View tapjoyBannerAd;
    private static TapjoyConnect tapjoyConnectInstance;
    private static TapjoyDisplayAd tapjoyDisplayAd;
    private static TapjoyEvent tapjoyEvent;
    private static TapjoyFeaturedApp tapjoyFeaturedApp;
    private static TJCOffers tapjoyOffers;
    private static TapjoyReEngagementAd tapjoyReEngagementAd;
    private static TapjoyVideo tapjoyVideo;
    private static Hashtable<String, String> tempFlags;
    private boolean getBannerAdResponse;
    private int primaryColor;
    private boolean showWhenReady = false;
    private TapjoyFeaturedAppObject tapjoyFeaturedAppObject;
    private int tapjoyPointTotal = 0;
    private TJCVirtualGoodUtil virtualgoodsUtil = null;

    static {
        tapjoyConnectInstance = null;
        tapjoyOffers = null;
        tapjoyFeaturedApp = null;
        tapjoyDisplayAd = null;
        tapjoyVideo = null;
        tapjoyEvent = null;
        tapjoyReEngagementAd = null;
        handlerClassName = null;
        tempFlags = null;
    }

    private TapjoyConnect(Context context, String string2, String string3, Hashtable<String, String> hashtable) {
        TapjoyConnectCore.requestTapjoyConnect(context, string2, string3, hashtable);
    }

    public static TapjoyConnect getTapjoyConnectInstance() {
        if (tapjoyConnectInstance == null) {
            Log.e((String)TAPJOY_CONNECT, (String)"----------------------------------------");
            Log.e((String)TAPJOY_CONNECT, (String)"ERROR -- call requestTapjoyConnect before any other Tapjoy methods");
            Log.e((String)TAPJOY_CONNECT, (String)"----------------------------------------");
        }
        return tapjoyConnectInstance;
    }

    public static void requestTapjoyConnect(Context context, String string2, String string3) {
        TapjoyConnect.requestTapjoyConnect(context, string2, string3, tempFlags);
    }

    public static void requestTapjoyConnect(Context context, String string2, String string3, Hashtable<String, String> hashtable) {
        TapjoyConnectCore.setSDKType("virtual_goods");
        TapjoyConnectCore.setPlugin("unity");
        tapjoyConnectInstance = new TapjoyConnect(context, string2, string3, hashtable);
        tapjoyOffers = new TJCOffers(context);
        tapjoyFeaturedApp = new TapjoyFeaturedApp(context);
        tapjoyDisplayAd = new TapjoyDisplayAd(context);
        tapjoyVideo = new TapjoyVideo(context);
        tapjoyEvent = new TapjoyEvent(context);
        tapjoyReEngagementAd = new TapjoyReEngagementAd(context);
    }

    public static void setFlagKeyValue(String string2, String string3) {
        if (tempFlags == null) {
            tempFlags = new Hashtable();
        }
        TapjoyLog.i(TAPJOY_CONNECT, "setFlagKeyValue: " + string2 + ", " + string3);
        tempFlags.put((Object)string2, (Object)string3);
    }

    public static void setHandler(Handler handler) {
        TapjoyConnect.handler = handler;
    }

    public static void setHandlerClass(String string2) {
        Log.i((String)TAPJOY_CONNECT, (String)("setHandlerClass: " + string2));
        handlerClassName = string2;
    }

    public void actionComplete(String string2) {
        TapjoyConnectCore.getInstance().actionComplete(string2);
    }

    public void awardTapPoints(int n) {
        tapjoyOffers.awardTapPoints(n, (TapjoyAwardPointsNotifier)this);
    }

    public void cancelShowImmediately() {
        this.showWhenReady = false;
    }

    public void checkForVirtualGoods() {
        this.virtualgoodsUtil = new TJCVirtualGoodUtil(TapjoyConnectCore.getContext(), TapjoyConnectCore.getClientPackage());
        if (!TJCVirtualGoodUtil.virtualGoodsUIOpened) {
            TJCVirtualGoods.setVirtualGoodDownloadListener(new TJCVirtualGoods.TapjoyDownloadListener(){

                @Override
                public void onDownLoad(VGStoreItem vGStoreItem) {
                    Log.i((String)TapjoyConnect.TAPJOY_CONNECT, (String)("downloadListener download: " + vGStoreItem.getName()));
                }
            });
            this.virtualgoodsUtil.checkForVirtualGoods(TapjoyConnectCore.getContext(), TapjoyConnectCore.getURLParams(), TapjoyConnectCore.getClientPackage());
        }
    }

    public boolean didReceiveDisplayAdData() {
        return this.getBannerAdResponse;
    }

    public void enableBannerAdAutoRefresh(boolean bl) {
        tapjoyDisplayAd.enableAutoRefresh(bl);
    }

    public void enablePaidAppWithActionID(String string2) {
        TapjoyConnectCore.getInstance().enablePaidAppWithActionID(string2);
    }

    public void enableVideoCache(boolean bl) {
        tapjoyVideo.enableVideoCache(bl);
    }

    public String getAppID() {
        return TapjoyConnectCore.getAppID();
    }

    @Override
    public void getAwardPointsResponse(String string2, int n) {
        this.tapjoyPointTotal = n;
    }

    @Override
    public void getAwardPointsResponseFailed(String string2) {
    }

    public View getBannerAdView() {
        return tapjoyBannerAd;
    }

    public int getBannerX() {
        return bannerX;
    }

    public int getBannerY() {
        return bannerY;
    }

    public float getCurrencyMultiplier() {
        return TapjoyConnectCore.getInstance().getCurrencyMultiplier();
    }

    public void getDisplayAd() {
        this.getBannerAdResponse = false;
        tapjoyDisplayAd.getDisplayAd(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void getDisplayAdResponse(View view) {
        TapjoyLog.i(TAPJOY_CONNECT, "getDisplayAdResponse");
        this.getBannerAdResponse = true;
        if (view == null) {
            TapjoyLog.i(TAPJOY_CONNECT, "null ad");
        } else {
            TapjoyLog.i(TAPJOY_CONNECT, "ad not null");
        }
        tapjoyBannerAd = view;
        if (this.showWhenReady) {
            this.showWhenReady = false;
            TapjoyGlu.Show();
        }
    }

    @Override
    public void getDisplayAdResponseFailed(String string2) {
        TapjoyLog.i(TAPJOY_CONNECT, "getDisplayAdResponseFailed: " + string2);
    }

    public void getDisplayAdWithCurrencyID(String string2, TapjoyDisplayAdNotifier tapjoyDisplayAdNotifier) {
        this.getBannerAdResponse = false;
        tapjoyDisplayAd.getDisplayAd(string2, (TapjoyDisplayAdNotifier)this);
    }

    public void getFeaturedApp() {
        tapjoyFeaturedApp.getFeaturedApp(this);
    }

    public TapjoyFeaturedAppObject getFeaturedAppObject() {
        return this.tapjoyFeaturedAppObject;
    }

    @Override
    public void getFeaturedAppResponse(TapjoyFeaturedAppObject tapjoyFeaturedAppObject) {
        this.tapjoyFeaturedAppObject = tapjoyFeaturedAppObject;
        this.showFeaturedAppFullScreenAd();
    }

    @Override
    public void getFeaturedAppResponseFailed(String string2) {
    }

    public void getFeaturedAppWithCurrencyID(String string2) {
        tapjoyFeaturedApp.getFeaturedApp(string2, (TapjoyFeaturedAppNotifier)this);
    }

    public TJCVirtualGoods.FocusListener getFocusListener() {
        return TJCVirtualGoods.getVirtualGoodsFocusListener();
    }

    public int getPrimaryColor() {
        return this.primaryColor;
    }

    public VGStoreItem getPurchasedItemAtIndex(int n) {
        if (n < this.getPurchasedItemsCount()) {
            return (VGStoreItem)TJCVirtualGoodsData.getPurchasedItems(TapjoyConnectCore.getContext()).get(n);
        }
        return null;
    }

    public ArrayList<VGStoreItem> getPurchasedItems() {
        return TJCVirtualGoodsData.getPurchasedItems(TapjoyConnectCore.getContext());
    }

    public int getPurchasedItemsCount() {
        if (TJCVirtualGoodsData.getPurchasedItems(TapjoyConnectCore.getContext()) != null) {
            return TJCVirtualGoodsData.getPurchasedItems(TapjoyConnectCore.getContext()).size();
        }
        return 0;
    }

    public void getReEngagementAd() {
        tapjoyReEngagementAd.getReEngagementAd(this);
    }

    @Override
    public void getReEngagementAdResponse() {
    }

    @Override
    public void getReEngagementAdResponseFailed(int n) {
    }

    public void getReEngagementAdWithCurrencyID(String string2) {
        tapjoyReEngagementAd.getReEngagementAdWithCurrencyID(string2, (TapjoyReEngagementAdNotifier)this);
    }

    @Override
    public void getSpendPointsResponse(String string2, int n) {
        this.tapjoyPointTotal = n;
        TapjoyGlu.getSpendPointsResponse(string2, n);
    }

    @Override
    public void getSpendPointsResponseFailed(String string2) {
        TapjoyGlu.getSpendPointsResponseFailed(string2);
    }

    public void getTapPoints() {
        tapjoyOffers.getTapPoints(this);
    }

    public int getTapPointsTotal() {
        TapjoyLog.i(TAPJOY_CONNECT, "getTapPointsTotal: " + this.tapjoyPointTotal);
        return this.tapjoyPointTotal;
    }

    @Override
    public void getUpdatePoints(String string2, int n) {
        this.tapjoyPointTotal = n;
        TapjoyGlu.getUpdatePoints(string2, n);
    }

    @Override
    public void getUpdatePointsFailed(String string2) {
        TapjoyGlu.getUpdatePointsFailed(string2);
    }

    public String getUserID() {
        return TapjoyConnectCore.getUserID();
    }

    public void hideBannerAd() {
        TapjoyLog.i(TAPJOY_CONNECT, "removeBannerAd");
        handler.handleMessage(Message.obtain());
    }

    public void initVideoAd() {
        tapjoyVideo.initVideoAd(this);
    }

    public void sendIAPEvent(String string2, float f2, int n, String string3) {
        tapjoyEvent.sendIAPEvent(string2, f2, n, string3);
    }

    public void sendShutDownEvent() {
        tapjoyEvent.sendShutDownEvent();
    }

    public void setBannerAdPosition(int n, int n2) {
        bannerX = n;
        bannerY = n2;
        handler.handleMessage(null);
    }

    public void setBannerAdSize(String string2) {
        tapjoyDisplayAd.setBannerAdSize(string2);
    }

    public void setCurrencyMultiplier(float f2) {
        TapjoyConnectCore.getInstance().setCurrencyMultiplier(f2);
    }

    public void setEarnedPointsNotifier() {
        tapjoyOffers.setEarnedPointsNotifier(new TapjoyEarnedPointsNotifier(){

            @Override
            public void earnedTapPoints(int n) {
            }
        });
    }

    public void setFeaturedAppDisplayCount(int n) {
        tapjoyFeaturedApp.setDisplayCount(n);
    }

    public void setFocusListener(TJCVirtualGoods.FocusListener focusListener) {
        TJCVirtualGoods.setVirtualGoodsFocusListener(focusListener);
    }

    public void setUserDefinedColor(int n) {
        this.primaryColor = n;
        SharedPreferences.Editor editor = TapjoyConnectCore.getContext().getSharedPreferences("tjcPrefrences", 0).edit();
        editor.putInt("tapjoyPrimaryColor", this.primaryColor);
        editor.commit();
    }

    public void setUserID(String string2) {
        TapjoyConnectCore.setUserID(string2);
    }

    public void setVideoCacheCount(int n) {
        tapjoyVideo.setVideoCacheCount(n);
    }

    public void showBannerAd() {
        TapjoyLog.i(TAPJOY_CONNECT, "showBannerAd");
        handler.handleMessage(null);
    }

    public void showFeaturedAppFullScreenAd() {
        tapjoyFeaturedApp.showFeaturedAppFullScreenAd();
    }

    public void showImmediately() {
        this.showWhenReady = true;
    }

    public void showOffers() {
        tapjoyOffers.showOffers();
    }

    public void showOffersWithCurrencyID(String string2, boolean bl) {
        tapjoyOffers.showOffersWithCurrencyID(string2, bl);
    }

    public void showReEngagementFullScreenAd() {
        tapjoyReEngagementAd.showReEngagementFullScreenAd();
    }

    public void showVirtualGoods() {
        TJCVirtualGoods.setVirtualGoodDownloadListener(new TJCVirtualGoods.TapjoyDownloadListener(){

            @Override
            public void onDownLoad(VGStoreItem vGStoreItem) {
                Log.i((String)TapjoyConnect.TAPJOY_CONNECT, (String)("downloadListener download: " + vGStoreItem.getName()));
            }
        });
        TJCVirtualGoods.doNotify = false;
        TJCVirtualGoodUtil.virtualGoodsUIOpened = true;
        Intent intent = new Intent(TapjoyConnectCore.getContext(), TJCVirtualGoods.class);
        intent.setFlags(268435456);
        intent.putExtra("URL_PARAMS", TapjoyConnectCore.getURLParams());
        TapjoyConnectCore.getContext().startActivity(intent);
    }

    public void spendTapPoints(int n) {
        tapjoyOffers.spendTapPoints(n, (TapjoySpendPointsNotifier)this);
    }

    @Override
    public void videoComplete() {
        Log.i((String)TAPJOY_CONNECT, (String)"VIDEO COMPLETE");
    }

    @Override
    public void videoError(int n) {
        Log.i((String)TAPJOY_CONNECT, (String)("VIDEO ERROR: " + n));
    }

    @Override
    public void videoReady() {
        Log.i((String)TAPJOY_CONNECT, (String)"VIDEO READY");
    }

}


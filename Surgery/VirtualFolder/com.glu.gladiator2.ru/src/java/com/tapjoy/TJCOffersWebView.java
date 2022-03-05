/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.ProgressBar
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Hashtable
 */
package com.tapjoy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.tapjoy.TapjoyConnectCore;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyVideo;
import java.util.Hashtable;

public class TJCOffersWebView
extends Activity {
    final String TAPJOY_OFFERS = "Offers";
    private String clientPackage = "";
    private Dialog dialog = null;
    private String offersURL = null;
    private ProgressBar progressBar;
    private boolean resumeCalled = false;
    private boolean skipOfferWall = false;
    private String urlParams = "";
    private String userID = "";
    private WebView webView = null;

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            if (bundle2.getString("DISPLAY_AD_URL") != null) {
                this.skipOfferWall = true;
                this.offersURL = bundle2.getString("DISPLAY_AD_URL");
            } else {
                this.skipOfferWall = false;
                this.urlParams = bundle2.getString("URL_PARAMS");
                this.userID = bundle2.getString("USER_ID");
                this.urlParams = this.urlParams + "&publisher_user_id=" + this.userID;
                if (TapjoyConnectCore.getVideoParams().length() > 0) {
                    this.urlParams = this.urlParams + "&" + TapjoyConnectCore.getVideoParams();
                }
                TapjoyLog.i("Offers", "urlParams: [" + this.urlParams + "]");
                this.offersURL = "https://ws.tapjoyads.com/get_offers/webpage?" + this.urlParams;
            }
        } else {
            TapjoyLog.e("Offers", "Tapjoy offers meta data initialization fail.");
        }
        this.offersURL = this.offersURL.replaceAll(" ", "%20");
        this.clientPackage = TapjoyConnectCore.getClientPackage();
        TapjoyLog.i("Offers", "clientPackage: [" + this.clientPackage + "]");
        super.onCreate(bundle);
        this.requestWindowFeature(1);
        RelativeLayout relativeLayout = new RelativeLayout((Context)this);
        this.webView = new WebView((Context)this);
        this.webView.setWebViewClient((WebViewClient)new TapjoyWebViewClient((TJCOffersWebView)this, null));
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.progressBar = new ProgressBar((Context)this, null, 16842874);
        this.progressBar.setVisibility(0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        this.progressBar.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        relativeLayout.addView((View)this.webView, -1, -1);
        relativeLayout.addView((View)this.progressBar);
        this.setContentView((View)relativeLayout);
        this.webView.loadUrl(this.offersURL);
        TapjoyLog.i("Offers", "Opening URL = [" + this.offersURL + "]");
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.webView != null) {
            this.webView.clearCache(true);
            this.webView.destroyDrawingCache();
            this.webView.destroy();
        }
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 4 && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    protected void onResume() {
        super.onResume();
        if (this.offersURL != null && this.webView != null) {
            this.webView.loadUrl(this.offersURL);
        }
        if (this.resumeCalled && TapjoyConnectCore.getInstance() != null) {
            TapjoyLog.i("Offers", "call connect");
            TapjoyConnectCore.getInstance().callConnect();
        }
        this.resumeCalled = true;
    }

    private class TapjoyWebViewClient
    extends WebViewClient {
        final /* synthetic */ TJCOffersWebView this$0;

        private TapjoyWebViewClient(TJCOffersWebView tJCOffersWebView) {
            this.this$0 = tJCOffersWebView;
        }

        /* synthetic */ TapjoyWebViewClient(TJCOffersWebView tJCOffersWebView, com.tapjoy.TJCOffersWebView$1 var2_2) {
            super(tJCOffersWebView);
        }

        public void onPageFinished(WebView webView, String string2) {
            this.this$0.progressBar.setVisibility(8);
        }

        public void onPageStarted(WebView webView, String string2, Bitmap bitmap) {
            this.this$0.progressBar.setVisibility(0);
            this.this$0.progressBar.bringToFront();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean shouldOverrideUrlLoading(WebView webView, String string2) {
            TapjoyLog.i("Offers", "URL = [" + string2 + "]");
            if (!string2.startsWith("tjvideo://")) {
                if (string2.contains((CharSequence)"ws.tapjoyads.com")) {
                    TapjoyLog.i("Offers", "Open redirecting URL = [" + string2 + "]");
                    webView.loadUrl(string2);
                    return true;
                }
                TapjoyLog.i("Offers", "Opening URL in new browser = [" + string2 + "]");
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)string2));
                this.this$0.startActivity(intent);
                TapjoyLog.i("Offers", "skipOfferWall: " + this.this$0.skipOfferWall);
                if (!this.this$0.skipOfferWall) return true;
                {
                    this.this$0.finish();
                    return true;
                }
            }
            Hashtable hashtable = new Hashtable();
            int n = string2.indexOf("://") + "://".length();
            String string3 = "";
            String string4 = "";
            boolean bl = false;
            for (int i2 = n; i2 < string2.length() && i2 != -1; ++i2) {
                char c2 = string2.charAt(i2);
                if (!bl) {
                    if (c2 == '=') {
                        string4 = Uri.decode((String)string3);
                        string3 = "";
                        bl = true;
                        continue;
                    }
                    string3 = string3 + c2;
                    continue;
                }
                if (!bl) continue;
                if (c2 == '&') {
                    String string5 = Uri.decode((String)string3);
                    string3 = "";
                    TapjoyLog.i("Offers", "k:v: " + string4 + ", " + string5);
                    hashtable.put((Object)string4, (Object)string5);
                    bl = false;
                    continue;
                }
                string3 = string3 + c2;
            }
            if (bl && string3.length() > 0) {
                String string6 = Uri.decode((String)string3);
                TapjoyLog.i("Offers", "k:v: " + string4 + ", " + string6);
                hashtable.put((Object)string4, (Object)string6);
            }
            String string7 = (String)hashtable.get((Object)"video_id");
            String string8 = (String)hashtable.get((Object)"amount");
            String string9 = (String)hashtable.get((Object)"currency_name");
            String string10 = (String)hashtable.get((Object)"click_url");
            String string11 = (String)hashtable.get((Object)"video_complete_url");
            String string12 = (String)hashtable.get((Object)"video_url");
            TapjoyLog.i("Offers", "videoID: " + string7);
            TapjoyLog.i("Offers", "currencyAmount: " + string8);
            TapjoyLog.i("Offers", "currencyName: " + string9);
            TapjoyLog.i("Offers", "clickURL: " + string10);
            TapjoyLog.i("Offers", "webviewURL: " + string11);
            TapjoyLog.i("Offers", "videoURL: " + string12);
            if (TapjoyVideo.getInstance().startVideo(string7, string9, string8, string10, string11, string12)) {
                TapjoyLog.i("Offers", "VIDEO");
                return true;
            }
            TapjoyLog.e("Offers", "Unable to play video: " + string7);
            this.this$0.dialog = (Dialog)new AlertDialog.Builder((Context)this.this$0).setTitle((CharSequence)"").setMessage((CharSequence)"Unable to play video.").setPositiveButton((CharSequence)"OK", new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    dialogInterface.dismiss();
                }
            }).create();
            try {
                this.this$0.dialog.show();
                return true;
            }
            catch (Exception exception) {
                TapjoyLog.e("Offers", "e: " + exception.toString());
                return true;
            }
        }

    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.view.View
 *  android.webkit.WebView
 *  java.lang.Boolean
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.util.concurrent.Callable
 */
package com.wildtangent.brandboost;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import com.wildtangent.brandboost.BrandBoostActivity;
import com.wildtangent.brandboost.a.a;
import com.wildtangent.brandboost.util.b;
import com.wildtangent.brandboost.util.e;
import java.util.concurrent.Callable;

public class JavascriptInterface {
    public static final String KEY_CALLBACK = "KEY_CALLBACK";
    public static final String KEY_MSG_TXT = "KEY_MSG_TXT";
    public static final String KEY_NO_BUTTON = "KEY_NO_BUTTON";
    public static final String KEY_OK_BUTTON = "KEY_OK_BUTTON";
    public static final String KEY_TITLE_TEXT = "KEY_TITLE_TEXT";
    public static final String KEY_URL = "KEY_URL";
    public static final String KEY_YES_BUTTON = "KEY_YES_BUTTON";
    public static final int WHAT_PAGE_LOAD_COMPLETE = 1;
    public static final int WHAT_SHOW_ALERT = 2;
    public static final int WHAT_SHOW_CONFIRM = 3;
    public static final int WHAT_SPAWN_EXTERNAL_BROWSER = 4;
    private static String a = "BB_JSI";
    private BrandBoostActivity b;
    private WebView c;
    private a d;
    private e e;
    private Handler f;
    private int g;
    private int h;

    public JavascriptInterface(BrandBoostActivity brandBoostActivity, WebView webView, a a2, Handler handler) {
        this.b = brandBoostActivity;
        this.c = webView;
        this.e = new e((View)this.c);
        this.d = a2;
        this.f = handler;
    }

    public String ConsoleVersion() {
        return "1.0.0.74";
    }

    public int DisplayHeight() {
        return this.h;
    }

    public int DisplayWidth() {
        return this.g;
    }

    public void FinishWebView(String string) {
        b.a(a, "finishWebView: " + string);
        this.b.a(string);
    }

    public String GetDistributionPartner() {
        return BrandBoostActivity.a;
    }

    public int GetVideoDuration() {
        Integer n2 = this.e.a(new Callable<Integer>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public Integer a() {
                int n2;
                if (JavascriptInterface.this.d != null) {
                    n2 = JavascriptInterface.this.d.c();
                    do {
                        return n2;
                        break;
                    } while (true);
                }
                n2 = -1;
                return n2;
            }
        });
        if (n2 != null) {
            return n2;
        }
        return -1;
    }

    public int GetVideoHeight() {
        Integer n2 = this.e.a(new Callable<Integer>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public Integer a() {
                int n2;
                if (JavascriptInterface.this.d != null) {
                    n2 = JavascriptInterface.this.d.e();
                    do {
                        return n2;
                        break;
                    } while (true);
                }
                n2 = -1;
                return n2;
            }
        });
        if (n2 != null) {
            return n2;
        }
        return -1;
    }

    public int GetVideoWidth() {
        Integer n2 = this.e.a(new Callable<Integer>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public Integer a() {
                int n2;
                if (JavascriptInterface.this.d != null) {
                    n2 = JavascriptInterface.this.d.d();
                    do {
                        return n2;
                        break;
                    } while (true);
                }
                n2 = -1;
                return n2;
            }
        });
        if (n2 != null) {
            return n2;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean IsMobileNetworkConnection() {
        Boolean bl = this.e.a(this.b, new Callable<Boolean>(){

            public Boolean a() {
                for (NetworkInfo networkInfo : ((ConnectivityManager)JavascriptInterface.this.b.getSystemService("connectivity")).getAllNetworkInfo()) {
                    if (networkInfo.getType() == 1 || !networkInfo.isConnected()) continue;
                    return true;
                }
                return false;
            }
        });
        boolean bl2 = bl != null ? bl : false;
        b.a(a, "IsMobileNetworkConnection: " + bl2);
        return bl2;
    }

    public void LogD(String string) {
        b.a(string);
    }

    public void LogE(String string) {
        b.b(string);
    }

    public void LogI(String string) {
        b.c(string);
    }

    public void LogW(String string) {
        b.d(string);
    }

    public void PageLoadComplete() {
        if (this.f != null) {
            this.f.obtainMessage(1).sendToTarget();
            return;
        }
        b.b(a, "null handler in PageLoadComplete");
    }

    public void RedeemVexCode(String string, String string2) {
        b.a(a, "Sending vex code to BB");
        this.b.b(string2);
    }

    public void ShowAlert(String string, String string2, String string3, String string4) {
        b.a(a, "ShowAlert: " + string2 + ", " + string3 + ", " + string4);
        Message message = this.f.obtainMessage(2);
        Bundle bundle = message.getData();
        bundle.putString(KEY_TITLE_TEXT, string);
        bundle.putString(KEY_MSG_TXT, string2);
        bundle.putString(KEY_OK_BUTTON, string3);
        bundle.putString(KEY_CALLBACK, string4);
        message.sendToTarget();
    }

    public void ShowConfirm(String string, String string2, String string3, String string4, String string5) {
        b.a(a, "ShowConfirm " + string2 + ", " + string3 + ", " + string4 + ", " + string5);
        Message message = this.f.obtainMessage(3);
        Bundle bundle = message.getData();
        bundle.putString(KEY_TITLE_TEXT, string);
        bundle.putString(KEY_MSG_TXT, string2);
        bundle.putString(KEY_YES_BUTTON, string3);
        bundle.putString(KEY_NO_BUTTON, string4);
        bundle.putString(KEY_CALLBACK, string5);
        message.sendToTarget();
    }

    public void SpawnExternalBrowser(String string) {
        b.a(a, "SpawnExternalBrowser: " + string);
        Message message = this.f.obtainMessage(4);
        message.getData().putString(KEY_URL, string);
        message.sendToTarget();
    }

    public void SubscribeEvent(String string, String string2) {
    }

    public void VideoSetup(final String string, final String string2, final String string3) {
        this.c.post(new Runnable(){

            public void run() {
                if (JavascriptInterface.this.d != null) {
                    JavascriptInterface.this.d.a(string, string2, string3);
                    return;
                }
                b.b(a, "Video overlay not set up!");
            }
        });
    }

    public void VideoStart(final int n2, final int n3, final int n4, final int n5) {
        this.c.post(new Runnable(){

            public void run() {
                if (JavascriptInterface.this.d != null) {
                    JavascriptInterface.this.d.a(n2, n3, n4, n5);
                    return;
                }
                b.b(a, "Video overlay not set up!");
            }
        });
    }

    public void VideoStop() {
        this.c.post(new Runnable(){

            public void run() {
                if (JavascriptInterface.this.d != null) {
                    JavascriptInterface.this.d.a();
                    return;
                }
                b.b(a, "Video overlay not set up!");
            }
        });
    }

    public void brandBoostStateChanged(String string) {
        try {
            int n2 = Integer.parseInt((String)string);
            this.b.a(n2);
            return;
        }
        catch (NumberFormatException numberFormatException) {
            b.d(a, "Couldn't parse BrandBoost state.");
            return;
        }
    }

    public void setVideoOverlay(a a2) {
        this.d = a2;
    }

    public void setWebParams(String string) {
    }

    public void setWidthAndHeight(int n2, int n3) {
        this.g = n2;
        this.h = n3;
    }

}


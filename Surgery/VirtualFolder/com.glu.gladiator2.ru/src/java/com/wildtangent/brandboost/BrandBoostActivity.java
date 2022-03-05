/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ProgressBar
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  com.wildtangent.brandboost.BrandBoostCallbacks
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.wildtangent.brandboost;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.wildtangent.brandboost.BrandBoostCallbacks;
import com.wildtangent.brandboost.c;
import com.wildtangent.brandboost.h;
import com.wildtangent.brandboost.util.b;
import org.json.JSONException;
import org.json.JSONObject;

public class BrandBoostActivity
extends Activity {
    static String a;
    private static String b;
    private c c = null;
    private RelativeLayout d;
    private Messenger e = null;
    private boolean f = false;
    private boolean g = false;
    private Handler h = new h(this);

    static {
        b = "com.wildtangent.brandboost__" + BrandBoostActivity.class.getSimpleName();
        a = "";
    }

    private void a(int n2, Bundle bundle) {
        b.a(b, "Sending message to BB, what: " + n2);
        Messenger messenger = BrandBoostActivity.super.c();
        if (messenger != null) {
            Message message = Message.obtain(null, (int)n2);
            message.setData(bundle);
            try {
                messenger.send(message);
                return;
            }
            catch (RemoteException remoteException) {
                b.a(b, "Failed to send message to BB: " + n2, remoteException);
                return;
            }
        }
        b.b(b, "No BB messenger set! Unable to send message: " + n2);
    }

    public static void a(Context context, Uri uri, Messenger messenger) {
        Intent intent = new Intent(context, BrandBoostActivity.class);
        intent.setData(uri);
        intent.putExtra("BUNDLE_MESSENGER", (Parcelable)messenger);
        try {
            context.startActivity(intent);
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            b.b(b, BrandBoostActivity.class.getName() + " not found. Have you declared this activity in your AndroidManifest.xml?");
            return;
        }
    }

    private void a(Message message) {
        b.a(b, "handleWhatError");
        if (BrandBoostActivity.super.c(message)) {
            if (this.c != null) {
                String string = message.getData().getString("BUNDLE_ITEM_KEY");
                if (string == null) {
                    string = "";
                    b.d(b, "received null itemKey for error to JS. Using empty string.");
                }
                this.c.a(string, false);
                return;
            }
            b.d(b, "null webview. error not passed to javascript.");
            return;
        }
        b.d(b, "invalid error msg");
    }

    private void a(BrandBoostCallbacks.ClosedReason closedReason) {
        if (!this.g) {
            Bundle bundle = new Bundle();
            bundle.putInt("BUNDLE_CLOSE_REASON", closedReason.ordinal());
            bundle.putParcelable("BUNDLE_MESSENGER", (Parcelable)this.e);
            BrandBoostActivity.super.a(2, bundle);
            this.g = true;
        }
    }

    static /* synthetic */ boolean a(BrandBoostActivity brandBoostActivity, Message message) {
        return brandBoostActivity.c(message);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String b(Message message) {
        if (message == null) return null;
        Bundle bundle = message.getData();
        String string = null;
        if (bundle == null) return string;
        return bundle.getString("BUNDLE_ITEM_KEY");
    }

    static /* synthetic */ void b(BrandBoostActivity brandBoostActivity, Message message) {
        brandBoostActivity.a(message);
    }

    private Messenger c() {
        return (Messenger)this.getIntent().getParcelableExtra("BUNDLE_MESSENGER");
    }

    static /* synthetic */ String c(BrandBoostActivity brandBoostActivity, Message message) {
        return brandBoostActivity.b(message);
    }

    private boolean c(Message message) {
        return message != null && message.getData() != null;
    }

    public void a() {
        this.a(BrandBoostCallbacks.ClosedReason.Error);
        this.finish();
    }

    public void a(int n2) {
        this.f = true;
        switch (n2) {
            default: {
                return;
            }
            case 1: {
                b.a(b, "BB state: Start page.");
                return;
            }
            case 2: {
                b.a(b, "BB state: Interstitial video.");
                return;
            }
            case 3: {
                b.a(b, "BB state: Interstitial rich media.");
                return;
            }
            case 4: {
                b.a(b, "BB state: End page.");
                this.f = false;
                return;
            }
            case 5: {
                b.a(b, "BB state: Replay video.");
                return;
            }
            case 6: {
                b.a(b, "BB state: Replay rich media.");
                return;
            }
            case 7: 
        }
        b.a(b, "BB state: Learn more.");
        this.f = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(String string) {
        b.a(b, "finishWebView: " + string);
        if (string != null) {
            try {
                String string2 = new JSONObject(string).getString("errorCode");
                b.a(b, "finishWebView, code: " + string2);
                if (string2.equals((Object)"0")) {
                    BrandBoostActivity.super.a(BrandBoostCallbacks.ClosedReason.Success);
                } else {
                    if (string2.equals((Object)"1")) {
                        BrandBoostActivity.super.a(BrandBoostCallbacks.ClosedReason.Abandoned);
                    }
                    BrandBoostActivity.super.a(BrandBoostCallbacks.ClosedReason.Error);
                }
            }
            catch (JSONException jSONException) {
                b.b(b, jSONException);
                BrandBoostActivity.super.a(BrandBoostCallbacks.ClosedReason.Error);
            }
        } else {
            b.d(b, "null errorCode to finishWebView");
            BrandBoostActivity.super.a(BrandBoostCallbacks.ClosedReason.Error);
        }
        this.finish();
    }

    public void b(String string) {
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_VEXCODE", string);
        bundle.putParcelable("BUNDLE_MESSENGER", (Parcelable)this.e);
        BrandBoostActivity.super.a(0, bundle);
    }

    public void onBackPressed() {
        b.a(b, "Back button pressed. Abandoning BrandBoost.");
        this.c.a();
        this.a(BrandBoostCallbacks.ClosedReason.Abandoned);
        this.finish();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(1);
        this.setRequestedOrientation(0);
        this.d = new RelativeLayout((Context)this);
        this.d.setGravity(17);
        ProgressBar progressBar = new ProgressBar((Context)this);
        progressBar.setIndeterminate(true);
        this.d.addView((View)progressBar);
        this.setContentView((View)this.d, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
        this.e = new Messenger(this.h);
    }

    public void onPause() {
        super.onPause();
        if (this.f) {
            if (!this.g) {
                b.a(b, "Paused reached in a cancelable state. Abandoning BrandBoost.");
                this.a(BrandBoostCallbacks.ClosedReason.Abandoned);
            }
            this.c.a();
            this.finish();
        }
    }

    public void onStart() {
        super.onStart();
        if (this.c == null) {
            this.c = new c(this, new Runnable(){

                public void run() {
                    int n2 = BrandBoostActivity.this.d.getWidth();
                    int n3 = BrandBoostActivity.this.d.getHeight();
                    b.a(b, "Activity dimensions: " + n2 + "x" + n3);
                    BrandBoostActivity.this.c.a(n2, n3);
                    BrandBoostActivity.this.setContentView((View)BrandBoostActivity.this.c);
                }
            });
        }
    }

}


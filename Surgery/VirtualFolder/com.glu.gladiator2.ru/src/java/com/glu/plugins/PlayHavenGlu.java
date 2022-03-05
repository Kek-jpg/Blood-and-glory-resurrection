/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Thread
 */
package com.glu.plugins;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.glu.plugins.AAds;
import com.playhaven.src.common.PHConfig;
import com.playhaven.src.publishersdk.content.PHPublisherContentRequest;
import com.playhaven.src.publishersdk.content.PHPurchase;
import com.playhaven.src.publishersdk.open.PHPublisherOpenRequest;
import com.playhaven.src.publishersdk.purchases.PHPublisherIAPTrackingRequest;
import com.unity3d.player.UnityPlayer;

public class PlayHavenGlu {
    private static final String SHAREDPREF_KEY_VGP_CALLBACK = "phvgp_product";
    private static final String SHAREDPREF_KEY_VGP_INTENT = "phvgp_intent";
    private static final String SHAREDPREF_KEY_VGP_NAME = "phvgp_name";
    private static final String SHAREDPREF_KEY_VGP_PRODUCT = "phvgp_product";
    private static final String SHAREDPREF_KEY_VGP_QUANTITY = "phvgp_product";
    private static final String SHAREDPREF_KEY_VGP_RECEIPT = "phvgp_product";
    private static PHPurchase activePurchase;
    private static boolean didInit;
    private static String unityGameObject;

    static {
        didInit = false;
    }

    public static void Init(String string2, final String string3, final String string4) {
        AAds.Log("PlayHaven.Init( " + string2 + ", " + string3 + ", " + string4 + " )");
        if (string3 == null || string3.equals((Object)"") || string3 == null || string3.equals((Object)"")) {
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("**********                WARNING                **********");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("PlayHaven is disabled, because no keys were passed in.");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            return;
        }
        AAds.Log("PlayHaven Version: " + PHConfig.sdk_version);
        unityGameObject = string2;
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                PHConfig.token = string3;
                PHConfig.secret = string4;
                didInit = true;
                new PHPublisherOpenRequest((Context)UnityPlayer.currentActivity).send();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void ReportResolution(String string2) {
        SharedPreferences sharedPreferences;
        PHPurchase.Resolution resolution;
        block10 : {
            block9 : {
                AAds.Log("PlayHaven.ReportResolution( " + string2 + " )");
                if (!didInit) break block9;
                sharedPreferences = UnityPlayer.currentActivity.getSharedPreferences("aads", 0);
                if (activePurchase != null || sharedPreferences.contains("phvgp_product")) break block10;
            }
            return;
        }
        if (activePurchase == null) {
            activePurchase = new PHPurchase();
            PlayHavenGlu.activePurchase.product = sharedPreferences.getString("phvgp_product", null);
            PlayHavenGlu.activePurchase.contentview_intent = sharedPreferences.getString(SHAREDPREF_KEY_VGP_INTENT, null);
            PlayHavenGlu.activePurchase.name = sharedPreferences.getString(SHAREDPREF_KEY_VGP_NAME, null);
            PlayHavenGlu.activePurchase.quantity = sharedPreferences.getInt("phvgp_product", 1);
            PlayHavenGlu.activePurchase.receipt = sharedPreferences.getString("phvgp_product", null);
            PlayHavenGlu.activePurchase.callback = sharedPreferences.getString("phvgp_product", null);
        }
        if (string2.equals((Object)"buy")) {
            resolution = PHPurchase.Resolution.Buy;
        } else if (string2.equals((Object)"cancel")) {
            resolution = PHPurchase.Resolution.Cancel;
        } else {
            if (!string2.equals((Object)"error")) {
                AAds.Log("Invalid Resolution: " + string2 + "\nUse: buy, cancel, or error");
                return;
            }
            resolution = PHPurchase.Resolution.Error;
        }
        activePurchase.reportResolution(resolution, UnityPlayer.currentActivity);
        new PHPublisherIAPTrackingRequest((Context)UnityPlayer.currentActivity, activePurchase).send();
        activePurchase = null;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("phvgp_product");
        editor.remove(SHAREDPREF_KEY_VGP_INTENT);
        editor.remove(SHAREDPREF_KEY_VGP_NAME);
        editor.remove("phvgp_product");
        editor.remove("phvgp_product");
        editor.remove("phvgp_product");
        editor.commit();
    }

    public static void Show(final String string2) {
        AAds.Log("PlayHaven.Show( " + string2 + " )");
        if (!didInit) {
            return;
        }
        if (PHPublisherContentRequest.didDismissContentWithin(10000L)) {
            AAds.Log("Just dismissed a placement, rejecting new request");
            return;
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void run() {
                try {
                    new Thread(){

                        /*
                         * Enabled aggressive block sorting
                         * Enabled unnecessary exception pruning
                         * Enabled aggressive exception aggregation
                         */
                        public void run() {
                            try {
                                PHPublisherContentRequest pHPublisherContentRequest = new PHPublisherContentRequest(UnityPlayer.currentActivity, string2);
                                pHPublisherContentRequest.setOverlayImmediately(true);
                                pHPublisherContentRequest.setOnPurchaseListener(new PHPublisherContentRequest.PurchaseDelegate(){

                                    @Override
                                    public void shouldMakePurchase(PHPublisherContentRequest pHPublisherContentRequest, PHPurchase pHPurchase) {
                                        SharedPreferences.Editor editor = UnityPlayer.currentActivity.getSharedPreferences("aads", 0).edit();
                                        editor.putString("phvgp_product", pHPurchase.product);
                                        editor.putString(PlayHavenGlu.SHAREDPREF_KEY_VGP_INTENT, pHPurchase.contentview_intent);
                                        editor.putString(PlayHavenGlu.SHAREDPREF_KEY_VGP_NAME, pHPurchase.name);
                                        editor.putInt("phvgp_product", pHPurchase.quantity);
                                        editor.putString("phvgp_product", pHPurchase.receipt);
                                        editor.putString("phvgp_product", pHPurchase.callback);
                                        editor.commit();
                                        activePurchase = pHPurchase;
                                        UnityPlayer.UnitySendMessage(unityGameObject, "onPlayHavenShouldMakePurchase", pHPurchase.product);
                                    }
                                });
                                pHPublisherContentRequest.send();
                                return;
                            }
                            catch (Exception exception) {
                                if (!AAds.DEBUG) return;
                                exception.printStackTrace();
                                return;
                            }
                        }

                    }.start();
                    return;
                }
                catch (Exception exception) {
                    if (!AAds.DEBUG) return;
                    exception.printStackTrace();
                    return;
                }
            }

        });
    }

}


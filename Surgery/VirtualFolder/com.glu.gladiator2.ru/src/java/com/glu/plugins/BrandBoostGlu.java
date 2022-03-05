/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ContentResolver
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.glu.plugins;

import android.app.Activity;
import android.content.ContentResolver;
import android.provider.Settings;
import com.glu.plugins.AAds;
import com.unity3d.player.UnityPlayer;
import com.wildtangent.brandboost.BrandBoost;
import com.wildtangent.brandboost.BrandBoostAPI;
import com.wildtangent.brandboost.BrandBoostCallbacks;
import com.wildtangent.brandboost.GameSpecification;

public class BrandBoostGlu {
    private static BrandBoost bb = null;
    private static String unityGameObject;

    public static void Hide() {
        AAds.Log("BrandBoost.Hide()");
        if (bb != null) {
            bb.setAutoHover(false);
        }
    }

    public static void Init(String string2, final String string3, final String string4, final String string5) {
        AAds.Log("BrandBoost.Init( " + string2 + ", " + string3 + ", " + string4 + ", " + string5 + " )");
        if (string3 == null || string3.equals((Object)"") || string4 == null || string4.equals((Object)"") || string5 == null || string5.equals((Object)"")) {
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("**********                WARNING                **********");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("BrandBoost is disabled, because no keys were passed in.");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            AAds.LogError("***********************************************************");
            return;
        }
        unityGameObject = string2;
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                String string2 = Settings.Secure.getString((ContentResolver)UnityPlayer.currentActivity.getContentResolver(), (String)"android_id");
                GameSpecification gameSpecification = new GameSpecification(string3, string4, string5);
                bb = new BrandBoost(UnityPlayer.currentActivity, gameSpecification, string2, false, new BrandBoostCallbacks(){

                    @Override
                    public boolean onBrandBoostCampaignReady(String string2) {
                        AAds.Log("onBrandBoostCampaignReady( " + string2 + " )");
                        return true;
                    }

                    @Override
                    public void onBrandBoostClosed(BrandBoostCallbacks.ClosedReason closedReason) {
                        AAds.Log("onBrandBoostClosed( " + (Object)((Object)closedReason) + " )");
                    }

                    @Override
                    public void onBrandBoostHoverTapped() {
                        AAds.Log("onBrandBoostHoverTapped()");
                    }

                    @Override
                    public void onBrandBoostItemReady(String string2) {
                        AAds.Log("onBrandBoostItemReady( " + string2 + " )");
                        if (UnityPlayer.currentActivity != null) {
                            UnityPlayer.UnitySendMessage(unityGameObject, "onBrandBoostItemReady", string2);
                        }
                        bb.itemGranted(string2);
                    }
                });
            }

        });
    }

    public static boolean IsAvailable() {
        AAds.Log("BrandBoost.IsAvailable()");
        return bb != null && bb.retrieveItemKey() != null;
    }

    public static void Launch() {
        String string2;
        AAds.Log("BrandBoost.Launch()");
        if (bb != null && (string2 = bb.retrieveItemKey()) != null) {
            AAds.Log("Promotion available for: " + string2);
            bb.launch();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void Show(int n) {
        BrandBoostAPI.Position position;
        AAds.Log("BrandBoost.Show( " + n + " )");
        if (bb == null) {
            return;
        }
        switch (n) {
            default: {
                position = BrandBoostAPI.Position.Center;
                break;
            }
            case 51: {
                position = BrandBoostAPI.Position.Northwest;
                break;
            }
            case 49: {
                position = BrandBoostAPI.Position.North;
                break;
            }
            case 53: {
                position = BrandBoostAPI.Position.Northeast;
                break;
            }
            case 21: {
                position = BrandBoostAPI.Position.East;
                break;
            }
            case 85: {
                position = BrandBoostAPI.Position.Southeast;
                break;
            }
            case 81: {
                position = BrandBoostAPI.Position.South;
                break;
            }
            case 83: {
                position = BrandBoostAPI.Position.Southwest;
                break;
            }
            case 19: {
                position = BrandBoostAPI.Position.West;
            }
        }
        bb.setHoverPosition(position);
        bb.setAutoHover(true);
    }

}


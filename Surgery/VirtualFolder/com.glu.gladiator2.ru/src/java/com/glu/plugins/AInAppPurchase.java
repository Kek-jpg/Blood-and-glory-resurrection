/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Environment
 *  android.util.Log
 *  java.io.File
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.glu.plugins;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import com.glu.plugins.amazon.AmazonIAP;
import com.glu.plugins.google.GoogleIAP;
import com.unity3d.player.UnityPlayer;
import java.io.File;

public class AInAppPurchase {
    public static boolean DEBUG = false;
    private static final String SHAREDPREF_KEY_DEBUG = "DEBUG";
    private static final String SHAREDPREF_KEY_PENDING = "PENDING";
    private static final String SHAREDPREF_NAME = "aiap";
    private static final String VERSION = "1.1.1";
    private static BillingType billing;
    private static IAP iap;
    private static SharedPreferences sprefs;
    private static String unityGameObject;

    static {
        DEBUG = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void ConfirmPurchase(String string2) {
        AInAppPurchase.Log("ConfirmPurchase( " + string2 + " )");
        String string3 = sprefs.getString(SHAREDPREF_KEY_PENDING, null);
        if (string3 != null) {
            AInAppPurchase.Log("ConfirmPurchase() Pending: " + string3);
            String[] arrstring = string3.split(";");
            String string4 = "";
            for (int i2 = 0; i2 < arrstring.length; ++i2) {
                AInAppPurchase.Log("ConfirmPurchase() Transaction[" + i2 + "]: " + arrstring[i2]);
                if (arrstring[i2].startsWith(string2 + "|")) {
                    AInAppPurchase.Log("ConfirmPurchase() Removing Pending Transaction");
                    continue;
                }
                string4 = arrstring[i2] + ";";
            }
            SharedPreferences.Editor editor = sprefs.edit();
            if (!string4.equals((Object)"")) {
                editor.putString(SHAREDPREF_KEY_PENDING, string4);
            } else {
                editor.remove(SHAREDPREF_KEY_PENDING);
            }
            editor.commit();
        }
    }

    public static void Destroy() {
        AInAppPurchase.Log("Destroy()");
        if (iap != null) {
            iap.Destroy();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void Init(String string2, String string3, boolean bl) {
        sprefs = UnityPlayer.currentActivity.getSharedPreferences(SHAREDPREF_NAME, 0);
        if (bl || new File(Environment.getExternalStorageDirectory().toString() + "/.gludebug").exists()) {
            DEBUG = true;
            SharedPreferences.Editor editor = sprefs.edit();
            editor.putBoolean(SHAREDPREF_KEY_DEBUG, DEBUG);
            editor.commit();
        }
        AInAppPurchase.Log("Init( " + string2 + ", " + string3 + ", " + bl + " )");
        AInAppPurchase.Log("AInAppPurchase Version: 1.1.1");
        unityGameObject = string2;
        if (string3.toLowerCase().equals((Object)"google")) {
            billing = BillingType.GOOGLE;
        } else if (string3.toLowerCase().equals((Object)"amazon")) {
            billing = BillingType.AMAZON;
        }
        if (billing == BillingType.GOOGLE) {
            iap = new GoogleIAP();
        } else if (billing == BillingType.AMAZON) {
            iap = new AmazonIAP();
        }
        if (iap != null) {
            iap.Init();
        }
    }

    private static void Log(String string2) {
        if (DEBUG) {
            Log.d((String)"AInAppPurchase", (String)string2);
        }
    }

    private static void LogE(String string2) {
        if (DEBUG) {
            Log.e((String)"AInAppPurchase", (String)string2);
        }
    }

    public static void Register() {
        AInAppPurchase.Log("Register()");
        if (iap != null) {
            iap.Register();
        }
    }

    public static void RequestPendingPurchases() {
        AInAppPurchase.Log("RequestPendingPurchases()");
        if (sprefs.contains(SHAREDPREF_KEY_PENDING)) {
            String string2 = sprefs.getString(SHAREDPREF_KEY_PENDING, null);
            AInAppPurchase.Log("RequestPendingPurchases() Pending: " + string2);
            String[] arrstring = string2.split(";");
            for (int i2 = 0; i2 < arrstring.length; ++i2) {
                AInAppPurchase.Log("RequestPendingPurchases() Transaction[" + i2 + "]: " + arrstring[i2]);
                AInAppPurchase.onPurchaseStateChange(arrstring[i2]);
            }
        }
    }

    public static void RequestPurchase(String string2, String string3) {
        AInAppPurchase.Log("RequestPurchase( " + string2 + ", " + string3 + " )");
        if (DEBUG && (string2.contains((CharSequence)"daily") || string2.contains((CharSequence)"weekly") || string2.contains((CharSequence)"monthly") || string2.contains((CharSequence)"yearly") || string2.contains((CharSequence)"vip")) && !string3.equals((Object)"subscription")) {
            AInAppPurchase.LogE("***********************************************************");
            AInAppPurchase.LogE("***********************************************************");
            AInAppPurchase.LogE("***********************************************************");
            AInAppPurchase.LogE("**********                WARNING                **********");
            AInAppPurchase.LogE("***********************************************************");
            AInAppPurchase.LogE("***********************************************************");
            AInAppPurchase.LogE("***********************************************************");
            AInAppPurchase.LogE("Item ID: " + string2);
            AInAppPurchase.LogE("Developer Payload: " + string3);
            AInAppPurchase.LogE("If " + string2 + " is a subscription,");
            AInAppPurchase.LogE("it must pass the developer payload \"subscription\".");
            AInAppPurchase.LogE("***********************************************************");
            AInAppPurchase.LogE("***********************************************************");
            AInAppPurchase.LogE("***********************************************************");
        }
        if (iap != null) {
            iap.RequestPurchase(string2, string3);
        }
    }

    public static void RestoreTransactions() {
        AInAppPurchase.Log("RestoreTransactions()");
        if (iap != null) {
            iap.RestoreTransactions();
        }
    }

    public static void Unregister() {
        AInAppPurchase.Log("Unregister()");
        if (iap != null) {
            iap.Unregister();
        }
    }

    public static void onBillingSupported(boolean bl) {
        AInAppPurchase.Log("UnitySendMessage onBillingSupported: " + bl);
        if (UnityPlayer.currentActivity != null) {
            UnityPlayer.UnitySendMessage(unityGameObject, "onBillingSupported", Boolean.toString((boolean)bl));
        }
    }

    public static void onGetUserIdResponse(String string2) {
        AInAppPurchase.Log("UnitySendMessage onGetUserIdResponse: " + string2);
        if (UnityPlayer.currentActivity != null) {
            UnityPlayer.UnitySendMessage(unityGameObject, "onGetUserIdResponse", string2);
        }
    }

    public static void onPurchaseStateChange(String string2) {
        AInAppPurchase.Log("UnitySendMessage onPurchaseStateChange: " + string2);
        if (UnityPlayer.currentActivity != null) {
            UnityPlayer.UnitySendMessage(unityGameObject, "onPurchaseStateChange", string2);
        }
    }

    public static void onRequestPurchaseResponse(String string2) {
        AInAppPurchase.Log("UnitySendMessage onRequestPurchaseResponse: " + string2);
        if (UnityPlayer.currentActivity != null) {
            UnityPlayer.UnitySendMessage(unityGameObject, "onRequestPurchaseResponse", string2);
        }
    }

    public static void onRestoreTransactionsResponse(String string2) {
        AInAppPurchase.Log("UnitySendMessage onRestoreTransactionsResponse: " + string2);
        if (UnityPlayer.currentActivity != null) {
            UnityPlayer.UnitySendMessage(unityGameObject, "onRestoreTransactionsResponse", string2);
        }
    }

    public static void onSubscriptionSupported(boolean bl) {
        AInAppPurchase.Log("UnitySendMessage onSubscriptionSupported: " + bl);
        if (UnityPlayer.currentActivity != null) {
            UnityPlayer.UnitySendMessage(unityGameObject, "onSubscriptionSupported", Boolean.toString((boolean)bl));
        }
    }

    private static final class BillingType
    extends Enum<BillingType> {
        private static final /* synthetic */ BillingType[] $VALUES;
        public static final /* enum */ BillingType AMAZON;
        public static final /* enum */ BillingType GOOGLE;

        static {
            GOOGLE = new BillingType();
            AMAZON = new BillingType();
            BillingType[] arrbillingType = new BillingType[]{GOOGLE, AMAZON};
            $VALUES = arrbillingType;
        }

        public static BillingType valueOf(String string2) {
            return (BillingType)Enum.valueOf(BillingType.class, (String)string2);
        }

        public static BillingType[] values() {
            return (BillingType[])$VALUES.clone();
        }
    }

    public static interface IAP {
        public void Destroy();

        public void Init();

        public void Register();

        public void RequestPurchase(String var1, String var2);

        public void RestoreTransactions();

        public void Unregister();
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.res.Resources
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.widget.LinearLayout
 *  android.widget.ProgressBar
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.glu.plugins;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.glu.plugins.AJTDeviceInfo;
import com.glu.plugins.AJavaTools;
import com.unity3d.player.UnityPlayer;

public class AJTUI {
    private static ProgressBar pb;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String GetString(String string2) {
        try {
            String string3 = UnityPlayer.currentActivity.getPackageName();
            Resources resources = UnityPlayer.currentActivity.getApplicationContext().getResources();
            String string4 = resources.getString(resources.getIdentifier("string/" + string2, null, string3));
            return string4;
        }
        catch (Exception exception) {
            if (!AJavaTools.DEBUG) return string2;
            exception.printStackTrace();
            return string2;
        }
    }

    public static void ShowAlert(final String string2, final String string3, final String string4, final String string5, final String string6, final String string7, final String string8) {
        AJavaTools.Log("ShowAlert( " + string2 + ", " + string3 + ", " + string4 + ", " + string5 + ", " + string6 + ", " + string7 + ", " + string8 + " )");
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                        AJavaTools.Log("UnitySendMessage " + string3 + "( " + n + " )");
                        UnityPlayer.UnitySendMessage(string2, string3, Integer.toString((int)n));
                        dialogInterface.dismiss();
                    }
                };
                DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener(){

                    public void onCancel(DialogInterface dialogInterface) {
                        AJavaTools.Log("UnitySendMessage " + string3 + "( " + -2 + " )");
                        UnityPlayer.UnitySendMessage(string2, string3, Integer.toString((int)-2));
                    }
                };
                AlertDialog alertDialog = new AlertDialog.Builder((Context)UnityPlayer.currentActivity).create();
                alertDialog.setTitle((CharSequence)string4);
                alertDialog.setMessage((CharSequence)string5);
                alertDialog.setOnCancelListener(onCancelListener);
                alertDialog.setButton(-1, (CharSequence)string6, onClickListener);
                if (string7 != null && !string7.equals((Object)"")) {
                    alertDialog.setButton(-2, (CharSequence)string7, onClickListener);
                }
                if (string8 != null && !string8.equals((Object)"")) {
                    alertDialog.setButton(-3, (CharSequence)string8, onClickListener);
                }
                alertDialog.show();
            }

        });
    }

    public static void ShowExitPrompt(final String string2, final String string3) {
        AJavaTools.Log("ShowExitPrompt( " + string2 + ", " + string3 + " )");
        final String string4 = UnityPlayer.currentActivity.getPackageName();
        final Resources resources = UnityPlayer.currentActivity.getApplicationContext().getResources();
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                        if (n == -1) {
                            UnityPlayer.currentActivity.finish();
                            return;
                        }
                        if (string2 != null && !string2.equals((Object)"") && string3 != null && !string3.equals((Object)"")) {
                            AJavaTools.Log("UnitySendMessage " + string3 + "( " + n + " )");
                            UnityPlayer.UnitySendMessage(string2, string3, Integer.toString((int)n));
                        }
                        dialogInterface.dismiss();
                    }
                };
                DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener(){

                    public void onCancel(DialogInterface dialogInterface) {
                        if (string2 != null && !string2.equals((Object)"") && string3 != null && !string3.equals((Object)"")) {
                            AJavaTools.Log("UnitySendMessage " + string3 + "( " + -2 + " )");
                            UnityPlayer.UnitySendMessage(string2, string3, Integer.toString((int)-2));
                        }
                    }
                };
                AlertDialog alertDialog = new AlertDialog.Builder((Context)UnityPlayer.currentActivity).create();
                alertDialog.setTitle((CharSequence)resources.getString(resources.getIdentifier("string/exit_prompt_title", null, string4)));
                alertDialog.setMessage((CharSequence)resources.getString(resources.getIdentifier("string/exit_prompt_message", null, string4)));
                alertDialog.setOnCancelListener(onCancelListener);
                alertDialog.setButton(-1, (CharSequence)resources.getString(resources.getIdentifier("string/exit_prompt_button_exit", null, string4)), onClickListener);
                alertDialog.setButton(-2, (CharSequence)resources.getString(resources.getIdentifier("string/exit_prompt_button_cancel", null, string4)), onClickListener);
                alertDialog.show();
            }

        });
    }

    public static void ShowNotificationPrompt(String string2, String string3) {
        AJavaTools.Log("ShowNotificationPrompt( " + string2 + ", " + string3 + " )");
        String string4 = UnityPlayer.currentActivity.getPackageName();
        Resources resources = UnityPlayer.currentActivity.getApplicationContext().getResources();
        AJTUI.ShowAlert(string2, string3, resources.getString(resources.getIdentifier("string/notification_prompt_title", null, string4)), resources.getString(resources.getIdentifier("string/notification_prompt_message", null, string4)), resources.getString(resources.getIdentifier("string/notification_prompt_button_yes", null, string4)), resources.getString(resources.getIdentifier("string/notification_prompt_button_no", null, string4)), null);
    }

    public static void ShowToast(final String string2) {
        AJavaTools.Log("ShowToast( " + string2 + " )");
        if (UnityPlayer.currentActivity != null) {
            UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

                public void run() {
                    Toast.makeText((Context)UnityPlayer.currentActivity, (CharSequence)string2, (int)1).show();
                }
            });
        }
    }

    public static void StartIndeterminateProgress(final int n) {
        AJavaTools.Log("StartIndeterminateProgress( " + n + " )");
        if (pb != null) {
            return;
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

            public void run() {
                pb = new ProgressBar((Context)UnityPlayer.currentActivity);
                pb.setIndeterminate(true);
                LinearLayout linearLayout = new LinearLayout((Context)UnityPlayer.currentActivity);
                linearLayout.setGravity(n);
                linearLayout.addView((View)pb);
                UnityPlayer.currentActivity.getWindow().addContentView((View)linearLayout, new ViewGroup.LayoutParams(AJTDeviceInfo.GetScreenWidth(), AJTDeviceInfo.GetScreenHeight()));
            }
        });
    }

    public static void StopIndeterminateProgress() {
        AJavaTools.Log("StopIndeterminateProgress()");
        if (pb == null) {
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
                    ViewGroup viewGroup = (ViewGroup)pb.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView((View)pb);
                    }
                    pb = null;
                    return;
                }
                catch (Exception exception) {
                    return;
                }
            }
        });
    }

}


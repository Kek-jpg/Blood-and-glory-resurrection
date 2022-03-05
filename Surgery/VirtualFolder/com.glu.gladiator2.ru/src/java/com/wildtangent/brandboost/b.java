/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package com.wildtangent.brandboost;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class b {
    public static void a(Context context, String string, String string2, String string3, String string4, final Runnable runnable, final Runnable runnable2, final Runnable runnable3) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle((CharSequence)string);
        builder.setMessage((CharSequence)string2);
        builder.setCancelable(true);
        if (string3 != null) {
            builder.setPositiveButton((CharSequence)string3, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n2) {
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
        }
        if (string4 != null) {
            builder.setNegativeButton((CharSequence)string4, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n2) {
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener(){

            public void onCancel(DialogInterface dialogInterface) {
                if (runnable3 != null) {
                    runnable3.run();
                }
            }
        });
        alertDialog.show();
    }

}


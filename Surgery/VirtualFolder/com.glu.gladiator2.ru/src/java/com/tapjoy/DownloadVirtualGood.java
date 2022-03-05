/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.tapjoy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.tapjoy.TJCVirtualGoods;
import com.tapjoy.TapjoyLog;

public class DownloadVirtualGood
extends Activity {
    public static final String EXTRA_KEY_VIRTUAL_GOOD_NAME = "NAME";
    private Button cancelBtn;
    View.OnClickListener cancelListener = new View.OnClickListener(){

        public void onClick(View view) {
            DownloadVirtualGood.this.finish();
        }
    };
    private String clientPackage = "";
    private Button downloadBtn;
    View.OnClickListener downloadListener = new View.OnClickListener(){

        public void onClick(View view) {
            Intent intent = new Intent((Context)DownloadVirtualGood.this, TJCVirtualGoods.class);
            intent.putExtra("URL_PARAMS", DownloadVirtualGood.this.urlParams);
            intent.putExtra("my_items_tab", true);
            DownloadVirtualGood.this.startActivity(intent);
            DownloadVirtualGood.this.finish();
        }
    };
    private String urlParams = "";
    private TextView vgAcquiredMsg;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(1);
        this.clientPackage = this.getPackageName();
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null && bundle2.containsKey("URL_PARAMS")) {
            this.urlParams = bundle2.getString("URL_PARAMS");
        }
        TapjoyLog.i("DOWNLOAD VIRTUAL GOODS", "clientPackage: " + TJCVirtualGoods.getClientPackage());
        this.setContentView(this.getResources().getIdentifier("tapjoy_virtualgoods_reconnectvirtualgoods", "layout", this.clientPackage));
        this.downloadBtn = (Button)this.findViewById(this.getResources().getIdentifier("DownloadBtn", "id", this.clientPackage));
        this.cancelBtn = (Button)this.findViewById(this.getResources().getIdentifier("CancelBtn", "id", this.clientPackage));
        this.vgAcquiredMsg = (TextView)this.findViewById(this.getResources().getIdentifier("VGAcquiredMsgText", "id", this.clientPackage));
        this.downloadBtn.setOnClickListener(this.downloadListener);
        this.cancelBtn.setOnClickListener(this.cancelListener);
        this.vgAcquiredMsg.setText((CharSequence)("You have successfully acquired item '" + bundle2.getString(EXTRA_KEY_VIRTUAL_GOOD_NAME) + "'." + " Would you like to download it now?"));
    }

}


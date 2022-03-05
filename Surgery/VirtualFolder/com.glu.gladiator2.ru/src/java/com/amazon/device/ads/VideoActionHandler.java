/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  java.lang.Object
 *  java.lang.String
 */
package com.amazon.device.ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.amazon.device.ads.AdVideoPlayer;
import com.amazon.device.ads.Controller;

public class VideoActionHandler
extends Activity {
    private RelativeLayout layout_;
    private AdVideoPlayer player_;

    /*
     * Enabled aggressive block sorting
     */
    private void initPlayer(Bundle bundle) {
        RelativeLayout.LayoutParams layoutParams;
        Controller.PlayerProperties playerProperties = (Controller.PlayerProperties)bundle.getParcelable("player_properties");
        Controller.Dimensions dimensions = (Controller.Dimensions)bundle.getParcelable("player_dimensions");
        this.player_ = new AdVideoPlayer((Context)this);
        this.player_.setPlayData(playerProperties, bundle.getString("url"));
        if (dimensions == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(13);
        } else {
            layoutParams = new RelativeLayout.LayoutParams(dimensions.width, dimensions.height);
            layoutParams.topMargin = dimensions.y;
            layoutParams.leftMargin = dimensions.x;
        }
        this.player_.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.layout_.addView((View)this.player_);
        VideoActionHandler.super.setPlayerListener(this.player_);
    }

    private void setPlayerListener(AdVideoPlayer adVideoPlayer) {
        adVideoPlayer.setListener(new AdVideoPlayer.AdVideoPlayerListener(){

            @Override
            public void onComplete() {
                VideoActionHandler.this.finish();
            }

            @Override
            public void onError() {
                VideoActionHandler.this.finish();
            }

            @Override
            public void onPrepared() {
            }
        });
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle bundle2 = this.getIntent().getExtras();
        this.layout_ = new RelativeLayout((Context)this);
        this.layout_.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.setContentView((View)this.layout_);
        VideoActionHandler.super.initPlayer(bundle2);
        this.player_.playVideo();
    }

    protected void onStop() {
        this.player_.releasePlayer();
        this.player_ = null;
        super.onStop();
    }

}


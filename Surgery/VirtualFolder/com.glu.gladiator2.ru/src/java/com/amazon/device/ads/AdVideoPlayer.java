/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.AudioManager
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnCompletionListener
 *  android.media.MediaPlayer$OnErrorListener
 *  android.media.MediaPlayer$OnPreparedListener
 *  android.net.Uri
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.MediaController
 *  android.widget.VideoView
 *  java.lang.Object
 *  java.lang.String
 */
package com.amazon.device.ads;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.MediaController;
import android.widget.VideoView;
import com.amazon.device.ads.Controller;
import com.amazon.device.ads.Log;

final class AdVideoPlayer
extends VideoView
implements MediaPlayer.OnCompletionListener,
MediaPlayer.OnErrorListener,
MediaPlayer.OnPreparedListener {
    private static String LOG_TAG = "AdVideoPlayer";
    private AudioManager audioManager_;
    private String contentUrl_;
    private Context context_;
    private AdVideoPlayerListener listener_;
    private Controller.PlayerProperties playerProperties_;
    private boolean released_ = false;
    private int volumeBeforeMuting_;

    public AdVideoPlayer(Context context) {
        super(context);
        this.context_ = context;
        this.setOnCompletionListener((MediaPlayer.OnCompletionListener)this);
        this.setOnErrorListener((MediaPlayer.OnErrorListener)this);
        this.setOnPreparedListener((MediaPlayer.OnPreparedListener)this);
        this.playerProperties_ = new Controller.PlayerProperties();
        this.audioManager_ = (AudioManager)this.context_.getSystemService("audio");
    }

    private void displayPlayerControls() {
        Log.d(LOG_TAG, "in displayPlayerControls");
        if (this.playerProperties_.showControl()) {
            MediaController mediaController = new MediaController(this.context_);
            this.setMediaController(mediaController);
            mediaController.setAnchorView((View)this);
            mediaController.requestFocus();
        }
    }

    private void loadPlayerContent() {
        this.setVideoURI(Uri.parse((String)this.contentUrl_));
    }

    private void removePlayerFromParent() {
        Log.d(LOG_TAG, "in removePlayerFromParent");
        ViewGroup viewGroup = (ViewGroup)this.getParent();
        if (viewGroup != null) {
            viewGroup.removeView((View)this);
        }
    }

    public void mutePlayer() {
        Log.d(LOG_TAG, "in mutePlayer");
        this.volumeBeforeMuting_ = this.audioManager_.getStreamVolume(3);
        this.audioManager_.setStreamVolume(3, 0, 4);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (this.playerProperties_.doLoop()) {
            this.start();
        } else if (this.playerProperties_.exitOnComplete() || this.playerProperties_.inline) {
            this.releasePlayer();
        }
        if (this.listener_ != null) {
            this.listener_.onComplete();
        }
    }

    public boolean onError(MediaPlayer mediaPlayer, int n2, int n3) {
        AdVideoPlayer.super.removePlayerFromParent();
        if (this.listener_ != null) {
            this.listener_.onError();
        }
        return false;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        if (this.listener_ != null) {
            this.listener_.onPrepared();
        }
    }

    public void playAudio() {
        Log.d(LOG_TAG, "in playAudio");
        this.loadPlayerContent();
    }

    public void playVideo() {
        Log.d(LOG_TAG, "in playVideo");
        if (this.playerProperties_.doMute()) {
            this.mutePlayer();
        }
        this.loadPlayerContent();
        this.startPlaying();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void releasePlayer() {
        block5 : {
            block4 : {
                Log.d(LOG_TAG, "in releasePlayer");
                if (this.released_) break block4;
                this.released_ = true;
                this.stopPlayback();
                this.removePlayerFromParent();
                if (this.playerProperties_.doMute()) {
                    this.unmutePlayer();
                }
                if (this.listener_ != null) break block5;
            }
            return;
        }
        this.listener_.onComplete();
    }

    public void setListener(AdVideoPlayerListener adVideoPlayerListener) {
        this.listener_ = adVideoPlayerListener;
    }

    public void setPlayData(Controller.PlayerProperties playerProperties, String string) {
        this.released_ = false;
        if (playerProperties != null) {
            this.playerProperties_ = playerProperties;
        }
        this.contentUrl_ = string;
    }

    public void startPlaying() {
        Log.d(LOG_TAG, "in startPlaying");
        this.displayPlayerControls();
        if (this.playerProperties_.isAutoPlay()) {
            this.start();
        }
    }

    public void unmutePlayer() {
        Log.d(LOG_TAG, "in unmutePlayer");
        this.audioManager_.setStreamVolume(3, this.volumeBeforeMuting_, 4);
    }

    public static interface AdVideoPlayerListener {
        public void onComplete();

        public void onError();

        public void onPrepared();
    }

}


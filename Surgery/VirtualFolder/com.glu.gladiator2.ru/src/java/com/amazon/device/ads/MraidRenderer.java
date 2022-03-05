/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.amazon.device.ads;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdBridge;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.AdRenderer;
import com.amazon.device.ads.Log;
import com.amazon.device.ads.MraidDisplayController;
import com.amazon.device.ads.MraidView;
import java.util.Map;

class MraidRenderer
extends AdRenderer
implements MraidView.OnCloseListener,
MraidView.OnExpandListener,
MraidView.OnReadyListener,
MraidView.OnSpecialUrlClickListener {
    private static final String LOG_TAG = "MraidRenderer";
    protected MraidView mraidView_;

    protected MraidRenderer(Ad ad2, AdBridge adBridge) {
        super(ad2, adBridge);
    }

    @Override
    protected void adLoaded(AdProperties adProperties) {
        super.adLoaded(adProperties);
    }

    @Override
    protected void destroy() {
        if (this.mraidView_ != null) {
            this.mraidView_.destroy();
            this.mraidView_ = null;
            this.isDestroyed_ = true;
        }
    }

    @Override
    protected boolean getAdState(AdRenderer.AdState adState) {
        switch (adState) {
            default: {
                return false;
            }
            case EXPANDED: 
        }
        return this.mraidView_.getDisplayController().isExpanded();
    }

    @Override
    public void onClose(MraidView mraidView, MraidView.ViewState viewState) {
        if (!this.isAdViewRemoved()) {
            this.bridge_.adClosedExpansion();
        }
    }

    @Override
    public void onExpand(MraidView mraidView) {
        if (!this.isAdViewRemoved()) {
            this.bridge_.adExpanded();
        }
    }

    @Override
    public void onReady(MraidView mraidView) {
        this.adLoaded(this.ad_.getProperties());
    }

    @Override
    public void onSpecialUrlClick(MraidView mraidView, String string) {
        if (!this.isAdViewRemoved()) {
            this.bridge_.specialUrlClicked(string);
        }
    }

    @Override
    protected void prepareToGoAway() {
        if (this.mraidView_ != null) {
            this.mraidView_.prepareToGoAway();
        }
    }

    @Override
    protected void removeView() {
        if (!this.bridge_.isInvalidated()) {
            this.bridge_.getAdLayout().removeAllViews();
        }
        this.viewRemoved_ = true;
    }

    @Override
    protected void render() {
        if (this.isAdViewDestroyed()) {
            return;
        }
        this.mraidView_ = new MraidView(this, this.bridge_.getWindowWidth(), this.bridge_.getWindowHeight(), this.scalingFactor_);
        this.mraidView_.loadHtmlData(this.ad_.getCreative());
        this.mraidView_.setOnReadyListener(this);
        this.mraidView_.setOnSpecialUrlClickListener(this);
        this.mraidView_.setOnExpandListener(this);
        this.mraidView_.setOnCloseListener(this);
        this.bridge_.getAdLayout().removeAllViews();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1, 17);
        this.bridge_.getAdLayout().addView((View)this.mraidView_, (ViewGroup.LayoutParams)layoutParams);
    }

    @Override
    protected boolean sendCommand(String string, Map<String, String> map) {
        Log.d(LOG_TAG, "sendCommand: %s", string);
        if (string.equals((Object)"close") && this.mraidView_ != null && this.mraidView_.getDisplayController().isExpanded()) {
            this.mraidView_.getDisplayController().close();
            return true;
        }
        return false;
    }

    @Override
    protected boolean shouldReuse() {
        return false;
    }

}


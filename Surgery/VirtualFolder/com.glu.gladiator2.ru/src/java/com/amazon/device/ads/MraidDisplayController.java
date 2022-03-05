/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.graphics.Bitmap
 *  android.graphics.Rect
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnKeyListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.view.WindowManager
 *  android.webkit.URLUtil
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.amazon.device.ads;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.amazon.device.ads.AdVideoPlayer;
import com.amazon.device.ads.Controller;
import com.amazon.device.ads.Log;
import com.amazon.device.ads.MraidAbstractController;
import com.amazon.device.ads.MraidProperty;
import com.amazon.device.ads.MraidRenderer;
import com.amazon.device.ads.MraidScreenSizeProperty;
import com.amazon.device.ads.MraidStateProperty;
import com.amazon.device.ads.MraidView;
import com.amazon.device.ads.MraidViewableProperty;
import com.amazon.device.ads.ResourceLookup;
import com.amazon.device.ads.Utils;
import com.amazon.device.ads.VideoActionHandler;
import java.util.ArrayList;

class MraidDisplayController
extends MraidAbstractController {
    private static final int CLOSE_BUTTON_SIZE_DP = 50;
    private static final String LOGTAG = "MraidDisplayController";
    private boolean mAdWantsCustomCloseButton;
    private ImageView mCloseButton;
    private Context mContext;
    protected float mDensity;
    private final MraidView.ExpansionStyle mExpansionStyle;
    private boolean mIsViewable;
    private final MraidView.NativeCloseButtonStyle mNativeCloseButtonStyle;
    private BroadcastReceiver mOrientationBroadcastReceiver;
    private final int mOriginalRequestedOrientation;
    FrameLayout mPlaceholderView;
    private boolean mRegistered;
    private FrameLayout mRootView;
    protected int mScreenHeight;
    protected int mScreenWidth;
    private MraidView mTwoPartExpansionView;
    private int mViewHeight;
    private int mViewIndexInParent;
    private MraidView.ViewState mViewState;
    private int mViewWidth;
    private double scalingFactor_;
    private AdVideoPlayer vidPlayer_;
    private boolean vidPlaying_;
    private int windowHeight_;
    private int windowWidth_;

    MraidDisplayController(MraidView mraidView, MraidView.ExpansionStyle expansionStyle, MraidView.NativeCloseButtonStyle nativeCloseButtonStyle) {
        int n2 = -1;
        super(mraidView);
        this.mViewState = MraidView.ViewState.HIDDEN;
        this.vidPlaying_ = false;
        this.mOrientationBroadcastReceiver = new BroadcastReceiver(){
            private int mLastRotation;

            public void onReceive(Context context, Intent intent) {
                int n2;
                if (intent.getAction().equals((Object)"android.intent.action.CONFIGURATION_CHANGED") && (n2 = MraidDisplayController.this.getDisplayRotation()) != this.mLastRotation) {
                    this.mLastRotation = n2;
                    MraidDisplayController.this.onOrientationChanged(this.mLastRotation);
                }
            }
        };
        this.mScreenWidth = n2;
        this.mScreenHeight = n2;
        this.mRegistered = false;
        this.mExpansionStyle = expansionStyle;
        this.mNativeCloseButtonStyle = nativeCloseButtonStyle;
        this.windowHeight_ = mraidView.getWindowHeight();
        this.windowWidth_ = mraidView.getWindowWidth();
        this.scalingFactor_ = mraidView.getScalingFactor();
        this.mContext = this.getView().getContext();
        if (this.mContext instanceof Activity) {
            n2 = ((Activity)this.mContext).getRequestedOrientation();
        }
        this.mOriginalRequestedOrientation = n2;
        this.vidPlayer_ = new AdVideoPlayer(this.mContext);
        MraidDisplayController.super.initialize();
    }

    private ViewGroup createExpansionViewContainer(View view, int n2, int n3) {
        int n4 = (int)(0.5f + 50.0f * this.mDensity);
        if (n2 < n4) {
            n2 = n4;
        }
        if (n3 < n4) {
            n3 = n4;
        }
        RelativeLayout relativeLayout = new RelativeLayout(this.getView().getContext());
        relativeLayout.setId(101);
        View view2 = new View(this.getView().getContext());
        view2.setBackgroundColor(0);
        view2.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        relativeLayout.addView(view2, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
        FrameLayout frameLayout = new FrameLayout(this.getView().getContext());
        frameLayout.setId(102);
        frameLayout.addView(view, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(n2, n3);
        layoutParams.addRule(13);
        relativeLayout.addView((View)frameLayout, (ViewGroup.LayoutParams)layoutParams);
        return relativeLayout;
    }

    private int getDisplayRotation() {
        return ((WindowManager)this.getView().getContext().getSystemService("window")).getDefaultDisplay().getOrientation();
    }

    private void initialize() {
        this.mViewState = MraidView.ViewState.LOADING;
        this.initializeScreenMetrics();
        this.registerRecievers();
    }

    private void initializeScreenMetrics() {
        Context context = this.getView().getContext();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.mDensity = displayMetrics.density;
        boolean bl = context instanceof Activity;
        int n2 = 0;
        int n3 = 0;
        if (bl) {
            Window window = ((Activity)context).getWindow();
            Rect rect = new Rect();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            n2 = rect.top;
            n3 = window.findViewById(16908290).getTop() - n2;
        }
        int n4 = displayMetrics.widthPixels;
        int n5 = displayMetrics.heightPixels - n2 - n3;
        this.mScreenWidth = (int)((double)n4 * (160.0 / (double)displayMetrics.densityDpi));
        this.mScreenHeight = (int)((double)n5 * (160.0 / (double)displayMetrics.densityDpi));
    }

    private void onOrientationChanged(int n2) {
        MraidDisplayController.super.initializeScreenMetrics();
        if (this.mRegistered) {
            this.getView().fireChangeEventForProperty(MraidScreenSizeProperty.createWithSize(this.mScreenWidth, this.mScreenHeight));
        }
    }

    private void resetViewToDefaultState() {
        FrameLayout frameLayout = (FrameLayout)this.mRootView.findViewById(102);
        RelativeLayout relativeLayout = (RelativeLayout)this.mRootView.findViewById(101);
        this.setNativeCloseButtonEnabled(false);
        frameLayout.removeAllViewsInLayout();
        this.mRootView.removeView((View)relativeLayout);
        this.getView().requestLayout();
        ViewGroup viewGroup = (ViewGroup)this.mPlaceholderView.getParent();
        viewGroup.addView((View)this.getView(), this.mViewIndexInParent, new ViewGroup.LayoutParams(this.mViewWidth, this.mViewHeight));
        viewGroup.removeView((View)this.mPlaceholderView);
        viewGroup.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setOrientationLockEnabled(boolean bl) {
        Context context = this.getView().getContext();
        try {
            Activity activity = (Activity)context;
            int n2 = bl ? Utils.determineCanonicalScreenOrientation(activity) : this.mOriginalRequestedOrientation;
            activity.setRequestedOrientation(n2);
            return;
        }
        catch (Exception exception) {
            Log.d(LOGTAG, "Unable to modify device orientation.");
            return;
        }
    }

    private void swapViewWithPlaceholderView() {
        ViewGroup viewGroup = (ViewGroup)this.getView().getParent();
        if (viewGroup == null) {
            return;
        }
        this.mPlaceholderView = new FrameLayout(this.getView().getContext());
        int n2 = viewGroup.getChildCount();
        int n3 = 0;
        do {
            if (n3 >= n2 || viewGroup.getChildAt(n3) == this.getView()) {
                this.mViewIndexInParent = n3;
                this.mViewHeight = this.getView().getHeight();
                this.mViewWidth = this.getView().getWidth();
                viewGroup.addView((View)this.mPlaceholderView, n3, new ViewGroup.LayoutParams(this.getView().getWidth(), this.getView().getHeight()));
                viewGroup.removeView((View)this.getView());
                return;
            }
            ++n3;
        } while (true);
    }

    protected boolean checkViewable() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void close() {
        if (this.vidPlaying_) {
            this.vidPlayer_.releasePlayer();
            this.vidPlaying_ = false;
        }
        if (this.mViewState == MraidView.ViewState.EXPANDED) {
            this.resetViewToDefaultState();
            this.setOrientationLockEnabled(false);
            this.mViewState = MraidView.ViewState.DEFAULT;
            this.getView().fireChangeEventForProperty(MraidStateProperty.createWithViewState(this.mViewState));
        } else if (this.mViewState == MraidView.ViewState.DEFAULT) {
            this.getView().setVisibility(4);
            this.mViewState = MraidView.ViewState.HIDDEN;
            this.getView().fireChangeEventForProperty(MraidStateProperty.createWithViewState(this.mViewState));
        }
        if (this.getView().getOnCloseListener() != null) {
            this.getView().getOnCloseListener().onClose(this.getView(), this.mViewState);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void destroy() {
        try {
            this.getView().getContext().unregisterReceiver(this.mOrientationBroadcastReceiver);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            if (illegalArgumentException.getMessage().contains((CharSequence)"Receiver not registered")) return;
            throw illegalArgumentException;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void detachExpandedView() {
        if (this.mViewState == MraidView.ViewState.EXPANDED) {
            Log.d(LOGTAG, "Ad is currently expanded. Detaching the expanded view and returning ad to its default state.");
            RelativeLayout relativeLayout = (RelativeLayout)this.mRootView.findViewById(101);
            if (relativeLayout.isShown()) {
                this.mRootView.removeView((View)relativeLayout);
            } else {
                relativeLayout.removeAllViews();
            }
            ((ViewGroup)this.mPlaceholderView.getParent()).removeView((View)this.mPlaceholderView);
            this.setOrientationLockEnabled(false);
            this.mViewState = MraidView.ViewState.DEFAULT;
            this.getView().fireChangeEventForProperty(MraidStateProperty.createWithViewState(this.mViewState));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void expand(String string, int n2, int n3, boolean bl, boolean bl2) {
        block7 : {
            block6 : {
                if (this.mExpansionStyle == MraidView.ExpansionStyle.DISABLED || this.mViewState == MraidView.ViewState.EXPANDED) break block6;
                if (string != null && !URLUtil.isValidUrl((String)string)) {
                    this.getView().fireErrorEvent("expand", "URL passed to expand() was invalid.");
                    return;
                }
                this.mRootView = (FrameLayout)this.getView().getRootView().findViewById(16908290);
                this.useCustomClose(bl);
                this.setOrientationLockEnabled(bl2);
                this.swapViewWithPlaceholderView();
                MraidView mraidView = this.getView();
                if (string != null) {
                    this.mTwoPartExpansionView = new MraidView(this.getView().getRenderer(), this.windowWidth_, this.windowHeight_, this.scalingFactor_, MraidView.ExpansionStyle.DISABLED, MraidView.NativeCloseButtonStyle.AD_CONTROLLED, MraidView.PlacementType.INLINE);
                    this.mTwoPartExpansionView.setOnCloseListener(new MraidView.OnCloseListener(){

                        @Override
                        public void onClose(MraidView mraidView, MraidView.ViewState viewState) {
                            MraidDisplayController.this.close();
                        }
                    });
                    this.mTwoPartExpansionView.loadUrl(string);
                    mraidView = this.mTwoPartExpansionView;
                }
                ViewGroup viewGroup = this.createExpansionViewContainer((View)mraidView, (int)((float)n2 * this.mDensity), (int)((float)n3 * this.mDensity));
                this.mRootView.addView((View)viewGroup, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
                mraidView.requestFocus();
                mraidView.setOnKeyListener(new View.OnKeyListener(){

                    public boolean onKey(View view, int n2, KeyEvent keyEvent) {
                        if (n2 == 4 && keyEvent.getRepeatCount() == 0) {
                            MraidDisplayController.this.close();
                            return true;
                        }
                        return false;
                    }
                });
                if (this.mNativeCloseButtonStyle == MraidView.NativeCloseButtonStyle.ALWAYS_VISIBLE || !this.mAdWantsCustomCloseButton && this.mNativeCloseButtonStyle != MraidView.NativeCloseButtonStyle.ALWAYS_HIDDEN) {
                    this.setNativeCloseButtonEnabled(true);
                }
                this.mViewState = MraidView.ViewState.EXPANDED;
                this.getView().fireChangeEventForProperty(MraidStateProperty.createWithViewState(this.mViewState));
                if (this.getView().getOnExpandListener() != null) break block7;
            }
            return;
        }
        this.getView().getOnExpandListener().onExpand(this.getView());
    }

    protected void initializeJavaScriptState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add((Object)MraidScreenSizeProperty.createWithSize(this.mScreenWidth, this.mScreenHeight));
        arrayList.add((Object)MraidViewableProperty.createWithViewable(this.mIsViewable));
        this.getView().fireChangeEventForProperties((ArrayList<MraidProperty>)arrayList);
        this.mViewState = MraidView.ViewState.DEFAULT;
        this.getView().fireChangeEventForProperty(MraidStateProperty.createWithViewState(this.mViewState));
    }

    protected boolean isExpanded() {
        return this.mViewState == MraidView.ViewState.EXPANDED;
    }

    protected void playVideo(String string, Controller.Dimensions dimensions, Controller.PlayerProperties playerProperties) {
        Log.d(LOGTAG, "in playVideo");
        if (this.vidPlaying_) {
            return;
        }
        if (playerProperties.isFullScreen()) {
            Bundle bundle = new Bundle();
            bundle.putString("url", string);
            bundle.putParcelable("player_dimensions", (Parcelable)dimensions);
            bundle.putParcelable("player_properties", (Parcelable)playerProperties);
            try {
                Intent intent = new Intent(this.getView().getContext(), VideoActionHandler.class);
                intent.putExtras(bundle);
                this.getView().getContext().startActivity(intent);
                return;
            }
            catch (ActivityNotFoundException activityNotFoundException) {
                Log.e(LOGTAG, "Failed to open VideoAction activity");
                return;
            }
        }
        this.vidPlayer_.setPlayData(new Controller.PlayerProperties(), string);
        this.vidPlayer_.setListener(new AdVideoPlayer.AdVideoPlayerListener(){

            @Override
            public void onComplete() {
                Log.d(MraidDisplayController.LOGTAG, "videoplayback complete");
                MraidDisplayController.this.vidPlaying_ = false;
                FrameLayout frameLayout = (FrameLayout)MraidDisplayController.this.mRootView.findViewById(105);
                frameLayout.setVisibility(4);
                frameLayout.removeView((View)MraidDisplayController.this.vidPlayer_);
                MraidDisplayController.this.mRootView.removeView((View)frameLayout);
            }

            @Override
            public void onError() {
                this.onComplete();
            }

            @Override
            public void onPrepared() {
            }
        });
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dimensions.width, dimensions.height);
        layoutParams.topMargin = dimensions.x;
        layoutParams.bottomMargin = dimensions.y;
        this.vidPlayer_.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        FrameLayout frameLayout = new FrameLayout(this.getView().getContext());
        frameLayout.setId(105);
        frameLayout.setPadding(dimensions.x, dimensions.y, 0, 0);
        frameLayout.addView((View)this.vidPlayer_);
        this.mRootView.addView((View)frameLayout, -1, -1);
        this.vidPlaying_ = true;
        this.vidPlayer_.playVideo();
    }

    protected void registerRecievers() {
        if (!this.mRegistered) {
            this.mRegistered = true;
            this.getView().getContext().registerReceiver(this.mOrientationBroadcastReceiver, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void setNativeCloseButtonEnabled(boolean bl) {
        MraidView mraidView;
        block7 : {
            block6 : {
                if (this.mRootView == null) break block6;
                FrameLayout frameLayout = (FrameLayout)this.mRootView.findViewById(102);
                if (bl) {
                    if (this.mCloseButton == null) {
                        StateListDrawable stateListDrawable = new StateListDrawable();
                        stateListDrawable.addState(new int[]{-16842919}, (Drawable)new BitmapDrawable(ResourceLookup.bitmapFromJar(this.mContext, "ad_resources/drawable/amazon_ads_close_button_normal.png")));
                        stateListDrawable.addState(new int[]{16842919}, (Drawable)new BitmapDrawable(ResourceLookup.bitmapFromJar(this.mContext, "ad_resources/drawable/amazon_ads_close_button_pressed.png")));
                        this.mCloseButton = new ImageButton(this.getView().getContext());
                        this.mCloseButton.setImageDrawable((Drawable)stateListDrawable);
                        this.mCloseButton.setBackgroundDrawable(null);
                        this.mCloseButton.setOnClickListener(new View.OnClickListener(){

                            public void onClick(View view) {
                                MraidDisplayController.this.close();
                            }
                        });
                    }
                    int n2 = (int)(0.5f + 50.0f * this.mDensity);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(n2, n2, 5);
                    frameLayout.addView((View)this.mCloseButton, (ViewGroup.LayoutParams)layoutParams);
                } else {
                    frameLayout.removeView((View)this.mCloseButton);
                }
                if ((mraidView = this.getView()).getOnCloseButtonStateChangeListener() != null) break block7;
            }
            return;
        }
        mraidView.getOnCloseButtonStateChangeListener().onCloseButtonStateChange(mraidView, bl);
    }

    protected void surfaceAd() {
        this.getView().fireChangeEventForProperty(MraidViewableProperty.createWithViewable(true));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void unregisterRecievers() {
        if (!this.mRegistered) return;
        this.mRegistered = false;
        try {
            this.getView().getContext().unregisterReceiver(this.mOrientationBroadcastReceiver);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void useCustomClose(boolean bl) {
        this.mAdWantsCustomCloseButton = bl;
        MraidView mraidView = this.getView();
        boolean bl2 = !bl;
        if (mraidView.getOnCloseButtonStateChangeListener() != null) {
            mraidView.getOnCloseButtonStateChangeListener().onCloseButtonStateChange(mraidView, bl2);
        }
    }

}


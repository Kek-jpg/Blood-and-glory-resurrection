/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.view.animation.AlphaAnimation
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.AnimationSet
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  java.lang.String
 *  java.lang.System
 *  java.util.Random
 */
package com.wildtangent.brandboost.b;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.wildtangent.brandboost.util.b;
import java.util.Random;

public final class l
extends ImageButton {
    private static final String a = "com.wildtangent.brandboost__" + l.class.getSimpleName();
    private Animation b = null;
    private AnimationSet c = null;

    public l(Context context, Bitmap bitmap) {
        super(context);
        this.setScaleType(ImageView.ScaleType.CENTER);
        this.setId(new Random((long)((int)System.currentTimeMillis())).nextInt());
        this.setImageBitmap(bitmap);
        this.setBackgroundColor(0);
        l.super.b();
    }

    private void b() {
        this.b = new AlphaAnimation(0.0f, 1.0f);
        this.b.setInterpolator((Interpolator)new DecelerateInterpolator());
        this.b.setDuration(300L);
    }

    public void a() {
        if (this.c != null) {
            b.a(a, "Canceling fade-in animation");
            if (this.c.hasStarted() && !this.c.hasEnded()) {
                this.c.cancel();
                this.c.reset();
                return;
            }
            b.a(a, "fade-in animation not running");
            return;
        }
        b.d(a, "animation not defined.");
    }

    public void a(Animation.AnimationListener animationListener) {
        b.a(a, "animateCloseButton");
        this.c = new AnimationSet(false);
        this.c.addAnimation(this.b);
        this.c.setAnimationListener(animationListener);
        this.startAnimation((Animation)this.c);
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.VideoView
 */
package com.flurry.android;

import android.content.Context;
import android.widget.VideoView;

final class at
extends VideoView {
    public at(Context context) {
        super(context);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
    }

    public final void seekTo(int n2) {
        if (n2 < this.getCurrentPosition()) {
            super.seekTo(n2);
        }
    }
}


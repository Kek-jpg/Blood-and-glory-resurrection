/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.VideoView
 */
package com.wildtangent.brandboost.a;

import android.content.Context;
import android.widget.VideoView;

public final class g
extends VideoView {
    private int a = 100;
    private int b = 100;

    public g(Context context) {
        super(context);
    }

    public void a(int n2, int n3) {
        this.a = n2;
        this.b = n3;
        this.requestLayout();
        this.invalidate();
    }

    protected void onMeasure(int n2, int n3) {
        this.setMeasuredDimension(this.a, this.b);
    }
}


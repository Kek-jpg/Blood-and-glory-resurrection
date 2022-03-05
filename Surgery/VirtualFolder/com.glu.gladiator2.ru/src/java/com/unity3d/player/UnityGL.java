/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.opengl.GLSurfaceView
 *  android.opengl.GLSurfaceView$Renderer
 *  java.lang.Object
 *  java.lang.Runnable
 */
package com.unity3d.player;

import android.opengl.GLSurfaceView;

public interface UnityGL {
    public void a();

    public void onPause();

    public void onResume();

    public void queueEvent(Runnable var1);

    public void setRenderer(GLSurfaceView.Renderer var1);
}


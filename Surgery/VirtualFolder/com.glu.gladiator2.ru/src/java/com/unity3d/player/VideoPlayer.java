/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.AssetFileDescriptor
 *  android.content.res.AssetManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.hardware.Sensor
 *  android.hardware.SensorEvent
 *  android.hardware.SensorEventListener
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnBufferingUpdateListener
 *  android.media.MediaPlayer$OnCompletionListener
 *  android.media.MediaPlayer$OnPreparedListener
 *  android.media.MediaPlayer$OnVideoSizeChangedListener
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.view.Display
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.view.WindowManager
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.MediaController
 *  android.widget.MediaController$MediaPlayerControl
 *  java.io.FileDescriptor
 *  java.io.FileInputStream
 *  java.io.IOException
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 */
package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class VideoPlayer
extends Activity
implements SensorEventListener,
MediaPlayer.OnBufferingUpdateListener,
MediaPlayer.OnCompletionListener,
MediaPlayer.OnPreparedListener,
MediaPlayer.OnVideoSizeChangedListener,
SurfaceHolder.Callback,
MediaController.MediaPlayerControl {
    private int a;
    private int b;
    private int c;
    private int d;
    private MediaPlayer e;
    private MediaController f;
    private SurfaceView g;
    private SurfaceHolder h;
    private String i;
    private int j;
    private int k;
    private boolean l;
    private boolean m = false;
    private boolean n = false;
    private FrameLayout o;
    private int p = 0;
    private boolean q = false;
    private int r = 0;
    private PowerManager.WakeLock s = null;

    private void a() {
        if (this.e != null) {
            this.e.release();
            this.e = null;
        }
        this.c = 0;
        this.d = 0;
        this.n = false;
        this.m = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void b() {
        block3 : {
            block2 : {
                if (this.isPlaying()) break block2;
                this.c();
                if (!this.q) break block3;
            }
            return;
        }
        this.start();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void c() {
        WindowManager windowManager = (WindowManager)this.getSystemService("window");
        this.a = windowManager.getDefaultDisplay().getWidth();
        this.b = windowManager.getDefaultDisplay().getHeight();
        int n2 = this.a;
        int n3 = this.b;
        if (this.k == 1 || this.k == 2) {
            float f2 = (float)this.c / (float)this.d;
            if ((float)this.a / (float)this.b <= f2) {
                n3 = (int)((float)this.a / f2);
            } else {
                n2 = (int)(f2 * (float)this.b);
            }
        } else if (this.k == 0) {
            n2 = this.c;
            n3 = this.d;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(n2, n3, 17);
        this.o.updateViewLayout((View)this.g, (ViewGroup.LayoutParams)layoutParams);
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    public void finish() {
        super.finish();
        this.overridePendingTransition(0, 0);
    }

    public int getBufferPercentage() {
        if (this.l) {
            return this.p;
        }
        return 100;
    }

    public int getCurrentPosition() {
        if (this.e == null) {
            return 0;
        }
        return this.e.getCurrentPosition();
    }

    public int getDuration() {
        if (this.e == null) {
            return 0;
        }
        return this.e.getDuration();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isPlaying() {
        boolean bl = this.n && this.m;
        if (this.e == null) {
            if (!bl) return true;
            return false;
        }
        if (!this.e.isPlaying() && bl) return false;
        return true;
    }

    public void onAccuracyChanged(Sensor sensor, int n2) {
    }

    public void onBufferingUpdate(MediaPlayer mediaPlayer, int n2) {
        this.p = n2;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        this.finish();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        VideoPlayer.super.c();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        int n2 = 4;
        super.onCreate(bundle);
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2.getString("fileName").length() == 0) {
            this.finish();
            return;
        }
        this.setTheme(16973831);
        this.requestWindowFeature(1);
        this.getWindow().setFlags(1024, 1024);
        int n3 = bundle2.getInt("screenOrientation");
        if (bundle2.getBoolean("autorotationOn") || n3 == n2) {
            if (Build.VERSION.SDK_INT >= 9) {
                n2 = 10;
            }
            this.setRequestedOrientation(n2);
        } else {
            this.setRequestedOrientation(n3);
        }
        if (bundle2.getBoolean("wakeLock")) {
            this.s = ((PowerManager)this.getSystemService("power")).newWakeLock(26, "videowakelock");
            this.s.acquire();
        }
        String string2 = bundle2.getString("fileName");
        int n4 = bundle2.getInt("backgroundColor");
        int n5 = bundle2.getInt("controlMode");
        int n6 = bundle2.getInt("scalingMode");
        boolean bl = bundle2.getBoolean("isURL");
        if (string2.length() == 0) {
            this.finish();
            return;
        }
        this.o = new FrameLayout((Context)this);
        this.g = new SurfaceView((Context)this);
        this.h = this.g.getHolder();
        this.h.addCallback((SurfaceHolder.Callback)this);
        this.h.setType(3);
        this.o.setBackgroundColor(n4);
        this.o.addView((View)this.g);
        this.setContentView((View)this.o);
        this.i = string2;
        this.j = n5;
        this.k = n6;
        this.l = bl;
    }

    protected void onDestroy() {
        super.onDestroy();
        this.a();
        if (this.s != null) {
            this.s.release();
        }
        this.s = null;
    }

    public boolean onKeyDown(int n2, KeyEvent keyEvent) {
        if (n2 == 26 || n2 == 82 || n2 == 25 || n2 == 24 || n2 == 0) {
            return super.onKeyDown(n2, keyEvent);
        }
        if (this.f != null && (n2 == 23 || n2 == 19 || n2 == 20 || n2 == 21 || n2 == 22)) {
            return this.f.onKeyDown(n2, keyEvent);
        }
        this.finish();
        return true;
    }

    protected void onPause() {
        super.onPause();
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        this.n = true;
        if (this.n && this.m) {
            VideoPlayer.super.b();
        }
    }

    protected void onResume() {
        super.onResume();
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.f != null) {
            return this.f.onTouchEvent(motionEvent);
        }
        int n2 = 255 & motionEvent.getAction();
        if (this.j == 2 && n2 == 0) {
            this.finish();
        }
        return super.onTouchEvent(motionEvent);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int n2, int n3) {
        block3 : {
            block2 : {
                if (n2 == 0 || n3 == 0) break block2;
                this.m = true;
                this.c = n2;
                this.d = n3;
                if (this.n && this.m) break block3;
            }
            return;
        }
        VideoPlayer.super.b();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onWindowFocusChanged(boolean bl) {
        if (bl) {
            if (this.q) return;
            {
                this.start();
                return;
            }
        } else {
            if (!this.q) {
                this.pause();
                this.q = false;
            }
            if (this.e == null) return;
            {
                this.r = this.e.getCurrentPosition();
                return;
            }
        }
    }

    public void pause() {
        if (this.e == null) {
            return;
        }
        this.e.pause();
        this.q = true;
    }

    public void seekTo(int n2) {
        if (this.e == null) {
            return;
        }
        this.e.seekTo(n2);
    }

    public void start() {
        if (this.e == null) {
            return;
        }
        this.e.start();
        this.q = false;
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int n2, int n3, int n4) {
        this.a = n3;
        this.b = n4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        VideoPlayer.super.a();
        try {
            this.e = new MediaPlayer();
            if (this.l) {
                this.e.setDataSource((Context)this, Uri.parse((String)this.i));
            } else {
                AssetManager assetManager = this.getResources().getAssets();
                try {
                    AssetFileDescriptor assetFileDescriptor = assetManager.openFd(this.i);
                    this.e.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                    assetFileDescriptor.close();
                }
                catch (IOException iOException) {
                    FileInputStream fileInputStream = new FileInputStream(this.i);
                    this.e.setDataSource(fileInputStream.getFD());
                    fileInputStream.close();
                }
            }
            this.e.setDisplay(this.h);
            this.e.setOnBufferingUpdateListener((MediaPlayer.OnBufferingUpdateListener)this);
            this.e.setOnCompletionListener((MediaPlayer.OnCompletionListener)this);
            this.e.setOnPreparedListener((MediaPlayer.OnPreparedListener)this);
            this.e.setOnVideoSizeChangedListener((MediaPlayer.OnVideoSizeChangedListener)this);
            this.e.setAudioStreamType(3);
            this.e.prepare();
            if (this.j == 0 || this.j == 1) {
                this.f = new MediaController((Context)this);
                this.f.setMediaPlayer((MediaController.MediaPlayerControl)this);
                this.f.setAnchorView((View)this.g);
                this.f.setEnabled(true);
                this.f.show();
            }
        }
        catch (Exception exception) {
            this.finish();
        }
        this.seekTo(this.r);
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        VideoPlayer.super.a();
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ContextWrapper
 *  android.graphics.RectF
 *  android.hardware.Camera
 *  android.hardware.Camera$CameraInfo
 *  android.view.InputDevice
 *  android.view.InputDevice$MotionRange
 *  android.view.MotionEvent
 *  android.view.MotionEvent$PointerCoords
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.util.Queue
 *  java.util.concurrent.ConcurrentLinkedQueue
 */
package com.unity3d.player;

import android.app.Activity;
import android.content.ContextWrapper;
import android.graphics.RectF;
import android.hardware.Camera;
import android.view.InputDevice;
import android.view.MotionEvent;
import com.unity3d.player.d;
import com.unity3d.player.e;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class c
implements e {
    protected final ContextWrapper a;
    private Activity b;
    private Queue c = new ConcurrentLinkedQueue();
    private Runnable d;

    /*
     * Enabled aggressive block sorting
     */
    public c(ContextWrapper contextWrapper) {
        this.d = new d((c)this);
        this.a = contextWrapper;
        Activity activity = contextWrapper instanceof Activity ? (Activity)contextWrapper : null;
        this.b = activity;
    }

    private static int a(MotionEvent.PointerCoords[] arrpointerCoords, float[] arrf, int n2) {
        for (int i2 = 0; i2 < arrpointerCoords.length; ++i2) {
            MotionEvent.PointerCoords pointerCoords;
            arrpointerCoords[i2] = pointerCoords = new MotionEvent.PointerCoords();
            int n3 = n2 + 1;
            pointerCoords.orientation = arrf[n2];
            int n4 = n3 + 1;
            pointerCoords.pressure = arrf[n3];
            int n5 = n4 + 1;
            pointerCoords.size = arrf[n4];
            int n6 = n5 + 1;
            pointerCoords.toolMajor = arrf[n5];
            int n7 = n6 + 1;
            pointerCoords.toolMinor = arrf[n6];
            int n8 = n7 + 1;
            pointerCoords.touchMajor = arrf[n7];
            int n9 = n8 + 1;
            pointerCoords.touchMinor = arrf[n8];
            int n10 = n9 + 1;
            pointerCoords.x = arrf[n9];
            n2 = n10 + 1;
            pointerCoords.y = arrf[n10];
        }
        return n2;
    }

    static /* synthetic */ Queue a(c c2) {
        return c2.c;
    }

    @Override
    public final int a() {
        return Camera.getNumberOfCameras();
    }

    @Override
    public final int a(MotionEvent motionEvent) {
        return motionEvent.getSource();
    }

    @Override
    public final void a(long l2, long l3, int n2, int n3, int[] arrn, float[] arrf, int n4, float f2, float f3, int n5, int n6, int n7, int n8, int n9, long[] arrl, float[] arrf2) {
        if (this.b != null) {
            MotionEvent.PointerCoords[] arrpointerCoords = new MotionEvent.PointerCoords[n3];
            c.a(arrpointerCoords, arrf, 0);
            MotionEvent motionEvent = MotionEvent.obtain((long)l2, (long)l3, (int)n2, (int)n3, (int[])arrn, (MotionEvent.PointerCoords[])arrpointerCoords, (int)n4, (float)f2, (float)f3, (int)n5, (int)n6, (int)n7, (int)n8);
            int n10 = 0;
            for (int i2 = 0; i2 < n9; ++i2) {
                MotionEvent.PointerCoords[] arrpointerCoords2 = new MotionEvent.PointerCoords[n3];
                n10 = c.a(arrpointerCoords2, arrf2, n10);
                motionEvent.addBatch(arrl[i2], arrpointerCoords2, n4);
            }
            this.c.add((Object)motionEvent);
            this.b.runOnUiThread(this.d);
        }
    }

    @Override
    public final boolean a(int n2) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo((int)n2, (Camera.CameraInfo)cameraInfo);
        return cameraInfo.facing == 1;
    }

    @Override
    public final RectF b() {
        int[] arrn = InputDevice.getDeviceIds();
        for (int i2 = 0; i2 < arrn.length; ++i2) {
            InputDevice inputDevice = InputDevice.getDevice((int)arrn[i2]);
            if (inputDevice == null || (1048584 & inputDevice.getSources()) != 1048584) continue;
            InputDevice.MotionRange motionRange = inputDevice.getMotionRange(0);
            InputDevice.MotionRange motionRange2 = inputDevice.getMotionRange(1);
            if (motionRange == null || motionRange2 == null) continue;
            return new RectF(0.0f, 0.0f, motionRange.getRange(), motionRange2.getRange());
        }
        return null;
    }
}


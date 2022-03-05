/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.media.AudioRecord
 *  android.media.AudioTrack
 *  android.util.Log
 *  java.lang.IllegalStateException
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Thread
 *  java.nio.Buffer
 *  java.nio.ByteBuffer
 */
package org.fmod;

import android.media.AudioRecord;
import android.media.AudioTrack;
import android.util.Log;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class FMODAudioDevice
implements Runnable {
    private static int a = 2;
    private static int n = 1;
    private static int o = 2;
    private static int p = 3;
    private Thread b = null;
    private AudioTrack c = null;
    private boolean d = false;
    private boolean e = false;
    private ByteBuffer f = null;
    private AudioRecord g = null;
    private boolean h = false;
    private boolean i = false;
    private int j = 0;
    private int k = 0;
    private int l = 0;
    private int m = 0;

    private native int fmodGetInfo(int var1);

    private native int fmodInitJni();

    private native int fmodProcess(ByteBuffer var1);

    private native int fmodProcessMicData(ByteBuffer var1, int var2);

    private void releaseAudioTrack() {
        if (this.c != null) {
            if (this.c.getState() == 1) {
                this.c.stop();
            }
            this.c.release();
            this.c = null;
        }
    }

    private void sleep(int n2) {
        long l2 = n2;
        try {
            Thread.sleep((long)l2);
            return;
        }
        catch (InterruptedException interruptedException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void run() {
        byte[] arrby = null;
        ByteBuffer byteBuffer = null;
        do {
            ByteBuffer byteBuffer2;
            if (!this.e) {
                this.releaseAudioTrack();
                return;
            }
            if (!this.d) {
                int n2 = this.fmodGetInfo(0);
                if (n2 > 0) {
                    this.releaseAudioTrack();
                    int n3 = AudioTrack.getMinBufferSize((int)n2, (int)3, (int)2);
                    int n4 = this.fmodGetInfo(n);
                    int n5 = this.fmodGetInfo(o);
                    if (2 * (n4 * n5) * a > n3) {
                        n3 = 2 * (n5 * n4) * a;
                    }
                    ByteBuffer byteBuffer3 = ByteBuffer.allocateDirect((int)(n4 * 2 * a));
                    byte[] arrby2 = new byte[byteBuffer3.capacity()];
                    this.c = new AudioTrack(3, n2, 3, 2, n3, 1);
                    boolean bl = this.c.getState() == 1;
                    this.d = bl;
                    if (this.d) {
                        this.c.play();
                        arrby = arrby2;
                        byteBuffer = byteBuffer3;
                        continue;
                    }
                    Log.e((String)"FMOD", (String)("AudioTrack failed to initialize (status " + this.c.getState() + ")"));
                    arrby = arrby2;
                    byteBuffer = byteBuffer3;
                    continue;
                }
                this.sleep(1);
                continue;
            }
            if (this.fmodGetInfo(p) == 1) {
                this.fmodProcess(byteBuffer);
                byteBuffer.get(arrby, 0, byteBuffer.capacity());
                this.c.write(arrby, 0, byteBuffer.capacity());
                byteBuffer.position(0);
                byteBuffer2 = byteBuffer;
            } else {
                this.d = false;
                arrby = null;
                byteBuffer2 = null;
            }
            if (this.i) {
                if (this.g == null) {
                    this.g = new AudioRecord(1, this.l, this.m, this.k, this.j);
                }
                if (this.g.getState() == 1) {
                    this.f = ByteBuffer.allocateDirect((int)this.j);
                    this.f.position(0);
                    try {
                        this.g.startRecording();
                    }
                    catch (IllegalStateException illegalStateException) {
                        Log.e((String)"FMOD", (String)("failed to startRecording(): " + illegalStateException.getMessage()));
                    }
                }
                this.i = false;
            }
            if (this.g != null) {
                if (this.h) {
                    try {
                        this.g.stop();
                    }
                    catch (IllegalStateException illegalStateException) {
                        Log.e((String)"FMOD", (String)("failed to stop(): " + illegalStateException.getMessage()));
                    }
                    this.g.release();
                    this.g = null;
                    this.f = null;
                    this.h = false;
                    byteBuffer = byteBuffer2;
                    continue;
                }
                if (this.g.getRecordingState() == 3) {
                    int n6 = this.g.read(this.f, this.f.capacity());
                    this.fmodProcessMicData(this.f, n6);
                    this.f.position(0);
                    byteBuffer = byteBuffer2;
                    continue;
                }
                this.sleep(1);
            }
            byteBuffer = byteBuffer2;
        } while (true);
    }

    public void start() {
        if (this.b != null) {
            this.stop();
        }
        this.b = new Thread((Runnable)this, "FMODAudioDevice");
        this.b.setPriority(10);
        this.e = true;
        this.fmodInitJni();
        this.b.start();
    }

    public int startAudioRecord(int n2, int n3, int n4) {
        this.k = 2;
        this.l = n2;
        this.m = n3;
        this.i = true;
        this.j = AudioRecord.getMinBufferSize((int)n2, (int)n3, (int)this.k) * (n2 / 4410);
        return this.j;
    }

    public void stop() {
        while (this.b != null) {
            this.e = false;
            try {
                this.b.join();
                this.b = null;
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    public void stopAudioRecord() {
        this.h = true;
    }
}


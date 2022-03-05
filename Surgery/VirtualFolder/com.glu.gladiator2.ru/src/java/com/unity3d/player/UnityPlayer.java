/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.res.AssetManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.hardware.Camera
 *  android.hardware.Camera$PreviewCallback
 *  android.hardware.Sensor
 *  android.hardware.SensorEventListener
 *  android.hardware.SensorManager
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.Uri
 *  android.opengl.GLSurfaceView
 *  android.opengl.GLSurfaceView$Renderer
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.Process
 *  android.os.Vibrator
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.provider.Settings$System
 *  android.telephony.TelephonyManager
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.SurfaceHolder
 *  android.view.SurfaceView
 *  android.view.View
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.inputmethod.InputMethodManager
 *  android.webkit.MimeTypeMap
 *  android.widget.FrameLayout
 *  android.widget.ProgressBar
 *  java.io.File
 *  java.io.InputStream
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.System
 *  java.lang.UnsatisfiedLinkError
 *  java.lang.reflect.Method
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.concurrent.Semaphore
 *  java.util.concurrent.TimeUnit
 *  javax.microedition.khronos.egl.EGLConfig
 *  javax.microedition.khronos.opengles.GL10
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserFactory
 */
package com.unity3d.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Process;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.unity3d.player.PlayerPrefs;
import com.unity3d.player.UnityGL;
import com.unity3d.player.UnityPlayerProxyActivity;
import com.unity3d.player.VideoPlayer;
import com.unity3d.player.WWW;
import com.unity3d.player.a.a;
import com.unity3d.player.a.e;
import com.unity3d.player.a.i;
import com.unity3d.player.b;
import com.unity3d.player.c;
import com.unity3d.player.f;
import com.unity3d.player.g;
import com.unity3d.player.h;
import com.unity3d.player.p;
import com.unity3d.player.q;
import com.unity3d.player.r;
import com.unity3d.player.s;
import com.unity3d.player.t;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import org.fmod.FMODAudioDevice;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class UnityPlayer
extends FrameLayout
implements Camera.PreviewCallback,
GLSurfaceView.Renderer {
    private static b R;
    public static Activity currentActivity;
    private static boolean p;
    private static boolean q;
    private final h A;
    private int B = 0;
    private String C = null;
    private NetworkInfo D = null;
    private e E = null;
    private boolean F = false;
    private boolean G = false;
    private boolean H = false;
    private Bundle I = new Bundle();
    private Map J = new HashMap();
    private boolean K = true;
    private boolean L = false;
    private boolean M = false;
    private Runnable N;
    private ProgressBar O;
    private Runnable P;
    private Runnable Q;
    private float S;
    private float T;
    private Method U;
    private LinkedHashMap V;
    private BroadcastReceiver W;
    private boolean Z;
    p a = null;
    private int aa;
    private boolean ab;
    private boolean b = false;
    private boolean c = false;
    private final com.unity3d.player.e d;
    private boolean e = false;
    private Bundle f = null;
    private SharedPreferences g = null;
    private ContextWrapper h;
    private boolean i;
    private UnityGL j;
    private PowerManager.WakeLock k;
    private SensorManager l;
    private WindowManager m;
    private FMODAudioDevice n;
    private Vibrator o = null;
    private boolean r = false;
    private boolean s = true;
    private int t;
    private int u;
    private int v = 0;
    private int w = 0;
    private boolean x = false;
    private int y = -1;
    private boolean z = false;

    static {
        currentActivity = null;
        p = false;
        q = true;
        R = null;
        R = new b();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public UnityPlayer(ContextWrapper contextWrapper) {
        super((Context)contextWrapper);
        this.N = new r((UnityPlayer)this);
        this.O = null;
        this.P = new s((UnityPlayer)this);
        this.Q = new t((UnityPlayer)this);
        this.S = 0.0f;
        this.T = 0.0f;
        this.U = null;
        this.W = new BroadcastReceiver(){

            public void onReceive(Context context, Intent intent) {
                try {
                    UnityPlayer.this.queueEvent(new Runnable(this){
                        private /* synthetic */ 18 a;
                        {
                            this.a = var1;
                        }

                        public final void run() {
                            this.a.UnityPlayer.this.nativeJoystickRemoved();
                        }
                    });
                    return;
                }
                catch (Exception exception) {
                    return;
                }
            }

        };
        this.Z = false;
        this.aa = 1;
        this.ab = true;
        this.h = contextWrapper;
        try {
            this.i = Class.forName((String)"android.app.NativeActivity").isAssignableFrom(this.h.getClass());
        }
        catch (Exception exception) {}
        int n2 = Build.VERSION.SDK_INT;
        c c2 = null;
        if (n2 >= 9) {
            c2 = new c(contextWrapper);
        }
        this.d = c2;
        String string2 = this.h.getPackageName();
        if (contextWrapper instanceof Activity) {
            currentActivity = (Activity)contextWrapper;
            this.f = currentActivity.getIntent().getExtras();
            String[] arrstring = new String[]{"com.unity3d.player.UnityPlayerActivity", "com.unity3d.player.UnityPlayerNativeActivity", currentActivity.getLocalClassName()};
            UnityPlayerProxyActivity.copyPlayerPrefs((Context)contextWrapper, arrstring);
        }
        this.g = contextWrapper.getSharedPreferences(string2, 0);
        UnityPlayer.super.b();
        System.setProperty((String)"apk", (String)this.h.getPackageCodePath());
        System.loadLibrary((String)"mono");
        int n3 = R.a();
        if ((n3 & 2) != 0 && ((n3 & 128) == 0 || (n3 & 8) == 0)) {
            f.Log(6, "CPU features not supported! (no ARMv6+ / VFP)");
        } else {
            p = true;
            try {
                System.loadLibrary((String)"unity");
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                f.Log(5, "Unable to load libraries: " + (Object)((Object)unsatisfiedLinkError));
                p = false;
            }
        }
        UnityPlayer.super.nativeFile(this.h.getPackageCodePath());
        this.A = new h((Context)contextWrapper, (UnityPlayer)this);
        if (Build.VERSION.SDK_INT >= 8) {
            this.e = true;
        }
        try {
            boolean bl = -1 == this.h.checkCallingOrSelfPermission(new String(com.unity3d.player.b.a.b("Y29tLmFuZHJvaWQudmVuZGluZy5DSEVDS19MSUNFTlNF".getBytes())));
            this.H = bl;
            return;
        }
        catch (Exception exception) {
            this.H = true;
            return;
        }
    }

    public static native void UnitySendMessage(String var0, String var1, String var2);

    static /* synthetic */ ProgressBar a(UnityPlayer unityPlayer, ProgressBar progressBar) {
        unityPlayer.O = progressBar;
        return progressBar;
    }

    private String a(String string2, File file) {
        File file2;
        File file3;
        String string3 = Environment.getExternalStorageState();
        if (this.h.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0 && "mounted".equals((Object)string3) && ((file2 = new File((file3 = Environment.getExternalStorageDirectory()).getPath() + "/Android/data/" + this.h.getPackageName() + string2)).exists() || file2.mkdirs())) {
            return file2.getPath();
        }
        return file.getPath();
    }

    private void a(int n2, boolean bl) {
        RectF rectF;
        if (this.i) {
            this.nativeForwardEventsToDalvik(new g((Activity)this.h).a());
        }
        UnityPlayer.super.initJni();
        this.k = ((PowerManager)this.h.getSystemService("power")).newWakeLock(26, "DoNotDimScreen");
        new PlayerPrefs(this.g);
        UnityPlayer.super.nativeInitWWW(WWW.class);
        if (this.j == null) {
            q q2 = new q((UnityPlayer)this, (Context)this.h, n2, bl, false, (View)this){
                private /* synthetic */ View a;
                private /* synthetic */ UnityPlayer b;
                {
                    this.b = unityPlayer;
                    this.a = view;
                    super(context, n, bl, false);
                }

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                protected final void init(int n, boolean bl, int n2, int n3, int n4, int n5) {
                    if (this.b.y == -1) {
                        this.b.y = this.b.I.getInt("default_aa", 0);
                    }
                    if (!this.b.z) {
                        UnityPlayer unityPlayer = this.b;
                        boolean bl2 = this.b.canUse32bitDisplayBuffer() ? this.b.I.getBoolean("32bit_display", false) : false;
                        unityPlayer.z = bl2;
                    }
                    int n6 = this.b.z ? 32 : 16;
                    int n7 = this.b.I.getBoolean("24bit_depth", false) ? 24 : 16;
                    super.init(n, bl, n6, n7, 0, this.b.y);
                }

                @Override
                public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
                    return this.a.onTouchEvent(motionEvent);
                }

                public final boolean onKeyPreIme(int n, KeyEvent keyEvent) {
                    return this.a.onKeyPreIme(n, keyEvent) || super.onKeyPreIme(n, keyEvent);
                }
            };
            if (Build.VERSION.SDK_INT >= 11) {
                q2.setPreserveEGLContextOnPause(true);
            }
            q2.setFocusable(true);
            q2.setFocusableInTouchMode(true);
            this.j = q2;
        }
        this.j.setRenderer((GLSurfaceView.Renderer)this);
        this.l = (SensorManager)this.h.getSystemService("sensor");
        this.m = (WindowManager)this.h.getSystemService("window");
        int n3 = this.getSettings().getInt("splash_mode");
        this.j.queueEvent(new Runnable((UnityPlayer)this, n2, n3){
            private /* synthetic */ int a;
            private /* synthetic */ int b;
            private /* synthetic */ UnityPlayer c;
            {
                this.c = unityPlayer;
                this.a = n;
                this.b = n2;
            }

            public final void run() {
                this.c.nativeInit(this.a, this.b);
            }
        });
        UnityPlayer.super.nativeSetExtras(this.f);
        if (this.d != null && this.i && (rectF = this.d.b()) != null) {
            UnityPlayer.super.nativeEnableTouchpad(rectF.width(), rectF.height());
        }
        this.resume();
        this.windowFocusChanged(true);
    }

    private void a(Runnable runnable) {
        if (this.h instanceof Activity) {
            ((Activity)this.h).runOnUiThread(runnable);
            return;
        }
        f.Log(5, "Not running Unity from an Activity; ignored...");
    }

    private void a(boolean bl) {
        if (!this.Z) {
            return;
        }
        if (bl) {
            this.l.registerListener((SensorEventListener)this.A, this.l.getDefaultSensor(4), this.aa);
            this.l.registerListener((SensorEventListener)this.A, this.l.getDefaultSensor(9), this.aa);
            this.l.registerListener((SensorEventListener)this.A, this.l.getDefaultSensor(10), this.aa);
            this.l.registerListener((SensorEventListener)this.A, this.l.getDefaultSensor(11), this.aa);
            return;
        }
        this.l.unregisterListener((SensorEventListener)this.A, this.l.getDefaultSensor(4));
        this.l.unregisterListener((SensorEventListener)this.A, this.l.getDefaultSensor(9));
        this.l.unregisterListener((SensorEventListener)this.A, this.l.getDefaultSensor(10));
        this.l.unregisterListener((SensorEventListener)this.A, this.l.getDefaultSensor(11));
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean a(int n2, KeyEvent keyEvent) {
        if (!this.r) {
            return true;
        }
        if (this.x) {
            return false;
        }
        if (n2 == 25 || n2 == 24) {
            if (keyEvent.getAction() == 0) {
                return super.onKeyDown(n2, keyEvent);
            }
            return super.onKeyUp(n2, keyEvent);
        }
        int n3 = n2 == 4 && keyEvent.getMetaState() == 2 ? 101 : n2;
        int n4 = keyEvent.getUnicodeChar(keyEvent.getMetaState());
        int n5 = keyEvent.getAction();
        boolean bl = false;
        if (n5 == 0) {
            bl = true;
        }
        this.queueEvent(new Runnable((UnityPlayer)this, n3, n4, bl, keyEvent.getScanCode(), keyEvent.getDeviceId()){
            private /* synthetic */ int a;
            private /* synthetic */ int b;
            private /* synthetic */ boolean c;
            private /* synthetic */ int d;
            private /* synthetic */ int e;
            private /* synthetic */ UnityPlayer f;
            {
                this.f = unityPlayer;
                this.a = n;
                this.b = n2;
                this.c = bl;
                this.d = n3;
                this.e = n4;
            }

            public final void run() {
                this.f.nativeKeyState(this.a, this.b, this.c, this.d, this.e);
            }
        });
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean a(MotionEvent motionEvent) {
        if (this.i) {
            return true;
        }
        int n2 = motionEvent.getPointerCount();
        int n3 = 0;
        do {
            block8 : {
                float f2;
                float f3;
                float f4;
                float f5;
                float f6;
                int n4;
                block10 : {
                    block9 : {
                        float f7;
                        block7 : {
                            if (n3 >= n2) {
                                return true;
                            }
                            if (n3 != 0) break block8;
                            int n5 = motionEvent.getAction();
                            n4 = n3 == (n5 & 255) >> 8 ? n5 & 255 : 2;
                            f2 = motionEvent.getX(n3);
                            f5 = motionEvent.getY(n3);
                            f3 = this.S;
                            f4 = this.T;
                            if (Build.VERSION.SDK_INT < 12) break block9;
                            try {
                                if (this.U == null) {
                                    Class class_ = motionEvent.getClass();
                                    Class[] arrclass = new Class[]{Integer.TYPE, Integer.TYPE};
                                    this.U = class_.getDeclaredMethod("getAxisValue", arrclass);
                                }
                                if (this.U != null) {
                                    float f8;
                                    Method method = this.U;
                                    Object[] arrobject = new Object[]{9, n3};
                                    f7 = f8 = ((Float)method.invoke((Object)motionEvent, arrobject)).floatValue();
                                    break block7;
                                }
                                f7 = 0.0f;
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                        }
                        f6 = f7;
                        break block10;
                    }
                    f6 = 0.0f;
                }
                this.queueEvent(new Runnable((UnityPlayer)this, n4, 0, f2, f5, f3, f4, f6){
                    private /* synthetic */ int a;
                    private /* synthetic */ int b;
                    private /* synthetic */ float c;
                    private /* synthetic */ float d;
                    private /* synthetic */ float e;
                    private /* synthetic */ float f;
                    private /* synthetic */ float g;
                    private /* synthetic */ UnityPlayer h;
                    {
                        this.h = unityPlayer;
                        this.a = n;
                        this.b = 0;
                        this.c = f2;
                        this.d = f3;
                        this.e = f4;
                        this.f = f5;
                        this.g = f6;
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    public final void run() {
                        int n;
                        int n2;
                        switch (this.a) {
                            default: {
                                n2 = 0;
                                n = 0;
                                break;
                            }
                            case 0: {
                                n2 = this.b;
                                n = 0;
                                break;
                            }
                            case 1: {
                                n2 = this.b;
                                n = 1;
                                break;
                            }
                            case 7: {
                                n = 2;
                                n2 = 0;
                                break;
                            }
                            case 2: {
                                n = 3;
                                n2 = 0;
                            }
                        }
                        this.h.nativeQueueGUIEvent(n, this.c, this.d, n2);
                        float f2 = this.h.u;
                        float f3 = this.c;
                        float f4 = f2 - this.d;
                        float f5 = this.c - this.e;
                        float f6 = -(this.d - this.f);
                        float f7 = this.a == 8 ? this.g : 0.0f;
                        this.h.nativeSetMouseDelta(f5, f6, f7);
                        this.h.nativeSetMousePosition(f3, f4);
                        if (this.a == 0) {
                            this.h.nativeSetMouseButton(0, true);
                            return;
                        } else {
                            if (this.a != 1) return;
                            {
                                this.h.nativeSetMouseButton(0, false);
                                return;
                            }
                        }
                    }
                });
                this.S = f2;
                this.T = f5;
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void b() {
        try {
            InputStream inputStream = this.h.getAssets().open("bin/Data/settings.xml");
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(inputStream, null);
            int n2 = xmlPullParser.getEventType();
            String string2 = null;
            String string3 = null;
            while (n2 != 1) {
                if (n2 != 2) {
                    if (n2 == 3) {
                        string3 = null;
                    } else if (n2 == 4 && string2 != null) {
                        if (string3.equalsIgnoreCase("integer")) {
                            this.I.putInt(string2, Integer.parseInt((String)xmlPullParser.getText()));
                        } else if (string3.equalsIgnoreCase("string")) {
                            this.I.putString(string2, xmlPullParser.getText());
                        } else if (string3.equalsIgnoreCase("bool")) {
                            this.I.putBoolean(string2, Boolean.parseBoolean((String)xmlPullParser.getText()));
                        } else if (string3.equalsIgnoreCase("float")) {
                            this.I.putFloat(string2, Float.parseFloat((String)xmlPullParser.getText()));
                        }
                        string2 = null;
                    }
                } else {
                    string3 = xmlPullParser.getName();
                    String string4 = string2;
                    for (int i2 = 0; i2 < xmlPullParser.getAttributeCount(); ++i2) {
                        if (!xmlPullParser.getAttributeName(i2).equalsIgnoreCase("name")) continue;
                        string4 = xmlPullParser.getAttributeValue(i2);
                    }
                    string2 = string4;
                }
                n2 = xmlPullParser.next();
            }
            return;
        }
        catch (Exception exception) {
            f.Log(6, "Unable to locate player settings. " + exception.getLocalizedMessage());
            this.c();
        }
    }

    private void b(boolean bl) {
        if (!this.ab) {
            return;
        }
        if (bl) {
            this.l.registerListener((SensorEventListener)this.A, this.l.getDefaultSensor(2), 1);
            return;
        }
        this.l.unregisterListener((SensorEventListener)this.A, this.l.getDefaultSensor(2));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean b(MotionEvent motionEvent) {
        if (this.i) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 12) return true;
        if (this.U == null) {
            Class class_ = motionEvent.getClass();
            Class[] arrclass = new Class[]{Integer.TYPE, Integer.TYPE};
            this.U = class_.getDeclaredMethod("getAxisValue", arrclass);
        }
        this.queueEvent(new Runnable((UnityPlayer)this, motionEvent){
            private /* synthetic */ MotionEvent a;
            private /* synthetic */ UnityPlayer b;
            {
                this.b = unityPlayer;
                this.a = motionEvent;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public final void run() {
                int n = this.a.getPointerCount();
                int n2 = 0;
                while (n2 < n) {
                    int n3 = this.a.getDeviceId();
                    Integer[] arrinteger = UnityPlayer.b(this.b, n3);
                    if (arrinteger != null) {
                        int n4 = 1 + arrinteger[0];
                        for (int i2 = 1; i2 < arrinteger.length; ++i2) {
                            try {
                                Method method = this.b.U;
                                MotionEvent motionEvent = this.a;
                                Object[] arrobject = new Object[]{arrinteger[i2], n2};
                                float f2 = ((Float)method.invoke((Object)motionEvent, arrobject)).floatValue();
                                this.b.nativeSetJoystickPosition(n4, i2 - 1, f2);
                                continue;
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                    ++n2;
                }
                return;
            }
        });
        return true;
        catch (Exception exception) {
            exception.printStackTrace();
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static /* synthetic */ Integer[] b(UnityPlayer unityPlayer, int n) {
        block5 : {
            Integer[] arrinteger;
            block4 : {
                if (unityPlayer.V == null) {
                    unityPlayer.getConnectedJoysticks();
                }
                if ((arrinteger = (Integer[])unityPlayer.V.get((Object)n)) != null) break block4;
                unityPlayer.getConnectedJoysticks();
                arrinteger = (Integer[])unityPlayer.V.get((Object)n);
                if (arrinteger == null) break block5;
            }
            return arrinteger;
        }
        return null;
    }

    private void c() {
        if (this.h instanceof Activity && !((Activity)this.h).isFinishing()) {
            ((Activity)this.h).finish();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void d() {
        block6 : {
            block5 : {
                if (!this.M || this.K || this.L) break block5;
                this.L = true;
                boolean bl = !this.e ? this.indexOfChild((View)this.j) == -1 : true;
                if (bl && this.j instanceof q) {
                    this.addView((View)this.j);
                }
                if (this.r) {
                    this.j.queueEvent(new Runnable(this){
                        private /* synthetic */ UnityPlayer a;
                        {
                            this.a = unityPlayer;
                        }

                        public final void run() {
                            this.a.nativeResume();
                        }
                    });
                }
                if (q && this.n == null) break block6;
            }
            return;
        }
        this.n = new FMODAudioDevice();
        this.n.start();
    }

    static /* synthetic */ UnityGL i(UnityPlayer unityPlayer) {
        return unityPlayer.j;
    }

    private final native void initJni();

    static /* synthetic */ int k(UnityPlayer unityPlayer) {
        return unityPlayer.nativeActivityIndicatorStyle();
    }

    static /* synthetic */ ProgressBar l(UnityPlayer unityPlayer) {
        return unityPlayer.O;
    }

    private final native int nativeActivityIndicatorStyle();

    private final native void nativeDone();

    private final native void nativeEnableTouchpad(float var1, float var2);

    private final native void nativeFile(String var1);

    private final native void nativeFocusChanged(boolean var1);

    private final native String nativeGetGLContext();

    private final native String nativeGetGLScreen();

    private final native int nativeGetLicensePolicy();

    private final native void nativeInit(int var1, int var2);

    private final native void nativeInitWWW(Class var1);

    private final native boolean nativeIsAutorotationOn();

    private final native void nativeJoyButtonState(int var1, int var2, boolean var3);

    private final native void nativeKeyState(int var1, int var2, boolean var3, int var4, int var5);

    private final native boolean nativePause();

    private final native void nativeQueueGUIEvent(int var1, float var2, float var3, int var4);

    private final native void nativeRecreateGfxState();

    private final native boolean nativeRender();

    private final native boolean nativeRequested32bitDisplayBuffer();

    private final native int nativeRequestedAA();

    private final native void nativeResize(int var1, int var2, int var3, int var4);

    private final native void nativeResume();

    private final native void nativeSetExtras(Bundle var1);

    private final native void nativeSetInputString(String var1);

    private final native void nativeSetJoystickPosition(int var1, int var2, float var3);

    private final native void nativeSetMouseButton(int var1, boolean var2);

    private final native void nativeSetMouseDelta(float var1, float var2, float var3);

    private final native void nativeSetMousePosition(float var1, float var2);

    private final native void nativeSetTouchDeltaY(float var1);

    private final native void nativeSoftInputClosed();

    private final native void nativeTouch(int var1, float var2, float var3, int var4, long var5, int var7);

    private final native void nativeVideoFrameCallback(int var1, byte[] var2, int var3, int var4);

    private final native void unityAndroidInit(String var1, String var2);

    private final native void unityAndroidPrepareGameLoop();

    protected boolean Location_IsServiceEnabledByUser() {
        return this.A.a();
    }

    protected void Location_SetDesiredAccuracy(float f2) {
        this.A.b(f2);
    }

    protected void Location_SetDistanceFilter(float f2) {
        this.A.a(f2);
    }

    protected void Location_StartUpdatingLocation() {
        this.A.b();
    }

    protected void Location_StopUpdatingLocation() {
        this.A.c();
    }

    protected boolean canUse32bitDisplayBuffer() {
        return q.b();
    }

    protected void closeCamera(int n2) {
        for (com.unity3d.player.a a2 : this.J.values()) {
            if (a2.e() != n2) continue;
            a2.f();
            this.J.remove((Object)a2.a());
            break;
        }
    }

    public void configurationChanged(Configuration configuration) {
        if (this.j instanceof SurfaceView) {
            ((SurfaceView)this.j).getHolder().setSizeFromLayout();
        }
        if (this.c && configuration.hardKeyboardHidden == 2) {
            ((InputMethodManager)this.h.getSystemService("input_method")).toggleSoftInput(0, 1);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean dispatchTouchEvent(int n2, int n3, int n4, float f2, float f3, long l2, int n5) {
        if (!this.r) {
            return true;
        }
        if (!this.i) {
            int n6 = n3 & 255;
            if (n2 != (65280 & n3) >> 8) {
                n6 = 2;
            }
            this.queueEvent(new Runnable((UnityPlayer)this, n4, f2, f3, n6, l2, n5){
                private /* synthetic */ int a;
                private /* synthetic */ float b;
                private /* synthetic */ float c;
                private /* synthetic */ int d;
                private /* synthetic */ long e;
                private /* synthetic */ int f;
                private /* synthetic */ UnityPlayer g;
                {
                    this.g = unityPlayer;
                    this.a = n;
                    this.b = f2;
                    this.c = f3;
                    this.d = n2;
                    this.e = l;
                    this.f = n3;
                }

                public final void run() {
                    this.g.nativeTouch(this.a, this.b, this.c, this.d, this.e, this.f);
                }
            });
        }
        return true;
    }

    protected void forwardMotionEventToDalvik(long l2, long l3, int n2, int n3, int[] arrn, float[] arrf, int n4, float f2, float f3, int n5, int n6, int n7, int n8, int n9, long[] arrl, float[] arrf2) {
        if (this.d != null) {
            this.d.a(l2, l3, n2, n3, arrn, arrf, n4, f2, f3, n5, n6, n7, n8, n9, arrl, arrf2);
        }
    }

    protected String getCPUType() {
        return R.b();
    }

    protected String getCacheDir() {
        return this.a("/cache", this.h.getCacheDir());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected int[] getConnectedJoysticks() {
        int n2;
        int[] arrn;
        int n3;
        int n4;
        int[] arrn2;
        Class class_;
        if (Build.VERSION.SDK_INT < 9) {
            return null;
        }
        try {
            class_ = Class.forName((String)"android.view.InputDevice");
            arrn2 = (int[])class_.getDeclaredMethod("getDeviceIds", new Class[0]).invoke((Object)class_, new Object[0]);
            Arrays.sort((int[])arrn2);
            n4 = 0;
            n2 = 0;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        do {
            int n5;
            if (n4 < arrn2.length) {
                Class[] arrclass = new Class[]{Integer.TYPE};
                Method method = class_.getMethod("getDevice", arrclass);
                Object[] arrobject = new Object[]{arrn2[n4]};
                Object object = method.invoke((Object)class_, arrobject);
                if (object != null) {
                    if ((16777232 & (Integer)object.getClass().getMethod("getSources", new Class[0]).invoke(object, new Object[0])) == 16777232) {
                        n5 = n2 + 1;
                    } else {
                        arrn2[n4] = -1;
                        n5 = n2;
                    }
                } else {
                    arrn2[n4] = -1;
                    n5 = n2;
                }
            } else {
                arrn = new int[n2];
                n3 = 0;
                break;
            }
            ++n4;
            n2 = n5;
        } while (true);
        for (int i2 = 0; i2 < arrn2.length; ++i2) {
            int n6;
            if (arrn2[i2] != -1) {
                n6 = n3 + 1;
                arrn[n3] = arrn2[i2];
            } else {
                n6 = n3;
            }
            n3 = n6;
        }
        this.V = new LinkedHashMap();
        int n7 = arrn.length;
        int n8 = 0;
        int n9 = 0;
        while (n8 < n7) {
            int n10 = arrn[n8];
            int[] arrn3 = this.getJoystickAxes(n10);
            if (arrn3 != null) {
                Integer[] arrinteger = new Integer[1 + arrn3.length];
                int n11 = n9 + 1;
                arrinteger[0] = n9;
                for (int i3 = 0; i3 < arrn3.length; ++i3) {
                    arrinteger[i3 + 1] = arrn3[i3];
                }
                this.V.put((Object)n10, (Object)arrinteger);
                n9 = n11;
            }
            ++n8;
        }
        return arrn;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int getDeviceOrientation() {
        int n2 = 9;
        int n3 = 1;
        int n4 = this.m.getDefaultDisplay().getOrientation();
        int n5 = this.getResources().getConfiguration().orientation == 2 ? n3 : 0;
        int n6 = Build.VERSION.SDK_INT >= n2 ? 8 : 0;
        if (Build.VERSION.SDK_INT < n2) {
            n2 = n3;
        }
        if (n4 == 0) {
            if (n5 == 0) return n3;
            return 0;
        }
        if (n4 == n3) {
            if (n5 == 0) return n2;
            return 0;
        }
        if (n4 == 2) {
            if (n5 == 0) return n2;
            return n6;
        }
        if (n4 != 3) return n3;
        if (n5 == 0) return n3;
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected String getDeviceUniqueIdentifier() {
        String string2;
        if (this.C == null) {
            this.C = ((TelephonyManager)this.h.getSystemService("phone")).getDeviceId();
        }
        if ((string2 = this.C) == null) return Settings.Secure.getString((ContentResolver)this.h.getContentResolver(), (String)"android_id");
        try {
            int n2 = string2.length();
            if (n2 == 0) return Settings.Secure.getString((ContentResolver)this.h.getContentResolver(), (String)"android_id");
            return string2;
        }
        catch (Exception exception) {
            f.Log(5, "android.permission.READ_PHONE_STATE not available?");
        }
        return Settings.Secure.getString((ContentResolver)this.h.getContentResolver(), (String)"android_id");
    }

    protected String getFilesDir() {
        return this.a("/files", this.h.getFilesDir());
    }

    protected int getGyroUpdateDelay() {
        return this.aa;
    }

    protected int getInternetReachability() {
        NetworkInfo networkInfo;
        block6 : {
            block5 : {
                try {
                    if (this.D == null) {
                        this.D = ((ConnectivityManager)this.h.getSystemService("connectivity")).getActiveNetworkInfo();
                    }
                    if ((networkInfo = this.D) != null) break block5;
                    return 0;
                }
                catch (Exception exception) {
                    f.Log(5, "android.permission.ACCESS_NETWORK_STATE not available?");
                    return 0;
                }
            }
            if (networkInfo.isConnected()) break block6;
            return 0;
        }
        int n2 = networkInfo.getType();
        return n2 + 1;
    }

    protected boolean getIsGyroAvailable() {
        return this.h.getPackageManager().hasSystemFeature("android.hardware.sensor.gyroscope");
    }

    protected boolean getIsGyroEnabled() {
        return this.Z;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected int[] getJoystickAxes(int n2) {
        int n3 = 0;
        if (Build.VERSION.SDK_INT < 9) {
            return null;
        }
        try {
            Class class_ = Class.forName((String)"android.view.InputDevice");
            Class[] arrclass = new Class[]{Integer.TYPE};
            Method method = class_.getMethod("getDevice", arrclass);
            Object[] arrobject = new Object[]{n2};
            Object object = method.invoke((Object)class_, arrobject);
            if (object == null) return null;
            List list = (List)object.getClass().getMethod("getMotionRanges", new Class[0]).invoke(object, new Object[0]);
            int[] arrn = new int[list.size()];
            Iterator iterator = list.iterator();
            int n4 = 0;
            while (iterator.hasNext()) {
                Object object2 = iterator.next();
                if ((16777232 & (Integer)object2.getClass().getMethod("getSource", new Class[0]).invoke(object2, new Object[0])) != 16777232) continue;
                int n5 = n4 + 1;
                arrn[n4] = (Integer)object2.getClass().getMethod("getAxis", new Class[0]).invoke(object2, new Object[0]);
                n4 = n5;
            }
            int[] arrn2 = new int[n4];
            while (n3 < n4) {
                arrn2[n3] = arrn[n3];
                ++n3;
            }
            Arrays.sort((int[])arrn2);
            return arrn2;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected String getJoystickName(int n2) {
        Object object;
        if (Build.VERSION.SDK_INT < 9) {
            return null;
        }
        try {
            Class class_ = Class.forName((String)"android.view.InputDevice");
            Class[] arrclass = new Class[]{Integer.TYPE};
            Method method = class_.getMethod("getDevice", arrclass);
            Object[] arrobject = new Object[]{n2};
            object = method.invoke((Object)class_, arrobject);
            if (object == null) return null;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return (String)object.getClass().getMethod("getName", new Class[0]).invoke(object, new Object[0]);
    }

    protected int getNumCameras() {
        if (this.d != null) {
            return this.d.a();
        }
        return 1;
    }

    protected int getOrientation() {
        if (!(this.h instanceof Activity)) {
            return 1;
        }
        return ((Activity)this.h).getRequestedOrientation();
    }

    protected String getPackageName() {
        return this.h.getPackageName();
    }

    protected float getScreenDPI() {
        if (!(this.h instanceof Activity)) {
            return 0.0f;
        }
        Activity activity = (Activity)this.h;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return 0.5f * (displayMetrics.xdpi + displayMetrics.ydpi);
    }

    protected int getScreenTimeout() {
        return Settings.System.getInt((ContentResolver)this.h.getContentResolver(), (String)"screen_off_timeout", (int)15000) / 1000;
    }

    public Bundle getSettings() {
        return this.I;
    }

    protected int getTotalMemory() {
        return R.c();
    }

    public View getView() {
        return this;
    }

    protected boolean hasWakeLock() {
        return this.k.isHeld();
    }

    protected void hideSoftInput() {
        this.a(new Runnable(this){
            private /* synthetic */ UnityPlayer a;
            {
                this.a = unityPlayer;
            }

            /*
             * Enabled aggressive block sorting
             */
            public final void run() {
                if (this.a.c) {
                    ((InputMethodManager)this.a.h.getSystemService("input_method")).toggleSoftInput(1, 0);
                    this.a.c = false;
                    return;
                } else {
                    if (this.a.a == null) return;
                    {
                        this.a.a.dismiss();
                        this.a.a = null;
                        return;
                    }
                }
            }
        });
    }

    public void init(int n2, boolean bl) {
        if (!p) {
            AlertDialog alertDialog = new AlertDialog.Builder((Context)this.h).setTitle((CharSequence)"Failure to initialize!").setPositiveButton((CharSequence)"OK", new DialogInterface.OnClickListener((UnityPlayer)this){
                private /* synthetic */ UnityPlayer a;
                {
                    this.a = unityPlayer;
                }

                public final void onClick(DialogInterface dialogInterface, int n) {
                    this.a.c();
                }
            }).setMessage((CharSequence)"Your hardware does not support this application, sorry!").create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            return;
        }
        if (Build.MANUFACTURER.equalsIgnoreCase("samsung") && Build.VERSION.SDK_INT < 8 && (16 & R.a()) != 0) {
            p = false;
            AlertDialog alertDialog = new AlertDialog.Builder((Context)this.h).setTitle((CharSequence)"Old Android OS detected!").setPositiveButton((CharSequence)"OK", new DialogInterface.OnClickListener((UnityPlayer)this, n2, bl){
                private /* synthetic */ int a;
                private /* synthetic */ boolean b;
                private /* synthetic */ UnityPlayer c;
                {
                    this.c = unityPlayer;
                    this.a = n;
                    this.b = bl;
                }

                public final void onClick(DialogInterface dialogInterface, int n) {
                    p = true;
                    this.c.a(this.a, this.b);
                }
            }).setMessage((CharSequence)"This application requires at least Android OS version 2.2 on Samsung devices. Your device seems to be running an older OS version.\nPlease contact your carrier or the hardware vendor and ask them how to install a more recent version. It is a simple process that your provider's customer service can help you with.").create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            return;
        }
        UnityPlayer.super.a(n2, bl);
    }

    protected int[] initCamera(int n2, int n3, int n4, int n5) {
        com.unity3d.player.a a2;
        try {
            a2 = new com.unity3d.player.a(n2, n3, n4, n5);
        }
        catch (Exception exception) {
            f.Log(6, "Camera failed to open: " + exception.getLocalizedMessage());
            return null;
        }
        this.J.put((Object)a2.a(), (Object)a2);
        a2.a((Camera.PreviewCallback)this);
        a2.a().startPreview();
        int[] arrn = new int[]{a2.c(), a2.d()};
        return arrn;
    }

    protected boolean isCameraFrontFacing(int n2) {
        if (this.d != null) {
            return this.d.a(n2);
        }
        return false;
    }

    protected boolean isCompassAvailable() {
        return this.h.getPackageManager().hasSystemFeature("android.hardware.sensor.compass");
    }

    protected boolean isCompassEnabled() {
        return this.ab;
    }

    protected boolean loadLibrary(String string2) {
        try {
            System.loadLibrary((String)string2);
            return true;
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            f.Log(6, "Unable to find " + string2);
            return false;
        }
        catch (Exception exception) {
            f.Log(6, "Unknown error " + (Object)((Object)exception));
            return false;
        }
    }

    final native void nativeAttitude(float var1, float var2, float var3, float var4, long var5);

    final native void nativeCompass(float var1, float var2, float var3, float var4, float var5, double var6);

    final native void nativeDeviceOrientation(int var1);

    final native void nativeForwardEventsToDalvik(boolean var1);

    final native void nativeGravity(float var1, float var2, float var3, long var4);

    final native void nativeGyro(float var1, float var2, float var3, long var4);

    final native void nativeJoystickRemoved();

    final native void nativeLinearAcc(float var1, float var2, float var3, long var4);

    final native void nativeSensor(float var1, float var2, float var3, long var4);

    protected native void nativeSetLocation(float var1, float var2, float var3, float var4, double var5);

    protected native void nativeSetLocationStatus(int var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onDrawFrame(GL10 gL10) {
        block13 : {
            block12 : {
                String string2;
                boolean bl = true;
                if (this.x) break block12;
                if (!this.H && this.E == null && (string2 = UnityPlayer.super.nativeGetGLContext()) != null) {
                    String string3 = new String("android.opengl.GLSurfaceView$GLWrapper");
                    i i2 = new i((Context)this.h, new a(string3.getBytes(), this.h.getPackageName(), UnityPlayer.super.nativeGetGLScreen()));
                    try {
                        this.E = new e((Context)this.h, i2, string2);
                        this.E.a(new com.unity3d.player.a.f((UnityPlayer)this){
                            private /* synthetic */ UnityPlayer a;
                            {
                                this.a = unityPlayer;
                            }

                            @Override
                            public final void a() {
                                this.a.F = true;
                                this.a.G = true;
                            }

                            @Override
                            public final void b() {
                                this.a.F = false;
                                this.a.G = true;
                            }
                        });
                    }
                    catch (Exception exception) {
                        this.H = bl;
                    }
                }
                if (!UnityPlayer.super.nativeRender()) {
                    this.x = bl;
                    UnityPlayer.super.c();
                    return;
                }
                this.B = 1 + this.B;
                if (!this.r) {
                    if (this.s) {
                        this.s = false;
                        return;
                    }
                    UnityPlayer.super.unityAndroidInit("assets/bin/", this.h.getApplicationInfo().dataDir + "/lib");
                    this.r = bl;
                    UnityPlayer.super.unityAndroidPrepareGameLoop();
                    UnityPlayer.super.nativeResize(this.t, this.u, this.t, this.u);
                    UnityPlayer.super.nativeResume();
                    this.windowFocusChanged(bl);
                }
                if (!(this.j instanceof q)) break block12;
                boolean bl2 = this.canUse32bitDisplayBuffer() ? UnityPlayer.super.nativeRequested32bitDisplayBuffer() : false;
                int n2 = UnityPlayer.super.nativeRequestedAA();
                int n3 = this.y;
                boolean bl3 = false;
                if (n3 != n2) {
                    this.y = n2;
                    ((q)this.j).a(this.y);
                    bl3 = bl;
                }
                if (this.z != bl2) {
                    this.z = bl2;
                    ((q)this.j).a(this.z);
                } else {
                    bl = bl3;
                }
                if (bl) break block13;
            }
            return;
        }
        UnityPlayer.super.a(this.N);
    }

    public boolean onKeyDown(int n2, KeyEvent keyEvent) {
        return UnityPlayer.super.a(n2, keyEvent);
    }

    public boolean onKeyPreIme(int n2, KeyEvent keyEvent) {
        if (this.c && n2 == 4) {
            return UnityPlayer.super.a(n2, keyEvent);
        }
        return super.onKeyPreIme(n2, keyEvent);
    }

    public boolean onKeyUp(int n2, KeyEvent keyEvent) {
        return UnityPlayer.super.a(n2, keyEvent);
    }

    protected boolean onNativeKey(long l2, long l3, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, boolean bl) {
        return UnityPlayer.super.a(n3, new KeyEvent(l2, l3, n2, n3, n4, n5, n6, n7, n8));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onPreviewFrame(byte[] arrby, Camera camera) {
        com.unity3d.player.a a2;
        block3 : {
            block2 : {
                if (this.x || (a2 = (com.unity3d.player.a)this.J.get((Object)camera)) == null) break block2;
                int n2 = a2.c();
                int n3 = a2.d();
                this.queueEvent(new Runnable((UnityPlayer)this, a2.e(), arrby, n2, n3){
                    private /* synthetic */ int a;
                    private /* synthetic */ byte[] b;
                    private /* synthetic */ int c;
                    private /* synthetic */ int d;
                    private /* synthetic */ UnityPlayer e;
                    {
                        this.e = unityPlayer;
                        this.a = n;
                        this.b = arrby;
                        this.c = n2;
                        this.d = n3;
                    }

                    public final void run() {
                        this.e.nativeVideoFrameCallback(this.a, this.b, this.c, this.d);
                    }
                });
                if (Build.VERSION.SDK_INT >= 8) break block3;
            }
            return;
        }
        camera.addCallbackBuffer(a2.b());
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onSurfaceChanged(GL10 gL10, int n2, int n3) {
        int n4;
        int n5;
        if (!(!(this.j instanceof SurfaceView) || this.v == 0 && this.w == 0 || this.v == n2 && this.w == n3)) {
            this.setScreenSize(this.v, this.w);
        }
        if (this.j instanceof View) {
            n5 = ((View)this.j).getWidth();
            n4 = ((View)this.j).getHeight();
        } else {
            n4 = n3;
            n5 = n2;
        }
        this.t = n2;
        this.u = n3;
        UnityPlayer.super.nativeResize(n2, n3, n5, n4);
        if (this.h instanceof Activity) {
            boolean bl = this.getSettings().getBoolean("hide_status_bar", true);
            float f2 = 0.0f;
            if (!bl) {
                Activity activity = (Activity)this.h;
                Rect rect = new Rect();
                activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                f2 = rect.top;
            }
            UnityPlayer.super.nativeSetTouchDeltaY(f2);
        }
    }

    public void onSurfaceCreated(GL10 gL10, EGLConfig eGLConfig) {
        UnityPlayer.super.nativeRecreateGfxState();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.r) {
            return true;
        }
        if (this.i) {
            return true;
        }
        int n2 = this.d != null ? this.d.a(motionEvent) : 4098;
        if (n2 == 8194) {
            return UnityPlayer.super.a(motionEvent);
        }
        if (n2 == 16777232) {
            return UnityPlayer.super.b(motionEvent);
        }
        int n3 = motionEvent.getPointerCount();
        int n4 = 0;
        while (n4 < n3) {
            int n5 = motionEvent.getPointerId(n4);
            int n6 = motionEvent.getHistorySize();
            for (int i2 = 0; i2 < n6; ++i2) {
                this.dispatchTouchEvent(n4, 2, n5, motionEvent.getHistoricalX(n4, i2), motionEvent.getHistoricalY(n4, i2), motionEvent.getHistoricalEventTime(i2), n2);
            }
            this.dispatchTouchEvent(n4, motionEvent.getAction(), n5, motionEvent.getX(n4), motionEvent.getY(n4), motionEvent.getEventTime(), n2);
            ++n4;
        }
        return true;
    }

    protected void openURL(String string2) {
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri uri = Uri.parse((String)string2);
        intent.setData(uri);
        if (uri.isRelative()) {
            intent.setDataAndType(Uri.fromFile((File)new File(string2)), MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl((String)string2)));
        }
        this.h.startActivity(intent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void pause() {
        if (this.K || !p) {
            return;
        }
        this.K = true;
        this.L = false;
        if (!this.x) {
            Iterator iterator = this.J.entrySet().iterator();
            while (iterator.hasNext()) {
                ((com.unity3d.player.a)((Map.Entry)iterator.next()).getValue()).f();
            }
        }
        if (this.n != null) {
            this.n.stop();
            this.n = null;
        }
        if (this.r) {
            Semaphore semaphore;
            semaphore = new Semaphore(0);
            if (this.x) {
                this.j.queueEvent(new Runnable(this, semaphore){
                    private /* synthetic */ Semaphore a;
                    private /* synthetic */ UnityPlayer b;
                    {
                        this.b = unityPlayer;
                        this.a = semaphore;
                    }

                    public final void run() {
                        this.b.nativeDone();
                        this.a.release();
                    }
                });
            } else {
                this.j.queueEvent(new Runnable(this, semaphore){
                    private /* synthetic */ Semaphore a;
                    private /* synthetic */ UnityPlayer b;
                    {
                        this.b = unityPlayer;
                        this.a = semaphore;
                    }

                    public final void run() {
                        if (this.b.nativePause()) {
                            this.b.nativeDone();
                            this.a.release(2);
                            return;
                        }
                        this.a.release();
                    }
                });
            }
            try {
                semaphore.tryAcquire(10L, TimeUnit.SECONDS);
            }
            catch (InterruptedException interruptedException) {}
            if (semaphore.drainPermits() > 0) {
                this.quit();
            }
        }
        this.h.unregisterReceiver(this.W);
        if (this.e && this.j instanceof q) {
            this.removeView((View)this.j);
        }
        this.j.onPause();
        this.setWakeLock(false);
        this.a(false);
        this.b(false);
        this.l.unregisterListener((SensorEventListener)this.A);
        this.A.d();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void queueEvent(Runnable runnable) {
        if (!this.r || this.x) {
            return;
        }
        this.j.queueEvent(new Runnable((UnityPlayer)this, runnable){
            private /* synthetic */ Runnable a;
            private /* synthetic */ UnityPlayer b;
            {
                this.b = unityPlayer;
                this.a = runnable;
            }

            public final void run() {
                if (!this.b.x) {
                    this.a.run();
                }
            }
        });
    }

    public void quit() {
        if (p) {
            this.removeAllViews();
            this.j.a();
        }
        if (this.E != null) {
            this.E.a();
        }
        this.E = null;
        Process.killProcess((int)Process.myPid());
    }

    protected void reportSoftInputStr(String string2, int n2) {
        if (n2 == 1) {
            this.hideSoftInput();
        }
        this.queueEvent(new Runnable((UnityPlayer)this, string2, n2){
            private /* synthetic */ String a;
            private /* synthetic */ int b;
            private /* synthetic */ UnityPlayer c;
            {
                this.c = unityPlayer;
                this.a = string2;
                this.b = n;
            }

            public final void run() {
                if (this.a != null) {
                    this.c.nativeSetInputString(this.a);
                }
                if (this.b == 1) {
                    this.c.nativeSoftInputClosed();
                }
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    public void resume() {
        if (!this.K || !p) {
            return;
        }
        this.K = false;
        Iterator iterator = this.J.entrySet().iterator();
        HashMap hashMap = new HashMap();
        do {
            if (!iterator.hasNext()) {
                this.J = hashMap;
                this.j.onResume();
                this.h.registerReceiver(this.W, new IntentFilter("android.hardware.usb.action.USB_DEVICE_DETACHED"));
                this.l.registerListener((SensorEventListener)this.A, this.l.getDefaultSensor(1), 1);
                this.a(true);
                this.b(true);
                this.d();
                this.A.e();
                this.C = null;
                this.D = null;
                return;
            }
            com.unity3d.player.a a2 = (com.unity3d.player.a)((Map.Entry)iterator.next()).getValue();
            a2.b(this);
            hashMap.put((Object)a2.a(), (Object)a2);
        } while (true);
    }

    protected void setCompassEnabled(boolean bl) {
        if (this.ab == bl) {
            return;
        }
        if (bl) {
            boolean bl2 = false;
            if (bl) {
                boolean bl3 = this.isCompassAvailable();
                bl2 = false;
                if (bl3) {
                    bl2 = true;
                }
            }
            this.ab = bl2;
            UnityPlayer.super.b(true);
            return;
        }
        UnityPlayer.super.b(false);
        this.ab = bl;
        this.queueEvent(new Runnable((UnityPlayer)this){
            private /* synthetic */ UnityPlayer a;
            {
                this.a = unityPlayer;
            }

            public final void run() {
                this.a.nativeCompass(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0);
            }
        });
    }

    protected void setGyroEnabled(boolean bl) {
        if (this.Z == bl) {
            return;
        }
        if (bl) {
            boolean bl2 = false;
            if (bl) {
                boolean bl3 = this.getIsGyroAvailable();
                bl2 = false;
                if (bl3) {
                    bl2 = true;
                }
            }
            this.Z = bl2;
            UnityPlayer.super.a(true);
            return;
        }
        UnityPlayer.super.a(false);
        this.Z = bl;
        this.queueEvent(new Runnable((UnityPlayer)this){
            private /* synthetic */ UnityPlayer a;
            {
                this.a = unityPlayer;
            }

            public final void run() {
                this.a.nativeGyro(0.0f, 0.0f, 0.0f, -1L);
            }
        });
    }

    protected void setGyroUpdateDelay(int n2) {
        this.aa = n2;
    }

    protected void setHideInputField(boolean bl) {
        this.b = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void setOrientation(int n2) {
        Activity activity;
        if (!(this.h instanceof Activity) || n2 == (activity = (Activity)this.h).getRequestedOrientation() || Build.VERSION.SDK_INT < 9 && (n2 == 9 || n2 == 8)) {
            return;
        }
        activity.setRequestedOrientation(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void setScreenSize(int n2, int n3) {
        if (!(this.j instanceof SurfaceView)) {
            f.Log(5, "setScreenSize: Unable to retrieve surface holder");
            return;
        }
        SurfaceView surfaceView = (SurfaceView)this.j;
        surfaceView.getHolder().getSurfaceFrame();
        boolean bl = surfaceView.getWidth() == n2 && surfaceView.getHeight() == n3 || n2 == -1 && n3 == -1;
        if (bl) {
            this.v = 0;
            this.w = 0;
        } else {
            this.v = n2;
            this.w = n3;
        }
        UnityPlayer.super.a(new Runnable(bl, surfaceView, n2, n3){
            private /* synthetic */ boolean a;
            private /* synthetic */ SurfaceView b;
            private /* synthetic */ int c;
            private /* synthetic */ int d;
            {
                this.a = bl;
                this.b = surfaceView;
                this.c = n;
                this.d = n2;
            }

            /*
             * Enabled aggressive block sorting
             */
            public final void run() {
                if (this.a) {
                    this.b.getHolder().setSizeFromLayout();
                } else {
                    this.b.getHolder().setFixedSize(this.c, this.d);
                }
                this.b.invalidate();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void setWakeLock(boolean bl) {
        block5 : {
            block4 : {
                if (bl == this.hasWakeLock()) break block4;
                if (bl) {
                    this.k.acquire();
                    return;
                }
                if (!bl) break block5;
            }
            return;
        }
        this.k.release();
    }

    protected boolean showBuildSetup() {
        return this.F;
    }

    protected boolean showRuntimeSetup() {
        return this.G;
    }

    protected void showSoftInput(String string2, int n2, boolean bl, boolean bl2, boolean bl3, boolean bl4, String string3) {
        this.a(new Runnable(this, this, string2, n2, bl, bl2, bl3, bl4, string3){
            private /* synthetic */ UnityPlayer a;
            private /* synthetic */ String b;
            private /* synthetic */ int c;
            private /* synthetic */ boolean d;
            private /* synthetic */ boolean e;
            private /* synthetic */ boolean f;
            private /* synthetic */ boolean g;
            private /* synthetic */ String h;
            private /* synthetic */ UnityPlayer i;
            {
                this.i = unityPlayer;
                this.a = unityPlayer2;
                this.b = string2;
                this.c = n;
                this.d = bl;
                this.e = bl2;
                this.f = bl3;
                this.g = bl4;
                this.h = string3;
            }

            public final void run() {
                if (this.i.b) {
                    ((InputMethodManager)this.a.h.getSystemService("input_method")).toggleSoftInput(0, 1);
                    this.i.c = true;
                    return;
                }
                UnityPlayer unityPlayer = this.i;
                ContextWrapper contextWrapper = this.i.h;
                UnityPlayer unityPlayer2 = this.a;
                String string2 = this.b;
                int n = this.c;
                boolean bl = this.d;
                boolean bl2 = this.e;
                boolean bl3 = this.f;
                unityPlayer.a = new p((Context)contextWrapper, unityPlayer2, string2, n, bl, bl2, bl3, this.h);
                this.i.a.show();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void showVideoPlayer(String string2, int n2, int n3, int n4, boolean bl) {
        Intent intent = new Intent((Context)this.h, VideoPlayer.class);
        intent.putExtra("fileName", string2);
        intent.putExtra("backgroundColor", n2);
        intent.putExtra("controlMode", n3);
        intent.putExtra("scalingMode", n4);
        intent.putExtra("isURL", bl);
        if (this.h instanceof Activity) {
            intent.putExtra("screenOrientation", ((Activity)this.h).getRequestedOrientation());
            intent.putExtra("autorotationOn", this.nativeIsAutorotationOn());
        } else {
            intent.putExtra("screenOrientation", 1);
            intent.putExtra("autorotationOn", false);
        }
        intent.putExtra("wakeLock", this.hasWakeLock());
        intent.addFlags(65536);
        this.a(new Runnable(this, intent){
            private /* synthetic */ Intent a;
            private /* synthetic */ UnityPlayer b;
            {
                this.b = unityPlayer;
                this.a = intent;
            }

            public final void run() {
                this.b.h.startActivity(this.a);
            }
        });
    }

    protected void startActivityIndicator() {
        this.a(this.P);
    }

    protected void stopActivityIndicator() {
        this.a(this.Q);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void vibrate(int var1) {
        if (this.o == null) {
            this.o = (Vibrator)this.h.getSystemService("vibrator");
        }
        if (var1 != 0) ** GOTO lbl7
        try {
            this.o.cancel();
            return;
lbl7: // 1 sources:
            this.o.vibrate((long)var1);
            return;
        }
        catch (Exception var2_2) {
            f.Log(5, "android.permission.VIBRATE not available?");
            return;
        }
    }

    public void windowFocusChanged(boolean bl) {
        this.M = bl;
        if (this.r) {
            this.j.queueEvent(new Runnable((UnityPlayer)this){
                private /* synthetic */ UnityPlayer a;
                {
                    this.a = unityPlayer;
                }

                public final void run() {
                    this.a.nativeFocusChanged(this.a.M);
                }
            });
        }
        UnityPlayer.super.d();
    }

}


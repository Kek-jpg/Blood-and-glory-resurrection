/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.opengl.GLSurfaceView
 *  android.opengl.GLSurfaceView$EGLConfigChooser
 *  android.opengl.GLSurfaceView$EGLContextFactory
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.SurfaceHolder
 *  java.lang.AssertionError
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  javax.microedition.khronos.egl.EGL10
 *  javax.microedition.khronos.egl.EGLConfig
 *  javax.microedition.khronos.egl.EGLContext
 *  javax.microedition.khronos.egl.EGLDisplay
 */
package com.unity3d.player;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.unity3d.player.UnityGL;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

class q
extends GLSurfaceView
implements UnityGL {
    private static boolean a = false;
    private static boolean b;
    private a c = null;

    public q(Context context, int n, boolean bl, boolean bl2) {
        super(context);
        a = bl2;
        this.init(n, bl, 16, 16, 0, 0);
    }

    static /* synthetic */ void a(String string2) {
        if (!a) {
            Log.d((String)"Unity", (String)string2);
        }
    }

    static /* synthetic */ void a(String string2, EGL10 eGL10) {
        int n;
        for (int i2 = 0; i2 < 2 && (n = eGL10.eglGetError()) != 12288; ++i2) {
            Object[] arrobject = new Object[]{string2, n};
            Log.e((String)"Unity", (String)String.format((String)"%s: EGL error: 0x%x", (Object[])arrobject));
        }
    }

    public static boolean b() {
        return Build.VERSION.SDK_INT >= 9;
    }

    @Override
    public final void a() {
        super.onDetachedFromWindow();
    }

    public final void a(int n) {
        if (this.c != null) {
            this.c.e = n;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void a(boolean bl) {
        if (this.c == null) {
            return;
        }
        if (bl) {
            if (Build.VERSION.SDK_INT < 9) return;
            boolean bl2 = true;
            if (!bl2) return;
        }
        if (bl) {
            this.c.a = 8;
            this.c.b = 8;
            this.c.c = 8;
            this.c.d = 8;
            return;
        }
        this.c.a = 5;
        this.c.b = 6;
        this.c.c = 5;
        this.c.d = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void init(int n, boolean bl, int n2, int n3, int n4, int n5) {
        boolean bl2 = true;
        boolean bl3 = n == 2 ? bl2 : false;
        b = bl3;
        if (Build.VERSION.SDK_INT < 9) {
            bl2 = false;
        }
        if (!bl2) {
            n2 = 16;
        }
        int n6 = bl ? -3 : -1;
        if (n2 == 32 && !bl) {
            n6 = 2;
        }
        this.getHolder().setFormat(n6);
        this.setEGLContextFactory(new GLSurfaceView.EGLContextFactory(){
            {
                this(0);
            }

            /*
             * Enabled aggressive block sorting
             */
            public final EGLContext createContext(EGL10 eGL10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
                int n = a.a(eGL10, eGLDisplay, eGLConfig, 12320, 0);
                int n2 = a.a(eGL10, eGLDisplay, eGLConfig, 12324, 0);
                int n3 = a.a(eGL10, eGLDisplay, eGLConfig, 12323, 0);
                int n4 = a.a(eGL10, eGLDisplay, eGLConfig, 12322, 0);
                int n5 = a.a(eGL10, eGLDisplay, eGLConfig, 12321, 0);
                int n6 = a.a(eGL10, eGLDisplay, eGLConfig, 12325, 0);
                int n7 = a.a(eGL10, eGLDisplay, eGLConfig, 12326, 0);
                int n8 = a.a(eGL10, eGLDisplay, eGLConfig, 12337, 0);
                int n9 = a.a(eGL10, eGLDisplay, eGLConfig, 12513, 0);
                StringBuilder stringBuilder = new StringBuilder();
                String string2 = n5 == 0 ? "RGB" : "RGBA";
                StringBuilder stringBuilder2 = stringBuilder.append(string2).append(n).append(" ").append(Integer.toString((int)n2)).append(Integer.toString((int)n3)).append(Integer.toString((int)n4));
                String string3 = n5 == 0 ? "" : Integer.toString((int)n5);
                StringBuilder stringBuilder3 = stringBuilder2.append(string3).append(" ").append(Integer.toString((int)n6)).append("/").append(Integer.toString((int)n7));
                String string4 = n8 < 2 ? "" : " AAx" + Integer.toString((int)n8);
                StringBuilder stringBuilder4 = stringBuilder3.append(string4);
                String string5 = n9 < 2 ? "" : " CSAAx" + Integer.toString((int)n9);
                String string6 = stringBuilder4.append(string5).toString();
                StringBuilder stringBuilder5 = new StringBuilder().append("Creating OpenGL ES ");
                String string7 = b ? "2.0" : "1.x";
                q.a(stringBuilder5.append(string7).append(" context (").append(string6).append(")").toString());
                q.a("Before eglCreateContext", eGL10);
                int[] arrn = new int[3];
                arrn[0] = 12440;
                int n10 = b ? 2 : 1;
                arrn[1] = n10;
                arrn[2] = 12344;
                EGLContext eGLContext = eGL10.eglCreateContext(eGLDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, arrn);
                q.a("After eglCreateContext", eGL10);
                return eGLContext;
            }

            public final void destroyContext(EGL10 eGL10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
                eGL10.eglDestroyContext(eGLDisplay, eGLContext);
            }
        });
        GLSurfaceView.EGLConfigChooser eGLConfigChooser = bl || n2 == 32 ? new GLSurfaceView.EGLConfigChooser(8, 8, 8, 8, n3, n4, n5){
            private static final int[] f;
            private static final int[] g;
            private static int[] j;
            private static /* synthetic */ boolean k;
            protected int a;
            protected int b;
            protected int c;
            protected int d;
            public int e;
            private int h;
            private int i;

            /*
             * Enabled aggressive block sorting
             */
            static {
                boolean bl = !q.class.desiredAssertionStatus();
                k = bl;
                f = new int[]{12324, 4, 12323, 4, 12322, 4, 12352, 4, 12344};
                g = new int[]{12324, 5, 12323, 6, 12322, 5, 12352, 1, 12344};
                j = new int[1];
            }
            {
                this.a = n;
                this.b = n2;
                this.c = n3;
                this.d = n4;
                this.h = n5;
                this.i = n6;
                this.e = n7;
            }

            private static int a(EGL10 eGL10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int n, int n2) {
                if (eGL10.eglGetConfigAttrib(eGLDisplay, eGLConfig, n, j)) {
                    return j[0];
                }
                int n3 = eGL10.eglGetError();
                if (n3 != 12292) {
                    Object[] arrobject = new Object[]{n3};
                    Log.e((String)"Unity", (String)String.format((String)"findConfigAttrib: EGL error: 0x%x", (Object[])arrobject));
                }
                q.a("findConfigAttrib (" + Integer.toHexString((int)n) + ")", eGL10);
                return 0;
            }

            private EGLConfig a(EGL10 eGL10, EGLDisplay eGLDisplay, EGLConfig[] arreGLConfig) {
                for (EGLConfig eGLConfig : arreGLConfig) {
                    if (!k && eGLConfig == null) {
                        throw new AssertionError();
                    }
                    int n = a.a(eGL10, eGLDisplay, eGLConfig, 12325, 0);
                    int n2 = a.a(eGL10, eGLDisplay, eGLConfig, 12326, 0);
                    int n3 = a.a(eGL10, eGLDisplay, eGLConfig, 12337, 0);
                    int n4 = a.a(eGL10, eGLDisplay, eGLConfig, 12513, 0);
                    if (n < this.h || n2 < this.i || n3 < this.e && n4 - 1 < this.e) continue;
                    int n5 = a.a(eGL10, eGLDisplay, eGLConfig, 12324, 0);
                    int n6 = a.a(eGL10, eGLDisplay, eGLConfig, 12323, 0);
                    int n7 = a.a(eGL10, eGLDisplay, eGLConfig, 12322, 0);
                    int n8 = a.a(eGL10, eGLDisplay, eGLConfig, 12321, 0);
                    if (n5 != this.a || n6 != this.b || n7 != this.c || n8 != this.d) continue;
                    return eGLConfig;
                }
                return null;
            }

            /*
             * Enabled aggressive block sorting
             */
            protected static void printConfig(EGL10 eGL10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
                int[] arrn = new int[]{12320, 12321, 12322, 12323, 12324, 12325, 12326, 12327, 12328, 12329, 12330, 12331, 12332, 12333, 12334, 12335, 12336, 12337, 12338, 12339, 12340, 12343, 12342, 12341, 12345, 12346, 12347, 12348, 12349, 12350, 12351, 12352, 12354, 12512, 12513};
                String[] arrstring = new String[]{"EGL_BUFFER_SIZE", "EGL_ALPHA_SIZE", "EGL_BLUE_SIZE", "EGL_GREEN_SIZE", "EGL_RED_SIZE", "EGL_DEPTH_SIZE", "EGL_STENCIL_SIZE", "EGL_CONFIG_CAVEAT", "EGL_CONFIG_ID", "EGL_LEVEL", "EGL_MAX_PBUFFER_HEIGHT", "EGL_MAX_PBUFFER_PIXELS", "EGL_MAX_PBUFFER_WIDTH", "EGL_NATIVE_RENDERABLE", "EGL_NATIVE_VISUAL_ID", "EGL_NATIVE_VISUAL_TYPE", "EGL_PRESERVED_RESOURCES", "EGL_SAMPLES", "EGL_SAMPLE_BUFFERS", "EGL_SURFACE_TYPE", "EGL_TRANSPARENT_TYPE", "EGL_TRANSPARENT_RED_VALUE", "EGL_TRANSPARENT_GREEN_VALUE", "EGL_TRANSPARENT_BLUE_VALUE", "EGL_BIND_TO_TEXTURE_RGB", "EGL_BIND_TO_TEXTURE_RGBA", "EGL_MIN_SWAP_INTERVAL", "EGL_MAX_SWAP_INTERVAL", "EGL_LUMINANCE_SIZE", "EGL_ALPHA_MASK_SIZE", "EGL_COLOR_BUFFER_TYPE", "EGL_RENDERABLE_TYPE", "EGL_CONFORMANT", "EGL_COVERAGE_BUFFERS_NV", "EGL_COVERAGE_SAMPLES_NV"};
                int[] arrn2 = new int[1];
                int n = 0;
                while (n < arrn.length) {
                    int n2 = arrn[n];
                    String string2 = arrstring[n];
                    if (eGL10.eglGetConfigAttrib(eGLDisplay, eGLConfig, n2, arrn2)) {
                        Object[] arrobject = new Object[]{string2, arrn2[0]};
                        q.a(String.format((String)"  %s: %d\n", (Object[])arrobject));
                    } else {
                        q.a("printConfig (" + string2 + ")", eGL10);
                    }
                    ++n;
                }
                return;
            }

            /*
             * Enabled aggressive block sorting
             */
            public final EGLConfig chooseConfig(EGL10 eGL10, EGLDisplay eGLDisplay) {
                int[] arrn = b ? f : g;
                int[] arrn2 = new int[1];
                eGL10.eglChooseConfig(eGLDisplay, arrn, null, 0, arrn2);
                int n = arrn2[0];
                if (n <= 0) {
                    throw new IllegalArgumentException("No configs match configSpec");
                }
                EGLConfig[] arreGLConfig = new EGLConfig[n];
                eGL10.eglChooseConfig(eGLDisplay, arrn, arreGLConfig, n, arrn2);
                EGLConfig eGLConfig = a.super.a(eGL10, eGLDisplay, arreGLConfig);
                while (eGLConfig == null && this.e > 0) {
                    int n2 = this.e == 2 ? 0 : this.e / 2;
                    this.e = n2;
                    eGLConfig = a.super.a(eGL10, eGLDisplay, arreGLConfig);
                }
                if (eGLConfig == null && this.h == 24) {
                    this.h = 16;
                    eGLConfig = a.super.a(eGL10, eGLDisplay, arreGLConfig);
                }
                if (eGLConfig != null) return eGLConfig;
                return arreGLConfig[0];
            }
        } : new /* invalid duplicate definition of identical inner class */;
        this.c = eGLConfigChooser;
        this.setEGLConfigChooser((GLSurfaceView.EGLConfigChooser)this.c);
    }

    protected void onDetachedFromWindow() {
        if (!a) {
            Log.d((String)"Unity", (String)"onDetachedFromWindow");
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

}


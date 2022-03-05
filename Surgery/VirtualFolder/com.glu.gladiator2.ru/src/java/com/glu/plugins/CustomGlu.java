/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.Typeface
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  java.io.BufferedReader
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Thread
 *  java.net.HttpURLConnection
 *  java.net.URL
 *  java.net.URLConnection
 *  java.util.Enumeration
 *  java.util.Hashtable
 */
package com.glu.plugins;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.glu.plugins.AAds;
import com.unity3d.player.UnityPlayer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;

public class CustomGlu {
    private static final int TYPEFACE_DEFAULT = 0;
    private static final int TYPEFACE_DEFAULT_BOLD = 1;
    private static final int TYPEFACE_MONOSPACE = 2;
    private static final int TYPEFACE_SANS_SERIF = 3;
    private static final int TYPEFACE_SERIF = 4;
    private static Hashtable<String, CustomAd> ads;

    public static void Attach(String string2, String string3) {
        AAds.Log("Custom.Attach( " + string2 + ", " + string3 + " )");
        if (ads == null || !ads.containsKey((Object)string2)) {
            AAds.Log("[" + string2 + "] not initialized");
            return;
        }
        ((CustomAd)ads.get((Object)string2)).Attach(string3);
    }

    public static int GetSourceHeight(String string2) {
        AAds.Log("Custom.GetSourceHeight( " + string2 + " )");
        if (ads == null || !ads.containsKey((Object)string2)) {
            AAds.Log("[" + string2 + "] not initialized");
            return 0;
        }
        return ((CustomAd)ads.get((Object)string2)).GetSourceHeight();
    }

    public static int GetSourceWidth(String string2) {
        AAds.Log("Custom.GetSourceWidth( " + string2 + " )");
        if (ads == null || !ads.containsKey((Object)string2)) {
            AAds.Log("[" + string2 + "] not initialized");
            return 0;
        }
        return ((CustomAd)ads.get((Object)string2)).GetSourceWidth();
    }

    public static void Hide(String string2) {
        AAds.Log("Custom.Hide( " + string2 + " )");
        if (ads == null || !ads.containsKey((Object)string2)) {
            AAds.Log("[" + string2 + "] not initialized");
            return;
        }
        ((CustomAd)ads.get((Object)string2)).Hide();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void HideAll() {
        AAds.Log("Custom.HideAll()");
        if (ads != null) {
            Enumeration enumeration = ads.elements();
            while (enumeration.hasMoreElements()) {
                ((CustomAd)enumeration.nextElement()).Hide();
            }
        }
    }

    public static void Init(String string2, String string3, String string4, String string5) {
        AAds.Log("Custom.Init( " + string2 + ", " + string3 + ", " + string4 + ", " + string5 + " )");
        if (ads == null) {
            ads = new Hashtable();
        }
        ads.put((Object)string2, (Object)new CustomAd(string2, string3, string4, string5));
    }

    public static boolean IsActive(String string2) {
        AAds.Log("Custom.IsActive( " + string2 + " )");
        if (ads == null || !ads.containsKey((Object)string2)) {
            AAds.Log("[" + string2 + "] not initialized");
            return false;
        }
        return ((CustomAd)ads.get((Object)string2)).IsActive();
    }

    public static boolean IsAvailable(String string2) {
        AAds.Log("Custom.IsAvailable( " + string2 + " )");
        if (ads == null || !ads.containsKey((Object)string2)) {
            AAds.Log("[" + string2 + "] not initialized");
            return false;
        }
        return ((CustomAd)ads.get((Object)string2)).IsAvailable();
    }

    public static void SetPadding(String string2, int n, int n2, int n3, int n4) {
        AAds.Log("Custom.SetPadding( " + string2 + ", " + n + ", " + n2 + ", " + n3 + ", " + n4 + " )");
        if (ads == null || !ads.containsKey((Object)string2)) {
            AAds.Log("[" + string2 + "] not initialized");
            return;
        }
        ((CustomAd)ads.get((Object)string2)).SetPadding(n, n2, n3, n4);
    }

    public static void SetText(String string2, String string3, int n, int n2, float f2, int n3) {
        AAds.Log("Custom.SetText( " + string2 + ", " + string3 + ", " + n + ", " + n2 + ", " + f2 + ", " + n3 + " )");
        if (ads == null || !ads.containsKey((Object)string2)) {
            AAds.Log("[" + string2 + "] not initialized");
            return;
        }
        ((CustomAd)ads.get((Object)string2)).SetText(string3, n, n2, f2, n3);
    }

    public static void Show(String string2, int n, int n2, int n3) {
        AAds.Log("Custom.Show( " + string2 + ", " + n + ", " + n2 + ", " + n3 + " )");
        if (ads == null || !ads.containsKey((Object)string2)) {
            AAds.Log("[" + string2 + "] not initialized");
            return;
        }
        ((CustomAd)ads.get((Object)string2)).Show(n, n2, n3);
    }

    static /* synthetic */ Hashtable access$1300() {
        return ads;
    }

    private static class CustomAd {
        private int AD_GRAVITY;
        private int AD_HEIGHT;
        private int AD_WIDTH;
        private int AD_X;
        private int AD_Y;
        private int PADDING_BOTTOM = 0;
        private int PADDING_LEFT = 0;
        private int PADDING_RIGHT = 0;
        private int PADDING_TOP = 0;
        private String RELATIVE_TAG;
        private Bitmap altImage;
        private View bannerView;
        private String customAction;
        private int lastCheckedHeight = -1;
        private int lastCheckedWidth = -1;
        private LinearLayout linearLayout;
        private Bitmap mainImage;
        private int scaledHeight = 0;
        private int scaledWidth = 0;
        private boolean showImmediately = false;
        private String tag;
        private LinearLayout textLinearLayout;
        private TextView textView;
        private String unityGameObject;

        public CustomAd(String string2, String string3, String string4, String string5) {
            AAds.Log("CustomAd( " + string2 + ", " + string3 + ", " + string4 + ", " + string5 + " )");
            this.tag = string2;
            this.unityGameObject = string3;
            this.textView = new TextView((Context)UnityPlayer.currentActivity);
            CustomAd.super.LoadBitmap(string4, true);
            CustomAd.super.LoadBitmap(string5, false);
        }

        private int GetScaledHeight() {
            if (this.scaledHeight != 0 && this.lastCheckedHeight == this.AD_HEIGHT) {
                return this.scaledHeight;
            }
            this.MeasureScaledDimensions();
            this.lastCheckedWidth = this.AD_WIDTH;
            this.lastCheckedHeight = this.AD_HEIGHT;
            return this.scaledHeight;
        }

        private int GetScaledWidth() {
            if (this.scaledWidth != 0 && this.lastCheckedWidth == this.AD_WIDTH) {
                return this.scaledWidth;
            }
            this.MeasureScaledDimensions();
            this.lastCheckedWidth = this.AD_WIDTH;
            this.lastCheckedHeight = this.AD_HEIGHT;
            return this.scaledWidth;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void LoadBitmap(final String var1, final boolean var2_2) {
            block8 : {
                if (var1 == null) return;
                if (var1.equals((Object)"")) {
                    return;
                }
                AAds.Log("CustomAd[" + this.tag + "].LoadBitmap( " + var1 + ", " + var2_2 + " )");
                if (!var1.startsWith("http")) break block8;
                try {
                    block9 : {
                        AAds.Log("Attempting to load from cache");
                        var5_3 = UnityPlayer.currentActivity.getFilesDir();
                        var6_4 = new StringBuilder().append(this.tag);
                        var7_5 = var2_2 != false ? "" : "-alt";
                        new File(var5_3, var6_4.append(var7_5).append(".png").toString());
                        if (!var2_2) break block9;
                        this.mainImage = BitmapFactory.decodeFile((String)((Object)UnityPlayer.currentActivity.getFilesDir() + "/" + this.tag + ".png"));
                        ** GOTO lbl26
                    }
                    this.altImage = BitmapFactory.decodeFile((String)((Object)UnityPlayer.currentActivity.getFilesDir() + "/" + this.tag + "-alt.png"));
                }
                catch (Exception var3_7) {
                    try {
                        var3_7.printStackTrace();
                    }
                    catch (Exception var4_6) {
                        if (AAds.DEBUG == false) return;
                        var4_6.printStackTrace();
                        return;
                    }
lbl26: // 4 sources:
                    new Thread(){

                        /*
                         * Enabled aggressive block sorting
                         * Enabled unnecessary exception pruning
                         * Enabled aggressive exception aggregation
                         */
                        public void run() {
                            block14 : {
                                int n;
                                block13 : {
                                    File file;
                                    String string2;
                                    StringBuilder stringBuilder;
                                    try {
                                        if (var1.endsWith("txt")) {
                                            int n2;
                                            AAds.Log("Checking Text File");
                                            BufferedReader bufferedReader = new BufferedReader((Reader)new InputStreamReader(new URL(var1).openStream()));
                                            String string3 = bufferedReader.readLine();
                                            bufferedReader.close();
                                            AAds.Log("Got: " + string3);
                                            String[] arrstring = string3.split("\\|");
                                            if (arrstring.length != 2) {
                                                return;
                                            }
                                            CustomAd.this.customAction = arrstring[1];
                                            AAds.Log("Loading bitmap from URL");
                                            InputStream inputStream = ((HttpURLConnection)new URL(arrstring[0]).openConnection()).getInputStream();
                                            StringBuilder stringBuilder2 = new StringBuilder().append("Saving image as: ").append(CustomAd.this.tag);
                                            String string4 = var2_2 ? "" : "-alt";
                                            AAds.Log(stringBuilder2.append(string4).append(".png").toString());
                                            Activity activity = UnityPlayer.currentActivity;
                                            StringBuilder stringBuilder3 = new StringBuilder().append(CustomAd.this.tag);
                                            String string5 = var2_2 ? "" : "-alt";
                                            FileOutputStream fileOutputStream = activity.openFileOutput(stringBuilder3.append(string5).append(".png").toString(), 0);
                                            byte[] arrby = new byte[512];
                                            while ((n2 = inputStream.read(arrby)) > -1) {
                                                fileOutputStream.write(arrby, 0, n2);
                                            }
                                            fileOutputStream.close();
                                            file = UnityPlayer.currentActivity.getFilesDir();
                                            stringBuilder = new StringBuilder().append(CustomAd.this.tag);
                                            string2 = var2_2 ? "" : "-alt";
                                        }
                                        break block13;
                                    }
                                    catch (Exception exception) {
                                        if (!AAds.DEBUG) return;
                                        {
                                            exception.printStackTrace();
                                            return;
                                        }
                                    }
                                    new File(file, stringBuilder.append(string2).append(".png").toString());
                                    if (var2_2) {
                                        CustomAd.this.mainImage = BitmapFactory.decodeFile((String)((Object)UnityPlayer.currentActivity.getFilesDir() + "/" + CustomAd.this.tag + ".png"));
                                    } else {
                                        CustomAd.this.altImage = BitmapFactory.decodeFile((String)((Object)UnityPlayer.currentActivity.getFilesDir() + "/" + CustomAd.this.tag + "-alt.png"));
                                    }
                                    break block14;
                                }
                                AAds.Log("Loading bitmap from URL");
                                InputStream inputStream = ((HttpURLConnection)new URL(var1).openConnection()).getInputStream();
                                StringBuilder stringBuilder = new StringBuilder().append("Saving image as: ").append(CustomAd.this.tag);
                                String string6 = var2_2 ? "" : "-alt";
                                AAds.Log(stringBuilder.append(string6).append(".png").toString());
                                Activity activity = UnityPlayer.currentActivity;
                                StringBuilder stringBuilder4 = new StringBuilder().append(CustomAd.this.tag);
                                String string7 = var2_2 ? "" : "-alt";
                                FileOutputStream fileOutputStream = activity.openFileOutput(stringBuilder4.append(string7).append(".png").toString(), 0);
                                byte[] arrby = new byte[512];
                                while ((n = inputStream.read(arrby)) > -1) {
                                    fileOutputStream.write(arrby, 0, n);
                                }
                                fileOutputStream.close();
                                File file = UnityPlayer.currentActivity.getFilesDir();
                                StringBuilder stringBuilder5 = new StringBuilder().append(CustomAd.this.tag);
                                String string8 = var2_2 ? "" : "-alt";
                                new File(file, stringBuilder5.append(string8).append(".png").toString());
                                if (var2_2) {
                                    CustomAd.this.mainImage = BitmapFactory.decodeFile((String)((Object)UnityPlayer.currentActivity.getFilesDir() + "/" + CustomAd.this.tag + ".png"));
                                } else {
                                    CustomAd.this.altImage = BitmapFactory.decodeFile((String)((Object)UnityPlayer.currentActivity.getFilesDir() + "/" + CustomAd.this.tag + "-alt.png"));
                                }
                            }
                            if (!CustomAd.this.showImmediately) return;
                            {
                                CustomAd.this.Show(CustomAd.this.AD_WIDTH, CustomAd.this.AD_HEIGHT, CustomAd.this.AD_GRAVITY);
                                return;
                            }
                        }
                    }.start();
                    return;
                }
            }
            AAds.Log("Decoding bitmap from drawable");
            if (var2_2) {
                this.mainImage = BitmapFactory.decodeResource((Resources)UnityPlayer.currentActivity.getResources(), (int)UnityPlayer.currentActivity.getResources().getIdentifier(var1, "drawable", UnityPlayer.currentActivity.getPackageName()));
                return;
            }
            this.altImage = BitmapFactory.decodeResource((Resources)UnityPlayer.currentActivity.getResources(), (int)UnityPlayer.currentActivity.getResources().getIdentifier(var1, "drawable", UnityPlayer.currentActivity.getPackageName()));
        }

        /*
         * Enabled aggressive block sorting
         */
        private void MeasureScaledDimensions() {
            AAds.Log("MeasureScaledDimensions()");
            int n = AAds.GetScreenWidth();
            int n2 = AAds.GetScreenHeight();
            int n3 = this.mainImage.getWidth();
            int n4 = this.mainImage.getHeight();
            this.scaledWidth = n3;
            this.scaledHeight = n4;
            AAds.Log("Original Dimensions: " + this.scaledWidth + "x" + this.scaledHeight);
            if (this.AD_WIDTH != 0 || this.AD_HEIGHT != 0) {
                AAds.Log("Scaling ad based on custom input");
                this.scaledWidth = this.AD_WIDTH != 0 ? this.AD_WIDTH : n3 * this.AD_HEIGHT / n4;
                this.scaledHeight = this.AD_HEIGHT != 0 ? this.AD_HEIGHT : n4 * this.AD_WIDTH / n3;
                AAds.Log("New Dimensions: " + this.scaledWidth + "x" + this.scaledHeight);
            }
            if (this.scaledWidth > n) {
                AAds.Log("Scaling ad to fit screen width");
                this.scaledWidth = n;
                this.scaledHeight = n4 * this.scaledWidth / n3;
                AAds.Log("New Dimensions: " + this.scaledWidth + "x" + this.scaledHeight);
            }
            if (this.scaledHeight > n2) {
                AAds.Log("Scaling ad to fit screen height");
                this.scaledHeight = n2;
                this.scaledWidth = n3 * this.scaledHeight / n4;
                AAds.Log("New Dimensions: " + this.scaledWidth + "x" + this.scaledHeight);
            }
        }

        /*
         * Exception decompiling
         */
        private void mUpdateResults() {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
            // org.benf.cfr.reader.b.a.a.j.b(Op04StructuredStatement.java:409)
            // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:487)
            // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
            // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.g.p(Method.java:396)
            // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
            // org.benf.cfr.reader.entities.d.c(ClassFile.java:773)
            // org.benf.cfr.reader.entities.d.e(ClassFile.java:870)
            // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
            // org.benf.cfr.reader.b.a(Driver.java:128)
            // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
            // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
            // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
            // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
            // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
            // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1112)
            // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:587)
            // java.lang.Thread.run(Thread.java:818)
            throw new IllegalStateException("Decompilation failed");
        }

        public void Attach(String string2) {
            AAds.Log("CustomAd[" + this.tag + "].Attach( " + string2 + " )");
            this.RELATIVE_TAG = string2;
        }

        public int GetSourceHeight() {
            AAds.Log("CustomAd[" + this.tag + "].GetSourceHeight()");
            if (this.mainImage != null) {
                return this.mainImage.getHeight();
            }
            return 0;
        }

        public int GetSourceWidth() {
            AAds.Log("CustomAd[" + this.tag + "].GetSourceWidth()");
            if (this.mainImage != null) {
                return this.mainImage.getWidth();
            }
            return 0;
        }

        public void Hide() {
            AAds.Log("CustomAd[" + this.tag + "].Hide()");
            UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

                public void run() {
                    if (CustomAd.this.linearLayout != null) {
                        CustomAd.this.linearLayout.removeAllViews();
                    }
                    if (CustomAd.this.textLinearLayout != null) {
                        CustomAd.this.textLinearLayout.removeAllViews();
                    }
                    CustomAd.this.linearLayout = null;
                    CustomAd.this.textLinearLayout = null;
                    CustomAd.this.bannerView = null;
                    CustomAd.this.showImmediately = false;
                }
            });
        }

        public boolean IsActive() {
            AAds.Log("CustomAd[" + this.tag + "].IsActive()");
            return this.bannerView != null;
        }

        public boolean IsAvailable() {
            AAds.Log("CustomAd[" + this.tag + "].IsAvailable()");
            return this.mainImage != null;
        }

        public void SetPadding(int n, int n2, int n3, int n4) {
            AAds.Log("SetPadding[" + this.tag + "].SetPadding( " + n + ", " + n2 + ", " + n3 + ", " + n4 + " )");
            this.PADDING_LEFT = n;
            this.PADDING_TOP = n2;
            this.PADDING_RIGHT = n3;
            this.PADDING_BOTTOM = n4;
        }

        public void SetText(final String string2, final int n, final int n2, final float f2, final int n3) {
            AAds.Log("CustomAd[" + this.tag + "].SetText( " + string2 + ", " + n + ", " + n2 + ", " + f2 + ", " + n3 + " )");
            if (this.textView == null) {
                return;
            }
            UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 */
                public void run() {
                    CustomAd.this.textView.setText((CharSequence)string2);
                    if (n >= 0 || n2 >= 0) {
                        Typeface typeface;
                        switch (n) {
                            default: {
                                typeface = Typeface.DEFAULT;
                                break;
                            }
                            case 1: {
                                typeface = Typeface.DEFAULT_BOLD;
                                break;
                            }
                            case 2: {
                                typeface = Typeface.MONOSPACE;
                                break;
                            }
                            case 3: {
                                typeface = Typeface.SANS_SERIF;
                                break;
                            }
                            case 4: {
                                typeface = Typeface.SERIF;
                            }
                        }
                        if (n2 > 0) {
                            CustomAd.this.textView.setTypeface(typeface, n2);
                        } else {
                            CustomAd.this.textView.setTypeface(typeface);
                        }
                    }
                    if (f2 > 0.0f) {
                        CustomAd.this.textView.setTextSize(f2);
                    }
                    if (n3 != 0) {
                        CustomAd.this.textView.setTextColor(n3);
                    }
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         */
        public void Show(int n, int n2, int n3) {
            AAds.Log("CustomAd[" + this.tag + "].Show( " + n + ", " + n2 + ", " + n3 + " )");
            if (n >= 0) {
                this.AD_WIDTH = n;
            }
            if (n2 >= 0) {
                this.AD_HEIGHT = n2;
            }
            if (n3 >= 0) {
                this.AD_GRAVITY = n3;
            }
            if (this.mainImage == null) {
                this.showImmediately = true;
                return;
            }
            switch (this.AD_GRAVITY) {
                case 51: {
                    this.AD_X = 0;
                    this.AD_Y = 0;
                    break;
                }
                case 49: {
                    this.AD_X = AAds.GetScreenWidth() / 2 - CustomAd.super.GetScaledWidth() / 2;
                    this.AD_Y = 0;
                    break;
                }
                case 53: {
                    this.AD_X = AAds.GetScreenWidth() - CustomAd.super.GetScaledWidth();
                    this.AD_Y = 0;
                    break;
                }
                case 21: {
                    this.AD_X = AAds.GetScreenWidth() - CustomAd.super.GetScaledWidth();
                    this.AD_Y = AAds.GetScreenHeight() / 2 - CustomAd.super.GetScaledHeight() / 2;
                    break;
                }
                case 85: {
                    this.AD_X = AAds.GetScreenWidth() - CustomAd.super.GetScaledWidth();
                    this.AD_Y = AAds.GetScreenHeight() - CustomAd.super.GetScaledHeight();
                    break;
                }
                case 81: {
                    this.AD_X = AAds.GetScreenWidth() / 2 - CustomAd.super.GetScaledWidth() / 2;
                    this.AD_Y = AAds.GetScreenHeight() - CustomAd.super.GetScaledHeight();
                    break;
                }
                case 83: {
                    this.AD_X = 0;
                    this.AD_Y = AAds.GetScreenHeight() - CustomAd.super.GetScaledHeight();
                    break;
                }
                case 19: {
                    this.AD_X = 0;
                    this.AD_Y = AAds.GetScreenHeight() / 2 - CustomAd.super.GetScaledHeight() / 2;
                    break;
                }
                case 17: {
                    this.AD_X = AAds.GetScreenWidth() / 2 - CustomAd.super.GetScaledWidth() / 2;
                    this.AD_Y = AAds.GetScreenHeight() / 2 - CustomAd.super.GetScaledHeight() / 2;
                    break;
                }
            }
            UnityPlayer.currentActivity.runOnUiThread(new Runnable(){

                public void run() {
                    if (CustomAd.this.bannerView != null) {
                        return;
                    }
                    CustomAd.this.mUpdateResults();
                }
            });
        }

    }

}


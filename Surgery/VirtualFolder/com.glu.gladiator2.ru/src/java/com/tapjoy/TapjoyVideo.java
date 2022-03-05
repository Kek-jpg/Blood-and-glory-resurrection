/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.os.Environment
 *  android.util.Log
 *  java.io.BufferedInputStream
 *  java.io.ByteArrayInputStream
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.InputStream
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Thread
 *  java.net.SocketTimeoutException
 *  java.net.URL
 *  java.net.URLConnection
 *  java.util.Enumeration
 *  java.util.Hashtable
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.Vector
 *  javax.xml.parsers.DocumentBuilder
 *  javax.xml.parsers.DocumentBuilderFactory
 *  org.w3c.dom.Document
 *  org.w3c.dom.Element
 *  org.w3c.dom.NamedNodeMap
 *  org.w3c.dom.Node
 *  org.w3c.dom.NodeList
 */
package com.tapjoy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import com.tapjoy.TapjoyConnectCore;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyURLConnection;
import com.tapjoy.TapjoyUtil;
import com.tapjoy.TapjoyVideoNotifier;
import com.tapjoy.TapjoyVideoObject;
import com.tapjoy.TapjoyVideoView;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TapjoyVideo {
    public static final String TAPJOY_VIDEO = "TapjoyVideo";
    private static TapjoyVideo tapjoyVideo = null;
    private static TapjoyVideoNotifier tapjoyVideoNotifier;
    private static Bitmap watermarkImage;
    private static final String watermarkURL = "https://s3.amazonaws.com/tapjoy/videos/assets/watermark.png";
    private boolean cache3g = false;
    private boolean cacheAuto = false;
    private boolean cacheWifi = false;
    private Hashtable<String, TapjoyVideoObject> cachedVideos;
    Context context;
    private String imageCacheDir = null;
    private boolean initialized = false;
    private Hashtable<String, TapjoyVideoObject> uncachedVideos;
    private String videoCacheDir = null;
    private int videoCacheLimit = 5;
    private Vector<String> videoQueue;
    private TapjoyVideoObject videoToPlay;

    public TapjoyVideo(Context context) {
        this.context = context;
        tapjoyVideo = this;
        this.videoCacheDir = Environment.getExternalStorageDirectory().toString() + "/tjcache/data/";
        this.imageCacheDir = Environment.getExternalStorageDirectory().toString() + "/tjcache/tmp/";
        TapjoyUtil.deleteFileOrDirectory(new File(Environment.getExternalStorageDirectory().toString() + "/tapjoy/"));
        TapjoyUtil.deleteFileOrDirectory(new File(this.imageCacheDir));
        this.videoQueue = new Vector();
        this.uncachedVideos = new Hashtable();
        this.cachedVideos = new Hashtable();
        this.init();
    }

    static /* synthetic */ Hashtable access$1200(TapjoyVideo tapjoyVideo) {
        return tapjoyVideo.uncachedVideos;
    }

    private void cacheAllVideos() {
        new Thread(new Runnable(){

            public void run() {
                TapjoyLog.i(TapjoyVideo.TAPJOY_VIDEO, "--- cacheAllVideos called ---");
                int n = 0;
                while (!TapjoyVideo.this.initialized) {
                    try {
                        Thread.sleep((long)500L);
                    }
                    catch (Exception exception) {
                        TapjoyLog.e(TapjoyVideo.TAPJOY_VIDEO, "Exception in cacheAllVideos: " + exception.toString());
                        continue;
                    }
                    if ((long)(n = (int)(500L + (long)n)) <= 10000L) continue;
                    TapjoyLog.e(TapjoyVideo.TAPJOY_VIDEO, "Error during cacheVideos.  Timeout while waiting for initVideos to finish.");
                    return;
                }
                TapjoyLog.i(TapjoyVideo.TAPJOY_VIDEO, "cacheVideos connection_type: " + TapjoyConnectCore.getConnectionType());
                TapjoyLog.i(TapjoyVideo.TAPJOY_VIDEO, "cache3g: " + TapjoyVideo.this.cache3g);
                TapjoyLog.i(TapjoyVideo.TAPJOY_VIDEO, "cacheWifi: " + TapjoyVideo.this.cacheWifi);
                if (TapjoyVideo.this.cache3g && TapjoyConnectCore.getConnectionType().equals((Object)"mobile") || TapjoyVideo.this.cacheWifi && TapjoyConnectCore.getConnectionType().equals((Object)"wifi")) {
                    if (!"mounted".equals((Object)Environment.getExternalStorageState())) {
                        TapjoyLog.i(TapjoyVideo.TAPJOY_VIDEO, "Media storage unavailable.  Aborting caching videos.");
                        TapjoyVideo.videoNotifierError(1);
                        return;
                    }
                    while (TapjoyVideo.this.cachedVideos.size() < TapjoyVideo.this.videoCacheLimit && TapjoyVideo.this.videoQueue.size() > 0) {
                        String string2 = ((TapjoyVideoObject)TapjoyVideo.access$1200((TapjoyVideo)TapjoyVideo.this).get((Object)TapjoyVideo.access$1100((TapjoyVideo)TapjoyVideo.this).elementAt((int)0))).videoURL;
                        TapjoyVideo.this.cacheVideo(string2);
                    }
                } else {
                    TapjoyLog.i(TapjoyVideo.TAPJOY_VIDEO, " * Skipping caching videos because of video flags and connection_type...");
                }
                TapjoyVideo.this.printCachedVideos();
            }
        }).start();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void cacheVideo(String var1) {
        block24 : {
            var2_2 = null;
            TapjoyLog.i("TapjoyVideo", "download and cache video from: " + var1);
            var3_3 = System.currentTimeMillis();
            var21_4 = new URL(var1).openConnection();
            var21_4.setConnectTimeout(15000);
            var21_4.setReadTimeout(30000);
            var21_4.connect();
            var10_5 = new BufferedInputStream(var21_4.getInputStream());
            var22_6 = new File(this.videoCacheDir);
            var23_7 = var1.substring(0, 1 + var1.lastIndexOf("/"));
            var24_8 = var1.substring(1 + var1.lastIndexOf("/"));
            var25_9 = var24_8.substring(0, var24_8.indexOf(46));
            TapjoyLog.i("TapjoyVideo", "fileDir: " + (Object)var22_6);
            TapjoyLog.i("TapjoyVideo", "path: " + var23_7);
            TapjoyLog.i("TapjoyVideo", "file name: " + var25_9);
            if (var22_6.mkdirs()) {
                TapjoyLog.i("TapjoyVideo", "created directory at: " + var22_6.getPath());
            }
            var12_10 = new File(this.videoCacheDir, var25_9);
            var9_11 = new FileOutputStream(var12_10);
            try {
                TapjoyLog.i("TapjoyVideo", "downloading video file to: " + var12_10.toString());
                var26_12 = new byte[1024];
                while ((var27_13 = var10_5.read(var26_12)) != -1) {
                    var9_11.write(var26_12, 0, var27_13);
                }
                var9_11.close();
                var10_5.close();
                TapjoyLog.i("TapjoyVideo", "FILE SIZE: " + var12_10.length());
                var28_26 = var12_10.length();
                if (var28_26 != 0L) ** GOTO lbl84
                var11_22 = true;
                var13_23 = false;
                ** GOTO lbl86
            }
            catch (SocketTimeoutException var5_14) {
                block21 : {
                    block22 : {
                        var6_19 = var12_10;
                        var7_20 = var9_11;
                        var8_21 = var10_5;
                        break block22;
                        catch (Exception var20_27) {
                            block20 : {
                                var9_11 = null;
                                var10_5 = null;
                                break block20;
                                catch (Exception var20_29) {
                                    var2_2 = null;
                                    var9_11 = null;
                                    break block20;
                                }
                                catch (Exception var20_30) {
                                    var2_2 = var12_10;
                                    var9_11 = null;
                                    break block20;
                                }
                                catch (Exception var20_31) {
                                    var2_2 = var12_10;
                                }
                            }
                            TapjoyLog.e("TapjoyVideo", "Error caching video file: " + var20_28.toString());
                            var12_10 = var2_2;
                            var13_23 = true;
                            var11_22 = false;
                            break block21;
                        }
                        catch (SocketTimeoutException var5_16) {
                            var6_19 = null;
                            var7_20 = null;
                            var8_21 = null;
                            break block22;
                        }
                        catch (SocketTimeoutException var5_17) {
                            var8_21 = var10_5;
                            var6_19 = null;
                            var7_20 = null;
                            break block22;
                        }
                        catch (SocketTimeoutException var5_18) {
                            var6_19 = var12_10;
                            var8_21 = var10_5;
                            var7_20 = null;
                        }
                    }
                    TapjoyLog.e("TapjoyVideo", "Network timeout: " + var5_15.toString());
                    var9_11 = var7_20;
                    var10_5 = var8_21;
                    var11_22 = true;
                    var12_10 = var6_19;
                    var13_23 = true;
                    break block21;
lbl84: // 1 sources:
                    var11_22 = false;
                    var13_23 = false;
                }
                if (var11_22) {
                    TapjoyLog.i("TapjoyVideo", "Network timeout");
                    try {
                        var10_5.close();
                        var9_11.close();
                    }
                    catch (Exception var19_33) {}
                }
                if (var11_22 || var13_23) break block24;
                try {
                    var15_24 = (String)this.videoQueue.elementAt(0);
                    var16_25 = (TapjoyVideoObject)this.uncachedVideos.get((Object)var15_24);
                    var16_25.dataLocation = var12_10.getAbsolutePath();
                    this.cachedVideos.put((Object)var15_24, (Object)var16_25);
                    this.uncachedVideos.remove((Object)var15_24);
                    this.videoQueue.removeElementAt(0);
                    TapjoyVideo.super.setVideoIDs();
                    TapjoyLog.i("TapjoyVideo", "video cached in: " + (System.currentTimeMillis() - var3_3) + "ms");
                    return;
                }
                catch (Exception var14_32) {
                    TapjoyLog.e("TapjoyVideo", "error caching video ???: " + var14_32.toString());
                    return;
                }
            }
        }
        TapjoyVideo.videoNotifierError(2);
    }

    public static TapjoyVideo getInstance() {
        return tapjoyVideo;
    }

    public static Bitmap getWatermarkImage() {
        return watermarkImage;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean handleGetVideosResponse(String var1) {
        block19 : {
            var2_2 = DocumentBuilderFactory.newInstance();
            TapjoyLog.i("TapjoyVideo", "========================================");
            try {
                var3_3 = new ByteArrayInputStream(var1.getBytes("UTF-8"));
                var5_4 = var2_2.newDocumentBuilder().parse((InputStream)var3_3);
                var5_4.getDocumentElement().normalize();
                var6_5 = var5_4.getElementsByTagName("TapjoyVideos");
                var7_6 = var6_5.item(0).getChildNodes();
                var8_7 = var6_5.item(0).getAttributes();
                if (var8_7.getNamedItem("cache_auto") != null && var8_7.getNamedItem("cache_auto").getNodeValue() != null) {
                    this.cacheAuto = Boolean.valueOf((String)var8_7.getNamedItem("cache_auto").getNodeValue());
                }
                if (var8_7.getNamedItem("cache_wifi") != null && var8_7.getNamedItem("cache_wifi").getNodeValue() != null) {
                    this.cacheWifi = Boolean.valueOf((String)var8_7.getNamedItem("cache_wifi").getNodeValue());
                }
                if (var8_7.getNamedItem("cache_mobile") != null && var8_7.getNamedItem("cache_mobile").getNodeValue() != null) {
                    this.cache3g = Boolean.valueOf((String)var8_7.getNamedItem("cache_mobile").getNodeValue());
                }
                TapjoyLog.i("TapjoyVideo", "cacheAuto: " + this.cacheAuto);
                TapjoyLog.i("TapjoyVideo", "cacheWifi: " + this.cacheWifi);
                TapjoyLog.i("TapjoyVideo", "cache3g: " + this.cache3g);
                TapjoyLog.i("TapjoyVideo", "nodelistParent length: " + var6_5.getLength());
                TapjoyLog.i("TapjoyVideo", "nodelist length: " + var7_6.getLength());
                var9_8 = 0;
lbl22: // 2 sources:
                do {
                    if (var9_8 < var7_6.getLength()) {
                        var10_29 = var7_6.item(var9_8);
                        var11_9 = new TapjoyVideoObject();
                        if (var10_29 == null || var10_29.getNodeType() != 1) break;
                        var12_28 = (Element)var10_29;
                        var13_22 = TapjoyUtil.getNodeTrimValue(var12_28.getElementsByTagName("ClickURL"));
                        if (var13_22 != null && !var13_22.equals((Object)"")) {
                            var11_9.clickURL = var13_22;
                        }
                        if ((var14_16 = TapjoyUtil.getNodeTrimValue(var12_28.getElementsByTagName("OfferID"))) != null && !var14_16.equals((Object)"")) {
                            var11_9.offerID = var14_16;
                        }
                        if ((var15_11 = TapjoyUtil.getNodeTrimValue(var12_28.getElementsByTagName("Name"))) != null && !var15_11.equals((Object)"")) {
                            var11_9.videoAdName = var15_11;
                        }
                        if ((var16_18 = TapjoyUtil.getNodeTrimValue(var12_28.getElementsByTagName("Amount"))) != null && !var16_18.equals((Object)"")) {
                            var11_9.currencyAmount = var16_18;
                        }
                        if ((var17_15 = TapjoyUtil.getNodeTrimValue(var12_28.getElementsByTagName("CurrencyName"))) != null && !var17_15.equals((Object)"")) {
                            var11_9.currencyName = var17_15;
                        }
                        if ((var18_25 = TapjoyUtil.getNodeTrimValue(var12_28.getElementsByTagName("VideoURL"))) != null && !var18_25.equals((Object)"")) {
                            var11_9.videoURL = var18_25;
                        }
                        if ((var19_23 = TapjoyUtil.getNodeTrimValue(var12_28.getElementsByTagName("IconURL"))) != null && !var19_23.equals((Object)"")) {
                            var11_9.iconURL = var19_23;
                        }
                        TapjoyLog.i("TapjoyVideo", "-----");
                        TapjoyLog.i("TapjoyVideo", "videoObject.offerID: " + var11_9.offerID);
                        TapjoyLog.i("TapjoyVideo", "videoObject.videoAdName: " + var11_9.videoAdName);
                        TapjoyLog.i("TapjoyVideo", "videoObject.videoURL: " + var11_9.videoURL);
                        var20_19 = var12_28.getElementsByTagName("Buttons").item(0).getChildNodes();
                        var21_17 = 0;
                    } else {
                        TapjoyLog.i("TapjoyVideo", "========================================");
                        return true;
                    }
lbl52: // 2 sources:
                    do {
                        block20 : {
                            if (var21_17 >= var20_19.getLength()) break block20;
                            var23_13 = var20_19.item(var21_17).getChildNodes();
                            if (var23_13.getLength() != 0) break block19;
                            ** GOTO lbl94
                        }
                        this.videoQueue.addElement((Object)var11_9.offerID);
                        this.uncachedVideos.put((Object)var11_9.offerID, (Object)var11_9);
                        break;
                    } while (true);
                    break;
                } while (true);
            }
            catch (Exception var4_30) {
                TapjoyLog.e("TapjoyVideo", "Error parsing XML: " + var4_30.toString());
                return false;
            }
            ++var9_8;
            ** while (true)
        }
        var24_26 = "";
        var25_14 = "";
        var26_12 = 0;
        do {
            block24 : {
                block25 : {
                    block22 : {
                        block21 : {
                            block23 : {
                                if (var26_12 >= var23_13.getLength()) break block21;
                                if ((Element)var23_13.item(var26_12) == null) break block22;
                                var29_20 = ((Element)var23_13.item(var26_12)).getTagName();
                                if (!var29_20.equals((Object)"Name") || var23_13.item(var26_12).getFirstChild() == null) break block23;
                                var30_21 = var23_13.item(var26_12).getFirstChild().getNodeValue();
                                var31_27 = var25_14;
                                var28_10 = var30_21;
                                var27_24 = var31_27;
                                break block24;
                            }
                            if (!var29_20.equals((Object)"URL") || var23_13.item(var26_12).getFirstChild() == null) break block22;
                            var27_24 = var23_13.item(var26_12).getFirstChild().getNodeValue();
                            var28_10 = var24_26;
                            break block24;
                        }
                        TapjoyLog.i("TapjoyVideo", "name: " + var24_26 + ", url: " + var25_14);
                        var11_9.addButton(var24_26, var25_14);
                        break block25;
                    }
                    var27_24 = var25_14;
                    var28_10 = var24_26;
                    break block24;
                }
                ++var21_17;
                ** continue;
            }
            ++var26_12;
            var24_26 = var28_10;
            var25_14 = var27_24;
        } while (true);
    }

    private void printCachedVideos() {
        TapjoyLog.i(TAPJOY_VIDEO, "cachedVideos size: " + this.cachedVideos.size());
        for (Map.Entry entry : this.cachedVideos.entrySet()) {
            TapjoyLog.i(TAPJOY_VIDEO, "key: " + (String)entry.getKey() + ", name: " + ((TapjoyVideoObject)entry.getValue()).videoAdName);
        }
    }

    private void setVideoIDs() {
        String string2 = "";
        if (this.cachedVideos != null && this.cachedVideos.size() > 0) {
            Enumeration enumeration = this.cachedVideos.keys();
            String string3 = string2;
            while (enumeration.hasMoreElements()) {
                String string4 = (String)enumeration.nextElement();
                String string5 = string3 + string4;
                if (enumeration.hasMoreElements()) {
                    string5 = string5 + ",";
                }
                string3 = string5;
            }
            TapjoyLog.i(TAPJOY_VIDEO, "cachedVideos size: " + this.cachedVideos.size());
            string2 = string3;
        }
        TapjoyLog.i(TAPJOY_VIDEO, "videoIDs: [" + string2 + "]");
        TapjoyConnectCore.setVideoIDs(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean validateCachedVideos() {
        boolean bl;
        File[] arrfile = new File(this.videoCacheDir).listFiles();
        if (this.uncachedVideos == null) {
            TapjoyLog.e(TAPJOY_VIDEO, "Error: uncachedVideos is null");
            bl = false;
        } else {
            bl = true;
        }
        if (this.cachedVideos == null) {
            TapjoyLog.e(TAPJOY_VIDEO, "Error: cachedVideos is null");
            bl = false;
        }
        if (this.videoQueue == null) {
            TapjoyLog.e(TAPJOY_VIDEO, "Error: videoQueue is null");
            return (boolean)0;
        }
        int n = 0;
        if (!bl) return (boolean)n;
        n = 0;
        if (arrfile == null) return (boolean)n;
        while (n < arrfile.length) {
            String string2 = arrfile[n].getName();
            TapjoyLog.i(TAPJOY_VIDEO, "-----");
            TapjoyLog.i(TAPJOY_VIDEO, "Examining cached file[" + n + "]: " + arrfile[n].getAbsolutePath() + " --- " + arrfile[n].getName());
            if (this.uncachedVideos.containsKey((Object)string2)) {
                TapjoyLog.i(TAPJOY_VIDEO, "Local file found");
                TapjoyVideoObject tapjoyVideoObject = (TapjoyVideoObject)this.uncachedVideos.get((Object)string2);
                if (tapjoyVideoObject != null) {
                    String string3 = new TapjoyURLConnection().getContentLength(tapjoyVideoObject.videoURL);
                    TapjoyLog.i(TAPJOY_VIDEO, "local file size: " + arrfile[n].length() + " vs. target: " + string3);
                    if (string3 != null && (long)Integer.parseInt((String)string3) == arrfile[n].length()) {
                        tapjoyVideoObject.dataLocation = arrfile[n].getAbsolutePath();
                        this.cachedVideos.put((Object)string2, (Object)tapjoyVideoObject);
                        this.uncachedVideos.remove((Object)string2);
                        this.videoQueue.remove((Object)string2);
                        TapjoyLog.i(TAPJOY_VIDEO, "VIDEO PREVIOUSLY CACHED -- " + string2 + ", location: " + tapjoyVideoObject.dataLocation);
                    } else {
                        TapjoyLog.i(TAPJOY_VIDEO, "file size mismatch --- deleting video: " + arrfile[n].getAbsolutePath());
                        TapjoyUtil.deleteFileOrDirectory(arrfile[n]);
                    }
                }
            } else {
                TapjoyLog.i(TAPJOY_VIDEO, "VIDEO EXPIRED? removing video from cache: " + string2 + " --- " + arrfile[n].getAbsolutePath());
                TapjoyUtil.deleteFileOrDirectory(arrfile[n]);
            }
            ++n;
        }
        return (boolean)1;
    }

    public static void videoNotifierComplete() {
        if (tapjoyVideoNotifier != null) {
            tapjoyVideoNotifier.videoComplete();
        }
    }

    public static void videoNotifierError(int n) {
        if (tapjoyVideoNotifier != null) {
            tapjoyVideoNotifier.videoError(n);
        }
    }

    public static void videoNotifierReady() {
        if (tapjoyVideoNotifier != null) {
            tapjoyVideoNotifier.videoReady();
        }
    }

    public void enableVideoCache(boolean bl) {
    }

    public TapjoyVideoObject getCurrentVideoData() {
        return this.videoToPlay;
    }

    public void init() {
        TapjoyLog.i(TAPJOY_VIDEO, "initVideoAd");
        if (TapjoyConnectCore.getFlagValue("disable_video_offers") != null && TapjoyConnectCore.getFlagValue("disable_video_offers").equals((Object)"true")) {
            TapjoyLog.i(TAPJOY_VIDEO, "disable_video_offers: " + TapjoyConnectCore.getFlagValue("disable_video_offers") + ". Aborting video initializing... ");
            TapjoyConnectCore.setVideoEnabled(false);
            return;
        }
        this.setVideoIDs();
        new Thread(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void run() {
                String string2 = TapjoyConnectCore.getURLParams();
                String string3 = string2 + "&publisher_user_id=" + TapjoyConnectCore.getUserID();
                String string4 = new TapjoyURLConnection().connectToURL("https://ws.tapjoyads.com/videos?", string3);
                boolean bl = false;
                if (string4 != null) {
                    int n = string4.length();
                    bl = false;
                    if (n > 0) {
                        bl = TapjoyVideo.this.handleGetVideosResponse(string4);
                    }
                }
                if (!bl) {
                    TapjoyVideo.videoNotifierError(2);
                    return;
                }
                TapjoyVideo.this.validateCachedVideos();
                if (TapjoyVideo.watermarkURL != null && TapjoyVideo.watermarkURL.length() > 0) {
                    try {
                        URL uRL = new URL(TapjoyVideo.watermarkURL);
                        URLConnection uRLConnection = uRL.openConnection();
                        uRLConnection.setConnectTimeout(15000);
                        uRLConnection.setReadTimeout(25000);
                        uRLConnection.connect();
                        watermarkImage = BitmapFactory.decodeStream((InputStream)uRL.openConnection().getInputStream());
                    }
                    catch (Exception exception) {
                        TapjoyLog.e(TapjoyVideo.TAPJOY_VIDEO, "e: " + exception.toString());
                    }
                }
                TapjoyVideo.this.setVideoIDs();
                TapjoyVideo.this.initialized = true;
                TapjoyVideo.videoNotifierReady();
                if (TapjoyVideo.this.cacheAuto) {
                    TapjoyLog.i(TapjoyVideo.TAPJOY_VIDEO, "trying to cache because of cache_auto flag...");
                    TapjoyVideo.this.cacheAllVideos();
                }
                TapjoyLog.i(TapjoyVideo.TAPJOY_VIDEO, "------------------------------");
                TapjoyLog.i(TapjoyVideo.TAPJOY_VIDEO, "------------------------------");
                TapjoyLog.i(TapjoyVideo.TAPJOY_VIDEO, "INIT DONE!");
                TapjoyLog.i(TapjoyVideo.TAPJOY_VIDEO, "------------------------------");
            }
        }).start();
        TapjoyConnectCore.setVideoEnabled(true);
    }

    public void initVideoAd(TapjoyVideoNotifier tapjoyVideoNotifier) {
        this.initVideoAd(tapjoyVideoNotifier, false);
    }

    public void initVideoAd(TapjoyVideoNotifier tapjoyVideoNotifier, boolean bl) {
        TapjoyVideo.tapjoyVideoNotifier = tapjoyVideoNotifier;
        if (tapjoyVideoNotifier == null) {
            Log.e((String)TAPJOY_VIDEO, (String)"Error during initVideoAd -- TapjoyVideoNotifier is null");
            return;
        }
        if (this.initialized) {
            TapjoyVideo.videoNotifierReady();
        }
        TapjoyVideo.super.cacheAllVideos();
    }

    public void setVideoCacheCount(int n) {
        this.videoCacheLimit = n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean startVideo(String var1_4, String var2_6, String var3_2, String var4_3, String var5_1, String var6_5) {
        TapjoyLog.i("TapjoyVideo", "Starting video activity with video: " + var1_4);
        if (var1_4 == null || var4_3 == null || var5_1 == null || var1_4.length() == 0 || var4_3.length() == 0 || var5_1.length() == 0) {
            TapjoyLog.i("TapjoyVideo", "aborting video playback... invalid or missing parameter");
            return false;
        }
        this.videoToPlay = (TapjoyVideoObject)this.cachedVideos.get((Object)var1_4);
        if (!"mounted".equals((Object)Environment.getExternalStorageState())) {
            TapjoyLog.e("TapjoyVideo", "Cannot access external storage");
            TapjoyVideo.videoNotifierError(1);
            return false;
        }
        if (this.videoToPlay != null) ** GOTO lbl28
        TapjoyLog.i("TapjoyVideo", "video not cached... checking uncached videos");
        this.videoToPlay = (TapjoyVideoObject)this.uncachedVideos.get((Object)var1_4);
        if (this.videoToPlay != null) ** GOTO lbl24
        if (var6_5 != null && var6_5.length() > 0) {
            var12_7 = new TapjoyVideoObject();
            var12_7.offerID = var1_4;
            var12_7.currencyName = var2_6;
            var12_7.currencyAmount = var3_2;
            var12_7.clickURL = var4_3;
            var12_7.webviewURL = var5_1;
            var12_7.videoURL = var6_5;
            this.uncachedVideos.put((Object)var1_4, (Object)var12_7);
            this.videoToPlay = (TapjoyVideoObject)this.uncachedVideos.get((Object)var1_4);
lbl24: // 2 sources:
            var7_8 = false;
        } else {
            TapjoyLog.e("TapjoyVideo", "no video data and no video url - aborting playback...");
            return false;
lbl28: // 1 sources:
            var7_8 = true;
        }
        this.videoToPlay.currencyName = var2_6;
        this.videoToPlay.currencyAmount = var3_2;
        this.videoToPlay.clickURL = var4_3;
        this.videoToPlay.webviewURL = var5_1;
        this.videoToPlay.videoURL = var6_5;
        TapjoyLog.i("TapjoyVideo", "videoToPlay: " + this.videoToPlay.offerID);
        TapjoyLog.i("TapjoyVideo", "amount: " + this.videoToPlay.currencyAmount);
        TapjoyLog.i("TapjoyVideo", "currency: " + this.videoToPlay.currencyName);
        TapjoyLog.i("TapjoyVideo", "clickURL: " + this.videoToPlay.clickURL);
        TapjoyLog.i("TapjoyVideo", "location: " + this.videoToPlay.dataLocation);
        TapjoyLog.i("TapjoyVideo", "webviewURL: " + this.videoToPlay.webviewURL);
        TapjoyLog.i("TapjoyVideo", "videoURL: " + this.videoToPlay.videoURL);
        if (var7_8 && this.videoToPlay.dataLocation != null && ((var11_9 = new File(this.videoToPlay.dataLocation)) == null || !var11_9.exists())) {
            TapjoyLog.e("TapjoyVideo", "video file does not exist.");
            return false;
        }
        var8_10 = new Intent(this.context, TapjoyVideoView.class);
        var8_10.setFlags(268435456);
        var8_10.putExtra("VIDEO_PATH", var1_4);
        this.context.startActivity(var8_10);
        return true;
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.os.AsyncTask
 *  android.os.AsyncTask$Status
 *  android.os.Environment
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewParent
 *  android.view.WindowManager
 *  android.view.WindowManager$BadTokenException
 *  android.widget.Button
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ProgressBar
 *  android.widget.TableLayout
 *  android.widget.TextView
 *  java.io.BufferedInputStream
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.InputStream
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Thread
 *  java.net.SocketTimeoutException
 *  java.net.URL
 *  java.net.URLConnection
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Enumeration
 *  java.util.Hashtable
 *  java.util.List
 *  java.util.concurrent.RejectedExecutionException
 */
package com.tapjoy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import com.tapjoy.DownloadVirtualGood;
import com.tapjoy.TJCVirtualGoods;
import com.tapjoy.TJCVirtualGoodsConnection;
import com.tapjoy.TJCVirtualGoodsData;
import com.tapjoy.TapjoyLog;
import com.tapjoy.VGStoreItem;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

public class TJCVirtualGoodUtil {
    private static final String TAPJOY_VIRTUAL_GOOD_UTIL = "VirtualGoodUtil";
    private static String basePathSaveToPhone;
    private static String basePathSaveToSDCard;
    public static ArrayList<AsyncTask<VGStoreItem, Integer, VGStoreItem>> currentTasks;
    public static ArrayList<Object> pendingTasks;
    private static final int poolSize = 1;
    private static TJCVirtualGoodsConnection tapjoyVGConnection;
    private static TJCVirtualGoodsData tapjoyVGData;
    public static boolean virtualGoodsUIOpened;
    private Context applicationContext = null;
    private String clientPackage = null;
    public int detailIndex = 0;
    String dialogErrorMessage = "";
    private Context downloadPurchasedVGContext = null;
    public View.OnClickListener errorMsgClickListener;
    CheckForVirtualGoodsTask fetchPurchasedVGItems;
    private SQLiteDatabase myDB = null;
    public ArrayList<VGStoreItem> purchaseItems;
    private ArrayList<VGStoreItem> purchasedItemArray;
    public View.OnClickListener retryClickListener;
    View.OnClickListener retryDetailClickListener;
    private String urlParams = null;
    public Hashtable<String, DownloadVirtualGoodTask> virtualGoodsToDownload;

    static {
        tapjoyVGConnection = null;
        tapjoyVGData = null;
        virtualGoodsUIOpened = false;
        currentTasks = new ArrayList();
        pendingTasks = new ArrayList();
    }

    public TJCVirtualGoodUtil(Context context, String string2) {
        File file;
        this.errorMsgClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                TJCVirtualGoodUtil.this.showDLErrorDialog();
            }
        };
        this.retryClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "retryClick");
                int n = Integer.parseInt((String)((TextView)((LinearLayout)view.getParent().getParent()).findViewById(TJCVirtualGoodUtil.this.applicationContext.getResources().getIdentifier("vg_row_index", "id", TJCVirtualGoodUtil.this.clientPackage))).getText().toString());
                TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "retry index: " + n);
                TJCVirtualGoodUtil.this.retryDownloadVG((DownloadVirtualGoodTask)((Object)TJCVirtualGoodUtil.this.virtualGoodsToDownload.get((Object)((VGStoreItem)TJCVirtualGoodUtil.this.purchasedItemArray.get(n)).getVgStoreItemID())));
            }
        };
        this.retryDetailClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "RETRY DETAIL CLICK");
                try {
                    TJCVirtualGoodUtil.this.retryDownloadVG((DownloadVirtualGoodTask)((Object)TJCVirtualGoodUtil.this.virtualGoodsToDownload.get((Object)((VGStoreItem)TJCVirtualGoodUtil.this.purchasedItemArray.get(TJCVirtualGoodUtil.this.detailIndex)).getVgStoreItemID())));
                    return;
                }
                catch (Exception exception) {
                    TapjoyLog.e(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "RETRY DETAIl EXCEPTION: " + exception.toString());
                    return;
                }
            }
        };
        TapjoyLog.i(TAPJOY_VIRTUAL_GOOD_UTIL, "TJCVirtualGoodUtil init");
        this.applicationContext = context;
        this.clientPackage = string2;
        basePathSaveToPhone = "data/data/" + this.clientPackage + "/vgDownloads/";
        String string3 = Environment.getExternalStorageDirectory().toString();
        basePathSaveToSDCard = string3 + "/" + this.clientPackage + "/vgDownloads/";
        this.virtualGoodsToDownload = new Hashtable();
        tapjoyVGData = new TJCVirtualGoodsData(context, this.clientPackage);
        this.myDB = TJCVirtualGoodsData.TapjoyDatabaseUtil.getTapjoyDatabase(context);
        File file2 = new File("data/data/" + this.clientPackage + "/vgDownloads");
        if (!file2.exists() && file2.mkdir()) {
            TapjoyLog.i(TAPJOY_VIRTUAL_GOOD_UTIL, "vgDownloads directory created at device.....");
        }
        if (!(file = new File("data/data/" + this.clientPackage + "/tempZipDownloads")).exists() && file.mkdir()) {
            TapjoyLog.i(TAPJOY_VIRTUAL_GOOD_UTIL, "temporary zip file directory generated at device");
        }
        TapjoyLog.i(TAPJOY_VIRTUAL_GOOD_UTIL, "TJCVirtualGoodUtil init DONE");
    }

    static /* synthetic */ String access$500() {
        return basePathSaveToSDCard;
    }

    static /* synthetic */ String access$600() {
        return basePathSaveToPhone;
    }

    public static /* varargs */ boolean addTask(AsyncTask<VGStoreItem, Integer, VGStoreItem> asyncTask, VGStoreItem ... arrvGStoreItem) {
        TapjoyLog.i(TAPJOY_VIRTUAL_GOOD_UTIL, "addTask size: " + currentTasks.size() + ", pending size: " + pendingTasks.size());
        if (currentTasks.size() < 1) {
            currentTasks.add(asyncTask);
            if (arrvGStoreItem != null) {
                TapjoyLog.i(TAPJOY_VIRTUAL_GOOD_UTIL, "execute with params");
                asyncTask.execute((Object[])arrvGStoreItem);
                return true;
            }
            try {
                TapjoyLog.i(TAPJOY_VIRTUAL_GOOD_UTIL, "execute");
                asyncTask.execute((Object[])new VGStoreItem[0]);
                return true;
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                return true;
            }
        }
        Object[] arrobject = new Object[]{asyncTask, arrvGStoreItem};
        pendingTasks.add((Object)arrobject);
        return true;
    }

    public static boolean removeTask(AsyncTask<VGStoreItem, Integer, VGStoreItem> asyncTask) {
        if (currentTasks.contains(asyncTask)) {
            currentTasks.remove(asyncTask);
            return true;
        }
        return false;
    }

    public void cancelExecution() {
        Enumeration enumeration = this.virtualGoodsToDownload.keys();
        while (enumeration.hasMoreElements()) {
            String string2 = (String)enumeration.nextElement();
            DownloadVirtualGoodTask downloadVirtualGoodTask = (DownloadVirtualGoodTask)((Object)this.virtualGoodsToDownload.get((Object)string2));
            if (downloadVirtualGoodTask != null && downloadVirtualGoodTask.getStatus() == AsyncTask.Status.RUNNING) {
                downloadVirtualGoodTask.cancel(true);
            }
            this.virtualGoodsToDownload.remove((Object)string2);
        }
    }

    public void checkForVirtualGoods(Context context, String string2, String string3) {
        TapjoyLog.i(TAPJOY_VIRTUAL_GOOD_UTIL, "checkForVirtualGoods");
        if (tapjoyVGConnection == null) {
            this.clientPackage = string3;
            this.urlParams = string2;
            tapjoyVGConnection = new TJCVirtualGoodsConnection("https://ws.tapjoyads.com/", this.urlParams);
        }
        this.downloadPurchasedVGContext = context;
        this.purchasedItemArray = new ArrayList();
        new Thread((Runnable)new CheckForVirtualGoodsTask((TJCVirtualGoodUtil)this, null)).start();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void downLoadPurcahasedVirtualGood(List<VGStoreItem> list, TableLayout tableLayout, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        Cursor cursor = this.myDB.rawQuery("SELECT VGStoreItemID FROM tapjoy_VGStoreItems", null);
        int n2 = cursor.getColumnIndex("VGStoreItemID");
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.isFirst()) {
                do {
                    String string2 = cursor.getString(n2);
                    stringBuilder.append(string2 + ",");
                } while (cursor.moveToNext());
            }
        }
        cursor.deactivate();
        cursor.close();
        String string3 = stringBuilder.length() > 0 ? stringBuilder.substring(0, -1 + stringBuilder.length()) : "";
        this.purchaseItems = (ArrayList)list;
        while (n < list.size()) {
            VGStoreItem vGStoreItem = (VGStoreItem)list.get(n);
            if (vGStoreItem != null && string3.indexOf(vGStoreItem.getVgStoreItemID()) == -1) {
                TapjoyLog.i(TAPJOY_VIRTUAL_GOOD_UTIL, "download this purchased vg: " + vGStoreItem.getVgStoreItemID() + ", name: " + vGStoreItem.getName());
                DownloadVirtualGoodTask downloadVirtualGoodTask = (TJCVirtualGoodUtil)this.new DownloadVirtualGoodTask();
                View view = tableLayout.getChildAt(n * 2);
                if (view instanceof LinearLayout) {
                    LinearLayout linearLayout = (LinearLayout)view;
                    downloadVirtualGoodTask.localProgressBar = (ProgressBar)linearLayout.findViewById(this.applicationContext.getResources().getIdentifier("vg_row_progress_bar", "id", this.clientPackage));
                    downloadVirtualGoodTask.localProgressBar.setVisibility(8);
                    downloadVirtualGoodTask.localDownloadStatusText = (TextView)linearLayout.findViewById(this.applicationContext.getResources().getIdentifier("vg_row_download_status_text", "id", this.clientPackage));
                    downloadVirtualGoodTask.localDownloadStatusText.setText((CharSequence)"Download Pending");
                    downloadVirtualGoodTask.localRetryButton = (Button)linearLayout.findViewById(this.applicationContext.getResources().getIdentifier("vg_row_retry_button", "id", this.clientPackage));
                    downloadVirtualGoodTask.localErrorIcon = (ImageView)linearLayout.findViewById(this.applicationContext.getResources().getIdentifier("vg_row_error_icon", "id", this.clientPackage));
                    downloadVirtualGoodTask.localRetryButton.setOnClickListener(this.retryClickListener);
                    downloadVirtualGoodTask.localErrorIcon.setOnClickListener(this.errorMsgClickListener);
                    this.virtualGoodsToDownload.put((Object)vGStoreItem.getVgStoreItemID(), (Object)downloadVirtualGoodTask);
                    TJCVirtualGoodUtil.addTask(downloadVirtualGoodTask, vGStoreItem);
                }
            }
            ++n;
        }
        return;
    }

    public Hashtable<String, DownloadVirtualGoodTask> getdownloadVirtualGoods() {
        return this.virtualGoodsToDownload;
    }

    public boolean removeAndExecuteNext(AsyncTask<VGStoreItem, Integer, VGStoreItem> asyncTask) {
        TJCVirtualGoodUtil.removeTask(asyncTask);
        if (pendingTasks.size() > 0 && currentTasks.size() < 1) {
            Object[] arrobject = (Object[])pendingTasks.get(0);
            pendingTasks.remove((Object)arrobject);
            TJCVirtualGoodUtil.addTask((DownloadVirtualGoodTask)((Object)arrobject[0]), (VGStoreItem[])arrobject[1]);
        }
        return false;
    }

    public void retryDownloadVG(DownloadVirtualGoodTask downloadVirtualGoodTask) {
        DownloadVirtualGoodTask downloadVirtualGoodTask2 = (TJCVirtualGoodUtil)this.new DownloadVirtualGoodTask();
        TapjoyLog.i(TAPJOY_VIRTUAL_GOOD_UTIL, "RETRY DOWNLOAD VG: " + downloadVirtualGoodTask.vgItem.getName());
        downloadVirtualGoodTask2.localProgressBar = downloadVirtualGoodTask.localProgressBar;
        downloadVirtualGoodTask2.localProgressBar.setVisibility(8);
        downloadVirtualGoodTask2.localProgressBar.setProgress(0);
        downloadVirtualGoodTask2.localErrorIcon = downloadVirtualGoodTask.localErrorIcon;
        downloadVirtualGoodTask2.localDownloadStatusText = downloadVirtualGoodTask.localDownloadStatusText;
        downloadVirtualGoodTask2.localRetryButton = downloadVirtualGoodTask.localRetryButton;
        downloadVirtualGoodTask2.localDownloadStatusText = downloadVirtualGoodTask.localDownloadStatusText;
        downloadVirtualGoodTask2.localDownloadStatusText.setText((CharSequence)"Download Pending");
        downloadVirtualGoodTask2.localRetryButton.setVisibility(8);
        downloadVirtualGoodTask2.localErrorIcon.setVisibility(8);
        downloadVirtualGoodTask2.virtualGoodDownloadStatus = 0;
        VGStoreItem vGStoreItem = downloadVirtualGoodTask.vgItem;
        if (downloadVirtualGoodTask.getStatus() == AsyncTask.Status.RUNNING) {
            downloadVirtualGoodTask.cancel(true);
        }
        this.virtualGoodsToDownload.remove((Object)vGStoreItem.getVgStoreItemID());
        this.virtualGoodsToDownload.put((Object)vGStoreItem.getVgStoreItemID(), (Object)downloadVirtualGoodTask2);
        if (TJCVirtualGoods.isPurchasedItemDetailView && TJCVirtualGoods.detailViewStoreID.equals((Object)vGStoreItem.getVgStoreItemID())) {
            TapjoyLog.i(TAPJOY_VIRTUAL_GOOD_UTIL, "UPDATE DETAIL VIEW");
            TJCVirtualGoods.updateDetailViewFromDownloader(downloadVirtualGoodTask2);
        }
        TJCVirtualGoodUtil.addTask(downloadVirtualGoodTask2, vGStoreItem);
    }

    public void setDetailIndex(int n) {
        this.detailIndex = n;
    }

    public void setPurchasedItemArray(ArrayList<VGStoreItem> arrayList) {
        this.purchasedItemArray = arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void showDLErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.applicationContext);
        if (this.dialogErrorMessage.equals((Object)"")) {
            builder.setMessage((CharSequence)"An error occured while downloading the contents of acquired item.");
        } else {
            builder.setMessage((CharSequence)this.dialogErrorMessage);
        }
        builder.setNegativeButton((CharSequence)"OK", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        try {
            alertDialog.show();
            return;
        }
        catch (WindowManager.BadTokenException badTokenException) {
            return;
        }
    }

    private class CheckForVirtualGoodsTask
    implements Runnable {
        final /* synthetic */ TJCVirtualGoodUtil this$0;

        private CheckForVirtualGoodsTask(TJCVirtualGoodUtil tJCVirtualGoodUtil) {
            this.this$0 = tJCVirtualGoodUtil;
        }

        /* synthetic */ CheckForVirtualGoodsTask(TJCVirtualGoodUtil tJCVirtualGoodUtil, 1 var2_2) {
            super(tJCVirtualGoodUtil);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void run() {
            VGStoreItem vGStoreItem;
            block9 : {
                ArrayList<VGStoreItem> arrayList;
                if (TJCVirtualGoodUtil.virtualGoodsUIOpened) {
                    TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "virtual goods UI is already open -- aborting check for virtual goods");
                }
                try {
                    ArrayList<VGStoreItem> arrayList2;
                    TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "fetchPurchasedVGItems");
                    String string2 = tapjoyVGConnection.getAllPurchasedItemsFromServer(0, 25);
                    if (string2 == null) return;
                    if (string2.length() <= 0) return;
                    arrayList = arrayList2 = TJCVirtualGoodsData.parseVGItemListResponse(string2, 1, this.this$0.applicationContext);
                }
                catch (Exception exception) {
                    return;
                }
                if (arrayList == null) return;
                this.this$0.purchasedItemArray.addAll(arrayList);
                ArrayList arrayList3 = new ArrayList();
                Cursor cursor = TJCVirtualGoodsData.TapjoyDatabaseUtil.getTapjoyDatabase(this.this$0.applicationContext).rawQuery("SELECT VGStoreItemID FROM tapjoy_VGStoreItems", null);
                int n = cursor.getColumnIndex("VGStoreItemID");
                if (cursor != null) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        do {
                            arrayList3.add((Object)new String(cursor.getString(n)));
                        } while (cursor.moveToNext());
                    }
                }
                if (cursor != null) {
                    cursor.deactivate();
                    cursor.close();
                }
                int n2 = 0;
                while (n2 < this.this$0.purchasedItemArray.size()) {
                    vGStoreItem = (VGStoreItem)this.this$0.purchasedItemArray.get(n2);
                    if (vGStoreItem == null || arrayList3.contains((Object)vGStoreItem.getVgStoreItemID())) {
                        ++n2;
                        continue;
                    }
                    break block9;
                }
                return;
            }
            TJCVirtualGoodUtil.virtualGoodsUIOpened = true;
            Intent intent = new Intent(this.this$0.downloadPurchasedVGContext, DownloadVirtualGood.class);
            intent.setFlags(268435456);
            intent.putExtra("NAME", vGStoreItem.getName());
            intent.putExtra("URL_PARAMS", this.this$0.urlParams);
            TJCVirtualGoods.doNotify = false;
            this.this$0.downloadPurchasedVGContext.startActivity(intent);
        }
    }

    public class DownloadVirtualGoodTask
    extends AsyncTask<VGStoreItem, Integer, VGStoreItem> {
        public boolean downloadCancel = false;
        public TextView localDownloadStatusText;
        public ImageView localErrorIcon;
        public ProgressBar localProgressBar;
        public Button localRetryButton;
        public int progressPercent = 0;
        boolean saveToSDCard = false;
        private VGStoreItem vgItem = null;
        public int virtualGoodDownloadStatus;

        public DownloadVirtualGoodTask() {
            this.saveToSDCard = false;
            this.virtualGoodDownloadStatus = 0;
        }

        public DownloadVirtualGoodTask(boolean bl) {
            this.saveToSDCard = bl;
            this.virtualGoodDownloadStatus = 0;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private boolean downLoadVirtualGood(VGStoreItem var1, boolean var2_2) {
            block36 : {
                block35 : {
                    block38 : {
                        TapjoyLog.i("VirtualGoodUtil", "downloadVirtualGood: " + var1.getName() + ", sd_download: " + var2_2);
                        TapjoyLog.i("VirtualGoodUtil", "Downloading virtual good data for: " + var1.getName());
                        var3_3 = var1.getDatafileUrl();
                        var4_4 = null;
                        this.virtualGoodDownloadStatus = 1;
                        var5_5 = new Integer[]{0};
                        this.publishProgress(var5_5);
                        if (var3_3.length() != 0) break block38;
                        TapjoyLog.i("VirtualGoodUtil", "No zip file associated with virtual good.");
                        var7_6 = true;
                        break block36;
                    }
                    var6_11 = var3_3.lastIndexOf("/");
                    var4_4 = null;
                    var7_6 = false;
                    if (var6_11 <= -1) break block36;
                    var8_12 = var3_3.length();
                    var4_4 = null;
                    var7_6 = false;
                    if (var8_12 <= var6_11) break block36;
                    var9_13 = var3_3.substring(var6_11 + 1, var3_3.length());
                    var4_4 = null;
                    var10_14 = null;
                    if (var9_13 != null) {
                        var47_15 = var9_13.equals((Object)"");
                        var4_4 = null;
                        var10_14 = null;
                        if (!var47_15) {
                            var9_13 = var9_13.substring(0, -4 + var9_13.length());
                            var48_16 = Environment.getExternalStorageDirectory().toString();
                            TapjoyLog.i("VirtualGoodUtil", "externalRoot: " + var48_16);
                            if (this.saveToSDCard) {
                                var10_14 = var48_16 + "/" + TJCVirtualGoodUtil.access$100(TJCVirtualGoodUtil.this) + "/tempZipDownloads/";
                                var4_4 = TJCVirtualGoodUtil.access$500() + var9_13 + "/";
                            } else {
                                var10_14 = "data/data/" + TJCVirtualGoodUtil.access$100(TJCVirtualGoodUtil.this) + "/tempZipDownloads/";
                                var4_4 = TJCVirtualGoodUtil.access$600() + var9_13 + "/";
                            }
                        }
                    }
                    TapjoyLog.i("VirtualGoodUtil", "downloading zip file: " + var3_3);
                    var33_17 = new URL(var3_3).openConnection();
                    var33_17.setConnectTimeout(15000);
                    var33_17.setReadTimeout(30000);
                    var33_17.connect();
                    var17_18 = new BufferedInputStream(var33_17.getInputStream());
                    var35_19 = var33_17.getContentLength();
                    var36_20 = new File(var10_14);
                    if (var36_20.mkdirs()) {
                        TapjoyLog.i("VirtualGoodUtil", "created directory at: " + var36_20.getPath());
                    }
                    var37_21 = new File(var10_14, var9_13 + ".zip");
                    var18_22 = new FileOutputStream(var37_21);
                    try {
                        var38_23 = new byte[1024];
                        var39_24 = 0L;
                        while ((var41_25 = var17_18.read(var38_23)) != -1) {
                            var46_26 = new Integer[]{(int)(90L * (var39_24 += (long)var41_25) / (long)var35_19)};
                            this.publishProgress(var46_26);
                            var18_22.write(var38_23, 0, var41_25);
                        }
                        var18_22.close();
                        var17_18.close();
                        TapjoyLog.i("VirtualGoodUtil", "ZIP FILE SIZE: " + var37_21.length());
                        var42_37 = var37_21.length();
                        ** GOTO lbl69
                    }
                    catch (SocketTimeoutException var11_27) {
                        block33 : {
                            block34 : {
                                var12_31 = var18_22;
                                var13_32 = var17_18;
                                break block34;
lbl69: // 1 sources:
                                var44_38 = var42_37 LCMP 0L;
                                var45_36 = false;
                                if (var44_38 == false) {
                                    var45_36 = true;
                                }
                                var16_35 = var45_36;
                                var14_33 = true;
                                break block33;
                                catch (Exception var31_42) {
                                    block32 : {
                                        var17_18 = null;
                                        var18_22 = null;
                                        var32_40 = var31_42;
                                        break block32;
                                        catch (Exception var34_39) {
                                            var32_40 = var34_39;
                                            var18_22 = null;
                                            break block32;
                                        }
                                        catch (Exception var32_41) {}
                                    }
                                    TapjoyLog.e("VirtualGoodUtil", "Error downloading zip file: " + var32_40.toString());
                                    var14_33 = false;
                                    var16_35 = false;
                                    break block33;
                                }
                                catch (SocketTimeoutException var11_29) {
                                    var12_31 = null;
                                    var13_32 = null;
                                    break block34;
                                }
                                catch (SocketTimeoutException var11_30) {
                                    var13_32 = var17_18;
                                    var12_31 = null;
                                }
                            }
                            TapjoyLog.e("VirtualGoodUtil", "Network timeout: " + var11_28.toString());
                            var14_33 = false;
                            var15_34 = var12_31;
                            var16_35 = true;
                            var17_18 = var13_32;
                            var18_22 = var15_34;
                        }
                        if (!var16_35) break block35;
                        TapjoyLog.i("VirtualGoodUtil", "zip file not downloaded");
                        this.virtualGoodDownloadStatus = this.saveToSDCard != false ? 41 : 42;
                        try {
                            var17_18.close();
                            var18_22.close();
                            var7_6 = false;
                        }
                        catch (Exception var30_43) {
                            var7_6 = false;
                        }
                        break block36;
                    }
                }
                TapjoyLog.i("VirtualGoodUtil", "No network error.");
                if (var14_33) {
                    var19_44 = new File(var10_14 + var9_13 + ".zip");
                    if (TJCVirtualGoodUtil.access$400().extractZipFileToFolder(var19_44, var4_4)) {
                        for (var28_45 = 90; var28_45 <= 100; ++var28_45) {
                            var29_46 = new Integer[]{var28_45};
                            this.publishProgress(var29_46);
                        }
                        var20_47 = true;
                    } else if (this.saveToSDCard) {
                        this.virtualGoodDownloadStatus = 43;
                        var20_47 = false;
                    } else {
                        this.virtualGoodDownloadStatus = 44;
                        var20_47 = false;
                    }
                    var7_6 = var20_47;
                } else if (this.saveToSDCard) {
                    this.virtualGoodDownloadStatus = 43;
                    var7_6 = false;
                } else {
                    this.virtualGoodDownloadStatus = 44;
                    var7_6 = false;
                }
            }
            if (var7_6) {
                TJCVirtualGoodUtil.access$400().saveInfo(var1, this.saveToSDCard);
                this.virtualGoodDownloadStatus = this.saveToSDCard != false ? 20 : 10;
                var1.setDatafileUrl(var4_4);
                if (var3_3.length() > 0) {
                    TJCVirtualGoodUtil.access$400().deleteVGZip(var1, this.saveToSDCard);
                }
                var21_7 = new Integer[]{100};
                this.publishProgress(var21_7);
                try {
                    var23_8 = var1.getNumberOwned();
                    var24_9 = TJCVirtualGoodsData.TapjoyDatabaseUtil.getTapjoyDatabase(TJCVirtualGoodUtil.access$200(TJCVirtualGoodUtil.this));
                    var25_10 = new StringBuilder();
                    var25_10.append("UPDATE tapjoy_VGStoreItemAttribute SET AttributeValue='" + var23_8 + "' ");
                    var25_10.append("WHERE VGStoreItemID='" + var1.getVgStoreItemID() + "' AND AttributeName='quantity'");
                    var24_9.execSQL(var25_10.toString());
                }
                catch (Exception var22_48) {}
            }
            TapjoyLog.i("VirtualGoodUtil", "downloadVirtualGood success: " + var7_6);
            return var7_6;
        }

        protected /* varargs */ VGStoreItem doInBackground(VGStoreItem ... arrvGStoreItem) {
            TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "doInBackground");
            this.vgItem = arrvGStoreItem[0];
            DownloadVirtualGoodTask.super.downLoadVirtualGood(this.vgItem, this.saveToSDCard);
            return this.vgItem;
        }

        protected void onCancelled() {
            TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "onCancelled");
            super.onCancelled();
            if (!this.downloadCancel) {
                tapjoyVGData.deleteVGZip(this.vgItem, this.saveToSDCard);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void onPostExecute(VGStoreItem vGStoreItem) {
            TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "onPostExecute: " + this.virtualGoodDownloadStatus);
            switch (this.virtualGoodDownloadStatus) {
                case 10: 
                case 20: {
                    this.localRetryButton.setVisibility(8);
                    this.localErrorIcon.setVisibility(8);
                    this.localProgressBar.setVisibility(8);
                    this.localDownloadStatusText.setText((CharSequence)"Download Completed");
                    TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "*** Download completed! ***");
                    if (TJCVirtualGoods.getVirtualGoodDownloadListener() == null) break;
                    TJCVirtualGoods.getVirtualGoodDownloadListener().onDownLoad(vGStoreItem);
                    break;
                }
                case 41: 
                case 42: {
                    this.localRetryButton.setVisibility(0);
                    this.localErrorIcon.setVisibility(0);
                    this.localProgressBar.setVisibility(8);
                    this.localDownloadStatusText.setText((CharSequence)"Download Failed");
                    TJCVirtualGoodUtil.this.dialogErrorMessage = "Download Failed";
                    AlertDialog.Builder builder = new AlertDialog.Builder(TJCVirtualGoodUtil.this.applicationContext);
                    builder.setTitle((CharSequence)(this.vgItem.getName() + " fail to download. Would you like to download again?"));
                    builder.setNegativeButton((CharSequence)"Cancel", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            dialogInterface.cancel();
                            DownloadVirtualGoodTask.this.cancel(true);
                        }
                    });
                    builder.setPositiveButton((CharSequence)"Retry", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            DownloadVirtualGoodTask.this.cancel(true);
                            TJCVirtualGoodUtil.this.retryDownloadVG(DownloadVirtualGoodTask.this);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    try {
                        alertDialog.show();
                    }
                    catch (WindowManager.BadTokenException badTokenException) {}
                    break;
                }
                case 43: {
                    this.localRetryButton.setVisibility(0);
                    this.localErrorIcon.setVisibility(0);
                    this.localProgressBar.setVisibility(8);
                    this.localDownloadStatusText.setText((CharSequence)"Download Failed");
                    TJCVirtualGoodUtil.this.dialogErrorMessage = "No more space is available on Device and SD Card.";
                    if (TJCVirtualGoods.isPurchasedItemDetailView && TJCVirtualGoods.detailViewStoreID.equals((Object)vGStoreItem.getVgStoreItemID())) {
                        TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "UPDATE DETAIL VIEW");
                        TJCVirtualGoods.updateDetailViewFromDownloader((DownloadVirtualGoodTask)this);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(TJCVirtualGoodUtil.this.applicationContext);
                    builder.setMessage((CharSequence)"Not enough space on the device or SD card.  Free more space and try again.");
                    builder.setPositiveButton((CharSequence)"OK", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    try {
                        alertDialog.show();
                    }
                    catch (WindowManager.BadTokenException badTokenException) {}
                    break;
                }
                case 44: {
                    if (this.getStatus() == AsyncTask.Status.RUNNING) {
                        this.cancel(true);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(TJCVirtualGoodUtil.this.applicationContext);
                    builder.setMessage((CharSequence)("Not enough space on the device. Would you like to download Item '" + this.vgItem.getName() + "' on SD card?"));
                    builder.setNegativeButton((CharSequence)"No", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            dialogInterface.cancel();
                            DownloadVirtualGoodTask.this.localRetryButton.setVisibility(0);
                            DownloadVirtualGoodTask.this.localErrorIcon.setVisibility(0);
                            DownloadVirtualGoodTask.this.localProgressBar.setVisibility(8);
                            DownloadVirtualGoodTask.this.localDownloadStatusText.setText((CharSequence)"Download Failed");
                            TJCVirtualGoodUtil.this.dialogErrorMessage = "No more space is available on Device.";
                        }
                    });
                    builder.setPositiveButton((CharSequence)"Yes", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            DownloadVirtualGoodTask downloadVirtualGoodTask = new DownloadVirtualGoodTask(true);
                            TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "RETRY DOWNLOAD VG: " + DownloadVirtualGoodTask.this.vgItem.getName());
                            downloadVirtualGoodTask.localProgressBar = DownloadVirtualGoodTask.this.localProgressBar;
                            downloadVirtualGoodTask.localProgressBar.setVisibility(8);
                            downloadVirtualGoodTask.localProgressBar.setProgress(0);
                            downloadVirtualGoodTask.localErrorIcon = DownloadVirtualGoodTask.this.localErrorIcon;
                            downloadVirtualGoodTask.localDownloadStatusText = DownloadVirtualGoodTask.this.localDownloadStatusText;
                            downloadVirtualGoodTask.localRetryButton = DownloadVirtualGoodTask.this.localRetryButton;
                            downloadVirtualGoodTask.localDownloadStatusText = DownloadVirtualGoodTask.this.localDownloadStatusText;
                            downloadVirtualGoodTask.localDownloadStatusText.setText((CharSequence)"Download Pending");
                            downloadVirtualGoodTask.localRetryButton.setVisibility(8);
                            downloadVirtualGoodTask.localErrorIcon.setVisibility(8);
                            downloadVirtualGoodTask.virtualGoodDownloadStatus = 0;
                            VGStoreItem vGStoreItem = DownloadVirtualGoodTask.this.vgItem;
                            if (DownloadVirtualGoodTask.this.getStatus() == AsyncTask.Status.RUNNING) {
                                DownloadVirtualGoodTask.this.cancel(true);
                            }
                            TJCVirtualGoodUtil.this.virtualGoodsToDownload.remove((Object)vGStoreItem.getVgStoreItemID());
                            TJCVirtualGoodUtil.this.virtualGoodsToDownload.put((Object)vGStoreItem.getVgStoreItemID(), (Object)downloadVirtualGoodTask);
                            if (TJCVirtualGoods.isPurchasedItemDetailView && TJCVirtualGoods.detailViewStoreID.equals((Object)vGStoreItem.getVgStoreItemID())) {
                                TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "UPDATE DETAIL VIEW");
                                TJCVirtualGoods.updateDetailViewFromDownloader(downloadVirtualGoodTask);
                            }
                            TJCVirtualGoodUtil.addTask(downloadVirtualGoodTask, vGStoreItem);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    try {
                        alertDialog.show();
                        break;
                    }
                    catch (WindowManager.BadTokenException badTokenException) {
                        break;
                    }
                }
            }
            if (TJCVirtualGoods.isPurchasedItemDetailView && TJCVirtualGoods.detailViewStoreID.equals((Object)vGStoreItem.getVgStoreItemID())) {
                TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "UPDATE DETAIL VIEW");
                TJCVirtualGoods.updateDetailViewFromDownloader((DownloadVirtualGoodTask)this);
            }
            TJCVirtualGoodUtil.this.removeAndExecuteNext((AsyncTask<VGStoreItem, Integer, VGStoreItem>)this);
            TapjoyLog.i(TJCVirtualGoodUtil.TAPJOY_VIRTUAL_GOOD_UTIL, "currentTask size: " + TJCVirtualGoodUtil.currentTasks.size());
            if ((this.virtualGoodDownloadStatus == 20 || this.virtualGoodDownloadStatus == 10) && TJCVirtualGoodUtil.this.virtualGoodsToDownload.containsKey((Object)vGStoreItem.getVgStoreItemID())) {
                TJCVirtualGoodUtil.this.virtualGoodsToDownload.remove((Object)vGStoreItem.getVgStoreItemID());
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        protected /* varargs */ void onProgressUpdate(Integer ... arrinteger) {
            int n = 100;
            int n2 = arrinteger[0];
            if (n2 < 0) {
                n2 = 0;
            }
            if (n2 <= n) {
                n = n2;
            }
            this.localDownloadStatusText.setText((CharSequence)("Downloading... " + n + "%"));
            this.localProgressBar.setVisibility(0);
            this.localProgressBar.setProgress(n);
            this.progressPercent = n;
            if (TJCVirtualGoods.isPurchasedItemDetailView && TJCVirtualGoods.detailViewStoreID.equals((Object)this.vgItem.getVgStoreItemID())) {
                TJCVirtualGoods.updateDetailProgressBar(n);
            }
        }

    }

}


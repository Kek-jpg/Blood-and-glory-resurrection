/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Color
 *  android.graphics.Typeface
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.GradientDrawable
 *  android.graphics.drawable.GradientDrawable$Orientation
 *  android.graphics.drawable.StateListDrawable
 *  android.os.AsyncTask
 *  android.os.AsyncTask$Status
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.StatFs
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowManager
 *  android.view.WindowManager$BadTokenException
 *  android.widget.Button
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ProgressBar
 *  android.widget.RelativeLayout
 *  android.widget.ScrollView
 *  android.widget.TabHost
 *  android.widget.TabHost$TabSpec
 *  android.widget.TableLayout
 *  android.widget.TextView
 *  java.io.BufferedInputStream
 *  java.io.InputStream
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Void
 *  java.net.URL
 *  java.net.URLConnection
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Hashtable
 *  java.util.List
 *  java.util.concurrent.RejectedExecutionException
 */
package com.tapjoy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import com.tapjoy.TJCOffersWebView;
import com.tapjoy.TJCVirtualGoodUtil;
import com.tapjoy.TJCVirtualGoodsConnection;
import com.tapjoy.TJCVirtualGoodsData;
import com.tapjoy.TapjoyLog;
import com.tapjoy.VGStoreItem;
import com.tapjoy.VGStoreItemAttributeValue;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

public class TJCVirtualGoods
extends Activity
implements View.OnClickListener {
    private static final String BUNDLE_URL_PARAMS = "bundle_url_params";
    public static final String EXTRA_MY_ITEMS_TAB = "my_items_tab";
    public static final int NETWORK_DOWN = 11;
    public static final String NETWORK_DOWN_MESSAGE = "Service is unreachable.\nDo you want to try again?";
    public static final int NETWORK_TIME_OUT = 12;
    public static final int PARSER_CONFIGURATION_EXCEPTION = 14;
    private static final int PURCHASED_ITEM = 2;
    public static final int RESPONSE_FAIL = 13;
    public static final int SAX_EXCEPTION = 15;
    private static final int STORE_ITEM = 0;
    public static final String TAPJOY = "Tapjoy";
    public static final String TAPJOY_DATABASE = "Database";
    public static final String TAPJOY_DOWNLOADVIRTUALGOOD = "DownloadVirtualGood";
    public static final String TAPJOY_DOWNLOAD_COMPLETED = "Download Completed";
    public static final String TAPJOY_DOWNLOAD_FAILED = "Download Failed";
    public static final String TAPJOY_DOWNLOAD_PENDING = "Download Pending";
    public static final String TAPJOY_ERROR_NO_SPACE_ON_DEVICE = "No more space is available on Device.";
    public static final String TAPJOY_ERROR_NO_SPACE_ON_DEVICE_AND_SD_CARD = "No more space is available on Device and SD Card.";
    public static final String TAPJOY_FILE_SYSTEM = "File System";
    public static final String TAPJOY_VIRTUAL_GOODS = "Virtual Goods";
    public static final int VG_STATUS_DOWNLOAD_INIT = 1;
    public static final int VG_STATUS_DOWNLOAD_PENDING = 0;
    public static final int VG_STATUS_DOWNLOAD_SUCCESS_TO_PHONE = 10;
    public static final int VG_STATUS_DOWNLOAD_SUCCESS_TO_SD_CARD = 20;
    public static final int VG_STATUS_INSUFFICIENT_DISK_SPACE_PHONE = 44;
    public static final int VG_STATUS_INSUFFICIENT_DISK_SPACE_SD_CARD = 43;
    public static final int VG_STATUS_NETWORK_ERROR_PHONE = 42;
    public static final int VG_STATUS_NETWORK_ERROR_SD_CARD = 41;
    private static String clientPackage;
    static Context ctx;
    private static String currencyName;
    static boolean dataSavedAtSDCard;
    public static TextView detailDescQuantity;
    private static TextView detailDownloadStatusTextView;
    private static ImageView detailErrorIcon;
    private static ProgressBar detailProgressBar;
    public static Button detailRetryButton;
    public static String detailViewStoreID;
    public static boolean doNotify;
    private static boolean downloadStarted;
    public static boolean isPurchasedItemDetailView;
    public static boolean offersFromVG;
    private static TextView storeNoData;
    private static String tapPoints;
    public static TJCVirtualGoodsConnection tapjoyVGConnection;
    private static TapjoyDownloadListener tapjoyVGDownloadListener;
    private static FocusListener tapjoyVGFocusListener;
    private static String urlParams;
    private static String userID;
    public static boolean vgFromDownloadedVG;
    public static boolean vgFromOffers;
    private static TextView yourItemNoData;
    AlertDialog allItemAlert = null;
    AsyncTaskPool asyncTaskPool = new AsyncTaskPool(2);
    private Boolean checkPurchased = true;
    float[] cornerAll = new float[]{10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f};
    float[] cornerLeft = new float[]{10.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f, 10.0f, 10.0f};
    float[] cornerNone = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    float[] cornerRight = new float[]{0.0f, 0.0f, 10.0f, 10.0f, 10.0f, 10.0f, 0.0f, 0.0f};
    private VGStoreItem currentVgStoreItem = null;
    private Button detailGetThisItemButton;
    private LinearLayout detailView;
    private Dialog exceptionDialog;
    private FetchAndBuildPurchasedItemsTask fetPurchasedVGItems = null;
    private FetchAndBuildStoreItemsTask fetchAllStoreItems = null;
    private Dialog getItemDialog;
    private Dialog getMoreItemDialog;
    private PurchaseVirtualGoodTask getPurchasedVGItems = null;
    private boolean isItemDetailView = false;
    private Dialog itemDownloadedDialog;
    private Button morePurchasedItem;
    private Button moreStoreItems;
    private TextView myItemsHeaderLeftTitle = null;
    private TextView myItemsHeaderRightTitle = null;
    private ProgressDialog progressDialog;
    AlertDialog purchasedItemAlert = null;
    private ArrayList<VGStoreItem> purchasedItemArray = null;
    private ProgressBar purchasedItemProgress;
    private int purchasedItemStart;
    private TableLayout purchasedItemTable;
    private int purchasedThroughAvailableItem = 0;
    TextView quantityTextView;
    private TextView storeHeaderLeftTitle = null;
    private TextView storeHeaderRightTitle = null;
    private ArrayList<VGStoreItem> storeItemArray = null;
    private int storeItemStart;
    private TableLayout storeItemTable;
    private ProgressBar storeProgress;
    private TabHost tabs;
    private TextView vgDetailFooterLeft = null;
    private Button vgDetailGetMoreBtn = null;
    private TextView vgFooterLeft = null;
    private Button vgGetMoreBtn = null;
    private TextView vgPurchasedFooterLeft = null;
    private Button vgPurchasedGetMoreBtn = null;
    private TJCVirtualGoodUtil virtualGoodUtil;

    static {
        tapjoyVGConnection = null;
        tapjoyVGDownloadListener = null;
        tapjoyVGFocusListener = null;
        ctx = null;
        doNotify = true;
        offersFromVG = false;
        vgFromOffers = false;
        vgFromDownloadedVG = false;
        userID = null;
        downloadStarted = false;
        isPurchasedItemDetailView = false;
        detailViewStoreID = "";
    }

    static /* synthetic */ int access$1202(TJCVirtualGoods tJCVirtualGoods, int n) {
        tJCVirtualGoods.storeItemStart = n;
        return n;
    }

    static /* synthetic */ TJCVirtualGoodUtil access$2200(TJCVirtualGoods tJCVirtualGoods) {
        return tJCVirtualGoods.virtualGoodUtil;
    }

    static /* synthetic */ Dialog access$2700(TJCVirtualGoods tJCVirtualGoods) {
        return tJCVirtualGoods.exceptionDialog;
    }

    static /* synthetic */ Dialog access$2702(TJCVirtualGoods tJCVirtualGoods, Dialog dialog) {
        tJCVirtualGoods.exceptionDialog = dialog;
        return dialog;
    }

    static /* synthetic */ ProgressDialog access$2800(TJCVirtualGoods tJCVirtualGoods) {
        return tJCVirtualGoods.progressDialog;
    }

    static /* synthetic */ VGStoreItem access$2902(TJCVirtualGoods tJCVirtualGoods, VGStoreItem vGStoreItem) {
        tJCVirtualGoods.currentVgStoreItem = vGStoreItem;
        return vGStoreItem;
    }

    static /* synthetic */ Dialog access$3002(TJCVirtualGoods tJCVirtualGoods, Dialog dialog) {
        tJCVirtualGoods.getMoreItemDialog = dialog;
        return dialog;
    }

    static /* synthetic */ void access$3200(TJCVirtualGoods tJCVirtualGoods, Context context) {
        tJCVirtualGoods.updateHeaderFooters(context);
    }

    static /* synthetic */ void access$3300(TJCVirtualGoods tJCVirtualGoods, String string2, VGStoreItem vGStoreItem) {
        tJCVirtualGoods.showItemDownloadedDialog(string2, vGStoreItem);
    }

    static /* synthetic */ TableLayout access$3400(TJCVirtualGoods tJCVirtualGoods) {
        return tJCVirtualGoods.storeItemTable;
    }

    /*
     * Enabled aggressive block sorting
     */
    private LinearLayout buildPurchasedItemRow(VGStoreItem vGStoreItem, int n, int n2, Context context) {
        new LinearLayout(context);
        LinearLayout linearLayout = (LinearLayout)View.inflate((Context)context, (int)context.getResources().getIdentifier("tapjoy_virtualgoods_purchaseitems_row", "layout", TJCVirtualGoods.getClientPackage()), null);
        linearLayout.setId(n2);
        linearLayout.setOnClickListener((View.OnClickListener)context);
        linearLayout.setBackgroundResource(context.getResources().getIdentifier("tapjoy_tablerowstates", "drawable", TJCVirtualGoods.getClientPackage()));
        ((TextView)linearLayout.findViewById(context.getResources().getIdentifier("vg_row_index", "id", TJCVirtualGoods.getClientPackage()))).setText((CharSequence)("" + n));
        ImageView imageView = (ImageView)linearLayout.findViewById(context.getResources().getIdentifier("vg_row_item_icon", "id", TJCVirtualGoods.getClientPackage()));
        Drawable drawable2 = vGStoreItem.getThumbImage();
        if (drawable2 != null) {
            imageView.setImageDrawable(drawable2);
        } else if (vGStoreItem.getThumbImageUrl() != null && !vGStoreItem.getThumbImageUrl().equals((Object)"")) {
            Object[] arrobject = new Object[]{imageView, vGStoreItem};
            this.asyncTaskPool.addTask(new FetchItemIconTask((TJCVirtualGoods)this, null), arrobject);
        }
        ((TextView)linearLayout.findViewById(context.getResources().getIdentifier("vg_row_name", "id", TJCVirtualGoods.getClientPackage()))).setText((CharSequence)vGStoreItem.getName());
        ((TextView)linearLayout.findViewById(context.getResources().getIdentifier("vg_row_type", "id", TJCVirtualGoods.getClientPackage()))).setText((CharSequence)vGStoreItem.getVgStoreItemTypeName());
        TextView textView = (TextView)linearLayout.findViewById(context.getResources().getIdentifier("vg_row_attribute", "id", TJCVirtualGoods.getClientPackage()));
        if (vGStoreItem.getVgStoreItemsAttributeValueList().size() > 0) {
            textView.setText((CharSequence)(((VGStoreItemAttributeValue)vGStoreItem.getVgStoreItemsAttributeValueList().get(0)).getAttributeType() + ": " + ((VGStoreItemAttributeValue)vGStoreItem.getVgStoreItemsAttributeValueList().get(0)).getAttributeValue()));
        } else {
            textView.setVisibility(8);
        }
        TextView textView2 = (TextView)linearLayout.findViewById(context.getResources().getIdentifier("vg_row_points_text", "id", TJCVirtualGoods.getClientPackage()));
        textView2.setText((CharSequence)("" + vGStoreItem.getPrice()));
        textView2.setSelected(true);
        return linearLayout;
    }

    /*
     * Enabled aggressive block sorting
     */
    private LinearLayout buildStoreItemRow(VGStoreItem vGStoreItem, int n, int n2, Context context) {
        new LinearLayout(context);
        LinearLayout linearLayout = (LinearLayout)View.inflate((Context)context, (int)context.getResources().getIdentifier("tapjoy_virtualgoods_row", "layout", TJCVirtualGoods.getClientPackage()), null);
        linearLayout.setId(n2);
        linearLayout.setOnClickListener((View.OnClickListener)context);
        linearLayout.setBackgroundResource(context.getResources().getIdentifier("tapjoy_tablerowstates", "drawable", TJCVirtualGoods.getClientPackage()));
        ((TextView)linearLayout.findViewById(context.getResources().getIdentifier("vg_row_index", "id", TJCVirtualGoods.getClientPackage()))).setText((CharSequence)("" + n));
        ImageView imageView = (ImageView)linearLayout.findViewById(context.getResources().getIdentifier("vg_row_item_icon", "id", TJCVirtualGoods.getClientPackage()));
        Drawable drawable2 = vGStoreItem.getThumbImage();
        if (drawable2 != null) {
            imageView.setImageDrawable(drawable2);
        } else if (vGStoreItem.getThumbImageUrl() != null && !vGStoreItem.getThumbImageUrl().equals((Object)"")) {
            Object[] arrobject = new Object[]{imageView, vGStoreItem};
            this.asyncTaskPool.addTask(new FetchItemIconTask((TJCVirtualGoods)this, null), arrobject);
        }
        ((TextView)linearLayout.findViewById(context.getResources().getIdentifier("vg_row_name", "id", TJCVirtualGoods.getClientPackage()))).setText((CharSequence)vGStoreItem.getName());
        ((TextView)linearLayout.findViewById(context.getResources().getIdentifier("vg_row_type", "id", TJCVirtualGoods.getClientPackage()))).setText((CharSequence)vGStoreItem.getVgStoreItemTypeName());
        TextView textView = (TextView)linearLayout.findViewById(context.getResources().getIdentifier("vg_row_attribute", "id", TJCVirtualGoods.getClientPackage()));
        if (vGStoreItem.getVgStoreItemsAttributeValueList().size() > 0) {
            textView.setText((CharSequence)(((VGStoreItemAttributeValue)vGStoreItem.getVgStoreItemsAttributeValueList().get(0)).getAttributeType() + ": " + ((VGStoreItemAttributeValue)vGStoreItem.getVgStoreItemsAttributeValueList().get(0)).getAttributeValue()));
        } else {
            textView.setVisibility(8);
        }
        TextView textView2 = (TextView)linearLayout.findViewById(context.getResources().getIdentifier("vg_row_points_text", "id", TJCVirtualGoods.getClientPackage()));
        textView2.setText((CharSequence)("" + vGStoreItem.getPrice()));
        textView2.setSelected(true);
        return linearLayout;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void checkPurchasedVGForDownload(int n, Context context) {
        View view = this.purchasedItemTable.findViewWithTag((Object)"MorePurchasedItemsButton");
        if (view != null) {
            this.purchasedItemTable.removeView(view);
            this.morePurchasedItem = (Button)view;
        }
        if (n > 0) {
            if (n > 25) {
                this.morePurchasedItem.setText((CharSequence)"Show 25 more...");
            } else {
                this.morePurchasedItem.setText((CharSequence)("Show " + n + " more..."));
            }
            this.purchasedItemTable.addView((View)this.morePurchasedItem);
        }
        if (this.checkPurchased.booleanValue()) {
            this.checkPurchased = false;
            this.virtualGoodUtil.downLoadPurcahasedVirtualGood((List<VGStoreItem>)this.purchasedItemArray, this.purchasedItemTable, this.purchasedItemStart + this.purchasedThroughAvailableItem);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int createColor(int n, int n2, int n3, int n4) {
        int n5 = n2 + (255 & n >> 16);
        int n6 = n3 + (255 & n >> 8);
        int n7 = n4 + (n & 255);
        if (n5 < 0) {
            n5 = 0;
        }
        if (n6 < 0) {
            n6 = 0;
        }
        int n8 = 0;
        if (n7 >= 0) {
            n8 = n7;
        }
        if (n5 > 255) {
            n5 = 255;
        }
        if (n6 > 255) {
            n6 = 255;
        }
        if (n8 > 255) {
            n8 = 255;
        }
        return Color.argb((int)255, (int)n5, (int)n6, (int)n8);
    }

    private static GradientDrawable createGradient(int n, int n2, int n3, float f2, int n4, boolean bl, float[] arrf) {
        int n5;
        int n6 = 255 & n >> 16;
        int n7 = 255 & n >> 8;
        int n8 = n & 255;
        int[] arrn = new int[100];
        int n9 = arrn.length;
        int n10 = n6 - n2;
        int n11 = n7 - n2;
        int n12 = n8 - n2;
        if (n10 < 0) {
            n10 = 0;
        }
        if (n11 < 0) {
            n11 = 0;
        }
        if (n12 < 0) {
            n12 = 0;
        }
        if ((n5 = (n2 + n3) / n9) <= 0) {
            n5 = 1;
        }
        int n13 = n11;
        int n14 = n10;
        for (int i2 = 0; i2 < n9; ++i2) {
            n13 += n5;
            n12 += n5;
            if ((n14 += n5) > 255) {
                n14 = 255;
            }
            if (n13 > 255) {
                n13 = 255;
            }
            if (n12 > 255) {
                n12 = 255;
            }
            arrn[i2] = Color.argb((int)255, (int)n14, (int)n13, (int)n12);
        }
        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.BOTTOM_TOP;
        if (bl) {
            orientation = GradientDrawable.Orientation.TOP_BOTTOM;
        }
        GradientDrawable gradientDrawable = new GradientDrawable(orientation, arrn);
        gradientDrawable.setCornerRadii(arrf);
        if (f2 > 0.0f) {
            gradientDrawable.setStroke((int)f2, n4);
        }
        gradientDrawable.setGradientRadius(50.0f);
        return gradientDrawable;
    }

    public static String getClientPackage() {
        return clientPackage;
    }

    public static TapjoyDownloadListener getVirtualGoodDownloadListener() {
        return tapjoyVGDownloadListener;
    }

    public static FocusListener getVirtualGoodsFocusListener() {
        return tapjoyVGFocusListener;
    }

    private void initView() {
        this.setContentView(this.getResources().getIdentifier("tapjoy_virtualgoods", "layout", TJCVirtualGoods.getClientPackage()));
        int n = ctx.getSharedPreferences("tjcPrefrences", 0).getInt("tapjoyPrimaryColor", 0);
        TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "baseColor: " + Integer.toHexString((int)n));
        int n2 = TJCVirtualGoods.createColor(n, -16, -16, -16);
        int n3 = TJCVirtualGoods.createColor(n, -64, -64, -64);
        int n4 = TJCVirtualGoods.createColor(n, 64, 64, 64);
        int n5 = TJCVirtualGoods.createColor(n, -80, -80, -80);
        GradientDrawable gradientDrawable = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, true, this.cornerLeft);
        GradientDrawable gradientDrawable2 = TJCVirtualGoods.createGradient(n2, 48, 80, 1.0f, n5, false, this.cornerLeft);
        GradientDrawable gradientDrawable3 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, false, this.cornerLeft);
        GradientDrawable gradientDrawable4 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, true, this.cornerRight);
        GradientDrawable gradientDrawable5 = TJCVirtualGoods.createGradient(n2, 48, 80, 1.0f, n5, false, this.cornerRight);
        GradientDrawable gradientDrawable6 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, false, this.cornerRight);
        GradientDrawable gradientDrawable7 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, true, this.cornerAll);
        GradientDrawable gradientDrawable8 = TJCVirtualGoods.createGradient(n2, 48, 80, 1.0f, n5, false, this.cornerAll);
        GradientDrawable gradientDrawable9 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, false, this.cornerAll);
        GradientDrawable gradientDrawable10 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, true, this.cornerAll);
        GradientDrawable gradientDrawable11 = TJCVirtualGoods.createGradient(n2, 48, 80, 1.0f, n5, false, this.cornerAll);
        GradientDrawable gradientDrawable12 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, false, this.cornerAll);
        GradientDrawable gradientDrawable13 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, true, this.cornerAll);
        GradientDrawable gradientDrawable14 = TJCVirtualGoods.createGradient(n2, 48, 80, 1.0f, n5, false, this.cornerAll);
        GradientDrawable gradientDrawable15 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, false, this.cornerAll);
        GradientDrawable gradientDrawable16 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, true, this.cornerAll);
        GradientDrawable gradientDrawable17 = TJCVirtualGoods.createGradient(n2, 48, 80, 1.0f, n5, false, this.cornerAll);
        GradientDrawable gradientDrawable18 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, false, this.cornerAll);
        GradientDrawable gradientDrawable19 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, true, this.cornerAll);
        GradientDrawable gradientDrawable20 = TJCVirtualGoods.createGradient(n2, 48, 80, 1.0f, n5, false, this.cornerAll);
        GradientDrawable gradientDrawable21 = TJCVirtualGoods.createGradient(n3, 48, 80, 1.0f, n5, false, this.cornerAll);
        StateListDrawable stateListDrawable = new StateListDrawable();
        StateListDrawable stateListDrawable2 = new StateListDrawable();
        StateListDrawable stateListDrawable3 = new StateListDrawable();
        StateListDrawable stateListDrawable4 = new StateListDrawable();
        StateListDrawable stateListDrawable5 = new StateListDrawable();
        StateListDrawable stateListDrawable6 = new StateListDrawable();
        StateListDrawable stateListDrawable7 = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, (Drawable)gradientDrawable);
        int[] arrn = new int[]{-16842908, -16842913};
        stateListDrawable.addState(arrn, (Drawable)gradientDrawable2);
        int[] arrn2 = new int[]{-16842908, 16842913};
        stateListDrawable.addState(arrn2, (Drawable)gradientDrawable3);
        stateListDrawable2.addState(new int[]{16842919}, (Drawable)gradientDrawable4);
        int[] arrn3 = new int[]{-16842908, -16842913};
        stateListDrawable2.addState(arrn3, (Drawable)gradientDrawable5);
        int[] arrn4 = new int[]{-16842908, 16842913};
        stateListDrawable2.addState(arrn4, (Drawable)gradientDrawable6);
        stateListDrawable3.addState(new int[]{16842919}, (Drawable)gradientDrawable7);
        int[] arrn5 = new int[]{-16842908, -16842913};
        stateListDrawable3.addState(arrn5, (Drawable)gradientDrawable8);
        int[] arrn6 = new int[]{-16842908, 16842913};
        stateListDrawable3.addState(arrn6, (Drawable)gradientDrawable9);
        stateListDrawable4.addState(new int[]{16842919}, (Drawable)gradientDrawable10);
        int[] arrn7 = new int[]{-16842908, -16842913};
        stateListDrawable4.addState(arrn7, (Drawable)gradientDrawable11);
        int[] arrn8 = new int[]{-16842908, 16842913};
        stateListDrawable4.addState(arrn8, (Drawable)gradientDrawable12);
        stateListDrawable5.addState(new int[]{16842919}, (Drawable)gradientDrawable13);
        int[] arrn9 = new int[]{-16842908, -16842913};
        stateListDrawable5.addState(arrn9, (Drawable)gradientDrawable14);
        int[] arrn10 = new int[]{-16842908, 16842913};
        stateListDrawable5.addState(arrn10, (Drawable)gradientDrawable15);
        stateListDrawable6.addState(new int[]{16842919}, (Drawable)gradientDrawable16);
        int[] arrn11 = new int[]{-16842908, -16842913};
        stateListDrawable6.addState(arrn11, (Drawable)gradientDrawable17);
        int[] arrn12 = new int[]{-16842908, 16842913};
        stateListDrawable6.addState(arrn12, (Drawable)gradientDrawable18);
        stateListDrawable7.addState(new int[]{16842919}, (Drawable)gradientDrawable19);
        int[] arrn13 = new int[]{-16842908, -16842913};
        stateListDrawable7.addState(arrn13, (Drawable)gradientDrawable20);
        int[] arrn14 = new int[]{-16842908, 16842913};
        stateListDrawable7.addState(arrn14, (Drawable)gradientDrawable21);
        LinearLayout linearLayout = (LinearLayout)this.findViewById(this.getResources().getIdentifier("vg_background", "id", TJCVirtualGoods.getClientPackage()));
        LinearLayout linearLayout2 = (LinearLayout)this.findViewById(this.getResources().getIdentifier("vg_store_footer", "id", TJCVirtualGoods.getClientPackage()));
        LinearLayout linearLayout3 = (LinearLayout)this.findViewById(this.getResources().getIdentifier("vg_item_footer", "id", TJCVirtualGoods.getClientPackage()));
        TextView textView = (TextView)this.findViewById(this.getResources().getIdentifier("vg_detail_header", "id", TJCVirtualGoods.getClientPackage()));
        RelativeLayout relativeLayout = (RelativeLayout)this.findViewById(this.getResources().getIdentifier("vg_detail_info", "id", TJCVirtualGoods.getClientPackage()));
        ScrollView scrollView = (ScrollView)this.findViewById(this.getResources().getIdentifier("vg_detail_desc", "id", TJCVirtualGoods.getClientPackage()));
        LinearLayout linearLayout4 = (LinearLayout)this.findViewById(this.getResources().getIdentifier("vg_detail_footer", "id", TJCVirtualGoods.getClientPackage()));
        linearLayout.setBackgroundDrawable((Drawable)TJCVirtualGoods.createGradient(n, 32, 32, 0.0f, 0, false, this.cornerNone));
        linearLayout2.setBackgroundDrawable((Drawable)TJCVirtualGoods.createGradient(n, 32, 32, 0.0f, 0, false, this.cornerNone));
        linearLayout3.setBackgroundDrawable((Drawable)TJCVirtualGoods.createGradient(n, 32, 32, 0.0f, 0, false, this.cornerNone));
        textView.setBackgroundDrawable((Drawable)TJCVirtualGoods.createGradient(n4, 32, 4, 2.0f, n5, false, this.cornerAll));
        relativeLayout.setBackgroundDrawable((Drawable)TJCVirtualGoods.createGradient(n4, 32, 4, 2.0f, n5, true, this.cornerAll));
        scrollView.setBackgroundDrawable((Drawable)TJCVirtualGoods.createGradient(n4, 32, 4, 2.0f, n5, true, this.cornerAll));
        linearLayout4.setBackgroundDrawable((Drawable)TJCVirtualGoods.createGradient(n4, 32, 4, 2.0f, n5, false, this.cornerAll));
        this.tabs = (TabHost)this.findViewById(this.getResources().getIdentifier("VGTabHost", "id", TJCVirtualGoods.getClientPackage()));
        this.tabs.setup();
        TabHost.TabSpec tabSpec = this.tabs.newTabSpec("tab1");
        tabSpec.setContent(this.getResources().getIdentifier("Store", "id", TJCVirtualGoods.getClientPackage()));
        Button button = new Button((Context)this);
        button.setText((CharSequence)"Available Items");
        button.setTextColor(-1);
        button.setPadding(8, 8, 8, 8);
        button.setBackgroundDrawable((Drawable)stateListDrawable);
        button.setShadowLayer(2.0f, 2.0f, 2.0f, this.getResources().getIdentifier("vg_shadow_color", "color", TJCVirtualGoods.getClientPackage()));
        tabSpec.setIndicator((View)button);
        this.tabs.addTab(tabSpec);
        TabHost.TabSpec tabSpec2 = this.tabs.newTabSpec("tab3");
        tabSpec2.setContent(this.getResources().getIdentifier("YourItem", "id", TJCVirtualGoods.getClientPackage()));
        Button button2 = new Button((Context)this);
        button2.setText((CharSequence)"My Items");
        button2.setTextColor(-1);
        button2.setPadding(8, 8, 8, 8);
        button2.setBackgroundDrawable((Drawable)stateListDrawable2);
        button2.setShadowLayer(2.0f, 2.0f, 2.0f, this.getResources().getIdentifier("vg_shadow_color", "color", TJCVirtualGoods.getClientPackage()));
        tabSpec2.setIndicator((View)button2);
        this.tabs.addTab(tabSpec2);
        this.storeProgress = (ProgressBar)this.findViewById(this.getResources().getIdentifier("StoreProgress", "id", TJCVirtualGoods.getClientPackage()));
        this.purchasedItemProgress = (ProgressBar)this.findViewById(this.getResources().getIdentifier("YourItemProgress", "id", TJCVirtualGoods.getClientPackage()));
        this.storeItemTable = (TableLayout)this.findViewById(this.getResources().getIdentifier("StoreTable", "id", TJCVirtualGoods.getClientPackage()));
        this.purchasedItemTable = (TableLayout)this.findViewById(this.getResources().getIdentifier("YourItemTable", "id", TJCVirtualGoods.getClientPackage()));
        this.getItemDialog = new Dialog((Context)this);
        this.getMoreItemDialog = new Dialog((Context)this);
        this.itemDownloadedDialog = new Dialog((Context)this);
        this.detailView = (LinearLayout)this.findViewById(this.getResources().getIdentifier("ItemDetail", "id", TJCVirtualGoods.getClientPackage()));
        this.detailView.setVisibility(8);
        this.storeHeaderLeftTitle = (TextView)this.findViewById(this.getResources().getIdentifier("storeHeaderLeftTitle", "id", TJCVirtualGoods.getClientPackage()));
        this.storeHeaderLeftTitle.setText((CharSequence)"Item");
        detailErrorIcon = (ImageView)this.findViewById(this.getResources().getIdentifier("vg_detail_error_icon", "id", TJCVirtualGoods.getClientPackage()));
        this.myItemsHeaderLeftTitle = (TextView)this.findViewById(this.getResources().getIdentifier("myItemsHeaderLeftTitle", "id", TJCVirtualGoods.getClientPackage()));
        this.myItemsHeaderLeftTitle.setText((CharSequence)"Item");
        this.storeHeaderRightTitle = (TextView)this.findViewById(this.getResources().getIdentifier("storeHeaderRightTitle", "id", TJCVirtualGoods.getClientPackage()));
        this.storeHeaderRightTitle.setText((CharSequence)currencyName);
        this.myItemsHeaderRightTitle = (TextView)this.findViewById(this.getResources().getIdentifier("myItemsHeaderRightTitle", "id", TJCVirtualGoods.getClientPackage()));
        this.myItemsHeaderRightTitle.setText((CharSequence)currencyName);
        this.vgFooterLeft = (TextView)this.findViewById(this.getResources().getIdentifier("VGFooterLeft", "id", TJCVirtualGoods.getClientPackage()));
        this.vgFooterLeft.setText((CharSequence)("Your " + currencyName + ": " + tapPoints));
        this.vgPurchasedFooterLeft = (TextView)this.findViewById(this.getResources().getIdentifier("VGYourItemFooterLeft", "id", TJCVirtualGoods.getClientPackage()));
        this.vgPurchasedFooterLeft.setText((CharSequence)("Your " + currencyName + ": " + tapPoints));
        this.vgDetailFooterLeft = (TextView)this.findViewById(this.getResources().getIdentifier("VGDetailFooterLeft", "id", TJCVirtualGoods.getClientPackage()));
        this.vgDetailFooterLeft.setText((CharSequence)("Your " + currencyName + ": " + tapPoints));
        this.vgGetMoreBtn = (Button)this.findViewById(this.getResources().getIdentifier("VGGetMoreBtn", "id", TJCVirtualGoods.getClientPackage()));
        this.vgGetMoreBtn.setText((CharSequence)("Get more " + currencyName));
        this.vgGetMoreBtn.setBackgroundDrawable((Drawable)stateListDrawable3);
        this.vgGetMoreBtn.setOnClickListener((View.OnClickListener)this);
        this.vgPurchasedGetMoreBtn = (Button)this.findViewById(this.getResources().getIdentifier("VGYourItemGetMoreBtn", "id", TJCVirtualGoods.getClientPackage()));
        this.vgPurchasedGetMoreBtn.setText((CharSequence)("Get more " + currencyName));
        this.vgPurchasedGetMoreBtn.setBackgroundDrawable((Drawable)stateListDrawable4);
        this.vgPurchasedGetMoreBtn.setOnClickListener((View.OnClickListener)this);
        this.vgDetailGetMoreBtn = (Button)this.findViewById(this.getResources().getIdentifier("VGDetailGetMoreBtn", "id", TJCVirtualGoods.getClientPackage()));
        this.vgDetailGetMoreBtn.setText((CharSequence)("Get more " + currencyName));
        this.vgDetailGetMoreBtn.setBackgroundDrawable((Drawable)stateListDrawable5);
        this.vgDetailGetMoreBtn.setOnClickListener((View.OnClickListener)this);
        this.detailGetThisItemButton = (Button)this.findViewById(this.getResources().getIdentifier("vg_detail_action_button", "id", TJCVirtualGoods.getClientPackage()));
        this.detailGetThisItemButton.setText((CharSequence)"Get this item");
        this.detailGetThisItemButton.setBackgroundDrawable((Drawable)stateListDrawable6);
        detailRetryButton = (Button)this.findViewById(this.getResources().getIdentifier("RetryDetailBtn", "id", TJCVirtualGoods.getClientPackage()));
        detailRetryButton.setText((CharSequence)"Retry");
        detailRetryButton.setBackgroundDrawable((Drawable)stateListDrawable7);
        detailRetryButton.setOnClickListener(this.virtualGoodUtil.retryDetailClickListener);
        this.moreStoreItems = new Button((Context)this);
        this.moreStoreItems.setTag((Object)"MoreStoreItemsButton");
        this.moreStoreItems.setOnClickListener((View.OnClickListener)this);
        this.morePurchasedItem = new Button((Context)this);
        this.morePurchasedItem.setTag((Object)"MorePurchasedItemsButton");
        this.morePurchasedItem.setOnClickListener((View.OnClickListener)this);
        storeNoData = (TextView)this.findViewById(this.getResources().getIdentifier("StoreNoDataText", "id", TJCVirtualGoods.getClientPackage()));
        yourItemNoData = (TextView)this.findViewById(this.getResources().getIdentifier("YourItemNoDataText", "id", TJCVirtualGoods.getClientPackage()));
        this.tabs.setCurrentTab(0);
        ScrollView scrollView2 = (ScrollView)this.findViewById(this.getResources().getIdentifier("StoreLayout", "id", TJCVirtualGoods.getClientPackage()));
        scrollView2.setFillViewport(false);
        scrollView2.setScrollContainer(false);
        detailDownloadStatusTextView = (TextView)this.findViewById(this.getResources().getIdentifier("vg_detail_download_status_text", "id", TJCVirtualGoods.getClientPackage()));
        detailProgressBar = (ProgressBar)this.findViewById(this.getResources().getIdentifier("vg_detail_progress_bar", "id", TJCVirtualGoods.getClientPackage()));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void purchaseVirtualGood() {
        TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "callGetPurchasedVGItems");
        this.getPurchasedVGItems = new PurchaseVirtualGoodTask(this, null);
        TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "Processing your request...");
        try {
            this.progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)"Processing your request ...", (boolean)true);
        }
        catch (Exception exception) {
            TapjoyLog.e(TAPJOY_VIRTUAL_GOODS, "exception: " + exception.toString());
        }
        try {
            TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "getPurchasedVGItems...");
            PurchaseVirtualGoodTask purchaseVirtualGoodTask = this.getPurchasedVGItems;
            Object[] arrobject = new VGStoreItem[]{this.currentVgStoreItem};
            purchaseVirtualGoodTask.execute(arrobject);
            return;
        }
        catch (Exception exception) {
            TapjoyLog.e(TAPJOY_VIRTUAL_GOODS, "exception: " + exception.toString());
            this.progressDialog.cancel();
            this.showGetPurchasedItemsNetworkErrorDialog(this.currentVgStoreItem);
            return;
        }
    }

    public static void setVirtualGoodDownloadListener(TapjoyDownloadListener tapjoyDownloadListener) {
        tapjoyVGDownloadListener = tapjoyDownloadListener;
    }

    public static void setVirtualGoodsFocusListener(FocusListener focusListener) {
        tapjoyVGFocusListener = focusListener;
    }

    private void showCachedPurchasedItemsDialog() {
        this.runOnUiThread(new Runnable(){

            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder((Context)TJCVirtualGoods.this);
                builder.setTitle((CharSequence)"Can't load my items");
                builder.setPositiveButton((CharSequence)"OK", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                        dialogInterface.cancel();
                    }
                });
                builder.setMessage((CharSequence)"Service is unreachable.\nOnly showing downloaded items.");
                AlertDialog alertDialog = builder.create();
                try {
                    alertDialog.show();
                    return;
                }
                catch (WindowManager.BadTokenException badTokenException) {
                    return;
                }
            }

        });
    }

    private void showGetPurchasedItemsNetworkErrorDialog(VGStoreItem vGStoreItem) {
        this.currentVgStoreItem = vGStoreItem;
        this.exceptionDialog = new AlertDialog.Builder((Context)this).setMessage((CharSequence)NETWORK_DOWN_MESSAGE).setNegativeButton((CharSequence)"Cancel", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                dialogInterface.cancel();
            }
        }).setPositiveButton((CharSequence)"Retry", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                TJCVirtualGoods.this.purchaseVirtualGood();
            }
        }).create();
        try {
            if (this.exceptionDialog != null) {
                this.exceptionDialog.show();
            }
            return;
        }
        catch (WindowManager.BadTokenException badTokenException) {
            return;
        }
    }

    private void showGetStoreItemsNetworkErrorDialog() {
        this.runOnUiThread(new Runnable(){

            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder((Context)TJCVirtualGoods.this);
                builder.setNegativeButton((CharSequence)"Cancel", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton((CharSequence)"Retry", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                        TJCVirtualGoods.this.fetchAllStoreItems = new FetchAndBuildStoreItemsTask(TJCVirtualGoods.this, null);
                        TJCVirtualGoods.this.fetchAllStoreItems.execute((Object[])new Void[0]);
                        dialogInterface.cancel();
                    }
                });
                builder.setMessage((CharSequence)TJCVirtualGoods.NETWORK_DOWN_MESSAGE);
                TJCVirtualGoods.this.allItemAlert = builder.create();
                try {
                    TJCVirtualGoods.this.allItemAlert.show();
                    return;
                }
                catch (Exception exception) {
                    return;
                }
            }

        });
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showItemDetail(VGStoreItem vGStoreItem, int n, View view, int n2) {
        TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "********************");
        TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "showItemDetail");
        ImageView imageView = (ImageView)this.findViewById(this.getResources().getIdentifier("vg_detail_item_icon", "id", TJCVirtualGoods.getClientPackage()));
        imageView.setImageDrawable(null);
        if (vGStoreItem.getThumbImage() != null) {
            imageView.setImageDrawable(vGStoreItem.getThumbImage());
        } else if (vGStoreItem.getThumbImageUrl() != null && !vGStoreItem.getThumbImageUrl().equals((Object)"")) {
            Object[] arrobject = new Object[]{imageView, vGStoreItem};
        }
        ((TextView)this.findViewById(this.getResources().getIdentifier("vg_detail_item_name_text", "id", TJCVirtualGoods.getClientPackage()))).setText((CharSequence)vGStoreItem.getName());
        ((TextView)this.findViewById(this.getResources().getIdentifier("vg_detail_item_type_text", "id", TJCVirtualGoods.getClientPackage()))).setText((CharSequence)vGStoreItem.getVgStoreItemTypeName());
        TextView textView = (TextView)this.findViewById(this.getResources().getIdentifier("vg_detail_points_text", "id", TJCVirtualGoods.getClientPackage()));
        textView.setText((CharSequence)("" + vGStoreItem.getPrice()));
        textView.setSelected(true);
        ((TextView)this.findViewById(this.getResources().getIdentifier("vg_detail_currency_text", "id", TJCVirtualGoods.getClientPackage()))).setText((CharSequence)currencyName);
        LinearLayout linearLayout = (LinearLayout)this.findViewById(this.getResources().getIdentifier("ScrollLayout", "id", TJCVirtualGoods.getClientPackage()));
        linearLayout.removeAllViews();
        ArrayList<VGStoreItemAttributeValue> arrayList = vGStoreItem.getVgStoreItemsAttributeValueList();
        int n3 = this.getResources().getColor(this.getResources().getIdentifier("virtual_goods_details_text_color", "color", TJCVirtualGoods.getClientPackage()));
        for (int i2 = 0; i2 < arrayList.size(); ++i2) {
            TextView textView2 = new TextView((Context)this);
            String string2 = ((VGStoreItemAttributeValue)arrayList.get(i2)).getAttributeType();
            if (string2.equals((Object)"quantity")) {
                detailDescQuantity = new TextView((Context)this);
                detailDescQuantity.setText((CharSequence)(string2 + ": " + vGStoreItem.getNumberOwned()));
                detailDescQuantity.setPadding(0, 0, 0, 10);
                detailDescQuantity.setTextColor(n3);
                linearLayout.addView((View)detailDescQuantity);
                continue;
            }
            textView2.setText((CharSequence)(string2 + ": " + ((VGStoreItemAttributeValue)arrayList.get(i2)).getAttributeValue()));
            textView2.setPadding(0, 0, 0, 10);
            textView2.setTextColor(n3);
            linearLayout.addView((View)textView2);
        }
        if (vGStoreItem.getDescription() != null && !vGStoreItem.getDescription().equals((Object)"")) {
            TextView textView3 = new TextView((Context)this);
            textView3.setText((CharSequence)"Description:");
            textView3.setTypeface(Typeface.DEFAULT_BOLD);
            textView3.setPadding(0, 0, 0, 3);
            textView3.setTextColor(n3);
            linearLayout.addView((View)textView3);
            TextView textView4 = new TextView((Context)this);
            textView4.setText((CharSequence)vGStoreItem.getDescription());
            textView4.setTextColor(n3);
            linearLayout.addView((View)textView4);
        }
        vGStoreItem.getVgStoreItemID();
        final String string3 = vGStoreItem.getName();
        final String string4 = "" + vGStoreItem.getPrice();
        this.currentVgStoreItem = vGStoreItem;
        switch (n) {
            case 0: {
                this.detailGetThisItemButton.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        TJCVirtualGoods.this.getItemDialog = (Dialog)new AlertDialog.Builder((Context)TJCVirtualGoods.this).setTitle((CharSequence)"").setMessage((CharSequence)("Are you sure you want  \n" + string3 + " for " + string4 + " " + currencyName + "?")).setPositiveButton((CharSequence)"OK", new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialogInterface, int n) {
                                TJCVirtualGoods.this.purchaseVirtualGood();
                            }
                        }).setNegativeButton((CharSequence)"Cancel", new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialogInterface, int n) {
                            }
                        }).create();
                        try {
                            TJCVirtualGoods.this.getItemDialog.show();
                            return;
                        }
                        catch (WindowManager.BadTokenException badTokenException) {
                            return;
                        }
                    }

                });
                if (this.detailGetThisItemButton.getBackground() == null) {
                    this.detailGetThisItemButton.setText((CharSequence)"Get this item");
                }
                this.detailGetThisItemButton.setVisibility(0);
                detailDownloadStatusTextView.setVisibility(8);
                detailProgressBar.setVisibility(8);
                detailErrorIcon.setVisibility(8);
                detailRetryButton.setVisibility(8);
            }
            default: {
                break;
            }
            case 2: {
                this.detailGetThisItemButton.setVisibility(8);
                String string5 = vGStoreItem.getVgStoreItemID();
                TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "virtualGoodsToDownload size: " + this.virtualGoodUtil.virtualGoodsToDownload.size());
                if (this.virtualGoodUtil.virtualGoodsToDownload.containsKey((Object)string5)) {
                    TJCVirtualGoodUtil.DownloadVirtualGoodTask downloadVirtualGoodTask = (TJCVirtualGoodUtil.DownloadVirtualGoodTask)((Object)this.virtualGoodUtil.virtualGoodsToDownload.get((Object)string5));
                    TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "showing detail view of an download in progress... " + vGStoreItem.getName() + ", status: " + downloadVirtualGoodTask.virtualGoodDownloadStatus);
                    TJCVirtualGoods.updateDetailViewFromDownloader(downloadVirtualGoodTask);
                } else {
                    detailDownloadStatusTextView.setText((CharSequence)TAPJOY_DOWNLOAD_COMPLETED);
                    detailDownloadStatusTextView.setVisibility(0);
                    detailProgressBar.setVisibility(8);
                    detailErrorIcon.setVisibility(8);
                    detailRetryButton.setVisibility(8);
                }
                detailViewStoreID = string5;
                isPurchasedItemDetailView = true;
                this.virtualGoodUtil.setDetailIndex(n2);
            }
        }
        this.isItemDetailView = true;
    }

    private void showItemDownloadedDialog(String string2, final VGStoreItem vGStoreItem) {
        this.itemDownloadedDialog = new AlertDialog.Builder((Context)this).setTitle((CharSequence)"").setMessage((CharSequence)string2).setPositiveButton((CharSequence)"OK", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                TJCVirtualGoods.this.startDownloadingPurchasedVG(vGStoreItem);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener(){

            public void onCancel(DialogInterface dialogInterface) {
                TapjoyLog.i(TJCVirtualGoods.TAPJOY_VIRTUAL_GOODS, "onCANCEL");
                TJCVirtualGoods.this.startDownloadingPurchasedVG(vGStoreItem);
            }
        }).create();
        try {
            this.itemDownloadedDialog.show();
            return;
        }
        catch (Exception exception) {
            TapjoyLog.e(TAPJOY_VIRTUAL_GOODS, "showItemDownloadedDialog: " + exception.toString());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showMoreStoreItemsButton(int n) {
        View view = this.storeItemTable.findViewWithTag((Object)"MoreStoreItemsButton");
        if (view != null) {
            this.storeItemTable.removeView(view);
            this.moreStoreItems = (Button)view;
        }
        if (n > 0) {
            if (n > 25) {
                this.moreStoreItems.setText((CharSequence)"Show 25 more...");
            } else {
                this.moreStoreItems.setText((CharSequence)("Show " + n + " more..."));
            }
            this.storeItemTable.addView((View)this.moreStoreItems);
        }
    }

    private void showOffers() {
        TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "Showing offers (userID = " + userID + ")");
        Intent intent = new Intent((Context)this, TJCOffersWebView.class);
        intent.putExtra("USER_ID", userID);
        intent.putExtra("URL_PARAMS", urlParams);
        this.startActivity(intent);
    }

    private void showPurchasedStoreItemTableView(int n, Context context) {
        if (n == 0) {
            this.purchasedItemTable.removeAllViews();
        }
        if (this.purchasedItemArray.size() == 0) {
            TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "No items to display");
            yourItemNoData.setVisibility(0);
            return;
        }
        yourItemNoData.setVisibility(8);
        while (n < this.purchasedItemArray.size()) {
            this.purchasedItemTable.addView((View)TJCVirtualGoods.super.buildPurchasedItemRow((VGStoreItem)this.purchasedItemArray.get(n), n, 2, context));
            View view = new View((Context)this);
            view.setBackgroundResource(this.getResources().getIdentifier("tapjoy_gradientline", "drawable", TJCVirtualGoods.getClientPackage()));
            this.purchasedItemTable.addView(view, new ViewGroup.LayoutParams(-1, 1));
            ++n;
        }
        if (this.purchasedItemArray.size() > 0) {
            TJCVirtualGoods.super.showMoreStoreItemsButton(VGStoreItem.availableItemsMoreDataAvailable);
        }
        TJCVirtualGoods.super.updateHeaderFooters((Context)this);
    }

    private void showStoreItemTableView(int n, Context context) {
        if (n == 0) {
            this.storeItemTable.removeAllViews();
        }
        if (this.storeItemArray.size() == 0) {
            storeNoData.setVisibility(0);
            return;
        }
        storeNoData.setVisibility(8);
        while (n < this.storeItemArray.size()) {
            LinearLayout linearLayout = TJCVirtualGoods.super.buildStoreItemRow((VGStoreItem)this.storeItemArray.get(n), n, 0, context);
            View view = new View((Context)this);
            view.setBackgroundResource(this.getResources().getIdentifier("tapjoy_gradientline", "drawable", TJCVirtualGoods.getClientPackage()));
            if (!((VGStoreItem)this.storeItemArray.get(n)).isShown()) {
                linearLayout.setVisibility(8);
                view.setVisibility(8);
            }
            this.storeItemTable.addView((View)linearLayout);
            this.storeItemTable.addView(view, new ViewGroup.LayoutParams(-1, 1));
            ++n;
        }
        TJCVirtualGoods.super.updateHeaderFooters((Context)this);
    }

    private void startDownloadingPurchasedVG(VGStoreItem vGStoreItem) {
        TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "--------------------");
        TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "SUCCESSFUL PURCHASE");
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long l = statFs.getAvailableBlocks() * statFs.getBlockSize();
        TapjoyLog.i(TAPJOY_FILE_SYSTEM, "FREE INTERNAL MEMORY: " + l / 1024L + " KB");
        StatFs statFs2 = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long l2 = statFs2.getAvailableBlocks() * statFs2.getBlockSize();
        TapjoyLog.i(TAPJOY_FILE_SYSTEM, "FREE EXTERNAL MEMORY: " + l2 / 1024L + " KB");
        for (int i2 = 0; i2 < this.purchasedItemArray.size(); ++i2) {
            VGStoreItem vGStoreItem2 = (VGStoreItem)this.purchasedItemArray.get(i2);
            if (!vGStoreItem2.getVgStoreItemID().equals((Object)vGStoreItem.getVgStoreItemID())) continue;
            int n = vGStoreItem2.getNumberOwned();
            vGStoreItem2.setNumberOwned(n + 1);
            detailDescQuantity.setText((CharSequence)("quantity: " + (n + 1)));
            TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "ALREADY DOWNLOADED... updating quantity only");
            this.detailGetThisItemButton.setVisibility(8);
            detailDownloadStatusTextView.setText((CharSequence)TAPJOY_DOWNLOAD_COMPLETED);
            detailDownloadStatusTextView.setVisibility(0);
            return;
        }
        isPurchasedItemDetailView = true;
        detailViewStoreID = vGStoreItem.getVgStoreItemID();
        downloadStarted = true;
        this.storeItemArray.indexOf((Object)vGStoreItem);
        vGStoreItem.setShown(false);
        TJCVirtualGoodUtil tJCVirtualGoodUtil = this.virtualGoodUtil;
        tJCVirtualGoodUtil.getClass();
        TJCVirtualGoodUtil.DownloadVirtualGoodTask downloadVirtualGoodTask = tJCVirtualGoodUtil.new TJCVirtualGoodUtil.DownloadVirtualGoodTask();
        if (this.virtualGoodUtil.virtualGoodsToDownload.get((Object)vGStoreItem.getVgStoreItemID()) == null) {
            this.virtualGoodUtil.virtualGoodsToDownload.put((Object)vGStoreItem.getVgStoreItemID(), (Object)downloadVirtualGoodTask);
            this.purchasedThroughAvailableItem = 1 + this.purchasedThroughAvailableItem;
            int n = this.purchasedItemArray.size();
            View view = null;
            if (n > 0 && (view = this.purchasedItemTable.findViewWithTag((Object)"MorePurchasedItemsButton")) != null) {
                this.purchasedItemTable.removeView(view);
            }
            this.purchasedItemArray.add((Object)vGStoreItem);
            if (this.virtualGoodUtil.purchaseItems == null) {
                this.virtualGoodUtil.purchaseItems = this.purchasedItemArray;
                yourItemNoData.setVisibility(8);
            }
            int n2 = this.purchasedItemArray.indexOf((Object)vGStoreItem);
            int n3 = ((VGStoreItem)this.purchasedItemArray.get(n2)).getNumberOwned();
            ((VGStoreItem)this.purchasedItemArray.get(n2)).setNumberOwned(n3 + 1);
            detailDescQuantity.setText((CharSequence)("quantity: " + (n3 + 1)));
            LinearLayout linearLayout = TJCVirtualGoods.super.buildPurchasedItemRow(vGStoreItem, n2, 2, (Context)this);
            this.purchasedItemTable.addView((View)linearLayout);
            View view2 = new View((Context)this);
            view2.setBackgroundResource(this.getResources().getIdentifier("tapjoy_gradientline", "drawable", TJCVirtualGoods.getClientPackage()));
            this.purchasedItemTable.addView(view2, new ViewGroup.LayoutParams(-1, 1));
            if (view != null) {
                this.purchasedItemTable.addView((View)((Button)view));
            }
            ((TextView)linearLayout.findViewById(ctx.getResources().getIdentifier("vg_row_index", "id", TJCVirtualGoods.getClientPackage()))).setText((CharSequence)(n2 + ""));
            downloadVirtualGoodTask.localProgressBar = (ProgressBar)linearLayout.findViewById(ctx.getResources().getIdentifier("vg_row_progress_bar", "id", TJCVirtualGoods.getClientPackage()));
            this.detailGetThisItemButton.setVisibility(8);
            detailProgressBar.setVisibility(0);
            downloadVirtualGoodTask.localDownloadStatusText = (TextView)linearLayout.findViewById(ctx.getResources().getIdentifier("vg_row_download_status_text", "id", TJCVirtualGoods.getClientPackage()));
            downloadVirtualGoodTask.localDownloadStatusText.setText((CharSequence)TAPJOY_DOWNLOAD_PENDING);
            downloadVirtualGoodTask.localRetryButton = (Button)linearLayout.findViewById(ctx.getResources().getIdentifier("vg_row_retry_button", "id", TJCVirtualGoods.getClientPackage()));
            downloadVirtualGoodTask.localErrorIcon = (ImageView)linearLayout.findViewById(ctx.getResources().getIdentifier("vg_row_error_icon", "id", TJCVirtualGoods.getClientPackage()));
            downloadVirtualGoodTask.localRetryButton.setOnClickListener(this.virtualGoodUtil.retryClickListener);
            downloadVirtualGoodTask.localErrorIcon.setOnClickListener(this.virtualGoodUtil.errorMsgClickListener);
            this.virtualGoodUtil.setDetailIndex(n2);
            TJCVirtualGoods.updateDetailViewFromDownloader(downloadVirtualGoodTask);
            TJCVirtualGoodUtil.addTask(downloadVirtualGoodTask, vGStoreItem);
        }
        this.itemDownloadedDialog.dismiss();
    }

    private void syncAndRebuildAllTables() {
        this.syncAndRebuildStoreItemTable();
        this.syncAndRebuildPurchasedItemTable();
    }

    private void syncAndRebuildStoreItemTable() {
        this.storeItemArray.clear();
        this.storeItemStart = 0;
        this.fetchAllStoreItems = new FetchAndBuildStoreItemsTask(this, null);
        this.fetchAllStoreItems.execute((Object[])new Void[0]);
    }

    public static void updateDetailProgressBar(int n) {
        if (detailProgressBar != null) {
            detailDownloadStatusTextView.setText((CharSequence)("Downloading... " + n + "%"));
            detailDownloadStatusTextView.setVisibility(0);
            detailProgressBar.setProgress(n);
            detailProgressBar.setVisibility(0);
        }
    }

    public static void updateDetailViewFromDownloader(TJCVirtualGoodUtil.DownloadVirtualGoodTask downloadVirtualGoodTask) {
        switch (downloadVirtualGoodTask.virtualGoodDownloadStatus) {
            default: {
                return;
            }
            case 1: {
                detailDownloadStatusTextView.setText((CharSequence)("Downloading... " + downloadVirtualGoodTask.progressPercent + "%"));
                detailDownloadStatusTextView.setVisibility(0);
                detailProgressBar.setProgress(downloadVirtualGoodTask.progressPercent);
                detailErrorIcon.setVisibility(8);
                detailRetryButton.setVisibility(8);
                detailProgressBar.setVisibility(0);
                return;
            }
            case 0: {
                detailDownloadStatusTextView.setText((CharSequence)TAPJOY_DOWNLOAD_PENDING);
                detailDownloadStatusTextView.setVisibility(0);
                detailProgressBar.setVisibility(8);
                detailErrorIcon.setVisibility(8);
                detailRetryButton.setVisibility(8);
                return;
            }
            case 10: 
            case 20: {
                detailDownloadStatusTextView.setText((CharSequence)TAPJOY_DOWNLOAD_COMPLETED);
                detailDownloadStatusTextView.setVisibility(0);
                detailProgressBar.setVisibility(8);
                detailErrorIcon.setVisibility(8);
                detailRetryButton.setVisibility(8);
                return;
            }
            case 41: 
            case 42: {
                detailDownloadStatusTextView.setText((CharSequence)TAPJOY_DOWNLOAD_FAILED);
                detailDownloadStatusTextView.setVisibility(0);
                detailProgressBar.setVisibility(8);
                detailErrorIcon.setVisibility(0);
                detailRetryButton.setVisibility(0);
                return;
            }
            case 43: 
            case 44: 
        }
        detailDownloadStatusTextView.setText((CharSequence)TAPJOY_DOWNLOAD_FAILED);
        detailDownloadStatusTextView.setVisibility(0);
        detailProgressBar.setVisibility(8);
        detailErrorIcon.setVisibility(0);
        detailRetryButton.setVisibility(0);
    }

    private void updateHeaderFooters(Context context) {
        userID = TJCVirtualGoodsData.getUserID();
        currencyName = TJCVirtualGoodsData.getCurrencyName();
        tapPoints = TJCVirtualGoodsData.getPoints();
        this.storeHeaderRightTitle.setText((CharSequence)currencyName);
        this.myItemsHeaderRightTitle.setText((CharSequence)currencyName);
        this.vgFooterLeft.setText((CharSequence)("Your " + currencyName + ": " + tapPoints));
        this.vgPurchasedFooterLeft.setText((CharSequence)("Your " + currencyName + ": " + tapPoints));
        this.vgDetailFooterLeft.setText((CharSequence)("Your " + currencyName + ": " + tapPoints));
        if (this.vgGetMoreBtn.getBackground() == null) {
            this.vgGetMoreBtn.setText((CharSequence)("Get more " + currencyName));
        }
        if (this.vgPurchasedGetMoreBtn.getBackground() == null) {
            this.vgPurchasedGetMoreBtn.setText((CharSequence)("Get more " + currencyName));
        }
        if (this.vgDetailGetMoreBtn.getBackground() == null) {
            this.vgDetailGetMoreBtn.setText((CharSequence)("Get more " + currencyName));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case 0: {
                TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "storeItemArray.size(): " + this.storeItemArray.size());
                this.isItemDetailView = true;
                this.tabs.setVisibility(8);
                this.detailView.setVisibility(0);
                int n = Integer.parseInt((String)((TextView)view.findViewById(this.getResources().getIdentifier("vg_row_index", "id", TJCVirtualGoods.getClientPackage()))).getText().toString());
                TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "onClick index: " + n);
                if (n >= this.storeItemArray.size()) break;
                TJCVirtualGoods.super.showItemDetail((VGStoreItem)this.storeItemArray.get(n), 0, view, n);
                break;
            }
            case 2: {
                TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "purchasedItemArray.size(): " + this.purchasedItemArray.size());
                int n = Integer.parseInt((String)((TextView)view.findViewById(this.getResources().getIdentifier("vg_row_index", "id", TJCVirtualGoods.getClientPackage()))).getText().toString());
                TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "onClick index: " + n);
                if (n >= this.purchasedItemArray.size()) break;
                this.isItemDetailView = true;
                this.tabs.setVisibility(8);
                this.detailView.setVisibility(0);
                TJCVirtualGoods.super.showItemDetail((VGStoreItem)this.purchasedItemArray.get(n), 2, view, n);
            }
        }
        if (!(view instanceof Button)) return;
        {
            if (view == this.vgGetMoreBtn || view == this.vgPurchasedGetMoreBtn || view == this.vgDetailGetMoreBtn) {
                offersFromVG = true;
                vgFromOffers = true;
                doNotify = false;
                TJCVirtualGoods.super.showOffers();
                return;
            } else {
                if (view == this.moreStoreItems) {
                    this.moreStoreItems.setEnabled(false);
                    this.storeItemStart = 25 + this.storeItemStart;
                    this.fetchAllStoreItems = new FetchAndBuildStoreItemsTask((TJCVirtualGoods)this, null);
                    this.fetchAllStoreItems.execute((Object[])new Void[0]);
                    return;
                }
                if (view != this.morePurchasedItem) return;
                {
                    this.morePurchasedItem.setEnabled(false);
                    this.purchasedItemStart = 25 + this.purchasedItemStart;
                    this.fetPurchasedVGItems = new FetchAndBuildPurchasedItemsTask((TJCVirtualGoods)this, null);
                    this.fetPurchasedVGItems.execute((Object[])new Void[0]);
                    return;
                }
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void onCreate(Bundle var1) {
        TapjoyLog.i("Virtual Goods", "onCreate");
        TJCVirtualGoods.ctx = this;
        super.onCreate(var1);
        var2_2 = this.getIntent().getExtras();
        TJCVirtualGoods.clientPackage = this.getPackageName();
        if (var2_2 == null) ** GOTO lbl-1000
        if (var2_2.containsKey("URL_PARAMS")) {
            TJCVirtualGoods.urlParams = var2_2.getString("URL_PARAMS");
        }
        if (var2_2.containsKey("my_items_tab")) {
            var3_3 = var2_2.getBoolean("my_items_tab");
        } else lbl-1000: // 2 sources:
        {
            var3_3 = false;
        }
        if (var1 != null) {
            TapjoyLog.i("Virtual Goods", "*** Loading saved data from bundle ***");
            TJCVirtualGoods.urlParams = var1.getString("bundle_url_params");
        }
        TapjoyLog.i("Virtual Goods", "urlParams: " + TJCVirtualGoods.urlParams);
        this.virtualGoodUtil = new TJCVirtualGoodUtil((Context)this, TJCVirtualGoods.clientPackage);
        TJCVirtualGoods.tapjoyVGConnection = new TJCVirtualGoodsConnection("https://ws.tapjoyads.com/", TJCVirtualGoods.urlParams);
        this.isItemDetailView = false;
        TJCVirtualGoods.userID = TJCVirtualGoodsData.getUserID();
        TJCVirtualGoods.currencyName = TJCVirtualGoodsData.getCurrencyName();
        TJCVirtualGoods.tapPoints = TJCVirtualGoodsData.getPoints();
        this.storeItemArray = new ArrayList();
        this.purchasedItemArray = new ArrayList();
        TJCVirtualGoods.super.initView();
        TJCVirtualGoods.super.syncAndRebuildAllTables();
        if (var3_3) {
            this.tabs.setCurrentTab(1);
        }
        this.virtualGoodUtil.setPurchasedItemArray(this.purchasedItemArray);
    }

    protected void onDestroy() {
        TapjoyLog.i("ExtendedFocusListener", "onDestroy");
        super.onDestroy();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n != 4) return super.onKeyDown(n, keyEvent);
        if (this.isItemDetailView) {
            this.tabs.setVisibility(0);
            this.detailView.setVisibility(8);
            if (downloadStarted) {
                downloadStarted = false;
                this.tabs.setCurrentTab(1);
            }
            this.isItemDetailView = false;
            isPurchasedItemDetailView = false;
            do {
                return true;
                break;
            } while (true);
        }
        doNotify = false;
        for (int i2 = 0; i2 < TJCVirtualGoodUtil.currentTasks.size(); ++i2) {
            TJCVirtualGoodUtil.DownloadVirtualGoodTask downloadVirtualGoodTask = (TJCVirtualGoodUtil.DownloadVirtualGoodTask)((Object)TJCVirtualGoodUtil.currentTasks.get(i2));
            if (downloadVirtualGoodTask == null || downloadVirtualGoodTask.getStatus() != AsyncTask.Status.RUNNING) continue;
            downloadVirtualGoodTask.cancel(true);
        }
        TJCVirtualGoodUtil.currentTasks.clear();
        TJCVirtualGoodUtil.pendingTasks.clear();
        VGStoreItem.availableItemsMoreDataAvailable = 0;
        VGStoreItem.purchasedItemsMoreDataAvailable = 0;
        this.checkPurchased = true;
        downloadStarted = false;
        this.purchasedThroughAvailableItem = 0;
        if (this.fetchAllStoreItems != null && this.fetchAllStoreItems.getStatus() == AsyncTask.Status.RUNNING) {
            this.fetchAllStoreItems.cancel(true);
        }
        if (this.fetPurchasedVGItems != null && this.fetPurchasedVGItems.getStatus() == AsyncTask.Status.RUNNING) {
            this.fetPurchasedVGItems.cancel(true);
        }
        this.virtualGoodUtil.cancelExecution();
        this.finish();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onPause() {
        super.onPause();
        if (doNotify) {
            FocusListener focusListener = TJCVirtualGoods.getVirtualGoodsFocusListener();
            if (focusListener != null) {
                TapjoyLog.i("ExtendedFocusListener", "On Pause");
                focusListener.onFocusLost();
            }
        } else {
            TapjoyLog.i("ExtendedFocusListener", "On Pause Not called");
        }
        doNotify = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onResume() {
        super.onResume();
        if (doNotify && !vgFromOffers && !vgFromDownloadedVG) {
            FocusListener focusListener = TJCVirtualGoods.getVirtualGoodsFocusListener();
            if (focusListener != null) {
                TapjoyLog.i("ExtendedFocusListener", "On Resume");
                focusListener.onFocusGain();
            }
        } else {
            TapjoyLog.i("ExtendedFocusListener", "On Resume Not Called");
        }
        doNotify = true;
        vgFromOffers = false;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        TapjoyLog.i(TAPJOY_VIRTUAL_GOODS, "*** onSaveInstanceState ***");
        bundle.putString(BUNDLE_URL_PARAMS, urlParams);
    }

    public void syncAndRebuildPurchasedItemTable() {
        this.purchasedItemArray.clear();
        this.purchasedItemStart = 0;
        this.fetPurchasedVGItems = new FetchAndBuildPurchasedItemsTask(this, null);
        this.fetPurchasedVGItems.execute((Object[])new Void[0]);
    }

    public static class AsyncTaskPool {
        private ArrayList<AsyncTask> currentTasks = new ArrayList();
        private ArrayList<Object> pendingTasks = new ArrayList();
        private int poolSize;

        public AsyncTaskPool(int n) {
            this.poolSize = n;
            if (this.poolSize >= 5) {
                this.poolSize = 4;
            }
        }

        public /* varargs */ boolean addTask(AsyncTask asyncTask, Object ... arrobject) {
            if (this.currentTasks.size() < this.poolSize) {
                this.currentTasks.add((Object)asyncTask);
                if (arrobject != null) {
                    asyncTask.execute(arrobject);
                    return true;
                }
                try {
                    asyncTask.execute(new Object[0]);
                    return true;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    return true;
                }
            }
            Object[] arrobject2 = new Object[]{asyncTask, arrobject};
            this.pendingTasks.add((Object)arrobject2);
            return true;
        }

        public int getPoolSize() {
            return this.poolSize;
        }

        public boolean removeAndExecuteNext(AsyncTask asyncTask) {
            this.removeTask(asyncTask);
            if (this.pendingTasks.size() > 0 && this.currentTasks.size() < this.poolSize) {
                Object[] arrobject = (Object[])this.pendingTasks.get(0);
                this.pendingTasks.remove((Object)arrobject);
                this.addTask((AsyncTask)arrobject[0], (Object[])arrobject[1]);
            }
            return false;
        }

        public boolean removeTask(AsyncTask asyncTask) {
            if (this.currentTasks.contains((Object)asyncTask)) {
                this.currentTasks.remove((Object)asyncTask);
                return true;
            }
            return false;
        }
    }

    private class FetchAndBuildPurchasedItemsTask
    extends AsyncTask<Void, Void, ArrayList<VGStoreItem>> {
        final /* synthetic */ TJCVirtualGoods this$0;

        private FetchAndBuildPurchasedItemsTask(TJCVirtualGoods tJCVirtualGoods) {
            this.this$0 = tJCVirtualGoods;
        }

        /* synthetic */ FetchAndBuildPurchasedItemsTask(TJCVirtualGoods tJCVirtualGoods, 1 var2_2) {
            super(tJCVirtualGoods);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        protected /* varargs */ ArrayList<VGStoreItem> doInBackground(Void ... arrvoid) {
            TapjoyLog.i(TJCVirtualGoods.TAPJOY_VIRTUAL_GOODS, "Fetching All Purchased Items");
            String string2 = TJCVirtualGoods.tapjoyVGConnection.getAllPurchasedItemsFromServer(this.this$0.purchasedItemStart, 25);
            if (string2 == null) return null;
            try {
                if (string2.length() <= 0) return null;
                return TJCVirtualGoodsData.parseVGItemListResponse(string2, 1, (Context)this.this$0);
            }
            catch (Exception exception) {
                TapjoyLog.e(TJCVirtualGoods.TAPJOY_VIRTUAL_GOODS, exception.toString());
            }
            return null;
        }

        protected void onCancelled() {
            int n = this.this$0.purchasedItemArray.size();
            for (int i2 = 0; i2 < n; ++i2) {
                if (((VGStoreItem)this.this$0.purchasedItemArray.get(i2)).getThumbImage() == null) continue;
                ((BitmapDrawable)((VGStoreItem)this.this$0.purchasedItemArray.get(i2)).getThumbImage()).getBitmap().recycle();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void onPostExecute(ArrayList<VGStoreItem> arrayList) {
            this.this$0.purchasedItemProgress.setVisibility(8);
            this.this$0.morePurchasedItem.setEnabled(true);
            if (arrayList == null) {
                this.this$0.purchasedItemTable.removeAllViews();
                this.this$0.purchasedItemArray.clear();
                this.this$0.purchasedItemArray.addAll(TJCVirtualGoodsData.getPurchasedItems(TJCVirtualGoods.ctx));
                TJCVirtualGoods.access$2200((TJCVirtualGoods)this.this$0).virtualGoodsToDownload.clear();
                VGStoreItem.purchasedItemsMoreDataAvailable = 0;
                this.this$0.showCachedPurchasedItemsDialog();
                this.this$0.showPurchasedStoreItemTableView(0, TJCVirtualGoods.ctx);
                return;
            } else {
                int n = this.this$0.purchasedItemArray.size();
                this.this$0.purchasedItemArray.addAll(arrayList);
                this.this$0.showPurchasedStoreItemTableView(n, TJCVirtualGoods.ctx);
                if (arrayList.size() > 0) {
                    this.this$0.checkPurchased = true;
                    this.this$0.checkPurchasedVGForDownload(VGStoreItem.purchasedItemsMoreDataAvailable, TJCVirtualGoods.ctx);
                }
                if (this.this$0.purchasedItemArray.size() >= 1) return;
                {
                    yourItemNoData.setVisibility(0);
                    return;
                }
            }
        }

        protected void onPreExecute() {
            this.this$0.purchasedItemProgress.bringToFront();
            this.this$0.purchasedItemProgress.setVisibility(0);
            yourItemNoData.setVisibility(8);
        }
    }

    private class FetchAndBuildStoreItemsTask
    extends AsyncTask<Void, Void, ArrayList<VGStoreItem>> {
        final /* synthetic */ TJCVirtualGoods this$0;

        private FetchAndBuildStoreItemsTask(TJCVirtualGoods tJCVirtualGoods) {
            this.this$0 = tJCVirtualGoods;
        }

        /* synthetic */ FetchAndBuildStoreItemsTask(TJCVirtualGoods tJCVirtualGoods, 1 var2_2) {
            super(tJCVirtualGoods);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        protected /* varargs */ ArrayList<VGStoreItem> doInBackground(Void ... arrvoid) {
            TapjoyLog.i(TJCVirtualGoods.TAPJOY_VIRTUAL_GOODS, "Fetching All Store Items");
            String string2 = TJCVirtualGoods.tapjoyVGConnection.getAllStoreItemsFromServer(this.this$0.storeItemStart, 25);
            if (string2 == null) return null;
            try {
                if (string2.length() <= 0) return null;
                return TJCVirtualGoodsData.parseVGItemListResponse(string2, 0, (Context)this.this$0);
            }
            catch (Exception exception) {
                TapjoyLog.e(TJCVirtualGoods.TAPJOY_VIRTUAL_GOODS, "exception: " + exception.toString());
            }
            return null;
        }

        protected void onCancelled() {
            int n = this.this$0.storeItemArray.size();
            for (int i2 = 0; i2 < n; ++i2) {
                if (((VGStoreItem)this.this$0.storeItemArray.get(i2)).getThumbImage() == null) continue;
                ((BitmapDrawable)((VGStoreItem)this.this$0.storeItemArray.get(i2)).getThumbImage()).getBitmap().recycle();
            }
        }

        protected void onPostExecute(ArrayList<VGStoreItem> arrayList) {
            this.this$0.storeProgress.setVisibility(8);
            this.this$0.moreStoreItems.setEnabled(true);
            if (arrayList == null) {
                if (this.this$0.storeItemArray.size() < 1) {
                    storeNoData.setVisibility(0);
                }
                this.this$0.showGetStoreItemsNetworkErrorDialog();
                return;
            }
            int n = this.this$0.storeItemArray.size();
            this.this$0.storeItemArray.addAll(arrayList);
            this.this$0.showStoreItemTableView(n, TJCVirtualGoods.ctx);
        }

        protected void onPreExecute() {
            this.this$0.storeProgress.bringToFront();
            this.this$0.storeProgress.setVisibility(0);
            storeNoData.setVisibility(8);
        }
    }

    private class FetchItemIconTask
    extends AsyncTask<Object, Void, Drawable> {
        private VGStoreItem item;
        private ImageView itemIcon;
        final /* synthetic */ TJCVirtualGoods this$0;

        private FetchItemIconTask(TJCVirtualGoods tJCVirtualGoods) {
            this.this$0 = tJCVirtualGoods;
        }

        /* synthetic */ FetchItemIconTask(TJCVirtualGoods tJCVirtualGoods, 1 var2_2) {
            super(tJCVirtualGoods);
        }

        protected /* varargs */ Drawable doInBackground(Object ... arrobject) {
            this.itemIcon = (ImageView)arrobject[0];
            this.item = (VGStoreItem)arrobject[1];
            try {
                URLConnection uRLConnection = new URL(this.item.getThumbImageUrl()).openConnection();
                uRLConnection.setConnectTimeout(15000);
                uRLConnection.setReadTimeout(30000);
                uRLConnection.connect();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(uRLConnection.getInputStream());
                Drawable drawable2 = Drawable.createFromStream((InputStream)bufferedInputStream, (String)"src");
                bufferedInputStream.close();
                return drawable2;
            }
            catch (Exception exception) {
                TapjoyLog.e(TJCVirtualGoods.TAPJOY_VIRTUAL_GOODS, "FetchItemIconTask error: " + exception.toString());
                return null;
            }
        }

        protected void onPostExecute(Drawable drawable2) {
            this.this$0.asyncTaskPool.removeAndExecuteNext((AsyncTask)this);
            this.item.setThumbImage(drawable2);
            this.itemIcon.setImageDrawable(drawable2);
        }
    }

    public static interface FocusListener {
        public void onFocusGain();

        public void onFocusLost();
    }

    private class PurchaseVirtualGoodTask
    extends AsyncTask<VGStoreItem, Void, VGStoreItem> {
        boolean networkTimeout;
        String responseMessage;
        boolean successfulPurchase;
        final /* synthetic */ TJCVirtualGoods this$0;

        private PurchaseVirtualGoodTask(TJCVirtualGoods tJCVirtualGoods) {
            this.this$0 = tJCVirtualGoods;
            this.responseMessage = null;
            this.networkTimeout = false;
            this.successfulPurchase = false;
        }

        /* synthetic */ PurchaseVirtualGoodTask(TJCVirtualGoods tJCVirtualGoods, com.tapjoy.TJCVirtualGoods$1 var2_2) {
            super(tJCVirtualGoods);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected /* varargs */ VGStoreItem doInBackground(VGStoreItem ... arrvGStoreItem) {
            try {
                TapjoyLog.i(TJCVirtualGoods.TAPJOY_VIRTUAL_GOODS, "virtual_good_id: " + arrvGStoreItem[0].getVgStoreItemID());
                String string2 = TJCVirtualGoods.tapjoyVGConnection.purchaseVGFromServer(arrvGStoreItem[0].getVgStoreItemID());
                if (string2 != null && string2.length() > 0) {
                    ArrayList<String> arrayList = TJCVirtualGoodsData.parsePurchaseVGWithCurrencyResponse(string2, (Context)this.this$0);
                    if (arrayList != null && arrayList.size() > 0) {
                        if (!((String)arrayList.get(2)).equals((Object)"")) {
                            this.networkTimeout = true;
                        }
                        this.responseMessage = (String)arrayList.get(0);
                        this.successfulPurchase = !((String)arrayList.get(1)).equals((Object)"false");
                    } else {
                        this.networkTimeout = true;
                    }
                    TapjoyLog.i(TJCVirtualGoods.TAPJOY_VIRTUAL_GOODS, "responseMessage: " + this.responseMessage + ", successfulPurchase: " + this.successfulPurchase);
                    return arrvGStoreItem[0];
                }
            }
            catch (Exception exception) {
                TapjoyLog.e(TJCVirtualGoods.TAPJOY, "Failed to purchase item.  e: " + exception.toString());
                this.networkTimeout = true;
                return arrvGStoreItem[0];
            }
            this.networkTimeout = true;
            return arrvGStoreItem[0];
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        protected void onPostExecute(VGStoreItem var1) {
            block10 : {
                TJCVirtualGoods.access$2702(this.this$0, TJCVirtualGoods.access$2700(this.this$0));
                TJCVirtualGoods.access$2800(this.this$0).cancel();
                if (this.networkTimeout) {
                    TJCVirtualGoods.access$2902(this.this$0, var1);
                    TJCVirtualGoods.access$2702(this.this$0, (Dialog)new AlertDialog.Builder((Context)this.this$0).setMessage((CharSequence)"Service is unreachable.\nDo you want to try again?").setNegativeButton((CharSequence)"Cancel", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            dialogInterface.cancel();
                        }
                    }).setPositiveButton((CharSequence)"Retry", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialogInterface, int n) {
                            PurchaseVirtualGoodTask.this.this$0.purchaseVirtualGood();
                        }
                    }).create());
                    if (TJCVirtualGoods.access$2700(this.this$0) != null) {
                        TJCVirtualGoods.access$2700(this.this$0).show();
                    }
                    ** GOTO lbl25
                } else {
                    var1.getVgStoreItemID();
                    if (!this.successfulPurchase) {
                        TJCVirtualGoods.access$300(this.this$0).cancel();
                        if (this.responseMessage.equalsIgnoreCase("Balance too low")) {
                            this.responseMessage = "You do not have enough balance to purchase this item.";
                        }
                        TJCVirtualGoods.access$3002(this.this$0, (Dialog)new AlertDialog.Builder((Context)this.this$0).setTitle((CharSequence)"").setMessage((CharSequence)this.responseMessage).setPositiveButton((CharSequence)"OK", new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialogInterface, int n) {
                                PurchaseVirtualGoodTask.this.this$0.getMoreItemDialog.cancel();
                            }
                        }).setNegativeButton((CharSequence)"Get More", new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialogInterface, int n) {
                                TJCVirtualGoods.offersFromVG = true;
                                TJCVirtualGoods.vgFromOffers = true;
                                TJCVirtualGoods.doNotify = false;
                                PurchaseVirtualGoodTask.this.this$0.showOffers();
                            }
                        }).create());
                        try {
                            TJCVirtualGoods.access$3000(this.this$0).show();
                        }
                        catch (Exception var8_2) {}
                    } else {
                        TJCVirtualGoods.access$3200(this.this$0, (Context)this.this$0);
                        TJCVirtualGoods.access$3300(this.this$0, this.responseMessage, var1);
                    }
                }
                break block10;
                catch (Exception var11_3) {}
            }
            if (this.networkTimeout != false) return;
            TapjoyLog.i("Virtual Goods", "Now fetch store items again...");
            TJCVirtualGoods.access$1202(this.this$0, 0);
            TJCVirtualGoods.access$3400(this.this$0).removeAllViews();
            TJCVirtualGoods.access$900(this.this$0).clear();
            TJCVirtualGoods.access$702(this.this$0, new FetchAndBuildStoreItemsTask(this.this$0, null));
            TJCVirtualGoods.access$700(this.this$0).execute((Object[])new Void[0]);
        }

    }

    public static interface TapjoyDownloadListener {
        public void onDownLoad(VGStoreItem var1);
    }

}


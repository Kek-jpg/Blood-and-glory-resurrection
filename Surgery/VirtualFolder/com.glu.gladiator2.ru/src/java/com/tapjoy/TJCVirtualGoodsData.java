/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.database.Cursor
 *  android.database.SQLException
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.os.Environment
 *  android.os.StatFs
 *  java.io.ByteArrayInputStream
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.InputStream
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.ArrayList
 *  java.util.zip.ZipEntry
 *  java.util.zip.ZipFile
 *  javax.xml.parsers.DocumentBuilder
 *  javax.xml.parsers.DocumentBuilderFactory
 *  org.w3c.dom.Document
 *  org.w3c.dom.Element
 *  org.w3c.dom.Node
 *  org.w3c.dom.NodeList
 */
package com.tapjoy;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.os.StatFs;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyUtil;
import com.tapjoy.VGStoreItem;
import com.tapjoy.VGStoreItemAttributeValue;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TJCVirtualGoodsData {
    public static final String PREFS_CURRENCY_KEY = "currencyName";
    public static final String PREFS_FILE_NAME = "TJCPrefsFile";
    public static final String PREFS_POINTS_KEY = "tapPoints";
    private static final String TAPJOY_DATABASE = "TapjoyDatabase";
    private static final String TAPJOY_FILE_SYSTEM = "TapjoyFileSystem";
    private static final String TAPJOY_VIRTUAL_GOODS_DATA = "TapjoyVirtualGoodsData";
    private static Context context;
    private static String currencyName;
    private static String tapPoints;
    private static String userID;
    private String basePathSaveToPhone;
    private String basePathSaveToSDCard;
    private String clientPackage;

    static {
        userID = null;
        context = null;
    }

    public TJCVirtualGoodsData(Context context, String string2) {
        TJCVirtualGoodsData.context = context;
        this.clientPackage = string2;
        this.basePathSaveToPhone = "data/data/" + this.clientPackage + "/vgDownloads/";
        String string3 = Environment.getExternalStorageDirectory().toString();
        this.basePathSaveToSDCard = string3 + "/" + this.clientPackage + "/vgDownloads/";
        SharedPreferences sharedPreferences = TJCVirtualGoodsData.context.getSharedPreferences(PREFS_FILE_NAME, 0);
        currencyName = sharedPreferences.getString(PREFS_CURRENCY_KEY, "Points");
        tapPoints = "" + sharedPreferences.getInt(PREFS_POINTS_KEY, 0);
    }

    public static void clearRow(Context context, String string2) {
        TapjoyDatabaseUtil.getTapjoyDatabase(context).rawQuery("DELETE FROM tapjoy_VGStoreItems WHERE VGStoreItemID='" + string2 + "'", null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void createFilesAndFolders(String string2, ZipEntry zipEntry, ZipFile zipFile) throws Exception {
        FileOutputStream fileOutputStream;
        String string3 = zipEntry.getName();
        if (zipEntry.isDirectory()) {
            File file = new File((string2 + string3).replaceAll(" ", "%20"));
            if (file.exists() || !file.mkdirs()) return;
            {
                TapjoyLog.i(TAPJOY_FILE_SYSTEM, "Created directory");
            }
            return;
        }
        String string4 = "";
        try {
            int n;
            File file;
            String string5;
            if (string3.indexOf(47) > -1) {
                string4 = string3.substring(0, string3.lastIndexOf("/"));
                File file2 = new File((string2 + string4).replaceAll(" ", "%20"));
                if (!file2.exists() && file2.mkdirs()) {
                    TapjoyLog.i(TAPJOY_FILE_SYSTEM, "Created directory");
                }
            }
            if ((string5 = string3.substring(1 + string3.lastIndexOf("/"), string3.length())).startsWith(".")) {
                String string6 = "DOT" + string5.substring(1, string5.length());
                string3 = string4 + "/" + string6;
            }
            if (!(file = new File(string2, string3.replaceAll(" ", "%20"))).createNewFile()) return;
            byte[] arrby = new byte[1024];
            fileOutputStream = new FileOutputStream(file);
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            while ((n = inputStream.read(arrby, 0, 1024)) > -1) {
                fileOutputStream.write(arrby, 0, n);
            }
        }
        catch (Exception exception) {
            TapjoyLog.e(TAPJOY_FILE_SYSTEM, "createFilesAndFolders: " + exception.toString());
            throw exception;
        }
        fileOutputStream.close();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean deleteDir(File file) {
        TapjoyLog.i(TAPJOY_FILE_SYSTEM, "deleting directory: " + file.getPath());
        if (!file.exists()) return false;
        if (!file.isDirectory()) return file.delete();
        String[] arrstring = file.list();
        for (int i2 = 0; i2 < arrstring.length; ++i2) {
            if (TJCVirtualGoodsData.deleteDir(new File(file, arrstring[i2]))) continue;
            return false;
        }
        return file.delete();
    }

    public static String getCurrencyName() {
        return currencyName;
    }

    public static String getDataFileHashForVGStoreItemID(Context context, String string2) {
        Cursor cursor = TapjoyDatabaseUtil.getTapjoyDatabase(context).rawQuery("SELECT DataFileHash FROM tapjoy_VGStoreItems WHERE VGStoreItemID='" + string2 + "'", null);
        String string3 = "";
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.isFirst()) {
                string3 = cursor.getString(cursor.getColumnIndex("DataFileHash"));
            }
            cursor.close();
        }
        return string3;
    }

    public static String getPoints() {
        return tapPoints;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static ArrayList<VGStoreItem> getPurchasedItems(Context context) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = TapjoyDatabaseUtil.getTapjoyDatabase(context).rawQuery("SELECT i.VGStoreItemID,i.AppleProductID,i.Price,i.Name,i.Description,i.ItemTypeName,i.ItemsOwned,i.ThumbImageName,i.FullImageName,i.DataFileName,i.DataFileHash,a.AttributeName,a.AttributeValue FROM tapjoy_VGStoreItems i   left join tapjoy_VGStoreItemAttribute a on (i.VGStoreItemID=a.VGStoreItemID )  ", null);
        int n = cursor.getColumnIndex("VGStoreItemID");
        int n2 = cursor.getColumnIndex("AttributeName");
        int n3 = cursor.getColumnIndex("AttributeValue");
        int n4 = cursor.getColumnIndex("AppleProductID");
        int n5 = cursor.getColumnIndex("Price");
        int n6 = cursor.getColumnIndex("Name");
        int n7 = cursor.getColumnIndex("Description");
        int n8 = cursor.getColumnIndex("ItemTypeName");
        int n9 = cursor.getColumnIndex("ItemsOwned");
        int n10 = cursor.getColumnIndex("ThumbImageName");
        int n11 = cursor.getColumnIndex("FullImageName");
        int n12 = cursor.getColumnIndex("DataFileName");
        int n13 = cursor.getColumnIndex("DataFileHash");
        VGStoreItem vGStoreItem = null;
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.isFirst()) {
                String string2 = ",";
                do {
                    String string3;
                    if (string2.indexOf(string3 = cursor.getString(n)) > -1) {
                        String string4 = cursor.getString(n2);
                        String string5 = cursor.getString(n3);
                        VGStoreItemAttributeValue vGStoreItemAttributeValue = new VGStoreItemAttributeValue();
                        vGStoreItemAttributeValue.setAttributeType(TJCStringUtility.getStringFromSpecialCharacter(string4));
                        vGStoreItemAttributeValue.setAttributeValue(TJCStringUtility.getStringFromSpecialCharacter(string5));
                        if (vGStoreItem == null) continue;
                        if (vGStoreItem.getVgStoreItemsAttributeValueList() != null) {
                            vGStoreItem.getVgStoreItemsAttributeValueList().add((Object)vGStoreItemAttributeValue);
                            continue;
                        }
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add((Object)vGStoreItemAttributeValue);
                        vGStoreItem.setVgStoreItemsAttributeValueList((ArrayList<VGStoreItemAttributeValue>)arrayList2);
                        continue;
                    }
                    vGStoreItem = new VGStoreItem();
                    string2 = string2 + string3 + ",";
                    String string6 = cursor.getString(n4);
                    Integer n14 = cursor.getInt(n5);
                    String string7 = cursor.getString(n6);
                    String string8 = cursor.getString(n7);
                    String string9 = cursor.getString(n8);
                    Integer n15 = cursor.getInt(n9);
                    String string10 = cursor.getString(n10);
                    String string11 = cursor.getString(n11);
                    String string12 = cursor.getString(n12);
                    String string13 = cursor.getString(n13);
                    String string14 = cursor.getString(n2);
                    String string15 = cursor.getString(n3);
                    VGStoreItemAttributeValue vGStoreItemAttributeValue = new VGStoreItemAttributeValue();
                    vGStoreItemAttributeValue.setAttributeType(TJCStringUtility.getStringFromSpecialCharacter(string14));
                    vGStoreItemAttributeValue.setAttributeValue(TJCStringUtility.getStringFromSpecialCharacter(string15));
                    ArrayList arrayList3 = new ArrayList();
                    if (vGStoreItemAttributeValue != null && vGStoreItemAttributeValue.getAttributeType() != null) {
                        arrayList3.add((Object)vGStoreItemAttributeValue);
                    }
                    vGStoreItem.setVgStoreItemID(string3);
                    vGStoreItem.setProductID(string6);
                    vGStoreItem.setPrice(n14);
                    vGStoreItem.setName(TJCStringUtility.getStringFromSpecialCharacter(string7));
                    vGStoreItem.setDescription(TJCStringUtility.getStringFromSpecialCharacter(string8));
                    vGStoreItem.setVgStoreItemTypeName(TJCStringUtility.getStringFromSpecialCharacter(string9));
                    vGStoreItem.setNumberOwned(n15);
                    vGStoreItem.setThumbImageUrl(string10);
                    vGStoreItem.setFullImageUrl(string11);
                    vGStoreItem.setDatafileUrl(string12);
                    vGStoreItem.setDataFileHash(string13);
                    vGStoreItem.setVgStoreItemsAttributeValueList((ArrayList<VGStoreItemAttributeValue>)arrayList3);
                    arrayList.add((Object)vGStoreItem);
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return arrayList;
    }

    public static String getUserID() {
        return userID;
    }

    private static VGStoreItem parseNodeForStoreItems(Node node) {
        VGStoreItem vGStoreItem = null;
        if (node != null) {
            short s2 = node.getNodeType();
            vGStoreItem = null;
            if (s2 == 1) {
                String string2;
                String string3;
                String string4;
                String string5;
                String string6;
                String string7;
                String string8;
                String string9;
                String string10;
                String string11;
                VGStoreItem vGStoreItem2 = new VGStoreItem();
                Element element = (Element)node;
                String string12 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("VGStoreItemID"));
                if (string12 != null && !string12.equals((Object)"")) {
                    vGStoreItem2.setVgStoreItemID(string12);
                }
                if ((string11 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("AppleProductID"))) != null && !string11.equals((Object)"")) {
                    vGStoreItem2.setProductID(string11);
                }
                if ((string9 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("Price"))) != null && !string9.equals((Object)"")) {
                    vGStoreItem2.setPrice(Integer.parseInt((String)string9));
                }
                if ((string3 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("Name"))) != null && !string3.equals((Object)"")) {
                    vGStoreItem2.setName(string3);
                }
                if ((string6 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("Description"))) != null && !string6.equals((Object)"")) {
                    vGStoreItem2.setDescription(string6);
                }
                if ((string4 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("VGStoreItemTypeName"))) != null && !string4.equals((Object)"")) {
                    vGStoreItem2.setVgStoreItemTypeName(string4);
                }
                if ((string7 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("NumberOwned"))) != null && !string7.equals((Object)"")) {
                    vGStoreItem2.setNumberOwned(Integer.parseInt((String)string7));
                }
                if ((string5 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("ThumbImageURL"))) != null && !string5.equals((Object)"")) {
                    vGStoreItem2.setThumbImageUrl(string5);
                }
                if ((string10 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("FullImageURL"))) != null && !string10.equals((Object)"")) {
                    vGStoreItem2.setFullImageUrl(string10);
                }
                if ((string8 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("DatafileURL"))) != null && !string8.equals((Object)"")) {
                    vGStoreItem2.setDatafileUrl(string8);
                }
                if ((string2 = TapjoyUtil.getNodeTrimValue(element.getElementsByTagName("DataHash"))) != null && !string2.equals((Object)"")) {
                    vGStoreItem2.setDataFileHash(string2);
                }
                vGStoreItem2.setVgStoreItemsAttributeValueList(TJCVirtualGoodsData.parseNodeForStoreItemsAttributes(node));
                vGStoreItem = vGStoreItem2;
            }
        }
        try {
            int n = vGStoreItem.getNumberOwned();
            SQLiteDatabase sQLiteDatabase = TapjoyDatabaseUtil.getTapjoyDatabase(context);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UPDATE tapjoy_VGStoreItems SET ItemsOwned='" + n + "' ");
            stringBuilder.append("WHERE VGStoreItemID='" + vGStoreItem.getVgStoreItemID() + "'");
            sQLiteDatabase.execSQL(stringBuilder.toString());
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("UPDATE tapjoy_VGStoreItemAttribute SET AttributeValue='" + n + "' ");
            stringBuilder2.append("WHERE VGStoreItemID='" + vGStoreItem.getVgStoreItemID() + "' AND AttributeName='quantity'");
            sQLiteDatabase.execSQL(stringBuilder2.toString());
            return vGStoreItem;
        }
        catch (Exception exception) {
            return vGStoreItem;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static ArrayList<VGStoreItemAttributeValue> parseNodeForStoreItemsAttributes(Node node) {
        Element element = (Element)node;
        ArrayList arrayList = new ArrayList();
        NodeList nodeList = element.getElementsByTagName("VGStoreItemAttributeValueReturnClass");
        int n = 0;
        while (n < nodeList.getLength()) {
            VGStoreItemAttributeValue vGStoreItemAttributeValue;
            Node node2 = nodeList.item(n);
            if (node2 != null && node2.getNodeType() == 1) {
                String string2;
                VGStoreItemAttributeValue vGStoreItemAttributeValue2 = new VGStoreItemAttributeValue();
                Element element2 = (Element)node2;
                String string3 = TapjoyUtil.getNodeTrimValue(element2.getElementsByTagName("AttributeType"));
                if (string3 != null && !string3.equals((Object)"")) {
                    vGStoreItemAttributeValue2.setAttributeType(string3);
                }
                if ((string2 = TapjoyUtil.getNodeTrimValue(element2.getElementsByTagName("AttributeValue"))) != null && !string2.equals((Object)"")) {
                    vGStoreItemAttributeValue2.setAttributeValue(string2);
                }
                vGStoreItemAttributeValue = vGStoreItemAttributeValue2;
            } else {
                vGStoreItemAttributeValue = null;
            }
            arrayList.add(vGStoreItemAttributeValue);
            ++n;
        }
        return arrayList;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static ArrayList<String> parsePurchaseVGWithCurrencyResponse(String var0_1, Context var1) {
        block14 : {
            var2_2 = 0;
            var3_3 = DocumentBuilderFactory.newInstance();
            try {
                var4_4 = new ByteArrayInputStream(var0_1.getBytes("UTF-8"));
                var6_5 = var3_3.newDocumentBuilder().parse((InputStream)var4_4);
                var7_6 = new ArrayList();
                var8_7 = var6_5.getElementsByTagName("UserAccountObject");
                var9_8 = 0;
                do {
                    if (var9_8 >= var8_7.getLength()) {
                        var10_14 = var6_5.getElementsByTagName("TapjoyConnectReturnObject");
                        break;
                    }
                    var22_9 = var8_7.item(var9_8);
                    if (var22_9.getNodeType() == 1) {
                        var23_11 = (Element)var22_9;
                        var24_12 = TapjoyUtil.getNodeTrimValue(var23_11.getElementsByTagName("TapPoints"));
                        if (var24_12 != null && !var24_12.equals((Object)"")) {
                            TJCVirtualGoodsData.savePoints(Integer.parseInt((String)var24_12));
                        }
                        if ((var25_13 = TapjoyUtil.getNodeTrimValue(var23_11.getElementsByTagName("PointsID"))) != null && !var25_13.equals((Object)"")) {
                            TJCVirtualGoodsData.saveUserID(var25_13);
                        }
                        if ((var26_10 = TapjoyUtil.getNodeTrimValue(var23_11.getElementsByTagName("CurrencyName"))) != null && !var26_10.equals((Object)"")) {
                            TJCVirtualGoodsData.saveCurrencyName(var26_10);
                        }
                    }
                    ++var9_8;
                } while (true);
lbl25: // 2 sources:
                do {
                    if (var2_2 >= var10_14.getLength()) return var7_6;
                    var11_15 = var10_14.item(var2_2);
                    if (var11_15.getNodeType() != 1) break block14;
                    var12_18 = (Element)var11_15;
                    var13_16 = TapjoyUtil.getNodeTrimValue(var12_18.getElementsByTagName("Message"));
                    if (var13_16 != null && !var13_16.equals((Object)"")) {
                        var7_6.add((Object)var13_16);
                    } else {
                        var7_6.add((Object)"");
                    }
                    if ((var15_17 = TapjoyUtil.getNodeTrimValue(var12_18.getElementsByTagName("Success"))) != null) {
                        var7_6.add((Object)var15_17);
                    } else {
                        var7_6.add((Object)"");
                    }
                    if ((var17_19 = TapjoyUtil.getNodeTrimValue(var12_18.getElementsByTagName("ErrorMessage"))) == null || var17_19.equals((Object)"")) break;
                    var7_6.add((Object)var17_19);
                    break block14;
                    break;
                } while (true);
            }
            catch (Exception var5_20) {
                TapjoyLog.e("TapjoyVirtualGoodsData", "parseVGPurchaseResponse: " + var5_20.toString());
                return null;
            }
            var7_6.add((Object)"");
        }
        ++var2_2;
        ** while (true)
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static ArrayList<VGStoreItem> parseVGItemListResponse(String var0_1, int var1, Context var2_2) throws Exception {
        var3_3 = DocumentBuilderFactory.newInstance();
        try {
            var4_4 = new ByteArrayInputStream(var0_1.getBytes("UTF-8"));
            var6_5 = var3_3.newDocumentBuilder().parse((InputStream)var4_4);
            var7_6 = new ArrayList();
            var8_7 = var6_5.getElementsByTagName("UserAccountObject");
            var9_8 = 0;
            do {
                if (var9_8 >= var8_7.getLength()) {
                    var10_14 = var6_5.getElementsByTagName("VGStoreItemReturnClass");
                    var11_15 = null;
                    break;
                }
                var19_10 = var8_7.item(var9_8);
                if (var19_10.getNodeType() == 1) {
                    var20_13 = (Element)var19_10;
                    var21_12 = TapjoyUtil.getNodeTrimValue(var20_13.getElementsByTagName("TapPoints"));
                    if (var21_12 != null && !var21_12.equals((Object)"")) {
                        TapjoyLog.i("TapjoyVirtualGoodsData", "Virtual Currency: " + var21_12);
                        TJCVirtualGoodsData.savePoints(Integer.parseInt((String)var21_12));
                    }
                    if ((var22_9 = TapjoyUtil.getNodeTrimValue(var20_13.getElementsByTagName("PointsID"))) != null && !var22_9.equals((Object)"")) {
                        TJCVirtualGoodsData.saveUserID(var22_9);
                    }
                    if ((var23_11 = TapjoyUtil.getNodeTrimValue(var20_13.getElementsByTagName("CurrencyName"))) != null && !var23_11.equals((Object)"")) {
                        TJCVirtualGoodsData.saveCurrencyName(var23_11);
                    }
                }
                ++var9_8;
            } while (true);
            for (var12_16 = 0; var12_16 < var10_14.getLength(); ++var12_16) {
                var17_17 = var10_14.item(var12_16);
                var7_6.add((Object)TJCVirtualGoodsData.parseNodeForStoreItems(var17_17));
                var11_15 = var17_17;
            }
            var13_18 = var6_5.getElementsByTagName("MoreDataAvailable");
            if (var13_18.getLength() <= 0) ** GOTO lbl-1000
            (Element)var11_15;
            var16_19 = TapjoyUtil.getNodeTrimValue(var13_18);
            if (var16_19 != null && !var16_19.equals((Object)"")) {
                var14_20 = Integer.parseInt((String)var16_19);
            } else lbl-1000: // 2 sources:
            {
                var14_20 = 0;
            }
            if (var1 == 0) {
                VGStoreItem.availableItemsMoreDataAvailable = var14_20;
                return var7_6;
            }
            if (var1 != 1) return var7_6;
            VGStoreItem.purchasedItemsMoreDataAvailable = var14_20;
            return var7_6;
        }
        catch (Exception var5_21) {
            TapjoyLog.e("TapjoyVirtualGoodsData", "parseVGItemListResponse: " + var5_21.toString());
            throw new Exception(var5_21.getMessage());
        }
    }

    public static boolean rowExists(Context context, String string2) {
        Cursor cursor = TapjoyDatabaseUtil.getTapjoyDatabase(context).rawQuery("SELECT * FROM tapjoy_VGStoreItems WHERE VGStoreItemID='" + string2 + "'", null);
        boolean bl = false;
        if (cursor != null) {
            boolean bl2 = cursor.moveToFirst();
            bl = false;
            if (bl2) {
                bl = true;
            }
            cursor.close();
        }
        return bl;
    }

    private static void saveCurrencyName(String string2) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_FILE_NAME, 0).edit();
        editor.putString(PREFS_CURRENCY_KEY, string2);
        editor.commit();
        currencyName = string2;
    }

    private static void savePoints(int n) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_FILE_NAME, 0).edit();
        editor.putInt(PREFS_POINTS_KEY, n);
        editor.commit();
        tapPoints = "" + n;
    }

    private static void saveUserID(String string2) {
        userID = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void deleteVGZip(VGStoreItem vGStoreItem, boolean bl) {
        if (vGStoreItem == null) {
            return;
        }
        String string2 = vGStoreItem.getDatafileUrl();
        if (string2.length() == 0) return;
        TapjoyLog.i(TAPJOY_FILE_SYSTEM, "delete getDatafileUrl: " + string2);
        int n = string2.substring(0, -1 + string2.lastIndexOf("/")).lastIndexOf("/");
        String string3 = null;
        if (n > -1) {
            int n2 = string2.length();
            string3 = null;
            if (n2 > n) {
                String string4 = string2.substring(n + 1, -1 + string2.length());
                TapjoyLog.i(TAPJOY_FILE_SYSTEM, "storeItemFolder: " + string4);
                string3 = null;
                if (string4 != null) {
                    boolean bl2 = string4.equals((Object)"");
                    string3 = null;
                    if (!bl2) {
                        if (bl) {
                            String string5 = Environment.getExternalStorageDirectory().toString();
                            string3 = string5 + "/" + this.clientPackage + "/tempZipDownloads/" + string4 + ".zip";
                        } else {
                            string3 = "data/data/" + this.clientPackage + "/tempZipDownloads/" + string4 + ".zip";
                        }
                    }
                }
            }
        }
        TapjoyLog.i(TAPJOY_FILE_SYSTEM, "deleteVGZip: " + string3);
        try {
            File file = new File(string3);
            if (!file.exists()) return;
            TapjoyLog.i(TAPJOY_FILE_SYSTEM, "deleting file at: " + string3);
            file.delete();
            return;
        }
        catch (Exception exception) {
            TapjoyLog.e(TAPJOY_FILE_SYSTEM, "Error deleting virtual good zip file: " + exception.toString());
            return;
        }
    }

    /*
     * Exception decompiling
     */
    public boolean extractZipFileToFolder(File var1, String var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void saveInfo(VGStoreItem vGStoreItem, boolean bl) {
        boolean bl2;
        String string2;
        TapjoyLog.i(TAPJOY_DATABASE, "saveInfo to SQL DB: " + vGStoreItem.getName());
        String string3 = vGStoreItem.getDatafileUrl();
        if (string3 != null && !string3.equals((Object)"")) {
            String string4 = string3.substring(1 + string3.lastIndexOf("/"), -4 + string3.length());
            string2 = bl ? this.basePathSaveToSDCard + string4 : this.basePathSaveToPhone + string4;
        } else {
            string2 = "";
        }
        TapjoyLog.i(TAPJOY_DATABASE, "dataFileUrl: " + string2);
        if (vGStoreItem == null) return;
        SQLiteDatabase sQLiteDatabase = TapjoyDatabaseUtil.getTapjoyDatabase(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO tapjoy_VGStoreItems (VGStoreItemID, AppleProductID, Price, Name,Description , ItemTypeName , ItemsOwned , ThumbImageName , FullImageName , DataFileName , DataFileHash)");
        stringBuilder.append(" VALUES (");
        stringBuilder.append("'" + vGStoreItem.getVgStoreItemID() + "',");
        stringBuilder.append("'" + vGStoreItem.getProductID() + "',");
        String string5 = "" + vGStoreItem.getPrice();
        if (string5 != null) {
            if (string5.indexOf(",") > -1) {
                string5 = string5.replaceAll(",", "");
            }
            stringBuilder.append(string5 + ",");
        }
        stringBuilder.append("'" + TJCStringUtility.replaceSpecialChars(vGStoreItem.getName()) + "',");
        stringBuilder.append("'" + TJCStringUtility.replaceSpecialChars(vGStoreItem.getDescription()) + "',");
        stringBuilder.append("'" + TJCStringUtility.replaceSpecialChars(vGStoreItem.getVgStoreItemTypeName()) + "',");
        stringBuilder.append(vGStoreItem.getNumberOwned() + ",");
        stringBuilder.append("'" + vGStoreItem.getThumbImageUrl() + "',");
        stringBuilder.append("'" + vGStoreItem.getFullImageUrl() + "',");
        stringBuilder.append("'" + string2 + "',");
        stringBuilder.append("'" + vGStoreItem.getDataFileHash() + "')");
        try {
            sQLiteDatabase.execSQL(stringBuilder.toString());
            bl2 = true;
        }
        catch (SQLException sQLException) {
            TapjoyLog.i(TAPJOY_DATABASE, "Row already exists");
            return;
        }
        if (!bl2) return;
        ArrayList<VGStoreItemAttributeValue> arrayList = vGStoreItem.getVgStoreItemsAttributeValueList();
        int n = 0;
        while (n < arrayList.size()) {
            VGStoreItemAttributeValue vGStoreItemAttributeValue = (VGStoreItemAttributeValue)arrayList.get(n);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("INSERT INTO tapjoy_VGStoreItemAttribute ( VGStoreItemID, AttributeName, AttributeValue)");
            stringBuilder2.append(" Values (");
            stringBuilder2.append("'" + vGStoreItem.getVgStoreItemID() + "',");
            stringBuilder2.append("'" + TJCStringUtility.replaceSpecialChars(vGStoreItemAttributeValue.getAttributeType()) + "',");
            stringBuilder2.append("'" + TJCStringUtility.replaceSpecialChars(vGStoreItemAttributeValue.getAttributeValue()) + "')");
            try {
                sQLiteDatabase.execSQL(stringBuilder2.toString());
            }
            catch (SQLException sQLException) {
                TapjoyLog.e(TAPJOY_DATABASE, "SQLException while trying to insert into table: " + sQLException.toString());
            }
            ++n;
        }
    }

    public static class TJCSQLLiteDatabase
    extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "TapjoyLocalDB.sql";
        private static final int DATABASE_VERSION = 720;
        SQLiteDatabase tapjoyDatabase = null;

        public TJCSQLLiteDatabase(Context context) {
            super(context, DATABASE_NAME, null, 720);
        }

        public SQLiteDatabase getTapjoyDatabase() {
            return this.getWritableDatabase();
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE tapjoy_VGStoreItems(VGStoreItemID TEXT PRIMARY KEY, AppleProductID TEXT(50), Price INTEGER, Name TEXT(100),Description TEXT(1000), ItemTypeName TEXT(100), ItemsOwned INTEGER, ThumbImageName TEXT(100), FullImageName TEXT(100), DataFileName TEXT(100), DataFileHash TEXT(100))");
            sQLiteDatabase.execSQL("CREATE TABLE tapjoy_VGStoreItemAttribute(id INTEGER PRIMARY KEY, VGStoreItemID TEXT, AttributeName TEXT(100), AttributeValue TEXT(100))");
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
            TapjoyLog.w(TJCVirtualGoodsData.TAPJOY_DATABASE, "****************************************");
            TapjoyLog.w(TJCVirtualGoodsData.TAPJOY_DATABASE, "Upgrading database from version " + n + " to " + n2 + ", which will add new column to DB");
            TapjoyLog.w(TJCVirtualGoodsData.TAPJOY_DATABASE, "****************************************");
            try {
                sQLiteDatabase.execSQL("ALTER TABLE tapjoy_VGStoreItems ADD DataFileHash TEXT(100)");
                return;
            }
            catch (Exception exception) {
                TapjoyLog.e("SQLUpgrade", "No need to add the column 'DataFileHash'");
                return;
            }
        }
    }

    public static class TJCStringUtility {
        public static String getStringFromSpecialCharacter(String string2) {
            if (string2 != null) {
                return string2.replaceAll("COMMA", "'");
            }
            return null;
        }

        public static String replaceSpecialChars(String string2) {
            if (string2 != null) {
                return string2.replaceAll("'", "COMMA");
            }
            return null;
        }
    }

    public static class TJCUtility {
        public static long externalFreeMemorySize() {
            if (Environment.getExternalStorageState().equals((Object)"mounted")) {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                return (long)statFs.getAvailableBlocks() * (long)statFs.getBlockSize();
            }
            return -1L;
        }

        public static long internalFreeMemorySize() {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return (long)statFs.getAvailableBlocks() * (long)statFs.getBlockSize();
        }
    }

    public static class TapjoyDatabaseUtil {
        public static TJCSQLLiteDatabase db = null;

        private TapjoyDatabaseUtil() {
        }

        public static SQLiteDatabase getTapjoyDatabase(Context context) {
            if (db == null) {
                db = new TJCSQLLiteDatabase(context);
            }
            return db.getTapjoyDatabase();
        }
    }

}


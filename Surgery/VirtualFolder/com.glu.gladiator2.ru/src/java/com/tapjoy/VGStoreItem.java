/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.tapjoy;

import android.graphics.drawable.Drawable;
import com.tapjoy.VGStoreItemAttributeValue;
import java.util.ArrayList;

public final class VGStoreItem {
    public static final int MAX_ITEMS = 25;
    public static final int PURCHASED_ITEM = 1;
    public static final int STORE_ITEM;
    public static int availableItemsMoreDataAvailable;
    public static int purchasedItemsMoreDataAvailable;
    private String dataFileHash = "";
    private String datafileUrl = "";
    private String description = "";
    private String fullImageUrl = "";
    private String name = "";
    private int numberOwned = 0;
    private int price = 0;
    private String productID = "";
    private boolean shown = true;
    private Drawable thumbImage = null;
    private String thumbImageUrl = "";
    private String vgStoreItemID = "";
    private String vgStoreItemTypeName = "";
    ArrayList<VGStoreItemAttributeValue> vgStoreItemsAttributeValueList = null;

    static {
        availableItemsMoreDataAvailable = 0;
        purchasedItemsMoreDataAvailable = 0;
    }

    public String getDataFileHash() {
        return this.dataFileHash;
    }

    public String getDatafileUrl() {
        return this.datafileUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public String getFullImageUrl() {
        return this.fullImageUrl;
    }

    public String getName() {
        return this.name;
    }

    public int getNumberOwned() {
        return this.numberOwned;
    }

    public int getPrice() {
        return this.price;
    }

    public String getProductID() {
        return this.productID;
    }

    public Drawable getThumbImage() {
        return this.thumbImage;
    }

    public String getThumbImageUrl() {
        return this.thumbImageUrl;
    }

    public String getVgStoreItemID() {
        return this.vgStoreItemID;
    }

    public String getVgStoreItemTypeName() {
        return this.vgStoreItemTypeName;
    }

    public ArrayList<VGStoreItemAttributeValue> getVgStoreItemsAttributeValueList() {
        return this.vgStoreItemsAttributeValueList;
    }

    public boolean isShown() {
        return this.shown;
    }

    public void setDataFileHash(String string2) {
        this.dataFileHash = string2;
    }

    public void setDatafileUrl(String string2) {
        this.datafileUrl = string2;
    }

    public void setDescription(String string2) {
        this.description = string2;
    }

    public void setFullImageUrl(String string2) {
        this.fullImageUrl = string2;
    }

    public void setName(String string2) {
        this.name = string2;
    }

    public void setNumberOwned(int n) {
        this.numberOwned = n;
    }

    public void setPrice(int n) {
        this.price = n;
    }

    public void setProductID(String string2) {
        this.productID = string2;
    }

    public void setShown(boolean bl) {
        this.shown = bl;
    }

    public void setThumbImage(Drawable drawable2) {
        this.thumbImage = drawable2;
    }

    public void setThumbImageUrl(String string2) {
        this.thumbImageUrl = string2;
    }

    public void setVgStoreItemID(String string2) {
        this.vgStoreItemID = string2;
    }

    public void setVgStoreItemTypeName(String string2) {
        this.vgStoreItemTypeName = string2;
    }

    public void setVgStoreItemsAttributeValueList(ArrayList<VGStoreItemAttributeValue> arrayList) {
        this.vgStoreItemsAttributeValueList = arrayList;
    }
}


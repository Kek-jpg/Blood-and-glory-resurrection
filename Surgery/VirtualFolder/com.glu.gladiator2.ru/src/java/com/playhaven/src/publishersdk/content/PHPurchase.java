/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package com.playhaven.src.publishersdk.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.playhaven.src.publishersdk.content.PHContentView;

public class PHPurchase
implements Parcelable {
    public static final Parcelable.Creator<PHPurchase> CREATOR = new Parcelable.Creator<PHPurchase>(){

        public PHPurchase createFromParcel(Parcel parcel) {
            return new PHPurchase(parcel);
        }

        public PHPurchase[] newArray(int n) {
            return new PHPurchase[n];
        }
    };
    public static final String NO_CONTENTVIEW_INTENT = "com.playhaven.null";
    public String callback;
    public String contentview_intent;
    public String name;
    public String product;
    public int quantity;
    public String receipt;
    public Resolution resolution;

    public PHPurchase() {
        this.contentview_intent = NO_CONTENTVIEW_INTENT;
    }

    public PHPurchase(Parcel parcel) {
        this.product = parcel.readString();
        if (this.product != null && this.product.equals((Object)"null")) {
            this.product = null;
        }
        this.name = parcel.readString();
        if (this.name != null && this.name.equals((Object)"null")) {
            this.name = null;
        }
        this.quantity = parcel.readInt();
        this.receipt = parcel.readString();
        if (this.receipt != null && this.receipt.equals((Object)"null")) {
            this.receipt = null;
        }
        this.callback = parcel.readString();
        if (this.callback != null && this.callback.equals((Object)"null")) {
            this.callback = null;
        }
        this.contentview_intent = parcel.readString();
        if (this.contentview_intent != null && this.contentview_intent.equals((Object)"null")) {
            this.contentview_intent = null;
        }
    }

    public PHPurchase(String string2) {
        this.contentview_intent = string2;
    }

    public int describeContents() {
        return 0;
    }

    public void reportResolution(Resolution resolution, Activity activity) {
        this.resolution = resolution;
        Intent intent = new Intent(this.contentview_intent);
        Bundle bundle = new Bundle();
        bundle.putParcelable(PHContentView.Detail.Purchase.getKey(), (Parcelable)this);
        intent.putExtra("com.playhaven.md", bundle);
        activity.sendBroadcast(intent);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n) {
        String string2 = this.product == null ? "null" : this.product;
        parcel.writeString(string2);
        String string3 = this.name == null ? "null" : this.name;
        parcel.writeString(string3);
        parcel.writeInt(this.quantity);
        String string4 = this.receipt == null ? "null" : this.receipt;
        parcel.writeString(string4);
        String string5 = this.callback == null ? "null" : this.callback;
        parcel.writeString(string5);
        String string6 = this.contentview_intent == null ? "null" : this.contentview_intent;
        parcel.writeString(string6);
    }

    public static final class Resolution
    extends Enum<Resolution> {
        private static final /* synthetic */ Resolution[] $VALUES;
        public static final /* enum */ Resolution Buy = new Resolution("buy");
        public static final /* enum */ Resolution Cancel = new Resolution("cancel");
        public static final /* enum */ Resolution Error = new Resolution("error");
        private String type;

        static {
            Resolution[] arrresolution = new Resolution[]{Buy, Cancel, Error};
            $VALUES = arrresolution;
        }

        private Resolution(String string3) {
            this.type = string3;
        }

        public static Resolution valueOf(String string2) {
            return (Resolution)Enum.valueOf(Resolution.class, (String)string2);
        }

        public static Resolution[] values() {
            return (Resolution[])$VALUES.clone();
        }

        public String getType() {
            return this.type;
        }
    }

}


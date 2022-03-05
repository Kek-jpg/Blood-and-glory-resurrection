/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  java.lang.Object
 *  java.lang.String
 */
package com.playhaven.src.publishersdk.content;

import android.os.Parcel;
import android.os.Parcelable;

public class PHReward
implements Parcelable {
    public static final Parcelable.Creator<PHReward> CREATOR = new Parcelable.Creator<PHReward>(){

        public PHReward createFromParcel(Parcel parcel) {
            return new PHReward(parcel);
        }

        public PHReward[] newArray(int n) {
            return new PHReward[n];
        }
    };
    public String name;
    public int quantity;
    public String receipt;

    public PHReward() {
    }

    public PHReward(Parcel parcel) {
        this.name = parcel.readString();
        if (this.name != null && this.name.equals((Object)"null")) {
            this.name = null;
        }
        this.quantity = parcel.readInt();
        this.receipt = parcel.readString();
        if (this.receipt != null && this.receipt.equals((Object)"null")) {
            this.receipt = null;
        }
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n) {
        String string2 = this.name == null ? "null" : this.name;
        parcel.writeString(string2);
        parcel.writeInt(this.quantity);
        String string3 = this.receipt == null ? "null" : this.receipt;
        parcel.writeString(string3);
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.RectF
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.Set
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.playhaven.src.publishersdk.content;

import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.playhaven.src.utils.PHStringUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class PHContent
implements Parcelable {
    public static final Parcelable.Creator<PHContent> CREATOR = new Parcelable.Creator<PHContent>(){

        public PHContent createFromParcel(Parcel parcel) {
            return new PHContent(parcel);
        }

        public PHContent[] newArray(int n) {
            return new PHContent[n];
        }
    };
    public static final String PARCEL_NULL = "null";
    public double closeButtonDelay;
    public String closeURL;
    public JSONObject context;
    private HashMap<String, JSONObject> frameDict;
    public boolean preloaded;
    public TransitionType transition;
    public Uri url;

    public PHContent() {
        this.transition = TransitionType.Modal;
        this.preloaded = false;
        this.frameDict = new HashMap();
        this.closeButtonDelay = 10.0;
        this.transition = TransitionType.Modal;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public PHContent(Parcel parcel) {
        String string2;
        byte by = 1;
        this.transition = TransitionType.Modal;
        this.preloaded = false;
        this.frameDict = new HashMap();
        String string3 = parcel.readString();
        if (string3 != null && !string3.equals((Object)PARCEL_NULL)) {
            this.transition = TransitionType.valueOf(string3);
        }
        this.closeURL = parcel.readString();
        if (this.closeURL != null && this.closeURL.equals((Object)PARCEL_NULL)) {
            this.closeURL = null;
        }
        try {
            String string4 = parcel.readString();
            if (string4 != null && !string4.equals((Object)PARCEL_NULL)) {
                this.context = new JSONObject(string4);
            }
        }
        catch (JSONException jSONException) {
            PHStringUtil.log("Error hydrating PHContent JSON context from Parcel: " + jSONException.getLocalizedMessage());
        }
        if ((string2 = parcel.readString()) != null && !string2.equals((Object)PARCEL_NULL)) {
            this.url = Uri.parse((String)string2);
        }
        this.closeButtonDelay = parcel.readDouble();
        if (parcel.readByte() != by) {
            by = 0;
        }
        this.preloaded = by;
        Bundle bundle = parcel.readBundle();
        if (bundle != null) {
            for (String string5 : bundle.keySet()) {
                try {
                    this.frameDict.put((Object)string5, (Object)new JSONObject(bundle.getString(string5)));
                }
                catch (JSONException jSONException) {
                    PHStringUtil.log("Error deserializing frameDict from bundle in PHContent");
                }
            }
        }
    }

    public PHContent(JSONObject jSONObject) {
        this.transition = TransitionType.Modal;
        this.preloaded = false;
        this.frameDict = new HashMap();
        this.fromJSON(jSONObject);
    }

    private void setFrameDict(JSONObject jSONObject) {
        this.frameDict.clear();
        try {
            Iterator iterator = jSONObject.keys();
            while (iterator.hasNext()) {
                String string2 = (String)iterator.next();
                this.frameDict.put((Object)string2, (Object)((JSONObject)jSONObject.get(string2)));
            }
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void fromJSON(JSONObject jSONObject) {
        block9 : {
            try {
                Object object = jSONObject.opt("frame");
                String string2 = jSONObject.optString("url");
                String string3 = jSONObject.optString("transition");
                this.closeButtonDelay = jSONObject.optDouble("close_delay");
                this.closeURL = jSONObject.optString("close_ping");
                this.frameDict.clear();
                if (object instanceof String) {
                    this.frameDict.put((Object)((String)object), (Object)new JSONObject(String.format((String)"{\"%s\" : \"%s\"}", (Object[])new Object[]{object, object})));
                } else if (object instanceof JSONObject) {
                    PHContent.super.setFrameDict((JSONObject)object);
                }
                Uri uri = string2.compareTo("") != 0 ? Uri.parse((String)string2) : null;
                this.url = uri;
                JSONObject jSONObject2 = jSONObject.optJSONObject("context");
                if (!JSONObject.NULL.equals((Object)jSONObject2) && jSONObject2.length() > 0) {
                    this.context = jSONObject2;
                }
                if (string3.compareTo("") == 0) break block9;
                if (string3.equals((Object)"PH_MODAL")) {
                    this.transition = TransitionType.Modal;
                    return;
                }
                if (string3.equals((Object)"PH_DIALOG")) {
                    this.transition = TransitionType.Dialog;
                    return;
                }
            }
            catch (JSONException jSONException) {
                jSONException.printStackTrace();
                return;
            }
            this.transition = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public RectF getFrame(int n) {
        JSONObject jSONObject;
        String string2 = n == 2 ? "PH_LANDSCAPE" : "PH_PORTRAIT";
        if (this.frameDict.containsKey((Object)"PH_FULLSCREEN")) {
            return new RectF(0.0f, 0.0f, 2.1474836E9f, 2.1474836E9f);
        }
        if (this.frameDict.containsKey((Object)string2) && (jSONObject = (JSONObject)this.frameDict.get((Object)string2)) != null) {
            float f2 = (float)jSONObject.optDouble("x");
            float f3 = (float)jSONObject.optDouble("y");
            float f4 = (float)jSONObject.optDouble("w");
            float f5 = (float)jSONObject.optDouble("h");
            return new RectF(f2, f3, f4 + f2, f5 + f3);
        }
        return new RectF(0.0f, 0.0f, 0.0f, 0.0f);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        String string2;
        try {
            String string3;
            string2 = string3 = this.context.toString(2);
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
            string2 = "(NULL)";
        }
        Object[] arrobject = new Object[]{this.closeURL, this.closeButtonDelay, this.url, string2};
        return String.format((String)"Close URL: %s - Close Delay: %.1f - URL: %s\n\n---------------------------------\nJSON Context: %s", (Object[])arrobject);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n) {
        String string2 = this.transition != null ? this.transition.name() : PARCEL_NULL;
        parcel.writeString(string2);
        String string3 = this.closeURL != null ? this.closeURL : PARCEL_NULL;
        parcel.writeString(string3);
        String string4 = this.context != null ? this.context.toString() : PARCEL_NULL;
        parcel.writeString(string4);
        String string5 = this.url != null ? this.url.toString() : PARCEL_NULL;
        parcel.writeString(string5);
        parcel.writeDouble(this.closeButtonDelay);
        boolean bl = this.preloaded;
        parcel.writeByte((byte)(bl ? 1 : 0));
        if (this.frameDict != null) {
            Bundle bundle = new Bundle();
            for (String string6 : this.frameDict.keySet()) {
                bundle.putString(string6, ((JSONObject)this.frameDict.get((Object)string6)).toString());
            }
            parcel.writeBundle(bundle);
        }
    }

    public static final class TransitionType
    extends Enum<TransitionType> {
        private static final /* synthetic */ TransitionType[] $VALUES;
        public static final /* enum */ TransitionType Dialog;
        public static final /* enum */ TransitionType Modal;
        public static final /* enum */ TransitionType Unknown;

        static {
            Unknown = new TransitionType();
            Modal = new TransitionType();
            Dialog = new TransitionType();
            TransitionType[] arrtransitionType = new TransitionType[]{Unknown, Modal, Dialog};
            $VALUES = arrtransitionType;
        }

        public static TransitionType valueOf(String string2) {
            return (TransitionType)Enum.valueOf(TransitionType.class, (String)string2);
        }

        public static TransitionType[] values() {
            return (TransitionType[])$VALUES.clone();
        }
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  java.lang.Class
 *  java.lang.ClassLoader
 *  java.lang.IllegalAccessException
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Field
 */
package com.amazon.device.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.amazon.device.ads.Log;
import java.lang.reflect.Field;

class Controller {
    private static String LOG_TAG = "Controller";

    Controller() {
    }

    public static class Dimensions
    extends ReflectedParcelable {
        public static final Parcelable.Creator<Dimensions> CREATOR = new Parcelable.Creator<Dimensions>(){

            public Dimensions createFromParcel(Parcel parcel) {
                return new Dimensions(parcel);
            }

            public Dimensions[] newArray(int n2) {
                return new Dimensions[n2];
            }
        };
        public int height;
        public int width;
        public int x;
        public int y;

        public Dimensions() {
            this.x = -1;
            this.y = -1;
            this.width = -1;
            this.height = -1;
        }

        public Dimensions(Parcel parcel) {
            super(parcel);
        }

    }

    public static class PlayerProperties
    extends ReflectedParcelable {
        public static final Parcelable.Creator<PlayerProperties> CREATOR = new Parcelable.Creator<PlayerProperties>(){

            public PlayerProperties createFromParcel(Parcel parcel) {
                return new PlayerProperties(parcel);
            }

            public PlayerProperties[] newArray(int n2) {
                return new PlayerProperties[n2];
            }
        };
        public boolean audioMuted;
        public boolean autoPlay;
        public boolean doLoop;
        public boolean inline;
        public boolean showControl;
        public String startStyle;
        public String stopStyle;

        public PlayerProperties() {
            this.autoPlay = true;
            this.showControl = true;
            this.doLoop = false;
            this.audioMuted = false;
            this.startStyle = "normal";
            this.stopStyle = "normal";
        }

        public PlayerProperties(Parcel parcel) {
            super(parcel);
        }

        public boolean doLoop() {
            return this.doLoop;
        }

        public boolean doMute() {
            return this.audioMuted;
        }

        public boolean exitOnComplete() {
            return this.stopStyle.equalsIgnoreCase("exit");
        }

        public boolean isAutoPlay() {
            return this.autoPlay;
        }

        public boolean isFullScreen() {
            return this.startStyle.equalsIgnoreCase("fullscreen");
        }

        public void muteAudio() {
            this.audioMuted = true;
        }

        public void setProperties(boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, String string, String string2) {
            this.audioMuted = bl;
            this.autoPlay = bl2;
            this.showControl = bl3;
            this.inline = bl4;
            this.doLoop = bl5;
            this.startStyle = string;
            this.stopStyle = string2;
        }

        public boolean showControl() {
            return this.showControl;
        }

    }

    public static class ReflectedParcelable
    implements Parcelable {
        public static final Parcelable.Creator<ReflectedParcelable> CREATOR = new Parcelable.Creator<ReflectedParcelable>(){

            public ReflectedParcelable createFromParcel(Parcel parcel) {
                return new ReflectedParcelable(parcel);
            }

            public ReflectedParcelable[] newArray(int n2) {
                return new ReflectedParcelable[n2];
            }
        };

        public ReflectedParcelable() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected ReflectedParcelable(Parcel parcel) {
            Field[] arrfield = this.getClass().getDeclaredFields();
            int n2 = arrfield.length;
            int n3 = 0;
            while (n3 < n2) {
                Field field = arrfield[n3];
                try {
                    if (!(field.get((Object)this) instanceof Parcelable.Creator)) {
                        field.set((Object)this, parcel.readValue(null));
                    }
                }
                catch (IllegalAccessException illegalAccessException) {
                    String string = LOG_TAG;
                    Object[] arrobject = new Object[]{illegalAccessException.getMessage()};
                    Log.e(string, "Error: Could not create object from parcel: %s", arrobject);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    String string = LOG_TAG;
                    Object[] arrobject = new Object[]{illegalArgumentException.getMessage()};
                    Log.e(string, "Error: Could not create object from parcel: %s", arrobject);
                }
                ++n3;
            }
            return;
        }

        public int describeContents() {
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void writeToParcel(Parcel parcel, int n2) {
            Field[] arrfield = this.getClass().getDeclaredFields();
            int n3 = arrfield.length;
            int n4 = 0;
            while (n4 < n3) {
                Field field = arrfield[n4];
                try {
                    Object object = field.get((Object)this);
                    if (!(object instanceof Parcelable.Creator)) {
                        parcel.writeValue(object);
                    }
                }
                catch (IllegalAccessException illegalAccessException) {
                    String string = LOG_TAG;
                    Object[] arrobject = new Object[]{illegalAccessException.getMessage()};
                    Log.e(string, "Error: Could not write to parcel: %s", arrobject);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    String string = LOG_TAG;
                    Object[] arrobject = new Object[]{illegalArgumentException.getMessage()};
                    Log.e(string, "Error: Could not write to parcel: %s", arrobject);
                }
                ++n4;
            }
            return;
        }

    }

}


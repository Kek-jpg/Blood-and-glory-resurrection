/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  java.lang.Object
 *  java.lang.String
 */
package com.unity3d.player.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.unity3d.player.a.e;

public interface c
extends IInterface {
    public void a(int var1, String var2, String var3);

    public static abstract class com.unity3d.player.a.c$a
    extends Binder
    implements c {
        public com.unity3d.player.a.c$a() {
            this.attachInterface((IInterface)this, new String(e.a));
        }

        public static c a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(new String(e.a));
            if (iInterface != null && iInterface instanceof c) {
                return (c)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString(new String(e.a));
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface(new String(e.a));
            this.a(parcel.readInt(), parcel.readString(), parcel.readString());
            return true;
        }

        static final class a
        implements c {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override
            public final void a(int n2, String string2, String string3) {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(new String(e.a));
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    this.a.transact(1, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public final IBinder asBinder() {
                return this.a;
            }
        }

    }

}


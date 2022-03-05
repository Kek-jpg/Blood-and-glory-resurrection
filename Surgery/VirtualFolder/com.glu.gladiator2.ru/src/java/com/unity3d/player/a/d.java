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
import com.unity3d.player.a.c;
import com.unity3d.player.a.e;

public interface d
extends IInterface {
    public void a(long var1, String var3, c var4);

    public static abstract class com.unity3d.player.a.d$a
    extends Binder
    implements d {
        public com.unity3d.player.a.d$a() {
            this.attachInterface((IInterface)this, new String(e.b));
        }

        public static d a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(new String(e.b));
            if (iInterface != null && iInterface instanceof d) {
                return (d)iInterface;
            }
            return new a(iBinder);
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString(new String(e.b));
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface(new String(e.b));
            this.a(parcel.readLong(), parcel.readString(), c.a.a(parcel.readStrongBinder()));
            return true;
        }

        static final class a
        implements d {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public final void a(long l2, String string2, c c2) {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(new String(e.b));
                    parcel.writeLong(l2);
                    parcel.writeString(string2);
                    IBinder iBinder = null;
                    if (c2 != null) {
                        iBinder = c2.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
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


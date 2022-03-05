/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.DataInput
 *  java.io.DataOutput
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 */
package com.flurry.android;

import com.flurry.android.an;
import java.io.DataInput;
import java.io.DataOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class bb {
    private static int a = 1;
    private final int b;
    private final long c;
    private final String d;
    private List<an> e;

    bb(long l2, String string) {
        int n2 = a;
        a = n2 + 1;
        this.b = n2;
        this.c = l2;
        this.d = string;
        this.e = new ArrayList();
    }

    bb(DataInput dataInput) {
        this.b = dataInput.readInt();
        this.c = dataInput.readLong();
        this.d = dataInput.readUTF();
        this.e = new ArrayList();
        short s2 = dataInput.readShort();
        for (short s3 = 0; s3 < s2; s3 = (short)(s3 + 1)) {
            this.e.add((Object)new an(dataInput));
        }
    }

    final int a() {
        return this.b;
    }

    final void a(an an2) {
        this.e.add((Object)an2);
    }

    final void a(DataOutput dataOutput) {
        dataOutput.writeInt(this.b);
        dataOutput.writeLong(this.c);
        dataOutput.writeUTF(this.d);
        dataOutput.writeShort(this.e.size());
        Iterator iterator = this.e.iterator();
        while (iterator.hasNext()) {
            ((an)iterator.next()).a(dataOutput);
        }
    }

    final String b() {
        return this.d;
    }

    final long c() {
        return this.c;
    }

    final List<an> d() {
        return this.e;
    }
}


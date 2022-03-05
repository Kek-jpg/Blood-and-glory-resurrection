/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.DataInput
 *  java.io.DataOutput
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Arrays
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.android;

import com.flurry.android.bc;
import java.io.DataInput;
import java.io.DataOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class an {
    private static List<String> a = Arrays.asList((Object[])new String[]{"requested", "filled", "unfilled", "rendered", "clicked", "videoStart", "videoCompleted", "videoProgressed", "sentToUrl", "adClosed"});
    private final String b;
    private final boolean c;
    private final long d;
    private final Map<String, String> e;

    an(DataInput dataInput) {
        this.b = dataInput.readUTF();
        this.c = dataInput.readBoolean();
        this.d = dataInput.readLong();
        this.e = new HashMap();
        short s2 = dataInput.readShort();
        for (short s3 = 0; s3 < s2; s3 = (short)(s3 + 1)) {
            this.e.put((Object)dataInput.readUTF(), (Object)dataInput.readUTF());
        }
    }

    an(String string, boolean bl, long l2, Map<String, String> map) {
        if (!a.contains((Object)string)) {
            bc.a("FlurryAgent", "AdEvent initialized with unrecognized type: " + string);
        }
        this.b = string;
        this.c = bl;
        this.d = l2;
        if (map == null) {
            this.e = new HashMap();
            return;
        }
        this.e = map;
    }

    final String a() {
        return this.b;
    }

    final void a(DataOutput dataOutput) {
        dataOutput.writeUTF(this.b);
        dataOutput.writeBoolean(this.c);
        dataOutput.writeLong(this.d);
        dataOutput.writeShort(this.e.size());
        for (Map.Entry entry : this.e.entrySet()) {
            dataOutput.writeUTF((String)entry.getKey());
            dataOutput.writeUTF((String)entry.getValue());
        }
    }

    final boolean b() {
        return this.c;
    }

    final long c() {
        return this.d;
    }

    final Map<String, String> d() {
        return this.e;
    }
}


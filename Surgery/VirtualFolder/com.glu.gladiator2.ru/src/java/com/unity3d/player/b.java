/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.util.Log
 *  java.io.FileNotFoundException
 *  java.io.FileReader
 *  java.io.IOException
 *  java.io.LineNumberReader
 *  java.io.Reader
 *  java.lang.CharSequence
 *  java.lang.Character
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 */
package com.unity3d.player;

import android.os.Build;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

final class b {
    private int a = 0;
    private String b;
    private int c = 0;

    b() {
        Map map = b.a("/proc/cpuinfo");
        String string = (String)map.get((Object)"CPU architecture");
        String string2 = (String)map.get((Object)"Features");
        if (string != null) {
            int n2;
            int n3 = string.length();
            for (n2 = 0; n2 < n3 && Character.isDigit((char)string.charAt(n2)); ++n2) {
            }
            String string3 = string.substring(0, n2);
            if (Build.CPU_ABI.toLowerCase().startsWith("arm")) {
                this.a = 2 | this.a;
                if (Integer.decode((String)string3) >= 7 && !Build.CPU_ABI.equalsIgnoreCase("armeabi")) {
                    this.a = 16 | this.a;
                }
                if (Integer.decode((String)string3) >= 6) {
                    this.a = 8 | this.a;
                }
                if (Integer.decode((String)string3) >= 5) {
                    this.a = 4 | this.a;
                }
            }
        }
        if (string2 != null) {
            if (string2.contains((CharSequence)"vfpv3")) {
                this.a = 32 | this.a;
            }
            if (string2.contains((CharSequence)"neon")) {
                this.a = 64 | this.a;
            }
            if (string2.contains((CharSequence)"vfp")) {
                this.a = 128 | this.a;
            }
        }
        if (Build.CPU_ABI.equalsIgnoreCase("x86")) {
            this.a = 1;
        }
        this.b = (String)map.get((Object)"Processor");
        this.c = b.b((String)b.a("/proc/meminfo").get((Object)"MemTotal"));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Map a(String string) {
        HashMap hashMap = new HashMap();
        try {
            LineNumberReader lineNumberReader = new LineNumberReader((Reader)new FileReader(string), 8192);
            String string2 = lineNumberReader.readLine();
            while (string2 != null) {
                String string3;
                int n2 = string2.indexOf(58);
                if (n2 >= 0) {
                    hashMap.put((Object)string2.substring(0, n2).trim(), (Object)string2.substring(n2 + 1).trim());
                }
                string2 = string3 = lineNumberReader.readLine();
            }
            return hashMap;
        }
        catch (FileNotFoundException fileNotFoundException) {
            Log.e((String)"FileNotFoundException", (String)fileNotFoundException.toString());
        }
        return hashMap;
        catch (IOException iOException) {
            Log.e((String)"IOException", (String)iOException.toString());
            return hashMap;
        }
    }

    private static int b(String string) {
        int n2;
        int n3 = string.length();
        for (n2 = 0; n2 < n3 && Character.isDigit((char)string.charAt(n2)); ++n2) {
        }
        return Integer.decode((String)string.substring(0, n2));
    }

    public final int a() {
        return this.a;
    }

    public final String b() {
        return this.b;
    }

    public final int c() {
        return this.c / 1024;
    }
}


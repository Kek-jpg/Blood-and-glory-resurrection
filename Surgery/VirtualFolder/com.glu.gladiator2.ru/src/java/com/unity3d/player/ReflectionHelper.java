/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Byte
 *  java.lang.Class
 *  java.lang.ClassCastException
 *  java.lang.ClassNotFoundException
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.Void
 *  java.lang.reflect.Array
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.Field
 *  java.lang.reflect.Method
 *  java.lang.reflect.Modifier
 *  java.util.ArrayList
 *  java.util.Iterator
 */
package com.unity3d.player;

import com.unity3d.player.f;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

class ReflectionHelper {
    ReflectionHelper() {
    }

    private static float a(Class class_, Class class_2) {
        if (class_.equals((Object)class_2)) {
            return 1.0f;
        }
        if (!class_.isPrimitive() && !class_2.isPrimitive()) {
            try {
                Class class_3 = class_.asSubclass(class_2);
                if (class_3 != null) {
                    return 0.5f;
                }
            }
            catch (ClassCastException classCastException) {
                // empty catch block
            }
            try {
                Class class_4 = class_2.asSubclass(class_);
                if (class_4 != null) {
                    return 0.1f;
                }
            }
            catch (ClassCastException classCastException) {
                // empty catch block
            }
        }
        return 0.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static float a(Class class_, Class[] arrclass, Class[] arrclass2) {
        if (arrclass2.length == 0) {
            return 0.1f;
        }
        int n = arrclass == null ? 0 : arrclass.length;
        if (n + 1 != arrclass2.length) {
            return 0.0f;
        }
        float f2 = 1.0f;
        if (arrclass != null) {
            Class class_2;
            int n2 = arrclass.length;
            int n3 = 0;
            for (int i2 = 0; i2 < n2; f2 *= ReflectionHelper.a((Class)class_2, (Class)arrclass2[n3]), ++i2) {
                class_2 = arrclass[i2];
                int n4 = n3 + 1;
                n3 = n4;
            }
        }
        return f2 * ReflectionHelper.a(class_, arrclass2[-1 + arrclass2.length]);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Class a(String string2, int[] arrn) {
        int n;
        char c2;
        do {
            if (arrn[0] >= string2.length()) return null;
            n = arrn[0];
            arrn[0] = n + 1;
        } while ((c2 = string2.charAt(n)) == '(' || c2 == ')');
        if (c2 == 'L') {
            int n2 = string2.indexOf(59, arrn[0]);
            if (n2 == -1) return null;
            String string3 = string2.substring(arrn[0], n2);
            arrn[0] = n2 + 1;
            String string4 = string3.replace('/', '.');
            return Class.forName((String)string4);
        }
        if (c2 == 'Z') {
            return Boolean.TYPE;
        }
        if (c2 == 'I') {
            return Integer.TYPE;
        }
        if (c2 == 'F') {
            return Float.TYPE;
        }
        if (c2 == 'V') {
            return Void.TYPE;
        }
        if (c2 == 'B') {
            return Byte.TYPE;
        }
        if (c2 == 'S') {
            return Short.TYPE;
        }
        if (c2 == 'J') {
            return Long.TYPE;
        }
        if (c2 == 'D') {
            return Double.TYPE;
        }
        if (c2 == '[') {
            return Array.newInstance((Class)ReflectionHelper.a(string2, arrn), (int)0).getClass();
        }
        f.Log(5, "parseType; " + c2 + " is not known!");
        return null;
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    private static Class[] a(String string2) {
        Class class_;
        int[] arrn = new int[]{0};
        ArrayList arrayList = new ArrayList();
        while (arrn[0] < string2.length() && (class_ = ReflectionHelper.a(string2, arrn)) != null) {
            arrayList.add((Object)class_);
        }
        Class[] arrclass = new Class[arrayList.size()];
        Iterator iterator = arrayList.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            Class class_2 = (Class)iterator.next();
            int n2 = n + 1;
            arrclass[n] = class_2;
            n = n2;
        }
        return arrclass;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static Constructor getConstructorID(Class class_, String string2) {
        Class[] arrclass = ReflectionHelper.a(string2);
        Constructor constructor = null;
        float f2 = 0.0f;
        Constructor[] arrconstructor = class_.getConstructors();
        int n = arrconstructor.length;
        int n2 = 0;
        do {
            Constructor constructor2;
            float f3;
            block5 : {
                block3 : {
                    Constructor constructor3;
                    block4 : {
                        block2 : {
                            if (n2 >= n) break block2;
                            constructor3 = arrconstructor[n2];
                            f3 = ReflectionHelper.a(Void.TYPE, constructor3.getParameterTypes(), arrclass);
                            if (!(f3 > f2)) break block3;
                            if (f3 == 1.0f) break block4;
                            constructor2 = constructor3;
                            break block5;
                        }
                        constructor3 = constructor;
                    }
                    if (constructor3 == null) {
                        f.Log(6, "getConstructorID(\"" + class_.getName() + "\", \"" + string2 + "\") FAILED!");
                    }
                    return constructor3;
                }
                f3 = f2;
                constructor2 = constructor;
            }
            ++n2;
            constructor = constructor2;
            f2 = f3;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected static Field getFieldID(Class var0_1, String var1, String var2_3, boolean var3_2) {
        var4_4 = ReflectionHelper.a(var2_3);
        var5_5 = 0.0f;
        var6_6 = null;
        while (var0_1 != null) {
            block4 : {
                var7_13 = var0_1.getDeclaredFields();
                var8_7 = var7_13.length;
                var10_12 = var6_6;
                for (var9_10 = 0; var9_10 < var8_7; ++var9_10) {
                    var11_8 = var7_13[var9_10];
                    if (var3_2 != Modifier.isStatic((int)var11_8.getModifiers()) || var11_8.getName().compareTo(var1) != 0 || !((var12_11 = ReflectionHelper.a(var11_8.getType(), null, var4_4)) > var5_5)) ** GOTO lbl17
                    if (var12_11 != 1.0f) {
                        var13_9 = var11_8;
                    } else {
                        var5_5 = var12_11;
                        var6_6 = var11_8;
                        break block4;
lbl17: // 1 sources:
                        var12_11 = var5_5;
                        var13_9 = var10_12;
                    }
                    var10_12 = var13_9;
                    var5_5 = var12_11;
                }
                var6_6 = var10_12;
            }
            if (var5_5 == 1.0f || var0_1.isPrimitive() || var0_1.isInterface() || var0_1.equals(Object.class) || var0_1.equals((Object)Void.TYPE)) break;
            var0_1 = var0_1.getSuperclass();
        }
        if (var6_6 != null) return var6_6;
        f.Log(6, "getFieldID(\"" + var1 + "\", \"" + var2_3 + "\") FAILED!");
        return var6_6;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected static Method getMethodID(Class var0_1, String var1, String var2_3, boolean var3_2) {
        var4_4 = ReflectionHelper.a(var2_3);
        var5_5 = null;
        var6_6 = 0.0f;
        while (var0_1 != null) {
            block4 : {
                var7_13 = var0_1.getDeclaredMethods();
                var8_7 = var7_13.length;
                var10_12 = var5_5;
                for (var9_10 = 0; var9_10 < var8_7; ++var9_10) {
                    var11_8 = var7_13[var9_10];
                    if (var3_2 != Modifier.isStatic((int)var11_8.getModifiers()) || var11_8.getName().compareTo(var1) != 0 || !((var12_11 = ReflectionHelper.a(var11_8.getReturnType(), var11_8.getParameterTypes(), var4_4)) > var6_6)) ** GOTO lbl17
                    if (var12_11 != 1.0f) {
                        var13_9 = var11_8;
                    } else {
                        var6_6 = var12_11;
                        var5_5 = var11_8;
                        break block4;
lbl17: // 1 sources:
                        var12_11 = var6_6;
                        var13_9 = var10_12;
                    }
                    var10_12 = var13_9;
                    var6_6 = var12_11;
                }
                var5_5 = var10_12;
            }
            if (var6_6 == 1.0f || var0_1.isPrimitive() || var0_1.isInterface() || var0_1.equals(Object.class) || var0_1.equals((Object)Void.TYPE)) break;
            var0_1 = var0_1.getSuperclass();
        }
        if (var5_5 != null) return var5_5;
        f.Log(6, "getMethodID(\"" + var1 + "\", \"" + var2_3 + "\") FAILED!");
        return var5_5;
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.ClassNotFoundException
 *  java.lang.IllegalAccessException
 *  java.lang.NoSuchMethodException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.InvocationTargetException
 *  java.lang.reflect.Method
 */
package com.kontagent.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LibUtils {
    private static final String LIB_BASE_PACKAGE = "com.kontagent.android.lib";
    public static final String LIB_FB = "KontagentFBLib";

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static <R, T> R callPrivateMethod(Class class_, String string2, Class<T>[] arrclass, Object[] arrobject) {
        Object object;
        try {
            Object object2 = LibUtils.getInstance(class_);
            Method method = class_.getDeclaredMethod(string2, arrclass);
            method.setAccessible(true);
            object = method.invoke(object2, arrobject);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
            do {
                return null;
                break;
            } while (true);
        }
        catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
            return null;
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
            return null;
        }
        return (R)object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static <R, T> R callPublicMethod(Class class_, String string2, Class<T>[] arrclass, Object[] arrobject) {
        Object object;
        try {
            Object object2 = LibUtils.getInstance(class_);
            object = class_.getMethod(string2, arrclass).invoke(object2, arrobject);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
            do {
                return null;
                break;
            } while (true);
        }
        catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
            return null;
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
            return null;
        }
        return (R)object;
    }

    private static Object getInstance(Class class_) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = class_.getMethod("getInstance", new Class[0]);
        if (method == null) {
            throw new IllegalAccessException("Dynamic library should have public static 'getInstance()' method");
        }
        return method.invoke(null, new Object[0]);
    }

    public static Class getRuntimeLibClass(String string2) {
        try {
            Class class_ = Class.forName((String)("com.kontagent.android.lib." + string2));
            return class_;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }
}


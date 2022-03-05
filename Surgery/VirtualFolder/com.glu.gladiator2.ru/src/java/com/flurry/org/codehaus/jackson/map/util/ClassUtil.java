/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.util.ClassUtil$EnumTypeLocator
 *  java.lang.Boolean
 *  java.lang.Byte
 *  java.lang.Character
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Double
 *  java.lang.Enum
 *  java.lang.Error
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.NoSuchMethodException
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.SecurityException
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.Void
 *  java.lang.reflect.AccessibleObject
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.Member
 *  java.lang.reflect.Method
 *  java.lang.reflect.Modifier
 *  java.lang.reflect.Proxy
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.EnumMap
 *  java.util.EnumSet
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.Set
 */
package com.flurry.org.codehaus.jackson.map.util;

import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ClassUtil {
    /*
     * Enabled aggressive block sorting
     */
    private static void _addSuperTypes(Class<?> class_, Class<?> class_2, Collection<Class<?>> collection, boolean bl) {
        block6 : {
            block7 : {
                block5 : {
                    if (class_ == class_2 || class_ == null || class_ == Object.class) break block5;
                    if (!bl) break block6;
                    if (!collection.contains(class_)) break block7;
                }
                return;
            }
            collection.add(class_);
        }
        Class[] arrclass = class_.getInterfaces();
        int n = arrclass.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                ClassUtil._addSuperTypes(class_.getSuperclass(), class_2, collection, true);
                return;
            }
            ClassUtil._addSuperTypes(arrclass[n2], class_2, collection, true);
            ++n2;
        } while (true);
    }

    public static String canBeABeanType(Class<?> class_) {
        if (class_.isAnnotation()) {
            return "annotation";
        }
        if (class_.isArray()) {
            return "array";
        }
        if (class_.isEnum()) {
            return "enum";
        }
        if (class_.isPrimitive()) {
            return "primitive";
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void checkAndFixAccess(Member member) {
        AccessibleObject accessibleObject = (AccessibleObject)member;
        try {
            accessibleObject.setAccessible(true);
            return;
        }
        catch (SecurityException securityException) {
            if (accessibleObject.isAccessible()) return;
            Class class_ = member.getDeclaringClass();
            throw new IllegalArgumentException("Can not access " + (Object)member + " (from class " + class_.getName() + "; failed to set access: " + securityException.getMessage());
        }
    }

    public static <T> T createInstance(Class<T> class_, boolean bl) throws IllegalArgumentException {
        Object object;
        Constructor<T> constructor = ClassUtil.findConstructor(class_, bl);
        if (constructor == null) {
            throw new IllegalArgumentException("Class " + class_.getName() + " has no default (no arg) constructor");
        }
        try {
            object = constructor.newInstance(new Object[0]);
        }
        catch (Exception exception) {
            ClassUtil.unwrapAndThrowAsIAE(exception, "Failed to instantiate class " + class_.getName() + ", problem: " + exception.getMessage());
            return null;
        }
        return (T)object;
    }

    public static Object defaultValue(Class<?> class_) {
        if (class_ == Integer.TYPE) {
            return 0;
        }
        if (class_ == Long.TYPE) {
            return 0L;
        }
        if (class_ == Boolean.TYPE) {
            return Boolean.FALSE;
        }
        if (class_ == Double.TYPE) {
            return 0.0;
        }
        if (class_ == Float.TYPE) {
            return Float.valueOf((float)0.0f);
        }
        if (class_ == Byte.TYPE) {
            return (byte)0;
        }
        if (class_ == Short.TYPE) {
            return (short)0;
        }
        if (class_ == Character.TYPE) {
            return Character.valueOf((char)'\u0000');
        }
        throw new IllegalArgumentException("Class " + class_.getName() + " is not a primitive type");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static <T> Constructor<T> findConstructor(Class<T> class_, boolean bl) throws IllegalArgumentException {
        Constructor constructor;
        block6 : {
            constructor = class_.getDeclaredConstructor(new Class[0]);
            if (!bl) break block6;
            ClassUtil.checkAndFixAccess((Member)constructor);
            return constructor;
        }
        try {
            if (Modifier.isPublic((int)constructor.getModifiers())) return constructor;
            throw new IllegalArgumentException("Default constructor for " + class_.getName() + " is not accessible (non-public?): not allowed to try modify access via Reflection: can not instantiate type");
        }
        catch (NoSuchMethodException noSuchMethodException) {
            do {
                return null;
                break;
            } while (true);
        }
        catch (Exception exception) {
            ClassUtil.unwrapAndThrowAsIAE(exception, "Failed to find default constructor of class " + class_.getName() + ", problem: " + exception.getMessage());
            return null;
        }
    }

    public static Class<? extends Enum<?>> findEnumType(Class<?> class_) {
        if (class_.getSuperclass() != Enum.class) {
            class_ = class_.getSuperclass();
        }
        return class_;
    }

    public static Class<? extends Enum<?>> findEnumType(Enum<?> enum_) {
        Class class_ = enum_.getClass();
        if (class_.getSuperclass() != Enum.class) {
            class_ = class_.getSuperclass();
        }
        return class_;
    }

    public static Class<? extends Enum<?>> findEnumType(EnumMap<?, ?> enumMap) {
        if (!enumMap.isEmpty()) {
            return ClassUtil.findEnumType((Enum)enumMap.keySet().iterator().next());
        }
        return EnumTypeLocator.instance.enumTypeFor(enumMap);
    }

    public static Class<? extends Enum<?>> findEnumType(EnumSet<?> enumSet) {
        if (!enumSet.isEmpty()) {
            return ClassUtil.findEnumType((Enum)enumSet.iterator().next());
        }
        return EnumTypeLocator.instance.enumTypeFor(enumSet);
    }

    public static List<Class<?>> findSuperTypes(Class<?> class_, Class<?> class_2) {
        return ClassUtil.findSuperTypes(class_, class_2, new ArrayList(8));
    }

    public static List<Class<?>> findSuperTypes(Class<?> class_, Class<?> class_2, List<Class<?>> list) {
        ClassUtil._addSuperTypes(class_, class_2, list, false);
        return list;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String getClassDescription(Object object) {
        Class class_;
        if (object == null) {
            return "unknown";
        }
        if (object instanceof Class) {
            class_ = (Class)object;
            do {
                return class_.getName();
                break;
            } while (true);
        }
        class_ = object.getClass();
        return class_.getName();
    }

    public static Class<?> getOuterClass(Class<?> class_) {
        block5 : {
            if (class_.getEnclosingMethod() == null) break block5;
            return null;
        }
        try {
            if (!Modifier.isStatic((int)class_.getModifiers())) {
                Class class_2 = class_.getEnclosingClass();
                return class_2;
            }
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        return null;
    }

    public static Throwable getRootCause(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean hasGetterSignature(Method method) {
        Class[] arrclass;
        return !Modifier.isStatic((int)method.getModifiers()) && ((arrclass = method.getParameterTypes()) == null || arrclass.length == 0) && Void.TYPE != method.getReturnType();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean isCollectionMapOrArray(Class<?> class_) {
        return class_.isArray() || Collection.class.isAssignableFrom(class_) || Map.class.isAssignableFrom(class_);
    }

    public static boolean isConcrete(Class<?> class_) {
        return (1536 & class_.getModifiers()) == 0;
    }

    public static boolean isConcrete(Member member) {
        return (1536 & member.getModifiers()) == 0;
    }

    @Deprecated
    public static String isLocalType(Class<?> class_) {
        return ClassUtil.isLocalType(class_, false);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String isLocalType(Class<?> class_, boolean bl) {
        try {
            if (class_.getEnclosingMethod() != null) {
                return "local/anonymous";
            }
            if (bl) return null;
            if (class_.getEnclosingClass() == null) return null;
            if (Modifier.isStatic((int)class_.getModifiers())) return null;
            return "non-static member class";
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
        return null;
        catch (SecurityException securityException) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean isProxyType(Class<?> class_) {
        String string;
        return Proxy.isProxyClass(class_) || (string = class_.getName()).startsWith("net.sf.cglib.proxy.") || string.startsWith("org.hibernate.proxy.");
    }

    public static void throwAsIAE(Throwable throwable) {
        ClassUtil.throwAsIAE(throwable, throwable.getMessage());
    }

    public static void throwAsIAE(Throwable throwable, String string) {
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        throw new IllegalArgumentException(string, throwable);
    }

    public static void throwRootCause(Throwable throwable) throws Exception {
        Throwable throwable2 = ClassUtil.getRootCause(throwable);
        if (throwable2 instanceof Exception) {
            throw (Exception)throwable2;
        }
        throw (Error)throwable2;
    }

    public static void unwrapAndThrowAsIAE(Throwable throwable) {
        ClassUtil.throwAsIAE(ClassUtil.getRootCause(throwable));
    }

    public static void unwrapAndThrowAsIAE(Throwable throwable, String string) {
        ClassUtil.throwAsIAE(ClassUtil.getRootCause(throwable), string);
    }

    public static Class<?> wrapperType(Class<?> class_) {
        if (class_ == Integer.TYPE) {
            return Integer.class;
        }
        if (class_ == Long.TYPE) {
            return Long.class;
        }
        if (class_ == Boolean.TYPE) {
            return Boolean.class;
        }
        if (class_ == Double.TYPE) {
            return Double.class;
        }
        if (class_ == Float.TYPE) {
            return Float.class;
        }
        if (class_ == Byte.TYPE) {
            return Byte.class;
        }
        if (class_ == Short.TYPE) {
            return Short.class;
        }
        if (class_ == Character.TYPE) {
            return Character.class;
        }
        throw new IllegalArgumentException("Class " + class_.getName() + " is not a primitive type");
    }
}


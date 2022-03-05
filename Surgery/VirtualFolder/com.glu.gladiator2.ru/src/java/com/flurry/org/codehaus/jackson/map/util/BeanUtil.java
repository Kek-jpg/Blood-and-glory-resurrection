/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Character
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.Package
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.flurry.org.codehaus.jackson.map.util;

import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;

public class BeanUtil {
    /*
     * Enabled aggressive block sorting
     */
    protected static boolean isCglibGetCallbacks(AnnotatedMethod annotatedMethod) {
        Package package_;
        String string;
        Class<?> class_ = annotatedMethod.getRawType();
        return class_ != null && class_.isArray() && (package_ = class_.getComponentType().getPackage()) != null && ((string = package_.getName()).startsWith("net.sf.cglib") || string.startsWith("org.hibernate.repackage.cglib"));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static boolean isGroovyMetaClassGetter(AnnotatedMethod annotatedMethod) {
        Package package_;
        Class<?> class_ = annotatedMethod.getRawType();
        return class_ != null && !class_.isArray() && (package_ = class_.getPackage()) != null && package_.getName().startsWith("groovy.lang");
    }

    protected static boolean isGroovyMetaClassSetter(AnnotatedMethod annotatedMethod) {
        Package package_ = annotatedMethod.getParameterClass(0).getPackage();
        boolean bl = false;
        if (package_ != null) {
            boolean bl2 = package_.getName().startsWith("groovy.lang");
            bl = false;
            if (bl2) {
                bl = true;
            }
        }
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static String manglePropertyName(String string) {
        int n = string.length();
        if (n == 0) {
            return null;
        }
        StringBuilder stringBuilder = null;
        int n2 = 0;
        do {
            char c;
            char c2;
            if (n2 >= n || (c2 = string.charAt(n2)) == (c = Character.toLowerCase((char)c2))) {
                if (stringBuilder == null) return string;
                return stringBuilder.toString();
            }
            if (stringBuilder == null) {
                stringBuilder = new StringBuilder(string);
            }
            stringBuilder.setCharAt(n2, c);
            ++n2;
        } while (true);
    }

    public static String okNameForGetter(AnnotatedMethod annotatedMethod) {
        String string = annotatedMethod.getName();
        String string2 = BeanUtil.okNameForIsGetter(annotatedMethod, string);
        if (string2 == null) {
            string2 = BeanUtil.okNameForRegularGetter(annotatedMethod, string);
        }
        return string2;
    }

    public static String okNameForIsGetter(AnnotatedMethod annotatedMethod, String string) {
        Class<?> class_;
        if (!string.startsWith("is") || (class_ = annotatedMethod.getRawType()) != Boolean.class && class_ != Boolean.TYPE) {
            return null;
        }
        return BeanUtil.manglePropertyName(string.substring(2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String okNameForRegularGetter(AnnotatedMethod annotatedMethod, String string) {
        if (!string.startsWith("get") || ("getCallbacks".equals((Object)string) ? BeanUtil.isCglibGetCallbacks(annotatedMethod) : "getMetaClass".equals((Object)string) && BeanUtil.isGroovyMetaClassGetter(annotatedMethod))) {
            return null;
        }
        return BeanUtil.manglePropertyName(string.substring(3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String okNameForSetter(AnnotatedMethod annotatedMethod) {
        String string;
        String string2 = annotatedMethod.getName();
        if (!string2.startsWith("set") || (string = BeanUtil.manglePropertyName(string2.substring(3))) == null || "metaClass".equals((Object)string) && BeanUtil.isGroovyMetaClassSetter(annotatedMethod)) {
            return null;
        }
        return string;
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.Method
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class MemberKey {
    static final Class<?>[] NO_CLASSES = new Class[0];
    final Class<?>[] _argTypes;
    final String _name;

    public MemberKey(String string, Class<?>[] arrclass) {
        this._name = string;
        if (arrclass == null) {
            arrclass = NO_CLASSES;
        }
        this._argTypes = arrclass;
    }

    public MemberKey(Constructor<?> constructor) {
        super("", constructor.getParameterTypes());
    }

    public MemberKey(Method method) {
        super(method.getName(), method.getParameterTypes());
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (object != this) {
            if (object == null) {
                return false;
            }
            if (object.getClass() != this.getClass()) {
                return false;
            }
            MemberKey memberKey = (MemberKey)object;
            if (!this._name.equals((Object)memberKey._name)) {
                return false;
            }
            Class<?>[] arrclass = memberKey._argTypes;
            int n = this._argTypes.length;
            if (arrclass.length != n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                Class<?> class_ = arrclass[i];
                Class<?> class_2 = this._argTypes[i];
                if (class_ == class_2 || class_.isAssignableFrom(class_2) || class_2.isAssignableFrom(class_)) continue;
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return this._name.hashCode() + this._argTypes.length;
    }

    public String toString() {
        return this._name + "(" + this._argTypes.length + "-args)";
    }
}


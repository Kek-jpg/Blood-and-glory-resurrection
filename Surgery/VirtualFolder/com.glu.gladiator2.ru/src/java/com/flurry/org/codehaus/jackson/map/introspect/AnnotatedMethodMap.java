/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Method
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.MemberKey;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;

public final class AnnotatedMethodMap
implements Iterable<AnnotatedMethod> {
    protected LinkedHashMap<MemberKey, AnnotatedMethod> _methods;

    public void add(AnnotatedMethod annotatedMethod) {
        if (this._methods == null) {
            this._methods = new LinkedHashMap();
        }
        this._methods.put((Object)new MemberKey(annotatedMethod.getAnnotated()), (Object)annotatedMethod);
    }

    public AnnotatedMethod find(String string, Class<?>[] arrclass) {
        if (this._methods == null) {
            return null;
        }
        return (AnnotatedMethod)this._methods.get((Object)new MemberKey(string, arrclass));
    }

    public AnnotatedMethod find(Method method) {
        if (this._methods == null) {
            return null;
        }
        return (AnnotatedMethod)this._methods.get((Object)new MemberKey(method));
    }

    public boolean isEmpty() {
        return this._methods == null || this._methods.size() == 0;
    }

    public Iterator<AnnotatedMethod> iterator() {
        if (this._methods != null) {
            return this._methods.values().iterator();
        }
        return Collections.emptyList().iterator();
    }

    public AnnotatedMethod remove(AnnotatedMethod annotatedMethod) {
        return this.remove(annotatedMethod.getAnnotated());
    }

    public AnnotatedMethod remove(Method method) {
        if (this._methods != null) {
            return (AnnotatedMethod)this._methods.remove((Object)new MemberKey(method));
        }
        return null;
    }

    public int size() {
        if (this._methods == null) {
            return 0;
        }
        return this._methods.size();
    }
}


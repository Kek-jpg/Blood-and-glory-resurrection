/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Enum
 *  java.lang.String
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.deser;

import java.util.HashMap;

@Deprecated
public final class EnumResolver<T extends Enum<T>>
extends com.flurry.org.codehaus.jackson.map.util.EnumResolver<T> {
    private EnumResolver(Class<T> class_, T[] arrT, HashMap<String, T> hashMap) {
        super(class_, arrT, hashMap);
    }
}


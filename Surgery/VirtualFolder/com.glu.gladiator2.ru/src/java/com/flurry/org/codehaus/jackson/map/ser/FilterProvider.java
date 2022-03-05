/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyFilter;

public abstract class FilterProvider {
    public abstract BeanPropertyFilter findFilter(Object var1);
}


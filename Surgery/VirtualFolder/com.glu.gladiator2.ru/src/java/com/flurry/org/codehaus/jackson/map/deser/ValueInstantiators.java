/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;

public interface ValueInstantiators {
    public ValueInstantiator findValueInstantiator(DeserializationConfig var1, BeanDescription var2, ValueInstantiator var3);
}


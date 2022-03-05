/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.deser.impl;

import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;

public class ValueInjector
extends BeanProperty.Std {
    protected final Object _valueId;

    public ValueInjector(String string, JavaType javaType, Annotations annotations, AnnotatedMember annotatedMember, Object object) {
        super(string, javaType, annotations, annotatedMember);
        this._valueId = object;
    }

    public Object findValue(DeserializationContext deserializationContext, Object object) {
        return deserializationContext.findInjectableValue(this._valueId, (BeanProperty)this, object);
    }

    public void inject(DeserializationContext deserializationContext, Object object) throws IOException {
        this._member.setValue(object, this.findValue(deserializationContext, object));
    }
}


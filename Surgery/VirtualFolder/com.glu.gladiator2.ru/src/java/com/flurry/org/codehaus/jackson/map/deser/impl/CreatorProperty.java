/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.annotation.Annotation
 */
package com.flurry.org.codehaus.jackson.map.deser.impl;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedParameter;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.annotation.Annotation;

public class CreatorProperty
extends SettableBeanProperty {
    protected final AnnotatedParameter _annotated;
    protected final Object _injectableValueId;

    protected CreatorProperty(CreatorProperty creatorProperty, JsonDeserializer<Object> jsonDeserializer) {
        super(creatorProperty, jsonDeserializer);
        this._annotated = creatorProperty._annotated;
        this._injectableValueId = creatorProperty._injectableValueId;
    }

    public CreatorProperty(String string, JavaType javaType, TypeDeserializer typeDeserializer, Annotations annotations, AnnotatedParameter annotatedParameter, int n, Object object) {
        super(string, javaType, typeDeserializer, annotations);
        this._annotated = annotatedParameter;
        this._propertyIndex = n;
        this._injectableValueId = object;
    }

    @Override
    public void deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        this.set(object, this.deserialize(jsonParser, deserializationContext));
    }

    public Object findInjectableValue(DeserializationContext deserializationContext, Object object) {
        if (this._injectableValueId == null) {
            throw new IllegalStateException("Property '" + this.getName() + "' (type " + this.getClass().getName() + ") has no injectable value id configured");
        }
        return deserializationContext.findInjectableValue(this._injectableValueId, (BeanProperty)this, object);
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        if (this._annotated == null) {
            return null;
        }
        return this._annotated.getAnnotation(class_);
    }

    @Override
    public Object getInjectableValueId() {
        return this._injectableValueId;
    }

    @Override
    public AnnotatedMember getMember() {
        return this._annotated;
    }

    public void inject(DeserializationContext deserializationContext, Object object) throws IOException {
        this.set(object, this.findInjectableValue(deserializationContext, object));
    }

    @Override
    public void set(Object object, Object object2) throws IOException {
    }

    @Override
    public CreatorProperty withValueDeserializer(JsonDeserializer<Object> jsonDeserializer) {
        return new CreatorProperty((CreatorProperty)this, jsonDeserializer);
    }
}


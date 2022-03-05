/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.annotation.Annotation
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.annotate.JsonSerialize;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedParameter;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.annotation.Annotation;

public class NopAnnotationIntrospector
extends AnnotationIntrospector {
    public static final NopAnnotationIntrospector instance = new NopAnnotationIntrospector();

    public Class<JsonDeserializer<?>> findContentDeserializer(Annotated annotated) {
        return null;
    }

    @Override
    public String findDeserializablePropertyName(AnnotatedField annotatedField) {
        return null;
    }

    @Override
    public Class<?> findDeserializationContentType(Annotated annotated, JavaType javaType, String string) {
        return null;
    }

    @Override
    public Class<?> findDeserializationKeyType(Annotated annotated, JavaType javaType, String string) {
        return null;
    }

    @Override
    public Class<?> findDeserializationType(Annotated annotated, JavaType javaType, String string) {
        return null;
    }

    @Override
    public Object findDeserializer(Annotated annotated) {
        return null;
    }

    @Override
    public String findEnumValue(Enum<?> enum_) {
        return null;
    }

    @Override
    public String findGettablePropertyName(AnnotatedMethod annotatedMethod) {
        return null;
    }

    @Override
    public Boolean findIgnoreUnknownProperties(AnnotatedClass annotatedClass) {
        return null;
    }

    public Class<KeyDeserializer> findKeyDeserializer(Annotated annotated) {
        return null;
    }

    @Override
    public String[] findPropertiesToIgnore(AnnotatedClass annotatedClass) {
        return null;
    }

    @Override
    public String findPropertyNameForParam(AnnotatedParameter annotatedParameter) {
        return null;
    }

    @Override
    public String findRootName(AnnotatedClass annotatedClass) {
        return null;
    }

    @Override
    public String findSerializablePropertyName(AnnotatedField annotatedField) {
        return null;
    }

    @Override
    public String[] findSerializationPropertyOrder(AnnotatedClass annotatedClass) {
        return null;
    }

    @Override
    public Boolean findSerializationSortAlphabetically(AnnotatedClass annotatedClass) {
        return null;
    }

    @Override
    public Class<?> findSerializationType(Annotated annotated) {
        return null;
    }

    @Override
    public JsonSerialize.Typing findSerializationTyping(Annotated annotated) {
        return null;
    }

    @Override
    public Class<?>[] findSerializationViews(Annotated annotated) {
        return null;
    }

    @Override
    public Object findSerializer(Annotated annotated) {
        return null;
    }

    @Override
    public String findSettablePropertyName(AnnotatedMethod annotatedMethod) {
        return null;
    }

    @Override
    public boolean hasAsValueAnnotation(AnnotatedMethod annotatedMethod) {
        return false;
    }

    @Override
    public boolean hasIgnoreMarker(AnnotatedMember annotatedMember) {
        return false;
    }

    @Override
    public boolean isHandled(Annotation annotation) {
        return false;
    }

    @Override
    public boolean isIgnorableConstructor(AnnotatedConstructor annotatedConstructor) {
        return false;
    }

    @Override
    public boolean isIgnorableField(AnnotatedField annotatedField) {
        return false;
    }

    @Override
    public boolean isIgnorableMethod(AnnotatedMethod annotatedMethod) {
        return false;
    }
}


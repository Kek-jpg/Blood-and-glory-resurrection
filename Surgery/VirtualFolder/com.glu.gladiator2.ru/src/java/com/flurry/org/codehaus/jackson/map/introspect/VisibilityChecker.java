/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.reflect.Field
 *  java.lang.reflect.Member
 *  java.lang.reflect.Method
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.annotate.JsonAutoDetect;
import com.flurry.org.codehaus.jackson.annotate.JsonMethod;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public interface VisibilityChecker<T extends VisibilityChecker<T>> {
    public boolean isCreatorVisible(AnnotatedMember var1);

    public boolean isCreatorVisible(Member var1);

    public boolean isFieldVisible(AnnotatedField var1);

    public boolean isFieldVisible(Field var1);

    public boolean isGetterVisible(AnnotatedMethod var1);

    public boolean isGetterVisible(Method var1);

    public boolean isIsGetterVisible(AnnotatedMethod var1);

    public boolean isIsGetterVisible(Method var1);

    public boolean isSetterVisible(AnnotatedMethod var1);

    public boolean isSetterVisible(Method var1);

    public T with(JsonAutoDetect.Visibility var1);

    public T with(JsonAutoDetect var1);

    public T withCreatorVisibility(JsonAutoDetect.Visibility var1);

    public T withFieldVisibility(JsonAutoDetect.Visibility var1);

    public T withGetterVisibility(JsonAutoDetect.Visibility var1);

    public T withIsGetterVisibility(JsonAutoDetect.Visibility var1);

    public T withSetterVisibility(JsonAutoDetect.Visibility var1);

    public T withVisibility(JsonMethod var1, JsonAutoDetect.Visibility var2);
}


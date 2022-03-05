/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.util.Collection
 */
package com.flurry.org.codehaus.jackson.map.jsontype;

import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.jsontype.NamedType;
import java.util.Collection;

public abstract class SubtypeResolver {
    public abstract Collection<NamedType> collectAndResolveSubtypes(AnnotatedClass var1, MapperConfig<?> var2, AnnotationIntrospector var3);

    public abstract Collection<NamedType> collectAndResolveSubtypes(AnnotatedMember var1, MapperConfig<?> var2, AnnotationIntrospector var3);

    public /* varargs */ abstract void registerSubtypes(NamedType ... var1);

    public /* varargs */ abstract void registerSubtypes(Class<?> ... var1);
}


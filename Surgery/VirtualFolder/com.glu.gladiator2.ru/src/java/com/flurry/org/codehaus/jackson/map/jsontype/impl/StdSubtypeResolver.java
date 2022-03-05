/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.LinkedHashSet
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.map.jsontype.impl;

import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.jsontype.NamedType;
import com.flurry.org.codehaus.jackson.map.jsontype.SubtypeResolver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class StdSubtypeResolver
extends SubtypeResolver {
    protected LinkedHashSet<NamedType> _registeredSubtypes;

    /*
     * Enabled aggressive block sorting
     */
    protected void _collectAndResolve(AnnotatedClass annotatedClass, NamedType namedType, MapperConfig<?> mapperConfig, AnnotationIntrospector annotationIntrospector, HashMap<NamedType, NamedType> hashMap) {
        String string;
        if (!namedType.hasName() && (string = annotationIntrospector.findTypeName(annotatedClass)) != null) {
            namedType = new NamedType(namedType.getType(), string);
        }
        if (hashMap.containsKey((Object)namedType)) {
            if (!namedType.hasName() || ((NamedType)hashMap.get((Object)namedType)).hasName()) return;
            {
                hashMap.put((Object)namedType, (Object)namedType);
            }
            return;
        } else {
            hashMap.put((Object)namedType, (Object)namedType);
            List<NamedType> list = annotationIntrospector.findSubtypes(annotatedClass);
            if (list == null || list.isEmpty()) return;
            {
                for (NamedType namedType2 : list) {
                    AnnotatedClass annotatedClass2 = AnnotatedClass.constructWithoutSuperTypes(namedType2.getType(), annotationIntrospector, mapperConfig);
                    if (!namedType2.hasName()) {
                        namedType2 = new NamedType(namedType2.getType(), annotationIntrospector.findTypeName(annotatedClass2));
                    }
                    this._collectAndResolve(annotatedClass2, namedType2, mapperConfig, annotationIntrospector, hashMap);
                }
            }
        }
    }

    @Override
    public Collection<NamedType> collectAndResolveSubtypes(AnnotatedClass annotatedClass, MapperConfig<?> mapperConfig, AnnotationIntrospector annotationIntrospector) {
        HashMap hashMap = new HashMap();
        if (this._registeredSubtypes != null) {
            Class<?> class_ = annotatedClass.getRawType();
            for (NamedType namedType : this._registeredSubtypes) {
                if (!class_.isAssignableFrom(namedType.getType())) continue;
                this._collectAndResolve(AnnotatedClass.constructWithoutSuperTypes(namedType.getType(), annotationIntrospector, mapperConfig), namedType, mapperConfig, annotationIntrospector, (HashMap<NamedType, NamedType>)hashMap);
            }
        }
        this._collectAndResolve(annotatedClass, new NamedType(annotatedClass.getRawType(), null), mapperConfig, annotationIntrospector, (HashMap<NamedType, NamedType>)hashMap);
        return new ArrayList(hashMap.values());
    }

    @Override
    public Collection<NamedType> collectAndResolveSubtypes(AnnotatedMember annotatedMember, MapperConfig<?> mapperConfig, AnnotationIntrospector annotationIntrospector) {
        List<NamedType> list;
        HashMap hashMap = new HashMap();
        if (this._registeredSubtypes != null) {
            Class<?> class_ = annotatedMember.getRawType();
            for (NamedType namedType : this._registeredSubtypes) {
                if (!class_.isAssignableFrom(namedType.getType())) continue;
                this._collectAndResolve(AnnotatedClass.constructWithoutSuperTypes(namedType.getType(), annotationIntrospector, mapperConfig), namedType, mapperConfig, annotationIntrospector, (HashMap<NamedType, NamedType>)hashMap);
            }
        }
        if ((list = annotationIntrospector.findSubtypes(annotatedMember)) != null) {
            for (NamedType namedType : list) {
                this._collectAndResolve(AnnotatedClass.constructWithoutSuperTypes(namedType.getType(), annotationIntrospector, mapperConfig), namedType, mapperConfig, annotationIntrospector, (HashMap<NamedType, NamedType>)hashMap);
            }
        }
        NamedType namedType = new NamedType(annotatedMember.getRawType(), null);
        this._collectAndResolve(AnnotatedClass.constructWithoutSuperTypes(annotatedMember.getRawType(), annotationIntrospector, mapperConfig), namedType, mapperConfig, annotationIntrospector, (HashMap<NamedType, NamedType>)hashMap);
        return new ArrayList(hashMap.values());
    }

    @Override
    public /* varargs */ void registerSubtypes(NamedType ... arrnamedType) {
        if (this._registeredSubtypes == null) {
            this._registeredSubtypes = new LinkedHashSet();
        }
        for (NamedType namedType : arrnamedType) {
            this._registeredSubtypes.add((Object)namedType);
        }
    }

    @Override
    public /* varargs */ void registerSubtypes(Class<?> ... arrclass) {
        NamedType[] arrnamedType = new NamedType[arrclass.length];
        int n = arrclass.length;
        for (int i = 0; i < n; ++i) {
            arrnamedType[i] = new NamedType(arrclass[i]);
        }
        this.registerSubtypes(arrnamedType);
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Collection
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.jsontype.impl;

import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.jsontype.NamedType;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.TypeIdResolverBase;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.Collection;
import java.util.HashMap;

public class TypeNameIdResolver
extends TypeIdResolverBase {
    protected final MapperConfig<?> _config;
    protected final HashMap<String, JavaType> _idToType;
    protected final HashMap<String, String> _typeToId;

    protected TypeNameIdResolver(MapperConfig<?> mapperConfig, JavaType javaType, HashMap<String, String> hashMap, HashMap<String, JavaType> hashMap2) {
        super(javaType, mapperConfig.getTypeFactory());
        this._config = mapperConfig;
        this._typeToId = hashMap;
        this._idToType = hashMap2;
    }

    protected static String _defaultTypeId(Class<?> class_) {
        String string = class_.getName();
        int n = string.lastIndexOf(46);
        if (n < 0) {
            return string;
        }
        return string.substring(n + 1);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static TypeNameIdResolver construct(MapperConfig<?> mapperConfig, JavaType javaType, Collection<NamedType> collection, boolean bl, boolean bl2) {
        if (bl == bl2) {
            throw new IllegalArgumentException();
        }
        HashMap hashMap = null;
        if (bl) {
            hashMap = new HashMap();
        }
        HashMap hashMap2 = null;
        if (bl2) {
            hashMap2 = new HashMap();
        }
        if (collection != null) {
            for (NamedType namedType : collection) {
                JavaType javaType2;
                Class<?> class_ = namedType.getType();
                String string = namedType.hasName() ? namedType.getName() : TypeNameIdResolver._defaultTypeId(class_);
                if (bl) {
                    hashMap.put((Object)class_.getName(), (Object)string);
                }
                if (!bl2 || (javaType2 = (JavaType)hashMap2.get((Object)string)) != null && class_.isAssignableFrom(javaType2.getRawClass())) continue;
                hashMap2.put((Object)string, (Object)mapperConfig.constructType(class_));
            }
        }
        return new TypeNameIdResolver(mapperConfig, javaType, (HashMap<String, String>)hashMap, (HashMap<String, JavaType>)hashMap2);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.NAME;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String idFromValue(Object object) {
        HashMap<String, String> hashMap;
        Class class_ = object.getClass();
        String string = class_.getName();
        HashMap<String, String> hashMap2 = hashMap = this._typeToId;
        synchronized (hashMap2) {
            String string2 = (String)this._typeToId.get((Object)string);
            if (string2 == null) {
                if (this._config.isAnnotationProcessingEnabled()) {
                    BasicBeanDescription basicBeanDescription = (BasicBeanDescription)this._config.introspectClassAnnotations(class_);
                    string2 = this._config.getAnnotationIntrospector().findTypeName(basicBeanDescription.getClassInfo());
                }
                if (string2 == null) {
                    string2 = TypeNameIdResolver._defaultTypeId(class_);
                }
                this._typeToId.put((Object)string, (Object)string2);
            }
            return string2;
        }
    }

    @Override
    public String idFromValueAndType(Object object, Class<?> class_) {
        return this.idFromValue(object);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[').append(this.getClass().getName());
        stringBuilder.append("; id-to-type=").append(this._idToType);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public JavaType typeFromId(String string) throws IllegalArgumentException {
        return (JavaType)this._idToType.get((Object)string);
    }
}


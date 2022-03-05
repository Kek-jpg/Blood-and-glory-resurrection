/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.type.HierarchicType
 *  com.flurry.org.codehaus.jackson.map.type.SimpleType
 *  com.flurry.org.codehaus.jackson.map.type.TypeParser
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.GenericArrayType
 *  java.lang.reflect.ParameterizedType
 *  java.lang.reflect.Type
 *  java.lang.reflect.TypeVariable
 *  java.lang.reflect.WildcardType
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.type;

import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.map.type.CollectionLikeType;
import com.flurry.org.codehaus.jackson.map.type.CollectionType;
import com.flurry.org.codehaus.jackson.map.type.HierarchicType;
import com.flurry.org.codehaus.jackson.map.type.MapLikeType;
import com.flurry.org.codehaus.jackson.map.type.MapType;
import com.flurry.org.codehaus.jackson.map.type.SimpleType;
import com.flurry.org.codehaus.jackson.map.type.TypeBindings;
import com.flurry.org.codehaus.jackson.map.type.TypeModifier;
import com.flurry.org.codehaus.jackson.map.type.TypeParser;
import com.flurry.org.codehaus.jackson.map.util.ArrayBuilders;
import com.flurry.org.codehaus.jackson.type.JavaType;
import com.flurry.org.codehaus.jackson.type.TypeReference;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TypeFactory {
    private static final JavaType[] NO_TYPES;
    @Deprecated
    public static final TypeFactory instance;
    protected HierarchicType _cachedArrayListType;
    protected HierarchicType _cachedHashMapType;
    protected final TypeModifier[] _modifiers;
    protected final TypeParser _parser;

    static {
        instance = new TypeFactory();
        NO_TYPES = new JavaType[0];
    }

    private TypeFactory() {
        this._parser = new TypeParser(this);
        this._modifiers = null;
    }

    protected TypeFactory(TypeParser typeParser, TypeModifier[] arrtypeModifier) {
        this._parser = typeParser;
        this._modifiers = arrtypeModifier;
    }

    private JavaType _collectionType(Class<?> class_) {
        JavaType[] arrjavaType = this.findTypeParameters(class_, Collection.class);
        if (arrjavaType == null) {
            return CollectionType.construct(class_, this._unknownType());
        }
        if (arrjavaType.length != 1) {
            throw new IllegalArgumentException("Strange Collection type " + class_.getName() + ": can not determine type parameters");
        }
        return CollectionType.construct(class_, arrjavaType[0]);
    }

    private JavaType _mapType(Class<?> class_) {
        JavaType[] arrjavaType = this.findTypeParameters(class_, Map.class);
        if (arrjavaType == null) {
            return MapType.construct(class_, this._unknownType(), this._unknownType());
        }
        if (arrjavaType.length != 2) {
            throw new IllegalArgumentException("Strange Map type " + class_.getName() + ": can not determine type parameters");
        }
        return MapType.construct(class_, arrjavaType[0], arrjavaType[1]);
    }

    @Deprecated
    public static JavaType arrayType(JavaType javaType) {
        return instance.constructArrayType(javaType);
    }

    @Deprecated
    public static JavaType arrayType(Class<?> class_) {
        return instance.constructArrayType(instance.constructType((Type)class_));
    }

    @Deprecated
    public static JavaType collectionType(Class<? extends Collection> class_, JavaType javaType) {
        return instance.constructCollectionType(class_, javaType);
    }

    @Deprecated
    public static JavaType collectionType(Class<? extends Collection> class_, Class<?> class_2) {
        return instance.constructCollectionType(class_, instance.constructType((Type)class_2));
    }

    public static TypeFactory defaultInstance() {
        return instance;
    }

    @Deprecated
    public static JavaType fastSimpleType(Class<?> class_) {
        return instance.uncheckedSimpleType(class_);
    }

    @Deprecated
    public static JavaType[] findParameterTypes(JavaType javaType, Class<?> class_) {
        return instance.findTypeParameters(javaType, class_);
    }

    @Deprecated
    public static JavaType[] findParameterTypes(Class<?> class_, Class<?> class_2) {
        return instance.findTypeParameters(class_, class_2);
    }

    @Deprecated
    public static JavaType[] findParameterTypes(Class<?> class_, Class<?> class_2, TypeBindings typeBindings) {
        return instance.findTypeParameters(class_, class_2, typeBindings);
    }

    public static JavaType fromCanonical(String string) throws IllegalArgumentException {
        return instance.constructFromCanonical(string);
    }

    @Deprecated
    public static JavaType fromClass(Class<?> class_) {
        return instance._fromClass(class_, null);
    }

    @Deprecated
    public static JavaType fromType(Type type) {
        return instance._constructType(type, null);
    }

    @Deprecated
    public static JavaType fromTypeReference(TypeReference<?> typeReference) {
        return TypeFactory.type(typeReference.getType());
    }

    @Deprecated
    public static JavaType mapType(Class<? extends Map> class_, JavaType javaType, JavaType javaType2) {
        return instance.constructMapType(class_, javaType, javaType2);
    }

    @Deprecated
    public static JavaType mapType(Class<? extends Map> class_, Class<?> class_2, Class<?> class_3) {
        return instance.constructMapType(class_, TypeFactory.type(class_2), instance.constructType((Type)class_3));
    }

    @Deprecated
    public static /* varargs */ JavaType parametricType(Class<?> class_, JavaType ... arrjavaType) {
        return instance.constructParametricType(class_, arrjavaType);
    }

    @Deprecated
    public static /* varargs */ JavaType parametricType(Class<?> class_, Class<?> ... arrclass) {
        return instance.constructParametricType(class_, arrclass);
    }

    public static Class<?> rawClass(Type type) {
        if (type instanceof Class) {
            return (Class)type;
        }
        return TypeFactory.defaultInstance().constructType(type).getRawClass();
    }

    @Deprecated
    public static JavaType specialize(JavaType javaType, Class<?> class_) {
        return instance.constructSpecializedType(javaType, class_);
    }

    @Deprecated
    public static JavaType type(TypeReference<?> typeReference) {
        return instance.constructType(typeReference.getType());
    }

    @Deprecated
    public static JavaType type(Type type) {
        return instance._constructType(type, null);
    }

    @Deprecated
    public static JavaType type(Type type, TypeBindings typeBindings) {
        return instance._constructType(type, typeBindings);
    }

    @Deprecated
    public static JavaType type(Type type, JavaType javaType) {
        return instance.constructType(type, javaType);
    }

    @Deprecated
    public static JavaType type(Type type, Class<?> class_) {
        return instance.constructType(type, class_);
    }

    public static JavaType unknownType() {
        return TypeFactory.defaultInstance()._unknownType();
    }

    protected HierarchicType _arrayListSuperInterfaceChain(HierarchicType hierarchicType) {
        void var6_2 = this;
        synchronized (var6_2) {
            if (this._cachedArrayListType == null) {
                HierarchicType hierarchicType2 = hierarchicType.deepCloneWithoutSubtype();
                this._doFindSuperInterfaceChain(hierarchicType2, List.class);
                this._cachedArrayListType = hierarchicType2.getSuperType();
            }
            HierarchicType hierarchicType3 = this._cachedArrayListType.deepCloneWithoutSubtype();
            hierarchicType.setSuperType(hierarchicType3);
            hierarchicType3.setSubType(hierarchicType);
            return hierarchicType;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public JavaType _constructType(Type type, TypeBindings typeBindings) {
        JavaType javaType;
        if (type instanceof Class) {
            Class class_ = (Class)type;
            if (typeBindings == null) {
                typeBindings = new TypeBindings((TypeFactory)this, class_);
            }
            javaType = this._fromClass(class_, typeBindings);
        } else if (type instanceof ParameterizedType) {
            javaType = this._fromParamType((ParameterizedType)type, typeBindings);
        } else if (type instanceof GenericArrayType) {
            javaType = this._fromArrayType((GenericArrayType)type, typeBindings);
        } else if (type instanceof TypeVariable) {
            javaType = this._fromVariable((TypeVariable)type, typeBindings);
        } else {
            if (!(type instanceof WildcardType)) {
                throw new IllegalArgumentException("Unrecognized Type: " + type.toString());
            }
            javaType = this._fromWildcard((WildcardType)type, typeBindings);
        }
        if (this._modifiers != null && !javaType.isContainerType()) {
            TypeModifier[] arrtypeModifier = this._modifiers;
            int n = arrtypeModifier.length;
            for (int i = 0; i < n; ++i) {
                javaType = arrtypeModifier[i].modifyType(javaType, type, typeBindings, (TypeFactory)this);
            }
        }
        return javaType;
    }

    protected HierarchicType _doFindSuperInterfaceChain(HierarchicType hierarchicType, Class<?> class_) {
        Type type;
        HierarchicType hierarchicType2;
        Class class_2 = hierarchicType.getRawClass();
        Type[] arrtype = class_2.getGenericInterfaces();
        if (arrtype != null) {
            int n = arrtype.length;
            for (int i = 0; i < n; ++i) {
                HierarchicType hierarchicType3 = this._findSuperInterfaceChain(arrtype[i], class_);
                if (hierarchicType3 == null) continue;
                hierarchicType3.setSubType(hierarchicType);
                hierarchicType.setSuperType(hierarchicType3);
                return hierarchicType;
            }
        }
        if ((type = class_2.getGenericSuperclass()) != null && (hierarchicType2 = this._findSuperInterfaceChain(type, class_)) != null) {
            hierarchicType2.setSubType(hierarchicType);
            hierarchicType.setSuperType(hierarchicType2);
            return hierarchicType;
        }
        return null;
    }

    protected HierarchicType _findSuperClassChain(Type type, Class<?> class_) {
        HierarchicType hierarchicType;
        HierarchicType hierarchicType2 = new HierarchicType(type);
        Class class_2 = hierarchicType2.getRawClass();
        if (class_2 == class_) {
            return hierarchicType2;
        }
        Type type2 = class_2.getGenericSuperclass();
        if (type2 != null && (hierarchicType = this._findSuperClassChain(type2, class_)) != null) {
            hierarchicType.setSubType(hierarchicType2);
            hierarchicType2.setSuperType(hierarchicType);
            return hierarchicType2;
        }
        return null;
    }

    protected HierarchicType _findSuperInterfaceChain(Type type, Class<?> class_) {
        HierarchicType hierarchicType = new HierarchicType(type);
        Class class_2 = hierarchicType.getRawClass();
        if (class_2 == class_) {
            return new HierarchicType(type);
        }
        if (class_2 == HashMap.class && class_ == Map.class) {
            return this._hashMapSuperInterfaceChain(hierarchicType);
        }
        if (class_2 == ArrayList.class && class_ == List.class) {
            return this._arrayListSuperInterfaceChain(hierarchicType);
        }
        return this._doFindSuperInterfaceChain(hierarchicType, class_);
    }

    protected HierarchicType _findSuperTypeChain(Class<?> class_, Class<?> class_2) {
        if (class_2.isInterface()) {
            return this._findSuperInterfaceChain((Type)class_, class_2);
        }
        return this._findSuperClassChain((Type)class_, class_2);
    }

    protected JavaType _fromArrayType(GenericArrayType genericArrayType, TypeBindings typeBindings) {
        return ArrayType.construct(this._constructType(genericArrayType.getGenericComponentType(), typeBindings), null, null);
    }

    protected JavaType _fromClass(Class<?> class_, TypeBindings typeBindings) {
        if (class_.isArray()) {
            return ArrayType.construct(this._constructType((Type)class_.getComponentType(), null), null, null);
        }
        if (class_.isEnum()) {
            return new SimpleType(class_);
        }
        if (Map.class.isAssignableFrom(class_)) {
            return TypeFactory.super._mapType(class_);
        }
        if (Collection.class.isAssignableFrom(class_)) {
            return TypeFactory.super._collectionType(class_);
        }
        return new SimpleType(class_);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JavaType _fromParamType(ParameterizedType parameterizedType, TypeBindings typeBindings) {
        JavaType[] arrjavaType;
        Class class_ = (Class)parameterizedType.getRawType();
        Type[] arrtype = parameterizedType.getActualTypeArguments();
        int n = arrtype == null ? 0 : arrtype.length;
        if (n == 0) {
            arrjavaType = NO_TYPES;
        } else {
            arrjavaType = new JavaType[n];
            for (int i = 0; i < n; ++i) {
                arrjavaType[i] = this._constructType(arrtype[i], typeBindings);
            }
        }
        if (Map.class.isAssignableFrom(class_)) {
            JavaType[] arrjavaType2 = this.findTypeParameters(this.constructSimpleType(class_, arrjavaType), Map.class);
            if (arrjavaType2.length != 2) {
                throw new IllegalArgumentException("Could not find 2 type parameters for Map class " + class_.getName() + " (found " + arrjavaType2.length + ")");
            }
            return MapType.construct(class_, arrjavaType2[0], arrjavaType2[1]);
        }
        if (Collection.class.isAssignableFrom(class_)) {
            JavaType[] arrjavaType3 = this.findTypeParameters(this.constructSimpleType(class_, arrjavaType), Collection.class);
            if (arrjavaType3.length != 1) {
                throw new IllegalArgumentException("Could not find 1 type parameter for Collection class " + class_.getName() + " (found " + arrjavaType3.length + ")");
            }
            return CollectionType.construct(class_, arrjavaType3[0]);
        }
        if (n == 0) {
            return new SimpleType(class_);
        }
        return this.constructSimpleType(class_, arrjavaType);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JavaType _fromParameterizedClass(Class<?> class_, List<JavaType> list) {
        if (class_.isArray()) {
            return ArrayType.construct(this._constructType((Type)class_.getComponentType(), null), null, null);
        }
        if (class_.isEnum()) {
            return new SimpleType(class_);
        }
        if (Map.class.isAssignableFrom(class_)) {
            JavaType javaType;
            if (list.size() <= 0) return TypeFactory.super._mapType(class_);
            JavaType javaType2 = (JavaType)list.get(0);
            if (list.size() >= 2) {
                javaType = (JavaType)list.get(1);
                do {
                    return MapType.construct(class_, javaType2, javaType);
                    break;
                } while (true);
            }
            javaType = this._unknownType();
            return MapType.construct(class_, javaType2, javaType);
        }
        if (Collection.class.isAssignableFrom(class_)) {
            if (list.size() < 1) return TypeFactory.super._collectionType(class_);
            return CollectionType.construct(class_, (JavaType)list.get(0));
        }
        if (list.size() != 0) return this.constructSimpleType(class_, (JavaType[])list.toArray((Object[])new JavaType[list.size()]));
        return new SimpleType(class_);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JavaType _fromVariable(TypeVariable<?> typeVariable, TypeBindings typeBindings) {
        if (typeBindings == null) {
            return this._unknownType();
        }
        String string = typeVariable.getName();
        JavaType javaType = typeBindings.findType(string);
        if (javaType != null) return javaType;
        Type[] arrtype = typeVariable.getBounds();
        typeBindings._addPlaceholder(string);
        return this._constructType(arrtype[0], typeBindings);
    }

    protected JavaType _fromWildcard(WildcardType wildcardType, TypeBindings typeBindings) {
        return this._constructType(wildcardType.getUpperBounds()[0], typeBindings);
    }

    protected HierarchicType _hashMapSuperInterfaceChain(HierarchicType hierarchicType) {
        void var6_2 = this;
        synchronized (var6_2) {
            if (this._cachedHashMapType == null) {
                HierarchicType hierarchicType2 = hierarchicType.deepCloneWithoutSubtype();
                this._doFindSuperInterfaceChain(hierarchicType2, Map.class);
                this._cachedHashMapType = hierarchicType2.getSuperType();
            }
            HierarchicType hierarchicType3 = this._cachedHashMapType.deepCloneWithoutSubtype();
            hierarchicType.setSuperType(hierarchicType3);
            hierarchicType3.setSubType(hierarchicType);
            return hierarchicType;
        }
    }

    protected JavaType _resolveVariableViaSubTypes(HierarchicType hierarchicType, String string, TypeBindings typeBindings) {
        if (hierarchicType != null && hierarchicType.isGeneric()) {
            TypeVariable[] arrtypeVariable = hierarchicType.getRawClass().getTypeParameters();
            int n = arrtypeVariable.length;
            for (int i = 0; i < n; ++i) {
                if (!string.equals((Object)arrtypeVariable[i].getName())) continue;
                Type type = hierarchicType.asGeneric().getActualTypeArguments()[i];
                if (type instanceof TypeVariable) {
                    return this._resolveVariableViaSubTypes(hierarchicType.getSubType(), ((TypeVariable)type).getName(), typeBindings);
                }
                return this._constructType(type, typeBindings);
            }
        }
        return this._unknownType();
    }

    protected JavaType _unknownType() {
        return new SimpleType(Object.class);
    }

    public ArrayType constructArrayType(JavaType javaType) {
        return ArrayType.construct(javaType, null, null);
    }

    public ArrayType constructArrayType(Class<?> class_) {
        return ArrayType.construct(this._constructType((Type)class_, null), null, null);
    }

    public CollectionLikeType constructCollectionLikeType(Class<?> class_, JavaType javaType) {
        return CollectionLikeType.construct(class_, javaType);
    }

    public CollectionLikeType constructCollectionLikeType(Class<?> class_, Class<?> class_2) {
        return CollectionLikeType.construct(class_, this.constructType((Type)class_2));
    }

    public CollectionType constructCollectionType(Class<? extends Collection> class_, JavaType javaType) {
        return CollectionType.construct(class_, javaType);
    }

    public CollectionType constructCollectionType(Class<? extends Collection> class_, Class<?> class_2) {
        return CollectionType.construct(class_, this.constructType((Type)class_2));
    }

    public JavaType constructFromCanonical(String string) throws IllegalArgumentException {
        return this._parser.parse(string);
    }

    public MapLikeType constructMapLikeType(Class<?> class_, JavaType javaType, JavaType javaType2) {
        return MapLikeType.construct(class_, javaType, javaType2);
    }

    public MapLikeType constructMapLikeType(Class<?> class_, Class<?> class_2, Class<?> class_3) {
        return MapType.construct(class_, this.constructType((Type)class_2), this.constructType((Type)class_3));
    }

    public MapType constructMapType(Class<? extends Map> class_, JavaType javaType, JavaType javaType2) {
        return MapType.construct(class_, javaType, javaType2);
    }

    public MapType constructMapType(Class<? extends Map> class_, Class<?> class_2, Class<?> class_3) {
        return MapType.construct(class_, this.constructType((Type)class_2), this.constructType((Type)class_3));
    }

    public /* varargs */ JavaType constructParametricType(Class<?> class_, JavaType ... arrjavaType) {
        if (class_.isArray()) {
            if (arrjavaType.length != 1) {
                throw new IllegalArgumentException("Need exactly 1 parameter type for arrays (" + class_.getName() + ")");
            }
            return this.constructArrayType(arrjavaType[0]);
        }
        if (Map.class.isAssignableFrom(class_)) {
            if (arrjavaType.length != 2) {
                throw new IllegalArgumentException("Need exactly 2 parameter types for Map types (" + class_.getName() + ")");
            }
            return this.constructMapType(class_, arrjavaType[0], arrjavaType[1]);
        }
        if (Collection.class.isAssignableFrom(class_)) {
            if (arrjavaType.length != 1) {
                throw new IllegalArgumentException("Need exactly 1 parameter type for Collection types (" + class_.getName() + ")");
            }
            return this.constructCollectionType(class_, arrjavaType[0]);
        }
        return this.constructSimpleType(class_, arrjavaType);
    }

    public /* varargs */ JavaType constructParametricType(Class<?> class_, Class<?> ... arrclass) {
        int n = arrclass.length;
        JavaType[] arrjavaType = new JavaType[n];
        for (int i = 0; i < n; ++i) {
            arrjavaType[i] = this._fromClass(arrclass[i], null);
        }
        return this.constructParametricType(class_, arrjavaType);
    }

    public CollectionLikeType constructRawCollectionLikeType(Class<?> class_) {
        return CollectionLikeType.construct(class_, TypeFactory.unknownType());
    }

    public CollectionType constructRawCollectionType(Class<? extends Collection> class_) {
        return CollectionType.construct(class_, TypeFactory.unknownType());
    }

    public MapLikeType constructRawMapLikeType(Class<?> class_) {
        return MapLikeType.construct(class_, TypeFactory.unknownType(), TypeFactory.unknownType());
    }

    public MapType constructRawMapType(Class<? extends Map> class_) {
        return MapType.construct(class_, TypeFactory.unknownType(), TypeFactory.unknownType());
    }

    public JavaType constructSimpleType(Class<?> class_, JavaType[] arrjavaType) {
        TypeVariable[] arrtypeVariable = class_.getTypeParameters();
        if (arrtypeVariable.length != arrjavaType.length) {
            throw new IllegalArgumentException("Parameter type mismatch for " + class_.getName() + ": expected " + arrtypeVariable.length + " parameters, was given " + arrjavaType.length);
        }
        String[] arrstring = new String[arrtypeVariable.length];
        int n = arrtypeVariable.length;
        for (int i = 0; i < n; ++i) {
            arrstring[i] = arrtypeVariable[i].getName();
        }
        return new SimpleType(class_, arrstring, arrjavaType, null, null);
    }

    public JavaType constructSpecializedType(JavaType javaType, Class<?> class_) {
        if (javaType instanceof SimpleType && (class_.isArray() || Map.class.isAssignableFrom(class_) || Collection.class.isAssignableFrom(class_))) {
            Object t;
            if (!javaType.getRawClass().isAssignableFrom(class_)) {
                throw new IllegalArgumentException("Class " + class_.getClass().getName() + " not subtype of " + javaType);
            }
            JavaType javaType2 = this._fromClass(class_, new TypeBindings((TypeFactory)this, javaType.getRawClass()));
            Object t2 = javaType.getValueHandler();
            if (t2 != null) {
                javaType2 = javaType2.withValueHandler(t2);
            }
            if ((t = javaType.getTypeHandler()) != null) {
                javaType2 = javaType2.withTypeHandler(t);
            }
            return javaType2;
        }
        return javaType.narrowBy(class_);
    }

    public JavaType constructType(TypeReference<?> typeReference) {
        return this._constructType(typeReference.getType(), null);
    }

    public JavaType constructType(Type type) {
        return this._constructType(type, null);
    }

    public JavaType constructType(Type type, TypeBindings typeBindings) {
        return this._constructType(type, typeBindings);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JavaType constructType(Type type, JavaType javaType) {
        TypeBindings typeBindings;
        if (javaType == null) {
            typeBindings = null;
            do {
                return this._constructType(type, typeBindings);
                break;
            } while (true);
        }
        typeBindings = new TypeBindings((TypeFactory)this, javaType);
        return this._constructType(type, typeBindings);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JavaType constructType(Type type, Class<?> class_) {
        TypeBindings typeBindings;
        if (class_ == null) {
            typeBindings = null;
            do {
                return this._constructType(type, typeBindings);
                break;
            } while (true);
        }
        typeBindings = new TypeBindings((TypeFactory)this, class_);
        return this._constructType(type, typeBindings);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JavaType[] findTypeParameters(JavaType javaType, Class<?> class_) {
        Class<?> class_2 = javaType.getRawClass();
        if (class_2 != class_) return this.findTypeParameters(class_2, class_, new TypeBindings((TypeFactory)this, javaType));
        int n = javaType.containedTypeCount();
        if (n == 0) {
            return null;
        }
        JavaType[] arrjavaType = new JavaType[n];
        int n2 = 0;
        while (n2 < n) {
            arrjavaType[n2] = javaType.containedType(n2);
            ++n2;
        }
        return arrjavaType;
    }

    public JavaType[] findTypeParameters(Class<?> class_, Class<?> class_2) {
        return this.findTypeParameters(class_, class_2, new TypeBindings((TypeFactory)this, class_));
    }

    public JavaType[] findTypeParameters(Class<?> class_, Class<?> class_2, TypeBindings typeBindings) {
        HierarchicType hierarchicType = this._findSuperTypeChain(class_, class_2);
        if (hierarchicType == null) {
            throw new IllegalArgumentException("Class " + class_.getName() + " is not a subtype of " + class_2.getName());
        }
        HierarchicType hierarchicType2 = hierarchicType;
        while (hierarchicType2.getSuperType() != null) {
            hierarchicType2 = hierarchicType2.getSuperType();
            Class class_3 = hierarchicType2.getRawClass();
            TypeBindings typeBindings2 = new TypeBindings((TypeFactory)this, class_3);
            if (hierarchicType2.isGeneric()) {
                Type[] arrtype = hierarchicType2.asGeneric().getActualTypeArguments();
                TypeVariable[] arrtypeVariable = class_3.getTypeParameters();
                int n = arrtype.length;
                for (int i = 0; i < n; ++i) {
                    typeBindings2.addBinding(arrtypeVariable[i].getName(), instance._constructType(arrtype[i], typeBindings));
                }
            }
            typeBindings = typeBindings2;
        }
        if (!hierarchicType2.isGeneric()) {
            return null;
        }
        return typeBindings.typesAsArray();
    }

    public JavaType uncheckedSimpleType(Class<?> class_) {
        return new SimpleType(class_);
    }

    public TypeFactory withModifier(TypeModifier typeModifier) {
        if (this._modifiers == null) {
            return new TypeFactory(this._parser, new TypeModifier[]{typeModifier});
        }
        return new TypeFactory(this._parser, ArrayBuilders.insertInListNoDup(this._modifiers, typeModifier));
    }
}


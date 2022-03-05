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
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.annotate.JsonSerialize;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedParameter;
import com.flurry.org.codehaus.jackson.map.introspect.NopAnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.introspect.VisibilityChecker;
import com.flurry.org.codehaus.jackson.map.jsontype.NamedType;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AnnotationIntrospector {
    public static AnnotationIntrospector nopInstance() {
        return NopAnnotationIntrospector.instance;
    }

    public static AnnotationIntrospector pair(AnnotationIntrospector annotationIntrospector, AnnotationIntrospector annotationIntrospector2) {
        return new Pair(annotationIntrospector, annotationIntrospector2);
    }

    public Collection<AnnotationIntrospector> allIntrospectors() {
        return Collections.singletonList((Object)this);
    }

    public Collection<AnnotationIntrospector> allIntrospectors(Collection<AnnotationIntrospector> collection) {
        collection.add((Object)this);
        return collection;
    }

    public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass annotatedClass, VisibilityChecker<?> visibilityChecker) {
        return visibilityChecker;
    }

    public Boolean findCachability(AnnotatedClass annotatedClass) {
        return null;
    }

    public abstract Class<? extends JsonDeserializer<?>> findContentDeserializer(Annotated var1);

    public Class<? extends JsonSerializer<?>> findContentSerializer(Annotated annotated) {
        return null;
    }

    public abstract String findDeserializablePropertyName(AnnotatedField var1);

    public abstract Class<?> findDeserializationContentType(Annotated var1, JavaType var2, String var3);

    public abstract Class<?> findDeserializationKeyType(Annotated var1, JavaType var2, String var3);

    public abstract Class<?> findDeserializationType(Annotated var1, JavaType var2, String var3);

    public abstract Object findDeserializer(Annotated var1);

    public abstract String findEnumValue(Enum<?> var1);

    public Object findFilterId(AnnotatedClass annotatedClass) {
        return null;
    }

    public abstract String findGettablePropertyName(AnnotatedMethod var1);

    public abstract Boolean findIgnoreUnknownProperties(AnnotatedClass var1);

    public Object findInjectableValueId(AnnotatedMember annotatedMember) {
        return null;
    }

    public abstract Class<? extends KeyDeserializer> findKeyDeserializer(Annotated var1);

    public Class<? extends JsonSerializer<?>> findKeySerializer(Annotated annotated) {
        return null;
    }

    public abstract String[] findPropertiesToIgnore(AnnotatedClass var1);

    public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        return null;
    }

    public abstract String findPropertyNameForParam(AnnotatedParameter var1);

    public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        return null;
    }

    public ReferenceProperty findReferenceType(AnnotatedMember annotatedMember) {
        return null;
    }

    public abstract String findRootName(AnnotatedClass var1);

    public abstract String findSerializablePropertyName(AnnotatedField var1);

    public Class<?> findSerializationContentType(Annotated annotated, JavaType javaType) {
        return null;
    }

    public JsonSerialize.Inclusion findSerializationInclusion(Annotated annotated, JsonSerialize.Inclusion inclusion) {
        return inclusion;
    }

    public Class<?> findSerializationKeyType(Annotated annotated, JavaType javaType) {
        return null;
    }

    public abstract String[] findSerializationPropertyOrder(AnnotatedClass var1);

    public abstract Boolean findSerializationSortAlphabetically(AnnotatedClass var1);

    public abstract Class<?> findSerializationType(Annotated var1);

    public abstract JsonSerialize.Typing findSerializationTyping(Annotated var1);

    public abstract Class<?>[] findSerializationViews(Annotated var1);

    public abstract Object findSerializer(Annotated var1);

    public abstract String findSettablePropertyName(AnnotatedMethod var1);

    public List<NamedType> findSubtypes(Annotated annotated) {
        return null;
    }

    public String findTypeName(AnnotatedClass annotatedClass) {
        return null;
    }

    public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, JavaType javaType) {
        return null;
    }

    public Object findValueInstantiator(AnnotatedClass annotatedClass) {
        return null;
    }

    public boolean hasAnyGetterAnnotation(AnnotatedMethod annotatedMethod) {
        return false;
    }

    public boolean hasAnySetterAnnotation(AnnotatedMethod annotatedMethod) {
        return false;
    }

    public abstract boolean hasAsValueAnnotation(AnnotatedMethod var1);

    public boolean hasCreatorAnnotation(Annotated annotated) {
        return false;
    }

    public boolean hasIgnoreMarker(AnnotatedMember annotatedMember) {
        if (annotatedMember instanceof AnnotatedMethod) {
            return this.isIgnorableMethod((AnnotatedMethod)annotatedMember);
        }
        if (annotatedMember instanceof AnnotatedField) {
            return this.isIgnorableField((AnnotatedField)annotatedMember);
        }
        if (annotatedMember instanceof AnnotatedConstructor) {
            return this.isIgnorableConstructor((AnnotatedConstructor)annotatedMember);
        }
        return false;
    }

    public abstract boolean isHandled(Annotation var1);

    public abstract boolean isIgnorableConstructor(AnnotatedConstructor var1);

    public abstract boolean isIgnorableField(AnnotatedField var1);

    public abstract boolean isIgnorableMethod(AnnotatedMethod var1);

    public Boolean isIgnorableType(AnnotatedClass annotatedClass) {
        return null;
    }

    public Boolean shouldUnwrapProperty(AnnotatedMember annotatedMember) {
        return null;
    }

    public static class Pair
    extends AnnotationIntrospector {
        protected final AnnotationIntrospector _primary;
        protected final AnnotationIntrospector _secondary;

        public Pair(AnnotationIntrospector annotationIntrospector, AnnotationIntrospector annotationIntrospector2) {
            this._primary = annotationIntrospector;
            this._secondary = annotationIntrospector2;
        }

        public static AnnotationIntrospector create(AnnotationIntrospector annotationIntrospector, AnnotationIntrospector annotationIntrospector2) {
            if (annotationIntrospector == null) {
                return annotationIntrospector2;
            }
            if (annotationIntrospector2 == null) {
                return annotationIntrospector;
            }
            return new Pair(annotationIntrospector, annotationIntrospector2);
        }

        @Override
        public Collection<AnnotationIntrospector> allIntrospectors() {
            return this.allIntrospectors((Collection<AnnotationIntrospector>)new ArrayList());
        }

        @Override
        public Collection<AnnotationIntrospector> allIntrospectors(Collection<AnnotationIntrospector> collection) {
            this._primary.allIntrospectors(collection);
            this._secondary.allIntrospectors(collection);
            return collection;
        }

        @Override
        public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass annotatedClass, VisibilityChecker<?> visibilityChecker) {
            VisibilityChecker<?> visibilityChecker2 = this._secondary.findAutoDetectVisibility(annotatedClass, visibilityChecker);
            return this._primary.findAutoDetectVisibility(annotatedClass, visibilityChecker2);
        }

        @Override
        public Boolean findCachability(AnnotatedClass annotatedClass) {
            Boolean bl = this._primary.findCachability(annotatedClass);
            if (bl == null) {
                bl = this._secondary.findCachability(annotatedClass);
            }
            return bl;
        }

        @Override
        public Class<? extends JsonDeserializer<?>> findContentDeserializer(Annotated annotated) {
            Class<? extends JsonDeserializer<?>> class_ = this._primary.findContentDeserializer(annotated);
            if (class_ == null || class_ == JsonDeserializer.None.class) {
                class_ = this._secondary.findContentDeserializer(annotated);
            }
            return class_;
        }

        @Override
        public Class<? extends JsonSerializer<?>> findContentSerializer(Annotated annotated) {
            Class<? extends JsonSerializer<?>> class_ = this._primary.findContentSerializer(annotated);
            if (class_ == null || class_ == JsonSerializer.None.class) {
                class_ = this._secondary.findContentSerializer(annotated);
            }
            return class_;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public String findDeserializablePropertyName(AnnotatedField annotatedField) {
            String string = this._primary.findDeserializablePropertyName(annotatedField);
            if (string == null) {
                return this._secondary.findDeserializablePropertyName(annotatedField);
            }
            if (string.length() != 0) return string;
            String string2 = this._secondary.findDeserializablePropertyName(annotatedField);
            if (string2 == null) return string;
            return string2;
        }

        @Override
        public Class<?> findDeserializationContentType(Annotated annotated, JavaType javaType, String string) {
            Class<?> class_ = this._primary.findDeserializationContentType(annotated, javaType, string);
            if (class_ == null) {
                class_ = this._secondary.findDeserializationContentType(annotated, javaType, string);
            }
            return class_;
        }

        @Override
        public Class<?> findDeserializationKeyType(Annotated annotated, JavaType javaType, String string) {
            Class<?> class_ = this._primary.findDeserializationKeyType(annotated, javaType, string);
            if (class_ == null) {
                class_ = this._secondary.findDeserializationKeyType(annotated, javaType, string);
            }
            return class_;
        }

        @Override
        public Class<?> findDeserializationType(Annotated annotated, JavaType javaType, String string) {
            Class<?> class_ = this._primary.findDeserializationType(annotated, javaType, string);
            if (class_ == null) {
                class_ = this._secondary.findDeserializationType(annotated, javaType, string);
            }
            return class_;
        }

        @Override
        public Object findDeserializer(Annotated annotated) {
            Object object = this._primary.findDeserializer(annotated);
            if (object == null) {
                object = this._secondary.findDeserializer(annotated);
            }
            return object;
        }

        @Override
        public String findEnumValue(Enum<?> enum_) {
            String string = this._primary.findEnumValue(enum_);
            if (string == null) {
                string = this._secondary.findEnumValue(enum_);
            }
            return string;
        }

        @Override
        public Object findFilterId(AnnotatedClass annotatedClass) {
            Object object = this._primary.findFilterId(annotatedClass);
            if (object == null) {
                object = this._secondary.findFilterId(annotatedClass);
            }
            return object;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public String findGettablePropertyName(AnnotatedMethod annotatedMethod) {
            String string = this._primary.findGettablePropertyName(annotatedMethod);
            if (string == null) {
                return this._secondary.findGettablePropertyName(annotatedMethod);
            }
            if (string.length() != 0) return string;
            String string2 = this._secondary.findGettablePropertyName(annotatedMethod);
            if (string2 == null) return string;
            return string2;
        }

        @Override
        public Boolean findIgnoreUnknownProperties(AnnotatedClass annotatedClass) {
            Boolean bl = this._primary.findIgnoreUnknownProperties(annotatedClass);
            if (bl == null) {
                bl = this._secondary.findIgnoreUnknownProperties(annotatedClass);
            }
            return bl;
        }

        @Override
        public Object findInjectableValueId(AnnotatedMember annotatedMember) {
            Object object = this._primary.findInjectableValueId(annotatedMember);
            if (object == null) {
                object = this._secondary.findInjectableValueId(annotatedMember);
            }
            return object;
        }

        @Override
        public Class<? extends KeyDeserializer> findKeyDeserializer(Annotated annotated) {
            Class<? extends KeyDeserializer> class_ = this._primary.findKeyDeserializer(annotated);
            if (class_ == null || class_ == KeyDeserializer.None.class) {
                class_ = this._secondary.findKeyDeserializer(annotated);
            }
            return class_;
        }

        @Override
        public Class<? extends JsonSerializer<?>> findKeySerializer(Annotated annotated) {
            Class<? extends JsonSerializer<?>> class_ = this._primary.findKeySerializer(annotated);
            if (class_ == null || class_ == JsonSerializer.None.class) {
                class_ = this._secondary.findKeySerializer(annotated);
            }
            return class_;
        }

        @Override
        public String[] findPropertiesToIgnore(AnnotatedClass annotatedClass) {
            String[] arrstring = this._primary.findPropertiesToIgnore(annotatedClass);
            if (arrstring == null) {
                arrstring = this._secondary.findPropertiesToIgnore(annotatedClass);
            }
            return arrstring;
        }

        @Override
        public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
            TypeResolverBuilder<?> typeResolverBuilder = this._primary.findPropertyContentTypeResolver(mapperConfig, annotatedMember, javaType);
            if (typeResolverBuilder == null) {
                typeResolverBuilder = this._secondary.findPropertyContentTypeResolver(mapperConfig, annotatedMember, javaType);
            }
            return typeResolverBuilder;
        }

        @Override
        public String findPropertyNameForParam(AnnotatedParameter annotatedParameter) {
            String string = this._primary.findPropertyNameForParam(annotatedParameter);
            if (string == null) {
                string = this._secondary.findPropertyNameForParam(annotatedParameter);
            }
            return string;
        }

        @Override
        public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
            TypeResolverBuilder<?> typeResolverBuilder = this._primary.findPropertyTypeResolver(mapperConfig, annotatedMember, javaType);
            if (typeResolverBuilder == null) {
                typeResolverBuilder = this._secondary.findPropertyTypeResolver(mapperConfig, annotatedMember, javaType);
            }
            return typeResolverBuilder;
        }

        @Override
        public ReferenceProperty findReferenceType(AnnotatedMember annotatedMember) {
            ReferenceProperty referenceProperty = this._primary.findReferenceType(annotatedMember);
            if (referenceProperty == null) {
                referenceProperty = this._secondary.findReferenceType(annotatedMember);
            }
            return referenceProperty;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public String findRootName(AnnotatedClass annotatedClass) {
            String string = this._primary.findRootName(annotatedClass);
            if (string == null) {
                return this._secondary.findRootName(annotatedClass);
            }
            if (string.length() > 0) return string;
            String string2 = this._secondary.findRootName(annotatedClass);
            if (string2 == null) return string;
            return string2;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public String findSerializablePropertyName(AnnotatedField annotatedField) {
            String string = this._primary.findSerializablePropertyName(annotatedField);
            if (string == null) {
                return this._secondary.findSerializablePropertyName(annotatedField);
            }
            if (string.length() != 0) return string;
            String string2 = this._secondary.findSerializablePropertyName(annotatedField);
            if (string2 == null) return string;
            return string2;
        }

        @Override
        public Class<?> findSerializationContentType(Annotated annotated, JavaType javaType) {
            Class<?> class_ = this._primary.findSerializationContentType(annotated, javaType);
            if (class_ == null) {
                class_ = this._secondary.findSerializationContentType(annotated, javaType);
            }
            return class_;
        }

        @Override
        public JsonSerialize.Inclusion findSerializationInclusion(Annotated annotated, JsonSerialize.Inclusion inclusion) {
            JsonSerialize.Inclusion inclusion2 = this._secondary.findSerializationInclusion(annotated, inclusion);
            return this._primary.findSerializationInclusion(annotated, inclusion2);
        }

        @Override
        public Class<?> findSerializationKeyType(Annotated annotated, JavaType javaType) {
            Class<?> class_ = this._primary.findSerializationKeyType(annotated, javaType);
            if (class_ == null) {
                class_ = this._secondary.findSerializationKeyType(annotated, javaType);
            }
            return class_;
        }

        @Override
        public String[] findSerializationPropertyOrder(AnnotatedClass annotatedClass) {
            String[] arrstring = this._primary.findSerializationPropertyOrder(annotatedClass);
            if (arrstring == null) {
                arrstring = this._secondary.findSerializationPropertyOrder(annotatedClass);
            }
            return arrstring;
        }

        @Override
        public Boolean findSerializationSortAlphabetically(AnnotatedClass annotatedClass) {
            Boolean bl = this._primary.findSerializationSortAlphabetically(annotatedClass);
            if (bl == null) {
                bl = this._secondary.findSerializationSortAlphabetically(annotatedClass);
            }
            return bl;
        }

        @Override
        public Class<?> findSerializationType(Annotated annotated) {
            Class<?> class_ = this._primary.findSerializationType(annotated);
            if (class_ == null) {
                class_ = this._secondary.findSerializationType(annotated);
            }
            return class_;
        }

        @Override
        public JsonSerialize.Typing findSerializationTyping(Annotated annotated) {
            JsonSerialize.Typing typing = this._primary.findSerializationTyping(annotated);
            if (typing == null) {
                typing = this._secondary.findSerializationTyping(annotated);
            }
            return typing;
        }

        @Override
        public Class<?>[] findSerializationViews(Annotated annotated) {
            Class<?>[] arrclass = this._primary.findSerializationViews(annotated);
            if (arrclass == null) {
                arrclass = this._secondary.findSerializationViews(annotated);
            }
            return arrclass;
        }

        @Override
        public Object findSerializer(Annotated annotated) {
            Object object = this._primary.findSerializer(annotated);
            if (object == null) {
                object = this._secondary.findSerializer(annotated);
            }
            return object;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public String findSettablePropertyName(AnnotatedMethod annotatedMethod) {
            String string = this._primary.findSettablePropertyName(annotatedMethod);
            if (string == null) {
                return this._secondary.findSettablePropertyName(annotatedMethod);
            }
            if (string.length() != 0) return string;
            String string2 = this._secondary.findSettablePropertyName(annotatedMethod);
            if (string2 == null) return string;
            return string2;
        }

        @Override
        public List<NamedType> findSubtypes(Annotated annotated) {
            List<NamedType> list = this._primary.findSubtypes(annotated);
            List<NamedType> list2 = this._secondary.findSubtypes(annotated);
            if (list == null || list.isEmpty()) {
                return list2;
            }
            if (list2 == null || list2.isEmpty()) {
                return list;
            }
            ArrayList arrayList = new ArrayList(list.size() + list2.size());
            arrayList.addAll(list);
            arrayList.addAll(list2);
            return arrayList;
        }

        @Override
        public String findTypeName(AnnotatedClass annotatedClass) {
            String string = this._primary.findTypeName(annotatedClass);
            if (string == null || string.length() == 0) {
                string = this._secondary.findTypeName(annotatedClass);
            }
            return string;
        }

        @Override
        public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, JavaType javaType) {
            TypeResolverBuilder<?> typeResolverBuilder = this._primary.findTypeResolver(mapperConfig, annotatedClass, javaType);
            if (typeResolverBuilder == null) {
                typeResolverBuilder = this._secondary.findTypeResolver(mapperConfig, annotatedClass, javaType);
            }
            return typeResolverBuilder;
        }

        @Override
        public Object findValueInstantiator(AnnotatedClass annotatedClass) {
            Object object = this._primary.findValueInstantiator(annotatedClass);
            if (object == null) {
                object = this._secondary.findValueInstantiator(annotatedClass);
            }
            return object;
        }

        @Override
        public boolean hasAnyGetterAnnotation(AnnotatedMethod annotatedMethod) {
            return this._primary.hasAnyGetterAnnotation(annotatedMethod) || this._secondary.hasAnyGetterAnnotation(annotatedMethod);
        }

        @Override
        public boolean hasAnySetterAnnotation(AnnotatedMethod annotatedMethod) {
            return this._primary.hasAnySetterAnnotation(annotatedMethod) || this._secondary.hasAnySetterAnnotation(annotatedMethod);
        }

        @Override
        public boolean hasAsValueAnnotation(AnnotatedMethod annotatedMethod) {
            return this._primary.hasAsValueAnnotation(annotatedMethod) || this._secondary.hasAsValueAnnotation(annotatedMethod);
        }

        @Override
        public boolean hasCreatorAnnotation(Annotated annotated) {
            return this._primary.hasCreatorAnnotation(annotated) || this._secondary.hasCreatorAnnotation(annotated);
        }

        @Override
        public boolean hasIgnoreMarker(AnnotatedMember annotatedMember) {
            return this._primary.hasIgnoreMarker(annotatedMember) || this._secondary.hasIgnoreMarker(annotatedMember);
        }

        @Override
        public boolean isHandled(Annotation annotation) {
            return this._primary.isHandled(annotation) || this._secondary.isHandled(annotation);
        }

        @Override
        public boolean isIgnorableConstructor(AnnotatedConstructor annotatedConstructor) {
            return this._primary.isIgnorableConstructor(annotatedConstructor) || this._secondary.isIgnorableConstructor(annotatedConstructor);
        }

        @Override
        public boolean isIgnorableField(AnnotatedField annotatedField) {
            return this._primary.isIgnorableField(annotatedField) || this._secondary.isIgnorableField(annotatedField);
        }

        @Override
        public boolean isIgnorableMethod(AnnotatedMethod annotatedMethod) {
            return this._primary.isIgnorableMethod(annotatedMethod) || this._secondary.isIgnorableMethod(annotatedMethod);
        }

        @Override
        public Boolean isIgnorableType(AnnotatedClass annotatedClass) {
            Boolean bl = this._primary.isIgnorableType(annotatedClass);
            if (bl == null) {
                bl = this._secondary.isIgnorableType(annotatedClass);
            }
            return bl;
        }

        @Override
        public Boolean shouldUnwrapProperty(AnnotatedMember annotatedMember) {
            Boolean bl = this._primary.shouldUnwrapProperty(annotatedMember);
            if (bl == null) {
                bl = this._secondary.shouldUnwrapProperty(annotatedMember);
            }
            return bl;
        }
    }

    public static class ReferenceProperty {
        private final String _name;
        private final Type _type;

        public ReferenceProperty(Type type, String string) {
            this._type = type;
            this._name = string;
        }

        public static ReferenceProperty back(String string) {
            return new ReferenceProperty(Type.BACK_REFERENCE, string);
        }

        public static ReferenceProperty managed(String string) {
            return new ReferenceProperty(Type.MANAGED_REFERENCE, string);
        }

        public String getName() {
            return this._name;
        }

        public Type getType() {
            return this._type;
        }

        public boolean isBackReference() {
            return this._type == Type.BACK_REFERENCE;
        }

        public boolean isManagedReference() {
            return this._type == Type.MANAGED_REFERENCE;
        }

        public static final class Type
        extends Enum<Type> {
            private static final /* synthetic */ Type[] $VALUES;
            public static final /* enum */ Type BACK_REFERENCE;
            public static final /* enum */ Type MANAGED_REFERENCE;

            static {
                MANAGED_REFERENCE = new Type();
                BACK_REFERENCE = new Type();
                Type[] arrtype = new Type[]{MANAGED_REFERENCE, BACK_REFERENCE};
                $VALUES = arrtype;
            }

            public static Type valueOf(String string) {
                return (Type)Enum.valueOf(Type.class, (String)string);
            }

            public static Type[] values() {
                return (Type[])$VALUES.clone();
            }
        }

    }

}


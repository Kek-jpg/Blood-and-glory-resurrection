/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.annotation.Annotation
 *  java.util.ArrayList
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.annotate.JacksonAnnotation;
import com.flurry.org.codehaus.jackson.annotate.JsonAnyGetter;
import com.flurry.org.codehaus.jackson.annotate.JsonAnySetter;
import com.flurry.org.codehaus.jackson.annotate.JsonAutoDetect;
import com.flurry.org.codehaus.jackson.annotate.JsonBackReference;
import com.flurry.org.codehaus.jackson.annotate.JsonCreator;
import com.flurry.org.codehaus.jackson.annotate.JsonGetter;
import com.flurry.org.codehaus.jackson.annotate.JsonIgnore;
import com.flurry.org.codehaus.jackson.annotate.JsonIgnoreProperties;
import com.flurry.org.codehaus.jackson.annotate.JsonIgnoreType;
import com.flurry.org.codehaus.jackson.annotate.JsonManagedReference;
import com.flurry.org.codehaus.jackson.annotate.JsonProperty;
import com.flurry.org.codehaus.jackson.annotate.JsonPropertyOrder;
import com.flurry.org.codehaus.jackson.annotate.JsonRawValue;
import com.flurry.org.codehaus.jackson.annotate.JsonSetter;
import com.flurry.org.codehaus.jackson.annotate.JsonSubTypes;
import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.annotate.JsonTypeName;
import com.flurry.org.codehaus.jackson.annotate.JsonUnwrapped;
import com.flurry.org.codehaus.jackson.annotate.JsonValue;
import com.flurry.org.codehaus.jackson.annotate.JsonWriteNullProperties;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonInject;
import com.flurry.org.codehaus.jackson.map.annotate.JsonCachable;
import com.flurry.org.codehaus.jackson.map.annotate.JsonDeserialize;
import com.flurry.org.codehaus.jackson.map.annotate.JsonFilter;
import com.flurry.org.codehaus.jackson.map.annotate.JsonRootName;
import com.flurry.org.codehaus.jackson.map.annotate.JsonSerialize;
import com.flurry.org.codehaus.jackson.map.annotate.JsonTypeIdResolver;
import com.flurry.org.codehaus.jackson.map.annotate.JsonTypeResolver;
import com.flurry.org.codehaus.jackson.map.annotate.JsonValueInstantiator;
import com.flurry.org.codehaus.jackson.map.annotate.JsonView;
import com.flurry.org.codehaus.jackson.map.annotate.NoClass;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedParameter;
import com.flurry.org.codehaus.jackson.map.introspect.VisibilityChecker;
import com.flurry.org.codehaus.jackson.map.jsontype.NamedType;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeIdResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.StdTypeResolverBuilder;
import com.flurry.org.codehaus.jackson.map.ser.std.RawSerializer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class JacksonAnnotationIntrospector
extends AnnotationIntrospector {
    protected StdTypeResolverBuilder _constructNoTypeResolverBuilder() {
        return StdTypeResolverBuilder.noTypeInfoBuilder();
    }

    protected StdTypeResolverBuilder _constructStdTypeResolverBuilder() {
        return new StdTypeResolverBuilder();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected TypeResolverBuilder<?> _findTypeResolver(MapperConfig<?> mapperConfig, Annotated annotated, JavaType javaType) {
        JsonTypeIdResolver jsonTypeIdResolver;
        StdTypeResolverBuilder stdTypeResolverBuilder;
        JsonTypeInfo jsonTypeInfo = annotated.getAnnotation(JsonTypeInfo.class);
        JsonTypeResolver jsonTypeResolver = annotated.getAnnotation(JsonTypeResolver.class);
        if (jsonTypeResolver != null) {
            if (jsonTypeInfo == null) {
                return null;
            }
            stdTypeResolverBuilder = mapperConfig.typeResolverBuilderInstance(annotated, jsonTypeResolver.value());
        } else {
            if (jsonTypeInfo == null) {
                return null;
            }
            if (jsonTypeInfo.use() == JsonTypeInfo.Id.NONE) {
                return this._constructNoTypeResolverBuilder();
            }
            stdTypeResolverBuilder = this._constructStdTypeResolverBuilder();
        }
        TypeIdResolver typeIdResolver = (jsonTypeIdResolver = annotated.getAnnotation(JsonTypeIdResolver.class)) == null ? null : mapperConfig.typeIdResolverInstance(annotated, jsonTypeIdResolver.value());
        if (typeIdResolver != null) {
            typeIdResolver.init(javaType);
        }
        Object t = stdTypeResolverBuilder.init(jsonTypeInfo.use(), typeIdResolver);
        JsonTypeInfo.As as = jsonTypeInfo.include();
        if (as == JsonTypeInfo.As.EXTERNAL_PROPERTY && annotated instanceof AnnotatedClass) {
            as = JsonTypeInfo.As.PROPERTY;
        }
        TypeResolverBuilder typeResolverBuilder = (TypeResolverBuilder)t.inclusion(as).typeProperty(jsonTypeInfo.property());
        Class<?> class_ = jsonTypeInfo.defaultImpl();
        if (class_ == JsonTypeInfo.None.class) return typeResolverBuilder;
        return typeResolverBuilder.defaultImpl(class_);
    }

    protected boolean _isIgnorable(Annotated annotated) {
        JsonIgnore jsonIgnore = annotated.getAnnotation(JsonIgnore.class);
        return jsonIgnore != null && jsonIgnore.value();
    }

    @Override
    public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass annotatedClass, VisibilityChecker<?> visibilityChecker) {
        JsonAutoDetect jsonAutoDetect = annotatedClass.getAnnotation(JsonAutoDetect.class);
        if (jsonAutoDetect == null) {
            return visibilityChecker;
        }
        return visibilityChecker.with(jsonAutoDetect);
    }

    @Override
    public Boolean findCachability(AnnotatedClass annotatedClass) {
        JsonCachable jsonCachable = annotatedClass.getAnnotation(JsonCachable.class);
        if (jsonCachable == null) {
            return null;
        }
        if (jsonCachable.value()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Class<? extends JsonDeserializer<?>> findContentDeserializer(Annotated annotated) {
        Class<? extends JsonDeserializer<?>> class_;
        JsonDeserialize jsonDeserialize = annotated.getAnnotation(JsonDeserialize.class);
        if (jsonDeserialize != null && (class_ = jsonDeserialize.contentUsing()) != JsonDeserializer.None.class) {
            return class_;
        }
        return null;
    }

    @Override
    public Class<? extends JsonSerializer<?>> findContentSerializer(Annotated annotated) {
        Class<? extends JsonSerializer<?>> class_;
        JsonSerialize jsonSerialize = annotated.getAnnotation(JsonSerialize.class);
        if (jsonSerialize != null && (class_ = jsonSerialize.contentUsing()) != JsonSerializer.None.class) {
            return class_;
        }
        return null;
    }

    @Override
    public String findDeserializablePropertyName(AnnotatedField annotatedField) {
        JsonProperty jsonProperty = annotatedField.getAnnotation(JsonProperty.class);
        if (jsonProperty != null) {
            return jsonProperty.value();
        }
        if (annotatedField.hasAnnotation(JsonDeserialize.class) || annotatedField.hasAnnotation(JsonView.class) || annotatedField.hasAnnotation(JsonBackReference.class) || annotatedField.hasAnnotation(JsonManagedReference.class)) {
            return "";
        }
        return null;
    }

    @Override
    public Class<?> findDeserializationContentType(Annotated annotated, JavaType javaType, String string) {
        Class<?> class_;
        JsonDeserialize jsonDeserialize = annotated.getAnnotation(JsonDeserialize.class);
        if (jsonDeserialize != null && (class_ = jsonDeserialize.contentAs()) != NoClass.class) {
            return class_;
        }
        return null;
    }

    @Override
    public Class<?> findDeserializationKeyType(Annotated annotated, JavaType javaType, String string) {
        Class<?> class_;
        JsonDeserialize jsonDeserialize = annotated.getAnnotation(JsonDeserialize.class);
        if (jsonDeserialize != null && (class_ = jsonDeserialize.keyAs()) != NoClass.class) {
            return class_;
        }
        return null;
    }

    @Override
    public Class<?> findDeserializationType(Annotated annotated, JavaType javaType, String string) {
        Class<?> class_;
        JsonDeserialize jsonDeserialize = annotated.getAnnotation(JsonDeserialize.class);
        if (jsonDeserialize != null && (class_ = jsonDeserialize.as()) != NoClass.class) {
            return class_;
        }
        return null;
    }

    public Class<? extends JsonDeserializer<?>> findDeserializer(Annotated annotated) {
        Class<? extends JsonDeserializer<?>> class_;
        JsonDeserialize jsonDeserialize = annotated.getAnnotation(JsonDeserialize.class);
        if (jsonDeserialize != null && (class_ = jsonDeserialize.using()) != JsonDeserializer.None.class) {
            return class_;
        }
        return null;
    }

    @Override
    public String findEnumValue(Enum<?> enum_) {
        return enum_.name();
    }

    @Override
    public Object findFilterId(AnnotatedClass annotatedClass) {
        String string;
        JsonFilter jsonFilter = annotatedClass.getAnnotation(JsonFilter.class);
        if (jsonFilter != null && (string = jsonFilter.value()).length() > 0) {
            return string;
        }
        return null;
    }

    @Override
    public String findGettablePropertyName(AnnotatedMethod annotatedMethod) {
        JsonProperty jsonProperty = annotatedMethod.getAnnotation(JsonProperty.class);
        if (jsonProperty != null) {
            return jsonProperty.value();
        }
        JsonGetter jsonGetter = annotatedMethod.getAnnotation(JsonGetter.class);
        if (jsonGetter != null) {
            return jsonGetter.value();
        }
        if (annotatedMethod.hasAnnotation(JsonSerialize.class) || annotatedMethod.hasAnnotation(JsonView.class)) {
            return "";
        }
        return null;
    }

    @Override
    public Boolean findIgnoreUnknownProperties(AnnotatedClass annotatedClass) {
        JsonIgnoreProperties jsonIgnoreProperties = annotatedClass.getAnnotation(JsonIgnoreProperties.class);
        if (jsonIgnoreProperties == null) {
            return null;
        }
        return jsonIgnoreProperties.ignoreUnknown();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object findInjectableValueId(AnnotatedMember annotatedMember) {
        JacksonInject jacksonInject = annotatedMember.getAnnotation(JacksonInject.class);
        if (jacksonInject == null) {
            return null;
        }
        String string = jacksonInject.value();
        if (string.length() != 0) return string;
        if (!(annotatedMember instanceof AnnotatedMethod)) {
            return annotatedMember.getRawType().getName();
        }
        AnnotatedMethod annotatedMethod = (AnnotatedMethod)annotatedMember;
        if (annotatedMethod.getParameterCount() != 0) return annotatedMethod.getParameterClass(0).getName();
        return annotatedMember.getRawType().getName();
    }

    @Override
    public Class<? extends KeyDeserializer> findKeyDeserializer(Annotated annotated) {
        Class<? extends KeyDeserializer> class_;
        JsonDeserialize jsonDeserialize = annotated.getAnnotation(JsonDeserialize.class);
        if (jsonDeserialize != null && (class_ = jsonDeserialize.keyUsing()) != KeyDeserializer.None.class) {
            return class_;
        }
        return null;
    }

    @Override
    public Class<? extends JsonSerializer<?>> findKeySerializer(Annotated annotated) {
        Class<? extends JsonSerializer<?>> class_;
        JsonSerialize jsonSerialize = annotated.getAnnotation(JsonSerialize.class);
        if (jsonSerialize != null && (class_ = jsonSerialize.keyUsing()) != JsonSerializer.None.class) {
            return class_;
        }
        return null;
    }

    @Override
    public String[] findPropertiesToIgnore(AnnotatedClass annotatedClass) {
        JsonIgnoreProperties jsonIgnoreProperties = annotatedClass.getAnnotation(JsonIgnoreProperties.class);
        if (jsonIgnoreProperties == null) {
            return null;
        }
        return jsonIgnoreProperties.value();
    }

    @Override
    public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        if (!javaType.isContainerType()) {
            throw new IllegalArgumentException("Must call method with a container type (got " + javaType + ")");
        }
        return this._findTypeResolver(mapperConfig, annotatedMember, javaType);
    }

    @Override
    public String findPropertyNameForParam(AnnotatedParameter annotatedParameter) {
        JsonProperty jsonProperty;
        if (annotatedParameter != null && (jsonProperty = annotatedParameter.getAnnotation(JsonProperty.class)) != null) {
            return jsonProperty.value();
        }
        return null;
    }

    @Override
    public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        if (javaType.isContainerType()) {
            return null;
        }
        return this._findTypeResolver(mapperConfig, annotatedMember, javaType);
    }

    @Override
    public AnnotationIntrospector.ReferenceProperty findReferenceType(AnnotatedMember annotatedMember) {
        JsonManagedReference jsonManagedReference = annotatedMember.getAnnotation(JsonManagedReference.class);
        if (jsonManagedReference != null) {
            return AnnotationIntrospector.ReferenceProperty.managed(jsonManagedReference.value());
        }
        JsonBackReference jsonBackReference = annotatedMember.getAnnotation(JsonBackReference.class);
        if (jsonBackReference != null) {
            return AnnotationIntrospector.ReferenceProperty.back(jsonBackReference.value());
        }
        return null;
    }

    @Override
    public String findRootName(AnnotatedClass annotatedClass) {
        JsonRootName jsonRootName = annotatedClass.getAnnotation(JsonRootName.class);
        if (jsonRootName == null) {
            return null;
        }
        return jsonRootName.value();
    }

    @Override
    public String findSerializablePropertyName(AnnotatedField annotatedField) {
        JsonProperty jsonProperty = annotatedField.getAnnotation(JsonProperty.class);
        if (jsonProperty != null) {
            return jsonProperty.value();
        }
        if (annotatedField.hasAnnotation(JsonSerialize.class) || annotatedField.hasAnnotation(JsonView.class)) {
            return "";
        }
        return null;
    }

    @Override
    public Class<?> findSerializationContentType(Annotated annotated, JavaType javaType) {
        Class<?> class_;
        JsonSerialize jsonSerialize = annotated.getAnnotation(JsonSerialize.class);
        if (jsonSerialize != null && (class_ = jsonSerialize.contentAs()) != NoClass.class) {
            return class_;
        }
        return null;
    }

    @Override
    public JsonSerialize.Inclusion findSerializationInclusion(Annotated annotated, JsonSerialize.Inclusion inclusion) {
        JsonSerialize jsonSerialize = annotated.getAnnotation(JsonSerialize.class);
        if (jsonSerialize != null) {
            return jsonSerialize.include();
        }
        JsonWriteNullProperties jsonWriteNullProperties = annotated.getAnnotation(JsonWriteNullProperties.class);
        if (jsonWriteNullProperties != null) {
            if (jsonWriteNullProperties.value()) {
                return JsonSerialize.Inclusion.ALWAYS;
            }
            return JsonSerialize.Inclusion.NON_NULL;
        }
        return inclusion;
    }

    @Override
    public Class<?> findSerializationKeyType(Annotated annotated, JavaType javaType) {
        Class<?> class_;
        JsonSerialize jsonSerialize = annotated.getAnnotation(JsonSerialize.class);
        if (jsonSerialize != null && (class_ = jsonSerialize.keyAs()) != NoClass.class) {
            return class_;
        }
        return null;
    }

    @Override
    public String[] findSerializationPropertyOrder(AnnotatedClass annotatedClass) {
        JsonPropertyOrder jsonPropertyOrder = annotatedClass.getAnnotation(JsonPropertyOrder.class);
        if (jsonPropertyOrder == null) {
            return null;
        }
        return jsonPropertyOrder.value();
    }

    @Override
    public Boolean findSerializationSortAlphabetically(AnnotatedClass annotatedClass) {
        JsonPropertyOrder jsonPropertyOrder = annotatedClass.getAnnotation(JsonPropertyOrder.class);
        if (jsonPropertyOrder == null) {
            return null;
        }
        return jsonPropertyOrder.alphabetic();
    }

    @Override
    public Class<?> findSerializationType(Annotated annotated) {
        Class<?> class_;
        JsonSerialize jsonSerialize = annotated.getAnnotation(JsonSerialize.class);
        if (jsonSerialize != null && (class_ = jsonSerialize.as()) != NoClass.class) {
            return class_;
        }
        return null;
    }

    @Override
    public JsonSerialize.Typing findSerializationTyping(Annotated annotated) {
        JsonSerialize jsonSerialize = annotated.getAnnotation(JsonSerialize.class);
        if (jsonSerialize == null) {
            return null;
        }
        return jsonSerialize.typing();
    }

    @Override
    public Class<?>[] findSerializationViews(Annotated annotated) {
        JsonView jsonView = annotated.getAnnotation(JsonView.class);
        if (jsonView == null) {
            return null;
        }
        return jsonView.value();
    }

    @Override
    public Object findSerializer(Annotated annotated) {
        Class<? extends JsonSerializer<?>> class_;
        JsonSerialize jsonSerialize = annotated.getAnnotation(JsonSerialize.class);
        if (jsonSerialize != null && (class_ = jsonSerialize.using()) != JsonSerializer.None.class) {
            return class_;
        }
        JsonRawValue jsonRawValue = annotated.getAnnotation(JsonRawValue.class);
        if (jsonRawValue != null && jsonRawValue.value()) {
            return new RawSerializer(annotated.getRawType());
        }
        return null;
    }

    @Override
    public String findSettablePropertyName(AnnotatedMethod annotatedMethod) {
        JsonProperty jsonProperty = annotatedMethod.getAnnotation(JsonProperty.class);
        if (jsonProperty != null) {
            return jsonProperty.value();
        }
        JsonSetter jsonSetter = annotatedMethod.getAnnotation(JsonSetter.class);
        if (jsonSetter != null) {
            return jsonSetter.value();
        }
        if (annotatedMethod.hasAnnotation(JsonDeserialize.class) || annotatedMethod.hasAnnotation(JsonView.class) || annotatedMethod.hasAnnotation(JsonBackReference.class) || annotatedMethod.hasAnnotation(JsonManagedReference.class)) {
            return "";
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<NamedType> findSubtypes(Annotated annotated) {
        JsonSubTypes jsonSubTypes = annotated.getAnnotation(JsonSubTypes.class);
        if (jsonSubTypes == null) {
            return null;
        }
        JsonSubTypes.Type[] arrtype = jsonSubTypes.value();
        ArrayList arrayList = new ArrayList(arrtype.length);
        int n = arrtype.length;
        int n2 = 0;
        while (n2 < n) {
            JsonSubTypes.Type type = arrtype[n2];
            arrayList.add((Object)new NamedType(type.value(), type.name()));
            ++n2;
        }
        return arrayList;
    }

    @Override
    public String findTypeName(AnnotatedClass annotatedClass) {
        JsonTypeName jsonTypeName = annotatedClass.getAnnotation(JsonTypeName.class);
        if (jsonTypeName == null) {
            return null;
        }
        return jsonTypeName.value();
    }

    @Override
    public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, JavaType javaType) {
        return this._findTypeResolver(mapperConfig, annotatedClass, javaType);
    }

    @Override
    public Object findValueInstantiator(AnnotatedClass annotatedClass) {
        JsonValueInstantiator jsonValueInstantiator = annotatedClass.getAnnotation(JsonValueInstantiator.class);
        if (jsonValueInstantiator == null) {
            return null;
        }
        return jsonValueInstantiator.value();
    }

    @Override
    public boolean hasAnyGetterAnnotation(AnnotatedMethod annotatedMethod) {
        return annotatedMethod.hasAnnotation(JsonAnyGetter.class);
    }

    @Override
    public boolean hasAnySetterAnnotation(AnnotatedMethod annotatedMethod) {
        return annotatedMethod.hasAnnotation(JsonAnySetter.class);
    }

    @Override
    public boolean hasAsValueAnnotation(AnnotatedMethod annotatedMethod) {
        JsonValue jsonValue = annotatedMethod.getAnnotation(JsonValue.class);
        return jsonValue != null && jsonValue.value();
    }

    @Override
    public boolean hasCreatorAnnotation(Annotated annotated) {
        return annotated.hasAnnotation(JsonCreator.class);
    }

    @Override
    public boolean hasIgnoreMarker(AnnotatedMember annotatedMember) {
        return this._isIgnorable(annotatedMember);
    }

    @Override
    public boolean isHandled(Annotation annotation) {
        return annotation.annotationType().getAnnotation(JacksonAnnotation.class) != null;
    }

    @Override
    public boolean isIgnorableConstructor(AnnotatedConstructor annotatedConstructor) {
        return this._isIgnorable(annotatedConstructor);
    }

    @Override
    public boolean isIgnorableField(AnnotatedField annotatedField) {
        return this._isIgnorable(annotatedField);
    }

    @Override
    public boolean isIgnorableMethod(AnnotatedMethod annotatedMethod) {
        return this._isIgnorable(annotatedMethod);
    }

    @Override
    public Boolean isIgnorableType(AnnotatedClass annotatedClass) {
        JsonIgnoreType jsonIgnoreType = annotatedClass.getAnnotation(JsonIgnoreType.class);
        if (jsonIgnoreType == null) {
            return null;
        }
        return jsonIgnoreType.value();
    }

    @Override
    public Boolean shouldUnwrapProperty(AnnotatedMember annotatedMember) {
        JsonUnwrapped jsonUnwrapped = annotatedMember.getAnnotation(JsonUnwrapped.class);
        if (jsonUnwrapped != null && jsonUnwrapped.enabled()) {
            return Boolean.TRUE;
        }
        return null;
    }
}


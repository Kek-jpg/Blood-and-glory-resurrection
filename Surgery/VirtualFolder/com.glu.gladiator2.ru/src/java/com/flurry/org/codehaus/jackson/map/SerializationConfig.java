/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.ser.FilterProvider
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Enum
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.text.DateFormat
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.annotate.JsonAutoDetect;
import com.flurry.org.codehaus.jackson.annotate.JsonMethod;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.ClassIntrospector;
import com.flurry.org.codehaus.jackson.map.HandlerInstantiator;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.PropertyNamingStrategy;
import com.flurry.org.codehaus.jackson.map.annotate.JsonSerialize;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.VisibilityChecker;
import com.flurry.org.codehaus.jackson.map.jsontype.SubtypeResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import com.flurry.org.codehaus.jackson.map.ser.FilterProvider;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.text.DateFormat;
import java.util.HashMap;

public class SerializationConfig
extends MapperConfig.Impl<Feature, SerializationConfig> {
    protected FilterProvider _filterProvider;
    protected JsonSerialize.Inclusion _serializationInclusion;
    protected Class<?> _serializationView;

    public SerializationConfig(ClassIntrospector<? extends BeanDescription> classIntrospector, AnnotationIntrospector annotationIntrospector, VisibilityChecker<?> visibilityChecker, SubtypeResolver subtypeResolver, PropertyNamingStrategy propertyNamingStrategy, TypeFactory typeFactory, HandlerInstantiator handlerInstantiator) {
        super(classIntrospector, annotationIntrospector, visibilityChecker, subtypeResolver, propertyNamingStrategy, typeFactory, handlerInstantiator, SerializationConfig.collectFeatureDefaults(Feature.class));
        this._serializationInclusion = null;
        this._filterProvider = null;
    }

    protected SerializationConfig(SerializationConfig serializationConfig) {
        super(serializationConfig, serializationConfig._base);
    }

    protected SerializationConfig(SerializationConfig serializationConfig, int n) {
        super(serializationConfig, n);
        this._serializationInclusion = null;
        this._serializationInclusion = serializationConfig._serializationInclusion;
        this._serializationView = serializationConfig._serializationView;
        this._filterProvider = serializationConfig._filterProvider;
    }

    protected SerializationConfig(SerializationConfig serializationConfig, MapperConfig.Base base) {
        super(serializationConfig, base, serializationConfig._subtypeResolver);
        this._serializationInclusion = null;
        this._serializationInclusion = serializationConfig._serializationInclusion;
        this._serializationView = serializationConfig._serializationView;
        this._filterProvider = serializationConfig._filterProvider;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected SerializationConfig(SerializationConfig serializationConfig, JsonSerialize.Inclusion inclusion) {
        super(serializationConfig);
        this._serializationInclusion = null;
        this._serializationInclusion = inclusion;
        this._featureFlags = inclusion == JsonSerialize.Inclusion.NON_NULL ? (this._featureFlags &= -1 ^ Feature.WRITE_NULL_PROPERTIES.getMask()) : (this._featureFlags |= Feature.WRITE_NULL_PROPERTIES.getMask());
        this._serializationView = serializationConfig._serializationView;
        this._filterProvider = serializationConfig._filterProvider;
    }

    protected SerializationConfig(SerializationConfig serializationConfig, FilterProvider filterProvider) {
        super(serializationConfig);
        this._serializationInclusion = null;
        this._serializationInclusion = serializationConfig._serializationInclusion;
        this._serializationView = serializationConfig._serializationView;
        this._filterProvider = filterProvider;
    }

    protected SerializationConfig(SerializationConfig serializationConfig, Class<?> class_) {
        super(serializationConfig);
        this._serializationInclusion = null;
        this._serializationInclusion = serializationConfig._serializationInclusion;
        this._serializationView = class_;
        this._filterProvider = serializationConfig._filterProvider;
    }

    protected SerializationConfig(SerializationConfig serializationConfig, HashMap<ClassKey, Class<?>> hashMap, SubtypeResolver subtypeResolver) {
        super(serializationConfig, serializationConfig._base);
        this._mixInAnnotations = hashMap;
        this._subtypeResolver = subtypeResolver;
    }

    @Override
    public boolean canOverrideAccessModifiers() {
        return this.isEnabled(Feature.CAN_OVERRIDE_ACCESS_MODIFIERS);
    }

    @Override
    public SerializationConfig createUnshared(SubtypeResolver subtypeResolver) {
        HashMap hashMap = this._mixInAnnotations;
        this._mixInAnnotationsShared = true;
        return new SerializationConfig((SerializationConfig)this, hashMap, subtypeResolver);
    }

    @Deprecated
    @Override
    public void disable(Feature feature) {
        super.disable(feature);
    }

    @Deprecated
    @Override
    public void enable(Feature feature) {
        super.enable(feature);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    @Override
    public void fromAnnotations(Class<?> class_) {
        JsonSerialize.Typing typing;
        AnnotationIntrospector annotationIntrospector = this.getAnnotationIntrospector();
        AnnotatedClass annotatedClass = AnnotatedClass.construct(class_, annotationIntrospector, null);
        this._base = this._base.withVisibilityChecker(annotationIntrospector.findAutoDetectVisibility(annotatedClass, this.getDefaultVisibilityChecker()));
        JsonSerialize.Inclusion inclusion = annotationIntrospector.findSerializationInclusion(annotatedClass, null);
        if (inclusion != this._serializationInclusion) {
            this.setSerializationInclusion(inclusion);
        }
        if ((typing = annotationIntrospector.findSerializationTyping(annotatedClass)) != null) {
            Feature feature = Feature.USE_STATIC_TYPING;
            boolean bl = typing == JsonSerialize.Typing.STATIC;
            this.set(feature, bl);
        }
    }

    @Override
    public AnnotationIntrospector getAnnotationIntrospector() {
        if (this.isEnabled(Feature.USE_ANNOTATIONS)) {
            return super.getAnnotationIntrospector();
        }
        return AnnotationIntrospector.nopInstance();
    }

    @Override
    public VisibilityChecker<?> getDefaultVisibilityChecker() {
        VisibilityChecker<?> visibilityChecker = super.getDefaultVisibilityChecker();
        if (!this.isEnabled(Feature.AUTO_DETECT_GETTERS)) {
            visibilityChecker = visibilityChecker.withGetterVisibility(JsonAutoDetect.Visibility.NONE);
        }
        if (!this.isEnabled(Feature.AUTO_DETECT_IS_GETTERS)) {
            visibilityChecker = visibilityChecker.withIsGetterVisibility(JsonAutoDetect.Visibility.NONE);
        }
        if (!this.isEnabled(Feature.AUTO_DETECT_FIELDS)) {
            visibilityChecker = visibilityChecker.withFieldVisibility(JsonAutoDetect.Visibility.NONE);
        }
        return visibilityChecker;
    }

    public FilterProvider getFilterProvider() {
        return this._filterProvider;
    }

    public JsonSerialize.Inclusion getSerializationInclusion() {
        if (this._serializationInclusion != null) {
            return this._serializationInclusion;
        }
        if (this.isEnabled(Feature.WRITE_NULL_PROPERTIES)) {
            return JsonSerialize.Inclusion.ALWAYS;
        }
        return JsonSerialize.Inclusion.NON_NULL;
    }

    public Class<?> getSerializationView() {
        return this._serializationView;
    }

    public <T extends BeanDescription> T introspect(JavaType javaType) {
        return (T)this.getClassIntrospector().forSerialization((SerializationConfig)this, javaType, (ClassIntrospector.MixInResolver)this);
    }

    @Override
    public <T extends BeanDescription> T introspectClassAnnotations(JavaType javaType) {
        return (T)this.getClassIntrospector().forClassAnnotations((MapperConfig<?>)this, javaType, (ClassIntrospector.MixInResolver)this);
    }

    @Override
    public <T extends BeanDescription> T introspectDirectClassAnnotations(JavaType javaType) {
        return (T)this.getClassIntrospector().forDirectClassAnnotations((MapperConfig<?>)this, javaType, (ClassIntrospector.MixInResolver)this);
    }

    @Override
    public boolean isAnnotationProcessingEnabled() {
        return this.isEnabled(Feature.USE_ANNOTATIONS);
    }

    public boolean isEnabled(Feature feature) {
        return (this._featureFlags & feature.getMask()) != 0;
    }

    public JsonSerializer<Object> serializerInstance(Annotated annotated, Class<? extends JsonSerializer<?>> class_) {
        JsonSerializer<Object> jsonSerializer;
        HandlerInstantiator handlerInstantiator = this.getHandlerInstantiator();
        if (handlerInstantiator != null && (jsonSerializer = handlerInstantiator.serializerInstance((SerializationConfig)this, annotated, class_)) != null) {
            return jsonSerializer;
        }
        return ClassUtil.createInstance(class_, this.canOverrideAccessModifiers());
    }

    @Deprecated
    @Override
    public void set(Feature feature, boolean bl) {
        super.set(feature, bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    @Override
    public final void setDateFormat(DateFormat dateFormat) {
        super.setDateFormat(dateFormat);
        Feature feature = Feature.WRITE_DATES_AS_TIMESTAMPS;
        boolean bl = dateFormat == null;
        this.set(feature, bl);
    }

    @Deprecated
    public void setSerializationInclusion(JsonSerialize.Inclusion inclusion) {
        this._serializationInclusion = inclusion;
        if (inclusion == JsonSerialize.Inclusion.NON_NULL) {
            this.disable(Feature.WRITE_NULL_PROPERTIES);
            return;
        }
        this.enable(Feature.WRITE_NULL_PROPERTIES);
    }

    @Deprecated
    public void setSerializationView(Class<?> class_) {
        this._serializationView = class_;
    }

    @Override
    public boolean shouldSortPropertiesAlphabetically() {
        return this.isEnabled(Feature.SORT_PROPERTIES_ALPHABETICALLY);
    }

    public String toString() {
        return "[SerializationConfig: flags=0x" + Integer.toHexString((int)this._featureFlags) + "]";
    }

    public /* varargs */ SerializationConfig with(Feature ... arrfeature) {
        int n = this._featureFlags;
        int n2 = arrfeature.length;
        for (int i = 0; i < n2; ++i) {
            n |= arrfeature[i].getMask();
        }
        return new SerializationConfig((SerializationConfig)this, n);
    }

    @Override
    public SerializationConfig withAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return new SerializationConfig((SerializationConfig)this, this._base.withAnnotationIntrospector(annotationIntrospector));
    }

    @Override
    public SerializationConfig withAppendedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return new SerializationConfig((SerializationConfig)this, this._base.withAppendedAnnotationIntrospector(annotationIntrospector));
    }

    @Override
    public SerializationConfig withClassIntrospector(ClassIntrospector<? extends BeanDescription> classIntrospector) {
        return new SerializationConfig((SerializationConfig)this, this._base.withClassIntrospector(classIntrospector));
    }

    @Override
    public SerializationConfig withDateFormat(DateFormat dateFormat) {
        SerializationConfig serializationConfig = new SerializationConfig((SerializationConfig)this, this._base.withDateFormat(dateFormat));
        if (dateFormat == null) {
            Feature[] arrfeature = new Feature[]{Feature.WRITE_DATES_AS_TIMESTAMPS};
            return serializationConfig.with(arrfeature);
        }
        Feature[] arrfeature = new Feature[]{Feature.WRITE_DATES_AS_TIMESTAMPS};
        return serializationConfig.without(arrfeature);
    }

    public SerializationConfig withFilters(FilterProvider filterProvider) {
        return new SerializationConfig((SerializationConfig)this, filterProvider);
    }

    @Override
    public SerializationConfig withHandlerInstantiator(HandlerInstantiator handlerInstantiator) {
        return new SerializationConfig((SerializationConfig)this, this._base.withHandlerInstantiator(handlerInstantiator));
    }

    @Override
    public SerializationConfig withInsertedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return new SerializationConfig((SerializationConfig)this, this._base.withInsertedAnnotationIntrospector(annotationIntrospector));
    }

    @Override
    public SerializationConfig withPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
        return new SerializationConfig((SerializationConfig)this, this._base.withPropertyNamingStrategy(propertyNamingStrategy));
    }

    public SerializationConfig withSerializationInclusion(JsonSerialize.Inclusion inclusion) {
        return new SerializationConfig((SerializationConfig)this, inclusion);
    }

    @Override
    public SerializationConfig withSubtypeResolver(SubtypeResolver subtypeResolver) {
        SerializationConfig serializationConfig = new SerializationConfig((SerializationConfig)this);
        serializationConfig._subtypeResolver = subtypeResolver;
        return serializationConfig;
    }

    @Override
    public SerializationConfig withTypeFactory(TypeFactory typeFactory) {
        return new SerializationConfig((SerializationConfig)this, this._base.withTypeFactory(typeFactory));
    }

    @Override
    public SerializationConfig withTypeResolverBuilder(TypeResolverBuilder<?> typeResolverBuilder) {
        return new SerializationConfig((SerializationConfig)this, this._base.withTypeResolverBuilder(typeResolverBuilder));
    }

    public SerializationConfig withView(Class<?> class_) {
        return new SerializationConfig((SerializationConfig)this, class_);
    }

    @Override
    public SerializationConfig withVisibility(JsonMethod jsonMethod, JsonAutoDetect.Visibility visibility) {
        return new SerializationConfig((SerializationConfig)this, this._base.withVisibility(jsonMethod, visibility));
    }

    @Override
    public SerializationConfig withVisibilityChecker(VisibilityChecker<?> visibilityChecker) {
        return new SerializationConfig((SerializationConfig)this, this._base.withVisibilityChecker(visibilityChecker));
    }

    public /* varargs */ SerializationConfig without(Feature ... arrfeature) {
        int n = this._featureFlags;
        int n2 = arrfeature.length;
        for (int i = 0; i < n2; ++i) {
            n &= -1 ^ arrfeature[i].getMask();
        }
        return new SerializationConfig((SerializationConfig)this, n);
    }

    public static final class Feature
    extends Enum<Feature>
    implements MapperConfig.ConfigFeature {
        private static final /* synthetic */ Feature[] $VALUES;
        public static final /* enum */ Feature AUTO_DETECT_FIELDS;
        public static final /* enum */ Feature AUTO_DETECT_GETTERS;
        public static final /* enum */ Feature AUTO_DETECT_IS_GETTERS;
        public static final /* enum */ Feature CAN_OVERRIDE_ACCESS_MODIFIERS;
        public static final /* enum */ Feature CLOSE_CLOSEABLE;
        public static final /* enum */ Feature DEFAULT_VIEW_INCLUSION;
        public static final /* enum */ Feature FAIL_ON_EMPTY_BEANS;
        public static final /* enum */ Feature FLUSH_AFTER_WRITE_VALUE;
        public static final /* enum */ Feature INDENT_OUTPUT;
        public static final /* enum */ Feature REQUIRE_SETTERS_FOR_GETTERS;
        public static final /* enum */ Feature SORT_PROPERTIES_ALPHABETICALLY;
        public static final /* enum */ Feature USE_ANNOTATIONS;
        public static final /* enum */ Feature USE_STATIC_TYPING;
        public static final /* enum */ Feature WRAP_EXCEPTIONS;
        public static final /* enum */ Feature WRAP_ROOT_VALUE;
        public static final /* enum */ Feature WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS;
        public static final /* enum */ Feature WRITE_DATES_AS_TIMESTAMPS;
        public static final /* enum */ Feature WRITE_DATE_KEYS_AS_TIMESTAMPS;
        public static final /* enum */ Feature WRITE_EMPTY_JSON_ARRAYS;
        public static final /* enum */ Feature WRITE_ENUMS_USING_INDEX;
        public static final /* enum */ Feature WRITE_ENUMS_USING_TO_STRING;
        public static final /* enum */ Feature WRITE_NULL_MAP_VALUES;
        @Deprecated
        public static final /* enum */ Feature WRITE_NULL_PROPERTIES;
        final boolean _defaultState;

        static {
            USE_ANNOTATIONS = new Feature(true);
            AUTO_DETECT_GETTERS = new Feature(true);
            AUTO_DETECT_IS_GETTERS = new Feature(true);
            AUTO_DETECT_FIELDS = new Feature(true);
            CAN_OVERRIDE_ACCESS_MODIFIERS = new Feature(true);
            REQUIRE_SETTERS_FOR_GETTERS = new Feature(false);
            WRITE_NULL_PROPERTIES = new Feature(true);
            USE_STATIC_TYPING = new Feature(false);
            DEFAULT_VIEW_INCLUSION = new Feature(true);
            WRAP_ROOT_VALUE = new Feature(false);
            INDENT_OUTPUT = new Feature(false);
            SORT_PROPERTIES_ALPHABETICALLY = new Feature(false);
            FAIL_ON_EMPTY_BEANS = new Feature(true);
            WRAP_EXCEPTIONS = new Feature(true);
            CLOSE_CLOSEABLE = new Feature(false);
            FLUSH_AFTER_WRITE_VALUE = new Feature(true);
            WRITE_DATES_AS_TIMESTAMPS = new Feature(true);
            WRITE_DATE_KEYS_AS_TIMESTAMPS = new Feature(false);
            WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS = new Feature(false);
            WRITE_ENUMS_USING_TO_STRING = new Feature(false);
            WRITE_ENUMS_USING_INDEX = new Feature(false);
            WRITE_NULL_MAP_VALUES = new Feature(true);
            WRITE_EMPTY_JSON_ARRAYS = new Feature(true);
            Feature[] arrfeature = new Feature[]{USE_ANNOTATIONS, AUTO_DETECT_GETTERS, AUTO_DETECT_IS_GETTERS, AUTO_DETECT_FIELDS, CAN_OVERRIDE_ACCESS_MODIFIERS, REQUIRE_SETTERS_FOR_GETTERS, WRITE_NULL_PROPERTIES, USE_STATIC_TYPING, DEFAULT_VIEW_INCLUSION, WRAP_ROOT_VALUE, INDENT_OUTPUT, SORT_PROPERTIES_ALPHABETICALLY, FAIL_ON_EMPTY_BEANS, WRAP_EXCEPTIONS, CLOSE_CLOSEABLE, FLUSH_AFTER_WRITE_VALUE, WRITE_DATES_AS_TIMESTAMPS, WRITE_DATE_KEYS_AS_TIMESTAMPS, WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, WRITE_ENUMS_USING_TO_STRING, WRITE_ENUMS_USING_INDEX, WRITE_NULL_MAP_VALUES, WRITE_EMPTY_JSON_ARRAYS};
            $VALUES = arrfeature;
        }

        private Feature(boolean bl) {
            this._defaultState = bl;
        }

        public static Feature valueOf(String string) {
            return (Feature)Enum.valueOf(Feature.class, (String)string);
        }

        public static Feature[] values() {
            return (Feature[])$VALUES.clone();
        }

        @Override
        public boolean enabledByDefault() {
            return this._defaultState;
        }

        @Override
        public int getMask() {
            return 1 << this.ordinal();
        }
    }

}


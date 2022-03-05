/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.text.DateFormat
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.Base64Variants;
import com.flurry.org.codehaus.jackson.annotate.JsonAutoDetect;
import com.flurry.org.codehaus.jackson.annotate.JsonMethod;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.ClassIntrospector;
import com.flurry.org.codehaus.jackson.map.DeserializationProblemHandler;
import com.flurry.org.codehaus.jackson.map.HandlerInstantiator;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.PropertyNamingStrategy;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.NopAnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.introspect.VisibilityChecker;
import com.flurry.org.codehaus.jackson.map.jsontype.SubtypeResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.map.util.LinkedNode;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.text.DateFormat;
import java.util.HashMap;

public class DeserializationConfig
extends MapperConfig.Impl<Feature, DeserializationConfig> {
    protected final JsonNodeFactory _nodeFactory;
    protected LinkedNode<DeserializationProblemHandler> _problemHandlers;
    protected boolean _sortPropertiesAlphabetically;

    public DeserializationConfig(ClassIntrospector<? extends BeanDescription> classIntrospector, AnnotationIntrospector annotationIntrospector, VisibilityChecker<?> visibilityChecker, SubtypeResolver subtypeResolver, PropertyNamingStrategy propertyNamingStrategy, TypeFactory typeFactory, HandlerInstantiator handlerInstantiator) {
        super(classIntrospector, annotationIntrospector, visibilityChecker, subtypeResolver, propertyNamingStrategy, typeFactory, handlerInstantiator, DeserializationConfig.collectFeatureDefaults(Feature.class));
        this._nodeFactory = JsonNodeFactory.instance;
    }

    protected DeserializationConfig(DeserializationConfig deserializationConfig) {
        super(deserializationConfig, deserializationConfig._base);
    }

    protected DeserializationConfig(DeserializationConfig deserializationConfig, int n) {
        super(deserializationConfig, n);
        this._problemHandlers = deserializationConfig._problemHandlers;
        this._nodeFactory = deserializationConfig._nodeFactory;
        this._sortPropertiesAlphabetically = deserializationConfig._sortPropertiesAlphabetically;
    }

    protected DeserializationConfig(DeserializationConfig deserializationConfig, MapperConfig.Base base) {
        super(deserializationConfig, base, deserializationConfig._subtypeResolver);
        this._problemHandlers = deserializationConfig._problemHandlers;
        this._nodeFactory = deserializationConfig._nodeFactory;
        this._sortPropertiesAlphabetically = deserializationConfig._sortPropertiesAlphabetically;
    }

    protected DeserializationConfig(DeserializationConfig deserializationConfig, JsonNodeFactory jsonNodeFactory) {
        super(deserializationConfig);
        this._problemHandlers = deserializationConfig._problemHandlers;
        this._nodeFactory = jsonNodeFactory;
        this._sortPropertiesAlphabetically = deserializationConfig._sortPropertiesAlphabetically;
    }

    private DeserializationConfig(DeserializationConfig deserializationConfig, HashMap<ClassKey, Class<?>> hashMap, SubtypeResolver subtypeResolver) {
        super(deserializationConfig, deserializationConfig._base);
        this._mixInAnnotations = hashMap;
        this._subtypeResolver = subtypeResolver;
    }

    public void addHandler(DeserializationProblemHandler deserializationProblemHandler) {
        if (!LinkedNode.contains(this._problemHandlers, deserializationProblemHandler)) {
            this._problemHandlers = new LinkedNode<DeserializationProblemHandler>(deserializationProblemHandler, this._problemHandlers);
        }
    }

    @Override
    public boolean canOverrideAccessModifiers() {
        return this.isEnabled(Feature.CAN_OVERRIDE_ACCESS_MODIFIERS);
    }

    public void clearHandlers() {
        this._problemHandlers = null;
    }

    @Override
    public DeserializationConfig createUnshared(SubtypeResolver subtypeResolver) {
        HashMap hashMap = this._mixInAnnotations;
        this._mixInAnnotationsShared = true;
        return new DeserializationConfig((DeserializationConfig)this, hashMap, subtypeResolver);
    }

    public JsonDeserializer<Object> deserializerInstance(Annotated annotated, Class<? extends JsonDeserializer<?>> class_) {
        JsonDeserializer<Object> jsonDeserializer;
        HandlerInstantiator handlerInstantiator = this.getHandlerInstantiator();
        if (handlerInstantiator != null && (jsonDeserializer = handlerInstantiator.deserializerInstance((DeserializationConfig)this, annotated, class_)) != null) {
            return jsonDeserializer;
        }
        return ClassUtil.createInstance(class_, this.canOverrideAccessModifiers());
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

    @Deprecated
    @Override
    public void fromAnnotations(Class<?> class_) {
        AnnotationIntrospector annotationIntrospector = this.getAnnotationIntrospector();
        AnnotatedClass annotatedClass = AnnotatedClass.construct(class_, annotationIntrospector, null);
        VisibilityChecker<?> visibilityChecker = this.getDefaultVisibilityChecker();
        this._base = this._base.withVisibilityChecker(annotationIntrospector.findAutoDetectVisibility(annotatedClass, visibilityChecker));
    }

    @Override
    public AnnotationIntrospector getAnnotationIntrospector() {
        if (this.isEnabled(Feature.USE_ANNOTATIONS)) {
            return super.getAnnotationIntrospector();
        }
        return NopAnnotationIntrospector.instance;
    }

    public Base64Variant getBase64Variant() {
        return Base64Variants.getDefaultVariant();
    }

    @Override
    public VisibilityChecker<?> getDefaultVisibilityChecker() {
        VisibilityChecker<?> visibilityChecker = super.getDefaultVisibilityChecker();
        if (!this.isEnabled(Feature.AUTO_DETECT_SETTERS)) {
            visibilityChecker = visibilityChecker.withSetterVisibility(JsonAutoDetect.Visibility.NONE);
        }
        if (!this.isEnabled(Feature.AUTO_DETECT_CREATORS)) {
            visibilityChecker = visibilityChecker.withCreatorVisibility(JsonAutoDetect.Visibility.NONE);
        }
        if (!this.isEnabled(Feature.AUTO_DETECT_FIELDS)) {
            visibilityChecker = visibilityChecker.withFieldVisibility(JsonAutoDetect.Visibility.NONE);
        }
        return visibilityChecker;
    }

    public final JsonNodeFactory getNodeFactory() {
        return this._nodeFactory;
    }

    public LinkedNode<DeserializationProblemHandler> getProblemHandlers() {
        return this._problemHandlers;
    }

    public <T extends BeanDescription> T introspect(JavaType javaType) {
        return (T)this.getClassIntrospector().forDeserialization((DeserializationConfig)this, javaType, (ClassIntrospector.MixInResolver)this);
    }

    @Override
    public <T extends BeanDescription> T introspectClassAnnotations(JavaType javaType) {
        return (T)this.getClassIntrospector().forClassAnnotations((MapperConfig<?>)this, javaType, (ClassIntrospector.MixInResolver)this);
    }

    @Override
    public <T extends BeanDescription> T introspectDirectClassAnnotations(JavaType javaType) {
        return (T)this.getClassIntrospector().forDirectClassAnnotations((MapperConfig<?>)this, javaType, (ClassIntrospector.MixInResolver)this);
    }

    public <T extends BeanDescription> T introspectForCreation(JavaType javaType) {
        return (T)this.getClassIntrospector().forCreation((DeserializationConfig)this, javaType, (ClassIntrospector.MixInResolver)this);
    }

    @Override
    public boolean isAnnotationProcessingEnabled() {
        return this.isEnabled(Feature.USE_ANNOTATIONS);
    }

    public boolean isEnabled(Feature feature) {
        return (this._featureFlags & feature.getMask()) != 0;
    }

    public KeyDeserializer keyDeserializerInstance(Annotated annotated, Class<? extends KeyDeserializer> class_) {
        KeyDeserializer keyDeserializer;
        HandlerInstantiator handlerInstantiator = this.getHandlerInstantiator();
        if (handlerInstantiator != null && (keyDeserializer = handlerInstantiator.keyDeserializerInstance((DeserializationConfig)this, annotated, class_)) != null) {
            return keyDeserializer;
        }
        return ClassUtil.createInstance(class_, this.canOverrideAccessModifiers());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected DeserializationConfig passSerializationFeatures(int n) {
        boolean bl = (n & SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY.getMask()) != 0;
        this._sortPropertiesAlphabetically = bl;
        return this;
    }

    @Deprecated
    @Override
    public void set(Feature feature, boolean bl) {
        super.set(feature, bl);
    }

    @Override
    public boolean shouldSortPropertiesAlphabetically() {
        return this._sortPropertiesAlphabetically;
    }

    public ValueInstantiator valueInstantiatorInstance(Annotated annotated, Class<? extends ValueInstantiator> class_) {
        ValueInstantiator valueInstantiator;
        HandlerInstantiator handlerInstantiator = this.getHandlerInstantiator();
        if (handlerInstantiator != null && (valueInstantiator = handlerInstantiator.valueInstantiatorInstance((MapperConfig<?>)this, annotated, class_)) != null) {
            return valueInstantiator;
        }
        return ClassUtil.createInstance(class_, this.canOverrideAccessModifiers());
    }

    public /* varargs */ DeserializationConfig with(Feature ... arrfeature) {
        int n = this._featureFlags;
        int n2 = arrfeature.length;
        for (int i = 0; i < n2; ++i) {
            n |= arrfeature[i].getMask();
        }
        return new DeserializationConfig((DeserializationConfig)this, n);
    }

    @Override
    public DeserializationConfig withAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return new DeserializationConfig((DeserializationConfig)this, this._base.withAnnotationIntrospector(annotationIntrospector));
    }

    @Override
    public DeserializationConfig withAppendedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return new DeserializationConfig((DeserializationConfig)this, this._base.withAppendedAnnotationIntrospector(annotationIntrospector));
    }

    @Override
    public DeserializationConfig withClassIntrospector(ClassIntrospector<? extends BeanDescription> classIntrospector) {
        return new DeserializationConfig((DeserializationConfig)this, this._base.withClassIntrospector(classIntrospector));
    }

    @Override
    public DeserializationConfig withDateFormat(DateFormat dateFormat) {
        if (dateFormat == this._base.getDateFormat()) {
            return this;
        }
        return new DeserializationConfig((DeserializationConfig)this, this._base.withDateFormat(dateFormat));
    }

    @Override
    public DeserializationConfig withHandlerInstantiator(HandlerInstantiator handlerInstantiator) {
        if (handlerInstantiator == this._base.getHandlerInstantiator()) {
            return this;
        }
        return new DeserializationConfig((DeserializationConfig)this, this._base.withHandlerInstantiator(handlerInstantiator));
    }

    @Override
    public DeserializationConfig withInsertedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return new DeserializationConfig((DeserializationConfig)this, this._base.withInsertedAnnotationIntrospector(annotationIntrospector));
    }

    public DeserializationConfig withNodeFactory(JsonNodeFactory jsonNodeFactory) {
        return new DeserializationConfig((DeserializationConfig)this, jsonNodeFactory);
    }

    @Override
    public DeserializationConfig withPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
        return new DeserializationConfig((DeserializationConfig)this, this._base.withPropertyNamingStrategy(propertyNamingStrategy));
    }

    @Override
    public DeserializationConfig withSubtypeResolver(SubtypeResolver subtypeResolver) {
        DeserializationConfig deserializationConfig = new DeserializationConfig((DeserializationConfig)this);
        deserializationConfig._subtypeResolver = subtypeResolver;
        return deserializationConfig;
    }

    @Override
    public DeserializationConfig withTypeFactory(TypeFactory typeFactory) {
        if (typeFactory == this._base.getTypeFactory()) {
            return this;
        }
        return new DeserializationConfig((DeserializationConfig)this, this._base.withTypeFactory(typeFactory));
    }

    @Override
    public DeserializationConfig withTypeResolverBuilder(TypeResolverBuilder<?> typeResolverBuilder) {
        return new DeserializationConfig((DeserializationConfig)this, this._base.withTypeResolverBuilder(typeResolverBuilder));
    }

    @Override
    public DeserializationConfig withVisibility(JsonMethod jsonMethod, JsonAutoDetect.Visibility visibility) {
        return new DeserializationConfig((DeserializationConfig)this, this._base.withVisibility(jsonMethod, visibility));
    }

    @Override
    public DeserializationConfig withVisibilityChecker(VisibilityChecker<?> visibilityChecker) {
        return new DeserializationConfig((DeserializationConfig)this, this._base.withVisibilityChecker(visibilityChecker));
    }

    public /* varargs */ DeserializationConfig without(Feature ... arrfeature) {
        int n = this._featureFlags;
        int n2 = arrfeature.length;
        for (int i = 0; i < n2; ++i) {
            n &= -1 ^ arrfeature[i].getMask();
        }
        return new DeserializationConfig((DeserializationConfig)this, n);
    }

    public static final class Feature
    extends Enum<Feature>
    implements MapperConfig.ConfigFeature {
        private static final /* synthetic */ Feature[] $VALUES;
        public static final /* enum */ Feature ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
        public static final /* enum */ Feature ACCEPT_SINGLE_VALUE_AS_ARRAY;
        public static final /* enum */ Feature AUTO_DETECT_CREATORS;
        public static final /* enum */ Feature AUTO_DETECT_FIELDS;
        public static final /* enum */ Feature AUTO_DETECT_SETTERS;
        public static final /* enum */ Feature CAN_OVERRIDE_ACCESS_MODIFIERS;
        public static final /* enum */ Feature FAIL_ON_NULL_FOR_PRIMITIVES;
        public static final /* enum */ Feature FAIL_ON_NUMBERS_FOR_ENUMS;
        public static final /* enum */ Feature FAIL_ON_UNKNOWN_PROPERTIES;
        public static final /* enum */ Feature READ_ENUMS_USING_TO_STRING;
        public static final /* enum */ Feature UNWRAP_ROOT_VALUE;
        public static final /* enum */ Feature USE_ANNOTATIONS;
        public static final /* enum */ Feature USE_BIG_DECIMAL_FOR_FLOATS;
        public static final /* enum */ Feature USE_BIG_INTEGER_FOR_INTS;
        public static final /* enum */ Feature USE_GETTERS_AS_SETTERS;
        public static final /* enum */ Feature USE_JAVA_ARRAY_FOR_JSON_ARRAY;
        public static final /* enum */ Feature WRAP_EXCEPTIONS;
        final boolean _defaultState;

        static {
            USE_ANNOTATIONS = new Feature(true);
            AUTO_DETECT_SETTERS = new Feature(true);
            AUTO_DETECT_CREATORS = new Feature(true);
            AUTO_DETECT_FIELDS = new Feature(true);
            USE_GETTERS_AS_SETTERS = new Feature(true);
            CAN_OVERRIDE_ACCESS_MODIFIERS = new Feature(true);
            USE_BIG_DECIMAL_FOR_FLOATS = new Feature(false);
            USE_BIG_INTEGER_FOR_INTS = new Feature(false);
            USE_JAVA_ARRAY_FOR_JSON_ARRAY = new Feature(false);
            READ_ENUMS_USING_TO_STRING = new Feature(false);
            FAIL_ON_UNKNOWN_PROPERTIES = new Feature(true);
            FAIL_ON_NULL_FOR_PRIMITIVES = new Feature(false);
            FAIL_ON_NUMBERS_FOR_ENUMS = new Feature(false);
            WRAP_EXCEPTIONS = new Feature(true);
            ACCEPT_SINGLE_VALUE_AS_ARRAY = new Feature(false);
            UNWRAP_ROOT_VALUE = new Feature(false);
            ACCEPT_EMPTY_STRING_AS_NULL_OBJECT = new Feature(false);
            Feature[] arrfeature = new Feature[]{USE_ANNOTATIONS, AUTO_DETECT_SETTERS, AUTO_DETECT_CREATORS, AUTO_DETECT_FIELDS, USE_GETTERS_AS_SETTERS, CAN_OVERRIDE_ACCESS_MODIFIERS, USE_BIG_DECIMAL_FOR_FLOATS, USE_BIG_INTEGER_FOR_INTS, USE_JAVA_ARRAY_FOR_JSON_ARRAY, READ_ENUMS_USING_TO_STRING, FAIL_ON_UNKNOWN_PROPERTIES, FAIL_ON_NULL_FOR_PRIMITIVES, FAIL_ON_NUMBERS_FOR_ENUMS, WRAP_EXCEPTIONS, ACCEPT_SINGLE_VALUE_AS_ARRAY, UNWRAP_ROOT_VALUE, ACCEPT_EMPTY_STRING_AS_NULL_OBJECT};
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


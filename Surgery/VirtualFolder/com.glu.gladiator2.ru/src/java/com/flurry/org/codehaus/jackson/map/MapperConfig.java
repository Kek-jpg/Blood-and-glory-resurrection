/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.reflect.Type
 *  java.text.DateFormat
 *  java.util.HashMap
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.annotate.JsonAutoDetect;
import com.flurry.org.codehaus.jackson.annotate.JsonMethod;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.ClassIntrospector;
import com.flurry.org.codehaus.jackson.map.HandlerInstantiator;
import com.flurry.org.codehaus.jackson.map.PropertyNamingStrategy;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.VisibilityChecker;
import com.flurry.org.codehaus.jackson.map.jsontype.SubtypeResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeIdResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.StdSubtypeResolver;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import com.flurry.org.codehaus.jackson.map.type.TypeBindings;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.map.util.StdDateFormat;
import com.flurry.org.codehaus.jackson.type.JavaType;
import com.flurry.org.codehaus.jackson.type.TypeReference;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class MapperConfig<T extends MapperConfig<T>>
implements ClassIntrospector.MixInResolver {
    protected static final DateFormat DEFAULT_DATE_FORMAT = StdDateFormat.instance;
    protected Base _base;
    protected HashMap<ClassKey, Class<?>> _mixInAnnotations;
    protected boolean _mixInAnnotationsShared;
    protected SubtypeResolver _subtypeResolver;

    protected MapperConfig(ClassIntrospector<? extends BeanDescription> classIntrospector, AnnotationIntrospector annotationIntrospector, VisibilityChecker<?> visibilityChecker, SubtypeResolver subtypeResolver, PropertyNamingStrategy propertyNamingStrategy, TypeFactory typeFactory, HandlerInstantiator handlerInstantiator) {
        this._base = new Base(classIntrospector, annotationIntrospector, visibilityChecker, propertyNamingStrategy, typeFactory, null, DEFAULT_DATE_FORMAT, handlerInstantiator);
        this._subtypeResolver = subtypeResolver;
        this._mixInAnnotationsShared = true;
    }

    protected MapperConfig(MapperConfig<T> mapperConfig) {
        super(mapperConfig, mapperConfig._base, mapperConfig._subtypeResolver);
    }

    protected MapperConfig(MapperConfig<T> mapperConfig, Base base, SubtypeResolver subtypeResolver) {
        this._base = base;
        this._subtypeResolver = subtypeResolver;
        this._mixInAnnotationsShared = true;
        this._mixInAnnotations = mapperConfig._mixInAnnotations;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void addMixInAnnotations(Class<?> class_, Class<?> class_2) {
        if (this._mixInAnnotations == null) {
            this._mixInAnnotationsShared = false;
            this._mixInAnnotations = new HashMap();
        } else if (this._mixInAnnotationsShared) {
            this._mixInAnnotationsShared = false;
            this._mixInAnnotations = new HashMap(this._mixInAnnotations);
        }
        this._mixInAnnotations.put((Object)new ClassKey(class_), class_2);
    }

    @Deprecated
    public final void appendAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        this._base = this._base.withAnnotationIntrospector(AnnotationIntrospector.Pair.create(this.getAnnotationIntrospector(), annotationIntrospector));
    }

    public abstract boolean canOverrideAccessModifiers();

    public JavaType constructSpecializedType(JavaType javaType, Class<?> class_) {
        return this.getTypeFactory().constructSpecializedType(javaType, class_);
    }

    public final JavaType constructType(TypeReference<?> typeReference) {
        return this.getTypeFactory().constructType(typeReference.getType(), (TypeBindings)null);
    }

    public final JavaType constructType(Class<?> class_) {
        return this.getTypeFactory().constructType((Type)class_, (TypeBindings)null);
    }

    public abstract T createUnshared(SubtypeResolver var1);

    @Override
    public final Class<?> findMixInClassFor(Class<?> class_) {
        if (this._mixInAnnotations == null) {
            return null;
        }
        return (Class)this._mixInAnnotations.get((Object)new ClassKey(class_));
    }

    @Deprecated
    public abstract void fromAnnotations(Class<?> var1);

    public AnnotationIntrospector getAnnotationIntrospector() {
        return this._base.getAnnotationIntrospector();
    }

    public ClassIntrospector<? extends BeanDescription> getClassIntrospector() {
        return this._base.getClassIntrospector();
    }

    public final DateFormat getDateFormat() {
        return this._base.getDateFormat();
    }

    public final TypeResolverBuilder<?> getDefaultTyper(JavaType javaType) {
        return this._base.getTypeResolverBuilder();
    }

    public VisibilityChecker<?> getDefaultVisibilityChecker() {
        return this._base.getVisibilityChecker();
    }

    public final HandlerInstantiator getHandlerInstantiator() {
        return this._base.getHandlerInstantiator();
    }

    public final PropertyNamingStrategy getPropertyNamingStrategy() {
        return this._base.getPropertyNamingStrategy();
    }

    public final SubtypeResolver getSubtypeResolver() {
        if (this._subtypeResolver == null) {
            this._subtypeResolver = new StdSubtypeResolver();
        }
        return this._subtypeResolver;
    }

    public final TypeFactory getTypeFactory() {
        return this._base.getTypeFactory();
    }

    @Deprecated
    public final void insertAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        this._base = this._base.withAnnotationIntrospector(AnnotationIntrospector.Pair.create(annotationIntrospector, this.getAnnotationIntrospector()));
    }

    public abstract <DESC extends BeanDescription> DESC introspectClassAnnotations(JavaType var1);

    public <DESC extends BeanDescription> DESC introspectClassAnnotations(Class<?> class_) {
        return this.introspectClassAnnotations(this.constructType(class_));
    }

    public abstract <DESC extends BeanDescription> DESC introspectDirectClassAnnotations(JavaType var1);

    public <DESC extends BeanDescription> DESC introspectDirectClassAnnotations(Class<?> class_) {
        return this.introspectDirectClassAnnotations(this.constructType(class_));
    }

    public abstract boolean isAnnotationProcessingEnabled();

    public abstract boolean isEnabled(ConfigFeature var1);

    public final int mixInCount() {
        if (this._mixInAnnotations == null) {
            return 0;
        }
        return this._mixInAnnotations.size();
    }

    @Deprecated
    public final void setAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        this._base = this._base.withAnnotationIntrospector(annotationIntrospector);
    }

    @Deprecated
    public void setDateFormat(DateFormat dateFormat) {
        if (dateFormat == null) {
            dateFormat = DEFAULT_DATE_FORMAT;
        }
        this._base = this._base.withDateFormat(dateFormat);
    }

    public final void setMixInAnnotations(Map<Class<?>, Class<?>> map) {
        HashMap hashMap = null;
        if (map != null) {
            int n = map.size();
            hashMap = null;
            if (n > 0) {
                hashMap = new HashMap(map.size());
                for (Map.Entry entry : map.entrySet()) {
                    hashMap.put((Object)new ClassKey((Class)entry.getKey()), entry.getValue());
                }
            }
        }
        this._mixInAnnotationsShared = false;
        this._mixInAnnotations = hashMap;
    }

    public abstract boolean shouldSortPropertiesAlphabetically();

    public TypeIdResolver typeIdResolverInstance(Annotated annotated, Class<? extends TypeIdResolver> class_) {
        TypeIdResolver typeIdResolver;
        HandlerInstantiator handlerInstantiator = this.getHandlerInstantiator();
        if (handlerInstantiator != null && (typeIdResolver = handlerInstantiator.typeIdResolverInstance((MapperConfig<?>)this, annotated, class_)) != null) {
            return typeIdResolver;
        }
        return ClassUtil.createInstance(class_, this.canOverrideAccessModifiers());
    }

    public TypeResolverBuilder<?> typeResolverBuilderInstance(Annotated annotated, Class<? extends TypeResolverBuilder<?>> class_) {
        TypeResolverBuilder<?> typeResolverBuilder;
        HandlerInstantiator handlerInstantiator = this.getHandlerInstantiator();
        if (handlerInstantiator != null && (typeResolverBuilder = handlerInstantiator.typeResolverBuilderInstance((MapperConfig<?>)this, annotated, class_)) != null) {
            return typeResolverBuilder;
        }
        return ClassUtil.createInstance(class_, this.canOverrideAccessModifiers());
    }

    public abstract T withAnnotationIntrospector(AnnotationIntrospector var1);

    public abstract T withAppendedAnnotationIntrospector(AnnotationIntrospector var1);

    public abstract T withClassIntrospector(ClassIntrospector<? extends BeanDescription> var1);

    public abstract T withDateFormat(DateFormat var1);

    public abstract T withHandlerInstantiator(HandlerInstantiator var1);

    public abstract T withInsertedAnnotationIntrospector(AnnotationIntrospector var1);

    public abstract T withPropertyNamingStrategy(PropertyNamingStrategy var1);

    public abstract T withSubtypeResolver(SubtypeResolver var1);

    public abstract T withTypeFactory(TypeFactory var1);

    public abstract T withTypeResolverBuilder(TypeResolverBuilder<?> var1);

    public abstract T withVisibility(JsonMethod var1, JsonAutoDetect.Visibility var2);

    public abstract T withVisibilityChecker(VisibilityChecker<?> var1);

    public static class Base {
        protected final AnnotationIntrospector _annotationIntrospector;
        protected final ClassIntrospector<? extends BeanDescription> _classIntrospector;
        protected final DateFormat _dateFormat;
        protected final HandlerInstantiator _handlerInstantiator;
        protected final PropertyNamingStrategy _propertyNamingStrategy;
        protected final TypeFactory _typeFactory;
        protected final TypeResolverBuilder<?> _typeResolverBuilder;
        protected final VisibilityChecker<?> _visibilityChecker;

        public Base(ClassIntrospector<? extends BeanDescription> classIntrospector, AnnotationIntrospector annotationIntrospector, VisibilityChecker<?> visibilityChecker, PropertyNamingStrategy propertyNamingStrategy, TypeFactory typeFactory, TypeResolverBuilder<?> typeResolverBuilder, DateFormat dateFormat, HandlerInstantiator handlerInstantiator) {
            this._classIntrospector = classIntrospector;
            this._annotationIntrospector = annotationIntrospector;
            this._visibilityChecker = visibilityChecker;
            this._propertyNamingStrategy = propertyNamingStrategy;
            this._typeFactory = typeFactory;
            this._typeResolverBuilder = typeResolverBuilder;
            this._dateFormat = dateFormat;
            this._handlerInstantiator = handlerInstantiator;
        }

        public AnnotationIntrospector getAnnotationIntrospector() {
            return this._annotationIntrospector;
        }

        public ClassIntrospector<? extends BeanDescription> getClassIntrospector() {
            return this._classIntrospector;
        }

        public DateFormat getDateFormat() {
            return this._dateFormat;
        }

        public HandlerInstantiator getHandlerInstantiator() {
            return this._handlerInstantiator;
        }

        public PropertyNamingStrategy getPropertyNamingStrategy() {
            return this._propertyNamingStrategy;
        }

        public TypeFactory getTypeFactory() {
            return this._typeFactory;
        }

        public TypeResolverBuilder<?> getTypeResolverBuilder() {
            return this._typeResolverBuilder;
        }

        public VisibilityChecker<?> getVisibilityChecker() {
            return this._visibilityChecker;
        }

        public Base withAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
            return new Base(this._classIntrospector, annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator);
        }

        public Base withAppendedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
            return this.withAnnotationIntrospector(AnnotationIntrospector.Pair.create(this._annotationIntrospector, annotationIntrospector));
        }

        public Base withClassIntrospector(ClassIntrospector<? extends BeanDescription> classIntrospector) {
            return new Base(classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator);
        }

        public Base withDateFormat(DateFormat dateFormat) {
            return new Base(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, dateFormat, this._handlerInstantiator);
        }

        public Base withHandlerInstantiator(HandlerInstantiator handlerInstantiator) {
            return new Base(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, handlerInstantiator);
        }

        public Base withInsertedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
            return this.withAnnotationIntrospector(AnnotationIntrospector.Pair.create(annotationIntrospector, this._annotationIntrospector));
        }

        public Base withPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
            return new Base(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator);
        }

        public Base withTypeFactory(TypeFactory typeFactory) {
            return new Base(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator);
        }

        public Base withTypeResolverBuilder(TypeResolverBuilder<?> typeResolverBuilder) {
            return new Base(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, typeResolverBuilder, this._dateFormat, this._handlerInstantiator);
        }

        public Base withVisibility(JsonMethod jsonMethod, JsonAutoDetect.Visibility visibility) {
            return new Base(this._classIntrospector, this._annotationIntrospector, (VisibilityChecker<?>)this._visibilityChecker.withVisibility(jsonMethod, visibility), this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator);
        }

        public Base withVisibilityChecker(VisibilityChecker<?> visibilityChecker) {
            return new Base(this._classIntrospector, this._annotationIntrospector, visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator);
        }
    }

    public static interface ConfigFeature {
        public boolean enabledByDefault();

        public int getMask();
    }

    static abstract class Impl<CFG extends ConfigFeature, T extends Impl<CFG, T>>
    extends MapperConfig<T> {
        protected int _featureFlags;

        protected Impl(ClassIntrospector<? extends BeanDescription> classIntrospector, AnnotationIntrospector annotationIntrospector, VisibilityChecker<?> visibilityChecker, SubtypeResolver subtypeResolver, PropertyNamingStrategy propertyNamingStrategy, TypeFactory typeFactory, HandlerInstantiator handlerInstantiator, int n) {
            super(classIntrospector, annotationIntrospector, visibilityChecker, subtypeResolver, propertyNamingStrategy, typeFactory, handlerInstantiator);
            this._featureFlags = n;
        }

        protected Impl(Impl<CFG, T> impl) {
            super(impl);
            this._featureFlags = impl._featureFlags;
        }

        protected Impl(Impl<CFG, T> impl, int n) {
            super(impl);
            this._featureFlags = n;
        }

        protected Impl(Impl<CFG, T> impl, Base base, SubtypeResolver subtypeResolver) {
            super(impl, base, subtypeResolver);
            this._featureFlags = impl._featureFlags;
        }

        static <F extends Enum<F>> int collectFeatureDefaults(Class<F> class_) {
            int n = 0;
            for (Enum enum_ : (Enum[])class_.getEnumConstants()) {
                if (!((ConfigFeature)((Object)enum_)).enabledByDefault()) continue;
                n |= ((ConfigFeature)((Object)enum_)).getMask();
            }
            return n;
        }

        @Deprecated
        public void disable(CFG CFG) {
            this._featureFlags &= -1 ^ CFG.getMask();
        }

        @Deprecated
        public void enable(CFG CFG) {
            this._featureFlags |= CFG.getMask();
        }

        @Override
        public boolean isEnabled(ConfigFeature configFeature) {
            return (this._featureFlags & configFeature.getMask()) != 0;
        }

        @Deprecated
        public void set(CFG CFG, boolean bl) {
            if (bl) {
                this.enable(CFG);
                return;
            }
            this.disable(CFG);
        }

        public /* varargs */ abstract T with(CFG ... var1);

        public /* varargs */ abstract T without(CFG ... var1);
    }

}


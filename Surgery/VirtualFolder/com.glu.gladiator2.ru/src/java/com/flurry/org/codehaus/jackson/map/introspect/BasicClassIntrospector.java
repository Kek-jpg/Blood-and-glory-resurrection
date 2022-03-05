/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Method
 *  java.lang.reflect.Modifier
 *  java.util.Collection
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.ClassIntrospector;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.introspect.MethodFilter;
import com.flurry.org.codehaus.jackson.map.introspect.POJOPropertiesCollector;
import com.flurry.org.codehaus.jackson.map.type.SimpleType;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

public class BasicClassIntrospector
extends ClassIntrospector<BasicBeanDescription> {
    protected static final BasicBeanDescription BOOLEAN_DESC;
    @Deprecated
    public static final GetterMethodFilter DEFAULT_GETTER_FILTER;
    @Deprecated
    public static final SetterAndGetterMethodFilter DEFAULT_SETTER_AND_GETTER_FILTER;
    @Deprecated
    public static final SetterMethodFilter DEFAULT_SETTER_FILTER;
    protected static final BasicBeanDescription INT_DESC;
    protected static final BasicBeanDescription LONG_DESC;
    protected static final MethodFilter MINIMAL_FILTER;
    protected static final BasicBeanDescription STRING_DESC;
    public static final BasicClassIntrospector instance;

    static {
        AnnotatedClass annotatedClass = AnnotatedClass.constructWithoutSuperTypes(String.class, null, null);
        STRING_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(String.class), annotatedClass);
        AnnotatedClass annotatedClass2 = AnnotatedClass.constructWithoutSuperTypes(Boolean.TYPE, null, null);
        BOOLEAN_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Boolean.TYPE), annotatedClass2);
        AnnotatedClass annotatedClass3 = AnnotatedClass.constructWithoutSuperTypes(Integer.TYPE, null, null);
        INT_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Integer.TYPE), annotatedClass3);
        AnnotatedClass annotatedClass4 = AnnotatedClass.constructWithoutSuperTypes(Long.TYPE, null, null);
        LONG_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Long.TYPE), annotatedClass4);
        DEFAULT_GETTER_FILTER = new GetterMethodFilter(null);
        DEFAULT_SETTER_FILTER = new SetterMethodFilter();
        DEFAULT_SETTER_AND_GETTER_FILTER = new SetterAndGetterMethodFilter();
        MINIMAL_FILTER = new MinimalMethodFilter(null);
        instance = new BasicClassIntrospector();
    }

    protected BasicBeanDescription _findCachedDesc(JavaType javaType) {
        Class<?> class_ = javaType.getRawClass();
        if (class_ == String.class) {
            return STRING_DESC;
        }
        if (class_ == Boolean.TYPE) {
            return BOOLEAN_DESC;
        }
        if (class_ == Integer.TYPE) {
            return INT_DESC;
        }
        if (class_ == Long.TYPE) {
            return LONG_DESC;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public AnnotatedClass classWithCreators(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        boolean bl = mapperConfig.isAnnotationProcessingEnabled();
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        Class<?> class_ = javaType.getRawClass();
        if (!bl) {
            annotationIntrospector = null;
        }
        AnnotatedClass annotatedClass = AnnotatedClass.construct(class_, annotationIntrospector, mixInResolver);
        annotatedClass.resolveMemberMethods(MINIMAL_FILTER);
        annotatedClass.resolveCreators(true);
        return annotatedClass;
    }

    public POJOPropertiesCollector collectProperties(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver, boolean bl) {
        AnnotatedClass annotatedClass = this.classWithCreators(mapperConfig, javaType, mixInResolver);
        annotatedClass.resolveMemberMethods(MINIMAL_FILTER);
        annotatedClass.resolveFields();
        return this.constructPropertyCollector(mapperConfig, annotatedClass, javaType, bl).collect();
    }

    protected POJOPropertiesCollector constructPropertyCollector(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, JavaType javaType, boolean bl) {
        return new POJOPropertiesCollector(mapperConfig, bl, javaType, annotatedClass);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public BasicBeanDescription forClassAnnotations(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        boolean bl = mapperConfig.isAnnotationProcessingEnabled();
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        Class<?> class_ = javaType.getRawClass();
        if (bl) {
            do {
                return BasicBeanDescription.forOtherUse(mapperConfig, javaType, AnnotatedClass.construct(class_, annotationIntrospector, mixInResolver));
                break;
            } while (true);
        }
        annotationIntrospector = null;
        return BasicBeanDescription.forOtherUse(mapperConfig, javaType, AnnotatedClass.construct(class_, annotationIntrospector, mixInResolver));
    }

    @Override
    public BasicBeanDescription forCreation(DeserializationConfig deserializationConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        BasicBeanDescription basicBeanDescription = this._findCachedDesc(javaType);
        if (basicBeanDescription == null) {
            basicBeanDescription = BasicBeanDescription.forDeserialization(this.collectProperties(deserializationConfig, javaType, mixInResolver, false));
        }
        return basicBeanDescription;
    }

    @Override
    public BasicBeanDescription forDeserialization(DeserializationConfig deserializationConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        BasicBeanDescription basicBeanDescription = this._findCachedDesc(javaType);
        if (basicBeanDescription == null) {
            basicBeanDescription = BasicBeanDescription.forDeserialization(this.collectProperties(deserializationConfig, javaType, mixInResolver, false));
        }
        return basicBeanDescription;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public BasicBeanDescription forDirectClassAnnotations(MapperConfig<?> mapperConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        boolean bl = mapperConfig.isAnnotationProcessingEnabled();
        AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        Class<?> class_ = javaType.getRawClass();
        if (bl) {
            do {
                return BasicBeanDescription.forOtherUse(mapperConfig, javaType, AnnotatedClass.constructWithoutSuperTypes(class_, annotationIntrospector, mixInResolver));
                break;
            } while (true);
        }
        annotationIntrospector = null;
        return BasicBeanDescription.forOtherUse(mapperConfig, javaType, AnnotatedClass.constructWithoutSuperTypes(class_, annotationIntrospector, mixInResolver));
    }

    @Override
    public BasicBeanDescription forSerialization(SerializationConfig serializationConfig, JavaType javaType, ClassIntrospector.MixInResolver mixInResolver) {
        BasicBeanDescription basicBeanDescription = this._findCachedDesc(javaType);
        if (basicBeanDescription == null) {
            basicBeanDescription = BasicBeanDescription.forSerialization(this.collectProperties(serializationConfig, javaType, mixInResolver, true));
        }
        return basicBeanDescription;
    }

    @Deprecated
    protected MethodFilter getDeserializationMethodFilter(DeserializationConfig deserializationConfig) {
        if (deserializationConfig.isEnabled(DeserializationConfig.Feature.USE_GETTERS_AS_SETTERS)) {
            return DEFAULT_SETTER_AND_GETTER_FILTER;
        }
        return DEFAULT_SETTER_FILTER;
    }

    @Deprecated
    protected MethodFilter getSerializationMethodFilter(SerializationConfig serializationConfig) {
        return DEFAULT_GETTER_FILTER;
    }

    @Deprecated
    public static class GetterMethodFilter
    implements MethodFilter {
        private GetterMethodFilter() {
        }

        /* synthetic */ GetterMethodFilter(1 var1) {
        }

        @Override
        public boolean includeMethod(Method method) {
            return ClassUtil.hasGetterSignature(method);
        }
    }

    private static class MinimalMethodFilter
    implements MethodFilter {
        private MinimalMethodFilter() {
        }

        /* synthetic */ MinimalMethodFilter(1 var1) {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean includeMethod(Method method) {
            return !Modifier.isStatic((int)method.getModifiers()) && method.getParameterTypes().length <= 2;
        }
    }

    @Deprecated
    public static final class SetterAndGetterMethodFilter
    extends SetterMethodFilter {
        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean includeMethod(Method method) {
            block5 : {
                block4 : {
                    if (super.includeMethod(method)) break block4;
                    if (!ClassUtil.hasGetterSignature(method)) {
                        return false;
                    }
                    Class class_ = method.getReturnType();
                    if (!Collection.class.isAssignableFrom(class_) && !Map.class.isAssignableFrom(class_)) break block5;
                }
                return true;
            }
            return false;
        }
    }

    @Deprecated
    public static class SetterMethodFilter
    implements MethodFilter {
        @Override
        public boolean includeMethod(Method method) {
            if (Modifier.isStatic((int)method.getModifiers())) {
                return false;
            }
            switch (method.getParameterTypes().length) {
                default: {
                    return false;
                }
                case 1: {
                    return true;
                }
                case 2: 
            }
            return true;
        }
    }

}


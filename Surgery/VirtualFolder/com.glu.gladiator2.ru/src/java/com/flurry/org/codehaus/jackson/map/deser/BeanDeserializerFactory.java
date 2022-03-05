/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Double
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Iterable
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.Member
 *  java.lang.reflect.Type
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.AbstractTypeResolver;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.BeanPropertyDefinition;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializerFactory;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.Deserializers;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializers;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.AbstractDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.BasicDeserializerFactory;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializerBuilder;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializerModifier;
import com.flurry.org.codehaus.jackson.map.deser.SettableAnyProperty;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiators;
import com.flurry.org.codehaus.jackson.map.deser.impl.CreatorCollector;
import com.flurry.org.codehaus.jackson.map.deser.impl.CreatorProperty;
import com.flurry.org.codehaus.jackson.map.deser.std.StdKeyDeserializers;
import com.flurry.org.codehaus.jackson.map.deser.std.ThrowableDeserializer;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedParameter;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedWithParams;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.introspect.VisibilityChecker;
import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.map.type.CollectionLikeType;
import com.flurry.org.codehaus.jackson.map.type.CollectionType;
import com.flurry.org.codehaus.jackson.map.type.MapLikeType;
import com.flurry.org.codehaus.jackson.map.type.MapType;
import com.flurry.org.codehaus.jackson.map.type.TypeBindings;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.map.util.ArrayBuilders;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.map.util.EnumResolver;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanDeserializerFactory
extends BasicDeserializerFactory {
    private static final Class<?>[] INIT_CAUSE_PARAMS = new Class[]{Throwable.class};
    public static final BeanDeserializerFactory instance = new BeanDeserializerFactory(null);
    protected final DeserializerFactory.Config _factoryConfig;

    @Deprecated
    public BeanDeserializerFactory() {
        this(null);
    }

    public BeanDeserializerFactory(DeserializerFactory.Config config) {
        if (config == null) {
            config = new ConfigImpl();
        }
        this._factoryConfig = config;
    }

    private KeyDeserializer _createEnumKeyDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        BasicBeanDescription basicBeanDescription = (BasicBeanDescription)deserializationConfig.introspect(javaType);
        Class<?> class_ = javaType.getRawClass();
        EnumResolver<?> enumResolver = this.constructEnumResolver(class_, deserializationConfig);
        for (AnnotatedMethod annotatedMethod : basicBeanDescription.getFactoryMethods()) {
            if (!deserializationConfig.getAnnotationIntrospector().hasCreatorAnnotation(annotatedMethod)) continue;
            if (annotatedMethod.getParameterCount() == 1 && annotatedMethod.getRawType().isAssignableFrom(class_)) {
                if (annotatedMethod.getParameterType(0) != String.class) {
                    throw new IllegalArgumentException("Parameter #0 type for factory method (" + annotatedMethod + ") not suitable, must be java.lang.String");
                }
                if (deserializationConfig.canOverrideAccessModifiers()) {
                    ClassUtil.checkAndFixAccess(annotatedMethod.getMember());
                }
                return StdKeyDeserializers.constructEnumKeyDeserializer(enumResolver, annotatedMethod);
            }
            throw new IllegalArgumentException("Unsuitable method (" + annotatedMethod + ") decorated with @JsonCreator (for Enum type " + class_.getName() + ")");
        }
        return StdKeyDeserializers.constructEnumKeyDeserializer(enumResolver);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addDeserializerConstructors(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, VisibilityChecker<?> visibilityChecker, AnnotationIntrospector annotationIntrospector, CreatorCollector creatorCollector) throws JsonMappingException {
        int n;
        AnnotatedParameter annotatedParameter;
        int n2;
        int n3;
        AnnotatedConstructor annotatedConstructor;
        Iterator iterator = basicBeanDescription.getConstructors().iterator();
        do {
            if (!iterator.hasNext()) {
                return;
            }
            annotatedConstructor = (AnnotatedConstructor)iterator.next();
            n = annotatedConstructor.getParameterCount();
            if (n < 1) continue;
            boolean bl = annotationIntrospector.hasCreatorAnnotation(annotatedConstructor);
            boolean bl2 = visibilityChecker.isCreatorVisible(annotatedConstructor);
            if (n == 1) {
                this._handleSingleArgumentConstructor(deserializationConfig, basicBeanDescription, visibilityChecker, annotationIntrospector, creatorCollector, annotatedConstructor, bl, bl2);
                continue;
            }
            if (!bl && !bl2) continue;
            annotatedParameter = null;
            n2 = 0;
            n3 = 0;
            CreatorProperty[] arrcreatorProperty = new CreatorProperty[n];
            for (int i = 0; i < n; ++i) {
                AnnotatedParameter annotatedParameter2 = annotatedConstructor.getParameter(i);
                String string = annotatedParameter2 == null ? null : annotationIntrospector.findPropertyNameForParam(annotatedParameter2);
                Object object = annotationIntrospector.findInjectableValueId(annotatedParameter2);
                if (string != null && string.length() > 0) {
                    ++n2;
                    arrcreatorProperty[i] = this.constructCreatorProperty(deserializationConfig, basicBeanDescription, string, i, annotatedParameter2, object);
                    continue;
                }
                if (object != null) {
                    ++n3;
                    arrcreatorProperty[i] = this.constructCreatorProperty(deserializationConfig, basicBeanDescription, string, i, annotatedParameter2, object);
                    continue;
                }
                if (annotatedParameter != null) continue;
                annotatedParameter = annotatedParameter2;
            }
            if (bl || n2 > 0 || n3 > 0) {
                if (n2 + n3 != n) break;
                creatorCollector.addPropertyCreator(annotatedConstructor, arrcreatorProperty);
            }
            if (!false) continue;
            creatorCollector.addPropertyCreator(annotatedConstructor, arrcreatorProperty);
        } while (true);
        if (n2 == 0 && n3 + 1 == n) {
            throw new IllegalArgumentException("Delegated constructor with Injectables not yet supported (see [JACKSON-712]) for " + annotatedConstructor);
        }
        throw new IllegalArgumentException("Argument #" + annotatedParameter.getIndex() + " of constructor " + annotatedConstructor + " has no property name annotation; must have name when multiple-paramater constructor annotated as Creator");
    }

    protected void _addDeserializerFactoryMethods(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, VisibilityChecker<?> visibilityChecker, AnnotationIntrospector annotationIntrospector, CreatorCollector creatorCollector) throws JsonMappingException {
        for (AnnotatedMethod annotatedMethod : basicBeanDescription.getFactoryMethods()) {
            int n = annotatedMethod.getParameterCount();
            if (n < 1) continue;
            boolean bl = annotationIntrospector.hasCreatorAnnotation(annotatedMethod);
            if (n == 1) {
                AnnotatedParameter annotatedParameter = annotatedMethod.getParameter(0);
                String string = annotationIntrospector.findPropertyNameForParam(annotatedParameter);
                if (annotationIntrospector.findInjectableValueId(annotatedParameter) == null && (string == null || string.length() == 0)) {
                    this._handleSingleArgumentFactory(deserializationConfig, basicBeanDescription, visibilityChecker, annotationIntrospector, creatorCollector, annotatedMethod, bl);
                    continue;
                }
            } else if (!annotationIntrospector.hasCreatorAnnotation(annotatedMethod)) continue;
            CreatorProperty[] arrcreatorProperty = new CreatorProperty[n];
            for (int i = 0; i < n; ++i) {
                AnnotatedParameter annotatedParameter = annotatedMethod.getParameter(i);
                String string = annotationIntrospector.findPropertyNameForParam(annotatedParameter);
                Object object = annotationIntrospector.findInjectableValueId(annotatedParameter);
                if ((string == null || string.length() == 0) && object == null) {
                    throw new IllegalArgumentException("Argument #" + i + " of factory method " + annotatedMethod + " has no property name annotation; must have when multiple-paramater static method annotated as Creator");
                }
                arrcreatorProperty[i] = this.constructCreatorProperty(deserializationConfig, basicBeanDescription, string, i, annotatedParameter, object);
            }
            creatorCollector.addPropertyCreator(annotatedMethod, arrcreatorProperty);
        }
    }

    @Override
    protected JsonDeserializer<?> _findCustomArrayDeserializer(ArrayType arrayType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BeanProperty beanProperty, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        Iterator iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer<?> jsonDeserializer2 = ((Deserializers)iterator.next()).findArrayDeserializer(arrayType, deserializationConfig, deserializerProvider, beanProperty, typeDeserializer, jsonDeserializer);
            if (jsonDeserializer2 == null) continue;
            return jsonDeserializer2;
        }
        return null;
    }

    protected JsonDeserializer<Object> _findCustomBeanDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty) throws JsonMappingException {
        Iterator iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer<Object> jsonDeserializer = ((Deserializers)iterator.next()).findBeanDeserializer(javaType, deserializationConfig, deserializerProvider, basicBeanDescription, beanProperty);
            if (jsonDeserializer == null) continue;
            return jsonDeserializer;
        }
        return null;
    }

    @Override
    protected JsonDeserializer<?> _findCustomCollectionDeserializer(CollectionType collectionType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        Iterator iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer<?> jsonDeserializer2 = ((Deserializers)iterator.next()).findCollectionDeserializer(collectionType, deserializationConfig, deserializerProvider, basicBeanDescription, beanProperty, typeDeserializer, jsonDeserializer);
            if (jsonDeserializer2 == null) continue;
            return jsonDeserializer2;
        }
        return null;
    }

    @Override
    protected JsonDeserializer<?> _findCustomCollectionLikeDeserializer(CollectionLikeType collectionLikeType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        Iterator iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer<?> jsonDeserializer2 = ((Deserializers)iterator.next()).findCollectionLikeDeserializer(collectionLikeType, deserializationConfig, deserializerProvider, basicBeanDescription, beanProperty, typeDeserializer, jsonDeserializer);
            if (jsonDeserializer2 == null) continue;
            return jsonDeserializer2;
        }
        return null;
    }

    @Override
    protected JsonDeserializer<?> _findCustomEnumDeserializer(Class<?> class_, DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty) throws JsonMappingException {
        Iterator iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer<?> jsonDeserializer = ((Deserializers)iterator.next()).findEnumDeserializer(class_, deserializationConfig, basicBeanDescription, beanProperty);
            if (jsonDeserializer == null) continue;
            return jsonDeserializer;
        }
        return null;
    }

    @Override
    protected JsonDeserializer<?> _findCustomMapDeserializer(MapType mapType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        Iterator iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer<?> jsonDeserializer2 = ((Deserializers)iterator.next()).findMapDeserializer(mapType, deserializationConfig, deserializerProvider, basicBeanDescription, beanProperty, keyDeserializer, typeDeserializer, jsonDeserializer);
            if (jsonDeserializer2 == null) continue;
            return jsonDeserializer2;
        }
        return null;
    }

    @Override
    protected JsonDeserializer<?> _findCustomMapLikeDeserializer(MapLikeType mapLikeType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, KeyDeserializer keyDeserializer, TypeDeserializer typeDeserializer, JsonDeserializer<?> jsonDeserializer) throws JsonMappingException {
        Iterator iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer<?> jsonDeserializer2 = ((Deserializers)iterator.next()).findMapLikeDeserializer(mapLikeType, deserializationConfig, deserializerProvider, basicBeanDescription, beanProperty, keyDeserializer, typeDeserializer, jsonDeserializer);
            if (jsonDeserializer2 == null) continue;
            return jsonDeserializer2;
        }
        return null;
    }

    @Override
    protected JsonDeserializer<?> _findCustomTreeNodeDeserializer(Class<? extends JsonNode> class_, DeserializationConfig deserializationConfig, BeanProperty beanProperty) throws JsonMappingException {
        Iterator iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer<?> jsonDeserializer = ((Deserializers)iterator.next()).findTreeNodeDeserializer(class_, deserializationConfig, beanProperty);
            if (jsonDeserializer == null) continue;
            return jsonDeserializer;
        }
        return null;
    }

    protected boolean _handleSingleArgumentConstructor(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, VisibilityChecker<?> visibilityChecker, AnnotationIntrospector annotationIntrospector, CreatorCollector creatorCollector, AnnotatedConstructor annotatedConstructor, boolean bl, boolean bl2) throws JsonMappingException {
        AnnotatedParameter annotatedParameter = annotatedConstructor.getParameter(0);
        String string = annotationIntrospector.findPropertyNameForParam(annotatedParameter);
        Object object = annotationIntrospector.findInjectableValueId(annotatedParameter);
        if (object != null || string != null && string.length() > 0) {
            CreatorProperty[] arrcreatorProperty = new CreatorProperty[]{this.constructCreatorProperty(deserializationConfig, basicBeanDescription, string, 0, annotatedParameter, object)};
            creatorCollector.addPropertyCreator(annotatedConstructor, arrcreatorProperty);
            return true;
        }
        Class<?> class_ = annotatedConstructor.getParameterClass(0);
        if (class_ == String.class) {
            if (bl || bl2) {
                creatorCollector.addStringCreator(annotatedConstructor);
            }
            return true;
        }
        if (class_ == Integer.TYPE || class_ == Integer.class) {
            if (bl || bl2) {
                creatorCollector.addIntCreator(annotatedConstructor);
            }
            return true;
        }
        if (class_ == Long.TYPE || class_ == Long.class) {
            if (bl || bl2) {
                creatorCollector.addLongCreator(annotatedConstructor);
            }
            return true;
        }
        if (class_ == Double.TYPE || class_ == Double.class) {
            if (bl || bl2) {
                creatorCollector.addDoubleCreator(annotatedConstructor);
            }
            return true;
        }
        if (bl) {
            creatorCollector.addDelegatingCreator(annotatedConstructor);
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean _handleSingleArgumentFactory(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, VisibilityChecker<?> visibilityChecker, AnnotationIntrospector annotationIntrospector, CreatorCollector creatorCollector, AnnotatedMethod annotatedMethod, boolean bl) throws JsonMappingException {
        Class<?> class_ = annotatedMethod.getParameterClass(0);
        if (class_ == String.class) {
            if (!bl && !visibilityChecker.isCreatorVisible(annotatedMethod)) return true;
            {
                creatorCollector.addStringCreator(annotatedMethod);
            }
            return true;
        }
        if (class_ == Integer.TYPE || class_ == Integer.class) {
            if (!bl && !visibilityChecker.isCreatorVisible(annotatedMethod)) return true;
            {
                creatorCollector.addIntCreator(annotatedMethod);
                return true;
            }
        }
        if (class_ == Long.TYPE || class_ == Long.class) {
            if (!bl && !visibilityChecker.isCreatorVisible(annotatedMethod)) return true;
            {
                creatorCollector.addLongCreator(annotatedMethod);
                return true;
            }
        }
        if (class_ == Double.TYPE || class_ == Double.class) {
            if (!bl && !visibilityChecker.isCreatorVisible(annotatedMethod)) return true;
            {
                creatorCollector.addDoubleCreator(annotatedMethod);
                return true;
            }
        }
        if (class_ == Boolean.TYPE || class_ == Boolean.class) {
            if (!bl && !visibilityChecker.isCreatorVisible(annotatedMethod)) return true;
            {
                creatorCollector.addBooleanCreator(annotatedMethod);
                return true;
            }
        }
        if (!annotationIntrospector.hasCreatorAnnotation(annotatedMethod)) return false;
        {
            creatorCollector.addDelegatingCreator(annotatedMethod);
            return true;
        }
    }

    protected JavaType _mapAbstractType2(DeserializationConfig deserializationConfig, JavaType javaType) throws JsonMappingException {
        Class<?> class_ = javaType.getRawClass();
        if (this._factoryConfig.hasAbstractTypeResolvers()) {
            Iterator iterator = this._factoryConfig.abstractTypeResolvers().iterator();
            while (iterator.hasNext()) {
                JavaType javaType2 = ((AbstractTypeResolver)iterator.next()).findTypeMapping(deserializationConfig, javaType);
                if (javaType2 == null || javaType2.getRawClass() == class_) continue;
                return javaType2;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void addBeanProps(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, BeanDeserializerBuilder beanDeserializerBuilder) throws JsonMappingException {
        List<BeanPropertyDefinition> list = basicBeanDescription.findProperties();
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        Boolean bl = annotationIntrospector.findIgnoreUnknownProperties(basicBeanDescription.getClassInfo());
        if (bl != null) {
            beanDeserializerBuilder.setIgnoreUnknownProperties(bl);
        }
        HashSet<String> hashSet = ArrayBuilders.arrayToSet(annotationIntrospector.findPropertiesToIgnore(basicBeanDescription.getClassInfo()));
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            beanDeserializerBuilder.addIgnorable((String)iterator.next());
        }
        AnnotatedMethod annotatedMethod = basicBeanDescription.findAnySetter();
        Set<String> set = annotatedMethod == null ? basicBeanDescription.getIgnoredPropertyNames() : basicBeanDescription.getIgnoredPropertyNamesForDeser();
        if (set != null) {
            Iterator iterator2 = set.iterator();
            while (iterator2.hasNext()) {
                beanDeserializerBuilder.addIgnorable((String)iterator2.next());
            }
        }
        HashMap hashMap = new HashMap();
        for (BeanPropertyDefinition beanPropertyDefinition : list) {
            String string = beanPropertyDefinition.getName();
            if (hashSet.contains((Object)string)) continue;
            if (beanPropertyDefinition.hasConstructorParameter()) {
                beanDeserializerBuilder.addCreatorProperty(beanPropertyDefinition);
                continue;
            }
            if (beanPropertyDefinition.hasSetter()) {
                AnnotatedMethod annotatedMethod2 = beanPropertyDefinition.getSetter();
                if (this.isIgnorableType(deserializationConfig, basicBeanDescription, annotatedMethod2.getParameterClass(0), (Map<Class<?>, Boolean>)hashMap)) {
                    beanDeserializerBuilder.addIgnorable(string);
                    continue;
                }
                SettableBeanProperty settableBeanProperty = this.constructSettableProperty(deserializationConfig, basicBeanDescription, string, annotatedMethod2);
                if (settableBeanProperty == null) continue;
                beanDeserializerBuilder.addProperty(settableBeanProperty);
                continue;
            }
            if (!beanPropertyDefinition.hasField()) continue;
            AnnotatedField annotatedField = beanPropertyDefinition.getField();
            if (this.isIgnorableType(deserializationConfig, basicBeanDescription, annotatedField.getRawType(), (Map<Class<?>, Boolean>)hashMap)) {
                beanDeserializerBuilder.addIgnorable(string);
                continue;
            }
            SettableBeanProperty settableBeanProperty = this.constructSettableProperty(deserializationConfig, basicBeanDescription, string, annotatedField);
            if (settableBeanProperty == null) continue;
            beanDeserializerBuilder.addProperty(settableBeanProperty);
        }
        if (annotatedMethod != null) {
            beanDeserializerBuilder.setAnySetter(this.constructAnySetter(deserializationConfig, basicBeanDescription, annotatedMethod));
        }
        if (deserializationConfig.isEnabled(DeserializationConfig.Feature.USE_GETTERS_AS_SETTERS)) {
            for (BeanPropertyDefinition beanPropertyDefinition : list) {
                String string;
                AnnotatedMethod annotatedMethod3;
                Class<?> class_;
                if (!beanPropertyDefinition.hasGetter() || beanDeserializerBuilder.hasProperty(string = beanPropertyDefinition.getName()) || hashSet.contains((Object)string) || !Collection.class.isAssignableFrom(class_ = (annotatedMethod3 = beanPropertyDefinition.getGetter()).getRawType()) && !Map.class.isAssignableFrom(class_) || hashSet.contains((Object)string) || beanDeserializerBuilder.hasProperty(string)) continue;
                beanDeserializerBuilder.addProperty(this.constructSetterlessProperty(deserializationConfig, basicBeanDescription, string, annotatedMethod3));
            }
        }
    }

    protected void addInjectables(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, BeanDeserializerBuilder beanDeserializerBuilder) throws JsonMappingException {
        Map<Object, AnnotatedMember> map = basicBeanDescription.findInjectables();
        if (map != null) {
            boolean bl = deserializationConfig.isEnabled(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS);
            for (Map.Entry entry : map.entrySet()) {
                AnnotatedMember annotatedMember = (AnnotatedMember)entry.getValue();
                if (bl) {
                    annotatedMember.fixAccess();
                }
                beanDeserializerBuilder.addInjectable(annotatedMember.getName(), basicBeanDescription.resolveType(annotatedMember.getGenericType()), basicBeanDescription.getClassAnnotations(), annotatedMember, entry.getKey());
            }
        }
    }

    protected void addReferenceProperties(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, BeanDeserializerBuilder beanDeserializerBuilder) throws JsonMappingException {
        Map<String, AnnotatedMember> map = basicBeanDescription.findBackReferenceProperties();
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                String string = (String)entry.getKey();
                AnnotatedMember annotatedMember = (AnnotatedMember)entry.getValue();
                if (annotatedMember instanceof AnnotatedMethod) {
                    beanDeserializerBuilder.addBackReferenceProperty(string, this.constructSettableProperty(deserializationConfig, basicBeanDescription, annotatedMember.getName(), (AnnotatedMethod)annotatedMember));
                    continue;
                }
                beanDeserializerBuilder.addBackReferenceProperty(string, this.constructSettableProperty(deserializationConfig, basicBeanDescription, annotatedMember.getName(), (AnnotatedField)annotatedMember));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonDeserializer<Object> buildBeanDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty) throws JsonMappingException {
        void var7_7;
        ValueInstantiator valueInstantiator = this.findValueInstantiator(deserializationConfig, basicBeanDescription);
        if (javaType.isAbstract() && !valueInstantiator.canInstantiate()) {
            AbstractDeserializer abstractDeserializer = new AbstractDeserializer(javaType);
            return var7_7;
        } else {
            BeanDeserializerBuilder beanDeserializerBuilder = this.constructBeanDeserializerBuilder(basicBeanDescription);
            beanDeserializerBuilder.setValueInstantiator(valueInstantiator);
            this.addBeanProps(deserializationConfig, basicBeanDescription, beanDeserializerBuilder);
            this.addReferenceProperties(deserializationConfig, basicBeanDescription, beanDeserializerBuilder);
            this.addInjectables(deserializationConfig, basicBeanDescription, beanDeserializerBuilder);
            if (this._factoryConfig.hasDeserializerModifiers()) {
                Iterator iterator = this._factoryConfig.deserializerModifiers().iterator();
                while (iterator.hasNext()) {
                    beanDeserializerBuilder = ((BeanDeserializerModifier)iterator.next()).updateBuilder(deserializationConfig, basicBeanDescription, beanDeserializerBuilder);
                }
            }
            JsonDeserializer<?> jsonDeserializer = beanDeserializerBuilder.build(beanProperty);
            if (!this._factoryConfig.hasDeserializerModifiers()) return var7_7;
            {
                Iterator iterator = this._factoryConfig.deserializerModifiers().iterator();
                while (iterator.hasNext()) {
                    void var7_9;
                    JsonDeserializer<?> jsonDeserializer2 = ((BeanDeserializerModifier)iterator.next()).modifyDeserializer(deserializationConfig, basicBeanDescription, (JsonDeserializer<?>)var7_9);
                }
            }
        }
        return var7_7;
    }

    public JsonDeserializer<Object> buildThrowableDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty) throws JsonMappingException {
        SettableBeanProperty settableBeanProperty;
        JsonDeserializer jsonDeserializer;
        BeanDeserializerBuilder beanDeserializerBuilder = this.constructBeanDeserializerBuilder(basicBeanDescription);
        beanDeserializerBuilder.setValueInstantiator(this.findValueInstantiator(deserializationConfig, basicBeanDescription));
        this.addBeanProps(deserializationConfig, basicBeanDescription, beanDeserializerBuilder);
        AnnotatedMethod annotatedMethod = basicBeanDescription.findMethod("initCause", INIT_CAUSE_PARAMS);
        if (annotatedMethod != null && (settableBeanProperty = this.constructSettableProperty(deserializationConfig, basicBeanDescription, "cause", annotatedMethod)) != null) {
            beanDeserializerBuilder.addOrReplaceProperty(settableBeanProperty, true);
        }
        beanDeserializerBuilder.addIgnorable("localizedMessage");
        beanDeserializerBuilder.addIgnorable("message");
        if (this._factoryConfig.hasDeserializerModifiers()) {
            Iterator iterator = this._factoryConfig.deserializerModifiers().iterator();
            while (iterator.hasNext()) {
                beanDeserializerBuilder = ((BeanDeserializerModifier)iterator.next()).updateBuilder(deserializationConfig, basicBeanDescription, beanDeserializerBuilder);
            }
        }
        if ((jsonDeserializer = beanDeserializerBuilder.build(beanProperty)) instanceof BeanDeserializer) {
            jsonDeserializer = new ThrowableDeserializer((BeanDeserializer)jsonDeserializer);
        }
        if (this._factoryConfig.hasDeserializerModifiers()) {
            Iterator iterator = this._factoryConfig.deserializerModifiers().iterator();
            while (iterator.hasNext()) {
                jsonDeserializer = ((BeanDeserializerModifier)iterator.next()).modifyDeserializer(deserializationConfig, basicBeanDescription, jsonDeserializer);
            }
        }
        return jsonDeserializer;
    }

    protected SettableAnyProperty constructAnySetter(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, AnnotatedMethod annotatedMethod) throws JsonMappingException {
        if (deserializationConfig.isEnabled(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
            annotatedMethod.fixAccess();
        }
        JavaType javaType = basicBeanDescription.bindingsForBeanType().resolveType(annotatedMethod.getParameterType(1));
        BeanProperty.Std std = new BeanProperty.Std(annotatedMethod.getName(), javaType, basicBeanDescription.getClassAnnotations(), annotatedMethod);
        JavaType javaType2 = this.resolveType(deserializationConfig, basicBeanDescription, javaType, annotatedMethod, std);
        JsonDeserializer<Object> jsonDeserializer = this.findDeserializerFromAnnotation(deserializationConfig, annotatedMethod, std);
        if (jsonDeserializer != null) {
            return new SettableAnyProperty((BeanProperty)std, annotatedMethod, javaType2, jsonDeserializer);
        }
        return new SettableAnyProperty((BeanProperty)std, annotatedMethod, this.modifyTypeByAnnotation(deserializationConfig, annotatedMethod, javaType2, std.getName()), null);
    }

    protected BeanDeserializerBuilder constructBeanDeserializerBuilder(BasicBeanDescription basicBeanDescription) {
        return new BeanDeserializerBuilder(basicBeanDescription);
    }

    protected CreatorProperty constructCreatorProperty(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, String string, int n, AnnotatedParameter annotatedParameter, Object object) throws JsonMappingException {
        BeanProperty.Std std;
        JavaType javaType = deserializationConfig.getTypeFactory().constructType(annotatedParameter.getParameterType(), basicBeanDescription.bindingsForBeanType());
        JavaType javaType2 = this.resolveType(deserializationConfig, basicBeanDescription, javaType, annotatedParameter, std = new BeanProperty.Std(string, javaType, basicBeanDescription.getClassAnnotations(), annotatedParameter));
        if (javaType2 != javaType) {
            std = std.withType(javaType2);
        }
        JsonDeserializer<Object> jsonDeserializer = this.findDeserializerFromAnnotation(deserializationConfig, annotatedParameter, std);
        JavaType javaType3 = this.modifyTypeByAnnotation(deserializationConfig, annotatedParameter, javaType2, string);
        TypeDeserializer typeDeserializer = (TypeDeserializer)javaType3.getTypeHandler();
        if (typeDeserializer == null) {
            typeDeserializer = this.findTypeDeserializer(deserializationConfig, javaType3, std);
        }
        SettableBeanProperty settableBeanProperty = new CreatorProperty(string, javaType3, typeDeserializer, basicBeanDescription.getClassAnnotations(), annotatedParameter, n, object);
        if (jsonDeserializer != null) {
            settableBeanProperty = settableBeanProperty.withValueDeserializer((JsonDeserializer)jsonDeserializer);
        }
        return settableBeanProperty;
    }

    protected ValueInstantiator constructDefaultValueInstantiator(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription) throws JsonMappingException {
        AnnotatedConstructor annotatedConstructor;
        boolean bl = deserializationConfig.isEnabled(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS);
        CreatorCollector creatorCollector = new CreatorCollector(basicBeanDescription, bl);
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        if (basicBeanDescription.getType().isConcrete() && (annotatedConstructor = basicBeanDescription.findDefaultConstructor()) != null) {
            if (bl) {
                ClassUtil.checkAndFixAccess(annotatedConstructor.getAnnotated());
            }
            creatorCollector.setDefaultConstructor(annotatedConstructor);
        }
        VisibilityChecker<?> visibilityChecker = deserializationConfig.getDefaultVisibilityChecker();
        VisibilityChecker<?> visibilityChecker2 = deserializationConfig.getAnnotationIntrospector().findAutoDetectVisibility(basicBeanDescription.getClassInfo(), visibilityChecker);
        this._addDeserializerFactoryMethods(deserializationConfig, basicBeanDescription, visibilityChecker2, annotationIntrospector, creatorCollector);
        this._addDeserializerConstructors(deserializationConfig, basicBeanDescription, visibilityChecker2, annotationIntrospector, creatorCollector);
        return creatorCollector.constructValueInstantiator(deserializationConfig);
    }

    protected SettableBeanProperty constructSettableProperty(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, String string, AnnotatedField annotatedField) throws JsonMappingException {
        JavaType javaType;
        BeanProperty.Std std;
        JavaType javaType2;
        AnnotationIntrospector.ReferenceProperty referenceProperty;
        if (deserializationConfig.isEnabled(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
            annotatedField.fixAccess();
        }
        if ((javaType = this.resolveType(deserializationConfig, basicBeanDescription, javaType2 = basicBeanDescription.bindingsForBeanType().resolveType(annotatedField.getGenericType()), annotatedField, std = new BeanProperty.Std(string, javaType2, basicBeanDescription.getClassAnnotations(), annotatedField))) != javaType2) {
            std = std.withType(javaType);
        }
        JsonDeserializer<Object> jsonDeserializer = this.findDeserializerFromAnnotation(deserializationConfig, annotatedField, std);
        JavaType javaType3 = this.modifyTypeByAnnotation(deserializationConfig, annotatedField, javaType, string);
        SettableBeanProperty settableBeanProperty = new SettableBeanProperty.FieldProperty(string, javaType3, (TypeDeserializer)javaType3.getTypeHandler(), basicBeanDescription.getClassAnnotations(), annotatedField);
        if (jsonDeserializer != null) {
            settableBeanProperty = settableBeanProperty.withValueDeserializer(jsonDeserializer);
        }
        if ((referenceProperty = deserializationConfig.getAnnotationIntrospector().findReferenceType(annotatedField)) != null && referenceProperty.isManagedReference()) {
            settableBeanProperty.setManagedReferenceName(referenceProperty.getName());
        }
        return settableBeanProperty;
    }

    protected SettableBeanProperty constructSettableProperty(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, String string, AnnotatedMethod annotatedMethod) throws JsonMappingException {
        JavaType javaType;
        BeanProperty.Std std;
        JavaType javaType2;
        AnnotationIntrospector.ReferenceProperty referenceProperty;
        if (deserializationConfig.isEnabled(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
            annotatedMethod.fixAccess();
        }
        if ((javaType = this.resolveType(deserializationConfig, basicBeanDescription, javaType2 = basicBeanDescription.bindingsForBeanType().resolveType(annotatedMethod.getParameterType(0)), annotatedMethod, std = new BeanProperty.Std(string, javaType2, basicBeanDescription.getClassAnnotations(), annotatedMethod))) != javaType2) {
            std = std.withType(javaType);
        }
        JsonDeserializer<Object> jsonDeserializer = this.findDeserializerFromAnnotation(deserializationConfig, annotatedMethod, std);
        JavaType javaType3 = this.modifyTypeByAnnotation(deserializationConfig, annotatedMethod, javaType, string);
        SettableBeanProperty settableBeanProperty = new SettableBeanProperty.MethodProperty(string, javaType3, (TypeDeserializer)javaType3.getTypeHandler(), basicBeanDescription.getClassAnnotations(), annotatedMethod);
        if (jsonDeserializer != null) {
            settableBeanProperty = settableBeanProperty.withValueDeserializer(jsonDeserializer);
        }
        if ((referenceProperty = deserializationConfig.getAnnotationIntrospector().findReferenceType(annotatedMethod)) != null && referenceProperty.isManagedReference()) {
            settableBeanProperty.setManagedReferenceName(referenceProperty.getName());
        }
        return settableBeanProperty;
    }

    protected SettableBeanProperty constructSetterlessProperty(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, String string, AnnotatedMethod annotatedMethod) throws JsonMappingException {
        if (deserializationConfig.isEnabled(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
            annotatedMethod.fixAccess();
        }
        JavaType javaType = annotatedMethod.getType(basicBeanDescription.bindingsForBeanType());
        JsonDeserializer<Object> jsonDeserializer = this.findDeserializerFromAnnotation(deserializationConfig, annotatedMethod, new BeanProperty.Std(string, javaType, basicBeanDescription.getClassAnnotations(), annotatedMethod));
        JavaType javaType2 = this.modifyTypeByAnnotation(deserializationConfig, annotatedMethod, javaType, string);
        SettableBeanProperty settableBeanProperty = new SettableBeanProperty.SetterlessProperty(string, javaType2, (TypeDeserializer)javaType2.getTypeHandler(), basicBeanDescription.getClassAnnotations(), annotatedMethod);
        if (jsonDeserializer != null) {
            settableBeanProperty = ((SettableBeanProperty)settableBeanProperty).withValueDeserializer(jsonDeserializer);
        }
        return settableBeanProperty;
    }

    @Override
    public JsonDeserializer<Object> createBeanDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer<Object> jsonDeserializer;
        BasicBeanDescription basicBeanDescription;
        JsonDeserializer<Object> jsonDeserializer2;
        JavaType javaType2;
        if (javaType.isAbstract()) {
            javaType = this.mapAbstractType(deserializationConfig, javaType);
        }
        if ((jsonDeserializer = this.findDeserializerFromAnnotation(deserializationConfig, (basicBeanDescription = (BasicBeanDescription)deserializationConfig.introspect(javaType)).getClassInfo(), beanProperty)) != null) {
            return jsonDeserializer;
        }
        JavaType javaType3 = this.modifyTypeByAnnotation(deserializationConfig, basicBeanDescription.getClassInfo(), javaType, null);
        if (javaType3.getRawClass() != javaType.getRawClass()) {
            javaType = javaType3;
            basicBeanDescription = (BasicBeanDescription)deserializationConfig.introspect(javaType);
        }
        if ((jsonDeserializer2 = this._findCustomBeanDeserializer(javaType, deserializationConfig, deserializerProvider, basicBeanDescription, beanProperty)) != null) {
            return jsonDeserializer2;
        }
        if (javaType.isThrowable()) {
            return this.buildThrowableDeserializer(deserializationConfig, javaType, basicBeanDescription, beanProperty);
        }
        if (javaType.isAbstract() && (javaType2 = this.materializeAbstractType(deserializationConfig, basicBeanDescription)) != null) {
            return this.buildBeanDeserializer(deserializationConfig, javaType2, (BasicBeanDescription)deserializationConfig.introspect(javaType2), beanProperty);
        }
        JsonDeserializer<Object> jsonDeserializer3 = this.findStdBeanDeserializer(deserializationConfig, deserializerProvider, javaType, beanProperty);
        if (jsonDeserializer3 != null) {
            return jsonDeserializer3;
        }
        if (!this.isPotentialBeanType(javaType.getRawClass())) {
            return null;
        }
        return this.buildBeanDeserializer(deserializationConfig, javaType, basicBeanDescription, beanProperty);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public KeyDeserializer createKeyDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        Class<?> class_;
        if (this._factoryConfig.hasKeyDeserializers()) {
            BasicBeanDescription basicBeanDescription = (BasicBeanDescription)deserializationConfig.introspectClassAnnotations(javaType.getRawClass());
            Iterator iterator = this._factoryConfig.keyDeserializers().iterator();
            while (iterator.hasNext()) {
                KeyDeserializer keyDeserializer = ((KeyDeserializers)iterator.next()).findKeyDeserializer(javaType, deserializationConfig, basicBeanDescription, beanProperty);
                if (keyDeserializer == null) continue;
                return keyDeserializer;
            }
        }
        if ((class_ = javaType.getRawClass()) == String.class) return StdKeyDeserializers.constructStringKeyDeserializer(deserializationConfig, javaType);
        if (class_ == Object.class) {
            return StdKeyDeserializers.constructStringKeyDeserializer(deserializationConfig, javaType);
        }
        KeyDeserializer keyDeserializer = (KeyDeserializer)_keyDeserializers.get((Object)javaType);
        if (keyDeserializer != null) return keyDeserializer;
        if (!javaType.isEnumType()) return StdKeyDeserializers.findStringBasedKeyDeserializer(deserializationConfig, javaType);
        return BeanDeserializerFactory.super._createEnumKeyDeserializer(deserializationConfig, javaType, beanProperty);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public ValueInstantiator findValueInstantiator(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription) throws JsonMappingException {
        ValueInstantiator valueInstantiator;
        AnnotatedClass annotatedClass = basicBeanDescription.getClassInfo();
        Object object = deserializationConfig.getAnnotationIntrospector().findValueInstantiator(annotatedClass);
        if (object != null) {
            if (object instanceof ValueInstantiator) {
                valueInstantiator = (ValueInstantiator)object;
            } else {
                if (!(object instanceof Class)) {
                    throw new IllegalStateException("Invalid value instantiator returned for type " + basicBeanDescription + ": neither a Class nor ValueInstantiator");
                }
                Class class_ = (Class)object;
                if (!ValueInstantiator.class.isAssignableFrom(class_)) {
                    throw new IllegalStateException("Invalid instantiator Class<?> returned for type " + basicBeanDescription + ": " + class_.getName() + " not a ValueInstantiator");
                }
                valueInstantiator = deserializationConfig.valueInstantiatorInstance(annotatedClass, (Class<? extends ValueInstantiator>)class_);
            }
        } else {
            valueInstantiator = this.constructDefaultValueInstantiator(deserializationConfig, basicBeanDescription);
        }
        if (this._factoryConfig.hasValueInstantiators()) {
            for (ValueInstantiators valueInstantiators : this._factoryConfig.valueInstantiators()) {
                valueInstantiator = valueInstantiators.findValueInstantiator(deserializationConfig, basicBeanDescription, valueInstantiator);
                if (valueInstantiator != null) continue;
                throw new JsonMappingException("Broken registered ValueInstantiators (of type " + valueInstantiators.getClass().getName() + "): returned null ValueInstantiator");
            }
        }
        return valueInstantiator;
    }

    @Override
    public final DeserializerFactory.Config getConfig() {
        return this._factoryConfig;
    }

    protected boolean isIgnorableType(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, Class<?> class_, Map<Class<?>, Boolean> map) {
        Boolean bl = (Boolean)map.get(class_);
        if (bl == null) {
            BasicBeanDescription basicBeanDescription2 = (BasicBeanDescription)deserializationConfig.introspectClassAnnotations(class_);
            bl = deserializationConfig.getAnnotationIntrospector().isIgnorableType(basicBeanDescription2.getClassInfo());
            if (bl == null) {
                bl = Boolean.FALSE;
            }
        }
        return bl;
    }

    protected boolean isPotentialBeanType(Class<?> class_) {
        String string = ClassUtil.canBeABeanType(class_);
        if (string != null) {
            throw new IllegalArgumentException("Can not deserialize Class " + class_.getName() + " (of type " + string + ") as a Bean");
        }
        if (ClassUtil.isProxyType(class_)) {
            throw new IllegalArgumentException("Can not deserialize Proxy class " + class_.getName() + " as a Bean");
        }
        String string2 = ClassUtil.isLocalType(class_, true);
        if (string2 != null) {
            throw new IllegalArgumentException("Can not deserialize Class " + class_.getName() + " (of type " + string2 + ") as a Bean");
        }
        return true;
    }

    @Override
    public JavaType mapAbstractType(DeserializationConfig deserializationConfig, JavaType javaType) throws JsonMappingException {
        JavaType javaType2;
        while ((javaType2 = this._mapAbstractType2(deserializationConfig, javaType)) != null) {
            Class<?> class_;
            Class<?> class_2 = javaType.getRawClass();
            if (class_2 == (class_ = javaType2.getRawClass()) || !class_2.isAssignableFrom(class_)) {
                throw new IllegalArgumentException("Invalid abstract type resolution from " + javaType + " to " + javaType2 + ": latter is not a subtype of former");
            }
            javaType = javaType2;
        }
        return javaType;
    }

    protected JavaType materializeAbstractType(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription) throws JsonMappingException {
        JavaType javaType = basicBeanDescription.getType();
        Iterator iterator = this._factoryConfig.abstractTypeResolvers().iterator();
        while (iterator.hasNext()) {
            JavaType javaType2 = ((AbstractTypeResolver)iterator.next()).resolveAbstractType(deserializationConfig, javaType);
            if (javaType2 == null) continue;
            return javaType2;
        }
        return null;
    }

    @Override
    public DeserializerFactory withConfig(DeserializerFactory.Config config) {
        if (this._factoryConfig == config) {
            return this;
        }
        if (this.getClass() != BeanDeserializerFactory.class) {
            throw new IllegalStateException("Subtype of BeanDeserializerFactory (" + this.getClass().getName() + ") has not properly overridden method 'withAdditionalDeserializers': can not instantiate subtype with " + "additional deserializer definitions");
        }
        return new BeanDeserializerFactory(config);
    }

    public static class ConfigImpl
    extends DeserializerFactory.Config {
        protected static final AbstractTypeResolver[] NO_ABSTRACT_TYPE_RESOLVERS;
        protected static final KeyDeserializers[] NO_KEY_DESERIALIZERS;
        protected static final BeanDeserializerModifier[] NO_MODIFIERS;
        protected static final ValueInstantiators[] NO_VALUE_INSTANTIATORS;
        protected final AbstractTypeResolver[] _abstractTypeResolvers;
        protected final Deserializers[] _additionalDeserializers;
        protected final KeyDeserializers[] _additionalKeyDeserializers;
        protected final BeanDeserializerModifier[] _modifiers;
        protected final ValueInstantiators[] _valueInstantiators;

        static {
            NO_KEY_DESERIALIZERS = new KeyDeserializers[0];
            NO_MODIFIERS = new BeanDeserializerModifier[0];
            NO_ABSTRACT_TYPE_RESOLVERS = new AbstractTypeResolver[0];
            NO_VALUE_INSTANTIATORS = new ValueInstantiators[0];
        }

        public ConfigImpl() {
            this(null, null, null, null, null);
        }

        protected ConfigImpl(Deserializers[] arrdeserializers, KeyDeserializers[] arrkeyDeserializers, BeanDeserializerModifier[] arrbeanDeserializerModifier, AbstractTypeResolver[] arrabstractTypeResolver, ValueInstantiators[] arrvalueInstantiators) {
            if (arrdeserializers == null) {
                arrdeserializers = NO_DESERIALIZERS;
            }
            this._additionalDeserializers = arrdeserializers;
            if (arrkeyDeserializers == null) {
                arrkeyDeserializers = NO_KEY_DESERIALIZERS;
            }
            this._additionalKeyDeserializers = arrkeyDeserializers;
            if (arrbeanDeserializerModifier == null) {
                arrbeanDeserializerModifier = NO_MODIFIERS;
            }
            this._modifiers = arrbeanDeserializerModifier;
            if (arrabstractTypeResolver == null) {
                arrabstractTypeResolver = NO_ABSTRACT_TYPE_RESOLVERS;
            }
            this._abstractTypeResolvers = arrabstractTypeResolver;
            if (arrvalueInstantiators == null) {
                arrvalueInstantiators = NO_VALUE_INSTANTIATORS;
            }
            this._valueInstantiators = arrvalueInstantiators;
        }

        @Override
        public Iterable<AbstractTypeResolver> abstractTypeResolvers() {
            return ArrayBuilders.arrayAsIterable(this._abstractTypeResolvers);
        }

        @Override
        public Iterable<BeanDeserializerModifier> deserializerModifiers() {
            return ArrayBuilders.arrayAsIterable(this._modifiers);
        }

        @Override
        public Iterable<Deserializers> deserializers() {
            return ArrayBuilders.arrayAsIterable(this._additionalDeserializers);
        }

        @Override
        public boolean hasAbstractTypeResolvers() {
            return this._abstractTypeResolvers.length > 0;
        }

        @Override
        public boolean hasDeserializerModifiers() {
            return this._modifiers.length > 0;
        }

        @Override
        public boolean hasDeserializers() {
            return this._additionalDeserializers.length > 0;
        }

        @Override
        public boolean hasKeyDeserializers() {
            return this._additionalKeyDeserializers.length > 0;
        }

        @Override
        public boolean hasValueInstantiators() {
            return this._valueInstantiators.length > 0;
        }

        @Override
        public Iterable<KeyDeserializers> keyDeserializers() {
            return ArrayBuilders.arrayAsIterable(this._additionalKeyDeserializers);
        }

        @Override
        public Iterable<ValueInstantiators> valueInstantiators() {
            return ArrayBuilders.arrayAsIterable(this._valueInstantiators);
        }

        @Override
        public DeserializerFactory.Config withAbstractTypeResolver(AbstractTypeResolver abstractTypeResolver) {
            if (abstractTypeResolver == null) {
                throw new IllegalArgumentException("Can not pass null resolver");
            }
            AbstractTypeResolver[] arrabstractTypeResolver = ArrayBuilders.insertInListNoDup(this._abstractTypeResolvers, abstractTypeResolver);
            return new ConfigImpl(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, arrabstractTypeResolver, this._valueInstantiators);
        }

        @Override
        public DeserializerFactory.Config withAdditionalDeserializers(Deserializers deserializers) {
            if (deserializers == null) {
                throw new IllegalArgumentException("Can not pass null Deserializers");
            }
            return new ConfigImpl(ArrayBuilders.insertInListNoDup(this._additionalDeserializers, deserializers), this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
        }

        @Override
        public DeserializerFactory.Config withAdditionalKeyDeserializers(KeyDeserializers keyDeserializers) {
            if (keyDeserializers == null) {
                throw new IllegalArgumentException("Can not pass null KeyDeserializers");
            }
            KeyDeserializers[] arrkeyDeserializers = ArrayBuilders.insertInListNoDup(this._additionalKeyDeserializers, keyDeserializers);
            return new ConfigImpl(this._additionalDeserializers, arrkeyDeserializers, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
        }

        @Override
        public DeserializerFactory.Config withDeserializerModifier(BeanDeserializerModifier beanDeserializerModifier) {
            if (beanDeserializerModifier == null) {
                throw new IllegalArgumentException("Can not pass null modifier");
            }
            BeanDeserializerModifier[] arrbeanDeserializerModifier = ArrayBuilders.insertInListNoDup(this._modifiers, beanDeserializerModifier);
            return new ConfigImpl(this._additionalDeserializers, this._additionalKeyDeserializers, arrbeanDeserializerModifier, this._abstractTypeResolvers, this._valueInstantiators);
        }

        @Override
        public DeserializerFactory.Config withValueInstantiators(ValueInstantiators valueInstantiators) {
            if (valueInstantiators == null) {
                throw new IllegalArgumentException("Can not pass null resolver");
            }
            ValueInstantiators[] arrvalueInstantiators = ArrayBuilders.insertInListNoDup(this._valueInstantiators, valueInstantiators);
            return new ConfigImpl(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, arrvalueInstantiators);
        }
    }

}


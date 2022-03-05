/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.introspect.POJOPropertiesCollector
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Error
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.Method
 *  java.lang.reflect.Type
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Set
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.BeanPropertyDefinition;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.annotate.JsonSerialize;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedParameter;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedWithParams;
import com.flurry.org.codehaus.jackson.map.introspect.POJOPropertiesCollector;
import com.flurry.org.codehaus.jackson.map.introspect.VisibilityChecker;
import com.flurry.org.codehaus.jackson.map.type.TypeBindings;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasicBeanDescription
extends BeanDescription {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected AnnotatedMethod _anyGetterMethod;
    protected AnnotatedMethod _anySetterMethod;
    protected TypeBindings _bindings;
    protected final AnnotatedClass _classInfo;
    protected final MapperConfig<?> _config;
    protected Set<String> _ignoredPropertyNames;
    protected Set<String> _ignoredPropertyNamesForDeser;
    protected Map<Object, AnnotatedMember> _injectables;
    protected AnnotatedMethod _jsonValueMethod;
    protected final List<BeanPropertyDefinition> _properties;

    @Deprecated
    public BasicBeanDescription(MapperConfig<?> mapperConfig, JavaType javaType, AnnotatedClass annotatedClass) {
        super(mapperConfig, javaType, annotatedClass, (List<BeanPropertyDefinition>)Collections.emptyList());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected BasicBeanDescription(MapperConfig<?> mapperConfig, JavaType javaType, AnnotatedClass annotatedClass, List<BeanPropertyDefinition> list) {
        super(javaType);
        this._config = mapperConfig;
        AnnotationIntrospector annotationIntrospector = mapperConfig == null ? null : mapperConfig.getAnnotationIntrospector();
        this._annotationIntrospector = annotationIntrospector;
        this._classInfo = annotatedClass;
        this._properties = list;
    }

    public static BasicBeanDescription forDeserialization(POJOPropertiesCollector pOJOPropertiesCollector) {
        BasicBeanDescription basicBeanDescription = new BasicBeanDescription(pOJOPropertiesCollector.getConfig(), pOJOPropertiesCollector.getType(), pOJOPropertiesCollector.getClassDef(), (List<BeanPropertyDefinition>)pOJOPropertiesCollector.getProperties());
        basicBeanDescription._anySetterMethod = pOJOPropertiesCollector.getAnySetterMethod();
        basicBeanDescription._ignoredPropertyNames = pOJOPropertiesCollector.getIgnoredPropertyNames();
        basicBeanDescription._ignoredPropertyNamesForDeser = pOJOPropertiesCollector.getIgnoredPropertyNamesForDeser();
        basicBeanDescription._injectables = pOJOPropertiesCollector.getInjectables();
        return basicBeanDescription;
    }

    public static BasicBeanDescription forOtherUse(MapperConfig<?> mapperConfig, JavaType javaType, AnnotatedClass annotatedClass) {
        return new BasicBeanDescription(mapperConfig, javaType, annotatedClass, (List<BeanPropertyDefinition>)Collections.emptyList());
    }

    public static BasicBeanDescription forSerialization(POJOPropertiesCollector pOJOPropertiesCollector) {
        BasicBeanDescription basicBeanDescription = new BasicBeanDescription(pOJOPropertiesCollector.getConfig(), pOJOPropertiesCollector.getType(), pOJOPropertiesCollector.getClassDef(), (List<BeanPropertyDefinition>)pOJOPropertiesCollector.getProperties());
        basicBeanDescription._jsonValueMethod = pOJOPropertiesCollector.getJsonValueMethod();
        basicBeanDescription._anyGetterMethod = pOJOPropertiesCollector.getAnyGetterMethod();
        return basicBeanDescription;
    }

    public LinkedHashMap<String, AnnotatedField> _findPropertyFields(Collection<String> collection, boolean bl) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (BeanPropertyDefinition beanPropertyDefinition : this._properties) {
            AnnotatedField annotatedField = beanPropertyDefinition.getField();
            if (annotatedField == null) continue;
            String string = beanPropertyDefinition.getName();
            if (collection != null && collection.contains((Object)string)) continue;
            linkedHashMap.put((Object)string, (Object)annotatedField);
        }
        return linkedHashMap;
    }

    @Override
    public TypeBindings bindingsForBeanType() {
        if (this._bindings == null) {
            this._bindings = new TypeBindings(this._config.getTypeFactory(), this._type);
        }
        return this._bindings;
    }

    @Override
    public AnnotatedMethod findAnyGetter() throws IllegalArgumentException {
        if (this._anyGetterMethod != null && !Map.class.isAssignableFrom(this._anyGetterMethod.getRawType())) {
            throw new IllegalArgumentException("Invalid 'any-getter' annotation on method " + this._anyGetterMethod.getName() + "(): return type is not instance of java.util.Map");
        }
        return this._anyGetterMethod;
    }

    @Override
    public AnnotatedMethod findAnySetter() throws IllegalArgumentException {
        Class<?> class_;
        if (this._anySetterMethod != null && (class_ = this._anySetterMethod.getParameterClass(0)) != String.class && class_ != Object.class) {
            throw new IllegalArgumentException("Invalid 'any-setter' annotation on method " + this._anySetterMethod.getName() + "(): first argument not of type String or Object, but " + class_.getName());
        }
        return this._anySetterMethod;
    }

    public Map<String, AnnotatedMember> findBackReferenceProperties() {
        HashMap hashMap = null;
        Iterator iterator = this._properties.iterator();
        while (iterator.hasNext()) {
            String string;
            AnnotationIntrospector.ReferenceProperty referenceProperty;
            AnnotatedMember annotatedMember = ((BeanPropertyDefinition)iterator.next()).getMutator();
            if (annotatedMember == null || (referenceProperty = this._annotationIntrospector.findReferenceType(annotatedMember)) == null || !referenceProperty.isBackReference()) continue;
            if (hashMap == null) {
                hashMap = new HashMap();
            }
            if (hashMap.put((Object)(string = referenceProperty.getName()), (Object)annotatedMember) == null) continue;
            throw new IllegalArgumentException("Multiple back-reference properties with name '" + string + "'");
        }
        return hashMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    public List<String> findCreatorPropertyNames() {
        List list = null;
        for (int i = 0; i < 2; ++i) {
            Object object = i == 0 ? this.getConstructors() : this.getFactoryMethods();
            for (AnnotatedWithParams annotatedWithParams : object) {
                String string;
                int n = annotatedWithParams.getParameterCount();
                if (n < 1 || (string = this._annotationIntrospector.findPropertyNameForParam(annotatedWithParams.getParameter(0))) == null) continue;
                if (list == null) {
                    list = new ArrayList();
                }
                list.add((Object)string);
                for (int j = 1; j < n; ++j) {
                    list.add((Object)this._annotationIntrospector.findPropertyNameForParam(annotatedWithParams.getParameter(j)));
                }
            }
        }
        if (list != null) return list;
        return Collections.emptyList();
    }

    @Override
    public AnnotatedConstructor findDefaultConstructor() {
        return this._classInfo.getDefaultConstructor();
    }

    @Override
    public LinkedHashMap<String, AnnotatedField> findDeserializableFields(VisibilityChecker<?> visibilityChecker, Collection<String> collection) {
        return this._findPropertyFields(collection, false);
    }

    public /* varargs */ Method findFactoryMethod(Class<?> ... arrclass) {
        for (AnnotatedMethod annotatedMethod : this._classInfo.getStaticMethods()) {
            if (!this.isFactoryMethod(annotatedMethod)) continue;
            Class<?> class_ = annotatedMethod.getParameterClass(0);
            int n = arrclass.length;
            for (int i = 0; i < n; ++i) {
                if (!class_.isAssignableFrom(arrclass[i])) continue;
                return annotatedMethod.getAnnotated();
            }
        }
        return null;
    }

    @Override
    public LinkedHashMap<String, AnnotatedMethod> findGetters(VisibilityChecker<?> visibilityChecker, Collection<String> collection) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (BeanPropertyDefinition beanPropertyDefinition : this._properties) {
            AnnotatedMethod annotatedMethod = beanPropertyDefinition.getGetter();
            if (annotatedMethod == null) continue;
            String string = beanPropertyDefinition.getName();
            if (collection != null && collection.contains((Object)string)) continue;
            linkedHashMap.put((Object)string, (Object)annotatedMethod);
        }
        return linkedHashMap;
    }

    @Override
    public Map<Object, AnnotatedMember> findInjectables() {
        return this._injectables;
    }

    @Override
    public AnnotatedMethod findJsonValueMethod() {
        return this._jsonValueMethod;
    }

    public AnnotatedMethod findMethod(String string, Class<?>[] arrclass) {
        return this._classInfo.findMethod(string, arrclass);
    }

    @Override
    public List<BeanPropertyDefinition> findProperties() {
        return this._properties;
    }

    public LinkedHashMap<String, AnnotatedField> findSerializableFields(VisibilityChecker<?> visibilityChecker, Collection<String> collection) {
        return this._findPropertyFields(collection, true);
    }

    public JsonSerialize.Inclusion findSerializationInclusion(JsonSerialize.Inclusion inclusion) {
        if (this._annotationIntrospector == null) {
            return inclusion;
        }
        return this._annotationIntrospector.findSerializationInclusion(this._classInfo, inclusion);
    }

    @Override
    public LinkedHashMap<String, AnnotatedMethod> findSetters(VisibilityChecker<?> visibilityChecker) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (BeanPropertyDefinition beanPropertyDefinition : this._properties) {
            AnnotatedMethod annotatedMethod = beanPropertyDefinition.getSetter();
            if (annotatedMethod == null) continue;
            linkedHashMap.put((Object)beanPropertyDefinition.getName(), (Object)annotatedMethod);
        }
        return linkedHashMap;
    }

    public /* varargs */ Constructor<?> findSingleArgConstructor(Class<?> ... arrclass) {
        for (AnnotatedConstructor annotatedConstructor : this._classInfo.getConstructors()) {
            if (annotatedConstructor.getParameterCount() != 1) continue;
            Class<?> class_ = annotatedConstructor.getParameterClass(0);
            int n = arrclass.length;
            for (int i = 0; i < n; ++i) {
                if (arrclass[i] != class_) continue;
                return annotatedConstructor.getAnnotated();
            }
        }
        return null;
    }

    @Override
    public Annotations getClassAnnotations() {
        return this._classInfo.getAnnotations();
    }

    @Override
    public AnnotatedClass getClassInfo() {
        return this._classInfo;
    }

    public List<AnnotatedConstructor> getConstructors() {
        return this._classInfo.getConstructors();
    }

    public List<AnnotatedMethod> getFactoryMethods() {
        List<AnnotatedMethod> list = this._classInfo.getStaticMethods();
        if (list.isEmpty()) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (AnnotatedMethod annotatedMethod : list) {
            if (!this.isFactoryMethod(annotatedMethod)) continue;
            arrayList.add((Object)annotatedMethod);
        }
        return arrayList;
    }

    @Override
    public Set<String> getIgnoredPropertyNames() {
        if (this._ignoredPropertyNames == null) {
            return Collections.emptySet();
        }
        return this._ignoredPropertyNames;
    }

    public Set<String> getIgnoredPropertyNamesForDeser() {
        return this._ignoredPropertyNamesForDeser;
    }

    @Override
    public boolean hasKnownClassAnnotations() {
        return this._classInfo.hasAnnotations();
    }

    public Object instantiateBean(boolean bl) {
        AnnotatedConstructor annotatedConstructor = this._classInfo.getDefaultConstructor();
        if (annotatedConstructor == null) {
            return null;
        }
        if (bl) {
            annotatedConstructor.fixAccess();
        }
        try {
            Object object = annotatedConstructor.getAnnotated().newInstance(new Object[0]);
            return object;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            while (exception2.getCause() != null) {
                exception2 = exception2.getCause();
            }
            if (exception2 instanceof Error) {
                throw (Error)exception2;
            }
            if (exception2 instanceof RuntimeException) {
                throw (RuntimeException)exception2;
            }
            throw new IllegalArgumentException("Failed to instantiate bean of type " + this._classInfo.getAnnotated().getName() + ": (" + exception2.getClass().getName() + ") " + exception2.getMessage(), (Throwable)exception2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean isFactoryMethod(AnnotatedMethod annotatedMethod) {
        block5 : {
            block4 : {
                Class<?> class_ = annotatedMethod.getRawType();
                if (!this.getBeanClass().isAssignableFrom(class_)) break block4;
                if (this._annotationIntrospector.hasCreatorAnnotation(annotatedMethod)) {
                    return true;
                }
                if ("valueOf".equals((Object)annotatedMethod.getName())) break block5;
            }
            return false;
        }
        return true;
    }

    @Override
    public JavaType resolveType(Type type) {
        if (type == null) {
            return null;
        }
        return this.bindingsForBeanType().resolveType(type);
    }
}


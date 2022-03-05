/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethodMap
 *  com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap
 *  com.flurry.org.codehaus.jackson.map.introspect.MemberKey
 *  com.flurry.org.codehaus.jackson.map.introspect.MethodFilter
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Iterable
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.annotation.Annotation
 *  java.lang.reflect.AnnotatedElement
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.Field
 *  java.lang.reflect.Method
 *  java.lang.reflect.Modifier
 *  java.lang.reflect.Type
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.ClassIntrospector;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethodMap;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap;
import com.flurry.org.codehaus.jackson.map.introspect.MemberKey;
import com.flurry.org.codehaus.jackson.map.introspect.MethodFilter;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class AnnotatedClass
extends Annotated {
    private static final AnnotationMap[] NO_ANNOTATION_MAPS = new AnnotationMap[0];
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final Class<?> _class;
    protected AnnotationMap _classAnnotations;
    protected List<AnnotatedConstructor> _constructors;
    protected List<AnnotatedMethod> _creatorMethods;
    protected AnnotatedConstructor _defaultConstructor;
    protected List<AnnotatedField> _fields;
    protected AnnotatedMethodMap _memberMethods;
    protected final ClassIntrospector.MixInResolver _mixInResolver;
    protected final Class<?> _primaryMixIn;
    protected final List<Class<?>> _superTypes;

    /*
     * Enabled aggressive block sorting
     */
    private AnnotatedClass(Class<?> class_, List<Class<?>> list, AnnotationIntrospector annotationIntrospector, ClassIntrospector.MixInResolver mixInResolver, AnnotationMap annotationMap) {
        this._class = class_;
        this._superTypes = list;
        this._annotationIntrospector = annotationIntrospector;
        this._mixInResolver = mixInResolver;
        Class<?> class_2 = this._mixInResolver == null ? null : this._mixInResolver.findMixInClassFor(this._class);
        this._primaryMixIn = class_2;
        this._classAnnotations = annotationMap;
    }

    private AnnotationMap _emptyAnnotationMap() {
        return new AnnotationMap();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private AnnotationMap[] _emptyAnnotationMaps(int n) {
        if (n == 0) {
            return NO_ANNOTATION_MAPS;
        }
        AnnotationMap[] arrannotationMap = new AnnotationMap[n];
        int n2 = 0;
        while (n2 < n) {
            arrannotationMap[n2] = AnnotatedClass.super._emptyAnnotationMap();
            ++n2;
        }
        return arrannotationMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean _isIncludableField(Field field) {
        int n;
        return !field.isSynthetic() && !Modifier.isStatic((int)(n = field.getModifiers())) && !Modifier.isTransient((int)n);
    }

    public static AnnotatedClass construct(Class<?> class_, AnnotationIntrospector annotationIntrospector, ClassIntrospector.MixInResolver mixInResolver) {
        AnnotatedClass annotatedClass = new AnnotatedClass(class_, ClassUtil.findSuperTypes(class_, null), annotationIntrospector, mixInResolver, null);
        annotatedClass.resolveClassAnnotations();
        return annotatedClass;
    }

    public static AnnotatedClass constructWithoutSuperTypes(Class<?> class_, AnnotationIntrospector annotationIntrospector, ClassIntrospector.MixInResolver mixInResolver) {
        AnnotatedClass annotatedClass = new AnnotatedClass(class_, Collections.emptyList(), annotationIntrospector, mixInResolver, null);
        annotatedClass.resolveClassAnnotations();
        return annotatedClass;
    }

    protected void _addClassMixIns(AnnotationMap annotationMap, Class<?> class_) {
        if (this._mixInResolver != null) {
            this._addClassMixIns(annotationMap, class_, this._mixInResolver.findMixInClassFor(class_));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addClassMixIns(AnnotationMap annotationMap, Class<?> class_, Class<?> class_2) {
        if (class_2 != null) {
            for (Annotation annotation : class_2.getDeclaredAnnotations()) {
                if (!this._annotationIntrospector.isHandled(annotation)) continue;
                annotationMap.addIfNotPresent(annotation);
            }
            Iterator iterator = ClassUtil.findSuperTypes(class_2, class_).iterator();
            while (iterator.hasNext()) {
                for (Annotation annotation : ((Class)iterator.next()).getDeclaredAnnotations()) {
                    if (!this._annotationIntrospector.isHandled(annotation)) continue;
                    annotationMap.addIfNotPresent(annotation);
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addConstructorMixIns(Class<?> class_) {
        MemberKey[] arrmemberKey = null;
        int n = this._constructors == null ? 0 : this._constructors.size();
        Constructor[] arrconstructor = class_.getDeclaredConstructors();
        int n2 = arrconstructor.length;
        int n3 = 0;
        do {
            block7 : {
                Constructor constructor;
                block6 : {
                    if (n3 >= n2) {
                        return;
                    }
                    constructor = arrconstructor[n3];
                    if (constructor.getParameterTypes().length != 0) break block6;
                    if (this._defaultConstructor == null) break block7;
                    this._addMixOvers(constructor, this._defaultConstructor, false);
                    break block7;
                }
                if (arrmemberKey == null) {
                    arrmemberKey = new MemberKey[n];
                    for (int i = 0; i < n; ++i) {
                        arrmemberKey[i] = new MemberKey(((AnnotatedConstructor)this._constructors.get(i)).getAnnotated());
                    }
                }
                MemberKey memberKey = new MemberKey(constructor);
                for (int i = 0; i < n; ++i) {
                    if (!memberKey.equals((Object)arrmemberKey[i])) {
                        continue;
                    }
                    this._addMixOvers(constructor, (AnnotatedConstructor)this._constructors.get(i), true);
                    break;
                }
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addFactoryMixIns(Class<?> class_) {
        MemberKey[] arrmemberKey = null;
        int n = this._creatorMethods.size();
        Method[] arrmethod = class_.getDeclaredMethods();
        int n2 = arrmethod.length;
        int n3 = 0;
        while (n3 < n2) {
            Method method = arrmethod[n3];
            if (Modifier.isStatic((int)method.getModifiers()) && method.getParameterTypes().length != 0) {
                if (arrmemberKey == null) {
                    arrmemberKey = new MemberKey[n];
                    for (int i = 0; i < n; ++i) {
                        arrmemberKey[i] = new MemberKey(((AnnotatedMethod)this._creatorMethods.get(i)).getAnnotated());
                    }
                }
                MemberKey memberKey = new MemberKey(method);
                for (int i = 0; i < n; ++i) {
                    if (!memberKey.equals((Object)arrmemberKey[i])) {
                        continue;
                    }
                    this._addMixOvers(method, (AnnotatedMethod)this._creatorMethods.get(i), true);
                    break;
                }
            }
            ++n3;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addFieldMixIns(Class<?> class_, Map<String, AnnotatedField> map) {
        Field[] arrfield = class_.getDeclaredFields();
        int n = arrfield.length;
        int n2 = 0;
        while (n2 < n) {
            AnnotatedField annotatedField;
            Field field = arrfield[n2];
            if (AnnotatedClass.super._isIncludableField(field) && (annotatedField = (AnnotatedField)map.get((Object)field.getName())) != null) {
                for (Annotation annotation : field.getDeclaredAnnotations()) {
                    if (!this._annotationIntrospector.isHandled(annotation)) continue;
                    annotatedField.addOrOverride(annotation);
                }
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addFields(Map<String, AnnotatedField> map, Class<?> class_) {
        Class class_2 = class_.getSuperclass();
        if (class_2 != null) {
            Class<?> class_3;
            this._addFields(map, class_2);
            for (Field field : class_.getDeclaredFields()) {
                if (!AnnotatedClass.super._isIncludableField(field)) continue;
                map.put((Object)field.getName(), (Object)this._constructField(field));
            }
            if (this._mixInResolver != null && (class_3 = this._mixInResolver.findMixInClassFor(class_)) != null) {
                this._addFieldMixIns(class_3, map);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addMemberMethods(Class<?> class_, MethodFilter methodFilter, AnnotatedMethodMap annotatedMethodMap, Class<?> class_2, AnnotatedMethodMap annotatedMethodMap2) {
        if (class_2 != null) {
            this._addMethodMixIns(methodFilter, annotatedMethodMap, class_2, annotatedMethodMap2);
        }
        if (class_ != null) {
            for (Method method : class_.getDeclaredMethods()) {
                if (!this._isIncludableMethod(method, methodFilter)) continue;
                AnnotatedMethod annotatedMethod = annotatedMethodMap.find(method);
                if (annotatedMethod == null) {
                    AnnotatedMethod annotatedMethod2 = this._constructMethod(method);
                    annotatedMethodMap.add(annotatedMethod2);
                    AnnotatedMethod annotatedMethod3 = annotatedMethodMap2.remove(method);
                    if (annotatedMethod3 == null) continue;
                    this._addMixOvers(annotatedMethod3.getAnnotated(), annotatedMethod2, false);
                    continue;
                }
                this._addMixUnders(method, annotatedMethod);
                if (!annotatedMethod.getDeclaringClass().isInterface() || method.getDeclaringClass().isInterface()) continue;
                annotatedMethodMap.add(annotatedMethod.withMethod(method));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addMethodMixIns(MethodFilter methodFilter, AnnotatedMethodMap annotatedMethodMap, Class<?> class_, AnnotatedMethodMap annotatedMethodMap2) {
        Method[] arrmethod = class_.getDeclaredMethods();
        int n = arrmethod.length;
        int n2 = 0;
        while (n2 < n) {
            Method method = arrmethod[n2];
            if (this._isIncludableMethod(method, methodFilter)) {
                AnnotatedMethod annotatedMethod = annotatedMethodMap.find(method);
                if (annotatedMethod != null) {
                    this._addMixUnders(method, annotatedMethod);
                } else {
                    annotatedMethodMap2.add(this._constructMethod(method));
                }
            }
            ++n2;
        }
        return;
    }

    protected void _addMixOvers(Constructor<?> constructor, AnnotatedConstructor annotatedConstructor, boolean bl) {
        for (Annotation annotation : constructor.getDeclaredAnnotations()) {
            if (!this._annotationIntrospector.isHandled(annotation)) continue;
            annotatedConstructor.addOrOverride(annotation);
        }
        if (bl) {
            Annotation[][] arrannotation = constructor.getParameterAnnotations();
            int n = arrannotation.length;
            for (int i = 0; i < n; ++i) {
                Annotation[] arrannotation2 = arrannotation[i];
                int n2 = arrannotation2.length;
                for (int j = 0; j < n2; ++j) {
                    annotatedConstructor.addOrOverrideParam(i, arrannotation2[j]);
                }
            }
        }
    }

    protected void _addMixOvers(Method method, AnnotatedMethod annotatedMethod, boolean bl) {
        for (Annotation annotation : method.getDeclaredAnnotations()) {
            if (!this._annotationIntrospector.isHandled(annotation)) continue;
            annotatedMethod.addOrOverride(annotation);
        }
        if (bl) {
            Annotation[][] arrannotation = method.getParameterAnnotations();
            int n = arrannotation.length;
            for (int i = 0; i < n; ++i) {
                Annotation[] arrannotation2 = arrannotation[i];
                int n2 = arrannotation2.length;
                for (int j = 0; j < n2; ++j) {
                    annotatedMethod.addOrOverrideParam(i, arrannotation2[j]);
                }
            }
        }
    }

    protected void _addMixUnders(Method method, AnnotatedMethod annotatedMethod) {
        for (Annotation annotation : method.getDeclaredAnnotations()) {
            if (!this._annotationIntrospector.isHandled(annotation)) continue;
            annotatedMethod.addIfNotPresent(annotation);
        }
    }

    protected AnnotationMap _collectRelevantAnnotations(Annotation[] arrannotation) {
        AnnotationMap annotationMap = new AnnotationMap();
        if (arrannotation != null) {
            for (Annotation annotation : arrannotation) {
                if (!this._annotationIntrospector.isHandled(annotation)) continue;
                annotationMap.add(annotation);
            }
        }
        return annotationMap;
    }

    protected AnnotationMap[] _collectRelevantAnnotations(Annotation[][] arrannotation) {
        int n = arrannotation.length;
        AnnotationMap[] arrannotationMap = new AnnotationMap[n];
        for (int i = 0; i < n; ++i) {
            arrannotationMap[i] = this._collectRelevantAnnotations(arrannotation[i]);
        }
        return arrannotationMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected AnnotatedConstructor _constructConstructor(Constructor<?> constructor, boolean bl) {
        AnnotationMap[] arrannotationMap;
        if (this._annotationIntrospector == null) {
            return new AnnotatedConstructor(constructor, AnnotatedClass.super._emptyAnnotationMap(), AnnotatedClass.super._emptyAnnotationMaps(constructor.getParameterTypes().length));
        }
        if (bl) {
            return new AnnotatedConstructor(constructor, this._collectRelevantAnnotations(constructor.getDeclaredAnnotations()), null);
        }
        Annotation[][] arrannotation = constructor.getParameterAnnotations();
        int n = constructor.getParameterTypes().length;
        if (n != arrannotation.length) {
            Class class_ = constructor.getDeclaringClass();
            if (class_.isEnum() && n == 2 + arrannotation.length) {
                Annotation[][] arrannotation2 = arrannotation;
                arrannotation = new Annotation[2 + arrannotation2.length][];
                System.arraycopy((Object)arrannotation2, (int)0, (Object)arrannotation, (int)2, (int)arrannotation2.length);
                arrannotationMap = this._collectRelevantAnnotations(arrannotation);
            } else {
                boolean bl2 = class_.isMemberClass();
                arrannotationMap = null;
                if (bl2) {
                    int n2 = 1 + arrannotation.length;
                    arrannotationMap = null;
                    if (n == n2) {
                        Annotation[][] arrannotation3 = arrannotation;
                        arrannotation = new Annotation[1 + arrannotation3.length][];
                        System.arraycopy((Object)arrannotation3, (int)0, (Object)arrannotation, (int)1, (int)arrannotation3.length);
                        arrannotationMap = this._collectRelevantAnnotations(arrannotation);
                    }
                }
            }
            if (arrannotationMap != null) return new AnnotatedConstructor(constructor, this._collectRelevantAnnotations(constructor.getDeclaredAnnotations()), arrannotationMap);
            {
                throw new IllegalStateException("Internal error: constructor for " + constructor.getDeclaringClass().getName() + " has mismatch: " + n + " parameters; " + arrannotation.length + " sets of annotations");
            }
        } else {
            arrannotationMap = this._collectRelevantAnnotations(arrannotation);
        }
        return new AnnotatedConstructor(constructor, this._collectRelevantAnnotations(constructor.getDeclaredAnnotations()), arrannotationMap);
    }

    protected AnnotatedMethod _constructCreatorMethod(Method method) {
        if (this._annotationIntrospector == null) {
            return new AnnotatedMethod(method, AnnotatedClass.super._emptyAnnotationMap(), AnnotatedClass.super._emptyAnnotationMaps(method.getParameterTypes().length));
        }
        return new AnnotatedMethod(method, this._collectRelevantAnnotations(method.getDeclaredAnnotations()), this._collectRelevantAnnotations(method.getParameterAnnotations()));
    }

    protected AnnotatedField _constructField(Field field) {
        if (this._annotationIntrospector == null) {
            return new AnnotatedField(field, AnnotatedClass.super._emptyAnnotationMap());
        }
        return new AnnotatedField(field, this._collectRelevantAnnotations(field.getDeclaredAnnotations()));
    }

    protected AnnotatedMethod _constructMethod(Method method) {
        if (this._annotationIntrospector == null) {
            return new AnnotatedMethod(method, AnnotatedClass.super._emptyAnnotationMap(), null);
        }
        return new AnnotatedMethod(method, this._collectRelevantAnnotations(method.getDeclaredAnnotations()), null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean _isIncludableMethod(Method method, MethodFilter methodFilter) {
        return (methodFilter == null || methodFilter.includeMethod(method)) && !method.isSynthetic() && !method.isBridge();
    }

    public Iterable<AnnotatedField> fields() {
        if (this._fields == null) {
            return Collections.emptyList();
        }
        return this._fields;
    }

    public AnnotatedMethod findMethod(String string, Class<?>[] arrclass) {
        return this._memberMethods.find(string, arrclass);
    }

    @Override
    protected AnnotationMap getAllAnnotations() {
        return this._classAnnotations;
    }

    public Class<?> getAnnotated() {
        return this._class;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        if (this._classAnnotations == null) {
            return null;
        }
        return (A)this._classAnnotations.get(class_);
    }

    public Annotations getAnnotations() {
        return this._classAnnotations;
    }

    public List<AnnotatedConstructor> getConstructors() {
        if (this._constructors == null) {
            return Collections.emptyList();
        }
        return this._constructors;
    }

    public AnnotatedConstructor getDefaultConstructor() {
        return this._defaultConstructor;
    }

    public int getFieldCount() {
        if (this._fields == null) {
            return 0;
        }
        return this._fields.size();
    }

    @Override
    public Type getGenericType() {
        return this._class;
    }

    public int getMemberMethodCount() {
        return this._memberMethods.size();
    }

    @Override
    public int getModifiers() {
        return this._class.getModifiers();
    }

    @Override
    public String getName() {
        return this._class.getName();
    }

    @Override
    public Class<?> getRawType() {
        return this._class;
    }

    public List<AnnotatedMethod> getStaticMethods() {
        if (this._creatorMethods == null) {
            return Collections.emptyList();
        }
        return this._creatorMethods;
    }

    public boolean hasAnnotations() {
        return this._classAnnotations.size() > 0;
    }

    public Iterable<AnnotatedMethod> memberMethods() {
        return this._memberMethods;
    }

    public void resolveClassAnnotations() {
        this._classAnnotations = new AnnotationMap();
        if (this._annotationIntrospector == null) {
            return;
        }
        if (this._primaryMixIn != null) {
            this._addClassMixIns(this._classAnnotations, this._class, this._primaryMixIn);
        }
        for (Annotation annotation : this._class.getDeclaredAnnotations()) {
            if (!this._annotationIntrospector.isHandled(annotation)) continue;
            this._classAnnotations.addIfNotPresent(annotation);
        }
        for (Class class_ : this._superTypes) {
            this._addClassMixIns(this._classAnnotations, class_);
            for (Annotation annotation : class_.getDeclaredAnnotations()) {
                if (!this._annotationIntrospector.isHandled(annotation)) continue;
                this._classAnnotations.addIfNotPresent(annotation);
            }
        }
        this._addClassMixIns(this._classAnnotations, Object.class);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void resolveCreators(boolean bl) {
        this._constructors = null;
        Constructor[] arrconstructor = this._class.getDeclaredConstructors();
        int n = arrconstructor.length;
        for (int i = 0; i < n; ++i) {
            Constructor constructor = arrconstructor[i];
            if (constructor.getParameterTypes().length == 0) {
                this._defaultConstructor = this._constructConstructor(constructor, true);
                continue;
            }
            if (!bl) continue;
            if (this._constructors == null) {
                this._constructors = new ArrayList(Math.max((int)10, (int)arrconstructor.length));
            }
            this._constructors.add((Object)this._constructConstructor(constructor, false));
        }
        if (this._primaryMixIn != null && (this._defaultConstructor != null || this._constructors != null)) {
            this._addConstructorMixIns(this._primaryMixIn);
        }
        if (this._annotationIntrospector != null) {
            if (this._defaultConstructor != null && this._annotationIntrospector.isIgnorableConstructor(this._defaultConstructor)) {
                this._defaultConstructor = null;
            }
            if (this._constructors != null) {
                int n2 = this._constructors.size();
                while (--n2 >= 0) {
                    if (!this._annotationIntrospector.isIgnorableConstructor((AnnotatedConstructor)this._constructors.get(n2))) continue;
                    this._constructors.remove(n2);
                }
            }
        }
        this._creatorMethods = null;
        if (bl) {
            for (Method method : this._class.getDeclaredMethods()) {
                if (!Modifier.isStatic((int)method.getModifiers()) || method.getParameterTypes().length < 1) continue;
                if (this._creatorMethods == null) {
                    this._creatorMethods = new ArrayList(8);
                }
                this._creatorMethods.add((Object)this._constructCreatorMethod(method));
            }
            if (this._primaryMixIn != null && this._creatorMethods != null) {
                this._addFactoryMixIns(this._primaryMixIn);
            }
            if (this._annotationIntrospector != null && this._creatorMethods != null) {
                int n3 = this._creatorMethods.size();
                while (--n3 >= 0) {
                    if (!this._annotationIntrospector.isIgnorableMethod((AnnotatedMethod)this._creatorMethods.get(n3))) continue;
                    this._creatorMethods.remove(n3);
                }
            }
        }
    }

    public void resolveFields() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this._addFields((Map<String, AnnotatedField>)linkedHashMap, this._class);
        if (linkedHashMap.isEmpty()) {
            this._fields = Collections.emptyList();
            return;
        }
        this._fields = new ArrayList(linkedHashMap.size());
        this._fields.addAll(linkedHashMap.values());
    }

    @Deprecated
    public void resolveFields(boolean bl) {
        this.resolveFields();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void resolveMemberMethods(MethodFilter methodFilter) {
        Class<?> class_;
        this._memberMethods = new AnnotatedMethodMap();
        AnnotatedMethodMap annotatedMethodMap = new AnnotatedMethodMap();
        this._addMemberMethods(this._class, methodFilter, this._memberMethods, this._primaryMixIn, annotatedMethodMap);
        for (Class class_2 : this._superTypes) {
            Class<?> class_3 = this._mixInResolver == null ? null : this._mixInResolver.findMixInClassFor(class_2);
            this._addMemberMethods(class_2, methodFilter, this._memberMethods, class_3, annotatedMethodMap);
        }
        if (this._mixInResolver != null && (class_ = this._mixInResolver.findMixInClassFor(Object.class)) != null) {
            this._addMethodMixIns(methodFilter, this._memberMethods, class_, annotatedMethodMap);
        }
        if (this._annotationIntrospector != null && !annotatedMethodMap.isEmpty()) {
            for (AnnotatedMethod annotatedMethod : annotatedMethodMap) {
                try {
                    Method method = Object.class.getDeclaredMethod(annotatedMethod.getName(), annotatedMethod.getParameterClasses());
                    if (method == null) continue;
                    AnnotatedMethod annotatedMethod2 = this._constructMethod(method);
                    this._addMixOvers(annotatedMethod.getAnnotated(), annotatedMethod2, false);
                    this._memberMethods.add(annotatedMethod2);
                }
                catch (Exception exception) {}
            }
        }
    }

    @Deprecated
    public void resolveMemberMethods(MethodFilter methodFilter, boolean bl) {
        this.resolveMemberMethods(methodFilter);
    }

    public String toString() {
        return "[AnnotedClass " + this._class.getName() + "]";
    }

    @Override
    public AnnotatedClass withAnnotations(AnnotationMap annotationMap) {
        return new AnnotatedClass(this._class, this._superTypes, this._annotationIntrospector, this._mixInResolver, annotationMap);
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.LinkedList
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.TreeMap
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanPropertyDefinition;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.PropertyNamingStrategy;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedParameter;
import com.flurry.org.codehaus.jackson.map.introspect.POJOPropertyBuilder;
import com.flurry.org.codehaus.jackson.map.introspect.VisibilityChecker;
import com.flurry.org.codehaus.jackson.map.util.BeanUtil;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class POJOPropertiesCollector {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected LinkedList<AnnotatedMethod> _anyGetters = null;
    protected LinkedList<AnnotatedMethod> _anySetters = null;
    protected final AnnotatedClass _classDef;
    protected final MapperConfig<?> _config;
    protected LinkedList<POJOPropertyBuilder> _creatorProperties = null;
    protected final boolean _forSerialization;
    protected Set<String> _ignoredPropertyNames;
    protected Set<String> _ignoredPropertyNamesForDeser;
    protected LinkedHashMap<Object, AnnotatedMember> _injectables;
    protected LinkedList<AnnotatedMethod> _jsonValueGetters = null;
    protected final LinkedHashMap<String, POJOPropertyBuilder> _properties = new LinkedHashMap();
    protected final JavaType _type;
    protected final VisibilityChecker<?> _visibilityChecker;

    protected POJOPropertiesCollector(MapperConfig<?> mapperConfig, boolean bl, JavaType javaType, AnnotatedClass annotatedClass) {
        this._config = mapperConfig;
        this._forSerialization = bl;
        this._type = javaType;
        this._classDef = annotatedClass;
        boolean bl2 = mapperConfig.isAnnotationProcessingEnabled();
        AnnotationIntrospector annotationIntrospector = null;
        if (bl2) {
            annotationIntrospector = this._config.getAnnotationIntrospector();
        }
        this._annotationIntrospector = annotationIntrospector;
        if (this._annotationIntrospector == null) {
            this._visibilityChecker = this._config.getDefaultVisibilityChecker();
            return;
        }
        this._visibilityChecker = this._annotationIntrospector.findAutoDetectVisibility(annotatedClass, this._config.getDefaultVisibilityChecker());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _addIgnored(POJOPropertyBuilder pOJOPropertyBuilder) {
        String string;
        block3 : {
            block2 : {
                if (this._forSerialization) break block2;
                string = pOJOPropertyBuilder.getName();
                this._ignoredPropertyNames = POJOPropertiesCollector.super.addToSet(this._ignoredPropertyNames, string);
                if (pOJOPropertyBuilder.anyDeserializeIgnorals()) break block3;
            }
            return;
        }
        this._ignoredPropertyNamesForDeser = POJOPropertiesCollector.super.addToSet(this._ignoredPropertyNamesForDeser, string);
    }

    private Set<String> addToSet(Set<String> hashSet, String string) {
        if (hashSet == null) {
            hashSet = new HashSet();
        }
        hashSet.add((Object)string);
        return hashSet;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addCreators() {
        AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
        if (annotationIntrospector != null) {
            for (AnnotatedConstructor annotatedConstructor : this._classDef.getConstructors()) {
                if (this._creatorProperties == null) {
                    this._creatorProperties = new LinkedList();
                }
                int n = annotatedConstructor.getParameterCount();
                for (int i = 0; i < n; ++i) {
                    AnnotatedParameter annotatedParameter = annotatedConstructor.getParameter(i);
                    String string = annotationIntrospector.findPropertyNameForParam(annotatedParameter);
                    if (string == null) continue;
                    POJOPropertyBuilder pOJOPropertyBuilder = this._property(string);
                    pOJOPropertyBuilder.addCtor(annotatedParameter, string, true, false);
                    this._creatorProperties.add((Object)pOJOPropertyBuilder);
                }
            }
            for (AnnotatedMethod annotatedMethod : this._classDef.getStaticMethods()) {
                if (this._creatorProperties == null) {
                    this._creatorProperties = new LinkedList();
                }
                int n = annotatedMethod.getParameterCount();
                for (int i = 0; i < n; ++i) {
                    AnnotatedParameter annotatedParameter = annotatedMethod.getParameter(i);
                    String string = annotationIntrospector.findPropertyNameForParam(annotatedParameter);
                    if (string == null) continue;
                    POJOPropertyBuilder pOJOPropertyBuilder = this._property(string);
                    pOJOPropertyBuilder.addCtor(annotatedParameter, string, true, false);
                    this._creatorProperties.add((Object)pOJOPropertyBuilder);
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addFields() {
        AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
        Iterator iterator = this._classDef.fields().iterator();
        while (iterator.hasNext()) {
            boolean bl;
            AnnotatedField annotatedField = (AnnotatedField)iterator.next();
            String string = annotatedField.getName();
            String string2 = annotationIntrospector == null ? null : (this._forSerialization ? annotationIntrospector.findSerializablePropertyName(annotatedField) : annotationIntrospector.findDeserializablePropertyName(annotatedField));
            if ("".equals(string2)) {
                string2 = string;
            }
            if (!(bl = string2 != null)) {
                bl = this._visibilityChecker.isFieldVisible(annotatedField);
            }
            boolean bl2 = annotationIntrospector != null && annotationIntrospector.hasIgnoreMarker(annotatedField);
            this._property(string).addField(annotatedField, string2, bl, bl2);
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addInjectables() {
        AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
        if (annotationIntrospector != null) {
            for (AnnotatedField annotatedField : this._classDef.fields()) {
                this._doAddInjectable(annotationIntrospector.findInjectableValueId(annotatedField), annotatedField);
            }
            for (AnnotatedMethod annotatedMethod : this._classDef.memberMethods()) {
                if (annotatedMethod.getParameterCount() != 1) continue;
                this._doAddInjectable(annotationIntrospector.findInjectableValueId(annotatedMethod), annotatedMethod);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addMethods() {
        AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
        Iterator iterator = this._classDef.memberMethods().iterator();
        while (iterator.hasNext()) {
            AnnotatedMethod annotatedMethod = (AnnotatedMethod)iterator.next();
            int n = annotatedMethod.getParameterCount();
            if (n == 0) {
                String string;
                String string2;
                boolean bl;
                if (annotationIntrospector != null) {
                    if (annotationIntrospector.hasAnyGetterAnnotation(annotatedMethod)) {
                        if (this._anyGetters == null) {
                            this._anyGetters = new LinkedList();
                        }
                        this._anyGetters.add((Object)annotatedMethod);
                        continue;
                    }
                    if (annotationIntrospector.hasAsValueAnnotation(annotatedMethod)) {
                        if (this._jsonValueGetters == null) {
                            this._jsonValueGetters = new LinkedList();
                        }
                        this._jsonValueGetters.add((Object)annotatedMethod);
                        continue;
                    }
                }
                if ((string = annotationIntrospector == null ? null : annotationIntrospector.findGettablePropertyName(annotatedMethod)) == null) {
                    string2 = BeanUtil.okNameForRegularGetter(annotatedMethod, annotatedMethod.getName());
                    if (string2 == null) {
                        string2 = BeanUtil.okNameForIsGetter(annotatedMethod, annotatedMethod.getName());
                        if (string2 == null) continue;
                        bl = this._visibilityChecker.isIsGetterVisible(annotatedMethod);
                    } else {
                        bl = this._visibilityChecker.isGetterVisible(annotatedMethod);
                    }
                } else {
                    string2 = BeanUtil.okNameForGetter(annotatedMethod);
                    if (string2 == null) {
                        string2 = annotatedMethod.getName();
                    }
                    if (string.length() == 0) {
                        string = string2;
                    }
                    bl = true;
                }
                boolean bl2 = annotationIntrospector == null ? false : annotationIntrospector.hasIgnoreMarker(annotatedMethod);
                this._property(string2).addGetter(annotatedMethod, string, bl, bl2);
                continue;
            }
            if (n == 1) {
                String string;
                boolean bl;
                String string3 = annotationIntrospector == null ? null : annotationIntrospector.findSettablePropertyName(annotatedMethod);
                if (string3 == null) {
                    string = BeanUtil.okNameForSetter(annotatedMethod);
                    if (string == null) continue;
                    bl = this._visibilityChecker.isSetterVisible(annotatedMethod);
                } else {
                    string = BeanUtil.okNameForSetter(annotatedMethod);
                    if (string == null) {
                        string = annotatedMethod.getName();
                    }
                    if (string3.length() == 0) {
                        string3 = string;
                    }
                    bl = true;
                }
                boolean bl3 = annotationIntrospector == null ? false : annotationIntrospector.hasIgnoreMarker(annotatedMethod);
                this._property(string).addSetter(annotatedMethod, string3, bl, bl3);
                continue;
            }
            if (n != 2 || annotationIntrospector == null || !annotationIntrospector.hasAnySetterAnnotation(annotatedMethod)) continue;
            if (this._anySetters == null) {
                this._anySetters = new LinkedList();
            }
            this._anySetters.add((Object)annotatedMethod);
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _doAddInjectable(Object object, AnnotatedMember annotatedMember) {
        String string;
        if (object == null) {
            return;
        }
        if (this._injectables == null) {
            this._injectables = new LinkedHashMap();
        }
        if ((AnnotatedMember)this._injectables.put(object, (Object)annotatedMember) == null) return;
        if (object == null) {
            string = "[null]";
            throw new IllegalArgumentException("Duplicate injectable value with id '" + String.valueOf((Object)object) + "' (of type " + string + ")");
        }
        string = object.getClass().getName();
        throw new IllegalArgumentException("Duplicate injectable value with id '" + String.valueOf((Object)object) + "' (of type " + string + ")");
    }

    protected POJOPropertyBuilder _property(String string) {
        POJOPropertyBuilder pOJOPropertyBuilder = (POJOPropertyBuilder)this._properties.get((Object)string);
        if (pOJOPropertyBuilder == null) {
            pOJOPropertyBuilder = new POJOPropertyBuilder(string);
            this._properties.put((Object)string, (Object)pOJOPropertyBuilder);
        }
        return pOJOPropertyBuilder;
    }

    protected void _removeUnwantedProperties() {
        Iterator iterator = this._properties.entrySet().iterator();
        while (iterator.hasNext()) {
            POJOPropertyBuilder pOJOPropertyBuilder = (POJOPropertyBuilder)((Map.Entry)iterator.next()).getValue();
            if (!pOJOPropertyBuilder.anyVisible()) {
                iterator.remove();
                continue;
            }
            if (pOJOPropertyBuilder.anyIgnorals()) {
                this._addIgnored(pOJOPropertyBuilder);
                if (!pOJOPropertyBuilder.anyExplicitNames()) {
                    iterator.remove();
                    continue;
                }
                pOJOPropertyBuilder.removeIgnored();
            }
            pOJOPropertyBuilder.removeNonVisible();
        }
    }

    protected void _renameProperties() {
        Iterator iterator = this._properties.entrySet().iterator();
        LinkedList linkedList = null;
        while (iterator.hasNext()) {
            POJOPropertyBuilder pOJOPropertyBuilder = (POJOPropertyBuilder)((Map.Entry)iterator.next()).getValue();
            String string = pOJOPropertyBuilder.findNewName();
            if (string == null) continue;
            if (linkedList == null) {
                linkedList = new LinkedList();
            }
            linkedList.add((Object)pOJOPropertyBuilder.withName(string));
            iterator.remove();
        }
        if (linkedList != null) {
            for (POJOPropertyBuilder pOJOPropertyBuilder : linkedList) {
                String string = pOJOPropertyBuilder.getName();
                POJOPropertyBuilder pOJOPropertyBuilder2 = (POJOPropertyBuilder)this._properties.get((Object)string);
                if (pOJOPropertyBuilder2 == null) {
                    this._properties.put((Object)string, (Object)pOJOPropertyBuilder);
                    continue;
                }
                pOJOPropertyBuilder2.addAll(pOJOPropertyBuilder);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _renameUsing(PropertyNamingStrategy propertyNamingStrategy) {
        POJOPropertyBuilder[] arrpOJOPropertyBuilder = (POJOPropertyBuilder[])this._properties.values().toArray((Object[])new POJOPropertyBuilder[this._properties.size()]);
        this._properties.clear();
        int n = arrpOJOPropertyBuilder.length;
        int n2 = 0;
        while (n2 < n) {
            POJOPropertyBuilder pOJOPropertyBuilder;
            POJOPropertyBuilder pOJOPropertyBuilder2 = arrpOJOPropertyBuilder[n2];
            String string = pOJOPropertyBuilder2.getName();
            if (this._forSerialization) {
                if (pOJOPropertyBuilder2.hasGetter()) {
                    string = propertyNamingStrategy.nameForGetterMethod(this._config, pOJOPropertyBuilder2.getGetter(), string);
                } else if (pOJOPropertyBuilder2.hasField()) {
                    string = propertyNamingStrategy.nameForField(this._config, pOJOPropertyBuilder2.getField(), string);
                }
            } else if (pOJOPropertyBuilder2.hasSetter()) {
                string = propertyNamingStrategy.nameForSetterMethod(this._config, pOJOPropertyBuilder2.getSetter(), string);
            } else if (pOJOPropertyBuilder2.hasConstructorParameter()) {
                string = propertyNamingStrategy.nameForConstructorParameter(this._config, pOJOPropertyBuilder2.getConstructorParameter(), string);
            } else if (pOJOPropertyBuilder2.hasField()) {
                string = propertyNamingStrategy.nameForField(this._config, pOJOPropertyBuilder2.getField(), string);
            } else if (pOJOPropertyBuilder2.hasGetter()) {
                string = propertyNamingStrategy.nameForGetterMethod(this._config, pOJOPropertyBuilder2.getGetter(), string);
            }
            if (!string.equals((Object)pOJOPropertyBuilder2.getName())) {
                pOJOPropertyBuilder2 = pOJOPropertyBuilder2.withName(string);
            }
            if ((pOJOPropertyBuilder = (POJOPropertyBuilder)this._properties.get((Object)string)) == null) {
                this._properties.put((Object)string, (Object)pOJOPropertyBuilder2);
            } else {
                pOJOPropertyBuilder.addAll(pOJOPropertyBuilder2);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _sortProperties() {
        AnnotationIntrospector annotationIntrospector = this._config.getAnnotationIntrospector();
        Boolean bl = annotationIntrospector.findSerializationSortAlphabetically(this._classDef);
        boolean bl2 = bl == null ? this._config.shouldSortPropertiesAlphabetically() : bl.booleanValue();
        String[] arrstring = annotationIntrospector.findSerializationPropertyOrder(this._classDef);
        if (!bl2 && this._creatorProperties == null && arrstring == null) {
            return;
        }
        int n = this._properties.size();
        Object object = bl2 ? new TreeMap() : new LinkedHashMap(n + n);
        for (POJOPropertyBuilder pOJOPropertyBuilder : this._properties.values()) {
            object.put((Object)pOJOPropertyBuilder.getName(), (Object)pOJOPropertyBuilder);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(n + n);
        if (arrstring != null) {
            for (String string : arrstring) {
                POJOPropertyBuilder pOJOPropertyBuilder = (POJOPropertyBuilder)object.get((Object)string);
                if (pOJOPropertyBuilder == null) {
                    for (POJOPropertyBuilder pOJOPropertyBuilder2 : this._properties.values()) {
                        if (!string.equals((Object)pOJOPropertyBuilder2.getInternalName())) continue;
                        pOJOPropertyBuilder = pOJOPropertyBuilder2;
                        string = pOJOPropertyBuilder2.getName();
                        break;
                    }
                }
                if (pOJOPropertyBuilder == null) continue;
                linkedHashMap.put((Object)string, (Object)pOJOPropertyBuilder);
            }
        }
        if (this._creatorProperties != null) {
            for (POJOPropertyBuilder pOJOPropertyBuilder : this._creatorProperties) {
                linkedHashMap.put((Object)pOJOPropertyBuilder.getName(), (Object)pOJOPropertyBuilder);
            }
        }
        linkedHashMap.putAll((Map)object);
        this._properties.clear();
        this._properties.putAll((Map)linkedHashMap);
    }

    public POJOPropertiesCollector collect() {
        this._properties.clear();
        this._addFields();
        this._addMethods();
        this._addCreators();
        this._addInjectables();
        this._removeUnwantedProperties();
        this._renameProperties();
        PropertyNamingStrategy propertyNamingStrategy = this._config.getPropertyNamingStrategy();
        if (propertyNamingStrategy != null) {
            this._renameUsing(propertyNamingStrategy);
        }
        Iterator iterator = this._properties.values().iterator();
        while (iterator.hasNext()) {
            ((POJOPropertyBuilder)iterator.next()).trimByVisibility();
        }
        Iterator iterator2 = this._properties.values().iterator();
        while (iterator2.hasNext()) {
            ((POJOPropertyBuilder)iterator2.next()).mergeAnnotations(this._forSerialization);
        }
        this._sortProperties();
        return this;
    }

    public AnnotationIntrospector getAnnotationIntrospector() {
        return this._annotationIntrospector;
    }

    public AnnotatedMethod getAnyGetterMethod() {
        if (this._anyGetters != null) {
            if (this._anyGetters.size() > 1) {
                this.reportProblem("Multiple 'any-getters' defined (" + this._anyGetters.get(0) + " vs " + this._anyGetters.get(1) + ")");
            }
            return (AnnotatedMethod)this._anyGetters.getFirst();
        }
        return null;
    }

    public AnnotatedMethod getAnySetterMethod() {
        if (this._anySetters != null) {
            if (this._anySetters.size() > 1) {
                this.reportProblem("Multiple 'any-setters' defined (" + this._anySetters.get(0) + " vs " + this._anySetters.get(1) + ")");
            }
            return (AnnotatedMethod)this._anySetters.getFirst();
        }
        return null;
    }

    public AnnotatedClass getClassDef() {
        return this._classDef;
    }

    public MapperConfig<?> getConfig() {
        return this._config;
    }

    public Set<String> getIgnoredPropertyNames() {
        return this._ignoredPropertyNames;
    }

    public Set<String> getIgnoredPropertyNamesForDeser() {
        return this._ignoredPropertyNamesForDeser;
    }

    public Map<Object, AnnotatedMember> getInjectables() {
        return this._injectables;
    }

    public AnnotatedMethod getJsonValueMethod() {
        if (this._jsonValueGetters != null) {
            if (this._jsonValueGetters.size() > 1) {
                this.reportProblem("Multiple value properties defined (" + this._jsonValueGetters.get(0) + " vs " + this._jsonValueGetters.get(1) + ")");
            }
            return (AnnotatedMethod)this._jsonValueGetters.get(0);
        }
        return null;
    }

    public List<BeanPropertyDefinition> getProperties() {
        return new ArrayList(this._properties.values());
    }

    protected Map<String, POJOPropertyBuilder> getPropertyMap() {
        return this._properties;
    }

    public JavaType getType() {
        return this._type;
    }

    protected void reportProblem(String string) {
        throw new IllegalArgumentException("Problem with definition of " + this._classDef + ": " + string);
    }
}


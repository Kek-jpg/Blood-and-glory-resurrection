/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.AnnotatedElement
 *  java.lang.reflect.Member
 *  java.lang.reflect.Type
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.deser.impl;

import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.impl.CreatorProperty;
import com.flurry.org.codehaus.jackson.map.deser.std.StdValueInstantiator;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedWithParams;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.type.TypeBindings;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.HashMap;

public class CreatorCollector {
    final BasicBeanDescription _beanDesc;
    protected AnnotatedWithParams _booleanCreator;
    final boolean _canFixAccess;
    protected AnnotatedConstructor _defaultConstructor;
    protected AnnotatedWithParams _delegateCreator;
    protected AnnotatedWithParams _doubleCreator;
    protected AnnotatedWithParams _intCreator;
    protected AnnotatedWithParams _longCreator;
    protected CreatorProperty[] _propertyBasedArgs = null;
    protected AnnotatedWithParams _propertyBasedCreator;
    protected AnnotatedWithParams _stringCreator;

    public CreatorCollector(BasicBeanDescription basicBeanDescription, boolean bl) {
        this._beanDesc = basicBeanDescription;
        this._canFixAccess = bl;
    }

    public void addBooleanCreator(AnnotatedWithParams annotatedWithParams) {
        this._booleanCreator = this.verifyNonDup(annotatedWithParams, this._booleanCreator, "boolean");
    }

    public void addDelegatingCreator(AnnotatedWithParams annotatedWithParams) {
        this._delegateCreator = this.verifyNonDup(annotatedWithParams, this._delegateCreator, "delegate");
    }

    public void addDoubleCreator(AnnotatedWithParams annotatedWithParams) {
        this._doubleCreator = this.verifyNonDup(annotatedWithParams, this._doubleCreator, "double");
    }

    public void addIntCreator(AnnotatedWithParams annotatedWithParams) {
        this._intCreator = this.verifyNonDup(annotatedWithParams, this._intCreator, "int");
    }

    public void addLongCreator(AnnotatedWithParams annotatedWithParams) {
        this._longCreator = this.verifyNonDup(annotatedWithParams, this._longCreator, "long");
    }

    public void addPropertyCreator(AnnotatedWithParams annotatedWithParams, CreatorProperty[] arrcreatorProperty) {
        this._propertyBasedCreator = this.verifyNonDup(annotatedWithParams, this._propertyBasedCreator, "property-based");
        if (arrcreatorProperty.length > 1) {
            HashMap hashMap = new HashMap();
            int n = arrcreatorProperty.length;
            for (int i = 0; i < n; ++i) {
                String string = arrcreatorProperty[i].getName();
                Integer n2 = (Integer)hashMap.put((Object)string, (Object)i);
                if (n2 == null) continue;
                throw new IllegalArgumentException("Duplicate creator property \"" + string + "\" (index " + (Object)n2 + " vs " + i + ")");
            }
        }
        this._propertyBasedArgs = arrcreatorProperty;
    }

    public void addStringCreator(AnnotatedWithParams annotatedWithParams) {
        this._stringCreator = this.verifyNonDup(annotatedWithParams, this._stringCreator, "String");
    }

    /*
     * Enabled aggressive block sorting
     */
    public ValueInstantiator constructValueInstantiator(DeserializationConfig deserializationConfig) {
        StdValueInstantiator stdValueInstantiator = new StdValueInstantiator(deserializationConfig, this._beanDesc.getType());
        JavaType javaType = this._delegateCreator == null ? null : this._beanDesc.bindingsForBeanType().resolveType(this._delegateCreator.getParameterType(0));
        stdValueInstantiator.configureFromObjectSettings(this._defaultConstructor, this._delegateCreator, javaType, this._propertyBasedCreator, this._propertyBasedArgs);
        stdValueInstantiator.configureFromStringCreator(this._stringCreator);
        stdValueInstantiator.configureFromIntCreator(this._intCreator);
        stdValueInstantiator.configureFromLongCreator(this._longCreator);
        stdValueInstantiator.configureFromDoubleCreator(this._doubleCreator);
        stdValueInstantiator.configureFromBooleanCreator(this._booleanCreator);
        return stdValueInstantiator;
    }

    public void setDefaultConstructor(AnnotatedConstructor annotatedConstructor) {
        this._defaultConstructor = annotatedConstructor;
    }

    protected AnnotatedWithParams verifyNonDup(AnnotatedWithParams annotatedWithParams, AnnotatedWithParams annotatedWithParams2, String string) {
        if (annotatedWithParams2 != null && annotatedWithParams2.getClass() == annotatedWithParams.getClass()) {
            throw new IllegalArgumentException("Conflicting " + string + " creators: already had " + annotatedWithParams2 + ", encountered " + annotatedWithParams);
        }
        if (this._canFixAccess) {
            ClassUtil.checkAndFixAccess((Member)annotatedWithParams.getAnnotated());
        }
        return annotatedWithParams;
    }
}


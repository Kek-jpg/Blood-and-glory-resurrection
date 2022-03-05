/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Error
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Array
 *  java.lang.reflect.Field
 *  java.lang.reflect.Method
 *  java.util.Collection
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.annotate.JsonSerialize;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyWriter;
import com.flurry.org.codehaus.jackson.map.ser.BeanSerializerFactory;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.map.util.Comparators;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

public class PropertyBuilder {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final BasicBeanDescription _beanDesc;
    protected final SerializationConfig _config;
    protected Object _defaultBean;
    protected final JsonSerialize.Inclusion _outputProps;

    public PropertyBuilder(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription) {
        this._config = serializationConfig;
        this._beanDesc = basicBeanDescription;
        this._outputProps = basicBeanDescription.findSerializationInclusion(serializationConfig.getSerializationInclusion());
        this._annotationIntrospector = this._config.getAnnotationIntrospector();
    }

    protected Object _throwWrapped(Exception exception, String string, Object object) {
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
        throw new IllegalArgumentException("Failed to get property '" + string + "' of default " + object.getClass().getName() + " instance");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected BeanPropertyWriter buildWriter(String var1_4, JavaType var2_7, JsonSerializer<Object> var3_2, TypeSerializer var4_3, TypeSerializer var5_1, AnnotatedMember var6_6, boolean var7_5) {
        block14 : {
            if (var6_6 instanceof AnnotatedField) {
                var8_8 = null;
                var9_9 = ((AnnotatedField)var6_6).getAnnotated();
            } else {
                var8_8 = ((AnnotatedMethod)var6_6).getAnnotated();
                var9_9 = null;
            }
            var10_10 = this.findSerializationType(var6_6, var7_5, var2_7);
            if (var5_1 != null) {
                if (var10_10 == null) {
                    var10_10 = var2_7;
                }
                if (var10_10.getContentType() == null) {
                    throw new IllegalStateException("Problem trying to create BeanPropertyWriter for property '" + var1_4 + "' (of type " + this._beanDesc.getType() + "); serialization type " + var10_10 + " has no content");
                }
                var10_10 = var10_10.withContentTypeHandler(var5_1);
                var10_10.getContentType();
            }
            var11_11 = this._annotationIntrospector.findSerializationInclusion(var6_6, this._outputProps);
            var12_12 = false;
            var13_13 = null;
            if (var11_11 == null) break block14;
            var16_14 = 1.$SwitchMap$org$codehaus$jackson$map$annotate$JsonSerialize$Inclusion[var11_11.ordinal()];
            var13_13 = null;
            var12_12 = false;
            switch (var16_14) {
                case 1: {
                    var13_13 = this.getDefaultValue(var1_4, var8_8, var9_9);
                    if (var13_13 == null) {
                        var12_12 = true;
                        ** break;
                    }
                    var18_17 = var13_13.getClass().isArray();
                    var12_12 = false;
                    if (var18_17) {
                        var13_13 = Comparators.getArrayComparator(var13_13);
                        var12_12 = false;
                        ** break;
                    }
                    ** GOTO lbl38
                }
                case 2: {
                    var12_12 = true;
                    var13_13 = this.getEmptyValueChecker(var1_4, var2_7);
                }
lbl38: // 5 sources:
                default: {
                    break block14;
                }
                case 3: {
                    var12_12 = true;
                }
                case 4: 
            }
            var17_18 = var2_7.isContainerType();
            var13_13 = null;
            if (var17_18) {
                var13_13 = this.getContainerValueChecker(var1_4, var2_7);
            }
        }
        var14_15 = new BeanPropertyWriter(var6_6, this._beanDesc.getClassAnnotations(), var1_4, var2_7, var3_2, var4_3, var10_10, var8_8, var9_9, var12_12, var13_13);
        var15_16 = this._annotationIntrospector.shouldUnwrapProperty(var6_6);
        if (var15_16 == null) return var14_15;
        if (var15_16 == false) return var14_15;
        return var14_15.unwrappingWriter();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JavaType findSerializationType(Annotated annotated, boolean bl, JavaType javaType) {
        JsonSerialize.Typing typing;
        JavaType javaType2;
        Class<?> class_ = this._annotationIntrospector.findSerializationType(annotated);
        if (class_ != null) {
            Class<?> class_2 = javaType.getRawClass();
            if (class_.isAssignableFrom(class_2)) {
                javaType = javaType.widenBy(class_);
            } else {
                if (!class_2.isAssignableFrom(class_)) {
                    throw new IllegalArgumentException("Illegal concrete-type annotation for method '" + annotated.getName() + "': class " + class_.getName() + " not a super-type of (declared) class " + class_2.getName());
                }
                javaType = this._config.constructSpecializedType(javaType, class_);
            }
            bl = true;
        }
        if ((javaType2 = BeanSerializerFactory.modifySecondaryTypesByAnnotation(this._config, annotated, javaType)) != javaType) {
            bl = true;
            javaType = javaType2;
        }
        if (!bl && (typing = this._annotationIntrospector.findSerializationTyping(annotated)) != null) {
            if (typing != JsonSerialize.Typing.STATIC) return null;
            bl = true;
        }
        if (!bl) return null;
        return javaType;
    }

    public Annotations getClassAnnotations() {
        return this._beanDesc.getClassAnnotations();
    }

    protected Object getContainerValueChecker(String string, JavaType javaType) {
        if (!this._config.isEnabled(SerializationConfig.Feature.WRITE_EMPTY_JSON_ARRAYS)) {
            if (javaType.isArrayType()) {
                return new EmptyArrayChecker();
            }
            if (Collection.class.isAssignableFrom(javaType.getRawClass())) {
                return new EmptyCollectionChecker();
            }
        }
        return null;
    }

    protected Object getDefaultBean() {
        if (this._defaultBean == null) {
            this._defaultBean = this._beanDesc.instantiateBean(this._config.isEnabled(SerializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS));
            if (this._defaultBean == null) {
                Class<?> class_ = this._beanDesc.getClassInfo().getAnnotated();
                throw new IllegalArgumentException("Class " + class_.getName() + " has no default constructor; can not instantiate default bean value to support 'properties=JsonSerialize.Inclusion.NON_DEFAULT' annotation");
            }
        }
        return this._defaultBean;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Object getDefaultValue(String string, Method method, Field field) {
        Object object = this.getDefaultBean();
        if (method == null) return field.get(object);
        try {
            return method.invoke(object, new Object[0]);
        }
        catch (Exception exception) {
            return this._throwWrapped(exception, string, object);
        }
    }

    protected Object getEmptyValueChecker(String string, JavaType javaType) {
        Class<?> class_ = javaType.getRawClass();
        if (class_ == String.class) {
            return new EmptyStringChecker();
        }
        if (javaType.isArrayType()) {
            return new EmptyArrayChecker();
        }
        if (Collection.class.isAssignableFrom(class_)) {
            return new EmptyCollectionChecker();
        }
        if (Map.class.isAssignableFrom(class_)) {
            return new EmptyMapChecker();
        }
        return null;
    }

    public static class EmptyArrayChecker {
        public boolean equals(Object object) {
            return object == null || Array.getLength((Object)object) == 0;
        }
    }

    public static class EmptyCollectionChecker {
        public boolean equals(Object object) {
            return object == null || ((Collection)object).size() == 0;
        }
    }

    public static class EmptyMapChecker {
        public boolean equals(Object object) {
            return object == null || ((Map)object).size() == 0;
        }
    }

    public static class EmptyStringChecker {
        public boolean equals(Object object) {
            return object == null || ((String)object).length() == 0;
        }
    }

}


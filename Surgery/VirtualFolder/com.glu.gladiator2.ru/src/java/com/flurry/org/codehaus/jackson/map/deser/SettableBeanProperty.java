/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.lang.UnsupportedOperationException
 *  java.lang.annotation.Annotation
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.Field
 *  java.lang.reflect.Method
 *  java.util.Collection
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.type.JavaType;
import com.flurry.org.codehaus.jackson.util.InternCache;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

public abstract class SettableBeanProperty
implements BeanProperty {
    protected final Annotations _contextAnnotations;
    protected String _managedReferenceName;
    protected NullProvider _nullProvider;
    protected final String _propName;
    protected int _propertyIndex;
    protected final JavaType _type;
    protected JsonDeserializer<Object> _valueDeserializer;
    protected TypeDeserializer _valueTypeDeserializer;

    protected SettableBeanProperty(SettableBeanProperty settableBeanProperty) {
        this._propertyIndex = -1;
        this._propName = settableBeanProperty._propName;
        this._type = settableBeanProperty._type;
        this._contextAnnotations = settableBeanProperty._contextAnnotations;
        this._valueDeserializer = settableBeanProperty._valueDeserializer;
        this._valueTypeDeserializer = settableBeanProperty._valueTypeDeserializer;
        this._nullProvider = settableBeanProperty._nullProvider;
        this._managedReferenceName = settableBeanProperty._managedReferenceName;
        this._propertyIndex = settableBeanProperty._propertyIndex;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected SettableBeanProperty(SettableBeanProperty settableBeanProperty, JsonDeserializer<Object> jsonDeserializer) {
        this._propertyIndex = -1;
        this._propName = settableBeanProperty._propName;
        this._type = settableBeanProperty._type;
        this._contextAnnotations = settableBeanProperty._contextAnnotations;
        this._valueTypeDeserializer = settableBeanProperty._valueTypeDeserializer;
        this._managedReferenceName = settableBeanProperty._managedReferenceName;
        this._propertyIndex = settableBeanProperty._propertyIndex;
        this._valueDeserializer = jsonDeserializer;
        if (jsonDeserializer == null) {
            this._nullProvider = null;
            return;
        }
        Object object = jsonDeserializer.getNullValue();
        NullProvider nullProvider = null;
        if (object != null) {
            nullProvider = new NullProvider(this._type, object);
        }
        this._nullProvider = nullProvider;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected SettableBeanProperty(String string, JavaType javaType, TypeDeserializer typeDeserializer, Annotations annotations) {
        this._propertyIndex = -1;
        this._propName = string == null || string.length() == 0 ? "" : InternCache.instance.intern(string);
        this._type = javaType;
        this._contextAnnotations = annotations;
        this._valueTypeDeserializer = typeDeserializer;
    }

    protected IOException _throwAsIOE(Exception exception) throws IOException {
        if (exception instanceof IOException) {
            throw (IOException)((Object)exception);
        }
        if (exception instanceof RuntimeException) {
            throw (RuntimeException)exception;
        }
        Exception exception2 = exception;
        while (exception2.getCause() != null) {
            exception2 = exception2.getCause();
        }
        throw new JsonMappingException(exception2.getMessage(), null, exception2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _throwAsIOE(Exception exception, Object object) throws IOException {
        if (!(exception instanceof IllegalArgumentException)) {
            this._throwAsIOE(exception);
            return;
        }
        String string = object == null ? "[NULL]" : object.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder("Problem deserializing property '").append(this.getPropertyName());
        stringBuilder.append("' (expected type: ").append((Object)this.getType());
        stringBuilder.append("; actual type: ").append(string).append(")");
        String string2 = exception.getMessage();
        if (string2 != null) {
            stringBuilder.append(", problem: ").append(string2);
            throw new JsonMappingException(stringBuilder.toString(), null, exception);
        }
        stringBuilder.append(" (no error message provided)");
        throw new JsonMappingException(stringBuilder.toString(), null, exception);
    }

    public void assignIndex(int n) {
        if (this._propertyIndex != -1) {
            throw new IllegalStateException("Property '" + this.getName() + "' already had index (" + this._propertyIndex + "), trying to assign " + n);
        }
        this._propertyIndex = n;
    }

    public final Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
            if (this._nullProvider == null) {
                return null;
            }
            return this._nullProvider.nullValue(deserializationContext);
        }
        if (this._valueTypeDeserializer != null) {
            return this._valueDeserializer.deserializeWithType(jsonParser, deserializationContext, this._valueTypeDeserializer);
        }
        return this._valueDeserializer.deserialize(jsonParser, deserializationContext);
    }

    public abstract void deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3) throws IOException, JsonProcessingException;

    @Override
    public abstract <A extends Annotation> A getAnnotation(Class<A> var1);

    @Override
    public <A extends Annotation> A getContextAnnotation(Class<A> class_) {
        return this._contextAnnotations.get(class_);
    }

    protected final Class<?> getDeclaringClass() {
        return this.getMember().getDeclaringClass();
    }

    public Object getInjectableValueId() {
        return null;
    }

    public String getManagedReferenceName() {
        return this._managedReferenceName;
    }

    @Override
    public abstract AnnotatedMember getMember();

    @Override
    public final String getName() {
        return this._propName;
    }

    public int getPropertyIndex() {
        return this._propertyIndex;
    }

    @Deprecated
    public String getPropertyName() {
        return this._propName;
    }

    @Deprecated
    public int getProperytIndex() {
        return this.getPropertyIndex();
    }

    @Override
    public JavaType getType() {
        return this._type;
    }

    public JsonDeserializer<Object> getValueDeserializer() {
        return this._valueDeserializer;
    }

    public TypeDeserializer getValueTypeDeserializer() {
        return this._valueTypeDeserializer;
    }

    public boolean hasValueDeserializer() {
        return this._valueDeserializer != null;
    }

    public boolean hasValueTypeDeserializer() {
        return this._valueTypeDeserializer != null;
    }

    public abstract void set(Object var1, Object var2) throws IOException;

    public void setManagedReferenceName(String string) {
        this._managedReferenceName = string;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    public void setValueDeserializer(JsonDeserializer<Object> jsonDeserializer) {
        if (this._valueDeserializer != null) {
            throw new IllegalStateException("Already had assigned deserializer for property '" + this.getName() + "' (class " + this.getDeclaringClass().getName() + ")");
        }
        this._valueDeserializer = jsonDeserializer;
        Object object = this._valueDeserializer.getNullValue();
        NullProvider nullProvider = object == null ? null : new NullProvider(this._type, object);
        this._nullProvider = nullProvider;
    }

    public String toString() {
        return "[property '" + this.getName() + "']";
    }

    public abstract SettableBeanProperty withValueDeserializer(JsonDeserializer<Object> var1);

    public static final class FieldProperty
    extends SettableBeanProperty {
        protected final AnnotatedField _annotated;
        protected final Field _field;

        protected FieldProperty(FieldProperty fieldProperty, JsonDeserializer<Object> jsonDeserializer) {
            super(fieldProperty, jsonDeserializer);
            this._annotated = fieldProperty._annotated;
            this._field = fieldProperty._field;
        }

        public FieldProperty(String string, JavaType javaType, TypeDeserializer typeDeserializer, Annotations annotations, AnnotatedField annotatedField) {
            super(string, javaType, typeDeserializer, annotations);
            this._annotated = annotatedField;
            this._field = annotatedField.getAnnotated();
        }

        @Override
        public void deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
            this.set(object, this.deserialize(jsonParser, deserializationContext));
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> class_) {
            return this._annotated.getAnnotation(class_);
        }

        @Override
        public AnnotatedMember getMember() {
            return this._annotated;
        }

        @Override
        public final void set(Object object, Object object2) throws IOException {
            try {
                this._field.set(object, object2);
                return;
            }
            catch (Exception exception) {
                this._throwAsIOE(exception, object2);
                return;
            }
        }

        @Override
        public FieldProperty withValueDeserializer(JsonDeserializer<Object> jsonDeserializer) {
            return new FieldProperty((FieldProperty)this, jsonDeserializer);
        }
    }

    public static final class InnerClassProperty
    extends SettableBeanProperty {
        protected final Constructor<?> _creator;
        protected final SettableBeanProperty _delegate;

        protected InnerClassProperty(InnerClassProperty innerClassProperty, JsonDeserializer<Object> jsonDeserializer) {
            super(innerClassProperty, jsonDeserializer);
            this._delegate = innerClassProperty._delegate.withValueDeserializer(jsonDeserializer);
            this._creator = innerClassProperty._creator;
        }

        public InnerClassProperty(SettableBeanProperty settableBeanProperty, Constructor<?> constructor) {
            super(settableBeanProperty);
            this._delegate = settableBeanProperty;
            this._creator = constructor;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
            Object object2;
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
                object2 = this._nullProvider == null ? null : this._nullProvider.nullValue(deserializationContext);
            } else if (this._valueTypeDeserializer != null) {
                object2 = this._valueDeserializer.deserializeWithType(jsonParser, deserializationContext, this._valueTypeDeserializer);
            } else {
                try {
                    Object object3;
                    object2 = object3 = this._creator.newInstance(new Object[]{object});
                }
                catch (Exception exception) {
                    ClassUtil.unwrapAndThrowAsIAE(exception, "Failed to instantiate class " + this._creator.getDeclaringClass().getName() + ", problem: " + exception.getMessage());
                    object2 = null;
                }
                this._valueDeserializer.deserialize(jsonParser, deserializationContext, object2);
            }
            this.set(object, object2);
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> class_) {
            return this._delegate.getAnnotation(class_);
        }

        @Override
        public AnnotatedMember getMember() {
            return this._delegate.getMember();
        }

        @Override
        public final void set(Object object, Object object2) throws IOException {
            this._delegate.set(object, object2);
        }

        @Override
        public InnerClassProperty withValueDeserializer(JsonDeserializer<Object> jsonDeserializer) {
            return new InnerClassProperty((InnerClassProperty)this, jsonDeserializer);
        }
    }

    public static final class ManagedReferenceProperty
    extends SettableBeanProperty {
        protected final SettableBeanProperty _backProperty;
        protected final boolean _isContainer;
        protected final SettableBeanProperty _managedProperty;
        protected final String _referenceName;

        protected ManagedReferenceProperty(ManagedReferenceProperty managedReferenceProperty, JsonDeserializer<Object> jsonDeserializer) {
            super(managedReferenceProperty, jsonDeserializer);
            this._referenceName = managedReferenceProperty._referenceName;
            this._isContainer = managedReferenceProperty._isContainer;
            this._managedProperty = managedReferenceProperty._managedProperty;
            this._backProperty = managedReferenceProperty._backProperty;
        }

        public ManagedReferenceProperty(String string, SettableBeanProperty settableBeanProperty, SettableBeanProperty settableBeanProperty2, Annotations annotations, boolean bl) {
            super(settableBeanProperty.getName(), settableBeanProperty.getType(), settableBeanProperty._valueTypeDeserializer, annotations);
            this._referenceName = string;
            this._managedProperty = settableBeanProperty;
            this._backProperty = settableBeanProperty2;
            this._isContainer = bl;
        }

        @Override
        public void deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
            this.set(object, this._managedProperty.deserialize(jsonParser, deserializationContext));
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> class_) {
            return this._managedProperty.getAnnotation(class_);
        }

        @Override
        public AnnotatedMember getMember() {
            return this._managedProperty.getMember();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public final void set(Object object, Object object2) throws IOException {
            this._managedProperty.set(object, object2);
            if (object2 == null) return;
            if (this._isContainer) {
                if (object2 instanceof Object[]) {
                    for (Object object3 : (Object[])object2) {
                        if (object3 == null) continue;
                        this._backProperty.set(object3, object);
                    }
                    return;
                } else if (object2 instanceof Collection) {
                    for (Object object4 : (Collection)object2) {
                        if (object4 == null) continue;
                        this._backProperty.set(object4, object);
                    }
                    return;
                } else {
                    if (!(object2 instanceof Map)) throw new IllegalStateException("Unsupported container type (" + object2.getClass().getName() + ") when resolving reference '" + this._referenceName + "'");
                    for (Object object5 : ((Map)object2).values()) {
                        if (object5 == null) continue;
                        this._backProperty.set(object5, object);
                    }
                }
                return;
            } else {
                this._backProperty.set(object2, object);
            }
        }

        @Override
        public ManagedReferenceProperty withValueDeserializer(JsonDeserializer<Object> jsonDeserializer) {
            return new ManagedReferenceProperty((ManagedReferenceProperty)this, jsonDeserializer);
        }
    }

    public static final class MethodProperty
    extends SettableBeanProperty {
        protected final AnnotatedMethod _annotated;
        protected final Method _setter;

        protected MethodProperty(MethodProperty methodProperty, JsonDeserializer<Object> jsonDeserializer) {
            super(methodProperty, jsonDeserializer);
            this._annotated = methodProperty._annotated;
            this._setter = methodProperty._setter;
        }

        public MethodProperty(String string, JavaType javaType, TypeDeserializer typeDeserializer, Annotations annotations, AnnotatedMethod annotatedMethod) {
            super(string, javaType, typeDeserializer, annotations);
            this._annotated = annotatedMethod;
            this._setter = annotatedMethod.getAnnotated();
        }

        @Override
        public void deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
            this.set(object, this.deserialize(jsonParser, deserializationContext));
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> class_) {
            return this._annotated.getAnnotation(class_);
        }

        @Override
        public AnnotatedMember getMember() {
            return this._annotated;
        }

        @Override
        public final void set(Object object, Object object2) throws IOException {
            try {
                this._setter.invoke(object, new Object[]{object2});
                return;
            }
            catch (Exception exception) {
                this._throwAsIOE(exception, object2);
                return;
            }
        }

        @Override
        public MethodProperty withValueDeserializer(JsonDeserializer<Object> jsonDeserializer) {
            return new MethodProperty((MethodProperty)this, jsonDeserializer);
        }
    }

    protected static final class NullProvider {
        private final boolean _isPrimitive;
        private final Object _nullValue;
        private final Class<?> _rawType;

        protected NullProvider(JavaType javaType, Object object) {
            this._nullValue = object;
            this._isPrimitive = javaType.isPrimitive();
            this._rawType = javaType.getRawClass();
        }

        public Object nullValue(DeserializationContext deserializationContext) throws JsonProcessingException {
            if (this._isPrimitive && deserializationContext.isEnabled(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
                throw deserializationContext.mappingException("Can not map JSON null into type " + this._rawType.getName() + " (set DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES to 'false' to allow)");
            }
            return this._nullValue;
        }
    }

    public static final class SetterlessProperty
    extends SettableBeanProperty {
        protected final AnnotatedMethod _annotated;
        protected final Method _getter;

        protected SetterlessProperty(SetterlessProperty setterlessProperty, JsonDeserializer<Object> jsonDeserializer) {
            super(setterlessProperty, jsonDeserializer);
            this._annotated = setterlessProperty._annotated;
            this._getter = setterlessProperty._getter;
        }

        public SetterlessProperty(String string, JavaType javaType, TypeDeserializer typeDeserializer, Annotations annotations, AnnotatedMethod annotatedMethod) {
            super(string, javaType, typeDeserializer, annotations);
            this._annotated = annotatedMethod;
            this._getter = annotatedMethod.getAnnotated();
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public final void deserializeAndSet(JsonParser var1, DeserializationContext var2_3, Object var3_2) throws IOException, JsonProcessingException {
            if (var1.getCurrentToken() == JsonToken.VALUE_NULL) {
                return;
            }
            try {
                var6_4 = this._getter.invoke(var3_2, new Object[0]);
                ** if (var6_4 != null) goto lbl-1000
            }
            catch (Exception var4_5) {
                this._throwAsIOE(var4_5);
                return;
            }
lbl-1000: // 1 sources:
            {
                throw new JsonMappingException("Problem deserializing 'setterless' property '" + this.getName() + "': get method returned null");
            }
lbl-1000: // 1 sources:
            {
            }
            this._valueDeserializer.deserialize(var1, var2_3, var6_4);
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> class_) {
            return this._annotated.getAnnotation(class_);
        }

        @Override
        public AnnotatedMember getMember() {
            return this._annotated;
        }

        @Override
        public final void set(Object object, Object object2) throws IOException {
            throw new UnsupportedOperationException("Should never call 'set' on setterless property");
        }

        @Override
        public SetterlessProperty withValueDeserializer(JsonDeserializer<Object> jsonDeserializer) {
            return new SetterlessProperty((SetterlessProperty)this, jsonDeserializer);
        }
    }

}


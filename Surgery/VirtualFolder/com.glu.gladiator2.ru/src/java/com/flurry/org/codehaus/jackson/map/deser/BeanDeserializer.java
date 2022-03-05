/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Error
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.InvocationTargetException
 *  java.lang.reflect.Member
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.ResolvableDeserializer;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.annotate.JsonCachable;
import com.flurry.org.codehaus.jackson.map.deser.AbstractDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.SettableAnyProperty;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.impl.BeanPropertyMap;
import com.flurry.org.codehaus.jackson.map.deser.impl.CreatorCollector;
import com.flurry.org.codehaus.jackson.map.deser.impl.ExternalTypeHandler;
import com.flurry.org.codehaus.jackson.map.deser.impl.PropertyBasedCreator;
import com.flurry.org.codehaus.jackson.map.deser.impl.PropertyValueBuffer;
import com.flurry.org.codehaus.jackson.map.deser.impl.UnwrappedPropertyHandler;
import com.flurry.org.codehaus.jackson.map.deser.impl.ValueInjector;
import com.flurry.org.codehaus.jackson.map.deser.std.ContainerDeserializerBase;
import com.flurry.org.codehaus.jackson.map.deser.std.StdDeserializer;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedWithParams;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.type.JavaType;
import com.flurry.org.codehaus.jackson.util.TokenBuffer;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@JsonCachable
public class BeanDeserializer
extends StdDeserializer<Object>
implements ResolvableDeserializer {
    protected SettableAnyProperty _anySetter;
    protected final Map<String, SettableBeanProperty> _backRefs;
    protected final BeanPropertyMap _beanProperties;
    protected final JavaType _beanType;
    protected JsonDeserializer<Object> _delegateDeserializer;
    protected ExternalTypeHandler _externalTypeIdHandler;
    protected final AnnotatedClass _forClass;
    protected final HashSet<String> _ignorableProps;
    protected final boolean _ignoreAllUnknown;
    protected final ValueInjector[] _injectables;
    protected boolean _nonStandardCreation;
    protected final BeanProperty _property;
    protected final PropertyBasedCreator _propertyBasedCreator;
    protected HashMap<ClassKey, JsonDeserializer<Object>> _subDeserializers;
    protected UnwrappedPropertyHandler _unwrappedPropertyHandler;
    protected final ValueInstantiator _valueInstantiator;

    public BeanDeserializer(BeanDescription beanDescription, BeanProperty beanProperty, ValueInstantiator valueInstantiator, BeanPropertyMap beanPropertyMap, Map<String, SettableBeanProperty> map, HashSet<String> hashSet, boolean bl, SettableAnyProperty settableAnyProperty, List<ValueInjector> list) {
        super(beanDescription.getClassInfo(), beanDescription.getType(), beanProperty, valueInstantiator, beanPropertyMap, map, hashSet, bl, settableAnyProperty, list);
    }

    protected BeanDeserializer(BeanDeserializer beanDeserializer) {
        super(beanDeserializer, beanDeserializer._ignoreAllUnknown);
    }

    protected BeanDeserializer(BeanDeserializer beanDeserializer, boolean bl) {
        super(beanDeserializer._beanType);
        this._forClass = beanDeserializer._forClass;
        this._beanType = beanDeserializer._beanType;
        this._property = beanDeserializer._property;
        this._valueInstantiator = beanDeserializer._valueInstantiator;
        this._delegateDeserializer = beanDeserializer._delegateDeserializer;
        this._propertyBasedCreator = beanDeserializer._propertyBasedCreator;
        this._beanProperties = beanDeserializer._beanProperties;
        this._backRefs = beanDeserializer._backRefs;
        this._ignorableProps = beanDeserializer._ignorableProps;
        this._ignoreAllUnknown = bl;
        this._anySetter = beanDeserializer._anySetter;
        this._injectables = beanDeserializer._injectables;
        this._nonStandardCreation = beanDeserializer._nonStandardCreation;
        this._unwrappedPropertyHandler = beanDeserializer._unwrappedPropertyHandler;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected BeanDeserializer(AnnotatedClass annotatedClass, JavaType javaType, BeanProperty beanProperty, ValueInstantiator valueInstantiator, BeanPropertyMap beanPropertyMap, Map<String, SettableBeanProperty> map, HashSet<String> hashSet, boolean bl, SettableAnyProperty settableAnyProperty, List<ValueInjector> list) {
        super(javaType);
        this._forClass = annotatedClass;
        this._beanType = javaType;
        this._property = beanProperty;
        this._valueInstantiator = valueInstantiator;
        this._propertyBasedCreator = valueInstantiator.canCreateFromObjectWith() ? new PropertyBasedCreator(valueInstantiator) : null;
        this._beanProperties = beanPropertyMap;
        this._backRefs = map;
        this._ignorableProps = hashSet;
        this._ignoreAllUnknown = bl;
        this._anySetter = settableAnyProperty;
        ValueInjector[] arrvalueInjector = null;
        if (list != null) {
            boolean bl2 = list.isEmpty();
            arrvalueInjector = null;
            if (!bl2) {
                arrvalueInjector = (ValueInjector[])list.toArray((Object[])new ValueInjector[list.size()]);
            }
        }
        this._injectables = arrvalueInjector;
        boolean bl3 = valueInstantiator.canCreateUsingDelegate() || this._propertyBasedCreator != null || !valueInstantiator.canCreateUsingDefault() || this._unwrappedPropertyHandler != null;
        this._nonStandardCreation = bl3;
    }

    @Deprecated
    public BeanDeserializer(AnnotatedClass annotatedClass, JavaType javaType, BeanProperty beanProperty, CreatorCollector creatorCollector, BeanPropertyMap beanPropertyMap, Map<String, SettableBeanProperty> map, HashSet<String> hashSet, boolean bl, SettableAnyProperty settableAnyProperty) {
        super(annotatedClass, javaType, beanProperty, creatorCollector.constructValueInstantiator(null), beanPropertyMap, map, hashSet, bl, settableAnyProperty, null);
    }

    private final void _handleUnknown(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, String string) throws IOException, JsonProcessingException {
        if (this._ignorableProps != null && this._ignorableProps.contains((Object)string)) {
            jsonParser.skipChildren();
            return;
        }
        if (this._anySetter != null) {
            try {
                this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, string);
                return;
            }
            catch (Exception exception) {
                this.wrapAndThrow((Throwable)exception, object, string, deserializationContext);
                return;
            }
        }
        this.handleUnknownProperty(jsonParser, deserializationContext, object, string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final Object _deserializeUsingPropertyBased(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TokenBuffer tokenBuffer;
        Object object;
        block14 : {
            PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
            PropertyValueBuffer propertyValueBuffer = propertyBasedCreator.startBuilding(jsonParser, deserializationContext);
            tokenBuffer = null;
            JsonToken jsonToken = jsonParser.getCurrentToken();
            while (jsonToken == JsonToken.FIELD_NAME) {
                block13 : {
                    String string;
                    block15 : {
                        Object object2;
                        string = jsonParser.getCurrentName();
                        jsonParser.nextToken();
                        SettableBeanProperty settableBeanProperty = propertyBasedCreator.findCreatorProperty(string);
                        if (settableBeanProperty == null) break block15;
                        Object object3 = settableBeanProperty.deserialize(jsonParser, deserializationContext);
                        if (!propertyValueBuffer.assignParameter(settableBeanProperty.getPropertyIndex(), object3)) break block13;
                        jsonParser.nextToken();
                        try {
                            Object object4;
                            object2 = object4 = propertyBasedCreator.build(propertyValueBuffer);
                        }
                        catch (Exception exception) {
                            this.wrapAndThrow((Throwable)exception, (Object)this._beanType.getRawClass(), string, deserializationContext);
                            break block13;
                        }
                        if (object2.getClass() != this._beanType.getRawClass()) {
                            return this.handlePolymorphic(jsonParser, deserializationContext, object2, tokenBuffer);
                        }
                        if (tokenBuffer == null) return this.deserialize(jsonParser, deserializationContext, object2);
                        object2 = this.handleUnknownProperties(deserializationContext, object2, tokenBuffer);
                        return this.deserialize(jsonParser, deserializationContext, object2);
                    }
                    SettableBeanProperty settableBeanProperty = this._beanProperties.find(string);
                    if (settableBeanProperty != null) {
                        propertyValueBuffer.bufferProperty(settableBeanProperty, settableBeanProperty.deserialize(jsonParser, deserializationContext));
                    } else if (this._ignorableProps != null && this._ignorableProps.contains((Object)string)) {
                        jsonParser.skipChildren();
                    } else if (this._anySetter != null) {
                        propertyValueBuffer.bufferAnyProperty(this._anySetter, string, this._anySetter.deserialize(jsonParser, deserializationContext));
                    } else {
                        if (tokenBuffer == null) {
                            tokenBuffer = new TokenBuffer(jsonParser.getCodec());
                        }
                        tokenBuffer.writeFieldName(string);
                        tokenBuffer.copyCurrentStructure(jsonParser);
                    }
                }
                jsonToken = jsonParser.nextToken();
            }
            try {
                Object object5;
                object = object5 = propertyBasedCreator.build(propertyValueBuffer);
                if (tokenBuffer != null) break block14;
            }
            catch (Exception exception) {
                this.wrapInstantiationProblem(exception, deserializationContext);
                return null;
            }
            return object;
        }
        if (object.getClass() == this._beanType.getRawClass()) return this.handleUnknownProperties(deserializationContext, object, tokenBuffer);
        return this.handlePolymorphic(null, deserializationContext, object, tokenBuffer);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected JsonDeserializer<Object> _findSubclassDeserializer(DeserializationContext deserializationContext, Object object, TokenBuffer tokenBuffer) throws IOException, JsonProcessingException {
        void var10_4 = this;
        // MONITORENTER : var10_4
        JsonDeserializer<Object> jsonDeserializer = this._subDeserializers == null ? null : (JsonDeserializer<Object>)this._subDeserializers.get((Object)new ClassKey(object.getClass()));
        // MONITOREXIT : var10_4
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        DeserializerProvider deserializerProvider = deserializationContext.getDeserializerProvider();
        if (deserializerProvider == null) return jsonDeserializer;
        JavaType javaType = deserializationContext.constructType(object.getClass());
        jsonDeserializer = deserializerProvider.findValueDeserializer(deserializationContext.getConfig(), javaType, this._property);
        if (jsonDeserializer == null) return jsonDeserializer;
        var10_4 = this;
        // MONITORENTER : var10_4
        if (this._subDeserializers == null) {
            this._subDeserializers = new HashMap();
        }
        this._subDeserializers.put((Object)new ClassKey(object.getClass()), jsonDeserializer);
        // MONITOREXIT : var10_4
        return jsonDeserializer;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected SettableBeanProperty _resolveInnerClassValuedProperty(DeserializationConfig deserializationConfig, SettableBeanProperty settableBeanProperty) {
        JsonDeserializer<Object> jsonDeserializer = settableBeanProperty.getValueDeserializer();
        if (!(jsonDeserializer instanceof BeanDeserializer)) return settableBeanProperty;
        if (((BeanDeserializer)jsonDeserializer).getValueInstantiator().canCreateUsingDefault()) return settableBeanProperty;
        Class<?> class_ = settableBeanProperty.getType().getRawClass();
        Class<?> class_2 = ClassUtil.getOuterClass(class_);
        if (class_2 == null) return settableBeanProperty;
        if (class_2 != this._beanType.getRawClass()) return settableBeanProperty;
        Constructor[] arrconstructor = class_.getConstructors();
        int n = arrconstructor.length;
        int n2 = 0;
        while (n2 < n) {
            Constructor constructor = arrconstructor[n2];
            Class[] arrclass = constructor.getParameterTypes();
            if (arrclass.length == 1 && arrclass[0] == class_2) {
                if (!deserializationConfig.isEnabled(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) return new SettableBeanProperty.InnerClassProperty(settableBeanProperty, constructor);
                ClassUtil.checkAndFixAccess((Member)constructor);
                return new SettableBeanProperty.InnerClassProperty(settableBeanProperty, constructor);
            }
            ++n2;
        }
        return settableBeanProperty;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected SettableBeanProperty _resolveManagedReferenceProperty(DeserializationConfig deserializationConfig, SettableBeanProperty settableBeanProperty) {
        SettableBeanProperty settableBeanProperty2;
        String string = settableBeanProperty.getManagedReferenceName();
        if (string == null) {
            return settableBeanProperty;
        }
        JsonDeserializer<Object> jsonDeserializer = settableBeanProperty.getValueDeserializer();
        boolean bl = false;
        if (jsonDeserializer instanceof BeanDeserializer) {
            settableBeanProperty2 = ((BeanDeserializer)jsonDeserializer).findBackReference(string);
        } else {
            if (!(jsonDeserializer instanceof ContainerDeserializerBase)) {
                if (jsonDeserializer instanceof AbstractDeserializer) {
                    throw new IllegalArgumentException("Can not handle managed/back reference for abstract types (property " + this._beanType.getRawClass().getName() + "." + settableBeanProperty.getName() + ")");
                }
                throw new IllegalArgumentException("Can not handle managed/back reference '" + string + "': type for value deserializer is not BeanDeserializer or ContainerDeserializerBase, but " + jsonDeserializer.getClass().getName());
            }
            JsonDeserializer<Object> jsonDeserializer2 = ((ContainerDeserializerBase)jsonDeserializer).getContentDeserializer();
            if (!(jsonDeserializer2 instanceof BeanDeserializer)) {
                throw new IllegalArgumentException("Can not handle managed/back reference '" + string + "': value deserializer is of type ContainerDeserializerBase, but content type is not handled by a BeanDeserializer " + " (instead it's of type " + jsonDeserializer2.getClass().getName() + ")");
            }
            settableBeanProperty2 = ((BeanDeserializer)jsonDeserializer2).findBackReference(string);
            bl = true;
        }
        if (settableBeanProperty2 == null) {
            throw new IllegalArgumentException("Can not handle managed/back reference '" + string + "': no back reference property found from type " + settableBeanProperty.getType());
        }
        JavaType javaType = this._beanType;
        JavaType javaType2 = settableBeanProperty2.getType();
        if (!javaType2.getRawClass().isAssignableFrom(javaType.getRawClass())) {
            throw new IllegalArgumentException("Can not handle managed/back reference '" + string + "': back reference type (" + javaType2.getRawClass().getName() + ") not compatible with managed type (" + javaType.getRawClass().getName() + ")");
        }
        return new SettableBeanProperty.ManagedReferenceProperty(string, settableBeanProperty, settableBeanProperty2, this._forClass.getAnnotations(), bl);
    }

    protected SettableBeanProperty _resolveUnwrappedProperty(DeserializationConfig deserializationConfig, SettableBeanProperty settableBeanProperty) {
        JsonDeserializer<Object> jsonDeserializer;
        JsonDeserializer<Object> jsonDeserializer2;
        AnnotatedMember annotatedMember = settableBeanProperty.getMember();
        if (annotatedMember != null && deserializationConfig.getAnnotationIntrospector().shouldUnwrapProperty(annotatedMember) == Boolean.TRUE && (jsonDeserializer = (jsonDeserializer2 = settableBeanProperty.getValueDeserializer()).unwrappingDeserializer()) != jsonDeserializer2 && jsonDeserializer != null) {
            return settableBeanProperty.withValueDeserializer(jsonDeserializer);
        }
        return null;
    }

    @Override
    public final Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonParser.nextToken();
            return this.deserializeFromObject(jsonParser, deserializationContext);
        }
        switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonToken.ordinal()]) {
            default: {
                throw deserializationContext.mappingException(this.getBeanClass());
            }
            case 1: {
                return this.deserializeFromString(jsonParser, deserializationContext);
            }
            case 2: {
                return this.deserializeFromNumber(jsonParser, deserializationContext);
            }
            case 3: {
                return this.deserializeFromDouble(jsonParser, deserializationContext);
            }
            case 4: {
                return jsonParser.getEmbeddedObject();
            }
            case 5: 
            case 6: {
                return this.deserializeFromBoolean(jsonParser, deserializationContext);
            }
            case 7: {
                return this.deserializeFromArray(jsonParser, deserializationContext);
            }
            case 8: 
            case 9: 
        }
        return this.deserializeFromObject(jsonParser, deserializationContext);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        if (this._injectables != null) {
            this.injectValues(deserializationContext, object);
        }
        if (this._unwrappedPropertyHandler != null) {
            return this.deserializeWithUnwrapped(jsonParser, deserializationContext, object);
        }
        if (this._externalTypeIdHandler != null) {
            return this.deserializeWithExternalTypeId(jsonParser, deserializationContext, object);
        }
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonToken = jsonParser.nextToken();
        }
        while (jsonToken == JsonToken.FIELD_NAME) {
            String string = jsonParser.getCurrentName();
            jsonParser.nextToken();
            SettableBeanProperty settableBeanProperty = this._beanProperties.find(string);
            if (settableBeanProperty != null) {
                try {
                    settableBeanProperty.deserializeAndSet(jsonParser, deserializationContext, object);
                }
                catch (Exception exception) {
                    this.wrapAndThrow((Throwable)exception, object, string, deserializationContext);
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains((Object)string)) {
                jsonParser.skipChildren();
            } else if (this._anySetter != null) {
                this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, string);
            } else {
                this.handleUnknownProperty(jsonParser, deserializationContext, object, string);
            }
            jsonToken = jsonParser.nextToken();
        }
        return object;
    }

    public Object deserializeFromArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._delegateDeserializer != null) {
            try {
                Object object = this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
                if (this._injectables != null) {
                    this.injectValues(deserializationContext, object);
                }
                return object;
            }
            catch (Exception exception) {
                this.wrapInstantiationProblem(exception, deserializationContext);
            }
        }
        throw deserializationContext.mappingException(this.getBeanClass());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object deserializeFromBoolean(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        boolean bl;
        if (this._delegateDeserializer != null && !this._valueInstantiator.canCreateFromBoolean()) {
            Object object = this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
            if (this._injectables == null) return object;
            this.injectValues(deserializationContext, object);
            return object;
        }
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_TRUE) {
            bl = true;
            do {
                return this._valueInstantiator.createFromBoolean(bl);
                break;
            } while (true);
        }
        bl = false;
        return this._valueInstantiator.createFromBoolean(bl);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object deserializeFromDouble(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        switch (jsonParser.getNumberType()) {
            default: {
                if (this._delegateDeserializer == null) throw deserializationContext.instantiationException(this.getBeanClass(), "no suitable creator method found to deserialize from JSON floating-point number");
                return this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
            }
            case FLOAT: 
            case DOUBLE: {
                if (this._delegateDeserializer == null) return this._valueInstantiator.createFromDouble(jsonParser.getDoubleValue());
                if (this._valueInstantiator.canCreateFromDouble()) return this._valueInstantiator.createFromDouble(jsonParser.getDoubleValue());
                Object object = this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
                if (this._injectables == null) return object;
                this.injectValues(deserializationContext, object);
                return object;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public Object deserializeFromNumber(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        switch (jsonParser.getNumberType()) {
            Object object;
            default: {
                if (this._delegateDeserializer == null) throw deserializationContext.instantiationException(this.getBeanClass(), "no suitable creator method found to deserialize from JSON integer number");
                {
                    object = this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
                    if (this._injectables == null) return object;
                    this.injectValues(deserializationContext, object);
                    return object;
                }
            }
            case INT: {
                if (this._delegateDeserializer == null || this._valueInstantiator.canCreateFromInt()) return this._valueInstantiator.createFromInt(jsonParser.getIntValue());
                {
                    object = this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
                    if (this._injectables == null) return object;
                    this.injectValues(deserializationContext, object);
                    return object;
                }
            }
            case LONG: {
                if (this._delegateDeserializer == null || this._valueInstantiator.canCreateFromInt()) return this._valueInstantiator.createFromLong(jsonParser.getLongValue());
                {
                    object = this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
                    if (this._injectables == null) return object;
                    this.injectValues(deserializationContext, object);
                    return object;
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object deserializeFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._nonStandardCreation) {
            if (this._unwrappedPropertyHandler != null) {
                return this.deserializeWithUnwrapped(jsonParser, deserializationContext);
            }
            if (this._externalTypeIdHandler == null) return this.deserializeFromObjectUsingNonDefault(jsonParser, deserializationContext);
            return this.deserializeWithExternalTypeId(jsonParser, deserializationContext);
        }
        Object object = this._valueInstantiator.createUsingDefault();
        if (this._injectables != null) {
            this.injectValues(deserializationContext, object);
        }
        while (jsonParser.getCurrentToken() != JsonToken.END_OBJECT) {
            String string = jsonParser.getCurrentName();
            jsonParser.nextToken();
            SettableBeanProperty settableBeanProperty = this._beanProperties.find(string);
            if (settableBeanProperty != null) {
                try {
                    settableBeanProperty.deserializeAndSet(jsonParser, deserializationContext, object);
                }
                catch (Exception exception) {
                    this.wrapAndThrow((Throwable)exception, object, string, deserializationContext);
                }
            } else {
                BeanDeserializer.super._handleUnknown(jsonParser, deserializationContext, object, string);
            }
            jsonParser.nextToken();
        }
        return object;
    }

    protected Object deserializeFromObjectUsingNonDefault(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (this._propertyBasedCreator != null) {
            return this._deserializeUsingPropertyBased(jsonParser, deserializationContext);
        }
        if (this._beanType.isAbstract()) {
            throw JsonMappingException.from(jsonParser, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
        }
        throw JsonMappingException.from(jsonParser, "No suitable constructor found for type " + this._beanType + ": can not instantiate from JSON object (need to add/enable type information?)");
    }

    public Object deserializeFromString(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._delegateDeserializer != null && !this._valueInstantiator.canCreateFromString()) {
            Object object = this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
            if (this._injectables != null) {
                this.injectValues(deserializationContext, object);
            }
            return object;
        }
        return this._valueInstantiator.createFromString(jsonParser.getText());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object deserializeUsingPropertyBasedWithExternalTypeId(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Object object;
        ExternalTypeHandler externalTypeHandler = this._externalTypeIdHandler.start();
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer propertyValueBuffer = propertyBasedCreator.startBuilding(jsonParser, deserializationContext);
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser.getCodec());
        tokenBuffer.writeStartObject();
        JsonToken jsonToken = jsonParser.getCurrentToken();
        while (jsonToken == JsonToken.FIELD_NAME) {
            block13 : {
                String string;
                block14 : {
                    Object object2;
                    string = jsonParser.getCurrentName();
                    jsonParser.nextToken();
                    SettableBeanProperty settableBeanProperty = propertyBasedCreator.findCreatorProperty(string);
                    if (settableBeanProperty == null) break block14;
                    Object object3 = settableBeanProperty.deserialize(jsonParser, deserializationContext);
                    if (!propertyValueBuffer.assignParameter(settableBeanProperty.getPropertyIndex(), object3)) break block13;
                    JsonToken jsonToken2 = jsonParser.nextToken();
                    try {
                        object2 = propertyBasedCreator.build(propertyValueBuffer);
                    }
                    catch (Exception exception) {
                        this.wrapAndThrow((Throwable)exception, (Object)this._beanType.getRawClass(), string, deserializationContext);
                        break block13;
                    }
                    while (jsonToken2 == JsonToken.FIELD_NAME) {
                        jsonParser.nextToken();
                        tokenBuffer.copyCurrentStructure(jsonParser);
                        jsonToken2 = jsonParser.nextToken();
                    }
                    if (object2.getClass() != this._beanType.getRawClass()) {
                        throw deserializationContext.mappingException("Can not create polymorphic instances with unwrapped values");
                    }
                    return externalTypeHandler.complete(jsonParser, deserializationContext, object2);
                }
                SettableBeanProperty settableBeanProperty = this._beanProperties.find(string);
                if (settableBeanProperty != null) {
                    propertyValueBuffer.bufferProperty(settableBeanProperty, settableBeanProperty.deserialize(jsonParser, deserializationContext));
                } else if (!externalTypeHandler.handleToken(jsonParser, deserializationContext, string, null)) {
                    if (this._ignorableProps != null && this._ignorableProps.contains((Object)string)) {
                        jsonParser.skipChildren();
                    } else if (this._anySetter != null) {
                        propertyValueBuffer.bufferAnyProperty(this._anySetter, string, this._anySetter.deserialize(jsonParser, deserializationContext));
                    }
                }
            }
            jsonToken = jsonParser.nextToken();
        }
        try {
            object = propertyBasedCreator.build(propertyValueBuffer);
        }
        catch (Exception exception) {
            this.wrapInstantiationProblem(exception, deserializationContext);
            return null;
        }
        return externalTypeHandler.complete(jsonParser, deserializationContext, object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object deserializeUsingPropertyBasedWithUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Object object;
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer propertyValueBuffer = propertyBasedCreator.startBuilding(jsonParser, deserializationContext);
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser.getCodec());
        tokenBuffer.writeStartObject();
        JsonToken jsonToken = jsonParser.getCurrentToken();
        while (jsonToken == JsonToken.FIELD_NAME) {
            block12 : {
                String string;
                block13 : {
                    Object object2;
                    string = jsonParser.getCurrentName();
                    jsonParser.nextToken();
                    SettableBeanProperty settableBeanProperty = propertyBasedCreator.findCreatorProperty(string);
                    if (settableBeanProperty == null) break block13;
                    Object object3 = settableBeanProperty.deserialize(jsonParser, deserializationContext);
                    if (!propertyValueBuffer.assignParameter(settableBeanProperty.getPropertyIndex(), object3)) break block12;
                    JsonToken jsonToken2 = jsonParser.nextToken();
                    try {
                        object2 = propertyBasedCreator.build(propertyValueBuffer);
                    }
                    catch (Exception exception) {
                        this.wrapAndThrow((Throwable)exception, (Object)this._beanType.getRawClass(), string, deserializationContext);
                        break block12;
                    }
                    while (jsonToken2 == JsonToken.FIELD_NAME) {
                        jsonParser.nextToken();
                        tokenBuffer.copyCurrentStructure(jsonParser);
                        jsonToken2 = jsonParser.nextToken();
                    }
                    tokenBuffer.writeEndObject();
                    if (object2.getClass() != this._beanType.getRawClass()) {
                        throw deserializationContext.mappingException("Can not create polymorphic instances with unwrapped values");
                    }
                    return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, object2, tokenBuffer);
                }
                SettableBeanProperty settableBeanProperty = this._beanProperties.find(string);
                if (settableBeanProperty != null) {
                    propertyValueBuffer.bufferProperty(settableBeanProperty, settableBeanProperty.deserialize(jsonParser, deserializationContext));
                } else if (this._ignorableProps != null && this._ignorableProps.contains((Object)string)) {
                    jsonParser.skipChildren();
                } else {
                    tokenBuffer.writeFieldName(string);
                    tokenBuffer.copyCurrentStructure(jsonParser);
                    if (this._anySetter != null) {
                        propertyValueBuffer.bufferAnyProperty(this._anySetter, string, this._anySetter.deserialize(jsonParser, deserializationContext));
                    }
                }
            }
            jsonToken = jsonParser.nextToken();
        }
        try {
            object = propertyBasedCreator.build(propertyValueBuffer);
        }
        catch (Exception exception) {
            this.wrapInstantiationProblem(exception, deserializationContext);
            return null;
        }
        return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, object, tokenBuffer);
    }

    protected Object deserializeWithExternalTypeId(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._propertyBasedCreator != null) {
            return this.deserializeUsingPropertyBasedWithExternalTypeId(jsonParser, deserializationContext);
        }
        return this.deserializeWithExternalTypeId(jsonParser, deserializationContext, this._valueInstantiator.createUsingDefault());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object deserializeWithExternalTypeId(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        ExternalTypeHandler externalTypeHandler = this._externalTypeIdHandler.start();
        while (jsonParser.getCurrentToken() != JsonToken.END_OBJECT) {
            String string = jsonParser.getCurrentName();
            jsonParser.nextToken();
            SettableBeanProperty settableBeanProperty = this._beanProperties.find(string);
            if (settableBeanProperty != null) {
                try {
                    settableBeanProperty.deserializeAndSet(jsonParser, deserializationContext, object);
                }
                catch (Exception exception) {
                    this.wrapAndThrow((Throwable)exception, object, string, deserializationContext);
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains((Object)string)) {
                jsonParser.skipChildren();
            } else if (!externalTypeHandler.handleToken(jsonParser, deserializationContext, string, object)) {
                if (this._anySetter != null) {
                    try {
                        this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, string);
                    }
                    catch (Exception exception) {
                        this.wrapAndThrow((Throwable)exception, object, string, deserializationContext);
                    }
                } else {
                    this.handleUnknownProperty(jsonParser, deserializationContext, object, string);
                }
            }
            jsonParser.nextToken();
        }
        return externalTypeHandler.complete(jsonParser, deserializationContext, object);
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object deserializeWithUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (this._propertyBasedCreator != null) {
            return this.deserializeUsingPropertyBasedWithUnwrapped(jsonParser, deserializationContext);
        }
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser.getCodec());
        tokenBuffer.writeStartObject();
        Object object = this._valueInstantiator.createUsingDefault();
        if (this._injectables != null) {
            this.injectValues(deserializationContext, object);
        }
        do {
            if (jsonParser.getCurrentToken() == JsonToken.END_OBJECT) {
                tokenBuffer.writeEndObject();
                this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, object, tokenBuffer);
                return object;
            }
            String string = jsonParser.getCurrentName();
            jsonParser.nextToken();
            SettableBeanProperty settableBeanProperty = this._beanProperties.find(string);
            if (settableBeanProperty != null) {
                try {
                    settableBeanProperty.deserializeAndSet(jsonParser, deserializationContext, object);
                }
                catch (Exception exception) {
                    this.wrapAndThrow((Throwable)exception, object, string, deserializationContext);
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains((Object)string)) {
                jsonParser.skipChildren();
            } else {
                tokenBuffer.writeFieldName(string);
                tokenBuffer.copyCurrentStructure(jsonParser);
                if (this._anySetter != null) {
                    try {
                        this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, string);
                    }
                    catch (Exception exception) {
                        this.wrapAndThrow((Throwable)exception, object, string, deserializationContext);
                    }
                }
            }
            jsonParser.nextToken();
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object deserializeWithUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonToken = jsonParser.nextToken();
        }
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser.getCodec());
        tokenBuffer.writeStartObject();
        do {
            if (jsonToken != JsonToken.FIELD_NAME) {
                tokenBuffer.writeEndObject();
                this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, object, tokenBuffer);
                return object;
            }
            String string = jsonParser.getCurrentName();
            SettableBeanProperty settableBeanProperty = this._beanProperties.find(string);
            jsonParser.nextToken();
            if (settableBeanProperty != null) {
                try {
                    settableBeanProperty.deserializeAndSet(jsonParser, deserializationContext, object);
                }
                catch (Exception exception) {
                    this.wrapAndThrow((Throwable)exception, object, string, deserializationContext);
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains((Object)string)) {
                jsonParser.skipChildren();
            } else {
                tokenBuffer.writeFieldName(string);
                tokenBuffer.copyCurrentStructure(jsonParser);
                if (this._anySetter != null) {
                    this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, string);
                }
            }
            jsonToken = jsonParser.nextToken();
        } while (true);
    }

    public SettableBeanProperty findBackReference(String string) {
        if (this._backRefs == null) {
            return null;
        }
        return (SettableBeanProperty)this._backRefs.get((Object)string);
    }

    public final Class<?> getBeanClass() {
        return this._beanType.getRawClass();
    }

    public int getPropertyCount() {
        return this._beanProperties.size();
    }

    public ValueInstantiator getValueInstantiator() {
        return this._valueInstantiator;
    }

    @Override
    public JavaType getValueType() {
        return this._beanType;
    }

    protected Object handlePolymorphic(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, TokenBuffer tokenBuffer) throws IOException, JsonProcessingException {
        JsonDeserializer<Object> jsonDeserializer = this._findSubclassDeserializer(deserializationContext, object, tokenBuffer);
        if (jsonDeserializer != null) {
            if (tokenBuffer != null) {
                tokenBuffer.writeEndObject();
                JsonParser jsonParser2 = tokenBuffer.asParser();
                jsonParser2.nextToken();
                object = jsonDeserializer.deserialize(jsonParser2, deserializationContext, object);
            }
            if (jsonParser != null) {
                object = jsonDeserializer.deserialize(jsonParser, deserializationContext, object);
            }
            return object;
        }
        if (tokenBuffer != null) {
            object = this.handleUnknownProperties(deserializationContext, object, tokenBuffer);
        }
        if (jsonParser != null) {
            object = this.deserialize(jsonParser, deserializationContext, object);
        }
        return object;
    }

    protected Object handleUnknownProperties(DeserializationContext deserializationContext, Object object, TokenBuffer tokenBuffer) throws IOException, JsonProcessingException {
        tokenBuffer.writeEndObject();
        JsonParser jsonParser = tokenBuffer.asParser();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String string = jsonParser.getCurrentName();
            jsonParser.nextToken();
            this.handleUnknownProperty(jsonParser, deserializationContext, object, string);
        }
        return object;
    }

    @Override
    protected void handleUnknownProperty(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, String string) throws IOException, JsonProcessingException {
        if (this._ignoreAllUnknown || this._ignorableProps != null && this._ignorableProps.contains((Object)string)) {
            jsonParser.skipChildren();
            return;
        }
        super.handleUnknownProperty(jsonParser, deserializationContext, object, string);
    }

    public boolean hasProperty(String string) {
        return this._beanProperties.find(string) != null;
    }

    protected void injectValues(DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        ValueInjector[] arrvalueInjector = this._injectables;
        int n = arrvalueInjector.length;
        for (int i = 0; i < n; ++i) {
            arrvalueInjector[i].inject(deserializationContext, object);
        }
    }

    public Iterator<SettableBeanProperty> properties() {
        if (this._beanProperties == null) {
            throw new IllegalStateException("Can only call before BeanDeserializer has been resolved");
        }
        return this._beanProperties.allProperties();
    }

    @Override
    public void resolve(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider) throws JsonMappingException {
        Iterator<SettableBeanProperty> iterator = this._beanProperties.allProperties();
        UnwrappedPropertyHandler unwrappedPropertyHandler = null;
        ExternalTypeHandler.Builder builder = null;
        while (iterator.hasNext()) {
            TypeDeserializer typeDeserializer;
            SettableBeanProperty settableBeanProperty;
            SettableBeanProperty settableBeanProperty2;
            SettableBeanProperty settableBeanProperty3;
            SettableBeanProperty settableBeanProperty4 = (SettableBeanProperty)iterator.next();
            SettableBeanProperty settableBeanProperty5 = settableBeanProperty4;
            if (!settableBeanProperty5.hasValueDeserializer()) {
                settableBeanProperty5 = settableBeanProperty5.withValueDeserializer(this.findDeserializer(deserializationConfig, deserializerProvider, settableBeanProperty5.getType(), settableBeanProperty5));
            }
            if ((settableBeanProperty3 = this._resolveUnwrappedProperty(deserializationConfig, settableBeanProperty2 = this._resolveManagedReferenceProperty(deserializationConfig, settableBeanProperty5))) != null) {
                settableBeanProperty2 = settableBeanProperty3;
                if (unwrappedPropertyHandler == null) {
                    unwrappedPropertyHandler = new UnwrappedPropertyHandler();
                }
                unwrappedPropertyHandler.addProperty(settableBeanProperty2);
            }
            if ((settableBeanProperty = this._resolveInnerClassValuedProperty(deserializationConfig, settableBeanProperty2)) != settableBeanProperty4) {
                this._beanProperties.replace(settableBeanProperty);
            }
            if (!settableBeanProperty.hasValueTypeDeserializer() || (typeDeserializer = settableBeanProperty.getValueTypeDeserializer()).getTypeInclusion() != JsonTypeInfo.As.EXTERNAL_PROPERTY) continue;
            if (builder == null) {
                builder = new ExternalTypeHandler.Builder();
            }
            builder.addExternal(settableBeanProperty, typeDeserializer.getPropertyName());
            this._beanProperties.remove(settableBeanProperty);
        }
        if (this._anySetter != null && !this._anySetter.hasValueDeserializer()) {
            this._anySetter = this._anySetter.withValueDeserializer(this.findDeserializer(deserializationConfig, deserializerProvider, this._anySetter.getType(), this._anySetter.getProperty()));
        }
        if (this._valueInstantiator.canCreateUsingDelegate()) {
            JavaType javaType = this._valueInstantiator.getDelegateType();
            if (javaType == null) {
                throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._beanType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
            }
            AnnotatedWithParams annotatedWithParams = this._valueInstantiator.getDelegateCreator();
            this._delegateDeserializer = this.findDeserializer(deserializationConfig, deserializerProvider, javaType, new BeanProperty.Std(null, javaType, this._forClass.getAnnotations(), annotatedWithParams));
        }
        if (this._propertyBasedCreator != null) {
            for (SettableBeanProperty settableBeanProperty : this._propertyBasedCreator.getCreatorProperties()) {
                if (settableBeanProperty.hasValueDeserializer()) continue;
                this._propertyBasedCreator.assignDeserializer(settableBeanProperty, this.findDeserializer(deserializationConfig, deserializerProvider, settableBeanProperty.getType(), settableBeanProperty));
            }
        }
        if (builder != null) {
            this._externalTypeIdHandler = builder.build();
            this._nonStandardCreation = true;
        }
        this._unwrappedPropertyHandler = unwrappedPropertyHandler;
        if (unwrappedPropertyHandler != null) {
            this._nonStandardCreation = true;
        }
    }

    @Override
    public JsonDeserializer<Object> unwrappingDeserializer() {
        if (this.getClass() != BeanDeserializer.class) {
            return this;
        }
        return new BeanDeserializer(this, true);
    }

    @Deprecated
    public void wrapAndThrow(Throwable throwable, Object object, int n) throws IOException {
        this.wrapAndThrow(throwable, object, n, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void wrapAndThrow(Throwable throwable, Object object, int n, DeserializationContext deserializationContext) throws IOException {
        while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        boolean bl = deserializationContext == null || deserializationContext.isEnabled(DeserializationConfig.Feature.WRAP_EXCEPTIONS);
        if (throwable instanceof IOException) {
            if (bl && throwable instanceof JsonMappingException) throw JsonMappingException.wrapWithPath(throwable, object, n);
            {
                throw (IOException)throwable;
            }
        } else {
            if (bl || !(throwable instanceof RuntimeException)) throw JsonMappingException.wrapWithPath(throwable, object, n);
            {
                throw (RuntimeException)throwable;
            }
        }
    }

    @Deprecated
    public void wrapAndThrow(Throwable throwable, Object object, String string) throws IOException {
        this.wrapAndThrow(throwable, object, string, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void wrapAndThrow(Throwable throwable, Object object, String string, DeserializationContext deserializationContext) throws IOException {
        while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        boolean bl = deserializationContext == null || deserializationContext.isEnabled(DeserializationConfig.Feature.WRAP_EXCEPTIONS);
        if (throwable instanceof IOException) {
            if (bl && throwable instanceof JsonMappingException) throw JsonMappingException.wrapWithPath(throwable, object, string);
            {
                throw (IOException)throwable;
            }
        } else {
            if (bl || !(throwable instanceof RuntimeException)) throw JsonMappingException.wrapWithPath(throwable, object, string);
            {
                throw (RuntimeException)throwable;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void wrapInstantiationProblem(Throwable throwable, DeserializationContext deserializationContext) throws IOException {
        while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        boolean bl = deserializationContext == null || deserializationContext.isEnabled(DeserializationConfig.Feature.WRAP_EXCEPTIONS);
        if (throwable instanceof IOException) {
            throw (IOException)throwable;
        }
        if (!bl && throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
        }
        throw deserializationContext.instantiationException(this._beanType.getRawClass(), throwable);
    }

}


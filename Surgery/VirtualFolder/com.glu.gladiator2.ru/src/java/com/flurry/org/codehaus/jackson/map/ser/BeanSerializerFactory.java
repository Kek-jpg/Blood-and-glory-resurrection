/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.BeanPropertyDefinition;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerFactory;
import com.flurry.org.codehaus.jackson.map.Serializers;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.jsontype.NamedType;
import com.flurry.org.codehaus.jackson.map.jsontype.SubtypeResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import com.flurry.org.codehaus.jackson.map.ser.AnyGetterWriter;
import com.flurry.org.codehaus.jackson.map.ser.BasicSerializerFactory;
import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyWriter;
import com.flurry.org.codehaus.jackson.map.ser.BeanSerializer;
import com.flurry.org.codehaus.jackson.map.ser.BeanSerializerBuilder;
import com.flurry.org.codehaus.jackson.map.ser.BeanSerializerModifier;
import com.flurry.org.codehaus.jackson.map.ser.FilteredBeanPropertyWriter;
import com.flurry.org.codehaus.jackson.map.ser.PropertyBuilder;
import com.flurry.org.codehaus.jackson.map.ser.std.MapSerializer;
import com.flurry.org.codehaus.jackson.map.type.TypeBindings;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.map.util.ArrayBuilders;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class BeanSerializerFactory
extends BasicSerializerFactory {
    public static final BeanSerializerFactory instance = new BeanSerializerFactory(null);
    protected final SerializerFactory.Config _factoryConfig;

    protected BeanSerializerFactory(SerializerFactory.Config config) {
        if (config == null) {
            config = new ConfigImpl();
        }
        this._factoryConfig = config;
    }

    protected BeanPropertyWriter _constructWriter(SerializationConfig serializationConfig, TypeBindings typeBindings, PropertyBuilder propertyBuilder, boolean bl, String string, AnnotatedMember annotatedMember) throws JsonMappingException {
        if (serializationConfig.isEnabled(SerializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
            annotatedMember.fixAccess();
        }
        JavaType javaType = annotatedMember.getType(typeBindings);
        BeanProperty.Std std = new BeanProperty.Std(string, javaType, propertyBuilder.getClassAnnotations(), annotatedMember);
        JsonSerializer<Object> jsonSerializer = this.findSerializerFromAnnotation(serializationConfig, annotatedMember, std);
        boolean bl2 = ClassUtil.isCollectionMapOrArray(javaType.getRawClass());
        TypeSerializer typeSerializer = null;
        if (bl2) {
            typeSerializer = this.findPropertyContentTypeSerializer(javaType, serializationConfig, annotatedMember, std);
        }
        BeanPropertyWriter beanPropertyWriter = propertyBuilder.buildWriter(string, javaType, jsonSerializer, this.findPropertyTypeSerializer(javaType, serializationConfig, annotatedMember, std), typeSerializer, annotatedMember, bl);
        beanPropertyWriter.setViews(serializationConfig.getAnnotationIntrospector().findSerializationViews(annotatedMember));
        return beanPropertyWriter;
    }

    protected JsonSerializer<Object> constructBeanSerializer(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty) throws JsonMappingException {
        BeanSerializer beanSerializer;
        if (basicBeanDescription.getBeanClass() == Object.class) {
            throw new IllegalArgumentException("Can not create bean serializer for Object.class");
        }
        BeanSerializerBuilder beanSerializerBuilder = this.constructBeanSerializerBuilder(basicBeanDescription);
        List<BeanPropertyWriter> list = this.findBeanProperties(serializationConfig, basicBeanDescription);
        if (list == null) {
            list = new List<BeanPropertyWriter>();
        }
        if (this._factoryConfig.hasSerializerModifiers()) {
            Iterator iterator = this._factoryConfig.serializerModifiers().iterator();
            while (iterator.hasNext()) {
                list = ((BeanSerializerModifier)iterator.next()).changeProperties(serializationConfig, basicBeanDescription, list);
            }
        }
        List<BeanPropertyWriter> list2 = this.sortBeanProperties(serializationConfig, basicBeanDescription, this.filterBeanProperties(serializationConfig, basicBeanDescription, list));
        if (this._factoryConfig.hasSerializerModifiers()) {
            Iterator iterator = this._factoryConfig.serializerModifiers().iterator();
            while (iterator.hasNext()) {
                list2 = ((BeanSerializerModifier)iterator.next()).orderProperties(serializationConfig, basicBeanDescription, list2);
            }
        }
        beanSerializerBuilder.setProperties(list2);
        beanSerializerBuilder.setFilterId(this.findFilterId(serializationConfig, basicBeanDescription));
        AnnotatedMethod annotatedMethod = basicBeanDescription.findAnyGetter();
        if (annotatedMethod != null) {
            if (serializationConfig.isEnabled(SerializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
                annotatedMethod.fixAccess();
            }
            JavaType javaType = annotatedMethod.getType(basicBeanDescription.bindingsForBeanType());
            beanSerializerBuilder.setAnyGetter(new AnyGetterWriter(annotatedMethod, MapSerializer.construct(null, javaType, serializationConfig.isEnabled(SerializationConfig.Feature.USE_STATIC_TYPING), this.createTypeSerializer(serializationConfig, javaType.getContentType(), beanProperty), beanProperty, null, null)));
        }
        this.processViews(serializationConfig, beanSerializerBuilder);
        if (this._factoryConfig.hasSerializerModifiers()) {
            Iterator iterator = this._factoryConfig.serializerModifiers().iterator();
            while (iterator.hasNext()) {
                beanSerializerBuilder = ((BeanSerializerModifier)iterator.next()).updateBuilder(serializationConfig, basicBeanDescription, beanSerializerBuilder);
            }
        }
        if ((beanSerializer = beanSerializerBuilder.build()) == null && basicBeanDescription.hasKnownClassAnnotations()) {
            beanSerializer = beanSerializerBuilder.createDummy();
        }
        return beanSerializer;
    }

    protected BeanSerializerBuilder constructBeanSerializerBuilder(BasicBeanDescription basicBeanDescription) {
        return new BeanSerializerBuilder(basicBeanDescription);
    }

    protected BeanPropertyWriter constructFilteredBeanWriter(BeanPropertyWriter beanPropertyWriter, Class<?>[] arrclass) {
        return FilteredBeanPropertyWriter.constructViewBased(beanPropertyWriter, arrclass);
    }

    protected PropertyBuilder constructPropertyBuilder(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription) {
        return new PropertyBuilder(serializationConfig, basicBeanDescription);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonSerializer<Object> createKeySerializer(SerializationConfig serializationConfig, JavaType javaType, BeanProperty beanProperty) {
        if (!this._factoryConfig.hasKeySerializers()) {
            return null;
        }
        BasicBeanDescription basicBeanDescription = (BasicBeanDescription)serializationConfig.introspectClassAnnotations(javaType.getRawClass());
        JsonSerializer<Object> jsonSerializer = null;
        Iterator iterator = this._factoryConfig.keySerializers().iterator();
        do {
            if (!iterator.hasNext()) return jsonSerializer;
        } while ((jsonSerializer = ((Serializers)iterator.next()).findSerializer(serializationConfig, javaType, basicBeanDescription, beanProperty)) == null);
        return jsonSerializer;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonSerializer<Object> createSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<?> jsonSerializer;
        JsonSerializer<Object> jsonSerializer2;
        void var9_14;
        BasicBeanDescription basicBeanDescription = (BasicBeanDescription)serializationConfig.introspect(javaType);
        JsonSerializer<Object> jsonSerializer3 = this.findSerializerFromAnnotation(serializationConfig, basicBeanDescription.getClassInfo(), beanProperty);
        if (jsonSerializer3 != null) {
            return jsonSerializer3;
        }
        JavaType javaType2 = this.modifyTypeByAnnotation(serializationConfig, basicBeanDescription.getClassInfo(), javaType);
        boolean bl = javaType2 != javaType;
        if (javaType.isContainerType()) {
            return this.buildContainerSerializer(serializationConfig, javaType2, basicBeanDescription, beanProperty, bl);
        }
        Iterator iterator = this._factoryConfig.serializers().iterator();
        while (iterator.hasNext()) {
            JsonSerializer<Object> jsonSerializer4 = ((Serializers)iterator.next()).findSerializer(serializationConfig, javaType2, basicBeanDescription, beanProperty);
            if (jsonSerializer4 == null) continue;
            return jsonSerializer4;
        }
        JsonSerializer<?> jsonSerializer5 = this.findSerializerByLookup(javaType2, serializationConfig, basicBeanDescription, beanProperty, bl);
        if (jsonSerializer5 == null && (jsonSerializer = this.findSerializerByPrimaryType(javaType2, serializationConfig, basicBeanDescription, beanProperty, bl)) == null && (jsonSerializer2 = this.findBeanSerializer(serializationConfig, javaType2, basicBeanDescription, beanProperty)) == null) {
            JsonSerializer<?> jsonSerializer6 = this.findSerializerByAddonType(serializationConfig, javaType2, basicBeanDescription, beanProperty, bl);
        }
        return var9_14;
    }

    @Override
    protected Iterable<Serializers> customSerializers() {
        return this._factoryConfig.serializers();
    }

    protected List<BeanPropertyWriter> filterBeanProperties(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription, List<BeanPropertyWriter> list) {
        String[] arrstring = serializationConfig.getAnnotationIntrospector().findPropertiesToIgnore(basicBeanDescription.getClassInfo());
        if (arrstring != null && arrstring.length > 0) {
            HashSet<String> hashSet = ArrayBuilders.arrayToSet(arrstring);
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                if (!hashSet.contains((Object)((BeanPropertyWriter)iterator.next()).getName())) continue;
                iterator.remove();
            }
        }
        return list;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected List<BeanPropertyWriter> findBeanProperties(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription) throws JsonMappingException {
        List<BeanPropertyDefinition> list = basicBeanDescription.findProperties();
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        this.removeIgnorableTypes(serializationConfig, basicBeanDescription, list);
        if (serializationConfig.isEnabled(SerializationConfig.Feature.REQUIRE_SETTERS_FOR_GETTERS)) {
            this.removeSetterlessGetters(serializationConfig, basicBeanDescription, list);
        }
        if (list.isEmpty()) {
            return null;
        }
        boolean bl = this.usesStaticTyping(serializationConfig, basicBeanDescription, null, null);
        PropertyBuilder propertyBuilder = this.constructPropertyBuilder(serializationConfig, basicBeanDescription);
        int n = list.size();
        ArrayList arrayList = new ArrayList(n);
        TypeBindings typeBindings = basicBeanDescription.bindingsForBeanType();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            BeanPropertyDefinition beanPropertyDefinition = (BeanPropertyDefinition)iterator.next();
            AnnotatedMember annotatedMember = beanPropertyDefinition.getAccessor();
            AnnotationIntrospector.ReferenceProperty referenceProperty = annotationIntrospector.findReferenceType(annotatedMember);
            if (referenceProperty != null && referenceProperty.isBackReference()) continue;
            String string = beanPropertyDefinition.getName();
            if (annotatedMember instanceof AnnotatedMethod) {
                BeanPropertyWriter beanPropertyWriter = this._constructWriter(serializationConfig, typeBindings, propertyBuilder, bl, string, (AnnotatedMethod)annotatedMember);
                arrayList.add((Object)beanPropertyWriter);
                continue;
            }
            BeanPropertyWriter beanPropertyWriter = this._constructWriter(serializationConfig, typeBindings, propertyBuilder, bl, string, (AnnotatedField)annotatedMember);
            arrayList.add((Object)beanPropertyWriter);
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonSerializer<Object> findBeanSerializer(SerializationConfig serializationConfig, JavaType javaType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty) throws JsonMappingException {
        void var5_6;
        if (!this.isPotentialBeanType(javaType.getRawClass())) {
            return var5_6;
        }
        JsonSerializer<Object> jsonSerializer = this.constructBeanSerializer(serializationConfig, basicBeanDescription, beanProperty);
        if (this._factoryConfig.hasSerializerModifiers()) {
            Iterator iterator = this._factoryConfig.serializerModifiers().iterator();
            while (iterator.hasNext()) {
                void var5_8;
                JsonSerializer<?> jsonSerializer2 = ((BeanSerializerModifier)iterator.next()).modifySerializer(serializationConfig, basicBeanDescription, (JsonSerializer<?>)var5_8);
            }
        }
        return var5_6;
    }

    protected Object findFilterId(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription) {
        return serializationConfig.getAnnotationIntrospector().findFilterId(basicBeanDescription.getClassInfo());
    }

    public TypeSerializer findPropertyContentTypeSerializer(JavaType javaType, SerializationConfig serializationConfig, AnnotatedMember annotatedMember, BeanProperty beanProperty) throws JsonMappingException {
        JavaType javaType2 = javaType.getContentType();
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        TypeResolverBuilder<?> typeResolverBuilder = annotationIntrospector.findPropertyContentTypeResolver(serializationConfig, annotatedMember, javaType);
        if (typeResolverBuilder == null) {
            return this.createTypeSerializer(serializationConfig, javaType2, beanProperty);
        }
        return typeResolverBuilder.buildTypeSerializer(serializationConfig, javaType2, serializationConfig.getSubtypeResolver().collectAndResolveSubtypes(annotatedMember, serializationConfig, annotationIntrospector), beanProperty);
    }

    public TypeSerializer findPropertyTypeSerializer(JavaType javaType, SerializationConfig serializationConfig, AnnotatedMember annotatedMember, BeanProperty beanProperty) throws JsonMappingException {
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        TypeResolverBuilder<?> typeResolverBuilder = annotationIntrospector.findPropertyTypeResolver(serializationConfig, annotatedMember, javaType);
        if (typeResolverBuilder == null) {
            return this.createTypeSerializer(serializationConfig, javaType, beanProperty);
        }
        return typeResolverBuilder.buildTypeSerializer(serializationConfig, javaType, serializationConfig.getSubtypeResolver().collectAndResolveSubtypes(annotatedMember, serializationConfig, annotationIntrospector), beanProperty);
    }

    @Override
    public SerializerFactory.Config getConfig() {
        return this._factoryConfig;
    }

    protected boolean isPotentialBeanType(Class<?> class_) {
        return ClassUtil.canBeABeanType(class_) == null && !ClassUtil.isProxyType(class_);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void processViews(SerializationConfig serializationConfig, BeanSerializerBuilder beanSerializerBuilder) {
        List<BeanPropertyWriter> list = beanSerializerBuilder.getProperties();
        boolean bl = serializationConfig.isEnabled(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION);
        int n = list.size();
        int n2 = 0;
        BeanPropertyWriter[] arrbeanPropertyWriter = new BeanPropertyWriter[n];
        for (int i = 0; i < n; ++i) {
            BeanPropertyWriter beanPropertyWriter = (BeanPropertyWriter)list.get(i);
            Class<?>[] arrclass = beanPropertyWriter.getViews();
            if (arrclass == null) {
                if (!bl) continue;
                arrbeanPropertyWriter[i] = beanPropertyWriter;
                continue;
            }
            ++n2;
            arrbeanPropertyWriter[i] = this.constructFilteredBeanWriter(beanPropertyWriter, arrclass);
        }
        if (bl && n2 == 0) {
            return;
        }
        beanSerializerBuilder.setFilteredProperties(arrbeanPropertyWriter);
    }

    protected void removeIgnorableTypes(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription, List<BeanPropertyDefinition> list) {
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        HashMap hashMap = new HashMap();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            AnnotatedMember annotatedMember = ((BeanPropertyDefinition)iterator.next()).getAccessor();
            if (annotatedMember == null) {
                iterator.remove();
                continue;
            }
            Class<?> class_ = annotatedMember.getRawType();
            Boolean bl = (Boolean)hashMap.get(class_);
            if (bl == null) {
                bl = annotationIntrospector.isIgnorableType(((BasicBeanDescription)serializationConfig.introspectClassAnnotations(class_)).getClassInfo());
                if (bl == null) {
                    bl = Boolean.FALSE;
                }
                hashMap.put(class_, (Object)bl);
            }
            if (!bl.booleanValue()) continue;
            iterator.remove();
        }
    }

    protected void removeSetterlessGetters(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription, List<BeanPropertyDefinition> list) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            if (((BeanPropertyDefinition)iterator.next()).couldDeserialize()) continue;
            iterator.remove();
        }
    }

    @Deprecated
    protected List<BeanPropertyWriter> sortBeanProperties(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription, List<BeanPropertyWriter> list) {
        return list;
    }

    @Override
    public SerializerFactory withConfig(SerializerFactory.Config config) {
        if (this._factoryConfig == config) {
            return this;
        }
        if (this.getClass() != BeanSerializerFactory.class) {
            throw new IllegalStateException("Subtype of BeanSerializerFactory (" + this.getClass().getName() + ") has not properly overridden method 'withAdditionalSerializers': can not instantiate subtype with " + "additional serializer definitions");
        }
        return new BeanSerializerFactory(config);
    }

    public static class ConfigImpl
    extends SerializerFactory.Config {
        protected static final BeanSerializerModifier[] NO_MODIFIERS;
        protected static final Serializers[] NO_SERIALIZERS;
        protected final Serializers[] _additionalKeySerializers;
        protected final Serializers[] _additionalSerializers;
        protected final BeanSerializerModifier[] _modifiers;

        static {
            NO_SERIALIZERS = new Serializers[0];
            NO_MODIFIERS = new BeanSerializerModifier[0];
        }

        public ConfigImpl() {
            this(null, null, null);
        }

        protected ConfigImpl(Serializers[] arrserializers, Serializers[] arrserializers2, BeanSerializerModifier[] arrbeanSerializerModifier) {
            if (arrserializers == null) {
                arrserializers = NO_SERIALIZERS;
            }
            this._additionalSerializers = arrserializers;
            if (arrserializers2 == null) {
                arrserializers2 = NO_SERIALIZERS;
            }
            this._additionalKeySerializers = arrserializers2;
            if (arrbeanSerializerModifier == null) {
                arrbeanSerializerModifier = NO_MODIFIERS;
            }
            this._modifiers = arrbeanSerializerModifier;
        }

        @Override
        public boolean hasKeySerializers() {
            return this._additionalKeySerializers.length > 0;
        }

        @Override
        public boolean hasSerializerModifiers() {
            return this._modifiers.length > 0;
        }

        @Override
        public boolean hasSerializers() {
            return this._additionalSerializers.length > 0;
        }

        @Override
        public Iterable<Serializers> keySerializers() {
            return ArrayBuilders.arrayAsIterable(this._additionalKeySerializers);
        }

        @Override
        public Iterable<BeanSerializerModifier> serializerModifiers() {
            return ArrayBuilders.arrayAsIterable(this._modifiers);
        }

        @Override
        public Iterable<Serializers> serializers() {
            return ArrayBuilders.arrayAsIterable(this._additionalSerializers);
        }

        @Override
        public SerializerFactory.Config withAdditionalKeySerializers(Serializers serializers) {
            if (serializers == null) {
                throw new IllegalArgumentException("Can not pass null Serializers");
            }
            Serializers[] arrserializers = ArrayBuilders.insertInListNoDup(this._additionalKeySerializers, serializers);
            return new ConfigImpl(this._additionalSerializers, arrserializers, this._modifiers);
        }

        @Override
        public SerializerFactory.Config withAdditionalSerializers(Serializers serializers) {
            if (serializers == null) {
                throw new IllegalArgumentException("Can not pass null Serializers");
            }
            return new ConfigImpl(ArrayBuilders.insertInListNoDup(this._additionalSerializers, serializers), this._additionalKeySerializers, this._modifiers);
        }

        @Override
        public SerializerFactory.Config withSerializerModifier(BeanSerializerModifier beanSerializerModifier) {
            if (beanSerializerModifier == null) {
                throw new IllegalArgumentException("Can not pass null modifier");
            }
            BeanSerializerModifier[] arrbeanSerializerModifier = ArrayBuilders.insertInListNoDup(this._modifiers, beanSerializerModifier);
            return new ConfigImpl(this._additionalSerializers, this._additionalKeySerializers, arrbeanSerializerModifier);
        }
    }

}


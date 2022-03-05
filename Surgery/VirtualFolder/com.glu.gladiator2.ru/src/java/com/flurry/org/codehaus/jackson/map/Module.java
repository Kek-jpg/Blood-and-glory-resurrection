/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.Version;
import com.flurry.org.codehaus.jackson.Versioned;
import com.flurry.org.codehaus.jackson.map.AbstractTypeResolver;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.Deserializers;
import com.flurry.org.codehaus.jackson.map.KeyDeserializers;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.Serializers;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializerModifier;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiators;
import com.flurry.org.codehaus.jackson.map.ser.BeanSerializerModifier;
import com.flurry.org.codehaus.jackson.map.type.TypeModifier;

public abstract class Module
implements Versioned {
    public abstract String getModuleName();

    public abstract void setupModule(SetupContext var1);

    @Override
    public abstract Version version();

    public static interface SetupContext {
        public void addAbstractTypeResolver(AbstractTypeResolver var1);

        public void addBeanDeserializerModifier(BeanDeserializerModifier var1);

        public void addBeanSerializerModifier(BeanSerializerModifier var1);

        public void addDeserializers(Deserializers var1);

        public void addKeyDeserializers(KeyDeserializers var1);

        public void addKeySerializers(Serializers var1);

        public void addSerializers(Serializers var1);

        public void addTypeModifier(TypeModifier var1);

        public void addValueInstantiators(ValueInstantiators var1);

        public void appendAnnotationIntrospector(AnnotationIntrospector var1);

        public DeserializationConfig getDeserializationConfig();

        public Version getMapperVersion();

        public SerializationConfig getSerializationConfig();

        public void insertAnnotationIntrospector(AnnotationIntrospector var1);

        public boolean isEnabled(JsonGenerator.Feature var1);

        public boolean isEnabled(JsonParser.Feature var1);

        public boolean isEnabled(DeserializationConfig.Feature var1);

        public boolean isEnabled(SerializationConfig.Feature var1);

        public void setMixInAnnotations(Class<?> var1, Class<?> var2);
    }

}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.Member
 *  java.lang.reflect.Method
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.StdKeyDeserializer;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.map.util.EnumResolver;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashMap;

public class StdKeyDeserializers {
    protected final HashMap<JavaType, KeyDeserializer> _keyDeserializers = new HashMap();

    protected StdKeyDeserializers() {
        this.add(new StdKeyDeserializer.BoolKD());
        this.add(new StdKeyDeserializer.ByteKD());
        this.add(new StdKeyDeserializer.CharKD());
        this.add(new StdKeyDeserializer.ShortKD());
        this.add(new StdKeyDeserializer.IntKD());
        this.add(new StdKeyDeserializer.LongKD());
        this.add(new StdKeyDeserializer.FloatKD());
        this.add(new StdKeyDeserializer.DoubleKD());
        this.add(new StdKeyDeserializer.DateKD());
        this.add(new StdKeyDeserializer.CalendarKD());
        this.add(new StdKeyDeserializer.UuidKD());
    }

    private void add(StdKeyDeserializer stdKeyDeserializer) {
        Class<?> class_ = stdKeyDeserializer.getKeyClass();
        this._keyDeserializers.put((Object)TypeFactory.defaultInstance().uncheckedSimpleType(class_), (Object)stdKeyDeserializer);
    }

    public static HashMap<JavaType, KeyDeserializer> constructAll() {
        return new StdKeyDeserializers()._keyDeserializers;
    }

    public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver<?> enumResolver) {
        return new StdKeyDeserializer.EnumKD(enumResolver, null);
    }

    public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver<?> enumResolver, AnnotatedMethod annotatedMethod) {
        return new StdKeyDeserializer.EnumKD(enumResolver, annotatedMethod);
    }

    public static KeyDeserializer constructStringKeyDeserializer(DeserializationConfig deserializationConfig, JavaType javaType) {
        return StdKeyDeserializer.StringKD.forType(javaType.getClass());
    }

    public static KeyDeserializer findStringBasedKeyDeserializer(DeserializationConfig deserializationConfig, JavaType javaType) {
        BasicBeanDescription basicBeanDescription = (BasicBeanDescription)deserializationConfig.introspect(javaType);
        Constructor<?> constructor = basicBeanDescription.findSingleArgConstructor(String.class);
        if (constructor != null) {
            if (deserializationConfig.isEnabled(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
                ClassUtil.checkAndFixAccess(constructor);
            }
            return new StdKeyDeserializer.StringCtorKeyDeserializer(constructor);
        }
        Method method = basicBeanDescription.findFactoryMethod(String.class);
        if (method != null) {
            if (deserializationConfig.isEnabled(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
                ClassUtil.checkAndFixAccess((Member)method);
            }
            return new StdKeyDeserializer.StringFactoryKeyDeserializer(method);
        }
        return null;
    }
}


/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.type.JavaType;

public abstract class ClassIntrospector<T extends BeanDescription> {
    protected ClassIntrospector() {
    }

    public abstract T forClassAnnotations(MapperConfig<?> var1, JavaType var2, MixInResolver var3);

    @Deprecated
    public T forClassAnnotations(MapperConfig<?> mapperConfig, Class<?> class_, MixInResolver mixInResolver) {
        return this.forClassAnnotations(mapperConfig, mapperConfig.constructType(class_), mixInResolver);
    }

    public abstract T forCreation(DeserializationConfig var1, JavaType var2, MixInResolver var3);

    public abstract T forDeserialization(DeserializationConfig var1, JavaType var2, MixInResolver var3);

    public abstract T forDirectClassAnnotations(MapperConfig<?> var1, JavaType var2, MixInResolver var3);

    @Deprecated
    public T forDirectClassAnnotations(MapperConfig<?> mapperConfig, Class<?> class_, MixInResolver mixInResolver) {
        return this.forDirectClassAnnotations(mapperConfig, mapperConfig.constructType(class_), mixInResolver);
    }

    public abstract T forSerialization(SerializationConfig var1, JavaType var2, MixInResolver var3);

    public static interface MixInResolver {
        public Class<?> findMixInClassFor(Class<?> var1);
    }

}


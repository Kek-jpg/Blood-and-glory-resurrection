/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.System
 */
package com.flurry.org.codehaus.jackson.map.ser.impl;

import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.type.JavaType;

public abstract class PropertySerializerMap {
    public static PropertySerializerMap emptyMap() {
        return Empty.instance;
    }

    public final SerializerAndMapResult findAndAddSerializer(JavaType javaType, SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer = serializerProvider.findValueSerializer(javaType, beanProperty);
        return new SerializerAndMapResult(jsonSerializer, this.newWith(javaType.getRawClass(), jsonSerializer));
    }

    public final SerializerAndMapResult findAndAddSerializer(Class<?> class_, SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer = serializerProvider.findValueSerializer(class_, beanProperty);
        return new SerializerAndMapResult(jsonSerializer, this.newWith(class_, jsonSerializer));
    }

    public abstract PropertySerializerMap newWith(Class<?> var1, JsonSerializer<Object> var2);

    public abstract JsonSerializer<Object> serializerFor(Class<?> var1);

    private static final class Double
    extends PropertySerializerMap {
        private final JsonSerializer<Object> _serializer1;
        private final JsonSerializer<Object> _serializer2;
        private final Class<?> _type1;
        private final Class<?> _type2;

        public Double(Class<?> class_, JsonSerializer<Object> jsonSerializer, Class<?> class_2, JsonSerializer<Object> jsonSerializer2) {
            this._type1 = class_;
            this._serializer1 = jsonSerializer;
            this._type2 = class_2;
            this._serializer2 = jsonSerializer2;
        }

        @Override
        public PropertySerializerMap newWith(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
            TypeAndSerializer[] arrtypeAndSerializer = new TypeAndSerializer[]{new TypeAndSerializer(this._type1, this._serializer1), new TypeAndSerializer(this._type2, this._serializer2)};
            return new Multi(arrtypeAndSerializer);
        }

        @Override
        public JsonSerializer<Object> serializerFor(Class<?> class_) {
            if (class_ == this._type1) {
                return this._serializer1;
            }
            if (class_ == this._type2) {
                return this._serializer2;
            }
            return null;
        }
    }

    private static final class Empty
    extends PropertySerializerMap {
        protected static final Empty instance = new Empty();

        private Empty() {
        }

        @Override
        public PropertySerializerMap newWith(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
            return new Single(class_, jsonSerializer);
        }

        @Override
        public JsonSerializer<Object> serializerFor(Class<?> class_) {
            return null;
        }
    }

    private static final class Multi
    extends PropertySerializerMap {
        private static final int MAX_ENTRIES = 8;
        private final TypeAndSerializer[] _entries;

        public Multi(TypeAndSerializer[] arrtypeAndSerializer) {
            this._entries = arrtypeAndSerializer;
        }

        @Override
        public PropertySerializerMap newWith(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
            int n = this._entries.length;
            if (n == 8) {
                return this;
            }
            TypeAndSerializer[] arrtypeAndSerializer = new TypeAndSerializer[n + 1];
            System.arraycopy((Object)this._entries, (int)0, (Object)arrtypeAndSerializer, (int)0, (int)n);
            arrtypeAndSerializer[n] = new TypeAndSerializer(class_, jsonSerializer);
            return new Multi(arrtypeAndSerializer);
        }

        @Override
        public JsonSerializer<Object> serializerFor(Class<?> class_) {
            for (TypeAndSerializer typeAndSerializer : this._entries) {
                if (typeAndSerializer.type != class_) continue;
                return typeAndSerializer.serializer;
            }
            return null;
        }
    }

    public static final class SerializerAndMapResult {
        public final PropertySerializerMap map;
        public final JsonSerializer<Object> serializer;

        public SerializerAndMapResult(JsonSerializer<Object> jsonSerializer, PropertySerializerMap propertySerializerMap) {
            this.serializer = jsonSerializer;
            this.map = propertySerializerMap;
        }
    }

    private static final class Single
    extends PropertySerializerMap {
        private final JsonSerializer<Object> _serializer;
        private final Class<?> _type;

        public Single(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
            this._type = class_;
            this._serializer = jsonSerializer;
        }

        @Override
        public PropertySerializerMap newWith(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
            return new Double(this._type, this._serializer, class_, jsonSerializer);
        }

        @Override
        public JsonSerializer<Object> serializerFor(Class<?> class_) {
            if (class_ == this._type) {
                return this._serializer;
            }
            return null;
        }
    }

    private static final class TypeAndSerializer {
        public final JsonSerializer<Object> serializer;
        public final Class<?> type;

        public TypeAndSerializer(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
            this.type = class_;
            this.serializer = jsonSerializer;
        }
    }

}


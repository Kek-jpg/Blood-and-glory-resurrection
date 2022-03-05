/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.ser.impl;

import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.ResolvableSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.ser.impl.ReadOnlyClassToSerializerMap;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.HashMap;

public final class SerializerCache {
    private ReadOnlyClassToSerializerMap _readOnlyMap = null;
    private HashMap<TypeKey, JsonSerializer<Object>> _sharedMap = new HashMap(64);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addAndResolveNonTypedSerializer(JavaType javaType, JsonSerializer<Object> jsonSerializer, SerializerProvider serializerProvider) throws JsonMappingException {
        void var5_4 = this;
        synchronized (var5_4) {
            if (this._sharedMap.put((Object)new TypeKey(javaType, false), jsonSerializer) == null) {
                this._readOnlyMap = null;
            }
            if (jsonSerializer instanceof ResolvableSerializer) {
                ((ResolvableSerializer)((Object)jsonSerializer)).resolve(serializerProvider);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addAndResolveNonTypedSerializer(Class<?> class_, JsonSerializer<Object> jsonSerializer, SerializerProvider serializerProvider) throws JsonMappingException {
        void var5_4 = this;
        synchronized (var5_4) {
            if (this._sharedMap.put((Object)new TypeKey(class_, false), jsonSerializer) == null) {
                this._readOnlyMap = null;
            }
            if (jsonSerializer instanceof ResolvableSerializer) {
                ((ResolvableSerializer)((Object)jsonSerializer)).resolve(serializerProvider);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addTypedSerializer(JavaType javaType, JsonSerializer<Object> jsonSerializer) {
        void var4_3 = this;
        synchronized (var4_3) {
            if (this._sharedMap.put((Object)new TypeKey(javaType, true), jsonSerializer) == null) {
                this._readOnlyMap = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addTypedSerializer(Class<?> class_, JsonSerializer<Object> jsonSerializer) {
        void var4_3 = this;
        synchronized (var4_3) {
            if (this._sharedMap.put((Object)new TypeKey(class_, true), jsonSerializer) == null) {
                this._readOnlyMap = null;
            }
            return;
        }
    }

    public void flush() {
        SerializerCache serializerCache = this;
        synchronized (serializerCache) {
            this._sharedMap.clear();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ReadOnlyClassToSerializerMap getReadOnlyLookupMap() {
        SerializerCache serializerCache = this;
        synchronized (serializerCache) {
            ReadOnlyClassToSerializerMap readOnlyClassToSerializerMap = this._readOnlyMap;
            if (readOnlyClassToSerializerMap == null) {
                this._readOnlyMap = readOnlyClassToSerializerMap = ReadOnlyClassToSerializerMap.from(this._sharedMap);
            }
            return readOnlyClassToSerializerMap.instance();
        }
    }

    public int size() {
        SerializerCache serializerCache = this;
        synchronized (serializerCache) {
            int n = this._sharedMap.size();
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonSerializer<Object> typedValueSerializer(JavaType javaType) {
        void var4_2 = this;
        synchronized (var4_2) {
            return (JsonSerializer)this._sharedMap.get((Object)new TypeKey(javaType, true));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonSerializer<Object> typedValueSerializer(Class<?> class_) {
        void var4_2 = this;
        synchronized (var4_2) {
            return (JsonSerializer)this._sharedMap.get((Object)new TypeKey(class_, true));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonSerializer<Object> untypedValueSerializer(JavaType javaType) {
        void var4_2 = this;
        synchronized (var4_2) {
            return (JsonSerializer)this._sharedMap.get((Object)new TypeKey(javaType, false));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonSerializer<Object> untypedValueSerializer(Class<?> class_) {
        void var4_2 = this;
        synchronized (var4_2) {
            return (JsonSerializer)this._sharedMap.get((Object)new TypeKey(class_, false));
        }
    }

    public static final class TypeKey {
        protected Class<?> _class;
        protected int _hashCode;
        protected boolean _isTyped;
        protected JavaType _type;

        public TypeKey(JavaType javaType, boolean bl) {
            this._type = javaType;
            this._class = null;
            this._isTyped = bl;
            this._hashCode = TypeKey.hash(javaType, bl);
        }

        public TypeKey(Class<?> class_, boolean bl) {
            this._class = class_;
            this._type = null;
            this._isTyped = bl;
            this._hashCode = TypeKey.hash(class_, bl);
        }

        private static final int hash(JavaType javaType, boolean bl) {
            int n = -1 + javaType.hashCode();
            if (bl) {
                --n;
            }
            return n;
        }

        private static final int hash(Class<?> class_, boolean bl) {
            int n = class_.getName().hashCode();
            if (bl) {
                ++n;
            }
            return n;
        }

        /*
         * Enabled aggressive block sorting
         */
        public final boolean equals(Object object) {
            block6 : {
                block5 : {
                    if (object == this) break block5;
                    TypeKey typeKey = (TypeKey)object;
                    if (typeKey._isTyped != this._isTyped) {
                        return false;
                    }
                    if (this._class == null) {
                        return this._type.equals(typeKey._type);
                    }
                    if (typeKey._class != this._class) break block6;
                }
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return this._hashCode;
        }

        public void resetTyped(JavaType javaType) {
            this._type = javaType;
            this._class = null;
            this._isTyped = true;
            this._hashCode = TypeKey.hash(javaType, true);
        }

        public void resetTyped(Class<?> class_) {
            this._type = null;
            this._class = class_;
            this._isTyped = true;
            this._hashCode = TypeKey.hash(class_, true);
        }

        public void resetUntyped(JavaType javaType) {
            this._type = javaType;
            this._class = null;
            this._isTyped = false;
            this._hashCode = TypeKey.hash(javaType, false);
        }

        public void resetUntyped(Class<?> class_) {
            this._type = null;
            this._class = class_;
            this._isTyped = false;
            this._hashCode = TypeKey.hash(class_, false);
        }

        public final String toString() {
            if (this._class != null) {
                return "{class: " + this._class.getName() + ", typed? " + this._isTyped + "}";
            }
            return "{type: " + this._type + ", typed? " + this._isTyped + "}";
        }
    }

}


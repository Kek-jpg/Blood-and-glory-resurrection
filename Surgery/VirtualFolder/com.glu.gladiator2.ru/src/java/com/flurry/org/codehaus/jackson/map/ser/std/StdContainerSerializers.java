/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.util.Iterator
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.ser.impl.PropertySerializerMap;
import com.flurry.org.codehaus.jackson.map.ser.std.AsArraySerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.CollectionSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.ContainerSerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.EnumSetSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.IterableSerializer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class StdContainerSerializers {
    protected StdContainerSerializers() {
    }

    public static ContainerSerializerBase<?> collectionSerializer(JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty, JsonSerializer<Object> jsonSerializer) {
        return new CollectionSerializer(javaType, bl, typeSerializer, beanProperty, jsonSerializer);
    }

    public static JsonSerializer<?> enumSetSerializer(JavaType javaType, BeanProperty beanProperty) {
        return new EnumSetSerializer(javaType, beanProperty);
    }

    public static ContainerSerializerBase<?> indexedListSerializer(JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty, JsonSerializer<Object> jsonSerializer) {
        return new IndexedListSerializer(javaType, bl, typeSerializer, beanProperty, jsonSerializer);
    }

    public static ContainerSerializerBase<?> iterableSerializer(JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty) {
        return new IterableSerializer(javaType, bl, typeSerializer, beanProperty);
    }

    public static ContainerSerializerBase<?> iteratorSerializer(JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty) {
        return new IteratorSerializer(javaType, bl, typeSerializer, beanProperty);
    }

    @JacksonStdImpl
    public static class IndexedListSerializer
    extends AsArraySerializerBase<List<?>> {
        public IndexedListSerializer(JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty, JsonSerializer<Object> jsonSerializer) {
            super(List.class, javaType, bl, typeSerializer, beanProperty, jsonSerializer);
        }

        @Override
        public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return new IndexedListSerializer(this._elementType, this._staticTyping, typeSerializer, this._property, this._elementSerializer);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void serializeContents(List<?> var1, JsonGenerator var2_3, SerializerProvider var3_2) throws IOException, JsonGenerationException {
            if (this._elementSerializer != null) {
                this.serializeContentsUsing(var1, var2_3, var3_2, this._elementSerializer);
                return;
            }
            if (this._valueTypeSerializer != null) {
                this.serializeTypedContents(var1, var2_3, var3_2);
                return;
            }
            var4_4 = var1.size();
            if (var4_4 == 0) return;
            var5_5 = 0;
            try {
                var7_6 = this._dynamicSerializers;
lbl12: // 2 sources:
                do {
                    if (var5_5 >= var4_4) return;
                    var8_7 = var1.get(var5_5);
                    if (var8_7 == null) {
                        var3_2.defaultSerializeNull(var2_3);
                    } else {
                        var9_9 = var8_7.getClass();
                        var10_10 = var7_6.serializerFor(var9_9);
                        if (var10_10 == null) {
                            if (this._elementType.hasGenericTypes()) {
                                var10_10 = this._findAndAddDynamic(var7_6, var3_2.constructSpecializedType(this._elementType, var9_9), var3_2);
                            } else {
                                var11_8 = this._findAndAddDynamic(var7_6, var9_9, var3_2);
                                var10_10 = var11_8;
                            }
                            var7_6 = this._dynamicSerializers;
                        }
                        var10_10.serialize(var8_7, var2_3, var3_2);
                    }
                    break;
                } while (true);
            }
            catch (Exception var6_11) {
                this.wrapAndThrow(var3_2, (Throwable)var6_11, (Object)var1, var5_5);
                return;
            }
            ++var5_5;
            ** while (true)
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public void serializeContentsUsing(List<?> var1_1, JsonGenerator var2_4, SerializerProvider var3_3, JsonSerializer<Object> var4) throws IOException, JsonGenerationException {
            var5_5 = var1_1.size();
            if (var5_5 == 0) {
                return;
            }
            var6_6 = this._valueTypeSerializer;
            var7_7 = 0;
            while (var7_7 < var5_5) {
                block6 : {
                    var8_8 = var1_1.get(var7_7);
                    if (var8_8 != null) ** GOTO lbl12
                    try {
                        var3_3.defaultSerializeNull(var2_4);
                        break block6;
lbl12: // 1 sources:
                        if (var6_6 == null) {
                            var4.serialize(var8_8, var2_4, var3_3);
                        }
                        var4.serializeWithType(var8_8, var2_4, var3_3, var6_6);
                    }
                    catch (Exception var9_9) {
                        this.wrapAndThrow(var3_3, (Throwable)var9_9, (Object)var1_1, var7_7);
                    }
                }
                ++var7_7;
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public void serializeTypedContents(List<?> var1, JsonGenerator var2_3, SerializerProvider var3_2) throws IOException, JsonGenerationException {
            var4_4 = var1.size();
            if (var4_4 == 0) {
                return;
            }
            var5_5 = 0;
            try {
                var7_6 = this._valueTypeSerializer;
                var8_7 = this._dynamicSerializers;
lbl8: // 2 sources:
                do {
                    if (var5_5 >= var4_4) return;
                    var9_9 = var1.get(var5_5);
                    if (var9_9 == null) {
                        var3_2.defaultSerializeNull(var2_3);
                    } else {
                        var10_11 = var9_9.getClass();
                        var11_8 = var8_7.serializerFor(var10_11);
                        if (var11_8 == null) {
                            if (this._elementType.hasGenericTypes()) {
                                var11_8 = this._findAndAddDynamic(var8_7, var3_2.constructSpecializedType(this._elementType, var10_11), var3_2);
                            } else {
                                var12_10 = this._findAndAddDynamic(var8_7, var10_11, var3_2);
                                var11_8 = var12_10;
                            }
                            var8_7 = this._dynamicSerializers;
                        }
                        var11_8.serializeWithType(var9_9, var2_3, var3_2, var7_6);
                    }
                    break;
                } while (true);
            }
            catch (Exception var6_12) {
                this.wrapAndThrow(var3_2, (Throwable)var6_12, (Object)var1, var5_5);
                return;
            }
            ++var5_5;
            ** while (true)
        }
    }

    @JacksonStdImpl
    public static class IteratorSerializer
    extends AsArraySerializerBase<Iterator<?>> {
        public IteratorSerializer(JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty) {
            super(Iterator.class, javaType, bl, typeSerializer, beanProperty, null);
        }

        @Override
        public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return new IteratorSerializer(this._elementType, this._staticTyping, typeSerializer, this._property);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void serializeContents(Iterator<?> iterator, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            if (iterator.hasNext()) {
                TypeSerializer typeSerializer = this._valueTypeSerializer;
                JsonSerializer<Object> jsonSerializer = null;
                Class class_ = null;
                do {
                    JsonSerializer<Object> jsonSerializer2;
                    Object object;
                    if ((object = iterator.next()) == null) {
                        serializerProvider.defaultSerializeNull(jsonGenerator);
                        continue;
                    }
                    Class class_2 = object.getClass();
                    if (class_2 == class_) {
                        jsonSerializer2 = jsonSerializer;
                    } else {
                        jsonSerializer = jsonSerializer2 = serializerProvider.findValueSerializer(class_2, this._property);
                        class_ = class_2;
                    }
                    if (typeSerializer == null) {
                        jsonSerializer2.serialize(object, jsonGenerator, serializerProvider);
                        continue;
                    }
                    jsonSerializer2.serializeWithType(object, jsonGenerator, serializerProvider, typeSerializer);
                } while (iterator.hasNext());
            }
        }
    }

}


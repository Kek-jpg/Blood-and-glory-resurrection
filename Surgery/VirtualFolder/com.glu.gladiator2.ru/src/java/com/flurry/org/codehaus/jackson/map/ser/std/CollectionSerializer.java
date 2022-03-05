/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Throwable
 *  java.util.Collection
 *  java.util.Iterator
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.ser.impl.PropertySerializerMap;
import com.flurry.org.codehaus.jackson.map.ser.std.AsArraySerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.ContainerSerializerBase;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class CollectionSerializer
extends AsArraySerializerBase<Collection<?>> {
    public CollectionSerializer(JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty, JsonSerializer<Object> jsonSerializer) {
        super(Collection.class, javaType, bl, typeSerializer, beanProperty, jsonSerializer);
    }

    @Override
    public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new CollectionSerializer(this._elementType, this._staticTyping, typeSerializer, this._property, this._elementSerializer);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void serializeContents(Collection<?> var1, JsonGenerator var2_3, SerializerProvider var3_2) throws IOException, JsonGenerationException {
        if (this._elementSerializer != null) {
            this.serializeContentsUsing(var1, var2_3, var3_2, this._elementSerializer);
            return;
        }
        var4_4 = var1.iterator();
        if (var4_4.hasNext() == false) return;
        var5_5 = this._dynamicSerializers;
        var6_6 = this._valueTypeSerializer;
        var7_7 = 0;
        try {
            do {
                block7 : {
                    block9 : {
                        block8 : {
                            if ((var9_9 = var4_4.next()) != null) break block8;
                            var3_2.defaultSerializeNull(var2_3);
                            break block7;
                        }
                        var10_10 = var9_9.getClass();
                        var11_8 = var5_5.serializerFor(var10_10);
                        if (var11_8 != null) ** GOTO lbl25
                        if (!this._elementType.hasGenericTypes()) break block9;
                        var11_8 = this._findAndAddDynamic(var5_5, var3_2.constructSpecializedType(this._elementType, var10_10), var3_2);
                        ** GOTO lbl24
                    }
                    var11_8 = this._findAndAddDynamic(var5_5, var10_10, var3_2);
lbl24: // 2 sources:
                    var5_5 = this._dynamicSerializers;
lbl25: // 2 sources:
                    if (var6_6 == null) {
                        var11_8.serialize(var9_9, var2_3, var3_2);
                        break block7;
                    }
                    var11_8.serializeWithType(var9_9, var2_3, var3_2, var6_6);
                }
                ++var7_7;
            } while (var4_4.hasNext());
            return;
        }
        catch (Exception var8_11) {
            this.wrapAndThrow(var3_2, (Throwable)var8_11, (Object)var1, var7_7);
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void serializeContentsUsing(Collection<?> collection, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, JsonSerializer<Object> jsonSerializer) throws IOException, JsonGenerationException {
        Iterator iterator = collection.iterator();
        if (!iterator.hasNext()) return;
        TypeSerializer typeSerializer = this._valueTypeSerializer;
        int n = 0;
        do {
            Object object;
            if ((object = iterator.next()) == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
            } else if (typeSerializer == null) {
                jsonSerializer.serialize(object, jsonGenerator, serializerProvider);
            } else {
                jsonSerializer.serializeWithType(object, jsonGenerator, serializerProvider, typeSerializer);
            }
            ++n;
            continue;
            catch (Exception exception) {
                this.wrapAndThrow(serializerProvider, (Throwable)exception, (Object)collection, n);
            }
        } while (iterator.hasNext());
    }
}


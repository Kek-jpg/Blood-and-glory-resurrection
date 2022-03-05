/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.Collection
 *  java.util.Iterator
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.ResolvableSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.ser.std.StaticListSerializerBase;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@JacksonStdImpl
public class StringCollectionSerializer
extends StaticListSerializerBase<Collection<String>>
implements ResolvableSerializer {
    protected JsonSerializer<String> _serializer;

    public StringCollectionSerializer(BeanProperty beanProperty) {
        super(Collection.class, beanProperty);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final void serializeContents(Collection<String> var1, JsonGenerator var2_3, SerializerProvider var3_2) throws IOException, JsonGenerationException {
        if (this._serializer != null) {
            StringCollectionSerializer.super.serializeUsingCustom(var1, var2_3, var3_2);
            return;
        }
        var4_4 = 0;
        var5_5 = var1.iterator();
        while (var5_5.hasNext() != false) {
            block4 : {
                var6_7 = (String)var5_5.next();
                if (var6_7 != null) ** GOTO lbl12
                try {
                    var3_2.defaultSerializeNull(var2_3);
                    break block4;
lbl12: // 1 sources:
                    var2_3.writeString(var6_7);
                }
                catch (Exception var7_6) {
                    this.wrapAndThrow(var3_2, (Throwable)var7_6, (Object)var1, var4_4);
                    continue;
                }
            }
            ++var4_4;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void serializeUsingCustom(Collection<String> var1, JsonGenerator var2_3, SerializerProvider var3_2) throws IOException, JsonGenerationException {
        var4_4 = this._serializer;
        var5_5 = var1.iterator();
        while (var5_5.hasNext() != false) {
            var6_7 = (String)var5_5.next();
            if (var6_7 != null) ** GOTO lbl9
            try {
                var3_2.defaultSerializeNull(var2_3);
lbl9: // 1 sources:
                var4_4.serialize(var6_7, var2_3, var3_2);
            }
            catch (Exception var7_6) {
                this.wrapAndThrow(var3_2, (Throwable)var7_6, (Object)var1, 0);
            }
        }
    }

    @Override
    protected JsonNode contentSchema() {
        return this.createSchemaNode("string", true);
    }

    @Override
    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer = serializerProvider.findValueSerializer(String.class, this._property);
        if (!this.isDefaultSerializer(jsonSerializer)) {
            this._serializer = jsonSerializer;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serialize(Collection<String> collection, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartArray();
        if (this._serializer == null) {
            StringCollectionSerializer.super.serializeContents(collection, jsonGenerator, serializerProvider);
        } else {
            StringCollectionSerializer.super.serializeUsingCustom(collection, jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndArray();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeWithType(Collection<String> collection, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForArray(collection, jsonGenerator);
        if (this._serializer == null) {
            StringCollectionSerializer.super.serializeContents(collection, jsonGenerator, serializerProvider);
        } else {
            StringCollectionSerializer.super.serializeUsingCustom(collection, jsonGenerator, serializerProvider);
        }
        typeSerializer.writeTypeSuffixForArray(collection, jsonGenerator);
    }
}


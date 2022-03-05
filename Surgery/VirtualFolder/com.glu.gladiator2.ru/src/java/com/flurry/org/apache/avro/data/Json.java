/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.node.ArrayNode
 *  com.flurry.org.codehaus.jackson.node.BooleanNode
 *  com.flurry.org.codehaus.jackson.node.DoubleNode
 *  com.flurry.org.codehaus.jackson.node.JsonNodeFactory
 *  com.flurry.org.codehaus.jackson.node.LongNode
 *  com.flurry.org.codehaus.jackson.node.NullNode
 *  com.flurry.org.codehaus.jackson.node.ObjectNode
 *  com.flurry.org.codehaus.jackson.node.TextNode
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Enum
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.Iterator
 */
package com.flurry.org.apache.avro.data;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.DatumReader;
import com.flurry.org.apache.avro.io.DatumWriter;
import com.flurry.org.apache.avro.io.Decoder;
import com.flurry.org.apache.avro.io.DecoderFactory;
import com.flurry.org.apache.avro.io.Encoder;
import com.flurry.org.apache.avro.io.ResolvingDecoder;
import com.flurry.org.apache.avro.io.ValidatingDecoder;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.node.ArrayNode;
import com.flurry.org.codehaus.jackson.node.BooleanNode;
import com.flurry.org.codehaus.jackson.node.DoubleNode;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.node.LongNode;
import com.flurry.org.codehaus.jackson.node.NullNode;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.node.TextNode;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class Json {
    public static final Schema SCHEMA;

    static {
        try {
            SCHEMA = Schema.parse(Json.class.getResourceAsStream("/com/flurry/org/apache/avro/data/Json.avsc"));
            return;
        }
        catch (IOException iOException) {
            throw new AvroRuntimeException(iOException);
        }
    }

    private Json() {
    }

    public static JsonNode read(Decoder decoder) throws IOException {
        switch (JsonType.values()[decoder.readIndex()]) {
            default: {
                throw new AvroRuntimeException("Unexpected Json node type");
            }
            case LONG: {
                return new LongNode(decoder.readLong());
            }
            case DOUBLE: {
                return new DoubleNode(decoder.readDouble());
            }
            case STRING: {
                return new TextNode(decoder.readString());
            }
            case BOOLEAN: {
                if (decoder.readBoolean()) {
                    return BooleanNode.TRUE;
                }
                return BooleanNode.FALSE;
            }
            case NULL: {
                decoder.readNull();
                return NullNode.getInstance();
            }
            case ARRAY: {
                ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
                long l2 = decoder.readArrayStart();
                while (l2 > 0L) {
                    for (long i2 = 0L; i2 < l2; ++i2) {
                        arrayNode.add(Json.read(decoder));
                    }
                    l2 = decoder.arrayNext();
                }
                return arrayNode;
            }
            case OBJECT: 
        }
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        long l3 = decoder.readMapStart();
        while (l3 > 0L) {
            for (long i3 = 0L; i3 < l3; ++i3) {
                objectNode.put(decoder.readString(), Json.read(decoder));
            }
            l3 = decoder.mapNext();
        }
        return objectNode;
    }

    public static void write(JsonNode jsonNode, Encoder encoder) throws IOException {
        switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonNode.asToken().ordinal()]) {
            default: {
                throw new AvroRuntimeException((Object)((Object)jsonNode.asToken()) + " unexpected: " + jsonNode);
            }
            case 1: {
                encoder.writeIndex(JsonType.LONG.ordinal());
                encoder.writeLong(jsonNode.getLongValue());
                return;
            }
            case 2: {
                encoder.writeIndex(JsonType.DOUBLE.ordinal());
                encoder.writeDouble(jsonNode.getDoubleValue());
                return;
            }
            case 3: {
                encoder.writeIndex(JsonType.STRING.ordinal());
                encoder.writeString(jsonNode.getTextValue());
                return;
            }
            case 4: {
                encoder.writeIndex(JsonType.BOOLEAN.ordinal());
                encoder.writeBoolean(true);
                return;
            }
            case 5: {
                encoder.writeIndex(JsonType.BOOLEAN.ordinal());
                encoder.writeBoolean(false);
                return;
            }
            case 6: {
                encoder.writeIndex(JsonType.NULL.ordinal());
                encoder.writeNull();
                return;
            }
            case 7: {
                encoder.writeIndex(JsonType.ARRAY.ordinal());
                encoder.writeArrayStart();
                encoder.setItemCount(jsonNode.size());
                for (JsonNode jsonNode2 : jsonNode) {
                    encoder.startItem();
                    Json.write(jsonNode2, encoder);
                }
                encoder.writeArrayEnd();
                return;
            }
            case 8: 
        }
        encoder.writeIndex(JsonType.OBJECT.ordinal());
        encoder.writeMapStart();
        encoder.setItemCount(jsonNode.size());
        Iterator<String> iterator = jsonNode.getFieldNames();
        while (iterator.hasNext()) {
            encoder.startItem();
            String string = (String)iterator.next();
            encoder.writeString(string);
            Json.write(jsonNode.get(string), encoder);
        }
        encoder.writeMapEnd();
    }

    private static final class JsonType
    extends Enum<JsonType> {
        private static final /* synthetic */ JsonType[] $VALUES;
        public static final /* enum */ JsonType ARRAY;
        public static final /* enum */ JsonType BOOLEAN;
        public static final /* enum */ JsonType DOUBLE;
        public static final /* enum */ JsonType LONG;
        public static final /* enum */ JsonType NULL;
        public static final /* enum */ JsonType OBJECT;
        public static final /* enum */ JsonType STRING;

        static {
            LONG = new JsonType();
            DOUBLE = new JsonType();
            STRING = new JsonType();
            BOOLEAN = new JsonType();
            NULL = new JsonType();
            ARRAY = new JsonType();
            OBJECT = new JsonType();
            JsonType[] arrjsonType = new JsonType[]{LONG, DOUBLE, STRING, BOOLEAN, NULL, ARRAY, OBJECT};
            $VALUES = arrjsonType;
        }

        public static JsonType valueOf(String string) {
            return (JsonType)Enum.valueOf(JsonType.class, (String)string);
        }

        public static JsonType[] values() {
            return (JsonType[])$VALUES.clone();
        }
    }

    public static class Reader
    implements DatumReader<JsonNode> {
        private ResolvingDecoder resolver;
        private Schema written;

        @Override
        public JsonNode read(JsonNode jsonNode, Decoder decoder) throws IOException {
            if (this.written == null) {
                return Json.read(decoder);
            }
            if (this.resolver == null) {
                this.resolver = DecoderFactory.get().resolvingDecoder(this.written, Json.SCHEMA, null);
            }
            this.resolver.configure(decoder);
            JsonNode jsonNode2 = Json.read(this.resolver);
            this.resolver.drain();
            return jsonNode2;
        }

        @Override
        public void setSchema(Schema schema) {
            if (Json.SCHEMA.equals(this.written)) {
                schema = null;
            }
            this.written = schema;
        }
    }

    public static class Writer
    implements DatumWriter<JsonNode> {
        @Override
        public void setSchema(Schema schema) {
            if (!Json.SCHEMA.equals(schema)) {
                throw new RuntimeException("Not the Json schema: " + schema);
            }
        }

        @Override
        public void write(JsonNode jsonNode, Encoder encoder) throws IOException {
            Json.write(jsonNode, encoder);
        }
    }

}


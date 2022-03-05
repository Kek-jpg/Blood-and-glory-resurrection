/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Deprecated
 *  java.lang.Float
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.ThreadLocal
 *  java.nio.ByteBuffer
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.org.apache.avro.generic;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.generic.GenericArray;
import com.flurry.org.apache.avro.generic.GenericData;
import com.flurry.org.apache.avro.generic.GenericFixed;
import com.flurry.org.apache.avro.io.DatumReader;
import com.flurry.org.apache.avro.io.Decoder;
import com.flurry.org.apache.avro.io.DecoderFactory;
import com.flurry.org.apache.avro.io.ResolvingDecoder;
import com.flurry.org.apache.avro.io.ValidatingDecoder;
import com.flurry.org.apache.avro.util.Utf8;
import com.flurry.org.apache.avro.util.WeakIdentityHashMap;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GenericDatumReader<D>
implements DatumReader<D> {
    private static final ThreadLocal<Map<Schema, Map<Schema, ResolvingDecoder>>> RESOLVER_CACHE = new ThreadLocal<Map<Schema, Map<Schema, ResolvingDecoder>>>(){

        protected Map<Schema, Map<Schema, ResolvingDecoder>> initialValue() {
            return new WeakIdentityHashMap<Schema, Map<Schema, ResolvingDecoder>>();
        }
    };
    private Schema actual;
    private final Thread creator;
    private ResolvingDecoder creatorResolver;
    private final GenericData data;
    private Schema expected;

    public GenericDatumReader() {
        this(null, null, GenericData.get());
    }

    public GenericDatumReader(Schema schema) {
        super(schema, schema, GenericData.get());
    }

    public GenericDatumReader(Schema schema, Schema schema2) {
        super(schema, schema2, GenericData.get());
    }

    protected GenericDatumReader(Schema schema, Schema schema2, GenericData genericData) {
        this.creatorResolver = null;
        this.actual = schema;
        this.expected = schema2;
        this.data = genericData;
        this.creator = Thread.currentThread();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static void skip(Schema schema, Decoder decoder) throws IOException {
        switch (2.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()]) {
            default: {
                throw new RuntimeException("Unknown type: " + schema);
            }
            case 1: {
                Iterator iterator = schema.getFields().iterator();
                while (iterator.hasNext()) {
                    GenericDatumReader.skip(((Schema.Field)iterator.next()).schema(), decoder);
                }
                return;
            }
            case 2: {
                decoder.readInt();
            }
            case 14: {
                return;
            }
            case 3: {
                Schema schema2 = schema.getElementType();
                long l2 = decoder.skipArray();
                while (l2 > 0L) {
                    for (long i2 = 0L; i2 < l2; ++i2) {
                        GenericDatumReader.skip(schema2, decoder);
                    }
                    l2 = decoder.skipArray();
                }
                return;
            }
            case 4: {
                Schema schema3 = schema.getValueType();
                long l3 = decoder.skipMap();
                while (l3 > 0L) {
                    for (long i3 = 0L; i3 < l3; ++i3) {
                        decoder.skipString();
                        GenericDatumReader.skip(schema3, decoder);
                    }
                    l3 = decoder.skipMap();
                }
                return;
            }
            case 5: {
                GenericDatumReader.skip((Schema)schema.getTypes().get(decoder.readIndex()), decoder);
                return;
            }
            case 6: {
                decoder.skipFixed(schema.getFixedSize());
                return;
            }
            case 7: {
                decoder.skipString();
                return;
            }
            case 8: {
                decoder.skipBytes();
                return;
            }
            case 9: {
                decoder.readInt();
                return;
            }
            case 10: {
                decoder.readLong();
                return;
            }
            case 11: {
                decoder.readFloat();
                return;
            }
            case 12: {
                decoder.readDouble();
                return;
            }
            case 13: 
        }
        decoder.readBoolean();
    }

    protected void addToArray(Object object, long l2, Object object2) {
        ((Collection)object).add(object2);
    }

    protected void addToMap(Object object, Object object2, Object object3) {
        ((Map)object).put(object2, object3);
    }

    protected Object createBytes(byte[] arrby) {
        return ByteBuffer.wrap((byte[])arrby);
    }

    protected Object createEnum(String string, Schema schema) {
        return new GenericData.EnumSymbol(schema, string);
    }

    @Deprecated
    protected Object createFixed(Object object, Schema schema) {
        return this.data.createFixed(object, schema);
    }

    @Deprecated
    protected Object createFixed(Object object, byte[] arrby, Schema schema) {
        return this.data.createFixed(object, arrby, schema);
    }

    protected Object createString(String string) {
        return new Utf8(string);
    }

    public GenericData getData() {
        return this.data;
    }

    public Schema getExpected() {
        return this.expected;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final ResolvingDecoder getResolver(Schema schema, Schema schema2) throws IOException {
        ResolvingDecoder resolvingDecoder;
        Thread thread = Thread.currentThread();
        if (thread == this.creator && this.creatorResolver != null) {
            return this.creatorResolver;
        }
        WeakIdentityHashMap weakIdentityHashMap = (WeakIdentityHashMap)((Map)RESOLVER_CACHE.get()).get((Object)schema);
        if (weakIdentityHashMap == null) {
            weakIdentityHashMap = new WeakIdentityHashMap();
            ((Map)RESOLVER_CACHE.get()).put((Object)schema, weakIdentityHashMap);
        }
        if ((resolvingDecoder = (ResolvingDecoder)weakIdentityHashMap.get((Object)schema2)) == null) {
            resolvingDecoder = DecoderFactory.get().resolvingDecoder(Schema.applyAliases(schema, schema2), schema2, null);
            weakIdentityHashMap.put((Object)schema2, (Object)resolvingDecoder);
        }
        if (thread != this.creator) return resolvingDecoder;
        this.creatorResolver = resolvingDecoder;
        return resolvingDecoder;
    }

    public Schema getSchema() {
        return this.actual;
    }

    protected Object newArray(Object object, int n2, Schema schema) {
        if (object instanceof Collection) {
            ((Collection)object).clear();
            return object;
        }
        return new GenericData.Array(n2, schema);
    }

    protected Object newMap(Object object, int n2) {
        if (object instanceof Map) {
            ((Map)object).clear();
            return object;
        }
        return new HashMap(n2);
    }

    @Deprecated
    protected Object newRecord(Object object, Schema schema) {
        return this.data.newRecord(object, schema);
    }

    protected Object peekArray(Object object) {
        if (object instanceof GenericArray) {
            return ((GenericArray)object).peek();
        }
        return null;
    }

    protected Object read(Object object, Schema schema, ResolvingDecoder resolvingDecoder) throws IOException {
        switch (2.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()]) {
            default: {
                throw new AvroRuntimeException("Unknown type: " + schema);
            }
            case 1: {
                return this.readRecord(object, schema, resolvingDecoder);
            }
            case 2: {
                return this.readEnum(schema, resolvingDecoder);
            }
            case 3: {
                return this.readArray(object, schema, resolvingDecoder);
            }
            case 4: {
                return this.readMap(object, schema, resolvingDecoder);
            }
            case 5: {
                return this.read(object, (Schema)schema.getTypes().get(resolvingDecoder.readIndex()), resolvingDecoder);
            }
            case 6: {
                return this.readFixed(object, schema, resolvingDecoder);
            }
            case 7: {
                return this.readString(object, schema, resolvingDecoder);
            }
            case 8: {
                return this.readBytes(object, resolvingDecoder);
            }
            case 9: {
                return this.readInt(object, schema, resolvingDecoder);
            }
            case 10: {
                return resolvingDecoder.readLong();
            }
            case 11: {
                return Float.valueOf((float)resolvingDecoder.readFloat());
            }
            case 12: {
                return resolvingDecoder.readDouble();
            }
            case 13: {
                return resolvingDecoder.readBoolean();
            }
            case 14: 
        }
        resolvingDecoder.readNull();
        return null;
    }

    @Override
    public D read(D d2, Decoder decoder) throws IOException {
        ResolvingDecoder resolvingDecoder = this.getResolver(this.actual, this.expected);
        resolvingDecoder.configure(decoder);
        Object object = this.read(d2, this.expected, resolvingDecoder);
        resolvingDecoder.drain();
        return (D)object;
    }

    protected Object readArray(Object object, Schema schema, ResolvingDecoder resolvingDecoder) throws IOException {
        Schema schema2 = schema.getElementType();
        long l2 = resolvingDecoder.readArrayStart();
        long l3 = 0L;
        if (l2 > 0L) {
            Object object2 = this.newArray(object, (int)l2, schema);
            do {
                for (long i2 = 0L; i2 < l2; ++i2) {
                    this.addToArray(object2, l3 + i2, this.read(this.peekArray(object2), schema2, resolvingDecoder));
                }
                l3 += l2;
            } while ((l2 = resolvingDecoder.arrayNext()) > 0L);
            return object2;
        }
        return this.newArray(object, 0, schema);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Object readBytes(Object object, Decoder decoder) throws IOException {
        ByteBuffer byteBuffer;
        if (object instanceof ByteBuffer) {
            byteBuffer = (ByteBuffer)object;
            do {
                return decoder.readBytes(byteBuffer);
                break;
            } while (true);
        }
        byteBuffer = null;
        return decoder.readBytes(byteBuffer);
    }

    protected Object readEnum(Schema schema, Decoder decoder) throws IOException {
        return this.createEnum((String)schema.getEnumSymbols().get(decoder.readEnum()), schema);
    }

    protected Object readFixed(Object object, Schema schema, Decoder decoder) throws IOException {
        GenericFixed genericFixed = (GenericFixed)this.data.createFixed(object, schema);
        decoder.readFixed(genericFixed.bytes(), 0, schema.getFixedSize());
        return genericFixed;
    }

    protected Object readInt(Object object, Schema schema, Decoder decoder) throws IOException {
        return decoder.readInt();
    }

    protected Object readMap(Object object, Schema schema, ResolvingDecoder resolvingDecoder) throws IOException {
        Schema schema2 = schema.getValueType();
        long l2 = resolvingDecoder.readMapStart();
        Object object2 = this.newMap(object, (int)l2);
        if (l2 > 0L) {
            do {
                int n2 = 0;
                while ((long)n2 < l2) {
                    this.addToMap(object2, this.readString(null, schema, resolvingDecoder), this.read(null, schema2, resolvingDecoder));
                    ++n2;
                }
            } while ((l2 = resolvingDecoder.mapNext()) > 0L);
        }
        return object2;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object readRecord(Object object, Schema schema, ResolvingDecoder resolvingDecoder) throws IOException {
        Object object2 = this.data.newRecord(object, schema);
        Object object3 = this.data.getRecordState(object2, schema);
        Schema.Field[] arrfield = resolvingDecoder.readFieldOrder();
        int n2 = arrfield.length;
        int n3 = 0;
        while (n3 < n2) {
            Schema.Field field = arrfield[n3];
            int n4 = field.pos();
            String string = field.name();
            Object object4 = object != null ? this.data.getField(object2, string, n4, object3) : null;
            this.data.setField(object2, string, n4, this.read(object4, field.schema(), resolvingDecoder), object3);
            ++n3;
        }
        return object2;
    }

    protected Object readString(Object object, Schema schema, Decoder decoder) throws IOException {
        if ("String".equals((Object)schema.getProp("avro.java.string"))) {
            return decoder.readString();
        }
        return this.readString(object, decoder);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Object readString(Object object, Decoder decoder) throws IOException {
        Utf8 utf8;
        if (object instanceof Utf8) {
            utf8 = (Utf8)object;
            do {
                return decoder.readString(utf8);
                break;
            } while (true);
        }
        utf8 = null;
        return decoder.readString(utf8);
    }

    public void setExpected(Schema schema) {
        this.expected = schema;
        this.creatorResolver = null;
    }

    @Override
    public void setSchema(Schema schema) {
        this.actual = schema;
        if (this.expected == null) {
            this.expected = this.actual;
        }
        this.creatorResolver = null;
    }

}


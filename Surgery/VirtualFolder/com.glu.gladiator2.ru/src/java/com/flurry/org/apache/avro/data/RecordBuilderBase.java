/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.Arrays
 *  java.util.Iterator
 *  java.util.List
 *  java.util.concurrent.ConcurrentHashMap
 *  java.util.concurrent.ConcurrentMap
 */
package com.flurry.org.apache.avro.data;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.data.RecordBuilder;
import com.flurry.org.apache.avro.generic.GenericData;
import com.flurry.org.apache.avro.generic.IndexedRecord;
import com.flurry.org.apache.avro.io.BinaryDecoder;
import com.flurry.org.apache.avro.io.BinaryEncoder;
import com.flurry.org.apache.avro.io.DatumReader;
import com.flurry.org.apache.avro.io.Decoder;
import com.flurry.org.apache.avro.io.DecoderFactory;
import com.flurry.org.apache.avro.io.EncoderFactory;
import com.flurry.org.apache.avro.io.parsing.ResolvingGrammarGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class RecordBuilderBase<T extends IndexedRecord>
implements RecordBuilder<T> {
    private static final ConcurrentMap<String, ConcurrentMap<Integer, Object>> DEFAULT_VALUE_CACHE = new ConcurrentHashMap();
    private static final Schema.Field[] EMPTY_FIELDS = new Schema.Field[0];
    private final GenericData data;
    private BinaryDecoder decoder = null;
    private BinaryEncoder encoder = null;
    private final boolean[] fieldSetFlags;
    private final Schema.Field[] fields;
    private final Schema schema;

    protected RecordBuilderBase(Schema schema, GenericData genericData) {
        this.schema = schema;
        this.data = genericData;
        this.fields = (Schema.Field[])schema.getFields().toArray((Object[])EMPTY_FIELDS);
        this.fieldSetFlags = new boolean[this.fields.length];
    }

    protected RecordBuilderBase(RecordBuilderBase<T> recordBuilderBase, GenericData genericData) {
        this.schema = recordBuilderBase.schema;
        this.data = genericData;
        this.fields = (Schema.Field[])this.schema.getFields().toArray((Object[])EMPTY_FIELDS);
        this.fieldSetFlags = new boolean[recordBuilderBase.fieldSetFlags.length];
        System.arraycopy((Object)recordBuilderBase.fieldSetFlags, (int)0, (Object)this.fieldSetFlags, (int)0, (int)this.fieldSetFlags.length);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static boolean isValidValue(Schema.Field field, Object object) {
        Schema.Type type;
        Schema schema;
        if (object != null || (type = (schema = field.schema()).getType()) == Schema.Type.NULL) {
            return true;
        }
        if (type == Schema.Type.UNION) {
            Iterator iterator = schema.getTypes().iterator();
            while (iterator.hasNext()) {
                if (((Schema)iterator.next()).getType() != Schema.Type.NULL) continue;
                return true;
            }
        }
        return false;
    }

    protected final GenericData data() {
        return this.data;
    }

    protected Object defaultValue(Schema.Field field) throws IOException {
        Object object;
        JsonNode jsonNode = field.defaultValue();
        if (jsonNode == null) {
            throw new AvroRuntimeException("Field " + field + " not set and has no default value");
        }
        if (jsonNode.isNull() && (field.schema().getType() == Schema.Type.NULL || field.schema().getType() == Schema.Type.UNION && ((Schema)field.schema().getTypes().get(0)).getType() == Schema.Type.NULL)) {
            return null;
        }
        ConcurrentMap concurrentMap = (ConcurrentMap)DEFAULT_VALUE_CACHE.get((Object)this.schema.getFullName());
        if (concurrentMap == null) {
            DEFAULT_VALUE_CACHE.putIfAbsent((Object)this.schema.getFullName(), (Object)new ConcurrentHashMap(this.fields.length));
            concurrentMap = (ConcurrentMap)DEFAULT_VALUE_CACHE.get((Object)this.schema.getFullName());
        }
        if ((object = concurrentMap.get((Object)field.pos())) == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            this.encoder = EncoderFactory.get().binaryEncoder((OutputStream)byteArrayOutputStream, this.encoder);
            ResolvingGrammarGenerator.encode(this.encoder, field.schema(), jsonNode);
            this.encoder.flush();
            this.decoder = DecoderFactory.get().binaryDecoder(byteArrayOutputStream.toByteArray(), this.decoder);
            object = this.data.createDatumReader(field.schema()).read(null, this.decoder);
            concurrentMap.putIfAbsent((Object)field.pos(), object);
        }
        return this.data.deepCopy(field.schema(), object);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        RecordBuilderBase recordBuilderBase = (RecordBuilderBase)object;
        if (!Arrays.equals((boolean[])this.fieldSetFlags, (boolean[])recordBuilderBase.fieldSetFlags)) {
            return false;
        }
        if (this.schema == null) {
            if (recordBuilderBase.schema == null) return true;
            return false;
        }
        if (!this.schema.equals(recordBuilderBase.schema)) return false;
        return true;
    }

    protected final boolean[] fieldSetFlags() {
        return this.fieldSetFlags;
    }

    protected final Schema.Field[] fields() {
        return this.fields;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n2;
        int n3 = 31 * (31 + Arrays.hashCode((boolean[])this.fieldSetFlags));
        if (this.schema == null) {
            n2 = 0;
            do {
                return n3 + n2;
                break;
            } while (true);
        }
        n2 = this.schema.hashCode();
        return n3 + n2;
    }

    protected final Schema schema() {
        return this.schema;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void validate(Schema.Field field, Object object) {
        if (RecordBuilderBase.isValidValue(field, object) || field.defaultValue() != null) {
            return;
        }
        throw new AvroRuntimeException("Field " + field + " does not accept null values");
    }
}


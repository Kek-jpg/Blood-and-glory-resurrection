/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Comparable
 *  java.lang.Double
 *  java.lang.Enum
 *  java.lang.Float
 *  java.lang.IndexOutOfBoundsException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.UnsupportedOperationException
 *  java.nio.Buffer
 *  java.nio.ByteBuffer
 *  java.util.AbstractList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.org.apache.avro.generic;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.UnresolvedUnionException;
import com.flurry.org.apache.avro.generic.GenericArray;
import com.flurry.org.apache.avro.generic.GenericContainer;
import com.flurry.org.apache.avro.generic.GenericDatumReader;
import com.flurry.org.apache.avro.generic.GenericEnumSymbol;
import com.flurry.org.apache.avro.generic.GenericFixed;
import com.flurry.org.apache.avro.generic.GenericRecord;
import com.flurry.org.apache.avro.generic.IndexedRecord;
import com.flurry.org.apache.avro.io.BinaryData;
import com.flurry.org.apache.avro.io.DatumReader;
import com.flurry.org.apache.avro.util.Utf8;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenericData {
    private static final GenericData INSTANCE = new GenericData();
    private static final Schema STRINGS = Schema.create(Schema.Type.STRING);
    protected static final String STRING_PROP = "avro.java.string";
    protected static final String STRING_TYPE_STRING = "String";

    protected GenericData() {
    }

    public static GenericData get() {
        return INSTANCE;
    }

    public static void setStringType(Schema schema, StringType stringType) {
        if (stringType == StringType.String) {
            schema.addProp(STRING_PROP, STRING_TYPE_STRING);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeEscapedString(String string, StringBuilder stringBuilder) {
        int n2 = 0;
        do {
            block15 : {
                char c2;
                block14 : {
                    if (n2 >= string.length()) {
                        return;
                    }
                    c2 = string.charAt(n2);
                    switch (c2) {
                        default: {
                            if (c2 >= '\u0000' && c2 <= '\u001f' || c2 >= '' && c2 <= '\u009f' || c2 >= '\u2000' && c2 <= '\u20ff') {
                                Integer.toHexString((int)c2);
                                stringBuilder.append("\\u");
                                for (int i2 = 0; i2 < 4 - stringBuilder.length(); ++i2) {
                                    stringBuilder.append('0');
                                }
                                break;
                            }
                            break block14;
                        }
                        case '\"': {
                            stringBuilder.append("\\\"");
                            break block15;
                        }
                        case '\\': {
                            stringBuilder.append("\\\\");
                            break block15;
                        }
                        case '\b': {
                            stringBuilder.append("\\b");
                            break block15;
                        }
                        case '\f': {
                            stringBuilder.append("\\f");
                            break block15;
                        }
                        case '\n': {
                            stringBuilder.append("\\n");
                            break block15;
                        }
                        case '\r': {
                            stringBuilder.append("\\r");
                            break block15;
                        }
                        case '\t': {
                            stringBuilder.append("\\t");
                            break block15;
                        }
                        case '/': {
                            stringBuilder.append("\\/");
                            break block15;
                        }
                    }
                    stringBuilder.append(string.toUpperCase());
                    break block15;
                }
                stringBuilder.append(c2);
            }
            ++n2;
        } while (true);
    }

    public int compare(Object object, Object object2, Schema schema) {
        return this.compare(object, object2, schema, false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected int compare(Object var1_1, Object var2_4, Schema var3_3, boolean var4) {
        if (var1_1 == var2_4) {
            return 0;
        }
        switch (1.$SwitchMap$org$apache$avro$Schema$Type[var3_3.getType().ordinal()]) {
            default: {
                return ((Comparable)var1_1).compareTo(var2_4);
            }
            case 1: {
                var16_5 = var3_3.getFields().iterator();
                do lbl-1000: // 3 sources:
                {
                    if (var16_5.hasNext() == false) return 0;
                    var17_7 = (Schema.Field)var16_5.next();
                    if (var17_7.order() == Schema.Field.Order.IGNORE) ** GOTO lbl-1000
                    var18_9 = var17_7.pos();
                } while ((var20_8 = this.compare(this.getField(var1_1, var19_6 = var17_7.name(), var18_9), this.getField(var2_4, var19_6, var18_9), var17_7.schema(), var4)) == 0);
                if (var17_7.order() != Schema.Field.Order.DESCENDING) return var20_8;
                return -var20_8;
            }
            case 2: {
                return var3_3.getEnumOrdinal(var1_1.toString()) - var3_3.getEnumOrdinal(var2_4.toString());
            }
            case 3: {
                var10_10 = (Collection)var1_1;
                var11_11 = (Collection)var2_4;
                var12_12 = var10_10.iterator();
                var13_13 = var11_11.iterator();
                var14_14 = var3_3.getElementType();
                while (var12_12.hasNext() && var13_13.hasNext()) {
                    var15_15 = this.compare(var12_12.next(), var13_13.next(), var14_14, var4);
                    if (var15_15 == 0) continue;
                    return var15_15;
                }
                if (var12_12.hasNext()) {
                    return 1;
                }
                if (var13_13.hasNext() == false) return 0;
                return -1;
            }
            case 4: {
                if (var4 == false) throw new AvroRuntimeException("Can't compare maps!");
                if (((Map)var1_1).equals(var2_4) == false) return 1;
                return 0;
            }
            case 5: {
                var8_16 = this.resolveUnion(var3_3, var1_1);
                var9_17 = this.resolveUnion(var3_3, var2_4);
                if (var8_16 != var9_17) return var8_16 - var9_17;
                return this.compare(var1_1, var2_4, (Schema)var3_3.getTypes().get(var8_16), var4);
            }
            case 14: {
                return 0;
            }
            case 7: 
        }
        if (var1_1 instanceof Utf8) {
            var6_18 = (Utf8)var1_1;
        } else {
            var5_20 = var1_1.toString();
            var6_18 = new Utf8(var5_20);
        }
        if (var2_4 instanceof Utf8) {
            var7_19 = (Utf8)var2_4;
            return var6_18.compareTo(var7_19);
        }
        var7_19 = new Utf8(var2_4.toString());
        return var6_18.compareTo(var7_19);
    }

    public DatumReader createDatumReader(Schema schema) {
        return new GenericDatumReader(schema, schema, (GenericData)this);
    }

    public Object createFixed(Object object, Schema schema) {
        if (object instanceof GenericFixed && ((GenericFixed)object).bytes().length == schema.getFixedSize()) {
            return object;
        }
        return new Fixed(schema);
    }

    public Object createFixed(Object object, byte[] arrby, Schema schema) {
        GenericFixed genericFixed = (GenericFixed)this.createFixed(object, schema);
        System.arraycopy((Object)arrby, (int)0, (Object)genericFixed.bytes(), (int)0, (int)schema.getFixedSize());
        return genericFixed;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object deepCopy(Schema schema, Object object) {
        if (object == null) {
            return null;
        }
        switch (1.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()]) {
            case 2: {
                return object;
            }
            default: {
                throw new AvroRuntimeException("Deep copy failed for schema \"" + schema + "\" and value \"" + object + "\"");
            }
            case 3: {
                List list = (List)object;
                Array array = new Array(list.size(), schema);
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    Object object2 = iterator.next();
                    array.add(this.deepCopy(schema.getElementType(), object2));
                }
                return array;
            }
            case 13: {
                return new Boolean(((Boolean)object).booleanValue());
            }
            case 8: {
                ByteBuffer byteBuffer = (ByteBuffer)object;
                byte[] arrby = new byte[byteBuffer.capacity()];
                byteBuffer.rewind();
                byteBuffer.get(arrby);
                byteBuffer.rewind();
                return ByteBuffer.wrap((byte[])arrby);
            }
            case 12: {
                return new Double(((Double)object).doubleValue());
            }
            case 6: {
                return this.createFixed(null, ((GenericFixed)object).bytes(), schema);
            }
            case 11: {
                return new Float(((Float)object).floatValue());
            }
            case 9: {
                return new Integer(((Integer)object).intValue());
            }
            case 10: {
                return new Long(((Long)object).longValue());
            }
            case 4: {
                Map map = (Map)object;
                HashMap hashMap = new HashMap(map.size());
                Iterator iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry)iterator.next();
                    hashMap.put((Object)((CharSequence)this.deepCopy(STRINGS, entry.getKey())), this.deepCopy(schema.getValueType(), entry.getValue()));
                }
                return hashMap;
            }
            case 14: {
                return null;
            }
            case 1: {
                IndexedRecord indexedRecord = (IndexedRecord)object;
                IndexedRecord indexedRecord2 = (IndexedRecord)this.newRecord(null, schema);
                Iterator iterator = schema.getFields().iterator();
                while (iterator.hasNext()) {
                    Schema.Field field = (Schema.Field)iterator.next();
                    indexedRecord2.put(field.pos(), this.deepCopy(field.schema(), indexedRecord.get(field.pos())));
                }
                return indexedRecord2;
            }
            case 7: {
                if (object instanceof String) return object;
                if (!(object instanceof Utf8)) return new Utf8(object.toString());
                return new Utf8((Utf8)object);
            }
            case 5: 
        }
        return this.deepCopy((Schema)schema.getTypes().get(this.resolveUnion(schema, object)), object);
    }

    protected Schema getEnumSchema(Object object) {
        return ((GenericContainer)object).getSchema();
    }

    public Object getField(Object object, String string, int n2) {
        return ((IndexedRecord)object).get(n2);
    }

    protected Object getField(Object object, String string, int n2, Object object2) {
        return this.getField(object, string, n2);
    }

    protected Schema getFixedSchema(Object object) {
        return ((GenericContainer)object).getSchema();
    }

    protected Schema getRecordSchema(Object object) {
        return ((GenericContainer)object).getSchema();
    }

    protected Object getRecordState(Object object, Schema schema) {
        return null;
    }

    protected String getSchemaName(Object object) {
        if (object == null) {
            return Schema.Type.NULL.getName();
        }
        if (this.isRecord(object)) {
            return this.getRecordSchema(object).getFullName();
        }
        if (this.isEnum(object)) {
            return this.getEnumSchema(object).getFullName();
        }
        if (this.isArray(object)) {
            return Schema.Type.ARRAY.getName();
        }
        if (this.isMap(object)) {
            return Schema.Type.MAP.getName();
        }
        if (this.isFixed(object)) {
            return this.getFixedSchema(object).getFullName();
        }
        if (this.isString(object)) {
            return Schema.Type.STRING.getName();
        }
        if (this.isBytes(object)) {
            return Schema.Type.BYTES.getName();
        }
        if (object instanceof Integer) {
            return Schema.Type.INT.getName();
        }
        if (object instanceof Long) {
            return Schema.Type.LONG.getName();
        }
        if (object instanceof Float) {
            return Schema.Type.FLOAT.getName();
        }
        if (object instanceof Double) {
            return Schema.Type.DOUBLE.getName();
        }
        if (object instanceof Boolean) {
            return Schema.Type.BOOLEAN.getName();
        }
        throw new AvroRuntimeException("Unknown datum type: " + object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode(Object object, Schema schema) {
        if (object == null) {
            return 0;
        }
        int n2 = 1;
        switch (1.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()]) {
            default: {
                return object.hashCode();
            }
            case 1: {
                Iterator iterator = schema.getFields().iterator();
                while (iterator.hasNext()) {
                    Schema.Field field = (Schema.Field)iterator.next();
                    if (field.order() == Schema.Field.Order.IGNORE) continue;
                    n2 = this.hashCodeAdd(n2, this.getField(object, field.name(), field.pos()), field.schema());
                }
                return n2;
            }
            case 3: {
                Collection collection = (Collection)object;
                Schema schema2 = schema.getElementType();
                Iterator iterator = collection.iterator();
                while (iterator.hasNext()) {
                    n2 = this.hashCodeAdd(n2, iterator.next(), schema2);
                }
                return n2;
            }
            case 5: {
                return this.hashCode(object, (Schema)schema.getTypes().get(this.resolveUnion(schema, object)));
            }
            case 2: {
                return schema.getEnumOrdinal(object.toString());
            }
            case 14: {
                return 0;
            }
            case 7: 
        }
        if (object instanceof Utf8) {
            do {
                return object.hashCode();
                break;
            } while (true);
        }
        object = new Utf8(object.toString());
        return object.hashCode();
    }

    protected int hashCodeAdd(int n2, Object object, Schema schema) {
        return n2 * 31 + this.hashCode(object, schema);
    }

    public Schema induce(Object object) {
        if (this.isRecord(object)) {
            return this.getRecordSchema(object);
        }
        if (object instanceof Collection) {
            Schema schema = null;
            for (Object object2 : (Collection)object) {
                if (schema == null) {
                    schema = this.induce(object2);
                    continue;
                }
                if (schema.equals(this.induce(object2))) continue;
                throw new AvroTypeException("No mixed type arrays.");
            }
            if (schema == null) {
                throw new AvroTypeException("Empty array: " + object);
            }
            return Schema.createArray(schema);
        }
        if (object instanceof Map) {
            Map map = (Map)object;
            Schema schema = null;
            for (Map.Entry entry : map.entrySet()) {
                if (schema == null) {
                    schema = this.induce(entry.getValue());
                    continue;
                }
                if (schema.equals(this.induce(entry.getValue()))) continue;
                throw new AvroTypeException("No mixed type map values.");
            }
            if (schema == null) {
                throw new AvroTypeException("Empty map: " + object);
            }
            return Schema.createMap(schema);
        }
        if (object instanceof GenericFixed) {
            return Schema.createFixed(null, null, null, ((GenericFixed)object).bytes().length);
        }
        if (object instanceof CharSequence) {
            return Schema.create(Schema.Type.STRING);
        }
        if (object instanceof ByteBuffer) {
            return Schema.create(Schema.Type.BYTES);
        }
        if (object instanceof Integer) {
            return Schema.create(Schema.Type.INT);
        }
        if (object instanceof Long) {
            return Schema.create(Schema.Type.LONG);
        }
        if (object instanceof Float) {
            return Schema.create(Schema.Type.FLOAT);
        }
        if (object instanceof Double) {
            return Schema.create(Schema.Type.DOUBLE);
        }
        if (object instanceof Boolean) {
            return Schema.create(Schema.Type.BOOLEAN);
        }
        if (object == null) {
            return Schema.create(Schema.Type.NULL);
        }
        throw new AvroTypeException("Can't create schema for: " + object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected boolean instanceOf(Schema schema, Object object) {
        boolean bl = true;
        switch (1.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()]) {
            default: {
                throw new AvroRuntimeException("Unexpected type: " + schema);
            }
            case 1: {
                if (!this.isRecord(object)) {
                    return false;
                }
                if (schema.getFullName() != null) return schema.getFullName().equals((Object)this.getRecordSchema(object).getFullName());
                if (this.getRecordSchema(object).getFullName() != null) return false;
                return bl;
            }
            case 2: {
                if (!this.isEnum(object)) return false;
                return schema.getFullName().equals((Object)this.getEnumSchema(object).getFullName());
            }
            case 3: {
                return this.isArray(object);
            }
            case 4: {
                return this.isMap(object);
            }
            case 6: {
                if (!this.isFixed(object)) return false;
                return schema.getFullName().equals((Object)this.getFixedSchema(object).getFullName());
            }
            case 7: {
                return this.isString(object);
            }
            case 8: {
                return this.isBytes(object);
            }
            case 9: {
                return object instanceof Integer;
            }
            case 10: {
                return object instanceof Long;
            }
            case 11: {
                return object instanceof Float;
            }
            case 12: {
                return object instanceof Double;
            }
            case 13: {
                return object instanceof Boolean;
            }
            case 14: 
        }
        if (object != null) return false;
        return bl;
    }

    protected boolean isArray(Object object) {
        return object instanceof Collection;
    }

    protected boolean isBytes(Object object) {
        return object instanceof ByteBuffer;
    }

    protected boolean isEnum(Object object) {
        return object instanceof GenericEnumSymbol;
    }

    protected boolean isFixed(Object object) {
        return object instanceof GenericFixed;
    }

    protected boolean isMap(Object object) {
        return object instanceof Map;
    }

    protected boolean isRecord(Object object) {
        return object instanceof IndexedRecord;
    }

    protected boolean isString(Object object) {
        return object instanceof CharSequence;
    }

    public Object newRecord(Object object, Schema schema) {
        IndexedRecord indexedRecord;
        if (object instanceof IndexedRecord && (indexedRecord = (IndexedRecord)object).getSchema() == schema) {
            return indexedRecord;
        }
        return new Record(schema);
    }

    public int resolveUnion(Schema schema, Object object) {
        Integer n2 = schema.getIndexNamed(this.getSchemaName(object));
        if (n2 != null) {
            return n2;
        }
        throw new UnresolvedUnionException(schema, object);
    }

    public void setField(Object object, String string, int n2, Object object2) {
        ((IndexedRecord)object).put(n2, object2);
    }

    protected void setField(Object object, String string, int n2, Object object2, Object object3) {
        this.setField(object, string, n2, object2);
    }

    public String toString(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        this.toString(object, stringBuilder);
        return stringBuilder.toString();
    }

    protected void toString(Object object, StringBuilder stringBuilder) {
        if (this.isRecord(object)) {
            stringBuilder.append("{");
            int n2 = 0;
            Schema schema = this.getRecordSchema(object);
            for (Schema.Field field : schema.getFields()) {
                this.toString(field.name(), stringBuilder);
                stringBuilder.append(": ");
                this.toString(this.getField(object, field.name(), field.pos()), stringBuilder);
                if (++n2 >= schema.getFields().size()) continue;
                stringBuilder.append(", ");
            }
            stringBuilder.append("}");
            return;
        }
        if (object instanceof Collection) {
            Collection collection = (Collection)object;
            stringBuilder.append("[");
            long l2 = -1 + collection.size();
            int n3 = 0;
            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                this.toString(iterator.next(), stringBuilder);
                int n4 = n3 + 1;
                if ((long)n3 < l2) {
                    stringBuilder.append(", ");
                }
                n3 = n4;
            }
            stringBuilder.append("]");
            return;
        }
        if (object instanceof Map) {
            stringBuilder.append("{");
            int n5 = 0;
            Map map = (Map)object;
            for (Map.Entry entry : map.entrySet()) {
                this.toString(entry.getKey(), stringBuilder);
                stringBuilder.append(": ");
                this.toString(entry.getValue(), stringBuilder);
                if (++n5 >= map.size()) continue;
                stringBuilder.append(", ");
            }
            stringBuilder.append("}");
            return;
        }
        if (object instanceof CharSequence || object instanceof GenericEnumSymbol) {
            stringBuilder.append("\"");
            GenericData.super.writeEscapedString(object.toString(), stringBuilder);
            stringBuilder.append("\"");
            return;
        }
        if (object instanceof ByteBuffer) {
            stringBuilder.append("{\"bytes\": \"");
            ByteBuffer byteBuffer = (ByteBuffer)object;
            for (int i2 = byteBuffer.position(); i2 < byteBuffer.limit(); ++i2) {
                stringBuilder.append((char)byteBuffer.get(i2));
            }
            stringBuilder.append("\"}");
            return;
        }
        stringBuilder.append(object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean validate(Schema schema, Object object) {
        boolean bl = true;
        switch (1.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()]) {
            default: {
                return false;
            }
            case 1: {
                Schema.Field field;
                if (!this.isRecord(object)) return false;
                Iterator iterator = schema.getFields().iterator();
                do {
                    if (!iterator.hasNext()) return bl;
                } while (this.validate((field = (Schema.Field)iterator.next()).schema(), this.getField(object, field.name(), field.pos())));
                return false;
            }
            case 2: {
                return schema.getEnumSymbols().contains((Object)object.toString());
            }
            case 3: {
                Object object2;
                if (!(object instanceof Collection)) return false;
                Iterator iterator = ((Collection)object).iterator();
                do {
                    if (!iterator.hasNext()) return bl;
                    object2 = iterator.next();
                } while (this.validate(schema.getElementType(), object2));
                return false;
            }
            case 4: {
                Map.Entry entry;
                if (!(object instanceof Map)) return false;
                Iterator iterator = ((Map)object).entrySet().iterator();
                do {
                    if (!iterator.hasNext()) return bl;
                    entry = (Map.Entry)iterator.next();
                } while (this.validate(schema.getValueType(), entry.getValue()));
                return false;
            }
            case 5: {
                Iterator iterator = schema.getTypes().iterator();
                do {
                    if (!iterator.hasNext()) return false;
                } while (!this.validate((Schema)iterator.next(), object));
                return bl;
            }
            case 6: {
                if (!(object instanceof GenericFixed)) return false;
                if (((GenericFixed)object).bytes().length != schema.getFixedSize()) return false;
                return bl;
            }
            case 7: {
                return this.isString(object);
            }
            case 8: {
                return this.isBytes(object);
            }
            case 9: {
                return object instanceof Integer;
            }
            case 10: {
                return object instanceof Long;
            }
            case 11: {
                return object instanceof Float;
            }
            case 12: {
                return object instanceof Double;
            }
            case 13: {
                return object instanceof Boolean;
            }
            case 14: 
        }
        if (object != null) return false;
        return bl;
    }

    public static class Array<T>
    extends AbstractList<T>
    implements GenericArray<T>,
    Comparable<GenericArray<T>> {
        private static final Object[] EMPTY = new Object[0];
        private Object[] elements = EMPTY;
        private final Schema schema;
        private int size;

        public Array(int n2, Schema schema) {
            if (schema == null || !Schema.Type.ARRAY.equals((Object)schema.getType())) {
                throw new AvroRuntimeException("Not an array schema: " + schema);
            }
            this.schema = schema;
            if (n2 != 0) {
                this.elements = new Object[n2];
            }
        }

        public Array(Schema schema, Collection<T> collection) {
            if (schema == null || !Schema.Type.ARRAY.equals((Object)schema.getType())) {
                throw new AvroRuntimeException("Not an array schema: " + schema);
            }
            this.schema = schema;
            if (collection != null) {
                this.elements = new Object[collection.size()];
                this.addAll(collection);
            }
        }

        public void add(int n2, T t2) {
            if (n2 > this.size || n2 < 0) {
                throw new IndexOutOfBoundsException("Index " + n2 + " out of bounds.");
            }
            if (this.size == this.elements.length) {
                Object[] arrobject = new Object[1 + 3 * this.size / 2];
                System.arraycopy((Object)this.elements, (int)0, (Object)arrobject, (int)0, (int)this.size);
                this.elements = arrobject;
            }
            System.arraycopy((Object)this.elements, (int)n2, (Object)this.elements, (int)(n2 + 1), (int)(this.size - n2));
            this.elements[n2] = t2;
            this.size = 1 + this.size;
        }

        public boolean add(T t2) {
            if (this.size == this.elements.length) {
                Object[] arrobject = new Object[1 + 3 * this.size / 2];
                System.arraycopy((Object)this.elements, (int)0, (Object)arrobject, (int)0, (int)this.size);
                this.elements = arrobject;
            }
            Object[] arrobject = this.elements;
            int n2 = this.size;
            this.size = n2 + 1;
            arrobject[n2] = t2;
            return true;
        }

        public void clear() {
            this.size = 0;
        }

        public int compareTo(GenericArray<T> genericArray) {
            return GenericData.get().compare(this, genericArray, this.getSchema());
        }

        public T get(int n2) {
            if (n2 >= this.size) {
                throw new IndexOutOfBoundsException("Index " + n2 + " out of bounds.");
            }
            return (T)this.elements[n2];
        }

        @Override
        public Schema getSchema() {
            return this.schema;
        }

        public Iterator<T> iterator() {
            return new Iterator<T>(){
                private int position = 0;

                public boolean hasNext() {
                    return this.position < Array.this.size;
                }

                public T next() {
                    Object[] arrobject = Array.this.elements;
                    int n2 = this.position;
                    this.position = n2 + 1;
                    return (T)arrobject[n2];
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override
        public T peek() {
            if (this.size < this.elements.length) {
                return (T)this.elements[this.size];
            }
            return null;
        }

        public T remove(int n2) {
            if (n2 >= this.size) {
                throw new IndexOutOfBoundsException("Index " + n2 + " out of bounds.");
            }
            Object object = this.elements[n2];
            this.size = -1 + this.size;
            System.arraycopy((Object)this.elements, (int)(n2 + 1), (Object)this.elements, (int)n2, (int)(this.size - n2));
            this.elements[this.size] = null;
            return (T)object;
        }

        @Override
        public void reverse() {
            int n2 = 0;
            for (int i2 = -1 + this.elements.length; n2 < i2; ++n2, --i2) {
                Object object = this.elements[n2];
                this.elements[n2] = this.elements[i2];
                this.elements[i2] = object;
            }
        }

        public T set(int n2, T t2) {
            if (n2 >= this.size) {
                throw new IndexOutOfBoundsException("Index " + n2 + " out of bounds.");
            }
            Object object = this.elements[n2];
            this.elements[n2] = t2;
            return (T)object;
        }

        public int size() {
            return this.size;
        }

        /*
         * Enabled aggressive block sorting
         */
        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("[");
            int n2 = 0;
            Iterator<T> iterator = this.iterator();
            do {
                if (!iterator.hasNext()) {
                    stringBuffer.append("]");
                    return stringBuffer.toString();
                }
                Object object = iterator.next();
                String string = object == null ? "null" : object.toString();
                stringBuffer.append(string);
                if (++n2 >= this.size()) continue;
                stringBuffer.append(", ");
            } while (true);
        }

    }

    public static class EnumSymbol
    implements GenericEnumSymbol {
        private Schema schema;
        private String symbol;

        public EnumSymbol(Schema schema, String string) {
            this.schema = schema;
            this.symbol = string;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            return object == this || object instanceof GenericEnumSymbol && this.symbol.equals((Object)object.toString());
        }

        @Override
        public Schema getSchema() {
            return this.schema;
        }

        public int hashCode() {
            return this.symbol.hashCode();
        }

        @Override
        public String toString() {
            return this.symbol;
        }
    }

    public static class Fixed
    implements GenericFixed,
    Comparable<Fixed> {
        private byte[] bytes;
        private Schema schema;

        protected Fixed() {
        }

        public Fixed(Schema schema) {
            this.setSchema(schema);
        }

        public Fixed(Schema schema, byte[] arrby) {
            this.schema = schema;
            this.bytes = arrby;
        }

        public void bytes(byte[] arrby) {
            this.bytes = arrby;
        }

        @Override
        public byte[] bytes() {
            return this.bytes;
        }

        public int compareTo(Fixed fixed) {
            return BinaryData.compareBytes(this.bytes, 0, this.bytes.length, fixed.bytes, 0, fixed.bytes.length);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            return object == this || object instanceof GenericFixed && Arrays.equals((byte[])this.bytes, (byte[])((GenericFixed)object).bytes());
        }

        @Override
        public Schema getSchema() {
            return this.schema;
        }

        public int hashCode() {
            return Arrays.hashCode((byte[])this.bytes);
        }

        protected void setSchema(Schema schema) {
            this.schema = schema;
            this.bytes = new byte[schema.getFixedSize()];
        }

        public String toString() {
            return Arrays.toString((byte[])this.bytes);
        }
    }

    public static class Record
    implements GenericRecord,
    Comparable<Record> {
        private final Schema schema;
        private final Object[] values;

        public Record(Schema schema) {
            if (schema == null || !Schema.Type.RECORD.equals((Object)schema.getType())) {
                throw new AvroRuntimeException("Not a record schema: " + schema);
            }
            this.schema = schema;
            this.values = new Object[schema.getFields().size()];
        }

        public Record(Record record, boolean bl) {
            this.schema = record.schema;
            this.values = new Object[this.schema.getFields().size()];
            if (bl) {
                for (int i2 = 0; i2 < this.values.length; ++i2) {
                    this.values[i2] = INSTANCE.deepCopy(((Schema.Field)this.schema.getFields().get(i2)).schema(), record.values[i2]);
                }
            } else {
                System.arraycopy((Object)record.values, (int)0, (Object)this.values, (int)0, (int)record.values.length);
            }
        }

        public int compareTo(Record record) {
            return GenericData.get().compare(this, record, this.schema);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block6 : {
                block5 : {
                    if (object == this) break block5;
                    if (!(object instanceof Record)) {
                        return false;
                    }
                    Record record = (Record)object;
                    if (!this.schema.getFullName().equals((Object)record.schema.getFullName())) {
                        return false;
                    }
                    if (GenericData.get().compare(this, record, this.schema, true) != 0) break block6;
                }
                return true;
            }
            return false;
        }

        @Override
        public Object get(int n2) {
            return this.values[n2];
        }

        @Override
        public Object get(String string) {
            Schema.Field field = this.schema.getField(string);
            if (field == null) {
                return null;
            }
            return this.values[field.pos()];
        }

        @Override
        public Schema getSchema() {
            return this.schema;
        }

        public int hashCode() {
            return GenericData.get().hashCode(this, this.schema);
        }

        @Override
        public void put(int n2, Object object) {
            this.values[n2] = object;
        }

        @Override
        public void put(String string, Object object) {
            Schema.Field field = this.schema.getField(string);
            if (field == null) {
                throw new AvroRuntimeException("Not a valid schema field: " + string);
            }
            this.values[field.pos()] = object;
        }

        public String toString() {
            return GenericData.get().toString(this);
        }
    }

    public static final class StringType
    extends Enum<StringType> {
        private static final /* synthetic */ StringType[] $VALUES;
        public static final /* enum */ StringType CharSequence = new StringType();
        public static final /* enum */ StringType String = new StringType();
        public static final /* enum */ StringType Utf8 = new StringType();

        static {
            StringType[] arrstringType = new StringType[]{CharSequence, String, Utf8};
            $VALUES = arrstringType;
        }

        public static StringType valueOf(String string) {
            return (StringType)Enum.valueOf(StringType.class, (String)string);
        }

        public static StringType[] values() {
            return (StringType[])$VALUES.clone();
        }
    }

}


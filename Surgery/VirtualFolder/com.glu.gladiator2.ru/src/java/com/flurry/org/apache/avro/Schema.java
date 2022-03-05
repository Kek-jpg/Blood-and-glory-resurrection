/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.ObjectMapper
 *  com.flurry.org.codehaus.jackson.node.DoubleNode
 *  java.io.File
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.Reader
 *  java.io.StringReader
 *  java.io.StringWriter
 *  java.io.Writer
 *  java.lang.Boolean
 *  java.lang.Character
 *  java.lang.Double
 *  java.lang.Enum
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.lang.ThreadLocal
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.IdentityHashMap
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.LinkedHashSet
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.org.apache.avro;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.SchemaParseException;
import com.flurry.org.codehaus.jackson.JsonFactory;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.map.ObjectMapper;
import com.flurry.org.codehaus.jackson.node.DoubleNode;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Schema {
    static final JsonFactory FACTORY = new JsonFactory();
    private static final Set<String> FIELD_RESERVED;
    static final ObjectMapper MAPPER;
    private static final int NO_HASHCODE = Integer.MIN_VALUE;
    static final Map<String, Type> PRIMITIVES;
    private static final Set<String> SCHEMA_RESERVED;
    private static final ThreadLocal<Set> SEEN_EQUALS;
    private static final ThreadLocal<Map> SEEN_HASHCODE;
    private static ThreadLocal<Boolean> validateNames;
    int hashCode = Integer.MIN_VALUE;
    Props props = new Props(SCHEMA_RESERVED);
    private final Type type;

    static {
        MAPPER = new ObjectMapper(FACTORY);
        FACTORY.enable(JsonParser.Feature.ALLOW_COMMENTS);
        FACTORY.setCodec((ObjectCodec)MAPPER);
        SCHEMA_RESERVED = new HashSet();
        Collections.addAll(SCHEMA_RESERVED, (Object[])new String[]{"doc", "fields", "items", "name", "namespace", "size", "symbols", "values", "type"});
        FIELD_RESERVED = new HashSet();
        Collections.addAll(FIELD_RESERVED, (Object[])new String[]{"default", "doc", "name", "order", "type"});
        SEEN_EQUALS = new ThreadLocal<Set>(){

            protected Set initialValue() {
                return new HashSet();
            }
        };
        SEEN_HASHCODE = new ThreadLocal<Map>(){

            protected Map initialValue() {
                return new IdentityHashMap();
            }
        };
        PRIMITIVES = new HashMap();
        PRIMITIVES.put((Object)"string", (Object)Type.STRING);
        PRIMITIVES.put((Object)"bytes", (Object)Type.BYTES);
        PRIMITIVES.put((Object)"int", (Object)Type.INT);
        PRIMITIVES.put((Object)"long", (Object)Type.LONG);
        PRIMITIVES.put((Object)"float", (Object)Type.FLOAT);
        PRIMITIVES.put((Object)"double", (Object)Type.DOUBLE);
        PRIMITIVES.put((Object)"boolean", (Object)Type.BOOLEAN);
        PRIMITIVES.put((Object)"null", (Object)Type.NULL);
        validateNames = new ThreadLocal<Boolean>(){

            protected Boolean initialValue() {
                return true;
            }
        };
    }

    Schema(Type type) {
        this.type = type;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Schema applyAliases(Schema schema, Schema schema2) {
        IdentityHashMap identityHashMap;
        HashMap hashMap;
        HashMap hashMap2;
        block3 : {
            block2 : {
                if (schema == schema2) break block2;
                identityHashMap = new IdentityHashMap(1);
                hashMap2 = new HashMap(1);
                hashMap = new HashMap(1);
                Schema.getAliases(schema2, (Map<Schema, Schema>)identityHashMap, (Map<Name, Name>)hashMap2, (Map<Name, Map<String, String>>)hashMap);
                if (hashMap2.size() != 0 || hashMap.size() != 0) break block3;
            }
            return schema;
        }
        identityHashMap.clear();
        return Schema.applyAliases(schema, (Map<Schema, Schema>)identityHashMap, (Map<Name, Name>)hashMap2, (Map<Name, Map<String, String>>)hashMap);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Schema applyAliases(Schema schema, Map<Schema, Schema> map, Map<Name, Name> map2, Map<Name, Map<String, String>> map3) {
        Name name = schema instanceof NamedSchema ? ((NamedSchema)schema).name : null;
        Schema schema2 = schema;
        switch (4.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()]) {
            case 9: {
                if (map.containsKey((Object)schema)) {
                    return (Schema)map.get((Object)schema);
                }
                if (map2.containsKey((Object)name)) {
                    name = (Name)map2.get((Object)name);
                }
                schema2 = Schema.createRecord(name.full, schema.getDoc(), null, schema.isError());
                map.put((Object)schema, (Object)schema2);
                ArrayList arrayList = new ArrayList();
                for (Field field : schema.getFields()) {
                    Schema schema3 = Schema.applyAliases(field.schema, map, map2, map3);
                    Field field2 = new Field(Schema.getFieldAlias(name, field.name, map3), schema3, field.doc, field.defaultValue, field.order);
                    field2.props.putAll((Map)field.props);
                    arrayList.add((Object)field2);
                }
                schema2.setFields((List<Field>)arrayList);
                break;
            }
            case 10: {
                if (!map2.containsKey((Object)name)) break;
                schema2 = Schema.createEnum(((Name)map2.get((Object)name)).full, schema.getDoc(), null, schema.getEnumSymbols());
                break;
            }
            case 11: {
                Schema schema4 = Schema.applyAliases(schema.getElementType(), map, map2, map3);
                if (schema4 == schema.getElementType()) break;
                schema2 = Schema.createArray(schema4);
                break;
            }
            case 12: {
                Schema schema5 = Schema.applyAliases(schema.getValueType(), map, map2, map3);
                if (schema5 == schema.getValueType()) break;
                schema2 = Schema.createMap(schema5);
                break;
            }
            case 13: {
                ArrayList arrayList = new ArrayList();
                Iterator iterator = schema.getTypes().iterator();
                while (iterator.hasNext()) {
                    arrayList.add((Object)Schema.applyAliases((Schema)iterator.next(), map, map2, map3));
                }
                schema2 = Schema.createUnion((List<Schema>)arrayList);
                break;
            }
            case 14: {
                if (!map2.containsKey((Object)name)) break;
                schema2 = Schema.createFixed(((Name)map2.get((Object)name)).full, schema.getDoc(), null, schema.getFixedSize());
                break;
            }
        }
        if (schema2 != schema) {
            schema2.props.putAll((Map)schema.props);
        }
        return schema2;
    }

    public static Schema create(Type type) {
        switch (type) {
            default: {
                throw new AvroRuntimeException("Can't create a: " + (Object)((Object)type));
            }
            case STRING: {
                return new StringSchema();
            }
            case BYTES: {
                return new BytesSchema();
            }
            case INT: {
                return new IntSchema();
            }
            case LONG: {
                return new LongSchema();
            }
            case FLOAT: {
                return new FloatSchema();
            }
            case DOUBLE: {
                return new DoubleSchema();
            }
            case BOOLEAN: {
                return new BooleanSchema();
            }
            case NULL: 
        }
        return new NullSchema();
    }

    public static Schema createArray(Schema schema) {
        return new ArraySchema(schema);
    }

    public static Schema createEnum(String string, String string2, String string3, List<String> list) {
        return new EnumSchema(new Name(string, string3), string2, new LockableArrayList<String>(list));
    }

    public static Schema createFixed(String string, String string2, String string3, int n2) {
        return new FixedSchema(new Name(string, string3), string2, n2);
    }

    public static Schema createMap(Schema schema) {
        return new MapSchema(schema);
    }

    public static Schema createRecord(String string, String string2, String string3, boolean bl) {
        return new RecordSchema(new Name(string, string3), string2, bl);
    }

    public static Schema createRecord(List<Field> list) {
        Schema schema = Schema.createRecord(null, null, null, false);
        schema.setFields(list);
        return schema;
    }

    public static Schema createUnion(List<Schema> list) {
        return new UnionSchema(new LockableArrayList<Schema>(list));
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void getAliases(Schema schema, Map<Schema, Schema> map, Map<Name, Name> map2, Map<Name, Map<String, String>> map3) {
        if (schema instanceof NamedSchema) {
            NamedSchema namedSchema = (NamedSchema)schema;
            if (namedSchema.aliases != null) {
                Iterator iterator = namedSchema.aliases.iterator();
                while (iterator.hasNext()) {
                    map2.put((Object)((Name)iterator.next()), (Object)namedSchema.name);
                }
            }
        }
        switch (schema.getType()) {
            case RECORD: {
                if (map.containsKey((Object)schema)) return;
                {
                    map.put((Object)schema, (Object)schema);
                    RecordSchema recordSchema = (RecordSchema)schema;
                    for (Field field : schema.getFields()) {
                        if (field.aliases != null) {
                            for (String string : field.aliases) {
                                Map map4 = (Map)map3.get((Object)recordSchema.name);
                                if (map4 == null) {
                                    Name name = recordSchema.name;
                                    map4 = new HashMap();
                                    map3.put((Object)name, (Object)map4);
                                }
                                map4.put((Object)string, (Object)field.name);
                            }
                        }
                        Schema.getAliases(field.schema, map, map2, map3);
                    }
                    if (recordSchema.aliases == null || !map3.containsKey((Object)recordSchema.name)) return;
                    {
                        Iterator iterator = recordSchema.aliases.iterator();
                        while (iterator.hasNext()) {
                            map3.put((Object)((Name)iterator.next()), map3.get((Object)recordSchema.name));
                        }
                    }
                }
            }
            default: {
                return;
            }
            case ARRAY: {
                Schema.getAliases(schema.getElementType(), map, map2, map3);
                return;
            }
            case MAP: {
                Schema.getAliases(schema.getValueType(), map, map2, map3);
                return;
            }
            case UNION: {
                Iterator iterator = schema.getTypes().iterator();
                while (iterator.hasNext()) {
                    Schema.getAliases((Schema)iterator.next(), map, map2, map3);
                }
                break block0;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String getFieldAlias(Name name, String string, Map<Name, Map<String, String>> map) {
        String string2;
        Map map2 = (Map)map.get((Object)name);
        if (map2 == null || (string2 = (String)map2.get((Object)string)) == null) {
            return string;
        }
        return string2;
    }

    private static String getOptionalText(JsonNode jsonNode, String string) {
        JsonNode jsonNode2 = jsonNode.get(string);
        if (jsonNode2 != null) {
            return jsonNode2.getTextValue();
        }
        return null;
    }

    private static String getRequiredText(JsonNode jsonNode, String string, String string2) {
        String string3 = Schema.getOptionalText(jsonNode, string);
        if (string3 == null) {
            throw new SchemaParseException(string2 + ": " + jsonNode);
        }
        return string3;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static Schema parse(JsonNode var0_1, Names var1) {
        block40 : {
            block38 : {
                block34 : {
                    block39 : {
                        block37 : {
                            block36 : {
                                block35 : {
                                    if (var0_1.isTextual()) {
                                        var4_2 = var1.get(var0_1.getTextValue());
                                        if (var4_2 != null) return var4_2;
                                        throw new SchemaParseException("Undefined name: " + var0_1);
                                    }
                                    if (!var0_1.isObject()) break block34;
                                    var6_3 = Schema.getRequiredText(var0_1, "type", "No type");
                                    if (var6_3.equals((Object)"record") || var6_3.equals((Object)"error") || var6_3.equals((Object)"enum")) break block35;
                                    var47_4 = var6_3.equals((Object)"fixed");
                                    var8_5 = null;
                                    var10_6 = null;
                                    var12_7 = null;
                                    if (!var47_4) break block36;
                                }
                                var7_8 = Schema.getOptionalText(var0_1, "namespace");
                                var8_5 = Schema.getOptionalText(var0_1, "doc");
                                if (var7_8 == null) {
                                    var7_8 = var1.space();
                                }
                                var9_9 = Schema.getRequiredText(var0_1, "name", "No name in schema");
                                var10_6 = new Name(var9_9, var7_8);
                                var11_10 = Name.access$600(var10_6);
                                var12_7 = null;
                                if (var11_10 != null) {
                                    var12_7 = var1.space();
                                    var1.space(Name.access$600(var10_6));
                                }
                            }
                            if (!Schema.PRIMITIVES.containsKey((Object)var6_3)) break block37;
                            var4_2 = Schema.create((Type)Schema.PRIMITIVES.get((Object)var6_3));
                            break block38;
                        }
                        if (!var6_3.equals((Object)"record") && !var6_3.equals((Object)"error")) break block39;
                        var13_26 = new ArrayList();
                        var14_21 = var6_3.equals((Object)"error");
                        var4_2 = new RecordSchema(var10_6, var8_5, var14_21);
                        if (var10_6 != null) {
                            var1.add(var4_2);
                        }
                        if ((var15_15 = var0_1.get("fields")) == null) throw new SchemaParseException("Record has no fields: " + var0_1);
                        if (!var15_15.isArray()) {
                            throw new SchemaParseException("Record has no fields: " + var0_1);
                        }
                        var16_22 = var15_15.iterator();
                        break block40;
                    }
                    if (var6_3.equals((Object)"enum")) {
                        var43_27 = var0_1.get("symbols");
                        if (var43_27 == null) throw new SchemaParseException("Enum has no symbols: " + var0_1);
                        if (!var43_27.isArray()) {
                            throw new SchemaParseException("Enum has no symbols: " + var0_1);
                        }
                        var44_23 = new LockableArrayList<String>();
                        var45_11 = var43_27.iterator();
                        while (var45_11.hasNext()) {
                            var44_23.add(((JsonNode)var45_11.next()).getTextValue());
                        }
                        var4_2 = new EnumSchema(var10_6, var8_5, var44_23);
                        if (var10_6 != null) {
                            var1.add(var4_2);
                        }
                    } else if (var6_3.equals((Object)"array")) {
                        var41_18 = var0_1.get("items");
                        if (var41_18 == null) {
                            throw new SchemaParseException("Array has no items type: " + var0_1);
                        }
                        var42_20 = Schema.parse(var41_18, var1);
                        var4_2 = new ArraySchema(var42_20);
                    } else if (var6_3.equals((Object)"map")) {
                        var39_30 = var0_1.get("values");
                        if (var39_30 == null) {
                            throw new SchemaParseException("Map has no values type: " + var0_1);
                        }
                        var40_33 = Schema.parse(var39_30, var1);
                        var4_2 = new MapSchema(var40_33);
                    } else {
                        if (var6_3.equals((Object)"fixed") == false) throw new SchemaParseException("Type not supported: " + var6_3);
                        var37_13 = var0_1.get("size");
                        if (var37_13 == null) throw new SchemaParseException("Invalid or no size: " + var0_1);
                        if (!var37_13.isInt()) {
                            throw new SchemaParseException("Invalid or no size: " + var0_1);
                        }
                        var38_31 = var37_13.getIntValue();
                        var4_2 = new FixedSchema(var10_6, var8_5, var38_31);
                        if (var10_6 != null) {
                            var1.add(var4_2);
                        }
                    }
                    break block38;
                }
                if (var0_1.isArray() == false) throw new SchemaParseException("Schema not yet supported: " + var0_1);
                var2_42 = new LockableArrayList<Schema>(var0_1.size());
                var3_43 = var0_1.iterator();
                do {
                    if (!var3_43.hasNext()) {
                        return new UnionSchema(var2_42);
                    }
                    var2_42.add(Schema.parse((JsonNode)var3_43.next(), var1));
                } while (true);
            }
lbl90: // 2 sources:
            do {
                var17_36 = var0_1.getFieldNames();
                while (var17_36.hasNext()) {
                    var21_37 = (String)var17_36.next();
                    var22_38 = var0_1.get(var21_37).getTextValue();
                    if (Schema.SCHEMA_RESERVED.contains((Object)var21_37) || var22_38 == null) continue;
                    var4_2.addProp(var21_37, var22_38);
                }
                if (var12_7 != null) {
                    var1.space(var12_7);
                }
                if (var4_2 instanceof NamedSchema == false) return var4_2;
                var18_39 = Schema.parseAliases(var0_1);
                if (var18_39 == null) return var4_2;
                var19_40 = var18_39.iterator();
                while (var19_40.hasNext() != false) {
                    var20_41 = (String)var19_40.next();
                    var4_2.addAlias(var20_41);
                }
                return var4_2;
                break;
            } while (true);
        }
        while (var16_22.hasNext()) {
            var23_16 = (JsonNode)var16_22.next();
            var24_32 = Schema.getRequiredText(var23_16, "name", "No field name");
            var25_19 = Schema.getOptionalText(var23_16, "doc");
            var26_17 = var23_16.get("type");
            if (var26_17 == null) {
                throw new SchemaParseException("No field type: " + var23_16);
            }
            if (var26_17.isTextual() && var1.get(var26_17.getTextValue()) == null) {
                throw new SchemaParseException(var26_17 + " is not a defined name." + " The type of the \"" + var24_32 + "\" field must be" + " a defined name or a {\"type\": ...} expression.");
            }
            var27_28 = Schema.parse(var26_17, var1);
            var28_12 = Field.Order.ASCENDING;
            var29_25 = var23_16.get("order");
            if (var29_25 != null) {
                var28_12 = Field.Order.valueOf(var29_25.getTextValue().toUpperCase());
            }
            if ((var30_24 = var23_16.get("default")) != null && (Type.FLOAT.equals((Object)var27_28.getType()) || Type.DOUBLE.equals((Object)var27_28.getType())) && var30_24.isTextual()) {
                var30_24 = new DoubleNode(Double.valueOf((String)var30_24.getTextValue()).doubleValue());
            }
            var31_35 = new Field(var24_32, var27_28, var25_19, var30_24, var28_12);
            var32_29 = var23_16.getFieldNames();
            while (var32_29.hasNext()) {
                var35_34 = (String)var32_29.next();
                var36_14 = var23_16.get(var35_34).getTextValue();
                if (Schema.FIELD_RESERVED.contains((Object)var35_34) || var36_14 == null) continue;
                var31_35.addProp(var35_34, var36_14);
            }
            Field.access$1302(var31_35, Schema.parseAliases(var23_16));
            var13_26.add((Object)var31_35);
        }
        var4_2.setFields((List<Field>)var13_26);
        ** while (true)
    }

    public static Schema parse(File file) throws IOException {
        return new Parser().parse(file);
    }

    public static Schema parse(InputStream inputStream) throws IOException {
        return new Parser().parse(inputStream);
    }

    public static Schema parse(String string) {
        return new Parser().parse(string);
    }

    public static Schema parse(String string, boolean bl) {
        return new Parser().setValidate(bl).parse(string);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Set<String> parseAliases(JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode.get("aliases");
        if (jsonNode2 == null) {
            return null;
        }
        if (!jsonNode2.isArray()) {
            throw new SchemaParseException("aliases not an array: " + jsonNode);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<JsonNode> iterator = jsonNode2.iterator();
        while (iterator.hasNext()) {
            JsonNode jsonNode3 = (JsonNode)iterator.next();
            if (!jsonNode3.isTextual()) {
                throw new SchemaParseException("alias not a string: " + jsonNode3);
            }
            linkedHashSet.add((Object)jsonNode3.getTextValue());
        }
        return linkedHashSet;
    }

    static JsonNode parseJson(String string) {
        try {
            JsonNode jsonNode = MAPPER.readTree(FACTORY.createJsonParser((Reader)new StringReader(string)));
            return jsonNode;
        }
        catch (JsonParseException jsonParseException) {
            throw new RuntimeException((Throwable)((Object)jsonParseException));
        }
        catch (IOException iOException) {
            throw new RuntimeException((Throwable)iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String validateName(String string) {
        if (((Boolean)validateNames.get()).booleanValue()) {
            int n2 = string.length();
            if (n2 == 0) {
                throw new SchemaParseException("Empty name");
            }
            char c2 = string.charAt(0);
            if (!Character.isLetter((char)c2) && c2 != '_') {
                throw new SchemaParseException("Illegal initial character: " + string);
            }
            for (int i2 = 1; i2 < n2; ++i2) {
                char c3 = string.charAt(i2);
                if (Character.isLetterOrDigit((char)c3) || c3 == '_') continue;
                throw new SchemaParseException("Illegal character in: " + string);
            }
        }
        return string;
    }

    public void addAlias(String string) {
        throw new AvroRuntimeException("Not a named type: " + this);
    }

    public void addProp(String string, String string2) {
        void var4_3 = this;
        synchronized (var4_3) {
            this.props.add(string, string2);
            this.hashCode = Integer.MIN_VALUE;
            return;
        }
    }

    int computeHash() {
        return this.getType().hashCode() + this.props.hashCode();
    }

    final boolean equalCachedHash(Schema schema) {
        return this.hashCode == schema.hashCode || this.hashCode == Integer.MIN_VALUE || schema.hashCode == Integer.MIN_VALUE;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6 : {
            block5 : {
                if (object == this) break block5;
                if (!(object instanceof Schema)) {
                    return false;
                }
                Schema schema = (Schema)object;
                if (this.type != schema.type) {
                    return false;
                }
                if (!this.equalCachedHash(schema) || !this.props.equals((Object)schema.props)) break block6;
            }
            return true;
        }
        return false;
    }

    void fieldsToJson(Names names, JsonGenerator jsonGenerator) throws IOException {
        throw new AvroRuntimeException("Not a record: " + this);
    }

    public Set<String> getAliases() {
        throw new AvroRuntimeException("Not a named type: " + this);
    }

    public String getDoc() {
        return null;
    }

    public Schema getElementType() {
        throw new AvroRuntimeException("Not an array: " + this);
    }

    public int getEnumOrdinal(String string) {
        throw new AvroRuntimeException("Not an enum: " + this);
    }

    public List<String> getEnumSymbols() {
        throw new AvroRuntimeException("Not an enum: " + this);
    }

    public Field getField(String string) {
        throw new AvroRuntimeException("Not a record: " + this);
    }

    public List<Field> getFields() {
        throw new AvroRuntimeException("Not a record: " + this);
    }

    public int getFixedSize() {
        throw new AvroRuntimeException("Not fixed: " + this);
    }

    public String getFullName() {
        return this.getName();
    }

    public Integer getIndexNamed(String string) {
        throw new AvroRuntimeException("Not a union: " + this);
    }

    public String getName() {
        return this.type.name;
    }

    public String getNamespace() {
        throw new AvroRuntimeException("Not a named type: " + this);
    }

    public String getProp(String string) {
        void var4_2 = this;
        synchronized (var4_2) {
            String string2 = (String)this.props.get((Object)string);
            return string2;
        }
    }

    public Map<String, String> getProps() {
        return Collections.unmodifiableMap((Map)this.props);
    }

    public Type getType() {
        return this.type;
    }

    public List<Schema> getTypes() {
        throw new AvroRuntimeException("Not a union: " + this);
    }

    public Schema getValueType() {
        throw new AvroRuntimeException("Not a map: " + this);
    }

    public boolean hasEnumSymbol(String string) {
        throw new AvroRuntimeException("Not an enum: " + this);
    }

    public final int hashCode() {
        if (this.hashCode == Integer.MIN_VALUE) {
            this.hashCode = this.computeHash();
        }
        return this.hashCode;
    }

    public boolean isError() {
        throw new AvroRuntimeException("Not a record: " + this);
    }

    public void setFields(List<Field> list) {
        throw new AvroRuntimeException("Not a record: " + this);
    }

    void toJson(Names names, JsonGenerator jsonGenerator) throws IOException {
        if (this.props.size() == 0) {
            jsonGenerator.writeString(this.getName());
            return;
        }
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", this.getName());
        this.props.write(jsonGenerator);
        jsonGenerator.writeEndObject();
    }

    public String toString() {
        return this.toString(false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString(boolean bl) {
        try {
            StringWriter stringWriter = new StringWriter();
            JsonGenerator jsonGenerator = FACTORY.createJsonGenerator((Writer)stringWriter);
            if (bl) {
                jsonGenerator.useDefaultPrettyPrinter();
            }
            this.toJson(new Names(), jsonGenerator);
            jsonGenerator.flush();
            return stringWriter.toString();
        }
        catch (IOException iOException) {
            throw new AvroRuntimeException(iOException);
        }
    }

    private static class ArraySchema
    extends Schema {
        private final Schema elementType;

        public ArraySchema(Schema schema) {
            super(Type.ARRAY);
            this.elementType = schema;
        }

        @Override
        int computeHash() {
            return super.computeHash() + this.elementType.computeHash();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean equals(Object object) {
            block5 : {
                block4 : {
                    if (object == this) break block4;
                    if (!(object instanceof ArraySchema)) {
                        return false;
                    }
                    ArraySchema arraySchema = (ArraySchema)object;
                    if (!this.equalCachedHash(arraySchema) || !this.elementType.equals(arraySchema.elementType) || !this.props.equals((Object)arraySchema.props)) break block5;
                }
                return true;
            }
            return false;
        }

        @Override
        public Schema getElementType() {
            return this.elementType;
        }

        @Override
        void toJson(Names names, JsonGenerator jsonGenerator) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("type", "array");
            jsonGenerator.writeFieldName("items");
            this.elementType.toJson(names, jsonGenerator);
            this.props.write(jsonGenerator);
            jsonGenerator.writeEndObject();
        }
    }

    private static class BooleanSchema
    extends Schema {
        public BooleanSchema() {
            super(Type.BOOLEAN);
        }
    }

    private static class BytesSchema
    extends Schema {
        public BytesSchema() {
            super(Type.BYTES);
        }
    }

    private static class DoubleSchema
    extends Schema {
        public DoubleSchema() {
            super(Type.DOUBLE);
        }
    }

    private static class EnumSchema
    extends NamedSchema {
        private final Map<String, Integer> ordinals;
        private final List<String> symbols;

        public EnumSchema(Name name, String string, LockableArrayList<String> lockableArrayList) {
            super(Type.ENUM, name, string);
            this.symbols = lockableArrayList.lock();
            this.ordinals = new HashMap();
            int n2 = 0;
            Iterator iterator = lockableArrayList.iterator();
            while (iterator.hasNext()) {
                String string2 = (String)iterator.next();
                Map<String, Integer> map = this.ordinals;
                String string3 = Schema.validateName(string2);
                int n3 = n2 + 1;
                if (map.put((Object)string3, (Object)n2) != null) {
                    throw new SchemaParseException("Duplicate enum symbol: " + string2);
                }
                n2 = n3;
            }
        }

        @Override
        int computeHash() {
            return super.computeHash() + this.symbols.hashCode();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean equals(Object object) {
            block5 : {
                block4 : {
                    if (object == this) break block4;
                    if (!(object instanceof EnumSchema)) {
                        return false;
                    }
                    EnumSchema enumSchema = (EnumSchema)object;
                    if (!this.equalCachedHash(enumSchema) || !this.equalNames(enumSchema) || !this.symbols.equals(enumSchema.symbols) || !this.props.equals((Object)enumSchema.props)) break block5;
                }
                return true;
            }
            return false;
        }

        @Override
        public int getEnumOrdinal(String string) {
            return (Integer)this.ordinals.get((Object)string);
        }

        @Override
        public List<String> getEnumSymbols() {
            return this.symbols;
        }

        @Override
        public boolean hasEnumSymbol(String string) {
            return this.ordinals.containsKey((Object)string);
        }

        @Override
        void toJson(Names names, JsonGenerator jsonGenerator) throws IOException {
            if (this.writeNameRef(names, jsonGenerator)) {
                return;
            }
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("type", "enum");
            this.writeName(names, jsonGenerator);
            if (this.getDoc() != null) {
                jsonGenerator.writeStringField("doc", this.getDoc());
            }
            jsonGenerator.writeArrayFieldStart("symbols");
            Iterator iterator = this.symbols.iterator();
            while (iterator.hasNext()) {
                jsonGenerator.writeString((String)iterator.next());
            }
            jsonGenerator.writeEndArray();
            this.props.write(jsonGenerator);
            this.aliasesToJson(jsonGenerator);
            jsonGenerator.writeEndObject();
        }
    }

    public static class Field {
        private Set<String> aliases;
        private final JsonNode defaultValue;
        private final String doc;
        private final String name;
        private final Order order;
        private transient int position;
        private final Props props;
        private final Schema schema;

        public Field(String string, Schema schema, String string2, JsonNode jsonNode) {
            super(string, schema, string2, jsonNode, Order.ASCENDING);
        }

        public Field(String string, Schema schema, String string2, JsonNode jsonNode, Order order) {
            this.position = -1;
            this.props = new Props((Set<String>)FIELD_RESERVED);
            this.name = Schema.validateName(string);
            this.schema = schema;
            this.doc = string2;
            this.defaultValue = jsonNode;
            this.order = order;
        }

        static /* synthetic */ Set access$1302(Field field, Set set) {
            field.aliases = set;
            return set;
        }

        private boolean defaultValueEquals(JsonNode jsonNode) {
            if (this.defaultValue == null) {
                return jsonNode == null;
            }
            if (Double.isNaN((double)this.defaultValue.getValueAsDouble())) {
                return Double.isNaN((double)jsonNode.getValueAsDouble());
            }
            return this.defaultValue.equals(jsonNode);
        }

        public void addAlias(String string) {
            if (this.aliases == null) {
                this.aliases = new LinkedHashSet();
            }
            this.aliases.add((Object)string);
        }

        public void addProp(String string, String string2) {
            void var4_3 = this;
            synchronized (var4_3) {
                this.props.add(string, string2);
                return;
            }
        }

        public Set<String> aliases() {
            if (this.aliases == null) {
                return Collections.emptySet();
            }
            return Collections.unmodifiableSet(this.aliases);
        }

        public JsonNode defaultValue() {
            return this.defaultValue;
        }

        public String doc() {
            return this.doc;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block5 : {
                block4 : {
                    if (object == this) break block4;
                    if (!(object instanceof Field)) {
                        return false;
                    }
                    Field field = (Field)object;
                    if (!this.name.equals((Object)field.name) || !this.schema.equals(field.schema) || !Field.super.defaultValueEquals(field.defaultValue) || !this.props.equals((Object)field.props)) break block5;
                }
                return true;
            }
            return false;
        }

        public String getProp(String string) {
            void var4_2 = this;
            synchronized (var4_2) {
                String string2 = (String)this.props.get((Object)string);
                return string2;
            }
        }

        public int hashCode() {
            return this.name.hashCode() + this.schema.computeHash();
        }

        public String name() {
            return this.name;
        }

        public Order order() {
            return this.order;
        }

        public int pos() {
            return this.position;
        }

        public Map<String, String> props() {
            return Collections.unmodifiableMap((Map)this.props);
        }

        public Schema schema() {
            return this.schema;
        }

        public String toString() {
            return this.name + " type:" + (Object)((Object)this.schema.type) + " pos:" + this.position;
        }

        public static final class Order
        extends Enum<Order> {
            private static final /* synthetic */ Order[] $VALUES;
            public static final /* enum */ Order ASCENDING = new Order();
            public static final /* enum */ Order DESCENDING = new Order();
            public static final /* enum */ Order IGNORE = new Order();
            private String name;

            static {
                Order[] arrorder = new Order[]{ASCENDING, DESCENDING, IGNORE};
                $VALUES = arrorder;
            }

            private Order() {
                this.name = this.name().toLowerCase();
            }

            public static Order valueOf(String string) {
                return (Order)Enum.valueOf(Order.class, (String)string);
            }

            public static Order[] values() {
                return (Order[])$VALUES.clone();
            }
        }

    }

    private static class FixedSchema
    extends NamedSchema {
        private final int size;

        public FixedSchema(Name name, String string, int n2) {
            super(Type.FIXED, name, string);
            if (n2 < 0) {
                throw new IllegalArgumentException("Invalid fixed size: " + n2);
            }
            this.size = n2;
        }

        @Override
        int computeHash() {
            return super.computeHash() + this.size;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean equals(Object object) {
            block5 : {
                block4 : {
                    if (object == this) break block4;
                    if (!(object instanceof FixedSchema)) {
                        return false;
                    }
                    FixedSchema fixedSchema = (FixedSchema)object;
                    if (!this.equalCachedHash(fixedSchema) || !this.equalNames(fixedSchema) || this.size != fixedSchema.size || !this.props.equals((Object)fixedSchema.props)) break block5;
                }
                return true;
            }
            return false;
        }

        @Override
        public int getFixedSize() {
            return this.size;
        }

        @Override
        void toJson(Names names, JsonGenerator jsonGenerator) throws IOException {
            if (this.writeNameRef(names, jsonGenerator)) {
                return;
            }
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("type", "fixed");
            this.writeName(names, jsonGenerator);
            if (this.getDoc() != null) {
                jsonGenerator.writeStringField("doc", this.getDoc());
            }
            jsonGenerator.writeNumberField("size", this.size);
            this.props.write(jsonGenerator);
            this.aliasesToJson(jsonGenerator);
            jsonGenerator.writeEndObject();
        }
    }

    private static class FloatSchema
    extends Schema {
        public FloatSchema() {
            super(Type.FLOAT);
        }
    }

    private static class IntSchema
    extends Schema {
        public IntSchema() {
            super(Type.INT);
        }
    }

    static class LockableArrayList<E>
    extends ArrayList<E> {
        private static final long serialVersionUID = 1L;
        private boolean locked;

        public LockableArrayList() {
            this.locked = false;
        }

        public LockableArrayList(int n2) {
            super(n2);
            this.locked = false;
        }

        public LockableArrayList(List<E> list) {
            super(list);
            this.locked = false;
        }

        private void ensureUnlocked() {
            if (this.locked) {
                throw new IllegalStateException();
            }
        }

        public boolean add(E e2) {
            LockableArrayList.super.ensureUnlocked();
            return super.add(e2);
        }

        public boolean addAll(int n2, Collection<? extends E> collection) {
            LockableArrayList.super.ensureUnlocked();
            return super.addAll(n2, collection);
        }

        public boolean addAll(Collection<? extends E> collection) {
            LockableArrayList.super.ensureUnlocked();
            return super.addAll(collection);
        }

        public void clear() {
            this.ensureUnlocked();
            super.clear();
        }

        public List<E> lock() {
            this.locked = true;
            return this;
        }

        public E remove(int n2) {
            LockableArrayList.super.ensureUnlocked();
            return (E)super.remove(n2);
        }

        public boolean remove(Object object) {
            LockableArrayList.super.ensureUnlocked();
            return super.remove(object);
        }

        public boolean removeAll(Collection<?> collection) {
            LockableArrayList.super.ensureUnlocked();
            return super.removeAll(collection);
        }

        public boolean retainAll(Collection<?> collection) {
            LockableArrayList.super.ensureUnlocked();
            return super.retainAll(collection);
        }
    }

    private static class LongSchema
    extends Schema {
        public LongSchema() {
            super(Type.LONG);
        }
    }

    private static class MapSchema
    extends Schema {
        private final Schema valueType;

        public MapSchema(Schema schema) {
            super(Type.MAP);
            this.valueType = schema;
        }

        @Override
        int computeHash() {
            return super.computeHash() + this.valueType.computeHash();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean equals(Object object) {
            block5 : {
                block4 : {
                    if (object == this) break block4;
                    if (!(object instanceof MapSchema)) {
                        return false;
                    }
                    MapSchema mapSchema = (MapSchema)object;
                    if (!this.equalCachedHash(mapSchema) || !this.valueType.equals(mapSchema.valueType) || !this.props.equals((Object)mapSchema.props)) break block5;
                }
                return true;
            }
            return false;
        }

        @Override
        public Schema getValueType() {
            return this.valueType;
        }

        @Override
        void toJson(Names names, JsonGenerator jsonGenerator) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("type", "map");
            jsonGenerator.writeFieldName("values");
            this.valueType.toJson(names, jsonGenerator);
            this.props.write(jsonGenerator);
            jsonGenerator.writeEndObject();
        }
    }

    private static class Name {
        private final String full;
        private final String name;
        private final String space;

        /*
         * Enabled aggressive block sorting
         */
        public Name(String string, String string2) {
            if (string == null) {
                this.full = null;
                this.space = null;
                this.name = null;
                return;
            }
            int n2 = string.lastIndexOf(46);
            if (n2 < 0) {
                this.space = string2;
                this.name = Schema.validateName(string);
            } else {
                this.space = string.substring(0, n2);
                this.name = Schema.validateName(string.substring(n2 + 1, string.length()));
            }
            String string3 = this.space == null ? this.name : this.space + "." + this.name;
            this.full = string3;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block6 : {
                block5 : {
                    if (object == this) break block5;
                    if (!(object instanceof Name)) {
                        return false;
                    }
                    Name name = (Name)object;
                    if (this.full != null) {
                        return this.full.equals((Object)name.full);
                    }
                    if (name.full != null) break block6;
                }
                return true;
            }
            return false;
        }

        public String getQualified(String string) {
            if (this.space == null || this.space.equals((Object)string)) {
                return this.name;
            }
            return this.full;
        }

        public int hashCode() {
            if (this.full == null) {
                return 0;
            }
            return this.full.hashCode();
        }

        public String toString() {
            return this.full;
        }

        public void writeName(Names names, JsonGenerator jsonGenerator) throws IOException {
            if (this.name != null) {
                jsonGenerator.writeStringField("name", this.name);
            }
            if (this.space != null) {
                if (!this.space.equals((Object)names.space())) {
                    jsonGenerator.writeStringField("namespace", this.space);
                }
                if (names.space() == null) {
                    names.space(this.space);
                }
            }
        }
    }

    private static abstract class NamedSchema
    extends Schema {
        Set<Name> aliases;
        final String doc;
        final Name name;

        public NamedSchema(Type type, Name name, String string) {
            super(type);
            this.name = name;
            this.doc = string;
            if (PRIMITIVES.containsKey((Object)name.full)) {
                throw new AvroTypeException("Schemas may not be named after primitives: " + name.full);
            }
        }

        @Override
        public void addAlias(String string) {
            if (this.aliases == null) {
                this.aliases = new LinkedHashSet();
            }
            this.aliases.add((Object)new Name(string, this.name.space));
        }

        public void aliasesToJson(JsonGenerator jsonGenerator) throws IOException {
            if (this.aliases == null || this.aliases.size() == 0) {
                return;
            }
            jsonGenerator.writeFieldName("aliases");
            jsonGenerator.writeStartArray();
            Iterator iterator = this.aliases.iterator();
            while (iterator.hasNext()) {
                jsonGenerator.writeString(((Name)iterator.next()).getQualified(this.name.space));
            }
            jsonGenerator.writeEndArray();
        }

        @Override
        int computeHash() {
            return super.computeHash() + this.name.hashCode();
        }

        public boolean equalNames(NamedSchema namedSchema) {
            return this.name.equals(namedSchema.name);
        }

        @Override
        public Set<String> getAliases() {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            if (this.aliases != null) {
                Iterator iterator = this.aliases.iterator();
                while (iterator.hasNext()) {
                    linkedHashSet.add((Object)((Name)iterator.next()).full);
                }
            }
            return linkedHashSet;
        }

        @Override
        public String getDoc() {
            return this.doc;
        }

        @Override
        public String getFullName() {
            return this.name.full;
        }

        @Override
        public String getName() {
            return this.name.name;
        }

        @Override
        public String getNamespace() {
            return this.name.space;
        }

        public void writeName(Names names, JsonGenerator jsonGenerator) throws IOException {
            this.name.writeName(names, jsonGenerator);
        }

        public boolean writeNameRef(Names names, JsonGenerator jsonGenerator) throws IOException {
            if (this.equals(names.get(this.name))) {
                jsonGenerator.writeString(this.name.getQualified(names.space()));
                return true;
            }
            if (this.name.name != null) {
                names.put(this.name, (Schema)this);
            }
            return false;
        }
    }

    static class Names
    extends LinkedHashMap<Name, Schema> {
        private String space;

        public Names() {
        }

        public Names(String string) {
            this.space = string;
        }

        public void add(Schema schema) {
            this.put(((NamedSchema)schema).name, schema);
        }

        public boolean contains(Schema schema) {
            return this.get(((NamedSchema)schema).name) != null;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public Schema get(Object object) {
            Name name;
            if (object instanceof String) {
                Type type = (Type)((Object)Schema.PRIMITIVES.get((Object)((String)object)));
                if (type != null) {
                    return Schema.create(type);
                }
                name = new Name((String)object, this.space);
                do {
                    return (Schema)super.get((Object)name);
                    break;
                } while (true);
            }
            name = (Name)object;
            return (Schema)super.get((Object)name);
        }

        public Schema put(Name name, Schema schema) {
            if (this.containsKey((Object)name)) {
                throw new SchemaParseException("Can't redefine: " + name);
            }
            return (Schema)super.put((Object)name, (Object)schema);
        }

        public String space() {
            return this.space;
        }

        public void space(String string) {
            this.space = string;
        }
    }

    private static class NullSchema
    extends Schema {
        public NullSchema() {
            super(Type.NULL);
        }
    }

    public static class Parser {
        private Names names = new Names();
        private boolean validate = true;

        private Schema parse(JsonParser jsonParser) throws IOException {
            boolean bl = (Boolean)validateNames.get();
            try {
                validateNames.set((Object)this.validate);
                Schema schema = Schema.parse(Schema.MAPPER.readTree(jsonParser), this.names);
                return schema;
            }
            catch (JsonParseException jsonParseException) {
                throw new SchemaParseException((Throwable)((Object)jsonParseException));
            }
            finally {
                validateNames.set((Object)bl);
            }
        }

        public Parser addTypes(Map<String, Schema> map) {
            for (Schema schema : map.values()) {
                this.names.add(schema);
            }
            return this;
        }

        public Map<String, Schema> getTypes() {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Schema schema : this.names.values()) {
                linkedHashMap.put((Object)schema.getFullName(), (Object)schema);
            }
            return linkedHashMap;
        }

        public boolean getValidate() {
            return this.validate;
        }

        public Schema parse(File file) throws IOException {
            return Parser.super.parse(Schema.FACTORY.createJsonParser(file));
        }

        public Schema parse(InputStream inputStream) throws IOException {
            return Parser.super.parse(Schema.FACTORY.createJsonParser(inputStream));
        }

        public Schema parse(String string) {
            try {
                Schema schema = Parser.super.parse(Schema.FACTORY.createJsonParser((Reader)new StringReader(string)));
                return schema;
            }
            catch (IOException iOException) {
                throw new SchemaParseException(iOException);
            }
        }

        public Parser setValidate(boolean bl) {
            this.validate = bl;
            return this;
        }
    }

    static final class Props
    extends LinkedHashMap<String, String> {
        private Set<String> reserved;

        public Props(Set<String> set) {
            super(1);
            this.reserved = set;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void add(String string, String string2) {
            if (this.reserved.contains((Object)string)) {
                throw new AvroRuntimeException("Can't set reserved property: " + string);
            }
            if (string2 == null) {
                throw new AvroRuntimeException("Can't set a property to null: " + string);
            }
            String string3 = (String)this.get((Object)string);
            if (string3 == null) {
                this.put((Object)string, (Object)string2);
                return;
            } else {
                if (string3.equals((Object)string2)) return;
                {
                    throw new AvroRuntimeException("Can't overwrite property: " + string);
                }
            }
        }

        public void write(JsonGenerator jsonGenerator) throws IOException {
            for (Map.Entry entry : this.entrySet()) {
                jsonGenerator.writeStringField((String)entry.getKey(), (String)entry.getValue());
            }
        }
    }

    private static class RecordSchema
    extends NamedSchema {
        private Map<String, Field> fieldMap;
        private List<Field> fields;
        private final boolean isError;

        public RecordSchema(Name name, String string, boolean bl) {
            super(Type.RECORD, name, string);
            this.isError = bl;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        int computeHash() {
            int n2;
            int n3;
            Map map = (Map)SEEN_HASHCODE.get();
            if (map.containsKey((Object)this)) {
                return 0;
            }
            boolean bl = map.isEmpty();
            try {
                map.put((Object)this, (Object)this);
                n2 = super.computeHash();
                n3 = this.fields.hashCode();
            }
            catch (Throwable throwable) {
                if (!bl) throw throwable;
                map.clear();
                throw throwable;
            }
            int n4 = n2 + n3;
            if (!bl) return n4;
            map.clear();
            return n4;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object object) {
            SeenPair seenPair;
            boolean bl = true;
            if (object == this) {
                return bl;
            }
            if (!(object instanceof RecordSchema)) {
                return false;
            }
            RecordSchema recordSchema = (RecordSchema)object;
            if (!this.equalCachedHash(recordSchema)) {
                return false;
            }
            if (!this.equalNames(recordSchema)) {
                return false;
            }
            if (!this.props.equals((Object)recordSchema.props)) {
                return false;
            }
            Set set = (Set)SEEN_EQUALS.get();
            if (set.contains((Object)(seenPair = new SeenPair(this, object, null)))) return bl;
            boolean bl2 = set.isEmpty();
            try {
                boolean bl3;
                set.add((Object)seenPair);
                bl = bl3 = this.fields.equals(((RecordSchema)object).fields);
                if (!bl2) return bl;
            }
            catch (Throwable throwable) {
                if (!bl2) throw throwable;
                set.clear();
                throw throwable;
            }
            set.clear();
            return bl;
        }

        @Override
        void fieldsToJson(Names names, JsonGenerator jsonGenerator) throws IOException {
            jsonGenerator.writeStartArray();
            for (Field field : this.fields) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("name", field.name());
                jsonGenerator.writeFieldName("type");
                field.schema().toJson(names, jsonGenerator);
                if (field.doc() != null) {
                    jsonGenerator.writeStringField("doc", field.doc());
                }
                if (field.defaultValue() != null) {
                    jsonGenerator.writeFieldName("default");
                    jsonGenerator.writeTree(field.defaultValue());
                }
                if (field.order() != Field.Order.ASCENDING) {
                    jsonGenerator.writeStringField("order", field.order().name);
                }
                if (field.aliases != null && field.aliases.size() != 0) {
                    jsonGenerator.writeFieldName("aliases");
                    jsonGenerator.writeStartArray();
                    Iterator iterator = field.aliases.iterator();
                    while (iterator.hasNext()) {
                        jsonGenerator.writeString((String)iterator.next());
                    }
                    jsonGenerator.writeEndArray();
                }
                field.props.write(jsonGenerator);
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }

        @Override
        public Field getField(String string) {
            if (this.fieldMap == null) {
                throw new AvroRuntimeException("Schema fields not set yet");
            }
            return (Field)this.fieldMap.get((Object)string);
        }

        @Override
        public List<Field> getFields() {
            if (this.fields == null) {
                throw new AvroRuntimeException("Schema fields not set yet");
            }
            return this.fields;
        }

        @Override
        public boolean isError() {
            return this.isError;
        }

        @Override
        public void setFields(List<Field> list) {
            if (this.fields != null) {
                throw new AvroRuntimeException("Fields are already set");
            }
            int n2 = 0;
            this.fieldMap = new HashMap();
            LockableArrayList<Field> lockableArrayList = new LockableArrayList<Field>();
            for (Field field : list) {
                if (field.position != -1) {
                    throw new AvroRuntimeException("Field already used: " + field);
                }
                int n3 = n2 + 1;
                field.position = n2;
                this.fieldMap.put((Object)field.name(), (Object)field);
                lockableArrayList.add(field);
                n2 = n3;
            }
            this.fields = lockableArrayList.lock();
            this.hashCode = Integer.MIN_VALUE;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        void toJson(Names names, JsonGenerator jsonGenerator) throws IOException {
            if (this.writeNameRef(names, jsonGenerator)) {
                return;
            }
            String string = names.space;
            jsonGenerator.writeStartObject();
            String string2 = this.isError ? "error" : "record";
            jsonGenerator.writeStringField("type", string2);
            this.writeName(names, jsonGenerator);
            names.space = this.name.space;
            if (this.getDoc() != null) {
                jsonGenerator.writeStringField("doc", this.getDoc());
            }
            jsonGenerator.writeFieldName("fields");
            this.fieldsToJson(names, jsonGenerator);
            this.props.write(jsonGenerator);
            this.aliasesToJson(jsonGenerator);
            jsonGenerator.writeEndObject();
            names.space = string;
        }
    }

    private static class SeenPair {
        private Object s1;
        private Object s2;

        private SeenPair(Object object, Object object2) {
            this.s1 = object;
            this.s2 = object2;
        }

        /* synthetic */ SeenPair(Object object, Object object2, 1 var3_2) {
            super(object, object2);
        }

        public boolean equals(Object object) {
            return this.s1 == ((SeenPair)object).s1 && this.s2 == ((SeenPair)object).s2;
        }

        public int hashCode() {
            return System.identityHashCode((Object)this.s1) + System.identityHashCode((Object)this.s2);
        }
    }

    private static class StringSchema
    extends Schema {
        public StringSchema() {
            super(Type.STRING);
        }
    }

    public static final class Type
    extends Enum<Type> {
        private static final /* synthetic */ Type[] $VALUES;
        public static final /* enum */ Type ARRAY;
        public static final /* enum */ Type BOOLEAN;
        public static final /* enum */ Type BYTES;
        public static final /* enum */ Type DOUBLE;
        public static final /* enum */ Type ENUM;
        public static final /* enum */ Type FIXED;
        public static final /* enum */ Type FLOAT;
        public static final /* enum */ Type INT;
        public static final /* enum */ Type LONG;
        public static final /* enum */ Type MAP;
        public static final /* enum */ Type NULL;
        public static final /* enum */ Type RECORD;
        public static final /* enum */ Type STRING;
        public static final /* enum */ Type UNION;
        private String name;

        static {
            RECORD = new Type();
            ENUM = new Type();
            ARRAY = new Type();
            MAP = new Type();
            UNION = new Type();
            FIXED = new Type();
            STRING = new Type();
            BYTES = new Type();
            INT = new Type();
            LONG = new Type();
            FLOAT = new Type();
            DOUBLE = new Type();
            BOOLEAN = new Type();
            NULL = new Type();
            Type[] arrtype = new Type[]{RECORD, ENUM, ARRAY, MAP, UNION, FIXED, STRING, BYTES, INT, LONG, FLOAT, DOUBLE, BOOLEAN, NULL};
            $VALUES = arrtype;
        }

        private Type() {
            this.name = this.name().toLowerCase();
        }

        public static Type valueOf(String string) {
            return (Type)Enum.valueOf(Type.class, (String)string);
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public String getName() {
            return this.name;
        }
    }

    private static class UnionSchema
    extends Schema {
        private final Map<String, Integer> indexByName = new HashMap();
        private final List<Schema> types;

        public UnionSchema(LockableArrayList<Schema> lockableArrayList) {
            super(Type.UNION);
            this.types = lockableArrayList.lock();
            int n2 = 0;
            Iterator iterator = lockableArrayList.iterator();
            while (iterator.hasNext()) {
                Schema schema = (Schema)iterator.next();
                if (schema.getType() == Type.UNION) {
                    throw new AvroRuntimeException("Nested union: " + this);
                }
                String string = schema.getFullName();
                if (string == null) {
                    throw new AvroRuntimeException("Nameless in union:" + this);
                }
                Map<String, Integer> map = this.indexByName;
                int n3 = n2 + 1;
                if (map.put((Object)string, (Object)n2) != null) {
                    throw new AvroRuntimeException("Duplicate in union:" + string);
                }
                n2 = n3;
            }
        }

        @Override
        public void addProp(String string, String string2) {
            throw new AvroRuntimeException("Can't set properties on a union: " + this);
        }

        @Override
        int computeHash() {
            int n2 = super.computeHash();
            Iterator iterator = this.types.iterator();
            while (iterator.hasNext()) {
                n2 += ((Schema)iterator.next()).computeHash();
            }
            return n2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean equals(Object object) {
            block5 : {
                block4 : {
                    if (object == this) break block4;
                    if (!(object instanceof UnionSchema)) {
                        return false;
                    }
                    UnionSchema unionSchema = (UnionSchema)object;
                    if (!this.equalCachedHash(unionSchema) || !this.types.equals(unionSchema.types) || !this.props.equals((Object)unionSchema.props)) break block5;
                }
                return true;
            }
            return false;
        }

        @Override
        public Integer getIndexNamed(String string) {
            return (Integer)this.indexByName.get((Object)string);
        }

        @Override
        public List<Schema> getTypes() {
            return this.types;
        }

        @Override
        void toJson(Names names, JsonGenerator jsonGenerator) throws IOException {
            jsonGenerator.writeStartArray();
            Iterator iterator = this.types.iterator();
            while (iterator.hasNext()) {
                ((Schema)iterator.next()).toJson(names, jsonGenerator);
            }
            jsonGenerator.writeEndArray();
        }
    }

}


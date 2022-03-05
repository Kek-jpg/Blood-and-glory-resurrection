/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.node.NullNode
 *  com.thoughtworks.paranamer.CachingParanamer
 *  com.thoughtworks.paranamer.Paranamer
 *  java.lang.Boolean
 *  java.lang.Byte
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.ClassNotFoundException
 *  java.lang.Enum
 *  java.lang.IllegalAccessException
 *  java.lang.Math
 *  java.lang.NoSuchFieldError
 *  java.lang.NoSuchFieldException
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.Package
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.Void
 *  java.lang.annotation.Annotation
 *  java.lang.reflect.AccessibleObject
 *  java.lang.reflect.Array
 *  java.lang.reflect.Field
 *  java.lang.reflect.GenericArrayType
 *  java.lang.reflect.Method
 *  java.lang.reflect.ParameterizedType
 *  java.lang.reflect.Type
 *  java.nio.ByteBuffer
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.concurrent.ConcurrentHashMap
 */
package com.flurry.org.apache.avro.reflect;

import com.flurry.org.apache.avro.AvroRemoteException;
import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.Protocol;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.generic.GenericContainer;
import com.flurry.org.apache.avro.generic.GenericFixed;
import com.flurry.org.apache.avro.generic.IndexedRecord;
import com.flurry.org.apache.avro.io.BinaryData;
import com.flurry.org.apache.avro.io.DatumReader;
import com.flurry.org.apache.avro.reflect.Nullable;
import com.flurry.org.apache.avro.reflect.ReflectDatumReader;
import com.flurry.org.apache.avro.reflect.Stringable;
import com.flurry.org.apache.avro.reflect.Union;
import com.flurry.org.apache.avro.specific.FixedSize;
import com.flurry.org.apache.avro.specific.SpecificData;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.node.NullNode;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectData
extends SpecificData {
    private static final Class BYTES_CLASS;
    static final String CLASS_PROP = "java-class";
    static final String ELEMENT_PROP = "java-element-class";
    private static final Map<Class, Map<String, Field>> FIELD_CACHE;
    private static final ReflectData INSTANCE;
    private static final Schema THROWABLE_MESSAGE;
    private final Paranamer paranamer = new CachingParanamer();

    static {
        INSTANCE = new ReflectData();
        FIELD_CACHE = new ConcurrentHashMap();
        BYTES_CLASS = new byte[0].getClass();
        THROWABLE_MESSAGE = ReflectData.makeNullable(Schema.create(Schema.Type.STRING));
    }

    protected ReflectData() {
    }

    private static Field findField(Class class_, String string) {
        Class class_2 = class_;
        do {
            try {
                Field field = class_2.getDeclaredField(string);
                field.setAccessible(true);
                return field;
            }
            catch (NoSuchFieldException noSuchFieldException) {
                if ((class_2 = class_2.getSuperclass()) != null) continue;
                throw new AvroRuntimeException("No field named " + string + " in: " + (Object)class_);
            }
            break;
        } while (true);
    }

    public static ReflectData get() {
        return INSTANCE;
    }

    private Schema getAnnotatedUnion(Union union, Map<String, Schema> map) {
        ArrayList arrayList = new ArrayList();
        Class[] arrclass = union.value();
        int n2 = arrclass.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add((Object)this.createSchema((Type)arrclass[i2], map));
        }
        return Schema.createUnion((List<Schema>)arrayList);
    }

    static Class getClassProp(Schema schema, String string) {
        String string2 = schema.getProp(string);
        if (string2 == null) {
            return null;
        }
        try {
            Class class_ = Class.forName((String)string2);
            return class_;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new AvroRuntimeException(classNotFoundException);
        }
    }

    private static Field getField(Class class_, String string) {
        Field field;
        Map map = (Map)FIELD_CACHE.get((Object)class_);
        if (map == null) {
            map = new ConcurrentHashMap();
            FIELD_CACHE.put((Object)class_, (Object)map);
        }
        if ((field = (Field)map.get((Object)string)) == null) {
            field = ReflectData.findField(class_, string);
            map.put((Object)string, (Object)field);
        }
        return field;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Collection<Field> getFields(Class class_) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Class class_2 = class_;
        do {
            if (class_2.getPackage() != null && class_2.getPackage().getName().startsWith("java.")) {
                do {
                    return linkedHashMap.values();
                    break;
                } while (true);
            }
            for (Field field : class_2.getDeclaredFields()) {
                if ((136 & field.getModifiers()) != 0 || linkedHashMap.put((Object)field.getName(), (Object)field) == null) continue;
                throw new AvroTypeException((Object)class_2 + " contains two fields named: " + (Object)field);
            }
        } while ((class_2 = class_2.getSuperclass()) != null);
        return linkedHashMap.values();
    }

    /*
     * Enabled aggressive block sorting
     */
    private Protocol.Message getMessage(Method method, Protocol protocol, Map<String, Schema> map) {
        ArrayList arrayList = new ArrayList();
        String[] arrstring = this.paranamer.lookupParameterNames((AccessibleObject)method);
        Type[] arrtype = method.getGenericParameterTypes();
        Annotation[][] arrannotation = method.getParameterAnnotations();
        int n2 = 0;
        do {
            Schema schema;
            int n3;
            if (n2 < arrtype.length) {
                schema = ReflectData.super.getSchema(arrtype[n2], map);
            } else {
                Schema schema2 = Schema.createRecord((List<Schema.Field>)arrayList);
                Union union = (Union)method.getAnnotation(Union.class);
                Schema schema3 = union == null ? ReflectData.super.getSchema(method.getGenericReturnType(), map) : ReflectData.super.getAnnotatedUnion(union, map);
                if (method.isAnnotationPresent(Nullable.class)) {
                    schema3 = ReflectData.makeNullable(schema3);
                }
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add((Object)Protocol.SYSTEM_ERROR);
                Type[] arrtype2 = method.getGenericExceptionTypes();
                int n4 = arrtype2.length;
                int n5 = 0;
                do {
                    if (n5 >= n4) {
                        Schema schema4 = Schema.createUnion((List<Schema>)arrayList2);
                        return protocol.createMessage(method.getName(), null, schema2, schema3, schema4);
                    }
                    Type type = arrtype2[n5];
                    if (type != AvroRemoteException.class) {
                        arrayList2.add((Object)ReflectData.super.getSchema(type, map));
                    }
                    ++n5;
                } while (true);
            }
            for (int i2 = 0; i2 < (n3 = arrannotation[n2].length); ++i2) {
                if (arrannotation[n2][i2] instanceof Union) {
                    schema = ReflectData.super.getAnnotatedUnion((Union)arrannotation[n2][i2], map);
                    continue;
                }
                if (!(arrannotation[n2][i2] instanceof Nullable)) continue;
                schema = ReflectData.makeNullable(schema);
            }
            String string = arrstring.length == arrtype.length ? arrstring[n2] : schema.getName() + n2;
            arrayList.add((Object)new Schema.Field(string, schema, null, null));
            ++n2;
        } while (true);
    }

    private Schema getSchema(Type type, Map<String, Schema> map) {
        try {
            Schema schema = this.createSchema(type, map);
            return schema;
        }
        catch (AvroTypeException avroTypeException) {
            throw new AvroTypeException("Error getting schema for " + (Object)type + ": " + avroTypeException.getMessage(), (Throwable)((Object)avroTypeException));
        }
    }

    public static Schema makeNullable(Schema schema) {
        Object[] arrobject = new Schema[]{Schema.create(Schema.Type.NULL), schema};
        return Schema.createUnion((List<Schema>)Arrays.asList((Object[])arrobject));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setElement(Schema schema, Type type) {
        Class class_;
        if (!(type instanceof Class) || (Union)(class_ = (Class)type).getAnnotation(Union.class) == null) {
            return;
        }
        schema.addProp(ELEMENT_PROP, class_.getName());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected int compare(Object object, Object object2, Schema schema, boolean bl) {
        switch (schema.getType()) {
            default: {
                return super.compare(object, object2, schema, bl);
            }
            case ARRAY: {
                if (!object.getClass().isArray()) return super.compare(object, object2, schema, bl);
                Schema schema2 = schema.getElementType();
                int n2 = Array.getLength((Object)object);
                int n3 = Array.getLength((Object)object2);
                int n4 = Math.min((int)n2, (int)n3);
                int n5 = 0;
                while (n5 < n4) {
                    int n6 = this.compare(Array.get((Object)object, (int)n5), Array.get((Object)object2, (int)n5), schema2, bl);
                    if (n6 != 0) return n6;
                    ++n5;
                }
                return n2 - n3;
            }
            case BYTES: 
        }
        if (!object.getClass().isArray()) return super.compare(object, object2, schema, bl);
        byte[] arrby = (byte[])object;
        byte[] arrby2 = (byte[])object2;
        return BinaryData.compareBytes(arrby, 0, arrby.length, arrby2, 0, arrby2.length);
    }

    @Override
    public DatumReader createDatumReader(Schema schema) {
        return new ReflectDatumReader(schema, schema, (ReflectData)this);
    }

    protected Schema createFieldSchema(Field field, Map<String, Schema> map) {
        Schema schema = this.createSchema(field.getGenericType(), map);
        if (field.isAnnotationPresent(Nullable.class)) {
            schema = ReflectData.makeNullable(schema);
        }
        return schema;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected Schema createSchema(Type type, Map<String, Schema> map) {
        if (type instanceof GenericArrayType) {
            Type type2 = ((GenericArrayType)type).getGenericComponentType();
            if (type2 == Byte.TYPE) {
                return Schema.create(Schema.Type.BYTES);
            }
            Schema schema = Schema.createArray(this.createSchema(type2, map));
            ReflectData.super.setElement(schema, type2);
            return schema;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Class class_ = (Class)parameterizedType.getRawType();
            Type[] arrtype = parameterizedType.getActualTypeArguments();
            if (Map.class.isAssignableFrom(class_)) {
                Type type3 = arrtype[0];
                Type type4 = arrtype[1];
                if (type3 == String.class) return Schema.createMap(this.createSchema(type4, map));
                {
                    throw new AvroTypeException("Map key class not String: " + (Object)type3);
                }
            }
            if (!Collection.class.isAssignableFrom(class_)) return super.createSchema(type, map);
            {
                if (arrtype.length != 1) {
                    throw new AvroTypeException("No array type specified.");
                }
                Schema schema = Schema.createArray(this.createSchema(arrtype[0], map));
                schema.addProp(CLASS_PROP, class_.getName());
                return schema;
            }
        } else {
            if (type == Byte.class || type == Byte.TYPE) {
                Schema schema = Schema.create(Schema.Type.INT);
                schema.addProp(CLASS_PROP, Byte.class.getName());
                return schema;
            }
            if (type == Short.class || type == Short.TYPE) {
                Schema schema = Schema.create(Schema.Type.INT);
                schema.addProp(CLASS_PROP, Short.class.getName());
                return schema;
            }
            if (!(type instanceof Class)) return super.createSchema(type, map);
            {
                Class class_ = (Class)type;
                if (class_.isPrimitive() || Number.class.isAssignableFrom(class_) || class_ == Void.class || class_ == Boolean.class) {
                    return super.createSchema(type, map);
                }
                if (class_.isArray()) {
                    Class class_2 = class_.getComponentType();
                    if (class_2 == Byte.TYPE) {
                        return Schema.create(Schema.Type.BYTES);
                    }
                    Schema schema = Schema.createArray(this.createSchema((Type)class_2, map));
                    ReflectData.super.setElement(schema, (Type)class_2);
                    return schema;
                }
                if (CharSequence.class.isAssignableFrom(class_)) {
                    return Schema.create(Schema.Type.STRING);
                }
                if (ByteBuffer.class.isAssignableFrom(class_)) {
                    return Schema.create(Schema.Type.BYTES);
                }
                if (Collection.class.isAssignableFrom(class_)) {
                    throw new AvroRuntimeException("Can't find element type of Collection");
                }
                String string = class_.getName();
                Schema schema = (Schema)map.get((Object)string);
                if (schema != null) return schema;
                {
                    Union union;
                    String string2 = class_.getSimpleName();
                    String string3 = class_.getPackage() == null ? "" : class_.getPackage().getName();
                    if (class_.getEnclosingClass() != null) {
                        string3 = class_.getEnclosingClass().getName() + "$";
                    }
                    if ((union = (Union)class_.getAnnotation(Union.class)) != null) {
                        return ReflectData.super.getAnnotatedUnion(union, map);
                    }
                    if (class_.isAnnotationPresent(Stringable.class)) {
                        Schema schema2 = Schema.create(Schema.Type.STRING);
                        schema2.addProp(CLASS_PROP, class_.getName());
                        return schema2;
                    }
                    if (class_.isEnum()) {
                        ArrayList arrayList = new ArrayList();
                        Enum[] arrenum = (Enum[])class_.getEnumConstants();
                        for (int i2 = 0; i2 < arrenum.length; ++i2) {
                            arrayList.add((Object)arrenum[i2].name());
                        }
                        schema = Schema.createEnum(string2, null, string3, (List<String>)arrayList);
                    } else if (GenericFixed.class.isAssignableFrom(class_)) {
                        int n2 = ((FixedSize)class_.getAnnotation(FixedSize.class)).value();
                        schema = Schema.createFixed(string2, null, string3, n2);
                    } else {
                        if (IndexedRecord.class.isAssignableFrom(class_)) {
                            return super.createSchema(type, map);
                        }
                        ArrayList arrayList = new ArrayList();
                        boolean bl = Throwable.class.isAssignableFrom(class_);
                        schema = Schema.createRecord(string2, null, string3, bl);
                        map.put((Object)class_.getName(), (Object)schema);
                        for (Field field : ReflectData.super.getFields(class_)) {
                            if ((136 & field.getModifiers()) != 0) continue;
                            Schema schema3 = this.createFieldSchema(field, map);
                            Schema.Type type5 = schema3.getType();
                            Schema.Type type6 = Schema.Type.UNION;
                            NullNode nullNode = null;
                            if (type5 == type6) {
                                Schema.Type type7 = ((Schema)schema3.getTypes().get(0)).getType();
                                Schema.Type type8 = Schema.Type.NULL;
                                nullNode = null;
                                if (type7 == type8) {
                                    nullNode = NullNode.getInstance();
                                }
                            }
                            Schema.Field field2 = new Schema.Field(field.getName(), schema3, null, (JsonNode)nullNode);
                            arrayList.add((Object)field2);
                        }
                        if (bl) {
                            arrayList.add((Object)new Schema.Field("detailMessage", THROWABLE_MESSAGE, null, null));
                        }
                        schema.setFields((List<Schema.Field>)arrayList);
                    }
                    map.put((Object)string, (Object)schema);
                }
                return schema;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Class getClass(Schema schema) {
        switch (1.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()]) {
            default: {
                return super.getClass(schema);
            }
            case 1: {
                Class class_ = ReflectData.getClassProp(schema, CLASS_PROP);
                if (class_ != null) return class_;
                return Array.newInstance((Class)this.getClass(schema.getElementType()), (int)0).getClass();
            }
            case 2: {
                return String.class;
            }
            case 3: {
                return BYTES_CLASS;
            }
            case 4: 
        }
        String string = schema.getProp(CLASS_PROP);
        if (Byte.class.getName().equals((Object)string)) {
            return Byte.TYPE;
        }
        if (!Short.class.getName().equals((Object)string)) return super.getClass(schema);
        return Short.TYPE;
    }

    @Override
    public Object getField(Object object, String string, int n2) {
        if (object instanceof IndexedRecord) {
            return super.getField(object, string, n2);
        }
        try {
            Object object2 = ReflectData.getField(object.getClass(), string).get(object);
            return object2;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new AvroRuntimeException(illegalAccessException);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Protocol getProtocol(Class class_) {
        String string = class_.getSimpleName();
        String string2 = class_.getPackage() == null ? "" : class_.getPackage().getName();
        Protocol protocol = new Protocol(string, string2);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Map<String, Protocol.Message> map = protocol.getMessages();
        Method[] arrmethod = class_.getMethods();
        int n2 = arrmethod.length;
        int n3 = 0;
        do {
            if (n3 >= n2) {
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(linkedHashMap.values());
                Collections.reverse((List)arrayList);
                protocol.setTypes((Collection<Schema>)arrayList);
                return protocol;
            }
            Method method = arrmethod[n3];
            if ((8 & method.getModifiers()) == 0) {
                String string3 = method.getName();
                if (map.containsKey((Object)string3)) {
                    throw new AvroTypeException("Two methods with same name: " + string3);
                }
                map.put((Object)string3, (Object)ReflectData.super.getMessage(method, protocol, (Map<String, Schema>)linkedHashMap));
            }
            ++n3;
        } while (true);
    }

    @Override
    protected Schema getRecordSchema(Object object) {
        if (object instanceof GenericContainer) {
            return super.getRecordSchema(object);
        }
        return this.getSchema((Type)object.getClass());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected boolean isArray(Object object) {
        return object != null && (object instanceof Collection || object.getClass().isArray());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected boolean isBytes(Object object) {
        boolean bl = true;
        if (object == null) {
            return false;
        }
        if (super.isBytes(object)) return bl;
        Class class_ = object.getClass();
        if (!class_.isArray()) return false;
        if (class_.getComponentType() == Byte.TYPE) return bl;
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected boolean isRecord(Object object) {
        boolean bl = true;
        if (object == null) {
            return false;
        }
        if (super.isRecord(object)) {
            return bl;
        }
        if (object instanceof Collection) return false;
        if (this.getSchema((Type)object.getClass()).getType() != Schema.Type.RECORD) return false;
        return bl;
    }

    @Override
    public void setField(Object object, String string, int n2, Object object2) {
        if (object instanceof IndexedRecord) {
            super.setField(object, string, n2, object2);
            return;
        }
        try {
            ReflectData.getField(object.getClass(), string).set(object, object2);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new AvroRuntimeException(illegalAccessException);
        }
    }

    @Override
    public boolean validate(Schema schema, Object object) {
        switch (schema.getType()) {
            default: {
                return super.validate(schema, object);
            }
            case ARRAY: 
        }
        if (!object.getClass().isArray()) {
            return super.validate(schema, object);
        }
        int n2 = Array.getLength((Object)object);
        for (int i2 = 0; i2 < n2; ++i2) {
            if (this.validate(schema.getElementType(), Array.get((Object)object, (int)i2))) continue;
            return false;
        }
        return true;
    }

    public static class AllowNull
    extends ReflectData {
        private static final AllowNull INSTANCE = new AllowNull();

        public static AllowNull get() {
            return INSTANCE;
        }

        @Override
        protected Schema createFieldSchema(Field field, Map<String, Schema> map) {
            return AllowNull.makeNullable(super.createFieldSchema(field, map));
        }
    }

}


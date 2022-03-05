/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.ClassLoader
 *  java.lang.ClassNotFoundException
 *  java.lang.Double
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.IllegalAccessException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.NoSuchFieldError
 *  java.lang.NoSuchFieldException
 *  java.lang.Object
 *  java.lang.Package
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.Void
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.Field
 *  java.lang.reflect.ParameterizedType
 *  java.lang.reflect.Type
 *  java.nio.ByteBuffer
 *  java.util.Collection
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.WeakHashMap
 *  java.util.concurrent.ConcurrentHashMap
 */
package com.flurry.org.apache.avro.specific;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.Protocol;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.generic.GenericData;
import com.flurry.org.apache.avro.io.DatumReader;
import com.flurry.org.apache.avro.specific.SpecificDatumReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SpecificData
extends GenericData {
    private static final Map<Class, Constructor> CTOR_CACHE;
    private static final SpecificData INSTANCE;
    private static final Class<?>[] NO_ARG;
    private static final Class NO_CLASS;
    private static final Schema NULL_SCHEMA;
    private static final Class<?>[] SCHEMA_ARG;
    private Map<String, Class> classCache = new ConcurrentHashMap();
    private final ClassLoader classLoader;
    private final WeakHashMap<Type, Schema> schemaCache = new WeakHashMap();

    static {
        INSTANCE = new SpecificData();
        NO_ARG = new Class[0];
        SCHEMA_ARG = new Class[]{Schema.class};
        CTOR_CACHE = new ConcurrentHashMap();
        NO_CLASS = new Object(){}.getClass();
        NULL_SCHEMA = Schema.create(Schema.Type.NULL);
    }

    protected SpecificData() {
        this(SpecificData.class.getClassLoader());
    }

    public SpecificData(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public static SpecificData get() {
        return INSTANCE;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String getClassName(Schema schema) {
        String string;
        String string2 = schema.getNamespace();
        String string3 = schema.getName();
        if (string2 == null || "".equals((Object)string2)) {
            return string3;
        }
        if (string2.endsWith("$")) {
            string = "";
            do {
                return string2 + string + string3;
                break;
            } while (true);
        }
        string = ".";
        return string2 + string + string3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Object newInstance(Class class_, Schema schema) {
        boolean bl = SchemaConstructable.class.isAssignableFrom(class_);
        try {
            Object[] arrobject;
            Constructor constructor = (Constructor)CTOR_CACHE.get((Object)class_);
            if (constructor == null) {
                Class<?>[] arrclass = bl ? SCHEMA_ARG : NO_ARG;
                constructor = class_.getDeclaredConstructor(arrclass);
                constructor.setAccessible(true);
                CTOR_CACHE.put((Object)class_, (Object)constructor);
            }
            if (bl) {
                arrobject = new Object[]{schema};
                return constructor.newInstance(arrobject);
            }
            arrobject = null;
            return constructor.newInstance(arrobject);
        }
        catch (Exception exception) {
            throw new RuntimeException((Throwable)exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected int compare(Object object, Object object2, Schema schema, boolean bl) {
        switch (schema.getType()) {
            default: {
                return super.compare(object, object2, schema, bl);
            }
            case ENUM: {
                if (!(object instanceof Enum)) return super.compare(object, object2, schema, bl);
                return ((Enum)object).ordinal() - ((Enum)object2).ordinal();
            }
        }
    }

    @Override
    public DatumReader createDatumReader(Schema schema) {
        return new SpecificDatumReader(schema, schema, (SpecificData)this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object createFixed(Object object, Schema schema) {
        Class class_ = SpecificData.get().getClass(schema);
        if (class_ == null) {
            return super.createFixed(object, schema);
        }
        if (class_.isInstance(object)) return object;
        return SpecificData.newInstance(class_, schema);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected Schema createSchema(Type type, Map<String, Schema> map) {
        Schema schema;
        String string;
        Class class_;
        block14 : {
            if (type instanceof Class && CharSequence.class.isAssignableFrom((Class)type)) {
                return Schema.create(Schema.Type.STRING);
            }
            if (type == ByteBuffer.class) {
                return Schema.create(Schema.Type.BYTES);
            }
            if (type == Integer.class) return Schema.create(Schema.Type.INT);
            if (type == Integer.TYPE) {
                return Schema.create(Schema.Type.INT);
            }
            if (type == Long.class) return Schema.create(Schema.Type.LONG);
            if (type == Long.TYPE) {
                return Schema.create(Schema.Type.LONG);
            }
            if (type == Float.class) return Schema.create(Schema.Type.FLOAT);
            if (type == Float.TYPE) {
                return Schema.create(Schema.Type.FLOAT);
            }
            if (type == Double.class) return Schema.create(Schema.Type.DOUBLE);
            if (type == Double.TYPE) {
                return Schema.create(Schema.Type.DOUBLE);
            }
            if (type == Boolean.class) return Schema.create(Schema.Type.BOOLEAN);
            if (type == Boolean.TYPE) {
                return Schema.create(Schema.Type.BOOLEAN);
            }
            if (type == Void.class) return Schema.create(Schema.Type.NULL);
            if (type == Void.TYPE) {
                return Schema.create(Schema.Type.NULL);
            }
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType)type;
                Class class_2 = (Class)parameterizedType.getRawType();
                Type[] arrtype = parameterizedType.getActualTypeArguments();
                if (Collection.class.isAssignableFrom(class_2)) {
                    if (arrtype.length == 1) return Schema.createArray(this.createSchema(arrtype[0], map));
                    throw new AvroTypeException("No array type specified.");
                }
                if (!Map.class.isAssignableFrom(class_2)) return this.createSchema((Type)class_2, map);
                Type type2 = arrtype[0];
                Type type3 = arrtype[1];
                if (!(type instanceof Class)) throw new AvroTypeException("Map key class not CharSequence: " + (Object)type2);
                if (CharSequence.class.isAssignableFrom((Class)type)) return Schema.createMap(this.createSchema(type3, map));
                throw new AvroTypeException("Map key class not CharSequence: " + (Object)type2);
            }
            if (!(type instanceof Class)) throw new AvroTypeException("Unknown type: " + (Object)type);
            class_ = (Class)type;
            string = class_.getName();
            schema = (Schema)map.get((Object)string);
            if (schema == null) {
                Schema schema2;
                schema = (Schema)class_.getDeclaredField("SCHEMA$").get(null);
                if (string.equals((Object)SpecificData.getClassName(schema))) break block14;
                schema = schema2 = Schema.parse(schema.toString().replace((CharSequence)schema.getNamespace(), (CharSequence)class_.getPackage().getName()));
            }
        }
        map.put((Object)string, (Object)schema);
        return schema;
        catch (NoSuchFieldException noSuchFieldException) {
            throw new AvroRuntimeException("Not a Specific class: " + (Object)class_);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new AvroRuntimeException(illegalAccessException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Class getClass(Schema schema) {
        switch (2.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()]) {
            default: {
                throw new AvroRuntimeException("Unknown type: " + schema);
            }
            case 1: 
            case 2: 
            case 3: {
                String string = schema.getFullName();
                if (string == null) {
                    return null;
                }
                Class class_ = (Class)this.classCache.get((Object)string);
                if (class_ == null) {
                    try {
                        Class class_2;
                        class_ = class_2 = this.classLoader.loadClass(SpecificData.getClassName(schema));
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        class_ = NO_CLASS;
                    }
                    this.classCache.put((Object)string, (Object)class_);
                }
                if (class_ != NO_CLASS) return class_;
                return null;
            }
            case 4: {
                return List.class;
            }
            case 5: {
                return Map.class;
            }
            case 6: {
                int n2;
                List<Schema> list = schema.getTypes();
                if (list.size() != 2) return Object.class;
                if (!list.contains((Object)NULL_SCHEMA)) return Object.class;
                if (((Schema)list.get(0)).equals(NULL_SCHEMA)) {
                    n2 = 1;
                    return this.getClass((Schema)list.get(n2));
                }
                n2 = 0;
                return this.getClass((Schema)list.get(n2));
            }
            case 7: {
                if (!"String".equals((Object)schema.getProp("avro.java.string"))) return CharSequence.class;
                return String.class;
            }
            case 8: {
                return ByteBuffer.class;
            }
            case 9: {
                return Integer.TYPE;
            }
            case 10: {
                return Long.TYPE;
            }
            case 11: {
                return Float.TYPE;
            }
            case 12: {
                return Double.TYPE;
            }
            case 13: {
                return Boolean.TYPE;
            }
            case 14: 
        }
        return Void.TYPE;
    }

    @Override
    protected Schema getEnumSchema(Object object) {
        if (object instanceof Enum) {
            return this.getSchema((Type)object.getClass());
        }
        return super.getEnumSchema(object);
    }

    public Protocol getProtocol(Class class_) {
        try {
            Protocol protocol = (Protocol)class_.getDeclaredField("PROTOCOL").get(null);
            if (!protocol.getNamespace().equals((Object)class_.getPackage().getName())) {
                Protocol protocol2;
                protocol = protocol2 = Protocol.parse(protocol.toString().replace((CharSequence)protocol.getNamespace(), (CharSequence)class_.getPackage().getName()));
            }
            return protocol;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            throw new AvroRuntimeException("Not a Specific protocol: " + (Object)class_);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new AvroRuntimeException(illegalAccessException);
        }
    }

    public Schema getSchema(Type type) {
        Schema schema = (Schema)this.schemaCache.get((Object)type);
        if (schema == null) {
            schema = this.createSchema(type, (Map<String, Schema>)new LinkedHashMap());
            this.schemaCache.put((Object)type, (Object)schema);
        }
        return schema;
    }

    @Override
    protected boolean isEnum(Object object) {
        return object instanceof Enum || super.isEnum(object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object newRecord(Object object, Schema schema) {
        Class class_ = SpecificData.get().getClass(schema);
        if (class_ == null) {
            return super.newRecord(object, schema);
        }
        if (class_.isInstance(object)) return object;
        return SpecificData.newInstance(class_, schema);
    }

    public static interface SchemaConstructable {
    }

}


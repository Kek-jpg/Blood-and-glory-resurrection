/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.LinkageError
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Map$Entry
 */
package com.flurry.org.codehaus.jackson.map.ext;

import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.deser.std.StdDeserializer;
import com.flurry.org.codehaus.jackson.map.util.Provider;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class OptionalHandlerFactory {
    private static final String CLASS_NAME_DOM_DOCUMENT = "org.w3c.dom.Node";
    private static final String CLASS_NAME_DOM_NODE = "org.w3c.dom.Node";
    private static final String DESERIALIZERS_FOR_JAVAX_XML = "com.flurry.org.codehaus.jackson.map.ext.CoreXMLDeserializers";
    private static final String DESERIALIZERS_FOR_JODA_DATETIME = "com.flurry.org.codehaus.jackson.map.ext.JodaDeserializers";
    private static final String DESERIALIZER_FOR_DOM_DOCUMENT = "com.flurry.org.codehaus.jackson.map.ext.DOMDeserializer$DocumentDeserializer";
    private static final String DESERIALIZER_FOR_DOM_NODE = "com.flurry.org.codehaus.jackson.map.ext.DOMDeserializer$NodeDeserializer";
    private static final String PACKAGE_PREFIX_JAVAX_XML = "javax.xml.";
    private static final String PACKAGE_PREFIX_JODA_DATETIME = "org.joda.time.";
    private static final String SERIALIZERS_FOR_JAVAX_XML = "com.flurry.org.codehaus.jackson.map.ext.CoreXMLSerializers";
    private static final String SERIALIZERS_FOR_JODA_DATETIME = "com.flurry.org.codehaus.jackson.map.ext.JodaSerializers";
    private static final String SERIALIZER_FOR_DOM_NODE = "com.flurry.org.codehaus.jackson.map.ext.DOMSerializer";
    public static final OptionalHandlerFactory instance = new OptionalHandlerFactory();

    protected OptionalHandlerFactory() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean doesImplement(Class<?> class_, String string) {
        Class class_2 = class_;
        while (class_2 != null) {
            if (class_2.getName().equals((Object)string) || OptionalHandlerFactory.super.hasInterface(class_2, string)) {
                return true;
            }
            class_2 = class_2.getSuperclass();
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean hasInterface(Class<?> class_, String string) {
        Class[] arrclass = class_.getInterfaces();
        int n = arrclass.length;
        for (int i = 0; i < n; ++i) {
            if (arrclass[i].getName().equals((Object)string)) return true;
            {
                continue;
            }
        }
        int n2 = arrclass.length;
        int n3 = 0;
        while (n3 < n2) {
            if (OptionalHandlerFactory.super.hasInterface(arrclass[n3], string)) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean hasInterfaceStartingWith(Class<?> class_, String string) {
        Class[] arrclass = class_.getInterfaces();
        int n = arrclass.length;
        for (int i = 0; i < n; ++i) {
            if (arrclass[i].getName().startsWith(string)) return true;
            {
                continue;
            }
        }
        int n2 = arrclass.length;
        int n3 = 0;
        while (n3 < n2) {
            if (OptionalHandlerFactory.super.hasInterfaceStartingWith(arrclass[n3], string)) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean hasSupertypeStartingWith(Class<?> class_, String string) {
        for (Class class_2 = class_.getSuperclass(); class_2 != null; class_2 = class_2.getSuperclass()) {
            if (class_2.getName().startsWith(string)) return true;
            {
                continue;
            }
        }
        Class class_3 = class_;
        while (class_3 != null) {
            if (OptionalHandlerFactory.super.hasInterfaceStartingWith(class_3, string)) {
                return true;
            }
            class_3 = class_3.getSuperclass();
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Object instantiate(String string) {
        try {
            return Class.forName((String)string).newInstance();
        }
        catch (Exception exception) {
            do {
                return null;
                break;
            } while (true);
        }
        catch (LinkageError linkageError) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonDeserializer<?> findDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider) {
        String string;
        Object object;
        StdDeserializer stdDeserializer;
        Class<?> class_ = javaType.getRawClass();
        String string2 = class_.getName();
        if (string2.startsWith(PACKAGE_PREFIX_JODA_DATETIME)) {
            string = DESERIALIZERS_FOR_JODA_DATETIME;
        } else {
            if (!string2.startsWith(PACKAGE_PREFIX_JAVAX_XML) && !OptionalHandlerFactory.super.hasSupertypeStartingWith(class_, PACKAGE_PREFIX_JAVAX_XML)) {
                if (OptionalHandlerFactory.super.doesImplement(class_, "org.w3c.dom.Node")) {
                    return (JsonDeserializer)OptionalHandlerFactory.super.instantiate(DESERIALIZER_FOR_DOM_DOCUMENT);
                }
                if (!OptionalHandlerFactory.super.doesImplement(class_, "org.w3c.dom.Node")) return null;
                return (JsonDeserializer)OptionalHandlerFactory.super.instantiate(DESERIALIZER_FOR_DOM_NODE);
            }
            string = DESERIALIZERS_FOR_JAVAX_XML;
        }
        if ((object = OptionalHandlerFactory.super.instantiate(string)) == null) {
            return null;
        }
        Collection collection = ((Provider)object).provide();
        for (StdDeserializer stdDeserializer2 : collection) {
            if (class_ != stdDeserializer2.getValueClass()) continue;
            return stdDeserializer2;
        }
        Iterator iterator = collection.iterator();
        do {
            if (iterator.hasNext()) continue;
            return null;
        } while (!(stdDeserializer = (StdDeserializer)iterator.next()).getValueClass().isAssignableFrom(class_));
        return stdDeserializer;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonSerializer<?> findSerializer(SerializationConfig serializationConfig, JavaType javaType) {
        Object object;
        Map.Entry entry;
        String string;
        Class<?> class_ = javaType.getRawClass();
        String string2 = class_.getName();
        if (string2.startsWith(PACKAGE_PREFIX_JODA_DATETIME)) {
            string = SERIALIZERS_FOR_JODA_DATETIME;
        } else {
            if (!string2.startsWith(PACKAGE_PREFIX_JAVAX_XML) && !OptionalHandlerFactory.super.hasSupertypeStartingWith(class_, PACKAGE_PREFIX_JAVAX_XML)) {
                if (OptionalHandlerFactory.super.doesImplement(class_, "org.w3c.dom.Node")) {
                    return (JsonSerializer)OptionalHandlerFactory.super.instantiate(SERIALIZER_FOR_DOM_NODE);
                }
                return null;
            }
            string = SERIALIZERS_FOR_JAVAX_XML;
        }
        if ((object = OptionalHandlerFactory.super.instantiate(string)) == null) {
            return null;
        }
        Collection collection = ((Provider)object).provide();
        for (Map.Entry entry2 : collection) {
            if (class_ != entry2.getKey()) continue;
            return (JsonSerializer)entry2.getValue();
        }
        Iterator iterator = collection.iterator();
        do {
            if (iterator.hasNext()) continue;
            return null;
        } while (!((Class)(entry = (Map.Entry)iterator.next()).getKey()).isAssignableFrom(class_));
        return (JsonSerializer)entry.getValue();
    }
}


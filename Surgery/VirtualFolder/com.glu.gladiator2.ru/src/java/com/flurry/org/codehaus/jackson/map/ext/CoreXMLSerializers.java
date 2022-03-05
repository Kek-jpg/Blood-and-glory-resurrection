/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.reflect.Type
 *  java.util.Calendar
 *  java.util.Collection
 *  java.util.GregorianCalendar
 *  java.util.HashMap
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  javax.xml.datatype.Duration
 *  javax.xml.datatype.XMLGregorianCalendar
 *  javax.xml.namespace.QName
 */
package com.flurry.org.codehaus.jackson.map.ext;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.ser.std.CalendarSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.ToStringSerializer;
import com.flurry.org.codehaus.jackson.map.util.Provider;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

public class CoreXMLSerializers
implements Provider<Map.Entry<Class<?>, JsonSerializer<?>>> {
    static final HashMap<Class<?>, JsonSerializer<?>> _serializers = new HashMap();

    static {
        ToStringSerializer toStringSerializer = ToStringSerializer.instance;
        _serializers.put(Duration.class, (Object)toStringSerializer);
        _serializers.put(XMLGregorianCalendar.class, (Object)new XMLGregorianCalendarSerializer());
        _serializers.put(QName.class, (Object)toStringSerializer);
    }

    @Override
    public Collection<Map.Entry<Class<?>, JsonSerializer<?>>> provide() {
        return _serializers.entrySet();
    }

    public static class XMLGregorianCalendarSerializer
    extends SerializerBase<XMLGregorianCalendar> {
        public XMLGregorianCalendarSerializer() {
            super(XMLGregorianCalendar.class);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
            return CalendarSerializer.instance.getSchema(serializerProvider, type);
        }

        @Override
        public void serialize(XMLGregorianCalendar xMLGregorianCalendar, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            CalendarSerializer.instance.serialize((Calendar)xMLGregorianCalendar.toGregorianCalendar(), jsonGenerator, serializerProvider);
        }
    }

}


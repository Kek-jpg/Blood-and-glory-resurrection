/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Calendar
 *  java.util.Date
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.StdKeySerializer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class StdKeySerializers {
    protected static final JsonSerializer<Object> DEFAULT_KEY_SERIALIZER = new StdKeySerializer();
    protected static final JsonSerializer<Object> DEFAULT_STRING_SERIALIZER = new StringKeySerializer();

    private StdKeySerializers() {
    }

    public static JsonSerializer<Object> getStdKeySerializer(JavaType javaType) {
        if (javaType == null) {
            return DEFAULT_KEY_SERIALIZER;
        }
        Class<?> class_ = javaType.getRawClass();
        if (class_ == String.class) {
            return DEFAULT_STRING_SERIALIZER;
        }
        if (class_ == Object.class) {
            return DEFAULT_KEY_SERIALIZER;
        }
        if (Date.class.isAssignableFrom(class_)) {
            return DateKeySerializer.instance;
        }
        if (Calendar.class.isAssignableFrom(class_)) {
            return CalendarKeySerializer.instance;
        }
        return DEFAULT_KEY_SERIALIZER;
    }

    public static class CalendarKeySerializer
    extends SerializerBase<Calendar> {
        protected static final JsonSerializer<?> instance = new CalendarKeySerializer();

        public CalendarKeySerializer() {
            super(Calendar.class);
        }

        @Override
        public void serialize(Calendar calendar, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            serializerProvider.defaultSerializeDateKey(calendar.getTimeInMillis(), jsonGenerator);
        }
    }

    public static class DateKeySerializer
    extends SerializerBase<Date> {
        protected static final JsonSerializer<?> instance = new DateKeySerializer();

        public DateKeySerializer() {
            super(Date.class);
        }

        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            serializerProvider.defaultSerializeDateKey(date, jsonGenerator);
        }
    }

    public static class StringKeySerializer
    extends SerializerBase<String> {
        public StringKeySerializer() {
            super(String.class);
        }

        @Override
        public void serialize(String string, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeFieldName(string);
        }
    }

}


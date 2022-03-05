/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Type
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  org.joda.time.DateMidnight
 *  org.joda.time.DateMidnight$Property
 *  org.joda.time.DateTime
 *  org.joda.time.LocalDate
 *  org.joda.time.LocalDate$Property
 *  org.joda.time.LocalDateTime
 *  org.joda.time.LocalDateTime$Property
 *  org.joda.time.Period
 *  org.joda.time.ReadableInstant
 *  org.joda.time.ReadablePartial
 *  org.joda.time.format.DateTimeFormatter
 *  org.joda.time.format.ISODateTimeFormat
 */
package com.flurry.org.codehaus.jackson.map.ext;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.ToStringSerializer;
import com.flurry.org.codehaus.jackson.map.util.Provider;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class JodaSerializers
implements Provider<Map.Entry<Class<?>, JsonSerializer<?>>> {
    static final HashMap<Class<?>, JsonSerializer<?>> _serializers = new HashMap();

    static {
        _serializers.put(DateTime.class, (Object)new DateTimeSerializer());
        _serializers.put(LocalDateTime.class, (Object)new LocalDateTimeSerializer());
        _serializers.put(LocalDate.class, (Object)new LocalDateSerializer());
        _serializers.put(DateMidnight.class, (Object)new DateMidnightSerializer());
        _serializers.put(Period.class, (Object)ToStringSerializer.instance);
    }

    @Override
    public Collection<Map.Entry<Class<?>, JsonSerializer<?>>> provide() {
        return _serializers.entrySet();
    }

    public static final class DateMidnightSerializer
    extends JodaSerializer<DateMidnight> {
        public DateMidnightSerializer() {
            super(DateMidnight.class);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            String string;
            if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)) {
                string = "array";
                do {
                    return this.createSchemaNode(string, true);
                    break;
                } while (true);
            }
            string = "string";
            return this.createSchemaNode(string, true);
        }

        @Override
        public void serialize(DateMidnight dateMidnight, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)) {
                jsonGenerator.writeStartArray();
                jsonGenerator.writeNumber(dateMidnight.year().get());
                jsonGenerator.writeNumber(dateMidnight.monthOfYear().get());
                jsonGenerator.writeNumber(dateMidnight.dayOfMonth().get());
                jsonGenerator.writeEndArray();
                return;
            }
            jsonGenerator.writeString(this.printLocalDate((ReadableInstant)dateMidnight));
        }
    }

    public static final class DateTimeSerializer
    extends JodaSerializer<DateTime> {
        public DateTimeSerializer() {
            super(DateTime.class);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            String string;
            if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)) {
                string = "number";
                do {
                    return this.createSchemaNode(string, true);
                    break;
                } while (true);
            }
            string = "string";
            return this.createSchemaNode(string, true);
        }

        @Override
        public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)) {
                jsonGenerator.writeNumber(dateTime.getMillis());
                return;
            }
            jsonGenerator.writeString(dateTime.toString());
        }
    }

    protected static abstract class JodaSerializer<T>
    extends SerializerBase<T> {
        static final DateTimeFormatter _localDateFormat;
        static final DateTimeFormatter _localDateTimeFormat;

        static {
            _localDateTimeFormat = ISODateTimeFormat.dateTime();
            _localDateFormat = ISODateTimeFormat.date();
        }

        protected JodaSerializer(Class<T> class_) {
            super(class_);
        }

        protected String printLocalDate(ReadableInstant readableInstant) throws IOException, JsonProcessingException {
            return _localDateFormat.print(readableInstant);
        }

        protected String printLocalDate(ReadablePartial readablePartial) throws IOException, JsonProcessingException {
            return _localDateFormat.print(readablePartial);
        }

        protected String printLocalDateTime(ReadablePartial readablePartial) throws IOException, JsonProcessingException {
            return _localDateTimeFormat.print(readablePartial);
        }
    }

    public static final class LocalDateSerializer
    extends JodaSerializer<LocalDate> {
        public LocalDateSerializer() {
            super(LocalDate.class);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            String string;
            if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)) {
                string = "array";
                do {
                    return this.createSchemaNode(string, true);
                    break;
                } while (true);
            }
            string = "string";
            return this.createSchemaNode(string, true);
        }

        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)) {
                jsonGenerator.writeStartArray();
                jsonGenerator.writeNumber(localDate.year().get());
                jsonGenerator.writeNumber(localDate.monthOfYear().get());
                jsonGenerator.writeNumber(localDate.dayOfMonth().get());
                jsonGenerator.writeEndArray();
                return;
            }
            jsonGenerator.writeString(this.printLocalDate((ReadablePartial)localDate));
        }
    }

    public static final class LocalDateTimeSerializer
    extends JodaSerializer<LocalDateTime> {
        public LocalDateTimeSerializer() {
            super(LocalDateTime.class);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            String string;
            if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)) {
                string = "array";
                do {
                    return this.createSchemaNode(string, true);
                    break;
                } while (true);
            }
            string = "string";
            return this.createSchemaNode(string, true);
        }

        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)) {
                jsonGenerator.writeStartArray();
                jsonGenerator.writeNumber(localDateTime.year().get());
                jsonGenerator.writeNumber(localDateTime.monthOfYear().get());
                jsonGenerator.writeNumber(localDateTime.dayOfMonth().get());
                jsonGenerator.writeNumber(localDateTime.hourOfDay().get());
                jsonGenerator.writeNumber(localDateTime.minuteOfHour().get());
                jsonGenerator.writeNumber(localDateTime.secondOfMinute().get());
                jsonGenerator.writeNumber(localDateTime.millisOfSecond().get());
                jsonGenerator.writeEndArray();
                return;
            }
            jsonGenerator.writeString(this.printLocalDateTime((ReadablePartial)localDateTime));
        }
    }

}

